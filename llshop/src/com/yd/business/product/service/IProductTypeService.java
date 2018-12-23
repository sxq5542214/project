package com.yd.business.product.service;

import java.util.List;

import com.yd.business.product.bean.ProductTypeBean;

public interface IProductTypeService {
	public List<ProductTypeBean> listProductType(ProductTypeBean bean);
	public List<ProductTypeBean> listProductTypeByCustomerId(int customerid);
	ProductTypeBean findProductType(String name, int type);
	ProductTypeBean findProductType(int type, String code);
	ProductTypeBean findProductTypeById(int id);
}
