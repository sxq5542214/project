package com.yd.business.card.service;

import com.yd.business.card.bean.CardInfoBean;
import com.yd.business.card.bean.CardInfoBean.OtherParm;
import com.yd.business.card.bean.CardInfoBean.PriceParm;
import com.yd.business.card.bean.CardInfoBean.SysParam;
import com.yd.business.device.bean.DeviceKindBean;
import com.yd.business.price.bean.PriceBean;

public interface ICardInfoService {

	CardInfoBean generateCardInfoByNew(long userId, long deviceKindId);

	CardInfoBean generateCardInfoByChargeMoney(long userId, Long deviceKindId, int chargePrice);

	CardInfoBean generateCardInfoByUpdateLastChargeMoney(long userId, Long deviceKindId, int chargePrice);

	CardInfoBean generateCardInfoByRepairCard(long userId, Long deviceKindId, int chargePrice, boolean isBrushCard);

	CardInfoBean generateCardInfoByChangeMeter(long userId, Long deviceKindId, int chargePrice);

	int convertImeterkind(PriceBean price, DeviceKindBean deviceKind);

	PriceParm convertPriceParam(CardInfoBean card, PriceBean price, DeviceKindBean deviceKind);

	SysParam convertSysParam(CardInfoBean card, PriceBean price);

	OtherParm convertOtherParam(CardInfoBean card, PriceBean price, int cardKind);

	CardInfoBean generateCardInfoByOpenAccount(long userId, long deviceKindId, int chargePrice, String deviceCompany);

	int saveCardInfoParams(String action, CardInfoBean bean);

}
