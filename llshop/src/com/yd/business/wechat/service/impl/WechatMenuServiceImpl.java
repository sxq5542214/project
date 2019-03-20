package com.yd.business.wechat.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.wechat.bean.BaseMessage;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMenuBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.dao.IWechatDao;
import com.yd.business.wechat.service.IWechatMenuService;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.business.wechat.util.WechatConstant;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.HttpUtil;
import com.yd.util.StringUtil;
/**
 * 
 * @author BoBo
 *
 */
@Service("wechatMenuService")
public class WechatMenuServiceImpl extends BaseService implements IWechatMenuService {

	@Resource
	private IWechatService wechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IWechatDao wechatDao;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	protected IWechatOriginalInfoService wechatOriginalInfoService;
	
	@Override
	public WechatMenuBean getWechatMenuList(WechatOriginalInfoBean originalBean) {
		WechatOriginalInfoBean wechatOriginalInfoBean = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(originalBean.getOriginalid());
		WechatMenuBean showBean = new WechatMenuBean();
		showBean.setId(0);
		showBean.setName(wechatOriginalInfoBean.getOriginal_name());
		//本地查询菜单，封装成json发送到微信服务器
		WechatMenuBean queryBean = new WechatMenuBean();
		queryBean.setOriginal_id(wechatOriginalInfoBean.getOriginalid());
		List<WechatMenuBean> menuList = wechatDao.queryWechatMenuBean(queryBean);
		if(menuList.size() > 0){
			//封装成json格式
			List<WechatMenuBean> parentMenu = new ArrayList<WechatMenuBean>();
			Map<Integer,List<WechatMenuBean>> childrenMenu = new HashMap<Integer,List<WechatMenuBean>>();
			for (WechatMenuBean wechatMenuBean : menuList) {
				if(StringUtil.isNull(wechatMenuBean.getParent_id())){
					parentMenu.add(wechatMenuBean);
				}else{
					if(StringUtil.isNull(childrenMenu.get(wechatMenuBean.getParent_id()))){
						List<WechatMenuBean> cMenu = new ArrayList<WechatMenuBean>();
						cMenu.add(wechatMenuBean);
						childrenMenu.put(wechatMenuBean.getParent_id(), cMenu);
					}else{
						childrenMenu.get(wechatMenuBean.getParent_id()).add(wechatMenuBean);
					}
				}
			}
			//将子菜单添加到父级菜单上
			for (WechatMenuBean wechatMenuBean : parentMenu) {
				if(StringUtil.isNull(wechatMenuBean.getChildren_button())){
					if(!StringUtil.isNull(childrenMenu.get(wechatMenuBean.getId()))){
						List<WechatMenuBean> childernList = new ArrayList<WechatMenuBean>();
						childernList.addAll(childrenMenu.get(wechatMenuBean.getId()));
						wechatMenuBean.setChildren_button(childernList);
					}
				}else{
					wechatMenuBean.getChildren_button().addAll(childrenMenu.get(wechatMenuBean.getId()));
				}
			}
			showBean.setChildren_button(parentMenu);
		}
		return showBean;
	}
	
	/**
	 * 根据公众号id发送菜单到各个公众号
	 * @param wechatOriginalInfoBean
	 * @throws Exception 
	 */
	@Override
	public String sendWechatMenuToWeixin(WechatOriginalInfoBean wechatOriginalInfoBean) throws Exception{
		WechatMenuBean button = new WechatMenuBean();
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		String access_token = wechatOriginalInfoBean.getAccess_token();
		//获取创建菜单的链接
		String url = server_url + WechatConstant.CREATE_MENU + "?access_token=" + access_token;
		//本地查询菜单，封装成json发送到微信服务器
		WechatMenuBean queryBean = new WechatMenuBean();
		queryBean.setOriginal_id(wechatOriginalInfoBean.getOriginalid());
		List<WechatMenuBean> menuList = wechatDao.queryWechatMenuBean(queryBean);
		stitchingWechatMenuJumpUrl(menuList,wechatOriginalInfoBean);
		//整理菜单中的跳转链接（如果有）
		if(menuList.size() > 0){
			//封装成json格式
			List<WechatMenuBean> parentMenu = new ArrayList<WechatMenuBean>();
			Map<Integer,List<WechatMenuBean>> childrenMenu = new HashMap<Integer,List<WechatMenuBean>>();
			for (WechatMenuBean wechatMenuBean : menuList) {
				if(StringUtil.isNull(wechatMenuBean.getParent_id())){
					parentMenu.add(wechatMenuBean);
				}else{
					if(StringUtil.isNull(childrenMenu.get(wechatMenuBean.getParent_id()))){
						List<WechatMenuBean> cMenu = new ArrayList<WechatMenuBean>();
						cMenu.add(wechatMenuBean);
						childrenMenu.put(wechatMenuBean.getParent_id(), cMenu);
					}else{
						childrenMenu.get(wechatMenuBean.getParent_id()).add(wechatMenuBean);
					}
				}
			}
			//将子菜单添加到父级菜单上
			for (WechatMenuBean wechatMenuBean : parentMenu) {
				if(StringUtil.isNull(wechatMenuBean.getChildren_button())){
					if(!StringUtil.isNull(childrenMenu.get(wechatMenuBean.getId()))){
						List<WechatMenuBean> childernList = new ArrayList<WechatMenuBean>();
						childernList.addAll(childrenMenu.get(wechatMenuBean.getId()));
						wechatMenuBean.setChildren_button(childernList);
					}
				}else{
					wechatMenuBean.getChildren_button().addAll(childrenMenu.get(wechatMenuBean.getId()));
				}
			}
			button.setButton(parentMenu);
			String requestJson = new JSONObject(button).toString();
			requestJson = requestJson.replaceAll("\"children_button\":", "\"sub_button\":");
			requestJson = requestJson.replaceAll("\"menu_key\":", "\"key\":");
			String returnJson = HttpUtil.post(url, requestJson);
			JSONObject result = new JSONObject(returnJson);
			//成功后更新本地菜单
			if(result.getInt("errcode") == BaseMessage.ERROR_CODE_SUCCESS){
				this.saveWechatMenuToLoal(wechatOriginalInfoBean);
			}
			return (String) new JSONObject(returnJson).get("errmsg");
		}else{
			return "请先同步菜单";
		}
	}
	
	/**
	 * 拼接跳转链接（通过菜单bean里面的url_code，拼接url）
	 * https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx7b263199a1d6c688&redirect_uri=  微信请求，appid根据公众号取
	 * http://www.51bwc.com/llshop/   服务器域名，根据公众号信息取
	 * wechat/user/showActivityList.do&response_type=code&scope=snsapi_base&state=1#wechat_redirect  跳转链接，根据url_code取值
	 * @param menuList
	 */
	private void stitchingWechatMenuJumpUrl(List<WechatMenuBean> menuList,WechatOriginalInfoBean original){
		String appid = original.getAppid();
		String hostPath = original.getServer_url();
		String wechatMenuJumpServerUrl = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHAT_MENU_JUMP_SERVER);
		//获取微信系统菜单跳转链接
		wechatMenuJumpServerUrl = wechatMenuJumpServerUrl.replace("#appid#", appid);
		for (WechatMenuBean wechatMenuBean : menuList) {
			String url_code = wechatMenuBean.getUrl_code();
			if(!StringUtil.isNull(url_code)){
				//通过code获取对应的url
				ConfigCruxBean cruxBean = configCruxService.getAttributeByTypeAndKey(ConfigCruxBean.TYPE_WECHAT_MENU_JUMP_URL, url_code);
				String url = cruxBean.getValue();
				String jump_url = wechatMenuJumpServerUrl.replaceAll("#jump_url#", hostPath + url);
				wechatMenuBean.setUrl(jump_url);
			}
		}
	}
	
	/**
	 * 将微信公众号的菜单同步到本地服务器
	 * @param wechatOriginalInfoBean
	 * @throws Exception 
	 */
	@Override
	public void saveWechatMenuToLoal(WechatOriginalInfoBean wechatOriginalInfoBean) throws Exception{
		String server_url = configAttributeService.getValueByCode(AttributeConstant.CODE_WECHATSERVERURL);
		//删除菜单信息
		WechatMenuBean deleteBean = new WechatMenuBean();
		deleteBean.setOriginal_id(wechatOriginalInfoBean.getOriginalid());
		wechatDao.deleteWechatMenuBean(deleteBean);
		// 获取每个公众号的菜单信息
		String access_token = wechatOriginalInfoBean.getAccess_token();
		String url = server_url + WechatConstant.GET_MENU + "?access_token=" + access_token;
//		try {
			String menuJson = HttpUtil.get(url);
			menuJson = menuJson.replaceAll("\"key\":", "\"menu_key\":");
			JSONObject jsonData = new JSONObject(menuJson);
			JSONArray menuList = new JSONObject(jsonData.get("menu").toString()).getJSONArray("button");
			for (int i=0;i<menuList.length();i++) {
				JSONObject menu = new JSONObject(menuList.get(i).toString());
				WechatMenuBean menuBean = new WechatMenuBean();
				//将json串转换为json对象
				AutoInvokeGetSetMethod.autoInvoke(menu,menuBean);
				menuBean.setOriginal_id(wechatOriginalInfoBean.getOriginalid());
				menuBean.setLast_sync_time(DateUtil.getNowDateStrSSS());
				getSucaiNameToMenu(menuBean);
				wechatDao.insertWechatMenuBean(menuBean);
				List<WechatMenuBean> childrenBean = new ArrayList<WechatMenuBean>();
				menuBean.setChildren_button(childrenBean);
				if(menu.getJSONArray("sub_button").length() > 0){
					for (int j=0;j<menu.getJSONArray("sub_button").length();j++) {
						JSONObject sub_menu = new JSONObject(menu.getJSONArray("sub_button").get(j).toString());
						WechatMenuBean sub_menuBean = new WechatMenuBean();
						//将json串转换为json对象
						AutoInvokeGetSetMethod.autoInvoke(sub_menu,sub_menuBean);
						sub_menuBean.setParent_id(menuBean.getId());
						sub_menuBean.setOriginal_id(wechatOriginalInfoBean.getOriginalid());
						sub_menuBean.setLast_sync_time(DateUtil.getNowDateStrSSS());
						getSucaiNameToMenu(sub_menuBean);
						wechatDao.insertWechatMenuBean(sub_menuBean);
					}
				}
			}
//		} catch (Exception e) {
//			log.error(e, e);
//		}
	}
	
	/**
	 * 为菜单添加media_name
	 * @param menuBean
	 */
	private void getSucaiNameToMenu(WechatMenuBean menuBean){
		if(!StringUtil.isNull(menuBean.getMedia_id())){
			WechatMaterialBean queryBean = new WechatMaterialBean();
			queryBean.setSucai_media_id(menuBean.getMedia_id());
			queryBean = wechatDao.findWechatMaterial(queryBean);
			if(!StringUtil.isNull(queryBean.getSucai_title())){
				menuBean.setMedia_code(queryBean.getSucai_title());
			}else if(!StringUtil.isNull(queryBean.getSucai_name())){
				menuBean.setMedia_code(queryBean.getSucai_name());
			}else{
				queryBean = new WechatMaterialBean();
				queryBean.setParent_media_id(menuBean.getMedia_id());
				List<WechatMaterialBean> beanList = wechatDao.queryWechatMaterial(queryBean);
				if(beanList.size() > 0){
					menuBean.setMedia_code(beanList.get(0).getSucai_title());
				}
			}
		}
	}
	
	@Override
	public WechatMenuBean commitWechatMenuBean(String jsonDataStr) {
		WechatMenuBean systemMenuBean = new WechatMenuBean();
		//将json串转换为json对象
		JSONObject jsonData = new JSONObject(jsonDataStr);
		try {
			AutoInvokeGetSetMethod.autoInvoke(jsonData,systemMenuBean);
			systemMenuBean = wechatDao.createOrUpdateWechatMenuBean(systemMenuBean);
		} catch (Exception e) {
			log.error(e, e);
		}
		return systemMenuBean;
	}

	@Override
	public void deleteWechatMenuBean(WechatMenuBean bean) {
		if(!StringUtil.isNull(bean.getId())){
			wechatDao.deleteWechatMenuBean(bean);
		}
	}

	
}
