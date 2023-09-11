/**
 * 
 */
package com.yd.business.product.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.context.BaseContext;
import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaBean;
import com.yd.business.area.service.IAreaService;
import com.yd.business.company.bean.CompanyBean;
import com.yd.business.company.bean.CompanyExtBean;
import com.yd.business.company.service.ICompanyService;
import com.yd.business.image.bean.UploadLogBean;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.product.service.IProductService;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Controller
public class ProductController extends BaseController {
	@Autowired
	private IProductService productService;
	

	/**
	 *  界面查询营业所列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/product/ajaxQueryProductList.do")
	public ModelAndView ajaxQuerySupplierList(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result;
		try {

			LmOperatorModel op = (LmOperatorModel)WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR);
			
			result = productService.queryProductList(null);

		} catch (Exception e) {
			log.error(e, e);
			result = new IOTWebDataBean();
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result );
		return null;
	}
	
	
		
		
}
