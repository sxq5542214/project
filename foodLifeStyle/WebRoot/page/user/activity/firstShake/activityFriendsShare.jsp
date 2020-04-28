<%@page import="com.yd.business.activity.bean.ActivityUserRelationBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
UserWechatBean user = (UserWechatBean) request.getAttribute("user");
ActivityUserRelationBean relation = (ActivityUserRelationBean) request.getAttribute("relation");
// String userSenceLogId = String.valueOf(request.getAttribute("userSenceLogId"));
/*String title = user.getNick_name() + " 分享了5元红包给你，快来领取！"; */
String title = ""+ user.getNick_name() +"摇到了"+relation.getProduct_name()+"！关注即可参加，人人有份！拿走不谢！我是雷锋！" ;
String desc = "疯狂摇一摇，流量免费送！更有10元流量红包等着您，拿走不谢！我是雷锋" ; 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>加载中，请稍等</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
	
	<link href="css/bootstrap.css" rel="stylesheet">
	<link href="css/event/supplier-event.css" rel="stylesheet">
	<link href="css/event/supplier-event-ex.css" rel="stylesheet">
	<link href="css/event/weixinactive_signup.css" rel="stylesheet">
	<script src="js/jquery.js"></script>
	<script type="text/javascript" src="js/user/activity/activityFriendsShare.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
  </head>
  <script type="text/javascript">
  	weixinInit.setShareLink('<%=basePath%>activity/user/showFriendsGetActivityInfo.do?openid=<%=user.getOpenid()%>&relation_id=<%=relation.getId()%>');
  	weixinInit.setShareTitle('<%=title%>');
  	weixinInit.setShareImg('<%=user.getHead_img() %>');
  	weixinInit.setShareDesc('<%=desc%>');
  	
  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimelineSuccess);
	setTimeout("document.getElementById('body_div').style.display = 'block';",2000);
  </script>
  
<body class="weixinreg_xmas_body" style="overflow-x: hidden;" onload="showBodyDiv()">
    
<div class="weixinreg_xmas_body2"></div>
<div class="reg-body" id="body_div" style="display: none;">
    <section class="reg-top">
        <div class="rightarrow"><img src="images/event/weixin-arrow.png" alt=""></div>
        <div class="regdonetit">亲爱的【<%=user.getNick_name() %>】 <br>
        	恭喜您抢到了【<%=relation.getProduct_name() %>】</div>
         <div class="regtipinfo">点击右上角，转发朋友圈，即可填写号码领取流量并获得首单6折优惠特权！<br>
      <div class="regsharepic"><img src="images/event/weixin-sharepic-friends.jpg" alt=""></div>
 
		<br>
	
	
	<!--		
	<div class="regtipinfo">好友直接扫描下方您的专属二维码关注公众号，您和您的好友将享有福利二、福利三！</div>
		 <div class="regsharepic">
			
		<%if(ticket != null){ %>
			
			<img width="70%" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
		
			
		<%}else{ %>
		<%} %>
			
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">更多美好的事情与您一起分享</p>
			
		</div>
	-->

	</section>
</div>
<input type="hidden" id="openid" value="<%=user.getOpenid() %>">
<input type="hidden" id="relation_id" value="<%=relation.getId() %>">

<!--  -->


    <!-- footer -->

    <div class="container footer hidden-xs">
        <div class="row">
            <div class="col-xs-12 text-center">
               <!--  ICP沪   号  &nbsp;
					|&nbsp;  © 2015-2016, all rights reserved -->
            </div>
        </div>
    </div>
  </body>
</html>
