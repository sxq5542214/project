package com.yd.business.comment.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * 留言信息明细
 * @author BoBo
 *
 */
@Alias("commentInfo")
public class CommentInfoBean extends BaseBean implements Cloneable,Comparable<CommentInfoBean>  {
	public static final String COMMENT_DEFAULT_IMG = "page/comment/images/default_img.png";
	public static final String COMMENT_DEFAULT_ADMIN_IMG = "page/comment/images/admin.png";
	
	public static final String COMMENT_STATUS_CHECKDEL_ACTION = "checkdel";
	
	public static final String COMMENT_STATUS_REVCOVER_ACTION = "checkrecover";
	
	public static final String COMMENT_BRLONG_FRIEND = "nowpage_blogList_friend";
	public static final String COMMENT_BRLONG_ALL = "nowpage_blogList_all";
	public static final String COMMENT_BRLONG_OWNERL = "nowpage_blogList_owner";
	
	/**
	 * 未审核
	 */
	public static final int COMMENT_STATUS_NOCHECK = 0;
	/**
	 * 已审核
	 */
	public static final int COMMENT_STATUS_CHECKED = 1;
	/**
	 * 审核动作
	 */
	public static final String COMMENT_STATUS_CHECKED_ACTION = "checked";
	/**
	 * 审核不通过
	 */
	public static final int COMMENT_STATUS_CHECKFAIL = -1;
	/**
	 * 审核动作
	 */
	public static final String COMMENT_STATUS_CHECKFAIL_ACTION = "checkfail";
//	/**
//	 * 审核忽略
//	 */
//	public static final int COMMENT_STATUS_CHECKPASS = -2;
//	/**
//	 * 审核动作
//	 */
//	public static final String COMMENT_STATUS_CHECKPASS_ACTION = "pass";
	/**
	 * 恢复留言，待审核
	 */
	public static final int COMMENT_STATUS_CHECKRECOVER = 2;
	/**
	 * 备份表留言，已恢复留言
	 */
	public static final int COMMENT_STATUS_CHECKRECOVERED = -2;
	
	/**
	 * 管理员回复
	 */
	public static final int COMMENT_TYPE_ADMIN = 1;
	/**
	 * 用户回复
	 */
	public static final int COMMENT_TYPE_USER = 2;
	/**
	 * 留言本体，非回复
	 */
	public static final int COMMENT_TYPE_COMMENTINFO = 0;
	/**
	 * 默认点赞数
	 */
	public static final int COMMENT_AGREECOUNT_DEFALT = 0;
	/**
	 * 默认评论数
	 */
	public static final int COMMENT_RECOUNT_DEFALT = 0;
	
	public static final int COMMENT_AGREECOUNT_ADD = 0;
	public static final int COMMENT_AGREECOUNT_MINUS = -1;
	
	/**
	 * 留言管理员已评论
	 */
	public static final int COMMENT_ISADMINRE_YES = 1;
	public static final int COMMENT_ISADMINRE_NO = 0;
	/**
	 * 已评论
	 */
	public static final int COMMENT_ISVOTED_YES = 1;
	/**
	 * 未评论
	 */
	public static final int COMMENT_ISVOTED_NO = 0;
	
	private Integer id;
	/**
	 * 留言配置ID
	 */
	private Integer comment_conf_id;
	/**
	 * 留言所属父级ID
	 */
	private Integer comment_parentid;
	/**
	 * 用户ID
	 */
	private Integer user_id;
	/**
	 * 留言状态
	 */
	private Integer status;
	/**
	 * 评论数
	 */
	private Integer reCount;
	/**
	 * 点赞数
	 */
	private Integer agreeCount;
	/**
	 * 回复类型，游客回复  管理员回复
	 */
	private Integer type;
	/**
	 * 修改时间
	 */
	private String modifytime;
	/**
	 * 创建时间
	 */
	
	private String createtime;
	private String msgtext;
	private String remark;
	private List<CommentInfoBean> replyBeanList;
	private String nick_name;
	private String head_img;
	private Integer is_adminre;
	private String openid;
	private Integer is_voted;
	private String ip;
	private String comment_conf_code;
	private List<CommentInfoImgBean> imgBeanList;
	/**
	 * 评论所属留言的留言内容
	 */
	private String pmsgtext;
	/**
	 * 跳转链接，在留言配置表里
	 */
	private String link_url;
	
	/**
	 * 留言配置描述名称
	 */
	private String config_name;
	/**
	 * 评论所属父级留言
	 */
	private String parent_msg;
	/**
	 * 评论所属父级评论
	 */
	private String preplymsgtext;
	/**
	 * 所属父级留言id
	 */
	private Integer parent_replyid;
	/**
	 * 回复评论的评论人名字
	 */
	private String parent_replyUserName;
	/**
	 * 用于查询的openid集合
	 */
	private List<String> openids;
	private List<Integer> ids;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getComment_conf_id() {
		return comment_conf_id;
	}
	public void setComment_conf_id(Integer comment_conf_id) {
		this.comment_conf_id = comment_conf_id;
	}
	public Integer getComment_parentid() {
		return comment_parentid;
	}
	public void setComment_parentid(Integer comment_parentid) {
		this.comment_parentid = comment_parentid;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getReCount() {
		return reCount;
	}
	public void setReCount(Integer reCount) {
		this.reCount = reCount;
	}
	public Integer getAgreeCount() {
		return agreeCount;
	}
	public void setAgreeCount(Integer agreeCount) {
		this.agreeCount = agreeCount;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getModifytime() {
		return modifytime;
	}
	public void setModifytime(String modifytime) {
		this.modifytime = modifytime;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
	public String getMsgtext() {
		return msgtext;
	}
	public void setMsgtext(String msgtext) {
		this.msgtext = msgtext;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public List<CommentInfoBean> getReplyBeanList() {
		return replyBeanList;
	}
	public void setReplyBeanList(List<CommentInfoBean> replyBeanList) {
		this.replyBeanList = replyBeanList;
	}
	
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getHead_img() {
		return head_img;
	}
	public void setHead_img(String head_img) {
		this.head_img = head_img;
	}
	
	public String getPmsgtext() {
		return pmsgtext;
	}
	public void setPmsgtext(String pmsgtext) {
		this.pmsgtext = pmsgtext;
	}
	
	public Integer getIs_adminre() {
		return is_adminre;
	}
	public void setIs_adminre(Integer is_adminre) {
		this.is_adminre = is_adminre;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	
	public Integer getIs_voted() {
		return is_voted;
	}
	public void setIs_voted(Integer is_voted) {
		this.is_voted = is_voted;
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getComment_conf_code() {
		return comment_conf_code;
	}
	public void setComment_conf_code(String comment_conf_code) {
		this.comment_conf_code = comment_conf_code;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public String getConfig_name() {
		return config_name;
	}
	public void setConfig_name(String config_name) {
		this.config_name = config_name;
	}
	public String getParent_msg() {
		return parent_msg;
	}
	public void setParent_msg(String parent_msg) {
		this.parent_msg = parent_msg;
	}
	
	public Integer getParent_replyid() {
		return parent_replyid;
	}
	public void setParent_replyid(Integer parent_replyid) {
		this.parent_replyid = parent_replyid;
	}
	
	public String getParent_replyUserName() {
		return parent_replyUserName;
	}
	public void setParent_replyUserName(String parent_replyUserName) {
		this.parent_replyUserName = parent_replyUserName;
	}
	
	public List<String> getOpenids() {
		return openids;
	}
	public void setOpenids(List<String> openids) {
		this.openids = openids;
	}
	public List<Integer> getIds() {
		return ids;
	}
	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}
	public String getPreplymsgtext() {
		return preplymsgtext;
	}
	public void setPreplymsgtext(String preplymsgtext) {
		this.preplymsgtext = preplymsgtext;
	}
	public List<CommentInfoImgBean> getImgBeanList() {
		return imgBeanList;
	}
	public void setImgBeanList(List<CommentInfoImgBean> imgBeanList) {
		this.imgBeanList = imgBeanList;
	}
	@Override  
    public Object clone() throws CloneNotSupportedException  
    {  
        return super.clone();  
    }  
	@Override
	public int compareTo(CommentInfoBean o) {
		return o.getCreatetime().compareTo(this.getCreatetime());
	}
}
