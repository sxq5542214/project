/**
 * 
 */
package com.yd.business.other.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.other.bean.AddressBean;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.ICommandService;
import com.yd.iotbusiness.mapper.model.LmCmdModel;

/**
 * @author ice
 *
 */
@Controller
public class CmdController extends BaseController {
	@Resource
	private ICommandService  commandService;
	
	@RequestMapping("admin/other/cmd/ajaxQueryCMDLogList.do")
	public ModelAndView ajaxQueryCMDLogList(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result = new IOTWebDataBean();
		try {
			String meterCode = request.getParameter("meterCode");
			
			List<LmCmdModel> list = commandService.queryCmdList(meterCode);
			result.setData(list);
			result.setTotal((long) list.size());
			
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
		}
		writeJson(response, result);
		return null;
		
	}
	
	
}
