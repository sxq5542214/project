<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserWechatBean user = (UserWechatBean)request.getAttribute("user");
String serverUrl = BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url();
List<SupplierEventCodeBean> list = (List<SupplierEventCodeBean>)request.getAttribute("list");
SupplierEventBean supplierEvent = (SupplierEventBean)request.getAttribute("supplierEvent");
Integer supplierEventId = supplierEvent.getId();
ActivityPrize[] prize = new ActivityPrize[]{new ActivityPrize(),new ActivityPrize(),new ActivityPrize(),new ActivityPrize()};
prize[0].setId(115);
prize[0].setPrize_name("腰果200g");
prize[0].setTotalcount(5); //复用字段，代表参与人数要求
prize[1].setId(116);
prize[1].setPrize_name("碧根果200g");
prize[1].setTotalcount(10); //复用字段，代表参与人数要求
prize[2].setId(117);
prize[2].setPrize_name("开心果200g");
prize[2].setTotalcount(15); //复用字段，代表参与人数要求
prize[3].setId(118);
prize[3].setPrize_name("山核桃仁200g");
prize[3].setTotalcount(30); //复用字段，代表参与人数要求
int maxCount = prize[3].getTotalcount();
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
.zg-page-loading {
	position: fixed;
	top: 50%;
	left: 50%;
	margin: -20px 0 0 -20px;
	width: 40px;
	height: 40px;
	border: 2px solid;
	border-color: #ffd800 #ffd800 transparent;
	border-radius: 50%;
	box-sizing: border-box;
	animation: zg-page-loading 1s linear infinite;
}
.get-prize{
	margin:10px;
	display: inline-block;
	width: 40%;
	height:172px;
	background-image: url('images/user/supplierProductShop/coupon/juan.png');
	background-size:100% 100%;
}
@
keyframes zg-page-loading { 0% {
	transform: rotate(0deg);
}
100%
{
transform
:
 
rotate
(360deg);

        
}
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
<script	src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
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
						<p data-v-fbc5d9ba="" class="order-title">【坚果赠送卷】<br>邀请好友帮忙助力<br>积累后可免费得坚果礼品！
							</p>
						<p data-v-fbc5d9ba="" class="order-price">
							价值可达 <small data-v-fbc5d9ba="" class="mtfin">￥</small><b
								data-v-fbc5d9ba="" class="mtfin">80</b>
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
								String prizeName = "杏仁150g";
								int persent = size *100 / maxCount;
								if(size < prize[0].getTotalcount()){
									level = 1;
									needNum = prize[0].getTotalcount() - size;
									prizeName = prize[0].getPrize_name();
									persent = size * 10 / prize[0].getTotalcount();
								}else if(size < prize[1].getTotalcount()){
									level = 2;
									needNum = prize[1].getTotalcount() - size;
									prizeName = prize[1].getPrize_name();
									persent = size *34 /  prize[1].getTotalcount();
								}else if(size < prize[2].getTotalcount()){
									level = 3;
									needNum = prize[2].getTotalcount() - size;
									prizeName = prize[2].getPrize_name();
									persent = size * 60 /  prize[2].getTotalcount();
								}else if(size <= prize[3].getTotalcount()){
									level =4;
									needNum = prize[3].getTotalcount() - size;
									prizeName = prize[3].getPrize_name();
									persent = size * 100 /  prize[3].getTotalcount();
									if(persent < 65) persent = 65;
								}
								String needStr = "已有"+size+"个助力，再邀请"+needNum+"人可得"+prizeName;
								if(size >= maxCount){
									needStr = "恭喜您可以拿走大奖啦！";
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
									<p data-v-b3bd69c2=""><%=prize[0].getTotalcount() %>个助力</p>
									<p data-v-b3bd69c2=""><%=prize[0].getPrize_name() %></p>
									
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag1.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=prize[1].getTotalcount() %>个助力</p>
									<p data-v-b3bd69c2=""><%=prize[1].getPrize_name() %></p>
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag2.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=prize[2].getTotalcount() %>个助力</p>
									<p data-v-b3bd69c2=""><%=prize[2].getPrize_name() %></p>
								
								</li>
								<li data-v-b3bd69c2=""><img data-v-b3bd69c2=""
									src="page/user/activity/freeCutActivity/resource/redbag3.png"
									alt="红包">
									<p data-v-b3bd69c2=""><%=prize[3].getTotalcount() %>个助力</p>
									<p data-v-b3bd69c2=""><%=prize[3].getPrize_name() %></p>
								
								</li>
							</ul>
						</div>
					</div>
					<div data-v-165a82e2="" class="actions">
						<!---->
						<button data-v-165a82e2="" type="button" class="btn btn-main" onclick="$('#inviteFriend_panel').show();$('#inviteFriend_div').show();">
							邀请好友助力</button>
						<%if(size >=5){ %>
							<button data-v-165a82e2="" type="button" class="btn btn-main" style="margin: 0;padding: 0;" onclick="$('#getPrize_panel').show();$('#getPrize_div').show();">
							领取奖励</button>
						<%} %>
					</div>
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
						1.免单攻略： <br data-v-ab1c5a10="">1）点击邀请好友助力； <br
							data-v-ab1c5a10="">2）按界面提示发送给未关注过公众号的好友或朋友圈； <br data-v-ab1c5a10="">3）好友打开界面后，长按二维码识别并关注完成助力；
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
	<div class="sharetofriend-panel" style="display:none;top: 5em;" id="inviteFriend_div" >
		<div class="top"> 
			<div class="info" style="text-align:left;">
				点击右上角选择 <b>[分享到微信好友或朋友圈]</b> 即可参加活动！<br><br>好友打开您分享的文章，并扫码即可完成助力
			</div>
		</div>
		<div class="img-div">
			<!-- <img
				src="page/user/supplierEvent/common/active_share_f1.png"
				alt="">
				<img
					src="images/event/weixin-sharepic-friends.jpg"
					alt=""> -->
			<img
				src="page/user/activity/freeCutActivity/resource/share.png"
				alt="">
				
		</div>
	</div>
	<!--  领取奖品的  -->
	<div class="sharetofriend-md" style="display:none;" id="getPrize_panel" onclick="$('#getPrize_panel').hide();$('#getPrize_div').hide();"></div>
	<div class="sharetofriend-panel" style="display:none;top: 5em;" id="getPrize_div" >
		<div class="top">
			<div class="info" style="text-align:center;">
				<% if(level == 1) {%>
				 <b>【您还没有可以领的奖励哦，赶快去邀请好友吧！】</b> 
				<%}else{ %>
				 <b>【恭喜您可以领奖啦】 <br>  <%=needStr %> </b> 
				 <%} %>
			</div>
		</div>
		<div class="img-div" style="text-align: center;">
			<!-- <img
				src="page/user/supplierEvent/common/active_share_f1.png"
				alt="">
				<img
					src="images/event/weixin-sharepic-friends.jpg"
					alt=""> -->
		
			<%for(int i = 1 ; i < level  ; i++){ %>
			
			
			<div class="get-prize" onclick="getPrize(<%=prize[i-1].getId()%>,'<%=prize[i-1].getPrize_name() %>')">
				<span style="font-size: .4rem; color: rgb(213,36,34); ">
				&nbsp;<br><b>礼品卷<br>&nbsp; <br> <%=prize[i-1].getPrize_name() %><br> </b> 
				</span>
			</div>
			
			<%} %>
		</div>
	</div>
</body>
<!-- 
<img alt="" src="testImg.do" style="display: none;"> -->


<script type="text/javascript">
var openid = '<%=user.getOpenid()%>';
function getPrize(prizeId,prizeName){
	if(confirm('确定领取:'+ prizeName +"? ")){
		var supplierEventId = <%=supplierEventId%> ;
		$.ajax({
			url:"activity/user/dealFreeCutPrize.html",
			data:{openid:openid,  prizeId:prizeId,supplierEventId:supplierEventId},
			async:false,
			success:function(val){
				if(val.indexOf('成功')>=0){
					if(confirm('领取成功啦，是否跳转至公众号里下订单？')){
						window.location.href = 'wechat/user/toDefaultPlatformSupplierProduct.do?openid='+openid;
					}
				}else if(val.indexOf('已经')>=0){
					if(confirm('已经领取过啦，是否跳转至公众号里下订单？')){
						window.location.href = 'wechat/user/toDefaultPlatformSupplierProduct.do?openid='+openid;
					}
				}else {
					alert(val);
				}
			}
		});
	}
}

	weixinInit.setShareTitle("<%=supplierEvent.getTitle() %>");
	weixinInit.setShareDesc("<%=supplierEvent.getDescrip() %>");
	weixinInit.setShareLink("<%=serverUrl+"activity/user/toFreeCutHelpActivity.html?toOpenid="+user.getOpenid()+"&supplierEventId="+supplierEventId  %>");
	weixinInit.setShareImg("<%=supplierEvent.getImg_url()%>");

</script>
</html>