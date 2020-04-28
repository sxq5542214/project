package com.yd.business.comment.contorller;

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
import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.service.ICommentInfoService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * 留言板模块
 * @author BoBo
 *
 */
@Controller
public class CommentContorller extends BaseController {

	@Autowired
	private ICommentInfoService commentInfoService;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	protected IWechatService wechatService;
	@Resource
	protected IDictionaryService dictionaryService;
	
	
	
	public static final String PAGE_COMMENT_INFO_SHOW = "/page/pc/comment/comment_list.jsp";
	public static final String PAGE_COMMENT_INFO_RESHOW = "/page/pc/comment/recomment_list.jsp";
	public static final String PAGE_COMMENT_INFO_READD = "/page/pc/comment/comment_update.jsp";
	
	/**
	 * 新增留言
	 */
	@RequestMapping("**/comment/toCommentInstert.do")
	public ModelAndView toCommentInstert(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			String msgtext = request.getParameter("msgtext");
			String conf_code = request.getParameter("conf_code");
			String parentid = request.getParameter("parentid");
			String replyid = request.getParameter("replyid");
			Object img_arr = request.getParameter("img_arr");
			if(!StringUtil.isNotNull(conf_code) || !StringUtil.isNotNull(msgtext)/* || !StringUtil.isNotNull(openid)*/){
				writeJson(response, "<script>alert('获取参数出现错误，请刷新重试！');</script>");
				return null;
			}
			
			String ip = getRemoteHost(request);
			CommentInfoBean bean = commentInfoService.insertCommentInfo(img_arr,ip,openid,msgtext,null,conf_code,parentid,replyid,false);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "添加失败");
		}
		return null;
	}
	
	/**
	 * 获取留言列表
	 */
	@RequestMapping("/comment/getCommentInfoList.do")
	public ModelAndView getCommentInfoList(HttpServletRequest request,HttpServletResponse response){
		try {
			String code = request.getParameter("code");
			//传入用户openid，返回留言时，将用户的留言默认展示最前
			String openid = request.getParameter("openid");
			//传入id，将该ID默认展示最前（id和openid，id优先级更高）
			String id = request.getParameter("id");
			String nowpage = request.getParameter("nowpage");
			String idName = request.getParameter("idName");
			String ip = getRemoteHost(request);
			if(!StringUtil.isNotNull(code) || !StringUtil.isNotNull(ip)){
				writeJson(response, "<script>alert('获取参数出现错误，请刷新重试！');</script>");
				return null;
			}
			List<CommentInfoBean> listBean = commentInfoService.showCommentInfoByConfCode(code,openid,ip,id,nowpage,idName);
			writeJson(response,listBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	
	/**
	 * 处理评论点赞数
	 */
	@RequestMapping("comment/dealUserAgreeCommentInfo.do")
	public ModelAndView dealUserAgreeCommentInfo(HttpServletRequest request,HttpServletResponse response){
		try {
			String openid = request.getParameter("openid");
			String commentid = request.getParameter("commentid");
			String action = request.getParameter("action");
			String ip = getRemoteHost(request);
			if(!StringUtil.isNotNull(ip)){
				writeJson(response, "<script>alert('获取参数出现错误，请刷新重试！');</script>");
				return null;
			}
			CommentInfoBean bean = commentInfoService.dealUserAgreeCommentInfo(ip,openid, commentid, action);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "添加失败");
		}
		return null;
	}
	
	/**
	 * 后台获取评论
	 */
	@RequestMapping("**/admin/comment/getAllCommentInfoByBean.do")
	public ModelAndView getAllCommentInfoByBean(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String status = request.getParameter("status");
			String type = request.getParameter("type");
			String parentid = request.getParameter("parentid");
			String nowpage = request.getParameter("nowpage");
			CommentInfoBean bean = new CommentInfoBean();
			if(StringUtil.isNotNull(status)){
				bean.setStatus(Integer.valueOf(status));
			}
			bean.setType(Integer.valueOf(type));
			if(StringUtil.isNotNull(parentid)){
				bean.setComment_parentid(Integer.valueOf(parentid));
			}
			if(StringUtil.isNotNull(nowpage)){
				bean.setNowpage(Integer.valueOf(nowpage));
			}
			PageinationData pd = commentInfoService.queryCommentInfoByBean(bean);
			map.put("pd", pd);
			if(Integer.valueOf(type) != CommentInfoBean.COMMENT_TYPE_USER){
				return new ModelAndView(PAGE_COMMENT_INFO_SHOW,map);	
			}else{
				return new ModelAndView(PAGE_COMMENT_INFO_RESHOW,map);	
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	/**
	 * 用户后台管理员审核留言，评论信息
	 */
	@RequestMapping("**/admin/comment/dealCommentInfoStatus.do")
	public ModelAndView dealCommentInfoStatus(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String action = request.getParameter("action");
			String id = request.getParameter("id");
			String status = request.getParameter("status");
			String type = request.getParameter("type");
			CommentInfoBean bean = new CommentInfoBean();
			//审核留言
			List<CommentInfoBean> commentList = commentInfoService.checkCommentInfoByAction(action,id);
			writeJson(response,commentList);		
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 获取留言的评论
	 */
	@RequestMapping("**/admin/comment/getCommentInfoAndRe.do")
	public ModelAndView getCommentInfoAndRe(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String id = request.getParameter("id");
			String nowpage = request.getParameter("nowpage");
			CommentInfoBean bean = commentInfoService.findCommentInfoById(Integer.valueOf(id));
			if(StringUtil.isNotNull(nowpage)){
				bean.setNowpage(Integer.valueOf(nowpage));
			}
			map.put("comment", bean);
			return new ModelAndView(PAGE_COMMENT_INFO_READD,map);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "getCommentInfo失败");
		}
		return null;
	}
	
	/**
	 * 管理员评论用户留言
	 */
	@RequestMapping("**/admin/comment/toCommentInstertForAdmin.do")
	public ModelAndView toCommentInstertForAdmin(HttpServletRequest request,HttpServletResponse response){
		String remark = "";
		try {
			String msgtext = request.getParameter("msgtext");
			String conf_code = request.getParameter("conf_code");
			String parentid = request.getParameter("parentid");
			if(!StringUtil.isNotNull(conf_code) || !StringUtil.isNotNull(msgtext)){
				writeJson(response, "<script>alert('获取参数出现错误，请刷新重试！');</script>");
				return null;
			}
			//评论入库
			String ip = getRemoteHost(request);
			CommentInfoBean bean = commentInfoService.insertCommentInfo(null,ip,null,msgtext,null,conf_code,parentid,null,true);
			remark = bean.getRemark();
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "添加失败["+remark+"]");
		}
		return null;
	}
	
	/**
	 * 删除评论，管理员后台
	 */
	@RequestMapping("**/admin/comment/deleteCommentInfo.do")
	public ModelAndView deleteCommentInfo(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			commentInfoService.deleteCommentInfo(id);
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "删除失败");
		}
		writeJson(response, "删除成功");
		return null;
	}
	
	/**
	 * 后台展开未审核留言列表
	 * 留言和评论在一起展示，按照时间排序展示方便审核
	 */
	@RequestMapping("**/admin/comment/showCommentListForManage.do")
	public ModelAndView showCommentListForManage(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String type = request.getParameter("type");
			String status = request.getParameter("status");
			String activity_code = request.getParameter("activity_code");
			String nowpage = request.getParameter("nowpage");
			CommentInfoBean bean = new CommentInfoBean();
			if(StringUtil.isNotNull(status)){
				bean.setStatus(Integer.valueOf(status));
			}else{
				bean.setStatus(CommentInfoBean.COMMENT_STATUS_NOCHECK);
			}
			if(StringUtil.isNotNull(type)){
				bean.setType(Integer.valueOf(type));
			}
			if(StringUtil.isNotNull(activity_code)){
				bean.setComment_conf_code(activity_code);
			}
			
			if(StringUtil.isNotNull(nowpage)){
				if(Integer.valueOf(nowpage) == 0){
					bean.setNowpage(1);
				}else{
					bean.setNowpage(Integer.valueOf(nowpage));
				}
				
			}
			bean.setOrderby(" order by createtime desc");
			PageinationData pd = commentInfoService.queryCommentInfoByBean(bean);
			CommentConfigBean configBean = new CommentConfigBean();
			//获取配置信息
			List<CommentConfigBean> configBeanList = commentInfoService.queryCommentConfigByBean(configBean);
			List<DictionaryBean> typeList = dictionaryService.getValueByTablenameAndAttribute("CommentInfoBean","type");
			List<DictionaryBean> statusList = dictionaryService.getValueByTablenameAndAttribute("CommentInfoBean","status");
			map.put("pd", pd);
			map.put("bean", bean);
			map.put("configBeanList", configBeanList);
			map.put("statusList", statusList);
			map.put("typeList", typeList);
			return new ModelAndView(PAGE_COMMENT_INFO_SHOW,map);	
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("**/admin/comment/getCommentReplyInfoFroAjax.do")
	public ModelAndView getCommentReplyInfoFroAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			//类型
			String parentid = request.getParameter("parentid");
			String nowpage = request.getParameter("nowpage");
			CommentInfoBean bean = new CommentInfoBean();
			bean.setComment_parentid(Integer.valueOf(parentid));
			bean.setNowpage(Integer.valueOf(nowpage));
			bean.setOrderby("order by createtime asc");
			PageinationData replyList = commentInfoService.queryCommentInfoByBean(bean);
			writeJson(response,replyList);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 删除图片，管理员后台
	 */
	@RequestMapping("**/admin/comment/deleteCommentInfoImg.do")
	public ModelAndView deleteCommentInfoImg(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			commentInfoService.deleteCommentInfoImg(id);
		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "删除失败");
		}
		writeJson(response, "删除成功");
		return null;
	}
	
	public String getRemoteHost(HttpServletRequest request){
	    String ip = request.getHeader("x-forwarded-for");
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){
	        ip = request.getRemoteAddr();
	    }
	    return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;
	}
	
	
}
