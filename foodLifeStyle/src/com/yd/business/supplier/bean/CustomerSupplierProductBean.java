/**
 * 
 */
package com.yd.business.supplier.bean;

import java.util.List;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.business.area.bean.AreaDataBean;

/**
 * @author ice
 *
 */
public class CustomerSupplierProductBean extends BaseBean {
	private List<SupplierProductBean> listSupplierBean;
	private SupplierBean supplierBean;
	private AreaDataBean areaData;
	
	public List<SupplierProductBean> getListSupplierBean() {
		return listSupplierBean;
	}
	public AreaDataBean getAreaData() {
		return areaData;
	}
	public void setAreaData(AreaDataBean areaData) {
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
	public CustomerSupplierProductBean(AreaDataBean areaData, SupplierBean supplierBean,List<SupplierProductBean> listSupplierBean) {
		super();
		this.areaData = areaData;
		this.listSupplierBean = listSupplierBean;
		this.supplierBean = supplierBean;
	}
	
	
	
	
}
