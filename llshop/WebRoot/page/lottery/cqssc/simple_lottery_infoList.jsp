<%@page import="com.yd.business.lottery.bean.CqsscInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/page/lottery/cqssc/";
	
	List<CqsscInfoBean> list = (List<CqsscInfoBean>)request.getAttribute("list");
	if(list == null) list = Collections.EMPTY_LIST;
			
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- <link rel="shortcut icon" href="http://caipiao.163.com/favicon.ico">
 --><link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://pimg1.126.net/caipiao/wap/img/touchLogo.png?t=2">

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="telephone=no" name="format-detection">
<meta name="renderer" content="webkit">
<meta name="keywords" content="重庆时时彩开奖查询">
<meta name="description" content="重庆时时彩开奖查询">
<meta name="renderer" content="webkit">
<title>【时时彩开奖查询】</title>
<script type="text/javascript">
	window.__loadindStartTime = new Date();
</script>
<link rel="canonical" href="http://caipiao.163.com/award/">

<link rel="stylesheet" href="./css/combo_cd2b5c8.css">

<link rel="stylesheet" href="./css/award.css">
<link rel="stylesheet" href="./css/iconfont.css">

<style>
.gmu-media-detect {
	undefinedtransition: width 0.001ms;
	width: 0;
	position: relative;
	bottom: -999999px;
}

@media screen and (width: 375px) {
	#gmu-media-detect0 {
		width: 100px;
	}
}
</style>
<link rel="stylesheet" type="text/css" media="screen" charset="UTF-8"
	href="./css/topHi.css">
</head>
<body class="" style="" id="notInWebView">
	<article class="docBody clearfix showAwardBottom">
		<div id="header-sticky-wrapper" class="sticky-wrapper"
			style="height: 3.66rem;">
			<header id="header">
				<h1>时时彩开奖公告</h1>
				<!-- <a class="goBack " href="javascript:;" cpurl="/t/index.html"
					target="_self" rel="nofollow">首页</a> 
				<div class="rightBox">
					<a class="indexIcon "
						href="https://m3.ttacp8.com/nfop/tgnrrz/index.htm?auto=start"></a>
				</div>
				-->
			</header>
		</div>



		<section class="awardList">
			<dl>
				<dd>
					<table border="1"
						style="width: 99%; text-align: center;color: black;font-size: 1.18rem; ">
						<tr>
							<td style="width: 28%;">开奖时间</td>
							<td colspan="5">开出号码</td>
							<td style="width: 30%;" colspan="3" >总和</td>
							<td style="width: 11%;">龙虎</td>
						</tr>
						
						<% for(CqsscInfoBean bean : list){ 
							String[] code = bean.getOpenCode().split(",");
							boolean isBig = bean.getTotal().intValue() >= 23;
							boolean isDouble = bean.getTotal().intValue() % 2 == 0;
						%>
						<tr>
							<td><%= bean.getOpenTime().substring(5, 16) %></td>
							<td><strong> <%=code[0] %> </strong></td>
							<td><strong> <%=code[1] %> </strong></td>
							<td><strong> <%=code[2] %> </strong></td>
							<td><strong> <%=code[3] %> </strong></td>
							<td><strong> <%=code[4] %> </strong></td>
							<td style="color: #bc102d;"><strong><%=bean.getTotal() %></strong></td>
							<td <%if(isBig){%> style="color: #bc102d;" <%} %>><%=bean.getBigOrSmall() %></td>
							<td <%if(isDouble){%> style="color: #bc102d;" <%} %>><%=bean.getSingleOrDouble() %></td>
							<td <%if(bean.getDragonOrTiger().equals("龙")){%> style="color: #bc102d;" 
							<%}else if(bean.getDragonOrTiger().equals("和")){%> style="color:green;"  <%} %>>
							<%=bean.getDragonOrTiger() %></td>
						</tr>
						
						<%} %>
						

					</table>

				</dd>

			</dl>
		</section>

		<!--  底部浮动框 -->
		<section class="awardBottom " style="display: none;">
			<a class="clientDownloadButtonInAward"
				href="https://m3.rrzcp8.com/nfop/tgnrrz/index.htm?auto=start">下载客户端</a>
			<a class="buyButtonInAward"
				href="https://m3.ttacp8.com/nfop/tgnbutton/index.htm?from=kjb">领大神方案</a>
		</section>
	</article>



	<div class="gmu-media-detect" id="gmu-media-detect0"></div>
	<div id="forTap"
		style="color: White;opacity:0;border-radius: 60px; position: absolute; z-index: 99999; width: 60px; height: 60px;left:-999px;top:-999px;"></div>

	<!--  靠近右下固定位置的浮动框  -->
	<div class="topSwipeWrap fixed_right_bottom topHiPos4"
		style="display: none;">
		<div>
			<div class="topSwipeItem">
				<a href="https://m3.ttacp8.com/nfop/tgn3-2/index.htm?from=award"><img
					src="" style="width: 5.16667rem;"></a>
			</div>
		</div>
	</div>
</body>
</html>