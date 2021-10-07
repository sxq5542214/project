package com.yd.business.user.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.pageination.PageinationData;

/**
 * UserInfoBean entity. @author MyEclipse Persistence Tools
 */
@Alias("userInfo")
public class UserInfoBean extends PageinationData implements java.io.Serializable  {
	/**
	 * 未开户
	 */
	public static final int STATUS_UNOPEN = 6; 
//	/**
//	 * 待审核
//	 */
//	public static final int STATUS_WAIT_AUDIT = 6; 
	/**
	 * 正常，已开户
	 */
	public static final int STATUS_NORMAL = 7;
	/**
	 * 报停
	 */
	public static final int STATUS_STOP = 8;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5811069669937812165L;
	// Fields

	private Long u_id;
	private Long u_buildingid;
	private Long u_priceid;
	private Integer u_group;
	private Long u_no;
	private Integer u_cardno;
	private String u_address;
	private String u_name;
	private String u_phone;
	private String u_paperwork;
	private String u_remark;
	private Integer u_peoplesize;
	private BigDecimal u_materialfee;
	private BigDecimal u_constructioncost;
	private BigDecimal u_balance;
	private Integer u_status;
	private Long u_operatorid;
	private Date u_startdate;
	private Date u_createdate;
	private Date u_updatedate;
	private Integer u_prepayment;
	private Integer addressId;

	//extends field
	private Long areaid;
	private String priceName;
	private String deviceKindName;
	private String addressName;
	// Constructors

	/** default constructor */
	public UserInfoBean() {
	}


	// Property accessors

	public Long getU_id() {
		return this.u_id;
	}

	public Integer getAddressId() {
		return addressId;
	}


	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}


	public String getAddressName() {
		return addressName;
	}


	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}


	public Long getAreaid() {
		return areaid;
	}

	public String getPriceName() {
		return priceName;
	}

	public void setPriceName(String priceName) {
		this.priceName = priceName;
	}

	public String getDeviceKindName() {
		return deviceKindName;
	}

	public void setDeviceKindName(String deviceKindName) {
		this.deviceKindName = deviceKindName;
	}

	public void setAreaid(Long areaid) {
		this.areaid = areaid;
	}

	public void setU_id(Long u_id) {
		this.u_id = u_id;
	}

	public Long getU_buildingid() {
		return this.u_buildingid;
	}

	public void setU_buildingid(Long u_buildingid) {
		this.u_buildingid = u_buildingid;
	}

	public Long getU_priceid() {
		return this.u_priceid;
	}

	public void setU_priceid(Long u_priceid) {
		this.u_priceid = u_priceid;
	}

	public Integer getU_group() {
		return this.u_group;
	}

	public void setU_group(Integer u_group) {
		this.u_group = u_group;
	}

	public Long getU_no() {
		return this.u_no;
	}

	public void setU_no(Long u_no) {
		this.u_no = u_no;
	}

	public Integer getU_cardno() {
		return this.u_cardno;
	}

	public void setU_cardno(Integer u_cardno) {
		this.u_cardno = u_cardno;
	}

	public String getU_address() {
		return this.u_address;
	}

	public void setU_address(String u_address) {
		this.u_address = u_address;
	}

	public String getU_name() {
		return this.u_name;
	}

	public void setU_name(String u_name) {
		this.u_name = u_name;
	}

	public String getU_phone() {
		return this.u_phone;
	}

	public void setU_phone(String u_phone) {
		this.u_phone = u_phone;
	}

	public String getU_paperwork() {
		return this.u_paperwork;
	}

	public void setU_paperwork(String u_paperwork) {
		this.u_paperwork = u_paperwork;
	}

	public String getU_remark() {
		return this.u_remark;
	}

	public void setU_remark(String u_remark) {
		this.u_remark = u_remark;
	}

	public Integer getU_peoplesize() {
		return this.u_peoplesize;
	}

	public void setU_peoplesize(Integer u_peoplesize) {
		this.u_peoplesize = u_peoplesize;
	}

	public BigDecimal getU_materialfee() {
		return this.u_materialfee;
	}

	public void setU_materialfee(BigDecimal u_materialfee) {
		this.u_materialfee = u_materialfee;
	}

	public BigDecimal getU_constructioncost() {
		return this.u_constructioncost;
	}

	public void setU_constructioncost(BigDecimal u_constructioncost) {
		this.u_constructioncost = u_constructioncost;
	}

	public BigDecimal getU_balance() {
		return this.u_balance;
	}

	public void setU_balance(BigDecimal u_balance) {
		this.u_balance = u_balance;
	}

	public Integer getU_status() {
		return this.u_status;
	}

	public void setU_status(Integer u_status) {
		this.u_status = u_status;
	}

	public Long getU_operatorid() {
		return this.u_operatorid;
	}

	public void setU_operatorid(Long u_operatorid) {
		this.u_operatorid = u_operatorid;
	}

	public Date getU_startdate() {
		return this.u_startdate;
	}

	public void setU_startdate(Date u_startdate) {
		this.u_startdate = u_startdate;
	}

	public Date getU_createdate() {
		return this.u_createdate;
	}

	public void setU_createdate(Date u_createdate) {
		this.u_createdate = u_createdate;
	}

	public Date getU_updatedate() {
		return this.u_updatedate;
	}

	public void setU_updatedate(Date u_updatedate) {
		this.u_updatedate = u_updatedate;
	}

	public Integer getU_prepayment() {
		return this.u_prepayment;
	}

	public void setU_prepayment(Integer u_prepayment) {
		this.u_prepayment = u_prepayment;
	}

}