<%@page import="com.yd.business.user.bean.UserSenceLog"%>
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
// String userSenceLogId = String.valueOf(request.getAttribute("userSenceLogId"));
/*String title = user.getNick_name() + " 分享了5元红包给你，快来领取！"; */
String title =  "猜奖牌赢流量！最高12G！为中国奥运加油！" ; 
String desc = "更有"+ user.getNick_name() + "送你10元流量红包，拿走不谢！我是雷锋"  ; 
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
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
  </head>
  <script type="text/javascript">
	
    var share_time_ms = weixinInit.getShare_time_ms();
    
    
	function showBodyDiv(){
		setTimeout("document.getElementById('body_div').style.display = 'block';document.title='邀请好友送豪礼';",2000);
	}
	function shareTimeFirendSuccess(){
	  	$.ajax({
			type : "POST",
			url : "user/handleUserShare.do",
			data : {"openid": '<%=user.getOpenid() %>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %> 
			, share_from:"<%=UserSenceLog.SHARE_FROM_INVITE_FIRENDS %>",share_time_ms:share_time_ms },
			success : function(data) {
			}
		});
  	}
  	function shareOneFirendSuccess(){
	  	$.ajax({
			type : "POST",
			url : "user/handleUserShare.do",
			data : {"openid": '<%=user.getOpenid() %>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_ONE %> 
			, share_from:"<%=UserSenceLog.SHARE_FROM_INVITE_FIRENDS %>",share_time_ms:share_time_ms },
			success : function(data) {
			}
		});
  	}
    
	weixinInit.setShareLink('<%=BaseContext.getDefault_share_url()%>?fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
	weixinInit.setShareTitle('<%=BaseContext.getDefault_share_title()%>');
	weixinInit.setShareImg('<%= user == null? basePath+"images/icon/4g.png":user.getHead_img() %>');
	weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
	
  	
  	weixinInit.setOnShareAppMessageSuccess(shareOneFirendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
  </script>
  
<body id="body_div" class="weixinreg_xmas_body" style="overflow-x: hidden;margin: 0;display: none;" onload="showBodyDiv()">
    
       <%--  <div class="regdonetit">亲爱的【<%=user.getNick_name() %>】 <br> </div>
        <div class="regtipinfo"><br>
        	
        </div> --%>
        <img src="images/event/invite_firends.jpg" alt="" style="width: 100%;">
     
<input type="hidden" id="isapptxt" value="0"><%-- 
<input type="hidden" id="userSenceLogId" value="<%=userSenceLogId%>"> --%>
<!--  -->


    <!-- footer -->

   
  </body>
</html>
