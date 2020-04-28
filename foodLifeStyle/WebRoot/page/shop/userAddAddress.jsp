<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.other.bean.AddressBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<AddressBean> list = (List<AddressBean>) request.getAttribute("list");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	int userId = user.getId();
	String order_code = (String)request.getAttribute("order_code");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta name="decorator" content="index_template" />
<title>新增收货地址</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link href="page/shop/css_wy/font-awesome-4.5.0/css/font-awesome.min.css" rel="stylesheet" />
<link href="page/shop/css_wy/comm.css" rel="stylesheet" type="text/css" />
<link href="page/shop/css_wy/member.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	function queryAddress(parentid){
			
	}
	
	function queryCity(){
		var province = $("#province").val();
		$.ajax({
			url : "other/address/queryAddress.do",
			data : {"parent_id" : province},
			success : function(g) {
				g =  eval("(" +g+")") ;
				var f = '<option value="">请选择</option>';
				for (var d = 0; d < g.length; d++) {
				
					f += '<option value="' + g[d].id + '">' + g[d].name
							+ "</option>";
				}
				$("#city").html(f);
				$("#district")
						.html('<option value="">请选择</option>');
			},
			error : function() {
				alert("\u83b7\u53d6\u5730\u533a\u5931\u8d25\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
			}
		});
	}
	
	function queryDistrict(){
		var city = $("#city").val();
		$.ajax({
			url : "other/address/queryAddress.do",
			data : {"parent_id" : city},
			success : function(g) {
				g =  eval("(" +g+")") ;
				var f = '<option value="">请选择</option>';
				for (var d = 0; d < g.length; d++) {
				
					f += '<option value="' + g[d].id + '">' + g[d].name
							+ "</option>";
				}
				$("#district").html(f);
			},
			error : function() {
				alert("\u83b7\u53d6\u5730\u533a\u5931\u8d25\uff0c\u8bf7\u7a0d\u540e\u518d\u8bd5\uff01");
			}
		});
	}
	
	function submitForm(){
		var flag = true;
		if($("#province").val() == ''){
			alert('请选择省份');
			flag = false;
		}else if($("#city").val() == ''){
			alert('请选择城市');
			flag = false;
		}else if($("#district").val() == ''){
			alert('请选择区县');
			flag = false;
		}else if($("#street_name").val() == ''){
			alert('请填写街道地址');
			flag = false;
		}else if($("#post_code").val() == ''){
			alert('请填写邮编');
			flag = false;
		}else if($("#contact_name").val() == ''){
			alert('请填写收货人名称');
			flag = false;
		}else if($("#contact_phone").val() == ''){
			alert('请填写联系电话');
			flag = false;
		}
		if(flag){
			$("#addAddressForm").submit();
		}
		return flag;
	}
	
</script>
</head>
<body>

<!-- 内页顶部 -->

   <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>&nbsp;返回</b></a>
        </div>
        <h2>新增收货地址</h2>
    </header>
<section class="clearfix g-member">
	<div class="m-round m-member-nav">
		<form id="addAddressForm" action="user/addAddress.do" method="post">
			<input type="hidden" value="<%=userId %>" id="user_id" name="user_id" />
			<input type="hidden" value="<%=order_code %>" id="order_code" name="order_code" />
		    <ul id="ulFun">
			    <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 所在省份：
			    	<select name="province" id="province" onchange="queryCity()" style="width:200px;height: 40px;font-size: 16px;">
			    		<option  value="">请选择</option>
			    		<%for(AddressBean bean : list){ %>
			    		<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
			    		<%} %>
			    	</select>
				</li>
			    <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 所在城市：
			    	<select name="city" id="city" onchange="queryDistrict()" style="width:200px;height: 40px;font-size: 16px;"><option  value="">请选择</option></select>
			    </li>
                <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 所在区县：
                	<select name="district" id="district" style="width:200px;height: 40px;font-size: 16px;"><option  value="">请选择</option></select>
                </li>
                <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 街道地址：
                	<input name="street_name" id="street_name" type="text" maxlength="100" placeholder="(不需要重复填写省/市/区)" style="width:200px;height: 40px;font-size: 16px;" />
                </li>
			    <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 邮政编码：
			    	<input name="post_code" id="post_code" type="text" size="6" maxlength="6" style="width:200px;height: 40px;font-size: 16px;" />
			    </li>
			    <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 收货人名：
			    	<input name="contact_name" id="contact_name" type="text" maxlength="20" style="width:200px;height: 40px;font-size: 16px;"/>
			    </li>
			    <li>&nbsp;&nbsp;<em id="region_id_msg_valid" class="red">*</em> 联系电话：
			    	<input name="contact_phone" id="contact_phone" type="text" maxlength="15" style="width:200px;height: 40px;font-size: 16px;"/>
			    </li>
			    <li><a href="javascript:;" onclick="return submitForm()" id="btnConfrim" class="nextBtn  orgBtn">确 认</a>
			    	<input name="forward" type="hidden" id="forward" value="myUser" /></li>
		    </ul>
		    </form>
	    </div>
</section>
</body>

</html>
