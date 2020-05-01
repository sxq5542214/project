<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SupplierProductBean sp = (SupplierProductBean)request.getAttribute("supplierProduct");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
String phone = (String)request.getAttribute("phone");
//用户可用的积分 
int points = user.getPoints();
//商品最多可用积分
int limit_points = sp.getProduct_limit_points();
if(points > limit_points){
	points = limit_points;
}
double price = sp.getProduct_price()  /100d * sp.getDiscount() / 100d;

double cost_balance = user.getBalance()/100d > price  - points/100d  ? price : user.getBalance()/100d + points /100d;
cost_balance = cost_balance - points /100d;

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html >
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8">
	<style type="text/css">@charset "UTF-8";[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide{display:none !important;}ng\:form{display:block;}.ng-animate-block-transitions{transition:0s all!important;-webkit-transition:0s all!important;}.ng-hide-add-active,.ng-hide-remove{display:block!important;}</style>
    <title>支付订单</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta http-equiv="Cache-Control" content="max-age=300">
    <meta content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="format-detection" content="telephone=no">
   
	<link rel="stylesheet" type="text/css"	href="css/supplier/stylesd.css">
	<link rel="stylesheet" type="text/css"	href="css/supplier/reset.css">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/user/activity/buyGiveOne/orderConfirm.js"></script>
</head>
<body class="ng-scope">
<div><!-- ngView: undefined --><ng-view class="ng-scope">

<div   class="ng-scope">
<div class="width320">
  <div class="con">
    <h3>支付确认</h3>
    <h2>支付金额：¥<span id="price" class="ng-binding"><%=price  %></span>元</h2>
  </div>

  <div class="menu">
    <div class="downmenu">
      <h2>支付内容：<span  class="ng-binding"><%=phone %></span>&nbsp;抢购【<span  class="ng-binding"><%=sp.getProduct_name() %></span>】</h2>
    </div>
    
    <div class="downmenu">
      <h2>支付方式：<span  class="ng-binding">微信支付</span></h2>
    </div>
    
    <% if(user != null){ %>
    <input type="hidden" id="openid" value="<%=user.getOpenid()%>">
    <div class="downmenu">
      <h2>您拥有<span class="ng-binding" ><%=user.getPoints()/100d %></span>元积分，本商品可使用<span class="orderMinusTip ng-binding" id="points"><%=points/100d %></span>元积分</h2>
       <div onclick="changePointsBtn()" id="closeBg" class="pointclose">
        <div id="btnBg"  class="btnclose"></div>
    	</div>
    </div>
    
    <% if(user.getBalance() != null && user.getBalance() > 0){ %>
     <div class="downmenu">
      <h2>账户余额：<span class="ng-binding" ><%=user.getBalance()/100d %></span>元,本次支付将扣除<span id="cost_balance"><%=cost_balance %></span>元 </h2>
       <!-- <div style="width: 60px;height: 30px;border-radius: 25px;position: absolute;top: 5px;right: 0px;" class="pointopen">
        <div style="width: 30px;height: 30px;border-radius: 18px;position: absolute;background: white;box-shadow: 0px 1px 2px rgba(0,0,0,0.4);" class="btnopen"></div>
    	</div> -->
    </div>
<%}} %> 
	

    <div class="downmenu"  id="eff_num" style="height:80px;">
    <h2 style="float: left;">订购生效时间：</h2>
    		<h2 style="float: right;padding-right: 10%;"> 
				<input type="radio" name="eff_num"value="0"  id="eff0" checked>
				<label style="padding-right: 10px;" for="eff0"  class="eff_num0" >立即生效</label>
				<input type="radio" name="eff_num"value="1" id="eff1" >
				<label style="padding-right: 10px;" for="eff1"  class="eff_num1" ><%=DateUtil.getNextMonth(1) %>月1号</label>
			</h2>
			<h2 style="float: right;padding-right: 10%;">
				<input type="radio" name="eff_num"value="2"  id="eff2">
				<label style="padding-right: 10px;"  for="eff2"  class="eff_num2" ><%=DateUtil.getNextMonth(2) %>月1号</label>
				<input type="radio" name="eff_num" value="3"  id="eff3">
				<label style="padding-right: 10px;"  for="eff3"  class="eff_num3"><%=DateUtil.getNextMonth(3) %>月1号</label>
			</h2>
			</div>
  <div class="clear"></div>
  </div>
  
<div class="ejqrzf" style="width: 98%" id="show">
  <a href="javascript:;" class="hrefbtn" onclick="pay()">  确认支付 </a>
 <!-- 
    <a href="#" class="ejqr" >确认</a>
    <a href="#" class="ejqx" >取消</a> -->
</div>
<div id="loader" class="ejqrzf" style="width: 98%;display: none;">
        	<img alt="正在查询" src="images/ajax-loader-7.gif"><br>
        	<!-- 正在使劲抢啊抢啊抢，请耐心等待 -->
</div>
  
<div class="clear"></div>
<div class="menu" style="font-size: 14;padding:5 10 5 10;" >
	温馨提示：<br>
	1、支付成功后流量会在20分钟内到帐，系统将微信通知您。<br>
	&nbsp;&nbsp;&nbsp;&nbsp;您也可以发短信到运营商查询：<br>
	&nbsp;&nbsp;&nbsp;&nbsp;移动用户：发短信66到10086查询流量<br>
	&nbsp;&nbsp;&nbsp;&nbsp;电信用户：发短信1081到10001查询流量<br>
	2、如流量24小时内未到账系统会自动退款至余额账户；
</div>
</div>


</div></ng-view></div>
<input id="pointsFlag" name="pointsFlag" type="hidden" value="false">
<input id="product_id" type="hidden" value="<%=sp.getId()%>">
<input id="phone" type="hidden" value="<%=phone%>">
<input id="limit_points" type="hidden" value="<%=sp.getProduct_limit_points()%>">
<input id="balance" type="hidden" value="<%=user.getBalance()%>">
<script type="text/javascript">
//	changePointsBtn();
</script>
</body></html>
