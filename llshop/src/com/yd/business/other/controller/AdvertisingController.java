package com.yd.business.other.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.other.bean.AdvertisingBean;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IAdvertisingService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;
/**
 * @author zxz
 *
 */
@Controller
public class AdvertisingController extends BaseController {
	@Resource
	private IAdvertisingService advertisingService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IConfigCruxService configCruxService;
	
	/**
	 * 查询广告图片信息，和链接
	 */
	@RequestMapping("**/other/queryAdvertisingInfo.do")
	public ModelAndView queryAdvertisingInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try{
			String code = request.getParameter("code");
			List<AdvertisingBean> list = advertisingService.queryAdvertisingInfo(code);
			
			if(list == null || list.size() != 0)
			{
				writeJson(response, list);
			}
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
	
	
	/**
	 * 广告后台管理,查询功能
	 */
	@RequestMapping("/admin/other/advertisingController/queryAdvertisingAdministration.do")
	public ModelAndView queryAdvertisingAdministration(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			
			AdvertisingBean bean = new AdvertisingBean();
			String code =  request.getParameter("query_advertising_code");
			String picture_link =  request.getParameter("query_advertising_picture_link");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(code)){
				bean.setCode(code);
			}
			if(!StringUtil.isNull(picture_link)){
				bean.setPicture_link(picture_link);
			}
			PageinationData pd =  advertisingService.queryAdvertisingInfoPage(bean);
			//获取url地址
			ConfigAttributeBean attrBean = configAttributeService.getAttributeByCode(AttributeConstant.CODE_IMAGESERVERURL_POST);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("attrBean", attrBean);
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/advertising/iframe_advertising.jsp", map);
	}
	
	
	
	
	
	
	/**
	 * 轮播信息内容删除 	根据id删除advertising表信息
	 */
	@RequestMapping("/admin/other/advertisingController/deleteAdvertising.do")
	public ModelAndView deleteAdvertising(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			AdvertisingBean bean = new AdvertisingBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				advertisingService.deleteAdvertising(bean);
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_DELETE_ERROR)) ;
			}
			
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}
	
	
	/**
	 *  增加和编辑轮播图片信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/other/advertisingController/editAdvertising.do")
	public ModelAndView editAdvertising(HttpServletRequest request,HttpServletResponse response) throws IOException{
		AdvertisingBean bean =  new AdvertisingBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			}
			bean.setCode(request.getParameter("code"));
			bean.setType(request.getParameter("type"));
			bean.setPicture(request.getParameter("picture"));
			bean.setPicture_link(request.getParameter("picture_link"));
			bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			bean.setSeq(NumberUtil.toInt(request.getParameter("seq")));
			advertisingService.editAdvertising(bean);
			bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
}
