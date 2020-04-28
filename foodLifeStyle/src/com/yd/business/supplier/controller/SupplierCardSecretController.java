/**
 * 
 */
package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.service.ISupplierCardSecretService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class SupplierCardSecretController extends BaseController {
	
	@Resource
	private ISupplierCardSecretService supplierCardSecretService;
	@Autowired
	private ICustomerService customerService;
	@Resource
	private IDictionaryService dictionaryService;
	
	
	@RequestMapping("**/admin/supplier/cardSecret_query.do")
	public ModelAndView toCardSecretQueryPage(HttpServletRequest request,HttpServletResponse response){
		
		try{
			CustomerBean customer = customerService.getUser();
			Integer nowpage = getIntValueByRequest("nowpage", request);
			if(nowpage == 0){
				nowpage = 1;
			}
			SupplierCardSecretBean condition = new SupplierCardSecretBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), condition);
			condition.setCustomer_id(customer.getId());
			condition.setNowpage(nowpage );
			
			PageinationData pd = supplierCardSecretService.queryCardSecret(condition);
			
			List<DictionaryBean> dictList = dictionaryService.getValueByTablenameAndAttribute(DictionaryBean.TABLENAME_SUPPLIERCARDSECRETBEAN, DictionaryBean.ATTRIBUTE_STATUS);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("pd", pd);
			model.put("condition", condition);
			model.put("dictList", dictList);
			return new ModelAndView("/page/pc/iframe_card_secret_query.jsp",model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/supplier/createCardSecret.do")
	public ModelAndView createCardSecret(HttpServletRequest request,HttpServletResponse response){
		try{
			SupplierCardSecretBean bean = new SupplierCardSecretBean();
			Map<String,String> map = getRequestParamsMap(request);
			AutoInvokeGetSetMethod.autoInvoke(map, bean);
			System.out.println(map.get("product_name"));
			supplierCardSecretService.createCardSecret(bean);

			writeJson(response, "<script>alert('执行成功！');</script>");
			
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "<script>alert('执行失败！"+ e.getMessage() +"');</script>");
		}
		return null;
	}
	
	@RequestMapping("**/admin/supplier/toCreateCardSecretPage.do")
	public ModelAndView toCreateCardSecretPage(HttpServletRequest request,HttpServletResponse response){
		try{
			Map<String, Object> model = new HashMap<String, Object>();
			return new ModelAndView("/page/pc/iframe_create_card_secret.jsp", model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/supplier/findCardSecret.do")
	public ModelAndView findCardSecret(HttpServletRequest request,HttpServletResponse response){
		try{
			
			String card_secret_key = request.getParameter("card_secret_key");
			
			SupplierCardSecretBean scs = supplierCardSecretService.findSupplierCardSecret(card_secret_key);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("scs", scs);
			return new ModelAndView("/page/supplier/cardSecret/display_card_secret.jsp", model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	

	@RequestMapping("**/supplier/useCardSecret.do")
	public ModelAndView useCardSecret(HttpServletRequest request,HttpServletResponse response){
		try{

			String secret_key = request.getParameter("secret_key");
			String phone = request.getParameter("phone");
			String isNowStr = request.getParameter("isNow");
			boolean isNow = true;
			if(StringUtil.isNotNull(isNowStr)){ 
				isNow = Boolean.parseBoolean(isNowStr);
			}
			OrderProductLogBean orderLog = supplierCardSecretService.useCardSecret(secret_key,phone,isNow);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("orderLog", orderLog);
			return new ModelAndView("/page/supplier/cardSecret/use_card_secret_result.jsp", model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
