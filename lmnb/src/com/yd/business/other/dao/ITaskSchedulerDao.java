/**
 * 
 */
package com.yd.business.other.dao;

import java.util.List;

import com.yd.business.other.bean.TaskCronsBean;

/**
 * @author ice
 *
 */
public interface ITaskSchedulerDao {
	public List<TaskCronsBean> queryTaskCrons();
	
	void updateTaskStatus(Integer id, Integer status, String desc,
			Integer enable);

	TaskCronsBean findTaskCrons(TaskCronsBean bean);

	List<TaskCronsBean> queryTaskCrons(TaskCronsBean bean);
	
	/**
	 * 分页查询crons表数据
	 */
	public List<TaskCronsBean> queryTaskCronsPage(TaskCronsBean bean);

	/**
	 * 查询crons表总数
	 */
	public int taskCronsCount(TaskCronsBean bean);
	
	/**
	 * 删除cron表数据
	 */
	public void deleteTaskCron(TaskCronsBean bean);
	
	
	/**
	 * 后台插入cron表数据
	 */
	public void insertAdminTaskCron(TaskCronsBean bean);
	
	
	/**
	 * 后台更新cron表数据
	 */
	public void updateAdminTaskStatus(TaskCronsBean bean);
}
