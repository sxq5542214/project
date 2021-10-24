package com.yd.business.operator.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.bean.OperatorExtBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
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
	
	
	
}
