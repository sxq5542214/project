<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
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
SupplierBean supplier = (SupplierBean) request.getAttribute("supplier");
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
	
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-zh_CN.min.js"></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap-fileinput@5.0.8/js/fileinput.min.js"></script>
  	<script src="js/bootstrap/fileinput/i18n/zh.js" type="text/javascript"></script>
  </head>
  <body style="background-color: #eee;padding-top: 0px;">

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">店铺设置</h2>
	
      <form id="addForm" class="form-signup" action="supplier/shop/simpleSetUp.html" method="post" enctype="multipart/form-data" onsubmit="return checkRequire()">
        <input type="hidden" name="id" value="<%=supplier.getId()%>">
     	
     	
     	账户余额：
        <input type="text" class="form-control" readonly="readonly" disabled="disabled" value="<%=supplier.getBalance() / 100d %>">
        
     	
     	
     	店铺展示图（建议为门头图片，横屏）：
        <input id="supplier_img" name="supplier_img" type="file" class="file" data-browse-on-zone-click="true">
        
     	<div style="margin-top: 15px;">
	     	<select id="pay_where" name="pay_where" class="selectpicker form-control show-tick"  data-live-search="true" onchange="displayPersonalImg();" >
				<option value="1"  >收款至：平台账户（可直接提现）</option>
				<option value="2"  >收款至：个人微信</option>
			</select>
        </div>
	    <label for="charge_rate" class="sr-only" >费率（百分之）</label>
	    <div id="charge_rate">
	    	收款费率（百分之）:<input type="number" name="charge_rate" value="<%=NumberUtil.convertNull(supplier.getCharge_rate()) / 10d%>" disabled="disabled" readonly="readonly" class="form-control"  required="required" maxlength="10" >
		</div>
       
       	 个人微信收款码：
        <input id="personal_pay_img" name="personal_pay_img"  type="file" class="file" data-browse-on-zone-click="true">
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>
		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">

function checkRequire(){

	var targetUrl = $("#addForm").attr("action");    
	var data = new FormData($( "#addForm" )[0]);     
	$.ajax({
		 type:'post',  
		 url:targetUrl, 
		 cache: false,    //上传文件不需缓存
		 processData: false, //需设置为false。因为data值是FormData对象，不需要对数据做处理
		 contentType: false, //需设置为false。因为是FormData对象，且已经声明了属性enctype="multipart/form-data"
		 data:data,  
//		 dataType:'json', 
		 success:function(data){
		   if(data == 'success'){
		   		alert('提交成功');
		   }else{
		   	  alert('提交失败');
		   }
		 },
		 error:function(xhr, textStatus, errorThrown){ 
		    /*错误信息处理*/
　　　　　　　　alert("请求失败"); 
		 }
	})


	return false;
}
		


$("#supplier_img").fileinput({
    language: "zh",
    dropZoneTitle:"请点击这里上传1M以内16:9的店铺图片",
    dropZoneClickTitle: '',
    maxFileCount: 1,
    maxFileSize: 3072,
    <%if(supplier != null && supplier.getSupplier_img() != null){%>
    initialPreview: ['<img src="<%=supplier.getSupplier_img() %>" class="file-preview-image" alt="" title="">' ],
    <%}%>
    allowedFileExtensions:  ['jpg', 'png', 'jpeg', 'gif', 'bmp']
});


$("#personal_pay_img").fileinput({
    language: "zh",
    dropZoneTitle:"请点击这里上传1M以内个人微信收款二维码图片",
    dropZoneClickTitle: '',
    maxFileCount: 1,
    maxFileSize: 3072,
    <%if(supplier != null && supplier.getPersonal_pay_img() != null){%>
    initialPreview: ['<img src="<%=supplier.getPersonal_pay_img() %>" style="max-width: 100%; max-height: 100%;" class="file-preview-image" alt="" title="">' ],
    <%}%>
    allowedFileExtensions:  ['jpg', 'png', 'jpeg', 'gif', 'bmp']
});

function displayPersonalImg(){
	var val = $("#pay_where").val();
	if(val == '1'){
		$("#charge_rate").html('收款费率（百分之）:<input type="number" name="charge_rate" value="<%=NumberUtil.convertNull(supplier.getCharge_rate()) / 10d%>" disabled="disabled" readonly="readonly" class="form-control"  required="required" maxlength="10" >');
	}else{
		$("#charge_rate").html('无费率，请上传个人微信收款码。<span style="color:red;">此模式下因无法获取支付信息，将无法为您通知收款消息、订单信息且营销功能无法使用。</span>');
	}
	
}
<%if(supplier.getPay_where() != null && supplier.getPay_where() == SupplierBean.PAY_WHERE_PERSONAL){%>
		$("#pay_where").val('<%=supplier.getPay_where()%>') ;
		$("#pay_where").selectpicker('refresh');
		
		$("#charge_rate").hide();
		<%}%>
</script>
</body>
</html>
