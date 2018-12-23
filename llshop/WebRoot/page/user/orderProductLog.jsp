<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<OrderProductLogBean> list = (List<OrderProductLogBean>) request.getAttribute("list");
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
<title>流量充值</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>

	<div id="_contain">

		<div >
			<a href="user/toUserSignPage.do?openid=<%=openid%>">
					<img src="images/user/user_order_top2.png" style="width: 100%">
			</a>			

		</div>

		<div class="favorable" style="text-align: center;margin: 0 auto;">

			<%if(list.size()==0){ %>
				<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;">暂无数据</span> 
			
			<% }else{ %>
			<%
				for (OrderProductLogBean opl : list) {
			%>
			<div class="fb" style="height: auto;width: 98%;">
				<div class="fb-lt" style="width: 100%;">
					<!-- <img src="images/scenics/scenic_3.png"> -->
						<p class="price" onclick="location.href='order/userOrderProduct.do?out_trade_code=<%=opl.getOrder_code()%>'">
							<span class="discount" style="font-size: 0.4rem;margin: 0;">订单号：<big  style="color: black;"><%=opl.getOrder_code()%></big>
							</span> <span class="discount" style="font-size: 0.4rem;margin: 0;">充值号码：<big style="color: black;"><%=opl.getOrder_account()%></big>
							</span> <span class="num" style="float:right;font-size: 0.4rem;">&nbsp;&nbsp;&nbsp;&nbsp;状态：<big style="color: black;"><%=opl.getDictValueByField(OrderProductLogBean.DICT_FIELD_STATUS)%></big>
							</span> <span class="num" style="font-size: 0.4rem;float: left;">商品名称：<big  style="color: black;"><%=opl.getProduct_name()%></big>
							</span> <span class="num" style="font-size: 0.4rem;">&nbsp;&nbsp;<big>&nbsp;&nbsp;</big>&nbsp;&nbsp;</span>
	
							<span class="discount" style="font-size: 0.4rem;margin-left:0;">支付时间：<big style="color: black;"><%=opl.getCreate_time()%></big>
							</span><span class="num" style="font-size: 0.4rem;">支付：<big><%=(opl.getCost_balance() + opl.getCost_money()) / 100d%></big>元</span>
	
						</p>

				</div>
			</div>

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
