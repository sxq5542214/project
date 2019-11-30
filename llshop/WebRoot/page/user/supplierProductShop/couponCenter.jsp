<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String openid = (String)request.getAttribute("openid");
	List<SupplierCouponConfigBean> list = (List<SupplierCouponConfigBean>)request.getAttribute("list");
	
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<base href="<%=basePath%>">
		<title>优惠券领取中心</title>
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black">

		<!--标准mui.css-->
		<link rel="stylesheet" href="css/user/supplierProductShop/coupon/mui.min.css">
		<!--App自定义的css-->
		<link rel="stylesheet" type="text/css" href="css/user/supplierProductShop/coupon/coupon.css"/>
		<style>
			.mui-card .mui-control-content {
				padding: 10px;
			}
			.mui-control-content {
				height: 150px;
			}
			.mui-toast-container {bottom: 50% !important;}
        </style>
        <script type="text/javascript" src="js/jquery.js"></script>
        
	</head>
	<body>
		<header class="mui-bar mui-bar-nav">
			<a class="mui-action-back mui-icon mui-icon-left-nav mui-pull-left" style="color: #999;" onclick="history.go(-1);"></a>
			<h1 class="mui-title">优惠券领取中心</h1>
		</header>
		<div class="mui-content new-min-quan">
            <ul>
            	<% for(SupplierCouponConfigBean bean : list){
            		String time = "使用期限："+bean.getUseful_lift()/24+"天内";
            		String rightTitle = "";
            		String imageName = "type"+bean.getType()+".png";
            		
            		if(bean.getCoupon_discount() != null && bean.getCoupon_discount() != 0){
            			rightTitle = bean.getCoupon_discount()/10d +"折";
            		}else if(bean.getCoupon_offsetmoney() != null ){
            			rightTitle = "￥" + bean.getCoupon_offsetmoney()/100d ;
            		}else{
            		}
            		
            	 %>
            	<li>
                	<div class="left">
                    <div class="rtdian"></div><div class="rbdian"></div>
                    <div class="padding">
                    	<img src="images/user/supplierProductShop/coupon/<%=imageName%>" style="margin-top: 8px;">
 					<div class="tit" style="padding-top: 0;margin-top: 5px;"><%=bean.getCoupon_name() %>
 						<p style="white-space: normal;"> 
 						<%=StringUtil.convertNull(bean.getRemark()) %> <br>
 						<%=time %>
 						</p>
 					</div>
 					</div>
                    </div>
                	<div class="right bg2">
                    <div class="ltdian"></div><div class="lbdian"></div>
                    <div class="padding">
                    	<div class="price"><i><%=rightTitle %></i></div>
                        <div class="btn"><button class="use"  onclick="reveiveCoupon(<%=bean.getId()%>)">领取</button></div>
                    </div>
                    </div>
                </li>
                <%} %>
            <!-- 	<li>
                	<div class="left">
                    <div class="rtdian"></div><div class="rbdian"></div>
                    <div class="padding">
                    	<img src="images/user/supplierProductShop/coupon/logo.jpg"><div class="tit">好评券券自营店<p>2018.07.16-2018.08.16</p></div>
                    </div>
                    </div>
                	<div class="right bg1">
                    <div class="ltdian"></div><div class="lbdian"></div>
                    <div class="padding">
                    	<div class="price">￥<i>30</i></div>
                        <div class="btn"><button class="used">去使用</button></div>
                    </div>
                    </div>
                </li> -->
            </ul>
		</div>
	</body>
<script type="text/javascript">
	
function reveiveCoupon(couponid){
  	$.ajax({
		type : "POST",
		url : "supplier/supplierCouponController/reveiveCoupon.do",
		data : {"openid": '<%=openid%>',"coupon_id": couponid },
		success : function(data) {
 			var result = eval("(" + data + ")");
 			alert(result.reveiveResult);
		}
	});
}
</script>
</html>