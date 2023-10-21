package com.yd.business.device.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

/**
 * DeviceInfoBean entity. @author MyEclipse Persistence Tools
 */
@Alias("deviceInfo")
public class DeviceInfoBean implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5095310625119898588L;
	private Long di_id;
	private Long di_userid;
	private Long di_dkid;
	private BigDecimal di_totalamount;
	private BigDecimal di_totalmoney;
	private BigDecimal di_leaveamount;
	private BigDecimal di_overflatamount;
	private Integer di_electricstatus;
	private Integer di_magneticstatus;
	private Integer di_motstatus;
	private Integer di_overflatstatus;
	private Integer di_brushflag;
	private BigDecimal di_cardleave;
	private BigDecimal di_curamount;
	private Date di_brushdate;
	private BigDecimal di_useramount;
	private BigDecimal di_useramount1;
	private BigDecimal di_useramount2;
	private BigDecimal di_useramount3;
	private BigDecimal di_useramount4;
	private BigDecimal di_useramount5;
	private BigDecimal di_useramount6;
	private BigDecimal di_useramount7;
	private BigDecimal di_useramount8;
	private BigDecimal di_useramount9;
	private BigDecimal di_useramount10;
	private BigDecimal di_useramount11;
	private BigDecimal di_useramount12;
	private BigDecimal di_storedamount;
	private Date di_createdate;
	private Date di_updatedate;
	private BigDecimal di_signalintensity;
	private BigDecimal di_batteryvoltage;
	private String device_company;

	// Constructors

	/** default constructor */
	public DeviceInfoBean() {
	}

	/** full constructor */
	public DeviceInfoBean(Long di_userid, Long di_dkid, BigDecimal di_totalamount, BigDecimal di_totalmoney,
			BigDecimal di_leaveamount, BigDecimal di_overflatamount, Integer di_electricstatus,
			Integer di_magneticstatus, Integer di_motstatus, Integer di_overflatstatus, Integer di_brushflag,
			BigDecimal di_cardleave, BigDecimal di_curamount, Date di_brushdate, BigDecimal di_useramount,
			BigDecimal di_useramount1, BigDecimal di_useramount2, BigDecimal di_useramount3, BigDecimal di_useramount4,
			BigDecimal di_useramount5, BigDecimal di_useramount6, BigDecimal di_useramount7, BigDecimal di_useramount8,
			BigDecimal di_useramount9, BigDecimal di_useramount10, BigDecimal di_useramount11,
			BigDecimal di_useramount12, BigDecimal di_storedamount, Date di_createdate, Date di_updatedate,
			BigDecimal di_signalintensity, BigDecimal di_batteryvoltage) {
		this.di_userid = di_userid;
		this.di_dkid = di_dkid;
		this.di_totalamount = di_totalamount;
		this.di_totalmoney = di_totalmoney;
		this.di_leaveamount = di_leaveamount;
		this.di_overflatamount = di_overflatamount;
		this.di_electricstatus = di_electricstatus;
		this.di_magneticstatus = di_magneticstatus;
		this.di_motstatus = di_motstatus;
		this.di_overflatstatus = di_overflatstatus;
		this.di_brushflag = di_brushflag;
		this.di_cardleave = di_cardleave;
		this.di_curamount = di_curamount;
		this.di_brushdate = di_brushdate;
		this.di_useramount = di_useramount;
		this.di_useramount1 = di_useramount1;
		this.di_useramount2 = di_useramount2;
		this.di_useramount3 = di_useramount3;
		this.di_useramount4 = di_useramount4;
		this.di_useramount5 = di_useramount5;
		this.di_useramount6 = di_useramount6;
		this.di_useramount7 = di_useramount7;
		this.di_useramount8 = di_useramount8;
		this.di_useramount9 = di_useramount9;
		this.di_useramount10 = di_useramount10;
		this.di_useramount11 = di_useramount11;
		this.di_useramount12 = di_useramount12;
		this.di_storedamount = di_storedamount;
		this.di_createdate = di_createdate;
		this.di_updatedate = di_updatedate;
		this.di_signalintensity = di_signalintensity;
		this.di_batteryvoltage = di_batteryvoltage;
	}

	// Property accessors

	public Long getDi_id() {
		return this.di_id;
	}

	public String getDevice_company() {
		return device_company;
	}

	public void setDevice_company(String device_company) {
		this.device_company = device_company;
	}

	public void setDi_id(Long di_id) {
		this.di_id = di_id;
	}

	public Long getDi_userid() {
		return this.di_userid;
	}

	public void setDi_userid(Long di_userid) {
		this.di_userid = di_userid;
	}

	public Long getDi_dkid() {
		return this.di_dkid;
	}

	public void setDi_dkid(Long di_dkid) {
		this.di_dkid = di_dkid;
	}

	public BigDecimal getDi_totalamount() {
		return this.di_totalamount;
	}

	public void setDi_totalamount(BigDecimal di_totalamount) {
		this.di_totalamount = di_totalamount;
	}

	public BigDecimal getDi_totalmoney() {
		return this.di_totalmoney;
	}

	public void setDi_totalmoney(BigDecimal di_totalmoney) {
		this.di_totalmoney = di_totalmoney;
	}

	public BigDecimal getDi_leaveamount() {
		return this.di_leaveamount;
	}

	public void setDi_leaveamount(BigDecimal di_leaveamount) {
		this.di_leaveamount = di_leaveamount;
	}

	public BigDecimal getDi_overflatamount() {
		return this.di_overflatamount;
	}

	public void setDi_overflatamount(BigDecimal di_overflatamount) {
		this.di_overflatamount = di_overflatamount;
	}

	public Integer getDi_electricstatus() {
		return this.di_electricstatus;
	}

	public void setDi_electricstatus(Integer di_electricstatus) {
		this.di_electricstatus = di_electricstatus;
	}

	public Integer getDi_magneticstatus() {
		return this.di_magneticstatus;
	}

	public void setDi_magneticstatus(Integer di_magneticstatus) {
		this.di_magneticstatus = di_magneticstatus;
	}

	public Integer getDi_motstatus() {
		return this.di_motstatus;
	}

	public void setDi_motstatus(Integer di_motstatus) {
		this.di_motstatus = di_motstatus;
	}

	public Integer getDi_overflatstatus() {
		return this.di_overflatstatus;
	}

	public void setDi_overflatstatus(Integer di_overflatstatus) {
		this.di_overflatstatus = di_overflatstatus;
	}

	public Integer getDi_brushflag() {
		return this.di_brushflag;
	}

	public void setDi_brushflag(Integer di_brushflag) {
		this.di_brushflag = di_brushflag;
	}

	public BigDecimal getDi_cardleave() {
		return this.di_cardleave;
	}

	public void setDi_cardleave(BigDecimal di_cardleave) {
		this.di_cardleave = di_cardleave;
	}

	public BigDecimal getDi_curamount() {
		return this.di_curamount;
	}

	public void setDi_curamount(BigDecimal di_curamount) {
		this.di_curamount = di_curamount;
	}

	public Date getDi_brushdate() {
		return this.di_brushdate;
	}

	public void setDi_brushdate(Date di_brushdate) {
		this.di_brushdate = di_brushdate;
	}

	public BigDecimal getDi_useramount() {
		return this.di_useramount;
	}

	public void setDi_useramount(BigDecimal di_useramount) {
		this.di_useramount = di_useramount;
	}

	public BigDecimal getDi_useramount1() {
		return this.di_useramount1;
	}

	public void setDi_useramount1(BigDecimal di_useramount1) {
		this.di_useramount1 = di_useramount1;
	}

	public BigDecimal getDi_useramount2() {
		return this.di_useramount2;
	}

	public void setDi_useramount2(BigDecimal di_useramount2) {
		this.di_useramount2 = di_useramount2;
	}

	public BigDecimal getDi_useramount3() {
		return this.di_useramount3;
	}

	public void setDi_useramount3(BigDecimal di_useramount3) {
		this.di_useramount3 = di_useramount3;
	}

	public BigDecimal getDi_useramount4() {
		return this.di_useramount4;
	}

	public void setDi_useramount4(BigDecimal di_useramount4) {
		this.di_useramount4 = di_useramount4;
	}

	public BigDecimal getDi_useramount5() {
		return this.di_useramount5;
	}

	public void setDi_useramount5(BigDecimal di_useramount5) {
		this.di_useramount5 = di_useramount5;
	}

	public BigDecimal getDi_useramount6() {
		return this.di_useramount6;
	}

	public void setDi_useramount6(BigDecimal di_useramount6) {
		this.di_useramount6 = di_useramount6;
	}

	public BigDecimal getDi_useramount7() {
		return this.di_useramount7;
	}

	public void setDi_useramount7(BigDecimal di_useramount7) {
		this.di_useramount7 = di_useramount7;
	}

	public BigDecimal getDi_useramount8() {
		return this.di_useramount8;
	}

	public void setDi_useramount8(BigDecimal di_useramount8) {
		this.di_useramount8 = di_useramount8;
	}

	public BigDecimal getDi_useramount9() {
		return this.di_useramount9;
	}

	public void setDi_useramount9(BigDecimal di_useramount9) {
		this.di_useramount9 = di_useramount9;
	}

	public BigDecimal getDi_useramount10() {
		return this.di_useramount10;
	}

	public void setDi_useramount10(BigDecimal di_useramount10) {
		this.di_useramount10 = di_useramount10;
	}

	public BigDecimal getDi_useramount11() {
		return this.di_useramount11;
	}

	public void setDi_useramount11(BigDecimal di_useramount11) {
		this.di_useramount11 = di_useramount11;
	}

	public BigDecimal getDi_useramount12() {
		return this.di_useramount12;
	}

	public void setDi_useramount12(BigDecimal di_useramount12) {
		this.di_useramount12 = di_useramount12;
	}

	public BigDecimal getDi_storedamount() {
		return this.di_storedamount;
	}

	public void setDi_storedamount(BigDecimal di_storedamount) {
		this.di_storedamount = di_storedamount;
	}

	public Date getDi_createdate() {
		return this.di_createdate;
	}

	public void setDi_createdate(Date di_createdate) {
		this.di_createdate = di_createdate;
	}

	public Date getDi_updatedate() {
		return this.di_updatedate;
	}

	public void setDi_updatedate(Date di_updatedate) {
		this.di_updatedate = di_updatedate;
	}

	public BigDecimal getDi_signalintensity() {
		return this.di_signalintensity;
	}

	public void setDi_signalintensity(BigDecimal di_signalintensity) {
		this.di_signalintensity = di_signalintensity;
	}

	public BigDecimal getDi_batteryvoltage() {
		return this.di_batteryvoltage;
	}

	public void setDi_batteryvoltage(BigDecimal di_batteryvoltage) {
		this.di_batteryvoltage = di_batteryvoltage;
	}

}