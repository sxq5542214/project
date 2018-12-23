<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.business.order.bean.AreaData"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	WechatOriginalInfoBean original = (WechatOriginalInfoBean) request.getAttribute("original");
	UserWechatBean user = (UserWechatBean)request.getAttribute("user"); 
	List<SupplierEventCodeBean> codeList = (List<SupplierEventCodeBean>)request.getAttribute("codeList");
	SupplierEventBean event = (SupplierEventBean)request.getAttribute("event");
	
	String serverPath =  request.getScheme() + "://"
			+ original.getServer_domain() + ":" + request.getServerPort()
			+ path + "/";
	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-cn">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

<title>【重磅免费】<%=event.getTitle() %></title>

<meta name="keywords">
<meta name="description">
<meta name="copyright" content="2016, zmjiudian.com">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=no">
<meta name="format-detection" content="telephone=no">

<link rel="shortcut icon" href="images/icon/4g.png">
<!-- Bootstrap core CSS -->
<link href="page/user/supplierEvent/common/bootstrap.css"
	rel="stylesheet">
<link href="page/user/supplierEvent/common/zmjiudian.css"
	rel="stylesheet">
<link href="page/user/supplierEvent/common/zmjiudian-ex.css"
	rel="stylesheet">


<style>
html,body {
	height: 100%;
	margin: 0;
}

.modal-content {
	width: 90%;
	margin: auto;
}

.modal-body {
	padding: 2.6em 0.8em 1.7em 0.8em;
}

.modal-footer {
	margin-top: 0;
	padding: 0.7em 0.9em;
}

.btn-default,.btn-primary {
	width: auto;
	min-width: 4em;
	border: none;
	color: #3e9ec0;
	font-size: 1.4em;
}

.box-big-btn {
	width: 96%;
}

.box-btn-bottomborder {
	padding-bottom: 0.7em;
	margin-bottom: 0.3em;
	border-bottom: 1px solid #eeeded;
	border-radius: 0;
}
</style>

	<link
		href="page/user/supplierEvent/common/weixinactive_luck.css"
		rel="stylesheet">
	<script
		src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
	<script type="text/javascript" src="js/wechat/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
		
</head>

<body class="weixinreg_xmas_body3" style="overflow-x: hidden;">
	<div style="position: absolute;top: 25%;left: 40%;" id="loading_div"><img style="width: 80px;z-index: 1;" src="page/user/infoCenterCss/img/loding2.gif"></div>
	
	<div id="body_div" style="z-index: 8;">
	<div class="weixinreg_xmas_header">
		<img src="<%=event.getImg_url() %>"
			alt="">
		<div class="acount">
			<table border="0" cellpadding="0" cellspacing="0">
				<tbody>
					<tr>
						<td class="left"><img
							src="<%=user.getHead_img() %>" alt="">
						</td>
						<td class="right">
							<div class="phone"></div>
							<div class="name"><%=user.getNick_name() %></div></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="sharedone-panel">
		<div class="modular-panel">
			<div class="top-panel">
				<img src="page/user/supplierEvent/common/bgborder_top.png"
					alt="">
			</div>
			
			<% //活动已过了截止时间
			if(event.getEnd_time().compareTo(DateUtil.getNowDateStr()) < 0 ){
			%>
				<div class="middle-panel">
				<div class="btns">
					
				<input type="button" id="sharefd-btn" class="sharefd-btn" style="width: 100%;background-color: #A4845F;
   						color: white;"
					value="活动已结束，请参加其它活动吧！" readonly="readonly">
						
				</div>
				<div class="tip" style="color:red;text-align:left;font-size:14px;" >赶快看看自己的幸运码最后4位是否和开奖的一致吧！<br>
				</div>
			</div>
			<% //好人 
			}else if(user.getParentid() != null   && codeList.size() == 0 ){ %>
			<div class="middle-panel">
				<div class="btns">
					<input type="button" id="sharefd-btn" class="sharefd-btn" style="width: 100%;background-color: #A4845F;
    color: white;" value="点这里参加活动并邀请好友" onclick=" $('#inviteFriend_div').show();$('#inviteFriend_panel').show(); "> 
    
					<!-- <input type="button" id="partner-btn"
						class="partner-btn"
						onclick="gourl('/Active/Weixin_LuckActive_SharePartner/180?openid=okg6-uDsQ4CTcJsZzq4HxC21RK_w')"
						value="提高中奖机会"> -->
				</div>
				<div class="tip" style="color:red;text-align:left;" >1)首次分享此页面至朋友圈即获得一个幸运码<br>
								2)每成功邀请一位新用户参加活动，可多获得一个幸运码<br>
								
								<%-- <% if(event.getBonusMaxUserId() != null && event.getId() == 7 && user.getId() <= event.getBonusMaxUserId()){ %>
								4)10月16日24点前成功参与活动即可获得微信现金红包（仅一次，金额1到5元随机） <br>
								<%} %> --%>
								注：新用户指从未关注过本公众号的微信用户</div>
				
			</div>
			<%}else{ %>
			<div class="middle-panel">
				<div class="btns">
					<input type="button" id="sharefd-btn" class="sharefd-btn" style="width: 100%;background-color: #A4845F;
    						color: white;"
						value="您已经成功参加活动" onclick="">
						
					
					<!-- <input type="button" id="partner-btn"
						class="partner-btn"
						onclick="gourl('/Active/Weixin_LuckActive_SharePartner/180?openid=okg6-uDsQ4CTcJsZzq4HxC21RK_w')"
						value="提高中奖机会"> -->
				</div>
				<a href="<%=serverPath%>activity/user/getSignActivityList.do?openid=<%=user.getOpenid() %>" >
					<div class="tip" style="color:red;text-align:left;font-size:14px;" >手机流量8折优惠 支付满12元100%送微信红包 最低1元最高免单!点这里购买<br>
								</div>
				</a>
			</div>
			<%} %>
			<div class="line-panel">
				<img
					src="page/user/supplierEvent/common/bgborder_line.png"
					alt="">
			</div>
			<div class="middle-panel">
				<div class="tit">我的幸运码</div>
				<div class="look">
					您拥有<span class="h"><%=codeList.size() %></span>个幸运码
				</div>
				<div class="code-list">
					<ul>
						<% for(int i = 0 ; i < codeList.size(); i++){ %>
						<li><%=codeList.get(i).getCode() %></li>
						<%} %>
					</ul>
				</div>
			</div>
			<div class="line-panel">
				<img
					src="page/user/supplierEvent/common/bgborder_line.png"
					alt="">
			</div>
			<div class="middle-panel">
				<div class="tit">抽奖规则<br>取自福彩数据，100%真实可靠</div>
				<div class="active-info">
					<div class="t1"><%=event.getLotteryInfo() %></div>
					<div class="t2">
						目前共<span class="b"><%=event.getJoinCount() + 3 %></span>人参加活动<span
							style="margin:0 0 0 1em;color:#888;"></span>
					</div>
					<div class="d1">开奖时间：<span style="color: red;font-size: 1em;"><%=event.getLotteryDate() %></span> </div>
					<div class="d1">开奖结果：<span style="color: red;font-size: 1em;"><%=event.getLotteryNumber() %></span> </div>
					<div class="d1">
						<a href="http://www.fulicaipiao.cn/shanghai/ttcx4_shanghai/" target="_blank">查询“福彩天天彩选4”开奖情况点这里</a>
					</div>
				</div>
			</div>
			<div class="line-panel">
				<img
					src="page/user/supplierEvent/common/bgborder_line.png"
					alt="">
			</div>
			
			<div class="middle-panel">
			
				<div class="tit"> <strong style="color: red;font-size: 1.2em;">领奖方式</strong> </div>
				<div class="t1" style="font-size: 1.1em;width: 90%;margin: 0 auto;">
					<%=event.getLotteryPlace() %>
				</div>
			</div>
			
		</div>
		<div class="logo" style="text-align: center;margin-top: 1.5em;padding-bottom: 1.2em;">
		&nbsp;
		<!--	<img
				src="page/user/supplierEvent/common/activity-bottom-logo.png"
				alt=""> -->
		</div>
		
	</div>

	<%if(user.getParentid() != null  && codeList.size() == 0 ){%>
	<div class="sharetofriend-md" style="display:none;" id="inviteFriend_panel" onclick="$('#inviteFriend_panel').hide();$('#inviteFriend_div').hide();"></div>
	<div class="sharetofriend-panel" style="display:none;" id="inviteFriend_div" >
		<div class="top">
			<div class="info" style="text-align:left;">
				点击右上角选择 <b>[分享到朋友圈]</b> 即报名成功！<br><br>好友阅读您分享到朋友圈的文章，扫码并关注公众号即为成功邀请
			</div>
		</div>
		<div class="img-div">
			<!-- <img
				src="page/user/supplierEvent/common/active_share_f1.png"
				alt=""> -->
			<img
				src="images/event/weixin-sharepic-friends.jpg"
				alt="">
		</div>
	</div>
	<%} %>
	<input type="hidden" id="isapptxt" value="0">
	<!--  -->


	<div style="width: 100%;height: 40px;text-align: center;margin: 0 auto;position: fixed;bottom: 10px;">
		<div style="width: 100%;height: 100%;background: #3D3C39;opacity:0.75;z-index: -1;position: absolute;"></div>
		<a href="<%=serverPath%>activity/user/getSignActivityList.do?openid=<%=user.getOpenid() %>" style="text-align: center;margin: 0 auto;line-height: 40px;">
			<!-- <strong>
				<span style="font-size: 1.6em;color: white;text-align: center;margin: 0 auto;opacity:1;">手机流量8折购，还送现金红包</span>
			</strong> -->
			<img style="height: 100%;width: 90%;" alt="" src="page/user/supplierEvent/common/daohang.png">
		</a>
	</div>
	</div>
	<!-- Bootstrap core JavaScript -->

	<script>
	    function gourl(url)
	    {
	        location.href = url;
	    }
	    
	    $("#body_div").hide();
		var openid = $("#openid").val();
		var share_time_ms = weixinInit.getShare_time_ms();
		function shareTimeFirendSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_MYSUPPLIER_EVENT %>",share_time_ms:share_time_ms ,param:<%=event.getId()%> },
				success : function(data) {
					location.href = '<%=basePath%>supplierEvent/userRead/toMyEventPage.do?eventId=<%=event.getId()%>&openid=<%=user.getOpenid()%>';
				}
			});
	  	}
	  	function shareTimeFirendOneSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_ONE %>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_MYSUPPLIER_EVENT %>",share_time_ms:share_time_ms ,param:<%=event.getId()%> },
				success : function(data) {
//					location.href = '<%=basePath%>supplierEvent/userRead/toMyEventPage.do?eventId=<%=event.getId()%>&openid=<%=user.getOpenid()%>';
					
					<%-- <%if(user.getParentid() != null){%>
						alert('分享到朋友圈才可以哟！');
					<%}%> --%>
				}
			});
	  	}
		
		<%if(user.getParentid() != null  ){%>
			weixinInit.setShareTitle('<%=StringUtil.convertNull(event.getTitle_attached())%>');
			weixinInit.setShareDesc('<%=StringUtil.convertNull(event.getTitle_attached())%>');
		<%}else{ %>
			weixinInit.setShareTitle('<%=event.getTitle()%>');
			weixinInit.setShareDesc('<%=event.getTitle()%>');
		<%}%>
		weixinInit.setShareLink('<%=original.getServer_url()%>supplierEvent/readEvent.do?eventId=<%=event.getId()%>&fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
		weixinInit.setShareImg('<%= event.getImg_url() == null? basePath+"images/icon/4g.png":event.getImg_url() %>');
		weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
		weixinInit.setOnShareAppMessageSuccess(shareTimeFirendOneSuccess);
		
		setTimeout('$("#body_div").show(); $("#loading_div").hide(); ',2500);
    </script>

	<div class="hidden">
		<input type="hidden" id="pagetag" value="">
	</div>



	<div id="qq-sendUrl-btn"></div>
</body>
</html>