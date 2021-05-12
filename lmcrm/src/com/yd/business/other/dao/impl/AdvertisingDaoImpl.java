package com.yd.business.other.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yd.basic.framework.persistence.BaseDao;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.dao.IAdvertisingDao;

/**
 * 
 * @author zxz
 *
 */
@Repository("advertisingDao")
public class AdvertisingDaoImpl extends BaseDao implements IAdvertisingDao {

	private static final String NAMESPACE = "advertising.";
	@Override
	public List<AdvertisingBean> queryAdvertisingInfo(AdvertisingBean bean){
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAdvertisingInfo", bean);
	}
	
	/**
	 * 分页查询advertising表信息
	 */
	@Override
	public List<AdvertisingBean> queryAdvertisingInfoPage(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(NAMESPACE+"queryAdvertisingInfoPage", bean,rowBound(bean));
	}

	/**
	 * 查询advertising表总数
	 */
	@Override
	public int advertisingCount(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(NAMESPACE+"advertisingCount",bean);
	}

	
	/**
	 * 删除advertising表信息
	 */
	@Override
	public void deleteAdvertising(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete(NAMESPACE+"deleteAdvertising", bean);
	}
	
	/**
	 * 插入advertising表信息
	 */
	@Override
	public void insertAdvertising(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert(NAMESPACE+"insertAdvertising",bean);
	}

	/**
	 * 更新adbertising表信息s
	 */
	@Override
	public void updateAdvertising(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update(NAMESPACE+"updateAdvertising",bean);
	}
}
