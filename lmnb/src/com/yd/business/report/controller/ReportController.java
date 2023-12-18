/**
 * 
 */
package com.yd.business.report.controller;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.report.bean.ReportSimpleBean;
import com.yd.business.report.service.IReportService;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller 
public class ReportController extends BaseController {
	
	@Resource
	private IReportService reportService;
	
	public static final String PAGE_SIMPLEREPORT_PAGE = "/page/frame/report/report_simple.jsp";
	public static final String PAGE_SIMPLEREPORT_DATA_PAGE = "/page/frame/report/report_data.jsp";
	
	
	@RequestMapping("**/admin/report/toSimpleReportPage.do")
	public ModelAndView toSimpleReportPage(HttpServletRequest request, HttpServletResponse response){
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			//根据登录用户的报表权限查询报表清单
			List<ReportSimpleBean> list = reportService.queryReportSimpleListByAdminRole(op.getId());
			
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("list", list);
			
			return new ModelAndView(PAGE_SIMPLEREPORT_PAGE,model);
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/report/ajaxQuerySimpleReportList.do")
	public ModelAndView ajaxQuerySimpleReportList(HttpServletRequest request, HttpServletResponse response){
		IOTWebDataBean result = new IOTWebDataBean();
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			//根据登录用户的报表权限查询报表清单
			List<ReportSimpleBean> list = reportService.queryReportSimpleListByAdminRole(op.getId());
			
			result.setData(list);
			
			
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result);
		return null;
	}

	@RequestMapping("**/admin/report/ajaxSimpleReportDataView.do")
	public ModelAndView ajaxSimpleReportDataView(HttpServletRequest request, HttpServletResponse response){
		IOTWebDataBean result = new IOTWebDataBean();
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			

			String code = request.getParameter("code");
			String id = request.getParameter("report_id");
			//根据登录用户的报表权限查询报表清单
			ReportSimpleBean report = reportService.findReportSimpleById(Integer.parseInt(id));
			List<Map<String, Object>> list = reportService.querySingleReportData(report.getData_sql());
			
			result.setData(list);
			
			
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result);
		return null;
	}
	@RequestMapping("**/admin/report/ajaxSimpleReportTableColumns.do")
	public ModelAndView ajaxSimpleReportTableColumns(HttpServletRequest request, HttpServletResponse response){
		IOTWebDataBean result = new IOTWebDataBean();
		try {
			LmOperatorModel op = getCurrentLoginOperator();

			String code = request.getParameter("code");
			String id = request.getParameter("report_id");
			//根据登录用户的报表权限查询报表清单
			ReportSimpleBean report = reportService.findReportSimpleById(Integer.parseInt(id));
			String columnCodes = report.getColumn_codes();
			String columnNames = report.getColumn_names();
			List<ReportSimpleBean> dataList = new ArrayList<>();
			int num = columnNames.split(",").length ;
			for(int i = 0 ; i < num ; i++) {
				ReportSimpleBean bean = new ReportSimpleBean();
				String name = columnNames.split(",")[i];
				String cod = columnCodes.split(",")[i];
				bean.setName(name);
				bean.setCode(cod);
				dataList.add(bean);
			}
			
			result.setData(dataList);
			
			
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_INSERT_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result);
		return null;
	}
	
	@RequestMapping("**/admin/report/toSingleReportPage.do")
	public ModelAndView toSingleReportPage(HttpServletRequest request, HttpServletResponse response){
		try {
			String code = request.getParameter("code");
			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			String operator_id = request.getParameter("operator_id");
			
			if(StringUtil.isNull(operator_id)) {
				operator_id =getCurrentLoginOperator().getId().toString();
			}
			if(StringUtil.isNull(start_date)) {
				start_date = DateUtil.getNowOnlyDateStr();
			}
			if(StringUtil.isNull(end_date)) {
				// 加一天
				end_date = DateUtil.formatDateOnlyDate(System.currentTimeMillis() + 43200000) ;
			}
			
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("code", code);
			map.put("start_date", start_date);
			map.put("end_date", end_date);
			map.put("operator_id", operator_id);
			
			
			ReportSimpleBean report = reportService.querySimpleReportAndDataByCode(code, map  );

			Map<String, Object> model = new HashMap<String, Object>();
			model.put("report", report);
			model.put("start_date", start_date);
			model.put("end_date", end_date);
			
			
			return new ModelAndView(PAGE_SIMPLEREPORT_DATA_PAGE,model);

		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	

	/**
	 * 通用查询报表数据 , code入参必须提供,有界面提供
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/report/ajaxCommonChartDataByCode.html")
	public ModelAndView ajaxCommonChartDataByCode(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			LmOperatorModel op = getCurrentLoginOperator();
			Integer operatorId = op.getId();
			String code = request.getParameter("code");
			if(StringUtil.isNotNull(code)) {
				Map<String,String> params = new HashMap<String, String>();
				
				Enumeration<String> names = request.getParameterNames();
				while(names.hasMoreElements()) {
					String name = names.nextElement();
					params.put(name, request.getParameter(name));
				}

//				if(op.getO_kind() == OperatorBean.KIND_SUPPERUSER) {
//					params.put("company_id", "-1");
//				}
//				if(op.getO_kind() == OperatorBean.KIND_USER) {
//					params.put("operator_id", operatorId.toString());
//					params.put("company_id", op.getO_companyid().toString());
//				}
//				if(op.getO_kind() == OperatorBean.KIND_MANAGER) {
//					params.put("company_id", op.getO_companyid().toString());
//				}
//				
				ReportSimpleBean bean = reportService.querySimpleReportAndDataByCode(code, params);
				writeJson(response, bean);
			}

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}
	

	/**
	 * 通用查询报表数据 , code入参必须提供,有界面提供
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("admin/report/ajaxQueryReportSimpleByCode.html")
	public ModelAndView ajaxQueryReportSimpleByCode(HttpServletRequest request,HttpServletResponse response) {
		
		try {
			Integer operatorId = getCurrentLoginOperator().getId();
			String code = request.getParameter("code");
			if(StringUtil.isNotNull(code)) {
				Map<String,String> params = new HashMap<String, String>();
				params.put("operatorId", operatorId.toString());
				ReportSimpleBean bean = reportService.findReportSimpleByCode(code);
				bean.setData_sql(null);
				writeJson(response, bean);
			}

		} catch (Exception e) {
			log.error(e,e);
			writeJson(response, "error");
		}
		
		return null;
	}
}
