package com.yd.business.product.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.product.bean.ProductTypeBean;
import com.yd.business.product.dao.IProductTypeDao;
import com.yd.business.product.service.IProductTypeService;
@Service("productTypeService")
public class ProductTypeServiceImpl extends BaseService implements
		IProductTypeService {

	@Resource
	private IProductTypeDao productTypeDao;
	@Override
	public List<ProductTypeBean> listProductType(ProductTypeBean bean) {
		// TODO Auto-generated method stub
		return productTypeDao.listProductType(bean);
	}
	@Override
	public List<ProductTypeBean> listProductTypeByCustomerId(int customerid) {
		// TODO Auto-generated method stub
		return productTypeDao.listProductTypeByCustomerId(customerid);
	}
	@Override
	public List<ProductTypeBean> listProductBrandByCustomerId(int customerid) {
		// TODO Auto-generated method stub
		return productTypeDao.listProductBrandByCustomerId(customerid);
	}
	
	@Override
	public ProductTypeBean findProductType(String name,int type){
		
		ProductTypeBean bean = new ProductTypeBean();
		bean.setName(name);
		bean.setType(type);
		
		List<ProductTypeBean> list = listProductType(bean);
		
		if(list.size() > 0){
			return list.get(0);
		}
		return null;
		
	}
	
	@Override
	public ProductTypeBean findProductType(int type,String code){
		
		ProductTypeBean bean = new ProductTypeBean();
		bean.setCode(code);
		bean.setType(type);
		
		List<ProductTypeBean> list = listProductType(bean);
		
		if(list.size() >= 0){
			return list.get(0);
		}
		return null;
		
	}
	
	@Override
	public ProductTypeBean findProductTypeById(int id){
		ProductTypeBean bean = new ProductTypeBean();
		bean.setId(id);
		List<ProductTypeBean> list = listProductType(bean);
		
		if(list.size() >= 0){
			return list.get(0);
		}
		return null;
	}


}
