<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@page import="com.yd.business.order.bean.OrderProductEffShowPageBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<OrderProductEffShowPageBean> successList = (List<OrderProductEffShowPageBean>) request.getAttribute("successList");
	List<OrderProductEffShowPageBean> waitList = (List<OrderProductEffShowPageBean>) request.getAttribute("waitList");
	Object openid = request.getAttribute("openid");
	int effNum = waitList.size();
	int unEffNum = successList.size();
%>
<html><head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="keywords" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>预约订单中心</title>
	<base href="<%=basePath%>">
	<link href="./page/user/infoCenterCss/bootstrap.min.css" rel="stylesheet">
	<link href="./page/user/infoCenterCss/style2.css" rel="stylesheet">
   <script src="./js/jquery.js"></script>
   <script src="./page/user/infoCenterJs/bootstrap.min.js"></script>
	<script src="./page/user/infoCenterJs/snap.svg-min.js"></script>
	<script src="./page/user/infoCenterJs/ownerOrderLog.js"></script>
	<script src="./page/user/infoCenterJs/jquery.jqtransform.js"></script>
	<script language="javascript">
		$(function(){
			var width=window.screen.width;
			$(".huibg").css("width",width);
			$('.rowElem').jqTransform({imgPath:'./page/user/infoCenterCss/img/'});
		});
		
	</script>
<style type="text/css">
@font-face {font-family: "iconfont";
  src: url('./page/user/infoCenterCss/font.ttf') format('truetype');
}
.iconfont {
  font-family:"iconfont" !important;
  font-size:16px;
  font-style:normal;
  -webkit-font-smoothing: antialiased;
  -webkit-text-stroke-width: 0.2px;
  -moz-osx-font-smoothing: grayscale;
}
.icon-fanhui:before {
    content: "\e617";
}
.productinfo{
float:left}
.tb960x90 {display:none!important;display:none}
*, *:before, *:after {
    -webkit-box-sizing: border-box; 
    -moz-box-sizing: border-box;
    box-sizing: border-box; 
    float:left;
}
</style>
</head>
<body class="huibg">
<input id="effNum" type="hidden" value = "<%=effNum %>"/>
<input id="unEffNum" type="hidden" value = "<%=unEffNum %>"/>
<nav class="navbar text-center">
   <button class="topleft" onclick="javascript:history.go(-1);"><span class="iconfont icon-fanhui"></span></button>
	<a class="navbar-tit center-block">预约订单中心(<%=effNum+unEffNum %>)</a>
</nav>
<br>
<ul id="myTab" class="nav nav-tabs"  style="width: 100%;">
   <li  style="width: 50%;" class="active" id="reservation"><a style="width: 100%;" href="./page/user/ownerOrderLog.jsp#sp1" data-toggle="tab">已预约(<%=effNum %>)</a>
   </li>
   <li class="" id = "effective" style="width: 50%;"><a  style="width: 100%;" href="./page/user/ownerOrderLog.jsp#sp2" data-toggle="tab">已生效(<%=unEffNum %>)</a></li>
</ul>

<div id="myTabContent" class="tab-content">
   <div class="tab-pane fade  active in" id="sp1">
      <ul class="ddlist">
      <%
				for (OrderProductEffShowPageBean opl : waitList) {
					if(opl.getStatus() == 0){
			%>
         <li>
            <div class="oplData">
            <input class="oplId" type="hidden" value = "<%= opl.getId() %>"/>
               <p><a>订购号码：<%=opl.getOrder_account()%></a><a class = "eff_time ">预约时间：<%=opl.getEff_time() %></a></p>
               <p>订单时间：<%=opl.getCreate_time()%></p>
               <p class = "out_trade_code"><a style="width: 100%;">订单编号：<%=opl.getOrder_code() %></a></p>
               <p>商品名称：<%=opl.getProduct_name() %></p>
               <p><a style="color: rgba(0, 137, 255, 0.83);width:100%" class="remark">订单备注：<%=StringUtil.convertNull(opl.getRemark()) %></a></p>
      <div class="rowElem"><div style="width:90px"><label>预约生效时间 :</label></div>
      <div style="width: 190px;">
      <div>
			<input type="radio" class="eff_num" name="question<%= opl.getId() %>" value="0"><label class="effparam effparam0">立即生效</label>
			<input type="radio" class="eff_num" name="question<%= opl.getId() %>" value="1"><label class="effparam effparam1"><%=DateUtil.getNextMonth(1) %>月1号</label></div>
			<div>
			<input type="radio" class="eff_num" name="question<%= opl.getId() %>" value="2"><label class="effparam effparam2"><%=DateUtil.getNextMonth(2) %>月1号</label>
			<input type="radio" class="eff_num" name="question<%= opl.getId() %>" value="3"><label class="effparam effparam3"><%=DateUtil.getNextMonth(3) %>月1号</label></div>
			</div>
			</div>
         </li>
         <%
					}
				}
			%>
      </ul>
   </div>
   <div class="tab-pane fade" id="sp2">
     <ul class="ddlist">
        <%
				for (OrderProductEffShowPageBean opl : successList) {
					if(opl.getStatus() == 1 || opl.getStatus() == 2){
			%>
         <li>
            <div>
               <p><a>订购号码：<%=opl.getOrder_account()%></a><a class = "eff_time ">预约时间：<%=opl.getEff_time() %></a></p>
               <p>订单时间：<%=opl.getCreate_time()%></p>
               <p>订单编号：<%=opl.getOrder_code()%></p>
               <p>商品名称：<%=opl.getProduct_name()%></p>
               <p><div style="color: rgba(0, 137, 255, 0.83);width:100%">订单备注：<%=StringUtil.convertNull(opl.getRemark()) %></div></p>
               <p><a class = "execute_time">生效时间：<%=opl.getExecute_time() %></a></p>
            </div>
         </li>
         <%
					}
				}
			%>
      </ul>
   </div>
</div>
</body></html>