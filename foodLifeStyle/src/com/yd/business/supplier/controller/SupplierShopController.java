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

import org.apache.commons.fileupload.FileItem;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.image.bean.ImageBean;
import com.yd.business.order.bean.ShopOrderEffInfoBean;
import com.yd.business.order.bean.ShopOrderEffProductBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IShopOrderService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierPowerLogBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierProductCategoryBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.CookieUtil;
import com.yd.util.DateUtil;
import com.yd.util.ImageUtils;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author ice
 *
 */
@Controller
public class SupplierShopController extends BaseController {
	public static final String PAGE_SUPPLIERSHOPMANAGER_CATEGORY = "/page/supplier/shop/manager/category.jsp";
	public static final String PAGE_SUPPLIERSHOP_SHOPCATEGORY = "/page/supplier/shop/shopCategory.jsp";
	public static final String PAGE_SUPPLIERSHOPMANAGER_INDEX = "/page/supplier/shop/manager/index.jsp";
	public static final String PAGE_SUPPLIER_SHOP_SIMPLESETUP = "/page/supplier/shop/manager/shopSimpleSetUp.jsp";
	
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
	private ISupplierUserService supplierUserService;
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	private IShopOrderService shopOrderService;
	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toMySupplierShopManagerFramePage.html")
	public ModelAndView toMySupplierShopManagerFramePage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			String openid = request.getParameter("openid");
			List<SupplierBean> list = supplierService.querySupplierByOpenid(openid);
			SupplierBean supplier = null;
			String modelView = null;
			switch (list.size()) {
			case 0:
				//没有注册过,到注册界面
				modelView = "redirect:/wechat/user/toDistributeControll.do?openid="+openid+"&conName=wx.supplier.toSupplierSignupPage";
				break;
			case 1:
				//注册过，只有一个，进入管理界面
				supplier = list.get(0);
				//设置session
				WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_CURRENT_SUPPLIER, supplier);
//				CookieUtil.addCookie(response, WebContext.SESSION_ATTRIBUTE_CURRENT_SUPPLIER, ""+supplier.getId());
				modelView = "/page/supplier/shop/manager/frameIndex.jsp?openid="+openid+"&sid="+supplier.getId();
				break;
			default:
				//注册过多个
				supplier = list.get(0);
				modelView = "/page/supplier/shop/manager/frameIndex.jsp?openid="+openid+"&sid="+supplier.getId();
				// 要改
				break;
			}
			
			return new ModelAndView(modelView);
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "数据处理失败！");
		}
		return null;
	}

	/**
	 * 	跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/supplier/shop/toSupplierShopPage.html")
	public ModelAndView toSupplierShopPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String isEff = request.getParameter("isEff");
		String fromOpenid = request.getParameter("fromOpenid");
		String openid = getCurrentOpenid();
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}

			SupplierBean supplier = supplierService.findSupplierById(supplier_id);
			//创建商户的用户
			supplierUserService.createOrUpdateSupplierUser(openid,supplier_id);
			
			List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id,SupplierProductCategoryBean.STATUS_YES);
			
			SupplierProductBean condition = new SupplierProductBean();
			condition.setSupplier_id(supplier_id);
			condition.setStatus(SupplierProductBean.STATUS_UP);
			condition.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
			condition.setNow_time(DateUtil.getNowDateStr());
			condition.setOrderby(" order by pc.seq , product_category_id,sp.seq  asc ");
			List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			UserQrCodeBean qrCode = null;
			if(user.getStatus() != UserWechatBean.STATUS_SUBSCRIBE) {
				// 如果openid为空，则取商户创建人的
				if(fromOpenid == null) {
					fromOpenid = supplier.getOpenid();
				}
				int senceCode = "1".equals(isEff) ? WechatConstant.TICKET_SENCE_CODE_SUPPLIERSHOPEFF : WechatConstant.TICKET_SENCE_CODE_SUPPLIERSHOP ;
				qrCode = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, senceCode , supplier_id);
			}

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("productCategoryList", productCategoryList);
			model.put("productList", productList);
			model.put("openid", openid);
			model.put("supplier", supplier);
			model.put("user", user);
			model.put("qrCode", qrCode);
			
			return new ModelAndView(PAGE_SUPPLIERSHOP_SHOPCATEGORY, model);			
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toManagerCategoryPage.html")
	public ModelAndView toManagerCategoryPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String openid = request.getParameter("openid");
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			//判断openid 和 商户ID是否一致，不一致可能是被改了商户id
			SupplierBean supplier = supplierService.findSupplier(supplier_id,openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(supplier != null) {
				List<SupplierProductCategoryBean> productCategoryList = supplierProductService.querySupplierProductCategoryBySupplierId(supplier_id,SupplierProductCategoryBean.STATUS_YES);
				
				SupplierProductBean condition = new SupplierProductBean();
				condition.setSupplier_id(supplier_id);
				condition.setDelete_flag(SupplierProductBean.DELETE_FLAG_NO);
//				condition.setStatus(SupplierProductBean.STATUS_UP);
				condition.setNow_time(DateUtil.getNowDateStr());
				condition.setOrderby(" order by pc.seq , product_category_id,sp.seq  asc ");
				List<SupplierProductBean> productList = supplierProductService.listSupplierProduct(condition );
				
//					UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
				Map<String, Object> model = new HashMap<String, Object>();
//					model.put("user", user);
				model.put("productCategoryList", productCategoryList);
				model.put("productList", productList);
				model.put("openid", openid);
				model.put("supplier", supplier);
				model.put("user", user);

				return new ModelAndView(PAGE_SUPPLIERSHOPMANAGER_CATEGORY, model);	
			}else {
				writeJson(response, "您没有权限访问他人的店铺信息");
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 跳转至商户商城界面
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wx/supplier/shop/toShopManagerIndexPage.html")
	public ModelAndView toShopManagerIndexPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String sid = request.getParameter("sid");
		String openid = request.getParameter("openid");
		if(StringUtil.isNull(openid)) {
			openid = getCurrentOpenid();
		}
		try{
			Integer supplier_id = SupplierBean.PLATFROM_SUPPLIER_ID;
			if(StringUtil.isNotNull(sid)){
				supplier_id = Integer.parseInt(sid);
			}
			
			//判断openid 和 商户ID是否一致，不一致可能是被改了商户id
			SupplierBean supplier = supplierService.findSupplier(supplier_id,openid);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(supplier != null) {
				

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("supplier", supplier);
				return new ModelAndView(PAGE_SUPPLIERSHOPMANAGER_INDEX, model );	
			}else {
				writeJson(response, "您没有权限访问他人的店铺信息");
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	/**
	 * 设置店铺属性
	 */
	@RequestMapping("/supplier/shop/simpleSetUp.html")
	public ModelAndView simpleSetUp(HttpServletRequest request,HttpServletResponse response){
		try{
			SupplierBean supplier = getCurrentSupplier();
			String pay_where = request.getParameter("pay_where");
			String supplier_img = null ;
			String personal_pay_img = null;
			
			//压缩并写入图片到磁盘
			int width = 160 * 3;
			int height = 90 * 3;
			String tempDir = ImageBean.THUMB_IMG_DIR + supplier.getId() + "/";
			String targetDir = request.getServletContext().getRealPath("/")+ "/" + tempDir;
			
			MultiValueMap<String, MultipartFile> map = ((DefaultMultipartHttpServletRequest)request).getMultiFileMap();
			FileItem item = null;
			CommonsMultipartFile cmf = (CommonsMultipartFile) map.get("supplier_img").get(0);
			if(cmf != null && cmf.getFileItem().getSize() > 0) {
				String targetFileSrc = ImageUtils.uploadFileByRequest(cmf.getFileItem(), targetDir, width, height);
				if(StringUtil.isNotNull(targetFileSrc)) {
					String fileName = targetFileSrc.substring(targetFileSrc.lastIndexOf("/")+1);
					supplier_img = tempDir + fileName;
					supplier.setSupplier_img(supplier_img);
				}
			}
			
			//压缩并写入个人收款图片到磁盘
			CommonsMultipartFile cmf2 = (CommonsMultipartFile) map.get("personal_pay_img").get(0);
			if(cmf2 != null && cmf2.getFileItem().getSize() > 0) {
				String targetFileSrc = ImageUtils.uploadFileByRequest(cmf2.getFileItem(), targetDir, 1080, 1457);
				if(StringUtil.isNotNull(targetFileSrc)) {
					String fileName = targetFileSrc.substring(targetFileSrc.lastIndexOf("/")+1);
					personal_pay_img = tempDir + fileName;
					supplier.setPersonal_pay_img(personal_pay_img);
				}
			}
			
			supplier.setPay_where(Integer.parseInt(pay_where));
			
			supplierService.updateSupplier(supplier);
			
			writeJson(response, "success");
			
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	

	/**
	 * 跳转至设置店铺属性的界面
	 */
	@RequestMapping("/supplier/shop/toSimpleSetUpPage.html")
	public ModelAndView toSimpleSetUpPage(HttpServletRequest request,HttpServletResponse response){
		try{
			SupplierBean supplier = getCurrentSupplier();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("supplier", supplier);
			return new ModelAndView(PAGE_SUPPLIER_SHOP_SIMPLESETUP,map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	@RequestMapping("**order/shop/updateOrderEffDate.html")
	public ModelAndView updateOrderEffDate(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String effDate = request.getParameter("effDate");
			String remark = request.getParameter("remark");
			String contactName = request.getParameter("contact_name");
			String contactPhone = request.getParameter("contact_phone");
			
			boolean flag = shopOrderService.updateShopOrderToEff(orderCode, effDate,openid,remark,contactName,contactPhone);
			
			if(flag) {
				writeJson(response, "success");
			}else {
				writeJson(response, "error");
			}

		} catch (Exception e) {
			log.error(e,e);
		}
		
		return null;
	}
	

	@RequestMapping("**order/shop/cancelOrderEffDate.html")
	public ModelAndView cancelOrderEffDate(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String remark = request.getParameter("remark");
			
			shopOrderService.updateOrderStatusToCancel(orderCode, openid, remark);
			
			writeJson(response, "success");

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	/**
	 * 查询近期订单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/shop/ajaxShopEffOrderLatelyData.html")
	public ModelAndView ajaxShopEffOrderLatelyData(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			String openid = getCurrentOpenid();
			String orderCode = request.getParameter("orderCode");
			String remark = request.getParameter("remark");
			
			shopOrderService.updateOrderStatusToCancel(orderCode, openid, remark);
			
			writeJson(response, "success");

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
}
