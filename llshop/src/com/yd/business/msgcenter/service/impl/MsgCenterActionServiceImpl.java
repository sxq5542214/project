/**
 * 
 */
package com.yd.business.msgcenter.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.runable.BaseRunable;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterActionTriggerConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.dao.IMsgCenterActionDao;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.msgcenter.service.IMsgCenterSendService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("msgCenterActionService")
public class MsgCenterActionServiceImpl extends BaseService implements IMsgCenterActionService {
	
	@Resource
	private IMsgCenterActionDao msgCenterActionDao;
	@Resource
	private IMsgCenterArticleService msgCenterArticleService;
	@Resource
	private IMsgCenterSendService msgCenterSendService;
	@Resource
	private IUserWechatService userWechatService;
	
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	
	/**
	 * 处理用户各类动作，确定返回给用户什么
	 * @param action
	 */
	private void handleUserAction(MsgCenterUserActionBean action){
		
		MsgCenterActionDefineBean realAction = null;
		//查询用户动作是否有过定义
		MsgCenterActionDefineBean define = queryActionDefineByActionType(action.getAction_type() );
		if(define != null){
			realAction = define;
			
			//是否转义，如果转义，则用新action查询
			if(define.getCheck_key() == MsgCenterActionDefineBean.CHECK_KEY_YES){
				
				// 如果父类型有定义动作和文章关系的话，就先发父的
				//查询用户动作与文章的关系,一个动作可能会有多个关系，比如支付成功，既要发短信通知，也要发微信通知
				List<MsgCenterActionArticleRelationBean> relationList = findActionArticleRelationByType(realAction.getAction_type());
				
				for(MsgCenterActionArticleRelationBean relation : relationList)
				{
					// 每个动作与文章的关系都走发送逻辑，到底要不要发，交给后面来判断
					queryAndSendArticleToUser(relation, action);
					
				}
				
				//查找子动作，将要处理的动作置为子的
				realAction = checkAndFindActionDefineByParent(action);
			}
			
			if(realAction == null){
				log.warn("MSGCenter not find actionType:"+action.getAction_type() +"   actionValue:"+action.getAction_value());
				return ;
			}
			//查询用户动作与文章的关系,一个动作可能会有多个关系，比如支付成功，既要发短信通知，也要发微信通知
			List<MsgCenterActionArticleRelationBean> relationList = findActionArticleRelationByType(realAction.getAction_type());
			
			for(MsgCenterActionArticleRelationBean relation : relationList)
			{
				// 每个动作与文章的关系都走发送逻辑，到底要不要发，交给后面来判断
				queryAndSendArticleToUser(relation, action);
			}
		}
	}
	
	/**
	 * 通过动作与文章关系查询到文章，并发送给用户（关系可能是一对多，也可能是一对一，所以会分开取）
	 * @param relation
	 * @param action
	 */
	private void queryAndSendArticleToUser(MsgCenterActionArticleRelationBean relation,MsgCenterUserActionBean action){
		
		List<MsgCenterArticleBean> articleList = null;
		
		//如果有指定的，即一对一
		if(relation.getArticle_id() != null ){
			
			MsgCenterArticleConditionBean condition = new MsgCenterArticleConditionBean();
			condition.setNow_time(DateUtil.getNowDateStr());
			condition.setId(relation.getArticle_id());
			
			articleList = msgCenterArticleService.queryArticle(condition );
//			articleList = new ArrayList<MsgCenterArticleBean>();
//			articleList.add(article);
		}else{
			//没有指定的，即可能是多对多
			int article_type = relation.getArticle_type();
			
			//从分类的众多内容中找一条
			articleList = msgCenterArticleService.findArticleByType(article_type,action);
		}
		UserWechatBean user = userWechatService.findUserWechatByOpenId(action.getOpenid());
		
		//循环列表，进行文章处理
		for(MsgCenterArticleBean article : articleList){

			try{
				//检查文章是否符合发送条件，符合就发送
				checkAndSendArticle(user, article, action, relation);
				
			}catch (Exception e) {
				log.error(e, e);
			}
		}
	}
	
	/**
	 * 检查文章是否符合发送条件，符合就发送
	 * @param user
	 * @param article
	 * @param action
	 * @param relation
	 */
	private void checkAndSendArticle(UserWechatBean user, MsgCenterArticleBean article,MsgCenterUserActionBean action,MsgCenterActionArticleRelationBean relation){
		boolean isSend = true;
			//判断用户性别条件,如果文章指定性别，那性别不一样的，就不发这篇文章
			if(article.getSex_type() != null && article.getSex_type() != Integer.parseInt(user.getSex())){
				isSend = false;
			}
			//判断用户区域条件，如果文章指定区域，那区域不一样的，就不发这篇文章
			if( StringUtil.isNotNull(article.getArea()) && !article.getArea().equals(user.getCity())){
				isSend = false;
			}
			
			if(isSend){
				String now_time = DateUtil.getNowDateStrSSS();
				
				//判断用户动作是否有指定时间,如果有指定时间，那么relation的发送时间就要加上指定时间
				String assign_send_time = action.getAssign_send_time();
				if(StringUtil.isNotNull(assign_send_time) && assign_send_time.compareTo(now_time) >= 0 ){
					Date assign ;
					try{
						assign = DateUtil.parseDate(assign_send_time);
						// 毫秒数，要除1000
						int assign_delay = (int) (assign.getTime() - System.currentTimeMillis()) /1000;
						Integer delay = relation.getDelay_time() == null? MsgCenterActionArticleRelationBean.DELAY_TIME_NOW:relation.getDelay_time();
						if(assign_delay >=0){
							relation.setDelay_time(assign_delay + delay );
						}
					}catch (Exception e) {
						log.error(e, e);
					}
				}
				
				
				//判断是否延迟发送，还是立即发送
				if(relation.getDelay_time() == null || relation.getDelay_time() == MsgCenterActionArticleRelationBean.DELAY_TIME_NOW){
					//立即发送的，直接调用发送文章的方法
					msgCenterSendService.sendArticleToTargetObject(article, action);
				}else{
					//入等待表，延迟发送
					msgCenterSendService.createMsgWaitSendInfo(relation, action, article);
				}
			}
	}
	
	
	/**
	 * 查询用户动作与文章的关系
	 * @param action_type
	 * @return
	 */
	private List<MsgCenterActionArticleRelationBean> findActionArticleRelationByType(String action_type){
		MsgCenterActionArticleRelationBean condition = new MsgCenterActionArticleRelationBean();
		condition.setAction_type(action_type);
		condition.setStatus(MsgCenterActionArticleRelationBean.STATUS_ENABLE);
		
		return msgCenterActionDao.queryMsgCenterActionArticleRelation(condition);
		
	}
	
	/**
	 * 查询消息动作定义表
	 * @param action_type
	 * @return
	 */
	@Override
	public MsgCenterActionDefineBean queryActionDefineByActionType(String action_type){
		//查询用户动作定义表
		MsgCenterActionDefineBean actionDefine = new MsgCenterActionDefineBean();
		actionDefine.setStatus(MsgCenterActionDefineBean.STATUS_ENABLE);
		actionDefine.setAction_type(action_type);
		
		List<MsgCenterActionDefineBean> list = queryActionDefine(actionDefine);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<MsgCenterActionDefineBean> queryActionDefine(MsgCenterActionDefineBean bean){
		return msgCenterActionDao.queryActionDefine(bean);
	}
	
	/**
	 * 转义和查询用户动作
	 * @param action
	 * @return
	 */
	private MsgCenterActionDefineBean checkAndFindActionDefineByParent(MsgCenterUserActionBean action){

		String key = action.getAction_value();
		if(StringUtil.isNull(action.getAction_value())){
			key = MsgCenterUserActionBean.ACTION_VALUE_DEFAULT;
		}
		
		
		//查询用户动作为父动作的所有子动作
		MsgCenterActionDefineBean condition = new MsgCenterActionDefineBean();
		condition.setParent_action_type(action.getAction_type());
		condition.setStatus(MsgCenterActionDefineBean.STATUS_ENABLE);
		List<MsgCenterActionDefineBean> list = msgCenterActionDao.queryActionDefine(condition);
		for(MsgCenterActionDefineBean bean : list){
			if(key.indexOf(bean.getAction_key()) >=0){
				return bean;
			}
		}
		
		//设置key为未找到，再次查询
		key = MsgCenterUserActionBean.ACTION_VALUE_NOTFIND;
		for(MsgCenterActionDefineBean bean : list){
			if(key.indexOf(bean.getAction_key()) >=0){
				return bean;
			}
		}
		
		return null;
	}
	
	/**
	 * 保存用户动作
	 * @param user
	 * @param action_type
	 * @param action_value
	 * @param action_param
	 */
	public MsgCenterUserActionBean saveUserAction(UserWechatBean user,String action_type,String action_value,Object action_param ,Integer is_save){
		String action_param_json = null;
		String param_class = null;
		if(action_param != null){
			JSONObject jso = null;
			
			if(action_param instanceof Map ){
				jso = new JSONObject((Map) action_param);
			}else{
				jso = new JSONObject(action_param);
			}
			
			
			action_param_json = jso.toString();
			param_class = action_param.getClass().getName();
		}
		
//		MsgCenterActionDefineBean condition = new MsgCenterActionDefineBean();
//		condition.setAction_type(action_type);
//		msgCenterActionDao.queryActionDefine(condition );
		
		MsgCenterUserActionBean bean = new MsgCenterUserActionBean();
		bean.setAction_type(action_type);
		bean.setAction_param(action_param_json);
		bean.setAction_param_class(param_class);
		bean.setAction_value(action_value);
		bean.setCreate_time(DateUtil.getNowDateStrSSS());
		bean.setNick_name(user.getNick_name());
		bean.setOpenid(user.getOpenid());
		bean.setUser_id(user.getId());
		bean.setStatus(MsgCenterUserActionBean.STATUS_ENABLE);
		
		if(is_save == null || is_save == MsgCenterActionDefineBean.IS_SAVE_YES){
			msgCenterActionDao.createUserAction(bean);
		}
		return bean;
	}
	
	/**
	 * 保存用户动作
	 * @param openid
	 * @param action_type
	 * @param action_value
	 * @param action_param
	 */
	@Override
	public void saveAndHandleUserAction(final String openid,final String action_type,final String action_value,final Object action_param ){
		saveAndHandleUserAction(openid, action_type, action_value, action_param, null);
	}

	/**
	 * 保存用户动作
	 * @param openid
	 * @param action_type
	 * @param action_value
	 * @param action_param
	 * @param assign_send_time 指定发送时间
	 */
	@Override
	public void saveAndHandleUserAction(final String openid,final String action_type,final String action_value,final Object action_param ,final String assign_send_time){
		
		//启线程处理用户的各类动作
		taskExecutor.execute(new BaseRunable() {
			@Override
			public void runMethod() {
				MsgCenterActionDefineBean define = queryActionDefineByActionType(action_type);
				//如果用户这个动作未在动作配置表中定义，则不记录
				if(define == null){
					return ;
				}
				
				UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				//如果用户为空，可能是线程执行太快，先休眠一会,再去查询
				if(user == null){
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						log.error(e, e);
					}
				}
				user = userWechatService.findUserWechatByOpenId(openid);
				
				if(user != null ){
					MsgCenterUserActionBean action = saveUserAction(user, action_type, action_value, action_param,define.getIs_save());
					action.setAssign_send_time(assign_send_time);
					//处理用户动作
					handleUserAction(action);
				}
				
			}
		});
	}
	@Override
	public List<MsgCenterActionTriggerConditionBean> queryActionTriggerCondition(MsgCenterActionTriggerConditionBean bean){
		return msgCenterActionDao.queryActionTriggerCondition(bean);
	}
	
	@Override
	public MsgCenterUserActionBean findUserActionById(int id){
		MsgCenterUserActionBean bean = new MsgCenterUserActionBean();
		bean.setId(id);
		
		List<MsgCenterUserActionBean> list = queryUserAction(bean);
		if(list.size() >0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<MsgCenterUserActionBean> queryUserAction(MsgCenterUserActionBean bean){
		return msgCenterActionDao.queryUserAction(bean);
	}
	
	@Override
	public List<MsgCenterActionArticleRelationBean> queryUserActionArticleRelation(MsgCenterActionArticleRelationBean bean){

		return msgCenterActionDao.queryMsgCenterActionArticleRelation(bean);
	}

	@Override
	public void createOrUpdateActionArticleRelation(MsgCenterActionArticleRelationBean bean) {

		if(bean.getAction_name() != null){
			bean.setAction_name(bean.getAction_name().trim());
		}
		if(bean.getArticle_name() != null){
			bean.setArticle_name(bean.getArticle_name().trim());
		}
		if(bean.getArticle_type_name() != null){
			bean.setArticle_type_name(bean.getArticle_type_name().trim());
		}
		
		if(bean.getId() == null){
			msgCenterActionDao.createActionArticleRelation(bean);
		}else{
			msgCenterActionDao.updateActionArticleRelation(bean);
		}
		
	}
	
}
