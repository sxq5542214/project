/**
 * 
 */
package com.yd.business.partner.controller;

import java.io.IOException;
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
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.partner.service.IPartnerService;
import com.yd.util.NumberUtil;

/**
 * @author ice
 *
 */
@Controller
public class PartnerController extends BaseController {
	@Resource
	private IPartnerService partnerService;
	@Resource
	private ICustomerService customerService;
	
	/**
	 * api审核列表[资质审核]
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/auditlist.do")
	public ModelAndView toApiAudit(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			CustomerBean bean = new CustomerBean();
			bean.setAuditstatus(CustomerBean.AUDITSTATUS_WSH);
			List<CustomerBean> list = customerService.listCustomer(bean);
			map.put("list", list);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apiaudit.jsp",map);
	}
	/**
	 * 查看资质信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/api/admin/apiinfo.do")
	public ModelAndView infoApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Integer id = NumberUtil.toInt(request.getParameter("id"));
			CustomerBean bean = customerService.findCustomerById(id);
			map.put("user", bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return new ModelAndView("/page/pc/iframe_apiinfo.jsp",map);
	}
	/**
	 * api资质审核
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/api/admin/apiaudit.do")
	public ModelAndView auditApply(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerBean bean = new CustomerBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			bean.setAuditstatus(NumberUtil.toInt(request.getParameter("status")));
			customerService.updateCustomer(bean);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	
	@RequestMapping("/admin/partner/modifyPartnerStatus.do")
	public ModelAndView modifyPartnerStatus(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String customer_id = request.getParameter("customer_id");
			String status = request.getParameter("status");
			
			partnerService.updatePartnerStatusByCustomerId(Integer.parseInt(customer_id), Integer.parseInt(status));
			
			writeJson(response, "success");
			
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "failed");
		}
		return null;
	}
	
}
