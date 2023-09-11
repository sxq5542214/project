/**
 * 
 */
package com.yd.business.system.dao;

import java.util.List;

import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.bean.SystemRoleMenuRelationBean;

/**
 * @author ice
 *
 */
public interface ISystemManagerDao {

	List<SystemRoleMenuRelationBean> querySystemRoleMenuRelation(SystemRoleMenuRelationBean bean);

	SystemRoleBean findSystemRoleById(int id);

	SystemMenuBean findSystemMenuById(int id);
	
	List<SystemRoleBean> findSystemRoleByIds(List<Integer> ids);
	
	List<SystemMenuBean> findSystemMenuByIds(List<Integer> ids);

	List<SystemRoleAdminRelationBean> querySystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void createSystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void deleteSystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void createSystemRoleMenuRelation(SystemRoleMenuRelationBean bean);

	void deleteSystemRoleMenuRelation(SystemRoleMenuRelationBean bean);
	
	void updateSystemRoleMenuRelation(SystemRoleMenuRelationBean bean);
	
	void updateSystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	List<SystemMenuBean> querySystemMenuByBean(SystemMenuBean bean);

	/**
	 * 提交菜单信息，用于新建或者修改
	 * @param bean
	 */
	SystemMenuBean createOrUpdateSystemMenu(SystemMenuBean bean);
	
	/**
	 * 提交菜单信息，用于新建或者修改
	 * @param bean
	 */
	void deleteSystemMenu(SystemMenuBean bean);
	
	/**
	 * 查询系统权限组
	 * @param bean
	 * @return
	 */
	List<SystemRoleBean> querySystemRoleBeanByBean(SystemRoleBean bean);
	
	SystemRoleMenuRelationBean findSystemRoleMenuRelationById(SystemRoleMenuRelationBean bean);
	SystemRoleAdminRelationBean findSystemRoleAdminRelationById(SystemRoleAdminRelationBean bean);
	
	/**
	 * 提交权限信息，用于新建或者修改
	 * @param bean
	 */
	SystemRoleBean createOrUpdateSystemRole(SystemRoleBean bean);
	
	/**
	 * 返回菜单的末尾子节点
	 * @param bean
	 * @return
	 */
	List<SystemMenuBean> queryLastChildMenu(SystemMenuBean bean);
	
	/**
	 * 没有关联的菜单
	 * @return
	 */
	List<SystemMenuBean> queryLastChildMenuNotExist(SystemRoleBean bean);
	
	/**
	 * 权限的删除
	 * @param bean
	 */
	void deleteSystemRole(SystemRoleBean bean);

	List<SystemMenuBean> querySystemMenuListByOperatorId(long operatorid);

	List<SystemRoleBean> querySystemRoleListByOperatorId(long operatorid);

}
