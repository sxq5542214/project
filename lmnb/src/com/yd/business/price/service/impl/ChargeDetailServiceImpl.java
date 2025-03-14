package com.yd.business.price.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.bill.service.IBillService;
import com.yd.business.device.bean.MeterModelExtendsBean;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.msg.bean.SMSSendLogBean;
import com.yd.business.msg.service.ISMSService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.operator.service.IOperatorService;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.bean.PrintBean;
import com.yd.business.price.dao.IChargeDetailDao;
import com.yd.business.price.dao.IPaymentExtendsMapper;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.iotbusiness.mapper.dao.LmPaymentModelMapper;
import com.yd.iotbusiness.mapper.model.LmBillModel;
import com.yd.iotbusiness.mapper.model.LmMeterModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModel;
import com.yd.iotbusiness.mapper.model.LmPaymentModelExample;
import com.yd.iotbusiness.mapper.model.LmPaymentModelExample.Criteria;
import com.yd.iotbusiness.mapper.model.LmPricedetailModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.util.DateUtil;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

@Service("chargeDetailService")
public class ChargeDetailServiceImpl extends BaseService implements IChargeDetailService {
	@Resource
	private IChargeDetailDao chargeDetailDao;
	@Resource
	private IUserInfoService userInfoService;
	@Resource
	private IDeviceInfoService deviceInfoService;
	@Resource
	private IPriceService priceService;
	@Resource
	private IOperatorService operatorService;
	@Resource
	private IPaymentExtendsMapper iPaymentExtendsMapper;
	@Resource
	private IBillService billService;
	@Autowired
	private ISMSService smsService;

	@Override
	public ChargeDetailBean findLastChargeDetailByUserId(Long userid) throws Exception {
		
//		UserInfoBean user = userInfoService.findUserById(userid);
//		ChargeDetailBean bean = new ChargeDetailBean();
//		bean.setCd_userid(user.getU_id());
//		bean.setOrderby("order by cd_no desc ");
//		List<ChargeDetailBean> list = chargeDetailDao.queryChargeDetailList(bean);
//
//		if(list.size() > 0 ) {
//			bean = list.get(0);
//			return bean;
//		}
		return null;
	}

	/**
	 * 
	 * @param user		用户信息
	 * @param price		用户的价格信息
	 * @param kind		充值类型，0是开户，1是充值，2是充值修改等
	 * @param order		付款方式   0是现金 1是微信  2是支付宝
	 * @param operator	操作人
	 * @param money		充值金额，元为单位
	 * @param isBrushCard		是否刷卡到表
	 * @return			充值记录
	 * @throws Exception	上次有未刷卡至水表的数据
	 */
	@Override
	public ChargeDetailBean createChargeDetail(UserInfoBean user,PriceBean price,int kind,int order,OperatorBean operator,int money ,boolean isBrushCard ) throws Exception {
		
		if(user.getU_id() == null) {
			throw new Exception("创建充值记录时，未查询到用户信息，请检查！ ");
		}
		
		ChargeDetailBean bean = new ChargeDetailBean();
		bean.setCd_userid(user.getU_id());
		bean.setOrderby("order by cd_id desc ");
		List<ChargeDetailBean> list = chargeDetailDao.queryChargeDetailList(bean);
		Long no = 1l ;
		Long saving_no = 1l ;
		if(list.size() > 0 ) {
			ChargeDetailBean lastCharge = list.get(0);
			
			// 如果之前写卡失败，则修改金额，直接返回之前的写卡数据.   补卡/换表维护 可能之前未刷卡，所以判断剔除补卡情况
			if(lastCharge.getCd_charge() != ChargeDetailBean.CHARGE_SUCCESS && kind != ChargeDetailBean.KIND_CHANGE_CARD && kind != ChargeDetailBean.KIND_CHANGE_DEVICE ) {
				lastCharge.setCd_chargeamount(new BigDecimal(money).divide(price.getP_price1(),2,BigDecimal.ROUND_HALF_UP));
				lastCharge.setCd_chargemoney(new BigDecimal(money));
				lastCharge.setCd_basemoney(lastCharge.getCd_chargemoney());
				updateChargeDetail(lastCharge);
				return lastCharge ;
			}
//			老系统中未做该判断，仅判断卡中的刷表字段。  因此注释。 by 2022.2.23
//			if(lastCharge.getCd_brushflag() == ChargeDetailBean.BRUSHFLAG_NO && kind != ChargeDetailBean.KIND_CHANGE_CARD && kind != ChargeDetailBean.KIND_CHANGE_DEVICE ) {
//				throw new Exception("当前用户上次充值未刷卡至水表，请刷卡至水表后再试！ cardNo:"+ user.getU_cardno() );
//			}
			no = lastCharge.getCd_no() + 1l ;
			saving_no = lastCharge.getCd_savingno() + 1l;
		}
		bean.setCd_savingno(saving_no.intValue());
		bean.setCd_no(no);
		if( kind == ChargeDetailBean.KIND_CHANGE_CARD && !isBrushCard) { //补卡并且之前未刷卡
			bean.setCd_savingno(saving_no.intValue() - 1);
		}
		if( kind == ChargeDetailBean.KIND_CHANGE_DEVICE) {  //换表维护，充值次数需要修改为从1开始
			bean.setCd_savingno(1);
		}
		
		bean.setCd_brushflag(ChargeDetailBean.BRUSHFLAG_NO);
		bean.setCd_charge(ChargeDetailBean.CHARGE_FAILD_WRITE);
		bean.setCd_chargeamount(new BigDecimal(money).divide(price.getP_price1(),2,BigDecimal.ROUND_HALF_UP));
		bean.setCd_chargemoney(new BigDecimal(money));
		bean.setCd_kindid(kind);
		bean.setCd_operatorid(operator.getO_id());
		bean.setCd_order(order);
		bean.setCd_priceid(price.getP_id());
		bean.setCd_readerid(bean.getCd_operatorid());
		bean.setCd_printstatus(ChargeDetailBean.PRINT_STATUS_NO);
		
		bean.setCd_basemoney(bean.getCd_chargemoney());
		// 用户补卡、 换表的情况，实际不支付金额，所以付款金额设置为0
		if(kind == ChargeDetailBean.KIND_CHANGE_CARD || kind == ChargeDetailBean.KIND_CHANGE_DEVICE) {
			bean.setCd_paidmoney(BigDecimal.ZERO);
		}else {
			bean.setCd_paidmoney(bean.getCd_chargemoney());
		}
		bean.setCd_othermoney1(price.getP_other1().multiply(bean.getCd_chargeamount()));
		bean.setCd_othermoney2(price.getP_other2().multiply(bean.getCd_chargeamount()));
		bean.setCd_lastbalance(BigDecimal.ZERO);
		bean.setCd_balance(BigDecimal.ZERO);
		bean.setCd_startamount(BigDecimal.ZERO);
		bean.setCd_endamount(BigDecimal.ZERO);
		bean.setCd_printstatus(ChargeDetailBean.PRINT_STATUS_NO);
		bean.setCd_tradecustomer("FFFFFFFF");
		bean.setCd_tradeno("FFFFFFFF");
		bean.setCd_tradestatus(0);
		bean.setCd_amountton1(BigDecimal.ZERO);
		bean.setCd_amountton2(BigDecimal.ZERO);
		bean.setCd_amountton3(BigDecimal.ZERO);
		bean.setCd_basemoneyton2(BigDecimal.ZERO);
		bean.setCd_basemoneyton3(BigDecimal.ZERO);
		
		bean.setCd_happendate(new Date());
		bean.setCd_startdate(new Date());
		bean.setCd_enddate(new Date());
		chargeDetailDao.insertChargeDetail(bean);
		
		return bean;
	}	
	/**
	 * 
	 * @param user		用户信息
	 * @param price		用户的价格信息
	 * @param kind		充值类型，0是开户，1是充值，2是充值修改等
	 * @param order		付款方式   0是现金 1是微信  2是支付宝
	 * @param operator	操作人
	 * @param money		充值金额，元为单位
	 * @return			充值记录
	 * @throws Exception	上次有未刷卡至水表的数据
	 */
	@Override
	public ChargeDetailBean createChargeDetail(UserInfoBean user,PriceBean price,int kind,int order,OperatorBean operator,int money ) throws Exception {
		return createChargeDetail(user, price, kind, order, operator, money,false);

	}
	
	@Override
	public int updateChargeDetailToSuccess(String cdid) {
		Long id = Long.parseLong(cdid);
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(id);
		bean.setCd_charge(ChargeDetailBean.CHARGE_SUCCESS);
		bean.setCd_startdate(new Date());
		
		int num = chargeDetailDao.updateChargeDetail(bean);
		
		bean = findChargeDetailById(id);
		
		switch (bean.getCd_kindid()) {
		case ChargeDetailBean.KIND_OPEN_ACCOUNT:
			//修改用户表的用户状态为已开户
			userInfoService.updateUserStatusToNormal(bean.getCd_userid());
			break;

		default:
			break;
		}
		 
		return num;
	}
	
	@Override
	public int updateChargeDetail(ChargeDetailBean bean) {
		return chargeDetailDao.updateChargeDetail(bean);
		
	}

	@Override
	public int updateChargeDetailBrushFlagToSuccess(Long cd_id,Date brushDate) {
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(cd_id);
		bean.setCd_brushflag(ChargeDetailBean.BRUSHFLAG_YES);
		bean.setCd_enddate(brushDate);
		
		int num = chargeDetailDao.updateChargeDetail(bean);
		
		 
		return num;
	}
	

	@Override
	public int updateChargeDetailPrintStatus(Long cd_id,int printStatus) {
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(cd_id);
		bean.setCd_printstatus(printStatus);
		
		int num = chargeDetailDao.updateChargeDetail(bean);
		
		 
		return num;
	}
	
	
	@Override
	public ChargeDetailBean findChargeDetailById(Long id) {
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(id);
		List<ChargeDetailBean> list = chargeDetailDao.queryChargeDetailList(bean);
		return list.size() > 0 ? list.get(0):null;
	}
	@Override
	public List<ChargeDetailBean> queryChargeListByUserId(Long u_id) {
		
		return  chargeDetailDao.queryChargeListByUserId(u_id);
		
	}
	
	@Override
	public LmPaymentModel findPayMentBySerialnum(String serialnum){
		LmPaymentModelExample exam = new LmPaymentModelExample();
		LmPaymentModelExample.Criteria cri = exam.createCriteria();
		cri.andSerialnumEqualTo(serialnum);
		List<LmPaymentModel> list = iPaymentExtendsMapper.selectByExample(exam );
		if(list.size() > 0 ) return list.get(0);
		return null;
	}

	@Override
	public IOTWebDataBean queryMonthChargeAmoutSum(String billMonth,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", billMonth);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		BigDecimal value = iPaymentExtendsMapper.sumMonthChargeAmout(map);

		result.setData(value);
		return result;
	}

	@Override
	public IOTWebDataBean queryDayChargeAmoutSum(String day,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("day", day);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		BigDecimal value = iPaymentExtendsMapper.sumMonthChargeAmout(map);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryDayBuyAmountMeterCount(String day,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("day", day);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		int value = iPaymentExtendsMapper.countDayBuyAmountMeter(map);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryMonthBuyAmountMeterCount(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		int value = iPaymentExtendsMapper.countDayBuyAmountMeter(map);

		result.setData(value);
		return result;
	}

	@Override
	public IOTWebDataBean queryDayBuyAmountSumListOfMonth(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		List<Integer> value = iPaymentExtendsMapper.queryDayBuyAmountSumListOfMonth(map);

		result.setData(value);
		return result;
	}

	@Override
	public IOTWebDataBean queryDayBuyAmountListData(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		List<Map<String, Object>> value = iPaymentExtendsMapper.queryLast2MonthAmountDayList(map);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryDayBuyCountListData(String month,Integer systemid,Integer operatorid) {
		IOTWebDataBean result = new IOTWebDataBean();

		Map<String, Object> map= new HashMap<>();
		map.put("billMonth", month);
		map.put("systemid", systemid);
		map.put("operatorid", operatorid);
		
		List<Map<String, Object>> value = iPaymentExtendsMapper.queryLast2MonthCountDayList(map);

		result.setData(value);
		return result;
	}
	@Override
	public IOTWebDataBean queryChargeList(LmPaymentModel model) {
		IOTWebDataBean result = new IOTWebDataBean();
		
		LmPaymentModelExample ex = new LmPaymentModelExample();
		LmPaymentModelExample.Criteria cri = ex.createCriteria();
		cri.andUseridEqualTo( model.getUserid());
		cri.andSystemidEqualTo(model.getSystemid());
		cri.andSaleremarkNotLike("%开户费%");
		if(StringUtil.isNotNull(model.getMetercode())){
			cri.andMetercodeEqualTo(model.getMetercode());
		}
		ex.setOrderByClause(" id desc ");
		List<LmPaymentModel> list = iPaymentExtendsMapper.selectByExample(ex );
		result.setTotal(Long.valueOf(list.size()));
		result.setData(list);
		
		return result;
	}
	
	@Override
	public LmPaymentModel createOpenFeePayment(String metercode,int openfee,String remark) {
		LmPaymentModel payment = new LmPaymentModel();
		BigDecimal charge = new BigDecimal(openfee);
		// 插入payment表
		MeterModelExtendsBean meter = deviceInfoService.findMeterByCode(metercode);
		LmPricedetailModel price = priceService.findPriceDetailById(meter.getPricecode());
		payment.setUserid(meter.getUserid());
		payment.setSystemid(meter.getSystemid());
		payment.setPayamount(charge);
		payment.setPrebalance(meter.getBalance());
		payment.setAfterbalacne(meter.getBalance());
		payment.setCreatetime(new Date());
		payment.setUsername(meter.getUserName());
		payment.setUseraddress(meter.getArea1()+"-"+meter.getArea2()+"-"+meter.getArea3()+"-"+meter.getArea4());
		payment.setMeterid(meter.getId());
		payment.setMetercode(meter.getCode());
		payment.setLifecode(meter.getId());
		payment.setSerialnum(meter.getCode()+"-"+payment.getAccountmode() +"-"+DateUtil.getNowDateStrSSS());
		payment.setQuantity1(BigDecimal.ZERO);
		payment.setAmount(charge);
		payment.setAmount1(BigDecimal.ZERO);
		payment.setWaterfee(BigDecimal.ZERO);
		payment.setPricecode(meter.getPricecode().toString());
		payment.setPricename(meter.getPricename());
		payment.setSaleremark(remark);
		payment.setPaystate(ChargeDetailBean.PAY_STATUS_SUCCESS);

		Integer num = iPaymentExtendsMapper.insertSelective(payment);
		
		return payment;
	}
	
	@Override
	public Integer handleChargeBalance(LmPaymentModel model) {
		BigDecimal charge = model.getAmount();
		
		// 插入payment表
		MeterModelExtendsBean meter = deviceInfoService.findMeterByCode(model.getMetercode());
		LmPricedetailModel price = priceService.findPriceDetailById(meter.getPricecode());
		LmOperatorModel op = operatorService.findOperatorById(model.getOperatorid());
		LmUserModel user = userInfoService.findUserById(meter.getUserid());
		
		model.setUserid(meter.getUserid());
		model.setSystemid(meter.getSystemid());
		model.setPayamount(charge);
		model.setPrebalance(meter.getBalance());
		model.setAfterbalacne(meter.getBalance().add(charge));
		model.setCreatetime(new Date());
		model.setUsername(meter.getUserName());
		model.setUseraddress(meter.getArea1()+"-"+meter.getArea2()+"-"+meter.getArea3()+"-"+meter.getArea4());
		model.setMeterid(meter.getId());
		model.setMetercode(meter.getCode());
		model.setLifecode(meter.getId());
		if(model.getSerialnum() == null) {
			model.setSerialnum(meter.getCode()+"-"+model.getAccountmode() +"-"+DateUtil.formatDateToPureSSS(model.getCreatetime()));
		}else {
			// 如果已有订单号，查询是否已创建，对于已创建的微信支付单，直接结束，因为可能是微信多次回调的
			LmPaymentModel pay = findPayMentBySerialnum(model.getSerialnum());
			if(pay != null && pay.getSerialnum().startsWith("WX")) return 1;
		}
		model.setQuantity1(charge.divide(price.getPrice1()));
		model.setAmount(charge);
		model.setAmount1(charge);
		model.setWaterfee(charge);
		model.setPricecode(meter.getPricecode().toString());
		model.setPricename(meter.getPricename());
		
		Integer num = iPaymentExtendsMapper.insertSelective(model);

		//更新meter表中的余额
		meter.setBalance(meter.getBalance().add(charge));
		meter.setLastbuytime(model.getCreatetime());
		meter.setLastbuyamount(charge);
		meter.setLastbuyquantity(model.getQuantity1());
		meter.setCyclebuyquantity(model.getQuantity1());
		meter.setAddupamount(meter.getAddupamount().add(charge));
		
		
		// 若充值后余额大于0 ，则执行CMD开阀命令、并插入cmd表
		if(meter.getBalance().compareTo(BigDecimal.ZERO) > 0 ) {
			deviceInfoService.openOrCloseMeter(meter.getCode(), op, true,"充值后自动执行开阀");
		}
		
		meter.setRecentcmdtime(new Date());

		//生成账单，如果有则更新账单数据
		LmBillModel bill = billService.generatorBillByPayment(meter.getCode(), model);
		//更新水表数据
		deviceInfoService.addOrUpdateMeter(meter);
		
		//更新支付表对应的账单ID
		model.setBillid(bill.getId());
		iPaymentExtendsMapper.updateByPrimaryKeySelective(model);
		
		//触发缴费通知短信
		String content = "您于"+DateUtil.getNowDateStr()+"已交水费"+charge.setScale(2, RoundingMode.HALF_UP)+"元，账户余额"+meter.getBalance()+"元，请您知晓！户号："+user.getCode()+" 户名："+user.getName();
		smsService.sendJXTsms(user,meter.getId(), content, op,SMSSendLogBean.SENDTYPE_CHARGENOTIFY);
		
		return num;
	}
	
	@Override
	public void updatePaymentMeterCode(String oldMeterCode,String newMeterCode) {
		iPaymentExtendsMapper.updatePaymentMeterCode(oldMeterCode,newMeterCode);
	}
	
	@Override
	public PrintBean generatePrintBean(long cdid) {
		PrintBean bean = new PrintBean();
		
		ChargeDetailBean cd = findChargeDetailById(cdid);
		LmUserModel user = userInfoService.findUserById(cd.getCd_userid().intValue());
//		PriceBean price = priceService.findPriceById(user.getU_priceid());
		OperatorBean operator = operatorService.findOperatorById(cd.getCd_operatorid());
		// 用户信息、价格
		bean.setTxtUserNo1(user.getCode());
		bean.setTxtUserName1(user.getName());
		bean.setTxtUserAddress1(user.getArea1()+user.getArea2()+user.getArea3());
//		bean.setTxtPriceKind1(price.getP_name());
//		bean.setTxtPrice11(price.getP_price1().setScale(2, RoundingMode.HALF_UP).toString());
		
		//抄表
		bean.setTxtReadingDate1("/");
		bean.setTxtStartAmount1("/");
		bean.setTxtEndAmount1("/");
		bean.setTxtOperator12("/");
		
		//结算时间、水量、金额
		bean.setTxtStartDate("/");
		bean.setTxtEndDate("/");
		bean.setTxtChargeAmount11(cd.getCd_chargeamount().setScale(2, RoundingMode.HALF_UP).toString());
		bean.setTxtChargeMoney11(cd.getCd_chargemoney().setScale(2, RoundingMode.HALF_UP).toString());
		
		//实收金额
		bean.setTxtPaidMoney(cd.getCd_paidmoney().setScale(2, RoundingMode.HALF_UP).toString());
		//用户余额
//		bean.setTxtBalance(user.getU_balance().setScale(2, RoundingMode.HALF_UP).toString());
		bean.setTxtOperator11(operator.getO_name());
		
		//收费日期
		bean.setTxtChargeDate1(DateUtil.formatDateOnlyDate(cd.getCd_startdate() ));
		//累计购水量    打印纸上对应的是合计 小写
		bean.setTxtTotalCharge1(cd.getCd_paidmoney().setScale(2, RoundingMode.HALF_UP).toString());
		//大写金额
		String bigMoney = NumberUtil.number2CNMontrayUnit(cd.getCd_paidmoney());
		bean.setTxtBigMoney1(bigMoney);
		
		//支付方式
		bean.setTxtChargeOrder1("现金");
		//排污费等
		bean.setTxtOtherMoney11("/");
		bean.setTxtOtherMoney12("/");
		
		return bean;
	}
	
}
