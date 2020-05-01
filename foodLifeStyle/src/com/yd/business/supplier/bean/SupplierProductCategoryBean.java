package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.basic.framework.bean.BaseBean;
/**
 * 商户商品类型信息表
 * @author ice
 *
 */
@Alias("supplierProductCategory")
public class SupplierProductCategoryBean extends BaseBean {
	
	public static int STATUS_NO = 0;
	public static int STATUS_YES = 1;
	

	private Integer id;
	private Integer supplier_id;
	private Integer supplier_type_id;
	private String name;
	private String code;
	private Integer status;
	private String create_time;
	private String modify_time;
	private Integer seq;
	private Integer tag_flag;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Integer getSupplier_type_id() {
		return supplier_type_id;
	}
	public void setSupplier_type_id(Integer supplier_type_id) {
		this.supplier_type_id = supplier_type_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Integer getTag_flag() {
		return tag_flag;
	}
	public void setTag_flag(Integer tag_flag) {
		this.tag_flag = tag_flag;
	}
	
}
