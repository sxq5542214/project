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
String bodyHide ="";
String tips = "";
if(userIsNull){
	qrcodeHide = "";
}else{
	bodyHide  = "style=\"display: none;\" ";
	shareFriendHide = "";
	tips ="恭喜【"+user.getNick_name()+"】获得免费1000分钟通话机会";
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
<title>1000分钟通话免费送！限时领取，名额有限！实测有效，拿走不谢！</title>
<meta name="description" content="1000分钟通话免费送！限时领取，名额有限！实测有效，拿走不谢！">
<link rel="stylesheet" href="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/css/main.css" type="text/css">

<script src="js/jquery.js"></script>
<script src="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/js/public.js"></script>
<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
</head>
<body id="bodyDiv" <%=bodyHide %> >
<img width="0" height="0" style="position: absolute;" src="http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/images/ico.jpg">
<div id="wrap">
  <!-- 头部 -->
    <header>
	<div><img src="images/activity/volteCallActivity_head.jpg" alt=""  title="" ></div>
</header>
    <section>
	<div class="search" style="display: none;" id="inputPhoneDiv"  >
    	<p><input name="" id="phone" type="text" class="input1" value="输入手机号" onfocus="if(value=='输入手机号') {value=''}" onblur="if (value=='') {value='输入手机号'}"></p>
    	<p><span class="inbtn1" id="yzm_btn" onclick="yzm()">获取验证码</span><input name="" type="text" class="input2" id="smsYzm" value="输入短信验证码" onfocus="if(value=='输入短信验证码') {value=''}" onblur="if (value=='') {value='输入短信验证码'}"></p>
        <span class="inbtn2" id="grab_btn" onclick="grab()">领取通话时长</span>
    </div>
     <div class="search" id="scanQRCodeDiv" <%=qrcodeHide %> >
    	<p><input name="" id="" type="text" class="input1" style="" value="请长按二维码参与1000分钟电话免费送活动" style="color: red;" readonly="readonly"></p>
     	<p>
		<%if(ticket != null){ %>
			
			<img width="70%" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
		
			
		<%}else{ %>
			<img width="70%" src="images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" title="" align="" /> 
		<%} %>
			 </p>
        <span class="inbtn2" id="" onclick="alert('请长按二维码关注公众号，参与1000分钟电话免费送活动！')">领取通话时长</span> 
    </div>
    <div class="search" id="shareFirendDiv" <%=shareFriendHide %>  >
    	<p><input name="" id="" type="text" class="input1" value="<%=tips %>" style="color: red;" readonly="readonly"></p>
    	<p >
    		<img width="90%" src="images/event/weixin-sharepic-friends.jpg" alt="">
    	</p>
        <span class="inbtn2" id="" onclick="alert('请先分享到朋友圈，即可参与1000分钟电话免费送活动！')">领取通话时长</span> 
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
	loadAcInfo();
	//window.self.location.href='prompt.html';
});

function loadAcInfo(){
	$.ajax({
		type : "POST",
		url : "http://act.ahmobile.cn:8000/activity/marketplat/localActivity/yxgrab/loadQmqllAllAcInfo.do?t="+new Date().getTime(),
		dataType : "json",
		data : {},
		success : function(data) {
			if(data.code){
				if(data.code=='0601'){
					window.self.location.href='prompt.html';
				}
			}else{

				if(data.returnCode != '0000'){
					$('.me_txt span img').each(function(index,el){
						$(this).attr("src","http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/images/num"+data.resultId.charAt(index)+".png");
					});
					$('#ts1').html(data.returnMsg);
					
					$('#yzm_btn').addClass("hui");
					$('#grab_btn').addClass("hui");
					$('#yzm_btn').removeAttr("onClick");
					$('#grab_btn').removeAttr("onClick");
					toyhj();
				}else{
					$('.me_txt span img').each(function(index,el){
						$(this).attr("src","http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/images/num"+data.resultId.charAt(index)+".png");
					});
					$('#yzm_btn').removeClass("hui");
					$('#grab_btn').removeClass("hui");
					
				}
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		}
	});
}
function yzm(){
	
	var phone = $.trim($("#phone").val());
	if(phone == "输入手机号") { 
	$('#ts1').html("请输入手机号码!"); toyhj();   return; }
	else if(phone.length != 11) { 
	$('#ts1').html("手机号码位数不对!");toyhj();  return; }
	else if(!/^\d{11}$/.test(phone)) { 
	$('#ts1').html("手机号码格式不对!"); toyhj();    return; }
	
	$('#yzm_btn').html("重新获取验证码");
	$('#yzm_btn').addClass("hui");
	$.ajax({
		type : "POST",
		url : "http://act.ahmobile.cn:8000/activity/marketplat/localActivity/yxgrab/beforeQmqllAllGrabCode.do?t="+new Date().getTime(),
		dataType : "json",
		data : {"phone":$('#phone').val()},
		success : function(data) {
			if(data.returnCode == '0000'){
				//倒计时60s
				yzm_time();
				$('#grab_btn').removeClass("hui");
				toyhj2();
			}else{
			alert(data.returnMsg);
				$('.cover').show();
				$('#ts1').html(data.returnMsg);
				$('#grab_btn').addClass("hui");
				$('#yzm_btn').addClass("hui");
				$('#grab_btn').removeAttr("onClick");
				$('#yzm_btn').removeAttr("onClick");
				toyhj();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert('error');
		}
	});
}

function grab(){
	
	var phone = $.trim($("#phone").val());
	if(phone == "输入手机号") { 
	$('#ts1').html("请输入手机号码!"); toyhj();   return; }
	else if(phone.length != 11) { 
	$('#ts1').html("手机号码位数不对!"); toyhj();  return; }
	else if(!/^\d{11}$/.test(phone)) {  
	$('#ts1').html("手机号码格式不对!");toyhj();   return; }
	
	var smsYzm = $.trim($("#smsYzm").val());
	if(smsYzm == "输入短信验证码") { 
	$('#ts1').html("请输入短信验证码!"); toyhj(); return; }
	else if(smsYzm.length != 6) { 
	$('#ts1').html("短信验证码位数不对!"); toyhj();  return; }
	else if(!/^\d{6}$/.test(smsYzm)) {  
	$('#ts1').html("短信验证码格式不对!");toyhj();   return; }
	
	$('#yzm_btn').removeClass("hui");
	$('#grab_btn').addClass("hui");
	clearTimeout(yzm_timer);
	$('#yzm_btn').html("获取短信验证码");
	$('#yzm_btn').attr("onClick","yzm()");
//	alert(phone +","+smsYzm);
	$.ajax({
		type : "POST",
		url : "http://act.ahmobile.cn:8000/activity/marketplat/localActivity/yxgrab/grabQmqllAllCode.do?t="+new Date().getTime(),
		dataType : "json",
		data : {"phone":phone,"smsYzm":smsYzm},
		success : function(data) {
		alert(data);
			if(data.returnCode == '0000'){
				if(is_weixn()){
					location.href='http://mp.weixin.qq.com/s?__biz=MzI3NjMzMzYxOQ==&mid=100000041&idx=1&sn=2a416bf159aeacb7ec7f8a0b76934cad#rd';
				}else{
					location.href='http://mp.weixin.qq.com/s?__biz=MzI3NjMzMzYxOQ==&mid=100000041&idx=1&sn=2a416bf159aeacb7ec7f8a0b76934cad#rd';
				}				
			}else{
				
				$('#ts1').html(data.returnMsg);
				$('#grab_btn').removeClass("hui");
				$('#yzm_btn').removeClass("hui");
				$('#yzm_btn').attr("onClick","yzm()");
				$('#grab_btn').attr("onClick","grab()");
				toyhj();
			}
		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
		alert(textStatus);
		}
	});
}

var wait = 60;
var yzm_timer;
function yzm_time(){
	var btn=$("#yzm_btn");
   	if (wait == -1) {
   		btn.html('重发验证码');  wait = 60; 
   		btn.removeClass("hui");
		$('#yzm_btn').attr("onClick","yzm()");
    }else {
    	btn.html("重发验证码("+wait+")"); 
    	btn.addClass("hui");
		$('#yzm_btn').removeAttr("onClick");
    	wait--;
    	yzm_timer = setTimeout(function () { yzm_time(); }, 1000);
    }
}
   

function toyhj(){
	$(".tcbox1").addClass("am-acti-active");	
	if($(".sharebg").length>0){
		$(".sharebg").addClass("sharebg-active");
		
	}else{
		$("body").append('<div class="sharebg"></div>');
		
		$(".sharebg").addClass("sharebg-active");
		$("body").addClass("body_hidd");
	}
	$(".sharebg-active,.tcan").click(function(){
		$(".showtc").removeClass("am-acti-active");	
		setTimeout(function(){
			$(".sharebg-active").removeClass("sharebg-active");	
			$("body").removeClass("body_hidd");
			$(".sharebg").remove();				
		},0);
	})
}
function toyhj2(){
	$(".tcbox2").addClass("am-acti-active");	
	if($(".sharebg").length>0){
		$(".sharebg").addClass("sharebg-active");
		
	}else{
		$("body").append('<div class="sharebg"></div>');
		
		$(".sharebg").addClass("sharebg-active");
		$("body").addClass("body_hidd");
	}
	//$(".sharebg-active,.tcan").click(function(){
		
		setTimeout(function(){
			$(".showtc").removeClass("am-acti-active");	
			$(".sharebg-active").removeClass("sharebg-active");	
			$("body").removeClass("body_hidd");
			$(".sharebg").remove();				
		},3000);
	//})
}	

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
	function showBody(){
		$("#bodyDiv").show();
	}
  function shareFriendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/userSharedByScratch.do",
		data : {"openid": '<%=openid%>',"share_type":1},
		success : function(data) {
		}
	});
	location.href = "http://mp.weixin.qq.com/s?__biz=MzI3NjMzMzYxOQ==&mid=100000069&idx=1&sn=e6bcf36d560a074395ec84f4f6fc2672#rd";

// 	location.href = "http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/index.html";
  }
  function shareTimeFirendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/userSharedByScratch.do",
		data : {"openid": '<%=openid%>',"share_type":2},
		success : function(data) {
		}
	});
	location.href = "http://mp.weixin.qq.com/s?__biz=MzI3NjMzMzYxOQ==&mid=100000069&idx=1&sn=e6bcf36d560a074395ec84f4f6fc2672#rd";

 // 	location.href = "http://act.ahmobile.cn:8000/activity/deploy/anhui/qmqll/ah/index.html";
  }
  
  	weixinInit.setShareLink('<%=basePath%>wechat/user/volteCallActivity.htm?fromOpenid=<%=openid %>');
	weixinInit.setShareTitle('1000分钟通话免费送！限时领取，名额有限！实测有效，拿走不谢！');
  	weixinInit.setShareImg('<%= user == null? basePath+"images/qrcode/qrcode_for_zaixianjiayouzhan.jpg":user.getHead_img() %>');
  	weixinInit.setShareDesc('1000分钟通话免费送！限时领取，名额有限！实测有效，拿走不谢！');

  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
	
	setTimeout(showBody,2500);
</script>

<script type="text/javascript">_tag.trackEvent();</script>


</body></html>