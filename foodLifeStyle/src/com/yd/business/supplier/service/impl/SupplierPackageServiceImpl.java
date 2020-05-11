/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.msgcenter.bean.MsgCenterActionDefineBean;
import com.yd.business.msgcenter.service.IMsgCenterActionService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierPackageBean;
import com.yd.business.supplier.bean.SupplierPackageProductBean;
import com.yd.business.supplier.bean.SupplierPackageProductRecordBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierUserBean;
import com.yd.business.supplier.dao.ISupplierPackageDao;
import com.yd.business.supplier.service.ISupplierPackageService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.business.supplier.service.ISupplierUserService;
import com.yd.util.DateUtil;

/**
 * @author ice
 *
 */
@Service("supplierPackageService")
public class SupplierPackageServiceImpl extends BaseService implements ISupplierPackageService {
	@Resource
	private ISupplierPackageDao supplierPackageDao;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private ISupplierUserService supplierUserService;
	@Resource
	private ISupplierService supplierService;
	@Resource
	private IMsgCenterActionService msgCenterActionService;

	@Override
	public void createSupplierPackage(SupplierPackageBean bean) {
		
		bean.setCreate_time(DateUtil.getNowDateStr());
		bean.setDelete_flag(SupplierPackageBean.DELETE_FLAG_NO);
		
		
		supplierPackageDao.createSupplierPackage(bean);
	}
	
	@Override
	public void createSupplierPackageAndProducts(SupplierPackageBean bean,String[] products,String[] nums) {
		
		this.createSupplierPackage(bean);
		List<SupplierPackageProductBean> pplist = createPackageProductList(bean, products, nums);
		
		String title = generateTitle(pplist);
		bean.setTitle(title);
		this.updateSupplierPackage(bean);
		
	}
	
	@Override
	public void updatePackageAndProducts(SupplierPackageBean bean,String[] products,String[] nums) {
		
		//先删除再创建
		if(bean.getId() != null) {
			deleteSupplierPackageProductByPackageid(bean.getId());
			List<SupplierPackageProductBean> pplist = createPackageProductList(bean, products, nums);
			 
			String title = generateTitle(pplist);
			bean.setTitle(title);
			this.updateSupplierPackage(bean);
		}
		
	}
	
	private List<SupplierPackageProductBean> createPackageProductList(SupplierPackageBean pack,String[] products,String[] nums){
		
		List<SupplierPackageProductBean> resultList = new ArrayList<SupplierPackageProductBean>();
		Map<Integer,Integer> map =new HashMap<Integer, Integer>();
		List<Integer> ids = new ArrayList<Integer>();
		for(int i = 0 ; i < products.length ; i++) {
			Integer id = Integer.parseInt(products[i]);
			Integer num = Integer.parseInt(nums[i]);
			map.put(id, num);
			ids.add(id);
		}
		List<SupplierProductBean> prodList = supplierProductService.querySupplierProductByIds(ids);
		
		for(SupplierProductBean prod : prodList) {
			SupplierPackageProductBean ppb = new SupplierPackageProductBean();
			ppb.setSupplier_id(pack.getSupplier_id());
			ppb.setSupplier_package_id(pack.getId());
			ppb.setSupplier_product_id(prod.getId());
			ppb.setSupplier_product_name(prod.getProduct_name());
			ppb.setNum(map.get(prod.getId()));
			
			createSupplierPackageProduct(ppb);
			resultList.add(ppb);
		}
		
		return resultList;
	}
	
	private void deleteSupplierPackageProductByPackageid(Integer packageid) {
		if(packageid != null) {
			SupplierPackageProductBean bean = new SupplierPackageProductBean();
			bean.setSupplier_package_id(packageid);
			supplierPackageDao.deleteSupplierPackageProduct(bean );
		}
	}
	
	@Override
	public List<SupplierPackageBean> querySupplierPackage(SupplierPackageBean bean){
		return supplierPackageDao.querySupplierPackage(bean);
	}
	
	@Override
	public void updateSupplierPackage(SupplierPackageBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		supplierPackageDao.updateSupplierPackage(bean);
	}
	
	@Override
	public SupplierPackageBean findSupplierPackageById(Integer id,Integer sid){
		
		if(id == null || sid ==null) {
			return null;
		}
		
		SupplierPackageBean bean = new SupplierPackageBean();
		bean.setId(id);
		bean.setSupplier_id(sid);
		List<SupplierPackageBean> list = querySupplierPackage(bean );
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}
	
	@Override
	public void createSupplierPackageProduct(SupplierPackageProductBean bean) {
		
		bean.setCreate_time(DateUtil.getNowDateStr());
		supplierPackageDao.createSupplierPackageProduct(bean);
	}
	@Override
	public List<SupplierPackageProductBean> querySupplierPackageProduct(SupplierPackageProductBean bean){
		return supplierPackageDao.querySupplierPackageProduct(bean);
	}
	@Override
	public void createSupplierPackageProductRecord(SupplierPackageProductRecordBean bean) {
		supplierPackageDao.createSupplierPackageProductRecord(bean);
	}
	
	@Override
	public void createSupplierPackageProductRecord(Integer user_id,Integer packageId,Integer supplier_id) {
		
		SupplierUserBean user = supplierUserService.findSupplierUser(user_id, supplier_id);
		SupplierBean supplier = supplierService.findSupplierById(supplier_id);
		SupplierPackageBean pack = findSupplierPackageById(packageId, supplier_id);
		
		SupplierPackageProductBean condition = new SupplierPackageProductBean();
		condition.setSupplier_package_id(pack.getId());
		List<SupplierPackageProductBean> list = querySupplierPackageProduct(condition );
		
		String dff_time = "2099-12-13 23:59:59";
		Integer useful_lift = pack.getUseful_lift();
		if(useful_lift != null && useful_lift > 0) {
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_YEAR, useful_lift);
			dff_time = DateUtil.formatDate(c.getTime());
		}
		for(SupplierPackageProductBean spp : list) {
			SupplierPackageProductRecordBean sppr = new SupplierPackageProductRecordBean();
			sppr.setUser_id(user.getUser_id());
			sppr.setOpenid(user.getOpenid());
			sppr.setSupplier_id(supplier_id);
			sppr.setSupplier_package_id(pack.getId());
			sppr.setSupplier_package_name(pack.getName());
			sppr.setSupplier_product_id(spp.getSupplier_product_id());
			sppr.setSupplier_product_name(spp.getSupplier_product_name());
			sppr.setCreate_time(DateUtil.getNowDateStr());
			sppr.setNum(spp.getNum());
			sppr.setEff_time(DateUtil.getNowDateStr());
			sppr.setDff_time(dff_time);;
			
			this.createSupplierPackageProductRecord(sppr);
		}
		pack.setCode(supplier.getName());
		msgCenterActionService.saveAndHandleUserAction(user.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SUPPLIER_PACKAGE_ASSIGN, null, pack);
	}
	
	
	@Override
	public List<SupplierPackageProductRecordBean> querySupplierPackageProductRecord(SupplierPackageProductRecordBean bean){
		return supplierPackageDao.querySupplierPackageProductRecord(bean);
	}
	
	private String generateTitle(List<SupplierPackageProductBean> list) {
		StringBuffer sb = new StringBuffer();
		for(SupplierPackageProductBean prod : list) {
			sb.append("【"+prod.getSupplier_product_name() +"*"+prod.getNum() +"】");
		}
		return sb.toString();
	}

	@Override
	public boolean updatePackageProductRecordNum(Integer recordId, Integer num, SupplierBean supplier) {
		
		SupplierPackageProductRecordBean bean = new SupplierPackageProductRecordBean();
		bean.setSupplier_id(supplier.getId());
		bean.setId(recordId);
		List<SupplierPackageProductRecordBean> list = querySupplierPackageProductRecord(bean );
		if(list.size()>0) {
			bean = list.get(0);
			bean.setNum(num);
			int total = updatePackageProductRecord(bean);
			if(total > 0) {
				msgCenterActionService.saveAndHandleUserAction(bean.getOpenid(), MsgCenterActionDefineBean.ACTION_TYPE_SUPPLIER_PACKAGE_UPDATE_RECORD, null, bean);
				return true;
			}
		}
		
		
		
		return false;
	}
	
	
	private int updatePackageProductRecord(SupplierPackageProductRecordBean bean) {
		bean.setModify_time(DateUtil.getNowDateStr());
		return supplierPackageDao.updateSupplierPackageProductRecord(bean);
	}
	
}
