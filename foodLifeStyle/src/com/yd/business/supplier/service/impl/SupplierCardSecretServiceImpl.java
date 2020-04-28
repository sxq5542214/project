/**
 * 
 */
package com.yd.business.supplier.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.order.bean.OrderProductEffBean;
import com.yd.business.order.bean.OrderProductLogBean;
import com.yd.business.order.service.IOrderService;
import com.yd.business.supplier.bean.SupplierCardSecretBean;
import com.yd.business.supplier.dao.ISupplierCardSecretDao;
import com.yd.business.supplier.service.ISupplierCardSecretService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.Encryptor;

/**
 * @author ice
 *
 */
@Service("supplierCardSecretService")
public class SupplierCardSecretServiceImpl extends BaseService implements ISupplierCardSecretService {
	
	@Resource
	private ISupplierCardSecretDao supplierCardSecretDao;
	@Resource
	private IOrderService orderService;
	private static Random random = new Random(System.currentTimeMillis());
	
	
	@Override
	public PageinationData queryCardSecret(SupplierCardSecretBean bean) throws Exception{

		PageinationData pd = new PageinationData();
		List<SupplierCardSecretBean> list = supplierCardSecretDao.queryCardSecret(bean);
		int count = supplierCardSecretDao.queryCardSecretCount(bean);

		pd.setNowpage(bean.getNowpage() );
		pd.setDataList(list);
		pd.setTotalcount(count);
		pd.calculateTotalPage();
		
		return pd;
		
	}
	
	/**
	 * 创建卡密
	 */
	@Override
	public void createCardSecret(SupplierCardSecretBean bean) throws Exception{
		long time = System.currentTimeMillis();
		List<SupplierCardSecretBean> list = new ArrayList<SupplierCardSecretBean>();
		
		//取最新的批次编号
		int batch_no = supplierCardSecretDao.getNewBatchNoByCustomerId(bean.getCustomer_id());
		bean.setBatch_no(batch_no);
		
		
		int len = bean.getBatch_no_count();
		for(int i = 1 ; i <= len ; i ++){
			SupplierCardSecretBean cs = new SupplierCardSecretBean(bean);

			String key = bean.getCustomer_id().toString() + bean.getBatch_no().toString() + i;
			key = key + randomFixString(12 - key.length());
			
//			String secret_key = Encryptor.encrypt(key);
			String secret_key = key;
			cs.setSecret_key(secret_key);
			
			cs.setBatch_no_count(i);
			cs.setStatus(SupplierCardSecretBean.STATUS_WAIT);
			list.add(cs);
			if(i % 2000 == 0 ){
				//入库
				supplierCardSecretDao.batchCreateCardSecret(list);
				list.clear();
			}
		}
		
		//不是整数位，最后会有一部分数据没有入库
		if(list.size() > 0){
			//入库
			supplierCardSecretDao.batchCreateCardSecret(list);
		}
		
		log.debug("createCardSecret:"+len+" cost:"+ ( System.currentTimeMillis() - time ));
		
		
	}
	
	@Override
	public SupplierCardSecretBean findSupplierCardSecret(String secret_key){
		
		
		SupplierCardSecretBean bean = new SupplierCardSecretBean();
		bean.setSecret_key(secret_key);
		List<SupplierCardSecretBean> list = supplierCardSecretDao.queryCardSecret(bean );
		
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public SupplierCardSecretBean findSupplierCardSecret(int id){
		
		
		SupplierCardSecretBean bean = new SupplierCardSecretBean();
		bean.setId(id);
		List<SupplierCardSecretBean> list = supplierCardSecretDao.queryCardSecret(bean );
		
		if(list.size()>0){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public OrderProductLogBean useCardSecret(String secret_key,String phone,boolean isNow){
		
		return null;
	}
	
	
	@Override
	public void udpateCardSecret(SupplierCardSecretBean bean){
		supplierCardSecretDao.updateCardSecret(bean);
	}
	

	private static String randomFixString(int len){
		String pass = "abcdefghijklmnopqrstuvwxyz0123456789";
		StringBuffer result = new StringBuffer();
		int randomMax = pass.length();
		
		for(int i = 0 ; i < len ; i ++){
			int idx = random.nextInt(randomMax);
			result.append(pass.charAt(idx));
		}
		
		return result.toString();
	}
}
