package com.yd.business.product.dao;

import java.util.List;
import java.util.Map;

import com.yd.business.product.bean.ProductBean;

public interface IProductDao {

	public ProductBean findProductById(Integer id);
	public List<ProductBean> listProduct(ProductBean bean);
	ProductBean findProductByCode(String productCode);
	public List<ProductBean> listMealPro(ProductBean bean);
	public List<ProductBean> listProductByIds(Map<String, List<Integer>> params);
	void createProduct(ProductBean bean);
	void updateProduct(ProductBean bean);
}
