<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="UTF-8"  %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    <title>首页</title>
<script src="js/jquery.js"></script>
<script src="js/shad-win-query.js"></script>
<script>
$(function(){
	$(".nav-container ul li").click(function(){
		var n=$(".nav-container ul li").index(this);
		$(".nav-container ul li").removeClass("active-nav-li");
		$(".nav-container ul li").eq(n).addClass("active-nav-li");
		})
	})
function redirctPage(url){
	$('#page-frame').attr('src',url);
}
</script>
<link href="css/index-style.css" rel="stylesheet" type="text/css">
<link href="css/common-style.css" rel="stylesheet" type="text/css">
<link href="css/msg_style.css" rel="stylesheet" type="text/css">
</head>

<body onLoad="colorExchange();">
<div class="nav-container">
	<ul>
        <li onClick="redirctPage('redirect.do?page=supplierArt');"><div class="icon-pos menu-icon"></div><div class="nav-item-word">商户广告管理</div></li>
        <li onClick="redirctPage('redirect.do?page=supplierArtHis');"><div class="icon-pos pay-icon"></div><div class="nav-item-word">商户广告推送历史</div></li>
		<li onClick="redirctPage('redirect.do?page=supplierEvent');"><div class="icon-pos menu-icon"></div><div class="nav-item-word">商户活动管理</div></li>
    </ul>
</div>
<iframe width="1160" height="740" frameborder="0" src="redirect.do?page=supplierArt"  id="page-frame"></iframe>
</body>
</html>
