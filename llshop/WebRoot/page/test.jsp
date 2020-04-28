<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script	src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
  </head>
  
  <body style="display: none;">
    This is my JSP page. <br>
    
    <script type="text/javascript">
    	

	weixinInit.setShareTitle("<%="就是个标题" %>");

	weixinInit.setShareDesc("<%="就是个说明" %>");
	weixinInit.setShareLink("");
	weixinInit.setShareImg("");
	
	function shareSucess(){
		alert('朋友圈分享成功啦！！！222222');
	}
	
	weixinInit.setOnShareTimelineSuccess(shareSucess);
	weixinInit.setOnShareAppMessageSuccess(shareSucess);
	
	
    </script>
  </body>
</html>
