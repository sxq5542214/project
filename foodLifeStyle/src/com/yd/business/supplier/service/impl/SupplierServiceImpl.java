package com.yd.business.supplier.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.bean.CustomerDiscountBean;
import com.yd.business.customer.bean.CustomerDiscountGroupBean;
import com.yd.business.customer.service.ICustomerDiscountGroupService;
import com.yd.business.customer.service.ICustomerDiscountService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.other.dao.IConfigAttributeDao;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierDiscountRelationBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.bean.SupplierTypeBean;
import com.yd.business.supplier.dao.ISupplierDao;
import com.yd.business.supplier.service.ISupplierDiscountRelationService;
import com.yd.business.supplier.service.ISupplierPowerLogService;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
@Service("supplierService")
public class SupplierServiceImpl extends BaseService implements
		ISupplierService {

	@Resource
	private ISupplierDao supplierDao;
	@Resource
	private IConfigAttributeDao configAttributeDao;
	@Resource
	private ISupplierDiscountRelationService supplierDiscountRelationService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private IProductService productService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ICustomerDiscountService customerDiscountService;
	@Resource
	private ICustomerDiscountGroupService customerDiscountGroupService;
	@Resource
	private ISupplierPowerLogService supplierPowerLogService;
	public ISupplierDao getSupplierDao() {
		return supplierDao;
	}
	
	public IConfigAttributeDao getConfigAttributeDao() {
		return configAttributeDao;
	}
	
	@Override
	public List<SupplierBean> querySupplierByCustomerId(int customerId){
		
		return supplierDao.querySupplierByCustomerId(customerId);
	}

	/**
	 * 
	 * @param customer
	 * @param name
	 * @param type
	 */
	@Override
	public void createSupplier(CustomerBean customer,String openid,String name,Integer type) {
		SupplierBean bean = new SupplierBean();
		bean.setCustomer_id(customer.getId());
		bean.setName(name);
		bean.setContacts_name(customer.getContacts_name());
		bean.setContacts_phone(customer.getContacts_phone());
		bean.setOpenid(openid);
		bean.setCreate_time(DateUtil.getNowDateStr());
		bean.setExpired_day(0);
		bean.setBalance(0);
		bean.setPoints(0);
		bean.setType(type);
		bean.setStatus(SupplierBean.STATUS_Y);
		
		supplierDao.insertSupplier(bean);
		
	}
	
	@Override
	public String insertSupplier(SupplierBean bean,String disGroupIds) {
		// TODO Auto-generated method stub
		if(customerService.getUser()==null) bean.setLevel(2);
		supplierDao.insertSupplier(bean);
		bean=supplierDao.findSupplierById(bean.getId());
		String result = "";
		if(!NumberUtil.empty(disGroupIds)){
			String[] ids = disGroupIds.indexOf(",")>0?disGroupIds.split(","):new String[]{disGroupIds};
			for (int i = 0; i < ids.length; i++) {
				int id = NumberUtil.toInt(ids[i]);
				if(id>0){
					CustomerDiscountGroupBean groupBean = customerDiscountGroupService.findCustDisGroupById(id);
					groupBean.setCustomer_id(bean.getCustomer_id());
					groupBean.setCustomer_name(bean.getName());
					customerDiscountGroupService.insertCustomerDiscountGroup(groupBean);//新增商户对应的客户的折扣组信息
					
					SupplierDiscountRelationBean sdrb = new SupplierDiscountRelationBean();
					sdrb.setCustomer_id(bean.getCustomer_id());
					sdrb.setCustomer_name(bean.getName());
					sdrb.setSupplier_id(bean.getId());
					sdrb.setSupplier_name(bean.getName());
					sdrb.setDiscount_group_id(groupBean.getId());
					sdrb.setGroup_name(groupBean.getName());
					sdrb.setParent_discount_group_id(id);
					supplierDiscountRelationService.insertSupplierDiscountRelation(sdrb);//新增折扣于商户的关系
					
					CustomerDiscountBean custDisBean = new CustomerDiscountBean();
					custDisBean.setGroup_id(id);
					List<CustomerDiscountBean> listCustDis = customerDiscountService.listCustomerDiscount(custDisBean);
					for (int j = 0; j < listCustDis.size(); j++) {
						CustomerDiscountBean cdb = listCustDis.get(j);
						cdb.setGroup_id(groupBean.getId());
						cdb.setCustomer_id(bean.getCustomer_id());
						customerDiscountService.insertCustomerDiscount(cdb);
					}
					
					List<CustomerDiscountBean> custDisGroupBrandList = customerDiscountService.listCustDisGroupBrand(custDisBean);
					for (int j = 0; j < custDisGroupBrandList.size(); j++) {
						CustomerDiscountBean cdb = custDisGroupBrandList.get(j);
						ProductBean pb = new ProductBean();
						pb.setProduct_brand(cdb.getProduct_brand());
						List<ProductBean> productList = productService.listProduct(pb);
						for (int k = 0; k < productList.size(); k++) {
							ProductBean product = productList.get(k);
							SupplierProductBean supPro = new SupplierProductBean();
							supPro.setProduct_id(product.getId());
							supPro.setSupplier_id(bean.getId());
							supPro.setCustomer_id(bean.getCustomer_id());
							supPro.setStore_num(0);
							supPro.setProduct_name(product.getName());
							supPro.setProduct_price(product.getProduct_price());
							supPro.setProduct_real_price(product.getProduct_real_price());
							supPro.setProduct_type(product.getProduct_type());
							supPro.setProduct_type_name(product.getProduct_type_name());
							supPro.setProduct_brand(product.getProduct_brand());
							supPro.setProduct_brand_name(product.getProduct_brand_name());
							supPro.setCreate_time(NumberUtil.toString(new Date()));
							supPro.setModify_time(NumberUtil.toString(new Date()));
							supPro.setModify_admin(customerService.getUserId());
							supPro.setEff_time(DateUtil.getNowDateStr());
							supPro.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), bean.getExpired_day()));
							supplierProductService.insertSupplierProduct(supPro);
							supplierPowerLogService.insertSupplierPowerLog(bean.getId(), product.getId(), product.getName(), supPro.getStore_num());
						}
					}
				}
			}
		}
		return result;
	}

	@Override
	public void updateSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		supplierDao.updateSupplier(bean);
	}

	@Override
	public void deleteSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		supplierDao.deleteSupplier(bean);
	}

	@Override
	public List<SupplierBean> listSupplier(SupplierBean bean) {
		// TODO Auto-generated method stub
		return supplierDao.listSupplier(bean);
	}

	@Override
	public void batchDeleteSupplier(List<SupplierBean> list) {
		// TODO Auto-generated method stub
		supplierDao.batchDeleteSupplier(list);
	}

	@Override
	public SupplierBean findSupplierById(Integer id) {
		return supplierDao.findSupplierById(id);
	}
	@Override
	public SupplierBean findSupplier(Integer id,String openid) {
		SupplierBean bean = new SupplierBean();
		bean.setOpenid(openid);
		bean.setId(id);
		List<SupplierBean> list = supplierDao.listSupplier(bean);
		if(list.size()> 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<SupplierBean> listMinusSupplier(Integer customerid,
			Integer productid) {
		// TODO Auto-generated method stub
		return supplierDao.listSupplierByProductid(customerid, productid);
	}

	@Override
	public String insetSupPower(String params) {
		// TODO Auto-generated method stub
		String result = "";
		List<Integer> sdrs = new ArrayList<Integer>();//折扣规则组与商户的关联表的新增id
		List<Integer> sps = new ArrayList<Integer>();//商户与商品表的新增id信息
		List<Integer> cds = new ArrayList<Integer>();//客户与规则表的新增id
		List<Integer> pds = new ArrayList<Integer>();//折扣规则组id信息
		try {
			if(!NumberUtil.empty(params)){
				JSONArray jsoArr = new JSONArray(params);
				for (int i = 0; i < jsoArr.length(); i++) {
					JSONObject jso = jsoArr.getJSONObject(i);
					int supid = NumberUtil.toInt(jso.get("supid"));
					SupplierDiscountRelationBean bean = new SupplierDiscountRelationBean();
					bean.setCustomer_id(NumberUtil.toInt(jso.get("custid")));
					bean.setCustomer_name(NumberUtil.toString(jso.get("supname")));
					bean.setSupplier_id(supid);
					bean.setSupplier_name(NumberUtil.toString(jso.get("supname")));
					SupplierBean supBean = supplierDao.findSupplierById(supid);
					JSONArray groups = jso.getJSONArray("groups");
					for (int j = 0; j < groups.length(); j++) {
						JSONObject o = groups.getJSONObject(j);
						bean.setParent_discount_group_id(NumberUtil.toInt(o.get("group_id")));
						bean.setGroup_name(NumberUtil.toString(o.get("group_name")));
						//新增客户与折扣规则的关系
						CustomerDiscountGroupBean cdgb = new CustomerDiscountGroupBean();
						cdgb.setName(bean.getGroup_name());
						cdgb.setCustomer_id(bean.getCustomer_id());
						List<CustomerDiscountGroupBean> cdgbList = customerDiscountGroupService.listCustomerDiscountGroup(cdgb);
						if(cdgbList!=null&&cdgbList.size()>0) result = bean.getSupplier_name()+"用户已经授权过该折扣规则信息，请勿重复授权。";
						else{
							cdgb.setCustomer_name(bean.getSupplier_name());
							cdgb.setRemark(customerService.getUser().getName()+"客户授权所得");
							customerDiscountGroupService.insertCustomerDiscountGroup(cdgb);
							cds.add(cdgb.getId());//新增折扣组信息
							bean.setDiscount_group_id(cdgb.getId());
							supplierDiscountRelationService.insertSupplierDiscountRelation(bean);
							sdrs.add(bean.getId());//新增商户与折扣的关系
							//获取规则分组下的品牌和分类信息
							CustomerDiscountBean cdBean = new CustomerDiscountBean();
							cdBean.setGroup_id(bean.getParent_discount_group_id());
							List<CustomerDiscountBean> cdList = customerDiscountService.listCustomerDiscount(cdBean);
							if(cdList!=null&&cdList.size()>0){
								//将当前已经录入的客户添加折扣信息
								for (int k = 0; k < cdList.size(); k++) {
									CustomerDiscountBean custDisBean = cdList.get(k);
									CustomerDiscountBean powerCustDis = new CustomerDiscountBean();
									powerCustDis.setCustomer_id(cdgb.getCustomer_id());
									powerCustDis.setGroup_id(cdgb.getId());
									powerCustDis.setProduct_brand(custDisBean.getProduct_brand());
									powerCustDis.setName(custDisBean.getName());
									powerCustDis.setMax_price(custDisBean.getMax_price());
									powerCustDis.setMin_price(custDisBean.getMin_price());
									powerCustDis.setDiscount(custDisBean.getDiscount());
									customerDiscountService.insertCustomerDiscount(powerCustDis);
									pds.add(powerCustDis.getId());
								}
								List<CustomerDiscountBean> custDisGroupBrandList = customerDiscountService.listCustDisGroupBrand(cdBean);
								for (int k = 0; k < custDisGroupBrandList.size(); k++) {
									CustomerDiscountBean custDisBean = custDisGroupBrandList.get(k);
									ProductBean proBean = new ProductBean();
//									proBean.setProduct_type(custDisBean.getProduct_brand());
									proBean.setProduct_brand(custDisBean.getProduct_brand());
									//获取品牌或者分类下关联的商品信息
									List<ProductBean> listPro = productService.listProduct(proBean);
									if(listPro!=null&&listPro.size()>0){
										for (int l = 0; l < listPro.size(); l++) {
											ProductBean product = listPro.get(l);
											SupplierProductBean supPro = new SupplierProductBean();
											supPro.setProduct_id(product.getId());
											supPro.setSupplier_id(bean.getSupplier_id());
											supPro.setCustomer_id(bean.getCustomer_id());
											supPro.setStore_num(0);
											supPro.setProduct_name(product.getName());
											supPro.setProduct_price(product.getProduct_price());
											supPro.setProduct_real_price(product.getProduct_real_price());
											supPro.setProduct_type(product.getProduct_type());
											supPro.setProduct_type_name(product.getProduct_type_name());
											supPro.setProduct_brand(product.getProduct_brand());
											supPro.setProduct_brand_name(product.getProduct_brand_name());
											supPro.setCreate_time(NumberUtil.toString(new Date()));
											supPro.setModify_time(NumberUtil.toString(new Date()));
											supPro.setModify_admin(customerService.getUserId());
											supPro.setEff_time(DateUtil.getNowDateStr());
											supPro.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), supBean.getExpired_day()));
											supplierProductService.insertSupplierProduct(supPro);
											sps.add(supPro.getId());//新增商户和商品的关系
											supplierPowerLogService.insertSupplierPowerLog(bean.getId(), product.getId(), product.getName(), supPro.getStore_num());
										}
									}
								}
							}else result = "选取的规则分组下无规则信息，请确认后在提交！";
						}
					}
				}
			}else result = "参数不能空，请确认是否选取了相关信息！";
		}catch(JSONException e){
			e.printStackTrace();
			result = "参数解析错误，请确认信息是否准确！";
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			result = "信息入库错误！";
		}finally{
			//如果某个信息出现存储失败，则回滚所有信息
			if(!NumberUtil.empty(result)){
				if(sdrs.size()>0){
					for (int i = 0; i < sdrs.size(); i++) {
						SupplierDiscountRelationBean bean = new SupplierDiscountRelationBean();
						bean.setId(sdrs.get(i));
						supplierDiscountRelationService.deleteSupplierDiscountRelation(bean);
					}
				}
				if(sps.size()>0){
					for (int i = 0; i < sps.size(); i++) {
						SupplierProductBean bean = new SupplierProductBean();
						bean.setId(sps.get(i));
						supplierProductService.deleteSupplierProduct(bean);
					}
				}
				if(cds.size()>0){
					for (int i = 0; i < cds.size(); i++) {
						CustomerDiscountGroupBean bean = new CustomerDiscountGroupBean();
						bean.setId(cds.get(i));
						customerDiscountGroupService.deleteCustomerDiscountGroup(bean);
					}
				}
				if(pds.size()>0){
					for (int i = 0; i < pds.size(); i++) {
						CustomerDiscountBean bean = new CustomerDiscountBean();
						bean.setId(cds.get(i));
						customerDiscountService.deleteCustomerDiscount(bean);
					}
				}
			}
		}
		return result;
	}

	@Override
	public List<SupplierBean> listSupplierByPower(int parentCustomerId) {
		// TODO Auto-generated method stub
		return supplierDao.listSupplierByPower(parentCustomerId);
	}

	/**
	 * 取消授权信息
	 */
	@Override
	public String cancelPower(int id,int supplierid) {
		// TODO Auto-generated method stub
		//第一步，循环获取所有的分组信息
		List<CustomerDiscountGroupBean> cdgbList = new ArrayList<CustomerDiscountGroupBean>();
		List<SupplierDiscountRelationBean> sdrbList = new ArrayList<SupplierDiscountRelationBean>();
		List<SupplierProductBean> spbList = new ArrayList<SupplierProductBean>();
		List<CustomerDiscountBean> cdbList = new ArrayList<CustomerDiscountBean>();
		
		SupplierDiscountRelationBean bean = new SupplierDiscountRelationBean();
		bean.setDiscount_group_id(id);
		List<SupplierDiscountRelationBean> srList = supplierDiscountRelationService.listSupDisRela(bean);
		sdrbList.addAll(srList);
		getChildGroupInfoList(id,supplierid,cdgbList,sdrbList,spbList,cdbList);//获取基本信息
		
		if(cdgbList.size()>0) customerDiscountGroupService.batchDeleteCustomerDiscountGroup(cdgbList);
		if(sdrbList.size()>0) supplierDiscountRelationService.batchDeleteSupplierDiscountRelation(sdrbList);
		if(spbList.size()>0) supplierProductService.batchDeleteSupplierProduct(spbList);
		if(cdbList.size()>0) customerDiscountService.batchDeleteCustomerDiscountById(cdbList);
		
		return null;
	}
	/**
	 * 根据id查询是否已经授权给其他客户
	 * @param id
	 * @return
	 */
	private List<SupplierDiscountRelationBean> getChildGroupInfoList(int id,int supplierid,List<CustomerDiscountGroupBean> cdgbList,
			List<SupplierDiscountRelationBean> sdrbList,List<SupplierProductBean> spbList,List<CustomerDiscountBean> cdbList){
		CustomerDiscountGroupBean bean = customerDiscountGroupService.findCustDisGroupById(id);//获取分组信息
		cdgbList.add(bean);
		
		SupplierDiscountRelationBean sdrb = new SupplierDiscountRelationBean();
		sdrb.setParent_discount_group_id(id);
		List<SupplierDiscountRelationBean> list = supplierDiscountRelationService.listSupDisRela(sdrb);//获取当前分组已经授权的信息
		for (int i = 0; i < list.size(); i++) {
			SupplierDiscountRelationBean sdr = list.get(i);
			sdrbList.add(sdr);
			getChildGroupInfoList(sdr.getDiscount_group_id(),supplierid,cdgbList,sdrbList,spbList,cdbList);
		}
		
		CustomerDiscountBean cdb = new CustomerDiscountBean();
		cdb.setGroup_id(id);
		List<CustomerDiscountBean> listCustDis = customerDiscountService.listCustomerDiscount(cdb);
		cdbList.addAll(listCustDis);
		
		List<CustomerDiscountBean> listCustDisBrand = customerDiscountService.listCustDisGroupBrand(cdb);
		for (int i = 0; i < listCustDisBrand.size(); i++) {
			CustomerDiscountBean cdbBean = listCustDisBrand.get(i);
			SupplierProductBean spbBean = new SupplierProductBean();
			spbBean.setProduct_brand(cdbBean.getProduct_brand());
			spbBean.setSupplier_id(supplierid);
			List<SupplierProductBean> listPro = supplierProductService.listSupplierProduct(spbBean);
			spbList.addAll(listPro);
		}
		return list;
	}

	@Override
	public SupplierBean queryMealSupplier(int customerid) {
		// TODO Auto-generated method stub
		return supplierDao.queryMealSupplier(customerid);
	}
	/****************************************商品授权 begin*******************************************/
	@Override
	public String powerSupProduct(String params){
		String result = "";
		if(params!=null){
			//jsonarray格式为：[{"supplierid":"商户id":"data":[{"id":"品牌／分类id","name":"品牌/分类名称","type":"1、品牌，2、分类"}]}]
			JSONArray array = new JSONArray(params);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jso = array.getJSONObject(i);
				JSONArray data = jso.getJSONArray("data");
				int supplierid = NumberUtil.toInt(jso.get("supplierid"),0);//商户id
				SupplierBean supplierBean = supplierDao.findSupplierById(supplierid);//获取收取商户信息
				for (int j = 0; j < data.length(); j++) {
					JSONObject obj = data.getJSONObject(j);
					int id = NumberUtil.toInt(obj.get("id"), 0);//品牌、分类id
					int type = NumberUtil.toInt(obj.get("type"), 0);
					String name = NumberUtil.toString(obj.get("name"));
					ProductBean proBean = new ProductBean();
					proBean.setStatus(ProductBean.STATUS_UP);
					if(type==1)proBean.setProduct_brand(id);
					else if(type==0)proBean.setProduct_type(id);
					else{
						result = "提交信息错误，请确认后再提交！";
						break;
					}
					if(NumberUtil.empty(result)){//没有错误信息才可继续
						List<ProductBean> productList = productService.listProduct(proBean);
						if(productList!=null&&productList.size()>0) {
							for (int k = 0; k < productList.size(); k++) {
								if(NumberUtil.empty(result))result = insertPowerProduct(supplierBean, productList.get(k),0);//这里的数量默认为0
								else break;
							}
						}else result = "存在授权的品牌无商品信息,["+name+"]";
					}
				}
			}
		}else result = "授权错误，请不要提交空信息！";
		return result;
	}
	/**
	 * 新增授权的商品信息
	 * @param bean
	 * @param productList
	 * @return
	 */
	private String insertPowerProduct(SupplierBean bean,ProductBean productBean,int num){
		String result = "";
		//第一步先判断当前授权的商品商户是否已经存在
		SupplierProductBean supProBean = new SupplierProductBean();
		supProBean.setSupplier_id(bean.getId());
		supProBean.setProduct_id(productBean.getId());
		List<SupplierProductBean> list = supplierProductService.listSupplierProduct(supProBean);
		if(list!=null&&list.size()>0){
			SupplierProductBean listBean = list.get(0);
			SupplierProductBean spBean = new SupplierProductBean();
			spBean.setId(listBean.getId());
			spBean.setModify_admin(customerService.getUserId());
			spBean.setModify_time(DateUtil.getNowDateStr());
			spBean.setEff_time(DateUtil.getNowDateStr());
			spBean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), bean.getExpired_day()));
			if(num>0) spBean.setStore_num(listBean.getStore_num()+num);
			supplierProductService.updateSupplierProduct(spBean);
		}else{
			supProBean.setProduct_id(productBean.getId());
			supProBean.setSupplier_id(bean.getId());
			supProBean.setCustomer_id(bean.getCustomer_id());
			supProBean.setStore_num(num);
			supProBean.setProduct_name(productBean.getName());
			supProBean.setProduct_price(productBean.getProduct_price());
			supProBean.setProduct_real_price(productBean.getProduct_real_price());
			supProBean.setProduct_type(productBean.getProduct_type());
			supProBean.setProduct_type_name(productBean.getProduct_type_name());
			supProBean.setProduct_brand(productBean.getProduct_brand());
			supProBean.setProduct_brand_name(productBean.getProduct_brand_name());
			supProBean.setCreate_time(DateUtil.getNowDateStr());
			supProBean.setEff_time(DateUtil.getNowDateStr());
			supProBean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), bean.getExpired_day()));
			supProBean.setModify_time(DateUtil.getNowDateStr());
			supProBean.setModify_admin(customerService.getUserId());
			supplierProductService.insertSupplierProduct(supProBean);
		}
		supplierPowerLogService.insertSupplierPowerLog(bean.getId(), productBean.getId(), productBean.getName(), num);//添加授权日志，这里的授权默认数量为0
		return result;
	}
	/****************************************商品授权 end*******************************************/
	/****************************************商品指派 begin*******************************************/

	@Override
	public String designSupplierProduct(String params) {
		String result = "";
		if(params!=null){
			//jsonarray格式为：[{"supplierid":"商户id":"data":[{"id":"商品id","num":"指派数量","minuspid":"核减的商户id"}]}]
			JSONArray array = new JSONArray(params);
			for (int i = 0; i < array.length(); i++) {
				JSONObject jso = array.getJSONObject(i);
				JSONArray data = jso.getJSONArray("data");
				int supplierid = NumberUtil.toInt(jso.get("supplierid"),0);//商户id
				SupplierBean supplierBean = supplierDao.findSupplierById(supplierid);//获取指派商户信息
				for (int j = 0; j < data.length(); j++) {
					if(NumberUtil.empty(result)){
						JSONObject obj = data.getJSONObject(j);
						int id = NumberUtil.toInt(obj.get("id"), 0);
						int num = NumberUtil.toInt(obj.get("num"), 0);
						int minuspid = NumberUtil.toInt(obj.get("minuspid"), 0);
						ProductBean productBean = productService.findProductById(id);
						result = insertPowerProduct(supplierBean, productBean,num);
						if(NumberUtil.empty(result)) result = minuDesignSupplier(minuspid, id, num, productBean.getName());
					}else break;
				}
			}
		}else result = "授权错误，请不要提交空信息！";
		return result;
	}
	
	/**
	 * 需要核减的商户
	 * @param minusId
	 * @return
	 */
	private String minuDesignSupplier(int minusId,int productid,int num,String productname){
		String result = "";
		SupplierProductBean bean = new SupplierProductBean();
		bean.setSupplier_id(minusId);
		bean.setProduct_id(productid);
		List<SupplierProductBean> list = supplierProductService.listSupplierProduct(bean);
		if(list!=null&&list.size()>0){
			boolean isSuccess = false;
			SupplierProductBean supplierProductBean = new SupplierProductBean();
			supplierProductBean.setId(list.get(0).getId());
			supplierProductBean.setStore_num(list.get(0).getStore_num()-num);
			for (int i = 0; i < list.size(); i++) {
				if(list.get(i).getStore_num()>num) isSuccess = true;
			}
			if(isSuccess) supplierProductService.updateSupplierProduct(supplierProductBean);
			else result = "商品["+productname+"]选择的核减商户不合理，请重新选择！";
		}else result = "商品［"+productname+"］选择的核减商户不存在该商品，请重新选择！";
		return result;
	}
	/****************************************商品指派 end*******************************************/

	@Override
	public List<SupplierBean> querySupplierByMinus(int customerid,
			int productid, int storenum) {
		// TODO Auto-generated method stub
		return supplierDao.querySupplierByMinus(customerid, productid, storenum);
	}
	
	@Override
	public List<SupplierTypeBean> querySupplierType(SupplierTypeBean bean){
		return supplierDao.querySupplierType(bean);
	}
}
