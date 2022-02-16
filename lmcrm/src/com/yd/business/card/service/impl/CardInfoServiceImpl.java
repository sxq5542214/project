/**
 * 
 */
package com.yd.business.card.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.bean.BaseBean;
import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.card.bean.CardInfoBean;
import com.yd.business.card.bean.CardInfoBean.OtherParm;
import com.yd.business.card.bean.CardInfoBean.PriceParm;
import com.yd.business.card.bean.CardInfoBean.SysParam;
import com.yd.business.card.bean.CardInfoBean.UserParm;
import com.yd.business.card.dao.ICardInfoDao;
import com.yd.business.card.service.ICardInfoService;
import com.yd.business.device.bean.DeviceInfoBean;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.device.service.IChangeMeterService;
import com.yd.business.device.service.IDeviceInfoService;
import com.yd.business.device.service.IDeviceKindService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.price.service.IPriceService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.util.DateUtil;
import com.yd.util.JsonUtil;

/**
 * @author ice
 *
 */
@Service("cardInfoService")
public class CardInfoServiceImpl extends BaseService implements ICardInfoService {
	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IDeviceKindService deviceKindService;
	@Autowired
	private IDeviceInfoService deviceInfoService;
	@Autowired
	private IPriceService priceService;
	@Autowired
	private IChargeDetailService chargeDetailService;
	@Autowired
	private IChangeMeterService changeMeterService;
	@Autowired
	private ICardInfoDao cardInfoDao;
	

	@Override
	public CardInfoBean generateCardInfoByNew(long userId,long deviceKindId) {
		return generateCardInfo("newCard", userId, deviceKindId,0);
	}

	@Override
	public CardInfoBean generateCardInfoByOpenAccount(long userId,long deviceKindId , int chargePrice,String deviceCompany) {
		return generateCardInfo("openAccount", userId, deviceKindId,chargePrice,false,deviceCompany);
	}
	

	@Override
	public CardInfoBean generateCardInfoByChargeMoney(long userId,Long deviceKindId , int chargePrice) {
		// 查询用户的设备类型
		if(deviceKindId == null) {
			DeviceInfoBean device = deviceInfoService.findDeviceInfoByUserAndKind(userId, null);
			if(device == null) {
				throw new RuntimeException("error!  userid:" + userId +"  not find device .....");
			}
			deviceKindId = device.getDi_dkid();
		}
		
		return generateCardInfo("chargeMoney", userId, deviceKindId,chargePrice);
	}
	

	@Override
	public CardInfoBean generateCardInfoByUpdateLastChargeMoney(long userId,Long deviceKindId , int chargePrice) {
		// 查询用户的设备类型
		if(deviceKindId == null) {
			DeviceInfoBean device = deviceInfoService.findDeviceInfoByUserAndKind(userId, null);
			if(device == null) {
				throw new RuntimeException("error!  userid:" + userId +"  not find device .....");
			}
			deviceKindId = device.getDi_dkid();
		}
		
		return generateCardInfo("updateLastChargeMoney", userId, deviceKindId,chargePrice);
	}


	@Override
	public CardInfoBean generateCardInfoByRepairCard(long userId,Long deviceKindId , int chargePrice , boolean isBrushCard) {
		// 查询用户的设备类型
		if(deviceKindId == null) {
			DeviceInfoBean device = deviceInfoService.findDeviceInfoByUserAndKind(userId, null);
			if(device == null) {
				throw new RuntimeException("error!  userid:" + userId +"  not find device .....");
			}
			deviceKindId = device.getDi_dkid();
		}
		
		
		isBrushCard = false;// 如果为是充值次数会加1，但如果实际未刷卡，这里加1就会导致刷卡提示次数对不上。 统一都设置为未刷卡
		return generateCardInfo("repairCard", userId, deviceKindId,chargePrice,isBrushCard);
	}
	


	@Override
	public CardInfoBean generateCardInfoByChangeMeter(long userId,Long deviceKindId , int chargePrice ) {
		
		// 查询用户的设备类型
		if(deviceKindId == null) {
			DeviceInfoBean device = deviceInfoService.findDeviceInfoByUserAndKind(userId, null);
			if(device == null) {
				throw new RuntimeException("error!  userid:" + userId +"  not find device .....");
			}
			deviceKindId = device.getDi_dkid();
		}
		
		return generateCardInfo("changeMeter", userId, deviceKindId,chargePrice);
	}
	
	@Override
	public int convertImeterkind(PriceBean price , DeviceKindBean deviceKind) {
		int imeterkind = -1;
		// 价格阶梯
		if(price.getP_ladder() == PriceBean.LADDER_MONTH) {
			switch (deviceKind.getDk_meterkind()) {
			case 3:
				imeterkind = CardInfoBean.METERKIND_MON_0x31;
				break;
			case 4:
				imeterkind = CardInfoBean.METERKIND_MON_0x21;
				break;
			case 6:
				imeterkind = CardInfoBean.METERKIND_MON_0x41;
				break;
			default:
				break;
			}
		}else if(price.getP_ladder() == PriceBean.LADDER_YEAR) {
			imeterkind = CardInfoBean.METERKIND_YEAR;
		}
		return imeterkind;
	}

	@Override
	public PriceParm convertPriceParam(CardInfoBean card,PriceBean price , DeviceKindBean deviceKind) {
		if(card == null) {
			card = new CardInfoBean();
		}
		PriceParm pp = card.new PriceParm();
		
		int imeterKind = card.getImeterkind();
		switch (imeterKind) {
		case CardInfoBean.METERKIND_MON_0x21:

			pp.setIton1(price.getP_ton1().multiply(new BigDecimal(10)).intValue());
			pp.setIton2(price.getP_ton2().multiply(new BigDecimal(10)).intValue());
			pp.setIton3(0);
			//最低消费金额
			pp.setIton4(price.getP_lowprice().intValue());
			//最低消费吨数
			pp.setIton5(price.getP_lowamount().intValue());
			pp.setIprice1(price.getP_price1().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice2(price.getP_price2().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice3(price.getP_price3().multiply(new BigDecimal(100)).intValue()); //分为单位
			break;
		case CardInfoBean.METERKIND_MON_0x31:

			pp.setIton1(price.getP_ton1().multiply(new BigDecimal(10)).intValue());
			pp.setIton2(price.getP_ton2().multiply(new BigDecimal(10)).intValue());
			pp.setIton3(0);
			//最低消费金额
			pp.setIton4(price.getP_lowprice().multiply(new BigDecimal(100)).intValue()); //分为单位
			//最低消费吨数
			pp.setIton5(price.getP_lowamount().intValue());
			pp.setIprice1(price.getP_price1().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice2(price.getP_price2().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice3(price.getP_price3().multiply(new BigDecimal(100)).intValue()); //分为单位
			break;
		case CardInfoBean.METERKIND_MON_0x41:

			pp.setIton1(price.getP_ton1().intValue());
			pp.setIton2(price.getP_ton2().intValue());
			pp.setIton3(0);
			pp.setIton4(0);
			pp.setIton5(0);
			pp.setIprice1(price.getP_price1().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice2(price.getP_price2().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice3(price.getP_price3().multiply(new BigDecimal(100)).intValue()); //分为单位
			break;
		case CardInfoBean.METERKIND_YEAR:
			
			break;

		default:
			break;
		}
		
		pp.setIday(price.getP_settleday());
		pp.setImonth(price.getP_settlemonth());
		
		return pp;
	}
	@Override
	public OtherParm convertOtherParam(CardInfoBean card,PriceBean price ,int cardKind) {
		if(card == null) {
			card = new CardInfoBean();
		}
		OtherParm op = card.new OtherParm();
		
		int imeterKind = card.getImeterkind();
		switch (imeterKind) {
		case CardInfoBean.METERKIND_MON_0x21:
			op.setImonth(price.getP_settlemonth());
			op.setIday(price.getP_settleday());

			break;
		case CardInfoBean.METERKIND_MON_0x31:
			op.setImonth(price.getP_settlemonth());
			op.setIday(price.getP_settleday());
			break;
		case CardInfoBean.METERKIND_MON_0x41:
			op.setImonth(price.getP_settlemonth());
			op.setIday(price.getP_settleday());

			break;
		case CardInfoBean.METERKIND_YEAR:
			op.setImonth(price.getP_settlemonth());
			op.setIday(price.getP_settleday());
			
			break;

		default:
			break;
		}
		
		
		
		switch (cardKind) {
		case CardInfoBean.CARDKIND_TIME:
			String dateStr = DateUtil.getNowDateStr();

			op.setIyear(Integer.parseInt(dateStr.substring(2, 4)));
			op.setImonth(Integer.parseInt(dateStr.substring(5, 7)));
			op.setIday(Integer.parseInt(dateStr.substring(8, 10)));
			op.setIhour(Integer.parseInt(dateStr.substring(11, 13)));
			op.setImin(Integer.parseInt(dateStr.substring(14, 16)));
			op.setIsec(Integer.parseInt(dateStr.substring(17, 19)));
			
			break;

		default:
			break;
		}
		
		return op;
	}
	
	@Override
	public SysParam convertSysParam(CardInfoBean card,PriceBean price) {
		SysParam sp =  card.new SysParam();

		int imeterKind = card.getImeterkind();
		switch (imeterKind) {
		case CardInfoBean.METERKIND_MON_0x21:
			sp.setIlimitamount(price.getP_limitamount().multiply(new BigDecimal(10)).intValue());
			sp.setIoverflat(price.getP_overflat().multiply(new BigDecimal(10)).intValue());
			sp.setIstoredamount(price.getP_storedamount().multiply(new BigDecimal(10)).intValue());
			sp.setIweakprompt(price.getP_weakprompt().multiply(new BigDecimal(10)).intValue());

			break;
		case CardInfoBean.METERKIND_MON_0x31:
			sp.setIlimitamount(price.getP_limitamount().intValue());
			sp.setIoverflat(price.getP_overflat().intValue());
			sp.setIstoredamount(price.getP_storedamount().multiply(new BigDecimal(10)).intValue());
			sp.setIweakprompt(price.getP_weakprompt().intValue());
			break;
		case CardInfoBean.METERKIND_MON_0x41:
			sp.setIlimitamount(price.getP_limitamount().intValue());
			sp.setIoverflat(price.getP_overflat().intValue());
			sp.setIstoredamount(price.getP_storedamount().intValue());
			sp.setIweakprompt(price.getP_weakprompt().intValue());

			break;
		case CardInfoBean.METERKIND_YEAR:
			
			break;

		default:
			break;
		}
		
		return sp;
	}
	
	public CardInfoBean generateCardInfo(String action,long userId ,long deviceKindId,int chargePrice , Boolean isBrushFlag)	{
		return generateCardInfo(action, userId, deviceKindId, chargePrice, isBrushFlag, null);
	}
	
	public CardInfoBean generateCardInfo(String action,long userId ,long deviceKindId,int chargePrice , Boolean isBrushFlag,String deviceCompany)	{

		UserInfoBean user = userInfoService.findUserById(userId);
		long priceid = user.getU_priceid();
		PriceBean price = priceService.findPriceById(priceid);
		DeviceKindBean deviceKind = deviceKindService.findDeviceKindById(deviceKindId);
		OperatorBean operator = (OperatorBean)WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);

		CardInfoBean bean = new CardInfoBean();
		try {
			ChargeDetailBean chargeDetail = new ChargeDetailBean();
			
			
			int imeterkind = convertImeterkind(price, deviceKind);
			bean.setImeterkind(imeterkind);
			
			bean.setIsyscode(deviceKind.getDk_no()); // 维护码，记录在水表类型表中
			bean.setIsupper(0);
			
			UserParm up = bean.new UserParm();
			up.setIuserno(user.getU_cardno());
			up.setIamount(chargePrice * 10);  // 角为单位
			bean.setStru_userparm(up);
			
			PriceParm pp = bean.new PriceParm();
			pp.setIton1(price.getP_ton1().multiply(new BigDecimal(10)).intValue());
			pp.setIton2(price.getP_ton2().multiply(new BigDecimal(10)).intValue());
			pp.setIton3(0);
			
			//最低消费金额
			if(bean.getImeterkind() == CardInfoBean.METERKIND_MON_0x31 ) {
				pp.setIton4(price.getP_lowprice().multiply(new BigDecimal(100)).intValue());
			}else {
				pp.setIton4(price.getP_lowprice().intValue());
			}
			//最低消费吨数
			pp.setIton5(price.getP_lowamount().intValue());
			
			pp.setIprice1(price.getP_price1().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice2(price.getP_price2().multiply(new BigDecimal(100)).intValue()); //分为单位
			pp.setIprice3(price.getP_price3().multiply(new BigDecimal(100)).intValue()); //分为单位
			bean.setStru_priceparm(pp);
			
			switch (action) {
			case "newCard":
				up.setIsavingno(0);
				bean.setIcardkind(CardInfoBean.CARDKIND_NEW); // 卡类型
				break;
			case "openAccount":
				
				DeviceInfoBean deviceInfo = deviceInfoService.findDeviceInfoByUserAndKind(user.getU_id(), deviceKindId);
				if(deviceInfo == null) {
					// 充值的入参按角为单位，需要换算为元
					deviceInfo = deviceInfoService.createDeviceInfo(user,deviceKind,price,chargePrice,deviceCompany);
				}
				
				up.setIsavingno(1);
				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
				if(imeterkind == CardInfoBean.METERKIND_MON_0x31) { //技术文档中要求如果是49类型的表，price6传0x88
					bean.getStru_priceparm().setIprice6(0x88);
				}
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_OPEN_ACCOUNT, ChargeDetailBean.ORDER_MONEY, operator, chargePrice);
				break;
			case "chargeMoney":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_CHARGE, ChargeDetailBean.ORDER_MONEY, operator, chargePrice);

				up.setIsavingno(chargeDetail.getCd_savingno());
				
				break;
			case "updateLastChargeMoney":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
				chargeDetail = chargeDetailService.findLastChargeDetailByUserId(user.getU_id());
				
				chargeDetail.setCd_chargeamount(new BigDecimal(chargePrice).divide(price.getP_price1(),2,BigDecimal.ROUND_HALF_UP));
				chargeDetail.setCd_chargemoney(new BigDecimal(chargePrice));
				chargeDetail.setCd_basemoney(chargeDetail.getCd_chargemoney());
				chargeDetail.setCd_kindid(ChargeDetailBean.KIND_CHARGE_UPDATE);
				chargeDetailService.updateChargeDetail(chargeDetail);
				break;
			case "repairCard":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
//				chargeDetail = chargeDetailService.findLastChargeDetailByUser(user.getU_no());
				
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_CHANGE_CARD, ChargeDetailBean.ORDER_MONEY, operator, chargePrice , isBrushFlag);

				up.setIsavingno(chargeDetail.getCd_savingno());
				break;
				
			case "changeMeter":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
//				chargeDetail = chargeDetailService.findLastChargeDetailByUser(user.getU_no());
				
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_CHANGE_DEVICE, ChargeDetailBean.ORDER_MONEY, operator, chargePrice , isBrushFlag);

				up.setIsavingno(chargeDetail.getCd_savingno());
				break;
				
			default:
				break;
			}
		
			bean.setChargeDetailId(chargeDetail.getCd_id());
			
			//保存写卡记录
			saveCardInfoParams(action, bean);
		}catch (Exception e) {
			log.error(e, e);
			bean.setQueryStatus(BaseBean.QUERYSTATUS_ERROR);
			bean.setQueryResult(e.getMessage());
		}
		
		return bean;
	}
	/**
	 * 
	 * @param cardKind
	 * @param userId
	 * @param deviceKindId
	 * @param chargePrice  充值金额，元为单位
	 * @return
	 */
	public CardInfoBean generateCardInfo(String action,long userId ,long deviceKindId,int chargePrice)	{
		return generateCardInfo(action, userId, deviceKindId, chargePrice, false);
	}
	
	
	public CardInfoBean readCardAndUpdate() {
		
		
		
		return null;
	}
	
	/**
	 * 保存写卡的参数 日志
	 * @param bean
	 * @return
	 */
	@Override
	public int saveCardInfoParams(String action ,CardInfoBean bean) {

		OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
		bean.setResult_code( op.getO_id().intValue() );
		
		String jsonString = JsonUtil.convertObjectToJsonString(bean);
		bean.setResult_desc(jsonString);
		
		bean.setQueryResult(DateUtil.getNowDateStr());
		
		bean.setOrderby(action);
		
		return cardInfoDao.insertWriteCardLogs(bean);
	}
	
}
