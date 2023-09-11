package com.yd.business.login.service;

import java.awt.image.BufferedImage;

import javax.servlet.http.HttpSession;

import com.yd.business.operator.bean.OperatorBean;
import com.yd.iotbusiness.mapper.model.LmOperatorModel;



public interface ILoginService {
	
	/**
	 * 生成图片验证码
	 */
	public BufferedImage CheckCode(HttpSession session);
	
	/**
	 * 进行MD5加密
	 */
	public String encodeByMD5(String str);

	OperatorBean login(String username, String password);

	String modifyPassword(String oldPassword, String newPassword, long operatorid);

	LmOperatorModel loginIOTWeb(String username, String password);

}
