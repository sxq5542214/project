/**
 * 
 */
package com.yd.business.other.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.dictionary.service.impl.DictionaryServiceImpl;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ConfigAttributeController extends BaseController {
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	
public static final String PAGE_CONFIGATTRIBUTE = "/page/pc/attribute/iframe_config_attribute_mgr.jsp";
	
	@RequestMapping("**/admin/system/queryConfigAttribute.do")
	public ModelAndView querySysConfigAttribute(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			ConfigAttributeBean bean = new ConfigAttributeBean();
			Object name = request.getParameter("query_config_attribute_name");
			Object value = request.getParameter("query_config_attribute_value");
			Object originalid = request.getParameter("originalid");
			Object status = request.getParameter("query_config_attribute_status");
			String nowpage = request.getParameter("nowpage");
			if(StringUtil.isNotNull(nowpage)){
				if(Integer.valueOf(nowpage) == 0){
					bean.setNowpage(1);
				}else{
					bean.setNowpage(Integer.valueOf(nowpage));
				}
				
			}
			if(!StringUtil.isNull(name)){
				bean.setName(name.toString());
			}
			if(!StringUtil.isNull(value)){
				bean.setValue(value.toString());
			}
			if(!StringUtil.isNull(originalid) && Integer.valueOf(originalid.toString())!=-1){
				bean.setOriginalid(originalid.toString());
			}
			if(!StringUtil.isNull(status) && Integer.valueOf(status.toString())!=-1){
				bean.setStatus(Integer.valueOf(status.toString()));
			}
			//得到系统所有公众号集合
			List<WechatOriginalInfoBean> originalList = wechatOriginalInfoService.queryWechatOriginalInfo(null);
			PageinationData pd = configAttributeService.queryConfigAttributeForPage(bean);
			//初始化字典值
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			model.put("pd", pd);
			model.put("bean", bean);
			model.put("originalList", originalList);
			model.put("dicMap", dicMap);
			return new ModelAndView(PAGE_CONFIGATTRIBUTE,model);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}
	
	@RequestMapping("**/admin/system/commitConfigAttributeForAjax.do")
	public ModelAndView commitConfigAttributeForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			Object id = request.getParameter("id");
			String name = request.getParameter("name");
			String code = request.getParameter("code");
			String value = request.getParameter("value");
			String status = request.getParameter("status");
			String originalid = request.getParameter("originalid");
			ConfigAttributeBean bean = new ConfigAttributeBean();
			if(!StringUtil.isNull(id)){
				bean.setId(Integer.valueOf(id.toString()));
			}
			bean.setCode(code);
			bean.setName(name);
			bean.setValue(value);
			bean.setOriginalid(originalid);
			bean.setStatus(Integer.valueOf(status));
			bean = configAttributeService.commitConfigAttributeBean(bean);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "提交失败");
			return null;
		}
		return null;
	}
	@RequestMapping("**/admin/system/deleteConfigAttributeForAjax.do")
	public ModelAndView deleteConfigAttributeForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			ConfigAttributeBean bean = new ConfigAttributeBean();
			Object ids = request.getParameter("id");
			if(!StringUtil.isNull(ids)){
				configAttributeService.deleteConfigAttributeBean(ids.toString());
			}
			bean.setRemark("删除成功！");
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "提交失败");
		}
		return null;
	}
	
}
