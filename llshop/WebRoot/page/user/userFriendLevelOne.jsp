<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.basic.framework.context.WebContext"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List<UserWechatBean> friends = (List<UserWechatBean>)request.getAttribute("friends");
Object openid = request.getAttribute("openid");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>好友列表</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
</head>

<body>

	<div id="_contain">

		<div>
			<a href="wechat/user/toInviteFriendsShare.do?openid=<%=openid %>">
					<img src="images/user/user_order_top.png" style="width: 100%">
			</a>
			
		</div>

		<div class="favorable" style="text-align: center;margin: 0 auto;">
			<!-- 
<div class="fb">
    <div class="fb-lt">
    	<img src="images/scenics/scenic_3.png">
        <p class="fb-name">塞班岛5-6天</p>
        <p class="price">
        	<span class="discount"><big>4.7</big>折</span>
            <span class="num"><big>5699</big>元起</span>
        </p>
    </div>
</div> -->

			<%
				for (UserWechatBean user : friends) {
			%>
			<div class="fb" style="height: auto;width: 98%;">
				<div class="fb-lt" style="width: 100%;">
					<!-- <img src="images/scenics/scenic_3.png"> -->

					
					<p class="price">
						<span class="discount" style="font-size: 0.4rem;margin: 0;width: 70%;text-align: left;">昵称：<big  style="color: black;"><%=user.getNick_name()%></big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;text-align: right: ;">拥有好友：<big  style="color: black;"><%=user.getOffline_num()%>位</big></span>
						 
						<span class="discount" style="font-size: 0.4rem;margin: 0;">最后订购时间：<big style="color: black;"><%=StringUtil.convertNull(user.getLast_order_time())%></big></span>
						<span class="discount" style="font-size: 0.4rem;margin: 0;float: right;">拥有积分：<big style="color: black;"><%=user.getPoints()/100d%>元</big>&nbsp;&nbsp;&nbsp;&nbsp;</span>
					</p>



				</div>
			</div>

			<%
				}
			%>


		</div>

	</div>

	<!-- <footer>
	<div class="foot act">
    	<img src="images/foot/ft_1_1.png">
        <p>推荐</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_2.png">
        <p>目的地</p>
    </div>
	<div class="foot">
    	<img src="images/foot/ft_3.png">
        <p>帖子</p>
    </div>
	<a href="person/person.html"><div class="foot">
    	<img src="images/foot/ft_4.png">
        <p>我的</p>
    </div></a>
</footer>     -->

</body>
</html>