<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
OrderProductLogBean bean = (OrderProductLogBean)request.getAttribute("orderLog");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>充值结果</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="max-age=300">
    <meta content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
   
	<link rel="stylesheet" type="text/css"	href="css/supplier/stylesd.css">
	<link rel="stylesheet" type="text/css"	href="css/supplier/reset.css">
	<style type="text/css">
			.banner1{
	    		display: block;
	    		width: 100%;
	    		height: auto;
	    		overflow: hidden;
	    	}
			.ggl{
	    		position: relative;
	    		width: 85.6%;
	    		height: 90px;
	    		margin: 20px auto;
	    		background: url(page/user/activity/scratch/img/ggl.png) no-repeat center center;
	    		background-size: 100% 100%;
	    	}
	    	.canvas{
				position:absolute;
				top:2px;
				left:2.5%;
				width:95%;
				height:82px;
				line-height:82px;
				text-align:center;
				z-index: 2;
				
			}
			.info{
				position:absolute;
				top: 25%;
				left:2.5%;
				width:95%;
				text-align:center;
			}
			.info span{
				display: block;
				font-size: 18px;
			}
			#prompt{
				line-height: 40px;
			}
			.btn{
				position: relative;
				width: 50%;
				height: 35px;
				line-height: 35px;
				background: #df412b;
				color: #fff;
				border-radius: 5px;
				margin: 0 auto;
				text-align: center;
			}
			.guize{
				display: block;
				width: 85.6%;
				height: auto;
				margin: 5% auto 10% auto;
				border-radius: 5px;
			}
			.num{
				width: 100%;
				height: 30px;
				line-height: 30px;
				text-align: center;
				font-size:14px;
				margin-top: 5%;
			}
			#ok, #no{display: none;}
			.pop{
				position: fixed;
			 	left: 0;
			 	top: 0;
			 	z-index: 3;
			 	width: 100%;
			 	height: 100%;
			 	background: rgba(0,0,0,0.6);
			 	display: none;
			}
			.pop img{
				width: 100%;
				height: auto;
				overflow: hidden;
				margin: 15% auto;
			}
	    </style>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/wechat/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	<script type="text/javascript" src="page/user/infoCenterJs/orderResult.js"></script>
	<script type="text/javascript">
		$(document).ready(function($) {
		var share_time_ms = weixinInit.getShare_time_ms();
		function shareTimeFirendSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_ORDER_RESULT %>",share_time_ms:share_time_ms },
				success : function(data) {}
			});
		  	var orderId = $("#orderId").val();
		  	var openid = $("#openid").val();
		  	var is_sended = $("#is_sended").val();
		  	if(is_sended == 1){
		  		$("#ok").remove();
		  		return;
		  	}
		  	if(isOk > 0){
		  	  	$.ajax({
		  			type : "POST",
		  			url : "order/userSharedByOrderProduct.do",
		  			async : false,
		  			data : {"orderId": orderId,"share_type":2,"openid" : openid},
		  			success : function(data) {
		  				var result = eval("("+data+")");
		  				var now_issended = result.is_sended;
		  				//alertMsg(now_issended);
		  				alert(result.remark);
		  				$("#shareFriend").val('true');
		  				$("#is_sended").val(result.is_sended);
		  			}
		  		});
		  	}else if(is_sended != 1){
		  		alert("请刮奖！")
		  	}
  }
		
		
		
		setTimeout('$("#body_div").show(); ',2000);
		weixinInit.setShareLink('<%=BaseContext.getDefault_share_url()%>?fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
		weixinInit.setShareTitle('<%=BaseContext.getDefault_share_title()%>');
		weixinInit.setShareImg('<%= user == null? basePath+"images/icon/4g.png":user.getHead_img() %>');
		weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
		weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
		var is_sended = $("#is_sended").val();
		var luckymoney = $("#luckymoney").val();
  		
		showScratchDiv();	
		if(is_sended == 1 || is_sended == 2 || luckymoney == ""||luckymoney == "null" || luckymoney == null){
  			$("#ok").css('display','none'); 
  			if(luckymoney != 0 ){
  				$("#c1").css('display','none'); 
  			}
  		}
  		if(luckymoney == 0 || is_sended == -2){
  			$("#top").css('display','none'); 
  			$("#ok").css('display','none'); 
  		}
		$("#ok").click(function(){
		
		if($("#is_sended").val() == 1 || $("#is_sended").val() == 2 || $("#is_sended").val() == -2){
			alert($("#remark").val());
			return;
		}
		if(luckymoney == 0){
			alert("很遗憾！未中奖！");
			return;
		}
		alert("分享到朋友圈，即可领取微信现金红包！");
		
		});
		
/*		function alertMsg(now_issended){
			if(now_issended == -2){
		  		alert("异常订单，不发送红包！");
		  	}else if(now_issended == -1){
		  		alert("红包发送失败，请联系管理员！");
		  	}else if(now_issended == 0){
		  		alert("红包未发送！");
		  	}else if(now_issended == 1){
		  		alert("红包发送成功，请在公众号首页查收！");
		  	}else if(now_issended == 2){
		  		alert("由于微信限制，红包金额已入现金账户余额，注意查收！");
		  	}
		}*/
	});
	
	</script>
	
</head>
<body class="ng-scope" style="background-color: #F3F9F9;">
<div style="z-index: 1;position: absolute;top: 25%;left: 40%;"><img style="width: 80px;" src="page/user/infoCenterCss/img/loding2.gif"></div>
<div id="body_div" style="display:none;z-index: 2;position: absolute;width: 100%;background-color: #F3F9F9;"><!-- ngView: undefined --><ng-view class="ng-scope">

<div   class="ng-scope">
<div class="width320">
  <div class="con">
    <h3>充值结果</h3>
    <h2>支付金额：¥<span id="price" class="ng-binding"><%=(bean.getCost_money() + bean.getCost_balance())/100d %></span>元</h2>
  </div>

  <div class="menu">
    <div class="downmenu">
      <h2>支付方式：<span  class="ng-binding">微信支付</span></h2>
    </div>
  
    <div class="downmenu">
      <h2>充值结果：<span  class="ng-binding"><%=bean.getDictValueByField(OrderProductLogBean.DICT_FIELD_STATUS) %> </span></h2>
    </div>
    
    
    <div class="downmenu">
      <h2>结果说明：<span  class="ng-binding"><%= StringUtil.convertNull(bean.getRemark()) %></span></h2>
    </div>
    
  </div>
  		<div class="ggl" id="top" style="display: none;">
			<div class="info" id="prize">
				<span id="prompt" style="line-height:  normal;"></span>
			</div>
			<canvas id="c1" class="canvas"></canvas>
		</div>
		<span class="btn" id="ok">领取红包</span>
</div>


</div>
</ng-view>
<input type="hidden" id="openid" value="<%=user.getOpenid() %>">
<input type="hidden" id="orderId" value="<%=bean.getId() %>">
<input type="hidden" id="luckymoney" value="<%=StringUtil.convertNull(bean.getLucky_money()) %>">
<input type="hidden" id="is_sended" value="<%=bean.getIs_sended() %>">
<input type="hidden" id="remark" value="<%=bean.getRemark() %>">
<input type="hidden" id="shareFriend" value="false">
<input type="hidden" id="shareTimeFriend" value="false">
</div>
</body></html>
