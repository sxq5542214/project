package com.yd.business.other.service;

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.AdvertisingBean;



public interface IAdvertisingService {

	List<AdvertisingBean> queryAdvertisingInfo(String code);

	/**
	 * 后台分页查询Advertising表信息
	 */
	public PageinationData queryAdvertisingInfoPage(AdvertisingBean bean);

	public void  deleteAdvertising(AdvertisingBean bean);
	
	public void editAdvertising(AdvertisingBean bean);
}
