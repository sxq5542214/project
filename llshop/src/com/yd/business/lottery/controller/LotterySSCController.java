/**
 * 
 */
package com.yd.business.lottery.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.lottery.bean.CqsscInfoBean;
import com.yd.business.lottery.service.ILotterySSCService;

/**
 * @author ice
 *	时时彩controller
 */
@Controller
public class LotterySSCController extends BaseController {
	
	@Autowired
	private ILotterySSCService lotterySSCService;
	
	@RequestMapping("/lottery/displayCQSSCInfo.do")
	public ModelAndView displayCQSSCInfo(HttpServletRequest request,HttpServletResponse response){
		
		List<CqsscInfoBean> list = lotterySSCService.queryAndUpdateCQSSCInfo(null);
		System.out.println(list.size());
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("list", list);
		return new ModelAndView("/page/lottery/cqssc/simple_lottery_infoList.jsp", model);
	}
	
}
