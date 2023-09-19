/**
 * 
 */
package com.yd.business.client.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.service.IRecordService;
import com.yd.business.client.QingSongInterfaceClient;
import com.yd.business.client.bean.QingSongInterfaceBean;
import com.yd.business.client.bean.QingSongInterfaceBean.Conter;
import com.yd.business.client.bean.QingSongInterfaceBean.DeviceDto;
import com.yd.business.client.bean.QingSongInterfaceBean.MeterCMD;
import com.yd.business.client.service.IIOTInterfaceService;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.iotbusiness.mapper.dao.LmCmdModelMapper;
import com.yd.iotbusiness.mapper.model.LmCmdModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.util.DateUtil;

/**
 * 
 */
@Service("iotInterfaceService")
public class IOTInterfaceServiceImpl extends BaseService implements IIOTInterfaceService {
	
	@Autowired
	private LmCmdModelMapper cmdModelMapper;
	@Autowired
	private IConfigAttributeService configAttributeService;
	@Autowired
	private IRecordService recordService;

	@Autowired
	private IDeviceInfoService deviceInfoService;
	
	@Override
	public void saveQingSongStationCode(String stationCode) {
		
		configAttributeService.updateValueByCode(AttributeConstant.CODE_IOT_CLIENT_QINGSONG_STATIONCODE, stationCode, null);
		
	}
	
	@Override
	public void updateQingSongCMDState(MeterCMD cmd) {

		try {
			LmCmdModel mo = new LmCmdModel();
			mo.setId(Integer.parseInt(cmd.getOuterid()));
			mo.setType(cmd.getType());
			mo.setIspid(cmd.getIspid());
			mo.setExetime(DateUtil.parseDate(cmd.getFinishtime()) );
			mo.setState(Byte.parseByte(cmd.getState()));
			if("2".equals(cmd.getState())){ //成功
				
			}else {
				// 未成功需要做XXX
				
			}
			
			cmdModelMapper.updateByPrimaryKeySelective(mo);
			
		} catch (ParseException e) {
			log.error(e, e);
			
		}
		
	}
	
	@Override
	public int handlerQingSongPostMeterReading(String requestStr) {
		
		List<QingSongInterfaceBean> list = QingSongInterfaceClient.convertPostMeterReadingResult(requestStr);
		
		for(int i = 0 ; i < list.size() ; i++) {
			QingSongInterfaceBean bean = list.get(i);
			MeterModelExtendsBean meter = deviceInfoService.findMeterByIspid(bean.getIspid());
			if(meter == null) {
				log.info(" database not find Meter ,ispid is : " + bean.getIspid());
				continue;
			}
			try {
				meter.setFeetime(DateUtil.parseDate(bean.getCurtime()));
				
				// 更新水表信息
				Conter conter = bean.getConter();
				meter.setValvestate(Byte.parseByte(conter.getValvestate()));
				meter.setBatterystate(Byte.parseByte(conter.getBattery()));
				meter.setSensorstate(Byte.parseByte(conter.getSensor()));
				meter.setStrength(conter.getSignalstrength());
				meter.setTimer(Integer.parseInt(conter.getReadperiod()));
				meter.setFlowstate(conter.getReadstate());
				meter.setFeetime(new Date());
//				meter.setReversenum(new BigDecimal(conter.getReversenum()) );
				deviceInfoService.updateMeterModel(meter);
				
				//插入 recode记录前，先找上一次的数据
				LmRecordModel lastRecord = recordService.findLastRecord(meter.getCode());
				if(lastRecord == null) {
					lastRecord = new LmRecordModel();
					lastRecord.setCurnum(BigDecimal.ZERO);
					lastRecord.setCurtime(meter.getOpentime());
				}
				LmRecordModel record = new LmRecordModel();
				record.setBillmonth(DateUtil.getNowMonthPureStr());
				record.setCurnum(new BigDecimal(conter.getCurnum()));
				record.setCurquantity( record.getCurnum().subtract(lastRecord.getCurnum()) );
				record.setCurtime(DateUtil.parseDate(bean.getCurtime()));
				record.setLastnum(lastRecord.getCurnum());
				record.setLasttime(lastRecord.getCurtime());
				record.setLifecode(meter.getId());
				record.setMetercode(meter.getCode());
				record.setMeterid(meter.getId());
				record.setOperatorid(-1);
				record.setOperatorname("systemIntf");
				record.setPriceid(meter.getPricecode());
				record.setPricename(meter.getPricename());
				record.setReadtype((byte) 2); //系统抄表
				record.setState((byte) 1);	//已结算
				record.setSystemid(meter.getSystemid());
				record.setUserid(meter.getUserid());
				record.setUsername(meter.getUserName());
				recordService.saveRecord(record);
				
				
			} catch (ParseException e) {
				log.error(e,e);
			}
			
			
		}
		
		
		
		
		return 0;
		
	}
	
	/**
	 * 
	 * @param ispid 
	 * @param outerid 
	 * @param type 1开阀  2关阀
	 * @return
	 */
	@Override
	public QingSongInterfaceBean sendQingSongCmd(String ispid,String outerid,String type) {
		
		QingSongInterfaceBean bean = new QingSongInterfaceBean();
		bean.setIspid(ispid);
		bean.setOuterid(outerid);
		bean.setType(type);
		return QingSongInterfaceClient.sendCmd(bean );
		
	}
	
	@Override
	public DeviceDto checkQingSongMeterCode(String code ) {
		return QingSongInterfaceClient.checkDeviceInfo(code, null);
	}
	
}
