<%@page import="com.yd.business.user.bean.UserConsumeInfoBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserCommissionPointsBean"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<UserConsumeInfoBean> logs = (List<UserConsumeInfoBean>) request.getAttribute("logs");
	Object openid = request.getAttribute("openid");
	
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>现金账户明细</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>

		<div style="position: absolute;" onclick="javascript:history.go(-1);">
			<img style="width: 0.5rem; margin-top: 8px; margin-left: 8px;" src="page/shop/order/images/c_back_btn.png">
		</div>
	<div id="_contain">

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

		<div class="favorable" style="text-align: center;margin: 0 auto;">
			<!-- 
<div class="fb">
    <div class="fb-lt">
    	<img src="images/scenics/scenic_3.png">
        <p class="fb-name">塞班岛5-6天</p>
        <p class="price">
        	<span class="discount"><big>4.7</big>折</span>
            <span class="num"><big>5699</big>元起</span>
        </p>
    </div>
</div> -->
			<%if (logs.size()==0) {%>
				<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;">暂无数据</span> 
				
			<%}else{ %>
			<%
				for (UserConsumeInfoBean b : logs) {
			%>
				<% if ( b.getMoney() > 0 )  { %>
			<div class="fb" style="height: auto;width: 98%;">
				<div class="fb-lt" style="width: 100%;">
					<!-- <img src="images/scenics/scenic_3.png"> -->

					<p class="price">
						
					
						
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;">充值号码：<big  style="color: black;"><%=b.getPhone()%></big></span> 
						<span class="discount" style="font-size: 0.4rem;margin: 0;">充值时间：<big style="color: black;"><%=b.getCreate_date()  %></big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;float: right;"><%=b.getInterface_type() %>：<big style="color: black;"><%= b.getMoney()/100d%></big>元&nbsp;&nbsp;&nbsp;&nbsp;</span>
		
					</p>


				</div>
			</div>
				<% } %>
			<%
				}
			%>
			<%} %>

		</div>

	</div>

	<!-- <footer>
	<div class="foot act">
    	<img src="images/foot/ft_1_1.png">
        <p>推荐</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_2.png">
        <p>目的地</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_3.png">
        <p>帖子</p>
    </div>
	<a href="person/person.html"><div class="foot">
    	<img src="images/foot/ft_4.png">
        <p>我的</p>
    </div></a>
</footer>     -->

</body>
</html>
