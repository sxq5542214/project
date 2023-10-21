/**
 * 
 */
package com.yd.business.wechat.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.wechat.bean.WechatMaterialBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class WechatOriginalInfoController extends BaseController {
	
	@Autowired
	private IWechatOriginalInfoService wechatOriginalInfoService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IWechatService wechatService;
	
	
	/**
	 * 微信公众号管理查询功能
	 */
	@RequestMapping("/admin/wechat/wechatOriginalInfoCrontroller/queryAdmimWechatOriginalInfo.do")
	public ModelAndView queryAdmimWechatOriginalInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
			String original_name =  request.getParameter("query_wechatoriginalinfo_original_name");
			String server_domain =  request.getParameter("query_wechatoriginalinfo_server_domain");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(original_name)){
				bean.setOriginal_name(original_name);
			}
			if(!StringUtil.isNull(server_domain)){
				bean.setServer_domain(server_domain);
			}
			PageinationData pd =  wechatOriginalInfoService.queryWechatOriginalInfoPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			ConfigCruxBean cruxBean = new ConfigCruxBean();
			cruxBean.setType(ConfigCruxBean.TYPE_WECHAT_MENU_JUMP_URL);
			cruxBean.setStatus(ConfigCruxBean.STATUS_USE);
			List<ConfigCruxBean> cruxList = configCruxService.queryConfigCruxInfo(cruxBean);
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache("WechatMenuBean");
			map.put("pd", pd);
			map.put("dicMap", dicMap);
			map.put("cruxList", cruxList);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/wechatOriginalInfo/iframe_wechat_original_info.jsp", map);
	}
	
	
	
	/**
	 * 微信公众号管理删除 	根据id删除ll_wechat_original_info表信息
	 */
	@RequestMapping("/admin/wechat/wechatOriginalInfoCrontroller/deleteAdmimWechatOriginalInfo.do")
	public ModelAndView deleteAdminOriginalInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			WechatOriginalInfoBean bean = new WechatOriginalInfoBean();
			bean.setId(Integer.parseInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				wechatOriginalInfoService.deteleWechatOriginalInfo(bean);
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
	 *  增加和编辑微信公众号
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/wechat/wechatOriginalInfoCrontroller/editAdmimWechatOriginalInfo.do")
	public ModelAndView editAdmimWechatOriginalInfo(HttpServletRequest request,HttpServletResponse response) throws IOException{
		WechatOriginalInfoBean bean =  new WechatOriginalInfoBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			}
			bean.setOriginalid(request.getParameter("originalid"));
			bean.setOriginal_name(request.getParameter("original_name"));
			bean.setMethod_name(request.getParameter("method_name"));
			bean.setAppid(request.getParameter("appid"));
			bean.setSecret(request.getParameter("secret"));
			bean.setServer_domain(request.getParameter("server_domain"));
			String  weight = request.getParameter("weight");
			if(!StringUtil.isNull(weight)){
				bean.setWeight(Integer.parseInt(request.getParameter("weight")));
			}
			bean.setAccess_token(request.getParameter("access_token"));
			bean.setExpires_in(request.getParameter("expires_in"));
			bean.setToken(request.getParameter("token"));
			bean.setMch_name(request.getParameter("mch_name"));
			bean.setMch_id(request.getParameter("mch_id"));
			bean.setPay_cert_file_path(request.getParameter("pay_cert_file_path"));
			bean.setServer_url(request.getParameter("server_url"));
			bean.setJsapi_ticket(request.getParameter("jsapi_ticket"));
			bean.setPay_wechat_sign_key(request.getParameter("pay_wechat_sign_key"));

			
			wechatOriginalInfoService.editWechatOriginalInfo(bean);
			if(StringUtil.isNull(bean.getRemark())){
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}
			writeJson(response, bean);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return null;
	}	
	
	
}
