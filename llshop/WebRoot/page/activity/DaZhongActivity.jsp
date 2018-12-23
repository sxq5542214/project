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
boolean isShared = false;
if(userIsNull){
	qrcodeHide = "";
	loadingImgHide = "display:none;";
}else{
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
	<div><img src="<%=basePath%>images/activity/lamando.jpg" alt=""  title="" ></div>
</header>
    <section>
	<div class="search" style="display: none;" id="inputPhoneDiv"  >
    	<p><input name="" id="phone" type="text" class="input1" value="输入手机号" onfocus="if(value=='输入手机号') {value=''}" onblur="if (value=='') {value='输入手机号'}"></p>
    	<p><span class="inbtn1" id="yzm_btn" onclick="yzm()">获取验证码</span><input name="" type="text" class="input2" id="smsYzm" value="输入短信验证码" onfocus="if(value=='输入短信验证码') {value=''}" onblur="if (value=='') {value='输入短信验证码'}"></p>
        <span class="inbtn2" id="grab_btn" onclick="grab()">领取免费流量</span>
    </div>
     <div class="search" id="scanQRCodeDiv" <%=qrcodeHide %> >
	 
    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >&nbsp;</span></p>
	 <p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >免费领流量名额已满，活动结束啦！</span></p>

    	<p><input name="" id="" type="text" class="input1" style="" value="名额已满，活动结束啦" style="color: red;" readonly="readonly"></p>
     	<p>
		<%if(ticket != null){ %>
			
			<img width="70%" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
		
			
		<%}else{ %>
			<img width="70%" src="<%=basePath%>images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" title="" align="" /> 
		<%} %>
			 </p>
        <span class="inbtn2" id="" onclick="alert('请长按二维码关注公众号，参与免费领流量活动！')">领取免费流量</span> 
    </div>
    
    
    <div class="search" id="shareFirendDiv" <%=shareFriendHide %>  >
    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 16px;" >&nbsp;</span></p>

    	<p style="height: auto;text-align: center;margin: 0 auto;"><span style="color: red;font-size: 14px;" >免费领流量名额已满，活动结束啦！</span></p>
    	<p >
    		<img width="90%" src="<%=basePath%>images/event/weixin-sharepic-friends.jpg" alt="">
    	</p>
    	    	<p style="height: auto;">
    	    	
   			
  <!--			<span style="font-size: 14px;color:white;" ><%=tips %></span>  -->
     	<span style="font-size: 12px;color:white;" >温馨提示：<br> 1）因活动火爆，分享后的活动页面加载会有点慢，请耐心等待至出现下图【领取流量包】按钮点击即可领取流量！</span>
<br> 	<span style="font-size: 12px;color:white;" >2）部分机型可能会出现链接失效领取失败的现象，请找身边的朋友换一个手机试试，100%真实有效！</span>
<br>  <span style="font-size: 12px;color:white;" >3）由于拥塞，到账通知短信可能不会及时到达，请于1小时后通过运营商App或网上营业厅自助查询流量到账情况！</span>
    	    	</p>
    	
    	<%if(isShared){
    	 %>
    	 
        <span class="inbtn2" id="" onclick="location.href='http://xc.aipipi.mobi/a/4za5Uy'">领取免费流量</span> 
    	 <%}else{ %>
        <span class="inbtn2" id="" onclick="alert('请先分享到朋友圈，即可填写手机号领取免费流量！')">领取免费流量</span> 
        <%} %>
      <br> <br>   
    	<img width="90%"  src="<%=basePath%>images/activity/caozuo.png" alt=""  >
         
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
		data : {"openid": '<%=openid%>',"share_type": <%=UserSenceLog.SHARE_TYPE_FIREND_ONE %> ,
		"share_from":"<%=UserSenceLog.SHARE_FROM_LIULIANG1G_ACTIVITY %>",share_time_ms:share_time_ms },
		success : function(data) {
		}
	});

 		location.href = "http://xc.aipipi.mobi/a/4za5Uy";
// 	location.href = "http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/index.html";
  }
  function shareTimeFirendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": '<%=openid%>',"share_type": <%=UserSenceLog.SHARE_TYPE_FIREND_LINE %> ,
		"share_from":"<%=UserSenceLog.SHARE_FROM_LIULIANG1G_ACTIVITY %>",share_time_ms:share_time_ms },
		success : function(data) {
		}
	});

 		location.href = "http://xc.aipipi.mobi/a/4za5Uy";
  }
  		weixinInit.setShareLink('<%=BaseContext.getDefault_share_url()%>?fromOpenid=<%=openid %>&share_time_ms='+share_time_ms);
		weixinInit.setShareTitle('<%=BaseContext.getDefault_share_title()%>');
		weixinInit.setShareImg('<%= basePath+"images/activity/shareShangQi.jpg" %>');
		weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
  
//	weixinInit.setShareTitle('1G流量免费送,限时领取！实测有效！拿走不谢！');

  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
	
	setTimeout(showBody,2500);
</script>

<script type="text/javascript">_tag.trackEvent();</script>


</body></html>