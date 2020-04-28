<%@page import="com.yd.business.wechat.util.WechatConstant"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserWechatBean user = (UserWechatBean)request.getAttribute("user");
UserQrCodeBean ticket = (UserQrCodeBean)request.getAttribute("ticket");

boolean userIsNull = user == null;
String openid = user == null ? "" : user.getOpenid();
String qrcodeHide = "style=\"display: none;\" ";
String shareFriendHide = "style=\"display: none;\" ";
String loadingImgHide = "";
String bodyHide ="style=\"background:url();\"";
String tips = "";
String urlpre = "http://act.ahmobile.cn";
WechatOriginalInfoBean original = null;
boolean isShared = false;
if(userIsNull){
	qrcodeHide = "";
	loadingImgHide = "display:none;";
}else{
	original = BaseContext.getWechatOriginalInfo(user.getOriginalid());
	bodyHide  = "style=\"display: none;z-index: 2;background:url();\" ";
	shareFriendHide = "style=\"width:90%;padding:0 5%;\"";
	tips ="恭喜【"+user.getNick_name()+"】获得了免费领取流量机会";
	
//	tips ="很遗憾，您来晚了，名额已经满啦！";

	if(user.getShare_time() != null && user.getShare_time().substring(0, 10).equals(DateUtil.getNowDateStr().substring(0, 10)) ){
		isShared = true;
	}
}
%>
<!DOCTYPE html>
<html style="font-size:58.59375px !important">
<head>
<base href="<%=basePath %>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes">
<title>1G流量免费送,限时领取！实测有效！拿走不谢！</title>
<meta name="description" content="全民领流量,1G免费送,实测有效！名额有限！先到先得！">
<link rel="stylesheet" href="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/css/main.css" type="text/css">

<script src="js/jquery.js"></script>
<script src="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/js/public.js"></script>
<script type="text/javascript" src="js/wechat/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
</head>
<body id="bodyDiv" style="background:#171f21;" >
<div id="loadingImg" style="z-index: 1;position: absolute;top: 25%;left: 40%;<%=loadingImgHide %>" ><img style="width: 80px;" src="page/user/infoCenterCss/img/loding2.gif"></div>

<div id="wrap" <%=bodyHide %> >
  <!-- 头部 -->
    <header>
	<div><img src="<%=basePath%>images/activity/qiang1G.jpg" alt=""  title="" ></div>
</header>
    <section>
	<div class="search" style="display: none;" id="inputPhoneDiv"  >
    	<p><input name="" id="phone" type="text" class="input1" value="输入手机号" onfocus="if(value=='输入手机号') {value=''}" onblur="if (value=='') {value='输入手机号'}"></p>
    	<p><span class="inbtn1" id="yzm_btn" onclick="yzm()">获取验证码</span><input name="" type="text" class="input2" id="smsYzm" value="输入短信验证码" onfocus="if(value=='输入短信验证码') {value=''}" onblur="if (value=='') {value='输入短信验证码'}"></p>
        <span class="inbtn2" id="grab_btn" onclick="grab()">领取免费流量</span>
    </div>
     <div class="search" id="scanQRCodeDiv" <%=qrcodeHide %> >
	 
    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >&nbsp;</span></p>
	 <p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >免费领流量(仅安徽移动)，限时至10月31日止，赶快来参加活动吧！</span></p>

<!--     	<p><input name="" id="" type="text" class="input1" style="" value="限时至10月31日止，赶快来参加活动吧！" style="color: red;" readonly="readonly"></p>
 -->     	<p>
		<%if(ticket != null){ %>
			
			<img width="70%" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
			 </p>
        	<span class="inbtn2" id="" onclick="alert('请长按二维码，参与免费领流量活动！')">领取免费流量</span> 
			
		<%}else{ %>
			<img width="70%" src="<%=basePath%>images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" title="" align="" /> 
			 </p>
		<%} %>
    </div>
    
    
    <div class="search" id="shareFirendDiv" <%=shareFriendHide %>  >
    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 16px;" >&nbsp;</span></p>

    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >限时至10月31日止，赶快来参加活动吧！</span></p>
    	
    	<%if(user != null && user.getParentid() != null){ %>
    	<p >
    		<span style="font-size: 12px;color:white;" >雷锋精神是啥？是分享啦！有这么好的事情，还不通知好友一起来参加嘛？</span>
<%--     		<img width="90%" src="<%=basePath%>images/event/weixin-sharepic-friends.jpg" alt="">
 --%>    	</p>
    	<%} %>
    	    	<p style="height: auto;">
    	    	
   			
  <!--			<span style="font-size: 14px;color:white;" ><%=tips %></span>  -->
     	<span style="font-size: 12px;color:white;" >温馨提示：<br> 1）活动仅限安徽移动用户！</span>
<br> 	<span style="font-size: 12px;color:white;" >2）每个用户限参与1次！</span>
    	    	</p>
    	
    	<%if(user != null &&  user.getParentid() != null){
    	 %>
    	 
        	<span class="inbtn2" id="" onclick="alert('请先分享到朋友圈，即可填写手机号领取免费流量！')">领取免费流量</span> 
    	 <%}else{ %>
    		<span class="inbtn2" id="" onclick="location.href='<%=urlpre%>:8000/activity/deploy/ah/2016/kbx/index_wx.html'">领取免费流量</span> 
        <%} %>
      <br> <br>   
         
    </div> 
<!--     <div class="txt1">
    	<p>活动时间：7月28日-7月31日</p>
        <p>活动对象：安徽移动用户</p>
        <p>活动规则：</p>
        <p>1、输入您的手机号和验证码，点击领取流量。</p>
        <p>2、活动期间，每个用户只有一次参与活动的机会。</p>
        <p>3、欠费及黑名单用户不得参与此活动。</p>
        <p>4、本活动赠送的套餐外优惠流量将在您无流量套餐时使用。</p>
    </div> -->
</section>


    </div>
    
<div class="showtc tcbox1">
    <p id="ts1">验证码已过期，请重新获取！</p>
    <span class="tcan">知道了</span>
</div>
<div class="showtc tcbox2">
    <p id="ts2">短信发送成功</p>
</div>


<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>


<script src="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/js/jq_sw.js" type="text/javascript"></script>
<script src="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/js/zp_pop_sdc.js" type="text/javascript"></script>

<script type="text/javascript"> 

$(document).ready(function(){
	// loadAcInfo();
	//window.self.location.href='prompt.html';
});



var wait = 60;
var yzm_timer;
   


function is_weixn(){
    var ua = navigator.userAgent.toLowerCase();
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
        return true;
    } else {
        return false;
    }
}
</script>

<script>
	var share_time_ms = weixinInit.getShare_time_ms();
	function showBody(){
		$("#loadingImg").hide();
		$("#wrap").show();
	}
  	function shareFriendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": '<%=openid%>',"share_type": <%=UserSenceLog.SHARE_TYPE_FIREND_ONE %> ,"param":<%=WechatConstant.TICKET_SENCE_ID_ACTIVITY_QIANG1G%>,
		"share_from":"<%=UserSenceLog.SHARE_FROM_LIULIANG1G_ACTIVITY %>",share_time_ms:share_time_ms },
		success : function(data) {
		}
	});
		alert('界面即将跳转，请稍等');
 		location.href = "http://www.51bwc.com:8000/activity/deploy/ah/2016/kbx/index_wx.html";
// 	location.href = "http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/index.html";
  }
  function shareTimeFirendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": '<%=openid%>',"share_type": <%=UserSenceLog.SHARE_TYPE_FIREND_LINE %> ,"param":<%=WechatConstant.TICKET_SENCE_ID_ACTIVITY_QIANG1G%>,
		"share_from":"<%=UserSenceLog.SHARE_FROM_LIULIANG1G_ACTIVITY %>",share_time_ms:share_time_ms },
		success : function(data) {
		}
	});

		alert('界面即将跳转，请稍等');
 		location.href = "http://www.51bwc.com:8000/activity/deploy/ah/2016/kbx/index_wx.html";
  }
  		<%if(userIsNull){%>
  			weixinInit.setShareLink('http://www.fuli0551.cc/llshop/wechat/user/qiang1GActivity.htm?fromOpenid=<%=openid %>&share_time_ms='+share_time_ms);
  		<%} else{ %>
  			weixinInit.setShareLink('<%=original.getServer_url()%>wechat/user/qiang1GActivity.htm?fromOpenid=<%=openid %>&share_time_ms='+share_time_ms);
  		<%}%>
		weixinInit.setShareTitle('1g流量免费领 实测100%有效  小手一抖 流量我有');
		weixinInit.setShareImg('<%= basePath+"images/activity/qiang1G.jpg" %>');
		weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
  
//	weixinInit.setShareTitle('1G流量免费送,限时领取！实测有效！拿走不谢！');

  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
	
	setTimeout(showBody,2900);
</script>

<script type="text/javascript">_tag.trackEvent();</script>


</body></html>