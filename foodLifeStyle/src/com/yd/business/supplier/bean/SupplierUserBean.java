/**
 * 
 */
package com.yd.business.supplier.bean;

import org.apache.ibatis.type.Alias;

import com.yd.business.user.bean.UserWechatBean;
import com.yd.util.AutoInvokeGetSetMethod;

import sun.util.logging.resources.logging;

/**
 * @author ice
 *
 */
@Alias("supplierUser")
public class SupplierUserBean extends UserWechatBean {
	private Integer user_id;
	private Integer supplier_id;
	
	public SupplierUserBean() {}
	
	public SupplierUserBean(UserWechatBean user) {
		try {
			AutoInvokeGetSetMethod.autoInvoke(user, this);
		} catch (Exception e) {
			log.error(e,e);
			
			this.setOpenid(user.getOpenid());
			this.setHead_img(user.getHead_img());
			this.setNick_name(user.getNick_name());
			this.setOriginalid(user.getOriginalid());
			this.setStatus(user.getStatus());
			this.setPhone(user.getPhone());
		}
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
