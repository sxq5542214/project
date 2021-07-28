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
import com.yd.business.card.bean.CardInfoBean.PriceParm;
import com.yd.business.card.bean.CardInfoBean.UserParm;
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
	

	@Override
	public CardInfoBean generateCardInfoByNew(long userId,long deviceKindId) {
		return generateCardInfo("newCard", userId, deviceKindId,0);
	}

	@Override
	public CardInfoBean generateCardInfoByOpenAccount(long userId,long deviceKindId , int chargePrice) {
		return generateCardInfo("openAccount", userId, deviceKindId,chargePrice);
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
	
	
	
	public CardInfoBean generateCardInfo(String action,long userId ,long deviceKindId,int chargePrice , Boolean isBrushFlag)	{

		UserInfoBean user = userInfoService.findUserById(userId);
		long priceid = user.getU_priceid();
		PriceBean price = priceService.findPriceById(priceid);
		DeviceKindBean deviceKind = deviceKindService.findDeviceKindById(deviceKindId);
		OperatorBean operator = (OperatorBean)WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);

		CardInfoBean bean = new CardInfoBean();
		try {
			ChargeDetailBean chargeDetail = new ChargeDetailBean();
		
			// 价格阶梯
			if(price.getP_ladder() == PriceBean.LADDER_MONTH) {
				switch (deviceKind.getDk_meterkind()) {
				case 3:
					bean.setImeterkind(CardInfoBean.METERKIND_MON_0x31);
					break;
				case 4:
					bean.setImeterkind(CardInfoBean.METERKIND_MON_0x21);
					break;
				case 6:
					bean.setImeterkind(CardInfoBean.METERKIND_MON_0x41);
					break;
				default:
					break;
				}
			}else if(price.getP_ladder() == PriceBean.LADDER_YEAR) {
				bean.setImeterkind(CardInfoBean.METERKIND_YEAR);
			}
			
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
					deviceInfo = deviceInfoService.createDeviceInfo(user,deviceKind,price,chargePrice);
				}
				
				up.setIsavingno(1);
				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
	
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_OPEN_ACCOUNT, ChargeDetailBean.ORDER_MONEY, operator, chargePrice);
				break;
			case "chargeMoney":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
				chargeDetail = chargeDetailService.createChargeDetail(user, price, ChargeDetailBean.KIND_CHARGE, ChargeDetailBean.ORDER_MONEY, operator, chargePrice);

				up.setIsavingno(chargeDetail.getCd_savingno());
				
				break;
			case "updateLastChargeMoney":

				bean.setIcardkind(CardInfoBean.CARDKIND_USER); // 卡类型
				chargeDetail = chargeDetailService.findLastChargeDetailByUser(user.getU_no());
				
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
	
	
}
