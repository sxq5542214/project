package com.yd.business.user.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.bean.IOTWebDataBean;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.area.service.IAreaService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserModelExtendsBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.iotbusiness.mapper.model.LlAddressModel;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;
import com.yd.iotbusiness.mapper.model.LmUserModel;
import com.yd.util.AutoInvokeGetSetMethod;
@Controller
public class UserController extends BaseController {

	@Autowired
	private IUserInfoService userInfoService;
	@Autowired
	private IUserWechatService userWechatService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private IAreaService areaService;
	@Resource
	private ThreadPoolTaskExecutor taskExecutor;
	@Resource
	private IConfigCruxService configCruxService;
	
	public static final String PAGE_ORDERPRODUCTLOG = "/page/user/orderProductLog.jsp";
	/**
	 * 检查手机号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/user/findUserByOpenid.do")
	public ModelAndView findUserByOpenid(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String openid = request.getParameter("openid");
			LmUserModel user = userWechatService.findLmUserByOpenId(openid);
			if(user != null) {
				user.setIdcard(null);
			}
			writeJson(response, user);
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	/**
	 *  界面查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/user/ajaxQueryUserByCompany.do")
	public ModelAndView ajaxQueryUserByCompany(HttpServletRequest request,HttpServletResponse response){
		
		IOTWebDataBean result ;
		
		try {

//			OperatorBean operator = getCurrentLoginOperator();
			
			UserModelExtendsBean bean = new UserModelExtendsBean();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			result = userInfoService.queryUserAndMeterList(bean);
			
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
	 *  界面查询用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/user/ajaxAddOrUpdateUser.do")
	public ModelAndView ajaxAddOrUpdateUser(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result= new IOTWebDataBean();
		try {
			Map<String, String[]> map = request.getParameterMap();
			System.out.println(map);
			
			LmOperatorModel operator = getCurrentLoginOperator();
			LmUserModel bean = new LmUserModel();
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			
			bean.setSystemid(operator.getSystemid());
			
			
			LlAddressModel address = areaService.findAddressByFullname(bean.getArea1()+bean.getArea2()+bean.getArea3());
			bean.setAddressid(address.getId());
			
			int num = userInfoService.addOrUpdateUser(bean);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_SUCCESS);
			result.setData(bean);
			
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
			writeJson(response, result );
		}
		return null;
	}

	/**
	 *  界面删除用户
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/user/ajaxDeleteUser.do")
	public ModelAndView ajaxDeleteUser(HttpServletRequest request,HttpServletResponse response){
		IOTWebDataBean result= new IOTWebDataBean();
		try {

			LmOperatorModel operator = getCurrentLoginOperator();
			String uid = request.getParameter("id");
			LmUserModel user = userInfoService.findUserById(Integer.parseInt(uid));
			
			if(user.getSystemid().intValue() == operator.getSystemid().intValue()) {
				userInfoService.deleteUser(user.getId());
			}

			result.setMessage("用户及水表删除成功！");
			writeJson(response, result );
		} catch (Exception e) {
			log.error(e, e);
			result.setCode(IOTWebDataBean.CODE_IOTWEB_QUERY_ERROR);
			result.setMessage(e.getMessage());
			writeJson(response, result );
		}
		return null;
	}
	
	/**
	 *  界面查询未开户用户列表
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/user/ajaxQueryUnOpenAccountUser.do")
	public ModelAndView ajaxQueryUnOpenAccountUser(HttpServletRequest request,HttpServletResponse response){
		
		try {

//			LmOperatorModel operator = getCurrentLoginOperator();
//			UserInfoBean bean = new UserInfoBean();
//			bean.setU_status(UserInfoBean.STATUS_UNOPEN);
//			bean.setU_operatorid(operator.getId());
//			List<UserInfoBean> list = userInfoService.queryUserInfo(bean);
//			
//			
//			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	

	/**
	 *  界面查询未用户信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("**/admin/user/ajaxQueryAccountUser.do")
	public ModelAndView ajaxQueryAccountUser(HttpServletRequest request,HttpServletResponse response){
		
		try {

//			OperatorBean operator = getCurrentLoginOperator();
//			String u_phone = request.getParameter("u_phone");
//			String u_name = request.getParameter("u_name");
//			String u_paperwork = request.getParameter("u_paperwork");
//			String u_buildingid = request.getParameter("u_buildingid");
//			String u_areaid = request.getParameter("u_areaid");
//			
//			UserInfoBean bean = new UserInfoBean();
//			bean.setU_operatorid(operator.getO_id());
//			bean.setU_phone(u_phone);
//			bean.setU_name(u_name);
//			bean.setU_paperwork(u_paperwork);
//			bean.setIsdisplay(UserInfoBean.ISDISPLAY_YES);
//			if(StringUtil.isNotNull(u_buildingid)) {
//				bean.setU_buildingid(Long.parseLong(u_buildingid));
//			}else if(StringUtil.isNotNull(u_areaid)) {
//				bean.setAreaid(Long.parseLong(u_areaid));
//			}
//			List<UserInfoBean> list = userInfoService.queryUserInfo(bean);
//			
//			
//			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
}
