package com.yd.business.customer.bean;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 客户信息
 * @author Anlins
 *
 */
@Alias("customer")
public class CustomerBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3604219137878527170L;
	public static final int ID_DEFAULT = -1;
	
	public static final Integer ISCREATE_NO = 0;
	public static final Integer ISCREATE_YES = 1;
	
	public static final Integer PAY_CYCLE_NO = 0;
	public static final Integer PAY_CYCLE_MONTH = 1;
	public static final Integer PAY_CYCLE_WEEK = 2;
	public static final Integer PAY_CYCLE_DAY = 3;
	public static final Integer PAY_CYCLE_MIN = 4;
	
	public static final Integer TYPE_ADM = 0;//平台管理员
	public static final Integer TYPE_IMP = 1;//平台导入
	public static final Integer TYPE_REG = 2;//平台注册
	
	public static final Integer STATUS_NO = 0;//不可用
	public static final Integer STATUS_YES = 1;//可用 
	
	public static final Integer AUDITSTATUS_WSH = 0;//已提交未审核
	public static final Integer AUDITSTATUS_YES = 1;//审核通过
	public static final Integer AUDITSTATUS_NO = 2;//审核不通过
	public static final Integer AUDITSTATUS_CAN = 3;//用户取消

	public static final int ID_PLATFROM = 1; //平台商户
	public static final int ID_BUYGIVEONE = 4; //买一赠一商户
	
	
	
	private Integer id;
	private String name;
	private String openid;
	private String username;
	private String password;
	private Integer status;
	private Integer iscreate;
	private String address;
	private Integer balance;
	private Integer recharge_balance;
	private Integer points;
	private String create_time;
	private String contacts_name;
	private String contacts_phone;
	private String remark;
	private Integer credit;
	private String modify_time;
	private Integer get_cash_min;
	private Integer pay_cycle;
	private String session_id;
	private Integer level;
	private Integer type;
	private Integer auditstatus;
	private String company_legal_name;
	private String company_legal_idcard;
	private String company_legal_idcard_positive;
	private String company_legal_idcard_back;
	private String company_legal_phone;
	private String company_registcode;
	private String company_dff_date;
	private String company_address;
	private String company_scope;
	private String company_business_license;
	
	public String getCompany_legal_name() {
		return company_legal_name;
	}
	public void setCompany_legal_name(String company_legal_name) {
		this.company_legal_name = company_legal_name;
	}
	public String getCompany_legal_idcard() {
		return company_legal_idcard;
	}
	public void setCompany_legal_idcard(String company_legal_idcard) {
		this.company_legal_idcard = company_legal_idcard;
	}
	public String getCompany_legal_idcard_positive() {
		return company_legal_idcard_positive;
	}
	public void setCompany_legal_idcard_positive(
			String company_legal_idcard_positive) {
		this.company_legal_idcard_positive = company_legal_idcard_positive;
	}
	public String getCompany_legal_idcard_back() {
		return company_legal_idcard_back;
	}
	public void setCompany_legal_idcard_back(String company_legal_idcard_back) {
		this.company_legal_idcard_back = company_legal_idcard_back;
	}
	public String getCompany_legal_phone() {
		return company_legal_phone;
	}
	public void setCompany_legal_phone(String company_legal_phone) {
		this.company_legal_phone = company_legal_phone;
	}
	public String getCompany_registcode() {
		return company_registcode;
	}
	public void setCompany_registcode(String company_registcode) {
		this.company_registcode = company_registcode;
	}
	public String getCompany_dff_date() {
		return company_dff_date;
	}
	public void setCompany_dff_date(String company_dff_date) {
		this.company_dff_date = company_dff_date;
	}
	public String getCompany_address() {
		return company_address;
	}
	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	public String getCompany_scope() {
		return company_scope;
	}
	public void setCompany_scope(String company_scope) {
		this.company_scope = company_scope;
	}
	public String getCompany_business_license() {
		return company_business_license;
	}
	public void setCompany_business_license(String company_business_license) {
		this.company_business_license = company_business_license;
	}
	public Integer getAuditstatus() {
		return auditstatus;
	}
	public void setAuditstatus(Integer auditstatus) {
		this.auditstatus = auditstatus;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIscreate() {
		return iscreate;
	}
	public void setIscreate(Integer iscreate) {
		this.iscreate = iscreate;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getContacts_name() {
		return contacts_name;
	}
	public void setContacts_name(String contacts_name) {
		this.contacts_name = contacts_name;
	}
	public String getContacts_phone() {
		return contacts_phone;
	}
	public void setContacts_phone(String contacts_phone) {
		this.contacts_phone = contacts_phone;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getCredit() {
		return credit;
	}
	public void setCredit(Integer credit) {
		this.credit = credit;
	}
	public String getModify_time() {
		return modify_time;
	}
	public void setModify_time(String modify_time) {
		this.modify_time = modify_time;
	}
	public Integer getGet_cash_min() {
		return get_cash_min;
	}
	public void setGet_cash_min(Integer get_cash_min) {
		this.get_cash_min = get_cash_min;
	}
	public Integer getPay_cycle() {
		return pay_cycle;
	}
	public void setPay_cycle(Integer pay_cycle) {
		this.pay_cycle = pay_cycle;
	}
	public Integer getRecharge_balance() {
		return recharge_balance;
	}
	public void setRecharge_balance(Integer recharge_balance) {
		this.recharge_balance = recharge_balance;
	}
	
}
