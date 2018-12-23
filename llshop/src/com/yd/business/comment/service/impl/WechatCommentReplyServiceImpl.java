package com.yd.business.comment.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.comment.bean.CommentInfoBean;
import com.yd.business.comment.bean.WechatCommentBean;
import com.yd.business.comment.dao.IWechatCommentReplyDao;
import com.yd.business.comment.service.IWechatCommentReplyService;
import com.yd.business.msgcenter.bean.MsgCenterUserActionBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.user.util.EmojiUtil;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.TextBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

@Service("wechatCommentReplyService")
public class WechatCommentReplyServiceImpl extends BaseService implements IWechatCommentReplyService {

	@Resource
	private IWechatCommentReplyDao wechatCommentReplyDao;
	@Resource
	private IUserWechatService userWechatService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;
	@Resource
	private IWechatService wechatService;
	
	@Override
	public void insertCommentInfo(CommentInfoBean bean) {
		bean.setCreatetime(DateUtil.getNowDateStrSSS());
		wechatCommentReplyDao.insertReplyWechatCommentInfo(bean);
	}
	/**
	 * 获取用户留言信息
	 */
	@Override
	public PageinationData queryMsgCenterUserComment(String nowpage,String openid) {
		if(isShowCommentList(openid)){
			WechatCommentBean bean = new WechatCommentBean();
			if(StringUtil.isNotNull(nowpage)){
				bean.setNowpage(Integer.valueOf(nowpage));
			}
			List<WechatCommentBean> beanList = getWechatUserCommentListByBean(bean);
			Collections.sort(beanList,new Comparator<WechatCommentBean>(){ 
	            public int compare(WechatCommentBean arg0, WechatCommentBean arg1) { 
	                return -arg0.getCreate_time().compareTo(arg1.getCreate_time()); 
	            } 
	        }); 
			//处理时间
			for (WechatCommentBean commentInfoBean : beanList) {
				
//				commentInfoBean.setNowpage(Integer.valueOf(nowpage));
				if(StringUtil.isNotNull(commentInfoBean.getCreate_time())){
					try {
						commentInfoBean.setCreate_time(commentInfoBean.getCreate_time().substring(5, 19));
					} catch (Exception e) {
						log.error(e, e);
					}
				}
			}
			PageinationData returnData = new PageinationData();
			returnData.setDataList(beanList);
			returnData.setNowpage(Integer.valueOf(nowpage));
			return returnData;
		}
		return null;
	}

	@Override
	public CommentInfoBean replyMsgToWeChatUser(String str,String commentid,String adminopenid) {
		UserWechatBean admin = userWechatService.findUserWechatByOpenId(adminopenid);
		//获得该留言
		MsgCenterUserActionBean bean = msgCenterActionService.findUserActionById(Integer.valueOf(commentid));
		//获得该留言的用户
		UserWechatBean user = userWechatService.findUserWechatById(bean.getUser_id());
		TextBean text = new TextBean();
		text.setToUserName(user.getOpenid());
		text.setContent(EmojiUtil.filterEmoji(str));
		text.setFromUserName(user.getOriginalid());
		//记录消息日志
		CommentInfoBean infoBean = new CommentInfoBean();
		//发送消息
		BaseMessage base = wechatService.sendMessageToUser(text);
		if(base.getErrcode().compareTo(BaseMessage.ERROR_CODE_SUCCESS) == 0){
			infoBean.setMsgtext(EmojiUtil.filterEmoji(str));
			infoBean.setComment_parentid(Integer.valueOf(commentid));
			infoBean.setOpenid(adminopenid);
			infoBean.setHead_img(admin.getHead_img());
			this.insertCommentInfo(infoBean);
		}else{
			infoBean.setRemark("消息发送失败！");
		}
		return infoBean;
	}
	@Override
	public List<CommentInfoBean> queryWechatCommentInfoByParentId(List<WechatCommentBean> beanList) {
		return wechatCommentReplyDao.queryWechatCommentInfoByParentId(beanList);
	}
	/**
	 * 用于聊天时候 的异步请求数据
	 */
	@Override
	public List<WechatCommentBean> getNewMsgCount(String commentid) {
		//获得列表里最新的留言信息
		WechatCommentBean bean = new WechatCommentBean();
		if(StringUtil.isNotNull(commentid)){
			bean.setId(Integer.valueOf(commentid));
			bean = this.findWechatUserCommentByBean(bean);
			if(!StringUtil.isNull(bean)){
				List<WechatCommentBean> newBeanList = wechatCommentReplyDao.queryNewMsgCenterUserComment(bean);
				if(newBeanList.size() > 0){
					Collections.sort(newBeanList,new Comparator<WechatCommentBean>(){ 
			            public int compare(WechatCommentBean arg0, WechatCommentBean arg1) { 
			                return arg0.getCreate_time().compareTo(arg1.getCreate_time()); 
			            } 
			        }); 
					return newBeanList;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取该用户的留言信息
	 */
	@Override
	public List<WechatCommentBean> getWechatUserCommentListByOpenid(String openid,String adminopenid) {
		if(isShowCommentList(adminopenid)){
			WechatCommentBean bean = new WechatCommentBean();
			bean.setOpenid(openid);
			List<WechatCommentBean> beanList = getWechatUserCommentListByBean(bean);
			for (WechatCommentBean commentInfoBean : beanList) {
				commentInfoBean.setNowpage(commentInfoBean.getNowpage()+1);
				if(StringUtil.isNotNull(commentInfoBean.getCreate_time())){
					try {
						commentInfoBean.setCreate_time(commentInfoBean.getCreate_time().substring(5, 19));
					} catch (Exception e) {
						log.error(e, e);
					}
				}
			}
			return beanList;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取列表并排序
	 * @param bean
	 * @return
	 */
	private List<WechatCommentBean> getWechatUserCommentListByBean(WechatCommentBean bean){
		List<WechatCommentBean> beanList = wechatCommentReplyDao.queryMsgCenterUserComment(bean);
		if(beanList.size() > 0){
			Collections.sort(beanList);
			//查询留言的回复
			List<CommentInfoBean> replyBeanList = wechatCommentReplyDao.queryWechatCommentInfoByParentId(beanList);
			Collections.sort(replyBeanList);
			for (CommentInfoBean replyBean : replyBeanList) {
				if(StringUtil.isNotNull(replyBean.getCreatetime())){
					try {
						replyBean.setCreatetime(replyBean.getCreatetime().substring(5, 19));
					} catch (Exception e) {
						log.error(e, e);
					}
				}
				for (WechatCommentBean commentBean : beanList) {
					if(commentBean.getId().compareTo(replyBean.getComment_parentid()) == 0){
						if(StringUtil.isNull(commentBean.getReplyBeanList())){
							List<CommentInfoBean> list = new ArrayList<CommentInfoBean>();
							list.add(replyBean);
							commentBean.setReplyBeanList(list);
						}else{
							commentBean.getReplyBeanList().add(replyBean);
						}
						break;
					}
				}
			}
		}
		Collections.sort(beanList); 
		return beanList;
	}
	@Override
	public WechatCommentBean findWechatUserCommentByBean(WechatCommentBean bean) {
		List<WechatCommentBean> beanList = wechatCommentReplyDao.queryMsgCenterUserComment(bean);
		if(beanList.size() > 0){
			return beanList.get(0);
		}
		return null;
	}
	
	/**
	 * 判断该openid是否可以访问留言列表
	 * @param openid
	 * @return
	 */
	@Override
	public boolean isShowCommentList(String openid){
		if(StringUtil.isNotNull(openid)){
			UserWechatBean user = userWechatService.findUserWechatByOpenId(openid);
			if(!StringUtil.isNull(user)){
				if("oA4dzwrfEFoAnakpRguuFO0IKv80".equals(user.getOpenid()) ||
					"oA4dzwoulTrp09fypckJhatoZICk".equals(user.getOpenid()) ||
					"oA4dzwgM_L-eebwSjw-2fcKzX7kk".equals(user.getOpenid()) ||
					"oA4dzwujx2U9HZNT3xDQz-_chJTI".equals(user.getOpenid()) ||
					"oA4dzwhe6GkcnZP0i7jRFP45nZxY".equals(user.getOpenid())){
					return true;
				}
			}
		}
		return false;
	}
	
}
