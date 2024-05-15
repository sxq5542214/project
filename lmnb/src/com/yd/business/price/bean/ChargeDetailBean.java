package com.yd.business.price.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * ChargeDetailBean entity. @author MyEclipse Persistence Tools
 */
@Alias("chargeDetail")
public class ChargeDetailBean extends BaseBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3971413575227501413L;
	public static final int BRUSHFLAG_NO = 0 ; 	// 未刷卡至表中
	public static final int BRUSHFLAG_YES = 1 ; // 已刷卡至表中
	
	
	public static final int CHARGE_FAILD = 0 ; // 充值未成功
	public static final int CHARGE_FAILD_WRITE = 1 ; // 充值未成功,写卡失败
	public static final int CHARGE_SUCCESS = 2 ; // 充值成功
	
	public static final int KIND_OPEN_ACCOUNT = 0;	// 开户
	public static final int KIND_CHARGE = 1;  // 充值
	public static final int KIND_CHARGE_UPDATE = 2;  // 充值修改
	public static final int KIND_CHANGE_CARD = 3;  // 补卡
	public static final int KIND_CHANGE_DEVICE = 4;  // 换表维护
	public static final int KIND_CHANGE_ACCOUNT = 5;  // 过户
	public static final int KIND_CHARGE_BALANCE = 6;  // 预存充值
	public static final int KIND_USER_REFUND = 7;  // 用户退费
	public static final int KIND_USER_COSTMONEY = 8;  // 用户扣费
	public static final int KIND_DEADLINE_COSTMONEY = 9;  // 保底扣费
	public static final int KIND_USER_AAAAA = 10;  // 自助圈存
	
	public static final int ORDER_MONEY = 0 ;	 //现金
	public static final int ORDER_WECHAT = 1 ;	 // 微信
	public static final int ORDER_ALIPAY = 2 ;	 // 支付宝
	public static final int ORDER_POS = 3 ; 	//POS机
	public static final int ORDER_WECHAT_PROGRAM = 4 ; // 微信小程序
	
	public static final int PRINT_STATUS_NO = 0 ;  //未打印发票
	public static final int PRINT_STATUS_YES = 1 ; //已打印
	
	public static final byte PAY_STATUS_WAIT = 1 ; //待支付
	public static final byte PAY_STATUS_SUCCESS = 2 ; //已支付
	
	
	
	// Fields
	
	private Long cd_id;
	private Long cd_no;
	private Long cd_userid;
	private Long cd_priceid;
	private Integer cd_savingno;
	private BigDecimal cd_paidmoney;
	private BigDecimal cd_chargemoney;
	private BigDecimal cd_chargeamount;
	private BigDecimal cd_basemoney;
	private BigDecimal cd_othermoney1;
	private BigDecimal cd_othermoney2;
	private BigDecimal cd_lastbalance;
	private BigDecimal cd_balance;
	private Integer cd_brushflag;
	private Integer cd_kindid;
	private Integer cd_order;
	private Integer cd_charge;
	private Long cd_operatorid;
	private Date cd_happendate;	//操作时间
	private BigDecimal cd_startamount;
	private BigDecimal cd_endamount;
	private Date cd_startdate; // 写卡时间
	private Date cd_enddate;	// 刷表时间
	private Long cd_readerid;
	private Integer cd_printstatus;
	private String cd_tradecustomer;
	private String cd_tradeno;
	private Integer cd_tradestatus;
	private BigDecimal cd_amountton1;
	private BigDecimal cd_amountton2;
	private BigDecimal cd_amountton3;
	private BigDecimal cd_basemoneyton2;
	private BigDecimal cd_basemoneyton3;

	
	
	private Long user_no;
	private Integer user_cardno;
	private String user_name;
	private String user_phone;
	private String price_name;
	private String operator_name;
	// Constructors

	/** default constructor */
	public ChargeDetailBean() {
	}

	/** full constructor */
	public ChargeDetailBean(Long cd_no, Long cd_userid, Long cd_priceid, Integer cd_savingno, BigDecimal cd_paidmoney,
			BigDecimal cd_chargemoney, BigDecimal cd_chargeamount, BigDecimal cd_basemoney, BigDecimal cd_othermoney1,
			BigDecimal cd_othermoney2, BigDecimal cd_lastbalance, BigDecimal cd_balance, Integer cd_brushflag,
			Integer cd_kindid, Integer cd_order, Integer cd_charge, Long cd_operatorid, Date cd_happendate,
			BigDecimal cd_startamount, BigDecimal cd_endamount, Date cd_startdate, Date cd_enddate, Long cd_readerid,
			Integer cd_printstatus, String cd_tradecustomer, String cd_tradeno, Integer cd_tradestatus,
			BigDecimal cd_amountton1, BigDecimal cd_amountton2, BigDecimal cd_amountton3, BigDecimal cd_basemoneyton2,
			BigDecimal cd_basemoneyton3) {
		this.cd_no = cd_no;
		this.cd_userid = cd_userid;
		this.cd_priceid = cd_priceid;
		this.cd_savingno = cd_savingno;
		this.cd_paidmoney = cd_paidmoney;
		this.cd_chargemoney = cd_chargemoney;
		this.cd_chargeamount = cd_chargeamount;
		this.cd_basemoney = cd_basemoney;
		this.cd_othermoney1 = cd_othermoney1;
		this.cd_othermoney2 = cd_othermoney2;
		this.cd_lastbalance = cd_lastbalance;
		this.cd_balance = cd_balance;
		this.cd_brushflag = cd_brushflag;
		this.cd_kindid = cd_kindid;
		this.cd_order = cd_order;
		this.cd_charge = cd_charge;
		this.cd_operatorid = cd_operatorid;
		this.cd_happendate = cd_happendate;
		this.cd_startamount = cd_startamount;
		this.cd_endamount = cd_endamount;
		this.cd_startdate = cd_startdate;
		this.cd_enddate = cd_enddate;
		this.cd_readerid = cd_readerid;
		this.cd_printstatus = cd_printstatus;
		this.cd_tradecustomer = cd_tradecustomer;
		this.cd_tradeno = cd_tradeno;
		this.cd_tradestatus = cd_tradestatus;
		this.cd_amountton1 = cd_amountton1;
		this.cd_amountton2 = cd_amountton2;
		this.cd_amountton3 = cd_amountton3;
		this.cd_basemoneyton2 = cd_basemoneyton2;
		this.cd_basemoneyton3 = cd_basemoneyton3;
	}

	// Property accessors

	public Long getCd_id() {
		return this.cd_id;
	}

	public String getOperator_name() {
		return operator_name;
	}

	public void setOperator_name(String operator_name) {
		this.operator_name = operator_name;
	}

	public Long getUser_no() {
		return user_no;
	}


	public Integer getUser_cardno() {
		return user_cardno;
	}

	public void setUser_cardno(Integer user_cardno) {
		this.user_cardno = user_cardno;
	}

	public void setUser_no(Long user_no) {
		this.user_no = user_no;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_phone() {
		return user_phone;
	}

	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}

	public String getPrice_name() {
		return price_name;
	}

	public void setPrice_name(String price_name) {
		this.price_name = price_name;
	}

	public void setCd_id(Long cd_id) {
		this.cd_id = cd_id;
	}

	public Long getCd_no() {
		return this.cd_no;
	}

	public void setCd_no(Long cd_no) {
		this.cd_no = cd_no;
	}

	public Long getCd_userid() {
		return this.cd_userid;
	}

	public void setCd_userid(Long cd_userid) {
		this.cd_userid = cd_userid;
	}

	public Long getCd_priceid() {
		return this.cd_priceid;
	}

	public void setCd_priceid(Long cd_priceid) {
		this.cd_priceid = cd_priceid;
	}

	public Integer getCd_savingno() {
		return this.cd_savingno;
	}

	public void setCd_savingno(Integer cd_savingno) {
		this.cd_savingno = cd_savingno;
	}

	public BigDecimal getCd_paidmoney() {
		return this.cd_paidmoney;
	}

	public void setCd_paidmoney(BigDecimal cd_paidmoney) {
		this.cd_paidmoney = cd_paidmoney;
	}

	public BigDecimal getCd_chargemoney() {
		return this.cd_chargemoney;
	}

	public void setCd_chargemoney(BigDecimal cd_chargemoney) {
		this.cd_chargemoney = cd_chargemoney;
	}

	public BigDecimal getCd_chargeamount() {
		return this.cd_chargeamount;
	}

	public void setCd_chargeamount(BigDecimal cd_chargeamount) {
		this.cd_chargeamount = cd_chargeamount;
	}

	public BigDecimal getCd_basemoney() {
		return this.cd_basemoney;
	}

	public void setCd_basemoney(BigDecimal cd_basemoney) {
		this.cd_basemoney = cd_basemoney;
	}

	public BigDecimal getCd_othermoney1() {
		return this.cd_othermoney1;
	}

	public void setCd_othermoney1(BigDecimal cd_othermoney1) {
		this.cd_othermoney1 = cd_othermoney1;
	}

	public BigDecimal getCd_othermoney2() {
		return this.cd_othermoney2;
	}

	public void setCd_othermoney2(BigDecimal cd_othermoney2) {
		this.cd_othermoney2 = cd_othermoney2;
	}

	public BigDecimal getCd_lastbalance() {
		return this.cd_lastbalance;
	}

	public void setCd_lastbalance(BigDecimal cd_lastbalance) {
		this.cd_lastbalance = cd_lastbalance;
	}

	public BigDecimal getCd_balance() {
		return this.cd_balance;
	}

	public void setCd_balance(BigDecimal cd_balance) {
		this.cd_balance = cd_balance;
	}

	public Integer getCd_brushflag() {
		return this.cd_brushflag;
	}

	public void setCd_brushflag(Integer cd_brushflag) {
		this.cd_brushflag = cd_brushflag;
	}

	public Integer getCd_kindid() {
		return this.cd_kindid;
	}

	public void setCd_kindid(Integer cd_kindid) {
		this.cd_kindid = cd_kindid;
	}

	public Integer getCd_order() {
		return this.cd_order;
	}

	public void setCd_order(Integer cd_order) {
		this.cd_order = cd_order;
	}

	public Integer getCd_charge() {
		return this.cd_charge;
	}

	public void setCd_charge(Integer cd_charge) {
		this.cd_charge = cd_charge;
	}

	public Long getCd_operatorid() {
		return this.cd_operatorid;
	}

	public void setCd_operatorid(Long cd_operatorid) {
		this.cd_operatorid = cd_operatorid;
	}

	public Date getCd_happendate() {
		return this.cd_happendate;
	}

	public void setCd_happendate(Date cd_happendate) {
		this.cd_happendate = cd_happendate;
	}

	public BigDecimal getCd_startamount() {
		return this.cd_startamount;
	}

	public void setCd_startamount(BigDecimal cd_startamount) {
		this.cd_startamount = cd_startamount;
	}

	public BigDecimal getCd_endamount() {
		return this.cd_endamount;
	}

	public void setCd_endamount(BigDecimal cd_endamount) {
		this.cd_endamount = cd_endamount;
	}

	public Date getCd_startdate() {
		return this.cd_startdate;
	}

	public void setCd_startdate(Date cd_startdate) {
		this.cd_startdate = cd_startdate;
	}

	public Date getCd_enddate() {
		return this.cd_enddate;
	}

	public void setCd_enddate(Date cd_enddate) {
		this.cd_enddate = cd_enddate;
	}

	public Long getCd_readerid() {
		return this.cd_readerid;
	}

	public void setCd_readerid(Long cd_readerid) {
		this.cd_readerid = cd_readerid;
	}

	public Integer getCd_printstatus() {
		return this.cd_printstatus;
	}

	public void setCd_printstatus(Integer cd_printstatus) {
		this.cd_printstatus = cd_printstatus;
	}

	public String getCd_tradecustomer() {
		return this.cd_tradecustomer;
	}

	public void setCd_tradecustomer(String cd_tradecustomer) {
		this.cd_tradecustomer = cd_tradecustomer;
	}

	public String getCd_tradeno() {
		return this.cd_tradeno;
	}

	public void setCd_tradeno(String cd_tradeno) {
		this.cd_tradeno = cd_tradeno;
	}

	public Integer getCd_tradestatus() {
		return this.cd_tradestatus;
	}

	public void setCd_tradestatus(Integer cd_tradestatus) {
		this.cd_tradestatus = cd_tradestatus;
	}

	public BigDecimal getCd_amountton1() {
		return this.cd_amountton1;
	}

	public void setCd_amountton1(BigDecimal cd_amountton1) {
		this.cd_amountton1 = cd_amountton1;
	}

	public BigDecimal getCd_amountton2() {
		return this.cd_amountton2;
	}

	public void setCd_amountton2(BigDecimal cd_amountton2) {
		this.cd_amountton2 = cd_amountton2;
	}

	public BigDecimal getCd_amountton3() {
		return this.cd_amountton3;
	}

	public void setCd_amountton3(BigDecimal cd_amountton3) {
		this.cd_amountton3 = cd_amountton3;
	}

	public BigDecimal getCd_basemoneyton2() {
		return this.cd_basemoneyton2;
	}

	public void setCd_basemoneyton2(BigDecimal cd_basemoneyton2) {
		this.cd_basemoneyton2 = cd_basemoneyton2;
	}

	public BigDecimal getCd_basemoneyton3() {
		return this.cd_basemoneyton3;
	}

	public void setCd_basemoneyton3(BigDecimal cd_basemoneyton3) {
		this.cd_basemoneyton3 = cd_basemoneyton3;
	}

}