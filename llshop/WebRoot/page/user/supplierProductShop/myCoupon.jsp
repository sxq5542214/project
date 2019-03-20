<%@page import="com.yd.business.supplier.bean.SupplierCouponRecordBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String openid = (String)request.getAttribute("openid");
	List<SupplierCouponRecordBean> list = (List<SupplierCouponRecordBean>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<meta charset="utf-8">
	<base href="<%=basePath%>">
		<title>我的优惠券</title>
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
			<h1 class="mui-title">我的优惠券领</h1>
		</header>
		<div class="mui-content new-min-quan">
            <ul>
            	<% for(SupplierCouponRecordBean bean : list){
            		String rightTitle = "";
            		String imageName = "type"+bean.getCoupon_type()+".png";
            		if(bean.getCoupon_offsetmoney() != null){
            			rightTitle = "￥" + bean.getCoupon_offsetmoney()/100d ;
            		}else{
            			rightTitle = bean.getCoupon_discount()/10d +"折";
            		}
            		String buttonClass = "use";
            		String divClass = "right bg2";
            		if(bean.getStatus() != SupplierCouponRecordBean.STATUS_CANUSE){
            			buttonClass = "used";
            			divClass = "right bg1";
            		}
            	 %>
            	<li>
                	<div class="left">
                    <div class="rtdian"></div><div class="rbdian"></div>
                    <div class="padding">
                    	<img src="images/user/supplierProductShop/coupon/<%=imageName%>">
 					<div class="tit"><%=bean.getCoupon_name() %>
 						<p>
 						<%=bean.getDictValueByField("status", bean.getStatus()) %> <br>
 						过期时间：<%=bean.getExpire_time() %>
 						</p>
 					</div>
 					</div>
                    </div>
                	<div class="<%=divClass%>">
                    <div class="ltdian"></div><div class="lbdian"></div>
                    <div class="padding">
                    	<div class="price"><i><%=rightTitle %></i></div>
                    	<a href="user/supplier/toSupplierProductCategoryPage.do?openid=<%=openid%>">
                        	<div class="btn" ><button class="<%=buttonClass %>" >去使用</button></div>
                        </a>
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
</html>