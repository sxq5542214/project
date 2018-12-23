<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.order.bean.AreaData"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierTopicBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="java.text.SimpleDateFormat"%>
<% 
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	SupplierTopicBean bean = (SupplierTopicBean)request.getAttribute("bean");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
	String fromOpenid = (String)request.getAttribute("fromOpenid");
	if(StringUtil.isNull(fromOpenid)){
		fromOpenid = null;
	}
	String share_url = null;
	if(bean == null){
		bean = new SupplierTopicBean();
		bean.setTitle("这个话题已经找不到啦！");
		bean.setContent("没有这个话题哦！亲是不是搞错啦！");
	}
	if(user != null){
		share_url = bean.getUrl().replaceAll("#server_url#", BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url());
		share_url = share_url.replaceAll("#server_domain#", BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_domain());
		share_url = share_url.replaceAll("#action_openid#", "");
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
		<link href="page/comment/css/buju.css" rel="stylesheet">
 		<link href="page/comment/css/index.css" rel="stylesheet">
		<script src="js/jquery.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
 	<link href="page/comment/css/photoswipe.css" rel="stylesheet" />
	<link href="page/comment/css/default-skin.css" rel="stylesheet" />
	<script src="js/photoswipe.min.js"></script>
	<script src="js/photoswipe-ui-default.min.js"></script>
	<script src="page/comment/js/initPhotoSwipeFromDOM.js"></script>
 <script type="text/javascript" src="js/wechat-upload.js"> </script>
 <script type="text/javascript" src="page/comment/js/comment.js"> </script>
	</head>
	
	<body style="zoom: 1;">
		<div id="body_div" style="z-index: 8;zoom: 1;">
		<div class="head" style="margin: 0 auto;">
			
			<%if(user != null && user.getParentid() != null ){ %>
				<h2 class="hd" style="margin: 0 auto;text-align: center;font-size: 24px;" ><%=bean.getTitle_attached()  %></h2>
			<%}else{ %>
				<h2 class="hd" style="margin: 0 auto;text-align: center;font-size: 24px;"><%=bean.getTitle()%></h2>
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
			
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
						<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			<br />
			
		<% if( StringUtil.isNotNull(fromOpenid))
		{ if(user != null && user.getParentid() != null ){ %>
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
				<span style="font-size:11pt;line-height:1;"></span><img width="180" style="margin: 0 auto;" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>" alt="" height="180" title="" align="" /> 
			</p>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;">长按二维码,</span><span style="font-size:11pt;line-height:1;"><span style="font-size:11pt;">参与讨论吧！</span></span> 
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
				<span style="font-size:11pt;line-height:1;"></span><img style="margin: 0 auto;" width="180" src="images/qrcode/<%=user.getOriginalid() %>.jpg" alt="" height="180" title="" align="" /> 
			</p>
		<%}else{ %>
			<p class="MsoNormal" style="margin-left:0pt;text-indent:0pt;text-align:center;">
				<span style="font-size:11pt;line-height:1;"></span><img style="margin: 0 auto;" width="180" src="images/qrcode/qrcode_for_zaixianjiayouzhan.jpg" alt="" height="180" title="" align="" /> 
			</p>
		<%} } %>
		</div>
		
		<div style="margin: 0 auto; top: 5%;left: 40%;" id="loading_img">
			<img style="margin: 0 auto;width: 80px;z-index: 1;" src="page/user/infoCenterCss/img/loding2.gif">
		</div>
		
  <div id="swrap">
  </div>
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
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %>,"param":<%=bean.getId()%>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_SUPPLIER_TOPIC %>",share_time_ms:share_time_ms },
				success : function(data) {
					
				}
			});
	  	}
	  	function shareTimeFirendOneSuccess(){
		  	$.ajax({
				type : "POST",
				url : "user/handleUserShare.do",
				data : {"openid": '<%=user.getOpenid()%>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_ONE %>,"param":<%=bean.getId()%>,
				 "share_from":"<%=UserSenceLog.SHARE_FROM_SUPPLIER_TOPIC %>",share_time_ms:share_time_ms },
				success : function(data) {
					
				}
			});
	  	}
	  	
		<%if(user.getParentid() != null  ){%>
			weixinInit.setShareTitle('<%=StringUtil.convertNull(bean.getTitle_attached())%>');
			weixinInit.setShareDesc('<%=StringUtil.convertNull(bean.getTitle_attached())%>');
		<%}else{ %>
			weixinInit.setShareTitle('<%=bean.getTitle()%>');
			weixinInit.setShareDesc('<%=bean.getTitle()%>');
		<%}%>

		weixinInit.setShareLink('<%=share_url%>&fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
		weixinInit.setShareImg('<%= bean.getImg_url() == null? basePath+"images/icon/4g.png":bean.getImg_url() %>');
		weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
		weixinInit.setOnShareAppMessageSuccess(shareTimeFirendOneSuccess);
		setTimeout(' $("#loading_img").hide();$("#body_div").show(); ',2000);
	<%}%>
	//修改原型
var p = new CommentPageInit();
//三个tab的名称
p.title_name=["我的留言","精选留言","好友留言"];
//必要的参数传递
<%if(user != null && fromOpenid == null) {%>
p.comment_openid = "<%=user.getOpenid() %>";
	   <%}else{ %>
p.comment_openid = "";
	   <%} %>

p.comment_code = "<%=bean.getComment_code()%>";
//初始化页面
p.pageInit();
</script>
</html>

