<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
UserWechatBean user = (UserWechatBean) request.getAttribute("user");
String content = "",title = "讨厌哦，你不是通过正常途径进来的哟？",desc = "";


/*
title = user.getNick_name() +  "在【"+ BaseContext.getMchName() + "】 分享了5元红包给您，请快来领取！";
content = user.getNick_name() +  " 在【<font color=\"red\" >"+ BaseContext.getMchName() +"</font>】分享了5元红包给您，长按上方二维码，关注"+ BaseContext.getMchName() +"，领取您的红包和TA一起快乐充流量！<br>";

*/


title =  "疯狂摇一摇，流量免费送！关注即可参加，人人有份！拿走不谢！我是雷锋！" ; 
desc = "更有"+ user.getNick_name() + "送你10元流量红包，拿走不谢！我是雷锋"  ; 

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title><%=title %></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="css/index-style.css" rel="stylesheet" type="text/css">
	<link href="css/menu-style.css" rel="stylesheet" type="text/css">
	<script src="js/jquery.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	
  </head>
  <script type="text/javascript">
  	 	weixinInit.setShareLink('<%=basePath%>wechat/user/toInviteFriends.do?openId=<%=user.getOpenid()%>');
	  	weixinInit.setShareTitle('<%=title %>');
	  	weixinInit.setShareImg('<%=user.getHead_img()%>');
	  	weixinInit.setShareDesc('<%=desc%>');
  </script>
  <body onload="showBody();" style="margin: 0;">

				
<!-- 		<div style="margin-left:0pt;text-indent:0pt;text-align:center;font-size:40px;" >微信直接扫描下方二微码关注公众号后,即可和TA一起玩转全民好运啦。</div>
 -->		 
 <div style="width:100%; position:absolute;  z-index: 1;"  id="body_div" >
			
			
			
		<img alt="" style="width: 100%" src="images/user/user_invite_friends.png">
			
		</div>
		<div style="margin-top:130%;z-index: 99;position: absolute;width: 100%;">
			<%if(ticket != null){ %>
			
			<img style="width:70%;padding:0px 15% 0px 15% ;" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>"  /> 
			
			
		<%}else{ %>
			<img style="width:70%;padding:0px 15% 0px 15% ;"  src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQFb7zoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL2Z6aGpmbWZsSmM5YzV6RUhxaGIzAAIEPjxjVwMEAI0nAA=="  /> 
		<%} %>
		
		
	</div>
	
	
  </body>
  <script type="text/javascript">
   
  	function showBody(){
  		document.getElementById('body_div').style.display = 'block';
  	}
 // 	setTimeout("show();",1500);
  </script>
</html>
