<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page language="java" import="java.util.*"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="com.yd.business.user.bean.UserCommissionPointsBean"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@page import="com.yd.business.user.bean.UserConsumeInfoBean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<UserConsumeInfoBean> logs = (List<UserConsumeInfoBean>) request.getAttribute("logs");
	List<SupplierCouponConfigBean> showCouponlist = (List<SupplierCouponConfigBean>) request.getAttribute("showCouponlist");
	Object openid = request.getAttribute("openid");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

<title>领取优惠券中心</title>
<link rel="stylesheet" type="text/css" href="../../css/common/coupon/style.css" />


<script type="text/javascript" src="../../js/jquery.js"></script>
	<script type="text/javascript" src="../../js/user/activity/coupon/receiveCoupon.js"></script>
</head>
<base href="<%=basePath%>">
<script type="text/javascript">
</script>
<body>
		<div >
			<a href="user/toUserSignPage.do?openid=<%=openid%>">
					<img src="images/user/user_order_top2.png" style="width: 100%">
			</a>
			<!-- <div class="logo-dbs">
				<div class="dbs-img">
					<img src="images/icon/logo.png">
				</div>
				<p>全球低价，满足你的梦！</p>
			</div> -->
		</div>

<div class="demo">
	<%
       for (SupplierCouponConfigBean myCoupon : showCouponlist) {
     %>
	 	<%
           if(!"" .equals(myCoupon.getCoupon_backgroup())|| myCoupon.getCoupon_backgroup()!=null ){
         %>
         	<div class="" style="  background: <%=myCoupon.getCoupon_backgroup() %>;  background: radial-gradient(transparent 0, transparent 6px,  <%=myCoupon.getCoupon_backgroup() %> 6px);background-size: 15px 15px;background-position: 9px 3px; padding: 0 10px;width: 95%;">
  			<div class="stamp" style="height: 130px; padding: 0px 10px; margin-bottom: 5px; position: relative;overflow: hidden;background: <%=myCoupon.getCoupon_backgroup() %>	;">		 
         <% }else{ %>
         	<div class="stamp stamp01">
			<%} %>
		 	
		 	<%if(myCoupon.getCoupon_discount() != 0){ %>
		 		<div class="par"><p>折扣卷</p><span><%=myCoupon.getCoupon_discount()/10 %>折</span>
		 	<% }else{ %>
		 		<div class="par"><p>优惠券</p><sub class="sign">￥</sub><span><%=myCoupon.getCoupon_offsetmoney()/100 %>元</span>
		 	<% } %>
			<%if(!"".equals(myCoupon.getRule_name()) && myCoupon.getRule_name() != null){ %>
				<p>领取规则:<%=myCoupon.getRule_name() %></p>
			<% } %>
			<p style="bottom:2px;position:absolute;" id= "<%=myCoupon.getId() %>" >目前还剩余:<%= myCoupon.getNumber()%>张</p></div>		 
			<div class="copy">副券<p><%=myCoupon.getBegin_time() %><br><%=myCoupon.getEnd_time() %></p><a onclick ="reveiveCoupon('<%= myCoupon.getId()%>','<%=openid %>')"  >立即领取</a></div>	<i></i>
			<% if(!"" .equals(myCoupon.getCoupon_backgroup())|| myCoupon.getCoupon_backgroup()!=null ){%>
			</div>
			<%} %>
			</div>
	<% } %>

</div>
</body>
</html>