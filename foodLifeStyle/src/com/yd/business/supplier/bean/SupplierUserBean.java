/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.user.bean.UserWechatBean;
import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 *
 */
@Alias("supplierUser")
public class SupplierUserBean extends UserWechatBean {
	private Integer user_id;
	private Integer supplier_id;
	
	public SupplierUserBean() {}
	
	public SupplierUserBean(UserWechatBean user) throws Exception {
		AutoInvokeGetSetMethod.autoInvoke(user, this);
		this.setId(null);
	}
	
	public Integer getUser_id() {
		return user_id;
	}
	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	public Integer getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	
}
