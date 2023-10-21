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
import com.yd.business.area.bean.AddressBean;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.bean.AreaExtBean;
import com.yd.business.area.bean.BuildingBean;
import com.yd.business.area.bean.BuildingExtBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.area.service.IBuildingService;
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
	@Autowired
	private IBuildingService buildingService;
	

	/**
	 *  界面查询取悦列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/area/ajaxQueryAreaTreeByOperator.do")
	public ModelAndView ajaxQueryAreaTreeByOperator(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			CompanyExtBean tree = areaService.queryAreaAndBuildingTree(op.getO_companyid());
			List<CompanyExtBean> list = new ArrayList<CompanyExtBean>(1);
			list.add(tree);
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
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
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			AddressBean bean = new AddressBean();
			bean.setCompany_id(op.getO_companyid().intValue());
			List<AddressBean> list = areaService.queryAddressList(bean );
						
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
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
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			List<AreaExtBean> list = areaService.queryAreaList(op.getO_companyid());
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
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
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String addressId  = request.getParameter("addressId");
			
			
			String result = areaService.deleteAddressByIdAndCompany(Integer.parseInt(addressId) , op.getO_companyid().intValue() );
			
			
			writeString(response, result );
		} catch (Exception e) {
			log.error(e, e);
		}
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
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String a_name  = request.getParameter("a_name");
			int a_level  = Integer.parseInt(request.getParameter("a_level"));
			String parent_id  = request.getParameter("parent_id");
			String full_name  = request.getParameter("full_name");
			String addressId  = request.getParameter("addressId");
			int result = 0 ; 
			
			AddressBean bean = new AddressBean();
			bean.setName(a_name);
			bean.setParent_id(Integer.parseInt(parent_id));
//			bean.setFull_name(a_name);
			bean.setLevel(a_level);
			bean.setCompany_id(op.getO_companyid().intValue());
			result = areaService.addOrUpdateAddress(bean );
			
			//根据不同级别，插入不同数据
//			switch (a_level) {
//			case AreaBean.LEVEL_COMPANY:
//				
//				break;
//			case AreaBean.LEVEL_AREA:
//				AreaBean area = new AreaBean();
//				area.setA_companyid(Long.parseLong(parent_id));
//				area.setA_name(a_name);
//				result = areaService.addOrUpdateArea(area);
//				break;
//			case AreaBean.LEVEL_BUILDING:
//				BuildingBean build = new BuildingBean();
//				build.setB_areaid(Long.parseLong(parent_id));
//				build.setB_name(a_name);
//				result = buildingService.addOrUpdateBuilding(build);
//				break;
//
//			default:
//				break;
//			}
			
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
		}
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
		
		try {

			OperatorBean op = (OperatorBean) WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			String a_name  = request.getParameter("a_name");
			int a_level  = Integer.parseInt(request.getParameter("a_level"));
			Integer a_id  = Integer.parseInt(request.getParameter("a_id"));
			int result = 0 ; 
			 
			AddressBean bean = new AddressBean();
			bean.setName(a_name);
			bean.setId(a_id);
			bean.setCompany_id(op.getO_companyid().intValue());
			result = areaService.addOrUpdateAddress(bean);
			
			//根据不同级别，插入不同数据
//			switch (a_level) {
//			case AreaBean.LEVEL_COMPANY:
//				
//				break;
//			case AreaBean.LEVEL_AREA:
//				AreaBean area = new AreaBean();
//				area.setA_name(a_name);
//				area.setA_id(a_id);
//				result = areaService.addOrUpdateArea(area);
//				break;
//			case AreaBean.LEVEL_BUILDING:
//				BuildingBean build = new BuildingBean();
//				build.setB_name(a_name);
//				build.setB_id(a_id);
//				result = buildingService.addOrUpdateBuilding(build);
//				break;
//
//			default:
//				break;
//			}
			
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
}
