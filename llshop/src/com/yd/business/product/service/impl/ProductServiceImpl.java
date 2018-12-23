/**
 * 
 */
package com.yd.business.product.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.dao.IProductDao;
import com.yd.business.product.service.IProductService;

/**
 * @author ice
 *
 */
@Service("productService")
public class ProductServiceImpl extends BaseService implements IProductService {
	
	@Resource
	private IProductDao productDao;
	

	@Override
	public ProductBean findProductById(Integer id) {
		return productDao.findProductById(id);
	}


	@Override
	public List<ProductBean> listProduct(ProductBean bean) {
		// TODO Auto-generated method stub
		return productDao.listProduct(bean);
	}


	@Override
	public List<ProductBean> listMealPro(ProductBean bean) {
		// TODO Auto-generated method stub
		return productDao.listMealPro(bean);
	}
	
	@Override
	public ProductBean findProductByCode(String productCode){
		return productDao.findProductByCode(productCode);
	}


	@Override
	public List<ProductBean> listProductByIds(Map<String, List<Integer>> params) {
		// TODO Auto-generated method stub
		return productDao.listProductByIds(params);
	}
	
	/**
	 * 创建或修改基础商品
	 */
	@Override
	public void createOrUpdateProduct(ProductBean bean){
		
		if(bean.getId() == null){
			createProduct(bean);
		}else{
			updateProduct(bean);
		}
		
	}
	
	public void createProduct(ProductBean bean){
		productDao.createProduct(bean);
	}
	
	public void updateProduct(ProductBean bean){
		productDao.updateProduct(bean);
	}
	
	
	
	
}
