<%@page import="com.yd.business.user.bean.UserCartBean.CartInfo"%>
<%@page import="com.yd.business.user.bean.UserCartBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
	UserCartBean userCart = (UserCartBean)request.getAttribute("userCart");
	int totalPrice = 0 ;
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport">
<meta name="apple-mobile-web-app-capable" content="no">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="application-name" content="">
<title>购物车</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="author" content="">
<meta name="version" content="ddtouch">
<meta http-equiv="Cache-Control" content="must-revalidate,no-cache">
<link rel="stylesheet" href="page/shop/product/css/cart_v3.css">
</head>
<body style="height: 891px;">
	<header class="header" style="">
		<a class="back c_back"
			style="color:#464646;" href="javascript: history.go(-1);">返回</a>
		<div class="h_label" style="color:#464646;">购物车</div>
		<nav class="t-nav"></nav>
	</header>
	<!--<header class="navbar" style="">
    <div class="nav_main">
        <span class="center" style="color:#464646;">购物车</span>
        <a class="fl c_back" style="color:#464646;background-image:url(../images/c_back_btn.png);">返回</a>
        <span><a>编辑</a></span>
    </div>
</header>-->
	<div id="wrapper" style="height: 689px;">
		<div class="viewport" id="scroller"
			style="-webkit-transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); transition: 0ms cubic-bezier(0.1, 0.57, 0.1, 1); -webkit-transform: translate(0px, 0px) translateZ(0px);">
			<section class="content">

				<div class="block">
					<div class="cart_list">
						<div class="shop_title" shop_id="6860">
							<!-- <input type="checkbox" class="fl c_checkbox"> -->
							<div class="fl">
								<a class="shopLink eclipse" href="javascript:return false;"><%=BaseContext.getMchName(null)%></a>
							</div>
							<!-- <a class="fl right_arrow" href=""></a> -->
						</div>
						<%
							for( CartInfo ci : userCart.getCartInfoList()){
							SupplierProductBean bean = ci.getSupplierProduct();
							totalPrice += ci.getTotal_price();
						%>
						<div class="cart_item" id="<%=bean.getId()%>"
							item_id="1935345606" stock="195">
							<input type="checkbox" class="fl c_checkbox" checked="" onchange="reCheck(<%=bean.getId() %>)" >
							<img src="<%=bean.getHead_img()%>" class="fl pro_pic">
							<div class="detail">
								<p class="fr prd_price">
									￥ <span id="spTotalPrice<%=bean.getId() %>" name="spTotalPrice" check="true" data_spid="<%=bean.getId() %>" data_num="<%=ci.getNum() %>" ><%=ci.getTotal_price() / 100d %></span>元</p>
								<p class="fl prd_tit">
									<a href="javascript:;"><%=bean.getProduct_name()%></a>
								</p>
								
								<p class="clear tags" >
									<a class="garbage" style="display: inline;"
										onclick="dropCart(<%=bean.getId()%>)"></a>
									<!-- 	<a class="collect"
										style="display: inline;"></a> -->
										<span  onclick="changeBuyNum(-1,<%=bean.getId() %>)" style="width:2.5rem; height: 2rem;display: table-cell;background-image: url(page/shop/product/images/minus.png);background-size:cover;" ></span>
										<span id="buy_num<%=bean.getId() %>" singlePrice="<%=bean.getProduct_price() * bean.getDiscount() /100d / 100d %>"  max_num="<%=bean.getStore_num() %>" style="text-align:center; margin: 0 auto ;width:2rem; height:2rem;color: black;font-size: 1.5rem;" ><%=ci.getNum() %></span>
										<span  onclick="changeBuyNum(1,<%=bean.getId() %>)" style="width:2.5rem; height: 2rem;display: table-cell;background-image: url(page/shop/product/images/plus.png);background-size:cover;" ></span>
											
									</p>
									
							</div>
							<!--<div class="edit_area">-->
							<!--<a class="fr bottom_arrow arrow_num"></a>-->
							<!--<p class="fr num"><span class="fr quantity">1</span></p>-->

							<!---->
							<!--</div>-->

							<div class="bottom_line"></div>
						</div>
						<%
							}
						%>
					</div>
				</div>
			</section>


		</div>
		<footer class="total_result" style="">
			<!-- <div class="fl ">
				<input type="checkbox" class="c_checkbox" id="cart_check_all" onclick="selectAll()"
					style=""> <span style="color:">全选</span>
			</div> -->
			<div class="fl ">
				<span>商品金额总计</span>
				<span style="color:red;" >
					￥<span id="cart_price"><%=totalPrice / 100d %></span>元
				</span>
			</div>
			<div class="fr">
				<a style="color:;background-color:;border:.1rem solid red;" onclick="gotoOrder()"
					class="c_btn payBtn" id="do_checkout" href="javascript:;">去结算(<span
					id="cart_quantity"><%=userCart.getCartInfoList().size() %></span>)</a>
			</div>
			<%-- <div class="fr totalPrice">
				<span>商品金额总计</span>
				<p  style="color:">
					￥<span style="color:" id="cart_price"><%=totalPrice / 100d %></span>
				</p>
			</div> --%>
			<form id="cart_form" action="" method="post">
				<input type="hidden" value="touch" name="user_client"> <input
					type="hidden" value="1.0" name="client_version"> <input
					type="hidden" value="get_order_flow" name="action"> <input
					type="hidden" value="" name="union_id"> <input
					type="hidden" value="20151007171938214237638454188786121"
					name="permanent_id"> <input type="hidden" value="0"
					name="shop_id"> <input type="hidden"
					value="d256b1d537fe186e53c0444399341c24" name="udid"> <input
					type="hidden" value="1444445736" name="timestamp"> <input
					type="hidden" value="12a421d5328d6fe614213acfd06f0442"
					name="time_code"> <input type="hidden" value="ckadd"
					name="ref">
			</form>
		</footer>
	</div>

	<section class="loading"></section>
	<section class="f_mask"></section>
	<section class="simple_block"></section>
	<section class="m_block">
		<div class="m_content"></div>
		<div class="m_btns">
			<a class="m_ok_2">确定</a> <a class="m_cancel">取消</a><a class="m_ok">确定</a>
		</div>
	</section>
	<section direction="0" class="jp_block">
		<p class="jp_title">
			<a class="c_ok"></a>修改购买数量<a class="c_close"></a>
		</p>
		<div class="jp_content">
			<div class="jp_show">
				<div></div>
				<div>
					<input type="number" pattern="[0-9]*">
				</div>
				<div></div>
			</div>
		</div>
	</section>
	<section direction="1" class="f_block" id="gift_block">
		<p class="f_title">
			<span></span><a class="c_close"></a>
		</p>
		<div class="f_content">
			<div class="cart_list"></div>
		</div>
		<div class="f_foot">
			<a class="c_btn">确认</a>
		</div>
	</section>

	<section direction="0" class="f_block" id="choose_color_size">
		<p class="sel_title">
			<a class="c_close"></a>
		</p>
		<div class="f_content">

			<div class="select_box"></div>
			<ul class="sel_list">
				<li><img src=""><a class="f_del"></a>
					<div class="detail">
						<p class="prd_price"></p>
						<p class="prd_tit"></p>
					</div></li>
			</ul>

		</div>


		<div class="f_foot">
			<a class="c_btn">确认</a>
		</div>
	</section>

	<script src="js/jquery.js" type="text/javascript"></script>
	<script src="js/cookieUtil.js"></script>
	<script src="page/shop/product/js/shoppingCart.js"
		type="text/javascript"></script>
	<script type="text/javascript">
		var openid = getCookie("openid");
		function dropCart(spid){
			if(confirm('确认要删除此商品？')){
				$("#"+spid).hide();
				deleteProductToCart(spid);
			}
		};
		
		function changeBuyNum(num,spid){
		
			var sp = $("#buy_num"+spid);
			var nowNum = parseInt(sp.html());
			var singlePrice = parseFloat(sp.attr("singlePrice"));
			var max_num = sp.attr("max_num");
			nowNum = num + nowNum;
			
			if(nowNum < 1){
				nowNum = 1;
				return ;
			}
			if(nowNum > max_num){
				nowNum = max_num;
				alert("库存只有这么多了，无法购买更多啦！");
				return ;
			}
			
			$("#buy_num"+spid).html(nowNum);
			
			$("#spTotalPrice"+spid).html( (nowNum * singlePrice).toFixed(2) );
			$("#spTotalPrice"+spid).attr("data_num",nowNum);
			
			addProductToCart(spid,nowNum);
			
			calcTotalPrice();
		};
		
		function reCheck(spid){
			var flag = $("#spTotalPrice"+spid).attr("check");
			if(flag == "checked" || flag == "true"){
				$("#spTotalPrice"+spid).attr("check","false");
			}else{
				$("#spTotalPrice"+spid).attr("check","true");
			}
			calcTotalPrice();
		};
		
		
		function calcTotalPrice(){
			var prices = document.getElementsByName("spTotalPrice");
			var num = 0 ;
			var total = 0;
			for(var i = 0 ; i < prices.length ; i++){
				if(prices[i].getAttribute("check") == "true"){
					total = total +  parseFloat(prices[i].innerHTML) ;
					num++;
				};
			}
			$("#cart_quantity").html(num);
			$("#cart_price").html(total.toFixed(2));
		};
		
		function gotoOrder(){
			var prods = $("span[id^='spTotalPrice']");
			var time = new Date();
			var json_temp = "{productInfos:[";
			var isEmpty = true;
			
			for(var i = 0 ; i < prods.length ; i++){
				var spid = prods[i].getAttribute("data_spid");
				var num = prods[i].getAttribute("data_num");
			
				if(prods[i].getAttribute("check") == "true"){
					json_temp += "{spid:" + spid +",num:"+ num +"}" ;
					isEmpty = false;
					deleteProductToCart(spid);
				}
				if(i != prods.length - 1){
					json_temp +=",";
				}
				
			}
			
			if(isEmpty){
				alert("请先选择商品再结算！");
				return ;
			}
			json_temp += "],time:" + time.getTime() +"}";
			location.href= "<%=basePath%>user/toUserShopOrderPage.do?openid="+openid+"&data="+ json_temp ;
		};
		
	</script>
</body>
</html>
