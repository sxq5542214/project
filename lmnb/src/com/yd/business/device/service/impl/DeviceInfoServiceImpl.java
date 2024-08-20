/**
 * 
 */
package com.yd.business.device.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.client.QingSongInterfaceClient;
import com.yd.business.client.bean.QingSongInterfaceBean;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;
import com.yd.business.client.service.IIOTInterfaceService;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.dao.IDeviceDao;
import com.yd.business.device.dao.IMeterExtendsMapper;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.other.service.ICommandService;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.iotbusiness.mapper.dao.LmCmdModelMapper;
import com.yd.iotbusiness.mapper.dao.LmMeterModelMapper;
import com.yd.iotbusiness.mapper.model.LmCmdModel;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmMeterModelExample;
import com.yd.iotbusiness.mapper.model.LmMeterModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("deviceInfoService")
public class DeviceInfoServiceImpl extends BaseService implements IDeviceInfoService {
	@Resource
	private IDeviceDao deviceDao;
	@Autowired
	private IMeterExtendsMapper meterExtendsMapper;
	@Autowired
	private LmCmdModelMapper cmdModelMapper;
	@Autowired
	private IOperatorService operatorService;
	@Autowired
	private IIOTInterfaceService iotInterfaceService;
	@Autowired
	private ICommandService commandService;
	@Autowired
	private IPriceService priceService;
	
	
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
	
	
	@Override
	public IOTWebDataBean addOrUpdateMeter(LmMeterModel	bean) {
		IOTWebDataBean result = new IOTWebDataBean();
		
		
		if(bean.getOpened() != null && bean.getOpened() == MeterModelExtendsBean.OPEND_YES && bean.getOpentime() == null) {
			bean.setOpentime(new Date());
		}
		
		if(bean.getId() == null ) {
			bean.setInstalldate(new Date());
			LmPriceModel price = priceService.findPriceById(bean.getPricecode());
			bean.setPricename(price.getName());
			meterExtendsMapper.insertSelective(bean);
			
		}else {
			meterExtendsMapper.updateByPrimaryKeySelective(bean);
		}
		
		if(bean.getStationchecked() == null || bean.getStationchecked() != MeterModelExtendsBean.STATIONCHECKED_TRUE) {
			//调用服务查询水表是否可用
			DeviceDto dto = checkDeviceStation(bean.getId());
		}
		result.setData(bean);
		
		return result;
	}

	@Override
	public DeviceDto checkDeviceStation(Integer id) {
		LmMeterModel bean = meterExtendsMapper.selectByPrimaryKey(id);
		//调用服务查询水表是否可用
		DeviceDto dto = iotInterfaceService.checkQingSongMeterCode(bean.getCode());
		if(dto == null) {
			throw new RuntimeException("表具校验未通过，请检查水表编码~ " + bean.getCode());
		}else {
			bean.setIsp(Byte.valueOf(dto.getIsp()));
			bean.setIspid(dto.getIspid());
			bean.setImei(dto.getImei());
			bean.setImsi(dto.getSim());
			bean.setTimer(Integer.valueOf(dto.getReadperiod()));
			if("OPEN".equalsIgnoreCase(dto.getValvestate()) || "00".equalsIgnoreCase(dto.getValvestate())) {
				bean.setValvestate(MeterModelExtendsBean.VALVESTATE_OPEND);
			}else if("CLOSE".equalsIgnoreCase(dto.getValvestate()) || "01".equalsIgnoreCase(dto.getValvestate())) {
				bean.setValvestate(MeterModelExtendsBean.VALVESTATE_CLOSED);
			}else {
				bean.setValvestate(MeterModelExtendsBean.VALVESTATE_UNKNOW);
			}
			bean.setRemark3(dto.getValvestate());
			bean.setStrength(dto.getSignalstrength());
			bean.setBatterystate(Byte.valueOf(dto.getBattery()));
			bean.setCode(dto.getCode());
			bean.setStationchecked(MeterModelExtendsBean.STATIONCHECKED_TRUE);
			bean.setLifecode(bean.getId());
			meterExtendsMapper.updateByPrimaryKeySelective(bean);
		}
		return dto;
	}
	
	
	@Override
	public LmMeterModel updateMeterModel(LmMeterModel bean) {
		if(bean.getId() != null) {
			meterExtendsMapper.updateByPrimaryKeySelective(bean);
		}
		return bean;
	}
	
	@Override
	public IOTWebDataBean queryMeterList(LmMeterModel bean) {
		IOTWebDataBean result = new IOTWebDataBean();
		
		LmMeterModelExample ex = new LmMeterModelExample();
		LmMeterModelExample.Criteria cri = ex.createCriteria();
		cri.andUseridEqualTo(bean.getUserid());
		
		
		List<LmMeterModel> list = meterExtendsMapper.selectByExample(ex );
			
		result.setData(list);
		
		return result;
	}
	
	
	@Override
	public IOTWebDataBean queryMeterAndUserList(MeterModelExtendsBean bean) {
		
		
		List<MeterModelExtendsBean> list = meterExtendsMapper.queryMeterAndUserList(bean);
		bean.setRows(null);  // 不然分页查询的时候带上limit 会报错
		long total = meterExtendsMapper.countMeterAndUserList(bean);
		IOTWebDataBean result = new IOTWebDataBean();
		result.setData(list);
		result.setTotal(total);
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
	}
	
	/**
	 * 执行设备命令
	 * @param type  命令类型 1 开阀  2 关阀 3写周期  4 改表号  5 改表底数 6 读阀门状态  7 清除表节点  8 下发表节点  9 手动批量抄表  10 第三方系统立即抄表  11 直接抄表  12 自动批量抄表  13 自动补抄  14 缴费 99 取消待执行命令 
	 * @param meterCode
	 * @param operatorId
	 * @param remark
	 * @return
	 */
	public boolean executeDeviceCmd(int type,String meterCode,int operatorId,String remark) {
		boolean result = false;
		
		LmOperatorModel op = operatorService.findOperatorById(operatorId);
		MeterModelExtendsBean meter = findMeterByCode(meterCode);
		LmCmdModel cmd = new LmCmdModel();
		cmd.setCommtype((byte) 1);
		cmd.setMetercode(meterCode);
		cmd.setFactorycode(meter.getFactorycode());
		cmd.setState(QingSongInterfaceClient.CMD_STATE_WAIT);
		cmd.setType(String.valueOf(type));
		cmd.setCreatetime(new Date());
		cmd.setIsp(meter.getIsp());
		cmd.setImei(meter.getImei());
		cmd.setStationcode(QingSongInterfaceClient.STATIONCODE );
		cmd.setRemark(remark);
		cmd.setOperatorid(op.getId());
		cmd.setOperatorname(op.getRealname());
		cmd.setSystemid(meter.getSystemid());
		cmd.setProductid(meter.getProductid());
		cmd.setMeterid(meter.getId());
		cmd.setUserid(meter.getUserid());
		
		cmdModelMapper.insertSelective(cmd);
		
		
		return result;
	}
	

	@Override
	public MeterModelExtendsBean findMeterByCode(String code) {
		
		if(StringUtil.isNull(code)) {
			return null;
		}
		
		MeterModelExtendsBean bean = new MeterModelExtendsBean();
		bean.setCode(code);
		List<MeterModelExtendsBean> list = meterExtendsMapper.queryMeterAndUserList(bean);
		
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public LmMeterModel findMeterById(int id) {
		
		LmMeterModel meter = meterExtendsMapper.selectByPrimaryKey(id);
		
		return meter;
	}
	@Override
	public MeterModelExtendsBean findMeterByIspid(String ispid) {
		
		MeterModelExtendsBean bean = new MeterModelExtendsBean();
		bean.setIspid(ispid);;
		List<MeterModelExtendsBean> list = meterExtendsMapper.queryMeterAndUserList(bean);
		
		if(list.size() > 0) {
			return list.get(0);
		}
		
		return null;
	}

	@Override
	public IOTWebDataBean openOrCloseMeter(String meterCode,LmOperatorModel op,boolean isOpen,String remark) {
		
		LmMeterModelExample ex = new LmMeterModelExample();
		LmMeterModelExample.Criteria cri = ex.createCriteria();
		cri.andCodeEqualTo(meterCode);
		List<LmMeterModel> list =  meterExtendsMapper.selectByExample(ex );
		String type = "2";
		if(isOpen) {
			type = "1";
		}
		
		for(LmMeterModel me : list) {
			byte state = MeterCMD.STATE_WAITEXECUTE; //待執行
			LmCmdModel cmd = commandService.createCMDModel(meterCode, me.getFactorycode(), state, type, "", me.getIsp(), me.getIspid(),
					me.getImei(), QingSongInterfaceClient.STATIONCODE, op.getRealname(), op.getSystemid(), me.getProductid(), me.getId()	, me.getUserid(),op.getId(), remark);
			String outerid = cmd.getId().toString();
			QingSongInterfaceBean res = iotInterfaceService.sendQingSongCmd(me.getIspid(), outerid, type);
			if(QingSongInterfaceBean.code_success != Integer.valueOf(res.getCode())) {
				//失敗
				commandService.updateCmdStatus(cmd.getId(), MeterCMD.STATE_FAILD,res.getMsg());
				throw new RuntimeException("充值时开阀指令发送失败，请重试！"+res.getMsg());
			}else {
				
			}
		}
		
		IOTWebDataBean result = new IOTWebDataBean();
		result.setMessage("操作成功");
		result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
		
		return result;
	}


	@Override
	public IOTWebDataBean queryDayMeterReadingCount(String day,Integer systemid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("day", day);
		map.put("systemid", systemid);
		
		int value = meterExtendsMapper.countDayMeterReading(map);

		result.setData(value);
		return result;
	}

	@Override
	public IOTWebDataBean queryOpenedMeterCount(Integer systemid) {
		IOTWebDataBean result = new IOTWebDataBean();

		LmMeterModelExample ex = new LmMeterModelExample();
		LmMeterModelExample.Criteria cri = ex.createCriteria();
		cri.andOpenedEqualTo(MeterModelExtendsBean.OPEND_YES);
		cri.andSystemidEqualTo(systemid);
		
		long value = meterExtendsMapper.countByExample(ex);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryDayMeterReadingCountListData(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
//		map.put("operatorid", operatorid);
		
		List<Map<String, Object>> value = meterExtendsMapper.queryLast2MonthMeterReadingList(map);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryDayOpendedMeterCountListData(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
//		map.put("operatorid", operatorid);
		
		List<Map<String, Object>> value = meterExtendsMapper.queryLast2MonthOpenedMeterCountList(map);

		result.setData(value);
		return result;
	}
	
	@Override
	public int deleteMeterForStatus(int meterid) {
		LmMeterModel meter = findMeterById(meterid);
		meter.setOpened(MeterModelExtendsBean.OPEND_DELETED);
		meter.setCode(null);
		return meterExtendsMapper.updateByPrimaryKeySelective(meter);
	}
	
	
}
