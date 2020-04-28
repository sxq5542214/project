/**
 * 
 */
package com.yd.business.user.bean;

import java.util.List;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.product.bean.SupplierProductBean;

/**
 * @author ice
 *
 */
public class UserCartBean extends BaseBean {
	
	private Integer user_id ;
	private String openid;
	private String nick_name;
	private List<CartInfo> cartInfoList;
	
	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}

	public List<CartInfo> getCartInfoList() {
		return cartInfoList;
	}

	public void setCartInfoList(List<CartInfo> cartInfoList) {
		this.cartInfoList = cartInfoList;
	}

	public class CartInfo{
		private SupplierProductBean supplierProduct;
		private Integer num;
		private Integer total_price;
		
		public Integer getTotal_price() {
			return total_price;
		}
		public void setTotal_price(Integer total_price) {
			this.total_price = total_price;
		}
		public SupplierProductBean getSupplierProduct() {
			return supplierProduct;
		}
		public void setSupplierProduct(SupplierProductBean supplierProduct) {
			this.supplierProduct = supplierProduct;
		}
		public Integer getNum() {
			return num;
		}
		public void setNum(Integer num) {
			this.num = num;
		}
	}
}
