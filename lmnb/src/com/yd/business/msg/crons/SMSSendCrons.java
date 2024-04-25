/**
 * 
 */
package com.yd.business.msg.crons;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yd.basic.framework.cron.BaseCrons;
import com.yd.business.msg.service.ISMSService;

/**
 * @author ice
 *	短信发送定时任务
 */
@Component("SMSSendCrons")
public class SMSSendCrons extends BaseCrons {
	@Autowired
	private ISMSService smsService ;

	@Override
	public void execJob(JobExecutionContext jobContext) {

		log.debug(" SMSSendCrons begin.....");
		
		smsService.sendJXTsmsByWait();
		
		
		log.debug(" SMSSendCrons end.....");
	}

}
