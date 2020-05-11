<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierUserBean"%>
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
String phone = request.getParameter("phone");
List<SupplierUserBean> userList = (List<SupplierUserBean>)request.getAttribute("userList");
 
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
	<form class="form-signup form-inline " action="supplier/user/toSupplierUserListPage.html" style="max-width: 100%; ">
		<div class="container row">
			<input type="hidden" name="sid" value="<%=sid%>">
	        <input type="number" id="phone" name="phone" value="<%=StringUtil.convertNull(phone) %>" class="form-control " placeholder="请输入手机号" >
			<select id="level" name="level" class="form-control show-tick" title="请选择会员等级"   >
		      	 <option value="">全部会员</option>
		      	 <option value="0">普通会员</option>
		      	 <option value="1">一级会员</option>
		      	 <option value="2">二级会员</option>
		      	 <option value="3">三级会员</option>
		      	 <option value="4">四级会员</option>
		      	 <option value="5">五级会员</option>
			</select>	
        </div>
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 0px;" type="submit">确定查询</button>
      </form> 
	
    <div class="container-fluid" style="padding-left: 15px;"><!-- 
      <div class="table-responsive"> -->
      <div class="">
      				<%  int i = 0; String classStr = "";
      					for(SupplierUserBean user : userList){
      						switch( NumberUtil.convertNull(user.getLevel())){
	      						case 0:	classStr = "defalut";
	      						break;
	      						case 1:	classStr = "primary";
	      						break;
	      						case 2: classStr = "success";
	      						break;
	      						case 3: classStr = "warning";
	      						break;
	      						case 4: classStr = "info";
	      						break;
	      						case 5: classStr = "danger";
	      						break;
      						}
      				%>
      				<div class="panel panel-<%=classStr %> ">
      					<div class="panel-heading">【<%=StringUtil.convertNull(user.getCreate_time()).substring(0, 10) %>】名称【<%=user.getNick_name() %>】号码【<%=StringUtil.convertNull(user.getPhone()) %>】 
      					</div>
						  <div class="panel-body">
						    	【<%=user.getDictValueByField("level") %>】【积分：<%=user.getPoints() %>】
						   <a role="button" class="btn btn-success pull-right btn-xs"  >修改信息</a> 
						  </div>
      				</div>
      				
      				
      				<%}		//如果没有数据
      				if(userList.size() == 0 ){
      				 %>
      				 <div class="panel panel-primary ">
      					<div class="panel-heading">【暂无客户数据】
      					</div>
						  <div class="panel-body">
						  	  【无客户数据】
						<!--   <a href=""  role="button" class="btn btn-success pull-right btn-xs"  >确定</a> -->
						  </div>
      				</div>
      				 <%} %>
      </div>
    </div> <!-- /container -->
</body>
</html>
