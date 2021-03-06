<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	List<ShopOrderInfoBean> orderList = (List<ShopOrderInfoBean>) request.getAttribute("orderList");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	
	
%>
<!DOCTYPE html>
<html><head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui" name="viewport">
        <meta name="apple-mobile-web-app-capable" content="no">
        <meta name="apple-mobile-web-app-status-bar-style" content="black">
        <meta name="format-detection" content="telephone=no">
        <meta name="application-name" content="">
        <title>订单列表</title>
        <meta name="description" content="">
        <meta name="keywords" content="">
        <meta name="author" content="">
        <meta name="version" content="">
        <meta http-equiv="Cache-Control" content="must-revalidate,no-cache"> 
        <link rel="stylesheet" href="page/shop/order/css/order.css">
        <script type="text/javascript" src="js/jquery.js"></script>
        <script type="text/javascript">
        	function showOrder(id){
        		
        		var as = $(".ol_select_bar ul a").removeClass("hover");
        		$("#a_"+id).addClass("hover");
        		
        		var ss = $(".content_order").hide();
        		$("#"+id+"_order").show();
        	}
        	
        	function deleteOrder(orderId){
        		if(confirm("确定删除此定单吗？")){
        			
        				$.ajax({
							url : "order/shop/deleteShopOrder.do",
							data : { order_id : orderId,
									 openid : '<%=user.getOpenid() %>'
									},
							success : function(d) {
								alert("已成功删除");
        						$(".order"+orderId).hide();
							}
			           	});
        		}
        	}
        	
        	
        	function remindOrder(orderId){
        		var remind = prompt("请填写催单说明，以便我们为您提供更好的服务");
        		if(remind != null && remind != '' && remind != 'null'){
	   				$.ajax({
						url : "order/shop/remindShopOrder.do",
						data : { order_id : orderId,
								 openid : '<%=user.getOpenid() %>',
								 remind : remind
								},
						success : function(d) {
	    					alert('已提交催单申请，我们将会尽快为您完成发货');
						}
		           	});
	           	}
        	}
        	
        </script>
</head>
    <body style="overflow: scroll;">        
       <div id="customerServiceDiv" style="display: none;">
		<div id="shadowDiv" onclick="showOrHideCustomerService()"
			style="width:100%;height:100%;position:fixed;left:0;top:0;z-index:2;background-color:#000;opacity:0.6;">
			
		</div>
		<div style="position: fixed;margin-top: 50%;text-align: center;z-index: 99;">
			<img width="80%" style="height: 100%;margin: 0 auto;"
				src="<%=basePath%>images/qrcode/qrcode_for_kefu.png" alt="">
    	<p style="height: auto;">
			<span style="font-size: 16px;color: white;">温馨提示：<br>
				请长按二维码添加客服微信号，或直接搜索【yoyoyo1105】添加！
			</span>
		</p>
		</div>
	</div>
	
	
        <label class="list_order" id="info_f304ba748b4cc1faa1cdc419651b5c17_0_0__1_0"></label>

        <div class="viewport">  
            <!--页头-->
            <header class="navbar"> 

                <div class="nav_main">
					<a class="prd_order trans_arrow">订单列表</a>
                    <a class="order_c_back" href="javascript:history.go(-1);">
                    	<img src="page/shop/order/images/c_back_btn.png">
                    </a>

                </div> 
            </header>
<input type="hidden" id="user_id" value="<%=user.getId()%>">
<!--筛选导航栏-->
<nav class="ol_select_bar">
    <ul>
        <a href="javascript:;" class="hover" onclick="showOrder('all')" id="a_all"><li>全部</li></a>
        <a href="javascript:;" class="" onclick="showOrder('wait_pay')" id="a_wait_pay"><li>待付款</li></a>
        <a href="javascript:;" class="" onclick="showOrder('pay_success')" id="a_pay_success"><li>待收货</li></a>
    </ul>
</nav>
<!--订单列表-->
<section class="content_order" id="all_order">
<div class="warnning">
	<!-- <span class="title">重要提醒：</span> -->
	<span class="word">我们不会以<i>订单异常、系统升级</i>为由，要求您点击任何链接进行退款！郑重提醒您：提高警惕，谨防受骗！</span>
</div>

<% for(ShopOrderInfoBean order : orderList) { %>
<div class="block order<%=order.getId() %>" >
    <div class="order_list">
        <!--非商城自营显示店铺入口-->
    <%--         <div class="shop_title">
                <!--合并支付选项-->
                                                <!--店铺名称-->
                <div class="fl"> <%=BaseContext.getMchName(null)%> </div>
            </div> --%>
                <!--分包商品信息-->
        <div class="cart_item prd_ebook" id="<%=order.getId() %>"><a href="user/supplier/toSupplierShopUserEffOrderPage.html?openid=<%=user.getOpenid() %>&order_code=<%=order.getOrder_code()%>">
                <!--电子书加签-->
                                <!--包裹图片-->
                <img src="<%=order.getOrder_img() %>" class="fl pro_pic">
                <!--包裹详情-->
                <div class="detail" style="padding: 1.4rem 0 3.7rem 0">
                    <!--包裹状态-->
                    <div class="fr prd_state">
                        <!--状态文字-->
                        <div class="prd_state_title" id="" style="font-size: 1rem;">
                            <%=order.getDictValueByField("status") %> </div>
                    </div>
                <!--包裹名称显示，多件产品，显示包裹编号，一件产品显示产品名称-->
                                <p >
                                   <%=order.getOrder_name() %>           </p>
                                   <p>联系人：<%=StringUtil.convertNull(order.getContact_name()) %> &nbsp;&nbsp;联系方式：<%=StringUtil.convertNull(order.getContact_phone()) %>  </p>
                                   <p>预约时间：<%=StringUtil.convertNull(order.getExpress_date()) %></p> 
                                   <p>下单时间：<%=order.getCreate_time() %></p> 
            </div>
	    </a> 
	    <!--数量价格信息-->
	    <div class="detail2">
	    	<span style="color:black; ">总价：<%= (order.getCost_price() ) /100d %>元 &nbsp;</span>
			<span style="color: #ff463c;">优惠抵扣：<%=NumberUtil.addtion(order.getCost_points(),order.getCoupon_total_price())   /100d %>元&nbsp;</span>
	        <span class="order_price" style="font-size: 1.6rem;">共计：￥<%=order.getCost_money() /100d %>元</span>
	    </div>
	    <!--操作按键-->
	        <div class="detail3">
	        	<% if(order.getStatus() == ShopOrderInfoBean.STATUS_PAYSUCCESS){ %>
	        	<a href="javascript:;" onclick="remindOrder(<%=order.getId()%>);">催促订单</a>
	        	<%}else if(order.getStatus() == ShopOrderInfoBean.STATUS_ALREADY_DELIVERY) { %>
	        	
	        	
	        	<a href="https://m.kuaidi100.com/app/query/?com==<%=order.getExpress_mode() %>&nu=<%=order.getExpress_order_code() %>" >查看物流</a>
	        	<a href="javascript:;" onclick="showOrHideCustomerService()" >联系客服</a>
	        	
	        	<%}else{ %>
	        	
	        	<%} %>
	        	<a href="javascript:;" onclick="deleteOrder(<%=order.getId()%>);">删除订单</a>
	        </div>
	    </div>
    </div>
</div>
<%} %>
 </section>   
 
 <section class="content_order" id="wait_pay_order" style="display: none;">
<div class="warnning">
	<!-- <span class="title">重要提醒：</span> -->
	<span class="word">我们不会以<i>订单异常、系统升级</i>为由，要求您点击任何链接进行退款！郑重提醒您：提高警惕，谨防受骗！</span>
</div>

<% for(ShopOrderInfoBean order : orderList) if(order.getStatus() == ShopOrderInfoBean.STATUS_WAIT) { %>
<div class="block order<%=order.getId() %>" >
    <div class="order_list">
        <!--非商城自营显示店铺入口-->
    <%--         <div class="shop_title">
                <!--合并支付选项-->
                                                <!--店铺名称-->
                <div class="fl"> <%=BaseContext.getMchName(null)%> </div>
            </div> --%>
                <!--分包商品信息-->
        <div class="cart_item prd_ebook" id="<%=order.getId() %>"><a href="user/supplier/toSupplierShopUserEffOrderPage.html?openid=<%=user.getOpenid() %>&order_code=<%=order.getOrder_code()%>">
                <!--电子书加签-->
                                <!--包裹图片-->
                <img src="<%=order.getOrder_img() %>" class="fl pro_pic">
                <!--包裹详情-->
                <div class="detail" style="padding: 1.4rem 0 3.7rem 0">
                    <!--包裹状态-->
                    <div class="fr prd_state">
                        <!--状态文字-->
                        <div class="prd_state_title" id="" style="font-size: 1rem;">
                            <%=order.getDictValueByField("status") %> </div>
                    </div>
                <!--包裹名称显示，多件产品，显示包裹编号，一件产品显示产品名称-->
                                <p >
                                   <%=order.getOrder_name() %>           </p>
                                   <p>联系人：<%=StringUtil.convertNull(order.getContact_name()) %> &nbsp;&nbsp;联系方式：<%=StringUtil.convertNull(order.getContact_phone()) %>  </p>
                                   <p>预约时间：<%=StringUtil.convertNull(order.getExpress_date()) %></p> 
                                   <p>下单时间：<%=order.getCreate_time() %></p> 
            </div>
	    </a> 
	    <!--数量价格信息-->
	    <div class="detail2">
	    	<span style="color:black; ">总价：<%= (order.getCost_price() ) /100d %>元 &nbsp;</span>
			<span style="color: #ff463c;">优惠抵扣：<%=NumberUtil.addtion(order.getCost_points(),order.getCoupon_total_price())   /100d %>元&nbsp;</span>
	        <span class="order_price" style="font-size: 1.6rem;">共计：￥<%=order.getCost_money() /100d %>元</span>
	    </div>
	    <!--操作按键-->
	        <div class="detail3">
	        	<% if(order.getStatus() == ShopOrderInfoBean.STATUS_PAYSUCCESS){ %>
	        	<a href="javascript:;" onclick="remindOrder(<%=order.getId()%>);">催促订单</a>
	        	<%}else{ %>
	        	<%} %>
	        	<a href="javascript:;" onclick="deleteOrder(<%=order.getId()%>);">删除订单</a>
	        </div>
	    </div>
    </div>
</div>
<%} %>
 </section>   
 
 
 <section class="content_order" id="pay_success_order" style="display: none;">
<div class="warnning">
	<!-- <span class="title">重要提醒：</span> -->
	<span class="word">我们不会以<i>订单异常、系统升级</i>为由，要求您点击任何链接进行退款！郑重提醒您：提高警惕，谨防受骗！</span>
</div>

<% for(ShopOrderInfoBean order : orderList)if(order.getStatus() >= ShopOrderInfoBean.STATUS_PAYSUCCESS) { %>
<div class="block order<%=order.getId() %>" >
    <div class="order_list">
        <!--非商城自营显示店铺入口-->
    <%--         <div class="shop_title">
                <!--合并支付选项-->
                                                <!--店铺名称-->
                <div class="fl"> <%=BaseContext.getMchName(null)%> </div>
            </div> --%>
                <!--分包商品信息-->
        <div class="cart_item prd_ebook" id="<%=order.getId() %>"><a href="user/supplier/toSupplierShopUserEffOrderPage.html?openid=<%=user.getOpenid() %>&order_code=<%=order.getOrder_code()%>">
                <!--电子书加签-->
                                <!--包裹图片-->
                <img src="<%=order.getOrder_img() %>" class="fl pro_pic">
                <!--包裹详情-->
                <div class="detail" style="padding: 1.4rem 0 3.7rem 0">
                    <!--包裹状态-->
                    <div class="fr prd_state">
                        <!--状态文字-->
                        <div class="prd_state_title" id="" style="font-size: 1rem;">
                            <%=order.getDictValueByField("status") %> </div>
                    </div>
                <!--包裹名称显示，多件产品，显示包裹编号，一件产品显示产品名称-->
                                <p ><%=order.getOrder_name() %></p>
                                   <p>联系人：<%=StringUtil.convertNull(order.getContact_name()) %> &nbsp;&nbsp;联系方式：<%=StringUtil.convertNull(order.getContact_phone()) %>  </p>
                                   <p>预约时间：<%=StringUtil.convertNull(order.getExpress_date()) %></p> 
                                   <p>下单时间：<%=order.getCreate_time() %></p> 
            </div>
	    </a> 
	    <!--数量价格信息-->
	    <div class="detail2">
	   		<span style="color:black; ">总价：<%= ( order.getCost_price() ) /100d %>元 &nbsp;</span>
			<span style="color: #ff463c;">优惠抵扣：<%=NumberUtil.addtion(order.getCost_points(),order.getCoupon_total_price())   /100d %>元&nbsp;</span>
	        <span class="order_price" style="font-size: 1.6rem;">共计：￥<%=order.getCost_money() /100d %>元</span>
	    </div>
	    <!--操作按键-->
	        <div class="detail3">
	        	<% if(order.getStatus() == ShopOrderInfoBean.STATUS_PAYSUCCESS){ %>
	        	<a href="javascript:;" onclick="remindOrder(<%=order.getId()%>);">催促订单</a>
	        	<%}else if(order.getStatus() == ShopOrderInfoBean.STATUS_ALREADY_DELIVERY) { %>
	        	
	        	
	        	<a href="https://m.kuaidi100.com/app/query/?com==<%=order.getExpress_mode() %>&nu=<%=order.getExpress_order_code() %>" >查看物流</a>
	        	<a href="javascript:;" onclick="showOrHideCustomerService()" >联系客服</a>
	        	
	        	
	        	<%}else{ %>
	        	
	        	<%} %>
	        	<a href="javascript:;" onclick="deleteOrder(<%=order.getId()%>);">删除订单</a>
	        </div>
	    </div>
    </div>
</div>
<%} %>
 </section>   
 
 
<!--合并支付-->
<!--订单切换-->
<div class="o_select">
    <ul>
                <li id="prd_ck">商品订单<span class="fr prd_check"></span></li>        
        <a href=""><li>手机充值订单</li></a>
            </ul>
</div>

<!--工具浮层-->
<div class="toolbar">
    <p class="order_search"><img src="page/shop/order/images/order_search_btn.png">搜索</p>
    <p class="order_filter"><img src="page/shop/order/images/filter.png">筛选</p>
</div>

<!--搜索浮层-->
<div class="searchbar">
    <form action="" method="post" onsubmit="return check_search(this)">
        <p><input id="search_text" name="search_txt" type="text" placeholder="在此输入您想要搜索的关键字" required="required"><a class="del_search"></a></p>
        <input type="submit" id="go_search" value="搜索">
    </form>
</div>

<!--筛选浮层-->
<div class="ol_filter">    
    <div class="fil_title2">订单时间<a class="close_fil fr"><img src="page/shop/order/images/c_cancle.png"></a></div>
    <div class="fil_content fil1">
        <div>
            <span id="time_1">三十天以内</span>
            <span id="time_2">3个月以内</span>
            <span id="time_3">2015年</span>
        </div>
        <div>
            <span id="time_4">2014年</span>
            <span id="time_5">更早的订单</span>
        </div>
    </div>
    <div class="fil_title2">订单状态</div>
    <div class="fil_content fil2">
        <div>
            <span id="stat_0">全部状态</span>
            <span id="stat_1">待付款</span>
            <span id="stat_5">待确认收货</span>
        </div>
        <div>
            <span id="stat_6">待评价</span>
            <span id="stat_2">交易进行中</span>
            <span id="stat_3">交易成功</span>
        </div>
        <div>
            <span id="stat_4">交易失败</span>
        </div>
    </div>
    <div class="fil_content2">
        <form action="" method="post">
            <input id="filter_time" name="filter_time" type="hidden" value="1">
            <input id="filter_stat" name="filter_stat" type="hidden" value="0">
            <input type="submit" class="go_filter" value="应用">
        </form>        
    </div>
</div>

<!--弹窗背景-->
<section class="f_mask" id="pop_mask"></section>
<section class="f_mask" id="head_mask"></section>
<section class="f_mask" id="search_mask"></section>

<!--提示弹窗-->
<div class="m_tips">
    <div class="m_content">
        <p>温馨提示</p>
        <span><span>
    </span></span></div>
    <div class="m_btns">
        <a class="m_no">返回</a><a class="m_ok">确定</a>
    </div>
</div>

<!--回到顶部-->
<div class="fixed_box" style="display: none;">
    <a href="javascript:;" class="btn_back" id="backtop"><img src="page/shop/order/images/search_icon.png"></a>
</div>

<!--发票弹窗-->
<div class="ol_invoice">
    <div class="invoice_title">
        修改发票信息
        <a class="close_invoice fl"><img src="page/shop/order/images/c_cancle.png"></a>
        <a class="save_invoice fr" id="si__f304ba748b4cc1faa1cdc419651b5c17">保存</a>
    </div>
    <div class="invoice_content">
        <input id="invoice_title" type="text" value="" autocomplete="off"><a class="del_input_inv"></a>  
        <input id="inv_con" name="inv_con" type="hidden" value="">
        <p><img src="page/shop/order/images/c_checkbox_on.png"></p>
    </div>

</div>

<!--收货人弹窗-->
<div class="ol_rec">
    <div class="rec_title">
        修改收货人信息
        <a class="close_rec fl"><img src="page/shop/order/images/c_cancle.png"></a>
        <a class="save_rec fr" id="sr__f304ba748b4cc1faa1cdc419651b5c17">保存</a>
    </div>
    <div class="rec_content">
        <input id="rec_name" type="text" value="" autocomplete="off">
        <input type="text" class="no_edit" value=",,,," readonly="true">
        <input id="rec_adr" type="text" value="" autocomplete="off">
        <input type="text" class="no_edit" value="" readonly="true">
        <input id="rec_mob" type="text" value="" autocomplete="off">
        <input id="rec_tel" type="text" value="" autocomplete="off"> 
        <p>手机和固定电话，选择其中一项填写即可</p>
    </div>
</div>
<span class="simple_block"></span>


<!--页尾-->
<label class="has_topay" id="nopay"></label>
<footer>
</footer>
</div>

  <script type="text/javascript">
  	function showOrHideCustomerService(){
  	
  		var str = document.getElementById("customerServiceDiv").style.display;
  		if(str == 'block'){
  			document.getElementById("customerServiceDiv").style.display = 'none';
  		}else{
  			document.getElementById("customerServiceDiv").style.display = 'block';
  		}
  	}
  </script>


<!-- <script src="js/zepto.min.js"></script>
<script src="js/fx.js" type="text/javascript"></script>
<script src="js/event.js" type="text/javascript"></script>
<script src="js/fx_methods.js" type="text/javascript"></script>
<script src="js/order.js" type="text/javascript"></script>
<script src="js/js_tracker.js"></script> -->
        <div class="gwd_toolbar_control_small" gwd-subject="open" title="无比价" style="background-image:url()">

        </div>


    </body>
</html><!--LHC-2015-10-06-->
