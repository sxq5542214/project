/**
 * 
 */
package com.yd.business.system.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.system.bean.SystemBean;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.bean.SystemRoleAdminRelationBean;
import com.yd.business.system.bean.SystemRoleBean;
import com.yd.business.system.bean.SystemRoleMenuRelationBean;
import com.yd.business.system.service.ISystemManagerService;
import com.yd.iotbusiness.mapper.model.LlDictionaryModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class SystemManagerController extends BaseController {
	@Resource
	private ISystemManagerService systemManagerService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IOperatorService operatorService;
	
	public static final String PAGE_SYSTEM_MENU_SHOW = "/page/pc/systemMenu/iframe_sysrem_menu_mgr.jsp";
	public static final String PAGE_SYSTEM_ROLE_SHOW = "/page/pc/systemRole/iframe_sysrem_role_mgr.jsp";
	
	/**
	 * 异步获取菜单数据，用于树形结构展示
	 * 
	 */
	@RequestMapping("**/admin/system/querySystemMenuToShow.do")
	public ModelAndView querySystemMenuToShow(HttpServletRequest request,HttpServletResponse response){
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			
			List<SystemMenuBean> list = systemManagerService.querySystemMenuByOperator(op.getId());
			
			writeJson(response, list);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	@RequestMapping("**/admin/system/queryRoleByOperator.do")
	public ModelAndView queryRoleByOperator(HttpServletRequest request,HttpServletResponse response){
		try {
			List<SystemRoleBean> list = null;
			String opidstr = request.getParameter("opid");
			OperatorBean target ;
			LmOperatorModel cur = getCurrentLoginOperator();
			if(StringUtil.isNotNull(opidstr)) {// 有目标用户 查目标用户的
				target = operatorService.findOperatorById(Long.parseLong(opidstr));
			
				
//				if(cur.getO_kind() == OperatorBean.KIND_SUPPERUSER) {
//					list = systemManagerService.querySystemRoleByOperator(target.getO_id());
//				}else {
//					// 判断同一公司才能查询
//					if(cur.getO_companyid().longValue() == target.getO_companyid().longValue()) {
//						list = systemManagerService.querySystemRoleByOperator(target.getO_id());
//					}
//				}
			
			}else { //  没有目标用户查当前登录用户的
				list = systemManagerService.querySystemRoleByOperator(cur.getId());
			}
			
			writeJson(response, list);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	
	/**
	 * 提交菜单数据(新增或者修改)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/createSystemMenu.do")
	public ModelAndView createSystemMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			SystemMenuBean bean = systemManagerService.createSystemMenuInfo(jsonData);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 删除菜单数据(包含级联删除)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/deelteSystemMenu.do")
	public ModelAndView deleteSystemMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			systemManagerService.deleteSystemMenuInfo(id);
			writeJson(response, "SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	/**
	 * 提交菜单与权限的关系，并作新增删除修改操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/createSystemMenuAndRole.do")
	public ModelAndView createSystemMenuAndRole(HttpServletRequest request,HttpServletResponse response){
		try {
			String action = request.getParameter("action");
			Object menu_id = request.getParameter("menu_id");
			Object role_ids = request.getParameter("role_ids");
			Object rela_id = request.getParameter("rela_id");
			String status = request.getParameter("status");
			List<SystemRoleMenuRelationBean> relaList = systemManagerService.createSystemMenuAndRoleRelation(menu_id,role_ids,rela_id,action,status);
			writeJson(response, relaList);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 权限组管理
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/getSystemRoleToShow.do")
	public ModelAndView getSystemRoleToShow(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			SystemRoleBean bean = new SystemRoleBean();
			//初始化字典值
			Map<String, List<LlDictionaryModel>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			SystemRoleBean roleBean = new SystemRoleBean();
			List<SystemRoleBean> roleList = systemManagerService.querySystemRoleBeanByBean(roleBean);
			List<SystemMenuBean> menuList = systemManagerService.queryLastChildMenu(null);
//			List<CustomerBean> customerList = customerService.listCustomer(null);
			map.put("dicMap", dicMap);
			map.put("roleList", roleList);
			map.put("menuList", menuList);
//			map.put("customerList", customerList);
			return new ModelAndView(PAGE_SYSTEM_ROLE_SHOW,map);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}
	
	/**
	 * 获取权限下的用户和菜单信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/getSystemRoleRelaForAjax.do")
	public ModelAndView getSystemRoleRelaForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			String role_id = request.getParameter("role_id");
			SystemRoleBean bean = new SystemRoleBean();
			SystemRoleAdminRelationBean roleAdminBean = new SystemRoleAdminRelationBean();
			roleAdminBean.setRole_id(Integer.valueOf(role_id));
			List<SystemRoleAdminRelationBean> roleAdminBeanList = systemManagerService.querySystemRoleAdminRelation(roleAdminBean);
			SystemRoleMenuRelationBean roleMenuBean = new SystemRoleMenuRelationBean();
			roleMenuBean.setRole_id(Integer.valueOf(role_id));
			List<SystemRoleMenuRelationBean> roleMenuBeanList = systemManagerService.querySystemRoleMenuRelation(roleMenuBean);
			bean.setRoleAdminList(roleAdminBeanList);
			bean.setRoleMenuList(roleMenuBeanList);
			writeJson(response,bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 提交新建和修改的权限组信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/createSystemRoleForAjax.do")
	public ModelAndView createSystemRoleForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			SystemRoleBean bean = systemManagerService.createSystemRoleInfo(jsonData);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 更改权限的状态
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/changeRoleRelationForAjax.do")
	public ModelAndView changeRoleStatusForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			String status = request.getParameter("status");
			String ids = request.getParameter("ids");
			if(!StringUtil.isNull(ids) && !StringUtil.isNull(status)){
				List<SystemRoleBean> bean = systemManagerService.changeSystemRoleStatus(ids, status);
				writeJson(response, bean);
			}else{
				writeJson(response, "失败");
			}
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 系统权限的关联管理（对于菜单和客户的权限关联）
	 */
	@RequestMapping("**/admin/system/createRoleRelation.do")
	public ModelAndView createRoleRelation(HttpServletRequest request,HttpServletResponse response){
		try {
			String itemIds = request.getParameter("itemIds");
			String roleId = request.getParameter("roleId");
			String type = request.getParameter("type");
			List<Object> list = systemManagerService.createRoleRelation(type, itemIds, roleId, null, SystemBean.ACTION_TYPE_ADD, null);
			writeJson(response, list);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 获得可选择的菜单集合
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/getItemListNotExist.do")
	public ModelAndView getItemListNotExist(HttpServletRequest request,HttpServletResponse response){
		try {
			String role_id = request.getParameter("role_id");
			List<SystemMenuBean> itemList = systemManagerService.getMenuListNotExistRela(role_id);
			writeJson(response,itemList);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 权限的删除
	 * @param request
	 * @param reponse
	 * @return
	 */
	@RequestMapping("**/admin/system/deleteRoleByIdForAjax.do")
	public ModelAndView deleteRoleByIdForAjax(HttpServletRequest request, HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			if(StringUtil.isNotNull(ids)){
				systemManagerService.deleteRoleByIds(Arrays.asList(ids.split(",")));
				writeJson(response,"SUCCESS");
			}else{
				writeJson(response,"FAIL");
			}
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"FAIL");
		}
		
		return null;
	}
	
	/**
	 * 提交客户与权限的关系，并作新增删除修改操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/createSystemAdminAndRole.do")
	public ModelAndView createSystemAdminAndRole(HttpServletRequest request,HttpServletResponse response){
		try {
			String action = request.getParameter("action");
			Object admin_id = request.getParameter("admin_id");
			Object role_ids = request.getParameter("role_ids");
			Object rela_id = request.getParameter("rela_id");
			String status = request.getParameter("status");
			List<SystemRoleAdminRelationBean> relaList = systemManagerService.createSystemAdminAndRoleRelation(admin_id,role_ids,rela_id,action,status);
			writeJson(response, relaList);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	

	/**
	 * 提交客户与权限的关系，并作新增删除修改操作
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/system/queryLastChildMenu.do")
	public ModelAndView queryLastChildMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			List<SystemMenuBean> menuList = systemManagerService.queryLastChildMenu(null);
			writeJson(response, menuList);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "queryLastChildMenu失败");
		}
		return null;
	}
}
