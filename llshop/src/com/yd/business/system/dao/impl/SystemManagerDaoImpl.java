/**
 * 
 */
package com.yd.business.system.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.system.bean.SystemBean;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.bean.SystemRoleMenuRelationBean;
import com.yd.business.system.dao.ISystemManagerDao;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Repository("systemManagerDao")
public class SystemManagerDaoImpl extends BaseDao implements ISystemManagerDao {
	private static final String NAMESPACE = "systemManager.";
	
	@Override
	public List<SystemRoleMenuRelationBean> querySystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"querySystemRoleMenuRelation", bean);
	}
	
	@Override
	public SystemRoleBean findSystemRoleById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE +"findSystemRoleById", id);
	}
	
	@Override
	public SystemMenuBean findSystemMenuById(int id){
		return sqlSessionTemplate.selectOne(NAMESPACE +"findSystemMenuById", id);
	}
	
	@Override
	public List<SystemRoleBean> findSystemRoleByIds(List<Integer> ids){
		return sqlSessionTemplate.selectList(NAMESPACE +"findSystemRoleByIds", ids);
	}
	
	@Override
	public List<SystemMenuBean> findSystemMenuByIds(List<Integer> ids){
		return sqlSessionTemplate.selectList(NAMESPACE +"findSystemMenuByIds", ids);
	}
	
	@Override
	public List<SystemRoleAdminRelationBean> querySystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE +"querySystemRoleAdminRelation", bean);
	}
	
	@Override
	public void createSystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		sqlSessionTemplate.insert(NAMESPACE + "createSystemRoleAdminRelation", bean);
	}
	
	@Override
	public void deleteSystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		sqlSessionTemplate.delete(NAMESPACE +"deleteSystemRoleAdminRelation", bean);
	}
	
	@Override
	public void createSystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		sqlSessionTemplate.insert(NAMESPACE +"createSystemRoleMenuRelation", bean);
	}
	
	@Override
	public void deleteSystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		sqlSessionTemplate.delete(NAMESPACE +"deleteSystemRoleMenuRelation", bean);
	}
	
	@Override
	public List<SystemMenuBean> querySystemMenuByCustomer(int customer_id,Integer admin_id){
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("customer_id", customer_id);
		param.put("admin_id", admin_id);
		param.put("status", SystemBean.STATUS_ENABLE);
		return sqlSessionTemplate.selectList(NAMESPACE + "querySystemMenuByAdmin", param);
	}

	@Override
	public List<SystemMenuBean> querySystemMenuByBean(SystemMenuBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "querySystemMenuByBean", bean);
	}

	@Override
	public SystemMenuBean createOrUpdateSystemMenu(SystemMenuBean bean) {
		if(StringUtil.isNull(bean.getId())){
			bean.setCreate_time(DateUtil.getNowDateStr());
			sqlSessionTemplate.insert(NAMESPACE + "createSystemMenu", bean);
		}else{
			bean.setModify_time(DateUtil.getNowDateStr());
			sqlSessionTemplate.update(NAMESPACE + "updateSystemMenu", bean);
		}
		return bean;
	}

	@Override
	public void deleteSystemMenu(SystemMenuBean bean) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteSystemMenu", bean);
	}

	@Override
	public List<SystemRoleBean> querySystemRoleBeanByBean(SystemRoleBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE +"querySystemRoleBeanByBean", bean);
	}

	@Override
	public void updateSystemRoleMenuRelation(SystemRoleMenuRelationBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		sqlSessionTemplate.update(NAMESPACE + "updateSystemRoleMenuRelation", bean);
	}
	
	@Override
	public void updateSystemRoleAdminRelation(SystemRoleAdminRelationBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		sqlSessionTemplate.update(NAMESPACE + "updateSystemRoleAdminRelation", bean);
	}

	@Override
	public SystemRoleMenuRelationBean findSystemRoleMenuRelationById(SystemRoleMenuRelationBean bean) {
		if(StringUtil.isNull(bean.getId())){
			return null;
		}else{
			return sqlSessionTemplate.selectOne(NAMESPACE + "findSystemRoleMenuRelationById", bean);
		}
	}
	@Override
	public SystemRoleAdminRelationBean findSystemRoleAdminRelationById(SystemRoleAdminRelationBean bean) {
		if(StringUtil.isNull(bean.getId())){
			return null;
		}else{
			return sqlSessionTemplate.selectOne(NAMESPACE + "findSystemRoleAdminRelationById", bean);
		}
	}
	@Override
	public SystemRoleBean createOrUpdateSystemRole(SystemRoleBean bean) {
		if(StringUtil.isNull(bean.getId())){
			bean.setCreate_time(DateUtil.getNowDateStr());
			sqlSessionTemplate.insert(NAMESPACE + "createSystemRole", bean);
		}else{
			bean.setModify_time(DateUtil.getNowDateStr());
			sqlSessionTemplate.update(NAMESPACE + "updateSystemRole", bean);
		}
		return bean;
	}

	@Override
	public List<SystemMenuBean> queryLastChildMenu(SystemMenuBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryLastChildMenu", bean);
	}

	@Override
	public List<SystemMenuBean> queryLastChildMenuNotExist(SystemRoleBean bean) {
		return sqlSessionTemplate.selectList(NAMESPACE + "queryLastChildMenuNotExist", bean);
	}

	@Override
	public void deleteSystemRole(SystemRoleBean bean) {
		sqlSessionTemplate.delete(NAMESPACE + "deleteSystemRole", bean);
	}
}
