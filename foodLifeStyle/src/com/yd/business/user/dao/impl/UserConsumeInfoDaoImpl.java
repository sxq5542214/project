package com.yd.business.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.user.bean.UserConsumeInfoBean;
import com.yd.business.user.dao.IUserConsumeInfoDao;

@Repository("userConsumeInfoDao")
public class UserConsumeInfoDaoImpl extends BaseDao implements IUserConsumeInfoDao {
	private static final String NAMESPACE = "userConsumeInfo.";
	
	@Override
	public void createConsumeInfo(UserConsumeInfoBean bean){
		sqlSessionTemplate.insert(NAMESPACE+"createConsumeInfo", bean);
	}
	
	@Override
	public List<UserConsumeInfoBean> queryUserConsumeInfoBean(UserConsumeInfoBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryUserConsumeInfoBean", bean);
	}
	
	@Override
	public UserConsumeInfoBean findUserConsumeInfoBean(String out_trade_code){
		return sqlSessionTemplate.selectOne(NAMESPACE+"findUserConsumeInfoBean", out_trade_code);
	}
	
	@Override
	public void updateUserConsumeInfoBean(UserConsumeInfoBean bean){
		sqlSessionTemplate.update(NAMESPACE+"updateUserConsumeInfoBean", bean);
	}

	@Override
	public void updateUserConsumeInfoStatus(int status, String out_trade_code) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("out_trade_code", out_trade_code);
		
		sqlSessionTemplate.update(NAMESPACE+"updateUserConsumeInfoStatus", map);
	}
	
	
}
