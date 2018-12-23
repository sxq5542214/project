<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.AreaData"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	SupplierEventBean bean = (SupplierEventBean)request.getAttribute("bean");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
	WechatOriginalInfoBean original = BaseContext.getWechatOriginalInfo(user.getOriginalid());
	if(bean == null){
		bean = new SupplierEventBean();
		bean.setTitle("不要不要嘛，这个活动已经找不到了！");
		bean.setContent("没有这个活动哦！亲是不是搞错啦！");
	}
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title><%=bean.getTitle() %></title>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1">
		<link rel="stylesheet" type="text/css" href="css/news/style.css">
		<script src="js/jquery.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	</head>
	
	<body style="zoom: 1;">
		<div id="body_div" style="z-index: 8;zoom: 1;">
		<div class="head">
			
			<%if(user != null && user.getParentid() != null ){ %>
				<h2 class="hd"><%=bean.getTitle_attached() %></h2>
			<%}else{ %>
				<h2 class="hd"><%=bean.getTitle()%></h2>
			<%} %>
		</div>
		
		
		<%if(user != null && user.getParentid() != null){ %>
		<div>
			<%=bean.getPrize_content() %>
		</div>
		<%}else{ %>
		<div>
			<%=bean.getContent() %>
		</div>
		<%} %>
			
			
			
		<%if(user != null && user.getParentid() != null){ %>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:10pt;line-height:1;"> </span> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;">-本期活动最终解释权归<%=BaseContext.getMchName(ticket.getOriginalid()) %>所有-</span> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;font-family:KaiTi_GB2312;color:#E53333;line-height:1;"><strong>幸运生活，从<%=BaseContext.getMchName(ticket.getOriginalid()) %>开始</strong></span> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;"></span><img width="180" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt="" height="180" title="" align="" /> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;">长按二维码,</span><span style="font-size:11pt;line-height:1;"><span style="font-size:11pt;">关注<span style="color:#E53333;">【</span></span>
				<span style="font-family:宋体;font-size:11pt;line-height:16.2963px;color:#E53333;"><%=BaseContext.getMchName(ticket.getOriginalid()) %></span><span style="font-size:11pt;"><span style="color:#E53333;">】</span>公众号，</span></span> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;"><span style="font-size:11pt;"><span style="font-size:15px;line-height:14.6666669845581px;">更多好玩有趣的事情与您一起分享</span><br />
			</span></span> 
			</p>
			<p>
				<br />
			</p>
		<%}else if(user != null){ %>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;"></span><img width="180" src="images/qrcode/<%=user.getOriginalid() %>.jpg" alt="" height="180" title="" align="" /> 
			</p>
		<%}else{ %>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;"></span><img width="180" src="images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" height="180" title="" align="" /> 
			</p>
		<%} %>
		</div>
		
		<div style="position: absolute;top: 25%;left: 40%;" id="loading_img">
		<img style="width: 80px;z-index: 1;" src="page/user/infoCenterCss/img/loding2.gif"></div>
</body>

<script type="text/javascript">
	$("#loading_img").hide();
	//微信配置
	<% if(user != null){%>
		$("#loading_img").show();
		$("#body_div").hide();
		var openid = $("#openid").val();
		var share_time_ms = weixinInit.getShare_time_ms();
		function shareTimeFirendSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_SUPPLIER_EVENT %>",share_time_ms:share_time_ms },
				success : function(data) {
					location.href = '<%=basePath%>supplierEvent/userRead/toMyEventPage.do?eventId=<%=bean.getId()%>&openid=<%=user.getOpenid()%>';
				}
			});
	  	}
	  	function shareTimeFirendOneSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_ONE %>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_SUPPLIER_EVENT %>",share_time_ms:share_time_ms },
				success : function(data) {
					location.href = '<%=basePath%>supplierEvent/userRead/toMyEventPage.do?eventId=<%=bean.getId()%>&openid=<%=user.getOpenid()%>';
				}
			});
	  	}
		setTimeout(' $("#loading_img").hide();$("#body_div").show(); ',2000);
		weixinInit.setShareLink('<%=original.getServer_url()%>supplierEvent/readEvent.do?eventId=<%=bean.getId()%>&fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
		weixinInit.setShareTitle('<%=bean.getTitle()%>');
		weixinInit.setShareImg('<%= bean.getImg_url() == null? basePath+"images/icon/4g.png":bean.getImg_url() %>');
		weixinInit.setShareDesc('<%=bean.getTitle()%>');
		weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
		weixinInit.setOnShareAppMessageSuccess(shareTimeFirendOneSuccess);
	<%}%>
</script>
</html>

