package com.yd.business.login.bean;

public class LoginBean {
	private String user ;
	private String pwd ; 
	private String checkCode ;
	
	public static final String USER_PWD_ISNULL  = "用户名或密码为空,请重新输入";
	public static final String USER_PWD_ERROR = "用户不存在或密码错误";
	
	public static final String CHECKCODE_ERROR = "验证码错误";
	public static final String CHECKCODE_FONT_STYLE = "华文宋体";

	
	public static final String  DATA_ERROR = "数据处理失败";
	
	public static final String RANDCHECKCODE = "randCheckCode";
	
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	} 
	
	
	
	
}
