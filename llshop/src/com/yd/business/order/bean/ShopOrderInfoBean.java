/**
 * 
 */
package com.yd.business.order.bean;

import java.util.List;

import org.apache.ibatis.type.Alias;

/**
 * @author ice
 *
 */
@Alias("shopOrderInfo")
public class ShopOrderInfoBean extends OrderProductLogBean {
	
	
	private Integer id;
	private Integer user_id;
	private String nick_name;
	private String order_code;
	private String order_name;
	private String order_img;
	private String create_time;
	private String modify_time;
	private Integer cost_price;
	private Integer cost_points;
	private Integer cost_money;
	private Integer cost_balance;
	private Integer coupon_total_price;
	private Integer status;
	private String remark;
	private Integer event_type;
	private Integer lucky_money;
	private String share_time;
	private Integer share_type;
	private Integer is_sended;
	private Integer express_price;
	private String contact_name;
	private String contact_phone;
	private String contact_address;
	private String express_mode;
	private String express_code;
	private String express_date;
	private String express_time;
	private String express_order_code;
	private Integer invoice_type;
	private String invoice_info;
	
	private List<ShopOrderProductBean> productList;
	public List<ShopOrderProductBean> getProductList() {
		return productList;
	}
	public void setProductList(List<ShopOrderProductBean> productList) {
		this.productList = productList;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public String getNick_name() {
		return nick_name;
	}
	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
	public String getOrder_code() {
		return order_code;
	}
	public void setOrder_code(String order_code) {
		this.order_code = order_code;
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
	public Integer getCost_price() {
		return cost_price;
	}
	public void setCost_price(Integer cost_price) {
		this.cost_price = cost_price;
	}
	public Integer getCost_points() {
		return cost_points;
	}
	public void setCost_points(Integer cost_points) {
		this.cost_points = cost_points;
	}
	public Integer getCost_money() {
		return cost_money;
	}
	public void setCost_money(Integer cost_money) {
		this.cost_money = cost_money;
	}
	public Integer getCost_balance() {
		return cost_balance;
	}
	public void setCost_balance(Integer cost_balance) {
		this.cost_balance = cost_balance;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getEvent_type() {
		return event_type;
	}
	public void setEvent_type(Integer event_type) {
		this.event_type = event_type;
	}
	public Integer getLucky_money() {
		return lucky_money;
	}
	public void setLucky_money(Integer lucky_money) {
		this.lucky_money = lucky_money;
	}
	public String getShare_time() {
		return share_time;
	}
	public void setShare_time(String share_time) {
		this.share_time = share_time;
	}
	public Integer getShare_type() {
		return share_type;
	}
	public void setShare_type(Integer share_type) {
		this.share_type = share_type;
	}
	public Integer getIs_sended() {
		return is_sended;
	}
	public void setIs_sended(Integer is_sended) {
		this.is_sended = is_sended;
	}
	public Integer getExpress_price() {
		return express_price;
	}
	public void setExpress_price(Integer express_price) {
		this.express_price = express_price;
	}
	public String getContact_name() {
		return contact_name;
	}
	public void setContact_name(String contact_name) {
		this.contact_name = contact_name;
	}
	public String getContact_phone() {
		return contact_phone;
	}
	public void setContact_phone(String contact_phone) {
		this.contact_phone = contact_phone;
	}
	public String getContact_address() {
		return contact_address;
	}
	public void setContact_address(String contact_address) {
		this.contact_address = contact_address;
	}
	public String getExpress_mode() {
		return express_mode;
	}
	public void setExpress_mode(String express_mode) {
		this.express_mode = express_mode;
	}
	public String getExpress_date() {
		return express_date;
	}
	public void setExpress_date(String express_date) {
		this.express_date = express_date;
	}
	public String getExpress_time() {
		return express_time;
	}
	public void setExpress_time(String express_time) {
		this.express_time = express_time;
	}
	public String getExpress_order_code() {
		return express_order_code;
	}
	public void setExpress_order_code(String express_order_code) {
		this.express_order_code = express_order_code;
	}
	public Integer getInvoice_type() {
		return invoice_type;
	}
	public void setInvoice_type(Integer invoice_type) {
		this.invoice_type = invoice_type;
	}
	public String getInvoice_info() {
		return invoice_info;
	}
	public void setInvoice_info(String invoice_info) {
		this.invoice_info = invoice_info;
	}
	public String getOrder_name() {
		return order_name;
	}
	public void setOrder_name(String order_name) {
		this.order_name = order_name;
	}
	public String getOrder_img() {
		return order_img;
	}
	public void setOrder_img(String order_img) {
		this.order_img = order_img;
	}
	public Integer getCoupon_total_price() {
		return coupon_total_price;
	}
	public void setCoupon_total_price(Integer coupon_total_price) {
		this.coupon_total_price = coupon_total_price;
	}
	public String getExpress_code() {
		return express_code;
	}
	public void setExpress_code(String express_code) {
		this.express_code = express_code;
	}
}
