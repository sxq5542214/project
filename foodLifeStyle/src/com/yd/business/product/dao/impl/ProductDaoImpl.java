package com.yd.business.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.dao.IProductDao;

@Repository("productDao")
public class ProductDaoImpl extends BaseDao implements IProductDao {
	private static final String NAMESPACE = "product.";

	@Override
	public ProductBean findProductById(Integer id) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"queryProductById", id);
	}

	@Override
	public List<ProductBean> listProduct(ProductBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryProduct", bean);
	}
	
	@Override
	public ProductBean findProductByCode(String productCode){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findProductByCode", productCode);
	}
	

	@Override
	public List<ProductBean> listMealPro(ProductBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryMealProductList", bean);
	}

	@Override
	public List<ProductBean> listProductByIds(Map<String, List<Integer>> params) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryProductByIds", params);
	}
	
	@Override
	public void createProduct(ProductBean bean){
		sqlSessionTemplate.insert(NAMESPACE +"createProduct", bean);
	}
	
	@Override
	public void updateProduct(ProductBean bean){
		sqlSessionTemplate.update(NAMESPACE +"updateProduct", bean);
	}
	
	
}
