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
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="yes" name="apple-touch-fullscreen">
<meta content="telephone=no,email=no" name="format-detection">
<meta name="App-Config" content="fullscreen=yes,useHistoryState=yes,transition=yes">
<title>合肥万达乐园开业啦!免费体验快来抢</title>
<meta name="description" content="合肥万达乐园开业啦!免费体验快来抢">
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
<div>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">活动奖品</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">合肥万达主题乐园成人票（高峰票）2张</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">活动日期</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">报名截止：2016年9月30日24点</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">获奖公布：2016年9月31日17点</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">抽奖方式</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">1）关注我们就赠送您一个抽奖号，希望获得更多抽奖号详见公众号内活动说明</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">2）抽奖号后4位与9月31日当天“福彩天天彩选4”开奖结果一致即为中奖</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">注：查询“福彩天天彩选4”开奖情况点这里</span></strong></p>


		<p><img src="<%=basePath%>images/activity/wanda/WanDa.jpg" alt=""  title="" ></div></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">包揽多项“世界之最”的</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">合肥万达乐园9月24日开呀啦</span></strong></p>
		
				<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">合肥万达乐园内含三大园区</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">全球首座徽文化主题乐园</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">华东最大的室内恒温水乐园</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">高科技电影乐园</span></strong></p>

		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">世界最高立环过山车</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/GuoShanChe.jpg" alt=""  title="" ></div></p>
			
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">世界首创的双层漂流河</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/PiaoLiu.jpg" alt=""  title="" ></div></p>

		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">世界水桶容量最大的巨型水寨</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/Shui3.jpg" alt=""  title="" ></div></p>

				<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">国内直径最大的大喇叭滑道</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/DaLaBa.jpg" alt=""  title="" ></div></p>
			

		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">摩天轮 以合肥市花桂花主题的配色</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">轮架是以渐变色过度表现花蕊特点</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/MoTianLun2.jpg" alt=""  title="" ></div></p>
		

		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">古老的“木船”冲刺26米高的的茶山飞渠</span></strong></p>
		<p><img src="<%=basePath%>images/activity/wanda/Chuan1.jpg" alt=""  title="" ></div></p>

		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">活动奖品</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">合肥万达主题乐园成人票（高峰票）2张</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">&nbsp;</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">活动日期</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">报名截止：2016年9月30日24点</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">获奖公布：2016年9月31日17点</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">抽奖方式</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">每成功邀请1名好友参加活动就获得一个抽奖号码</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">抽奖号后4位与9月31日当天“福彩天天彩选4”开奖结果一致即为中奖号</span></strong></p>
		<p style="text-align: center;"><strong><span style="font-size: 14px; color:red">注：查询“福彩天天彩选4”开奖情况点这里</span></strong></p>
</div>
	
</header>


    <section>
	   <div class="search" id="scanQRCodeDiv" <%=qrcodeHide %> >
	   		 
  		<%if(ticket != null){ %>
			
			<img width="70%" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt=""  title="" align="" /> 
		
			
		<%}else{ %>
			<img width="70%" src="<%=basePath%>images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" title="" align="" /> 
		<%} %>
			 </p>
        <span class="inbtn2" id="" onclick="alert('请长按二维码关注公众号，参与免费游万达乐园活动！')">参与免费游乐园活动</span> 
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