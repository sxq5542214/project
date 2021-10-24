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
import com.yd.business.device.bean.ChangeMeterBean;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.dao.IChangeMeterDao;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;

/**
 * @author ice
 *
 */
@Service("changeMeterService")
public class ChangeMeterServiceImpl extends BaseService implements IChangeMeterService {
	
	@Resource
	private IChangeMeterDao changeMeterDao;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IDeviceInfoService deviceInfoService;
	
	public int createChangeMeter(long user_no, BigDecimal cm_oldmetercode, int cm_type, long cm_operatorid, String cm_remark) {
		return createChangeMeter(user_no, cm_oldmetercode, BigDecimal.ZERO, 0l, cm_type, cm_operatorid, cm_remark, null,"","");
	}

	
	@Override
	public int createChangeMeter(long user_no ,BigDecimal cm_oldmetercode,BigDecimal cm_newmetercode ,Long cm_newmeterno, int cm_type,long cm_operatorid,String cm_remark,Long deviceKindId ,String old_device_company ,String new_device_company) {
		
		UserInfoBean user = userInfoService.findUserByNo(user_no);

		ChangeMeterBean bean = new ChangeMeterBean();
		bean.setCm_userid(user.getU_id());
		bean.setCm_oldmeterno(user_no);
		bean.setCm_oldmetercode(cm_oldmetercode);
		bean.setCm_operatorid(cm_operatorid);
		bean.setCm_type(cm_type);
		bean.setCm_remark(cm_remark);
		bean.setCm_happendate(new Date());
		bean.setCm_status(13);  // 不知意义
		
		bean.setCm_newmetercode(cm_newmetercode);
		bean.setCm_newmeterno(cm_newmeterno);
		bean.setOld_device_company(old_device_company);
		bean.setNew_device_company(new_device_company);

		DeviceInfoBean di = deviceInfoService.findFirstDeviceInfoByUser(user.getU_id());
		if(cm_type == ChangeMeterBean.TYPE_CHANGE_DEVICEKIND) { //更换了表具类型
			di.setDi_dkid(deviceKindId);
		}
		di.setDevice_company(new_device_company);
		deviceInfoService.updateDeviceInfo(di);
		
		return changeMeterDao.insertChangeMeter(bean);
	}
	
	@Override
	public List<ChangeMeterExtBean> queryChangeMeterByExt(ChangeMeterExtBean bean){
		
		return changeMeterDao.listChangeMeter(bean);
		
	}
	
	
}
