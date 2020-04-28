<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>界面跳转中，请稍等</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
  var urlFix = '';
  <% String userId = String.valueOf(request.getAttribute("userId"));
  	 String pageUrl = String.valueOf(request.getAttribute("url"));
  	 //判断是否有参数了
  	 if(pageUrl.indexOf("?") == -1){
  	 	pageUrl += "?";
  	 }else{
  	 	pageUrl += "&";
  	 }
  	 String userName = "";
  	 String loginType = String.valueOf(request.getAttribute("loginType"));
  	 String token = String.valueOf(request.getAttribute("token"));
	if(userId != null && !userId.equals("null")){
%>
	document.cookie = "userName=<%=userName%>;path=/";
	document.cookie = "loginType=<%=loginType%>;path=/";
	document.cookie = "userId=<%=userId %>;path=/";
	urlFix = 'uid=<%=userId%>&token=<%=token%>';
<%} %>
	window.location.href = '<%=pageUrl%>'+urlFix;
  </script>
  <body>
  </body>
</html>
