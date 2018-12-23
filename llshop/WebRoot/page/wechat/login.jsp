<%@page import="com.yd.util.NumberUtil"%>
<%@page import="org.json.JSONObject"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
Cookie[] c = request.getCookies();
String user="",pwd="";
String isChecked = "";
if(c!=null){
	for(int i=0;i<c.length;i++){
		System.out.println(c[i].getName()+"-"+c[i].getValue());
		if(c[i].getName().equals("LoginUserInfo")){
			JSONObject jso = new JSONObject(c[i].getValue());
			user = NumberUtil.toString(jso.get("user"));
			pwd = NumberUtil.toString(jso.get("pwd"));
			isChecked = NumberUtil.toBool(jso.get("type"))?"checked":"";
		}
	}
}
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=GB18030">
<title>万大商城-登录</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/flat-ui.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>
<body>
	<div style="height: 1.35rem; text-align: center;">
		<header> <a style="color: white;">万大商城-登录</a>
		</header>
		</div>
	<div class="container" id="_contain">
		<form role="form">
			<div class="form-group">
				<label for="username">登录账号</label> <input
					type="text" class="form-control" id="username" value="<%=user %>"
					placeholder="请输入登录账号">
			</div>
			<div class="form-group">
				<label for="password">登录密码</label> <input
					type="password" class="form-control" id="password" value="<%=pwd %>"
					placeholder="请输入登录密码">
			</div>
			<label class="checkbox" for="isadmin"> <input
				type="checkbox" data-toggle="checkbox" <%=isChecked %> value="" id="isadmin" class="custom-checkbox"><span class="icons"><span
					class="icon-unchecked"></span><span class="icon-checked"></span></span>
				使用操作员登录
			</label>
			<button type="button" onclick="L.login();" class="btn btn-primary btn-block">登&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;录</button>
		</form>
	</div>
	<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="js/flat-ui.js"></script>
	<script src="js/login.js"></script>
	<script src="../../js/Util.js"></script>
</body>
</html>