package com.yd.business.supplier.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.taobao.api.domain.Area;
import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.log.service.ILogService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.supplier.bean.SupplierArticleBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardBean;
import com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierStoreService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.NumberUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author sxq
 *
 */
@Controller
public class SupplierStoreController extends BaseController{
	
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_ADDORUPDATEBALANCECARD = "/page/supplier/shop/manager/store/addOrUpdateBalanceCard.jsp";
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_BALANCECARDLIST = "/page/supplier/shop/manager/store/storeBalanceCardList.jsp";
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_ASSIGNUSER = "/page/supplier/shop/manager/store/storeBalanceCardAssignUser.jsp";
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERASSIGNCARD = "/page/supplier/shop/manager/store/userAssignBalanceCard.jsp";
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERBALANCECARDUPDATE = "/page/supplier/shop/manager/store/userBalanceCardUpdate.jsp";
	public static final String PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERBALANCECARDLIST = "/page/supplier/shop/manager/store/userBalanceCardList.jsp";
	
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private ILogService logService;
	@Resource
	private ISupplierStoreService supplierStoreService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private ISupplierUserService supplierUserService;
	
	
	@RequestMapping("/supplier/store/toCreateOrUpdateStoreBalanceCardPage.html")
	public ModelAndView toCreateOrUpdateStoreBalanceCardPage(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			String id = request.getParameter("id");
			SupplierStoreBalanceCardBean card = null ;
			if(StringUtil.isNotNull(id)) {
				card = supplierStoreService.findStoreBalanceCardById(Integer.parseInt(id));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("card", card);
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_ADDORUPDATEBALANCECARD ,map);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/supplier/store/createOrUpdateStoreBalanceCard.html")
	public ModelAndView createOrUpdateStoreBalanceCard(HttpServletRequest request,HttpServletResponse response) {
		try {
			String card_priceStr = request.getParameter("card_price");
			String init_balanceStr =request.getParameter("init_balance");
			
			int card_price = NumberUtil.multiply100(card_priceStr);
			int init_balance = NumberUtil.multiply100(init_balanceStr);
			
			SupplierStoreBalanceCardBean card = new SupplierStoreBalanceCardBean() ;
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), card);
			SupplierBean supplier = getCurrentSupplier();
			card.setCard_price(card_price);
			card.setInit_balance(init_balance);
			
			if(StringUtil.isNull(card.getId())) {
				card.setSupplier_id(supplier.getId());
				supplierStoreService.createStoreBalanceCard(card);
			}else {
				supplierStoreService.updateStoreBalanceCard(card);
			}
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("card", card);
			return new ModelAndView("redirect:/supplier/store/toStoreBalanceCardListPage.html" );
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}

	@RequestMapping("/supplier/store/toStoreBalanceCardListPage.html")
	public ModelAndView toStoreBalanceCardListPage(HttpServletRequest request,HttpServletResponse response) {
		try {
			
			SupplierBean supplier = getCurrentSupplier();
			supplier = supplierService.findSupplierById(supplier.getId());
			SupplierStoreBalanceCardBean card = new SupplierStoreBalanceCardBean() ;
			card.setSupplier_id(supplier.getId());
			card.setOrderby(" order by id desc");
			List<SupplierStoreBalanceCardBean> list = supplierStoreService.queryStoreBalanceCard(card);
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("supplier", supplier);
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_BALANCECARDLIST ,map);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 * 	跳转至折扣卡分配界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/toStoreAssignUserPage.html")
	public ModelAndView toStoreAssignUserPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String card_id = request.getParameter("card_id");
			
			SupplierStoreBalanceCardBean card = null;
			List<SupplierUserBean> userList = Collections.EMPTY_LIST;
			if(StringUtil.isNotNull(card_id)) {
				
				card = supplierStoreService.findStoreBalanceCardById(Integer.parseInt(card_id),supplier.getId());
				
				SupplierUserBean userBean = new SupplierUserBean();
				userBean.setSupplier_id(supplier.getId());
				userBean.setOrderby(" order by last_access_time desc");
				userList = supplierUserService.querySupplierUser(userBean );
				
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("card", card);
			map.put("userList", userList);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_ASSIGNUSER ,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	

	/**
	 * 	跳转至折扣卡分配界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/toAssignBalanceCardToUserPage.html")
	public ModelAndView toAssignBalanceCardToUserPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String openid = request.getParameter("openid");
			
			SupplierUserBean spUser=  supplierUserService.findSupplierUser(openid, supplier.getId());
			
			SupplierStoreBalanceCardBean bean = new SupplierStoreBalanceCardRecordBean();
			bean.setSupplier_id(supplier.getId());
			bean.setStatus(SupplierStoreBalanceCardBean.STATUS_UP);
			List<SupplierStoreBalanceCardBean> cardList = supplierStoreService.queryStoreBalanceCard(bean );
			
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("spUser", spUser);
			map.put("cardList", cardList);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERASSIGNCARD ,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	

	/**
	 * 	给用户分配折扣卡
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/storeBalanaceCardAssignUser.html")
	public ModelAndView storeBalanaceCardAssignUser(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String card_id = request.getParameter("card_id");
			String user_id = request.getParameter("user_id");
			String nick_name = request.getParameter("nick_name");
			String phone = request.getParameter("phone");
			
			
			int record_id = supplierStoreService.createStoreBalanceCardRecord(Integer.parseInt(user_id),Integer.parseInt(card_id),supplier.getId());
			
			//更新用户昵称和手机号
			SupplierUserBean spUser = supplierUserService.findSupplierUser(Integer.parseInt(user_id), supplier.getId());
			if(StringUtil.isNotNull(phone)){
				spUser.setPhone(phone);
			}
			if(StringUtil.isNotNull(nick_name)){
				spUser.setNick_name(nick_name);
			}
			supplierUserService.updateSupplierUser(spUser);
			
			return new ModelAndView("redirect:/supplier/store/toUserBalanceCardManagerPage.html?record_id="+ record_id +"&openid=" + spUser.getOpenid());
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/toUserBalanceCardManagerPage.html")
	public ModelAndView toUserBalanceCardManagerPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String openid = request.getParameter("openid");
			String record_id = request.getParameter("record_id");
			
			SupplierUserBean spUser = supplierUserService.findSupplierUser(openid, supplier.getId());
			
			SupplierStoreBalanceCardRecordBean record = null;
			SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
			bean.setSupplier_id(supplier.getId());
			bean.setOpenid(openid);
			bean.setId(Integer.parseInt(record_id));
			List<SupplierStoreBalanceCardRecordBean> cardList = supplierStoreService.queryStoreBalanceCardRecord(bean);
			
			if(cardList.size() > 0) {
				record = cardList.get(0);
			}
			SupplierStoreBalanceCardBean card = supplierStoreService.findStoreBalanceCardById(record.getCard_id(), supplier.getId());

			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("record", record);
			map.put("spUser", spUser);
			map.put("card", card);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERBALANCECARDUPDATE  ,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/toUserBalanceCardListPage.html")
	public ModelAndView toUserBalanceCardListPage(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String openid = request.getParameter("openid");
			
			SupplierUserBean spUser = supplierUserService.findSupplierUser(openid, supplier.getId());
			
			SupplierStoreBalanceCardRecordBean record = null;
			SupplierStoreBalanceCardRecordBean bean = new SupplierStoreBalanceCardRecordBean();
			bean.setSupplier_id(supplier.getId());
			bean.setOpenid(openid);
			List<SupplierStoreBalanceCardRecordBean> recordList = supplierStoreService.queryStoreBalanceCardRecord(bean);
			
			//如果只有一条记录，则直接进入对应的余额卡管理界面
			if(recordList.size() == 1) {
				record = recordList.get(0);
				return new ModelAndView("redirect:/supplier/store/toUserBalanceCardManagerPage.html?openid="+openid+"&record_id="+record.getId());
			}

			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("recordList", recordList);
			map.put("spUser", spUser);
			
			return new ModelAndView(PAGE_SUPPLIER_SHOP_MANAGER_STORE_USERBALANCECARDLIST  ,map );
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
	
	/**
	 * 	跳转至产品套餐管理界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("supplier/store/ajax/updateUserStoreCardRecordBalance.html")
	public ModelAndView updateUserStoreCardRecordBalance(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			SupplierBean supplier = getCurrentSupplier();
			String openid = request.getParameter("openid");
			String record_id = request.getParameter("id");
			String dff_time = request.getParameter("dff_time");
			String addBalance = request.getParameter("addBalance");
			
			
			int add = NumberUtil.multiply100(addBalance);
			String remark = "店主为您修改余额";
			supplierStoreService.updateStoreCardRecordBalance(openid, Integer.parseInt(record_id), add, supplier.getId(),null,remark);
			
			SupplierStoreBalanceCardRecordBean record = supplierStoreService.findStoreBalanceCardRecordById(Integer.parseInt(record_id));
			record.setDff_time(dff_time);
			supplierStoreService.updateStoreBalanceCardRecord(record);
			
			writeJson(response, "success");
			
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, e.toString());
		}
		
		return null;
	}
	
}
