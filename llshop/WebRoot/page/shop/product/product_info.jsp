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
		
	}
	
	function gotoCart(spid){
		addToCart(spid);
		location.href = 'user/cart/toMycartPage.do';
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


<section class="detail book_detail">
	<div class="title">
		<div class="right">
		<span class="icon"></span>
		</div>商品简介</div>

	<%=bean.getProduct_desc() == null? "<p><span>该商品暂时没有更多介绍</span></p>":bean.getProduct_desc() %>

<!-- 
	<p><span>产&nbsp;&nbsp;&nbsp;&nbsp;品：</span>姗蔻无毒指甲油包邮环保裸色指甲油儿童孕妇持久无毒指甲油快干</p>
	<p><span>净含量：</span>7.5</p>
	<p><span>上市时间：</span>2013-06-18</p>
	<p><span>颜色分类：</span>颜色分类: 湖水蓝 裸粉色 透明亮片 玫红色 珠光红</p>
	<p><span>肤&nbsp;&nbsp;&nbsp;&nbsp;质：</span>任何肤质</p>
	<p><span>产&nbsp;&nbsp;&nbsp;&nbsp;地：</span>中国</p> -->

</section>


<!-- <section class="review_area"><section class="jump">
	<a href="javascript:return false;" class="arrow_con">
		<div class="arrow">
			<h4>商品评论（254）</h4>
			<em>好评率92.9%</em>

		</div>
		<div style="line-height: 30px;">
			&nbsp;&nbsp;&nbsp;&nbsp;<span style="font-weight: bold;font-size: 12px">Johnson丶Lee</span><br>
			<div style="padding-left: 14px;line-height: 1.5;font-size: 10px;">卖家服务态度很好，随商品也有附赠的小赠品，感觉买这家的商品很舒服。快递也非常快，接到东西的时候还感到有些意外：怎么这么快？！        <div style="float: right;padding-right: 7px;font-weight: bold">2015-09-16</div>
			</div>
		</div>
	</a>
</section></section> -->
<!-- 商品评论结束 -->

<!-- 店铺评价开始 -->
<!-- <section class="shop">
	<a href="">
	<div class="shop_con arrow_con">
		<div class="arrow">
						<div class="logo"><img src="page/shop/product/images/2011111810580945.jpg" alt="Sweet city美甲官方旗舰店"></div>
			<div class="name">Sweet city美甲官方旗舰店</div>
		</div>
	</div>
	</a>
	<ul class="evaluation"><li>
	<p>描述相符</p>
	<em>4.9</em>
	<i class="low">低</i>
</li>
<li>
	<p>服务态度</p>
	<em>4.9</em>
	<i class="low">低</i>
</li>
<li>
	<p>发货速度</p>
	<em>4.8</em>
	<i class="low">低</i>
</li>
<li>
	<p>关注人数</p>
	<em>11980</em>
</li></ul>
	<div class="shop_btn">
		<span><a id="collect_shop">关注店铺</a></span>
		<span><a href="">进店逛逛</a></span>
	</div>
</section> -->
<!-- 店铺评价结束 -->

<!-- 猜你喜欢开始 -->
<!-- <section class="J_guess">
<section class="guess">
<div class="title_con"><span class="title">猜你喜欢</span><div class="line"></div></div>
<ul>

<li>

<a href="">

<aside><img class="" src="page/shop/product/images/1477078008-1_b.jpg"></aside>
<span>【包邮】珍视明冰敷眼罩 冷热敷双功效 缓解眼疲劳 满108赠礼 买就赠眼贴</span>
<em>￥45.00</em>
</a>
</li>

<li>

<a href="">

<aside><img class="" src="page/shop/product/images/1273687641-1_b.jpg"></aside>
<span>女神新妆彩妆套装 现在买送 大礼包 BB霜+眼线液笔+ 加妆前乳+新款口红+睫毛</span>
<em>￥128.00</em>
</a>
</li>



<li>

<a href="">

<aside><img class="" src="page/shop/product/images/1426474506-1_b.jpg"></aside>
<span>韩熙贞  丝滑细腻埃及粉饼 控油防汗修容 清爽服帖 补妆 全网抢购中</span>
<em>￥39.00</em>
</a>
</li>





<li>

<a href="">

<aside><img class="" src="page/shop/product/images/1206438229-1_b.jpg"></aside>
<span>珍视明 薰衣草蒸汽眼罩 芳香蒸汽热敷 遮光安神助眠 买就赠眼贴！买3赠大礼包</span>
<em>￥49.00</em>
</a>
</li>



</ul>
</section>
</section> -->
<!-- 猜你喜欢结束 -->
<!-- 购物车开始 -->
<section class="shopping_cart">
	<div class="btn_con">
		<button class="add" dd_name="加入购物车" onclick="addToCart(<%=bean.getId()%>);">加入购物车</button><button  onclick="gotoCart(<%=bean.getId()%>);" class="buy J_buy" dd_name="直接购买">立即购买</button>		
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
                            <!-- <a class="nickname" href="">L</a>
                <a href="">退出</a> -->
                    </div>
        <a class="top" href="javascript:scrollTo(0,0);">TOP</a>
    </section>
   <!--  <nav class="b-nav">
        <p>
            <a href="" ontouchstart="">提建议</a>
            <a class="red" href="" ontouchstart="">触屏版</a>
            <a href="" ontouchstart="">电脑版</a>
            <a href="" ontouchstart="">帮&nbsp;&nbsp;助</a>
        </p>
    </nav> -->
    <section class="copyright">
        <p>优惠活动最终解释权归主办方所有 </p>
    </section>
</footer>
<!-- 购物车占位开始 -->
<section class="space"></section>
<!-- 购物车占位结束 -->

<!-- 
<script src="page/shop/product/js/zepto.min.js"></script>
<script src="page/shop/product/js/underscore.min.js"></script>
<script src="page/shop/product/js/iscroll5.min.js"></script>
<script src="page/shop/product/js/fastclick.min.js"></script>
<script src="page/shop/product/js/common.min.js"></script>
<script src="page/shop/product/js/product.min.js"></script>
<script src="page/shop/product/js/js_tracker.js"></script> -->
<script src="js/cookieUtil.js"></script>
<script src="js/jquery.js"></script>
<script src="page/shop/product/js/shoppingCart.js"></script><!-- 
<script src="js/user/supplierProductShop/shoppingCart.js"></script> -->


</body>
</html><!--LHC-2015-10-10-->
