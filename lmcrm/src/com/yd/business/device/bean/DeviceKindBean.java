package com.yd.business.device.bean;

import org.apache.ibatis.type.Alias;

/**
 * DeviceKindBean entity. @author MyEclipse Persistence Tools
 */
@Alias("deviceKind")
public class DeviceKindBean implements java.io.Serializable {

	public static final int ENABLED_TRUE = 1;
	public static final int ENABLED_FALSE = 0;

	
	
	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8771381528851578817L;
	private Long dk_id;
	private String dk_name;
	private Integer dk_kind;
	private Integer dk_meterkind;
	private Integer dk_no;
	private String dk_key;
	private String dk_commpath;
	private Integer dk_enabled;
	private Integer dk_commmode;

	// Constructors

	/** default constructor */
	public DeviceKindBean() {
	}

	/** minimal constructor */
	public DeviceKindBean(String dk_name, Integer dk_kind, Integer dk_meterkind, Integer dk_no, String dk_key,
			Integer dk_enabled, Integer dk_commmode) {
		this.dk_name = dk_name;
		this.dk_kind = dk_kind;
		this.dk_meterkind = dk_meterkind;
		this.dk_no = dk_no;
		this.dk_key = dk_key;
		this.dk_enabled = dk_enabled;
		this.dk_commmode = dk_commmode;
	}

	/** full constructor */
	public DeviceKindBean(String dk_name, Integer dk_kind, Integer dk_meterkind, Integer dk_no, String dk_key,
			String dk_commpath, Integer dk_enabled, Integer dk_commmode) {
		this.dk_name = dk_name;
		this.dk_kind = dk_kind;
		this.dk_meterkind = dk_meterkind;
		this.dk_no = dk_no;
		this.dk_key = dk_key;
		this.dk_commpath = dk_commpath;
		this.dk_enabled = dk_enabled;
		this.dk_commmode = dk_commmode;
	}

	// Property accessors

	public Long getDk_id() {
		return this.dk_id;
	}

	public void setDk_id(Long dk_id) {
		this.dk_id = dk_id;
	}

	public String getDk_name() {
		return this.dk_name;
	}

	public void setDk_name(String dk_name) {
		this.dk_name = dk_name;
	}

	public Integer getDk_kind() {
		return this.dk_kind;
	}

	public void setDk_kind(Integer dk_kind) {
		this.dk_kind = dk_kind;
	}

	public Integer getDk_meterkind() {
		return this.dk_meterkind;
	}

	public void setDk_meterkind(Integer dk_meterkind) {
		this.dk_meterkind = dk_meterkind;
	}

	public Integer getDk_no() {
		return this.dk_no;
	}

	public void setDk_no(Integer dk_no) {
		this.dk_no = dk_no;
	}

	public String getDk_key() {
		return this.dk_key;
	}

	public void setDk_key(String dk_key) {
		this.dk_key = dk_key;
	}

	public String getDk_commpath() {
		return this.dk_commpath;
	}

	public void setDk_commpath(String dk_commpath) {
		this.dk_commpath = dk_commpath;
	}

	public Integer getDk_enabled() {
		return this.dk_enabled;
	}

	public void setDk_enabled(Integer dk_enabled) {
		this.dk_enabled = dk_enabled;
	}

	public Integer getDk_commmode() {
		return this.dk_commmode;
	}

	public void setDk_commmode(Integer dk_commmode) {
		this.dk_commmode = dk_commmode;
	}

}