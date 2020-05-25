<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
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
SupplierProductBean bean = (SupplierProductBean) request.getAttribute("bean");
List<SupplierProductCategoryBean> productCategoryList = (List<SupplierProductCategoryBean>)request.getAttribute("productCategoryList");
if(bean == null){
	bean = new SupplierProductBean();
}
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

    <title>商品信息管理</title>
	<link rel="stylesheet" href="css/supplier/signup.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/css/bootstrap-select.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-fileinput@5.0.8/css/fileinput.min.css">
	
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-zh_CN.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap-fileinput@5.0.8/js/fileinput.min.js"></script>
  	<script src="js/bootstrap/fileinput/i18n/zh.js" type="text/javascript"></script>
  </head>
  <body style="background-color: #eee">

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">添加/修改商品信息</h2>
	
      <form class="form-signup" action="supplierProduct/createOrUpdateProduct.html" method="post" enctype="multipart/form-data" onsubmit="return checkRequire()">
        <input type="hidden" name="openid" value="<%=openid%>">
        <input type="hidden" name="sid" value="<%=sid%>">
        <input type="hidden" name="id" value="<%=StringUtil.convertNull(bean.getId())%>">
     	
     	<div style="margin-bottom: 15px;">
	     	<select id="product_category_id" name="product_category_id" class="selectpicker form-control show-tick"  data-live-search="true" title="请选择归属的商品分类"  >
				<% for(SupplierProductCategoryBean category : productCategoryList){ %>
					<option value="<%=category.getId()%>"  ><%=category.getName() %></option>
				<%} %>
			</select>
        </div>
        <label for="product_name" class="sr-only" >商品名称</label>
        <input type="text" id="product_name" name="product_name" value="<%=StringUtil.convertNull(bean.getProduct_name())%>" placeholder="请输入商品名称,建议6字以内" class="form-control"  required="required" maxlength="10" >
        
        <label for="product_title" class="sr-only" >商品描述</label>
        <input type="text" id="product_title" name="product_title" value="<%=StringUtil.convertNull(bean.getProduct_title())%>" class="form-control" placeholder="请输入商品描述,建议20字以内" required="required" maxlength="30" value="">
        
        <label for="product_price" class="sr-only" >商品销售价格（元为单位）</label>
        <input type="number" id="product_price" name="product_price" step="0.01"  value="<%=NumberUtil.divideHave100(bean.getProduct_price())%>" class="form-control" placeholder="请输入商品销售价格（元为单位）" required="required" maxlength="8" value="">
        
        <label for="product_real_price" class="sr-only" >商品市场/划线价格（元）</label>
        <input type="number" id="product_real_price" step="0.01"  name="product_real_price" value="<%=NumberUtil.divideHave100(bean.getProduct_real_price())%>" class="form-control" placeholder="请输入商品市场/划线价格（元为单位）" required="required"  maxlength="8" value="">
        
        <label for="prime_cost_price" class="sr-only" >商品成本价格（元）</label>
        <input type="number" id="prime_cost_price" step="0.01"  name="prime_cost_price" value="<%=NumberUtil.divideHave100(bean.getPrime_cost_price())%>" class="form-control" placeholder="请输入成本价格（元为单位）" maxlength="20" value="">
        
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
        
        <div style="margin-bottom: 15px;">
	     	<select id="status" name="status" class="selectpicker form-control show-tick"  data-live-search="true" title="">
					<option value="1">上架</option>
					<option value="0">下架</option>
			</select>
        </div>
        
        
        <input id="product_img" name="product_img" type="file" class="file" data-browse-on-zone-click="true">
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>
		<%if(bean.getId() != null){ %>
		<button id="deleteBTN" class="btn btn-lg btn-danger btn-block" style="margin-top: 15px;display: none;" type="button" onclick="deleteProduct()">删除商品</button>
		<%} %>
		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">

function checkRequire(){
	var product_category_id = $("#product_category_id").val() ;
	if(product_category_id == ''){
		alert("请选择商品归属分类");
		return false;
	}
	return true;
}
		
function deleteProduct(){
	var id = $("#id").val();
	if(confirm("确定删除这个商品？")){

		location.href="wx/supplierProduct/deleteProduct.html?openid=<%=openid%>&sid=<%=sid%>&id="+id;
	}
}


$("#product_img").fileinput({
    language: "zh",
    dropZoneTitle:"请点击这里上传1M以内100*100的图片",
    dropZoneClickTitle: '',
    maxFileCount: 1,
    maxFileSize: 3072,
    <%if(bean != null && bean.getProduct_img() != null){%>
    initialPreview: ['<img src="<%=bean.getProduct_img() %>" class="file-preview-image" alt="" title="">' ],
    <%}%>
    allowedFileExtensions:  ['jpg', 'png', 'jpeg', 'gif', 'bmp']
});

<%if(bean.getId() != null){%>
		$("#seq").val('<%=bean.getSeq()%>') ;
		$("#product_category_id").val('<%=bean.getProduct_category_id()%>') ;
		$("#status").val('<%=bean.getStatus()%>') ;
		
		$("#seq").selectpicker('refresh');
		$("#product_category_id").selectpicker('refresh');
		$("#status").selectpicker('refresh');
		<%}%>
</script>
</body>
</html>
