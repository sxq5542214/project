/**
 * 
 */
package com.yd.business.device.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.service.IRecordService;
import com.yd.business.device.bean.ChangeMeterBean;
import com.yd.business.device.bean.ChangeMeterExtBean;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.dao.IChangeMeterDao;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.util.DateUtil;

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
	@Autowired
	private IRecordService recordService;
	@Resource
	private IOperatorService operatorService;
	@Resource
	private IChargeDetailService chargeDetailService;
	
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
	
	@Override
	public IOTWebDataBean changeMeter(String oldMeterCode,String changeMeterCode,BigDecimal changeMeterCost,BigDecimal oldReadNum,BigDecimal changeReadNum,String changeFixUser,String remark1,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		//查找操作员
		LmOperatorModel op = operatorService.findOperatorById(operatorid);
		//以旧水表为基础
		MeterModelExtendsBean oldMeter = deviceInfoService.findMeterByCode(oldMeterCode);
		LmMeterModel newMeter = deviceInfoService.findMeterById(oldMeter.getId());
		newMeter.setId(null);
		newMeter.setBalance(oldMeter.getBalance().subtract(changeMeterCost));
		newMeter.setCode(changeMeterCode);
		newMeter.setInstalldate(new Date());
		newMeter.setCycleamount(BigDecimal.ZERO);
		newMeter.setCyclebuyamount(BigDecimal.ZERO);
		newMeter.setCyclebuyquantity(BigDecimal.ZERO);
		newMeter.setCycleendbalance(BigDecimal.ZERO);
		newMeter.setCyclestartbalance(BigDecimal.ZERO);
		newMeter.setCyclestartnum(changeReadNum);
		newMeter.setCycleuse(BigDecimal.ZERO);
		newMeter.setImei(null);
		newMeter.setIsp(null);
		newMeter.setIspid(null);
		newMeter.setOpened(null);
		newMeter.setRecentcmdstate(null);
		newMeter.setRecentcmdtime(null);
		newMeter.setRecentcmdtype(null);
		newMeter.setRecentfailstate(null);
		newMeter.setRecentfailtime(null);
		newMeter.setRecentreadexcept(null);
		newMeter.setRecentreadnum(changeReadNum);
		newMeter.setRecentreadtime(null);
		newMeter.setStationchecked(null);
		newMeter.setRemark2("换表收费"+changeMeterCost+"元");
		deviceInfoService.addOrUpdateMeter(newMeter);
		
		oldMeter.setChanged(MeterModelExtendsBean.CHANGED_TRUE);
		oldMeter.setChangeid(newMeter.getId());
		oldMeter.setRemark1(remark1);
		oldMeter.setRemark2(changeFixUser);
		oldMeter.setLyname(op.getRealname());
		oldMeter.setOpened(MeterModelExtendsBean.OPEND_CLOSE);
		oldMeter.setStationchecked(MeterModelExtendsBean.STATIONCHECKED_FALSE);
		oldMeter.setStoptime(new Date());
		oldMeter.setIspid("old"+oldMeter.getIspid());
		
		String targetOldMeterCode = "old"+oldMeterCode;
		oldMeter.setCode(targetOldMeterCode);
		deviceInfoService.updateMeterModel(oldMeter);
		//因为更新了旧表的metercode ，需要连带更新payment表和record表
		chargeDetailService.updatePaymentMeterCode(oldMeterCode, targetOldMeterCode);
		recordService.updateRecordMeterCode(oldMeterCode, targetOldMeterCode);
		
		
		
		//插入 recode记录前，先找上一次的数据
		LmRecordModel lastRecord = recordService.findLastRecord(oldMeter.getCode());
		if(lastRecord == null) {
			lastRecord = new LmRecordModel();
		}		
		lastRecord.setId(null);
		lastRecord.setBillmonth(DateUtil.getNowMonthPureStr());
		lastRecord.setCurnum(oldReadNum);
		lastRecord.setCurtime(new Date());
		
		lastRecord.setOperatorid(operatorid);
		lastRecord.setOperatorname(op.getRealname());
		lastRecord.setReadtype((byte) 6); //换表
		lastRecord.setState((byte) 1);	//已结算
		recordService.saveRecord(lastRecord);
		
		result.setData(newMeter);
		return result;
	}
	
	
}
