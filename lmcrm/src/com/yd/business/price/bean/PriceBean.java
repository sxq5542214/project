package com.yd.business.price.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * PriceBean entity. @author MyEclipse Persistence Tools
 */
@Alias("price")
public class PriceBean implements java.io.Serializable {
	public static final int ENABLED_TRUE =1;
	public static final int ENABLED_FALSE =0;
	public static final int LADDER_YEAR = 1;
	public static final int LADDER_MONTH = 0;
	
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5258425131824204620L;
	private Long p_id;
	private Long p_companyid;  // 所属公司
	private String p_name;		// 价格名称
	private BigDecimal p_base1;  // 基本单价   一阶单价
	private BigDecimal p_ton1;	 //一阶吨限
	private BigDecimal p_base2;	 // 二阶单价
	private BigDecimal p_ton2;	 // 二阶吨限
	private BigDecimal p_base3;	 // 三阶单价
	private BigDecimal p_other1;  // 排污费
	private BigDecimal p_other2;	//其他费
	private BigDecimal p_price1;	//一阶合计
	private BigDecimal p_price2;	//二阶合计
	private BigDecimal p_price3;	//三阶合计
	private BigDecimal p_lowprice;		//保底金额
	private BigDecimal p_lowamount;		//保底量
	private BigDecimal p_limitamount;	//囤积量
	private BigDecimal p_storedamount;	//预存量
	private BigDecimal p_weakprompt;	// 报警量
	private BigDecimal p_overflat;	// 最大透支量
	private Integer p_enabled;
	private Date p_createdate;
	private Date p_updatedate;
	private Integer p_settlemonth;	// 结算月份
	private Integer p_settleday;	// 结算日
	private Integer p_ladder;  // 结算模式
	private Integer seq ; //排序  越小越先
	// Constructors

	/** default constructor */
	public PriceBean() {
	}

	/** full constructor */
	public PriceBean(Long p_companyid, String p_name, BigDecimal p_base1, BigDecimal p_ton1, BigDecimal p_base2,
			BigDecimal p_ton2, BigDecimal p_base3, BigDecimal p_other1, BigDecimal p_other2, BigDecimal p_price1,
			BigDecimal p_price2, BigDecimal p_price3, BigDecimal p_lowprice, BigDecimal p_lowamount,
			BigDecimal p_limitamount, BigDecimal p_storedamount, BigDecimal p_weakprompt, BigDecimal p_overflat,
			Integer p_enabled, Date p_createdate, Date p_updatedate, Integer p_settlemonth, Integer p_settleday,
			Integer p_ladder) {
		this.p_companyid = p_companyid;
		this.p_name = p_name;
		this.p_base1 = p_base1;
		this.p_ton1 = p_ton1;
		this.p_base2 = p_base2;
		this.p_ton2 = p_ton2;
		this.p_base3 = p_base3;
		this.p_other1 = p_other1;
		this.p_other2 = p_other2;
		this.p_price1 = p_price1;
		this.p_price2 = p_price2;
		this.p_price3 = p_price3;
		this.p_lowprice = p_lowprice;
		this.p_lowamount = p_lowamount;
		this.p_limitamount = p_limitamount;
		this.p_storedamount = p_storedamount;
		this.p_weakprompt = p_weakprompt;
		this.p_overflat = p_overflat;
		this.p_enabled = p_enabled;
		this.p_createdate = p_createdate;
		this.p_updatedate = p_updatedate;
		this.p_settlemonth = p_settlemonth;
		this.p_settleday = p_settleday;
		this.p_ladder = p_ladder;
	}

	// Property accessors

	public Long getP_id() {
		return this.p_id;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public void setP_id(Long p_id) {
		this.p_id = p_id;
	}

	public Long getP_companyid() {
		return this.p_companyid;
	}

	public void setP_companyid(Long p_companyid) {
		this.p_companyid = p_companyid;
	}

	public String getP_name() {
		return this.p_name;
	}

	public void setP_name(String p_name) {
		this.p_name = p_name;
	}

	public BigDecimal getP_base1() {
		return this.p_base1;
	}

	public void setP_base1(BigDecimal p_base1) {
		this.p_base1 = p_base1;
	}

	public BigDecimal getP_ton1() {
		return this.p_ton1;
	}

	public void setP_ton1(BigDecimal p_ton1) {
		this.p_ton1 = p_ton1;
	}

	public BigDecimal getP_base2() {
		return this.p_base2;
	}

	public void setP_base2(BigDecimal p_base2) {
		this.p_base2 = p_base2;
	}

	public BigDecimal getP_ton2() {
		return this.p_ton2;
	}

	public void setP_ton2(BigDecimal p_ton2) {
		this.p_ton2 = p_ton2;
	}

	public BigDecimal getP_base3() {
		return this.p_base3;
	}

	public void setP_base3(BigDecimal p_base3) {
		this.p_base3 = p_base3;
	}

	public BigDecimal getP_other1() {
		return this.p_other1;
	}

	public void setP_other1(BigDecimal p_other1) {
		this.p_other1 = p_other1;
	}

	public BigDecimal getP_other2() {
		return this.p_other2;
	}

	public void setP_other2(BigDecimal p_other2) {
		this.p_other2 = p_other2;
	}

	public BigDecimal getP_price1() {
		return this.p_price1;
	}

	public void setP_price1(BigDecimal p_price1) {
		this.p_price1 = p_price1;
	}

	public BigDecimal getP_price2() {
		return this.p_price2;
	}

	public void setP_price2(BigDecimal p_price2) {
		this.p_price2 = p_price2;
	}

	public BigDecimal getP_price3() {
		return this.p_price3;
	}

	public void setP_price3(BigDecimal p_price3) {
		this.p_price3 = p_price3;
	}

	public BigDecimal getP_lowprice() {
		return this.p_lowprice;
	}

	public void setP_lowprice(BigDecimal p_lowprice) {
		this.p_lowprice = p_lowprice;
	}

	public BigDecimal getP_lowamount() {
		return this.p_lowamount;
	}

	public void setP_lowamount(BigDecimal p_lowamount) {
		this.p_lowamount = p_lowamount;
	}

	public BigDecimal getP_limitamount() {
		return this.p_limitamount;
	}

	public void setP_limitamount(BigDecimal p_limitamount) {
		this.p_limitamount = p_limitamount;
	}

	public BigDecimal getP_storedamount() {
		return this.p_storedamount;
	}

	public void setP_storedamount(BigDecimal p_storedamount) {
		this.p_storedamount = p_storedamount;
	}

	public BigDecimal getP_weakprompt() {
		return this.p_weakprompt;
	}

	public void setP_weakprompt(BigDecimal p_weakprompt) {
		this.p_weakprompt = p_weakprompt;
	}

	public BigDecimal getP_overflat() {
		return this.p_overflat;
	}

	public void setP_overflat(BigDecimal p_overflat) {
		this.p_overflat = p_overflat;
	}

	public Integer getP_enabled() {
		return this.p_enabled;
	}

	public void setP_enabled(Integer p_enabled) {
		this.p_enabled = p_enabled;
	}

	public Date getP_createdate() {
		return this.p_createdate;
	}

	public void setP_createdate(Date p_createdate) {
		this.p_createdate = p_createdate;
	}

	public Date getP_updatedate() {
		return this.p_updatedate;
	}

	public void setP_updatedate(Date p_updatedate) {
		this.p_updatedate = p_updatedate;
	}

	public Integer getP_settlemonth() {
		return this.p_settlemonth;
	}

	public void setP_settlemonth(Integer p_settlemonth) {
		this.p_settlemonth = p_settlemonth;
	}

	public Integer getP_settleday() {
		return this.p_settleday;
	}

	public void setP_settleday(Integer p_settleday) {
		this.p_settleday = p_settleday;
	}

	public Integer getP_ladder() {
		return this.p_ladder;
	}

	public void setP_ladder(Integer p_ladder) {
		this.p_ladder = p_ladder;
	}

}