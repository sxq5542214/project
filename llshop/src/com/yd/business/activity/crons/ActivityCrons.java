/**
 * 
 */
package com.yd.business.activity.crons;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.other.crons.BaseCrons;

/**
 * @author ice
 *
 */
@Component("activityCrons")
public class ActivityCrons extends BaseCrons {
	@Resource
	private IActivityService activityService;
	
	@Resource
	private IActivitConfigService activityConfigService;

	private List<ActivityConfigBean> activityList;
	
	private static long last_time = 0;
	
	/**
	 * 各类活动开始监控的定时器，每分钟执行一次
	 */
	@Override
	public void execJob(JobExecutionContext jobContext) {
		
		//本任务设置为每分钟执行一次
		
		Calendar c = Calendar.getInstance();
		
		
		long reloadTime = 1000 * 60 ;
	}
	
	
	
}
