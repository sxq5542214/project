package com.yd.business.activity.controller;

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
import com.yd.business.activity.bean.ActicityLimitParamBean;
import com.yd.business.activity.bean.ActivityConfigBean;
import com.yd.business.activity.bean.ActivityConfigShowBean;
import com.yd.business.activity.bean.ActivityOriginalRelationBean;
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.bean.ActivityProductBean;
import com.yd.business.activity.bean.ActivityRule;
import com.yd.business.activity.service.IActivitConfigService;
import com.yd.business.activity.service.IActivityPrizeService;
import com.yd.business.activity.service.IActivityService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;
import com.yd.business.other.bean.ConfigAttributeBean;
import com.yd.business.other.bean.ConfigCruxBean;
import com.yd.business.other.constant.AttributeConstant;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.wechat.bean.WechatOriginalInfoBean;
import com.yd.business.wechat.service.IWechatOriginalInfoService;
import com.yd.business.wechat.service.IWechatService;

/**
 * 活动配置的管理控制
 * @author BoBo
 *
 */
@Controller
public class ActivityManagerController extends BaseController {

	@Resource
	private IActivitConfigService activityConfigService;
	@Resource
	private IActivityService activityService;
	@Resource
	private IActivityPrizeService activityPrizeService;
	@Resource
	private IWechatService wechatService;
	@Resource
	private IDictionaryService dictionaryService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IConfigCruxService configCruxService;
	@Resource
	private IWechatOriginalInfoService wechatOriginalInfoService;
	public static final String PAGE_ACTIVITY_LIST_QUERY = "/page/pc/activity/iframe_config_activity_mgr.jsp";
	
	/**
	 * 展现活动配置的列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/queryActivityConfigList.do")
	public ModelAndView queryActivityConfigList(HttpServletRequest request ,HttpServletResponse response){
		try {
			Map<String,Object> model = new HashMap<String,Object>();
			ActivityConfigBean bean = new ActivityConfigBean();
			List<ActivityConfigBean> confList = activityConfigService.queryActivityConfigByActivity(bean);
			//得到系统所有公众号集合
			List<WechatOriginalInfoBean> originalList = wechatOriginalInfoService.queryWechatOriginalInfo(null);
			//初始化字典值
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			Map<String, List<DictionaryBean>>  paramDicMap = dictionaryService.getTableAttributuByDictionaryCache("ActicityLimitParamBean");
			Map<String, List<DictionaryBean>>  prizeDicMap = dictionaryService.getTableAttributuByDictionaryCache("ActivityProductBean");
			//获取图片服务器地址
			ConfigAttributeBean attrBean = configAttributeService.getAttributeByCode(AttributeConstant.CODE_IMAGESERVERURL_POST);
			List<ActivityPrize> prizeList = activityPrizeService.queryActivityPrizeByBean(null);
			ConfigCruxBean cruxBean = new ConfigCruxBean();
			cruxBean.setType(ConfigCruxBean.TYPE_ACTIVITY_JUMP_URL);
			cruxBean.setStatus(ConfigCruxBean.STATUS_USE);
			List<ConfigCruxBean> cruxList = configCruxService.queryConfigCruxInfo(cruxBean);
			cruxBean.setType(ConfigCruxBean.TYPE_ACTIVITYIMG_JUMP_URL);
			List<ConfigCruxBean> cruxImgList = configCruxService.queryConfigCruxInfo(cruxBean);
			
			model.put("cruxList", cruxList);
			model.put("cruxImgList", cruxImgList);
			model.put("confList", confList);
			model.put("dicMap", dicMap);
			model.put("paramDicMap", paramDicMap);
			model.put("prizeDicMap", prizeDicMap);
			model.put("prizeList", prizeList);
			model.put("originalList", originalList);
			model.put("img_server_url", attrBean.getValue());
			model.put("bean", bean);
			return new ModelAndView(PAGE_ACTIVITY_LIST_QUERY,model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	/**
	 * 获取活动配置的详细信息，用于修改和展现，（展现为树形结构，包括规则和奖品等。。。）
	 * @return
	 */
	@RequestMapping("**/admin/activity/getDetailActivityConfigInfoForAjax.do")
	public ModelAndView getDetailActivityConfigInfoForAjax(HttpServletRequest request ,HttpServletResponse response){
		try {
			//获取id，
			Object id = request.getParameter("id");
			ActivityConfigShowBean showBean = activityConfigService.getActivityConfigForMrg(id);
			writeJson(response,showBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
	/**
	 * 提交活动限制
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/commitActivityParam.do")
	public ModelAndView commitActivityParam(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActicityLimitParamBean paramBean = activityConfigService.commitActivityLimitParamBeanForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	/**
	 * 提交活动限制
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/deleteActivityParam.do")
	public ModelAndView deleteActivityParam(HttpServletRequest request ,HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			activityConfigService.deleteActivityLimitParamBeanByIds(ids);
			writeJson(response,"SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
	/**
	 * 提交活动规则
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/commitActivityRule.do")
	public ModelAndView commitActivityRule(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActivityRule paramBean = activityConfigService.commitActivityRuleForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	/**
	 * 提交活动规则
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/deleteActivityRule.do")
	public ModelAndView deleteActivityRule(HttpServletRequest request ,HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			activityConfigService.deleteActivityRuleByIds(ids);
			writeJson(response,"SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	/**
	 * 提交活动关联奖品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/commitActivityPrize.do")
	public ModelAndView commitActivityPrize(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActivityProductBean paramBean = activityConfigService.commitActivityPrizeForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	/**
	 * 提交活动关联奖品
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/deleteActivityPrize.do")
	public ModelAndView deleteActivityPrize(HttpServletRequest request ,HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			activityConfigService.deleteActivityPrizeByIds(ids);
			writeJson(response,"SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
	/**
	 * 提交活动关联公众号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/commitActivityOringinal.do")
	public ModelAndView commitActivityOringinal(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActivityOriginalRelationBean paramBean = activityConfigService.commitActivityOriginalForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	/**
	 * 提交活动关联公众号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/deleteActivityOringinal.do")
	public ModelAndView deleteActivityOringinal(HttpServletRequest request ,HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			activityConfigService.deleteActivityOriginalByIds(ids);
			writeJson(response,"SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
	/**
	 * 提交活动关联公众号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/activity/commitActivityConfig.do")
	public ModelAndView commitActivityConfig(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActivityConfigBean paramBean = activityConfigService.commitActivityConfigForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
}
