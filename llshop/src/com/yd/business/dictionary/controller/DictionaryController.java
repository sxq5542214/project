/**
 * 
 */
package com.yd.business.dictionary.controller;

import java.io.IOException;
import java.util.HashMap;
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
import com.yd.util.NumberUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class DictionaryController extends BaseController {
	
	@Autowired
	private IDictionaryService dictionaryService;
	@Resource
	private IConfigCruxService configCruxService;
	
	@RequestMapping("/dictionary/refreshDictionary.do")
	public ModelAndView refreshDictionary(HttpServletRequest request,HttpServletResponse response){
		try{
			dictionaryService.initDictData();
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	@RequestMapping("/dictionary/refreshBaseContext.do")
	public ModelAndView refreshBaseContext(HttpServletRequest request,HttpServletResponse response){
		try{
			BaseContext.refreshParam();
		}catch (Exception e) {
			log.error(e, e);
		}
		
		return null;
	}
	
	
	
	
	
	/**
	 * 字典值管理,查询功能
	 */
	@RequestMapping("/admin/dictionary/dictionaryController/queryAdmimDictionary.do")
	public ModelAndView queryAdmimDictionary(HttpServletRequest request,HttpServletResponse response) throws IOException{
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			DictionaryBean bean = new DictionaryBean();
			String tablename =  request.getParameter("query_dictionary_tablename");
			String attribute =  request.getParameter("query_dictionary_attribute");
			String nowpage = request.getParameter("nowpage");
			if(!StringUtil.isNull(nowpage)){
				bean.setNowpage(NumberUtil.toInt(nowpage));
			}
			if(!StringUtil.isNull(tablename)){
				bean.setTablename(tablename);
			}
			if(!StringUtil.isNull(attribute)){
				bean.setAttribute(attribute);
			}
			PageinationData pd =  dictionaryService.queryDictionaryPage(bean);
			if(!StringUtil.isNull(nowpage)){
				pd.setNowpage(NumberUtil.toInt(nowpage));
			}
			map.put("pd", pd);
		}catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载出错");
		}
		return new ModelAndView("/page/pc/dictionary/iframe_dictionary.jsp", map);
	}
	
	
	
	/**
	 * 字典表删除 	根据id删除ll_dictionary表信息
	 */
	@RequestMapping("/admin/dictionary/dictionaryController/deleteDictionary.do")
	public ModelAndView deleteDictionary(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			DictionaryBean bean = new DictionaryBean();
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			if(!StringUtil.isNull(bean.getId())){
				dictionaryService.deteleDictionary (bean);
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
	 *  增加和编辑字典值表信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/admin/dictionary/dictionaryController/editDictionary.do")
	public ModelAndView editDictionary(HttpServletRequest request,HttpServletResponse response) throws IOException{
		DictionaryBean bean =  new DictionaryBean();
		try {
			String id = request.getParameter("id");
			if(!StringUtil.isNull(id)){
			bean.setId(NumberUtil.toInt(request.getParameter("id")));
			}
			bean.setTablename(request.getParameter("tablename"));
			bean.setAttribute(request.getParameter("attribute"));
			bean.setValue(request.getParameter("value"));
			bean.setDescription(request.getParameter("description"));
			bean.setCommons(request.getParameter("commons"));
			dictionaryService.editDictionary(bean);
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
