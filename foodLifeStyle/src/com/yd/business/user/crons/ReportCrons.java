/**
 * 
 */
package com.yd.business.user.crons;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.other.crons.BaseCrons;
import com.yd.business.user.dao.IUserWechatDao;
import com.yd.business.user.service.IUserWechatService;

/**
 * @author ice
 *
 */
@Component("reportCrons")
public class ReportCrons extends BaseCrons {
	@Resource
	private IUserWechatDao userWechatDao;
	
	@Resource
	private IUserWechatService userWechatService;
	/* (non-Javadoc)
	 * @see com.ym.business.other.crons.BaseCrons#execJob(org.quartz.JobExecutionContext)
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
			log.debug(" reportCrons begin.....");
		
			log.debug(" reportCrons end.....");
	}
}