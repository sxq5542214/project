package com.yd.business.comment.contorller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.basic.framework.pageination.PageinationData;
import com.yd.business.comment.bean.CommentConfigBean;
import com.yd.business.comment.service.ICommentInfoService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.StringUtil;

/**
 * 留言配置得后台管理
 * @author BoBo
 *
 */
@Controller
public class CommentMgrContorller extends BaseController {

	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IWechatService wechatService;
	@Autowired
	private ICommentInfoService commentInfoService;
	
	public static final String PAGE_CONFIGCOMMENT = "/page/pc/comment/iframe_config_comment_mgr.jsp";
	
	/**
	 * 获取留言配置信息列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/comment/getCommentConfigList.do")
	public ModelAndView getCommentConfigList(HttpServletRequest request,HttpServletResponse response){
		try {
			Map<String, Object> model = new HashMap<String, Object>();
			CommentConfigBean bean = new CommentConfigBean();
			Map<String,String[]> code = request.getParameterMap();
			PageinationData pd = commentInfoService.getCommentConfigListForShow(code,bean);
			//初始化字典值
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			model.put("pd", pd);
			model.put("bean", bean);
			model.put("dicMap", dicMap);
			return new ModelAndView(PAGE_CONFIGCOMMENT,model);
		} catch (Exception e) {
			log.error(e, e);
			return null;
		}
	}
	
	/**
	 * 获取默认得配置信息进行填充
	 */
	@RequestMapping("**/admin/comment/getDefaultCommentConfigForAjax.do")
	public ModelAndView getDefaultCommentConfigForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			CommentConfigBean bean = new CommentConfigBean();
			bean.setActivity_code(CommentConfigBean.COMMENT_DEDAULT_INFO);
			List<CommentConfigBean> configBean = commentInfoService.queryCommentConfigByBean(bean);
			writeJson(response, configBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response, "获取失败");
		}
		return null;
	}
	/**
	 * 创建或者修改留言配置信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/comment/creatOrUpdateCommentConfigBean.do")
	public ModelAndView creatOrUpdateCommentConfigBean(HttpServletRequest request,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			CommentConfigBean bean = commentInfoService.createOrUpdateCommentConfig(jsonData);
			//初始化字典值
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			bean.setDicMap(dicMap);
			writeJson(response, bean);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 删除操作
	 */
	@RequestMapping("**/admin/comment/deleteConfigCommentForAjax.do")
	public ModelAndView deleteConfigCommentForAjax(HttpServletRequest request,HttpServletResponse response){
		try {
			ConfigAttributeBean bean = new ConfigAttributeBean();
			Object ids = request.getParameter("id");
			if(!StringUtil.isNull(ids)){
				commentInfoService.deleteCommentConfigBean(ids.toString());
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
