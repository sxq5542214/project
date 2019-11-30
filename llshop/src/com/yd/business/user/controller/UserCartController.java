/**
 * 
 */
package com.yd.business.user.controller;

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
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.user.bean.UserCartBean;
import com.yd.business.user.service.IUserCartService;
import com.yd.util.CookieUtil;

/**
 * @author ice
 *
 */
@Controller
public class UserCartController extends BaseController {
	
	public static final String COOKIE_KEY_PRODUCTINFO = "productInfo";
	
	public static final String PAGE_MYCART = "/page/shop/product/myCart.jsp";
	
	@Resource
	private IUserCartService userCartService;
	
	
	@RequestMapping("/user/cart/toMycartPage.do")
	public ModelAndView toMycartPage(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String openid = request.getParameter("openid");
			Map<String, Object> model = new HashMap<String, Object>();
			
			String productJson = CookieUtil.getValueByCookie(request, COOKIE_KEY_PRODUCTINFO);
			
			UserCartBean userCart = userCartService.queryUserCartListByCookieJson(productJson);
			
			userCart.setOpenid(openid);
			model.put("userCart", userCart);
			
			
			return new ModelAndView(PAGE_MYCART , model);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	
	
}
