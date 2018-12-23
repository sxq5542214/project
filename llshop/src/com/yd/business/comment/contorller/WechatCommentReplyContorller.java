package com.yd.business.comment.contorller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.WechatCommentBean;
import com.yd.business.comment.service.IWechatCommentReplyService;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * 微信消息回复
 * @author BoBo
 *
 */
@Controller
public class WechatCommentReplyContorller extends BaseController  {

	@Autowired
	private IWechatCommentReplyService wechatCommentReplyService;
	@Resource
	private IWechatService wechatUserService;
	
	public Map<String,Object> maxBean = new HashMap<String,Object>();
	
	public static final String PAGE_COMMENT_INFO_DIALOG = "/page/customer/reply_customer.jsp";
	public static final String PAGE_COMMENT_INFO_INDEX = "/page/customer/customer_comment_list.jsp";
	/**
	 * 获取留言列表
	 */
	@RequestMapping("/comment/getMsgCenterUserCommentList.do")
	public ModelAndView getMsgCenterUserCommentList(HttpServletRequest request,HttpServletResponse response){
		try {
			String userid = request.getParameter("userid");
			String msgid = request.getParameter("msgid");
			String nowpage = request.getParameter("nowpage");
			String openid = request.getParameter("openid");
			PageinationData returnData = wechatCommentReplyService.queryMsgCenterUserComment(nowpage,openid);
			writeJson(response,returnData);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	
	/**
	 * 回复留言
	 */
	@RequestMapping("/comment/replyMsgToWeChatUser.do")
	public ModelAndView replyMsgToWeChatUser(HttpServletRequest request,HttpServletResponse response){
		try {
			String commentid = request.getParameter("commentid");
			String msgtext = request.getParameter("msgtext");
			String adminopenid = request.getParameter("adminopenid");
			CommentInfoBean bean = wechatCommentReplyService.replyMsgToWeChatUser(msgtext,commentid,adminopenid);
			if(StringUtil.isNotNull(bean.getRemark())){
				writeJson(response, bean);
				return null;
			}else{
//				WechatCommentBean commentInfoBean = new WechatCommentBean();
//				commentInfoBean.setId(bean.getComment_parentid());
//				List<WechatCommentBean> beanList = new ArrayList<WechatCommentBean>();
//				beanList.add(commentInfoBean);
//				List<CommentInfoBean> replyBeanList = wechatCommentReplyService.queryWechatCommentInfoByParentId(beanList);
//				writeJson(response,replyBeanList);
			}
			writeJson(response, bean);
			return null;
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	
//	/**
//	 * 定时刷新留言列表
//	 */
//	@RequestMapping("/comment/getNewMsg.do")
//	public ModelAndView getNewMsg(HttpServletRequest request,HttpServletResponse response){
//		try {
//			List<WechatCommentBean> count = wechatCommentReplyService.getNewMsgCount(maxBean,WechatCommentBean.REPLY_COMMENT_ALL);
//			if(!StringUtil.isNull(count)){
//				writeJson(response, count.size()+"条");
//			}
//			writeJson(response, "0条");
//		} catch (Exception e) {
//			log.error(e, e);
//			writeJson(response, "获取失败");
//		}
//		return null;
//	}
	
	/**
	 * 回复留言(对话框的留言列表自动刷新)
	 */
	@RequestMapping("/comment/getNewMsgForOwner.do")
	public ModelAndView getNewMsgForOwner(HttpServletRequest request,HttpServletResponse response){
		try {
			String commentid = request.getParameter("commentid");
			List<WechatCommentBean> count = wechatCommentReplyService.getNewMsgCount(commentid);
			writeJson(response, count);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	
	/**
	 * 展开单独对话客户留言列表
	 */
	@RequestMapping("/comment/showWechatUserCommentList.do")
	public ModelAndView showWechatUserCommentList(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String openid = request.getParameter("openid");
			String adminopenid = request.getParameter("adminopenid");
			List<WechatCommentBean> beanList = wechatCommentReplyService.getWechatUserCommentListByOpenid(openid, adminopenid);
			map.put("beanList", beanList);
			if(StringUtil.isNull(beanList)){
				writeJson(response, "<script>alert('获取参数出现错误！');</script>");
				return null;
			}
			return new ModelAndView(PAGE_COMMENT_INFO_DIALOG,map);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	
}
