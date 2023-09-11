package com.yd.business.wechat.bean;

import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.util.DateUtil;

/**
 * 消息基类（普通用户 -> 公众帐号）.
 * 
 * @author Administrator
 * 
 */
@Alias("baseMessage")
public class BaseMessage {
	public static int ERROR_CODE_SUCCESS = 0;
	
	public static int NOTIFYTYPE_CHILDREN = 1;  //子收到
	public static int NOTIFYTYPE_PARENT = 2;	//父收到
	public static int NOTIFYTYPE_BROTHER = 3;	//兄弟之间
	public static int NOTIFYTYPE_GRANDSON = 4;	//中奖与孙之间
	public static int NOTIFYTYPE_UNCLES = 5;	//中奖与叔叔伯伯之间
	public static int NOTIFYTYPE_ORDER_SUCCESS = 7;	//充值成功
	public static int NOTIFYTYPE_ORDER_FAILD = 8;	//充值失败
	public static int NOTIFYTYPE_SYSTEM_NOTIFY = 9;	//系统通知
	
	
	//数据库ID
	private Integer id;
	// 开发者微信号
	private String ToUserName;
	// 发送方帐号（一个OpenID）
	private String FromUserName;
	// 消息创建时间 （整型）
	private long CreateTime;
	
	private Date CreateTime_Date;
	// 消息类型（text/image/location/link）
	private String MsgType;
	// 消息id，64位整型
	private long MsgId;
	
	private String status;

	private Integer errcode;

	private String errmsg;
	
	private String msgContent;
	
	
	//非微信的扩展字段
	private String fromUserOpenId;
	private Integer notifyType;
	private Integer productId;
	private Integer sbProductId;
	private Integer productPrice;
	private Integer seq;

	public Integer getErrcode() {
		return errcode;
	}

	public void setErrcode(Integer errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
		if(CreateTime_Date == null)
			setCreateTime_Date(DateUtil.php2javaDate(createTime));
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

	public Date getCreateTime_Date() {
		return CreateTime_Date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public void setCreateTime_Date(Date createTime_Date) {
		CreateTime_Date = createTime_Date;
		if(CreateTime == 0l)
			setCreateTime(DateUtil.java2phpDate(createTime_Date));
	}

	public String getFromUserOpenId() {
		return fromUserOpenId;
	}

	public void setFromUserOpenId(String fromUserOpenId) {
		this.fromUserOpenId = fromUserOpenId;
	}

	public Integer getNotifyType() {
		return notifyType;
	}

	public void setNotifyType(Integer notifyType) {
		this.notifyType = notifyType;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getSbProductId() {
		return sbProductId;
	}

	public void setSbProductId(Integer sbProductId) {
		this.sbProductId = sbProductId;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected void baseInfo(BaseMessage base) {
		if (base == null)
			return;
		setId(base.getId());
		setCreateTime(base.getCreateTime());
		setErrcode(base.getErrcode());
		setErrmsg(base.getErrmsg());
		setFromUserName(base.getFromUserName());
		setMsgId(base.getMsgId());
		setMsgType(base.getMsgType());
		setToUserName(base.getToUserName());
		setStatus(base.getStatus());
	}
}
