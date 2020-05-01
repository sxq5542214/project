package com.yd.business.customer.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.customer.bean.CustomerApplyProductBean;
import com.yd.business.customer.bean.CustomerBalanceLogBean;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.dao.ICustomerApplyProductDao;
import com.yd.business.customer.service.ICustomerApplyProductService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.product.bean.ProductBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.bean.SupplierProductBean;
import com.yd.business.supplier.service.ISupplierProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
/**
 * 客户申领商品服务
 * @author Anlins
 *
 */
@Service("customerApplyProductService")
public class CustomerApplyProductServiceImpl extends BaseService implements
		ICustomerApplyProductService {

	@Resource
	private ICustomerApplyProductDao customerApplyProductDao;
	@Resource
	private IProductService productService;
	@Resource
	private ICustomerApplyProductService customerApplyProductService;
	@Resource
	private ISupplierProductService supplierProductService;
	@Resource
	private ICustomerService customerService;
	@Resource
	private ISupplierService supplierService;
	@Override
	public void insertCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		customerApplyProductDao.insertCustomerApplyProduct(bean);
	}

	@Override
	public void deleteCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		customerApplyProductDao.deleteCustomerApplyProduct(bean);
	}

	@Override
	public void updateCustomerApplyProduct(CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		customerApplyProductDao.updateCustomerApplyProduct(bean);
	}

	@Override
	public List<CustomerApplyProductBean> listCustomerApplyProduct(
			CustomerApplyProductBean bean) {
		// TODO Auto-generated method stub
		return customerApplyProductDao.listCustomerApplyProduct(bean);
	}

	@Override
	public void batchDeleteCustomerApplyProduct(
			List<CustomerApplyProductBean> list) {
		// TODO Auto-generated method stub
		customerApplyProductDao.batchDeleteCustomerApplyProduct(list);
	}

	@Override
	public CustomerApplyProductBean findCustApplyProductById(Integer id) {
		// TODO Auto-generated method stub
		return customerApplyProductDao.findCustApplyProductById(id);
	}

	@Override
	public String applyAudit(Integer id, Integer status, Integer supid,Integer num) {
		Integer userid = customerService.getUserId();
		// TODO Auto-generated method stub
		String result = "";
		CustomerApplyProductBean bean = new CustomerApplyProductBean();
		bean.setId(id);
		bean.setStatus(status);
		bean.setModify_time(NumberUtil.toString(new Date()));
		bean.setAudit_by_num(num);
		//重新获取一下工单信息
		CustomerApplyProductBean apply = customerApplyProductService.findCustApplyProductById(id);
		if(apply!=null){//判断工单是否存在
			if(apply.getApply_supplier_id()!=supid){//判断当前申请的商户是否与核减的商户相同
				SupplierProductBean minusSupBean = new SupplierProductBean();
				minusSupBean.setSupplier_id(supid);
				minusSupBean.setProduct_id(apply.getProduct_id());
				List<SupplierProductBean> list = supplierProductService.listSupplierProduct(minusSupBean);
				if(list!=null&&list.size()>0){
					minusSupBean = list.get(0);//需要核减的商品信息
					if(status==CustomerApplyProductBean.APPLY_STATUS_YES){//当前审核通过时判定
						if(minusSupBean.getStore_num()-num>0){
							//判定当前商户是否存在相应的商品信息
							SupplierProductBean supProBean = new SupplierProductBean();
							supProBean.setSupplier_id(apply.getApply_supplier_id());
							supProBean.setProduct_id(apply.getProduct_id());
							List<SupplierProductBean> supProList = supplierProductService.listSupplierProduct(supProBean);
							
							SupplierProductBean minusProductBean = new SupplierProductBean();//需要核减的商户商品信息
							minusProductBean.setStore_num(minusSupBean.getStore_num()-num);
							minusProductBean.setId(minusSupBean.getId());
							minusProductBean.setModify_time(NumberUtil.toString(new Date()));
							minusProductBean.setModify_admin(userid);
							if(supProList!=null&&supProList.size()>0){//如果申请商户下存在相应的商品信息
								SupplierProductBean proBean = supProList.get(0);//原始的信息
								SupplierProductBean productBean = new SupplierProductBean();//需要新增修改的信息
								productBean.setStore_num(proBean.getStore_num()+num);
								productBean.setModify_time(NumberUtil.toString(new Date()));
								productBean.setModify_admin(userid);
								productBean.setId(proBean.getId());
								productBean.setEff_time(DateUtil.getNowDateStr());
								productBean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), 90));
								//更新商品信息
								supplierProductService.updateSupplierProduct(productBean);
							}else{//如果申请的商户下不存在相应的商品信息
								ProductBean product = productService.findProductById(apply.getProduct_id());//获取商品基本信息
								supProBean.setCustomer_id(apply.getCustomer_id());
								supProBean.setProduct_name(apply.getProduct_name());
								supProBean.setStore_num(num);
								supProBean.setProduct_price(product.getProduct_price());
								supProBean.setProduct_real_price(product.getProduct_real_price());
								supProBean.setProduct_type(product.getProduct_type());
								supProBean.setProduct_type_name(product.getProduct_type_name());
								supProBean.setProduct_brand(product.getProduct_brand());
								supProBean.setProduct_brand_name(product.getProduct_brand_name());
								supProBean.setDiscount(product.getDiscount());
								supProBean.setCreate_time(NumberUtil.toString(new Date()));
								supProBean.setEff_time(DateUtil.getNowDateStr());
								supProBean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), 90));
								supProBean.setModify_time(NumberUtil.toString(new Date()));
								supProBean.setModify_admin(userid);
								
								supplierProductService.insertSupplierProduct(supProBean);//插入到数据库中
							}
							bean.setSupplier_store_num(minusSupBean.getStore_num()-num);
							//更新核减的商品信息
							supplierProductService.updateSupplierProduct(minusProductBean);
							//都处理完成之后更新工单状态信息
							customerApplyProductService.updateCustomerApplyProduct(bean);
						}else result = "选择的商户商品小于当前申请的数量，请重新选择！";
					}else if(status==CustomerApplyProductBean.APPLY_STATUS_NO){//当审核不通过时判定
						//如果审核不同过，直接修改工单状态，不做其他操作
						customerApplyProductService.updateCustomerApplyProduct(bean);
					}
				}
			}else result = "核减的商户与当前申请的商户相同，不能选择！";
		}else result = "当前工单不存在，请查证！";
		return result;
	}

	@Override
	public String mealProduct(int money, JSONArray arr) {
		// TODO Auto-generated method stub
		String result = "";
		try {
			CustomerBean customerBean = customerService.getUser();
			if(customerBean.getBalance()>money){//判断余额是否充足
				if(arr!=null&&arr.length()>0){
					int allMoney = 0;//总额
					List<SupplierProductBean> listPro = new ArrayList<SupplierProductBean>();
					SupplierBean supBean = supplierService.queryMealSupplier(customerBean.getId());//获取当前客户的商户
					//后台计算总金额情况，防止人为修改数据
					for (int i = 0; i < arr.length(); i++) {
						JSONObject jso = arr.getJSONObject(i);
						int id = NumberUtil.toInt(jso.get("id"));//商品id
						int num = NumberUtil.toInt(jso.get("num"));//购买数量
						ProductBean proBean = productService.findProductById(id);//获取数据库中的商品信息，用于校验
						allMoney += proBean.getProduct_price()*num;
						SupplierProductBean bean = new SupplierProductBean();
						bean.setSupplier_id(supBean.getId());
						bean.setProduct_id(proBean.getId());
						bean.setCustomer_id(customerBean.getId());
						bean.setProduct_name(proBean.getName());
						bean.setStore_num(num);
						bean.setProduct_price(proBean.getProduct_price());
						bean.setProduct_real_price(proBean.getProduct_real_price());
						bean.setProduct_type(proBean.getProduct_type());
						bean.setProduct_type_name(proBean.getProduct_type_name());
						bean.setProduct_brand(proBean.getProduct_brand());
						bean.setProduct_brand_name(proBean.getProduct_brand_name());
						bean.setDiscount(proBean.getDiscount());
						bean.setCreate_time(DateUtil.getNowDateStr());
						bean.setEff_time(DateUtil.getNowDateStr());
						bean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), 90));
						bean.setModify_time(DateUtil.getNowDateStr());
						bean.setModify_admin(customerBean.getId());
						listPro.add(bean);//这里是为了防止在下面新增时，重复获取商品信息
					}
					if(allMoney!=money) result = "金额校验异常，存在数据修改！";
					else if(customerBean.getBalance()<money) result = "当前余额不足，请充值后在购买！";
					else {
						//循环插入上面校验获得的商户商品信息
						for (int i = 0; i < listPro.size(); i++) {
							SupplierProductBean proBean = listPro.get(i);
							SupplierProductBean supProBean = new SupplierProductBean();
							supProBean.setSupplier_id(proBean.getSupplier_id());
							supProBean.setProduct_id(proBean.getProduct_id());
							List<SupplierProductBean> supProList = supplierProductService.listSupplierProduct(supProBean);
							//如果当前商品已经存在，则进行更新操作，如果不存在，怎进行新增操作
							if(supProList==null||supProList.size()==0)supplierProductService.insertSupplierProduct(proBean);//插入到数据库中
							else {
								supProBean.setEff_time(DateUtil.getNowDateStr());
								supProBean.setDff_time(DateUtil.plusDate(DateUtil.getNowDateStr(), 90));
								supProBean.setId(supProList.get(0).getId());
								supProBean.setStore_num(proBean.getStore_num()+supProList.get(0).getStore_num());
								supplierProductService.updateSupplierProduct(supProBean);
							}
						}
						//更新金额信息
						customerService.getUser().setBalance(customerBean.getBalance()-allMoney);//更新缓存
						customerService.updateCustomerBalance(customerBean.getId(), customerBean.getBalance()-allMoney);
						//保存客户余额日志
						CustomerBalanceLogBean logBean = new CustomerBalanceLogBean();
						logBean.setCreate_time(DateUtil.getNowDateStr());
						logBean.setCustomer_id(customerBean.getId());
						logBean.setCustomer_name(customerBean.getName());
						logBean.setGet_balance(-allMoney);
						logBean.setRemark("购买商品");
						logBean.setTotal_balance(customerBean.getBalance());
						customerService.addCustomerBalanceLog(logBean);
					}
				}else result = "当前没有选择可售商品，请选择后再提交！";
			}else result = "当前余额不足，请充值后在购买！";
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e, e);
			result = "数据请求错误！";
		}
		return result;
	}

}
