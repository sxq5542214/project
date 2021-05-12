package com.yd.business.other.dao;

import java.util.List;

import com.yd.business.other.bean.AdvertisingBean;


/**
 * 商城广告
 * @author zxz
 *
 */
public interface IAdvertisingDao {

	
	public List<AdvertisingBean> queryAdvertisingInfo(AdvertisingBean bean);
	
	
	/**
	 * 分页查询advertising表信息
	 */
	public List<AdvertisingBean> queryAdvertisingInfoPage(AdvertisingBean bean);


	/**
	 * 查询advertising表总数
	 */
	public int advertisingCount(AdvertisingBean bean);
	
	/**
	 * 删除advertising表信息
	 */
	public void deleteAdvertising(AdvertisingBean bean);
	
	/**
	 * 插入advertising表信息
	 */
	public void insertAdvertising(AdvertisingBean bean);
	
	/**
	 * 更新advertising表信息
	 */
	public void updateAdvertising(AdvertisingBean bean);
}
