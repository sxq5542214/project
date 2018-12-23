package com.yd.business.wechat.controller;

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
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatMaterialRelationBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;

@Controller
public class WechatMsgDeliveryController extends BaseController {

	@Resource
	private IWechatService wechatService;
	
	/**
	 * 分发微信消息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("wechat/delivery/deliveryWechatMaterialInfo.do")
	public ModelAndView deliveryWechatMaterialInfo(HttpServletRequest request,HttpServletResponse response){
		try{
			//获取消息mediaid集合
			String media_ids = request.getParameter("media_ids");
			//获取需要分发的公众号id
			String originalids = request.getParameter("originalids");
			
			String action = request.getParameter("action");
			
			List<WechatMaterialRelationBean> relaBeanList = wechatService.deliveryWechatMaterialInfo(media_ids, originalids,action);
			writeJson(response, relaBeanList);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	/**
	 * 得到微信素材列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("wechat/delivery/showWechatMaterialInfo.do")	
	public ModelAndView showWechatMaterialInfo(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, String[]> param = request.getParameterMap();
			WechatMaterialBean bean = new WechatMaterialBean();
			AutoInvokeGetSetMethod.autoInvoke(param,bean);
			//得到系统所有公众号集合
			List<WechatOriginalInfoBean> originalList = wechatService.queryWechatOriginalInfo(null);
			Object start_time = request.getParameter("start_time");
			Object end_time = request.getParameter("end_time");
			PageinationData pd = wechatService.showWechatMaterialInfoForDelivery(originalList,bean,start_time,end_time);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("pd", pd);
			model.put("originalList", originalList);
			model.put("bean", bean);
			model.put("end_time", end_time);
			model.put("start_time", start_time);
			return new ModelAndView("/page/pc/messageDelivery/message_delivery_mrg.jsp",model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 发送消息给用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("wechat/delivery/sendWechatMaterialToUserAll.do")
	public ModelAndView sendWechatMaterialToUserAll(HttpServletRequest request,HttpServletResponse response){
		try {
			//获取消息mediaid集合
			String media_ids = request.getParameter("media_ids");
			//获取需要分发的公众号id
			String originalids = request.getParameter("originalids");
			wechatService.sendMessageByToUserAll(originalids, media_ids);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 异步获取公众号的素材信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("wechat/delivery/getOriginalWecahtMaterialList.do")
	public ModelAndView getOriginalWecahtMaterialList(HttpServletRequest request,HttpServletResponse response){
		try {
			String original_id = request.getParameter("original_id");
			WechatOriginalInfoBean wechatOriginalInfoBean = wechatService.findWechatOriginalInfoByOriginalid(original_id);
			String nowpage = request.getParameter("nowpage");
			String type = request.getParameter("type");
			WechatMaterialBean materialBean = new WechatMaterialBean();
			materialBean.setOriginalid(wechatOriginalInfoBean.getOriginalid());
			//获取改公众号下的素材信息
			if(WechatMaterialBean.MATERIAL_SUCAITYPE_NEWS.equals(type)){
				materialBean.setNowpage(Integer.valueOf(nowpage));
				PageinationData pd = wechatService.showWechatMaterialInfoForDelivery(null,materialBean,null,null);
				writeJson(response, pd);
			}else{
				materialBean.setSucai_type(type);
				List<WechatMaterialBean> materialList = wechatService.queryWechatMaterial(materialBean);
				writeJson(response, materialList);
			}
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
}
