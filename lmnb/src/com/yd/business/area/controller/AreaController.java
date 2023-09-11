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

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.iotbusiness.mapper.model.LlAddressModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.AutoInvokeGetSetMethod;

/**
 * @author ice
 *
 */
@Controller
public class AreaController extends BaseController {
	@Autowired
	private IAreaService areaService;
	@Autowired
	private IBuildingService buildingService;
	

//	/**
//	 *  界面查询取悦列表
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping("**/admin/area/ajaxQueryAreaTreeByOperator.do")
//	public ModelAndView ajaxQueryAreaTreeByOperator(HttpServletRequest request,HttpServletResponse response){
//		
//		try {
//			
//			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
//			
//			CompanyExtBean tree = areaService.queryAreaAndBuildingTree(op.getO_companyid());
//			List<CompanyExtBean> list = new ArrayList<CompanyExtBean>(1);
//			list.add(tree);
//			
//			writeJson(response, list );
//		} catch (Exception e) {
//			log.error(e, e);
//		}
//		return null;
//	}
//	
	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxQueryBuildingById.do")
	public ModelAndView ajaxQueryBuildingById(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String id = request.getParameter("id");
			BuildingExtBean build = buildingService.findBuildingById(Long.parseLong(id));
			
			writeJson(response, build );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 *  界面查询地址信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxQueryAddressByParent.do")
	public ModelAndView ajaxQueryAddressByParent(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result  ;
		try {
			LmOperatorModel op = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			LlAddressModel model = new LlAddressModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), model);
			model.setSystemid(op.getSystemid());
			result = areaService.queryAreaList(model);

		} catch (Exception e) {
			result = new IOTWebDataBean();
			result.setCode(-1);
			result.setMessage(e.getMessage());
			
			log.error(e, e);
		}
		writeJson(response, result );
		return null;
	}
	
	
	

	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxQueryAreaByCompany.do")
	public ModelAndView ajaxQueryAreaByCompany(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result  ;
		try {
			int level_top = 1;
			LmOperatorModel op = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			LlAddressModel model = new LlAddressModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), model);
			model.setSystemid(op.getSystemid());
			model.setLevel(level_top);
			result = areaService.queryAreaList(model);
			

		} catch (Exception e) {
			result = new IOTWebDataBean();
			result.setCode(-1);
			result.setMessage(e.getMessage());
			
			log.error(e, e);
		}
		writeJson(response, result );
		return null;
	}
	
	

	/**
	 *  界面删除地址信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxDeleteAddressInfo.do")
	public ModelAndView ajaxDeleteAddressInfo(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result  ;
		try {
			LmOperatorModel op = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String id = request.getParameter("id");
			result = areaService.deleteAddressByIdAndCompany(Integer.parseInt(id), op.getSystemid());
			

		} catch (Exception e) {
			result = new IOTWebDataBean();
			result.setCode(-1);
			result.setMessage(e.getMessage());
			
			log.error(e, e);
		}
		writeJson(response, result );
		return null;
	}
	
	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxAddAddressInfo.do")
	public ModelAndView ajaxAddAddressInfo(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result;
		try {

			LmOperatorModel op = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			 
			LlAddressModel bean = new LlAddressModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setSystemid(op.getSystemid());
			if(bean.getParentId() == null) {
				bean.setLevel(1);
			}
			
			result = areaService.addOrUpdateAddress(bean);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_UPDATE_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	

	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxUpdateAddressInfo.do")
	public ModelAndView ajaxUpdateAddressInfo(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result;
		try {

			LmOperatorModel op = (LmOperatorModel) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			 
			LlAddressModel bean = new LlAddressModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			bean.setSystemid(op.getSystemid());
			result = areaService.addOrUpdateAddress(bean);
			
		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setMessage(e.getMessage());
			result.setCode(IOTWebDataBean.CODE_IOTWEB_UPDATE_ERROR);
		}
		writeJson(response, result );
		return null;
	}
	
	
	
}
