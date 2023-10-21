/**
 * 
 */
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
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ConfigCruxAjaxController extends BaseController {
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IDictionaryService dictionaryService;
	
	@RequestMapping("other/cinfigcrux/ajax/queryConfigCrux.do")
	public ModelAndView queryConfigCrux(HttpServletRequest request,HttpServletResponse response){
		try {
			
			ConfigCruxBean bean = new ConfigCruxBean();
			AutoInvokeGetSetMethod.autoInvoke(getRequestParamsMap(request), bean);
			bean.setStatus(ConfigCruxBean.STATUS_USE);
			
			List<ConfigCruxBean> list =  configCruxService.queryConfigCruxInfo(bean );
			
			writeJson(response, list);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 发送消息内容模版查询
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/other/configCruxAjaxController/queryAdminConfigCrux.do")
	public ModelAndView queryAdminConfigCrux(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ConfigCruxBean bean = new ConfigCruxBean();
			String name =  request.getParameter("query_config_crux_name");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(name)){
				bean.setName(name);
			}
			PageinationData pd =  configCruxService.queryConfigCruxInfoPage(bean);
			List<DictionaryBean> dictionarylist = dictionaryService.getValueByTablenameAndAttribute(ConfigCruxBean.DICTIONARY_TABLENAME_CONFIGCRUXBEAN,ConfigCruxBean.DICTIONARY_ATTRBUTE_TYPE);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("dictionarylist", dictionarylist);
			map.put("pd", pd);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/configCrux/iframe_config_crux.jsp", map);
	}
	
	
	
	
	/**
	 * 发送消息内容删除
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/other/configCruxAjaxController/deleteSendMessage.do")
	public ModelAndView deleteSendMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			ConfigCruxBean bean = new ConfigCruxBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				configCruxService.deleteSendMessage(bean);
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
	 *  增加和编辑短信内容
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/other/configCruxAjaxController/editSendMessage.do")
	public ModelAndView editSendMessage(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		ConfigCruxBean bean =  new ConfigCruxBean();
		try {
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			bean.setName(request.getParameter("name"));
			bean.setCode(request.getParameter("code"));
			bean.setType(request.getParameter("type"));
			bean.setKey(request.getParameter("key"));
			bean.setStatus(NumberUtil.toInt(request.getParameter("status")));
			bean.setValue(request.getParameter("value"));
			boolean judgeRepeat =configCruxService.editSendMessage(bean);
			if(judgeRepeat){							//判断是否重复
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_OPERATION_SUCCESS)) ;
			}else{
				bean.setRemark(configCruxService.getValueByTypeAndKey(ConfigCruxBean.CONFIG_CRUX_TYPE_POP_NEWS,ConfigCruxBean.CONFIG_CRUX_KEY_DATA_REPEAT)) ;
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
