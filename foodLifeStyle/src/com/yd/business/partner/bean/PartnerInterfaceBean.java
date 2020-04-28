/**
 * 
 */
package com.yd.business.partner.bean;

import com.yd.business.isp.bean.InterfaceBean;

/**
 * @author ice
 *
 */
public class PartnerInterfaceBean extends InterfaceBean {

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_FAILD = -1;

	//合作伙伴订购成功
	public static final String RESULT_CODE_ORDER_SUCCESS = "SUCCESS";
	//合作伙伴订购成功
	public static final String RESULT_CODE_ORDER_FAILD = "FAILD";
	//合作伙伴订购中
	public static final String RESULT_CODE_ORDER_WAIT = "WAIT";
	//合作伙伴信息出错
	public static final String RESULT_CODE_PARTNER_ERROR = "PARTNER_ERROR";
	//客户信息出错
	public static final String RESULT_CODE_CUSTOMER_ERROR = "CUSTOMER_ERROR";
	//合作伙伴信息与客户信息不匹配
	public static final String RESULT_CODE_PARTNER_CUSTOMER_ERROR = "PARTNER_CUSTOMER_ERROR";
	//合作伙伴信息与定单号有误
	public static final String RESULT_CODE_PARTNER_ORDERCODE_ERROR = "PARTNER_ORDERCODE_ERROR";
	//合作伙伴产品不存在
	public static final String RESULT_CODE_PARTNER_PRODUCT_ERROR = "PARTNER_PRODUCT_ERROR";
	//合作伙伴产品已过期
	public static final String RESULT_CODE_PARTNER_PRODUCT_DFF = "PARTNER_PRODUCT_DFF";
	//合作伙伴余额不够
	public static final String RESULT_CODE_PARTNER_BALANCE_ERROR = "PARTNER_BALANCE_ERROR";
	//合作伙伴状态未启用
	public static final String RESULT_CODE_PARTNER_STATUS_ERROR = "PARTNER_STATUS_ERROR";
	//合作伙伴状态已过期
	public static final String RESULT_CODE_PARTNER_STATUS_DFF = "PARTNER_STATUS_DFF";
	//合作伙伴订购号码错误
	public static final String RESULT_CODE_PARTNER_ORDER_ACCOUNT_ERROR = "PARTNER_ORDER_ACCOUNT_ERROR";
	//合作伙伴签错误
	public static final String RESULT_CODE_PARTNER_SIGN_ERROR = "PARTNER_SIGN_ERROR";
	//合作伙伴调用接口超过限额
	public static final String RESULT_CODE_PARTNER_DAYCALL_LIMIT = "PARTNER_DAYCALL_LIMIT";
	//合作伙伴接口无效日，不可以调用
	public static final String RESULT_CODE_PARTNER_INVALID_DAY = "PARTNER_INVALID_DAY";
	//合作伙伴签错误
	public static final String RESULT_CODE_PARTNER_ORDER_RUNNING = "PARTNER_ORDER_RUNNING";
	//合作伙伴IP限制
	public static final String RESULT_CODE_PARTNER_IP_ERROR = "PARTNER_IP_ERROR";
	//系统内部错误
	public static final String RESULT_CODE_HTTP_METHOD_GET_ERROR = "HTTP_METHOD_GET_ERROR";
	//系统内部错误
	public static final String RESULT_CODE_SYSTEM_INTERNAL_ERROR = "SYSTEM_INTERNAL_ERROR";
	
	
	
	
	
	
	
	
	
	private Integer id;
	private String interface_name;
	private String partner_code;
	private String sign_type;
	private String sign;
	private String notify_url;
	private String partner_perpay_id;
	private String partner_out_trade_no;
	private String out_trade_no;
	private String body;
	private String attach;
	private String order_account;
	private String finish_time;
	private Integer customer_id;
	private String result_code;
	private String result_desc;
	private String product_code;
	private String spbill_create_ip;
	private String eff_date;
	private String dff_date;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getInterface_name() {
		return interface_name;
	}
	public void setInterface_name(String interface_name) {
		this.interface_name = interface_name;
	}
	public String getPartner_code() {
		return partner_code;
	}
	public void setPartner_code(String partner_code) {
		this.partner_code = partner_code;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getPartner_perpay_id() {
		return partner_perpay_id;
	}
	public void setPartner_perpay_id(String partner_perpay_id) {
		this.partner_perpay_id = partner_perpay_id;
	}
	public String getPartner_out_trade_no() {
		return partner_out_trade_no;
	}
	public void setPartner_out_trade_no(String partner_out_trade_no) {
		this.partner_out_trade_no = partner_out_trade_no;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOrder_account() {
		return order_account;
	}
	public void setOrder_account(String order_account) {
		this.order_account = order_account;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public Integer getCustomer_id() {
		return customer_id;
	}
	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}
	public String getResult_code() {
		return result_code;
	}
	public void setResult_code(String result_code) {
		this.result_code = result_code;
	}
	public String getResult_desc() {
		return result_desc;
	}
	public void setResult_desc(String result_desc) {
		this.result_desc = result_desc;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public String getEff_date() {
		return eff_date;
	}
	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public String getDff_date() {
		return dff_date;
	}
	public void setDff_date(String dff_date) {
		this.dff_date = dff_date;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	
}
