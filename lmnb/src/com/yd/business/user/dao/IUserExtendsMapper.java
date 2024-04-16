package com.yd.business.user.dao;

import java.util.List;

import com.yd.business.user.bean.UserModelExtendsBean;
import com.yd.iotbusiness.mapper.dao.LmUserModelMapper;

public interface IUserExtendsMapper extends LmUserModelMapper {

	public List<UserModelExtendsBean> queryUserAndMeterByExtend(UserModelExtendsBean bean);
	public Integer countUserAndMeterByExtend(UserModelExtendsBean bean);

}
