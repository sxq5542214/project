package com.yd.business.comment.service.impl;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.sun.image.codec.jpeg.ImageFormatException;
import com.yd.basic.framework.bean.BaseBean;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.runable.BaseRunable;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.CommentInfoImgBean;
import com.yd.business.comment.bean.CommentUserRelationBean;
import com.yd.business.comment.dao.ICommentInfoDao;
import com.yd.business.comment.service.ICommentInfoService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.bean.MsgCenterArticleBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.msgcenter.service.IMsgCenterSendService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.bean.UserWechatConditionBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.util.EmojiUtil;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.FileUploadUtil;
import com.yd.util.HttpUtil;
import com.yd.util.JsonUtil;
import com.yd.util.StringUtil;

@Service("commentInfoService")
public class CommentInfoServiceImpl extends BaseService implements ICommentInfoService {

	@Autowired
	private ICommentInfoDao commentInfoDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IWechatService wechatService;
	@Resource
	protected IConfigAttributeService configAttributeService;
	@Resource
	protected ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	@Override
	public void insertCommentInfo(CommentInfoBean bean) {
		bean.setCreatetime(DateUtil.getNowDateStrSSS());
		bean.setStatus(CommentInfoBean.COMMENT_STATUS_NOCHECK);
		commentInfoDao.insertCommentInfo(bean);
	}

	@Override
	public CommentInfoBean insertCommentInfo(final Object img_arr,String ip,String openid, String msgtext,String conf_id,final String conf_code,String parentid,String replyid, boolean isadmin) {
		final UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
		//获取留言配置信息
		CommentConfigBean confBean = new CommentConfigBean();
		if(StringUtil.isNotNull(conf_code)){
			confBean = this.findCommentConfigInfoByCode(conf_code);
		}else if(StringUtil.isNotNull(conf_id)){
			confBean = this.findCommentConfigInfoById(Integer.valueOf(conf_id));
		}
		final CommentInfoBean bean = new CommentInfoBean();
		CommentInfoBean adminRe = new CommentInfoBean();
		bean.setComment_conf_id(confBean.getId());
		if(!StringUtil.isNull(user)){
			bean.setOpenid(user.getOpenid());
		}else if(!isadmin){
			bean.setIp(ip);
		}
		bean.setType(CommentInfoBean.COMMENT_TYPE_COMMENTINFO);
		if(!StringUtil.isNull(confBean) && checkCommentIsOld(confBean)){
			Integer is_repeat = confBean.getIs_repeat();
			//查询是否有过留言
			List<CommentInfoBean> commentList = commentInfoDao.queryCommentInfoByBeanForPage(bean);
			//未留言或者可重复留言
			if(StringUtil.isNotNull(parentid) || commentList.size() == 0 || (!StringUtil.isNull(is_repeat) && is_repeat.intValue() == CommentConfigBean.COMMENT_CONFIG_IS_REPEAT_YES)){
				//过滤HTML标签
				bean.setMsgtext(StringUtil.guoHtml(msgtext));
				bean.setCreatetime(DateUtil.getNowDateStrSSS());
				bean.setType(CommentInfoBean.COMMENT_TYPE_COMMENTINFO);
				bean.setAgreeCount(CommentInfoBean.COMMENT_AGREECOUNT_DEFALT);
				bean.setReCount(CommentInfoBean.COMMENT_RECOUNT_DEFALT);
				bean.setComment_conf_code(confBean.getActivity_code());
				if(StringUtil.isNotNull(parentid)){
					CommentInfoBean parentBean = this.findCommentInfoWithChildren(Integer.valueOf(parentid));
					if(StringUtil.isNull(parentBean)){
						adminRe.setRemark("留言已失效，无法评论！");
						return adminRe;
					}
					bean.setComment_parentid(Integer.valueOf(parentid));
					bean.setType(CommentInfoBean.COMMENT_TYPE_USER);
					if(!StringUtil.isNull(replyid)){
						CommentInfoBean parentReplyBean = this.findCommentInfoById(Integer.valueOf(replyid));
						if(!StringUtil.isNull(parentReplyBean)){
							bean.setParent_replyid(parentReplyBean.getId());
							bean.setParent_replyUserName(parentReplyBean.getNick_name());
						}
					}else{
						bean.setParent_replyid(Integer.valueOf(parentid));
					}
				}
				
				if(isadmin){
					CustomerBean customer = customerService.getUser();
					//将留言置为管理员已回
					adminRe = this.findCommentInfoWithChildren(Integer.valueOf(parentid));
					adminRe.setIs_adminre(CommentInfoBean.COMMENT_ISADMINRE_YES);
					commentInfoDao.updateCommentInfoBean(adminRe);
					//管理员评论，默认审核通过
					bean.setNick_name(customer.getName());
					bean.setUser_id(customer.getId());
					bean.setHead_img(CommentInfoBean.COMMENT_DEFAULT_ADMIN_IMG);
					bean.setType(CommentInfoBean.COMMENT_TYPE_ADMIN);
					adminRe = bean;
				}else{
					if(confBean.getIs_user_subscribe() == CommentConfigBean.COMMENT_IS_USER_SUBSCRIBE_YES){
						if(StringUtil.isNull(user) || user.getStatus() == UserWechatBean.STATUS_UNSUBSCRIBE){
							adminRe.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.TYPE_COMMENT_NOTIFY_TEXT,"comment_need_user_subscribe"));
							return adminRe;
						}
					}
					if(!StringUtil.isNull(user)){
						bean.setUser_id(user.getId());
					}
					if(StringUtil.isNotNull(openid) && !StringUtil.isNull(user)){
						bean.setOpenid(openid);
						bean.setNick_name(user.getNick_name());
						bean.setHead_img(user.getHead_img());
					}else{
						bean.setIp(ip);
						bean.setNick_name(ip);
						bean.setHead_img(CommentInfoBean.COMMENT_DEFAULT_IMG);
					}
				}
				//处理留言的状态
				dealCommentStatusForCheck(bean,null,isadmin);
				commentInfoDao.insertCommentInfo(bean);
				//上传图片
				taskExecutor.execute(new BaseRunable() {
					@Override
					public void runMethod() {
						try {
							UpLoadImgThread(img_arr,conf_code,bean,user.getOriginalid());
						} catch (ImageFormatException e) {
							log.error(e, e);
						} catch (IOException e) {
							log.error(e, e);
						}
					}
				});
				if(!StringUtil.isNull(confBean.getIs_admin_review()) && confBean.getIs_admin_review() == CommentConfigBean.COMMENT_IS_ADMIN_REVIEW_NO){
//					bean.setRemark("您已成功留言，谢谢参与！");
					bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_UNNEED_REVIEW));
				}else{
//					bean.setRemark("您已成功留言，我们将会竟快审核，谢谢参与！");
					bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_NEED_REVIEW));
				}
				adminRe = bean;
				dealCommentInfoBeanToShow(adminRe);
			}else{
//				adminRe.setRemark("您已参与留言，请勿重复参与！");
				adminRe.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_NOT_REJOIN));
			}
		}else if(!checkCommentIsOld(confBean)){
			if(StringUtil.isNotNull(confBean.getStart_time()) && !StringUtil.isNotNull(confBean.getEnd_time())){
				adminRe.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_NEED_CYC_UNSTART));
//				adminRe.setRemark("留言未开放，开放时间："+confBean.getStart_time().substring(0, 19) +"开始");
			}else if(!StringUtil.isNotNull(confBean.getStart_time()) && StringUtil.isNotNull(confBean.getEnd_time())){
				adminRe.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_NEED_CYC_ENDED));
//				adminRe.setRemark("留言未开放，已于："+confBean.getEnd_time().substring(0, 19) +"结束");
			}else{
				adminRe.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_INTO_NEED_CYC_UNSTART2));
//				adminRe.setRemark("留言未开放，开放时间："+confBean.getStart_time().substring(0, 19) +"到"+confBean.getEnd_time().substring(0, 19));
			}
		}else{
			adminRe.setRemark("留言板配置信息为空，留言失败！");
		}
		return adminRe;
	}
	
	private void dealCommentInfoBeanToShow(CommentInfoBean bean){
		if(StringUtil.isNotNull(bean.getOpenid())){
			bean.setNick_name(userWechatService.findUserWechatByOpenId(bean.getOpenid()).getNick_name());
		}else if(bean.getType() != CommentInfoBean.COMMENT_TYPE_ADMIN){
			bean.setNick_name(bean.getIp());
			bean.setHead_img(CommentInfoBean.COMMENT_DEFAULT_IMG);
		}
	}
	
	/**
	 * 判断留言是否在开放时间内
	 * @param confBean
	 * @return
	 */
	private boolean checkCommentIsOld(CommentConfigBean confBean){
		boolean is_old = true;
		String nowDate = DateUtil.getNowDateStrSSS();
		if(StringUtil.isNotNull(confBean.getStart_time())){
			if(nowDate.compareTo(confBean.getStart_time()) < 0){
				is_old = false;
			}
		}
		if(StringUtil.isNotNull(confBean.getEnd_time())){
			if(nowDate.compareTo(confBean.getEnd_time()) > 0){
				is_old = false;
			}
		}
		return is_old;
	}
	
	/**
	 * 获取留言配置信息
	 */
	@Override
	public CommentConfigBean findCommentConfigInfoById(int id) {
		CommentConfigBean bean = new CommentConfigBean();
		bean.setId(id);
		List<CommentConfigBean> beanList = commentInfoDao.queryCommentConfigInfoByBean(bean);
		if(beanList.size() > 0){
			return beanList.get(0);
		}
		return null;
	}
	
	/**
	 * 处理留言/评论的审核状态
	 * @param bean     留言/评论对象
	 * @param status   状态
	 * @param isadmin  是否是管理员回复
	 */
	private void dealCommentStatusForCheck(CommentInfoBean bean,Integer status,boolean isadmin){
		CommentInfoBean infoBean = new CommentInfoBean();
		CommentConfigBean commentConfig = new CommentConfigBean();
		//此处区分了审核和新增
		if(!StringUtil.isNull(bean.getId())){
			infoBean = this.findCommentInfoById(bean.getId());
			commentConfig = this.findCommentConfigInfoById(infoBean.getComment_conf_id());
		}else{
			infoBean = bean;
			commentConfig = this.findCommentConfigInfoById(bean.getComment_conf_id());
		}
		//是否需要审核为空或者需要审核，都是要将状态置为未审核
		if(StringUtil.isNull(commentConfig.getIs_admin_review()) || commentConfig.getIs_admin_review() == CommentConfigBean.COMMENT_IS_ADMIN_REVIEW_YES){
			bean.setStatus(CommentInfoBean.COMMENT_STATUS_NOCHECK);
		}else if(!StringUtil.isNull(commentConfig.getIs_admin_review()) && commentConfig.getIs_admin_review() == CommentConfigBean.COMMENT_IS_ADMIN_REVIEW_NO){
			bean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKED);
		}
		//状态不为空   跟状态走
		if(!StringUtil.isNull(status)){
			bean.setStatus(status);
		}
		//管理员的评论默认为审核通过
		if(isadmin){
			bean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKED);
		}
		if(!StringUtil.isNull(infoBean.getComment_parentid())){
			CommentInfoBean parentBean = this.findCommentInfoById(infoBean.getComment_parentid());
			CommentInfoBean parentReplyBean = this.findCommentInfoById(infoBean.getParent_replyid());
			//真实存在的用户发消息
			if(!StringUtil.isNull(parentReplyBean.getOpenid())){
				UserWechatBean user = userWechatService.findUserWechatByOpenId(parentReplyBean.getOpenid());
				WechatOriginalInfoBean info = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(user.getOriginalid());
				String link_url = commentConfig.getLink_url();
				//修改链接的参数
				if(StringUtil.isNotNull(link_url)){
					link_url = link_url + "&commentId=" + parentBean.getId().toString();
					link_url = link_url.replaceAll("#action_openid#", parentReplyBean.getOpenid());
					link_url = link_url.replaceAll("#server_domain#", info.getServer_domain());
					link_url = link_url.replaceAll("#server_url#", info.getServer_url());
				}
				infoBean.setLink_url(link_url);
				infoBean.setParent_msg(parentReplyBean.getMsgtext());
				infoBean.setConfig_name(commentConfig.getName());
				String nick_name = infoBean.getNick_name();
				infoBean.setNick_name("作者回复");
				if(!StringUtil.isNull(bean.getStatus()) && bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKED){
					//给留言消息数量+1
					commentInfoDao.updateCommentInfoReCount(parentBean.getId(), CommentInfoBean.COMMENT_AGREECOUNT_ADD);
					//发消息给用户,捕捉异常
					try {
						msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_GET_NEW_COMMENT_REMIND , null, infoBean);
					} catch (Exception e) {
						log.error(e, e);
					}
				}else if(!StringUtil.isNull(bean.getStatus()) && bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKFAIL){
					//审核不通过留言
					//发消息给用户
//					msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_WECHAT_USER_GET_NEW_COMMENT_REMIND , null, bean);
				}
				infoBean.setNick_name(nick_name);
			}
			
		}
		
	}
	
	public CommentConfigBean findCommentConfigInfoByCode(String code) {
		CommentConfigBean bean = new CommentConfigBean();
		bean.setActivity_code(code);
		List<CommentConfigBean> beanList = commentInfoDao.queryCommentConfigInfoByBean(bean);
		if(beanList.size() > 0 && StringUtil.isNotNull(code)){
			return beanList.get(0);
		}
		return null;
	}

	/**
	 * 获取留言列表用于展示，展示用于区分自己的还是别人的
	 */
	@Override
	public List<CommentInfoBean> showCommentInfoByConfCode(String code,String openid,String ip,String id,String nowpage,String idName) {
		//先获取留言配置数据
		CommentConfigBean confBean = new CommentConfigBean();
		confBean = findCommentConfigInfoByCode(code);
		if (!StringUtil.isNull(confBean.getIs_show()) && CommentConfigBean.COMMENT_IS_SHOW_NO == confBean.getIs_show()) {
			List<CommentInfoBean> returnBean = new ArrayList<CommentInfoBean>();
			CommentInfoBean bean = new CommentInfoBean();
			bean.setRemark("NOSHOW");
			returnBean.add(bean);
			return returnBean;
		}
		List<CommentInfoBean> beanList = new ArrayList<CommentInfoBean>();
		List<String> openids = returnOpenidsToQueryComment(idName,openid);
		//通用查询
		beanList = getCommentInfoList(confBean, openid, openids, idName, ip, nowpage);
		//查询自己评论的别人的留言
		if(beanList.size() > 0){
			//查询留言的回复
			List<CommentInfoBean> replyBeanList = commentInfoDao.queryCommentInfoByParentId(beanList);
			//查询留言的配图
			List<CommentInfoImgBean> imgBeanList = commentInfoDao.queryCommentImgInfoByInfoId(beanList);
			CommentUserRelationBean reBean = new CommentUserRelationBean();
			if(StringUtil.isNotNull(openid)){
				reBean.setOpenid(openid);
			}else{
				reBean.setIp(ip);
			}
			//判断用户有没有点赞过
			List<CommentUserRelationBean> reBeanList = commentInfoDao.queryCommentUserRelationByBean(reBean);
			sortCommentInfoBeanByDIY(beanList,replyBeanList,imgBeanList,reBeanList,true,nowpage);
			filterUserInfo(beanList);
		}

		return beanList;
	}

	/**
	 * 自定义评论的排序，将留言的评论和晒图拍好顺序后返回
	 * @param commentList
	 * @param replyList
	 * @param imgList
	 * @return
	 */
	public void sortCommentInfoBeanByDIY(List<CommentInfoBean> commentList,List<CommentInfoBean> replyList,List<CommentInfoImgBean> imgList,List<CommentUserRelationBean> reBeanList,boolean statusChecked,String nowpage){
		//循环beanlist得到留言的map集合
		Map<Integer,CommentInfoBean> commentMap = new HashMap<Integer,CommentInfoBean>();
		for (CommentInfoBean commentBean : commentList) {
			commentBean.setNowpage(Integer.valueOf(nowpage)+1);
			if(StringUtil.isNotNull(commentBean.getCreatetime())){
				try {
					commentBean.setCreatetime(commentBean.getCreatetime().substring(0, 19));
				} catch (Exception e) {
					log.error(e, e);
				}
			}
			commentMap.put(commentBean.getId(), commentBean);
		}
		if(!StringUtil.isNull(imgList)){
			//循环晒图列表，将图片归属到留言里面
			for (CommentInfoImgBean imgBean : imgList) {
				CommentInfoBean commentBean = commentMap.get(imgBean.getInfo_id());
				if(StringUtil.isNull(commentBean.getImgBeanList())){
					List<CommentInfoImgBean> list = new ArrayList<CommentInfoImgBean>();
					list.add(imgBean);
					commentBean.setImgBeanList(list);
				}else{
					commentBean.getImgBeanList().add(imgBean);
				}
			}
		}
		//循环replylist得到留言的map集合
		Map<Integer,CommentInfoBean> replyMap = new HashMap<Integer,CommentInfoBean>();
		if(!StringUtil.isNull(replyList)){
			Collections.sort(replyList,new Comparator<CommentInfoBean>(){   
		           public int compare(CommentInfoBean arg0, CommentInfoBean arg1) {   
		               return arg0.getCreatetime().compareTo(arg1.getCreatetime());   
		            }   
		        }); 
			for (CommentInfoBean replyBean : replyList) {
				commentMap.put(replyBean.getId(), replyBean);
				//将评论也放入map
				replyMap.put(replyBean.getParent_replyid(), replyBean);
				//展示已审核的评论
			}
			for(CommentInfoBean replyBean : replyList){
				if(replyBean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKED || !statusChecked){
					CommentInfoBean commentInfo = commentMap.get(replyBean.getParent_replyid());
					if(!StringUtil.isNull(commentInfo)){
						CommentInfoBean commentPInfo = commentMap.get(commentInfo.getId());
						if(!StringUtil.isNull(commentPInfo.getReplyBeanList())){
							commentPInfo.getReplyBeanList().add(replyBean);
						}else{
							List<CommentInfoBean> list = new ArrayList<CommentInfoBean>();
							list.add(replyBean);
							commentPInfo.setReplyBeanList(list);
						}
					}

				}
			}
		}
		if(!StringUtil.isNull(reBeanList)){
			//用户是否过点赞
			for (CommentUserRelationBean commentUserRelationBean : reBeanList) {
				CommentInfoBean infoBean = commentMap.get(commentUserRelationBean.getCommentid());
				if(!StringUtil.isNull(infoBean)){
					infoBean.setIs_voted(CommentInfoBean.COMMENT_ISVOTED_YES);
				}
			}
		}
	}
	
//	/**
//	 * 自定义评论的排序
//	 * @param newList
//	 * @param comment
//	 * @param commentList
//	 * @return
//	 */
//	private List<CommentInfoBean> sortCommentInfoBeanByDIY(List<CommentInfoBean> newList,CommentInfoBean comment,List<CommentInfoBean> commentList){
//		if(!newList.contains(comment)){
//			newList.add(comment);
//			for (CommentInfoBean commentInfoBean : commentList) {
//				if(comment.getId().compareTo(commentInfoBean.getId()) != 0){
//					if(!StringUtil.isNull(commentInfoBean.getParent_replyid())){
//						if(commentInfoBean.getParent_replyid().compareTo(comment.getId()) == 0){
//							sortCommentInfoBeanByDIY(newList,commentInfoBean,commentList);
//						}
//					}
//				}
//			}
//		}
//		return newList;
//	}
	
	/**
	 * 返回需要查询的用户openid集合
	 * @return
	 */
	private List<String> returnOpenidsToQueryComment(String idName,String openid){
		List<String> openids = new ArrayList<String>();
		if(StringUtil.isNotNull(openid)){
			List<UserWechatBean> ownerUserList = new ArrayList<UserWechatBean>();
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(!StringUtil.isNull(user) && !StringUtil.isNull(user.getUnionid())){
				//根据用户的uninid得到系统的所有该用户（多个公众号的同一用户）
				UserWechatConditionBean queryBean = new UserWechatConditionBean();
				queryBean.setUnionid(user.getUnionid());
				queryBean.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
				ownerUserList = userWechatService.queryUser(queryBean);
			}else{
				ownerUserList.add(user);
			}
			if(CommentInfoBean.COMMENT_BRLONG_FRIEND.equals(idName)){
				//查询父用户和子用户的留言消息
				//1、根据openid得到用户信息
				if(ownerUserList.size() > 0){
					for (UserWechatBean userWechatBean : ownerUserList) {
						UserWechatBean parentUser = userWechatService.findUserWechatById(userWechatBean.getParentid());
						UserWechatBean queryUser = new UserWechatBean();
						queryUser.setParentid(userWechatBean.getId());
						queryUser.setStatus(UserWechatBean.STATUS_SUBSCRIBE);
						List<UserWechatBean> childrenUserList = userWechatService.list(queryUser);
						for (UserWechatBean childrenUser : childrenUserList) {
							openids.add(childrenUser.getOpenid());
						}
						//父用户处于关注状态
						if(!StringUtil.isNull(parentUser) && parentUser.getStatus() == UserWechatBean.STATUS_SUBSCRIBE){
							openids.add(parentUser.getOpenid());
						}
					}
				}
			}else if(CommentInfoBean.COMMENT_BRLONG_OWNERL.equals(idName)){
				for (UserWechatBean userWechatBean : ownerUserList) {
					openids.add(userWechatBean.getOpenid());
				}
			}
		}
		
		return openids;
	}
	
	/**
	 * 获取留言列表
	 * @param code
	 * @param openid
	 * @param openids 用于查询留言的openid集合，一般情况下只有一个值，但是对于查询父子用户的留言信息的时候就会有多个值
	 * @param id
	 * @param nowpage
	 * @return
	 */
	private List<CommentInfoBean> getCommentInfoList(CommentConfigBean confBean,String openid,List<String> openids,String idName,String ip,String nowpage){
		Integer pageSize = confBean.getPageSize();
		CommentInfoBean bean = new CommentInfoBean();
		bean.setPageSize(pageSize);
		if(StringUtil.isNotNull(nowpage)){
			bean.setNowpage(Integer.valueOf(nowpage));
		}
		if(!StringUtil.isNull(confBean.getId())){
			bean.setComment_conf_id(confBean.getId());
		}
		bean.setOrderby(confBean.getOrderby());
		//审核通过的留言
		bean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKED);
		//查询留言（非回复内容）
		bean.setType(CommentInfoBean.COMMENT_TYPE_COMMENTINFO);
		List<CommentInfoBean> beanList = new ArrayList<CommentInfoBean>();
		bean.setOpenids(openids);
		if(CommentInfoBean.COMMENT_BRLONG_OWNERL.equals(idName)){
			beanList = commentInfoDao.queryUserAllCommentByBean(bean);
		}else{
			//有openid，返回该用户的所有留言含分页
			if(StringUtil.isNull(openids) || openids.size() == 0){
				//openid集合为空，好友留言直接返回空
				if(CommentInfoBean.COMMENT_BRLONG_FRIEND.equals(idName)){
					return beanList;
				}
			}
			beanList = commentInfoDao.queryCommentInfoByBeanForPage(bean);
		}
		return beanList;
	}
	
	/**
	 * //留言点赞是否需要关注的判断
	 * @param user
	 * @param ip
	 * @param openid
	 * @param confBean
	 * @return
	 */
	private boolean getIsUserSubscribe(UserWechatBean user,String ip,String openid,CommentConfigBean confBean){
		boolean flag = false;
		//openid不为空
		if(StringUtil.isNotNull(openid)){
			//获取用户是否关注公众号
			user = userWechatService.findUserWechatByOpenId(openid);
		}
		//留言点赞是否需要关注的判断
		if(!StringUtil.isNull(confBean.getVote_is_user_subscribe()) && confBean.getVote_is_user_subscribe() == CommentConfigBean.COMMENT_VOTE_IS_USER_SUBSCRIBE_YES){
			//用户存在，并且用户关注
			if(!StringUtil.isNull(user) && user.getStatus() == UserWechatBean.STATUS_SUBSCRIBE){
				flag = true;
			}
		}else{
			//不需要关注
			if(!StringUtil.isNull(user) || StringUtil.isNotNull(ip)){
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 用户点赞的处理（即投票处理）
	 */
	@Override
	public CommentInfoBean dealUserAgreeCommentInfo(String ip,String openid,String commentid,String action) {
		CommentInfoBean bean = new CommentInfoBean();
		UserWechatBean  user = null;
		//获取用户
		if(StringUtil.isNotNull(commentid)){
			//获取该留言对于评论的权限（评论数限制等。。。）
			CommentInfoBean infoBean = this.findCommentInfoWithChildren(Integer.valueOf(commentid));
			CommentConfigBean confBean = this.findCommentConfigInfoById(infoBean.getComment_conf_id());
			boolean is_user_subscribe = getIsUserSubscribe(user,ip,openid,confBean);
			if(!is_user_subscribe && !StringUtil.isNull(confBean.getVote_is_user_subscribe()) && confBean.getVote_is_user_subscribe() == CommentConfigBean.COMMENT_VOTE_IS_USER_SUBSCRIBE_YES){
				//提示信息
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.TYPE_COMMENT_NOTIFY_TEXT,"comment_vote_need_user_subscribe"));
				return bean;
			}else if(!is_user_subscribe){
				//提示唯一性数据获取失败
				bean.setRemark("数据获取错误！");
				return bean;
			}
			if(StringUtil.isNotNull(openid)){
				user = userWechatService.findUserWechatByOpenId(openid);
			}
			boolean isVoted = checkUserIsVoted(confBean,user,infoBean,ip);
			//每人的投票次数
			int per_voted_num = confBean.getPer_voted_num();
			//投票的时间限制
			String startTime = confBean.getStart_time();
			String endTime = confBean.getEnd_time();
			String nowTime = DateUtil.getNowDateStrSSS();
			if(nowTime.compareTo(endTime) > 0 || nowTime.compareTo(startTime) < 0){
				if(StringUtil.isNull(confBean.getIs_voted_expired()) || confBean.getIs_voted_expired() == CommentConfigBean.COMMENT_IS_VOTED_EXPIRED_CYC){
//					bean.setRemark("投票已暂停！");
					bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_NEED_CYC));
					return bean;	
				}
			}
			//投票周期
			int voted_cycle = confBean.getVoted_cycle();
			CommentUserRelationBean reBean = new CommentUserRelationBean();
			if(!StringUtil.isNull(user)){
				reBean.setOpenid(user.getOpenid());
			}else{
				reBean.setIp(ip);
			}
			reBean.setCommentid(Integer.valueOf(commentid));
			//判断用户有没有点赞过
			List<CommentUserRelationBean> reBeanList = commentInfoDao.queryCommentUserRelationByBean(reBean);
			if(StringUtil.isNotNull(action)){
				//点赞
				if(Integer.valueOf(action) == CommentInfoBean.COMMENT_AGREECOUNT_ADD){
					if(isVoted && reBeanList.size() == 0){
						//记录点赞记录
						reBean.setCreatetime(DateUtil.getNowDateStrSSS());
						commentInfoDao.insertCommentUserRelation(reBean);
						commentInfoDao.updateCommentInfoAgreeCount(Integer.valueOf(commentid),CommentInfoBean.COMMENT_AGREECOUNT_ADD);
					}else if(reBeanList.size() == 0 && per_voted_num >= CommentConfigBean.COMMENT_PERVOTEDNUM_WITHOUTLIMIT){
						//投票周期每天
						if(voted_cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_DAY){
							bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_OUTCYC_DAY));
//							bean.setRemark("投票次数超过限定（每天"+per_voted_num+"次）！请明天再来！");
						//投票周期每周
						}else if(voted_cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_WEEK){
							bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_OUTCYC_WEEK));
//							bean.setRemark("投票次数超过限定（每周"+per_voted_num+"次）！请下周再来！");
						//投票周期每月	
						}else if(voted_cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_MONTH){
							bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_OUTCYC_MONTH));
//							bean.setRemark("投票次数超过限定（每月"+per_voted_num+"次）！请下个月再来！");
						//投票周期每年
						}else if(voted_cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_YEAR){
							bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_OUTCYC_YEAR));
//							bean.setRemark("投票次数超过限定（每年"+per_voted_num+"次）！请明年再来！");
						}
					}else if(per_voted_num < CommentConfigBean.COMMENT_PERVOTEDNUM_WITHOUTLIMIT){
						bean.setRemark(replaceRemarkInfo(confBean,CommentConfigBean.COMMENT_VOTED_IS_STOP));
//						bean.setRemark("投票已暂停！");
					}
				//取消点赞
				}else if(Integer.valueOf(action) == CommentInfoBean.COMMENT_AGREECOUNT_MINUS){
					if(reBeanList.size() > 0){
						//删除点赞记录
//						deleteCommentInfoVotedInfo(openid);(删除全部的记录，此处不适用)
						commentInfoDao.deleteCommentUserRelation(reBean);
						commentInfoDao.updateCommentInfoAgreeCount(Integer.valueOf(commentid),CommentInfoBean.COMMENT_AGREECOUNT_MINUS);
					}
				}
			}
		}
		return bean;
	}

	private String replaceRemarkInfo(Object mm,String key){
		String content = configCruxService.getValueByTypeAndKey(ConfigCruxBean.TYPE_COMMENT_NOTIFY_TEXT,key);
		String param = new JSONObject(mm).toString();
		JSONObject jso = null;  //参数转换为json对象
		if(StringUtil.isNotNull(param)){
			jso = new JSONObject(param);
			content = content+" ";
			String[] strs = content.split("#");
			StringBuilder sb = new StringBuilder();
			boolean isFind = false;
			for(int i = 0 ; i < strs.length ; i++){
				String str = strs[i];
					String param_key = strs[i];
					str = JsonUtil.getParamValueByKey(jso, param_key);

					//判断是否有找到参数
					if(param_key.equals(str)){
						//没找到的话，判断上一个参数有没有找到，如果本次和上次都没有找到，就在开始加上#号
						if(!isFind && i != 0){
							sb.append("#");
						}
						isFind = false;
					}else{
						isFind = true;
					}
				sb.append(str);
			}
			return sb.toString();
		}
		return content;
	}
	
	/**
	 * 用户取消关注，删除该用户所有点赞记录
	 */
	@Override
	public void deleteCommentInfoVotedInfo(String openid){
		CommentUserRelationBean reBean = new CommentUserRelationBean();
		reBean.setOpenid(openid);
		List<CommentUserRelationBean> reBeanList = commentInfoDao.queryCommentUserRelationByBean(reBean);
		for (CommentUserRelationBean commentUserRelationBean : reBeanList) {
			//删除点赞记录
			commentInfoDao.deleteCommentUserRelation(commentUserRelationBean);
			commentInfoDao.updateCommentInfoAgreeCount(commentUserRelationBean.getCommentid(),CommentInfoBean.COMMENT_AGREECOUNT_MINUS);
		}
	}
	
	/**
	 * 判断用户是否可以投票
	 * @param per_voted_num
	 * @param voted_cycle
	 * @param user
	 * @param infoBean
	 * @return
	 */
	private boolean checkUserIsVoted(CommentConfigBean confBean,UserWechatBean user,CommentInfoBean infoBean,String ip){
		//每人的投票次数
		int per_voted_num = confBean.getPer_voted_num();
		//投票周期
		int voted_cycle = confBean.getVoted_cycle();
		//初始化返回参数
		boolean isVoted = true;
		//获取用户的投票次数
		CommentUserRelationBean reBean = new CommentUserRelationBean();
		if(!StringUtil.isNull(user)){
			reBean.setOpenid(user.getOpenid());
		}else{
			reBean.setIp(ip);
		}
		
		List<CommentUserRelationBean> reBeanList = commentInfoDao.queryCommentUserRelationByBean(reBean);
		//投票数无限制
		if(per_voted_num == CommentConfigBean.COMMENT_PERVOTEDNUM_WITHOUTLIMIT){
			isVoted = true;
		//投票次数有限制
		}else if(per_voted_num > CommentConfigBean.COMMENT_PERVOTEDNUM_WITHOUTLIMIT){
			long current=System.currentTimeMillis();//当前时间毫秒数  
			//获取周期起始时间
			String beginDate = getCycleBeginDate(current,voted_cycle);
			//获取周期结束时间
			String endDate = getCycleEndDate(current,voted_cycle);
			int number = 0;
			for (CommentUserRelationBean commentUserRelationBean : reBeanList) {
				if(commentUserRelationBean.getCreatetime().compareTo(beginDate) > 0 && commentUserRelationBean.getCreatetime().compareTo(endDate) < 0){
					number ++;
				}
			}
			if(number < per_voted_num){
				isVoted = true;
			}else{
				isVoted = false;
			}
		}else{
			isVoted = false;
		}
		//可投票，检查该留言是否已经投过票
		if(isVoted){
			for (CommentUserRelationBean commentUserRelationBean : reBeanList) {
				//留言已经投过了
				if(commentUserRelationBean.getCommentid().compareTo(infoBean.getId()) == 0){
					isVoted = false;
					return isVoted;
				}
			}
		}
		return isVoted;
	}
	
	   /**
		 * 获取当前周期第一天日期
		 * @param date
		 * @param sf_date
		 * @return
		 */ 
	private String getCycleBeginDate(long current,int cycle){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(current);
		//投票周期每天
		if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_DAY){
			return DateUtil.getNowDateBegin(current);
		//投票周期每周
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_WEEK){
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		//投票周期每月	
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_MONTH){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
		//投票周期每年
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_YEAR){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
		}
		Date beginDate = calendar.getTime();
		beginDate.setHours(0);
		beginDate.setMinutes(0);
		beginDate.setSeconds(0);
		return DateUtil.formatDateSSS(beginDate);
	}
		
	    /**
		 * 获取当前周期最后一天日期
		 * @param date
		 * @param sf_date
		 * @return
		 */ 
	private String getCycleEndDate(long current,int cycle){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(current);
		//投票周期每天
		if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_DAY){
		   return DateUtil.getNowDateEnd(current);
		//投票周期每周
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_WEEK){
			calendar.add(Calendar.DATE, -1);
			calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY); 
			calendar.add(Calendar.DATE, 1);
		//投票周期每月	
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_MONTH){
			calendar.add(Calendar.MONTH, 1);
			calendar.set(Calendar.DAY_OF_MONTH, 1); 
			calendar.add(Calendar.DATE, -1);
			
		//投票周期每年
		}else if(cycle == CommentConfigBean.COMMENT_VOTED_CYCLE_YEAR){
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.add(Calendar.YEAR, 1);
			calendar.add(Calendar.DATE, -1);
				
		}
		Date beginDate = calendar.getTime();
		beginDate.setHours(23);
		beginDate.setMinutes(59);
		beginDate.setSeconds(59);
		return DateUtil.formatDateSSS(beginDate);
	}
	
	/**
	 * 返回留言的分页信息
	 */
	@Override
	public PageinationData queryCommentInfoByBean(CommentInfoBean bean) throws Exception {
		PageinationData pd = new PageinationData();
		List<CommentInfoBean> list = new ArrayList<CommentInfoBean>();
		int count = 0;
		//审核不通过留言
		if(!StringUtil.isNull(bean.getStatus()) && CommentInfoBean.COMMENT_STATUS_CHECKFAIL == bean.getStatus()){
			bean.setType(CommentInfoBean.COMMENT_TYPE_COMMENTINFO);
			count = commentInfoDao.queryFailCommentInfoCountByBean(bean);
			list = commentInfoDao.queryFailCommentInfoWithParent(bean);
			bean.setType(null);
		}else if(!StringUtil.isNull(bean.getType()) && CommentInfoBean.COMMENT_TYPE_USER != bean.getType()){
			count = commentInfoDao.queryCommentInfoCountByBean(bean);
			list = commentInfoDao.queryCommentInfoByBeanForPage(bean);
		}else{
			count = commentInfoDao.queryCommentInfoCountByBean(bean);
			list = commentInfoDao.queryCommentInfoWithParent(bean);
		}
		
		if(!StringUtil.isNull(bean.getComment_parentid())){
			List<CommentInfoBean> beanList = new ArrayList<CommentInfoBean>();
			bean.setId(bean.getComment_parentid());
			beanList.add(bean);
			sortCommentInfoBeanByDIY(beanList,list,null,null,false,"0");
			pd.setDataList(beanList);
			filterUserInfo(beanList);
		}else{
			if(list.size() > 0){
				List<CommentInfoImgBean> imgList = commentInfoDao.queryCommentImgInfoByInfoId(list);
				sortCommentInfoBeanByDIY(list,null,imgList,null,false,"0");
			}
			pd.setDataList(list);
			filterUserInfo(list);
		}
		pd.setNowpage(bean.getNowpage() );
		pd.setTotalcount(count);
		pd.calculateTotalPage();
		return pd;
		
	}

	/**
	 * 审核留言
	 */
	@Override
	public List<CommentInfoBean> checkCommentInfoByAction(String action,String id) {
		List<CommentInfoBean> returnList = new ArrayList<CommentInfoBean>();
		if(StringUtil.isNotNull(id)){
			List<String> ids = Arrays.asList(id.split(","));
			for (String string : ids) {
				CommentInfoBean bean = new CommentInfoBean();
				//审核通过
				if(CommentInfoBean.COMMENT_STATUS_CHECKED_ACTION.equals(action) && StringUtil.isNotNull(string)){
					bean.setId(Integer.valueOf(string));
					//处理留言的状态
					dealCommentStatusForCheck(bean,CommentInfoBean.COMMENT_STATUS_CHECKED,false);
//					bean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKED);
					bean.setModifytime(DateUtil.getNowDateStrSSS());
					commentInfoDao.updateCommentInfoBean(bean);
				}else if(CommentInfoBean.COMMENT_STATUS_CHECKFAIL_ACTION.equals(action) && StringUtil.isNotNull(string)){
					bean.setId(Integer.valueOf(string));
					//审核不通过留言的处理
					checkFailCommentInfo(bean,true);
				}else if(CommentInfoBean.COMMENT_STATUS_CHECKDEL_ACTION.equals(action) && StringUtil.isNotNull(string)){
					bean.setId(Integer.valueOf(string));
					//删除留言的处理
					checkFailCommentInfo(bean,false);
				}else if(CommentInfoBean.COMMENT_STATUS_REVCOVER_ACTION.equals(action) && StringUtil.isNotNull(string)){
					bean.setId(Integer.valueOf(string));
					//恢复留言的处理
					recoverCommentInfo(bean);
				}
				returnList.add(bean);
			}
		}
		return returnList;
	}

	/**
	 * 还原留言信息
	 * @param bean
	 */
	private void recoverCommentInfo(CommentInfoBean bean){
		//查询备份表数据
		List<CommentInfoBean> backCommentInfoList = this.queryFailCommentList(bean);
		if(backCommentInfoList.size() > 0){
			//恢复数据
			for (CommentInfoBean commentInfoBean : backCommentInfoList) {
				commentInfoDao.deleteFailCommentInfo(commentInfoBean);
				commentInfoBean.setId(null);
				commentInfoBean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKRECOVER);
				commentInfoDao.insertCommentInfo(commentInfoBean);
				List<CommentInfoBean> reCommentList = commentInfoBean.getReplyBeanList();
				for (CommentInfoBean commentInfoBean2 : reCommentList) {
					commentInfoDao.deleteFailCommentInfo(commentInfoBean2);
					commentInfoBean2.setId(null);
					commentInfoBean2.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKRECOVER);
					commentInfoDao.insertCommentInfo(commentInfoBean2);
				}
			}
		}
	}
	
	/**
	 * 查询留言信息包含评论信息（审核不通过数据）
	 * @return
	 */
	private List<CommentInfoBean> queryFailCommentList(CommentInfoBean bean){
		//查询备份表数据
		List<CommentInfoBean> backCommentInfoList = commentInfoDao.queryFailCommentInfoByBean(bean);
		if(backCommentInfoList.size() > 0){
			List<CommentInfoBean> backReCommentInfoList = commentInfoDao.queryFailCommentInfoByParentId(backCommentInfoList);
			for (CommentInfoBean commentInfoBean : backCommentInfoList) {
				List<CommentInfoBean> reCommentList = new ArrayList<CommentInfoBean>();
				for (CommentInfoBean reComment : backReCommentInfoList) {
					if(reComment.getComment_parentid() == commentInfoBean.getId()){
						reCommentList.add(reComment);
					}
				}
				//获取留言的评论，如果由的话
				commentInfoBean.setReplyBeanList(reCommentList);
			}
		}
		return backCommentInfoList;
	}
	
	/**
	 * 审核不通过留言的处理方法,将留言表里面的数据删除，备份到back表里
	 */
	private void checkFailCommentInfo(CommentInfoBean bean,boolean is_back){
		try {
			//将信息添加近备份表，将留言表数据删除
			bean = this.findCommentInfoWithChildren(bean.getId());
			CommentInfoBean parentBean = this.findCommentInfoById(bean.getComment_parentid());
			//废弃的是通过的留言，留言数-1
			if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKED){
				if(!StringUtil.isNull(parentBean)){
					commentInfoDao.updateCommentInfoReCount(parentBean.getId(), CommentInfoBean.COMMENT_AGREECOUNT_MINUS);
				}
			}
			//处理留言的状态
			dealCommentStatusForCheck(bean,CommentInfoBean.COMMENT_STATUS_CHECKFAIL,false);
//			bean.setStatus(CommentInfoBean.COMMENT_STATUS_CHECKFAIL);
			bean.setModifytime(DateUtil.getNowDateStrSSS());
			if(is_back){
				commentInfoDao.insertCommentInfoToBackTable(bean);
			}
			CommentInfoBean querybean = new CommentInfoBean();
			querybean.setComment_parentid(bean.getId());
			List<CommentInfoBean> reList = commentInfoDao.queryCommentInfoByBeanForPage(querybean);
			//将留言下面的评论全部废弃
			for (CommentInfoBean commentInfoBean : reList) {
				//处理留言的状态
				dealCommentStatusForCheck(commentInfoBean,CommentInfoBean.COMMENT_STATUS_CHECKFAIL,false);
				commentInfoBean.setModifytime(DateUtil.getNowDateStrSSS());
				if(is_back){
					commentInfoDao.insertCommentInfoToBackTable(commentInfoBean);
				}
				commentInfoDao.deleteCommentInfo(commentInfoBean);
			}
			commentInfoDao.deleteCommentInfo(bean);
		} catch (Exception e) {
			log.error(e, e);
		}
	}
	
	/**
	 * 获取留言信息并且带上管理员的评论  如果有的话
	 */
	@Override
	public CommentInfoBean findCommentInfoWithChildren(int id) {
		CommentInfoBean bean = new CommentInfoBean();
		bean.setId(id);
		List<CommentInfoBean> list = commentInfoDao.queryCommentInfoByBeanForPage(bean);
		filterUserInfo(list);
		if(list.size() > 0){
			bean = list.get(0);
			//获取管理员的评论
			List<CommentInfoBean> reList = commentInfoDao.queryCommentInfoByParentId(list);
			List<CommentInfoBean> newReList = new ArrayList<CommentInfoBean>();
			for (CommentInfoBean commentInfoBean : reList) {
				if(commentInfoBean.getType() == CommentInfoBean.COMMENT_TYPE_ADMIN){
					if(!StringUtil.isNull(commentInfoBean.getUser_id())){
						//获取该留言是那个客户回复的
						CustomerBean customer = customerService.findCustomerById(commentInfoBean.getUser_id());
						if(!StringUtil.isNull(customer)){
							commentInfoBean.setNick_name(customer.getName());
						}
					}
					newReList.add(commentInfoBean);
				}
			}
			bean.setReplyBeanList(newReList);
			return bean;
		}
		return null;
	}

	@Override
	public void deleteCommentInfo(String id) {
		if(StringUtil.isNotNull(id)){
			CommentInfoBean bean = new CommentInfoBean();
			bean.setId(Integer.valueOf(id));
			commentInfoDao.deleteCommentInfo(bean);
		}
	}

	/**
	 * 初始化留言配置信息
	 */
	@Override
	public void initCommentConfig(String code,String start_time,String end_time,String link_url,String name) {
		if(StringUtil.isNotNull(code)){
			//检查code在数据库中是否存在，存在就不新增了
			CommentConfigBean condifgBean = this.findCommentConfigInfoByCode(code);
			if(StringUtil.isNull(condifgBean)){
				//获取默认值
				CommentConfigBean defaultConfigBean = this.findCommentConfigInfoByCode(CommentConfigBean.COMMENT_DEDAULT_INFO);
				//新增配置实体
				defaultConfigBean.setId(null);
				defaultConfigBean.setCreate_time(DateUtil.getNowDateStrSSS());
				defaultConfigBean.setActivity_code(code);
				defaultConfigBean.setStart_time(start_time);
				defaultConfigBean.setEnd_time(end_time);
				defaultConfigBean.setLink_url(link_url);
				defaultConfigBean.setName(name);
				commentInfoDao.insertCommentConfig(defaultConfigBean);
			}
		}
	}	
	
	/**
	 * 过滤用户的敏感信息，用于留言的展示
	 */
	private void filterUserInfo(List<CommentInfoBean> reList){
		if(!StringUtil.isNull(reList)){
			for (CommentInfoBean commentInfoBean : reList) {
				commentInfoBean.setOpenid(null);
				commentInfoBean.setUser_id(null);
				List<CommentInfoBean> replyList = commentInfoBean.getReplyBeanList();
				if(!StringUtil.isNull(replyList)){
					filterUserInfo(replyList);
				}
			}
		}
	}

	@Override
	public List<CommentConfigBean> queryCommentConfigByBean(CommentConfigBean bean) {
		List<CommentConfigBean> beanList = new ArrayList<CommentConfigBean>();
		beanList = commentInfoDao.queryCommentConfigInfoByBean(bean);
		return beanList;
	}

	@Override
	public CommentInfoBean findCommentInfoById(Integer id) {
		if(StringUtil.isNull(id)){
			return null;
		}
		CommentInfoBean bean = new CommentInfoBean();
		bean.setId(id);
		List<CommentInfoBean> list = commentInfoDao.queryCommentInfoByBean(bean);
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
	}
	
	
	/**
	 * 上传图片和保存图片的方法
	 * 
	 * @author BoBo
	 * 
	 */
	public void UpLoadImgThread(Object img_arr, String code, CommentInfoBean bean, String originalid) throws ImageFormatException, IOException {
		if (!StringUtil.isNull(img_arr)) {
			String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
			WechatOriginalInfoBean originalInfo = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(originalid);
			String access_token = originalInfo.getAccess_token();
			String url = server_url + WechatConstant.DOWNLOAD_IMG + "?access_token=" + access_token;
			List<String> mediaList = Arrays.asList(img_arr.toString().split(","));
			List<String> urlList = new ArrayList<String>();
			for (String media : mediaList) {
				if (StringUtil.isNotNull(media)) {
					// 添加图片下载地址
					log.info(url + "&media_id=" + media);
					urlList.add(url + URLEncoder.encode("&media_id=", "utf8") + media);
				}
			}
			//获取图片服务器地址
			ConfigAttributeBean attrBean = configAttributeService.getAttributeByCode(AttributeConstant.CODE_IMAGESERVERURL);
			if (urlList.size() > 0) {
				for (String img : urlList) {
					long start = new Date().getTime();
					CommentInfoImgBean imgBean = new CommentInfoImgBean();
					imgBean.setCreate_time(DateUtil.getNowDateStrSSS());
					String img_url = attrBean.getValue()+"httpFilePath="+img;
					try {
						String imageJson = HttpUtil.get(img_url);
						log.info(imageJson);
						JSONObject imgObj = new JSONObject(imageJson);
						if(imgObj.optInt("status", 0) == CommentInfoImgBean.IMG_IPLOAD_SUCCESS){
							imgBean.setInfo_id(bean.getId());
							imgBean.setPath(imgObj.optString("real_file_path",""));
							imgBean.setImg_url(imgObj.optString("return_file_url",""));
							imgBean.setThumb_url(imgObj.optString("thumb_file_url",""));
							imgBean.setImg_sizex(imgObj.optInt("width", 100)+"x"+imgObj.optInt("height",100));
							long end = new Date().getTime();
							imgBean.setCost_time(end - start);
							commentInfoDao.insertCommentInfoImgBean(imgBean);
						}
					} catch (Exception e) {
						log.error(e,e);
					}
					
				}
			}
		}

	}

	@Override
	public void deleteCommentInfoImg(String id) {
		if(StringUtil.isNotNull(id)){
			CommentInfoImgBean bean = new CommentInfoImgBean();
			bean.setId(Integer.valueOf(id));
			commentInfoDao.deleteCommentInfoImg(bean);
		}
	}

	@Override
	public CommentConfigBean createOrUpdateCommentConfig(String bean) {
		CommentConfigBean configBean = new CommentConfigBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(bean);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,configBean);
			//校验code的唯一性
			CommentConfigBean queryBean = new CommentConfigBean();
			queryBean.setActivity_code(configBean.getActivity_code());
			
			if(StringUtil.isNull(configBean.getId())){
				List<CommentConfigBean> oldList = commentInfoDao.queryCommentConfigInfoByBean(queryBean);
				if(oldList.size() > 0){
					configBean.setRemark("编码重复！");
					return configBean;
				}
				configBean.setCreate_time(DateUtil.getNowDateStrSSS());
				commentInfoDao.insertCommentConfig(configBean);
			}else{
				configBean.setModify_time(DateUtil.getNowDateStrSSS());
				commentInfoDao.updateCommentConfig(configBean);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return configBean;
	}

	@Override
	public PageinationData getCommentConfigListForShow(Map<String, String[]> requestMap,CommentConfigBean configBean) {
		PageinationData pd = new PageinationData();
		//一些初始话的数据需要清空
		configBean.setIs_show(null);
		configBean.setVoted_cycle(null);
		configBean.setPer_voted_num(null);
		Object expir = requestMap.get("expir");
		try {
			AutoInvokeGetSetMethod.autoInvoke(requestMap, configBean);
			List<CommentConfigBean> showList = commentInfoDao.queryCommentConfigInfoForShow(configBean);
			int count = commentInfoDao.queryCommentConfigInfoCountForShow(configBean);
			pd.setDataList(showList);
			pd.setNowpage(configBean.getNowpage() );
			pd.setTotalcount(count);
			pd.calculateTotalPage();
			if(!StringUtil.isNull(expir)){
				
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return pd;
	}
	
	@Override
	public List<CommentConfigBean> deleteCommentConfigBean(String ids) {
		List<CommentConfigBean> beanList = new ArrayList<CommentConfigBean>();
		List<String> idList = Arrays.asList(ids.split(","));
		for (String id : idList) {
			CommentConfigBean bean = new CommentConfigBean();
			bean.setId(Integer.valueOf(id));
			beanList.add(bean);
		}
		commentInfoDao.deleteCommentConfigBean(beanList);
		return beanList;
	}
}
