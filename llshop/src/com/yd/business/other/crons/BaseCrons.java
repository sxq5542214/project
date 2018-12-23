/**
 * 
 */
package com.yd.business.other.crons;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;

import com.yd.basic.framework.context.BaseContext;
import com.yd.business.other.bean.TaskCronsBean;
import com.yd.business.other.dao.ITaskSchedulerDao;


/**
 * 
 顺便贴一下cronExpression表达式备忘：
还不清楚的请百度！
字段 允许值 允许的特殊字符

秒 0-59 , – * /

分 0-59 , – * /

小时 0-23 , – * /

日期 1-31 , – * ? / L W C

月份 1-12 或者 JAN-DEC , – * /

星期 1-7 或者 SUN-SAT , – * ? / L C #

年（可选） 留空, 1970-2099 , – * /

表达式意义

"0 0 12 * * ?" 每天中午12点触发

"0 15 10 ? * *" 每天上午10:15触发

"0 15 10 * * ?" 每天上午10:15触发

"0 15 10 * * ? *" 每天上午10:15触发

"0 15 10 * * ? 2005" 2005年的每天上午10:15触发

"0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发

"0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发

"0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发

"0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发

"0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发

"0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发

"0 15 10 15 * ?" 每月15日上午10:15触发

"0 15 10 L * ?" 每月最后一日的上午10:15触发

"0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发

"0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发

"0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发

每天早上6点

0 6 * * *

每两个小时

0 *\2(这个\反了)  * * *

晚上11点到早上8点之间每两个小时，早上八点

0 23-7/2，8 * * *

每个月的4号和每个礼拜的礼拜一到礼拜三的早上11点

0 11 4 * 1-3

1月1日早上4点

0 4 1 1 * 

 * @author ice
 *
 */
public abstract class BaseCrons implements Job{
	protected Logger log = Logger.getLogger(getClass());
	@Resource
	protected ITaskSchedulerDao taskSchedulerDao;
	public static final String JOB_DETAIL_KEY = "bean";
	protected TaskCronsBean taskBean;
	private BaseCrons subCrons;

	@Override
	public void execute(JobExecutionContext jobContext) {
		Long time = System.currentTimeMillis();
		JobDetail job = jobContext.getJobDetail();
		
		taskBean = (TaskCronsBean) job.getJobDataMap().get(BaseCrons.JOB_DETAIL_KEY);
		
		if(taskBean.getStatus() == TaskCronsBean.STATUS_RUNING){
			log.info(taskBean.getCron_name() +" 依然在运行中，本次任务跳过");
			return ;
		}
		
		//由于quartz里的任务直接是new出来的类，没有注入，这里需要从spring中获取一下
		if(subCrons == null && taskBean.getEnable() == TaskCronsBean.ENABLE_TRUE &&  checkIsSpringBean(taskBean.getClass_name()))
		{
			subCrons = (BaseCrons) BaseContext.getBean(taskBean.getClass_name());
		}
//		taskBean = (TaskCronsBean)job.getJobDataMap().get(JOB_DETAIL_KEY);
		String desc = "任务执行中！";
		Integer status = TaskCronsBean.STATUS_RUNING;
		Integer id = Integer.parseInt(job.getKey().getName());
		Integer enable = null;
		taskBean.setStatus(TaskCronsBean.STATUS_RUNING);
		subCrons.taskSchedulerDao.updateTaskStatus(id, status, desc, null);
		
		try{
			subCrons.execJob(jobContext);
			desc = "任务执行成功！";
			status = TaskCronsBean.STATUS_WAIT;
		}catch(Exception e){
			System.out.println(" 执行计划任务出现异常! "+taskBean.getCron_name() );
			log.error(taskBean.getCron_name()+ e.getMessage(),e);
			desc = "任务执行失败！"+e.getMessage();
			status = TaskCronsBean.STATUS_ERROR;
		}

		taskBean.setStatus(TaskCronsBean.STATUS_WAIT);
		
		time = System.currentTimeMillis() - time;
		log.debug(" crons id:"+job.getKey().getName() + " name:"+taskBean.getCron_name()+" run successed! cost:"+time+"ms");
		
		//判断是否只执行一次，如果是，则把enable改为False
		if(checkRunOnce(job)){
			enable = TaskCronsBean.ENABLE_FALSE;
			taskBean.setEnable(enable);
		}
		subCrons.taskSchedulerDao.updateTaskStatus(id, status, desc, enable);
	}
	
	private boolean checkRunOnce(JobDetail job){
		
		
		if(taskBean.getRun_once() != null && taskBean.getRun_once() == TaskCronsBean.RUNONCE_TRUE)
		{
			return true;
		}
		
		return false;
	}
	
	
	public abstract void execJob(JobExecutionContext jobContext);
	
	public static boolean checkIsSpringBean(String className) {
		boolean isSpring = false;
		if (className.indexOf(".") != -1) {
			isSpring = false;
		} else {
			isSpring = true;
		}
		return isSpring;
	}
}
