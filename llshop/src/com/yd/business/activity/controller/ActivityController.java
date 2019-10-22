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
	

	public static final String PAGE_BUYGIVEONE_ORDERCONFIRM = "/page/user/activity/buyGiveOne/orderConfirm.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_SHAKEADDPHONE = "/page/user/activity/firstShake/shakeAddPhone.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_FRIENDSHARE = "/page/user/activity/firstShake/activityFriendsShare.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_RESULT = "/page/user/activity/firstShake/activityResult.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_WAITRESULT = "/page/user/activity/firstShake/activityWaitResult.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_INVITEFRIENDS = "/page/user/activity/firstShake/inviteFriends.jsp";
	public static final String PAGE_USER_ACTIVITY_SHAKE_NOREPEATED = "/page/user/activity/firstShake/activityNoRepeated.jsp";
	public static final String PAGE_USER_ACTIVITY_OLYMPIC = "/page/user/activity/olympicActivity/index.jsp";
	public static final String PAGE_USER_ACTIVITY_OLYMPIC_USERGETPOINTSRESULT = "/page/user/activity/olympicActivity/userGetPointsResult.jsp";
	public static final String PAGE_USER_ACTIVITY_OLYMPIC_JOINRESULT = "/page/user/activity/olympicActivity/joinResult.jsp";
	public static final String PAGE_USER_ACTIVITY_OLYMPIC_ADDPHONE = "/page/user/activity/olympicActivity/olympicAddPhone.jsp";
	public static final String PAGE_USER_ACTIVITY_FREECUTACTIVITY = "/page/user/activity/freeCutActivity/freeCutActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_FREECUTHELPACTIVITY = "/page/user/activity/freeCutActivity/freeCutHelpActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_FREECUTHELFRIENDCIRCLEPACTIVITY = "/page/user/activity/freeCutActivity/freeCutHelpFriendCircleActivity.jsp";

	
	public static final String PAGE_USER_ACTIVITY_LISTHOME = "/page/user/activity/signActivity/index.jsp";
	public static final String PAGE_USER_ACTIVITY_HOURLYACTIVITY = "/page/user/activity/signActivity/hourlyActivity.jsp";
	public static final String PAGE_USER_ACTIVITY_WINNERLIST = "/page/user/activity/signActivity/winners.jsp";
	public static final String PAGE_USER_ACTIVITY_JOIN_FAIL = "/page/user/activity/signActivity/join-fail.jsp";
	public static final String PAGE_USER_ACTIVITY_JOIN_SUCCESS = "/page/user/activity/signActivity/join-success.jsp";
	
	public static final String USER_ACTIVITY_FUTURE = "future";
	public static final String USER_ACTIVITY_NOW = "now";
	public static final String USER_ACTIVITY_HISTORY = "history";

	/**
	 * 用户抢红包接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/activity/userGrabActivity.do")
	public ModelAndView userGrabActivity(HttpServletRequest request,HttpServletResponse response){
		
		String user_id = request.getParameter("user_id");
		try{
			int id = Integer.parseInt(user_id);
			UserWechatBean user = userWechatService.findUserWechatById(id);
			
			if(user != null)
			{
				String str = "";//activityService.userGrabActivity(user);
				
				System.out.println("userGrabActivity: "+str);
				writeJson(response, str);
			}else{
				writeJson(response, "您还不是我们的用户哦！");
			}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);
		}
		
		return null;
	}
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
	
	@RequestMapping("**/activity/firstShakeActivity.do")
	public ModelAndView firstShakeActivity(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid );
			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(user.getOpenid(), WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 0);
			
			ActivityUserRelationBean relation = activityService.joinFirstShakeActivity(user );
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("ticket", ticket);
			model.put("relation", relation);
			
			if(relation.getStatus() != ActivityUserRelationBean.STATUS_USED && relation.getShare_status() != ActivityUserRelationBean.SHARE_STATUS_NO){
				
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_SHAKEADDPHONE, model);
			}else{
			
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_FRIENDSHARE, model);
			}
//			writeJson(response, relation);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 
	 * 刮刮乐活动
	 */
	@RequestMapping("**/activity/scratchAddPhoneActivity.do")
	public ModelAndView scratchAddPhoneActivity(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			String openid = request.getParameter("openid");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid );
//			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(user.getOpenid(), WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 0);
			//目前直接取摇一摇的结果
			ActivityUserRelationBean relation = activityService.joinFirstShakeActivity(user );
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
//			model.put("ticket", ticket);
			model.put("relation", relation);
			
			if(relation.getStatus() == ActivityUserRelationBean.STATUS_USED){
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_RESULT, model);

			}else{
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_SHAKEADDPHONE, model);
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	@RequestMapping("**/activity/shakeAddPhone.do")
	public ModelAndView shakeAddPhone(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String openid = request.getParameter("openid");
		String relation_id = request.getParameter("relation_id");
		String phone = request.getParameter("phone");
		try{
			//对于已经参与该活动的号码，默认返回失败
			boolean isJoined = activityService.checkPhoneHasJoinedRelation(openid,Integer.parseInt(relation_id), phone);
			if(isJoined){
				ActivityUserRelationBean relationNo = new ActivityUserRelationBean();
				relationNo.setPhone(phone);
				model.put("relation", relationNo);
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_NOREPEATED,model);
			}
			ActivityUserRelationBean relation = activityService.firstShakeAddPhone(openid, Integer.parseInt(relation_id), phone);
			model.put("relation", relation);
			//活动是否进行中
			boolean flag = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_ACTIVITY_RUNNING);
			if(flag)
			{
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_RESULT, model);
			}else{
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_WAITRESULT, model);
			}
			
			
		}catch (Exception e) {
			log.error(e, e);
			log.error("shakeAddPhone , openid:"+ openid +",relation_id:"+relation_id+",phone:"+phone);
			
			writeJson(response, "<script>alert('充值出现错误，请重新打开页面！');</script>");
		}
		
		return null;
	}
	

	@RequestMapping("**/activity/olympicAddPhone.do")
	public ModelAndView olympicAddPhone(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		String openid = request.getParameter("openid");
		String relation_id = request.getParameter("relation_id");
		String phone = request.getParameter("phone");
		try{
			//对于已经参与该活动的号码，默认返回失败
			boolean isJoined = activityService.checkPhoneHasJoinedRelation(openid,Integer.parseInt(relation_id), phone);
			if(isJoined){
				ActivityUserRelationBean relationNo = new ActivityUserRelationBean();
				relationNo.setPhone(phone);
				model.put("relation", relationNo);
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_NOREPEATED,model);
			}
			ActivityUserRelationBean relation = activityService.firstShakeAddPhone(openid, Integer.parseInt(relation_id), phone);
			model.put("relation", relation);
			//活动是否进行中
//			boolean flag = configAttributeService.getBooleanValueByCode(AttributeConstant.CODE_ACTIVITY_RUNNING);
//			if(flag)
//			{
				return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_RESULT, model);
//			}
			
			
		}catch (Exception e) {
			log.error(e, e);
			log.error("shakeAddPhone ,  openid:"+ openid +",relation_id:"+relation_id+",phone:"+phone);
			
			writeJson(response, "<script>alert('充值出现错误，请重新打开页面！');</script>");
		}
		
		return null;
	}
	
	
	@RequestMapping("**/activity/activityShareSuccess.do")
	public ModelAndView activityShareSuccess(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String openid = request.getParameter("openid");
			String relation_id = request.getParameter("relation_id");
			String share_type = request.getParameter("shareType");
			
			ActivityUserRelationBean relation = activityService.firstShakeShareSuccessToOrderProduct(openid, Integer.parseInt(share_type), Integer.parseInt(relation_id));
			
			//更新用户的分享时间
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			user.setShare_type(Integer.parseInt(share_type));
			user.setShare_time(DateUtil.getNowDateStr());
			userWechatService.update(user);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("relation", relation);
			return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_SHAKEADDPHONE, model);
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	

	@RequestMapping("**/activity/buyGiveOne/toOrderConfirm.do")
	public ModelAndView toOrderConfirm(String phone,String spid,String openid){
		try{
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			SupplierProductBean sp = supplierProductService.findSupplierProductBySpid(Integer.parseInt(spid));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("user", user);
			model.put("supplierProduct", sp);
			model.put("phone", phone);
			
			return new ModelAndView(PAGE_BUYGIVEONE_ORDERCONFIRM, model);
		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	@RequestMapping("**/activity/user/showFriendsGetActivityInfo.do")
	public ModelAndView showFriendsGetActivityInfo(HttpServletRequest request,HttpServletResponse response){

		try{
			String openid = request.getParameter("openid");
			String relation_id = request.getParameter("relation_id");
			
			ActivityUserRelationBean relation = activityService.findUserRelation(openid, Integer.parseInt(relation_id));
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			UserQrCodeBean ticket = userWechatService.queryQrCodeTicketByUserIdAndSence(user.getOpenid(), WechatConstant.TICKET_SENCE_CODE_INVITEFRIENDS, 1);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("relation", relation);
			model.put("ticket", ticket);
			model.put("user", user);
			return new ModelAndView(PAGE_USER_ACTIVITY_SHAKE_INVITEFRIENDS, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}

//	@RequestMapping("**/activity/user/restartActivityByTestTable.do")
//	public ModelAndView restartActivityByTestTable(HttpServletRequest request,HttpServletResponse response){
//
//		try{
//			
//			taskExecutor.execute(new BaseRunable() {
//				@Override
//				public void runMethod() {
//					
//					List<ActivityUserRelationBean> list = activityService.findUserRelationByTestTable();
//					for(ActivityUserRelationBean bean : list){
//	
//						try{
//							activityService.firstShakeAddPhone(bean.getOpenid(), bean.getId(), bean.getPhone());
//						}catch (Exception e) {
//							log.error(e, e);
//						}
//					}
//				}
//			});
//			
//			writeJson(response, "执行成功");
//		}catch (Exception e) {
//			log.error(e, e);
//		}
//		
//		return null;
//	}
	
	
	/**
	 * 进入奥运猜奖牌活动的界面
	 * 
	 */
	@RequestMapping("**/activity/user/toOlympicActivity.do")
	public ModelAndView toOlympicActivity(HttpServletRequest request,HttpServletResponse response){

		try{
			String openid = request.getParameter("openid");
			String fromOpenid = request.getParameter("fromOpenid");
			boolean hasUser = true;
			if(StringUtil.isNull(openid)){
//				String code = request.getParameter("code");
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
	 * 参加奥运猜奖牌活动
	 */
	@RequestMapping("**/activity/user/joinOlympicActivity.do")
	public ModelAndView joinOlympicActivity(HttpServletRequest request,HttpServletResponse response){

		try{
			
			String openid = request.getParameter("openid");
			String guess_num = request.getParameter("guess_num");
			String user_shared = request.getParameter("user_shared");
			
			String result = activityService.joinOlympicGuessActivity(openid, Integer.parseInt(guess_num),Integer.parseInt(user_shared));
			
			writeJson(response, result);
			
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");

		}
		
		return null;
	}
	
	/**
	 * 处理奥运活动开奖结果
	 * 
	 */
	@RequestMapping("**/activity/user/dealOlympicActivityResult.do")
	public ModelAndView dealOlympicActivityResult(HttpServletRequest request,HttpServletResponse response){

		try{
			Map<String, Object> model = new HashMap<String, Object>();
			
			String openid = request.getParameter("openid");
			String guess_id = request.getParameter("guess_id");
			ActivityOlympicGuessBean guess = activityService.findOlympicGuess(openid, Integer.parseInt(guess_id));
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//参加了，开奖后猜中且领过的，和没猜中的
			if(guess.getStatus() == ActivityOlympicGuessBean.STATUS_SUCCESS 
					|| guess.getStatus() == ActivityOlympicGuessBean.STATUS_NOTWIN){
				
				model.put("bean", guess);
				return new ModelAndView(PAGE_USER_ACTIVITY_OLYMPIC_JOINRESULT, model);
				
			}else if(guess.getStatus() == ActivityOlympicGuessBean.STATUS_WAIT){
				
				//修改状态
				guess.setStatus(ActivityOlympicGuessBean.STATUS_SUCCESS);
				//参加了，开奖后未领取的
				//未分享过的，猜中了，直接得30M流量
				if(guess.getShared() == ActivityOlympicGuessBean.SHARED_NO){
					ActivityUserRelationBean relation = activityService.joinActivityRelation(user, ActivityConfigBean.CODE_OLYMPICGUESS_ACTIVITY);
					model.put("relation", relation);
					model.put("user", user);
					guess.setWin_desc("恭喜您猜中啦，获得了30M流量！");

					activityService.updateOlympicGuess(guess);
					return new ModelAndView(PAGE_USER_ACTIVITY_OLYMPIC_ADDPHONE, model);
				}else{
					guess.setWin_desc("恭喜您猜中啦，5元积分红包已充入您的账户！");
					//分享过的，猜中了，获得积分红包
					user.setPoints(user.getPoints() + 500);
					userWechatService.update(user);
					userCommissionPointsService.createUserPointLog(user.getId(), 500, "奥运竞猜活动中奖积分");
					model.put("bean", guess);

					activityService.updateOlympicGuess(guess);
					return new ModelAndView(PAGE_USER_ACTIVITY_OLYMPIC_JOINRESULT, model);
				}
			}
			
			
			
		}catch (Exception e) {
			log.error(e, e);
			writeJson(response, "<script>alert('出现错误，请关闭当前页面后重试！');</script>");

		}
		
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
			
			String category = prize.getCategory();
			if(ActivityPrize.ACTIVITY_PRIZE_FAIL.equals(category)){
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
	 * 买一赠一活动入口 
	 */
	@RequestMapping("/activity/user/queryBuyGiveOneProductActivity.do")
	public ModelAndView queryBuyGiveOneProductActivity(HttpServletRequest request,HttpServletResponse response){
		try{
			Map<String,Object> model = new HashMap<String,Object>();
			String openId = request.getParameter("openid");
			String code = request.getParameter("code");
			ActivityConfigBean activityConfigBean = activityService.findActivityConfigByCode(code);
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			
			//判断活动的条件
			String instanceActivityId = request.getParameter("instanceActivityId");
			ActivityInstanceBean bean = activityService.findActivityInstanceByIdAndCode(instanceActivityId, null);
			ActivityPrize  prize = activityService.checkActivityParamsEasy(new ActivityPrize(),activityConfigBean,user,bean);
			if(StringUtil.isNotNull(prize.getRemark())){
				model.put("user", user);
				model.put("activityConfigBean", activityConfigBean);
				model.put("prize", prize);
				String category = prize.getCategory();
				if(ActivityPrize.ACTIVITY_PRIZE_FAIL.equals(category) || ActivityPrize.ACTIVITY_PRIZE_NO_WINNER.equals(category)){
					return new ModelAndView(PAGE_USER_ACTIVITY_JOIN_FAIL, model);
				}
			}			
			//有父用户
			if(user != null ){
//				List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
				model.put("user", user);
				model.put("customer_id", CustomerBean.ID_BUYGIVEONE);
				model.put("openid", openId);
				model.put("activityConfigBean", activityConfigBean);
				model.put("instanceBean", bean);
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
	
	/**
	 * 8月流量活动，刮刮乐
	 */
	@RequestMapping("/activity/user/toScratchActivity.do")
	public ModelAndView toScratchActivity(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> model = new HashMap<String, Object>();
		try{
//			String code = request.getParameter("code");
			String openId = request.getParameter("openid");
//			String openId = wechatService.getOpenId(code);
//			String openId = "123";
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openId);
			String code = request.getParameter("code");
			ActivityConfigBean activityConfigBean = activityService.findActivityConfigByCode(code);
			//获得活动规则
			List<ActivityRule> returnRuleList = activityService.queryActivityRuleByActivityConfigId(activityConfigBean.getId());
			activityConfigBean.setRule(returnRuleList);
			ActivityUserRelationBean relation = activityService.joinFirstShakeActivity(user );

			model.put("user", user);
			model.put("relation", relation);
			model.put("activityConfigBean", activityConfigBean);
//			model.put("fromUser", fromUser);
			return new ModelAndView(UserController.PAGE_ACTIVITY_SCRATCHACTIVITY, model);

		}catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
	

	/**
	 * 用户购买平台流量入口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/activity/user/querySupplierProduct.do")
	public ModelAndView querySupplierProduct(HttpServletRequest request,HttpServletResponse response){
		try{
			Map<String,Object> model = new HashMap<String,Object>();
			String openid = request.getParameter("openid");
			String activity_code = request.getParameter("code");
			String customer_id = request.getParameter("customer_id");
			
			
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			ActivityConfigBean activityConfigBean = activityService.findActivityConfigByCode(activity_code);
			
			
			//判断活动的条件
			String instanceActivityId = request.getParameter("instanceActivityId");
			if(!StringUtil.isNotNull(openid) || !StringUtil.isNotNull(activity_code) || !StringUtil.isNotNull(instanceActivityId)){
				writeJson(response, "<script>alert('获取活动失败,请刷新后重试！');</script>");
				return null;
			}
			ActivityInstanceBean bean = activityService.findActivityInstanceByIdAndCode(instanceActivityId, null);
			ActivityPrize  prize = activityService.checkActivityParamsEasy(new ActivityPrize(),activityConfigBean,user,bean);
			String category = prize.getCategory();
			model.put("user", user);
			model.put("activityConfigBean", activityConfigBean);
			model.put("prize", prize);
			if(StringUtil.isNotNull(prize.getRemark())){
				if(ActivityPrize.ACTIVITY_PRIZE_FAIL.equals(category) || ActivityPrize.ACTIVITY_PRIZE_NO_WINNER.equals(category)){
					return new ModelAndView(PAGE_USER_ACTIVITY_JOIN_FAIL, model);
				}
			}
			
			//有父用户
			if(user != null){
			
//				List<CustomerSupplierProductBean> list= supplierProductService.queryPlatformSupplierProduct();
				model.put("user", user);
				model.put("customer_id",  customer_id);
				model.put("openid", openid);
				model.put("activityConfigBean", activityConfigBean);
				model.put("instanceBean", bean);
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
				
				return new ModelAndView(UserController.PAGE_ACTIVITY_SIGN_ORDERPRODUCT, model);
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
			String category = prize.getCategory();
			if(ActivityPrize.ACTIVITY_PRIZE_FAIL.equals(category)){
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
	 * 移动的活动，调用验证码
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/activity/checkYDYZM.do")
	public ModelAndView checkYDYZM(HttpServletRequest request,HttpServletResponse response){
		try {
			String phone = request.getParameter("phone");
			String actId = request.getParameter("actId");
			
			String url = "http://act.ahmobile.cn:8000/activity/localLty/sms.do?t="+System.currentTimeMillis()+"&phone="+phone+"&actId="+actId;
			
			String json = HttpUtil.get(url);
			writeJson(response, json);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 移动的活动，调用参加活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/activity/joinYDActivity.do")
	public ModelAndView joinYDActivity(HttpServletRequest request,HttpServletResponse response){
		try {
			String phone = request.getParameter("phone");
			String actId = request.getParameter("actId");
			String smsyzm = request.getParameter("smsyzm");
			
			String url = "http://act.ahmobile.cn:8000/activity/localLty/login.do?t="+System.currentTimeMillis();
			System.out.println(url);
			Map<String, String> map = new HashMap<String, String>();
			map.put("phone", phone);
			map.put("actId", actId);
			map.put("smsyzm", smsyzm);
			
			String json = HttpUtil.post(url,map );
			writeJson(response, json);
			
		} catch (Exception e) {
			log.error(e, e);
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
			String cachedOpenid = (String)request.getSession().getAttribute("cachedOpenid");
			String originalid = null;
			//先查缓存
			if(StringUtil.isNull(cachedOpenid) && StringUtil.isNull(openid)){
				String code = request.getParameter("code");
				originalid = wechatOriginalInfoService.getOriginalidByServerDomain(request);
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
			
//			String openid = (String)request.getSession().getAttribute("code_openid");
			String openid ="123";
			String code = request.getParameter("code");
			if(StringUtil.isNull(openid) && StringUtil.isNull(code)){
				// 没有缓存，也没有传code过来，则跳转至微信授权
				String enCodeUrl = URLEncoder.encode(original.getServer_url() +"activity/user/toFreeCutHelpActivity.html?toOpenid="+ toOpenId +"&supplierEventId="+ supplierEventId + "&share_type="+share_type , "utf-8");
				response.sendRedirect("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+original.getAppid()+"&redirect_uri="+ enCodeUrl + "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
				return null;
			}else if(StringUtil.isNull(openid)){
				//好友信息，创建用户及好友关系
				WechatWebAuthBean auth = wechatUserService.getOpenIdByWebAuthCode(code, originalid);

				request.getSession().setAttribute("code_openid",auth.getOpenid());
				UserWechatBean friendUser = wechatUserService.createWechatUserByWebAuth(auth.getOpenid(), user.getId(),  WechatConstant.TICKET_SENCE_CODE_SUPPLIEREVENT, supplierEventId, originalid , auth.getAccess_token());
				userWechatService.createUserWechatFriend(user,friendUser);
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
			
			if("2".equals(share_type)){
				//查询中奖纪录
				ActivityWinHisBean bean = new ActivityWinHisBean();
				bean.setActivity_config_id(supplierEventId);
				bean.setUser_id(user.getId());
				List<ActivityWinHisBean> activityWinHisList = activityService.queryActivityWinHis(bean);

				model.put("activityWinHisList", activityWinHisList);
				return new ModelAndView(PAGE_USER_ACTIVITY_FREECUTHELFRIENDCIRCLEPACTIVITY, model);
			}
			
			List<SupplierEventCodeBean> list = supplierEventService.queryEventCode(supplierEventId, user.getId(), null);
			model.put("list", list);
			
			return new ModelAndView(PAGE_USER_ACTIVITY_FREECUTHELPACTIVITY, model);

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
	
	
	
	
	
}
