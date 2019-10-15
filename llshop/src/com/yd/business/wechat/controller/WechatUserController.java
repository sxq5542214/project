/**
 * 
 */
package com.yd.business.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpRequest;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityOlympicGuessBean;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.controller.ActivityController;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.comment.bean.WechatCommentBean;
import com.yd.business.comment.contorller.WechatCommentReplyContorller;
import com.yd.business.comment.service.IWechatCommentReplyService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.impl.CustomerServiceImpl;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.bean.ShopOrderInfoBean;
import com.yd.business.order.bean.ShopOrderProductBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.controller.SupplierCouponController;
import com.yd.business.user.bean.UserInfoCenterPageBean;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserSignBean;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.controller.UserController;
import com.yd.business.user.controller.UserSupplierProductController;
import com.yd.business.user.service.IUserSignService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.SignServerBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.Sign;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.business.wechat.util.WechatUtil;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.CookieUtil;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class WechatUserController extends BaseController {
	
	@Resource
	private IWechatService wechatUserService;
	@Resource
	protected IWechatService wechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	@Resource
	protected IUserWechatService userWechatService;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IUserSignService userSignService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IProductTypeService productTypeService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IWechatCommentReplyService wechatCommentReplyService;
	@Resource
	private UserSupplierProductController userSupplierProductController;
	@Resource
	private SupplierCouponController supplierCouponController;
	
	
	@RequestMapping("/wechat/user/handle.do")
	public String handle(HttpServletRequest request,HttpServletResponse response){
		
		String originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
		
		setResponseCharSet(response);
		log.debug("wechat/user/handleSupplier.do  method:" + request.getMethod());
		if(request.getMethod().equals(METHOD_GET)){
			//微信验证服务接口
			return checkServerSign(request,response,originalid);
			
		}else{
			//微信接口调用入口
			InputStream inputStream = null;
			String resultXml = null;
			try {
				
				// 从request中取得输入流  
				inputStream = request.getInputStream();
		        // 读取输入流  
		        SAXReader reader = new SAXReader();  
		        Document document = reader.read(inputStream);  
				log.debug("request doc==============\n"+document.asXML());
				
				//得到返回值，如果有，立刻返回消息
				BaseMessage result = wechatUserService.handlerWechatMessage(document);
		        if(result != null){
		        	resultXml = WechatUtil.MessageToXml(result);
		        	log.debug("response doc==============\n"+resultXml);
		        	if(resultXml != null){
						endResponse(response.getWriter(), resultXml);
					}
		        }else{
		        	endResponse(response.getWriter(), "");
		        }
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e,e);
			}finally{
				if(inputStream != null)
				{
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
						log.error(e,e);
					}
			        inputStream = null;
				}
			}
			
			return null;
		}
	}
	
	@RequestMapping("/wechat/user/toInviteFriends.do")
	public ModelAndView toInviteFriends(HttpServletRequest request,HttpServletResponse response){

//		String code = request.getParameter("code"); //原先是从微信直接跳转过来，现在多了个share界面，会直接传openId过来
		try{
			String openid = request.getParameter("openId");
			String userSenceLogId = request.getParameter("userSenceLogId");
			if(StringUtil.isNotNull(userSenceLogId) ){
				userWechatService.readUserSenceLog(Integer.parseInt(userSenceLogId));
			}

			String originalid = null;
			if(StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			
			Map<String,Object> map = new HashMap<String, Object>();
			
			if(user != null){
				
				UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(user.getOpenid(), WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 1);
				map.put("user", user);
				map.put("ticket", ticket);
				return new ModelAndView("/page/hongbao/inviteFriends.jsp",map);
			}else{
				log.error("wechat/toInviteFriends  no found User openId:"+openid );
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/wechat/user/toInviteFriendsShare.do")
	public ModelAndView toInviteFriendsShare(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			
			if(user == null ){
				return null;
	//			不创建用户，直接返回空
	//			try {
	//				log.info("openId:"+openId+" 的用户未找到，新创建。。。");
	//				wechatService.createWechatUser(openId, null,null,null);
	////				wechatService.getBWCUserByOpenId(openId);
	//				user = userWechatService.findUserWechatByOpenId(openId);
	//				
	//			} catch (Exception e) {
	//				e.printStackTrace();
	//				log.error(e,e);
	//			}
			}
			if(user != null){
//				不用了 16.08.18
//				Integer userSenceLogId = userWechatService.createUserSenceLog(openId, WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 0);
				
				UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(user.getOpenid(), WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 0);
				model.put("user", user);
				model.put("ticket", ticket);
//				model.put("userSenceLogId", userSenceLogId);
			}
			return new ModelAndView("/page/hongbao/inviteFriendsShare.jsp",model);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 微信用户摇一摇活动， 16.8月后已不用
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wechat/user/toShakeActivity.do")
	public ModelAndView toShakeActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			
			ActivityUserRelationBean relation = activityService.findUserRelation(ActivityConfigBean.CODE_FIRSTSHAKE_ACTIVITY, openid);
			
			//已参加过
			if(relation != null && relation.getStatus() == ActivityUserRelationBean.STATUS_USED ){
				
				
				model.put("user", user);
				model.put("relation", relation);
				return new ModelAndView("/page/user/activity/firstShake/activityJoined.jsp", model);
			}
			//未参加过
			if(StringUtil.isNotNull(openid) ){
				
				model.put("openid", openid);
				return new ModelAndView("/page/user/activity/firstShake/shakeActivity.jsp", model);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("/wechat/user/updateUserSenceShareInfo.do")
	public ModelAndView updateUserSenceShareInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			String userSenceLogId = request.getParameter("userSenceLogId");
			String shareType = request.getParameter("shareType");
			
			userWechatService.shareUserSenceLog(Integer.parseInt(userSenceLogId), Integer.parseInt(shareType));
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	@RequestMapping("**/wechat/user/toAdminUserSSOPage.do")
	public ModelAndView toAdminUserSSOPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			String openid = request.getParameter("openid");
			String forward = request.getParameter("forward");
			List<UserWechatBean> adminUser = userWechatService.queryMGRAdminUser();
			boolean isAdmin = false;
			for(UserWechatBean user : adminUser){
				
				if(user.getOpenid().equals(openid)){
					
					CustomerBean bean = new CustomerBean();
					bean.setId(user.getId());
					bean.setName(user.getNick_name());
					
					WebContext.getHttpSession().setAttribute(CustomerServiceImpl._CURRENT_USER, bean);
					WebContext.getHttpSession().setAttribute(CustomerServiceImpl._CURRENT_USER_TYPE, "1");
					bean.setSession_id(WebContext.getHttpSession().getId());
					isAdmin = true;
					break;
				}
			}
			
			if(isAdmin){
				return new ModelAndView(forward);
			}
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	/**
	 * 微信用户摇一摇活动， 16.8月后已不用
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wechat/user/toOlympicActivity.do")
	public ModelAndView toOlympicActivity(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			String fromOpenid = request.getParameter("fromOpenid");
			String share_time_ms = request.getParameter("share_time_ms");
			boolean hasUser = true;
			if(StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				String originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				String openId = wechatService.getOpenId(code,originalid);
			}
			if(openid == null && fromOpenid == null){
				hasUser = false;
			}
			if(fromOpenid == null){//系统定义的用户
				fromOpenid = "olympicActivityUser";
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			UserQrCodeBean ticket = null;
			Integer winNum = 0;

			if(user == null){
				user = userWechatService.findUserWechatByOpenId(fromOpenid);
				
				ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_ACTIVITY, 4);
				
			}
			List<ActivityOlympicGuessBean> list = activityService.queryUserOlympicGuessInfo(user.getId());
			for(ActivityOlympicGuessBean bean : list){
				if(bean.getStatus() == ActivityOlympicGuessBean.STATUS_SUCCESS 
						|| bean.getStatus() == ActivityOlympicGuessBean.STATUS_WAIT){
					winNum++;
				}
			}
			
			//记录用户阅读信息
			userWechatService.readUserSenceLog(fromOpenid, null, share_time_ms);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			model.put("hasUser", hasUser);
			model.put("user", user);
			model.put("ticket", ticket);
			model.put("winNum", winNum);
			model.put("showQRCode", openid == null);
			return new ModelAndView(ActivityController.PAGE_USER_ACTIVITY_OLYMPIC, model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	/**
	 * 用户购买平台流量入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wechat/user/queryPlatformProduct.do")
	public ModelAndView queryPlatformProduct(HttpServletRequest request,HttpServletResponse response){
		try{

			Map<String,Object> model = new HashMap<String,Object>();
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			//有父用户
			if(user != null ){
			
//				List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
				model.put("user", user);
				model.put("customer_id",  CustomerBean.ID_PLATFROM);
				model.put("openid", openid);
				//调用数据库查询是否有历史的号码,获得直接在页面展示;
				OrderProductLogBean beanout = null;
				beanout=orderProductLogService.findOrderProductLogByUseridDesc(user.getId());
				if(!StringUtil.isNull(beanout)){
						model.put("order_account",beanout.getOrder_account());
					
				}
//				ActivityUserRelationBean relation = activityService.findUserRelation(ActivityConfigBean.CODE_FIRSTSHAKE_ACTIVITY, openId);
				
//				//必须没有订购过，且活动已分享到朋友才到  首单6折页面，    ---活动结束 16.8.17
//				if(StringUtil.isNull(user.getLast_order_time()) && user.getShare_type() != null ){
//					return new ModelAndView(UserController.PAGE_FIRST_ORDERPRODUCT, model);
//				}
				
				return new ModelAndView(UserController.PAGE_ORDERPRODUCT, model);
			}else{
				//没父用户,直接到404
//				log.debug("openId:"+openId +", not has partner,return 404...");
//				return new ModelAndView("/page/aaaa.html");
				
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	/**
	 * 客户端查询商户商品
	 */
	@RequestMapping("/wechat/user/queryPlatformProductByShop.do")
	public ModelAndView queryPlatformProductByShop(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
//			WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_USER_OPENID, openid);
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			
			
			SupplierProductBean bean = new SupplierProductBean();
			bean.setCustomer_id(CustomerBean.ID_PLATFROM);
			bean.setStatus(SupplierProductBean.STATUS_UP);
			List<SupplierProductBean> listProduct= supplierProductService.listSupplierProduct(bean );
			
//			ProductTypeBean type = new ProductTypeBean();
//			type.setType(ProductTypeBean.TYPE_CLS);
			List<ProductTypeBean> typeList = productTypeService.listProductType(null );
			
			
			
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("listProduct", listProduct);
			map.put("adminid", 1);
			map.put("customerid", CustomerBean.ID_PLATFROM);
			map.put("user", user);
			map.put("typeList", typeList);
			
			return new ModelAndView("/page/shop/default_shop.jsp", map);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	
	
	
	/**
	 * 买一赠一活动入口 
	 */
	@RequestMapping("/wechat/user/queryBuyGiveOneProduct.do")
	public ModelAndView queryBuyGiveOneProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			Map<String,Object> model = new HashMap<String,Object>();
			String originalid = null;
			String openid = request.getParameter("openid");
			if(StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			//有父用户
			if(user != null ){
			
//				List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
				model.put("user", user);
				model.put("customer_id", CustomerBean.ID_BUYGIVEONE);
				model.put("openid", openid);
				//调用数据库查询是否有历史的号码,获得直接在页面展示;
				OrderProductLogBean beanout = null;
				beanout=orderProductLogService.findOrderProductLogByUseridDesc(user.getId());
				if(!StringUtil.isNull(beanout)){
						model.put("order_account",beanout.getOrder_account());
					
				}
				return new ModelAndView(UserController.PAGE_BUYGIVEONE_ORDERPRODUCT, model);
			}else{
//				//没父用户,直接到404
//				log.debug("openId:"+openId +", not has partner,return 404...");
//				return new ModelAndView("/page/aaaa.html");
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	@RequestMapping("**/wechat/user/toUserInfoCenter.do")
	public ModelAndView toUserInfoCenter(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			
			
			if(user != null ){
				UserInfoCenterPageBean userInfoPage = userWechatService.queryActivityFriendLevelCount(user.getId());
				userInfoPage.setUserWechat(user);
				boolean isShop = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_SYSTEM_IS_SHOP_ORDER);

				Map<String, Object> model = new HashMap<String, Object>();
				model.put("userInfoPage", userInfoPage);
				model.put("isShop", isShop);
				return new ModelAndView(UserController.PAGE_USERINFOCENTER, model);
			}else{
				writeJson(response, "请先关注公众号！如已关注请重新进入");
			}
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}

	@RequestMapping("**/wechat/user/toUserSignPage.do")
	public ModelAndView toUserSignPage(HttpServletRequest request,HttpServletResponse response){
		
		try {
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				writeJson(response, " <script>alert('请先关注公众号！如果已关注，请重新打开界面 '); </script> ");
				return null;
			}
			UserSignBean sign = userSignService.whetherSign(openid);

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openid", openid);
			model.put("sign", sign);
			model.put("user", user);
			return new ModelAndView(UserController.PAGE_USERSIGN, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/wechat/user/toUserSupplierEventInfoPage.do")
	public ModelAndView toUserSupplierEventInfoPage(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			String eventId = request.getParameter("eventId");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("openid", openid);
			model.put("eventId", eventId);
			return new ModelAndView("redirect:supplierEvent/userRead/toMyEventPage.do",model);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

//	@RequestMapping("**/wechat/user/toNewUserSupplierEventInfoPage.do")
//	public ModelAndView toNewUserSupplierEventInfoPage(HttpServletRequest request,HttpServletResponse response){
//		
//		try{
//			String openid = request.getParameter("openid");
//			String eventId = request.getParameter("eventId");
//			if(StringUtil.isNull(openid)){
//				String code = request.getParameter("code");
//				openid = wechatService.getOpenId(code);
//			}
//			
//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("openid", openid);
//			model.put("eventId", eventId);
//			String url = BaseContext.getServerUrl();
//			
//			return new ModelAndView(url+"supplierEvent/userRead/toMyEventPage.do?openid="+openid+"&eventId="+eventId ,model);
//		}catch (Exception e) {
//			log.error(e, e);
//		}
//		return null;
//	}
	
	@RequestMapping("**/wechat/user/queryOrderProductLog.do")
	public ModelAndView queryOrderProductLog(HttpServletRequest request,HttpServletResponse response){
		
		try{

			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			if(user != null){
			
				List<OrderProductLogBean> list = orderProductLogService.queryOrderProductLogByUserId(user.getId());
				
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("list", list);
				return new ModelAndView(UserController.PAGE_ORDERPRODUCTLOG, model);
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 检查用户是否存在，不存在 就创建，或者 不创建
	 * @param user
	 * @param openid
	 * @return
	 * @throws Exception
	 */
	private UserWechatBean checkUserExists(UserWechatBean user,String openid,String originalid) throws Exception{
		
		if(openid == null){
			return null;
		}
		if(user == null){
			wechatService.createWechatUser(openid, null, null, null,originalid);
			user = userWechatService.findUserWechatByOpenId(openid);
		}
//		if(user.getParentid() == null){
//			user = null;
//		}
		
		return user;
	}

	/**
	 * 7月流量活动，送1G，移动提供的，借来用
	 */
	@RequestMapping("**/wechat/user/liuliang1GActivity.htm")
	public ModelAndView liuliang1GActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String code = request.getParameter("code");
			String fromOpenid = request.getParameter("fromOpenid");
			String share_time_ms = request.getParameter("share_time_ms");
			String originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			String openId = wechatService.getOpenId(code,originalid);
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			user = checkUserExists(user,openId,originalid);
//			UserWechatBean fromUser = userWechatService.findUserWechatByOpenId(fromOpenid);
			
			userWechatService.readUserSenceLog(fromOpenid, null, share_time_ms);
			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_ACTIVITY, WechatConstant.TICKET_SENCE_ID_ACTIVITY_LIULIANG1G);

			
			model.put("user", user);
			model.put("ticket", ticket);
//			model.put("fromUser", fromUser);
				
		}catch (Exception e) {
			log.error(e,e);
		}
		return new ModelAndView(UserController.PAGE_LIULIANG1GACTIVITY, model);
	}
	

	/**
	 * 8月流量活动，免单活动
	 */
	@RequestMapping("**/wechat/user/toFreeOrderActivity.do")
	public ModelAndView toFreeOrderActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String fromOpenid = request.getParameter("fromOpenid");
			String share_time_ms = request.getParameter("share_time_ms");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(fromOpenid);

			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_ACTIVITY, WechatConstant.TICKET_SENCE_ID_ACTIVITY_FREEORDER);
			
			userWechatService.readUserSenceLog(fromOpenid, null, share_time_ms);
			
			model.put("user", user);
			model.put("ticket", ticket);
				
		}catch (Exception e) {
			log.error(e,e);
		}
		return new ModelAndView(UserController.PAGE_ACTIVITY_FREE_ORDER, model);
	}
	

	/**
	 * 8月流量活动，送volte免费通话，移动提供的
	 */
	@RequestMapping("**/wechat/user/volteCallActivity.htm")
	public ModelAndView volteCallActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String code = request.getParameter("code");
			String fromOpenid = request.getParameter("fromOpenid");
			String originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			String openId = wechatService.getOpenId(code,originalid);
//			openId = "123";
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			user = checkUserExists(user,openId,originalid);
//			UserWechatBean fromUser = userWechatService.findUserWechatByOpenId(fromOpenid);

			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_ACTIVITY, WechatConstant.TICKET_SENCE_ID_ACTIVITY_VOLTE);

			model.put("user", user);
			model.put("ticket", ticket);
//			model.put("fromUser", fromUser);

		}catch (Exception e) {
			log.error(e,e);
		}
		return new ModelAndView(UserController.PAGE_ACTIVITY_VOLTECALL, model);
	}
	

	/**
	 * 10月流量活动，送1G省内流量，移动提供的
	 */
	@RequestMapping("**/wechat/user/qiang1GActivity.htm")
	public ModelAndView qiang1GActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
//			WechatOriginalInfoBean original = getOriginalInfoByServerDomain(request);
			
//			if(original == null){
//				original = wechatService.f(request)
//			}
			
			
			String share_time_ms = request.getParameter("share_time_ms");
			String openid = request.getParameter("openid");
//			if(StringUtil.isNull(openid)){
//				String code = request.getParameter("code");
//				openid = wechatService.getOpenId(code,original.getOriginalid());
//			}
			String fromOpenid = request.getParameter("fromOpenid");
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			model.put("user", user);
			if(fromOpenid != null){
				
				userWechatService.readUserSenceLog(fromOpenid, null, share_time_ms);
				UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(fromOpenid, WechatConstant.TICKET_SENCE_CODE_ACTIVITY, WechatConstant.TICKET_SENCE_ID_ACTIVITY_QIANG1G);
				
				model.put("ticket", ticket);
			}
			//坏人直接就看不到
			if(user != null && user.getParentid() == null){
				
				writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
				return null;
			}
			
			return new ModelAndView(UserController.PAGE_ACTIVITY_QIANG1G, model);

		}catch (Exception e) {
			log.error(e,e);
			writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
		}
		return null;
	}
	
	/**
	 * 8月流量活动，刮刮乐
	 */
	@RequestMapping("**/wechat/user/toScratchActivity.do")
	public ModelAndView toScratchActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);

			ActivityUserRelationBean relation = activityService.joinFirstShakeActivity(user );

			model.put("user", user);
			model.put("relation", relation);
//			model.put("fromUser", fromUser);
			return new ModelAndView(UserController.PAGE_ACTIVITY_SCRATCHACTIVITY, model);

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	/**
	 * 微信JS-SDK签名.
	 * @param json
	 * @return
	 * @throws Exception 
	 * @throws JSONException
	 */
	@RequestMapping("**/wechat/user/checkJsSign.do")
	public ModelAndView checkJsSign(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			String json = request.getParameter("json");
			JSONObject jso = null;
			jso = new JSONObject(json);
			String url = jso.optString("url");
			
			WechatOriginalInfoBean info = wechatOriginalInfoService.getOriginalInfoByServerDomain(request);
			if(info == null){
				log.error("not found WechatOriginalInfo by reqeust:"+ request.getRequestURI() +"  , try find the first...");
				info = wechatOriginalInfoService.queryFirstWechatOriginalInfo();
			}
			
			String originalid = info.getOriginalid();
					
			String jsapi_ticket = null;
			jsapi_ticket = wechatService.getJsapiTicket(originalid);
			List<Map<String,String>> list = new ArrayList<Map<String,String>>();
			Map<String, String> ret = Sign.sign(jsapi_ticket, url);
			
			String appid = info.getAppid();
			ret.put("appid", appid);
			list.add(ret);
			writeJson(response, list);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 * 验证服务签名
	 * @param request
	 * @param response
	 * @return
	 */
//	@RequestMapping("/wechat/checkServerSign.do")
	private String checkServerSign(HttpServletRequest request,HttpServletResponse response,String originalid){
//		String signature=request.getParameter("signature");
//		String timestamp=request.getParameter("timestamp");
//		String nonce=request.getParameter("nonce");
//		String echostr=request.getParameter("echostr");
		
//		log.debug("requestMap:" + request.getParameterMap());
		SignServerBean bean = new SignServerBean();
		try {
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			String value = wechatService.validateServerSign(bean,originalid);
			
			endResponse(response.getWriter(), value);
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("/wechat/user/execWechatWaitSend.do")
	public ModelAndView execWechatWaitSend(HttpServletRequest request,HttpServletResponse response){
		try {
			taskExecutor.execute(new BaseRunable() {
				@Override
				public void runMethod() {
					wechatUserService.execSendMessageToUserList();
					
				}
			});
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	/**
	 * 活动入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/wechat/user/showActivityList.do")
	public ModelAndView showActivityList(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			if(!StringUtil.isNull(user)){
				Map<String, Object> model = new HashMap<String, Object>();
				model.put("openid", user.getOpenid());
				
				return new ModelAndView("redirect:"+UserController.PAGE_ACTIVYTY_GETLIST, model);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/wechat/user/queryWechatUserCommentInfo.do")
	public ModelAndView queryWechatUserCommentInfo(HttpServletRequest request,HttpServletResponse response){
		
		try{

			String openid = request.getParameter("openid");
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
				openid = wechatService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user = checkUserExists(user,openid,originalid);
			if(user != null){
				if(wechatCommentReplyService.isShowCommentList(openid)){
					Map<String, Object> model = new HashMap<String, Object>();
					model.put("openid", user.getOpenid());
					return new ModelAndView(WechatCommentReplyContorller.PAGE_COMMENT_INFO_INDEX, model);
				}else{
					return null;
				}
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	@RequestMapping("/wechat/user/toDefaultPlatformSupplierProduct.do")
	public ModelAndView toDefaultPlatformSupplierProduct(HttpServletRequest request,HttpServletResponse response){

		String openid = request.getParameter("openid");
		String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
		String originalid = null;
		//先查缓存
		if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
			String code = request.getParameter("code");
			originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			openid = wechatService.getOpenId(code,originalid);
			request.getSession().setAttribute("cachedOpenid", openid);
		}else if(StringUtil.isNotNull(cachedOpenid)){
			openid = cachedOpenid;
		}
		//获取 参数解析后，访问用户的控制层
		try {
			response.sendRedirect(request.getServletContext().getContextPath()+"/user/supplier/queryPlatformSupplierProduct.do?openid="+openid);
		} catch (IOException e) {
			log.error(e, e);
		}
		return null;
//		这种方式用户刷新会有code 重复问题，改为跳转链接
//		return userSupplierProductController.queryPlatformSupplierProduct(null, openid);
	}
	  

	/**
	 * 查询目前配置的优惠卷
	 */
	@RequestMapping("/wechat/user/toCouponCenterPage.do")
	public ModelAndView toCouponCenterPage(HttpServletRequest request,HttpServletResponse response){
		String openid = request.getParameter("openid");
		//微信公众号菜单直接打开界面，要获取CODE为OPENID
		String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
		String originalid = null;
		//先查缓存
		if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
			String code = request.getParameter("code");
			originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			openid = wechatService.getOpenId(code,originalid);
			request.getSession().setAttribute("cachedOpenid", openid);
		}else if(StringUtil.isNotNull(cachedOpenid)){
			openid = cachedOpenid;
		}
		return supplierCouponController.toUserCouponCenterPage(openid);
	}
	
	/**
	 *  打开商城分类界面
	 */
	@RequestMapping("/wechat/user/toSupplierProductCategoryPage.do")
	public ModelAndView toSupplierProductCategoryPage(HttpServletRequest request,HttpServletResponse response){

		String openid = request.getParameter("openid");
		String sid = request.getParameter("sid");
		String originalid = null;
		//微信公众号菜单直接打开界面，要获取CODE为OPENID
		String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
		//先查缓存
		if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
			String code = request.getParameter("code");
			originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			openid = wechatService.getOpenId(code,originalid);
			request.getSession().setAttribute("cachedOpenid", openid);
		}else if(StringUtil.isNotNull(cachedOpenid)){
			openid = cachedOpenid;
		}
		
		return userSupplierProductController.toSupplierProductCategoryPage(sid, openid);
	}
	
	
	/**
	 * 查询自己拥有的优惠卷,跳到转查询自己优惠卷界面
	 */
	@RequestMapping("/wechat/user/toUserCouponPage.do")
	public ModelAndView toUserCouponPage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String openid = request.getParameter("openid");
		//微信公众号菜单直接打开界面，要获取CODE为OPENID
		String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
		String originalid = null;
		//先查缓存
		if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
			String code = request.getParameter("code");
			originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
			openid = wechatService.getOpenId(code,originalid);
			request.getSession().setAttribute("cachedOpenid", openid);
		}else if(StringUtil.isNotNull(cachedOpenid)){
			openid = cachedOpenid;
		}
		return supplierCouponController.toMycouponPage(openid);
	}
	
}
