/**
 * 
 */
package com.yd.business.system.service;

import java.util.List;
import java.util.Map;

import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.bean.SystemRoleMenuRelationBean;

/**
 * @author ice
 *
 */
public interface ISystemManagerService {

	List<SystemRoleMenuRelationBean> querySystemRoleMenuRelation(SystemRoleMenuRelationBean bean);

	SystemRoleBean findSystemRoleById(int id);

	SystemMenuBean findSystemMenuById(int id);

	List<SystemRoleAdminRelationBean> querySystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void createSystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void deleteSystemRoleAdminRelation(SystemRoleAdminRelationBean bean);

	void createSystemRoleMenuRelation(SystemRoleMenuRelationBean bean);

	void deleteSystemRoleMenuRelation(SystemRoleMenuRelationBean bean);

	Map<String, List<SystemMenuBean>> queryMenuByCustomer(int customer_id);
	
	/**
	 * 查询系统的菜单信息用于后台管理
	 * @param bean
	 * @return
	 */
	SystemMenuBean querySystemMenuForMgr(SystemMenuBean bean);
	
	/**
	 * 处理提交菜单信息
	 * @param jsonData
	 * @return
	 */
	SystemMenuBean commitSystemMenuInfo(String jsonData);
	
	/**
	 * 删除菜单信息
	 * @param jsonData
	 * @return
	 */
	void deleteSystemMenuInfo(String id);
	
	/**
	 * 查询系统权限组
	 * @param bean
	 * @return
	 */
	List<SystemRoleBean> querySystemRoleBeanByBean(SystemRoleBean bean);
	
	/**
	 * 菜单权限的关联修改
	 * @param menu_ids  菜单id集合
	 * @param role_ids  权限id集合
	 * @param action    
	 * @param rela_id   关联id
	 * @return
	 */
	List<SystemRoleMenuRelationBean> commitSystemMenuAndRoleRelation(Object menu_ids, Object role_ids,Object rela_ids, String action, String status);
	/**
	 * 用户权限的关联修改
	 * @param admin_ids  用户id集合
	 * @param role_ids  权限id集合
	 * @param action    
	 * @param rela_id   关联id
	 * @return
	 */
	List<SystemRoleAdminRelationBean> commitSystemAdminAndRoleRelation(Object admin_ids, Object role_ids,Object rela_ids, String action, String status);

	
	/**
	 * 处理提交权限组信息
	 * @param jsonData
	 * @return
	 */
	SystemRoleBean commitSystemRoleInfo(String jsonData);
	
	/**
	 * 修改权限状态
	 * @param jsonData
	 * @return
	 */
	List<SystemRoleBean> changeSystemRoleStatus(String ids,String status);
	
	/**
	 * 查询子菜单节点（没有子菜单的节点）
	 * @param 
	 * @return
	 */
	List<SystemMenuBean> queryLastChildMenu(SystemMenuBean bean);
	
	/**
	 * 统一处理权限与菜单，ke
	 * @param type
	 * @param itemIds
	 * @param roleIds
	 * @return
	 */
	<T> List<T> commitRoleRelation(String type,String itemIds,String roleIds,Object rela_ids,String action,String status);

	/**
	 * 没有关联的菜单集合
	 * @param rela_ids
	 * @return
	 */
	List<SystemMenuBean> getMenuListNotExistRela(Object roleids);
	
	/**
	 * 权限的删除,包含删除权限所包含的所有关联关系，目前是菜单  客户的关联，统一执行删除操作
	 * @param list
	 */
	void deleteRoleByIds(List<String> list);
	
	/**
	 * 得到一级菜单
	 * @param bean
	 * @return
	 */
	List<SystemMenuBean> findLevel1MenuList(SystemMenuBean bean);
}
