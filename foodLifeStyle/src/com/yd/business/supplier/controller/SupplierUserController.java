package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.order.bean.ShopOrderEffInfoBean;
import com.yd.business.order.bean.ShopOrderEffProductBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.service.IReportService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.CookieUtil;
import com.yd.util.DateUtil;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author ice
 *
 */
@Controller
public class SupplierUserController extends BaseController {
	public static final String PAGE_SUPPLIERSHOPMANAGERUSER_USERLIST = "/page/supplier/shop/manager/user/userList.jsp";


	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ISupplierProductService supplierProductService;
	@Autowired
	private IProductTypeService productTypeService;
	@Autowired
	private ISupplierPowerLogService supplierPowerLogService;
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private IReportService reportService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IShopOrderService shopOrderService;
	@Resource
	private ISupplierUserService supplierUserService;

	/**
	 * 查询近期订单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/user/toSupplierUserListPage.html")
	public ModelAndView toSupplierUserListPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
//			String openid = getCurrentOpenid();
			String sid = request.getParameter("sid");
			String level = request.getParameter("level");
			String phone = request.getParameter("phone");
			
			
			SupplierUserBean bean = new SupplierUserBean();
			bean.setSupplier_id(Integer.parseInt(sid));
			if(StringUtil.isNotNull(level)) {
				bean.setLevel(Integer.parseInt(level));
			}
			if(StringUtil.isNotNull(phone)) {
				bean.setPhone(phone);
			}
			
			bean.setOrderby(" order by id desc ");
			List<SupplierUserBean> userList = supplierUserService.querySupplierUser(bean );
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("userList", userList);
			
			return new ModelAndView(PAGE_SUPPLIERSHOPMANAGERUSER_USERLIST, map);
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}

	
}
