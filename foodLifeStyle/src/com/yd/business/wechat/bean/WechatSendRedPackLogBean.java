/**
 * 
 */
package com.yd.business.wechat.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("wechatSendRedPackLog")
public class WechatSendRedPackLogBean {

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILD = -1;
	
	
	private Integer id;
	private String order_code;
	private Integer seq;
	private Integer status;
	private String return_msg;
	private String mch_billno;
	private String re_openid;
	private Integer total_amount;
	private Integer total_num;
	private String act_name;
	private String send_time;
	private String send_listid;
	private String req_xml;
	private String res_xml;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReturn_msg() {
		return return_msg;
	}
	public void setReturn_msg(String return_msg) {
		this.return_msg = return_msg;
	}
	public String getMch_billno() {
		return mch_billno;
	}
	public void setMch_billno(String mch_billno) {
		this.mch_billno = mch_billno;
	}
	public String getRe_openid() {
		return re_openid;
	}
	public void setRe_openid(String re_openid) {
		this.re_openid = re_openid;
	}
	public Integer getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(Integer total_amount) {
		this.total_amount = total_amount;
	}
	public Integer getTotal_num() {
		return total_num;
	}
	public void setTotal_num(Integer total_num) {
		this.total_num = total_num;
	}
	public String getAct_name() {
		return act_name;
	}
	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	public String getSend_listid() {
		return send_listid;
	}
	public void setSend_listid(String send_listid) {
		this.send_listid = send_listid;
	}
	public String getReq_xml() {
		return req_xml;
	}
	public void setReq_xml(String req_xml) {
		this.req_xml = req_xml;
	}
	public String getRes_xml() {
		return res_xml;
	}
	public void setRes_xml(String res_xml) {
		this.res_xml = res_xml;
	}
	
	
}
