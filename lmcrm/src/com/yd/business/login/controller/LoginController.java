package com.yd.business.login.controller;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.yd.basic.framework.context.WebContext;
import com.yd.basic.framework.controller.BaseController;
import com.yd.business.login.service.ILoginService;
import com.yd.business.operator.bean.OperatorBean;
import com.yd.business.system.bean.SystemMenuBean;
import com.yd.business.system.service.ISystemManagerService;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private ILoginService loginService;
	@Autowired
	private ISystemManagerService systemManagerService;

	/**
	 * 生成验证码图片，以及把验证码生成的随机文字放到session中
	 */
	@RequestMapping("**/login/LoginController/checkCode.do")
	public void checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//禁止缓存图片
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		//指定生成的响应是图片
		response.setContentType("image/jpeg");
		HttpSession session=request.getSession(true);
		//生成图片验证码
		BufferedImage image = loginService.CheckCode(session);
		ImageIO.write(image,"JPEG",response.getOutputStream());
	}
	

	/**
	 * 界面登录
	 */
	@RequestMapping("**/login/loginByWeb.do")
	public void loginByWeb(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		OperatorBean bean = loginService.login(username, password);
		if(bean == null) {
			writeJson(response, "登录失败，请检查用户名或密码");
		}
		
	}
	
	@RequestMapping("**/login/unloginByWeb.do")
	public void unloginByWeb(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		WebContext.setObejctToSession(WebContext.SESSION_ATTRIBUTE_CURRENT_OPERATOR, null);
		
	}
	

	@RequestMapping("**/admin/login/ajaxModifyPasswordByWeb.do")
	public void ajaxModifyPasswordByWeb(HttpServletRequest request, HttpServletResponse response){
		
		String newPassword = request.getParameter("newPassword");
		String oldPassword = request.getParameter("oldPassword");
		OperatorBean op = getCurrentLoginOperator();
		
		String result = loginService.modifyPassword(oldPassword, newPassword, op.getO_id());
		writeString(response, result);
		
		
	}
	

	@RequestMapping("**/admin/login/toIndexFramePage.do")
	public ModelAndView toIndexFramePage(HttpServletRequest request, HttpServletResponse response){
		
		OperatorBean op = getCurrentLoginOperator();
		
		List<SystemMenuBean> menuList = systemManagerService.querySystemMenuByOperator(op.getO_id());
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("menuList", menuList);
		model.put("operator", op);
		return new ModelAndView("/page/frame/indexFrame.jsp", model);
		
	}
	
}
