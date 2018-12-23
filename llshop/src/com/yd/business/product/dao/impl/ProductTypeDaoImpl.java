package com.yd.business.product.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.dao.IProductTypeDao;
@Repository("productTypeDao")
public class ProductTypeDaoImpl extends BaseDao implements IProductTypeDao {

	private static final String NAMESPACE = "productType.";
	@Override
	public List<ProductTypeBean> listProductType(ProductTypeBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryProductType", bean);
	}
	@Override
	public List<ProductTypeBean> listProductTypeByCustomerId(int customerid) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryProductTypeByCustomerid", customerid);
	}

}
