package com.yd.business.supplier.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.order.service.IOrderService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierCouponConfigBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierCouponRuleBean;
import com.yd.business.supplier.service.ISupplierCouponService;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserConsumeInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatPayInfoBean;
import com.yd.business.wechat.bean.WechatPayResultBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.HttpUtil;
import com.yd.util.MD5Util;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;


/**
 * 
 * @author zxz
 *
 */
@Controller
public class SupplierCouponController extends BaseController{
	
	@Resource
	private ISupplierCouponService supplierCouponService;
	@Resource
	protected IUserWechatService userWechatService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IUserConsumeInfoService userConsumeInfoService;
	@Resource
	protected IWechatService wechatService;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	private IOrderService orderService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	
	public static final String PAGE_MYCOUPON = "/page/user/activity/coupon/myCoupon.jsp";
	public static final String PAGE_MYCOUPONPAGE = "/page/user/activity/coupon/myCoupon.jsp";
	public static final String PAGE_COUPONSHOP = "/page/user/activity/coupon/orderProduct.jsp";
	public static final String PAGE_ORDERCONFIRM = "/page/user/activity/coupon/orderConfirm.jsp";
	public static final String PAGE_USER_ORDER_PRODUCT_RESULT = "/page/user/orderResult.jsp";
	public static final String PAGE_RECEIVECOUPON = "/page/user/activity/coupon/receiveCoupon.jsp";
	public static final String PAGE_USER_COUPONCENTER = "/page/user/supplierProductShop/couponCenter.jsp";
	public static final String PAGE_USER_MYCOUPON = "/page/user/supplierProductShop/myCoupon.jsp";

	
	
	
	
	/**
	 * 跳转到领取优惠卷界面,展示目前具有的优惠卷
	 */
	@RequestMapping("**/supplier/supplierCouponController/receiveCouponPage.do")
	public ModelAndView receiveCouponPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String openid = request.getParameter("openid");	//用于正式
//			String openid = "123";							//测试方法
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//领取优惠卷的时候查询可以展示的优惠卷信息
			List<SupplierCouponConfigBean> showCouponlist = supplierCouponService.querySureShowCoupon(user);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("showCouponlist", showCouponlist);
			model.put("openid", openid);
			return new ModelAndView(PAGE_RECEIVECOUPON, model);
		}catch(Exception e){
			log.error(e, e);
			writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
		}
		return null;
	}

	
	
	

	/**
	 *领取优惠卷
	 *	 */
	@RequestMapping("**/supplier/supplierCouponController/reveiveCoupon.do")
	public ModelAndView reveiveCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String coupon_id = request.getParameter("coupon_id");	//优惠卷id
			String openid = request.getParameter("openid");			//用于正式
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			String reveiveResult = supplierCouponService.reveiveCouponResult(Integer.parseInt(coupon_id),user);				//根据优惠卷规则表中该优惠卷规则，返回结果
			SupplierCouponConfigBean bean = supplierCouponService.findCouponInfoByCouponid(Integer.parseInt(coupon_id));			
			bean.setReveiveResult(reveiveResult);
			bean.setOpenid(openid);
			writeJson(response,bean); 
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
	/**
	 *	查询目前还剩余的优惠卷
	 *	 *	 */
	@RequestMapping("**/supplier/supplierCouponController/findSurplusCoupon.do")
	public ModelAndView findSurplusCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String coupon_id = request.getParameter("coupon_id");	//优惠卷id
			SupplierCouponConfigBean bean = supplierCouponService.findCouponInfoAndRuleNameByCouponid(Integer.parseInt(coupon_id));			
			writeJson(response,bean); 
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
	
	
	/**
	 *	查询用户自己拥有的优惠卷数目
	 *	 *	 */
	@Deprecated
	@RequestMapping("**/supplier/supplierCouponController/queryMyAllCoupon.do")
	public ModelAndView queryMyAllCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String userid = request.getParameter("userid");					//当前用户id
			String supplier_id = request.getParameter("supplier_id");  		//当前用户产品id
			List<SupplierCouponRecordBean> list = supplierCouponService.queryUserAllCoupon(Integer.parseInt(userid),Integer.parseInt(supplier_id));			
			writeJson(response,list); 
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	/**
	 *使用优惠卷
	 *	 */
	@RequestMapping("**/supplier/supplierCouponController/useCoupon.do")
	public ModelAndView useCoupon(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String userid = request.getParameter("userid");	//接收userid
			String coupon_id = request.getParameter("coupon_id");	//优惠卷id
			String orderCode = request.getParameter("orderCode");
			 SupplierCouponRecordBean bean = new  SupplierCouponRecordBean();
			 bean.setUserid(Integer.parseInt(userid));
			 bean.setCoupon_id(Integer.parseInt(coupon_id));
			 List<SupplierCouponRecordBean> myCouponList = supplierCouponService.queryUserCanUseCoupon(bean.getUserid());
			 if(myCouponList.size() == SupplierCouponRecordBean.LIST_SIZE_ZERO ){
			       writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>" );
			       return null ;
			   }
			String reveiveResult = supplierCouponService.useCouponResult(Integer.parseInt(coupon_id),Integer.parseInt(userid),orderCode);				//根据优惠卷规则表中该优惠卷规则，返回结果
			bean.setReveiveresult(reveiveResult);
			writeJson(response,bean); 
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
	
	
	
	/**
	 * 从个人中心优惠卷的立即使用跳转到流量商城界面
	 */
	@RequestMapping("**/supplier/jumpCouponShop.do")
	public ModelAndView jumpCouponShop(HttpServletRequest request,HttpServletResponse response){
		try{
			
//			List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
			String openid = request.getParameter("openid");	//用户openid
			String coupon_record_id = request.getParameter("coupon_record_id");				//优惠卷记录表中的id
			String coupon_id = request.getParameter("coupon_id");				//优惠卷id
			String coupon_discount = request.getParameter("coupon_discount");	//优惠卷折扣
			String coupon_offsetmoney = request.getParameter("coupon_offsetmoney");//优惠卷现金折扣
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);

//			SupplierCouponRecordBean bean = new  SupplierCouponRecordBean();
//			bean.setUserid(user.getId());
//			bean.setCoupon_id(Integer.parseInt(coupon_id));
			List<SupplierCouponRecordBean> myCouponList = supplierCouponService.queryUserCanUseCoupon(user.getId());
			if(myCouponList.size() == SupplierCouponRecordBean.LIST_SIZE_ZERO){
				writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
				return null ;
			}
			
			
//			UserWechatBean user = userWechatService.findUserWechatById(5000);
			Map<String, Object> model = new HashMap<String, Object>();
			model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("customer_id", CustomerBean.ID_PLATFROM);
			model.put("openid", user.getOpenid());
			model.put("coupon_id", coupon_id);
			model.put("coupon_record_id", coupon_record_id);
			model.put("coupon_discount", coupon_discount);
			model.put("coupon_offsetmoney", coupon_offsetmoney);
			//调用数据库查询是否有历史的号码,获得直接在页面展示
			OrderProductLogBean beanout = null;
			beanout=orderProductLogService.findOrderProductLogByUseridDesc(user.getId());
			if(beanout!=null){
			if(!"null".equals(beanout.getOrder_account())){
				model.put("order_account",beanout.getOrder_account());
			}
			}
			return new ModelAndView(PAGE_COUPONSHOP, model);			//返回优惠卷流量商城购买界面
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	


	/**
	 * 查询目前配置的优惠卷
	 */
	@RequestMapping("/supplier/queryCouponInfo.do")
	public ModelAndView queryCouponInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			List<SupplierCouponConfigBean> list = supplierCouponService.queryAllCouponInfo();
			if(list == null || list.size() != 0)
			{
				writeJson(response, list);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		
		return null;
	}


	/**
	 * 查询目前配置的优惠卷
	 */
	@RequestMapping("**/supplier/coupon/toUserCouponCenterPage.do")
	public ModelAndView toUserCouponCenterPage(String openid){
		try{
			List<SupplierCouponConfigBean> list = supplierCouponService.queryAllEnableCouponInfo();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("openid", openid);
			
			return new ModelAndView(PAGE_USER_COUPONCENTER, map);
		}catch(Exception e){
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	/**
	 * 查询自己拥有的优惠卷,跳到转查询自己优惠卷界面
	 */
	@Deprecated
	@RequestMapping("**/supplier/supplierCouponController/queryMycouponPage.do")
	public ModelAndView queryMycouponPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String openid = request.getParameter("openid");	//用于正式
//			String openid = "123";							//测试方法
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierCouponRecordBean bean = new  SupplierCouponRecordBean();
			bean.setUserid(user.getId());
			List<SupplierCouponRecordBean> myCouponList = supplierCouponService.queryUserCanUseCoupon(user.getId());
			if(myCouponList == null || myCouponList.size() != 0)
			{
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("openid",user.getOpenid());
				model.put("myCouponList", myCouponList);
				return new ModelAndView(PAGE_MYCOUPON,model);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	

	
	/**
	 * 查询自己拥有的优惠卷,跳到转查询自己优惠卷界面
	 */
	@RequestMapping("**/supplier/supplierCouponController/toMycouponPage.do")
	public ModelAndView toMycouponPage(String openid) throws IOException{
		try{
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierCouponRecordBean bean = new  SupplierCouponRecordBean();
			bean.setUserid(user.getId());
			List<SupplierCouponRecordBean> myCouponList = supplierCouponService.queryUserAllCoupon(user.getId(),null);
			if(myCouponList == null || myCouponList.size() != 0)
			{
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("openid",user.getOpenid());
				model.put("list", myCouponList);
				return new ModelAndView(PAGE_USER_MYCOUPON,model);
			}
		}catch(Exception e){
			log.error(e, e);
		}
		return null;
	}
	
	
	/**
	 * 查询商品列表
	 */
	@RequestMapping("**/supplier/queryProductByPhone.do")
	public ModelAndView queryProductByPhone(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String customer_id = request.getParameter("customer_id");
			String phone = request.getParameter("phone");
			String coupon_id = request.getParameter("coupon_id");
			List<CustomerSupplierProductBean> list = supplierCouponService.queryCustomerProductByPhone(Integer.parseInt(customer_id), phone,Integer.parseInt(coupon_id));
			
			if(list == null || list.size() != 0)
			{
				writeJson(response, list);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
	/**
	 *优惠卷配置管理,查询功能
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/queryAdminConponConfig.do")
	public ModelAndView queryAdminConponConfig(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
			SupplierProductBean supplierProductBean = new SupplierProductBean();
			String merchant_name =  request.getParameter("query_dictionary_merchant_name");
			String coupon_name =  request.getParameter("query_couponconfig_coupon_name");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(merchant_name)){
				bean.setSupplier_name(merchant_name);
			}
			if(!StringUtil.isNull(coupon_name)){
				bean.setCoupon_name(coupon_name);
			}
			PageinationData pd =  supplierCouponService.queryAdminConponConfigPage(bean);
			
			List<SupplierProductBean> supplierProductList =  supplierProductService.listSupplierProduct(supplierProductBean);
			
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("supplierProductList", supplierProductList);
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/coupon/iframe_coupon_config.jsp", map);
	}
	
	
	/**
	 *优惠卷记录管理,查询功能
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/queryAdminConponRecord.do")
	public ModelAndView queryAdminConponRecord(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
			String query_couponrecord_userid =  request.getParameter("query_couponrecord_userid");
			String query_couponrecord_order_code =  request.getParameter("query_couponrecord_order_code");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(query_couponrecord_userid)){
				bean.setUserid(Integer.parseInt(query_couponrecord_userid));
			}
			if(!StringUtil.isNull(query_couponrecord_order_code)){
				bean.setOrder_code(query_couponrecord_order_code);
			}
			PageinationData pd =  supplierCouponService.queryAdminConponRecordPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/coupon/iframe_coupon_record.jsp", map);
	}
	
	
	/**
	 *优惠卷规则管理,查询功能
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/queryAdminConponRule.do")
	public ModelAndView queryAdminConponRule(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
			String rule_name =  request.getParameter("query_couponrule_rule_name");
			String sql =  request.getParameter("query_crons_SQL");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(Integer.parseInt(nowpage));
			}
			if(!StringUtil.isNull(rule_name)){
				bean.setRule_name(rule_name);
			}
			if(!StringUtil.isNull(sql)){
				bean.setSQL(sql);
			}
			PageinationData pd =  supplierCouponService.queryAdminConponRulePage(bean);
			List<SupplierCouponConfigBean> couponInfo = supplierCouponService.queryAllCouponInfo();

			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("couponInfo", couponInfo);
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/coupon/iframe_coupon_rule.jsp", map);
	}
	
	
	
	/**
	 * 优惠卷配置表删除 	根据id删除ll_coupon_config表信息
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/deleteCouponConfig.do")
	public ModelAndView deleteCouponConfig(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
				bean.setId(Integer.parseInt(id));
				supplierCouponService.deteleCouponConfig(bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponConfigBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponConfigBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponConfigBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponConfigBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	
	/**
	 * 优惠卷记录表删除 	根据id删除ll_coupon_record表信息
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/deleteCouponRecord.do")
	public ModelAndView deleteCouponRecord(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierCouponRecordBean bean = new SupplierCouponRecordBean();
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
				bean.setId(Integer.parseInt(id));
				supplierCouponService.deteleCouponRecord(bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRecordBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRecordBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRecordBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRecordBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	
	/**
	 * 优惠卷规则表删除 	根据id删除ll_coupon_rule表信息
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/deleteCouponRule.do")
	public ModelAndView deleteCouponRule(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			SupplierCouponRuleBean bean = new SupplierCouponRuleBean();
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
				bean.setId(Integer.parseInt(id));
				supplierCouponService.deteleCouponRule(bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRuleBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRuleBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	
	
	
	/**
	 *  增加和编辑优惠卷配置表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/editCouponConfig.do")
	public ModelAndView editCouponConfig(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SupplierCouponConfigBean bean =  new SupplierCouponConfigBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setSupplier_id(Integer.parseInt(request.getParameter("merchant_id")));
			bean.setSupplier_name(request.getParameter("merchant_name"));
			bean.setCode(request.getParameter("code"));
			bean.setType(Integer.parseInt(request.getParameter("type")));
			bean.setCoupon_name(request.getParameter("coupon_name"));
			bean.setCoupon_discount(Integer.parseInt(request.getParameter("coupon_discount")));
			bean.setCoupon_offsetmoney(Integer.parseInt(request.getParameter("coupon_offsetmoney")));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			bean.setNumber(Integer.parseInt(request.getParameter("number")));
			bean.setReceive_limit_num(Integer.parseInt(request.getParameter("receive_limit_num")));
			bean.setCoupon_count(Integer.parseInt(request.getParameter("coupon_count")));
			bean.setBegin_time(request.getParameter("begin_time"));
			bean.setEnd_time(request.getParameter("end_time"));
			bean.setRemark(request.getParameter("remark"));
			bean.setCoupon_backgroup(request.getParameter("coupon_backgroup"));
			bean.setCoupon_spid(request.getParameter("couponshow_product"));
			supplierCouponService.editCouponConfig(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponConfigBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponConfigBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
	
	/**
	 * 后台管理中得到目前优惠卷配置的产品
	 * @return
	 */
	@RequestMapping("**/admin/supplier/supplierCouponController/adminGetProductForAjax.do")
	public ModelAndView adminGetProductForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			SupplierProductBean bean = new SupplierProductBean();
			List<Integer> integerList =  new  ArrayList<Integer>();
			List<SupplierProductBean> SupplierProductList = new  ArrayList<SupplierProductBean>();
			String couponshow_product =  request.getParameter("couponshow_product");
			if(StringUtil.isNull(couponshow_product)){
				integerList.add(SupplierProductBean.ZERO);
				 SupplierProductList =  supplierProductService.querySupplierProductByIds(integerList);
			}else{
				StringTokenizer st = new StringTokenizer(couponshow_product,",");
			    while(st.hasMoreTokens() ){
				    Integer productId = Integer.valueOf(st.nextToken());
				    integerList.add(productId);
				 SupplierProductList =  supplierProductService.querySupplierProductByIds(integerList);
			    }
			}
			
			if(SupplierProductList == null || SupplierProductList.size() != 0)
			{
				writeJson(response, SupplierProductList);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	/**
	 * 后台管理中=目前优惠卷可以展示产品删除
	 * @return
	 */
	@RequestMapping("**/admin/supplier/supplierCouponController/admindeleteShowProduct.do")
	public ModelAndView admindeleteShowProduct(HttpServletRequest request,HttpServletResponse response){
		try {
			SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
			String id = request.getParameter("id");
			String show_prodcut = request.getParameter("show_product");
			bean.setId(Integer.parseInt(id));
			bean.setCoupon_spid(show_prodcut);
			String remark = supplierCouponService.deleteShowProduct(bean);
			writeJson(response, remark);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	/**
	 * 后台管理中=目前优惠卷可以展示产品增加
	 * @return
	 */
	@RequestMapping("**/admin/supplier/supplierCouponController/adminAddShowProduct.do")
	public ModelAndView adminAddShowProduct(HttpServletRequest request,HttpServletResponse response){
		try {
			SupplierCouponConfigBean bean = new SupplierCouponConfigBean();
			String id = request.getParameter("ids");
			String show_prodcut = request.getParameter("show_product");
			bean.setId(Integer.parseInt(id));
			bean.setCoupon_spid(show_prodcut);
			String remark = supplierCouponService.addShowProduct(bean);
			writeJson(response, remark);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	/**
	 *  增加和编辑优惠卷记录表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/editCouponRecord.do")
	public ModelAndView editCouponRecord(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SupplierCouponRecordBean bean =  new SupplierCouponRecordBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			String supplier_id = request.getParameter("supplier_id");
			if(!StringUtil.isNull(supplier_id)){
				bean.setSupplier_id(Integer.parseInt(supplier_id));
			}
			bean.setUserid(Integer.parseInt(request.getParameter("userid")));
			bean.setSupplier_name(request.getParameter("supplier_name"));
			bean.setCoupon_id(Integer.parseInt(request.getParameter("coupon_id")));
			bean.setOrder_id(Integer.parseInt(request.getParameter("order_id")));
			bean.setOrder_code(request.getParameter("order_code"));
			bean.setProduct_name(request.getParameter("product_name"));
			bean.setCreate_time(request.getParameter("create_time"));
			bean.setModify_time(request.getParameter("modify_time"));
			bean.setUse_time(request.getParameter("use_time"));
			bean.setExpire_time(request.getParameter("expire_time"));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			bean.setStatus_description(request.getParameter("status_description"));
			bean.setRemark(request.getParameter("remark"));
			supplierCouponService.editCouponRecord(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRecordBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRecordBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
	/**
	 *  增加和编辑优惠卷规则表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/supplier/supplierCouponController/editCouponRule.do")
	public ModelAndView editCouponRule(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SupplierCouponRuleBean bean =  new SupplierCouponRuleBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(Integer.parseInt(request.getParameter("id")));
			}
			bean.setCoupon_id(Integer.parseInt(request.getParameter("coupon_id")));
			bean.setRule_name(request.getParameter("rule_name"));
			bean.setExplain(request.getParameter("explain"));
			bean.setStatus(Integer.parseInt(request.getParameter("status")));
			bean.setType(Integer.parseInt(request.getParameter("type")));
			bean.setBegin_time(request.getParameter("begin_time"));
			bean.setEnd_time(request.getParameter("end_time"));
			bean.setSQL(request.getParameter("SQL"));
			supplierCouponService.editCouponRule(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(SupplierCouponRuleBean.CONFIG_CRUX_TYPE_POP_NEWS,SupplierCouponRuleBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
	
	
	
	
	
	
	
//	/**
//	 * 查询商品列表
//	 */
//	@RequestMapping("**/supplier/toOrderConfirm.do")
//	public ModelAndView toOrderConfirm(HttpServletRequest request,HttpServletResponse response,String phone,String spid,String openid,String coupon_id,String coupon_record_id,String customer_id){
//		try{
//			Map<String, Object> model = new HashMap<String, Object>();
//			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
//			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(Integer.parseInt(spid));
//			
//			int useCouponCount=supplierCouponService.queryUserCouponCount(Integer.parseInt(coupon_record_id),user.getId(),Integer.parseInt(coupon_id));
//			boolean checkProduct = supplierCouponService.checkCouponProduct( Integer.parseInt(customer_id), phone , Integer.parseInt(coupon_id));
//			if(checkProduct){
//				if(useCouponCount <= SupplierCouponRecordBean.USE_NO_COUPON_COUNT){
//					writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
//					return null ;
//				}
//			}else{
//				writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
//				return null ;
//			}
//			
//			
//			
//			SupplierCouponConfigBean bean = new  SupplierCouponConfigBean();//查询正在消费的优惠卷信息
//			bean.setId(Integer.parseInt(coupon_id));
//			SupplierCouponConfigBean myCouponbean = supplierCouponService.querySpendingCoupon(bean);
//			model = new HashMap<String, Object>();
//			model.put("customer_id", customer_id);
//			model.put("user", user);
//			model.put("coupon_record_id", coupon_record_id);
//			model.put("supplierProduct", sp);
//			model.put("phone", phone);
//			model.put("myCouponbean", myCouponbean);
//			return new ModelAndView(PAGE_ORDERCONFIRM, model);
//		}catch (Exception e) {
//			log.error(e,e);
//		}
//		return null;
//	}
//	
	
//	/**
//	 * 查询优惠卷规则通过id
//	 */
//	@RequestMapping("**/supplier/queryCouponRuleById.do")
//	public ModelAndView queryCouponRuleById(HttpServletRequest request,HttpServletResponse response){
//		try{
//			SupplierCouponRuleBean bean = supplierCouponService.queryCouponRuleById(1);
//			if(bean != null)
//			{
//				writeJson(response, bean);
//			}
//		}catch (Exception e) {
//			log.error(e, e);
//		}
//		return null;
//	}
	
	/**
	 * 支付成功之后给优惠卷拥有记录给更新了(该方法暂且不用)
	 * */
	@RequestMapping("**/supplier/changeCouponRecodeUserd.do")
	public ModelAndView changeCouponRecodeUserd(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String openid = request.getParameter("openid");	//用户openid
			String coupon_id = request.getParameter("coupon_id");	//用户openid
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			supplierCouponService.changeCouponRecodeUserd(user.getId() ,Integer.parseInt(coupon_id),Integer.parseInt(coupon_id) );
		}catch(Exception e){
			log.error(e,e);
		}
		
		return null;
	}
	
	
	//以下方法为调用支付方法
	@RequestMapping("**/supplier/user/createUnifiedOrderUser.do")
	public ModelAndView createUnifiedOrderUser(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");					//用户openid				123
			String price = request.getParameter("price");					//该商品需要支付的金额			2.4
			String pointsStr = request.getParameter("points");				//用积分可以抵用的现金			0
			String balanceStr = request.getParameter("cost_balance");		//从现金账户中将扣除的钱		240	
			String eff_numStr = request.getParameter("eff_num");			//重新生效时间				立即生效为0
			Integer points = StringUtil.isNull(pointsStr) ? 0:Integer.parseInt(pointsStr);			//把pointsStr变成integer类型，同时用三维运算符让他们不能为null
			Integer costBalance = StringUtil.isNull(balanceStr) ? 0:Integer.parseInt(balanceStr);	//把balanceStr变成integer类型，同时用三维运算符让他们不能为null
			Integer eff_num = StringUtil.isNull(eff_numStr) ? 0:Integer.parseInt(eff_numStr);		//把eff_numStr变成integer类型，同时用三维运算符让他们不能为null
			String phone = request.getParameter("phone");					//需要充值的号码
			Integer product_id = Integer.parseInt(request.getParameter("product_id"));				//产品id	40

			int rmb = (int) (Double.parseDouble(price)  * 100);										//把需要支付的金额转换成double类型，并乘以100.用rmb接收
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);					//通过openid把这个人的用户信息查出来
			if(user == null){																		//如何没有查到该人，返回异常，该用户为空
				throw new RuntimeException(" createUnifiedOrder user is null!");
			}
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(product_id);	//通过产品id到产品表中把这个购买的产品查出来
//			int costBalance = (int) (sp.getProduct_price() * sp.getDiscount()/100d - points);
			//商户订单号
			String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_USERBALANCE, user.getId());//生成一个订单号
			//保存充值记录
			
			UserConsumeInfoBean consume = userConsumeInfoService.createConsumeInfo(phone,costBalance, product_id, user.getId(), null, out_no,UserConsumeInfoBean.INTERFACETYPE_USERBALANCE,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER);
			//在ll_user_consume_info保存充值记录，status为0
			
			OrderProductLogBean productLog = orderProductLogService.createOrderProductLogByUserConsumeInfo(consume, points, 0,costBalance, null);
			//在ll_order_product_log表中创建一个订单记录,status为0
			
			
			orderProductLogService.updateOrderProductLogStatusToPaySuccess(productLog.getOrder_code());	//根据生成的订单号,更新ll_order_product_log状态为3
			userConsumeInfoService.updateUserConsumeInfoStatus(UserConsumeInfoBean.STATUS_SUCCESS, consume.getOut_trade_code());//根据订单号，把ll_user_consume_info状态更改为1
			writeJson(response, productLog.getOrder_code());
			
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "false");
		}
		
		return null;
		
	}
	
	
	/**
	 * 获取统一下单接口的预支付交易单号
	 * @return
	 */
	@RequestMapping("**/supplier/wechat/createUnifiedOrderWechat.do")
	public ModelAndView createUnifiedOrderWechat(HttpServletRequest request,HttpServletResponse response){
		
		String openid = request.getParameter("openid");
		String price = request.getParameter("price");
		String pointsStr = request.getParameter("points");
		String balanceStr = request.getParameter("cost_balance");
		String eff_numStr = request.getParameter("eff_num");
		Integer eff_num = StringUtil.isNull(eff_numStr) ? 0:Integer.parseInt(eff_numStr);
		Integer points = StringUtil.isNull(pointsStr) ? 0:Integer.parseInt(pointsStr);
		Integer cost_balance = StringUtil.isNull(balanceStr) ? 0:Integer.parseInt(balanceStr);
		String phone = request.getParameter("phone");
		Integer product_id = Integer.parseInt(request.getParameter("product_id"));
		
		
		UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		if(user == null){
			throw new RuntimeException(" createUnifiedOrder user is null!");
		}
		
		WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
		
		//wx26a55db19faf530f
		String appidStr = originalInfo.getAppid();
		String appid = "appid=" + appidStr; 
		//商户号
		String mch_id = "mch_id=" +originalInfo.getMch_id();
		//随机码
		String nonce_str = "nonce_str=" + UUID.randomUUID().toString().replaceAll("-", "");
		//附加数据，微信会原样返回,
		String attach = "attach="+ "test";
		//商品详情
		String body = "body=支付商品";
		//商户订单号
		String out_no = userConsumeInfoService.createOutTradeNo(IUserConsumeInfoService.OUTTRADE_TYPE_WXPAY, user.getId());
		String out_trade_no = "out_trade_no="+out_no;
		//需要支付的总金额,以分为单位
		int rmb = (int) (Double.parseDouble(price)  * 100);
		String total_fee = "total_fee="+ rmb;
		//客户IP
		String spbill_create_ip = request.getRemoteAddr();
		if(spbill_create_ip == null){ spbill_create_ip = "115.28.43.16"; }
		spbill_create_ip = "spbill_create_ip="+spbill_create_ip;
		
		//回调URL
		String notify_url = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_NOTIFY_URL);
		notify_url = "notify_url="+ notify_url ; //+ "wechat/serverNotify.html";
		
		//交易类型
		String trade_type = "trade_type=JSAPI";
		//用户ID //oiRcFuKHjk9_V8-eWwHA1W4x1XWc
		openid = "openid=" + user.getOpenid();
		//设备信息，公众号使用WEB
		String device_info = "device_info=WEB";
		//指定支付方式，不可使用信用卡
		String limit_pay = "limit_pay=no_credit";
		String key = "key=" + originalInfo.getPay_wechat_sign_key();

		Object[] params={appid,mch_id,nonce_str,attach,body,out_trade_no,device_info,
				total_fee,spbill_create_ip,notify_url,trade_type,openid,limit_pay};
		String tempStr = concatParam(params);
		//签名,需要把key放在最后
		tempStr += "&"+key;
		String sign = "sign="+MD5Util.encode16(tempStr,"UTF-8").toUpperCase();
		
		Object[] newParam = ArrayUtils.addAll(params, new String[]{sign});
		String xml = convertToXML(newParam);
		
		String callUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_PAY_WECHAT_UNIFIED_URL);
		long time = System.currentTimeMillis();
		try {
			//调用微信的预支付接口
			String responseStr = HttpUtil.post(callUrl, xml);
			WechatPayResultBean resultBean = parseWechatPayResult(responseStr);
			System.out.println("调用微信的预支付接口 cost:"+ (System.currentTimeMillis() - time));
			if(resultBean.getPrepay_id() != null){
				String interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHAT;
				if(cost_balance != null && cost_balance >0){
					interface_type = UserConsumeInfoBean.INTERFACETYPE_WEICHATANDBALANCE;
				}
				
				//保存充值记录
				UserConsumeInfoBean consume = userConsumeInfoService.createConsumeInfo(phone,rmb, product_id, user.getId(), resultBean.getPrepay_id(), out_no,interface_type,eff_num,UserConsumeInfoBean.EVENT_TYPE_USER_ORDER);
				orderProductLogService.createOrderProductLogByUserConsumeInfo(consume, points, rmb,cost_balance, null);
				
				//返回界面需要支付的信息
				WechatPayInfoBean data = createPayInfo(appidStr,resultBean.getPrepay_id(),key);
				data.setOutTradeNo(out_no);
				writeJson(response, data);
				return null;
			}else{
				log.error("调用微信的预支付接口失败！ requseXML:"+xml+" respXML:"+ response);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		writeJson(response, "false");
		
		return null;
	}
	
	protected static String concatParam(Object... params){
		
		Arrays.sort(params);

		StringBuffer str = new StringBuffer();
		
		for(Object s : params){
			str.append("&" + s);
		}
		
		return str.substring(1);
	}


	private static String convertToXML(Object... params){
	Document doc = DocumentHelper.createDocument();
	doc.setXMLEncoding("UTF-8");
	Element root = doc.addElement("xml");
	
	for(Object p : params){
		System.out.println(p);
		String pa =String.valueOf(p);
		int index = pa.indexOf("=");
		String key = pa.substring(0,index);
		String value = pa.substring(index+1, pa.length());
		
		Element ele = root.addElement(key);
//		ele.addCDATA(value);
		ele.setText(value);
	}
	
	return doc.asXML();
}
	
	
	/**
	 * 微信支付的返回解析
	 * @param xml
	 * @return
	 * @throws DocumentException
	 */
	private static WechatPayResultBean parseWechatPayResult(String xml) throws DocumentException{
		WechatPayResultBean result = new WechatPayResultBean();
		Document doc = DocumentHelper.parseText(xml);
//		List<String> params = new ArrayList<String>();
//		result.setParams(params);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for(Element e: list){

			if(e.getName().equalsIgnoreCase("return_code")){
				result.setReturn_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("return_msg")){
				result.setReturn_msg(e.getText());
			}
			if(e.getName().equalsIgnoreCase("appid")){
				result.setAppid(e.getText());
			}
			if(e.getName().equalsIgnoreCase("mch_id")){
				result.setMch_id(e.getText());
			}
			if(e.getName().equalsIgnoreCase("device_info")){
				result.setDevice_info(e.getText());
			}
			if(e.getName().equalsIgnoreCase("nonce_str")){
				result.setNonce_str(e.getText());
			}
			if(e.getName().equalsIgnoreCase("sign")){
				result.setSign(e.getText());
			}
			if(e.getName().equalsIgnoreCase("result_code")){
				result.setResult_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code")){
				result.setErr_code(e.getText());
			}
			if(e.getName().equalsIgnoreCase("err_code_des")){
				result.setErr_code_des(e.getText());
			}
			if(e.getName().equalsIgnoreCase("trade_type")){
				result.setTrade_type(e.getText());
			}
			if(e.getName().equalsIgnoreCase("prepay_id")){
				result.setPrepay_id(e.getText());
			}
			if(e.getName().equalsIgnoreCase("code_url")){
				result.setCode_url(e.getText());
			}
			//支付通知扩展字段
			if(e.getName().equalsIgnoreCase("attach")){
				result.setAttach(e.getText());
			}if(e.getName().equalsIgnoreCase("bank_type")){
				result.setBank_type(e.getText());
			}if(e.getName().equalsIgnoreCase("cash_fee")){
				result.setCash_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("fee_type")){
				result.setFee_type(e.getText());
			}if(e.getName().equalsIgnoreCase("is_subscribe")){
				result.setIs_subscribe(e.getText());
			}if(e.getName().equalsIgnoreCase("openid")){
				result.setOpenid(e.getText());
			}if(e.getName().equalsIgnoreCase("out_trade_no")){
				result.setOut_trade_no(e.getText());
			}if(e.getName().equalsIgnoreCase("time_end")){
				result.setTime_end(e.getText());
			}if(e.getName().equalsIgnoreCase("total_fee")){
				result.setTotal_fee(e.getText());
			}if(e.getName().equalsIgnoreCase("transaction_id")){
				result.setTransaction_id(e.getText());
			}
		}
		return result;
	}
	
	
	/**
	 * 创建支付信息
	 * @param appId
	 * @param prepay_id
	 * @param signKey
	 * @return
	 */
	protected WechatPayInfoBean createPayInfo(String appId,String prepay_id,String signKey){
		WechatPayInfoBean bean = new WechatPayInfoBean();
		bean.setAppId(appId);
		bean.setNonceStr(UUID.randomUUID().toString().replaceAll("-", ""));
		bean.setPackages("prepay_id="+prepay_id);
		bean.setSignType("MD5");
		bean.setTimeStamp(""+(System.currentTimeMillis()/1000));
		
		String temp = "appId="+appId+"&nonceStr="+bean.getNonceStr()+"&package="+bean.getPackages()
				+ "&signType="+bean.getSignType()+"&timeStamp="+bean.getTimeStamp() + "&"+ signKey;
		String paySign = MD5Util.encode16(temp,"UTF-8").toUpperCase();
		bean.setPaySign(paySign);
		
		return bean;
	}
	
	
	
	
	
	
	//订购商品
	/**
	 * 用户订购商品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/supplier/userOrderProduct.do")
	public ModelAndView userOrderProduct(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String out_trade_code = request.getParameter("out_trade_code");
			String coupon_id = request.getParameter("coupon_id");							//优惠卷id
			String coupon_record_id = request.getParameter("coupon_record_id");				//优惠卷规则id
			
			String phone = request.getParameter("phone");				//电话号码
			String customer_id = request.getParameter("customer_id");				//商户id
			String openid = request.getParameter("openid");				//用户openid
			UserWechatBean userCheck = userWechatService.findUserWechatByOpenId(openid);
			
            int useCouponCount=supplierCouponService .queryUserCouponCount(Integer.parseInt (coupon_record_id),userCheck.getId(),Integer.parseInt(coupon_id));
            boolean checkProduct = supplierCouponService.checkCouponProduct( Integer.parseInt(customer_id), phone , Integer.parseInt(coupon_id));
            if(checkProduct){
                  if(useCouponCount <= SupplierCouponRecordBean.USE_NO_COUPON_COUNT ){
                        writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>" );
                         return null ;
                 }
           } else{
                 writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>" );
                  return null ;
           }


			
			
			if(out_trade_code != null){
				OrderProductLogBean orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
				//根据订单号从订单表中(ll_order_product_log)查询订单详细数据
				
				UserConsumeInfoBean consumeInfo = userConsumeInfoService.findUserConsumeInfo(out_trade_code);	
				//根据订单号从用户消费信息表中(ll_user_consume_info)查询客户消费记录
				
				//判断是否是预定的订单
				if(consumeInfo.getEff_num() != null && consumeInfo.getEff_num() >0){
					//扣除用户优惠卷
					supplierCouponService.changeCouponRecodeUserd(consumeInfo.getUser_id(),Integer.parseInt(coupon_id),Integer.parseInt(coupon_record_id));
					OrderProductEffBean condition = new OrderProductEffBean();
					condition.setType(OrderProductEffBean.TYPE_USER_EFF);
					condition.setEff_id(orderLog.getId());
					//有预定记录的，就是预定单，不去再次生成了
					List<OrderProductEffBean> effs = orderService.queryOrderProductEff(condition);
					if(effs.size() == 0){
						orderService.createOrderProductEffByOrderProductLog(orderLog,consumeInfo.getEff_num());
					}
					
				}else{
					//没有预约，直接订购
					orderLog = supplierCouponService.orderProductByUser(out_trade_code, null,Integer.parseInt(coupon_id),Integer.parseInt(coupon_record_id));
				}
				//处理打包商品订单，其余的商品同一走预订流程，生成预订订单
				orderService.createPackageOrderProduct(orderLog,consumeInfo);
				//重新获取对象
				orderLog = orderProductLogService.findOrderProductLogByCode(out_trade_code);
				//获取用户
				UserWechatBean user = userWechatService.findUserWechatById(orderLog.getUser_id());
				OrderProductLogBean neworderLog = orderProductLogService.dealOrderProductLuckyMoney(orderLog);
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("user", user);
//				model.put("luckymoney", neworderLog.getLucky_money());
				model.put("orderLog", neworderLog);
				return new ModelAndView(PAGE_USER_ORDER_PRODUCT_RESULT, model);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	

}