package com.yd.business.product.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.order.service.IOrderService;
import com.yd.business.product.bean.SupplierProductAttachBean;
import com.yd.business.product.bean.SupplierProductBean;
import com.yd.business.product.dao.ISupplierProductDao;
import com.yd.business.product.service.ISupplierProductService;
import com.yd.business.supplier.bean.CustomerSupplierProductBean;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.DateUtil;
import com.yd.util.RandomUtil;
import com.yd.util.StringUtil;
@Service("supplierProductService")
public class SupplierProductServiceImpl extends BaseService implements
		ISupplierProductService {

	@Resource
	private ISupplierProductDao supplierProductDao;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IOrderService orderService;
	@Override
	public int insertSupplierProduct(SupplierProductBean bean) {
		bean.setCreate_time(DateUtil.getNowDateStr());
		// TODO Auto-generated method stub
		return supplierProductDao.insertSupplierProduct(bean);
	}

	@Override
	public void updateSupplierProduct(SupplierProductBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		// TODO Auto-generated method stub
		supplierProductDao.updateSupplierProduct(bean);
	}

	@Override
	public List<SupplierProductBean> listSupplierProduct(
			SupplierProductBean bean) {
		// TODO Auto-generated method stub
		return supplierProductDao.querySupplierProduct(bean);
	}
	
	@Override
	public List<SupplierProductBean> querySupplierProductByIds(String ids){
		
		return supplierProductDao.querySupplierProductByIds(ids);
	}
	
	@Override
	public List<SupplierProductBean> querySupplierProductByIds(List<Integer> ids){
		
		return supplierProductDao.querySupplierProductByIds(ids);
	}
	
	

	@Override
	public SupplierProductBean findSupplierProductById(Integer id) {
		// TODO Auto-generated method stub
		return supplierProductDao.findSupplierProductById(id);
	}
	
	@Override
	public void createOrUpdateSupplierProduct(SupplierProductBean bean){
		if(bean.getId() == null){
			insertSupplierProduct(bean);
		}else{
			updateSupplierProduct(bean);
		}
	}
	
	/**
	 * 查询客户下的所有商品
	 */
	@Override
	public List<CustomerSupplierProductBean> queryCustomerSupplierProduct(int customerId ){
		
		SupplierProductBean condition = new SupplierProductBean();
		condition.setCustomer_id(customerId);
		condition.setStatus(SupplierProductBean.STATUS_UP);
		List<CustomerSupplierProductBean> listCsp = new ArrayList<CustomerSupplierProductBean>();
		
		//查询客户下有多少商户
		List<SupplierBean> suppliers = supplierService.querySupplierByCustomerId(customerId);
		for(SupplierBean sb : suppliers){
			condition.setSupplier_id(sb.getId());
			//如果不可以销售，则必须有库存
			if(sb.getIssale() == SupplierBean.ISSALE_FALSE){
				condition.setStore_num(1);
			}else{
				condition.setStore_num(null);
			}
			//查询每个商户下的商品
			List<SupplierProductBean> csp = supplierProductDao.querySupplierProduct(condition);
			
			if(csp.size() >0){
				listCsp.add(new CustomerSupplierProductBean(null,sb,csp));
			}
		}
		
		return listCsp;
	}
	
	/**
	 * 查询平台的商品
	 */
	@Override
	public List<SupplierProductBean> queryPlatformSupplierProduct(){
		
		SupplierProductBean condition = new SupplierProductBean();
		condition.setCustomer_id(CustomerBean.ID_PLATFROM);
		condition.setStatus(SupplierProductBean.STATUS_UP);
		condition.setNow_time(DateUtil.getNowDateStr());
//		List<CustomerSupplierProductBean> listCsp = new ArrayList<CustomerSupplierProductBean>();
		
		//查询每个商户下的商品
		List<SupplierProductBean> csp = supplierProductDao.querySupplierProduct(condition);
		
		return csp;
	}
	
	
	/**
	 * 通过手机号查询商户客户下可用的商品
	 */
	@Override
	public List<CustomerSupplierProductBean> queryCustomerProductByPhone(int customer_id,String phone){
		
		AreaDataBean areaData = orderService.getAreaDataByPhone(phone);
		if(areaData == null) return null;
		SupplierProductBean condition = new SupplierProductBean();
		condition.setCustomer_id(customer_id);
		condition.setStatus(SupplierProductBean.STATUS_UP);
		condition.setNow_time(DateUtil.getNowDateStr());
		
		List<CustomerSupplierProductBean> listCsp = new ArrayList<CustomerSupplierProductBean>();
		
		//查询客户下有多少商户
		List<SupplierBean> suppliers = supplierService.querySupplierByCustomerId(customer_id);
		for(SupplierBean sb : suppliers){
			condition.setSupplier_id(sb.getId());
			//如果不可以销售，则必须有库存
			if(sb.getIssale() == SupplierBean.ISSALE_FALSE){
				condition.setStore_num(1);
			}else{
				condition.setStore_num(null);
			}
			
			//有没有区域数据
			if(areaData != null){
				condition.setProduct_brand_name(areaData.getBrand());
				condition.setProduct_province(areaData.getProvince());
			}
			//查询每个商户下的商品
			List<SupplierProductBean> csp = supplierProductDao.querySupplierProduct(condition);
			if(csp.size() >0){
				listCsp.add(new CustomerSupplierProductBean(areaData,sb,csp));
			}
			
			//如果查出的数据不是全国的，那么界面上再展示出来全国的
			if(areaData == null || !AreaDataBean.PROVINCE_QG.equals(areaData.getProvince())){
				//查询全国的商品
				condition.setProduct_province(AreaDataBean.PROVINCE_QG);
				csp = supplierProductDao.querySupplierProduct(condition);
				if(csp.size() >0){
					listCsp.add(new CustomerSupplierProductBean(areaData,sb,csp));
				}
			}
		}
		return listCsp;
	}

	@Override
	public SupplierProductBean findSupplierProductBySpid(Integer spid){
		if(spid == null){
			return null;
		}
		return supplierProductDao.findSupplierProductBySpid(spid);
		
	}

	@Override
	public List<SupplierProductBean> queryProBySupParCustomerId(
			Integer parcustid, Integer custid) {
		// TODO Auto-generated method stub
		return supplierProductDao.queryProBySupParCustomerId(parcustid, custid);
	}

	@Override
	public void deleteSupplierProduct(SupplierProductBean bean) {
		// TODO Auto-generated method stub
		supplierProductDao.deleteSupplierProduct(bean);
	}

	@Override
	public void batchDeleteSupplierProduct(List<SupplierProductBean> list) {
		// TODO Auto-generated method stub
		supplierProductDao.batchDeleteSupplierProduct(list);
	}
	
	/**
	 * 查询商户附加商品
	 * @param spid
	 * @return
	 */
	@Override
	public List<SupplierProductAttachBean> querySupplierAttachProductBySpid(int spid){
		SupplierProductAttachBean bean = new SupplierProductAttachBean();
		bean.setSupplier_product_id(spid);
		return supplierProductDao.queryAttachProduct(bean );
	}

	/**
	 * 返回商品的红包金额根据商品的最大金额数和最小金额数
	 * @param spid
	 * @return
	 */
	@Override
	public int returnProductLuckMoneyByOrder(int spid) {
		SupplierProductBean bean = this.findSupplierProductBySpid(spid);
		if(StringUtil.isNull(bean.getMax_luckymoney()) || StringUtil.isNull(bean.getMin_luckymoney())){
			return 0;
		}
		int max_luckMoney = bean.getMax_luckymoney();
		int min_luckMoney = bean.getMin_luckymoney();
		if(max_luckMoney == 0){
			return 0;
		}
		return RandomUtil.nextInt(max_luckMoney, min_luckMoney);
	}
	
}
