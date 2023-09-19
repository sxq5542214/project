/**
 * 
 */
package com.yd.business.bill.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.service.IBillService;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;
import com.yd.iotbusiness.mapper.dao.LmFeeModelMapper;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample;
import com.yd.iotbusiness.mapper.model.LmFeeModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample.Criteria;
import com.yd.util.DateUtil;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;

/**
 * @author ice
 *
 */
@Service("billService")
public class BillServiceImpl extends BaseService implements IBillService {
	
	@Resource
	private LmBillModelMapper billModelMapper;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IPriceService priceService;
	@Resource
	private LmFeeModelMapper feeModelMapper;
	@Resource
	private IDeviceInfoService deviceInfoService;
	
	@Override
	public IOTWebDataBean queryBillList(LmBillModel model) {
		IOTWebDataBean result = new IOTWebDataBean();
		LmBillModelExample ex = new LmBillModelExample();
		LmBillModelExample.Criteria cri = ex.createCriteria();
		
		cri.andUseridEqualTo(model.getUserid());
		cri.andSystemidEqualTo(model.getSystemid());
		ex.setOrderByClause(" id desc ");
		List<LmBillModel> list = billModelMapper.selectByExample(ex );
		
		result.setTotal(Long.valueOf(list.size()));
		result.setData(list);
		return result;
	}
	
	@Override
	public LmBillModel generatorBillByRecord(String meterCode ,LmRecordModel record) {
		
		MeterModelExtendsBean meter = deviceInfoService.findMeterByCode(meterCode);
		
		LmPricedetailModel priceDetail = priceService.findPriceDetailByPriceId(meter.getPricecode());
		LmPriceModel price = priceService.findPriceById(meter.getPricecode());
		
		// 费用读数明细
		LmFeeModel fee = new LmFeeModel();
		fee.setCurnum(record.getCurnum());
		fee.setCurquantity(record.getCurquantity());
		fee.setBillmonth(Integer.parseInt(DateUtil.formatMonthPure(new Date())));
		fee.setAmount(priceDetail.getPrice1().multiply(fee.getCurquantity()));
		fee.setAfterbalance(meter.getBalance().subtract(fee.getAmount()));
		fee.setCreatetime(new Date());
		fee.setCycleflag((byte) 1); // 未结算
		fee.setState((byte) 2); //已扣款
		fee.setLifecode(meter.getId());
		fee.setSystemid(meter.getSystemid());
		fee.setPriceid(meter.getPricecode());
		fee.setRecordid(record.getId());
		fee.setMeterid(meter.getId());
		fee.setSettleid(price.getSettleid());
		
		feeModelMapper.insertSelective(fee);
		
		
		//计算账单
		
		LmBillModel bill = queryBillByMonth(meter.getId(), fee.getBillmonth());
		if(bill == null) {
			bill = new LmBillModel();
			bill.setCyclestartbalance(meter.getBalance());
			bill.setMinconsumequantity(price.getMinconsumequantity());
			bill.setMinconsumamount(priceDetail.getPrice1().multiply(price.getMinconsumequantity()));
		}
		bill.setCycleendbalance(fee.getAfterbalance());
		bill.setCyclebuyamount(null);
		BigDecimal curQuantity = bill.getQuantity() == null?BigDecimal.ZERO:bill.getQuantity() ;
		bill.setQuantity(curQuantity.add(fee.getCurquantity()));
		bill.setShouldwateramount(bill.getQuantity().multiply(priceDetail.getPrice1()));
		bill.setRealbillamount(bill.getShouldwateramount());
		bill.setWaitpayrealbillamount(bill.getShouldwateramount());
		bill.setWaitpayrealwateramount(bill.getShouldwateramount());
		bill.setCreatetime(new Date());
		bill.setState((byte) 2);
		bill.setSettletype((byte) 1);
		bill.setFactorycode(meter.getFactorycode());
		bill.setLifecode(meter.getLifecode());
		bill.setMeterid(meter.getId());
		bill.setUserid(meter.getUserid());
		bill.setSystemid(meter.getSystemid());
		bill.setSettleid(price.getSettleid());
		bill.setPriceid(price.getId());
		bill.setOpername("抄表接口自动触发");
		
		
		//更新或创建账单
		if(bill.getId() == null) {
			billModelMapper.insertSelective(bill);
		}else {
			billModelMapper.updateByPrimaryKeySelective(bill);
		}
		
		
		//扣减余额
		meter.setBalance(fee.getAfterbalance());
		deviceInfoService.updateMeterModel(meter);
		
		
		// 是否有余额，不足则关阀
		if(meter.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			LmOperatorModel op = new LmOperatorModel();
			op.setId(-1);
			op.setRealname("抄表接口自动触发");
			//关阀
			deviceInfoService.openOrCloseMeter(meterCode, op , false, "欠费自动关阀，record："+record.getId());
		}
		
		
		return bill;
	}
	
	@Override
	public LmBillModel queryBillByMonth(int meterId,int billMonth) {
		
		LmBillModelExample ex = new LmBillModelExample();
		LmBillModelExample.Criteria cri = ex.createCriteria();
		
		cri.andMeteridEqualTo(meterId);
		cri.andBillmonthEqualTo(billMonth);
		
		List<LmBillModel> list = billModelMapper.selectByExample(ex );
		
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
