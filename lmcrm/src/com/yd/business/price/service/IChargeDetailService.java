package com.yd.business.price.service;

import java.util.Date;
import java.util.List;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.bean.PrintBean;
import com.yd.business.user.bean.UserInfoBean;

public interface IChargeDetailService {

	/**
	 * 
	 * @param user		用户信息
	 * @param price		用户的价格信息
	 * @param kind		充值类型，0是开户，1是充值，2是充值修改等
	 * @param order		付款方式   0是现金 1是微信  2是支付宝
	 * @param operator	操作人
	 * @param money		充值金额，元为单位
	 * @return			充值记录
	 * @throws Exception
	 */
	ChargeDetailBean createChargeDetail(UserInfoBean user, PriceBean price, int kind, int order, OperatorBean operator,
			int money) throws Exception;

	ChargeDetailBean createChargeDetail(UserInfoBean user, PriceBean price, int kind, int order, OperatorBean operator,
			int money, boolean isBrushCard) throws Exception;
	
	int updateChargeDetailToSuccess(String cdid);

	ChargeDetailBean findChargeDetailById(Long id);

	int updateChargeDetail(ChargeDetailBean bean);

	int updateChargeDetailBrushFlagToSuccess(Long cd_id, Date brushDate);

	List<ChargeDetailBean> queryChargeListByUserId(Long u_id);

	PrintBean generatePrintBean(long cdid);

	ChargeDetailBean findLastChargeDetailByUserId(Long userid) throws Exception;

	int updateChargeDetailPrintStatus(Long cd_id, int printStatus);

}
