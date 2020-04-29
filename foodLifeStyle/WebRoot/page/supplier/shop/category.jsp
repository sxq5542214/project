<%@page import="com.yd.business.product.bean.ProductTypeBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String openid = (String)request.getAttribute("openid");
	List<SupplierProductBean> productList = (List<SupplierProductBean>) request.getAttribute("productList");
	List<ProductTypeBean> productTypeList = (List<ProductTypeBean>) request.getAttribute("productTypeList");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<base href="<%=basePath%>">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<title>商品分类</title>
<link rel="stylesheet" type="text/css"
	href="css/user/supplierProductShop/category.css">
	<script type="text/javascript" src="js/user/supplierProductShop/shoppingCart.js"></script>
	<script src="js/common/cookieUtil.js"></script>
<head>
<body>
	<div style="position: absolute;margin-left: 15px;margin-top: 15px;padding-right: 15px;" onclick="history.go(-1)">
		<img alt="" src="images/user/supplierProductShop/left.png" style="width: 10px;height: 20px;">
	</div>
	<div class="header">
		<a href="supplier/coupon/toUserCouponCenterPage.do?openid=<%=openid%>">
		<img alt="" height="100%" width="100%"
			src="images/user/supplierProductShop/coupon/get_coupon.jpg">
		</a>
	</div>
	<div class="swiper-container">
		<ul class="swiper-container-ul">
			<li class="swiper-container-ul-li actives" style="width: 100%;color:black;border-color:gray;">商品分类</li>
			<!--       <li class="swiper-container-ul-li">店铺</li>
 -->
		</ul>
		<div class="swiper-wrapper">
			<div class="swiper-slide" style="margin-bottom: 52px;">
				<div class="content">
					<div class="left" id="left">
						<ul>
							<li v-for="item in items">{{item.name}}</li>
						</ul>
					</div>
					<div class="right" id="right">
						<ul>
							<li v-for="item in items">
								<div class="class-title">{{item.class}}</div>
								<div v-for="ite in item.list">
									<div class="item">
										<div class="item-left">
											<a :href="ite.productUrl">
												<div class="item-img">
													<img :src="ite.imgUrl" alt="" width="100%" height="100%">
												</div>
											</a>
										</div>
										<div class="item-right">
												<div class="title">{{ite.name}}</div>
												<div class="subtitle">{{ite.title}}</div>
											<div class="price" >
												<div style="float: left;">¥{{ite.price}}</div>
												<div style="float: left;text-decoration: line-through;color: black;font-size: 1rem; line-height: 25px;">{{ite.real_price}}</div>
												
												<div style="margin-right: 10px;position: absolute;right: 0;width: 74px;" :id="ite.id">
												
													<div style="float: left;" onclick="addToCart(this,-1)">
														<img width="25px" height="25px;" alt="" src="images/user/supplierProductShop/cart_minus.png">
													</div>
													
													<div style="float: left;margin-left: 5px; color: #333;font-size: 1rem;height: 25px;">
														<span style="line-height: 25px;" >0</span>
													</div>
													<div style="position: absolute;right: 0;" onclick="addToCart(this,1)" >
														<img width="25px" height="25px;" alt="" src="images/user/supplierProductShop/cart_plus.png">
													</div>
												</div>
												</div>
												
											</div>
										</div>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			
			
			<!-- 购物车拉伸框 -->
			
		<div style="display:none; width:100%; height:0px;position:fixed; top :0px;background-color:#000;background: rgba(0, 0, 0, 0.5);" id="mask_div" onclick="hide_mask(this);">			
			<div id="cartInfo_div"  style="transition:transform 200ms;transform:translateY(0px);position: absolute;bottom: 50px;background: white;left: 0;right: 0;color: black;z-index: 1;">
				<div>
					<div style="line-height: 30px;height: 30px;background: #F4F4F4;padding: 4px 15px;">
						<span style="font-size: 0.5rem;">购物车</span>
						<span style="float: right;font-size: 0.5rem;">清空购物车</span>
					</div>
					<div style="max-height: 300px;overflow: auto;">
						<div id="cartInfoTemplate_div" style="display: none;align-items:center;padding: 14px 0px;font-size: 16px;color: #333;border-bottom: 1px solid #E4E4E4;margin: 0 15px;">
							<div style="min-width: 0;flex:1 1 auto;display:flex;align-items:center;">
								<div style="flex:1 1 auto;min-width: 0;">
									<div style="word-wrap:break-word;flex:1 1 auto;">鸡小腿</div>
								</div>
								<div style="margin: 0 25px;flex-shrink:0;color: #FB4E44">¥<span>2.22</span></div>
							</div>
							<div style="min-width: 82px;padding-left: 20px;">
								<div style="flex-shrink:0;margin-left:-20px;float: right;">
									<span >
										<img width="25px" height="25px;" alt="" src="images/user/supplierProductShop/cart_minus.png">
									</span>
									<span style="padding: 0 10px;text-align: center;color: black;position: relative;bottom: 5px;">123</span>
									<span >
										<img width="25px" height="25px;" alt="" src="images/user/supplierProductShop/cart_plus.png">
									</span>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>	
			<!-- 底部购物车结算 -->
			<div id="cartBottom_div"
				style="position: fixed; background: #3b3b3c; height: 50px; left: 0;right: 0;bottom: 0;display: flex;align-items:center; z-index: 2;">
				<div onclick="show_mask();"
					style="width:50px;height:50px; top:-10px; margin:0 10px;position:relative; border-radius:50%;flex-shrink:0; background: url('images/user/supplierProductShop/category_cart.png'); background-size: contain;">
					<div
						style="background-color: #FB4E44;top: 0;right: 0;position: absolute;border-radius: 16px;color: white;font-size: 20px;width: 40px;text-align: center;line-height: 30px;transform: scale(0.5) translate(50%, -50%);">
						<span id="totalNum">0</span></div>
				</div>
				<div style="flex: 1 1 auto;">
					<div style="width: 100%;color: white;font-size: 1.5rem;">共计：¥
						<span style="" id="totalPrice">0</span>
					</div>
				</div>
				<div
					style="height: 50px;padding: 0 30px; font-size: 1.2rem;font-weight:bold; line-height:50px; color:#333; position: relative;background-image: linear-gradient(-135deg, #FFBD27 0%, #FFD161 100%);"
					onclick="gotoOrder();">
					去结算</div>
			</div>
			<div class="swiper-slide" style="display:none;">店铺介绍</div>
		</div>
	</div>
	<script src="js/user/supplierProductShop/jquery.min.js"></script>
	<script src="js/user/supplierProductShop/vue.min.js"></script>
	<script type="text/javascript">
		$(function() {
			$('.content').css('height', $('.right').height());
			$('.left ul li').eq(0).addClass('active');
			$(window).scroll(function() {
				if ($(window).scrollTop() >= 150) {
					$('.swiper-container-ul').css('position', 'fixed');
					$('.left').css('position', 'fixed');
					$('.right').css('margin-left', $('.left').width());
				} else {
					$('.swiper-container-ul').css('position', '');
					$('.left').css('position', '');
					$('.right').css('margin-left', '');
				}
				;
				//滚动到标杆位置,左侧导航加active
				$('.right ul li').each(function() {
					var target = parseInt($(this).offset().top - $(window).scrollTop() - 150);
					var i = $(this).index();
					if (target <= 0) {
						$('.left ul li').removeClass('active');
						$('.left ul li').eq(i).addClass('active');
					}
				});
			});
			$('.left ul li').click(function() {
				var i = $(this).index('.left ul li');
				$('body, html').animate({
					scrollTop : $('.right ul li').eq(i).offset().top - 40
				}, 500);
			});
			$('.swiper-container-ul-li').click(function() {
				var index = $(this).index();
				if (index == 0) {
					$('.swiper-slide').eq(0).show();
					$('.swiper-container-ul-li').eq(0).addClass('actives');
					$('.swiper-slide').eq(1).hide();
					$('.swiper-container-ul-li').eq(1).removeClass('actives');
				} else {
					$('.swiper-slide').eq(0).hide();
					$('.swiper-container-ul-li').eq(0).removeClass('actives');
					$('.swiper-slide').eq(1).show();
					$('.swiper-container-ul-li').eq(1).addClass('actives');
				}
			});
		});
	</script>
	<script>
	var productMap = new Map();
  var left = new Vue({
    el: '#left',
    data: {
      items: [
      <%for (int i = 0; i < productTypeList.size(); i++) {
				ProductTypeBean bean = productTypeList.get(i);%>
        { name : '<%=bean.getName()%>' } <%if (i != productTypeList.size() - 1) {%> ,
        <%}
			}%>
        
      ]
    }
  });
  var right = new Vue({
    el: '#right',
    data: {
      items: [
      <%String typeName = "";
			for (int i = 0; i < productList.size(); i++) {
				SupplierProductBean product = productList.get(i);
				//  	 String img = "<img width=\"100%\" height=\"100%\" alt=\"\" src=\"" + product.getHead_img() +  "\">";
				if (!typeName.equalsIgnoreCase(product.getProduct_type_name())) {
					if (i != 0) {%> ] }, <%}%>
        
        { class : '<%=product.getProduct_type_name()%>',list : [ {id:'<%=product.getId()%>', name:'<%=product.getProduct_name()%>', title : '<%=product.getProduct_title()%>' ,price:'<%=product.getProduct_price()/100d%>', real_price:'<%=product.getProduct_real_price()/100d%>', imgUrl:'<%=product.getHead_img()%>', productUrl:'product/supplierProduct/toSupplierProductShopInfo.do?id=<%=product.getId()%>' } 
        
        <%} else {%>
        		,{ id:'<%=product.getId()%>', name:'<%=product.getProduct_name()%>', title : '<%=product.getProduct_title()%>' ,price:'<%=product.getProduct_price()/100d%>' , real_price:'<%=product.getProduct_real_price()/100d %>', imgUrl:'<%=product.getHead_img()%>', productUrl:'product/supplierProduct/toSupplierProductShopInfo.do?id=<%=product.getId()%>' }
        <%}
				if (productList.size() == 1 || i == productList.size() - 1) {%>
       	  ] }
        
         <%}
				if (i != productTypeList.size() - 1  ) {%> 
        <%}
				typeName = product.getProduct_type_name();
			}%>
      ]
    }
  });
  </script>
  <script type="text/javascript">
  	var isClickMask = true;
  	function addToCart(div,num){
  		var parentDiv = div.parentNode; 
 		var span = parentDiv.children[1].children[0];
 		var cur_num = Number(span.innerHTML);
  		
 		if(cur_num == 0 ){
			var item = findItem(parentDiv.id);
		 	var price = item.price;
		 	var name = item.name;
 			
 			createCartInfo(parentDiv.id, name, 0, price)
 		}
  			modifyCartInfo(parentDiv.id,num);
 		
  		
  	}
  	function addToCartBySpid(spid,num){
  		var parentDiv = document.getElementById(spid);
 		var span = parentDiv.children[1].children[0];
 		var cur_num = Number(span.innerHTML);
 		if(cur_num >0  || (cur_num == 0 && num > 0)){
 		
			var item = findItem(spid);
		 	var price = item.price;
		 	var name = item.name;
 			
		 	span.innerHTML = cur_num + num;
		 	addProductToCart(spid,num);
		 	updateTotalNum(num);
		 	updateTotalPrice(Number(price * num));
		 	
  		}
  	}
  	
  	function updateTotalNum(count){
  		var span = document.getElementById("totalNum");
  		var num = Number(span.innerHTML);
  		span.innerHTML = num + count;
  	}
  	
  	function updateTotalPrice(price){
  		var span = document.getElementById("totalPrice");
  		var totalPrice = Number(span.innerHTML);
  		span.innerHTML = (totalPrice + price).toFixed(2);
  		
  	}
  	
  	function findItem(spid){
  		var array = right.items;
  		
  		for(var i =0 ; i < array.length;i++){
  			for(var x =0;x < array[i].list.length; x++){
  				var item = array[i].list[x];
  				if(item.id == spid){
  					return item;
  				}
  			}
  		}
  	}
  	
  	function gotoOrder(){
  		var span = document.getElementById("totalNum");
  		var num = Number(span.innerHTML);
  		if(num > 0){
  		
	  		var time = new Date();
	  		var gotoUrl = '<%=basePath%>user/supplier/toSupplierShopUserOrderPage.do?openid=<%=openid%>&time='+time.getTime();
	  		location.href = gotoUrl;
  		}else{
  			alert('小主，您还没有选择商品呢');
  		}
  	}
  	
  	function hide_mask(div){
  		if(isClickMask){
	  		var mask =  document.getElementById("mask_div");
	  		mask.style.display = 'none';
  		}
  	}
  	function show_mask(){
  		var mask =  document.getElementById("mask_div");
  		mask.style.display = 'block';
  		
   		//高度位置变化
		var wh = document.documentElement.clientHeight; 
		var cart_info_height = document.getElementById("cartInfo_div").offsetHeight;
  		var cart_height = cart_info_height  + document.getElementById("cartBottom_div").offsetHeight ;
  		
  		var new_height = Number(wh) - Number(cart_height);
  		mask.style.height =  new_height + 'px';
		document.getElementById("cartInfo_div").style.bottom = "-"+ cart_info_height +"px";
		
  	}
  	
  	function initCart(){
  		var productInfo = getCookie("productInfo");
		if (productInfo != null) {
			var productJson = eval('(' + productInfo + ')');
	
			var productInfos = productJson.productInfos;
			var len = productInfos.length;
	
			// 循环，找到相同ID的，然后设置数量
			for (var i = 0; i < len; i++) {
				var spInfo = productInfos[i];
				var spid = spInfo.spid;
				var num = spInfo.num;
				if (typeof(spInfo) != "undefined" &&  spid != -1 ) {
					var parentDiv = document.getElementById(spid);
					
					var span = parentDiv.children[1].children[0];
					span.innerHTML = num;
					
					updateTotalNum(num);
					var item = findItem(spid);
					var price = item.price;
					var name = item.name;
					var numPrice = Number(price * num);
		 			updateTotalPrice(numPrice);
		 			
		 			createCartInfo(spid,name,num,numPrice);
				}
			}
		}
  	}
  	function createCartInfo(spid,name,num,price){
  		var item = findItem(spid);
  		var node = document.getElementById('cartInfo_'+spid);
  		
  		if(node == null || node.id == undefined || node.id == null ){
  			node = document.getElementById("cartInfoTemplate_div").cloneNode(true);
  			node.id = 'cartInfo_'+spid;
	  		node.children[0].children[0].children[0].innerHTML = name;
	  		node.children[0].children[1].children[0].innerHTML = price;
	//  		node.children[1].children[0].children[0].onclick = addToCartBySpid(spid,-1);
	  		node.children[1].children[0].children[1].innerHTML = num;
	  		node.children[1].children[0].children[0].onclick = function(){
	  			modifyCartInfo(spid,-1);
	  		};
	  		node.children[1].children[0].children[2].onclick = function(){
	  			modifyCartInfo(spid,1);
	  		};
	  		node.style.display = 'flex';
			document.getElementById("cartInfoTemplate_div").parentNode.appendChild(node);
  			
  		}else{
	  		var n = node.children[1].children[0].children[1].innerHTML;
  			node.children[1].children[0].children[1].innerHTML = Number(n) + num;
		  	node.children[0].children[1].children[0].innerHTML = (Number(n) + num) * item.price ;
  		}
  		
  	}
  	
  	 function modifyCartInfo(spid,num){
  		var item = findItem(spid);
  		var node = document.getElementById('cartInfo_'+spid);
		var n = node.children[1].children[0].children[1].innerHTML;
		if(n == 0 && num < 0){
			
		}else{
			node.children[1].children[0].children[1].innerHTML = Number(n) + num;
			node.children[0].children[1].children[0].innerHTML = (Number(n) + num) * item.price ;
			
			addToCartBySpid(spid, num );
			
			isClickMask = false;
			setTimeout(function() {
				isClickMask = true;
			}, 100);
			
		}
	};
  	initCart();
  	
	
  </script>
  
</body>
</html>
