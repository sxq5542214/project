<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.user.bean.UserAddressBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.other.bean.AddressBean"%>
<%@page import="java.util.List"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<UserAddressBean> list = (List<UserAddressBean>) request.getAttribute("list");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	int userId = user.getId();
	String openid = user.getOpenid();
	String order_code = (String)request.getAttribute("order_code");
 %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<base href="<%=basePath %>" />
<meta name="decorator" content="index_template" />
<title>收货地址列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />
<link href="page/shop/css_wy/font-awesome-4.5.0/css/font-awesome.min.css" rel="stylesheet" />
<link href="page/shop/css_wy/comm.css" rel="stylesheet" type="text/css" />
<link href="page/shop/css_wy/member.css" rel="stylesheet" type="text/css" />

<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
<script type="text/javascript">
	var userId = "<%=userId%>";
	var openid = "<%=openid %>";
	function deleteAddress(id){
		
		if(confirm("确定要删除这条地址信息吗？")){
	
			$.ajax({
				url : "user/deleteUserAddress.do",
				data : {"user_id" : userId, "id" : id},
				success : function(g) {
					
					if(g == "true"){
						alert("删除成功");
						$("#address"+id).hide();
					}else{
						alert("删除失败");
					}
				},
				error : function() {
					alert("删除失败");
				}
			});
		}
	}
	
	
function useWechatAddress(){
	wx.openAddress({
	  success: function (res) {
	    var userName = res.userName; // 收货人姓名
	    var postalCode = res.postalCode; // 邮编
	    var provinceName = res.provinceName; // 国标收货地址第一级地址（省）
	    var cityName = res.cityName; // 国标收货地址第二级地址（市）
	    var countryName = res.countryName; // 国标收货地址第三级地址（国家）
	    var detailInfo = res.detailInfo; // 详细收货地址信息
	    var nationalCode = res.nationalCode; // 收货地址国家码
	    var telNumber = res.telNumber; // 收货人手机号码
	    
//	    var addressInfo = JSON.stringify(res);
	    
	    $.ajax({
			url : "user/addAddressByWechatData.do",
			data : {"openid" : openid,
					"province" : provinceName,
					"city" : cityName,
					"district" : countryName,
					"street_name" : detailInfo,
					"post_code" : postalCode,
					"contact_name" : userName,
					"contact_phone" : telNumber,
					"contact_address" : provinceName+ cityName+ countryName+ detailInfo,
					"user_id" : userId
			},
			success : function(addressid) {
				if(addressid == null || addressid == 'null'){
					alert("使用微信地址失败,请新增地址");
				}else{
					location.href = 'order/shop/setupOrderAddress.do?userAddrId='+ addressid +'&order_code=<%=order_code %>';
				}
			},
			error : function() {
				alert("使用微信地址失败,请新增地址");
			}
		});
	    
	    
	  },
	  fail : function(res){
	  	alert('fail:' + res.errMsg);
	  }
	  
	});

}
</script>
</head>
<body>

<!-- 内页顶部 -->

   <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>&nbsp;返回</b></a>
        </div>
        <h2>收货地址列表</h2>
    </header>
     <div class="h5-1yyg-v1" id="loadingPicBlock">
    
<!-- 栏目页面顶部 -->


<!-- 内页顶部 -->

   <%--  <header class="g-header">
        <div class="head-l">
	        <a href="javascript:;" onclick="history.go(-1)" class="z-HReturn"><s></s><b>返回</b></a>
        </div>
        <h2>收货地址管理</h2>
        <div class="head-r">
	        
        </div>
    </header> --%>
    <input type="hidden" value="<%=userId %>" id="userId" name="userId" />
    <section class="clearfix g-Cart">
    	
	    <article class="clearfix m-round">
		            <ul id="cartBody">
		            	<% for(UserAddressBean bean : list){ %>
	            			<li id="address<%=bean.getId() %>" name="checkedcurr" cid="<%=bean.getId() %>" style="border: 1px solid red;margin-bottom: 10px;">
						        <div style="margin-top: 5px; margin-left: 10px; margin-right: 10px">
						        	
						        	<p style="font-size: 18px;margin:5px">姓名：<%=bean.getContact_name() %> </p>
						        	<p style="font-size: 18px;margin:5px">手机：<%=bean.getContact_phone() %> </p>
						        	<p style="font-size: 18px;margin:5px">地址：<%=bean.getContact_address() %></p>
						        	
						        	 <p style="background-color: #ccc; width: 100%; height: 1px; margin-top: 2px; margin-bottom: 5px;"></p>
						        	 
						        	<% if(StringUtil.isNotNull(order_code)){ %>
							        <a href="order/shop/setupOrderAddress.do?userAddrId=<%=bean.getId() %>&order_code=<%=order_code %>" class="orgBtn" name="choseAddr" cid="" style=" border:#3399FE;width:9em;height:25px;line-height:25px;font-size:14px;margin-left:10%;margin-right:10%;display: inline-block;margin-bottom: 5px;">设为本次收货地址</a>
							        <%} %>
							        <a href="javascript:;" class="orgBtn" name="delLink" cid="" style=" border:#3399FE;width:9em;height:25px;line-height:25px;font-size:14px;display: inline-block;margin-bottom: 5px;" onclick="deleteAddress(<%=bean.getId() %>)">删除</a>
									<p class="f-Cart-Other"></p>
						        	
						        	
							       <%--  <p class="z-Cart-tt"><p style="margin-right: 5px"><strong style="margin-right: 5px;font-size: 13px;">${entry.consignee}</strong><strong style="margin-right: 10px;font-size: 13px;">${entry.phone}</strong>
							        <c:choose>
							        	<c:when test="${entry.status ==1}"><em style="color: red;font-size: 13px;">默认地址</em></c:when>
							        </c:choose>
							        
							        
							        </p></p>
							        <p class="gray9" style="font-size: 13px;">${entry.province},${entry.city},${entry.district},${entry.address}</p>
							        <p style="background-color: #ccc; width: 100%; height: 1px; margin-top: 2px; margin-bottom: 2px;"></p>
							        <p class="f-Cart-Other" id="btn">
								        <a href="javascript:;" class="fr z-del" name="delLink" cid="${entry.id }"></a>
								         <c:choose>
								        	<c:when test="${entry.status !=1}">
												<a href="javascript:;" style="padding-top: 5px;font-size: 14px;" class="blue" name="setdefault" cid="${entry.id }">设置为默认地址</a>
											</c:when>
								        </c:choose>
								         <c:if test="${request.id != null }"> <em style="color: red;font-size: 13px;">&nbsp;&nbsp;设为本次收货地址</em>  </c:if>
								        
							        </p> --%>
						        </div>
					        </li>
		            	<%} %>
		            </ul>
		        </article>
		        <div id="divBtmMoney" class="g-Total-bt" >
		        	<a href="javascript: useWechatAddress()" class="orgBtn" style="  border:#3399FE;background: green;float: left;width: 49%" >使用微信地址</a>
		        	<a href="user/toUserAddAddressPage.do?openid=<%=user.getOpenid() %>&order_code=<%=order_code %>" class="orgBtn" style="float:right;width:49%;  border:#3399FE" >添加新的收货地址</a>
		        
		        </div>
    </section>
    
</div>
</body>

</html>

