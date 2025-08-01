package com.yd.business.operator.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.system.bean.SystemMenuExtModel;
import com.yd.business.system.service.ISystemManagerService;
import com.yd.iotbusiness.mapper.model.LlSystemMenuModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;
@Controller
public class OperatorController extends BaseController {

	@Autowired
	private IOperatorService operatorService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private IAddressService addressService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private ISystemManagerService systemManagerService;
	
	public static final String PAGE_ORDERPRODUCTLOG = "/page/user/orderProductLog.jsp";

	/**
	 *  界面查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/operator/ajaxQueryOperatorList.do")
	public ModelAndView ajaxQueryOperatorList(HttpServletRequest request,HttpServletResponse response){
		
		try {

			String id = request.getParameter("company_id");
			String o_status = request.getParameter("o_status");
			String o_name = request.getParameter("o_name");
			Long company_id = id == null ? null : Long.parseLong(id) ;
			OperatorBean condition = new OperatorBean();
			
			OperatorBean operator = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			if(operator.getO_kind() != OperatorBean.KIND_SUPPERUSER) {
				company_id = operator.getO_companyid();
			}
			
			if(operator.getO_kind() == OperatorBean.KIND_USER) {
				condition.setO_id(operator.getO_id());
			}
			
			condition.setO_companyid(company_id);
			condition.setO_name(o_name);
			condition.setO_status(StringUtil.isNull(o_status)?null:Integer.parseInt(o_status));
			
			List<OperatorExtBean> list = operatorService.queryOperatorList(condition);
		
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 *  界面查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/operator/ajaxAddOrUpdateOperator.do")
	public ModelAndView ajaxAddOrUpdateOperator(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean bean = new OperatorBean();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			
			int result = operatorService.addOrUpdateOperator(bean);
			
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}


	/**
	 *  界面查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/operator/ajaxQueryCurrentOperator.do")
	public ModelAndView ajaxQueryCurrentOperator(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			OperatorBean operator = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			operator.setO_password("");
			operator.setO_password2("");

			if(operator.getO_kind() == OperatorBean.KIND_SUPPERUSER) {
				operator.setO_rank99("超级管理员");
			}
			if(operator.getO_kind() == OperatorBean.KIND_MANAGER) {
				operator.setO_rank99("公司主管");
			}
			if(operator.getO_kind() == OperatorBean.KIND_USER) {
				operator.setO_rank99("营业人员");
			}
		
			writeJson(response, operator );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	/**
	 *  界面查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/operator/findCurrentOperatorBySessionId.do")
	public ModelAndView findCurrentOperatorBySessionId(HttpServletRequest request,HttpServletResponse response){

		IOTWebDataBean result = new IOTWebDataBean();
		try {
			LmOperatorModel operator = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String sessionid = request.getParameter("token");
			
			//通过登录帐号找菜单
			List<SystemMenuExtModel> menus = systemManagerService.generateSystemMenuByOperator(operator.getId());
			if(menus == null || menus.size() == 0) {
				result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
				result.setMessage("该帐号下未找到菜单，请检查权限配置！");
			}else {
				SystemMenuExtModel last = menus.get(menus.size()-1);
				
				OperatorExtBean bean = new OperatorExtBean();
				bean.setName(operator.getRealname());
				List<String> roles = new ArrayList<String>(last.getRoles());
//				roles.add("test");
				bean.setRoles(roles);
				bean.setIntroduction(operator.getRealname());
				bean.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
				bean.setMenus(menus);
				result.setInfo(operator);
				result.setData(bean);
				result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
			}
		
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(-1);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}
	
	/**
	 *  界面查询员工列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/operator/ajaxUdateOperatorRole.do")
	public ModelAndView ajaxUdateOperatorRole(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
//			OperatorBean operator = getCurrentLoginOperator();
//			int result = 0 ; 
//			String opid = request.getParameter("opid");
//			String[] roleids = request.getParameterValues("roleids[]");
//			if(StringUtil.isNotNull(opid)) {
//				result = operatorService.updateOperatorRole(Long.parseLong(opid), roleids , operator);
//				
//			}
//		
//			writeString(response, String.valueOf(result) );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 *  根据openid查询系统操作员信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/operator/findOperatorByOpenid.do")
	public ModelAndView findOperatorByOpenid(HttpServletRequest request,HttpServletResponse response){
		LmOperatorModel op = null;
		try {
			String openid = request.getParameter("openid");
			op = operatorService.findOperatorByOpenid(openid);
			if(op != null) {
				op.setPass(null);
				op.setLoginname(null);
			}else {
				System.out.println("========= not find Operator By openid: "+ openid);
				log.warn("========= not find Operator By openid: "+ openid);
			}
			
		} catch (Exception e) {
			log.error(e, e);
		}
		writeJson(response, op );
		return null;
	}
	
}
