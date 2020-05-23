<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardRecordBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardBean"%>
<%@page import="com.yd.util.JsonUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierPackageProductRecordBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserCommissionPointsBean"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<SupplierStoreBalanceCardRecordBean> list = (List<SupplierStoreBalanceCardRecordBean>) request.getAttribute("list");
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
<title>我的折扣卡/储值卡详情</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>

</head>

<body>
	<!-- 	<div style="position: absolute;" onclick="javascript:history.go(-1);">
			<img style="width: 0.6rem; margin-top: 8px; margin-left: 8px;" src="page/shop/order/images/c_back_btn.png">
		</div> -->
	<div id="_contain">

		<div >
			<a >
					<img src="images/supplier/huiyuanzhuanxiang.png" style="width: 100%">
			</a>
			<!-- <div class="logo-dbs">
				<div class="dbs-img">
					<img src="images/icon/logo.png">
				</div>
				<p>全球低价，满足你的梦！</p>
			</div>  -->
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
			<%if(list.size()==0){ %>
				<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;">暂无数据</span> 
			<%}else{ %>
			<%
				for (SupplierStoreBalanceCardRecordBean bean : list) {
			%>
			<div class="fb" style="padding:5px;height:auto; width: 98%;padding-top: 0px;" >
				<div class="fb-lt" style="width: 100%;">
					<!-- <img src="images/scenics/scenic_3.png"> -->
					
					<p class="price">
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;margin-left: 5px;">店铺名称：<big  style="color: black;"><%=bean.getSupplier_name() %></big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;margin-left: 5px;">折扣卡名：<big  style="color: black;"><%=bean.getName()%></big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;margin-left: 5px;">折扣额度：<big style="color: black;"><%=NumberUtil.divideHave100(bean.getDiscount()) %>折</big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;margin-left: 5px;">剩余金额：<big style="color: black;"><%=NumberUtil.divideHave100(bean.getBalance()) %>元</big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 100%;text-align: left;margin-left: 5px;">失效日期：<big style="color: black;"><%=bean.getDff_time() %></big></span> 
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
<script type="text/javascript">



	// JavaScript Document
(function (doc, win) {
  var docEl = doc.documentElement,
	resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
	recalc = function () {
	  var clientWidth = docEl.clientWidth;
	  if (!clientWidth) return;
	  docEl.style.fontSize = 100 * (clientWidth / 1242) + 'px';
	};

  if (!doc.addEventListener) return;
  win.addEventListener(resizeEvt, recalc, false);
  doc.addEventListener('DOMContentLoaded', recalc, false);
})(document, window);
	
</script>
</html>
