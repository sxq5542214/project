<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String title = request.getParameter("title");
String sid = request.getParameter("sid");
String openid = request.getParameter("openid");
%>

<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <base href="<%=basePath%>">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">
	
    <title>菜单导航</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </head>
  
  <body>
    
	<nav class="navbar navbar-inverse navbar-static-top" style="margin-bottom : 0px;" id="navMenu">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" id="navTitle">我的店铺</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
          <ul class="nav navbar-nav">
            <li class="active"><a onclick="changeIframe('wx/supplier/shop/toShopManagerIndexPage.html?sid=<%=sid%>',this)">
            		店铺首页</a></li>
          <!--   <li><a href="#about">About</a></li> -->
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		商品发布中心【简单三步】 <span class="caret"></span></a>
              <ul class="dropdown-menu">
        <!--         <li role="separator" class="divider"></li>
                <li class="dropdown-header">商品管理</li> -->
                <li><a onclick="changeIframe('supplierProduct/toCreateOrUpdateProductCategoryPage.html?openid=<%=openid %>&sid=<%=sid%>',this)">
                	新增商品分类（轻松分类）【第一步】</a></li>
                <li><a onclick="changeIframe('supplierProduct/toCreateOrUpdateProductPage.html?openid=<%=openid %>&sid=<%=sid%>',this)">
                	新增商品信息（简单便捷）【第二步】</a></li>
                <li><a href="wx/supplier/shop/toManagerCategoryPage.html?openid=<%=openid %>&sid=<%=sid%>&fromOpenid=<%=openid %>">
                	预览/修改/发布（快速及时）【第三步】</a></li>
                	
               
              </ul>
            </li>
               <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		产品套餐/折扣卡/储值卡管理 （客户留存）<span class="caret"></span></a>
              <ul class="dropdown-menu">
             <!--    <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li> -->
                <li><a onclick="changeIframe('supplier/package/toSupplierPackageManagerPage.html?openid=<%=openid %>&sid=<%=sid%>&fromOpenid=<%=openid %>',this)">
                	产品套餐管理（增加客户留存）</a></li>
              	<li><a onclick="changeIframe('supplier/store/toStoreBalanceCardListPage.html?sid=<%=sid %>',this)">
                	折扣卡/储值卡管理（增加客户留存）</a></li>
               
              </ul>
            </li>
             <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		客户中心（会员管理） <span class="caret"></span></a>
              <ul class="dropdown-menu">
       <!--          <li role="separator" class="divider"></li>
                <li class="dropdown-header">客户管理</li> -->
                <li><a onclick="changeIframe('supplier/user/toSupplierUserListPage.html?sid=<%=sid %>',this)">
                	客户清单</a></li>
               
              </ul>
            </li>
             <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		订单中心（每日详情） <span class="caret"></span></a>
              <ul class="dropdown-menu">
             <!--    <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li> -->
                <li><a onclick="changeIframe('order/shop/toShopOrderListPage.html?openid=<%=openid %>&sid=<%=sid%>',this)">
                	订单列表（每日详情）</a></li>
                <li><a onclick="changeIframe('order/shop/toShopOrderEffListPage.html?openid=<%=openid %>&sid=<%=sid%>',this)">
                	预约订单列表（每日详情）</a></li>
              </ul>
            </li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		营销中心（拉新利器） <span class="caret"></span></a>
              <ul class="dropdown-menu">
             <!--    <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li> -->
                <li><a >
                	优惠券管理（暂未开放）</a></li>
                <li><a >
                	优惠券发放（暂未开放）</a></li>
                <li><a >
                	潜在用户（暂未开放）</a></li>
                
                	
              </ul>
            </li>
         
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		数据中心（敬请期待） <span class="caret"></span></a>
              <ul class="dropdown-menu">
             <!--    <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li> -->
                <li><a >近一周营业数据（暂未开放）</a></li>
                <li><a >近一月营业数据（暂未开放）</a></li>
                <li><a >近一周客户数据（暂未开放）</a></li>
              </ul>
            </li>
              <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
              		店铺设置 <span class="caret"></span></a>
              <ul class="dropdown-menu">
             <!--    <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li> -->
                <li><a onclick="changeIframe('supplier/shop/toSimpleSetUpPage.html',this)">
                	收款方式设置</a></li>
               	<li><a >
               		店铺余额提现【至店铺创建人微信】</a></li>
              </ul>
            </li>
    <!--         <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">智能分析 <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">订单管理</li>
                <li><a href="#">近一周营业数据</a></li>
                <li><a href="#">近一月营业数据</a></li>
                <li><a href="#">近一周客户数据</a></li>
              </ul>
            </li> -->
          </ul>
   <!--        <ul class="nav navbar-nav navbar-right">
            <li><a href="../navbar/">Default</a></li>
            <li class="active"><a href="./">Static top <span class="sr-only">(current)</span></a></li>
            <li><a href="../navbar-fixed-top/">Fixed top</a></li>
          </ul> -->
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	    <div style="background-color: grey;" class="embed-responsive" id="iframeDiv" >  
	        <iframe src="wx/supplier/shop/toShopManagerIndexPage.html?sid=<%=sid%>" id="iframPage" class="embed-responsive-item" width="100%" scrolling="yes" ></iframe>  
	    </div>
  </body>
  <script type="text/javascript">
  	function setTitle(title){
  		$("#navTitle").html(title) ;
  	}
  	function changeIframe(srcUrl , ele){
  		var title = ele.innerHTML ;
  		document.getElementById("iframPage").src = srcUrl ;
  		$('.navbar-collapse').collapse('hide');
  		$("#navTitle").html(title) ;
  	}
  
  	//设定frame的高度，少1像素，不出现双重滚动条
  	var screenHeight = window.screen.height ;
  	var navMenuHeight = $("#navMenu").height() ;
  	var frameHeight = screenHeight - navMenuHeight;
  	$("#iframeDiv").height(frameHeight - 1) ;
  	$("#iframPage").height(frameHeight - 1) ;
  </script>
</html>
