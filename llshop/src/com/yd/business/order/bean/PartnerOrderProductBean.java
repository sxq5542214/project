/**
 * 
 */
package com.yd.business.order.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * @author ice
 *
 */
@Alias("partnerOrderProduct")
public class PartnerOrderProductBean extends BaseBean {
	

	public static final int STATUS_SUCCESS = 1;
	public static final int STATUS_WAIT = 0;
	public static final int STATUS_FAILD = -1;
	
	public static final int NOTIFY_STATUS_SUCCESS = 1;
	public static final int NOTIFY_STATUS_WAIT = 0;
	public static final int NOTIFY_STATUS_FAILD = -1;
	
	
	private Integer id ;
	private Integer partner_id;
	private Integer partner_perpay_id;
	private Integer product_id;
	private String product_name;
	private Integer product_price;
	private Integer discount_price;
	private String create_time;
	private String modify_time;
	private Integer status;
	private String result_code;
	private String result_desc;
	private String order_account;
	private String out_trade_no;
	private String partner_out_trade_no;
	private String attach;
	private String finish_time;
	private String spbill_create_ip;
	private Integer channel_id;
	
	private String notify_url;
	private Integer notify_status;
	private String notify_desc;
	private Integer notify_count;
	private String notify_time;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPartner_id() {
		return partner_id;
	}
	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}
	public Integer getPartner_perpay_id() {
		return partner_perpay_id;
	}
	public void setPartner_perpay_id(Integer partner_perpay_id) {
		this.partner_perpay_id = partner_perpay_id;
	}
	public Integer getProduct_id() {
		return product_id;
	}
	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getProduct_price() {
		return product_price;
	}
	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getOrder_account() {
		return order_account;
	}
	public void setOrder_account(String order_account) {
		this.order_account = order_account;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getPartner_out_trade_no() {
		return partner_out_trade_no;
	}
	public void setPartner_out_trade_no(String partner_out_trade_no) {
		this.partner_out_trade_no = partner_out_trade_no;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getFinish_time() {
		return finish_time;
	}
	public void setFinish_time(String finish_time) {
		this.finish_time = finish_time;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public Integer getDiscount_price() {
		return discount_price;
	}
	public void setDiscount_price(Integer discount_price) {
		this.discount_price = discount_price;
	}
	public Integer getChannel_id() {
		return channel_id;
	}
	public void setChannel_id(Integer channel_id) {
		this.channel_id = channel_id;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public Integer getNotify_status() {
		return notify_status;
	}
	public void setNotify_status(Integer notify_status) {
		this.notify_status = notify_status;
	}
	public String getNotify_desc() {
		return notify_desc;
	}
	public void setNotify_desc(String notify_desc) {
		this.notify_desc = notify_desc;
	}
	public Integer getNotify_count() {
		return notify_count;
	}
	public void setNotify_count(Integer notify_count) {
		this.notify_count = notify_count;
	}
	public String getNotify_time() {
		return notify_time;
	}
	public void setNotify_time(String notify_time) {
		this.notify_time = notify_time;
	}
}
