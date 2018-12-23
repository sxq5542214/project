<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.supplier.bean.CustomerSupplierProductBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<CustomerSupplierProductBean> list = (List<CustomerSupplierProductBean>) request.getAttribute("list");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
Object customer_id = request.getAttribute("customer_id");
Object openid = request.getAttribute("openid");
Object order_account = request.getAttribute("order_account");
if(order_account==null){
	order_account="";
	}
%>
<html ng-app="ecssAppModule" class="ng-scope">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<style type="text/css">
@charset "UTF-8"; 

[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide
	{
	display: none !important;
}

ng\:form {
	display: block;
}

.ng-animate-block-transitions {
	transition: 0s all !important;
	-webkit-transition: 0s all !important;
}

.ng-hide-add-active,.ng-hide-remove {
	display: block !important;
}
</style>
<title>购买流量</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="max-age=300">
<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css"
	href="css/supplier/stylesd2.css">
<link rel="stylesheet" type="text/css"
	href="css/supplier/reset.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/user/first-orderProduct.js"></script>
	<script type="text/javascript" src="js/wechat/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
</head>
<script type="text/javascript">
$(function (){
   var order_account =  $("#order_account").val();
   if(order_account!=null && order_account!=""){
   checkPhone();
   }else{
   defaultPhone();
   }
   
 var share_time_ms = weixinInit.getShare_time_ms();
 function shareTimeFirendSuccess(){
  	
  	$.ajax({
		type : "POST",
		url : "user/handleUserShare.do",
		data : {"openid": '<%=openid%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %>,
		 "share_from":"<%=UserSenceLog.SHARE_FROM_FIRST_ORDER_PRODUCT %>",share_time_ms:share_time_ms },
		success : function(data) {
 			   var user_points =  $("#user_points").val();
			   user_points = user_points*1+2 ;
			   $("#user_points").val(user_points);
			   var text_price =  $("#text_price").text();						
			   var discount_price = $("#discount_price").text();				
			   var low_price = parseInt(text_price*1*0.6);						
			   discount_price = discount_price*1-2;	
			   if(discount_price<=low_price){
			   				discount_price = low_price.toFixed(2);
			   			    $("#discount_price").html(discount_price);
			   }else{
			 			    discount_price = discount_price.toFixed(2);
			   			    $("#discount_price").html(discount_price);
			   }
		}
	});

  }
  
	weixinInit.setShareLink('<%=BaseContext.getDefault_share_url()%>?fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
	weixinInit.setShareTitle('<%=BaseContext.getDefault_share_title()%>');
	weixinInit.setShareImg('<%= user == null? basePath+"images/icon/4g.png":user.getHead_img() %>');
	weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
		
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
  
   
 });
	
</script>
<body  class="ng-scope">
	<div>
		<!-- ngView: undefined -->
		<ng-view class="ng-scope">
		<div  class="ng-scope">
			<div class="pay">
				<div class="payable">
					<label   class="ng-scope"><label>需支付</label>:<label
						id="discount_price"  class="ng-binding" style="color:red;">0</label>元</label>
					<span class="ng-binding">原价<label id="text_price" style="font-size: 20px;">0</label>元</span>
				</div>
				<div
					onclick="payInfo()" class="recharge">立即支付</div>
			</div>
			<!-- <div class="pay">
				<div class="payable">
					<label  class="ng-scope"><label>库存</label>:<label
						id="text_store_num" class="ng-binding">0</label>个</label>
					<span class="ng-binding"><label id="discount_price">0.0</label></span>元
				</div>
				<div
					onclick="payInfo()" class="recharge">立即订购</div>
			</div>  -->

			<div class="width320">
				<div class="top">
					<a href="wechat/user/toInviteFriendsShare.do?openid=<%=openid %>">
					<img src="images/user/user_order_top.png">
					</a>
				</div>

				<div class="con" style="height: 40px;">
					<div class="srm">
						<div class="clearDiv">
							<input class="srhm ng-pristine ng-valid" type="number" onkeyup="checkPhone();"
								maxlength="11" name="phone" id="phone"  placeholder="购买流量请在这里输入手机号码"  value="<%=order_account%>">
							<!-- ngIf: focusOnPhoneNum() -->
						</div>
						<div class="yys">
							<span class="ng-binding"><label id="isp">运营商</label></span>
						</div>
					</div>
				</div>

				<!-- ngIf: isTelNoChanged -->
				<!--  <div style="background-color:#FFFFFF;margin-top:2" >&nbsp;&nbsp;当前积分<span class="ng-binding" id="user_points" style="color: red;"><%=user.getPoints()/100d %></span>元 <span class="orderMinusTip ng-binding" id="points"></span>
								<div class="jt"  >
								</div></div>-->
				<div class="main" id="main">				
				<%-- 	<%
						for( CustomerSupplierProductBean csp : list ){
					%>
					<!-- 
					<label class="bderror" style="text-align: center;color: black;">  </label> -->
					<div class="box ng-scope" >
						<%
							for(int i = 1 ; i <= csp.getListSupplierBean().size() ; i++){
											SupplierProductBean sp = csp.getListSupplierBean().get(i-1);
						%>
							
							<div class="Block" id="<%=sp.getId()%>" title="0" discount="0" onclick="choseProduct(<%=sp.getId()%>)" ><%=sp.getProduct_name() %></div>
							
							<%if(i == csp.getListSupplierBean().size()){%>
								<div class="clear"></div>
						<%}} %>
						
					</div>
					<%} %> --%>
				</div>
				
				<div id="ss"><div class="main" id="firstmain"></div></div>
				<div class="clear"></div>
				<div class="menu" style="padding-bottom: 80px;">
					<div  class="ng-scope">						
							<div class="downmenu">
							<h2 class="ng-binding"><span class="orderMinusTip ng-binding" id="givePoints"><font style="color:black;">成功购买后再返5%积分&nbsp;</font></span></h2>
							<div class="jt"  >
							</div>
						</div>
						<div class="downmenu">
							<h2 
								class="ng-binding">温馨提示</h2>
							<div class="jt" >
								<img onclick="changeTips()"
									class="ng-scope" id="tips_img"
									src="images/icon/upjt.png">
							</div>
						</div>
						<div class="nr ng-scope ng-binding" id="tips" >
							<div>
								1、 支持安徽电信、安徽移动、支持2G/3G/4G网络，当月有效；<br>
								2、 170号段、黑名单、欠费、停机、未实名登记以及单月有套餐变更行为的用户号码无法进行流量充值；<br>
								3、 客服微信：LLyun9922 在线时间为8:30-20:30；<br>
							</div>
						</div>
						<div class="clear ng-scope" ></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>

		</ng-view>
	</div>
	
<input type="hidden" id="product_id" name="product_id">
<input type="hidden" id="customer_id" name="customer_id" value="<%=customer_id %>">
<input type="hidden" id="product_price" name="product_price" value="0">
<input type="hidden" id="store_num" name="store_num" value="0">
<input type="hidden" id="openid" value="<%=openid%>">
<input type="hidden" id="order_account" value="<%=order_account%>">
<input type="hidden" id="user_points"  value="<%=user.getPoints()/100d %>">
</body>
<script type="text/javascript">
//changeTips();
</script>
</html> 