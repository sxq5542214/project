<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserWechatBean user = (UserWechatBean)request.getAttribute("user");
List<SupplierEventCodeBean> list = (List<SupplierEventCodeBean>)request.getAttribute("list");
Integer supplierEventId = (Integer)request.getAttribute("supplierEventId");
UserQrCodeBean qrCode = (UserQrCodeBean)request.getAttribute("qrCode");

SupplierCouponConfigBean[] coupon = new SupplierCouponConfigBean[]{new SupplierCouponConfigBean(),new SupplierCouponConfigBean(),new SupplierCouponConfigBean(),new SupplierCouponConfigBean()};
coupon[0].setId(1);
coupon[0].setCoupon_name("盐焗腰果125g");
coupon[0].setCoupon_count(7); //复用字段，代表参与人数要求
coupon[1].setId(114);
coupon[1].setCoupon_name("杏仁250g");
coupon[1].setCoupon_count(15); //复用字段，代表参与人数要求
coupon[2].setId(3);
coupon[2].setCoupon_name("开心果250g");
coupon[2].setCoupon_count(20); //复用字段，代表参与人数要求
coupon[3].setId(4);
coupon[3].setCoupon_name("山核桃仁250g");
coupon[3].setCoupon_count(40); //复用字段，代表参与人数要求
								
%>
<html class="plat-pc not-native">
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,user-scalable=0,viewport-fit=cover">
<meta name="description"
	content="免费坚果等你来拿！ 坚果小店，好吃不贵，健康自然，为您提供优质服务">
<meta name="keyword" content="坚果,核桃,山核桃,核桃仁,开心果,碧根果">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="apple-touch-fullscreen" content="yes">
<meta name="format-detection" content="telephone=no,email=no,adress=no">
<meta name="lx:category" content="airbnb">
<meta name="lx:defaultAppnm" content="phoenix_i">
<meta name="lx:autopv" content="off">
<title>邀友免费拿坚果</title>
<style type="text/css">
.get-prize{
	margin:10px;
	display: inline-block;
	width: 40%;
	height:172px;
	background-image: url('images/user/supplierProductShop/coupon/juan.png');
	background-size:100% 100%;
}
</style>
<link
	href="page/user/activity/freeCutActivity/resource/app.a54e9c98.css"
	rel="stylesheet" >
<link rel="stylesheet" type="text/css"
	href="page/user/activity/freeCutActivity/resource/chunk-74a5a1da.161130a8.css">
<link rel="stylesheet" type="text/css"
	href="page/user/activity/freeCutActivity/resource/chunk-6cddd8cf.f001de91.css">
<link rel="stylesheet" type="text/css"
	href="page/user/activity/freeCutActivity/resource/freeCutActivity.css">
	<script
		src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
</head>
<body>
	<main data-v-49b8e837="" id="app">
		<div data-v-49b8e837="">
			<div data-v-49b8e837="" class="initiator">
				<img data-v-163c7539="" data-v-49b8e837=""
					src="<%=user.getHead_img() %>"
					alt="头像" class="avatar">
				<p data-v-49b8e837="">ice_river</p>
				<p data-v-49b8e837="">“我想要的坚果，就差你这一下助力啦！”</p>
			</div>
			<div data-v-fbc5d9ba="" data-v-49b8e837="" class="order-card card">
				<div data-v-fbc5d9ba="" class="timer">
					<div data-v-fbc5d9ba="" class="timer-title">好吃的坚果免费送啦！</div>
				<!-- 	<div data-v-8b9121ba="" data-v-fbc5d9ba="" class="timer">
						<i data-v-8b9121ba="">04</i><span data-v-8b9121ba="" class="c">:</span><i
							data-v-8b9121ba="">35</i><span data-v-8b9121ba="" class="c">:</span><i
							data-v-8b9121ba="">37</i>
					</div> -->
				</div>
				<div data-v-fbc5d9ba="" class="order-main">
					<img data-v-163c7539="" data-v-fbc5d9ba=""
						src="images/user/supplierProductShop/coupon/lipingjuan.png"
						alt="礼品图片" class="order-cover">
					<div data-v-fbc5d9ba="" class="order-content">
						<p data-v-fbc5d9ba="" class="order-title">【坚果赠送卷】<br>腰果、核桃、开心果<br>杏仁、山核桃仁等免费送啦！
							</p>
						<p data-v-fbc5d9ba="" class="order-price">
							价值可达 <small data-v-fbc5d9ba="" class="mtfin">￥</small><b
								data-v-fbc5d9ba="" class="mtfin">60</b>
						</p>
						<!---->
						<!---->
					</div>
				</div>
			</div>
			<div data-v-49b8e837="">
				<div data-v-165a82e2="" data-v-49b8e837="">
					<div data-v-165a82e2="" class="progress-card card">
						<div data-v-1833cdda="" data-v-165a82e2="" class="text-progress">
							<%
								
								String str = "";
								int level = 0;
								int size = list.size() ;
								int needNum = 10 - size;
								String prize = "杏仁150g";
								int persent = size *100 / 40;
								if(size < coupon[0].getCoupon_count()){
									level = 1;
									needNum = coupon[0].getCoupon_count() - size;
									prize = coupon[0].getCoupon_name();
									persent = 5;
								}else if(size < coupon[1].getCoupon_count()){
									level = 2;
									needNum = coupon[1].getCoupon_count() - size;
									prize = coupon[1].getCoupon_name();
								}else if(size < coupon[2].getCoupon_count()){
									needNum = coupon[2].getCoupon_count() - size;
									prize = coupon[2].getCoupon_name();
									persent = persent +8;
									level = 3;
								}else if(size < coupon[3].getCoupon_count()){
									needNum = coupon[3].getCoupon_count() - size;
									prize = coupon[3].getCoupon_name();
									persent = persent + 15;
									level =4;
								}
								String needStr = "已有"+size+"位好友助力，快来帮帮忙！";
								
								if(size >= 40){
									needStr = "恭喜TA已经拿走大奖啦！";
									level =4;
								}
								
							 %>
						
							<p data-v-1833cdda=""><%=needStr %></p>
						</div>
						<div data-v-b3bd69c2="" data-v-165a82e2=""
							class="progress-bar-wrapper">
							<div data-v-b3bd69c2="" class="progress-bar">
								<div data-v-b3bd69c2="" class="progress-bar-inner"
									style="background: linear-gradient(60deg, rgb(255, 114, 58) 0px, rgb(255, 114, 58) <%=persent%>%, rgb(255, 230, 170) <%=persent%>%, rgb(255, 230, 170) 100%), linear-gradient(90deg, rgb(248, 180, 134), rgb(255, 114, 58));"></div>
							</div>
							<ul data-v-b3bd69c2="" class="progress-list">
								<li data-v-b3bd69c2=""><img style="width: .507rem; height: .627rem;"
									src="page/user/activity/freeCutActivity/resource/redbag1.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=coupon[0].getCoupon_count() %>个助力</p>
									<p data-v-b3bd69c2=""><%=coupon[0].getCoupon_name() %></p>
									
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag1.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=coupon[1].getCoupon_count() %>个助力</p>
									<p data-v-b3bd69c2=""><%=coupon[1].getCoupon_name() %></p>
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag2.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=coupon[2].getCoupon_count() %>个助力</p>
									<p data-v-b3bd69c2=""><%=coupon[2].getCoupon_name() %></p>
								
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag3.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=coupon[3].getCoupon_count() %>个助力</p>
									<p data-v-b3bd69c2=""><%=coupon[3].getCoupon_name() %></p>
								
								</li>
							</ul>
						</div>
					</div>
				<!-- 	<div data-v-165a82e2="" class="actions">
						
						<button data-v-165a82e2="" type="button" class="btn btn-main" onclick="$('#inviteFriend_panel').show();$('#inviteFriend_div').show();">
							参加并为TA助力</button>
					</div> -->
				</div>
			</div>
			<div data-v-7faf502c="" data-v-49b8e837="">
				<p data-v-7faf502c="" class="section-title">
					<img data-v-7faf502c=""
						src="page/user/activity/freeCutActivity/resource/tag.7b4ea60e.svg"
						alt="*">
						
						<%if(list.size() == 0 ) {%>
						<span data-v-7faf502c="">暂未有好友助力</span>
						<%}else {%>
						<span data-v-7faf502c="">已有<%=list.size() %>位好友助力</span>
						<%} %>
						<img
						data-v-7faf502c=""
						src="page/user/activity/freeCutActivity/resource/tag.7b4ea60e.svg"
						alt="*">
				</p>
				<div data-v-0f850d4c="" data-v-7faf502c="" class="list-wrapper">
					<%
						for(SupplierEventCodeBean bean : list){
					 %>
					<div data-v-0f850d4c="" mode="out-in" class="item-wrapper">
						<div data-v-0f850d4c="">
							<div data-v-7faf502c="" class="item">
								<img data-v-163c7539="" data-v-7faf502c=""
									src="<%=bean.getHead_img() %>"
									alt="头像" class="avatar"><span data-v-7faf502c=""
									class="text-center text-ellipsis"><%=bean.getFromUserName()+"&nbsp;&nbsp;&nbsp;&nbsp; 成功于"+ bean.getCreateDate()+"为TA助力"%></span><span data-v-7faf502c="" class="text-time"></span>
							</div>
						</div>
					</div>
					
					<%} %>
				</div>
			</div>
			
		<div class="top">
			<div class="info" style="text-align:center;color: #c3561a;">
				<b>【参加活动为TA助力】</b><br>
				
			</div>
		</div>
		<div class="img-div" style="text-align: center;" >
			<img style="width: 60%"
				src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=qrCode.getTicket() %>"
				alt="">
			<div>
				长按上方二维码识别并关注即可助力或参加活动
			</div>
		</div>
			
			
			
			<!---->
			<div data-v-ab1c5a10="" data-v-49b8e837="" class="rule-section">
				<p data-v-ab1c5a10="" class="section-title">
					<img data-v-ab1c5a10=""
						src="page/user/activity/freeCutActivity/resource/tag.7b4ea60e.svg"
						alt="*"><span data-v-ab1c5a10="">活动规则</span><img
						data-v-ab1c5a10=""
						src="page/user/activity/freeCutActivity/resource/tag.7b4ea60e.svg"
						alt="*">
				</p>
				<div data-v-ab1c5a10="">
					<p data-v-ab1c5a10="">
						1.人人免单攻略： <br data-v-ab1c5a10="">1）长按二维码识别并关注完成助力； <br
							data-v-ab1c5a10="">2）公众号底部菜单优惠活动； <br data-v-ab1c5a10="">3）打开界面后即可参与活动；
						<br data-v-ab1c5a10="">4）达到规则要求，即可领取赠送的礼品卷； <br data-v-ab1c5a10="">5）商城中下单并填写地址，等待收货；
					</p>
					<p data-v-ab1c5a10="">
						2.活动期间，用户可领取各类礼品卷一份（同品类仅可领一份）；
					</p>
					<p data-v-ab1c5a10="">3.每个好友可助力一次，同一手机号、移动设备、微信号视为同一用户；</p>
					<p data-v-ab1c5a10="">
						4.订单生成后，请尽快填写收货地址，对于已填写收货地址的用户我们会在24小时内发货，您可通过公众号查询物流状态；
					</p>
					<p data-v-ab1c5a10="">5.对物流、商品有疑问，可联系客服，微信号: yoyoyo1105 </p>
					<p data-v-ab1c5a10="">&nbsp;</p>
				</div>
			</div>
		</div>
		<!---->
		<!---->
		<!---->
		<!---->
		<!---->
		<div data-v-19d19ddd="" data-v-49b8e837="">
			<!---->
			<div data-v-cbfc8c30="" data-v-19d19ddd=""
				class="gen-share-image not-convert">
				<img data-v-cbfc8c30=""
					src="page/user/activity/freeCutActivity/resource/f703179cc4d1523908a1cf52d968b87a66270.png"
					alt="图" style="width: 375px;">
				<!---->
				<img data-v-cbfc8c30=""
					src=""
					alt="图"
					style="position: absolute; top: 300px; left: 98px; width: 180px; height: 180px;">
				<!---->
			</div>
		</div>
		<!---->
	</main>
	
	<div class="sharetofriend-md" style="display:none;" id="inviteFriend_panel" onclick="$('#inviteFriend_panel').hide();$('#inviteFriend_div').hide();"></div>
	<div class="sharetofriend-panel" style="display:none;" id="inviteFriend_div" >
		<div class="top">
			<div class="info" style="text-align:center;">
				<b>【<%=BaseContext.getWechatOriginalInfo(qrCode.getOriginalid()).getOriginal_name() %>】</b><br>
				长按下方二维码识别后关注即可完成助力
			</div>
		</div>
		<div class="img-div">
			<img
				src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=qrCode.getTicket() %>"
				alt="">
				
		</div>
	</div>
</body>
</html>