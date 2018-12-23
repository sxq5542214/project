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
import com.yd.business.activity.bean.ActivityPrize;
import com.yd.business.activity.service.IActivityPrizeService;
import com.yd.business.dictionary.bean.DictionaryBean;
import com.yd.business.dictionary.service.IDictionaryService;

/**
 * 系统奖品管理
 * @author BoBo
 *
 */
@Controller
public class ActivityPrizeManagerController extends BaseController {

	@Resource
	private IActivityPrizeService activityPrizeService;
	@Resource
	private IDictionaryService dictionaryService;
	
	public static final String PAGE_ACTIVITY_PRIZE_LIST_QUERY = "/page/pc/prize/iframe_config_prize_mgr.jsp";
	
	/**
	 * 获得奖品管理的相关信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/prize/getPrizeForMgr.do")
	public ModelAndView getPrizeForMgr(HttpServletRequest request ,HttpServletResponse response){
		try {
			Map<String,Object> model = new HashMap<String,Object>();
			//获得奖品列表（所有，此处不需要通过程序实现分页，因为数据量不大）
			ActivityPrize bean = new ActivityPrize();
			List<ActivityPrize> prizeList = activityPrizeService.queryActivityPrizeByBean(bean);
			Map<String, List<DictionaryBean>>  dicMap = dictionaryService.getTableAttributuByDictionaryCache(bean.getClass().getSimpleName());
			model.put("dicMap", dicMap);
			model.put("prizeList", prizeList);
			return new ModelAndView(PAGE_ACTIVITY_PRIZE_LIST_QUERY,model);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 * 删除奖品信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/prize/deleteActivityPrize.do")
	public ModelAndView deleteActivityPrize(HttpServletRequest request ,HttpServletResponse response){
		try {
			String ids = request.getParameter("ids");
			activityPrizeService.deleteActivityPrizeByIds(ids);
			writeJson(response,"SUCCESS");
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,e.getMessage());
		}
		return null;
	}
	
	/**
	 * 提交奖品信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/prize/commitActivityPrize.do")
	public ModelAndView commitActivityConfig(HttpServletRequest request ,HttpServletResponse response){
		try {
			String jsonData = request.getParameter("jsonData");
			ActivityPrize paramBean = activityPrizeService.commitActivityPrizeForJson(jsonData);
			writeJson(response,paramBean);
		} catch (Exception e) {
			log.error(e, e);
			writeJson(response,"失败");
		}
		return null;
	}
	
}
