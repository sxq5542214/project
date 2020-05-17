/**
 * 
 */
package com.yd.business.order.bean;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.type.Alias;

import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 *
 */
@Alias("shopOrderEffInfo")
public class ShopOrderEffInfoBean extends ShopOrderInfoBean {
	private Integer old_order_id;
	private String eff_date;
//	private List<ShopOrderEffProductBean> effProductList;
	
	public ShopOrderEffInfoBean(){
		super();
	}
	public ShopOrderEffInfoBean(ShopOrderInfoBean bean) throws Exception{
		AutoInvokeGetSetMethod.autoInvoke(bean, this);
		this.setProductList(bean.getProductList());
//		if(bean.getProductList() != null) {
//			for( ShopOrderProductBean prod : bean.getProductList()) {
//				if(effProductList == null) {
//					effProductList = new ArrayList<ShopOrderEffProductBean>();
//				}
//				effProductList.add(new ShopOrderEffProductBean(prod));
//			}
//		}
	}
	public String getEff_date() {
		return eff_date;
	}

	public void setEff_date(String eff_date) {
		this.eff_date = eff_date;
	}
	public Integer getOld_order_id() {
		return old_order_id;
	}
	public void setOld_order_id(Integer old_order_id) {
		this.old_order_id = old_order_id;
	}

//	public List<ShopOrderEffProductBean> getEffProductList() {
//		return effProductList;
//	}
//
//	public void setEffProductList(List<ShopOrderEffProductBean> effProductList) {
//		this.effProductList = effProductList;
//	}

	
}
