<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityRule"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
ActivityPrize prize = (ActivityPrize)request.getAttribute("prize");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
ActivityConfigBean activityConfigBean = (ActivityConfigBean)request.getAttribute("activityConfigBean");

%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="viewport" content="width=device-width, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
<meta name="generator" content="bd-mobcard">
<meta http-equiv="Cache-Control" content="no-cache">

<title>失败</title>
<link href="page/user/activity/signActivity/css/style_join_fail.css" rel="stylesheet" type="text/css" />
</head>

<body >
<div class="resuleBox">
<div id="resule-status-box" style="">
        <div id="resule-status-scrollWrap" style="height: 442px;">
            <div class="resule-bgLight" style="display: none;"></div>
            <div id="resule-status-bird" class="resule-status-birdfly" style="display: block;"></div>
            <div id="resule-status-head">
                <div class="resule-status-userImg one" style="border-color: rgb(181, 181, 181);">
                    <img src="page/user/activity/signActivity/images/manImg-fail.jpg">
                </div>
                <div id="resule-status-ribbon" class="resule-status-faiRibbon"></div>
            </div>
			<div id="resule-status-body">
				<p class="youraward" style="line-height:1.2rem">很遗憾：</p>
				<p class="resule-status-minscore layerId-11 defBgColor" style=""><%=prize.getRemark() %> </br>条件不满足！<span class="resuleArg resulescoreLimit"></span> </p>
				<p id="bestArg" style="margin-top:0.5rem">当前积分：<span class="resuleArg"><%=user.getPoints()/100 %></span><span class="result-scoreUnit"><span class="gameScoreUnit">元积分</span></span></p>
				
				<div id="rank_showRule" style="text-decoration: underline; margin: 0.7rem 0rem;"ontouchend="showRule();">活动规则</div>
				<div>
				<%if(!StringUtil.isNull(activityConfigBean.getRule()) && activityConfigBean.getRule().size() > 0){
                           for(ActivityRule rule:activityConfigBean.getRule()){
                           %>
                           	<li class = "fleft"><%=StringUtil.convertNull(rule.getSeq()) %>、<%=StringUtil.convertNull(rule.getDescription()) %></li>
                           <%}} else{%>
                           <li class = "fleft">无规则</li>
                           <%} %>
			</div>
			</div>
			<a href="activity/user/getSignActivityList.do?openid=<%=user.getOpenid() %>">
            <div id="resule-foot-box">				
                <div class="resule-foot-two">
                    <div class="resule-button resule-status-home" style="width:10rem;" >返回首页</div>
                </div>
            </div>
            </a>
        </div>
    </div>
	</div>
</body>
</html>
