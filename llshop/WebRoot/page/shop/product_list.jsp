<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
	
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base href="<%=basePath %>" />
		<meta name="decorator" content="index_template" />
		<title>商品列表页 </title>
		<link rel="stylesheet" type="text/css"
			href="page/shop/css_wy/product_list.css" />
		<link rel="stylesheet" type="text/css"
			href="page/shop/css_wy/product_list_header.css" />
	</head>

	<body class="buy">
		
		<!-- start -->
		<div id="loadingPicBlock" class="wrap">
			<div class="Current_nav">
				<a href="/">首页</a> &gt; ${typeName }
			</div>
			<div class="list_Curtit" id="current">
				<h1 class="fl">${typeName }</h1>
				<span id="spTotalCount">(共<em class="orange">${resultCount }</em>件相关商品)</span>
			</div>
			<div class="listContent">
				<ul id="ulGoodsList" class="item">
					<% for(SupplierProductBean bean : list){ %>
					<li codeid="" class="goods-iten">
							<div class="pic">
								<a title="<%=bean.getName() %>" target="_blank" href="<%=basePath%>/product/showProductDescById.do?id=<%=bean.getProduct_id()%>" rel="nofollow">
									<i class="goods_rq"></i>
								<img  class="scrollLoading" src="<%=bean.getHead_img()%>" title="<%=bean.getProduct_name() %>" alt="<%=bean.getProduct_title() %>" />
								</a>
								<p style="display: none;" name="buyCount"></p>
							</div>
							<p class="name">
								<a title="<%=bean.getName() %>" target="_blank" href="<%=basePath %>/product/showProductDescById.do?id=<%=bean.getProduct_id()%>"><%=bean.getProduct_name() %></a>
							</p>
							<p style="color: #999;font-family: 宋体;font-size: 16px;padding: 0 10px 0;text-align: left;">优惠价：<span style="background-position: 0 -3px;background-repeat: no-repeat;color: #f60;padding-left: 10px;">￥<%=bean.getProduct_price() / 100d %></span>
							</p>
							<div class="Progress-bar">

							</div>
							<div class="gl_buybtn">

								<div class="go_buy">
									<a class="go_Shopping fl" style="padding: 15px 55px;font-size: 24px;" title="立即购买" prId="${Products.productId }" price="${Products.productPrice }" name="goPay" href="javascript:;">立即购买</a>
								</div>
							</div>
							<div class="goods_Curbor"></div>
						</li>
					<%} %>
				</ul>
			</div>
			<div class="list_Pageline"></div>
			${pageString }
		</div>
		<!-- end -->
		<script type="text/javascript" src="js/jquery.scrollloading-min.js"></script>
	</body>
</html>
