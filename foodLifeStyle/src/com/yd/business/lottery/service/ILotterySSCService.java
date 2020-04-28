package com.yd.business.lottery.service;

import java.util.List;

import com.yd.business.lottery.bean.CqsscInfoBean;

public interface ILotterySSCService {

	List<CqsscInfoBean> queryAndUpdateCQSSCInfo(String date);

}
