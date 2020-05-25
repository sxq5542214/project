<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderEffInfoBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.yd.util.JsonUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponRecordBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
<%@page import="com.yd.business.order.bean.ShopOrderProductBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	ShopOrderEffInfoBean order = (ShopOrderEffInfoBean) request.getAttribute("order");
//	int expressBottomPrice = (int) request.getAttribute("expressBottomPrice");
	int expressBottomPrice = 0;
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	SupplierBean supplier = (SupplierBean) request.getAttribute("supplier");
	List<SupplierCouponRecordBean> couponList = (List<SupplierCouponRecordBean>) request.getAttribute("couponList");
	List<? extends ShopOrderProductBean> productList = order.getProductList();
	Integer checkCouponId = 0; 
	
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>确认订单</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="page/shop/order/css/base2013.css"
	charset="gbk">
<link rel="stylesheet" type="text/css" href="page/shop/order/css/order2014.src.css"
	charset="gbk">

 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
<script type="text/javascript" src="page/shop/order/js/orderInfo.js"></script>
 <script type="text/javascript" src="js/common/cookieUtil.js"></script>
<script type="text/javascript" src="js/common/date.format.js"></script>
<!--通用头尾css -->
<link rel="stylesheet" type="text/css" href="page/shop/order/css/header.css"
	charset="utf-8">
<body id="body">
	<header style="height: auto;">

		<!-- 通用头 div -->
		<div id="m_common_header" style="min-height:45px;">
			<header class="jd-header" style="height: auto;">
				<div class="jd-header-bar" style="background: white;">
					<div id="m_common_header_goback" class="jd-header-icon-back">
						<a href="javascript:history.go(-1);" >
							<img style="width: 1.05rem; margin-top: 8px; margin-left: 8px;" 
							src="page/shop/order/images/c_back_btn.png">
						</a>
					</div>
					<div class="jd-header-title" style="font-weight: bold">预订单详情</div>
				</div>
				<ul id="m_common_header_shortcut" class="jd-header-shortcut"
					style="display: none;">
					<li id="m_common_header_shortcut_m_index"><a class="J_ping"
						report-eventid="MCommonHead_home" report-eventparam=""
						page_name="" href=""> <span class="shortcut-home"></span> <strong>首页</strong>
					</a></li>
					<li class="J_ping" report-eventid="MCommonHead_CategorySearch"
						report-eventparam="" page_name=""
						id="m_common_header_shortcut_category_search"><a href="">
							<span class="shortcut-categories"></span> <strong>分类搜索</strong> </a>
					</li>
					<li class="J_ping" report-eventid="MCommonHead_Cart"
						report-eventparam="" page_name=""
						id="m_common_header_shortcut_p_cart"><a href=""
						id="html5_cart"> <span class="shortcut-cart"></span> <strong>购物车</strong>
					</a></li>
					<li id="m_common_header_shortcut_h_home" class=" current"><a
						class="J_ping" report-eventid="MCommonHead_MYJD"
						report-eventparam="" page_name="" href=""> <span
							class="shortcut-my-account"></span> <strong>我的京东</strong> </a></li>
				</ul>
			</header>
		</div>

	</header>

	<input type="hidden" id="sid" name="sid" value="<%=supplier.getId() %>">
	<div class="wrap">
		<section class="order-con">
			<ul class="order-list">
				<li>
					<div class="order-box">
						<div class="order-width" style="padding-top: 5px;">
							<p>订单编号：<%=order.getOrder_code() %></p>
							<p>订单金额：￥<%=order.getCost_price()/ 100d %></p>
							<p>订单日期：<%=order.getCreate_time() %></p>
						</div>


					</div></li>
				<li>
					<div class="order-box">
						<ul class="book-list">
							<%for(ShopOrderProductBean product : productList){ 
								String couponStr = "";
								String pointsStr = "暂无积分";
								if(product.getType() == ShopOrderProductBean.TYPE_COUPON){
									couponStr = " （优惠券抵扣）";
								}
								if(product.getCost_points() > 0){
									pointsStr = "可使用积分抵扣："+product.getCost_points() / 100d+"元";
								}
							%>
							<li class="border-bottom">
<%-- 								<a href="product/supplierProduct/toSupplierProductShopInfo.do?id=<%=product.getSupplier_product_id()%>&openid=<%=user.getOpenid()%>">
 --%>								<div class="order-msg">
										<img src="<%=product.getHead_img() %>" class="img_ware">
										<div class="order-msg">
											<p class="title"><%=product.getSupplier_product_name() %></p>
											<p class="price">
												单价：￥<%=product.getOriginal_price() /100d %> 元   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;  数量：* <%=product.getNum() + couponStr %> <!-- <span></span> -->
											</p>
											<p class="order-data" style="font-size: 0.7rem"><%=pointsStr %></p>
										</div>
									</div> 
								<!-- </a> -->
							</li>
							<%} %>
						</ul>
					</div></li>

				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="border-bottom usr-name">
								请选择预约时间（8点之后默认明天）
								<span class="fr">&nbsp;</span>
							</p>
							<div>
								</div>
							<p class="border-bottom">
									日期：<input id="eff_day" type="date" value=""> &nbsp;&nbsp;&nbsp;
									时间：<input id="eff_time" type="time" value="08:00:00">
									<span class="fr">&nbsp;</span>
							</p>  
								<p class="border-bottom">
									姓名：<input type="text" id="contact_name" name="contact_name" style="width: 70%;" value="<%=StringUtil.convertNull(order.getContact_name()) %>" maxlength="30">
									<span class="fr">&nbsp;</span>
								</p>
								<p class="border-bottom">
									电话：<input type="number" id="contact_phone" name="contact_phone" style="width: 70%;" value="<%=StringUtil.convertNull(order.getContact_phone()) %>" maxlength="30">
									<span class="fr">&nbsp;</span>
								</p>
								<p class="border-bottom">
									留言：<input type="text" id="remark" name="remark" style="width: 70%;" value="<%=StringUtil.convertNull(order.getRemark()) %>" maxlength="30">
									<span class="fr">&nbsp;</span>
								</p>
						</div>
					</div>
				</li>
				
				<%if(couponList.size()>0){ %>
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="border-bottom usr-name">
								当前订单可用优惠券：
							</p>
							<%-- <p class="usr-addr" style="text-align: center;"> 
								<a class="add-address" id="do_checkout" href="user/toUserAddressListPage.do?user_id=<%=order.getUser_id() %>&order_code=<%=order.getOrder_code()%>" onclick="toSetupAddress()">设置收货地址</a>
							</p> --%>
							
							<%for(SupplierCouponRecordBean coupon : couponList){ 
								if( order.getOrder_code().equals(coupon.getOrder_code())){  checkCouponId = coupon.getId(); }
							 %>
							<p class="border-bottom usr-name"  onclick="chooseRadio(this)">
								<input type="radio" name="couponId" value="<%=coupon.getId() %>" <%= checkCouponId == 0?"":"checked" %> > <%=coupon.getCoupon_name() %> （<%=coupon.getCoupon_remark()  %> ）
								
							</p>
							
							<%} %>
							
						</div>
					</div>
				</li>
				<%} %>
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="border-bottom usr-name">
								定单状态:<span class="fr"><%=order.getDictValueByField("status") %></span>
							</p>
							<p>
								商品金额:<span class="fr red" >￥ <span id="cost_price"><%=order.getCost_price()/100d %></span>&nbsp;元</span>
							</p>
							<p>
								积分抵扣(暂无积分):<span class="fr red" >￥ -<span id="points"><%=order.getCost_points() / 100d %></span>&nbsp;元</span>
							</p>
							<p class="border-bottom">
								优惠券:<span class="fr red">￥ -<span id="coupon_price"><%=order.getCoupon_total_price() / 100d %></span>&nbsp;元</span>
							</p>
							<p>
								待支付金额:<span class="fr red">￥ <span id="cost_money"><%=(order.getCost_money()) / 100d %></span>&nbsp;元</span>
							</p>
						</div>
					</div></li>
					
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="usr-addr" style="text-align: center;"> 
							<% if(order.getStatus() ==  ShopOrderInfoBean.STATUS_WAIT ||  order.getStatus() ==  ShopOrderInfoBean.STATUS_CANCEL ||  order.getStatus() ==  ShopOrderInfoBean.STATUS_ORDERING ){  %>
								<a class="add-address" style="width: 80%"  id="payButton" href="javascript:;" onclick="submitEff()"><%=StringUtil.isNotNull(order.getEff_date())?"修改预订":"提交预订"  %></a>
<!-- 								<a class="add-address" style="width: 42%;background: cadetblue;border-color: cadetblue;" id="cancelButton" href="javascript:;" onclick="cancelEff()">取消预订</a>
 -->							<%}else{ %>
								<a class="add-address" style="background-color: gray;border: gray;" href="javascript:;" >已完成预订</a>
							<%} %>
							</p>
						</div>
					</div></li>
				<!-- <li>
					<div class="order-box">
						<div class="order-width">

							<p class="border-bottom usr-name">
								发票信息<span class="fr"></span>
							</p>
							<p>发票类型: 不开发票</p>
							<p></p>
						</div>
					</div></li> -->
			</ul>

		</section>
	</div>

	<!-- 通用尾 div -->
	<div style="display:none;"></div>
	<input type="hidden" id="coupon_record_id" value="">
	<input type="hidden" id="order_code" value="<%=order.getOrder_code() %>" >
	<input type="hidden" id="openid" value="<%=user.getOpenid()%>">
	<input type="hidden" id="cost_balance" value="0">

<script type="text/javascript">
	var coupon = eval('<%=JsonUtil.convertObjectToJsonString(couponList) %>');
	var currentCoupon = findCouponById(<%=checkCouponId %>);
	var orderStatus = <%=order.getStatus()%>;
	function chooseRadio(dom){
		dom.children[0].checked = true;
		
		if(orderStatus != <%=ShopOrderInfoBean.STATUS_WAIT%>){
			return;
		}
		
		var couponid = dom.children[0].value ;
		$("#coupon_record_id").val(couponid);
		var coupon = findCouponById(couponid);
		if(coupon != currentCoupon)
		{
			calcPayPrice(coupon);
			currentCoupon = coupon;
		}
	}
	
	function findCouponById(couponid){
		for(var i = 0; i < coupon.length; i++){
			var record = coupon[i];
			if(record.id == couponid){
				return record;
			}
		}
	}
	
	//计算优惠信息
	function calcPayPrice(coupon){
		
		var coupon_offsetmoney;
		//还原为未选择优惠券时的数据
		if(currentCoupon != null ){
			$("#cost_price").html(<%=order.getCost_price()/100d %>);
			$("#cost_money").html(<%= (order.getCost_price() - order.getCost_points()) / 100d %>);
		}
		
		var cost_money = Number($("#cost_money").html());
		var cost_price = Number($("#cost_price").html());
		var points =  Number($("#points").html());
		
		//新选择的优惠券
		//代金券
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_CASH%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney)/100 ;
		}
		//折扣券
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_DISCOUNT%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney/100).toFixed(2) ;
			
			var discount = Number(coupon.coupon_discount) ;
			var new_price = cost_money * discount / 100;
			
			var value = Number(cost_money - new_price).toFixed(2) ;  // 折扣后的差值
			if(Number(value) > Number(coupon_offsetmoney) ){
				value = coupon_offsetmoney ;
			}else{
				
			}
			coupon_offsetmoney = value ;
			
		}
		//礼品券、 换购券
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_GIFT%> || coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_CHANGE%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney)/100 ; //礼品价值
			cost_money = cost_money  + coupon_offsetmoney;	// 订单总价增加礼品的价值
			cost_price = cost_price + coupon_offsetmoney;	// 商品总价也要相应的增加
			coupon_offsetmoney = coupon_offsetmoney;		// 礼品同等价值的优惠
		}
		//体验券、待补充
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_GIFT%> || coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_EXPERIENCE%>)
		{
			
		}
		$("#cost_price").html(cost_price );
		$("#cost_money").html((cost_money - coupon_offsetmoney).toFixed(2) );
		$("#coupon_price").html(coupon_offsetmoney );
	}
	function cancelEff(){
		var orderCode = '<%=order.getOrder_code()%>';
		var remark = $("#remark").val();
		if(confirm("确定取消该订单？")){
			$.ajax({
	            type : "POST",
	            //请求地址
	            url : "order/shop/cancelOrderEffDate.html",
	            //数据，json字符串
	            data : {orderCode : orderCode ,remark:remark },
	            //请求成功
	            success : function(result) {
	            	if(result == 'success')
	            	{
		                alert("预订单取消成功！");
	                }else{
	                	alert("预订单取消失败！" + result);
	                }
	            },
	            //请求失败，包含具体的错误信息
	            error : function(e){
	                alert("预订单取消失败！" + e.responseText);
	            }
	        });
		}
	}
	function submitEff(){
		var orderCode = '<%=order.getOrder_code()%>';
		
		var time = $("#eff_time").val();
		var day = $("#eff_day").val();
		var remark = $("#remark").val();
		var contact_name = $("#contact_name").val();
		var contact_phone = $("#contact_phone").val();
		if(time == ""){
			alert('请选择预约时间！');
			return false;
		}
		if(day == ""){
			alert('请选择预约日期！');
			return false;
		}
		var effDate = $("#eff_day").val()+" "+ time;
		if(effDate.length == 16){
			effDate = effDate + ":00";
		}
		var curTime = new Date().format("Y-m-d H:i:s");
		if(curTime > effDate){
			alert('预约时间小于当前时间，请重新选择！');
			return false;
		}
	
		$.ajax({
            type : "POST",
            //请求地址
            url : "order/shop/updateOrderEffDate.html",
            //数据，json字符串
            data : {orderCode : orderCode , effDate : effDate , remark : remark , contact_name:contact_name,contact_phone:contact_phone},
            //请求成功
            success : function(result) {
            	if(result == 'success')
            	{
	                alert("预订单提交成功！");
	                location.href = 'user/toUserShopOrderListPage.do?openid=<%=user.getOpenid()%>';
                }else{
                	alert("预订单提交失败！" + result);
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("预订单提交失败！" + e.responseText);
            }
        });

	}
	var oldDate = "<%=order.getEff_date()%>";
	
	if(oldDate.length >=13){
		//已有时间的
		var day = oldDate.substr(0, 10);
		var time = oldDate.substr(11,oldDate.length - 10);
		$("#eff_day").val(day);
		$("#eff_time").val(time);
	}else{
		// 没有时间的
		var dayStr = "";
		var day2 = new Date();
		var hour = day2.getHours();//得到小时
		if(hour <= 7 ){
			dayStr = day2.format("Y-m-d");
		}else{
			day2.setDate(day2.getDate() + 1);
			dayStr = day2.format("Y-m-d");
		}
		$("#eff_day").val(dayStr);
	}
	
</script>
</body>
</html>
