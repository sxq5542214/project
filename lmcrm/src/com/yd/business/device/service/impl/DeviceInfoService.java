/**
 * 
 */
package com.yd.business.device.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.dao.IDeviceDao;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.user.bean.UserInfoBean;

/**
 * @author ice
 *
 */
@Service("deviceInfoService")
public class DeviceInfoService extends BaseService implements IDeviceInfoService {
	@Resource
	private IDeviceDao deviceDao;
	
	@Override
	public List<DeviceInfoBean> queryAllDeviceInfo(){
		return deviceDao.queryDeviceInfo(null);
	}
	
	@Override
	public DeviceInfoBean createDeviceInfo(UserInfoBean user,DeviceKindBean deviceKind, PriceBean price, int chargePrice , String deviceCompany) {
		DeviceInfoBean bean = new DeviceInfoBean();
		bean.setDi_userid(user.getU_id());
		bean.setDi_dkid(deviceKind.getDk_id());
		bean.setDevice_company(deviceCompany);
		
		BigDecimal amount = new BigDecimal(chargePrice).divide(price.getP_price1() ,2, BigDecimal.ROUND_HALF_UP);
		
		bean.setDi_leaveamount(amount);
		bean.setDi_cardleave(amount);
		bean.setDi_totalamount(amount);
		bean.setDi_totalmoney(new BigDecimal(chargePrice));
		
		Date date = new Date();
		bean.setDi_createdate(date);
		bean.setDi_updatedate(date);
		bean.setDi_brushdate(date);

		bean.setDi_useramount(new BigDecimal(0));
		bean.setDi_useramount1(new BigDecimal(0));
		bean.setDi_useramount2(new BigDecimal(0));
		bean.setDi_useramount3(new BigDecimal(0));
		bean.setDi_useramount4(new BigDecimal(0));
		bean.setDi_useramount5(new BigDecimal(0));
		bean.setDi_useramount6(new BigDecimal(0));
		bean.setDi_useramount7(new BigDecimal(0));
		bean.setDi_useramount8(new BigDecimal(0));
		bean.setDi_useramount9(new BigDecimal(0));
		bean.setDi_useramount10(new BigDecimal(0));
		bean.setDi_useramount11(new BigDecimal(0));
		bean.setDi_useramount12(new BigDecimal(0));
		bean.setDi_storedamount(new BigDecimal(0));
		
		bean.setDi_electricstatus(0);
		bean.setDi_magneticstatus(0);
		bean.setDi_motstatus(0);
		bean.setDi_brushflag(0);
		bean.setDi_curamount(new BigDecimal(0));
		bean.setDi_batteryvoltage(new BigDecimal(0));
		bean.setDi_overflatamount(new BigDecimal(0));
		bean.setDi_overflatstatus(0);
		bean.setDi_signalintensity(new BigDecimal(0));
		bean.setDi_batteryvoltage(new BigDecimal(0));
		
		
		
		deviceDao.createDeviceInfo(bean);
		
		return bean;
	}
	
	@Override
	public DeviceInfoBean findDeviceInfoByUserAndKind(Long userid,Long dkid) {
		DeviceInfoBean bean = new DeviceInfoBean();
		bean.setDi_userid(userid);
		bean.setDi_dkid(dkid);
		
		List<DeviceInfoBean> list = deviceDao.queryDeviceInfo(bean);
		return list.size() >0 ? list.get(0):null;
	}
	

	@Override
	public DeviceInfoBean findFirstDeviceInfoByUser(Long userid) {
		DeviceInfoBean bean = new DeviceInfoBean();
		bean.setDi_userid(userid);
		
		List<DeviceInfoBean> list = deviceDao.queryDeviceInfo(bean);
		return list.size() >0 ? list.get(0):null;
	}
	
	@Override
	public int updateDeviceInfo(DeviceInfoBean bean) {
		bean.setDi_updatedate(new Date());
		return deviceDao.updateDeviceInfo(bean);
	}
	
}
