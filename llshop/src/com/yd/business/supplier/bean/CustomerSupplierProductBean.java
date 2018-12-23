/**
 * 
 */
package com.yd.business.supplier.bean;

import java.util.List;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.order.bean.AreaData;
import com.yd.business.product.bean.SupplierProductBean;

/**
 * @author ice
 *
 */
public class CustomerSupplierProductBean extends BaseBean {
	private List<SupplierProductBean> listSupplierBean;
	private SupplierBean supplierBean;
	private AreaData areaData;
	
	public List<SupplierProductBean> getListSupplierBean() {
		return listSupplierBean;
	}
	public AreaData getAreaData() {
		return areaData;
	}
	public void setAreaData(AreaData areaData) {
		this.areaData = areaData;
	}
	public void setListSupplierBean(List<SupplierProductBean> listSupplierBean) {
		this.listSupplierBean = listSupplierBean;
	}
	public SupplierBean getSupplierBean() {
		return supplierBean;
	}
	public void setSupplierBean(SupplierBean supplierBean) {
		this.supplierBean = supplierBean;
	}
	
	public CustomerSupplierProductBean(){}
	public CustomerSupplierProductBean(AreaData areaData, SupplierBean supplierBean,List<SupplierProductBean> listSupplierBean) {
		super();
		this.areaData = areaData;
		this.listSupplierBean = listSupplierBean;
		this.supplierBean = supplierBean;
	}
	
	
	
	
}
