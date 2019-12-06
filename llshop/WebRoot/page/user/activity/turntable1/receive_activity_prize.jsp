<%@page import="com.yd.business.activity.bean.ActivityWinHisBean"%>
<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	List<ActivityWinHisBean> prizeList = (List<ActivityWinHisBean>) request.getAttribute("prizeList");
	String prizeName = null;
%>
<!DOCTYPE html>
<html>
<head>

<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>【美味坚果】和【现金红包】免费送啦！</title>
<link rel="stylesheet"
	href="page/user/activity/turntable1/css/common.css">
<link rel="stylesheet"
	href="page/user/activity/turntable1/css/index.css">

<script	src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	
<!-- 移动端适配 -->
<script>
	var html = document.querySelector('html');
	changeRem();
	window.addEventListener('resize', changeRem);

	function changeRem() {
		var width = html.getBoundingClientRect().width;
		html.style.fontSize = width / 10 + 'px';
	}
</script>
</head>

<body style="background-color:#fce243;display: none;">
	<div id="shareDive" style="display: none;">
		<div id="shadowDiv"
			style="width:100%;height:100%;position:absolute;left:0;top:0;z-index:2;background-color:#000;opacity:0.6;">

		</div>
		<div style="position: fixed;margin-top: 50%;text-align: center;z-index: 99;">
			<img width="80%" style="height: 100%;margin: 0 auto;"
				src="<%=basePath%>images/event/weixin-sharepic-friends.jpg" alt="">
    	<p style="height: auto;">
			<span style="font-size: 16px;color:white;">温馨提示：<br>
				<span style="font-size: 16px;color:white;" id="tips">快去告知好友吧，奖品将会立刻发放到账！</span>
			</span>
		</p>
		</div>


	</div>
	<div class="banner">
		<img src="page/user/activity/turntable1/image/banner2.jpg" />
	</div>
	<div class="activity activity2">
		<div class="activity-amin" style="text-align: center;">
			<h2>我的奖品</h2>
			<table class="tb0">
				<tr>
					<th>中奖时间</th>
					<th>中奖奖品</th>
					<th>中奖结果</th>
				</tr>

				<%
					for (ActivityWinHisBean bean : prizeList) {
						prizeName = bean.getPrize_name();
				%>
				<tr>
					<td><%=bean.getCreate_time()%></td>
					<td style="line-height: inherit;"><%=bean.getPrize_name()%><br>（<%=bean.getRemark() %>）</td>
					<td><%=bean.getDictValueByField("status")%></td>
				</tr>
				<%
					}
				%>
			</table>

			<%
				if (user.getStatus() == UserWechatBean.STATUS_SUBSCRIBE) {
					for (ActivityWinHisBean bean : prizeList) {
						if (bean.getStatus() != ActivityWinHisBean.STATUS_SHARED) {
							// 提示告知后领奖
			%>
			<input type="hidden" id="winHisId" value="<%=bean.getId()%>">
			<a class="a-main2 a3" onclick="showShareDiv();"><strong>点我领奖</strong>
			</a>
			<%
				} else { //  直接领奖
			%>
			<a class="a-main2 a3"><strong>您已领奖</strong> </a>
			<%
				}
					}
				} else {
			%>
			<img alt="请长按二维码" src="images/qrcode/qrcode_for_WEjianguohui.jpg">
			<br> <span class="a-main2 a3" style="font-size: 0.4rem;">请长按上方二维码，关注公众号后即可领奖</span>
			<%
				}
			%>
		</div>
	</div>
<script type="text/javascript">
var openid = '<%=user.getOpenid()%>';
function showShareDiv(){
	document.getElementById("shadowDiv").style.height = window.innerHeight +'px';
	document.getElementById("shareDive").style.display = "block";
}


	function shareSucess(){
		var winHisId = document.getElementById('winHisId').value ;
		$.ajax({
        		type : "POST",
        		url : "activity/prize/receivePrize.html",
        		data : {"openid": openid,"winHisId": winHisId  },
        		success : function(result) {
         			alert(result );
					$("#tips").html(result);
         			
        		}
        	});
	}
	function needSharePYQ(){
		alert('要告知到朋友圈才可以哟！');
		$("#tips").html('要告知到朋友圈才可以哟！');
	}
	weixinInit.setOnShareTimelineSuccess(shareSucess);
	weixinInit.setOnShareAppMessageSuccess(needSharePYQ);
	<% 
		if(prizeName != null){
	 %>
		weixinInit.setShareTitle("<%="必中！【美味坚果】和【现金红包】免费送啦，【"+user.getNick_name()+"】抽中了【"+prizeName+"】，快看看你的运气吧！" %>");
	 <%}else{%>
		weixinInit.setShareTitle("<%="必中！【美味坚果】和【现金红包】免费送啦，限量1000份，再迟就没有了！" %>");
	<%}%>
	weixinInit.setShareDesc("<%="必中！【美味坚果】和【现金红包】免费送，限量1000份，再迟就没有了！" %>");
	weixinInit.setShareLink("<%=BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url() %>activity/user/toTurntable1Activity.html?fromOpenid=<%=user.getOpenid()%>");
	weixinInit.setShareImg("http://m.jg-shop.cn/jgshop/page/user/activity/freeCutActivity/resource/share_img.jpg");
	
</script>
</body>
</html>