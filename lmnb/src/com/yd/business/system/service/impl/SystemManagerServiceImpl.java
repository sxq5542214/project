/**
 * 
 */
package com.yd.business.system.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.system.bean.SystemBean;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.bean.SystemMenuExtModel;
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.bean.SystemRoleMenuRelationBean;
import com.yd.business.system.dao.ISystemManagerDao;
import com.yd.business.system.dao.ISystemMenuExtendsMapper;
import com.yd.business.system.service.ISystemManagerService;
import com.yd.iotbusiness.mapper.dao.LlSystemMenuModelMapper;
import com.yd.iotbusiness.mapper.model.LlSystemMenuModel;
import com.yd.iotbusiness.mapper.model.LlSystemMenuModelExample;
import com.yd.iotbusiness.mapper.model.LlSystemMenuModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("systemManagerService")
public class SystemManagerServiceImpl extends BaseService implements ISystemManagerService {
	@Resource
	private ISystemManagerDao systemManagerDao;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private ISystemMenuExtendsMapper systemMenuExtendsMapper;
	
	
	@Override
	public List<SystemRoleMenuRelationBean> querySystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		return systemManagerDao.querySystemRoleMenuRelation(bean);
	}

	@Override
	public SystemRoleBean findSystemRoleById(int id){
		return systemManagerDao.findSystemRoleById(id);
	}

	@Override
	public SystemMenuBean findSystemMenuById(int id){
		return systemManagerDao.findSystemMenuById(id);
	}

	@Override
	public List<SystemRoleAdminRelationBean> querySystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		return systemManagerDao.querySystemRoleAdminRelation(bean);
	}

	@Override
	public void createSystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		systemManagerDao.createSystemRoleAdminRelation(bean);
	}

	@Override
	public void deleteSystemRoleAdminRelation(SystemRoleAdminRelationBean bean){
		systemManagerDao.deleteSystemRoleAdminRelation(bean);
	}

	@Override
	public void createSystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		systemManagerDao.createSystemRoleMenuRelation(bean);
	}

	@Override
	public void deleteSystemRoleMenuRelation(SystemRoleMenuRelationBean bean){
		systemManagerDao.deleteSystemRoleMenuRelation(bean);
	}
	

//	@Override
//	public SystemMenuBean querySystemMenuForMgr(SystemMenuBean bean) {
//		//用于存储菜单信息的Map（非一级菜单）
//		Map<Integer,List<SystemMenuBean>> beanMap = new HashMap<Integer,List<SystemMenuBean>>();
//		//用于存储菜单信息的Map（所有）
//		Map<Integer,SystemMenuBean> allBeanMap = new HashMap<Integer,SystemMenuBean>();
//		//获得所有菜单信息
//		List<SystemMenuBean> beanList = systemManagerDao.querySystemMenuByBean(bean);
//		List<SystemRoleMenuRelationBean> roleList = systemManagerDao.querySystemRoleMenuRelation(null);
//		//获取第一级菜单，parentid为空的默认第一级
//		List<SystemMenuBean> firstLevelMenuList = new ArrayList<SystemMenuBean>();
//		for (SystemMenuBean systemMenuBean : beanList) {
//			allBeanMap.put(systemMenuBean.getId(), systemMenuBean);
//			if(StringUtil.isNull(systemMenuBean.getParentid())){
//				firstLevelMenuList.add(systemMenuBean);
//			}else{
//				if(StringUtil.isNull(beanMap.get(systemMenuBean.getParentid()))){
//					List<SystemMenuBean> mapList = new ArrayList<SystemMenuBean>();
//					mapList.add(systemMenuBean);
//					beanMap.put(systemMenuBean.getParentid(), mapList);
//				}else{
//					beanMap.get(systemMenuBean.getParentid()).add(systemMenuBean);
//				}
//			}
//		}
//		//遍历一级菜单列表，将子菜单插入，同时将菜单的权限带出
//		for (SystemMenuBean systemMenuBean : beanList) {
//			List<SystemMenuBean> childrenMenu = beanMap.get(systemMenuBean.getId());
//			if(StringUtil.isNull(systemMenuBean.getChildren_menu())){
//				systemMenuBean.setChildren_menu(childrenMenu);
//			}else{
//				systemMenuBean.getChildren_menu().addAll(childrenMenu);
//			}
//		}
//		//遍历权限集合将菜单的权限关联
//		for (SystemRoleMenuRelationBean systemRoleMenuRelationBean : roleList) {
//			SystemMenuBean menu = allBeanMap.get(systemRoleMenuRelationBean.getMenu_id());
//			if(!StringUtil.isNull(menu)){
//				if(StringUtil.isNull(menu.getMenu_roles())){
//					List<SystemRoleMenuRelationBean> menu_role = new ArrayList<SystemRoleMenuRelationBean>();
//					menu_role.add(systemRoleMenuRelationBean);
//					menu.setMenu_roles(menu_role);
//				}else{
//					menu.getMenu_roles().add(systemRoleMenuRelationBean);
//				}
//			}
//		}
//		bean.setName("菜单");
//		bean.setId(0);
//		bean.setChildren_menu(firstLevelMenuList);
//		return bean;
//	}

	@Override
	public SystemMenuBean createSystemMenuInfo(String jsonDataStr) {
		SystemMenuBean systemMenuBean = new SystemMenuBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(jsonDataStr);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,systemMenuBean);
			systemMenuBean = systemManagerDao.createOrUpdateSystemMenu(systemMenuBean);
		} catch (Exception e) {
			log.error(e, e);
		}
		return systemMenuBean;
	}

	@Override
	public void deleteSystemMenuInfo(String id) {
		SystemMenuBean bean = new SystemMenuBean();
		if(!StringUtil.isNull(id)){
			bean.setId(Integer.valueOf(id));
			//首先查询出菜单信息
			systemManagerDao.deleteSystemMenu(bean);
		}
	}

	@Override
	public List<SystemRoleBean> querySystemRoleBeanByBean(SystemRoleBean bean) {
		return systemManagerDao.querySystemRoleBeanByBean(bean);
	}

	@Override
	public List<SystemRoleMenuRelationBean> createSystemMenuAndRoleRelation(Object menu_ids, Object role_ids,Object rela_ids, String action, String status) {
		List<SystemRoleMenuRelationBean> relaList = new ArrayList<SystemRoleMenuRelationBean>();
		if(!StringUtil.isNull(action)){
			//新增关联关系
			if(SystemRoleMenuRelationBean.ACTION_TYPE_ADD.equals(action)){
				//判断菜单id和权限id是否存在
				if(!StringUtil.isNull(menu_ids) && !StringUtil.isNull(role_ids)){
					createOrUpdateSystemRoleMenuRelation(menu_ids,role_ids,relaList);
				}
			}else if(SystemRoleMenuRelationBean.ACTION_TYPE_MODIFY.equals(action)){
				List<SystemRoleMenuRelationBean> beans = modifySystemRoleMenuRelationBean( rela_ids, action, status, relaList);
				//修改关联关系
				if(!StringUtil.isNull(beans) && beans.size() > 0){
					for (SystemRoleMenuRelationBean systemRoleMenuRelationBean : beans) {
						systemManagerDao.updateSystemRoleMenuRelation(systemRoleMenuRelationBean);
					}
				}
			}else if(SystemRoleMenuRelationBean.ACTION_TYPE_DELETE.equals(action)){
				List<SystemRoleMenuRelationBean> beans = deleteSystemRoleMenuRelationBean( rela_ids, action, status, relaList);
				//删除关联关系
				if(!StringUtil.isNull(beans) && beans.size() > 0){
					for (SystemRoleMenuRelationBean systemRoleMenuRelationBean : beans) {
						systemManagerDao.deleteSystemRoleMenuRelation(systemRoleMenuRelationBean);
					}
				}
			}
		}
		return relaList;
	}
	
	@Override
	public List<SystemRoleAdminRelationBean> createSystemAdminAndRoleRelation(Object menu_ids, Object role_ids,Object rela_ids, String action, String status) {
		List<SystemRoleAdminRelationBean> relaList = new ArrayList<SystemRoleAdminRelationBean>();
		if(!StringUtil.isNull(action)){
			//新增关联关系
			if(SystemRoleAdminRelationBean.ACTION_TYPE_ADD.equals(action)){
				//判断菜单id和权限id是否存在
				if(!StringUtil.isNull(menu_ids) && !StringUtil.isNull(role_ids)){
//					createOrUpdateSystemRoleAdminRelation(menu_ids,role_ids,relaList);
				}
			}else if(SystemRoleAdminRelationBean.ACTION_TYPE_MODIFY.equals(action)){
				List<SystemRoleAdminRelationBean> beans = modifySystemRoleAdminRelationBean( rela_ids, action, status, relaList);
				//修改关联关系
				if(!StringUtil.isNull(beans) && beans.size() > 0){
					for (SystemRoleAdminRelationBean systemRoleAdminRelationBean : beans) {
						systemManagerDao.updateSystemRoleAdminRelation(systemRoleAdminRelationBean);
					}
				}
			}else if(SystemRoleAdminRelationBean.ACTION_TYPE_DELETE.equals(action)){
				List<SystemRoleAdminRelationBean> beans = deleteSystemRoleAdminRelationBean( rela_ids, action, status, relaList);
				//删除关联关系
				if(!StringUtil.isNull(beans) && beans.size() > 0){
					for (SystemRoleAdminRelationBean systemRoleAdminRelationBean : beans) {
						systemManagerDao.deleteSystemRoleAdminRelation(systemRoleAdminRelationBean);
					}
				}
			}
		}
		return relaList;
	}
	
	/**
	 * 实时修改关系状态
	 * @param rela_ids
	 * @param action
	 * @param status
	 * @return
	 */
	private List<SystemRoleMenuRelationBean> modifySystemRoleMenuRelationBean(Object rela_ids, String action, String status,List<SystemRoleMenuRelationBean> relaList) {
		List<String> relaIds = Arrays.asList(rela_ids.toString().split(","));
		for (String string : relaIds) {
			if (!StringUtil.isNull(string)) {
				Integer id = Integer.valueOf(string);
				SystemRoleMenuRelationBean bean = new SystemRoleMenuRelationBean();
				bean.setId(id);
				bean = systemManagerDao.findSystemRoleMenuRelationById(bean);
				if (!StringUtil.isNull(bean)) {
					if (SystemRoleMenuRelationBean.ACTION_STATUS_VALUE_LOSEEFF.equals(status.toString())) {
						bean.setStatus(SystemRoleMenuRelationBean.RELATION_STATUS_LOSSEFF);
						bean.setStatus_value(bean.getDictValueByField("status"));
						relaList.add(bean);
					} else if (SystemRoleMenuRelationBean.ACTION_STATUS_VALUE_EFF.equals(status.toString())) {
						bean.setStatus(SystemRoleMenuRelationBean.RELATION_STATUS_EFF);
						bean.setStatus_value(bean.getDictValueByField("status"));
						relaList.add(bean);
					}
				}
			}
		}
		return relaList;
	}
	
	/**
	 * 实时修改关系状态(用户权限)
	 * @param rela_ids
	 * @param action
	 * @param status
	 * @return
	 */
	private List<SystemRoleAdminRelationBean> modifySystemRoleAdminRelationBean(Object rela_ids, String action, String status,List<SystemRoleAdminRelationBean> relaList) {
		List<String> relaIds = Arrays.asList(rela_ids.toString().split(","));
		for (String string : relaIds) {
			if (!StringUtil.isNull(string)) {
				Integer id = Integer.valueOf(string);
				SystemRoleAdminRelationBean bean = new SystemRoleAdminRelationBean();
				bean.setId(id);
				bean = systemManagerDao.findSystemRoleAdminRelationById(bean);
				if (!StringUtil.isNull(bean)) {
					if (SystemRoleAdminRelationBean.ACTION_STATUS_VALUE_LOSEEFF.equals(status.toString())) {
						bean.setStatus(SystemRoleAdminRelationBean.RELATION_STATUS_LOSSEFF);
						bean.setStatus_value(bean.getDictValueByField("status"));
						relaList.add(bean);
					} else if (SystemRoleAdminRelationBean.ACTION_STATUS_VALUE_EFF.equals(status.toString())) {
						bean.setStatus(SystemRoleAdminRelationBean.RELATION_STATUS_EFF);
						bean.setStatus_value(bean.getDictValueByField("status"));
						relaList.add(bean);
					}
				}
			}
		}
		return relaList;
	}
	
	/**
	 * 实时修改关系状态(菜单权限)
	 * @param rela_ids
	 * @param action
	 * @param status
	 * @return
	 */
	private List<SystemRoleMenuRelationBean> deleteSystemRoleMenuRelationBean(Object rela_ids,String action,String status,List<SystemRoleMenuRelationBean> relaList){
		List<String> relaIds = Arrays.asList(rela_ids.toString().split(","));
		for (String string : relaIds) {
			if (!StringUtil.isNull(string)) {
				Integer id = Integer.valueOf(string);
				SystemRoleMenuRelationBean bean = new SystemRoleMenuRelationBean();
				bean.setId(id);
				relaList.add(bean);
			}
		}
		return relaList;
	}
	/**
	 * 实时修改关系状态(用户权限)
	 * @param rela_ids
	 * @param action
	 * @param status
	 * @return
	 */
	private List<SystemRoleAdminRelationBean> deleteSystemRoleAdminRelationBean(Object rela_ids,String action,String status,List<SystemRoleAdminRelationBean> relaList){
		List<String> relaIds = Arrays.asList(rela_ids.toString().split(","));
		for (String string : relaIds) {
			if (!StringUtil.isNull(string)) {
				Integer id = Integer.valueOf(string);
				SystemRoleAdminRelationBean bean = new SystemRoleAdminRelationBean();
				bean.setId(id);
				relaList.add(bean);
			}
		}
		return relaList;
	}
	
	/**
	 * 新增或者更新关系数据
	 * @param menu_ids
	 * @param role_ids
	 * @param relaList
	 * @return
	 */
	private List<SystemRoleMenuRelationBean> createOrUpdateSystemRoleMenuRelation(Object menu_ids, Object role_ids,List<SystemRoleMenuRelationBean> relaList){
		//菜单id集合
		List<String> menuList = Arrays.asList(menu_ids.toString().split(","));
		List<Integer> menuIds = new ArrayList<Integer>();
		List<SystemMenuBean> menuBeanList = new ArrayList<SystemMenuBean>();
		//权限id集合
		List<String> roleList = Arrays.asList(role_ids.toString().split(","));
		List<Integer> roleIds = new ArrayList<Integer>();
		List<SystemRoleBean> roleBeanList = new ArrayList<SystemRoleBean>();
		for (String string : roleList) {
			if(!StringUtil.isNull(string)){
				Integer id = Integer.valueOf(string);
				roleIds.add(id);
			}
		}
		roleBeanList = systemManagerDao.findSystemRoleByIds(roleIds);
		for (String string : menuList) {
			if(!StringUtil.isNull(string)){
				Integer id = Integer.valueOf(string);
				menuIds.add(id);
			}
		}
		menuBeanList = systemManagerDao.findSystemMenuByIds(menuIds);
		for (SystemMenuBean menuBean : menuBeanList) {
			for (SystemRoleBean systemRoleBean : roleBeanList) {
				//新增权限关系
				SystemRoleMenuRelationBean relaBean = new SystemRoleMenuRelationBean();
				relaBean.setMenu_id(menuBean.getId());
				relaBean.setRole_id(systemRoleBean.getId());
				relaBean.setCreate_time(DateUtil.getNowDateStrSSS());
				List<SystemRoleMenuRelationBean> oldBean = systemManagerDao.querySystemRoleMenuRelation(relaBean);
				if(oldBean.size() == 0){
					relaBean.setMenu_name(menuBean.getName());
					relaBean.setRole_name(systemRoleBean.getName());
					relaBean.setStatus(SystemRoleMenuRelationBean.STATUS_ENABLE);
					relaBean.setStatus_value(relaBean.getDictValueByField("status"));
					systemManagerDao.createSystemRoleMenuRelation(relaBean);
					relaList.add(relaBean);
				}
			}
		}
		return relaList;
	}
	
//	/**
//	 * 新增或者更新关系数据
//	 * @param menu_ids
//	 * @param role_ids
//	 * @param relaList
//	 * @return
//	 */
//	private List<SystemRoleAdminRelationBean> createOrUpdateSystemRoleAdminRelation(Object admin_ids, Object role_ids,List<SystemRoleAdminRelationBean> relaList){
//		//菜单id集合
//		List<String> adminList = Arrays.asList(admin_ids.toString().split(","));
//		List<Integer> adminIds = new ArrayList<Integer>();
//		List<CustomerBean> customerBeanList = new ArrayList<CustomerBean>();
//		//权限id集合
//		List<String> roleList = Arrays.asList(role_ids.toString().split(","));
//		List<Integer> roleIds = new ArrayList<Integer>();
//		List<SystemRoleBean> roleBeanList = new ArrayList<SystemRoleBean>();
//		for (String string : roleList) {
//			if(!StringUtil.isNull(string)){
//				Integer id = Integer.valueOf(string);
//				roleIds.add(id);
//			}
//		}
//		roleBeanList = systemManagerDao.findSystemRoleByIds(roleIds);
//		for (String string : adminList) {
//			if(!StringUtil.isNull(string)){
//				Integer id = Integer.valueOf(string);
//				CustomerBean cus = customerService.findCustomerById(id);
//				customerBeanList.add(cus);
////				adminIds.add(id);
//			}
//		}
//		for (CustomerBean cus : customerBeanList) {
//			for (SystemRoleBean systemRoleBean : roleBeanList) {
//				//新增权限关系
//				SystemRoleAdminRelationBean relaBean = new SystemRoleAdminRelationBean();
//				relaBean.setCustomer_id(cus.getId());
//				relaBean.setRole_id(systemRoleBean.getId());
//				relaBean.setCreate_time(DateUtil.getNowDateStrSSS());
//				List<SystemRoleAdminRelationBean> oldBean = systemManagerDao.querySystemRoleAdminRelation(relaBean);
//				if(oldBean.size() == 0){
//					relaBean.setCustomer_name(cus.getName());
//					relaBean.setRole_name(systemRoleBean.getName());
//					relaBean.setStatus(SystemRoleMenuRelationBean.STATUS_ENABLE);
//					relaBean.setStatus_value(relaBean.getDictValueByField("status"));
//					systemManagerDao.createSystemRoleAdminRelation(relaBean);
//					relaList.add(relaBean);
//				}
//			}
//		}
//		return relaList;
//	}
	
	@Override
	public SystemRoleBean createSystemRoleInfo(String jsonDataStr) {
		SystemRoleBean systemRoleBean = new SystemRoleBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(jsonDataStr);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,systemRoleBean);
			systemRoleBean = systemManagerDao.createOrUpdateSystemRole(systemRoleBean);
			systemRoleBean.setStatus_value(systemRoleBean.getDictValueByField("status"));
		} catch (Exception e) {
			log.error(e, e);
		}
		return systemRoleBean;
	}

	@Override
	public List<SystemRoleBean> changeSystemRoleStatus(String ids, String status) {
		List<SystemRoleBean> roleList = new ArrayList<SystemRoleBean>();
		List<String> isList = Arrays.asList(ids.split(","));
		for (String string : isList) {
			if(StringUtil.isNotNull(string)){
				//查询权限
				SystemRoleBean roleBean = systemManagerDao.findSystemRoleById(Integer.valueOf(string));
				if(!StringUtil.isNull(roleBean)){
					roleBean.setStatus(Integer.valueOf(status));
					roleBean.setStatus_value(roleBean.getDictValueByField("status"));
					systemManagerDao.createOrUpdateSystemRole(roleBean);
					roleList.add(roleBean);
				}
			}
		}
		return roleList;
	}

	@Override
	public List<SystemMenuBean> queryLastChildMenu(SystemMenuBean bean) {
		List<SystemMenuBean> beanList = systemManagerDao.queryLastChildMenu(bean);
		for (SystemMenuBean systemMenuBean : beanList) {
			systemMenuBean.setStatus_value(systemMenuBean.getDictValueByField("status"));
		}
		return beanList;
	}

	@Override
	public <T> List<T> createRoleRelation(String type, String itemIds,String roleIds, Object rela_ids, String action, String status) {		
		if(SystemBean.BEAN_TYPE_MENU.equals(type)){
			return (List<T>) this.createSystemMenuAndRoleRelation(itemIds, roleIds, rela_ids, action, status);
		}else if(SystemBean.BEAN_TYPE_ADMIN.equals(type)){
			return (List<T>) this.createSystemAdminAndRoleRelation(itemIds, roleIds, rela_ids, action, status);
		}
		return null;
	}

	@Override
	public List<SystemMenuBean> getMenuListNotExistRela(Object role_id) {
		SystemRoleBean bean = new SystemRoleBean();
		bean.setId(Integer.valueOf(role_id.toString()));
		List<SystemMenuBean> menuList = systemManagerDao.queryLastChildMenuNotExist(bean);
		for (SystemMenuBean systemMenuBean : menuList) {
			systemMenuBean.setStatus_value(systemMenuBean.getDictValueByField("status"));
		}
		return menuList;
	}

	@Override
	public void deleteRoleByIds(List<String> ids) {
		for (String id : ids) {
			if(StringUtil.isNotNull(id)){
				//查询出权限列表
				SystemRoleBean role = this.findSystemRoleById(Integer.valueOf(id));
				//权限关联的用户
				SystemRoleAdminRelationBean adminRelaBean = new SystemRoleAdminRelationBean();
				adminRelaBean.setRole_id(role.getId());
				List<SystemRoleAdminRelationBean> adminRelaBeanList = this.querySystemRoleAdminRelation(adminRelaBean);
				for (SystemRoleAdminRelationBean systemRoleAdminRelationBean : adminRelaBeanList) {
					//删除关联关系
					systemManagerDao.deleteSystemRoleAdminRelation(systemRoleAdminRelationBean);
				}
				//权限关联的菜单
				SystemRoleMenuRelationBean menuRelaBean = new SystemRoleMenuRelationBean();
				menuRelaBean.setRole_id(role.getId());
				List<SystemRoleMenuRelationBean> menuRelaBeanList = this.querySystemRoleMenuRelation(menuRelaBean);
				for (SystemRoleMenuRelationBean systemRoleMenuRelationBean : menuRelaBeanList) {
					//删除关联关系
					systemManagerDao.deleteSystemRoleMenuRelation(systemRoleMenuRelationBean);
				}
				//最后删除权限
				systemManagerDao.deleteSystemRole(role);
			}
		}
	}
	
	@Override
	public List<SystemMenuBean> querySystemMenuByOperator(long operatorid){
		
		return systemManagerDao.querySystemMenuListByOperatorId(operatorid);
	}
	
	@Override
	public List<SystemRoleBean> querySystemRoleByOperator(long operatorid){
		
		return systemManagerDao.querySystemRoleListByOperatorId(operatorid);
	}

	@Override
	public List<SystemMenuExtModel> generateSystemMenuByOperator(int operatorid){
		
		SystemMenuExtModel model = new SystemMenuExtModel();
		model.setStatus(SystemMenuExtModel.STATUS_ENABLE);
		model.setOpid(operatorid);
		
		List<SystemMenuExtModel> resultList = new ArrayList<>();
		List<SystemMenuExtModel> list = systemMenuExtendsMapper.querySystemMenuByExtModel(model);
		Set<String> roleNames = new HashSet<>();
		
		for(SystemMenuExtModel parent  : list) {
			
			if(parent.getParentid() == null) {
				List<SystemMenuExtModel> childList = new ArrayList<>();
				parent.setChildren(childList);
				for(SystemMenuExtModel child  : list) {
					//找到子集
					if(child.getParentid() != null && child.getParentid().intValue() == parent.getId().intValue()) {
						
						
						Map<String,Object> meta = new HashMap<>();
						meta.put("title", child.getTitle());
						meta.put("icon", child.getIcon());
						meta.put("noCache", true);

						List<String> roles = new ArrayList<>();
						roles.add(child.getRole_name());
						meta.put("roles", roles);
						
						child.setMeta(meta);
						child.setTitle(null);
						child.setIcon(null);
						childList.add(child);
						
						roleNames.add(child.getRole_name());
					}
				}
				parent.setRoles(roleNames);
				resultList.add(parent);
			}
		}
		
		return resultList;
		
//		String str = "[\r\n"
//				+ "  {\r\n"
//				+ "    path: '/userManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/user/userManager'),\r\n"
//				+ "        name: 'userManager',\r\n"
//				+ "        meta: { title: '用户管理', icon: 'peoples', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  {\r\n"
//				+ "    path: '/addressManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/address/addressManager'),\r\n"
//				+ "        name: 'addressManager',\r\n"
//				+ "        meta: { title: '地址管理', icon: 'list', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  {\r\n"
//				+ "    path: '/chargeManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/charge/chargeManager'),\r\n"
//				+ "        name: 'chargeManager',\r\n"
//				+ "        meta: { title: '充值管理', icon: 'money', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  {\r\n"
//				+ "    path: '/recordManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/record/recordManager'),\r\n"
//				+ "        name: 'recordManager', \r\n"
//				+ "        meta: { title: '抄表记录', icon: 'el-icon-s-order', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  {\r\n"
//				+ "    path: '/printManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/print/printManager'),\r\n"
//				+ "        name: 'printManager',\r\n"
//				+ "        meta: { title: '打印模板', icon: 'el-icon-printer', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  {\r\n"
//				+ "    path: '/reportManager',\r\n"
//				+ "    component: Layout,\r\n"
//				+ "    children: [\r\n"
//				+ "      {\r\n"
//				+ "        path: 'index',\r\n"
//				+ "        component: () => import('@/views/business/report/reportManager'),\r\n"
//				+ "        name: 'reportManager',\r\n"
//				+ "        meta: { title: '报表查询', icon: 'chart', noCache: true }\r\n"
//				+ "      }\r\n"
//				+ "    ]\r\n"
//				+ "  },\r\n"
//				+ "  { path: '*', redirect: '/404', hidden: true }\r\n"
//				+ "]";
//System.out.println(str);		
//		
//		return str;
	}
}
