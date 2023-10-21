package com.yd.business.wechat.dao.impl;

/**
 * 
 */

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.wechat.dao.IWechatOriginalInfoDAO;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;


/**
 * @author zxz
 *
 */
@Repository("wechatOriginalInfoDAO")
public class WechatOriginalInfoDAOImpl extends BaseDao implements IWechatOriginalInfoDAO
{
	private static final String NAMESPACE = "wechatOriginalInfo.";

	@Override
	public List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryWechatOriginalInfo", bean,rowBound(bean));
	}

	@Override
	public int wechatOriginalInfoCount(WechatOriginalInfoBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"wechatOriginalInfoCount",bean);
	}

	@Override
	public void deteleWechatOriginalInfo(WechatOriginalInfoBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deteleWechatOriginalInfo", bean);
	}

	@Override
	public void insertWechatOriginalInfo(WechatOriginalInfoBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertWechatOriginalInfo",bean);
	}

	@Override
	public void updateWechatOriginalInfo(WechatOriginalInfoBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateWechatOriginalInfo",bean);
	}

}
