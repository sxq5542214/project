/**
 * 
 */
package com.yd.business.msgcenter.crons;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.msgcenter.service.IMsgCenterSendService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * 
 * 消息中心待发送的定时任务
 * @author ice
 *
 */
@Component("msgCenterAssignSendTimeCrons")
public class MsgCenterAssignSendTimeCrons extends BaseCrons {

	@Resource
	private IMsgCenterSendService msgCenterSendService;
	@Resource
	private IMsgCenterArticleService msgCenterArticleService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	@Resource
	private IUserWechatService userWechatService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		
		//查询48小时内访问过的用户，是否有需要发送的文章
		queryAndSendArticleWithin48Hour();
		
		
		
		
		//查询并发送指定发送时间的文章
		queryAndSendAssignTimeArticle();
		
	}
	
	/**
	 * 给48小时内访问过的用户发送特定的文章
	 */
	private void queryAndSendArticleWithin48Hour(){
		//48小时内的用户
		List<UserWechatBean> userList = userWechatService.queryUserListWithin48Hour();
		
		for( int i = 0 ; i < userList.size() ; i++ ){
			UserWechatBean user = userList.get(i);
			//发送用户访问后的未读文章
			msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SYSTEM_ACCESS_READING, null, user);
			
			//每300个线程休息0.5秒
			if(i % 100 == 0){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error(e, e);
				}
			}
		}
		
		
		
		
	}
	
	
	
	
	/**
	 * 查询并发送指定发送时间的文章
	 */
	private void queryAndSendAssignTimeArticle(){

		String now_time = DateUtil.getNowDateStr();
		MsgCenterArticleConditionBean condition = new MsgCenterArticleConditionBean();
		condition.setStatus(MsgCenterArticleBean.STATUS_ENABLE);
		condition.setNow_time(now_time);
		condition.setSend_time(now_time);
		condition.setAssign_send_status(MsgCenterArticleBean.ASSIGN_SEND_STATUS_WAIT);
		//查询所有指定时间的文章列表
		List<MsgCenterArticleBean> list = msgCenterArticleService.queryArticle(condition );
		
		for(MsgCenterArticleBean article : list){
			
			UserWechatConditionBean bean = new UserWechatConditionBean();
			if( StringUtil.isNotNull(article.getArea())){
				bean.setCity(article.getArea());
			}
			if( article.getSex_type() != null){
				bean.setSex(article.getSex_type().toString());
			}
			bean.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
			//48小时以内的用户
			Calendar c = Calendar.getInstance();
			c.add(Calendar.HOUR_OF_DAY, -48);
			String start_time = DateUtil.formatDate(c.getTime());
			String end_time = DateUtil.getNowDateStrSSS();
			bean.setStart_date(start_time);
			bean.setEnd_date(end_time);
			//查询所有满足的用户
			List<UserWechatBean> users = userWechatService.queryUser(bean );
			
			MsgCenterUserActionBean userAction = new MsgCenterUserActionBean();
			userAction.setAction_type(MsgCenterActionDefineBean.ACTION_TYPE_SYSTEM_ASSIGN_SEND_TIME);
			JSONObject json = new JSONObject(userAction);
			
			//一个一个发消息
			for(UserWechatBean user : users){
				userAction.setOpenid(user.getOpenid());
				userAction.setNick_name(user.getNick_name());
				json.put("openid", user.getOpenid());
				json.put("nick_name", user.getNick_name());
				userAction.setAction_param(json.toString());
				
				msgCenterSendService.sendArticleToTargetObject(article, userAction);
				
			}
			
			article.setAssign_send_status(MsgCenterArticleBean.ASSIGN_SEND_STATUS_SUCCESS);
			article.setAssign_send_time(DateUtil.getNowDateStr());
			msgCenterArticleService.saveOrUpdateArticle(article);
		}
	}

}
