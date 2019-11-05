<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	boolean hasDiscount = false;
	SupplierProductBean bean = (SupplierProductBean) request.getAttribute("bean");
	String openid = (String) request.getAttribute("openid");
	
	%>
<!DOCTYPE html>
<html class="">
<head>
	<base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>产品详情</title>
    <meta name="charset" content="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="application-name" content="">
	    <meta name="description" content="">
	    <meta name="keywords" content="">
	    <meta name="author" content="">
    <meta name="version" content="ddtouch">
    <meta http-equiv="Cache-Control" content="must-revalidate,no-cache">
    <link rel="stylesheet" href="page/shop/product/css/common.css">
    <link rel="stylesheet" href="page/shop/product/css/product.css">
<script>var sid="d256b1d537fe186e53c0444399341c24";
	function changeBuyNum(changeNum){
		var val = parseInt($("#buy_num").val());
		
		if(val == 1 && changeNum < 0 ){
			$(".minus").removeClass("on");
			return;
		}
		$(".minus").addClass("on");
		if(val == 9999 && changeNum > 0 ){
			return;
		}
		
		$("#buy_num").val(changeNum + val);
	}
	
	function addToCart(spid){
		var val = parseInt($("#buy_num").val());
		addProductToCart(spid,val);
		location.href = 'user/supplier/toSupplierProductCategoryPage.do.do?openid=<%=openid%>';
	}
	
	function gotoCart(spid){
		addToCart(spid);
		location.href = 'user/cart/toMycartPage.do?openid=<%=openid%>';
	}
	
	
</script></head>

<body class="defwidth">
<header class="header">
    <a href="javascript:history.back();" class="back"></a>
    <h1><em>商品详情</em></h1>
    <!-- <nav class="t-nav">
    <ul>
        <li><a href="" class="home">首页</a></li>
        <li><a href="" class="search">搜索</a></li>
        <li><a href="" class="category">分类</a></li>
        <li><a href="" class="cart">购物车</a></li>
        <li><a href="" class="user">我的商城</a></li>
    </ul>
	</nav> -->
</header>
<!-- 轮播开始 -->
<div id="bigpic">
<div id="cell">
<section class="turn">
  <section id="slider">
    <ul class="top-slider" style="width: 200%; -webkit-transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); -webkit-transform: translate(0px, 0px) translateZ(0px);">
       <li style="width:50%">
		      <a>
		              <img src="<%=bean.getHead_img()%>">
		      </a>
      	</li>
          <!-- <li style="width:50%">
      <a>
                        <img class="" src="page/shop/product/images/1100604422-2_e_1.jpg">
                </a>
      </li> -->
        </ul>
    <!-- <div class="dot">
    <ul>
                   <li class="on"></li>
                     <li class=""></li>
                   </ul>
    </div> -->
  </section>
</section>
</div>
</div>
<!-- 轮播结束 -->

<!-- 详情区域开始 -->
<section class="detail">
	<div class="left">
		<b>优惠价：￥<span id="main_price"><%=bean.getProduct_price() * bean.getDiscount() / 100d / 100d %></span></b>
		<del style="">原价：￥<%=bean.getProduct_real_price() /100d %></del>
		<aside>
						
			                        					</aside>
	</div>
	<div class="right">
		<!-- <ul>
						<li class="fav"> <a>收藏</a></li>
		</ul> -->
	</div>
	<article><%=bean.getProduct_title() %></article>
								<p></p>
				
				
	<!-- 保障区域开始  如果是电子书没有保障区域-->
	        
		<!-- 保障区域结束 -->
</section>
<!-- 详情区域结束 -->

<!-- 税费 只有海外购有税费 -->
<!-- 税费结束 -->


<!-- 加载促销信息模板开始 -->
	<!-- 单品级促销有附加商品 -->
	
	<!-- 店铺级促销没有附加商品 -->
				<section class="promotion">
				<a href="javascript:void(0)" class="arrow_con">
					<div class="label">
					<div class="table">
						<div class="cell">
							<span class="purple">商品优惠</span>
						</div>
					</div>
				</div>
					<div class="info">
						<% if(NumberUtil.toIntDefaultZero(bean.getDiscount()) < 100){
						
						%> <p>原价基础上打 <%=bean.getDiscount() / 10d %>折 </p> <%
						
						}if( NumberUtil.toIntDefaultZero(bean.getProduct_offset_points()) > 0) {
							hasDiscount = true;
						%>
							<p>本商品可叠加积分抵扣，最高可抵扣￥ <%=bean.getProduct_offset_points() / 100d %>元 </p>
						<%} if(!hasDiscount){ %>
							<p>暂时没有优惠信息</p>
							<%} %>
					</div>
				</a>
			</section>
		<!-- 店铺级促销没有附加商品  -->


<!-- 加载促销信息模板结束 -->


<!-- 配送开始 -->
	<section class="select_box">
		<section class="title">
			<a class="back">返回</a>
			<span class="title">选择配送地区</span>
			<a class="close">关闭</a>
		</section>
		<div id="regionScroller">
		<section style="-webkit-transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); -webkit-transform: translate(0px, 0px) translateZ(0px);">
		<dl class="content"></dl>
		</section>
		</div>
	</section>
<!-- 配送结束 -->
<!-- 选项开始 -->
<!-- 数量开始 -->
<section class="quantity">
	<h4>购买数量：</h4>
	<div class="number_con">
		<span class="minus " onclick="changeBuyNum(-1)">-</span>
		<div class="input"><input id="buy_num" type="number" value="1"></div>
		<span class="plus on" onclick="changeBuyNum(1)">+</span>
	</div>
</section>

<section class="detail book_detail" style="margin-bottom: 0px;">
	<div class="title">
		<div class="right">
		<span class="icon"></span>
		</div>商品简介</div>
</section>
<section >
		<%=bean.getProduct_desc() == null? "<p><span>该商品暂时没有更多介绍</span></p>":bean.getProduct_desc() %>
	
</section>


<!-- 猜你喜欢结束 -->
<!-- 购物车开始 -->
<section class="shopping_cart">
	<div class="btn_con" style="margin-right: 0px;">
		<button class="add" dd_name="加购物车" onclick="addToCart(<%=bean.getId()%>);" style="width: 45%;">加购物车</button>
		<button  onclick="gotoCart(<%=bean.getId()%>);" class="buy J_buy" dd_name="直接购买" style="width: 45%;">立即购买</button>		
	</div>
	<!-- <a href="javascript: void(0);" class="cart" dd_name="查看购物车">
		<span>购物车<i></i></span>
	</a> -->
</section>
<!-- 购物车结束 -->
<form id="product_form" action="" method="post">
        <input type="hidden" value="touch" name="user_client">
        <input type="hidden" value="1.0" name="client_version">
        <input type="hidden" value="get_order_flow" name="action">
        <input type="hidden" value="" name="union_id">
        <input type="hidden" value="20151007171938214237638454188786121" name="permanent_id">
        <input type="hidden" value="0" name="shop_id">
        <input type="hidden" value="d256b1d537fe186e53c0444399341c24" name="udid">
        <input type="hidden" value="1444442002" name="timestamp">
        <input type="hidden" value="659ac7e360252f81fe946a0c662d7e7b" name="time_code">
        <input type="hidden" value="ddapp_h5|" name="order_follow_source">
    </form>
<!-- loading开始 -->
<!-- <section class="loading"></section>
 --><!-- loading结束 -->
<!-- 遮罩开始 -->
<section class="mask"></section>
<!-- 遮罩结束 -->
<!-- popup开始 -->
<section class="popup">
	<div class="box">
		<p class="cell">
			<span class="con">
				<span class="title">温馨提示</span>
				<span class="info"></span>
				<a href="javascript:void(0);" class="btn determine" ontouchstart=""><em>确定</em></a>
				<a href="javascript:void(0);" class="btn cancel" ontouchstart=""><em>取消</em></a>
				<a href="javascript:void(0);" class="btn ok" ontouchstart=""><em>确定</em></a>
			</span>
		</p>
	</div>
</section><script type="text/javascript">
	var is_weixin = false;
	var prd_cfg = {};
	prd_cfg.review_url = "";

</script>
<footer class="footer new">
    <section class="status-bar">
        <div class="actions-wrap">
                    </div>
        <a class="top" href="javascript:scrollTo(0,0);">TOP</a>
    </section>
    <section class="copyright">
        <p>优惠活动最终解释权归主办方所有 </p>
    </section>
</footer>
<!-- 购物车占位开始 -->
<section class="space"></section>
<!-- 购物车占位结束 -->

<script src="js/cookieUtil.js"></script>
<script src="js/jquery.js"></script>
<script src="page/shop/product/js/shoppingCart.js"></script><!-- 
<script src="js/user/supplierProductShop/shoppingCart.js"></script> -->


</body>
</html><!--LHC-2015-10-10-->
