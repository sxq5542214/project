<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductCategoryBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String openid = (String)request.getAttribute("openid");
	SupplierBean supplier = (SupplierBean)request.getAttribute("supplier"); 
	List<SupplierProductBean> productList = (List<SupplierProductBean>) request.getAttribute("productList");
	List<SupplierProductCategoryBean> productCategoryList = (List<SupplierProductCategoryBean>) request.getAttribute("productCategoryList");
	
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
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/vue@2.6.11/dist/vue.min.js"></script>
	
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
			src="images/shop/topBannerFree.png">
		</a>
	</div>
	<div class="swiper-container">
<!-- 		<ul class="swiper-container-ul">
			<li class="swiper-container-ul-li actives" style="width: 100%;color:black;border-color:gray;">商品分类</li>
			      <li class="swiper-container-ul-li">店铺</li>

		</ul> -->
		<div class="swiper-wrapper">
			<div class="swiper-slide" style="margin-bottom: 52px;">
				<div class="content">
					<div class="left" id="left">
						<ul>
							<li v-for="item in items">{{item.seq}}{{item.name}}</li>
						</ul>
					</div>
					<div class="right" id="right">
						<ul>
							<li v-for="item in items">
								<div class="class-title">{{item.class}}</div>
								<div v-for="ite in item.list">
									<div class="item">
											<a :href="ite.productUrl" style="text-decoration:none;color: black;">
										<div class="item-left">
												<div class="item-img">
													<img :src="ite.imgUrl" alt="" width="100%" height="100%">
												</div>
										</div>
										<div class="item-right">
												<div class="title">{{ite.name}}</div>
												<div class="subtitle">{{ite.title}}</div>
											<div class="price" >
												<div style="float: left;">¥{{ite.price}}</div>
												<div style="float: left;text-decoration: line-through;color: black;font-size: 1rem; line-height: 25px;">{{ite.real_price}}</div>
												<!-- 
												<div style="margin-right: 10px;position: absolute;right: 0;width: 74px;" :id="ite.id">
													<div style="float: left;margin-left: 5px; color: #333;font-size: 1rem;height: 25px;" onclick="addToCart(this,-1)">
														<span style="line-height: 25px; color: red;" >添加/修改</span>
													</div>
												</div> -->
											  </div>
												
											</div>
										</div>
										
											</a>
									</div>
								</div>
							</li>
						</ul>
					</div>
				</div>
		</div>
			
			
			<div class="swiper-slide" style="display:none;">店铺介绍</div>
	</div>
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
      	{ name : '编辑内容'  } 
      <%for (int i = 0; i < productCategoryList.size(); i++) {
				SupplierProductCategoryBean bean = productCategoryList.get(i);%>
        ,{ name : '<%=bean.getName()%>' , seq:'<%=bean.getSeq()%>' } 
		<%	}%>
        
      ]
    }
  });
  var right = new Vue({
    el: '#right',
    data: {
      items: [
      { class : '编辑内容',list : [ { id:'', name:'添加/修改分类', title : '如需添加/修改左侧商品分类导航请点击这里' ,price:'0' , real_price:'无价格', imgUrl:'images/shop/addShopItem.png', productUrl:'supplierProduct/toCreateOrUpdateProductCategoryPage.html?openid=<%=openid%>&sid=<%=supplier.getId()%>' } ,
      							 { id:'', name:'添加商品', title : '如需添加右侧商品展示信息请点击这里' ,price:'无价格', real_price:'', imgUrl:'images/shop/addShopItem.png', productUrl:'supplierProduct/toCreateOrUpdateProductPage.html?openid=<%=openid%>&sid=<%=supplier.getId()%>'  }
      	
      <% String typeName = "";
			for (int i = 0; i < productList.size(); i++) {
				SupplierProductBean product = productList.get(i);
				if(StringUtil.isNull(product.getProduct_img() )){	product.setProduct_img("images/shop/noUploadShop.png");	}
				if (!typeName.equalsIgnoreCase(product.getProduct_category_name())) {
					%>
       ]}, { class : '<%=product.getProduct_category_name()%>',list : [ {id:'<%=product.getId()%>', name:'<%=product.getProduct_name()%>', title : '<%=product.getProduct_title()%>' ,price:'<%=product.getProduct_price()/100d%>', real_price:'<%=NumberUtil.convertNull(product.getProduct_real_price())/100d%>', imgUrl:'<%=product.getProduct_img()%>', productUrl:'supplierProduct/toCreateOrUpdateProductPage.html?openid=<%=openid%>&sid=<%=supplier.getId()%>&id=<%=product.getId()%>' } 
        
        <%} else {%>
        		,{ id:'<%=product.getId()%>', name:'<%=product.getProduct_name()%>', title : '<%=product.getProduct_title()%>' ,price:'<%=product.getProduct_price()/100d%>' , real_price:'<%=NumberUtil.convertNull(product.getProduct_real_price())/100d %>', imgUrl:'<%=product.getProduct_img()%>', productUrl:'supplierProduct/toCreateOrUpdateProductPage.html?openid=<%=openid%>&sid=<%=supplier.getId()%>&id=<%=product.getId()%>' }
        <%}  typeName = product.getProduct_category_name();  }%>  ]}
      ]
    }
  });
  </script>
  <script type="text/javascript">
  	var isClickMask = true;
  	
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
  	
  	
	
  </script>
  
</body>
</html>
