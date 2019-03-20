/**
 * 
 */
package com.yd.business.wechat.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;

/**
 * @author ice
 *
 */
@Controller
public class WechatAjaxController extends BaseController {
	@Resource
	private IWechatService wechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	@RequestMapping("wechat/ajax/queryWechatMaterialInfo.do")
	public ModelAndView queryWechatMaterialInfo(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String sucai_type = request.getParameter("sucai_type");
			List list = wechatService.queryWechatMaterialBySucaiType(sucai_type);
			writeJson(response, list);
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	

	@RequestMapping("**/wechat/ajax/syncWechatMaterial.do")
	public ModelAndView syncWechatMaterial(HttpServletRequest request,HttpServletResponse response){
		
		try{
			
			List<WechatOriginalInfoBean> list = wechatOriginalInfoService.queryWechatOriginalInfo(null);
			
			for(WechatOriginalInfoBean bean : list)
			{
				wechatService.syncWechatMaterial(bean.getOriginalid());
			}
			
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
}
