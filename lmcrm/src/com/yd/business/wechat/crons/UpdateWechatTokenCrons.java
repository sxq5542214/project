/**
 * 
 */
package com.yd.business.wechat.crons;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.other.crons.BaseCrons;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Component("updateWechatTokenCrons")
public class UpdateWechatTokenCrons extends BaseCrons {

	@Resource
	private IWechatService wechatService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	@Resource
	private IConfigAttributeService configAttributeService;
	
	/* (non-Javadoc)
	 * @see com.ym.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {

		log.debug(" updateWechatTokenCrons begin.....");
		
		//读取所有的微信公众号配置信息
		List<WechatOriginalInfoBean> list = wechatOriginalInfoService.queryWechatOriginalInfo(null);
		
		for(WechatOriginalInfoBean bean : list){
			wechatService.updateWechatAccessToken(bean.getOriginalid());
			
			wechatService.updateWechatJsApiTicket(bean.getOriginalid());
		}
//		//检查快要超过48小时未操作的用户，给他们发消息
//		checkUserLastAccessDateAndSendMSG();

		log.debug(" updateWechatTokenCrons end.....");
	}
	
	private void checkUserLastAccessDateAndSendMSG(){
		
		//发送 已经有47.5小时 - 48 小时之间没操作过的
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR_OF_DAY, -48);
		calendar.add(Calendar.MINUTE, 30);

		String startDate = DateUtil.formatDate(calendar.getTime());
		
		calendar.add(Calendar.MINUTE, -31);
		
		String endDate = DateUtil.formatDate(calendar.getTime());
		
		
	}

}
