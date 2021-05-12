package com.yd.business.price.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.card.bean.CardInfoBean;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PriceBean;
import com.yd.business.price.dao.IChargeDetailDao;
import com.yd.business.price.service.IChargeDetailService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.util.DateUtil;

@Service("chargeDetailService")
public class ChargeDetailServiceImpl extends BaseService implements IChargeDetailService {
	@Resource
	private IChargeDetailDao chargeDetailDao;
	@Resource
	private IUserInfoService userInfoService;


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
	public ChargeDetailBean createChargeDetail(UserInfoBean user,PriceBean price,int kind,int order,OperatorBean operator,int money) throws Exception {
		ChargeDetailBean bean = new ChargeDetailBean();
		bean.setCd_userid(user.getU_id());
		bean.setOrderby("order by cd_no desc ");
		List<ChargeDetailBean> list = chargeDetailDao.queryChargeDetailList(bean);
		Long no = 1l ;
		if(list.size() > 0 ) {
			ChargeDetailBean lastCharge = list.get(0);
			no = lastCharge.getCd_no() + 1l ;
			
			if(lastCharge.getCd_brushflag() == ChargeDetailBean.BRUSHFLAG_NO) {
				throw new Exception("当前用户上次充值未刷卡至水表，请刷卡至水表后再试！");
			}
		}
		
		bean.setCd_savingno(no.intValue());
		bean.setCd_no(no);
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
		bean.setCd_paidmoney(BigDecimal.ZERO);
		bean.setCd_othermoney1(BigDecimal.ZERO);
		bean.setCd_othermoney2(BigDecimal.ZERO);
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
	
	@Override
	public int updateChargeDetailToSuccess(String cdid) {
		Long id = Long.parseLong(cdid);
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(id);
		bean.setCd_charge(ChargeDetailBean.CHARGE_SUCCESS);
		bean.setCd_enddate(new Date());
		
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
	public ChargeDetailBean findChargeDetailById(Long id) {
		ChargeDetailBean bean  = new ChargeDetailBean();
		bean.setCd_id(id);
		List<ChargeDetailBean> list = chargeDetailDao.queryChargeDetailList(bean);
		return list.size() > 0 ? list.get(0):null;
	}
	
}
