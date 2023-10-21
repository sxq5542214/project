package com.yd.business.wechat.dao;

import java.util.List;

import com.yd.business.wechat.bean.WechatOriginalInfoBean;


public interface IWechatOriginalInfoDAO {

	/**
	 * 查询ll_WechatOriginalInfo表信息
	 */
	public List<WechatOriginalInfoBean> queryWechatOriginalInfo(WechatOriginalInfoBean bean);
	
	
	/**
	 * 查询ll_WechatOriginalInfo表总数
	 */
	public int wechatOriginalInfoCount(WechatOriginalInfoBean bean);
	
	
	/**
	 * 删除ll_WechatOriginalInfo表信息
	 */
	public void deteleWechatOriginalInfo(WechatOriginalInfoBean bean);

	
	/**
	 * 插入ll_WechatOriginalInfo表信息
	 */
	public void insertWechatOriginalInfo(WechatOriginalInfoBean bean);
	
	/**
	 * 更新ll_WechatOriginalInfo表信息
	 */
	public void updateWechatOriginalInfo(WechatOriginalInfoBean bean);
	
}
