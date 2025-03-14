/**
 * 
 */
package com.yd.business.log.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanMap;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yd.basic.framework.service.BaseService;
import com.yd.business.log.bean.LogTemplateBean;
import com.yd.business.log.bean.OperatorLogBean;
import com.yd.business.log.dao.IOperatorLogDao;
import com.yd.business.log.service.IOperatorLogService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.iotbusiness.mapper.dao.LlOperatorLogModelMapper;
import com.yd.iotbusiness.mapper.model.LlOperatorLogModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.util.DateUtil;
import com.yd.util.StringUtil;

/**
 * @author ice
 *
 */
@Service("operatorLogService")
public class OperatorLogServiceImpl extends BaseService implements IOperatorLogService {
	@Autowired
	private IOperatorLogDao operatorLogDao;
	@Autowired
	private LlOperatorLogModelMapper operatorLogModelMapper;
	
	@Override
	public void createOperatoryLog(OperatorBean op ,HttpServletRequest request) {
		String uri = request.getRequestURI();
		Map<String, String[]> paramMap = request.getParameterMap();
		
		if(StringUtil.isNotNull(uri)) {
			String[] paths = uri.split("/");
			String method = paths[paths.length-1].split("\\.")[0] ;
//System.out.println("method : ======= " + method );
			
			
			
			LogTemplateBean logTemplate = findLogTemplate(method, "controller");
			if(logTemplate != null) {
				String template = logTemplate.getTemplate();
				
				if(StringUtil.isNotNull(template)) {
					JSONObject jsonParamMap = null;
					String[] array = template.split("#");
					if(array.length >= 2) {

						BeanMap opMap = new BeanMap(op);
						jsonParamMap = new JSONObject(paramMap);
						
						for(int i = 0 ; i < array.length; i++ ) {
							if(i%2 == 1) {
								String paramName = array[i];
								String value = "";
								//  op_ 前缀代表当前登录用户的参数
								if(paramName.startsWith("op_")) {
									value = opMap.get(paramName.substring(3)) == null? "": opMap.get(paramName.substring(3)).toString();
								}
								if(value == "") {
									value = jsonParamMap.opt(paramName) == null ? "" : jsonParamMap.opt(paramName).toString();
								}
								template = template.replaceAll("#"+ paramName +"#", value);
							}
						}
					}


					try {
				        
						OperatorLogBean bean = new OperatorLogBean();
						bean.setOl_happendate(new Date());
						bean.setOl_operatorid(op.getO_id());
						bean.setOl_remark(template);
						if(jsonParamMap != null && LogTemplateBean.IS_SAVE_PARAMS_TRUE == logTemplate.getIs_save_params()) {
							bean.setParams(jsonParamMap.toString());
						}
						
						if(request.getInputStream() != null) {
							ServletInputStream inputStream = request.getInputStream();
					        // 读取输入流  
					        SAXReader reader = new SAXReader();  
					        Document document = reader.read(inputStream);  
					        bean.setParams(document.asXML());
						}
						operatorLogDao.insertOperatorLog(bean );
					} catch (Exception e) {
						e.printStackTrace();
						log.error(e, e);
					}
				}
			}
		}
	}
	

	@Override
	public void createOperatoryLog(LmOperatorModel op ,HttpServletRequest request) {
		String uri = request.getRequestURI();
		Map<String, String[]> paramMap = request.getParameterMap();
		
		if(StringUtil.isNotNull(uri)) {
			String[] paths = uri.split("/");
			String method = paths[paths.length-1].split("\\.")[0] ;
//System.out.println("method : ======= " + method );
			
			
			
			LogTemplateBean logTemplate = findLogTemplate(method, "controller");
			if(logTemplate != null) {
				String template = logTemplate.getTemplate();
				
				if(StringUtil.isNotNull(template)) {
					JSONObject jsonParamMap = null;
					String[] array = template.split("#");
					if(array.length >= 2) {

						BeanMap opMap = new BeanMap(op);
						jsonParamMap = new JSONObject(paramMap);
						
						for(int i = 0 ; i < array.length; i++ ) {
							if(i%2 == 1) {
								String paramName = array[i];
								String value = "";
								//  op_ 前缀代表当前登录用户的参数
								if(paramName.startsWith("op_")) {
									value = opMap.get(paramName.substring(3)) == null? "": opMap.get(paramName.substring(3)).toString();
								}
								if(value == "") {
									value = jsonParamMap.opt(paramName) == null ? "" : jsonParamMap.opt(paramName).toString();
								}
								template = template.replaceAll("#"+ paramName +"#", value);
							}
						}
					}

					LlOperatorLogModel bean = new LlOperatorLogModel();
					bean.setCreateTime(DateUtil.getNowDateStr());
					bean.setOperatorid(op.getId());
					bean.setRemark(template);
					if(jsonParamMap != null && LogTemplateBean.IS_SAVE_PARAMS_TRUE == logTemplate.getIs_save_params()) {
						bean.setParams(jsonParamMap.toString());
					}
					operatorLogModelMapper.insertSelective(bean);
				}
				
				
			}
			
			
		}
	}
	
	private LogTemplateBean findLogTemplate(String method ,String hierarchy) {
		LogTemplateBean template = operatorLogDao.findLogTemplateByMethod(method, "controller");
		return template;
	}
	
	public static void main(String[] args) {
		
		System.out.println("op_asdffdsa".substring(3));
		
		String str = "#2#";
		System.out.println(str.split("#").length);
		for(String s : str.split("#")) {
			System.out.println("str: " + s);
		}
		
		
		String uri = "/lmcrm/admin/user/ajaxQueryUserByCompany.do";
		String[] paths = uri.split("/");
		String method = paths[paths.length-1] ;
		System.out.println(method + ", "+method.split("\\.").length);
		for(String s : method.split("\\.")) {
			System.out.println("str: " + s);
		}
		
	}
}
