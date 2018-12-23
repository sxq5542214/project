package com.yd.business.wechat.service;

/**
 * 
 */

import java.util.List;

import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;


/**
 * @author zxz		
 *
 */
public interface IWechatOriginalInfoService
{
	
	/**
	 * 分页查询
	 */
	public PageinationData queryWechatOriginalInfoPage(WechatOriginalInfoBean bean);


	/**
	 * 删除WechatOriginalInfo表信息
	 */
	public void deteleWechatOriginalInfo(WechatOriginalInfoBean bean);
	
	/**
	 * 更改WechatOriginalInfo表信息
	 */
	public void editWechatOriginalInfo(WechatOriginalInfoBean bean);

	
	/**
	 * list查询
	 */
	public List<WechatOriginalInfoBean>  queryWechatOriginalInfo(WechatOriginalInfoBean bean);
}
