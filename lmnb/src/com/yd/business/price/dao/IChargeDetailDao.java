package com.yd.business.price.dao;

import java.util.List;

import com.yd.business.price.bean.ChargeDetailBean;
import com.yd.business.price.bean.PriceBean;

public interface IChargeDetailDao {

	List<ChargeDetailBean> queryChargeDetailList(ChargeDetailBean bean);

	int insertChargeDetail(ChargeDetailBean bean);

	int updateChargeDetail(ChargeDetailBean bean);

	List<ChargeDetailBean> queryChargeListByUserId(Long u_id);

}
