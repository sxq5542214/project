package com.yd.business.user.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.area.bean.AreaDataBean;
import com.yd.business.area.service.IAreaDataService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.other.service.IAddressService;
import com.yd.business.other.service.IConfigAttributeService;
import com.yd.business.other.service.IConfigCruxService;
import com.yd.business.user.bean.UserInfoBean;
import com.yd.business.user.service.IUserInfoService;
import com.yd.business.user.service.IUserWechatService;
import com.yd.business.wechat.service.IWechatPayService;
import com.yd.business.wechat.service.IWechatService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.StringUtil;
@Controller
public class UserController extends BaseController {

	@Autowired
	private IUserInfoService userInfoService;
	@Resource
	private IConfigAttributeService configAttributeService;
	@Resource
	private IAreaDataService areaDataService;
	@Resource
	private IAddressService addressService;
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
	@RequestMapping("**/user/checkPhone.do")
	public ModelAndView checkPhone(HttpServletRequest request,HttpServletResponse response){
		
		try {
			
			String phone = request.getParameter("phone");
			AreaDataBean ad = areaDataService.getAreaDataByPhone(phone);
			writeJson(response, ad);
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
		
		try {

			OperatorBean operator = getCurrentLoginOperator();
			String u_phone = request.getParameter("u_phone");
			String u_name = request.getParameter("u_name");
			String u_paperwork = request.getParameter("u_paperwork");
			String u_buildingid = request.getParameter("u_buildingid");
			String u_areaid = request.getParameter("u_areaid");
			String u_cardno = request.getParameter("u_cardno");
			String addressid = request.getParameter("addressId");
			Integer addressId = null;
			if(StringUtil.isNotNull(addressid)) {
				addressId = Integer.parseInt(addressid);
			}
			
			UserInfoBean bean = new UserInfoBean();
			bean.setU_operatorid(operator.getO_id());
			bean.setU_phone(u_phone);
			bean.setU_name(u_name);
			bean.setU_paperwork(u_paperwork);
			bean.setAddressId(addressId);
			bean.setIsdisplay(UserInfoBean.ISDISPLAY_YES);
			if(StringUtil.isNotNull(u_buildingid)) {
				bean.setU_buildingid(Long.parseLong(u_buildingid));
			}else if(StringUtil.isNotNull(u_areaid)) {
				bean.setAreaid(Long.parseLong(u_areaid));
			}else if(StringUtil.isNotNull(u_cardno)) {
				bean.setU_cardno(Integer.parseInt(u_cardno));
			}
			List<UserInfoBean> list = userInfoService.queryUserInfo(bean);
			
			
			writeJson(response, list );
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
	@RequestMapping("**/admin/user/ajaxAddOrUpdateUser.do")
	public ModelAndView ajaxAddOrUpdateUser(HttpServletRequest request,HttpServletResponse response){
		
		try {

			OperatorBean operator = getCurrentLoginOperator();
//			String u_phone = request.getParameter("u_phone");
//			String u_name = request.getParameter("u_name");
			String u_paperwork = request.getParameter("u_paperwork");
			String u_remark = request.getParameter("u_remark");
//			String u_areaid = request.getParameter("u_areaid");
//			String u_no = request.getParameter("u_no");
			UserInfoBean bean = new UserInfoBean();
			
			AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
			
			
			bean.setU_operatorid(operator.getO_id());
//			bean.setU_phone(u_phone);
			bean.setU_remark(u_remark);
			bean.setU_paperwork(u_paperwork);
//			if(StringUtil.isNotNull(u_buildingid)) {
//				bean.setU_buildingid(Long.parseLong(u_buildingid));
//			}else if(StringUtil.isNotNull(u_areaid)) {
//				bean.setAreaid(Long.parseLong(u_areaid));
//			}else if(StringUtil.isNotNull(u_no)) {
//				bean.setU_no(Long.parseLong(u_no));
//			}
			int num = userInfoService.addOrUpdateUser(bean);
			
			
			writeJson(response, bean.getU_id() );
		} catch (Exception e) {
			log.error(e, e);
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
		
		try {

			OperatorBean operator = getCurrentLoginOperator();
			String u_id = request.getParameter("u_id");
			UserInfoBean bean = new UserInfoBean();
			
			bean.setU_id(Long.parseLong(u_id));
			bean.setIsdisplay(UserInfoBean.ISDISPLAY_NO);
			int num = userInfoService.addOrUpdateUser(bean);
			
			
			writeJson(response, bean.getU_id() );
		} catch (Exception e) {
			log.error(e, e);
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

			OperatorBean operator = getCurrentLoginOperator();
			UserInfoBean bean = new UserInfoBean();
			bean.setU_status(UserInfoBean.STATUS_UNOPEN);
			bean.setU_operatorid(operator.getO_id());
			List<UserInfoBean> list = userInfoService.queryUserInfo(bean);
			
			
			writeJson(response, list );
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

			OperatorBean operator = getCurrentLoginOperator();
			String u_phone = request.getParameter("u_phone");
			String u_name = request.getParameter("u_name");
			String u_paperwork = request.getParameter("u_paperwork");
			String u_buildingid = request.getParameter("u_buildingid");
			String u_areaid = request.getParameter("u_areaid");
			
			UserInfoBean bean = new UserInfoBean();
			bean.setU_operatorid(operator.getO_id());
			bean.setU_phone(u_phone);
			bean.setU_name(u_name);
			bean.setU_paperwork(u_paperwork);
			bean.setIsdisplay(UserInfoBean.ISDISPLAY_YES);
			if(StringUtil.isNotNull(u_buildingid)) {
				bean.setU_buildingid(Long.parseLong(u_buildingid));
			}else if(StringUtil.isNotNull(u_areaid)) {
				bean.setAreaid(Long.parseLong(u_areaid));
			}
			List<UserInfoBean> list = userInfoService.queryUserInfo(bean);
			
			
			writeJson(response, list );
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
	
	
}
