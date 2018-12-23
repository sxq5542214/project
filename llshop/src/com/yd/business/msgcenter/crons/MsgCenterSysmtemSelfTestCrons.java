/**
 * 
 */
package com.yd.business.msgcenter.crons;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.log.service.ILogService;
import com.yd.business.other.crons.BaseCrons;

/**
 * @author ice
 *
 */
@Component("msgCenterSysmtemSelfTestCrons")
public class MsgCenterSysmtemSelfTestCrons extends BaseCrons {
	@Resource
	private ILogService logService;
	
	/* (non-Javadoc)
	 * @see com.yd.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		//系统自检测定时任务
		

	}

}
