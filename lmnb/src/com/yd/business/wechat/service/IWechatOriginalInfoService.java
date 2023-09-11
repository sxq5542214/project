package com.yd.business.wechat.service;

/**
 * 
 */

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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

	WechatOriginalInfoBean findWechatOriginalInfoByServer(String server_domain);

	WechatOriginalInfoBean findWechatOriginalInfoByOriginalid(String originalid);
	
	/**
	 * list查询
	 */
	public List<WechatOriginalInfoBean>  queryWechatOriginalInfo(WechatOriginalInfoBean bean);

	/**
	 * 查询第一条
	 * @param id
	 * @return
	 */
	WechatOriginalInfoBean queryFirstWechatOriginalInfo();


	/**
	 * 通过服务域名换取公众号的原始ID
	 * @param request
	 * @return
	 */
	String getOriginalidByServerDomain(HttpServletRequest request);


	/**
	 * 通过服务域名换取公众号的原始ID
	 * @param request
	 * @return
	 */
	WechatOriginalInfoBean getOriginalInfoByServerDomain(HttpServletRequest request);
}
