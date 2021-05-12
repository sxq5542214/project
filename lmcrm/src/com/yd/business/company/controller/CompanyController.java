/**
 * 
 */
package com.yd.business.company.controller;

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
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.operator.bean.OperatorBean;

/**
 * @author ice
 *
 */
@Controller
public class CompanyController extends BaseController {
	@Autowired
	private ICompanyService companyService;
	

	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/company/ajaxQueryCompanyByOperator.do")
	public ModelAndView ajaxQueryUserByCompany(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(BaseContext.CURRENT_USER);
			
			CompanyBean bean = companyService.findCompanyById(op.getO_companyid());
			
			writeJson(response, bean );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
