<%@page import="com.yd.util.JsonUtil"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductCategoryBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierTypeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String openid = request.getParameter("openid");
String sid = request.getParameter("sid");
List<SupplierProductCategoryBean> productCategoryList = (List<SupplierProductCategoryBean>)request.getAttribute("productCategoryList");
String jsoStr = JsonUtil.convertObjectToJsonString(productCategoryList);
%>

<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <base href="<%=basePath%>">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">

    <title>商品分类管理</title>
	<link rel="stylesheet" href="css/supplier/signup.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-zh_CN.min.js"></script>
  </head>
  <body style="background-color: #eee">

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">商品分类信息</h2>
	
      <form class="form-signup" action="supplierProduct/createOrUpdateProductCategory.html" method="post" onsubmit="return checkRequire()">
        <input type="hidden" name="openid" value="<%=openid%>">
        <input type="hidden" name="sid" value="<%=sid%>">
     	
     	<div style="margin-bottom: 15px;">
	     	<select id="id" name="id" class="selectpicker form-control show-tick"  data-live-search="true" title="" onchange="updateData()"  >
					<option value="">新增商品分类</option>
				<% for(SupplierProductCategoryBean bean : productCategoryList){ %>
					<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
				<%} %>
			</select>
        </div>
        <label for="name" class="sr-only" >分类名称</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="请输入分类名称,建议4字以内" required="required" maxlength="10" value="">
        
        <div style="margin-bottom: 15px;">
	     	<select id="seq" name="seq" class="selectpicker form-control show-tick"  data-live-search="true" title="">
					<option value="16">请选择排序，越大越靠后</option>
					<option value="1">1</option>
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
					<option value="6">6</option>
					<option value="7">7</option>
					<option value="8">8</option>
					<option value="9">9</option>
					<option value="10">10</option>
					<option value="11">11</option>
					<option value="12">12</option>
					<option value="13">13</option>
					<option value="14">14</option>
					<option value="15">15</option>
			</select>
        </div>
        
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>

		<button id="deleteBTN" class="btn btn-lg btn-danger btn-block" style="margin-top: 15px;display: none;" type="button" onclick="deleteCategory()">删除分类</button>

		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">
var listData = $.parseJSON( '<%=jsoStr%>' );

function checkRequire(){
	var seq = $("#seq").val() ;
	var name = $("#name").val();
	if(seq.length >5){
		alert("排序请小于五位数");
		return false;
	}
	if(name.length >10){
		alert("名称请小于十个字");
		return false;
	}
	return true;
}
function updateData(){
	var id = $("#id").val();
	
	if(id == ''){
		$("#seq").val(0) ;
		$("#name").val('');
		$("#deleteBTN").hide();
	}else{
		for(var i = 0 ; i < listData.length ;i++){
			if(listData[i].id == id){
				$("#seq").val(listData[i].seq) ;
				$("#name").val(listData[i].name);
				
				$("#deleteBTN").show();
				break;
			}
		}
	}
	$("#seq").selectpicker('refresh');
}
function deleteCategory(){
	var id = $("#id").val();
	if(confirm("确定删除这个分类？分类下所有商品也会删除")){

		location.href="wx/supplierProduct/deleteProductCategory.html?openid=<%=openid%>&sid=<%=sid%>&id="+id;
	}
}

</script>
</body>
</html>
