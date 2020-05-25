<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierStoreBalanceCardBean"%>
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

SupplierStoreBalanceCardBean card = (SupplierStoreBalanceCardBean)request.getAttribute("card");

card = card == null? new SupplierStoreBalanceCardBean() : card;
// List<SupplierProductCategoryBean> productCategoryList = (List<SupplierProductCategoryBean>)request.getAttribute("productCategoryList");
// String jsoStr = JsonUtil.convertObjectToJsonString(productCategoryList);
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
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/bootstrap-select.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.9/dist/js/i18n/defaults-zh_CN.min.js"></script>
  </head>
  <body style="background-color: #eee">

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">新建/修改折扣卡</h2>
	
      <form class="form-signup" action="supplier/store/createOrUpdateStoreBalanceCard.html" method="post" onsubmit="return checkRequire()">
        <input type="hidden" name="id" value="<%=StringUtil.convertNull(card.getId())%>">
        <input type="hidden" name="status" value="<%=SupplierStoreBalanceCardBean.STATUS_UP %>">
     	
        <label for="name" class="sr-only" >折扣卡/储值卡名称</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="请输入折扣卡/储值卡名称,建议8字以内" required="required" maxlength="10" value="<%=StringUtil.convertNull(card.getName())%>">
        
        <label for="card_price" class="sr-only" >销售价</label>
        <input type="number" id="card_price" name="card_price" step="0.01" class="form-control" placeholder="请输入用户购买此卡的价格，元为单位" required="required" maxlength="10" value="<%=NumberUtil.divideHave100(card.getCard_price())%>">
        
        <label for="init_balance" class="sr-only" >初始金额</label>
        <input type="number" id="init_balance" name="init_balance" step="0.01" class="form-control" placeholder="请输入用户得卡后的初始金额，元为单位" required="required" maxlength="10" value="<%=NumberUtil.divideHave100(card.getInit_balance())%>">
        
     	<div style="margin-bottom: 15px;">
	     	<select id="discount" name="discount" class="selectpicker form-control show-tick"  data-live-search="true" title=""  >
					<option value="1000">折扣：不打折</option>
					<option value="950">折扣：95折</option>
					<option value="900">折扣：9折</option>
					<option value="850">折扣：85折</option>
					<option value="800">折扣：8折</option>
					<option value="750">折扣：75折</option>
					<option value="700">折扣：7折</option>
					<option value="650">折扣：65折</option>
					<option value="600">折扣：6折</option>
					<option value="550">折扣：55折</option>
					<option value="500">折扣：5折</option>
					<option value="450">折扣：45折</option>
					<option value="400">折扣：4折</option>
					<option value="350">折扣：35折</option>
					<option value="300">折扣：3折</option>
					<option value="250">折扣：25折</option>
					<option value="200">折扣：2折</option>
					<option value="150">折扣：15折</option>
					<option value="100">折扣：1折</option>
					<option value="50">折扣：0.5折</option>
			</select>
        </div>
        <label for="remark" class="sr-only" >备注说明</label>
        <input type="text" id="remark" name="remark" class="form-control" placeholder="请输入备注说明，可不填 "   maxlength="30" value="<%=StringUtil.convertNull(card.getRemark())%>">
        
        <div style="margin-bottom: 15px;">
	     	<select id="useful_lift" name="useful_lift" class="selectpicker form-control show-tick"  data-live-search="true" title=""  >
					<option value="0">领取后有效期：无限期</option>
					<option value="1825">领取后有效期：五年</option>
					<option value="1095">领取后有效期：三年</option>
					<option value="730">领取后有效期：两年</option>
					<option value="365">领取后有效期：一年</option>
					<option value="183">领取后有效期：半年</option>
					<option value="93">领取后有效期：一季度</option>
					<option value="31">领取后有效期：一个月</option>
					<option value="7">领取后有效期：一周</option>
					<option value="1">领取后有效期：一天</option>
			</select>
        </div>
        
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>

		<button id="deleteBTN" class="btn btn-lg btn-danger btn-block" style="margin-top: 15px;display: none;" type="button" onclick="deleteCard()">删除此卡</button>

		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">
// var listData = $.parseJSON( '' );
var id = '<%=NumberUtil.convertNull(card.getId()) %>';

function checkRequire(){
	
	
	return true;
}
function deleteCard(){
	
	if(confirm("确定删除？此操作将不可恢复！")){
		location.href="supplier/store/deleteStoreBalanceCard.html?id="+id;
	}
}

if(id > 0 ){
//	$("#deleteBTN").show();
	var discount = '<%=card.getDiscount()%>';
	var useful_lift = '<%=card.getUseful_lift()%>';
	$('#discount').selectpicker('val',discount);
	$('#discount').selectpicker('refresh');
	$("#useful_lift").selectpicker('val',useful_lift);
	$("#useful_lift").selectpicker('refresh');
	
}
</script>
</body>
</html>
