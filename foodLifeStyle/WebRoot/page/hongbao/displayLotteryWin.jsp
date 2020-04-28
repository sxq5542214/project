<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
UserWechatBean user = (UserWechatBean) request.getAttribute("user");
WechatOriginalInfoBean original = (WechatOriginalInfoBean)request.getAttribute("original");
String param = (String)request.getAttribute("param");
param = URLDecoder.decode(param, "UTF8");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>喜大普奔！恭喜<%= user.getNick_name() %>中奖啦！</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="css/index-style.css" rel="stylesheet" type="text/css">
	<link href="css/menu-style.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
  </head>
  
  <body>
  	

<div style="width:90%;margin-left:5%; position:absolute;"   >
			
		<%if(ticket != null){ %>
			
			<img style="width:70%;padding:0px 15% 0px 15% ;" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
			
		<%}else{ %>
			<img style="width:70%;padding:0px 15% 0px 15% ;"  src="images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" title="" align="" /> 
		<%} %>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;font-size:40px;" ><%=param %></p>

			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;font-size:40px;" >长按上方二维码，和<span style="color: red;"> <%=user.getNick_name() %></span>一起，更多好东东等着您！</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:30pt;font-family:KaiTi_GB2312;color:#E53333;line-height:1;"><strong>幸运生活，从<%=original.getMch_name()%>开始</strong></span> 
			</p>

<!--  			<img width="100%" src="images/lottery/fx3.jpg" alt=""  title="" align="" /> 
 -->
			
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:30pt;line-height:1;">-最终解释权归<%=original.getMch_name() %>所有-</span> 
			</p>
		
			
		
	</div>
	
  </body>
</html>
