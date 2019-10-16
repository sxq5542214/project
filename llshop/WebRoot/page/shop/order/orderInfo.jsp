<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="org.json.JSONObject"%>
<%@page import="com.yd.util.JsonUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponRecordBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
<%@page import="com.yd.business.order.bean.ShopOrderProductBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	ShopOrderInfoBean order = (ShopOrderInfoBean) request.getAttribute("order");
	int expressBottomPrice = (int) request.getAttribute("expressBottomPrice");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	List<SupplierCouponRecordBean> couponList = (List<SupplierCouponRecordBean>) request.getAttribute("couponList");
	List<ShopOrderProductBean> productList = order.getProductList();
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
<script type="text/javascript" src="page/shop/order/js/orderInfo.js"></script>
<!-- <script type="text/javascript" src="js/spin.min.js"></script>
 -->
 <script type="text/javascript" src="js/common/cookieUtil.js"></script>
<!-- 对url处理 -->
<!-- <script type="text/javascript" src="js/ojbUrl.js"></script> -->

<!--数据埋点-->
<!-- <script type="text/javascript" src="js/pingJS.1.0.js"></script> -->

<!--通用头尾css js add by lizhenyou 2015-4-17-->
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

	<input type="hidden" id="orderId" name="orderId" value="9941788290">
	<input type="hidden" id="sid" name="sid" value="">
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
									couponStr = " （优惠卷抵扣）";
								}
							%>
							<li class="border-bottom"><a href="product/supplierProduct/toSupplierProductShopInfo.do?id=<%=product.getSupplier_product_id()%>">
									<div class="order-msg">
										<img src="<%=product.getHead_img() %>" class="img_ware">
										<div class="order-msg">
											<p class="title"><%=product.getSupplier_product_name() %></p>
											<p class="price">
												单价：￥<%=product.getOriginal_price() /100d %> &nbsp;&nbsp;元 <span></span>
											</p>
											<p class="order-data">X<%=product.getNum() + couponStr %></p>
										</div>
									</div> </a></li>
							<%} %>
						</ul>
					</div></li>

				<li>
					<div class="order-box">
						<div class="order-width">
							<% if(StringUtil.isNull(order.getContact_phone() )){ %>
								<p class="usr-addr" style="text-align: center;"> 
									<a class="add-address" id="do_checkout" href="user/toUserAddressListPage.do?user_id=<%=order.getUser_id() %>&order_code=<%=order.getOrder_code()%>" onclick="toSetupAddress()">设置收货地址</a>
								</p>
							<%}else{ %>
							<p class="border-bottom usr-name">
								收货人： <%=StringUtil.convertNull(order.getContact_name()) %> <span class="fr">号码： <span id="phone"><%=StringUtil.convertNull(order.getContact_phone()) %></span></span> 
							</p>
							<p class="usr-addr" >地址：<%=StringUtil.convertNull(order.getContact_address()) %> 
							
							<%if(StringUtil.isNull(order.getExpress_order_code())){ %>
							<span class="fr"><a href="user/toUserAddressListPage.do?user_id=<%=order.getUser_id() %>&order_code=<%=order.getOrder_code()%>">修改地址  </a> </span> </p>
							<%}} %>
						</div>
					</div>
				</li>
				
				<%if(couponList.size()>0){ %>
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="border-bottom usr-name">
								当前订单可用优惠卷：
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
							<p >
								运费(满<%=expressBottomPrice/100d %>元免运费):   <span class="fr red">￥ <%if(order.getExpress_price() != 0){ %>  <del> <span id="express_price"><%=order.getExpress_price()/100d %></span>&nbsp;元 </del> <%}else{ %> <span id="express_price"><%=order.getExpress_price()/100d %></span>&nbsp;元 <%} %> </span>
							</p>
							<p>
								积分抵扣:<span class="fr red" >￥ -<span id="points"><%=order.getCost_points() / 100d %></span>&nbsp;元</span>
							</p>
							<p class="border-bottom">
								优惠卷:<span class="fr red">￥ -<span id="coupon_price"><%=order.getCoupon_total_price() / 100d %></span>&nbsp;元</span>
							</p>
							<p>
								支付金额:<span class="fr red">￥ <span id="cost_money"><%=(order.getCost_money()) / 100d %></span>&nbsp;元</span>
							</p>
						</div>
					</div></li>
				
				<% if(order.getStatus() >= ShopOrderInfoBean.STATUS_PAYSUCCESS || order.getStatus() == ShopOrderInfoBean.STATUS_SUCCESS){ %>	
				<li>
					<div class="order-box">
						<div class="order-width">
							<p class="border-bottom usr-name">
								配送信息<span class="fr"></span>
							</p>
							<p>配送状态： <span class="fr"><%= order.getExpress_order_code() == null ? "待发货":"已发货" %></span></p>
							<p>配送方式： <span class="fr"><%= order.getExpress_mode() == null ? "待发货":order.getExpress_mode() %></span></p>
							<p>配送单号： <span class="fr"><%= order.getExpress_order_code() == null ? "待发货":order.getExpress_order_code() %></span></p>
							<p>发货日期： <span class="fr"><%= order.getExpress_date() == null ? "待发货":order.getExpress_date() %></span></p>
						</div>
					</div></li>
				<%}%>
					
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
	delCookie('productInfo');
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
		//还原为未选择优惠卷时的数据
		if(currentCoupon != null ){
			$("#cost_price").html(<%=order.getCost_price()/100d %>);
			$("#cost_money").html(<%= (order.getCost_price() - order.getCost_points()) / 100d %>);
		}
		
		var cost_money = Number($("#cost_money").html());
		var cost_price = Number($("#cost_price").html());
		var points =  Number($("#points").html());
		
		//新选择的优惠卷
		//代金卷
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_CASH%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney)/100 ;
		}
		//折扣卷
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_DISCOUNT%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney/100).toFixed(2) ;
			var discount = Number(coupon.coupon_discount) ;
			var new_price = cost_money * discount / 100;
			var value = Number(cost_money - new_price).toFixed(2) ;  // 折扣后的差值
			
			if(value > coupon_offsetmoney){
				value = coupon_offsetmoney ;
			}else{
				
			}
			coupon_offsetmoney = value ;
			
		}
		//礼品卷、 换购卷
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_GIFT%> || coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_CHANGE%>)
		{
			coupon_offsetmoney = Number(coupon.coupon_offsetmoney)/100 ; //礼品价值
			cost_money = cost_money  + coupon_offsetmoney;	// 订单总价增加礼品的价值
			cost_price = cost_price + coupon_offsetmoney;	// 商品总价也要相应的增加
			coupon_offsetmoney = coupon_offsetmoney;		// 礼品同等价值的优惠
		}
		//体验卷、待补充
		if(coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_GIFT%> || coupon.coupon_type == <%=SupplierCouponConfigBean.TYPE_EXPERIENCE%>)
		{
			
		}
		$("#cost_price").html(cost_price );
		$("#cost_money").html((cost_money - coupon_offsetmoney).toFixed(2) );
		$("#coupon_price").html(coupon_offsetmoney );
	}
	
</script>
</body>
</html>
<!--LHC-2015-09-21-->
