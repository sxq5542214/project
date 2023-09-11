package com.yd.business.wechat.service;

import java.io.IOException;
import java.util.List;

import com.yd.business.wechat.bean.WechatMenuBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;

/**
 * 微信菜单处理
 * @author BoBo
 *
 */
public interface IWechatMenuService {

	/**
	 * 从微信服务器取出菜单信息
	 * @return
	 */
	WechatMenuBean getWechatMenuList(WechatOriginalInfoBean original);
	
	/**
	 * 发送菜单到公众号
	 * @param wechatOriginalInfoBean
	 */
	String sendWechatMenuToWeixin(WechatOriginalInfoBean wechatOriginalInfoBean)  throws Exception;
	
	/**
	 * 同步公众号菜单，并保存至本地
	 * @param wechatOriginalInfoBean
	 */
	void saveWechatMenuToLoal(WechatOriginalInfoBean wechatOriginalInfoBean) throws Exception;
	
	/**
	 * 提交菜单数据
	 * @param jsonDataStr
	 * @return
	 */
	WechatMenuBean commitWechatMenuBean(String jsonDataStr);
	
	/**
	 * 删除菜单数据
	 * @param bean
	 */
	void deleteWechatMenuBean(WechatMenuBean bean);
	
}
