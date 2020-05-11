<%@page import="com.yd.business.supplier.bean.SupplierUserBean"%>
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
List<SupplierUserBean> userList = (List<SupplierUserBean>)request.getAttribute("userList");

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
      <form class="form-signup" action="supplier/package/packageAssignUser.html" method="post" onsubmit="return checkRequire()">
     	<input type="hidden" name="supplier_package_id" value="<%=StringUtil.convertNull(pack.getId())%>">
     	
     	<div class="panel panel-primary ">
    		<div class="panel-heading">【<%=pack.getName() %>】  </div>
			  <div class="panel-body">
			    <%=pack.getTitle() %>
			</div>
		</div>
        
     	<div style="margin-bottom: 15px;">
	     	<select id="user_id" name="user_id"  title="请选择用户，可通过手机号查询"  data-height="50px" class="selectpicker form-control show-tick" data-live-search="true" onchange="showMoreInfo()" >
				<%for(SupplierUserBean user : userList){ %>
					<option id="<%=user.getUser_id() %>" class="<%=StringUtil.convertNull(user.getPhone()) %>" title="<%=user.getNick_name() %>" value="<%=user.getUser_id()%>" data-tokens="<%=user.getPhone()%>"><%=user.getNick_name() +"【"+ StringUtil.convertNull(user.getPhone()) %>】</option>
				<%} %>
			</select>
        </div>
        
        <label for="nick_name" class="sr-only" >用户名称</label>
        <input type="text" id="nick_name" name="nick_name" class="form-control" style="display: none;" placeholder="此处可修改用户的名称"  maxlength="20" value="">
        
        <label for="phone" class="sr-only" >手机号</label>
        <input type="number" id="phone" name="phone" class="form-control" style="display: none;" placeholder="此处可修改用户的手机号"   maxlength="20" value="">
        
        
        <button  class="btn btn-lg btn-primary btn-block" style="margin-top: 15px;" type="submit">确定提交</button>
		<button id="deleteBTN" class="btn btn-lg btn-danger btn-block" style="margin-top: 15px;display: none;" type="button" onclick="deleteCategory()">删除套餐</button>
		<button  class="btn btn-lg btn-info btn-block" style="margin-top: 15px;" type="button" onclick="history.go(-1)">返回</button>
      </form>
		
    </div> <!-- /container -->
<script type="text/javascript">

function showMoreInfo(){
	var nick_name = $("#nick_name").show() ;
	var phone = $("#phone").show() ;
	var user_id = $("#user_id").val();
	
	var userInfo = document.getElementById(""+user_id);
	if(userInfo.className != '' ){
		phone.val(userInfo.className);
		nick_name.val(userInfo.title);
	}else{
		phone.val('');
		nick_name.val(userInfo.title);
	}
	
}

function checkRequire(){
	var user_id = $("#user_id").val();
	if(user_id == null || user_id == 'null' || user_id.length <=0){
		alert("请选择需要分配套餐的会员！");
		return false;
	}
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
