package com.yd.business.operator.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * OperatorBean entity. @author MyEclipse Persistence Tools
 */
@Alias("operator")
public class OperatorBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4109613312546496072L;
	private Long o_id;
	private Long o_companyid;
	private String o_name;
	private String o_password;
	private Integer o_kind;
	private String o_rank1;
	private String o_rank2;
	private String o_rank3;
	private String o_rank4;
	private String o_rank5;
	private String o_rank6;
	private String o_rank7;
	private String o_rank8;
	private String o_rank9;
	private String o_rank99;
	private Integer o_status;
	private Date o_createdate;
	private Date o_updatedate;
	private Integer o_openaudit;
	private BigDecimal o_limitmoney;

	// Constructors

	/** default constructor */
	public OperatorBean() {
	}

	/** full constructor */
	public OperatorBean(Long o_companyid, String o_name, String o_password, Integer o_kind, String o_rank1,
			String o_rank2, String o_rank3, String o_rank4, String o_rank5, String o_rank6, String o_rank7,
			String o_rank8, String o_rank9, String o_rank99, Integer o_status, Date o_createdate, Date o_updatedate,
			Integer o_openaudit, BigDecimal o_limitmoney) {
		this.o_companyid = o_companyid;
		this.o_name = o_name;
		this.o_password = o_password;
		this.o_kind = o_kind;
		this.o_rank1 = o_rank1;
		this.o_rank2 = o_rank2;
		this.o_rank3 = o_rank3;
		this.o_rank4 = o_rank4;
		this.o_rank5 = o_rank5;
		this.o_rank6 = o_rank6;
		this.o_rank7 = o_rank7;
		this.o_rank8 = o_rank8;
		this.o_rank9 = o_rank9;
		this.o_rank99 = o_rank99;
		this.o_status = o_status;
		this.o_createdate = o_createdate;
		this.o_updatedate = o_updatedate;
		this.o_openaudit = o_openaudit;
		this.o_limitmoney = o_limitmoney;
	}

	// Property accessors

	public Long getO_id() {
		return this.o_id;
	}

	public void setO_id(Long o_id) {
		this.o_id = o_id;
	}

	public Long getO_companyid() {
		return this.o_companyid;
	}

	public void setO_companyid(Long o_companyid) {
		this.o_companyid = o_companyid;
	}

	public String getO_name() {
		return this.o_name;
	}

	public void setO_name(String o_name) {
		this.o_name = o_name;
	}

	public String getO_password() {
		return this.o_password;
	}

	public void setO_password(String o_password) {
		this.o_password = o_password;
	}

	public Integer getO_kind() {
		return this.o_kind;
	}

	public void setO_kind(Integer o_kind) {
		this.o_kind = o_kind;
	}

	public String getO_rank1() {
		return this.o_rank1;
	}

	public void setO_rank1(String o_rank1) {
		this.o_rank1 = o_rank1;
	}

	public String getO_rank2() {
		return this.o_rank2;
	}

	public void setO_rank2(String o_rank2) {
		this.o_rank2 = o_rank2;
	}

	public String getO_rank3() {
		return this.o_rank3;
	}

	public void setO_rank3(String o_rank3) {
		this.o_rank3 = o_rank3;
	}

	public String getO_rank4() {
		return this.o_rank4;
	}

	public void setO_rank4(String o_rank4) {
		this.o_rank4 = o_rank4;
	}

	public String getO_rank5() {
		return this.o_rank5;
	}

	public void setO_rank5(String o_rank5) {
		this.o_rank5 = o_rank5;
	}

	public String getO_rank6() {
		return this.o_rank6;
	}

	public void setO_rank6(String o_rank6) {
		this.o_rank6 = o_rank6;
	}

	public String getO_rank7() {
		return this.o_rank7;
	}

	public void setO_rank7(String o_rank7) {
		this.o_rank7 = o_rank7;
	}

	public String getO_rank8() {
		return this.o_rank8;
	}

	public void setO_rank8(String o_rank8) {
		this.o_rank8 = o_rank8;
	}

	public String getO_rank9() {
		return this.o_rank9;
	}

	public void setO_rank9(String o_rank9) {
		this.o_rank9 = o_rank9;
	}

	public String getO_rank99() {
		return this.o_rank99;
	}

	public void setO_rank99(String o_rank99) {
		this.o_rank99 = o_rank99;
	}

	public Integer getO_status() {
		return this.o_status;
	}

	public void setO_status(Integer o_status) {
		this.o_status = o_status;
	}

	public Date getO_createdate() {
		return this.o_createdate;
	}

	public void setO_createdate(Date o_createdate) {
		this.o_createdate = o_createdate;
	}

	public Date getO_updatedate() {
		return this.o_updatedate;
	}

	public void setO_updatedate(Date o_updatedate) {
		this.o_updatedate = o_updatedate;
	}

	public Integer getO_openaudit() {
		return this.o_openaudit;
	}

	public void setO_openaudit(Integer o_openaudit) {
		this.o_openaudit = o_openaudit;
	}

	public BigDecimal getO_limitmoney() {
		return this.o_limitmoney;
	}

	public void setO_limitmoney(BigDecimal o_limitmoney) {
		this.o_limitmoney = o_limitmoney;
	}

}