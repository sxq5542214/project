package com.yd.business.customer.controller;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.customer.bean.CustomerBean;
import com.yd.business.customer.service.ICustomerAdminService;
import com.yd.business.customer.service.ICustomerService;
import com.yd.business.login.bean.LoginBean;
import com.yd.business.login.service.ILoginService;
import com.yd.business.supplier.bean.SupplierBean;
import com.yd.business.supplier.service.ISupplierService;
import com.yd.util.AutoInvokeGetSetMethod;
import com.yd.util.DateUtil;
import com.yd.util.MD5;
import com.yd.util.NumberUtil;
/**
 * 客户信息
 * @author Anlins
 *
 */
@Controller
public class CustomerController extends BaseController {

	@Autowired
	private ICustomerService customerService;
	@Autowired
	private ICustomerAdminService customerAdminService;
	@Autowired
	private ISupplierService supplierService;
	@Autowired
	private ILoginService loginService;
	
	public static final String PAGE_CUSTOMER_LIST = "/page/pc/iframe_customer_list.jsp";

	/*******************微信－begin*********************/
	/**
	 * 微信登录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/wechat/login.do")
	public ModelAndView wechatLogin(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			int type = NumberUtil.toInt(request.getParameter("type"));//获取登录类型
			String user = request.getParameter("username");
			String pwd = request.getParameter("password");
			response.addCookie(addCookies(user, pwd, type));//添加cookies信息
			if(type==1){//商户登录
				String result = login(user, pwd, "../../app/apply/myapplylist.do");
				writeJson(response, result);
			}else if(type==2){//操作员登录
				String result = loginByAdmin(user, pwd, "../../app/apply/myapplylist.do");
				writeJson(response, result);
			}else writeJson(response, "请勿非法登录！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "系统错误，登录失败！");
		}
		return null;
	}
	/**
	 * 添加cookies信息
	 * @param user
	 * @param pwd
	 * @param type
	 */
	private Cookie addCookies(String user,String pwd,Integer type){
		String json = "{'user':'"+user+"','pwd':'"+pwd+"','type':'"+(type==2?true:false)+"'}";
		Cookie cookie = new Cookie("LoginUserInfo", json);
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24*365);//设置cookie有效期为30天  
		return cookie;
	}
	/*******************微信－end***********************/
	/**
	 * 系统登录
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customer/login.do")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			HttpSession session = request.getSession(); //创建 			
			String result = "";
			String user = request.getParameter("username");
			String pwd = request.getParameter("password");
			String checkCode = request.getParameter("checkCode");
			checkCode = checkCode.toUpperCase();
			checkCode = loginService.encodeByMD5(checkCode);	//进行MD5加密
			Object randCheckCode = session.getAttribute(LoginBean.RANDCHECKCODE);	//从session中获取验证码进行验证
			if(!randCheckCode.equals(checkCode)){							//如果验证码不匹配,返回验证码错误
				result = LoginBean.CHECKCODE_ERROR;							
				writeJson(response, result);
				return null;
			}
			result = login(user, pwd,"admin/index.do");
			writeJson(response, result);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, LoginBean.DATA_ERROR);
		}
		return null;
	}
	/**
	 * 操作员登录
	 * @param user
	 * @param pwd
	 * @param toPage
	 * @return
	 */
	private String loginByAdmin(String user,String pwd,String toPage){
		String result = "";
		if(user!=null || pwd != null){
			if(user==null||pwd==null||user.equals("") || pwd.equals("")){
				result = LoginBean.USER_PWD_ISNULL;
				customerAdminService.logoff();
			}
		}
		if(!NumberUtil.empty(user)&&!NumberUtil.empty(pwd)){
			result = LoginBean.USER_PWD_ERROR;
			customerAdminService.loginByCustomerAdmin(user, pwd);
			if (customerAdminService.getUser() != null) {
				result = toPage;
			}
		}
		return result;
	}
	/**
	 * 商户登录
	 * @param user
	 * @param pwd
	 * @param toPage
	 * @return
	 */
	private String login(String user,String pwd,String toPage){
		String result = "";
		if(user!=null || pwd != null){
			if(user==null||pwd==null||user.equals("") || pwd.equals("")){
				result = LoginBean.USER_PWD_ISNULL;
				customerService.logoff();
			}
		}
		if(!NumberUtil.empty(user)&&!NumberUtil.empty(pwd)){
			result = LoginBean.USER_PWD_ERROR;
			customerService.login(user, pwd);
			if (customerService.getUser() != null) {
				result = toPage;
			}
		}
		return result;
	}
	/**
	 * 根据id查询客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/customer/admin/find.do")
	public ModelAndView findCustomer(HttpServletRequest request,HttpServletResponse response) throws Exception{
		writeJson(response, customerService.findCustomerById(customerService.getUserId()));
		return null;
	}
	/**
	 * 更新客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customer/admin/update.do")
	public ModelAndView updateCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		try {
			CustomerBean bean = new CustomerBean();
			bean.setUsername(request.getParameter("username"));
			List<CustomerBean> list = customerService.listCustomer(bean);
			if(list!=null&&list.size()>0&&list.get(0).getId()!=customerService.getUserId()){
				result = "当前已存在该用户名，不允许更新！";
			}else{
				bean.setModify_time(NumberUtil.toString(new Date()));
				AutoInvokeGetSetMethod.autoInvoke(request.getParameterMap(), bean);
				String password = request.getParameter("password");
				if(!NumberUtil.empty(password)) bean.setPassword(MD5.md5(bean.getPassword()));
				customerService.updateCustomer(bean);
				bean = customerService.findCustomerById(bean.getId());
				bean.setPassword(password);
				customerService.setSession(bean);
				result = "个人信息更新成功";
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			result = "个人信息更新失败，服务端请求失败！";
		}
		writeJson(response, result);
		return null;
	}
	

	/**
	 * 更新客户信息
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("**/admin/customer/updateCustomerStatus.do")
	public ModelAndView updateCustomerStatus(HttpServletRequest request,HttpServletResponse response) throws IOException{
		String result = "";
		try {
			String customer_id = request.getParameter("customer_id");
			String status = request.getParameter("status");
			
			CustomerBean bean = customerService.findCustomerById(Integer.parseInt(customer_id));
			bean.setStatus(Integer.parseInt(status));
			customerService.updateCustomer(bean);
			
			result = "success" ;
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e,e);
			result = "个人信息更新失败，服务端请求失败！";
		}
		writeJson(response, result);
		return null;
	}
	/**
	 * 注册
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/customer/insert.do")
	public ModelAndView insertCustomer(HttpServletRequest request,HttpServletResponse response) throws IOException{
		try {
			CustomerBean bean = new CustomerBean();
			SupplierBean sup = new SupplierBean();
			
			String name = NumberUtil.toString(request.getParameter("name"));
			String address = NumberUtil.toString(request.getParameter("address"));
			String password = NumberUtil.toString(request.getParameter("password"));
			String passwordreset = NumberUtil.toString(request.getParameter("passwordreset"));
			String concats_name = NumberUtil.toString(request.getParameter("concats_name"));
			String concats_phone = NumberUtil.toString(request.getParameter("concats_phone"));
			String remark = NumberUtil.toString(request.getParameter("remark"));
			String currentDate = NumberUtil.toString(new Date());
			CustomerBean customer = customerService.findCustomerByPhone(concats_phone);//这里判断当前客户是否已经生成，如果没有生成则新增加一个
			if(!password.equals(passwordreset)) writeJson(response, "两次密码输入不相同，请确认！");
			else if(customer==null){
				bean.setName(name);
				bean.setContacts_name(concats_name);
				bean.setContacts_phone(concats_phone);
				bean.setAddress(address);
				bean.setCreate_time(currentDate);
				bean.setModify_time(currentDate);
				bean.setUsername(concats_phone);
				bean.setPassword(MD5.md5(password));
				bean.setStatus(CustomerBean.STATUS_YES);
				bean.setIscreate(CustomerBean.ISCREATE_YES);
				bean.setBalance(0);
				bean.setPoints(0);bean.setGet_cash_min(1);
				bean.setCredit(0);
				bean.setRemark(remark);
				bean.setPay_cycle(CustomerBean.PAY_CYCLE_MONTH);
				bean.setType(CustomerBean.TYPE_REG);
				customerService.insertCustomer(bean);
				int id = customerService.findCustomerByPhone(concats_phone).getId();
				CustomerBean admin = customerService.queryAdminCustomer();
				sup.setCustomer_id(id);
				sup.setParent_customer_id(admin.getId());
				sup.setLevel(2);//注册的都是2级用户
				sup.setParent_name(admin.getName());
				sup.setName(bean.getName());
				sup.setStatus(CustomerBean.STATUS_YES);
				sup.setType(-1);
				sup.setIssale(SupplierBean.ISSALE_TRUE);
				sup.setAddress(address);
				sup.setBalance(0);
				sup.setPoints(0);
				sup.setDeposit_money(0);
				sup.setCreate_time(currentDate);
				sup.setContacts_name(concats_name);
				sup.setContacts_phone(concats_phone);
				sup.setRemark(remark);
				sup.setModify_time(currentDate);
				supplierService.insertSupplier(sup, null);
			}else writeJson(response, "当前注册的手机号码已经存在，请勿重新注册！");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e,e);
			writeJson(response, "数据加载错误");
		}
		return null;
	}
	
	@RequestMapping("**/admin/customer/toCustomerAddBalancePage.do")
	public ModelAndView toCustomerAddBalancePage(HttpServletRequest request,HttpServletResponse response){
		
		try{
			List<CustomerBean> customerList = customerService.listCustomer(null);
			
			Map<String,Object> model = new HashMap<String, Object>();
			model.put("customerList", customerList);
			
			return new ModelAndView(PAGE_CUSTOMER_LIST, model);
		}catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	@RequestMapping("**/admin/customer/addCustomerBalance.do")
	public ModelAndView addCustomerBalance(HttpServletRequest request,HttpServletResponse response){
		try {
			String customer_id = request.getParameter("customer_id");
			String add_balance = request.getParameter("add_balance");
			String remark = request.getParameter("remark");
			
			CustomerBean user = getCurrentLoginUser();
			
			customerService.addCustomerBalance(DateUtil.formatDateToPureSSS(new Date()), Integer.parseInt(customer_id), Integer.parseInt(add_balance), user.getId()+"用户【" + user.getName() +"】后台管理界面添加余额"+add_balance +",说明："+remark);
			
			writeJson(response, "true");
			
		} catch (Exception e) {
			log.error(e, e);
		}
		return null;
	}
	
	
}
