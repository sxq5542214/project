/**
 * 
 */
package com.yd.business.wechat.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
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
import com.yd.business.product.service.IProductTypeService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierCouponRecordBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.controller.SupplierCouponController;
import com.yd.business.supplier.service.ISupplierProductService;
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
import com.yd.business.wechat.bean.WechatWebAuthBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.Sign;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.business.wechat.util.WechatUtil;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.CookieUtil;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;
import com.yd.util.WebUtil;

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
	 * 客户端查询商户商品
	 */
	@Deprecated
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
	 * 	所有微信界面跳转的分发服务
	 */
	@RequestMapping("/wechat/user/toReopenTips.html")
	public ModelAndView toReopenTips(HttpServletRequest request,HttpServletResponse response){
		
		writeJson(response, "<script>alert('登录失效，请通过公众号重新打开界面！')</script>");
		return null;
	}
	/**
	 * 	所有微信界面跳转的分发服务
	 */
	@RequestMapping("/wechat/user/toDistributeControll.do")
	public ModelAndView toDistributeControll(HttpServletRequest request,HttpServletResponse response){
		try{
			String openid = request.getParameter("openid");
			String code = request.getParameter("code");
			String conName = request.getParameter("conName");
			String fromOpenid = request.getParameter("fromOpenid");
			Integer parentId = null;
			if(StringUtil.isNotNull(fromOpenid)) {
				UserWechatBean parent = userWechatService.findUserWechatByOpenId(fromOpenid);
				parentId = parent == null ? null:parent.getId();
			}
			
			WechatOriginalInfoBean original = wechatOriginalInfoService.getOriginalInfoByServerDomain(request);
			String originalid = original.getOriginalid();
			if(StringUtil.isNull(openid)) {
				openid = (String)WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_USER_OPENID);
			}

			Map<String,String> param = getRequestParamsMap(request);
			param.remove("code");
			String paramStr = WebUtil.concatParam( param);
			if(StringUtil.isNull(openid) && StringUtil.isNull(code)){
				// 没有缓存，也没有传code过来，则跳转至微信授权
				String enCodeUrl = URLEncoder.encode(original.getServer_url() +"wechat/user/toDistributeControll.do?"+ paramStr , "utf-8");
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				return null;
			}else if(StringUtil.isNull(openid)){
				// 微信认证
				WechatWebAuthBean auth = wechatUserService.getOpenIdByWebAuthCode(code, originalid);
				//好友信息，创建用户及好友关系
				if(StringUtil.isNotNull(auth.getAccess_token())){
					//当前用户openid 添加到session
					WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_USER_OPENID,auth.getOpenid());
					wechatUserService.createWechatUserByWebAuth(auth.getOpenid(), parentId,  WechatConstant.TICKET_SENCE_CODE_WXMENU, null, originalid , auth.getAccess_token());
					openid = auth.getOpenid();
				}else{ // 没有accessstoken 则重新访问
					String enCodeUrl = URLEncoder.encode(original.getServer_url() +"wechat/user/toDistributeControll.do?"+ paramStr , "utf-8");
					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
					return null;
				}
			}
			param.put("openid", openid);
System.out.println("============================================toDistributeControll: "+ conName +"  " + openid);
			WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_USER_OPENID,openid);
			return new ModelAndView("/"+conName.replaceAll("\\.", "/")+".html?openid="+ StringUtil.convertNull(openid),param);
			
		}catch (Exception e) {
			log.error(e,e);
			writeJson(response, "服务出错，请联系客服人员检查");
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
	
	
}
