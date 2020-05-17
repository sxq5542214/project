<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderProductBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String openid = request.getParameter("openid");
String sid = request.getParameter("sid");
String queryDate = request.getParameter("queryDate");
if(StringUtil.isNull(queryDate)){
	queryDate = DateUtil.getNowOnlyDateStr();
}
String tomorrow = DateUtil.plusDate(queryDate, 1,false);
String yesterday = DateUtil.plusDate(queryDate, -1,false);


List<ShopOrderInfoBean> listOrder = (List<ShopOrderInfoBean>)request.getAttribute("listOrder");

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
	
    <title>每日订单清单</title>
	<link rel="stylesheet" href="css/supplier/signup.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 	<style type="text/css">
 	.panel {
 		margin-bottom:  10px;
 	}
 	</style>
 	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
  </head>
 
  <body style="background-color: #eee;padding-top: 0px;">

	
    <div class="container-fluid" style="padding: 0;"><!-- 
      <div class="table-responsive"> -->
      <div class="">
      				<%  int i = 0; String classStr = "";
      					for(ShopOrderInfoBean order : listOrder){ 
      					String name = StringUtil.isNotNull(order.getContact_name())?order.getContact_name():order.getNick_name();
      					boolean cancel = false;
      					if(order.getStatus() == ShopOrderInfoBean.STATUS_USER_DELETE || order.getStatus() == ShopOrderInfoBean.STATUS_WAIT){
      						cancel = true;
      					}
      					switch(i++ % 3){
      						case 0:	classStr = "primary";
      						break;
      						case 1: classStr = "success";
      						break;
      						case 2: classStr = "warning";
      						break;
      					}
      				%>
      				<div class="panel panel-<%=classStr %> cancel<%=order.getStatus() %>">
      					<div class="panel-heading">【<%=StringUtil.convertNull(order.getCreate_time()).substring(5, 16) %>】【<%=name %>】 【<%=order.getDictValueByField("status") %>】  
      					</div>
						  <div class="panel-body">
						    <%=order.getProduct_name_str() %>
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				
      				
      				<%}		//如果没有数据
      				if(listOrder.size() == 0 ){
      				 %>
      				 <div class="panel panel-primary ">
      					<div class="panel-heading">【今日无数据】
      					</div>
						  <div class="panel-body">
						  	  【无订单数据】
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				 <%} %>
      </div>
    </div> <!-- /container -->
    <div class="navbar navbar-fixed-bottom container-fluid">
    	<div class="row">
	    	<div style="text-align: center;">
				<a href="order/shop/toShopOrderListPage.html?sid=<%=sid %>&queryDate=<%=yesterday %>" style="width: 40%;" role="button" class="btn btn-info "  >前一天</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="order/shop/toShopOrderListPage.html?sid=<%=sid %>&queryDate=<%=tomorrow %>" style="width: 40%;" role="button" class="btn btn-primary " >后一天</a>
	    	</div>
    	</div>
	</div>
</body>
<script type="text/javascript">
	$(".cancel-3").attr("class","panel panel-danger");
	window.parent.setTitle('<%=queryDate%>订单清单');
</script>
</html>
