<%@page import="com.yd.business.product.bean.ProductTypeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.basic.framework.context.WebContext"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	List<SupplierProductBean> listProduct = (List<SupplierProductBean>)request.getAttribute("listProduct");
	
	List<ProductTypeBean> typeList = (List<ProductTypeBean>) request.getAttribute("typeList");
	
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	if(user == null) user = new UserWechatBean();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>
	选购商品</title>
	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, width=device-width">
  <!-- <link rel="shortcut icon" type="image/x-icon" href="">
    <link rel="icon" type="image/x-icon" href="">
-->
<link charset="utf-8" href="page/shop/css_wy/common.css" rel="stylesheet">
<link href="page/shop/css_wy/index.css" rel="stylesheet">
<link href="page/shop/css_wy/list.css" rel="stylesheet">

	<link rel="stylesheet" type="text/css"	href="css/common/slide/style.css" />
	<script src="js/cookieUtil.js"></script>
	<script src="js/jquery.js"></script>
	<script type="text/javascript" src="js/common/slide/touchslider.js"></script>

	<script src="page/shop/product/js/shoppingCart.js"></script>
</head> 


<body >
	<div class="g-header">
	</div>

	<div class="main_visual">
		<div class="flicking_con" id="flicking_con">
		</div>
		<div class="main_image" >
			<ul id="main_image">
			</ul>
		</div>
	</div>
	<div class="g-body">
		<div class="m-index">
			<div class="m-index-mod m-index-newArrivals">
				
				<div class="m-index-mod m-index-popular">
					
					<div class="g-wrap g-body-hd m-list-nav">
				<div class="m-list-nav-catlog selected" onclick="displayCatlog()">
					<a href="javascript:void(0)" class=""><span class="txt-red">热门分类</span>
						<i class="ico ico-arrow ico-arrow-s-gray ico-arrow-down"></i>
					</a>
				</div>
				
				<div class="m-list-types">
					<ul class="m-list-types-list">
						<% for(ProductTypeBean type : typeList){ if(type.getType() == ProductTypeBean.TYPE_BRA ){ %>
						<li class=" up"><a
							href="<%=basePath %>supplierProduct/toSupplierProductShopList.do?product_brand=<%=type.getId()%>"><%=type.getName() %></a></li>
							
						<%}} %>
					</ul>
				</div>
				
	
<!-- 所有分类列表 -->
				<div class="m-list-catlog" style="display: none;" id="catlogdiv"
					afmoldstyle="block">
					<ul class="m-list-catlog-list">
						
						<% 	int i = 0;
							for(ProductTypeBean pt : typeList){
							if(pt.getType() == ProductTypeBean.TYPE_CLS ){
						 %>
						
						<li ><a
							href="<%=basePath %>supplierProduct/toSupplierProductShopList.do?product_type=<%=pt.getId()%>"><i
								class="ico ico-type ico-type-<%=i++ %>"></i><%=pt.getName() %></a>
						</li>
						<%}} %>
						
					</ul>
				</div>
			</div>
					
					
					
					<div class="m-index-mod-bd">
						<ul class="w-goodsList w-goodsList-s m-index-popular-list">
							<% for(SupplierProductBean prod: listProduct){
							 %>
							<li class="w-goodsList-item">
								<div class="w-goods w-goods-ing" data-gid="<%=prod.getId() %>"
									data-period="<%=prod.getId() %>" data-price="<%=prod.getProduct_price() %>" data-buyunit="1">
									<div class="w-goods-pic">
<%-- 										<a href="<%=basePath %>product/showProductDescById.do?id=<%=prod.getProduct_id() %>"> 
 --%>										
 										<a href="<%=basePath %>supplierProduct/toSupplierProductShopInfo.do?id=<%=prod.getId() %>&openid=<%=user.getOpenid()%>">
 											<img alt="<%=prod.getProduct_name() %>" src="<%=prod.getHead_img()%>" >
										</a>
									</div>
									<div class="w-goods-info" onclick="btnClick('cartBtn<%=prod.getId()%>')">
									<marquee direction="left" behavior="scroll" scrollamount="5" >
										<p class="w-goods-title f-txtabb" style="overflow:visible;">
											<%=prod.getProduct_name() %>
										</p>
									</marquee>
										<div class="w-progressBar">
											<p class="txt">
												原价：<%=prod.getProduct_real_price() /100d %>元 &nbsp;&nbsp; 
												<%if(prod.getStore_num() == null ||prod.getStore_num() ==0 ){ %><span style="color: red;"> 缺货</span> <%} %> <br>
												<strong>优惠价：<%=prod.getProduct_price()/100d %>元</strong>
											</p>
										</div>
									</div>
									<div class="w-goods-shortFunc">
										<button id="cartBtn<%=prod.getId() %>" class="w-button w-button-round w-button-addToCart" onclick="addProductToCart(<%=prod.getId()%>,1)"></button>
									</div>
								</div></li>
								
								<%} %>
								
						</ul>
						<!-- <div style="line-height: 30px;margin: 5px 4px 0;background: #fff;text-align: center;font-size: 12px;color: #999;">
							<a style="color: #999" href="/list/hot20.html">点击查看更多商品</a>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div class="g-footer">
		<div class="g-wrap">
			<p
				style="margin-bottom: 7px;text-align: left;border-bottom: #DCDCDC 1px dotted;padding: 2px 6px;">
				说明：如有疑问请联系微信客服</p>
			<p class="m-copyright">
			</p>
			<p style="margin-bottom:4em;"></p>
		</div>
	</div>
	<button class="w-button w-button-round w-button-backToTop"
		style="display:none" id="pro-view-0">返回顶部</button>
	<footer class="next">
		<ul>
	    	<li class="w20" style="width: 25%;height: 100%;font-size: 1.5em;"><a href="javascript:void(0);" class="red">首页<br/></a></li>
	    	<li class="w20" style="width: 25%;height: 100%;font-size: 1.5em;"><a href="supplierProduct/toSupplierProductShopList.do">全部商品<br/></a></li>
	    	<li class="w20" style="width: 25%;height: 100%;font-size: 1.5em;"><a href="user/cart/toMycartPage.do"><span id="cartCount" class="numtip" style="display:none">0</span>购物车<br/></a></li>
	    	<li class="w20 noline" style="width: 20%;height: 100%;font-size: 1.5em;"><a href="<%=basePath %>wechat/user/toUserInfoCenter.do?openid=<%=user.getOpenid() %>">我<br/></a></li>
	    </ul>
	</footer>
</body>
<script type="text/javascript">
		advertising('default_shop','');

		function displayCatlog(){
			var style = document.getElementById('catlogdiv').style.display ;
			if(style == 'none'){
				document.getElementById('catlogdiv').style.display = 'block';
			}else{
				document.getElementById('catlogdiv').style.display = 'none';
			}
		}
</script>
</html>