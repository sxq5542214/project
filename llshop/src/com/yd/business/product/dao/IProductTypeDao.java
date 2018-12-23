package com.yd.business.product.dao;

import java.util.List;

import com.yd.business.product.bean.ProductTypeBean;

public interface IProductTypeDao {

	public List<ProductTypeBean> listProductType(ProductTypeBean bean);
	public List<ProductTypeBean> listProductTypeByCustomerId(int customerid);
}
