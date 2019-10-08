/**
 * 
 */
package com.yd.business.other.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerKey;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.TaskCronsBean;
import com.yd.business.other.crons.BaseCrons;
import com.yd.business.other.dao.ITaskSchedulerDao;
import com.yd.business.other.service.ITaskSchedulerService;
import com.yd.util.StringUtil;

/**
 * 定时任务管理基类
 * @author ice
 *
 */
@Service("taskSchedulerService")
public class TaskSchedulerServiceImpl extends BaseService implements ITaskSchedulerService {

	
	@Resource
	private ITaskSchedulerDao taskSchedulerDao;
	private Map<Integer,TaskCronsBean> cronsMap = new HashMap<Integer, TaskCronsBean>();
	@Resource
	private StdScheduler schedulerFactory;
	private Long sleepTime = (long) (100 * 1000);
	
	public void setSleepTime(Long sleepTime) {
		this.sleepTime = sleepTime;
	}

	public TaskSchedulerServiceImpl(){
	}
	
	@PostConstruct
	public void initTasks(){
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.out.println(" init TaskSchedulerService");
					Thread.sleep(20 * 1000);
				} catch (Exception e) {
					e.printStackTrace();
					log.error(e,e);
				}
				Properties pops = BaseContext.getPops();
				if(pops == null) {
					pops = BaseContext.initPops();
				}
				String flag = BaseContext.getPops().getProperty("crons.config", "false");
				
				if(flag.equalsIgnoreCase("true"))
				{
					checkTaskInfo();
				}else{
					System.out.println("TaskSchedulerService not start,because conf is false");
					schedulerFactory.shutdown();
				}
			}
		}," initTaskSchedulerThread ").start();
		
	}
	
	public void checkTaskInfo(){
		System.out.println(" initTasks start !");
		while(true){
			try {
				List<TaskCronsBean> list = taskSchedulerDao.queryTaskCrons();
				for(TaskCronsBean bean : list){
					try {
						if(bean.getEnable() == TaskCronsBean.ENABLE_TRUE){
							addOrUpdateTask(bean);
						}else{
							removeTask(bean);
						}
					}catch (Exception e) {
						e.printStackTrace();
						log.error(" operation TaskBean id:" + bean.getCron_id() + " Error!", e);
					}
				}
				log.debug("task scheduler will be sleep " + sleepTime +"ms ");
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
				log.error(e,e);
			}
		}
	}
	
	
	private void removeTask(TaskCronsBean bean) throws SchedulerException {
		
		if(cronsMap.get(bean.getCron_id()) != null ){
			cronsMap.remove(bean.getCron_id());
			JobKey key = new JobKey(bean.getCron_id().toString(), Scheduler.DEFAULT_GROUP);
			schedulerFactory.deleteJob(key);
		}
	}
	
	
	private void addOrUpdateTask(TaskCronsBean bean) throws Exception{

		Class<? extends Job> className = getClass(bean.getClass_name());
		//是BaseCrons 的子类才执行
		if(!BaseCrons.class.isAssignableFrom(className))
		{
			throw new Exception(bean.getClass_name() +" not is BaseCrons subClass!" );
		}
		
		if(cronsMap.get(bean.getCron_id()) == null )
		{
			//添加
			CronTriggerImpl tigger = new CronTriggerImpl();
			tigger.setKey(new TriggerKey(bean.getCron_id().toString(),Scheduler.DEFAULT_GROUP));
			tigger.setCronExpression(bean.getExpression());
			tigger.setName(bean.getCron_id().toString());
			
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setKey(new JobKey(bean.getCron_id().toString(),Scheduler.DEFAULT_GROUP));
			jobDetail.setJobClass(className);
			jobDetail.getJobDataMap().put(BaseCrons.JOB_DETAIL_KEY, bean);
			
			schedulerFactory.scheduleJob(jobDetail, tigger);
			
			cronsMap.put(bean.getCron_id(), bean);
		}else if(hasChange(bean)){
			//更新
			CronTriggerImpl trigger = (CronTriggerImpl) schedulerFactory.getTrigger(new TriggerKey(bean.getCron_id().toString(), Scheduler.DEFAULT_GROUP));
			CronTriggerImpl newTrigger = (CronTriggerImpl)trigger;
			newTrigger.setCronExpression(bean.getExpression());
			newTrigger.setName(bean.getCron_id().toString());
			
//			JobDetailImpl jobDetail = new JobDetailImpl();
//			jobDetail.setKey();
//			jobDetail.setJobClass(className);
//			jobDetail.getJobDataMap().put(BaseCrons.JOB_DETAIL_KEY, bean);
			newTrigger.setJobKey(new JobKey(bean.getCron_id().toString(),Scheduler.DEFAULT_GROUP));
			schedulerFactory.rescheduleJob(new TriggerKey(bean.getCron_id().toString(), Scheduler.DEFAULT_GROUP), newTrigger);
			cronsMap.put(bean.getCron_id(), bean);
		}
	}
	
	private boolean hasChange(TaskCronsBean bean) {
		TaskCronsBean oldBean = cronsMap.get(bean.getCron_id());
		
		if(oldBean.getExpression().equals(bean.getExpression())){
			return false;
		}
		
		return true;
	}

//	public void addTaskToScheduler(TaskCronsBean bean) throws SchedulerException{
//		
//		if(cronsMap.get(bean.getCron_id())  != null){
////			TaskCronsBean oldBean = cronsMap.get(bean.getCron_id());
//			
////			scheduler.deleteJob(bean.getCron_id().toString(), Scheduler.DEFAULT_GROUP);
//			
//		}else{
//			
//			CronTriggerBean tigger = new CronTriggerBean();
//			try {
//				tigger.setCronExpression("0-59 * * * * ?");
//				
//				
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			
//			JobDetail jobDetail = new JobDetail(bean.getCron_id().toString(), Scheduler.DEFAULT_GROUP, bean.getClass());
//			jobDetail.getJobDataMap().put("testJob", new SchedulerFactory());
//			
//			scheduler.addJob(jobDetail, true);
//			
//			cronsMap.put(bean.getCron_id(), bean);
//			
//			scheduler.scheduleJob(jobDetail, tigger);
//		}
//	}
	
	
	private Class<? extends Job> getClass(String className) throws ClassNotFoundException{
		Class<? extends Job> c;
		if (checkIsSpringBean(className)) { // 从SPRING中取
			 Object obj = BaseContext.getBean(className);
			 c = (Class<? extends Job>) obj.getClass();
		} else {
			c = (Class<? extends Job>) Class.forName(className);
		}
		return c;
	}
	
	public static boolean checkIsSpringBean(String className) {
		boolean isSpring = false;
		if (className.indexOf(".") != -1) {
			isSpring = false;
		} else {
			isSpring = true;
		}
		return isSpring;
	}

	/**
	 * 分页查询crons表信息
	 */
	@Override
	public PageinationData queryTaskCronsPage(TaskCronsBean bean) {
		PageinationData pd = new PageinationData();
		try{
			List<TaskCronsBean> showList = taskSchedulerDao.queryTaskCronsPage(bean);
		    int count = taskSchedulerDao.taskCronsCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
	}

	@Override
	public void deleteTaskCron(TaskCronsBean bean) {
		taskSchedulerDao.deleteTaskCron(bean);
	}
	
	

	@Override
	public void editTaskCron(TaskCronsBean bean) {
		if(StringUtil.isNull(bean.getCron_id())){
			taskSchedulerDao.insertAdminTaskCron(bean);
		}else{
			taskSchedulerDao.updateAdminTaskStatus(bean);
		}
	}
}
