/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.supplier.bean.SupplierVolumBean;
import com.yd.business.supplier.dao.ISupplierVolumDao;
import com.yd.business.supplier.service.ISupplierVolumService;
import com.yd.business.user.bean.UserWechatBean;
import com.yd.util.NumberUtil;

/**
 * @author ice
 *
 */
@Service("supplierVolumService")
public class SupplierVolumServiceImpl extends BaseService implements ISupplierVolumService {
	@Resource
	private ISupplierVolumDao supplierVolumDao;
	
	private Random random = new Random(); 
	
	@Override
	public PageinationData querySupplierVolum(SupplierVolumBean bean){
		PageinationData pd = new PageinationData();
		List<SupplierVolumBean> list = supplierVolumDao.querySupplierVolumList(bean);
		int total = supplierVolumDao.querySupplierVolumListCount(bean);
		try {
			pd.setDataList(list);
			pd.setNowpage(bean.getNowpage());
			pd.setTotalcount(total);
			pd.calculateTotalPage();
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
		}
		
		return pd;
	}
	
	/**
	 * 创建代金卷
	 */
	@Override
	public void createMoneyVolum(SupplierVolumBean bean){

		bean.setType(SupplierVolumBean.TYPE_MONEY);
		bean.setStatus(SupplierVolumBean.STATUS_WAIT_GET);

		Integer count = bean.getNum_count();
		for(int i = 0 ; i < count ; i++){
			int supplierId = 1;
			bean.setSupplier_id(supplierId);
			bean.setCode(generationDiscountCode(supplierId));
			try{
				supplierVolumDao.saveSupplierVolum(bean);
			}catch(Exception e){
				//如果code重复了，则i减1再次执行
				e.printStackTrace();
				log.error(e,e);
				if(e.getMessage().indexOf("idx_supplier_volum_code")>0){
					i--;
				}
			}
		}
		
	}
	
	/**
	 * 创建优惠卷
	 */
	@Override
	public void createExchangeVolum(SupplierVolumBean bean){

		bean.setType(SupplierVolumBean.TYPE_EXCHANGE);
		bean.setStatus(SupplierVolumBean.STATUS_WAIT_GET);

		Integer count = bean.getNum_count();
		for(int i = 0 ; i < count ; i++){
			int supplierId = 1;
			bean.setSupplier_id(supplierId);
			bean.setCode(generationDiscountCode(supplierId));
			try{
				supplierVolumDao.saveSupplierVolum(bean);
			}catch(Exception e){
				//如果code重复了，则i减1再次执行
				e.printStackTrace();
				log.error(e,e);
				i--;
			}
		}
	}
	
	@Override
	public Integer updateVolumOfGetUser(SupplierVolumBean bean,UserWechatBean user){
		
		bean.setSend_time(new Date());
		bean.setUser_id(user.getId());
		bean.setUser_name(user.getNick_name());
		bean.setStatus(SupplierVolumBean.STATUS_ALREADY_GET);
		return supplierVolumDao.updateSupplierVolum(bean);
	}
	
	@Override
	public SupplierVolumBean findVolumById(Integer id){
		SupplierVolumBean bean = null;
		if(id != null){
			bean = supplierVolumDao.findVolumById(id);
		}
		return bean;
	}
	
	@Override
	public List<SupplierVolumBean> queryFreeVolum(Integer count){
		SupplierVolumBean bean = new SupplierVolumBean();
		bean.setStatus(SupplierVolumBean.STATUS_WAIT_GET);
		bean.setPageSize(count);
		
		return supplierVolumDao.querySupplierVolumList(bean);
		
	}
	
	
	/**
	 * 生成卷号，4个随机数字
	 * @return
	 */
	private String generationDiscountCode(Integer supplierId){
		StringBuffer str = new StringBuffer(supplierId.toString());
		random.setSeed(System.currentTimeMillis());
		//生成4个
		for(int i =0;i <4 ;i++){
			
			int num = random.nextInt(10000);
			
			String code = NumberUtil.fixIntToString(num, 4);
			str.append("-" + code);
		}
		
		return str.toString();
	}
	
	public static void main(String[] args) {
		SupplierVolumServiceImpl service = new SupplierVolumServiceImpl();
		String str = service.generationDiscountCode(1);
		System.out.println(str);
		
	}
}
