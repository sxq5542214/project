package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserConsumeInfoBean;

public interface IUserConsumeInfoDao {

	void createConsumeInfo(UserConsumeInfoBean bean);

	List<UserConsumeInfoBean> queryUserConsumeInfoBean(UserConsumeInfoBean bean);

	UserConsumeInfoBean findUserConsumeInfoBean(String out_trade_code);

	void updateUserConsumeInfoBean(UserConsumeInfoBean bean);

	void updateUserConsumeInfoStatus(int status, String out_trade_no);

}
