/**
 * 
 */
package com.yd.business.bill.crons;

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
 *	月账单定时任务
 */
@Component("billMonthCronsCrons")
public class BillMonthCrons extends BaseCrons {

	@Resource
	private IConfigAttributeService configAttributeService;
	
	/* (non-Javadoc)
	 * @see com.ym.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {

		log.debug(" BillMonthCrons begin.....");
		
		
		
		log.debug(" BillMonthCrons end.....");
	}
	

}
