<%@page import="com.yd.business.supplier.bean.SupplierTypeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
List<SupplierTypeBean> parentTypeList = (List<SupplierTypeBean>)request.getAttribute("parentTypeList");
List<SupplierTypeBean> typeList = (List<SupplierTypeBean>)request.getAttribute("typeList");

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

    <title>商户免费入驻</title>
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
        <h2 class="form-signup-heading" style="text-align: center;">店铺免费入驻</h2>

      <form class="form-signup" action="wx/supplier/supplierSignup.html" method="post" onsubmit="return checkRequir()">
        <label class="form-signup-heading">【<%=user.getNick_name() %>】请填：</label>
        <input type="hidden" name="openid" value="<%=user.getOpenid()%>">
        <label for="custName" class="sr-only">真实姓名</label>
        <input type="text" id="custName" name="custName" class="form-control" placeholder="请输入真实姓名" required="required">
        <label for="phoneNo" class="sr-only">手机号</label>
        <input type="number" id="phoneNo" name="phoneNo" class="form-control" placeholder="请输入手机号" required="required">
        <label for="supplierName" class="sr-only">店铺名称</label>
        <input type="text" id="supplierName" name="supplierName" class="form-control" placeholder="请输入店铺名称" required="required" autofocus="">
        
    
		<select id="supplierType" name="supplierType" class="selectpicker form-control show-tick"  data-live-search="true" title="请选择店铺分类"   >
			<% for(SupplierTypeBean parentType : parentTypeList){ %>
			<optgroup label="<%=parentType.getName()%>">
				<%for(SupplierTypeBean type : typeList){
					if(type.getParentid().intValue() == parentType.getId()){
				 %>
					<option value="<%=type.getId()%>"><%=type.getName() %></option>
				<%}} %>
			</optgroup>
			<%} %>
		</select>	
        
        
<!--         <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me" checked="checked"> 同意入驻协议
          </label>
        </div> -->
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>
      </form>

		
    </div> <!-- /container -->
<script type="text/javascript">
function checkRequir(){
	var supplierType = $("#supplierType").val() ;
	if(supplierType == ''){
		alert("请选择店铺分类");
		return false;
	}
	return true;
}

</script>
</body>
</html>
