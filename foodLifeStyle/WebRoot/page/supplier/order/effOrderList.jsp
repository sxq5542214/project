<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderProductBean"%>
<%@page import="com.yd.business.order.bean.ShopOrderEffProductBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderEffInfoBean"%>
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


List<ShopOrderEffInfoBean> listOrder = (List<ShopOrderEffInfoBean>)request.getAttribute("listOrder");

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
	
    <title>预订清单</title>
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
	<nav class="navbar navbar-inverse navbar-static-top" style="margin-bottom : 0px;">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" ><%=queryDate %>预订清单</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse" aria-expanded="false" style="height: 1px;">
          <ul class="nav navbar-nav">
            <li class="active"><a href="#">店铺首页</a></li>
            <li><a href="#about">About</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <ul class="nav navbar-nav navbar-right">
            <li><a href="../navbar/">Default</a></li>
            <li class="active"><a href="./">Static top <span class="sr-only">(current)</span></a></li>
            <li><a href="../navbar-fixed-top/">Fixed top</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
	
    <div class="container-fluid" style="padding: 0;"><!-- 
      <div class="table-responsive"> -->
      <div class="">
      				<%  int i = 0; String classStr = "";
      					for(ShopOrderEffInfoBean order : listOrder){ 
      					String name = StringUtil.isNotNull(order.getContact_name())?order.getContact_name():order.getNick_name();
      					boolean cancel = false;
      					if(order.getStatus() != ShopOrderEffInfoBean.STATUS_ORDERING){
      						cancel = true;
      					}
      					switch(i++%3){
      						case 0:	classStr = "primary";
      						break;
      						case 1: classStr = "success";
      						break;
      						case 2: classStr = "warning";
      						break;
      					}
      				%>
      				<div class="panel panel-<%=classStr %> cancel<%=order.getStatus() %>">
      					<div class="panel-heading">【<%=StringUtil.convertNull(order.getEff_date()).substring(5, 16) %>】【<%=name %>】【<%=StringUtil.convertNull(order.getContact_phone()) %>】
      						<br>【<%=order.getDictValueByField("status") %>】  备注【<%=StringUtil.convertNull(order.getRemark()) %>】
      					</div>
						  <div class="panel-body">
						    <%=order.getProduct_name_str() %>
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				
      				
      				<%} %>
      </div>
    </div> <!-- /container -->
    <div class="navbar navbar-fixed-bottom container-fluid">
    	<div class="row">
	    	<div style="text-align: center;">
				<a href="order/shop/toShopOrderEffListPage.html?sid=<%=sid %>&queryDate=<%=yesterday %>" style="width: 40%;" role="button" class="btn btn-info "  >前一天</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="order/shop/toShopOrderEffListPage.html?sid=<%=sid %>&queryDate=<%=tomorrow %>" style="width: 40%;" role="button" class="btn btn-primary " >后一天</a>
	    	</div>
    	</div>
	</div>
</body>
<script type="text/javascript">
	$(".cancel-3").attr("class","panel panel-danger");
</script>
</html>
