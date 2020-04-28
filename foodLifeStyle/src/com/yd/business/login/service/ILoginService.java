package com.yd.business.login.service;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpSession;



public interface ILoginService {
	
	/**
	 * 生成图片验证码
	 */
	public BufferedImage CheckCode(HttpSession session);
	
	/**
	 * 进行MD5加密
	 */
	public String encodeByMD5(String str);

}
