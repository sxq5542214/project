package com.yd.business.wechat.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMenuBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatMenuService;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;

/**
 * 公众号菜单管理
 * @author BoBo
 *
 */
@Controller
public class WechatMenuContorller extends BaseController {

	@Resource
	private IWechatMenuService wechatMenuService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
	/**
	 * 获取微信公众号的菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/wechatmenu/getWechatMenuForShow.do")
	public ModelAndView getWechatMenuForShow(HttpServletRequest request,HttpServletResponse response){
		try {
			String original_id = request.getParameter("original_id");
			WechatOriginalInfoBean original = new WechatOriginalInfoBean();
			original.setOriginalid(original_id);
			WechatMenuBean showBean = wechatMenuService.getWechatMenuList(original);
			writeJson(response, showBean);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	/**
	 * 获取微信公众号的菜单
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/wechatmenu/sendWechatMenuToWeixin.do")
	public ModelAndView sendWechatMenuToWeixin(HttpServletRequest request,HttpServletResponse response){
		try {
			String original_id = request.getParameter("original_id");
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(original_id);
			String result = wechatMenuService.sendWechatMenuToWeixin(original);
			writeJson(response, result);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, e.getMessage());
		}
		return null;
	}
	
	/**
	 * 提交菜单数据(新增或者修改)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/wechatmenu/commitWechatMenu.do")
	public ModelAndView commitWechatMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			WechatMenuBean bean = wechatMenuService.commitWechatMenuBean(jsonData);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	/**
	 * 删除菜单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/wechatmenu/deleteWechatMenu.do")
	public ModelAndView deleteWechatMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			WechatMenuBean bean = new WechatMenuBean();
			bean.setId(Integer.valueOf(id));
			wechatMenuService.deleteWechatMenuBean(bean);
			writeJson(response, "SUSSECC");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
	
	/**
	 * 同步最新的菜单数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/wechatmenu/syncNewWechatMenu.do")
	public ModelAndView syncNewWechatMenu(HttpServletRequest request,HttpServletResponse response){
		try {
			String original_id = request.getParameter("original_id");
			WechatOriginalInfoBean original = wechatOriginalInfoService.findWechatOriginalInfoByOriginalid(original_id);
			wechatMenuService.saveWechatMenuToLoal(original);
			writeJson(response, "SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "失败");
		}
		return null;
	}
	
}
