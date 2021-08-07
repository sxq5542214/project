package com.yd.business.device.bean;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;

/**
 * ChangeMeterBean entity. @author MyEclipse Persistence Tools
 */
@Alias("changeMeter")
public class ChangeMeterBean extends BaseBean implements java.io.Serializable {

	// Fields
	public static final int TYPE_CHANGE_MODULES = 0;  // 更换模块
	public static final int TYPE_CHANGE_DEVICE = 1;	  //  更换整表
	public static final int TYPE_CHANGE_BATTERY = 2;	// 更换电池
	public static final int TYPE_CHANGE_OTHER = 3;	// 其他
	public static final int TYPE_CHANGE_DEVICEKIND = 4;	// 更换表具类型
	
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1783573255113979857L;
	private Long cm_id;
	private Long cm_userid;
	private BigDecimal cm_oldmetercode;
	private BigDecimal cm_newmetercode;
	private Date cm_happendate;
	private Integer cm_type;
	private Integer cm_status;
	private Long cm_operatorid;
	private String cm_remark;
	private Long cm_oldmeterno;
	private Long cm_newmeterno;

	// Constructors

	/** default constructor */
	public ChangeMeterBean() {
	}

	/** minimal constructor */
	public ChangeMeterBean(Long cm_userid, BigDecimal cm_oldmetercode, BigDecimal cm_newmetercode, Date cm_happendate,
			Integer cm_type, Integer cm_status, Long cm_operatorid, Long cm_oldmeterno, Long cm_newmeterno) {
		this.cm_userid = cm_userid;
		this.cm_oldmetercode = cm_oldmetercode;
		this.cm_newmetercode = cm_newmetercode;
		this.cm_happendate = cm_happendate;
		this.cm_type = cm_type;
		this.cm_status = cm_status;
		this.cm_operatorid = cm_operatorid;
		this.cm_oldmeterno = cm_oldmeterno;
		this.cm_newmeterno = cm_newmeterno;
	}

	/** full constructor */
	public ChangeMeterBean(Long cm_userid, BigDecimal cm_oldmetercode, BigDecimal cm_newmetercode, Date cm_happendate,
			Integer cm_type, Integer cm_status, Long cm_operatorid, String cm_remark, Long cm_oldmeterno,
			Long cm_newmeterno) {
		this.cm_userid = cm_userid;
		this.cm_oldmetercode = cm_oldmetercode;
		this.cm_newmetercode = cm_newmetercode;
		this.cm_happendate = cm_happendate;
		this.cm_type = cm_type;
		this.cm_status = cm_status;
		this.cm_operatorid = cm_operatorid;
		this.cm_remark = cm_remark;
		this.cm_oldmeterno = cm_oldmeterno;
		this.cm_newmeterno = cm_newmeterno;
	}

	// Property accessors

	public Long getCm_id() {
		return this.cm_id;
	}

	public void setCm_id(Long cm_id) {
		this.cm_id = cm_id;
	}

	public Long getCm_userid() {
		return this.cm_userid;
	}

	public void setCm_userid(Long cm_userid) {
		this.cm_userid = cm_userid;
	}

	public BigDecimal getCm_oldmetercode() {
		return this.cm_oldmetercode;
	}

	public void setCm_oldmetercode(BigDecimal cm_oldmetercode) {
		this.cm_oldmetercode = cm_oldmetercode;
	}

	public BigDecimal getCm_newmetercode() {
		return this.cm_newmetercode;
	}

	public void setCm_newmetercode(BigDecimal cm_newmetercode) {
		this.cm_newmetercode = cm_newmetercode;
	}

	public Date getCm_happendate() {
		return this.cm_happendate;
	}

	public void setCm_happendate(Date cm_happendate) {
		this.cm_happendate = cm_happendate;
	}

	public Integer getCm_type() {
		return this.cm_type;
	}

	public void setCm_type(Integer cm_type) {
		this.cm_type = cm_type;
	}

	public Integer getCm_status() {
		return this.cm_status;
	}

	public void setCm_status(Integer cm_status) {
		this.cm_status = cm_status;
	}

	public Long getCm_operatorid() {
		return this.cm_operatorid;
	}

	public void setCm_operatorid(Long cm_operatorid) {
		this.cm_operatorid = cm_operatorid;
	}

	public String getCm_remark() {
		return this.cm_remark;
	}

	public void setCm_remark(String cm_remark) {
		this.cm_remark = cm_remark;
	}

	public Long getCm_oldmeterno() {
		return this.cm_oldmeterno;
	}

	public void setCm_oldmeterno(Long cm_oldmeterno) {
		this.cm_oldmeterno = cm_oldmeterno;
	}

	public Long getCm_newmeterno() {
		return this.cm_newmeterno;
	}

	public void setCm_newmeterno(Long cm_newmeterno) {
		this.cm_newmeterno = cm_newmeterno;
	}

}