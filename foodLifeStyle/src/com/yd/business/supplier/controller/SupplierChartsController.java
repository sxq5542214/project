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
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
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
public class SupplierChartsController extends BaseController {
	public static final String PAGE_SUPPLIERSHOPMANAGER_CATEGORY = "/page/supplier/shop/manager/category.jsp";
	public static final String PAGE_SUPPLIERSHOP_SHOPCATEGORY = "/page/supplier/shop/shopCategory.jsp";
	public static final String PAGE_SUPPLIER_SHOP_ORDER_EFF = "/page/supplier/shop/orderEffInfo.jsp";
	public static final String PAGE_SUPPLIERSHOPMANAGER_INDEX = "/page/supplier/shop/manager/index.jsp";


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

	/**
	 * 查询近期订单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/chart/ajaxShopEffOrderLatelyData.html")
	public ModelAndView ajaxShopEffOrderLatelyData(HttpServletRequest request,HttpServletResponse response) {
		
		try {
//			String openid = getCurrentOpenid();
			String sid = request.getParameter("sid");
			
			String reportCode = "supplier.chart.ajaxShopEffOrderLatelyData";
			Map<String,String> params = new HashMap<String, String>();
			params.put("sid", sid);
			ReportSimpleBean bean = reportService.querySimpleReportAndDataByCode(reportCode, params);
			
			writeJson(response, bean);

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}

	/**
	 * 查询商品信息总体数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/chart/ajaxShopProductCountData.html")
	public ModelAndView ajaxShopProductCountData(HttpServletRequest request,HttpServletResponse response) {
		
		try {
//			String openid = getCurrentOpenid();
			String sid = request.getParameter("sid");
			
			String reportCode = "supplier.chart.ajaxShopProductCountData";
			Map<String,String> params = new HashMap<String, String>();
			params.put("sid", sid);
			ReportSimpleBean bean = reportService.querySimpleReportAndDataByCode(reportCode, params);
			
			writeJson(response, bean);

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}


	/**
	 * 通用查询报表数据 , code入参必须提供,有界面提供
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/chart/ajaxCommonChartDataByCode.html")
	public ModelAndView ajaxCommonChartDataByCode(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String sid = request.getParameter("sid");
			String code = request.getParameter("code");
			if(StringUtil.isNotNull(code)) {
				Map<String,String> params = new HashMap<String, String>();
				params.put("sid", sid);
				params.put("openid", openid);
				ReportSimpleBean bean = reportService.querySimpleReportAndDataByCode(code, params);
				writeJson(response, bean);
			}

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}
	
	
	
}
