/**
 * 
 */
package com.yd.business.channel.crons;

import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.channel.bean.ChannelBean;
import com.yd.business.channel.service.IChannelService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.user.bean.UserWechatBean;

/**
 * 通道告警定时任务
 * @author ice
 *
 */
@Component("channelAlarmCrons")
public class ChannelAlarmCrons extends BaseCrons {
	
	@Resource
	private IChannelService channelService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
//		暂时不用了，改为实时计算时触发告警
		
//		ChannelBean bean = new ChannelBean();
//		//查询启用的通道
//		bean.setStatus(ChannelBean.STATUS_ENABLE);
//		List<ChannelBean> list = channelService.queryChannel(bean );
//		
//		for(ChannelBean cb : list){
//			//判断是否达到告警值
//			if(cb.getAlarm_balance() != null && cb.getCalc_balance() != null && cb.getAlarm_balance() > 0
//					&& cb.getAlarm_balance().intValue() >= cb.getCalc_balance()){
//				
//				//触发消息中心的告警动作
//				msgCenterActionService.saveAndHandleUserAction(UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY, MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_TEMPLATE_CHANNEL_PRICE_ALARM  , null, cb);
//				
//			}
//		}
	}
}
