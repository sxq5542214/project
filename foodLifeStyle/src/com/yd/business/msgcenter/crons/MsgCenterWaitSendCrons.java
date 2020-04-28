/**
 * 
 */
package com.yd.business.msgcenter.crons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.msgcenter.bean.MsgCenterActionTriggerConditionBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterSendLogBean;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.bean.MsgCenterWaitSendBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.msgcenter.service.IMsgCenterSendService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.sms.service.ISmsService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;

/**
 * 
 * 消息中心待发送的定时任务
 * @author ice
 *
 */
@Component("msgCenterWaitSendCrons")
public class MsgCenterWaitSendCrons extends BaseCrons {

	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IMsgCenterSendService msgCenterSendService;
	@Resource
	private IMsgCenterArticleService msgCenterArticleService;
	@Resource
	private ISmsService smsService;
	@Resource
	private IWechatService wechatUserService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		
		queryAndSendWaitMSG();
		
		queryAndSendWaitSMS();
		
		queryAndSendWaitWechatMsg();
		
	}
	

	/**
	 * 查询并执行待发送的短信
	 */
	private void queryAndSendWaitSMS(){
		
		smsService.queryWaitAndSend();
		
	}
	
	private void queryAndSendWaitWechatMsg(){
		
		wechatUserService.execSendMessageToUserList();
	}
	
	/**
	 * 查询并执行待发送的消息
	 */
	private void queryAndSendWaitMSG(){
		String symbol = ",";
		
		MsgCenterWaitSendBean condition = new MsgCenterWaitSendBean();
		condition.setStatus(MsgCenterWaitSendBean.STATUS_WAIT);
		condition.setOrderby(" openid ,id desc");
		condition.setExec_time(DateUtil.getNowDateStr());
		//查询所有待发送的数据
		List<MsgCenterWaitSendBean> waitSendList = msgCenterSendService.queryWaitSendList(condition);
		
		//用来记录已发送过的，避免重复发送
		Map<String,Object> sendMap = new HashMap<String, Object>();

		MsgCenterActionTriggerConditionBean triggerCondition = new MsgCenterActionTriggerConditionBean();
		
		for(MsgCenterWaitSendBean waitSend : waitSendList){
			
			try{
				String action_type = waitSend.getAction_type();
				String openid = waitSend.getOpenid();
				//做为action_type + openid 做为 map 的key
				StringBuilder sb = new StringBuilder(openid);
				sb.append(symbol).append(action_type);
				
				String key = sb.toString();
				//看map里是否有数据，如果有，就不再次发送了
				Object sended =  sendMap.get(key);

				waitSend.setStatus(MsgCenterWaitSendBean.STATUS_ALREADY_SEND);
				if(sended == null){
					
					if(checkNeedSend(triggerCondition, waitSend)){
						
						MsgCenterUserActionBean action = msgCenterActionService.findUserActionById(waitSend.getUser_action_id());
						MsgCenterArticleBean article = msgCenterArticleService.findArticleById(waitSend.getArticle_id());
						msgCenterSendService.sendArticleToTargetObject(article , action);
						waitSend.setStatus(MsgCenterWaitSendBean.STATUS_SEND);
					}
				}
				
				//更新等待表
				waitSend.setModify_time(DateUtil.getNowDateStr());
				msgCenterSendService.updateWaitSend(waitSend);
				
				sendMap.put(key, key);
			
			}catch (Exception e) {
				log.error(e, e);
			}
		}
	}
	
	/**
	 * 查询是否需要发送
	 * @param triggerCondition
	 * @param openid
	 * @param action_type
	 * @return
	 */
	private boolean checkNeedSend(MsgCenterActionTriggerConditionBean triggerCondition,MsgCenterWaitSendBean waitSend){
		boolean flag = true;
		String action_type = waitSend.getAction_type();
		String openid = waitSend.getOpenid();
		
		triggerCondition.setAction_type(action_type);
		//查询发送条件表
		List<MsgCenterActionTriggerConditionBean> conditions = msgCenterActionService.queryActionTriggerCondition(triggerCondition);
		List<String> action_type_list = new ArrayList<String>();
		for(MsgCenterActionTriggerConditionBean tc : conditions){
			
			String action = tc.getNot_trigger_action();
			action_type_list.add(action);
		}
		//拼装条件，到发送日志表中查询
		if(action_type_list.size() > 0 ){
			MsgCenterSendLogBean logCondition = new MsgCenterSendLogBean();
			logCondition.setOpenid(openid);
//			logCondition.setAction_type(action_type);
//			logCondition.setExec_time(DateUtil.getNowDateStr());
			logCondition.setAction_type_list(action_type_list);
			logCondition.setStart_time(waitSend.getCreate_time());
			logCondition.setEnd_time(DateUtil.getNowDateStr());
			
			List<MsgCenterSendLogBean> listLog = msgCenterSendService.querySendLogList(logCondition);
			
			if(listLog.size() > 0){
				flag = false;
			}
		}
		
		return flag;
	}
	

}
