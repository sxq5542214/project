/**
 * 
 */
package com.yd.business.other.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.service.IAddressService;

/**
 * @author ice
 *
 */
@Controller
public class AddressController extends BaseController {
	@Resource
	private IAddressService addressService;
	
	@RequestMapping("other/address/queryAddress.do")
	public ModelAndView queryAddress(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String parent_id = request.getParameter("parent_id");
			
			AddressBean bean = new AddressBean();
			bean.setParent_id(Integer.parseInt(parent_id));
			List<AddressBean> list = addressService.queryAddress(bean );
			
			writeJson(response, list);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
		
	}
	
	
}
