<%@page import="com.yd.business.supplier.bean.SupplierUserBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierBalanceLogBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
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
	
	ShopOrderInfoBean order = (ShopOrderInfoBean) request.getAttribute("order");
	SupplierBean supplier = (SupplierBean)request.getAttribute("supplier");
	int expressBottomPrice = (int) request.getAttribute("expressBottomPrice");
	SupplierUserBean user = (SupplierUserBean) request.getAttribute("user");
	List<SupplierCouponRecordBean> couponList = (List<SupplierCouponRecordBean>) request.getAttribute("couponList");
	List<? extends ShopOrderProductBean> productList = order.getProductList();
	Integer checkCouponId = 0; 
	
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<meta name="author" content="m.jd.com">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css" href="page/shop/order/css/base2013.css"
	charset="gbk">
<link rel="stylesheet" type="text/css" href="page/shop/order/css/order2014.src.css"
	charset="gbk">

<script type="text/javascript" src="js/jquery.js"></script>
<!-- <script type="text/javascript" src="js/spin.min.js"></script>
 -->
 <script type="text/javascript" src="js/common/cookieUtil.js"></script>
<!-- 对url处理 -->
<!-- <script type="text/javascript" src="js/ojbUrl.js"></script> -->

<!--数据埋点-->
<!-- <script type="text/javascript" src="js/pingJS.1.0.js"></script> -->

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
					<div class="jd-header-title" style="font-weight: bold">订单详情</div>
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

	<input type="hidden" id="sid" name="sid" value="<%=supplier.getId()%>">
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
								if(product.getType() == ShopOrderProductBean.TYPE_COUPON){
									couponStr = " （优惠券抵扣）";
								}
							%>
							<li class="border-bottom">
<%-- 								<a href="product/supplierProduct/toSupplierProductShopInfo.do?id=<%=product.getSupplier_product_id()%>&openid=<%=user.getOpenid()%>">
 --%>									<div class="order-msg">
										<img src="<%=product.getHead_img() %>" class="img_ware">
										<div class="order-msg">
											<p class="title"><%=product.getSupplier_product_name() %></p>
											<p class="price">
												单价：￥<%=product.getOriginal_price() /100d %> 元   &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;  数量：* <%=product.getNum() + couponStr %> <!-- <span></span> -->
											</p>
											<p class="order-data" style="font-size: 0.7rem">可叠加积分抵扣：<%=product.getCost_points() / 100d %>元</p>
										</div>
									</div>
		<!-- 						</a> -->
							</li>
							<%} %>
						</ul>
					</div></li>

				
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
								积分抵扣(您有<%=user.getPoints() /100d %>元积分):<span class="fr red" >￥ -<span id="points"><%=order.getCost_points() / 100d %></span>&nbsp;元</span>
							</p>
							<p class="border-bottom">
								优惠券:<span class="fr red">￥ -<span id="coupon_price"><%=order.getCoupon_total_price() / 100d %></span>&nbsp;元</span>
							</p>
							<p>
								支付金额:<span class="fr red">￥ <span id="cost_money"><%=(order.getCost_money()) / 100d %></span>&nbsp;元</span>
							</p>
						</div>
					</div></li>
				
					
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="usr-addr" style="text-align: center;"> 
							<% if(order.getStatus() ==  ShopOrderInfoBean.STATUS_WAIT ||  order.getStatus() ==  ShopOrderInfoBean.STATUS_CANCEL ){  %>
								<a class="add-address" id="payButton" href="javascript:;" onclick="pay()">立即支付</a>
							<%}else{ %>
								<a class="add-address" style="background-color: gray;border: gray;" href="javascript:;" >已完成支付</a>
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
	
	
	
function pay(){

	$("#payButton").html('加载中...');
	$("#payButton").on('click', '' );

	var order_code = $("#order_code").val();
	var sid = $("#sid").val();
	var cost_money = $("#cost_money").html();
	var cost_balance = $("#cost_balance").val();
	var openid = $("#openid").val();
	var phone = '';
	var points = $("#points").html();
	var coupon_record_id = $("#coupon_record_id").val();
	
	$.ajax({
		url : "wechat/createUnifiedOrderByShop.do",
		data : { cost_money : (cost_money - cost_balance).toFixed(2),
				 cost_balance : (cost_balance * 100).toFixed(0),
				 openid : openid,
				 order_code : order_code,
				 phone : phone ,
				 points : points,
				 coupon_record_id : coupon_record_id,
				 sid : sid ,
				 type : <%=SupplierBalanceLogBean.TYPE_USER_SHOPORDER_OFFLINE%>
		},
		success : function(result) {
			if(result == 'false'){
				alert('调用支付失败');
				$("#payButton").html('立即支付');
				$("#payButton").on('click','pay()');
			}else{
				result = eval('('+result+')');
				
				WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId" : result.appId,     //公众号名称，由商户传入     
			           "timeStamp": result.timeStamp,         //时间戳，自1970年以来的秒数     
			           "nonceStr" : result.nonceStr, //随机串     
			           "package" : result.packages,     
			           "signType" : result.signType,         //微信签名方式：     
			           "paySign" : result.paySign //微信签名 
			       },
			       function(res){
			           if(res.err_msg == "get_brand_wcpay_request:ok" ) {
			        	delCookie('productInfo'); 
			           	alert('支付成功！');
			           
			           	$("#payButton").hide();
//			           	location.href = "order/userOrderProduct.do?out_trade_code="+result.outTradeNo;
			         	location.href = "page/supplier/shop/paySuccess.jsp";			           	
			           }else{
			           	$.ajax({
							url : "wechat/deleteUnifiedOrderByShop.do",
							data : { outTradeNo : result.outTradeNo,
									 transactionId : result.transactionId,
									 openid : openid
									},
							success : function(d) {
								$("#payButton").html('立即支付');
								$("#payButton").show();
								$("#payButton").on('click','pay()');
							}
			           	});
			           }
			       }
			   ); 
			}
		}
	});
	
}
</script>
</body>
</html>
