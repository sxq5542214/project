package com.yd.business.other.service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.TaskCronsBean;

public interface ITaskSchedulerService {

	/**
	 * 分页查询crons表信息
	 */
	public PageinationData queryTaskCronsPage(TaskCronsBean bean);
	
	/**
	 * 删除crons表数据
	 */
	public void deleteTaskCron(TaskCronsBean bean);

	/**
	 * 更改crons表数据
	 */
	public void editTaskCron(TaskCronsBean bean);
}
