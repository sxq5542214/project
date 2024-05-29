/**
 * 
 */
package com.yd.business.bill.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.bean.BillModelExtendBean;
import com.yd.business.bill.dao.IBillExtendsMapper;
import com.yd.business.bill.service.IBillService;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.dao.IMeterExtendsMapper;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.msg.bean.SMSSendLogBean;
import com.yd.business.msg.service.ISMSService;
import com.yd.business.other.service.ICommandService;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmBillModelMapper;
import com.yd.iotbusiness.mapper.dao.LmFeeModelMapper;
import com.yd.iotbusiness.mapper.model.LlSmsSendlogModel;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample;
import com.yd.iotbusiness.mapper.model.LmFeeModel;
import com.yd.iotbusiness.mapper.model.LmBillModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmCmdModel;
import com.yd.util.DateUtil;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModel;
import com.yd.iotbusiness.mapper.model.LmPriceModel;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;
import com.yd.iotbusiness.mapper.model.LmRecordModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;

/**
 * @author ice
 *
 */
@Service("billService")
public class BillServiceImpl extends BaseService implements IBillService {
	
//	@Resource
//	private LmBillModelMapper billModelMapper;
	@Resource
	private IBillExtendsMapper billExtendsMapper;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IPriceService priceService;
	@Resource
	private LmFeeModelMapper feeModelMapper;
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IMeterExtendsMapper meterExtendsMapper;
	@Resource
	private ISMSService smsService;
	@Resource
	private ICommandService commandService;
	
	@Override
	public IOTWebDataBean queryBillList(LmBillModel model) {
		IOTWebDataBean result = new IOTWebDataBean();
		LmBillModelExample ex = new LmBillModelExample();
		LmBillModelExample.Criteria cri = ex.createCriteria();
		
		cri.andUseridEqualTo(model.getUserid());
		cri.andSystemidEqualTo(model.getSystemid());
		ex.setOrderByClause(" billmonth desc, id desc ");
		List<LmBillModel> list = billExtendsMapper.selectByExample(ex );
		
		result.setTotal(Long.valueOf(list.size()));
		result.setData(list);
		return result;
	}
	

	@Override
	public IOTWebDataBean queryBillWaterList(BillModelExtendBean bean) {
		IOTWebDataBean result = new IOTWebDataBean();
//		BillModelExtendBean bean = new BillModelExtendBean();
//		bean.setMeterid(meterid);
//		bean.setOrderby(" id desc ");
		List<BillModelExtendBean> list = billExtendsMapper.queryBillWaterList(bean );
		
		result.setTotal(Long.valueOf(list.size()));
		result.setData(list);
		return result;
	}
	
	
	@Override
	public LmBillModel generatorBillByRecord(String meterCode ,LmRecordModel record) {
		
		MeterModelExtendsBean meter = deviceInfoService.findMeterByCode(meterCode);
		LmUserModel user = userInfoService.findUserById(meter.getUserid());
		
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
			bill.setBillmonth(fee.getBillmonth());
			bill.setCyclebuyamount(BigDecimal.ZERO);
		}
		bill.setCycleendbalance(fee.getAfterbalance());
//		bill.setCyclebuyamount(null);
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
			billExtendsMapper.insertSelective(bill);
		}else {
			billExtendsMapper.updateByPrimaryKeySelective(bill);
		}
		
		//更新费用表对应的账单ID
		fee.setBillid(bill.getId());
		feeModelMapper.updateByPrimaryKeySelective(fee);
		
		//扣减余额
		meter.setBalance(fee.getAfterbalance());
		meter.setDayuse(record.getCurquantity());
		meter.setMonthuse(bill.getQuantity());
		deviceInfoService.updateMeterModel(meter);
		
		
		// 是否有余额，不足则关阀
		if(meter.getBalance().compareTo(BigDecimal.ZERO) < 0) {
			LmOperatorModel op = new LmOperatorModel();
			op.setId(-1);
			op.setRealname("抄表接口自动触发");
			
			// 判断24小时内仅发送一次关阀指令
			Date start = new Date(System.currentTimeMillis() - 24*60*60*1000);
			Date end = new Date();
			List<LmCmdModel> listCmd = commandService.queryCmdList(meter.getUserid(), meterCode, null, ICommandService.CMD_TYPE_CLOSE, start, end);
			if(listCmd.size() ==0) {
				//关阀
				deviceInfoService.openOrCloseMeter(meterCode, op , false, "欠费自动关阀，record："+record.getId());
			}
			
			//判断是否已经发送过，余额不足的情况每个月仅发一次
			List<LlSmsSendlogModel> list = smsService.querySMSSendLogList(user.getId(), meter.getId(),SMSSendLogBean.SENDTYPE_STOPVALVE, null ,DateUtil.getNowMonthStr()+"%");
			if(list.size() == 0) {

				//发送关阀提醒短信
				String content = "您的水表账户余额已欠费("+meter.getBalance().setScale(1, RoundingMode.HALF_UP)+"元)，日内将关闭阀门，停止供水，请续交水费恢复供水。谢谢合作！户号："+user.getCode()+" 户名："+user.getName();
				smsService.saveSMSSendRequest(user ,meter.getId(), content, op,SMSSendLogBean.SENDTYPE_STOPVALVE);
			}
			
			
			//余额不足10元，发送余额不足告警
		}else if(meter.getBalance().compareTo(BigDecimal.TEN) < 0) {
			
			//判断是否已经发送过，余额不足的情况每个月仅发一次
			List<LlSmsSendlogModel> list = smsService.querySMSSendLogList(user.getId(), meter.getId(),SMSSendLogBean.SENDTYPE_BALANCEALARM, null ,DateUtil.getNowMonthStr()+"%");
			
			if(list.size() == 0) {
				LmOperatorModel op = new LmOperatorModel();
				op.setId(-1);
				op.setRealname("抄表接口自动触发");
				
				String content = "您的水表账户尚有"+meter.getBalance().setScale(1, RoundingMode.HALF_UP)+"元余额可用，请于近日续缴水费，以免欠费关阀影响您的用水。谢谢合作!户号："+user.getCode()+" 户名："+user.getName();
				smsService.saveSMSSendRequest(user ,meter.getId(), content, op,SMSSendLogBean.SENDTYPE_BALANCEALARM);
			}
		}
		
		
		return bill;
	}
	
	@Override
	public LmBillModel generatorBillByPayment(String meterCode ,LmPaymentModel payment) {
		
		MeterModelExtendsBean meter = deviceInfoService.findMeterByCode(meterCode);
		
		LmPricedetailModel priceDetail = priceService.findPriceDetailByPriceId(meter.getPricecode());
		LmPriceModel price = priceService.findPriceById(meter.getPricecode());
		
		//计算账单
		int billMonth =Integer.valueOf(DateUtil.formatMonthPure(payment.getCreatetime())) ;
		LmBillModel bill = queryBillByMonth(meter.getId(), billMonth);
		if(bill == null) {
			bill = new LmBillModel();
			bill.setCyclestartbalance(meter.getBalance());
			bill.setMinconsumequantity(price.getMinconsumequantity());
			bill.setMinconsumamount(priceDetail.getPrice1().multiply(price.getMinconsumequantity()));
			bill.setBillmonth(billMonth);
			bill.setCyclebuyamount(BigDecimal.ZERO);
			bill.setQuantity(BigDecimal.ZERO);
		}
		bill.setCycleendbalance(meter.getBalance().add(payment.getAmount()));
		bill.setCyclebuyamount(bill.getCyclebuyamount().add(payment.getAmount()));
//		BigDecimal curQuantity = bill.getQuantity() == null?BigDecimal.ZERO:bill.getQuantity() ;
//		bill.setQuantity(curQuantity.add(fee.getCurquantity()));
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
		bill.setOpername("充值自动触发更新账单");
		
		
		//更新或创建账单
		if(bill.getId() == null) {
			billExtendsMapper.insertSelective(bill);
		}else {
			billExtendsMapper.updateByPrimaryKeySelective(bill);
		}
		
		
		return bill;
	}
	
	@Override
	public LmBillModel queryBillByMonth(int meterId,int billMonth) {
		
		LmBillModelExample ex = new LmBillModelExample();
		LmBillModelExample.Criteria cri = ex.createCriteria();
		
		cri.andMeteridEqualTo(meterId);
		cri.andBillmonthEqualTo(billMonth);
		
		List<LmBillModel> list = billExtendsMapper.selectByExample(ex );
		
		if(list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void initNoPayUserBills(String billMonth ) {
		// 创建无数据（支付、读表）的水表账单， 水表状态为开户的
		billExtendsMapper.createNoPayUserBills(billMonth);
		
		//创建完后，要对这批水表账户扣减最低消费, 23.10.11 不再扣减了，因为会计算未达最低消费的账单时一并计算
//		meterExtendsMapper.updateNopayBillMeterBalance(billMonth);
		
	}
	
	@Override
	public void updateBillByDeductionMinconsumamout(String billMonth) {
		//更新未达到最低消费的账单信息
		billExtendsMapper.updateBillMinConsumamount(billMonth);

		//更新未达到最低消费的水表余额信息
		meterExtendsMapper.updateMeterBalanceByMinConsumamount(billMonth);
		
		
	}

	@Override
	public void updateBillCyclebuyamount(String billMonth) {
		//更新账单中的周期总购买金额数据
		billExtendsMapper.updateBillCyclebuyamount(billMonth);
	}
	
	
	
}
