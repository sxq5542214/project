package com.yd.business.card.service;

import com.yd.business.card.bean.CardInfoBean;

public interface ICardInfoService {

	CardInfoBean generateCardInfoByNew(long userId, long deviceKindId);

	CardInfoBean generateCardInfoByOpenAccount(long userId, long deviceKindId, int chargePrice);

	CardInfoBean generateCardInfoByChargeMoney(long userId, Long deviceKindId, int chargePrice);

	CardInfoBean generateCardInfoByUpdateLastChargeMoney(long userId, Long deviceKindId, int chargePrice);

	CardInfoBean generateCardInfoByRepairCard(long userId, Long deviceKindId, int chargePrice, boolean isBrushCard);

}
