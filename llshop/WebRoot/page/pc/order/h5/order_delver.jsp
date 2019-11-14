<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	String openid = (String)request.getAttribute("openid");
	List<ShopOrderInfoBean> orderList = (List<ShopOrderInfoBean>)request.getAttribute("list");
%>

<!DOCTYPE html>
<html>
<head >
	<base href="<%=basePath %>">
    <meta charset="utf-8">
    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
    <meta name="format-detection" content="telephone=no" />
    <title>坚果小店-代发货列表</title>
    <link rel="stylesheet" type="text/css" href="page/pc/order/h5/css/loaders.min.css"/>
    <link rel="stylesheet" type="text/css" href="page/pc/order/h5/css/loading.css"/>
    <link rel="stylesheet" type="text/css" href="page/pc/order/h5/css/base.css"/>
    <link rel="stylesheet" type="text/css" href="page/pc/order/h5/css/style.css"/>
	<script	src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
	<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	<script type="text/javascript" src="js/clipboard.min.js"></script>
    <script type="text/javascript">
    	$(window).load(function(){
    		$(".loading").addClass("loader-chanage")
    		$(".loading").fadeOut(300)
    	})
    </script>
</head>
<!--loading页开始-->
<div class="loading">
	<div class="loader">
        <div class="loader-inner pacman">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
          <div></div>
        </div>
	</div>
</div>
<!--loading页结束-->
<body style="margin: 0;">
	<header class="top-header fixed-header">
		<a class="icona" href="javascript:history.go(-1)">
				<img src="page/pc/order/h5/img/left.png"/>
			</a>
		<h3>待发货列表（<%=orderList.size() %>）</h3>
			<!-- <a class="iconb" href="shopcar.html">
			</a> -->
	</header>
	
	<div class="contaniner fixed-cont">
	
		<% for(ShopOrderInfoBean order : orderList) {%>
	
		<section class="go-order" >
			<!-- <h3>
				<img src="page/pc/order/h5/img/b-iocn01.png"/>
				<span>待发货</span>
			</h3> -->
			<div class="order-shop" id="<%=order.getOrder_code() %>"  title="true" >
				<ul style="padding: 0;">
					<li>
						<span>订单编号：</span>
						<small><%=order.getOrder_code() %></small>
					</li>
					<li>
						<span>创建时间：</span>
						<small><%=order.getCreate_time() %></small>
					</li>
					<li>
						<span>收货人姓名：</span>
						<small><%=order.getContact_name() %>  <button class="copy"  data-clipboard-text="<%=order.getContact_name() %>,<%=order.getContact_phone() %>,<%=order.getContact_address() %>" >复制收货信息</button></small>
					</li>
					<li>
						<span>联系方式：</span>
						<small><%=order.getContact_phone() %></small>
					</li>
					<li>
						<span>地址：</span>
						<small><%=order.getContact_address() %></small>
					</li>
					<li id="<%=order.getOrder_code() %>_productList">
						<!-- <span>碧根果150g</span>
						<small>*1</small>
						<br>
						<span>开心果250g</span>
						<small>*1</small>
						<br> -->
					</li>
					<li>
						<span>订单总额/实付金额</span>
						<small>￥<%=order.getCost_price().doubleValue()/100d %>/￥<%=(order.getCost_money()==null?0:order.getCost_money().doubleValue())/100d %></small>
						
						<p class="order-footer fixed-footer" style="position: relative;width: 100%;">
							<span style="margin-left: -15px;">请选择：</span> 
							<select id="express_mode" style="color: black;font-size: 1.34rem;" >
								<option value="圆通速递" selected="selected" >【圆通速递】</option>
								<option value="中通速递" >【中通速递】 </option>
								<option value="邮政包裹" > 【邮政包裹】  </option>
							</select>
							<input type="submit" value="扫码运单号发货" onclick="scanDeliver('<%=order.getOrder_code() %>');" />
						</p>
					</li>
				</ul>
			</div>
		</section>
		
		<%} %>
	</div>
	
<!-- 	<footer class="order-footer fixed-footer">
		
		<input type="button" value="查看物流" onclick="javascript:location.href='wuliu.html'" />
		
	</footer> -->
	
</body>
<script type="text/javascript">
var lastItem=0;
 displayProduct();
$(document).ready(function () { 
    window.addEventListener("scroll",function(e){
        displayProduct();
    });
});
function displayProduct(){
	var lis= $('.order-shop');
    //swHeight=滚动的高度+窗体的高度；当li的offset高度<=swHeight,那么说明当前li显示在可视区域了
    var swHeight=$(window).height();
    $.each(lis, function (index, item) {
        mTop=item.getBoundingClientRect().top;
        if(mTop<=swHeight){
            getOrderProductList(item);
        }
    });
}

function getOrderProductList(item){
    var flag = item.title;
    if(flag != 'false'){
    	var orderCode = item.id;
    	
    	$.ajax({
			url:"order/shop/queryShopOrderProductList.do",
			data:{ orderCode:orderCode },
			async:true,
			success:function(val){
				var result = eval(val);
				var html = '<span>订购商品清单：</span><br>';
				for(var i = 0 ; i < result.length; i++){
					var data = result[i];    
					html +='<span>'+ data.supplier_product_name +'</span><small>*'+ data.num +'</small><br>' ;
				}
				$('#'+orderCode+'_productList').html( html );
			}
		});
    	item.title = 'false';
    }
}

function scanDeliver(orderCode){
	//调用微信扫码
	var openid = '<%=openid %>';
	var express_mode = $('#express_mode').val();
	wx.scanQRCode({
	  needResult: 1, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
	  scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
	  success: function (res) {
	  	var strCode = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
	  	var strCount = strCode.split(',').length ;
	  	var result ;
	  	if(strCount <=1 ){
	  		result = strCode.split(',')[0];
	  	}else{
	  		result = strCode.split(',')[1];
	  	}
	  	
	    if(confirm('确定运单号为：'+express_mode+"的"+ result +"?")){
	    	$.ajax({
				url:"order/shop/updateShopOrderExpressByWechatUser.do",
				data:{openid : openid, order_code:orderCode , express_order_code: result ,express_mode : express_mode },
				async: false,
				success:function(val){
					if(val == 'true' || val == true){
						alert('运单号设置成功');
	    				$('#'+orderCode).hide();
					}else{
						alert('运单号设置失败:'+val);
					}
				}
			});
	    }
	  }
	})
	
}
 var clipboard = new ClipboardJS('.copy');
     // 显示用户反馈/捕获复制/剪切操作后选择的内容
     clipboard.on('success', function (e) {
         alert('姓名、电话、地址 复制成功！');
     })
     clipboard.on('error', function (e) {
         alert('复制失败');
     });
</script>
</html>