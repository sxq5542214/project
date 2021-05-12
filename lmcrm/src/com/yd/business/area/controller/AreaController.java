/**
 * 
 */
package com.yd.business.area.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.operator.bean.OperatorBean;

/**
 * @author ice
 *
 */
@Controller
public class AreaController extends BaseController {
	@Autowired
	private IAreaService areaService;
	

	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxQueryAreaTreeByOperator.do")
	public ModelAndView ajaxQueryUserByCompany(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(BaseContext.CURRENT_USER);
			
			CompanyExtBean tree = areaService.queryAreaAndBuildingTree(op.getO_companyid());
			List<CompanyExtBean> list = new ArrayList<CompanyExtBean>(1);
			list.add(tree);
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
