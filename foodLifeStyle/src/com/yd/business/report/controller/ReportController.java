/**
 * 
 */
package com.yd.business.report.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.service.IReportService;

/**
 * @author ice
 *
 */
@Controller 
public class ReportController extends BaseController {
	
	@Resource
	private IReportService reportService;
	
	public static final String PAGE_SIMPLEREPORT_PAGE = "/page/pc/report/report_simple.jsp";
	public static final String PAGE_SIMPLEREPORT_DATA_PAGE = "/page/pc/report/report_data.jsp";
	
	
	@RequestMapping("**/admin/report/toSimpleReportPage.do")
	public ModelAndView toSimpleReportPage(HttpServletRequest request, HttpServletResponse response){
		try {
			
			List<ReportSimpleBean> list = reportService.queryReportSimpleList(null);
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_SIMPLEREPORT_PAGE,model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/report/toSingleReportPage.do")
	public ModelAndView toSingleReportPage(HttpServletRequest request, HttpServletResponse response){
		try {
			String id = request.getParameter("id");
			ReportSimpleBean report = reportService.findReportSimpleById(Integer.parseInt(id));
			
			List<Map<String, Object>> list = reportService.querySingleReportData(report.getData_sql());
			

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			model.put("report", report);

			return new ModelAndView(PAGE_SIMPLEREPORT_DATA_PAGE,model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
}
