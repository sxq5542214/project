package com.yd.business.login.controller;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yd.basic.framework.controller.BaseController;
import com.yd.business.login.service.ILoginService;

@Controller
public class LoginController extends BaseController {
	@Autowired
	private ILoginService loginService;
	
	
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
	
	
	
}
