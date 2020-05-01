<%@page import="com.yd.business.product.bean.ProductTypeBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
		String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
	
	List<ProductTypeBean> typeList = (List<ProductTypeBean>) request.getAttribute("typeList");
	
 %>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath %>">
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
<title>商品列表页</title>

<link charset="utf-8" href="page/shop/css_wy/common.css" rel="stylesheet">
<link href="page/shop/css_wy/list.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="page/shop/list/css/style.css">	
<link rel="stylesheet" type="text/css"	href="css/common/slide/style.css" />
<script src="js/jquery.js" language="javascript"></script>
<script type="text/javascript" src="js/common/slide/touchslider.js"></script>

</head>

<body class="bj-hui">

	<div class="main_visual" style="height: 160px;">
		<div class="flicking_con" id="flicking_con">
		</div>
		<div class="main_image" >
			<ul id="main_image">
			</ul>
		</div>
	</div>
	
	<div class="g-wrap g-body-hd m-list-nav">
				<div class="m-list-nav-catlog"  onclick="displayCatlog()">
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
	
<div class="list-pic">
   <ul>
   	<% for(SupplierProductBean bean : list) {%>
      <li><a href="<%=basePath%>product/showProductDescById.do?id=<%=bean.getProduct_id()%>">
         <div class="pic"><img src="<%=bean.getHead_img() %>" width="100%"></div>
         <div class="neirong">
            <div class="biao"><img src="page/shop/list/img/biao-rm.png" width="100%"></div>
            <h3 class="dbt"><%=bean.getProduct_name() %></h3>
            <p class="xzsm"><%=bean.getProduct_title() %></p>
            <p class="jiage"><span class="bold">￥</span><span class="jgsz"><%=bean.getProduct_price() / 100d %></span>元</p>
         </div>
      </a></li>
      
      <%} %>
   </ul>
</div>

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
