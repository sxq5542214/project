/**
 * 
 */
package com.yd.business.other.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.bean.TaskCronsBean;
import com.yd.business.other.dao.ITaskSchedulerDao;

/**
 * @author ice
 *
 */
@Repository("taskSchedulerDao")
public class TaskSchedulerDaoImpl extends BaseDao implements ITaskSchedulerDao {
	private final static String NAMESPACE = "task.";
	
	@Override
	public List<TaskCronsBean> queryTaskCrons() {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryTaskCrons");
	}

	@Override
	public void updateTaskStatus(Integer id,Integer status, String desc,Integer enable) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("desc", desc);
		map.put("id", id);
		map.put("enable", enable);
		sqlSessionTemplate.update(NAMESPACE+"updateTaskStatus",map);
	}
	
	@Override
	public TaskCronsBean findTaskCrons(TaskCronsBean bean){
		List<TaskCronsBean> list = queryTaskCrons(bean);
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public List<TaskCronsBean> queryTaskCrons(TaskCronsBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE+"queryTaskCrons",bean);
	}

	@Override
	public List<TaskCronsBean> queryTaskCronsPage(TaskCronsBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryTaskCronsPage",bean,rowBound(bean));
	}

	@Override
	public int taskCronsCount(TaskCronsBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"taskCronsCount",bean);
	}

	@Override
	public void deleteTaskCron(TaskCronsBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteTaskCron", bean);
	}

	@Override
	public void insertAdminTaskCron(TaskCronsBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertAdminTaskCron",bean);
	}

	@Override
	public void updateAdminTaskStatus(TaskCronsBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateAdminTaskStatus",bean);
	}
	
	
}
