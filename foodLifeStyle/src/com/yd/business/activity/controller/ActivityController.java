/**
 * 
 */
package com.yd.business.activity.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityInstanceBean;
import com.yd.business.activity.bean.ActivityOlympicGuessBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityPrizeRelationBean;
import com.yd.business.activity.bean.ActivityRemindBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.bean.ActivityUserRelationBean;
import com.yd.business.activity.bean.ActivityWinHisBean;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityPrizeService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderProductLogService;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.SupplierEventBean;
import com.yd.business.supplier.bean.SupplierEventCodeBean;
import com.yd.business.supplier.service.ISupplierEventService;
import com.yd.business.user.bean.UserQrCodeBean;
import com.yd.business.user.bean.UserSenceLog;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.controller.UserController;
import com.yd.business.user.service.IUserCommissionPointsService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.bean.WechatWebAuthBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ActivityController extends BaseController {
	
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivitConfigService activityConfigService;
	@Resource
	private IActivityPrizeService activityPrizeService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	protected ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IUserCommissionPointsService userCommissionPointsService;
	@Resource
	private IOrderProductLogService orderProductLogService;
	@Resource
	private ISupplierEventService supplierEventService;
	@Resource
	private IWechatService wechatUserService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	

	public static final String PAGE_USER_ACTIVITY_FREECUTACTIVITY = "/page/user/activity/freeCutActivity/freeCutActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_FREECUTHELPACTIVITY = "/page/user/activity/freeCutActivity/freeCutHelpActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_FREECUTHELFRIENDCIRCLEPACTIVITY = "/page/user/activity/freeCutActivity/freeCutHelpFriendCircleActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_TURNTABLEACTIVITY = "/page/user/activity/turntable1/zhuanpan.jsp";

	public static final String PAGE_USER_ACTIVITY_LISTHOME = "/page/user/activity/signActivity/index.jsp";
	public static final String PAGE_USER_ACTIVITY_HOURLYACTIVITY = "/page/user/activity/signActivity/hourlyActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_WINNERLIST = "/page/user/activity/signActivity/winners.jsp";
	public static final String PAGE_USER_ACTIVITY_JOIN_FAIL = "/page/user/activity/signActivity/join-fail.jsp";
	public static final String PAGE_USER_ACTIVITY_JOIN_SUCCESS = "/page/user/activity/signActivity/join-success.jsp";
	
	public static final String USER_ACTIVITY_FUTURE = "future";
	public static final String USER_ACTIVITY_NOW = "now";
	public static final String USER_ACTIVITY_HISTORY = "history";

	/**
	 * 查询用户中奖信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/activity/queryActWin.do")
	public ModelAndView queryActWin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		String actName = request.getParameter("activity_name");
		String userName = request.getParameter("user_name");
		Integer category = NumberUtil.toInt(request.getParameter("category"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date beginWinDate = NumberUtil.toDate(request.getParameter("beginDate"), sdf);
		Date endWinDate = NumberUtil.toDate(request.getParameter("endDate"), sdf);
		Integer status = NumberUtil.toInt(request.getParameter("status"));
		String arrStatus = request.getParameter("arrstatus");
		Integer nowpage = NumberUtil.toInt(request.getParameter("nowpage"));
		ActivityWinHisBean bean = new ActivityWinHisBean();
		bean.setActivity_name(actName);
		bean.setUser_name(userName);
		bean.setCategory(category);
		bean.setBeginWinTime(beginWinDate);
		bean.setEndWinTime(endWinDate);
		bean.setStatus(status);
		bean.setArrstatus(arrStatus);
		List<ActivityWinHisBean> list = activityService.queryActivityWinHis(bean);
		PageinationData pd = new PageinationData().fenye(list, nowpage, 15);
		writeJson(response, pd);
		return null;
	}
	
	

	
	/**
	 * 获取签单活动的列表,活动配置表activity_type为3
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/activity/user/getSignActivityList.do")
	public ModelAndView getSignActivityList(HttpServletRequest request,HttpServletResponse response){
		try{
			//获取符合条件的活动列表
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			List<ActivityConfigBean> activityConfigBeanList = activityConfigService.queryActivityConfigByParam(ActivityConfigBean.ACTIVITY_STATUS_ENABLE,user);
			List<ActivityConfigBean> futureActivityConfigBeanList = activityConfigService.returnActivityConfigByDate(activityConfigBeanList,USER_ACTIVITY_FUTURE);
			List<ActivityConfigBean> nowActivityConfigBeanList = activityConfigService.returnActivityConfigByDate(activityConfigBeanList,USER_ACTIVITY_NOW);
			List<ActivityConfigBean> historyActivityConfigBeanList = activityConfigService.returnActivityConfigByDate(activityConfigBeanList,USER_ACTIVITY_HISTORY);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("futureActivityConfigBeanList", futureActivityConfigBeanList);
			model.put("nowActivityConfigBeanList", nowActivityConfigBeanList);
			model.put("historyActivityConfigBeanList", historyActivityConfigBeanList);
			model.put("user", user);
			return new ModelAndView(PAGE_USER_ACTIVITY_LISTHOME, model);
		}catch(Exception e){
			log.error(e, e);
			writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");
		}
		return null;
	}
	
	@RequestMapping("**/activity/user/showActivityInfo.do")
	public ModelAndView showActivityInfo(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String openid = request.getParameter("openid");
			String activity_config_code = request.getParameter("code");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//用户参与活动
			ActivityConfigBean activityConfigBean = activityService.findEnableActivityConfigByActivityIdAndCode(null,activity_config_code);//(activity_config_code);
			if(!StringUtil.isNull(activityConfigBean)){
				//初始化活动实例
				activityService.initActivity(activityConfigBean,user.getId());
				//返回活动实例列表(最多返回4条)
				List<ActivityInstanceBean> returnInstanceList = activityService.findActivityInstanceList(user,activityConfigBean,4);
				
				//参数指定是否需要展示活动详情页
				int is_show_detail = 1;
				if(!StringUtil.isNull(activityConfigBean.getIs_show_detail())){
					is_show_detail = activityConfigBean.getIs_show_detail();
				}
				if(ActivityConfigBean.ACTIVITY_IS_SHOW_DETAIL_NO == is_show_detail && returnInstanceList.size() > 0){
					model.put("instanceActivityId", returnInstanceList.get(0).getId());
					model.put("openid", user.getOpenid());
					model.put("code", returnInstanceList.get(0).getCode());
					return new ModelAndView("redirect:activity/user/dealUserJoinActivity.do", model);
				}else{
					model.put("user", user);
					model.put("returnInstanceList", returnInstanceList);
					model.put("activityConfigBean", activityConfigBean);
					return new ModelAndView(PAGE_USER_ACTIVITY_HOURLYACTIVITY, model);
				}
			}else{
				writeJson(response, "<script>alert('获取活动失败');</script>");
				return getSignActivityList(request,response);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 处理用户参与活动，并记录参与信息
	 */
	@RequestMapping("**/activity/user/dealUserJoinActivity.do")
	public ModelAndView dealUserJoinActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String openid = request.getParameter("openid");
			String instanceActivityId = request.getParameter("instanceActivityId");
			String code = request.getParameter("code");
			ActivityInstanceBean bean = activityService.findActivityInstanceByIdAndCode(instanceActivityId,code);
			//用户参与活动
			ActivityConfigBean activityConfigBean = activityService.findEnableActivityConfigByActivityIdAndCode(bean.getActivity_id(),bean.getCode());//(activity_config_code);
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			ActivityPrize prize = activityService.recordUserInstanceActivityInfo(user, bean);
			//处理用户参与后返回的url
			if(StringUtil.isNotNull(activityConfigBean.getActivity_jump_url()) && activityConfigBean.getActivity_jump_url().contains("#openid#")){
				activityConfigBean.setActivity_jump_url(activityConfigBean.getActivity_jump_url().replaceAll("#openid#", openid));
			}
			model.put("user", user);
			model.put("activityConfigBean", activityConfigBean);
			model.put("prize", prize);
			
			
			model.put("openid", user.getOpenid());
			model.put("code", activityConfigBean.getCode());
			model.put("instanceActivityId", instanceActivityId);
			
			String errorCode = prize.getErrorCode();
			if(ActivityPrize.ERROR_CODE_FAIL.equals(errorCode)){
				return new ModelAndView(PAGE_USER_ACTIVITY_JOIN_FAIL, model);
			}else{
				return new ModelAndView("redirect:"+activityConfigBean.getActivity_jump_url(), model);
			}
		} catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 展示中奖信息，
	 * @return
	 */
	@RequestMapping("**/activity/user/showWinnerResults.do")
	public ModelAndView showWinnerResults(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		List<ActivityUserRelationBean> relationBean = activityService.findUserRelationByPage(0,20);
		model.put("relationBean", relationBean);
		return new ModelAndView(PAGE_USER_ACTIVITY_WINNERLIST, model);
	}
	
	
	

	
	/**
	 * 秒杀活动
	 */
	@RequestMapping("/activity/user/toSpikeActivity.do")
	public ModelAndView toSpikeActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String openId = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			String code = request.getParameter("code");
			String instanceId = request.getParameter("instanceActivityId");
			ActivityConfigBean activityConfigBean = activityService.findActivityConfigByCode(code);
			//获得活动规则
			List<ActivityRule> returnRuleList = activityService.queryActivityRuleByActivityConfigId(activityConfigBean.getId());
			activityConfigBean.setRule(returnRuleList);
			ActivityUserRelationBean relation = activityService.findUserRelationByUserAndInstanceId(openId, Integer.valueOf(instanceId));
			
			//判断活动的条件
			String instanceActivityId = request.getParameter("instanceActivityId");
			ActivityInstanceBean bean = activityService.findActivityInstanceByIdAndCode(instanceActivityId, null);
			ActivityPrize  prize = activityService.checkActivityParamsEasy(new ActivityPrize(),activityConfigBean,user,bean);
			model.put("user", user);
			model.put("relation", relation);
			model.put("activityConfigBean", activityConfigBean);
			model.put("prize", prize);
			String errorCode = prize.getErrorCode();
			if(ActivityPrize.ERROR_CODE_FAIL.equals(errorCode)){
				return new ModelAndView(PAGE_USER_ACTIVITY_JOIN_FAIL, model);
			}			
			return new ModelAndView(PAGE_USER_ACTIVITY_JOIN_SUCCESS, model);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 用户设置活动提醒
	 * @return
	 */
	@RequestMapping("/activity/user/dealRemindUserJoinActivity.do")
	public ModelAndView dealRemindUserJoinActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			String openId = request.getParameter("openid");
			String instanceId = request.getParameter("instanceActivityId");
			ActivityInstanceBean bean = activityService.findActivityInstanceByIdAndCode(instanceId, null);
			ActivityConfigBean activityConfigBean = activityService.findActivityConfigByCode(bean.getCode());
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			//校验用户是否有资格
			String returnMag = activityService.checkLimitParams(activityConfigBean,user);
			if(StringUtil.isNotNull(returnMag)){
				ActivityRemindBean remindBean = new ActivityRemindBean();
				remindBean.setInstance_name("FAIL");
				remindBean.setRemark(returnMag);
				writeJson(response,remindBean);
				return null;
			}
			ActivityRemindBean remindRbean = activityService.remindUserJoinActivity(openId,instanceId);
			writeJson(response,remindRbean);
		} catch (Exception e) {
			log.error(e, e);
			ActivityRemindBean bean = new ActivityRemindBean();
			bean.setInstance_name("FAIL");
			writeJson(response,bean);
		}
		return null;
	}
	
	/**
	 * 免费得坚果活动
	 */
	@RequestMapping("/activity/user/toFreeCutActivity.html")
	public ModelAndView toFreeCutActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String openid = request.getParameter("openid");
			String code = request.getParameter("code");
			//用第二域名展示界面，避免第一域名被封
			WechatOriginalInfoBean original = wechatOriginalInfoService.getOriginalInfoByServerDomain(request);
			String serverName = request.getServerName();
			if(original.getServer_url2() != null && original.getServer_url2().indexOf(serverName)< 0){
				String url = original.getServer_url2()+"activity/user/toFreeCutActivity.html?openid="+openid+"&code="+code;
				response.sendRedirect(url);
				return null;
			}
			
			
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				originalid = original.getOriginalid();
				openid = wechatUserService.getOpenId(code,originalid);
				request.getSession().setAttribute("cachedOpenid", openid);
			}else if(StringUtil.isNotNull(cachedOpenid)){
				openid = cachedOpenid;
			}
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				//跳转至关注公众号界面
				writeJson(response, "<script>alert(\"请先关注公众号!\");</script>");
				return null;
			}
			
			Integer supplierEventId = 1; //也是activityconfigid ,需要保持一致
			SupplierEventBean supplierEvent = supplierEventService.queryByid(supplierEventId);
			
			List<SupplierEventCodeBean> list = supplierEventService.queryEventCode(supplierEventId, user.getId(), null);
			
			model.put("user", user);
			model.put("list", list);
			model.put("supplierEvent", supplierEvent);
			
			return new ModelAndView(PAGE_USER_ACTIVITY_FREECUTACTIVITY, model);

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	/**
	 * 免费得坚果活动
	 */
	@RequestMapping("/activity/user/toFreeCutHelpActivity.html")
	public ModelAndView toFreeCutHelpActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			String toOpenId = request.getParameter("toOpenid");
			final String share_type = request.getParameter("share_type");
			Integer supplierEventId = Integer.parseInt(request.getParameter("supplierEventId")); //也是activityconfigid ,需要保持一致
			final UserWechatBean user = userWechatService.findUserWechatByOpenId(toOpenId);
			WechatOriginalInfoBean original = wechatOriginalInfoService.getOriginalInfoByServerDomain(request);
			String originalid = original.getOriginalid();
			String code = request.getParameter("code");
			
			String openid = (String)request.getSession().getAttribute("code_openid");
			if(StringUtil.isNull(openid) && StringUtil.isNull(code)){
				// 没有缓存，也没有传code过来，则跳转至微信授权
				String enCodeUrl = URLEncoder.encode(original.getServer_url() +"activity/user/toFreeCutHelpActivity.html?toOpenid="+ toOpenId +"&supplierEventId="+ supplierEventId + "&share_type="+share_type , "utf-8");
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				return null;
			}else if(StringUtil.isNull(openid)){
				// 微信认证
				WechatWebAuthBean auth = wechatUserService.getOpenIdByWebAuthCode(code, originalid);
				//好友信息，创建用户及好友关系
				if( StringUtil.isNotNull(auth.getAccess_token())){
					request.getSession().setAttribute("code_openid",auth.getOpenid());
					UserWechatBean friendUser = wechatUserService.createWechatUserByWebAuth(auth.getOpenid(), user.getId(),  WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, supplierEventId, originalid , auth.getAccess_token());
					userWechatService.createUserWechatFriend(user,friendUser);
				}else{ // 没有accessstoken 则重新访问
					String enCodeUrl = URLEncoder.encode(original.getServer_url() +"activity/user/toFreeCutHelpActivity.html?toOpenid="+ toOpenId +"&supplierEventId="+ supplierEventId + "&share_type="+share_type , "utf-8");
					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
					return null;
				}
			}
			
			//用第二域名展示界面，避免第一域名被封
			String serverName = request.getServerName();
			if(original.getServer_url2() != null && original.getServer_url2().indexOf(serverName)< 0){
				String url = original.getServer_url2()+"activity/user/toFreeCutHelpActivity.html?toOpenid="+ toOpenId +"&supplierEventId="+ supplierEventId + "&share_type="+share_type+"&code="+code ;
				response.sendRedirect(url);
				return null;
			}
			
			
			
			
			final UserQrCodeBean qrCode = userWechatService.queryQrCodeTicketByUserIdAndSence(toOpenId, WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, supplierEventId);
			
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					//创建用户分享后打开的阅读日志
					userWechatService.createUserSenceLog(user, qrCode.getSenceCode(), qrCode.getSenceId(), NumberUtil.toInt(share_type), "FreeCutActivity", null);
				}
			});
			
			
			model.put("user", user);
			model.put("qrCode", qrCode);
			model.put("supplierEventId", supplierEventId);
			
//			if("2".equals(share_type)){
				//查询中奖纪录
				ActivityWinHisBean bean = new ActivityWinHisBean();
				bean.setActivity_config_id(supplierEventId);
				bean.setUser_id(user.getId());
				List<ActivityWinHisBean> activityWinHisList = activityService.queryActivityWinHis(bean);

				model.put("activityWinHisList", activityWinHisList);
				return new ModelAndView(PAGE_USER_ACTIVITY_FREECUTHELFRIENDCIRCLEPACTIVITY, model);
//			}
//			
//			List<SupplierEventCodeBean> list = supplierEventService.queryEventCode(supplierEventId, user.getId(), null);
//			model.put("list", list);
//			
//			return new ModelAndView(PAGE_USER_ACTIVITY_FREECUTHELPACTIVITY, model);

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 领取免费得坚果活动的奖品
	 */
	@RequestMapping("/activity/user/dealFreeCutPrize.html")
	public ModelAndView dealFreeCutPrize(HttpServletRequest request,HttpServletResponse response){
		try{
			String openId = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			Integer supplierEventId = Integer.parseInt(request.getParameter("supplierEventId"));
			Integer prizeId = Integer.parseInt(request.getParameter("prizeId"));
			
			
			String result = activityPrizeService.dealUserActivityPrize(user, supplierEventId, prizeId);
//			List<SupplierEventCodeBean> list = supplierEventService.queryEventCode(1, user.getId(), null);
			writeJson(response, result);
			
			return null;

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	/**
	 * 领取免费得坚果活动的奖品
	 */
	@RequestMapping("/activity/user/dealTurnTablePrize.html")
	public ModelAndView dealTurnTablePrize(HttpServletRequest request,HttpServletResponse response){
		try{
			String openId = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			Integer activityId = Integer.parseInt(request.getParameter("activityId"));
			String activityCode = request.getParameter("activityCode");
			
			
			String result = activityPrizeService.dealUserActivityPrize(user, activityId, activityCode);
			writeJson(response, result);
			
			return null;

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	
	

	/**
	 * 免费得坚果活动
	 */
	@RequestMapping("/activity/user/toTurntable1Activity.html")
	public ModelAndView toTurntable1Activity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
			
			final String fromOpenid = request.getParameter("fromOpenid");
			String code = request.getParameter("code");
			String activityConfigId = request.getParameter("activityId");
			final String shareType = request.getParameter("shareType");
			final Integer activityId = StringUtil.isNull(activityConfigId) ?12:Integer.parseInt(activityConfigId);
			WechatOriginalInfoBean original = wechatOriginalInfoService.getOriginalInfoByServerDomain(request);
			String openid = request.getParameter("openid");

			
			if(StringUtil.isNull(openid)) {
				openid = (String)request.getSession().getAttribute("code_openid");
			}
			if(StringUtil.isNull(openid) && StringUtil.isNull(code)){
				// 没有缓存，也没有传code过来，则跳转至微信授权
				String enCodeUrl = URLEncoder.encode(original.getServer_url() +"activity/user/toTurntable1Activity.html?fromOpenid="+ fromOpenid +"&activityId="+ activityId +"&shareType="+shareType , "utf-8");
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				return null;
			}else if(StringUtil.isNull(openid)){
				// 微信认证
				WechatWebAuthBean auth = wechatUserService.getOpenIdByWebAuthCode(code, original.getOriginalid());
				//好友信息，创建用户及好友关系
				if( StringUtil.isNotNull(auth.getAccess_token())){
					request.getSession().setAttribute("code_openid",auth.getOpenid());
					openid = auth.getOpenid();
					UserWechatBean parentUser = userWechatService.findUserWechatByOpenId(fromOpenid);
					Integer parentId = parentUser == null? null : parentUser.getId();
					UserWechatBean friendUser = wechatUserService.createWechatUserByWebAuth(auth.getOpenid(), parentId,  WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, activityId, original.getOriginalid() , auth.getAccess_token());
					userWechatService.createUserWechatFriend(parentUser,friendUser);
				}else{ // 没有accessstoken 则重新访问
					String enCodeUrl = URLEncoder.encode(original.getServer_url() +"activity/user/toTurntable1Activity.html?fromOpenid="+ fromOpenid +"&activityId="+ activityId +"&shareType="+shareType , "utf-8");
					response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
					return null;
				}
			}
			
			
			//用第二域名展示界面，避免第一域名被封
			String serverName = request.getServerName();
			if(original.getServer_url2() != null && original.getServer_url2().indexOf(serverName)< 0){
				String url = original.getServer_url2()+"activity/user/toTurntable1Activity.html?fromOpenid="+fromOpenid+"&openid="+openid+"&code="+code +"&activityId="+ activityId +"&shareType="+shareType;
				response.sendRedirect(url);
				return null;
			}
			

			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					//创建用户分享后打开的阅读日志
					userWechatService.readUserSenceLog(fromOpenid, activityId, WechatConstant.TICKET_SENCE_CODE_ACTIVITY,shareType);
				}
			});
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(user == null){
				log.warn(" activityController 1215 Line ; user is null!,openid:"+openid);
				//跳转至关注公众号界面
				writeJson(response, "<script>alert(\"请先关注【WE坚果汇】公众号!\");</script>");
				return null;
			}
			
			ActivityConfigBean activity = activityConfigService.findActivityConfigByActivityIdAndCode(activityId, "turntable_activity");
			
			//查询活动的奖品
			List<ActivityPrizeRelationBean> prizeList = activityPrizeService.queryActivityPrizeRelationByActivityId(activityId);
			
			//查询最近中奖名单
			ActivityWinHisBean winHis = new ActivityWinHisBean();
			winHis.setActivity_config_id(activityId);
			winHis.setOrderby(" order by id desc limit 10 ");
			List<ActivityWinHisBean> winHisList = activityService.queryActivityWinHis(winHis);
			
			//查询自己当前是否中奖过
			ActivityWinHisBean myWinInfo = null;
			winHis.setUser_id(user.getId());
			winHis.setActivity_config_id(activityId);
			
			List<ActivityWinHisBean> myWinHisList = activityService.queryActivityWinHis(winHis);
			if(myWinHisList.size() > 0) {
				myWinInfo = myWinHisList.get(0);
			}
			
			model.put("user", user);
			model.put("activity", activity);
			model.put("prizeList", prizeList);
			model.put("winHisList", winHisList);
			model.put("myWinInfo", myWinInfo);
			
			return new ModelAndView(PAGE_USER_ACTIVITY_TURNTABLEACTIVITY, model);

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	
}
