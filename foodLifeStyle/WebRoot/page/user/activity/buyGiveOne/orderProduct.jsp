<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.supplier.bean.CustomerSupplierProductBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityInstanceBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
Object customer_id = request.getAttribute("customer_id");
Object openid = request.getAttribute("openid");
Object order_account = request.getAttribute("order_account");
Object activityConfigBean = request.getAttribute("activityConfigBean");
Object instanceBean = request.getAttribute("instanceBean");
if(order_account==null){
	order_account="";
}
String img_url = "images/user/user_order_top.png";
String img_jump_url = "wechat/user/toInviteFriendsShare.do?openid="+openid;
if(activityConfigBean != null){
	img_url = ((ActivityConfigBean)activityConfigBean).getDescription_img();
	if(((ActivityConfigBean)activityConfigBean).getActivity_img_jump_url() != null){
	img_jump_url = ((ActivityConfigBean)activityConfigBean).getActivity_img_jump_url();
		Map<String,String> map = new HashMap<String,String>();
		map.put("openid", openid.toString());
		map.put("activityConfigBeanCode", ((ActivityConfigBean)activityConfigBean).getCode());
		map.put("instanceActivityId", ((ActivityInstanceBean)instanceBean).getId().toString());
		img_jump_url = StringUtil.dealHttpUrl(img_jump_url, map);
	}
}
%>
<html ng-app="ecssAppModule" class="ng-scope">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<style type="text/css">
@charset "UTF-8"; 

[ng\:cloak],[ng-cloak],[data-ng-cloak],[x-ng-cloak],.ng-cloak,.x-ng-cloak,.ng-hide
	{
	display: none !important;
}

ng\:form {
	display: block;
}

.ng-animate-block-transitions {
	transition: 0s all !important;
	-webkit-transition: 0s all !important;
}

.ng-hide-add-active,.ng-hide-remove {
	display: block !important;
}
</style>
<title>特价流量</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="max-age=300">
<meta
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0, maximum-scale=1.0, user-scalable=0"
	name="viewport">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="format-detection" content="telephone=no">
<link rel="stylesheet" type="text/css"
	href="css/supplier/stylesd3.css">
<link rel="stylesheet" type="text/css"
	href="css/supplier/reset.css">
<link rel="stylesheet" type="text/css"
	href="css/supplier/stylepop.css" />
	
	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/user/activity/buyGiveOne/orderProduct.js"></script>
</head>
<script type="text/javascript">
$(function (){
   var order_account =  $("#order_account").val();
      var order_account =  $("#order_account").val();
   if(order_account!=null && order_account!=""){
   checkPhone();
   }else {
   defaultPhone();
   }
 });
</script>
<body  class="ng-scope">
	<div class="tfrom"  >
					<div class="tfrom-name">请选择记录号码<span><img src="images/supplier/login_close.png" class="tfrom-picture" onclick="closePop()"></span></div>
   					<div class="tfrom-con">
    				<ul id="cognzhagnhul"></ul>
    				</div>
 	</div>
 	<div class="tfrom-mask"></div>
	<div>
		<!-- ngView: undefined -->
		<ng-view class="ng-scope">
		<div  class="ng-scope">
			<div class="pay">
				<div class="payable">
					<label   class="ng-scope"><label>需支付:</label><label
						id="discount_price"  class="ng-binding" style="color:red;">0</label>元</label>
					<span class="ng-binding">原价<label id="text_price" style="font-size: 20px;">0</label>元</span>
				</div>
				<div
					onclick="payInfo()" class="recharge">立即抢购</div>
			</div>
			<!-- <div class="pay">
				<div class="payable">
					<label  class="ng-scope"><label>库存</label>:<label
						id="text_store_num" class="ng-binding">0</label>个</label>
					<span class="ng-binding"><label id="discount_price">0.0</label></span>元
				</div>
				<div
					onclick="payInfo()" class="recharge">立即订购</div>
			</div>  -->

			<div class="width320">
				<div class="top">
				<a href=<%=img_jump_url %>>
					<img src="<%=img_url %>">
						</a>
				</div>

				<div class="con" style="height: 40px;">
					<div class="srm">
						<div class="clearDiv">
							<input class="srhm ng-pristine ng-valid" type="number" onkeyup="checkPhone();"
								maxlength="11" name="phone" style="font-size:16px;" id="phone"  placeholder="购买特价流量请在这里输入手机号码" value="<%=order_account%>">
							<!-- ngIf: focusOnPhoneNum() -->
						</div>
						<div class="yys">
							<span class="ng-binding"><label  onclick="historyPhone()">历史号码</label></span>
						</div>
					</div>
					<label class="bderror">
					</label>
				</div>

				<!-- ngIf: isTelNoChanged -->
				<div style="background-color:#FFFFFF;margin-top:2" > 
					 <div style="width: 100%;margin: 0 auto;text-align: center;color:red;"  >
					 <span >特价流量不参与免单活动哦！</span>
					 </div>
				<div class="main" id="main">
					<div class="box ng-scope" style="font-size:14px; padding:5 10 5 10px;" >
						<span	>特价流量不参与免单红包活动</span>
						<!-- <div class="Block">10M流量</div>
						<div class="Block">30M流量</div>
						<div class="Block">100M流量</div>
						<div class="Block">300M流量</div>
						<div class="clear"></div>
						<div class="Block">500M流量</div>
						<div class="Block">1G流量</div>
						<div class="Block">3G流量</div>
						<div class="Block">5G流量</div> 
						
						<span >限时特价：</span><br>
						<span style="color: red;">1.特惠20元500M、35元1G、50元2G限时抢购！</span><br>
						<span	>2.特价流量不参与免单红包活动</span><br>
						<span	>3.活动仅限安徽移动、安徽电信用户</span><br>
						<span style="font-size: 14;">4.机会难得，现在订购可在9月、10月和11月生效使用</span>	-->		
						<div class="clear"></div>
						
						
					</div>				
				<%-- 	<%
						for( CustomerSupplierProductBean csp : list ){
					%>
					<!-- 
					<label class="bderror" style="text-align: center;color: black;">  </label> -->
					<div class="box ng-scope" >
						<%
							for(int i = 1 ; i <= csp.getListSupplierBean().size() ; i++){
											SupplierProductBean sp = csp.getListSupplierBean().get(i-1);
						%>
							
							<div class="Block" id="<%=sp.getId()%>" title="0" discount="0" onclick="choseProduct(<%=sp.getId()%>)" ><%=sp.getProduct_name() %></div>
							
							<%if(i == csp.getListSupplierBean().size()){%>
								<div class="clear"></div>
						<%}} %>
						
					</div>
					<%} %> --%>
				</div>
 
				<div class="menu">
					<div  class="ng-scope">
						<%-- <div class="downmenu">
							<h2  style="color: black;"
								class="ng-scope ng-binding">当前积分<span class="ng-binding" id="user_points" style="color: red;"><%=user.getPoints()/100d %></span>元 <span class="orderMinusTip ng-binding" id="points"></span></h2>
							<div class="jt"  >
							</div>
						</div>
						<div class="downmenu">
							<h2 class="ng-binding"><span class="orderMinusTip ng-binding" id="givePoints"><font style="color:black;">成功购买后再返5%积分&nbsp;</font></span></h2>
							<div class="jt"  >
							</div>
						</div> --%>
						<div class="downmenu">
							<h2 
								class="ng-binding">温馨提示</h2>
							<div class="jt" >
								<img onclick="changeTips()"
									class="ng-scope" id="tips_img"
									src="images/icon/upjt.png">
							</div>
						</div>
						<div class="nr ng-scope ng-binding" id="tips" >
							<div>
								1、 支持安徽电信、安徽移动、支持2G/3G/4G网络，当月有效；<br>
								2、 170号段、黑名单、欠费、停机、未实名登记以及单月有套餐变更行为的用户号码无法进行流量充值；<br>
								3、 客服微信：LLyun9922 在线时间为8:30-20:30；<br>
							</div>
						</div>
						<div class="clear ng-scope" ></div>
					</div>
					<div class="clear"></div>
				</div>
				<div class="clear"></div>
			</div>
		</div>

		</ng-view>
	</div>
	
<input type="hidden" id="product_id" name="product_id">
<input type="hidden" id="customer_id" name="customer_id" value="<%=customer_id %>">
<input type="hidden" id="product_price" name="product_price" value="0">
<input type="hidden" id="store_num" name="store_num" value="0">
<input type="hidden" id="openid" value="<%=openid%>">
<input type="hidden" id="order_account" value="<%=order_account%>">
<input type="hidden" id="user_points"  value="<%=user.getPoints()/100d %>">
</body>
<script type="text/javascript">
//changeTips();
</script>
</html>