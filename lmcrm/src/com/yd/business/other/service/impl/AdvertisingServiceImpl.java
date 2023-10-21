package com.yd.business.other.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.dao.IAdvertisingDao;
import com.yd.business.other.service.IAdvertisingService;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

@Service("advertisingService")
public class AdvertisingServiceImpl extends BaseService implements
IAdvertisingService{
	@Resource
	private IAdvertisingDao advertisingDao;
	
	/**
	 * 通过手机号查询商户客户下可用的商品
	 */
	@Override
	public List<AdvertisingBean> queryAdvertisingInfo(String code){
		AdvertisingBean bean = new AdvertisingBean();
		bean.setCode(code);
		bean.setStatus(AdvertisingBean.STATUS_USE);
		List<AdvertisingBean> list = advertisingDao.queryAdvertisingInfo(bean);
		return list;
	}

	/**
	 * 后台分页查询Advertising表信息
	 */
	@Override
	public PageinationData queryAdvertisingInfoPage(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		PageinationData pd = new PageinationData();
		try{
			List<AdvertisingBean> showList = advertisingDao.queryAdvertisingInfoPage(bean);
		    int count = advertisingDao.advertisingCount(bean);
		    pd.setDataList(showList);
		    pd.setTotalcount(count);
		    pd.calculateTotalPage();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e,e);			
		}
		return pd;
	}

	/**
	 * 删除广告轮播信息
	 */
	@Override
	public void deleteAdvertising(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		advertisingDao.deleteAdvertising(bean);
	}

	/**
	 * 编辑和增加广告轮播信息
	 */
	@Override
	public void editAdvertising(AdvertisingBean bean) {
		// TODO Auto-generated method stub
		try{
			if(StringUtil.isNull(bean.getId())){
				bean.setCreate_time(DateUtil.getNowDateStr());
				advertisingDao.insertAdvertising(bean);
			}else{
				bean.setModify_time(DateUtil.getNowDateStr());
				advertisingDao.updateAdvertising(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);			
		}
	}
	
	
}
