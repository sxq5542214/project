<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierPackageProductBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierPackageBean"%>
<%@page import="com.yd.util.JsonUtil"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String sid = request.getParameter("sid");
String openid = request.getParameter("openid");
SupplierPackageBean pack = (SupplierPackageBean)request.getAttribute("pack");
List<SupplierProductBean> listProduct = (List<SupplierProductBean>)request.getAttribute("listProduct");
List<SupplierPackageProductBean> ppList = (List<SupplierPackageProductBean>)request.getAttribute("ppList");
String jsoStr = JsonUtil.convertObjectToJsonString(listProduct);
String ppListStr = JsonUtil.convertObjectToJsonString(ppList);

if(pack == null){
	pack = new SupplierPackageBean();
}
Integer id = pack.getId();
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

    <title>新增/修改产品套餐</title>
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
<!--         <h2 class="form-signup-heading" style="text-align: center;">产品套餐列表</h2>
 -->	
      <form class="form-signup" action="supplier/package/createOrUpdateSupplierPackage.html" method="post" onsubmit="return checkRequire()">
        <input type="hidden" name="supplier_id" value="<%=sid%>">
     	<input type="hidden" name="id" value="<%=StringUtil.convertNull(id)%>">
     	<input type="hidden" name="nums" id="nums" value="">
     	<input type="hidden" name="seq" id="seq" value="16">
     	
        <label for="name" class="sr-only" >套餐名称</label>
        <input type="text" id="name" name="name" class="form-control" placeholder="请输入产品套餐名称,建议8字以内" required="required" maxlength="20" value="<%=StringUtil.convertNull(pack.getName()) %>">
        

     	<div style="margin-bottom: 15px;">
	     	<select id="status" name="status" class="selectpicker form-control show-tick"  title=""  >
					<option value="1">启用</option>
					<option value="0">不启用</option>
			</select>
        </div>
        
     	<div style="margin-bottom: 15px;">
	     	<select id="useful_lift" name="useful_lift" class="selectpicker form-control show-tick"  title="请选择用户获得套餐后的有效期"  >
					<option value="0">套餐有效期：无限期</option>
					<option value="1825">套餐有效期：五年</option>
					<option value="1095">套餐有效期：三年</option>
					<option value="730">套餐有效期：两年</option>
					<option value="365">套餐有效期：一年</option>
					<option value="183">套餐有效期：半年</option>
					<option value="93">套餐有效期：一季度</option>
					<option value="31">套餐有效期：一个月</option>
					<option value="7">套餐有效期：一周</option>
					<option value="1">套餐有效期：一天</option>
			</select>
        </div>
      	
        <div style="margin-bottom: 15px;">
	     	<select id="products" name="products" class="selectpicker form-control show-tick"  data-live-search="true" title="请选择套餐内包含的产品" multiple onchange="updateProductNums()" >
					<optgroup label="Picnic">
						<option value="">xxxx</option>
					</optgroup>
			</select>
        </div>
        
        <div class="container" id="productNumInputs">
	        
        </div>
        
                
        <label for="product_price" class="sr-only" >销售价</label>
        <input type="number" id="product_price" name="product_price" step="0.01" class="form-control" placeholder="请输入产品套餐销售价格（单位元）" required="required" value="<%=NumberUtil.divideHave100(pack.getProduct_price()) %>">
        
        <label for="product_real_price" class="sr-only" >市场价</label>
        <input type="number" id="product_real_price" name="product_real_price" step="0.01" class="form-control" placeholder="请输入产品套餐市场价/划线价（单位元）" required="required" value="<%=NumberUtil.divideHave100(pack.getProduct_real_price()) %>">
        
        <label for="prime_cost_price" class="sr-only" >成本价</label>
        <input type="number" id="prime_cost_price" name="prime_cost_price" step="0.01" class="form-control" placeholder="请输入产品套餐成本价（单位元）" required="required" value="<%=NumberUtil.divideHave100(pack.getPrime_cost_price()) %>">
        
        
        <label for="remark" class="sr-only" >套餐备注</label>
        <textarea id="remark" name="remark" class="form-control" rows="3" maxlength="128" placeholder="请输入产品套餐备注" ><%=StringUtil.convertNull(pack.getRemark() ) %></textarea>
        
        
        
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>

		<button id="deleteBTN" class="btn btn-lg btn-danger btn-block" style="margin-top: 15px;display: none;" type="button" onclick="deleteCategory()">删除套餐</button>

		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">

var listData = $.parseJSON( '<%=jsoStr%>' );
var listPPData = $.parseJSON( '<%=ppListStr%>' );
var useful_lift = '<%=NumberUtil.convertNull(pack.getUseful_lift())%>';
var ppMap = new Map();
  updateProductList(listData);
  initProductsSelect(listPPData);
$("#useful_lift").selectpicker('val',useful_lift);


function initProductsSelect(listPPData){
	var ids = new Array();
	for(var i = 0 ; i < listPPData.length; i++){
		ids.push(listPPData[i].supplier_product_id );
		ppMap.set(listPPData[i].supplier_product_id+"",listPPData[i].num);
	}
	
	if(listPPData.length > 0){
		$("#products").selectpicker('val',ids);
		updateProductNums();
	}
}

function updateProductList(listData){
	var str = '';
	var category = '';
	for(var i = 0 ; i < listData.length ; i++){
		var data = listData[i];
		
		if(category != data.product_category_id){
			if(i != 0){
				str += '</optgroup>'
			}
			str += '<optgroup label="'+ data.product_category_name +'">';
			category = data.product_category_id;
		}
		
		str+= '<option value="'+ data.id +'">'+ data.product_name +'</option>';
		
	}
	
	if(str != ''){
		str += '</optgroup>';
	}
	
	$("#products").html(str);
	$("#products").selectpicker('refresh');
}

function updateProductNums(){

	var products = $("#products").val() +'';
	if(products != null && products != 'null' && products.length >0){
		var choiceList = products.split(",");
		var str = '<div style="width:100%;height:30px;"><label style="float:left;">商品名称</label><label style="float:right;width:15%;"> 数量 </label> </div>';
		
		for(var x = 0 ; x < listData.length ; x++){
		
			for(var i = 0 ; i < choiceList.length ; i++){
	
				if(choiceList[i] == listData[x].id){	
				//历史数据中找
					var value = ppMap.get(choiceList[i]+"");
					if(value == null || value =='null' || value == ''){
						value = 1;
					}
					str += '<div style="width:100%;height:30px;font-size:14px;"><label style="float:left;">' +listData[x].product_name + '</label><input type="number" name="num" style="float:right;width:15%;" value="'+ value +'"> </div>';
				}
			}
		}
		$("#productNumInputs").html(str);
	}
}
	
function checkRequire(){
	var products = $("#products").val() ;
	var name = $("#name").val();

	
	if(products == null || products == 'null' || products.length <=0){
		alert("请选择具体产品");
		return false;
	}
	if(name.length >10){
		alert("名称请小于十个字");
		return false;
	}
	
	var nums = $("#productNumInputs").find("input[name='num']");
	var numStr = '';
	for(var i = 0 ; i < nums.length ; i++){
		numStr += nums[i].value + ',';
	}
	$("#nums").val(numStr);
	
	return true;
}
function deleteCategory(){
	var id = $("#id").val();
	if(confirm("确定删除这个套餐？   已获得该套餐的用户将不受影响！")){

		location.href="wx/supplierProduct/deleteProductPackage.html?openid=<%=openid%>&sid=<%=sid%>&id="+id;
	}
}

</script>
</body>
</html>
