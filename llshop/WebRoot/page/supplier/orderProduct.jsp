<%@page import="com.yd.business.supplier.bean.CustomerSupplierProductBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<CustomerSupplierProductBean> list = (List<CustomerSupplierProductBean>) request.getAttribute("list");
Integer customer_id = (Integer)request.getAttribute("customerid");
Integer adminid = (Integer)request.getAttribute("adminid");

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
<title>充值流量</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="max-age=300">
<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css"
	href="css/supplier/stylesd.css">
<link rel="stylesheet" type="text/css"
	href="css/supplier/reset.css">
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/supplier/orderProduct.js"></script>
</head>
<body  class="ng-scope">
	<div>
		<!-- ngView: undefined -->
		<ng-view class="ng-scope">
		<div  class="ng-scope">
			<!-- <div class="pay">
				<div class="payable">
					<label ng-if="!isDiscountPrice()" class="ng-scope"><label>全民价</label>:<label
						id="text_price" class="ng-binding">0.0</label>元</label>
					<span class="ng-binding"><label id="discount_price">0.0</label></span>元
				</div>
				<div
					onclick="payInfo()" class="recharge">立即充值</div>
			</div> -->
			<div class="pay">
				<div class="payable">
					<label  class="ng-scope"><label>库存</label>:<label
						id="text_store_num" class="ng-binding">0</label>个</label>
					<!-- <span class="ng-binding"><label id="discount_price">0.0</label></span>元 -->
				</div>
				<div
					onclick="payInfo()" class="recharge">立即订购</div>
			</div> 

			<div class="width320">
				<div class="top">
					<img
						src="images/icon/top.jpg">
				</div>

				<div class="con">
					<div class="srm">
						<div class="clearDiv">
							<input class="srhm ng-pristine ng-valid" type="number" onkeyup="checkPhone();"
								maxlength="11" name="phone" id="phone"  placeholder="请输入手机号码">
							<!-- ngIf: focusOnPhoneNum() -->
						</div>
						<div class="yys">
							<span class="ng-binding"><label id="isp">运营商</label></span>
						</div>
					</div>
					<label class="bderror">
					</label>
				</div>

				<!-- ngIf: isTelNoChanged -->

				<div class="main" id="main">
					<%
						for( CustomerSupplierProductBean csp : list ){
					%>
					
					<label class="bderror" style="text-align: center;color: black;">货源：<%=csp.getSupplierBean().getParent_name()%>
					</label>
					<div class="box ng-scope" >
						<%
							for(int i = 1 ; i <= csp.getListSupplierBean().size() ; i++){
											SupplierProductBean sp = csp.getListSupplierBean().get(i-1);
						%>
							
							<div class="Block" id="<%=sp.getId()%>" title="<%=sp.getStore_num()%>" onclick="choseProduct(<%=sp.getId()%>)" ><%=sp.getProduct_name() %></div>
							
							<%if(i == csp.getListSupplierBean().size()){%>
								<div class="clear"></div>
						<%}} %>
						
					</div>
					<%} %>
				</div>

				<div class="menu">
					<div  class="ng-scope">
						<div class="downmenu">
							<h2 
								class="ng-binding">温馨提示</h2>
							<div class="jt" >
								<img onclick="changeTips(this)"
									class="ng-scope"
									src="images/icon/upjt.png">
							</div>
						</div>
						<div class="nr ng-scope ng-binding" id="tips" >
							<div>
								1.&nbsp;支持全国三网用户，支持2G/3G/4G网络；<br>2.&nbsp;订单支付成功后，流量会在5~20分钟内到账，当月有效；<br>3.&nbsp;如流量24小时内未到账系统会自动退款；流量具体到账时间以运营商查询为准，到账前产生的费用我司概不负责；<br>4.&nbsp;月末两天运营商系统维护，暂停充值；月初两天为系统出账日，流量充值会有延迟；<br>5.&nbsp;170号段、黑名单、欠费、停机、未实名登记以及单月有套餐变更行为的用户号码无法进行流量充值；<br>6.&nbsp;客服QQ
								1852752687，客服电话400-600-4523，在线时间为8:30-20:30；<br>7.&nbsp;如有其他问题可联系“开心流量”微信公众号客服。
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
<input type="hidden" id="adminid" name="adminid" value="<%=adminid %>">
	

</body>
</html>