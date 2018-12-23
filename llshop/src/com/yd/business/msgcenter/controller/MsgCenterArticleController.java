/**
 * 
 */
package com.yd.business.msgcenter.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean;
import com.yd.business.msgcenter.bean.MsgCenterUserSubscribeBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterArticleService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class MsgCenterArticleController extends BaseController {
	
	public static final String PAGE_ARTICLE_LIST = "/page/pc/article/article_list.jsp";
	public static final String PAGE_UPDATE_ARTICLE = "/page/pc/article/article_update.jsp";
	public static final String PAGE_ARTICLE_ASSIGNSENDTIME = "/page/pc/article/article_assign_send_time.jsp";
	
	public static final String PAGE_ACTION_ARTICL_RELATION_ELIST = "/page/pc/article/action_article_relation_list.jsp";
	public static final String PAGE_UPDATE_ACTION_ARTICLE_RELATION = "/page/pc/article/action_article_relation_update.jsp";

	
	@Resource
	private IMsgCenterArticleService msgCenterArticleService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IUserWechatService userWechatService;
	
	
	@RequestMapping("admin/msgcenter/article/toQueryArticleListPage.do")
	public ModelAndView toQueryArticleListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			
			MsgCenterArticleBean article = new MsgCenterArticleBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), article);
			
			List<MsgCenterArticleBean> articleList = msgCenterArticleService.queryArticle(article);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("articleList", articleList);
			return new ModelAndView(PAGE_ARTICLE_LIST, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/toUpdateArticlePage.do")
	public ModelAndView toUpdateArticlePage(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String id = request.getParameter("id");
			MsgCenterArticleBean article = null;
			if(StringUtil.isNotNull(id)){
				article = msgCenterArticleService.findArticleWithDeliveryInf(Integer.parseInt(id));
			}else{
				article = new MsgCenterArticleBean();
			}

			List<MsgCenterArticleTypeBean> articleTypeList = msgCenterArticleService.queryArticleType(null);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("articleTypeList", articleTypeList);
			model.put("article", article);
			return new ModelAndView(PAGE_UPDATE_ARTICLE, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/updateArticle.do")
	public ModelAndView updateArticle(HttpServletRequest request,HttpServletResponse response){
		try{
			
			MsgCenterArticleBean article = new MsgCenterArticleBean();
			Map<String, String> map = getRequestParamsMap(request);
			AutoInvokeGetSetMethod.autoInvoke( map, article);
			
			
			msgCenterArticleService.saveOrUpdateArticle(article);
			
			return new ModelAndView("/admin/msgcenter/article/toQueryArticleListPage.do");
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	

	
	@RequestMapping("admin/msgcenter/article/toQueryActionArticleRelationListPage.do")
	public ModelAndView toQueryActionArticleRelationListPage(HttpServletRequest request,HttpServletResponse response){
		try {
			
			MsgCenterActionArticleRelationBean bean = new MsgCenterActionArticleRelationBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			
			List<MsgCenterActionArticleRelationBean> relationList = msgCenterActionService.queryUserActionArticleRelation(bean);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("relationList", relationList);
			return new ModelAndView(PAGE_ACTION_ARTICL_RELATION_ELIST, model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/toUpdateActionArticleRelationPage.do")
	public ModelAndView toUpdateActionArticleRelationPage(HttpServletRequest request,HttpServletResponse response){

		try{
			String id = request.getParameter("id");
			MsgCenterActionArticleRelationBean bean = new MsgCenterActionArticleRelationBean();
			if(StringUtil.isNotNull(id)){
				bean.setId(Integer.parseInt(id));
				List<MsgCenterActionArticleRelationBean> list = msgCenterActionService.queryUserActionArticleRelation(bean);
				if(list.size()>0){
					bean = list.get(0);
				}
			}else{
			}
			
			MsgCenterActionDefineBean define = new MsgCenterActionDefineBean();
			define.setStatus(MsgCenterActionDefineBean.STATUS_ENABLE);
			List<MsgCenterActionDefineBean> actionDefineList = msgCenterActionService.queryActionDefine(define );
			
			List<MsgCenterArticleTypeBean> articleTypeList = msgCenterArticleService.queryArticleType(null);
			List<MsgCenterArticleBean> articleList = msgCenterArticleService.queryArticle(null);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("actionDefineList", actionDefineList);
			model.put("articleTypeList", articleTypeList);
			model.put("articleList", articleList);
			model.put("bean", bean);
			return new ModelAndView(PAGE_UPDATE_ACTION_ARTICLE_RELATION, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/updateActionArticleRelation.do")
	public ModelAndView updateActionArticleRelation(HttpServletRequest request,HttpServletResponse response){
		try {
			

			MsgCenterActionArticleRelationBean bean = new MsgCenterActionArticleRelationBean();
			Map<String, String> map = getRequestParamsMap(request);
			AutoInvokeGetSetMethod.autoInvoke( map, bean);
			
			msgCenterActionService.createOrUpdateActionArticleRelation(bean);
			
			return new ModelAndView("redirect:/admin/msgcenter/article/toQueryActionArticleRelationListPage.do");
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/toArticleAssignSendTimePage")
	public ModelAndView toArticleAssignSendTimePage(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			MsgCenterArticleBean article = msgCenterArticleService.findArticleById(Integer.parseInt(id));
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("article", article);
			return new ModelAndView(PAGE_ARTICLE_ASSIGNSENDTIME, model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("admin/msgcenter/article/updateArticleAssignSendTime.do")
	public ModelAndView updateArticleAssignSendTime(HttpServletRequest request,HttpServletResponse response){
		try {

			String id = request.getParameter("id");
			String assign_send_status = request.getParameter("assign_send_status");
			String assign_send_time = request.getParameter("assign_send_time");

			MsgCenterArticleBean article = msgCenterArticleService.findArticleById(Integer.parseInt(id));
			article.setAssign_send_status(Integer.parseInt(assign_send_status));
			article.setAssign_send_time(assign_send_time);
			
			msgCenterArticleService.saveOrUpdateArticle(article);
			
			return new ModelAndView("redirect:/admin/msgcenter/article/toQueryArticleListPage.do");
			
		} catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	@RequestMapping("**/msgcenter/yygLotteryUserMsg.do")
	public ModelAndView yygLotteryUserMsg(HttpServletRequest request,HttpServletResponse response){
		
		try{
			Map<String,String> map = getRequestParamsMap(request);
			String openid = map.get("buyUser");
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			//给中奖人发消息
			msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_USER_MSG , null, map);
			//给中奖人的好友
			UserWechatConditionBean condition = new UserWechatConditionBean();
			condition.setStatus(UserWechatConditionBean.STATUS_SUBSCRIBE);
			if(user.getParentid() != null){
				//中奖人的父
				UserWechatBean parent = userWechatService.findUserWechatById(user.getParentid());
				map.put("win_name", user.getNick_name());
				msgCenterActionService.saveAndHandleUserAction(parent.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_MSG , null, map);
				
				//中奖人的兄弟，也就是和中奖人同一个父用户
				condition.setParentid(user.getParentid());
				List<UserWechatBean> brothers = userWechatService.queryUser(condition);
				for(UserWechatBean brother : brothers){
					//不重复给父用户发
					if(brother.getId() != user.getId().intValue()){
						map.put("friend_name", parent.getNick_name());
						msgCenterActionService.saveAndHandleUserAction(brother.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_FIRENDS_MSG , null, map);
					}
				}
				
//				//父用户的同一个父用户，也就是父用户的兄弟节点，中奖人的叔叔伯伯七大姑八大姨们,这些不认识，不发
//				condition.setParentid(parent.getParentid());
//				List<UserWechatBean> uncles = userWechatService.queryUser(condition);
//				for(UserWechatBean uncle : uncles){
//					//不重复给父用户发
//					if(uncle.getId() != parent.getId().intValue()){
//						map.put("friend_name", parent.getNick_name());
//						msgCenterActionService.saveAndHandleUserAction(uncle.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_FIRENDS_MSG , null, map);
//					}
//				}
//				//中奖人父用户的父用户，即：爷爷
				condition.setParentid(null);
				condition.setId(parent.getParentid());
				List<UserWechatBean> grandpas = userWechatService.queryUser(condition);
				for(UserWechatBean grandpa : grandpas){
					//不重复给父用户发
					map.put("friend_name", parent.getNick_name());
					msgCenterActionService.saveAndHandleUserAction(grandpa.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_FIRENDS_MSG , null, map);
				}
				condition.setId(null);
				
			}
			
			//中奖人的子用户
			condition.setParentid(user.getId());
			List<UserWechatBean> childrens = userWechatService.queryUser(condition);
			for(UserWechatBean children : childrens){
				msgCenterActionService.saveAndHandleUserAction(children.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_MSG , null, map);
				
				//给孙用户发，也就是子用户的子用户
				condition.setParentid(children.getId());
				List<UserWechatBean> grandsons = userWechatService.queryUser(condition);
				for(UserWechatBean grandson : grandsons){
					map.put("friend_name", children.getNick_name());
					msgCenterActionService.saveAndHandleUserAction(grandson.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_YYG_LOTTERY_FRIENDS_FIRENDS_MSG , null, map);
				}
			}
			
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		writeJson(response, "true");
		
		return null;
	}
	
	@RequestMapping("**/msgcenter/yygBuyCompleteNotify.do")
	public ModelAndView yygBuyCompleteNotify(HttpServletRequest request,HttpServletResponse response){
		
		try {

			Map<String,String> map = getRequestParamsMap(request);
			String openid = UserWechatBean.OPENID_SYSTEM_MASS_NOTIFY;
			msgCenterActionService.saveAndHandleUserAction(openid, MsgCenterActionDefineBean.ACTION_TYPE_YYG_BUY_COMPLETE_MSG , null, map);
			
			
		} catch (Exception e) {
			log.error(e, e);
		}
		

		writeJson(response, "true");
		
		return null;
	}
	
	@RequestMapping("**/msgcenter/updateUserSubscribe.do")
	public ModelAndView updateUserSubscribe(HttpServletRequest request,HttpServletResponse response){
		try {

			Map<String,String> map = getRequestParamsMap(request);
			MsgCenterUserSubscribeBean bean = new MsgCenterUserSubscribeBean();
			AutoInvokeGetSetMethod.autoInvoke(map, bean);
			
			msgCenterArticleService.createOrUpdateUserSubscribeInfo(bean.getUser_id(), bean.getOpenid(), bean.getStatus(), bean.getCode(), bean.getType(), bean.getArticle_id());
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e,e);
		}
		return null;
	}
	
}
