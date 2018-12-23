/**
 * 
 */
package com.yd.business.lottery.controller;

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
import com.yd.business.lottery.bean.ExpertBean;
import com.yd.business.lottery.bean.ExpertResultAndFlowBean;
import com.yd.business.lottery.bean.ExpertResultBean;
import com.yd.business.lottery.service.ILotterySportService;
import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 * 体彩开奖控制类
 */
@Controller
public class LotterySportController extends BaseController {
	@Resource
	private ILotterySportService lotterySportService;
	
	@RequestMapping("/lottery/displayExpertResultInfo.do")
	public ModelAndView displayExpertResultInfo(HttpServletRequest request,HttpServletResponse response){

		try {
			ExpertResultBean bean = new ExpertResultBean();
			Map<String, String> map = getRequestParamsMap(request);
			AutoInvokeGetSetMethod.autoInvoke( map, bean);

			List<ExpertResultAndFlowBean> list = lotterySportService.queryExpertResultInfo(bean);
			List<ExpertBean> expertList = lotterySportService.queryExpertInfo(null);
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			model.put("expertList", expertList);

			return new ModelAndView("/page/lottery/sportLottery/lottery_result_write.jsp", model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("/lottery/createExpertResultInfo.do")
	public ModelAndView createExpertResultInfo(HttpServletRequest request,HttpServletResponse response){
		try {
			ExpertResultBean bean = new ExpertResultBean();
			Map<String, String> map = getRequestParamsMap(request);
			AutoInvokeGetSetMethod.autoInvoke( map, bean);
			
			lotterySportService.handleExpertResult(bean);
			
			
		} catch (Exception e) {
			log.error(e,e);
		}
		
		return new ModelAndView("redirect:/lottery/displayExpertResultInfo.do", null);
	}
	
	@RequestMapping("/lottery/calcCurrentJoinMoney.do")
	public ModelAndView calcCurrentJoinMoney(HttpServletRequest request,HttpServletResponse response){
		try {
			
			int expertId = Integer.parseInt(request.getParameter("expertid"));
			int odds = Integer.parseInt(request.getParameter("odds"));
			
			Integer currentJoinMoney = lotterySportService.calcCurrentJoinMoney(expertId,odds);
			
			writeJson(response, currentJoinMoney);
			
		} catch (Exception e) {
			log.error(e,e);
		}
		
		
		return null;
	}
	
}
