<%@page import="com.yd.business.activity.bean.ActivityInstanceBean"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityRemindBean"%>
<%@page import="com.yd.business.activity.bean.ActivityRule"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%List<ActivityInstanceBean> returnInstanceList = (List<ActivityInstanceBean>)request.getAttribute("returnInstanceList"); 
ActivityConfigBean activityConfigBean = (ActivityConfigBean)request.getAttribute("activityConfigBean");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
int size = returnInstanceList.size();
int width = 100;
if(size>0){
width = (100/size);
}
int hour = new Date().getHours();
String nowDate = DateUtil.formatDateOnlyDate(new Date());
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<script type="text/javascript" src="js/jquery.js"></script>
<link rel='stylesheet' href='page/user/activity/signActivity/css/hourlyActivity.css'> 
<script type="text/javascript" src="page/user/activity/signActivity/js/hourlyActivity.js"></script>
<script>
	function returnDate(startDateTime,startDate,hour){
		var startDateTimeList = startDateTime.split("-");
		startDate.setFullYear(startDateTimeList[0],(startDateTimeList[1]*1-1),startDateTimeList[2]);
		startDate.setHours(hour);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		startDate.setMilliseconds(0);
		return startDate;
	}
</script>
<style>
html {
	font-size: 50px;
	-webkit-tap-highlight-color: transparent;
	height: 100%;
	min-width: 320px;
	overflow-x: hidden
}

body {
	font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;
	font-size: .28em;
	line-height: 1;
	color: #333;
	background-color: #f0efed
	
}

    @font-face {
        font-family: 'cate_icon';
        src: url(http://ms0.meituan.net/touch/css/i/fonts/cate4.woff?v7) format("woff"), url(http://ms0.meituan.net/touch/css/i/fonts/cate4.otf?v7); }
    @font-face {
        font-family: 'base_icon';
        src: url(http://ms0.meituan.net/touch/css/i/fonts/base14.woff) format("woff"), url(http://ms0.meituan.net/touch/css/i/fonts/base14.otf); }

</style>
<style>

    /*agreement*/
    .agreement li {
        display: inline-block;
        width: 50%;
        margin-bottom: .22rem;
        box-sizing: border-box;
        color: #666;
    }

    /*tips deal-terms*/
    #deal-terms .dd-padding{
        padding-top: 0;
        padding-bottom: 0;
    }
    #deal-terms .tip-title{
        font-size: 0.3rem;
        color: #ff9900;
    }
    #deal-terms .tip-des{
        padding-top: 0.2rem;
        padding-left: 0.16rem;
        font-size: 0.3rem;
        color: #333333;
    }
    #deal-terms .ul{
        padding-left: 0.16rem;
    }

  
</style>
<style type="text/css">body {
  padding: 0;
  margin: 0;
}


.navbar {
	height: 1.01rem;
	color: #fff;
	background: #06c1ae;
	border-bottom: 1px solid #21897d;
	display: -webkit-box;
	display: -ms-flexbox;
	position: relative
}

.navbar .nav-wrap-left {
	height: 1.01rem;
	line-height: 1.01rem
}

.navbar .nav-wrap-right {
	height: 100%
}

.navbar .box-search {
	-webkit-box-flex: 1;
	-ms-flex: 1;
	border-radius: .06rem;
	background: rgba(0,0,0,.15);
	height: .64rem;
	line-height: .64rem;
	-webkit-box-sizing: border-box;
	position: relative;
	margin-top: .2rem
}

.navbar h1.nav-header,.navbar .h1.nav-header {
	display: block;
	-webkit-box-flex: 1;
	-ms-flex: 1;
	font-size: .36rem;
	font-weight: 400;
	text-align: center;
	line-height: 1rem;
	margin: 0;
	text-overflow: ellipsis;
	white-space: nowrap;
	overflow: hidden
}

.navbar .icon-search {
	position: absolute;
	left: .2rem;
	font-size: .26rem;
	color: #fff
}
.nav-wrap-left a.back {
	height: 1rem;
	width: .45rem;
	line-height: 1rem;
	padding: 0 .3rem
}
.navbar .nav-wrap-left {
	height: 1.01rem;
	line-height: 1.01rem
}

dl.list {
	border-top: 1px solid #DDD8CE;
	border-bottom: 1px solid #DDD8CE;
	margin-top: .2rem;
	margin-bottom: 0;
	background-color: #fff
}

dl.list dt,dl.list dd {
	margin: 0;
	border-bottom: 1px solid #DDD8CE;
	overflow: hidden;
	font-size: inherit;
	font-weight: 400;
	position: relative
}

dl.list dt:last-child,dl.list dd:last-of-type {
	border-bottom: 0
}

dl.list .dd-padding,dl.list dt,dl.list dd>.react {
	padding: .28rem .2rem
}

dl.list dd.poi-list-item>.react {
	padding-bottom: .2rem;
	padding-top: .2rem
}

dl.list dt {
	font-size: .34rem;
	padding-bottom: .2rem;
	color: #333
}

dl.list .db {
	height: .8rem;
	line-height: .8rem;
	font-size: .3rem
}

dl.list dd dl {
	margin: 0;
	margin-bottom: -1px;
	padding-left: .2rem;
	border: 0
}

dl.list dd dl>.dd-padding,dl.list dd dl dd>.react,dl.list dd dl>dt {
	padding-left: 0
}

dl.list .db>.react {
	color: #06c1ae;
	padding: 0 .2rem
}

dl.list .posi-right-bottom {
	position: absolute;
	bottom: .26rem;
	right: .2rem
}

dl.list .statusInfo {
	color: #666;
	font-size: .24rem
}

dl.list del {
	text-decoration: none
}

dl.list del:before {
	content: '门市价:'
}

dl.list-in {
	margin: 0;
	border-top: 0
}

dl.list:first-child {
	margin: 0;
	border-top: 0
}

dl.list dd>.input-weak {
	width: 100%;
	display: block
}

dl.list dd>.btn {
	margin-top: -.15rem;
	margin-bottom: -.15rem
}
.csp-wrapper .dd-padding label.btn {
	margin: 0;
	margin-left: .06rem;
	margin-right: .06rem;
	margin-bottom: .2rem;
	min-width: 1.4rem;
	padding: 0 .2rem
}
</style>
<type >
 <title><%=activityConfigBean.getName() %></title>
</head>
<body id="body"  onload="startTime()">

<div class="new-skill-wrap" style=" position: absolute;width:100%;">
<!--秒杀场次切换部分-->
<header class="topfixed" id="topfixed">
	<ul class="tap-list" id ="nav">
	<% int n = 0; for(ActivityInstanceBean bean:returnInstanceList) {
	if(bean.getFrequency() !=ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER && bean.getStart_hour() == hour && nowDate.equals(bean.getStart_day()) ){
	%>
		<li id="<%=bean.getId() %>" style="width: <%=width%>%" class="cur">
		<p calss="start_day"><%=StringUtil.cutDateString(bean.getStart_day(), 2, "-")+"-"+StringUtil.cutDateString(bean.getStart_day(), 3, "-") %></p>
			<p><%=bean.getStart_hour() %>:00</p>
			<p class="seckilling">活动进行中</p>
			<p style="display:none" class="start_hour"><%=bean.getStart_hour() %></p>
			<p style="display:none" class="is_other"><%=bean.getFrequency() %></p>
    		<p style="display:none" class="start_day"><%=bean.getStart_day() %></p>
		</li>
		<%}else if(bean.getFrequency() !=ActivityConfigBean.ACTIVITY_FREQUENCY_OTHER){
		if(n == 0){%>
		<li id="<%=bean.getId() %>" style="width: <%=width%>%" class="cur">
		<%}else{%>
		<li id="<%=bean.getId() %>" style="width: <%=width%>%">
		<%}%>
		<p calss="start_day"><%=StringUtil.cutDateString(bean.getStart_day(), 2, "-")+"-"+StringUtil.cutDateString(bean.getStart_day(), 3, "-") %></p>
			<p><%=bean.getStart_hour() %>:00</p>
			<p class="seckilling">即将开始</p>
			<p style="display:none" class="start_hour"><%=bean.getStart_hour() %></p>
			<p style="display:none" class="is_other"><%=bean.getFrequency() %></p>
    		<p style="display:none" class="start_day"><%=bean.getStart_day() %></p>
		</li>
		<%}else{%>
		<li id="<%=bean.getId() %>" style="width: <%=width%>%;padding-top: 20px;height:45px" class="cur">
		<p></p>
			<p>活动正在火热进行</p>
			<p style="display:none" class="is_other"><%=bean.getFrequency() %></p>
		</li>
		<%} 
		n++;}if(returnInstanceList.size() == 0){%>
		<li style="width: <%=width%>%;padding-top: 20px;height:45px">
		<p></p>
			<p>本轮活动已结束</p>
		</li>
		<%} %>
	</ul>
	
</header>
<div id="seckill-body">
<header class="list-head">
<%if(returnInstanceList.size() > 0){ %>
	<div style="display: none;">
	<span class="seckill-status" id="seckillTile">活动进行中</span>
	<span class="seckill-round-no" id="seckillRoundNo"><%=returnInstanceList.get(0).getStart_hour() %></span>
	<span class="seckill-life-age" id="seckillLifeAge"><%=activityConfigBean.getLife_age() %></span>
	<input type="hidden" id ="instanceid" value="<%=returnInstanceList.get(0).getId() %>"/>
	<input type="hidden" id ="cost_points" value="<%=activityConfigBean.getCost_points() %>"/>
	<input type="hidden" id ="openid" value="<%=user.getOpenid() %>"/>
	<input type="hidden" id ="points" value="<%=user.getPoints() %>"/>
	<input type="hidden" id ="code" value="<%=activityConfigBean.getCode() %>"/>
	<input type="hidden" id ="frequence" value="<%=returnInstanceList.get(0).getFrequency() %>"/>
	<input type="hidden" id ="in_start_day" value="<%=returnInstanceList.get(0).getStart_day() %>"/>
	</div>
<%} else{%>
	<div style="display: none;">
	<span class="seckill-status" id="seckillTile">活动结束</span>
	<span class="seckill-round-no" id="seckillRoundNo">0</span>
	<span class="seckill-life-age" id="seckillLifeAge">0</span>
	</div>
<% }%>
	<span class="time">
		<span class="static-txt-end" id="staticTxtEnd">距开始</span>
		<div id="seckill_time" class="timeText">
		<span class="seckill-time">00</span>
		<span class="time-separator-day"></span>
			<span class="seckill-time">00</span>
			<span class="time-separator"></span>
			<span class="seckill-time">00</span>
			<span class="time-separator"></span>
			<span class="seckill-time">00</span>
		</div>
	</span>
</header>
  <%int i= 0;
  for(ActivityInstanceBean bean:returnInstanceList) {
  if(i == 0){%>
  <div class="skill-hot" > 
  <%}else{%>
  <div class="skill-hot" style="display:none;" > 
<%} 
i++;
%>
   <!--列表切换部分--> 
   <ul class="good-list bdr-b seckilling"> 
 
    <li class="bdr-bom">  
      <div class="skill-pic">
       <div class="img">
        <img src="<%=activityConfigBean.getActivity_img() %>" style="animation: fade 400ms 0s;width:100%" />
       </div>
      </div>
	  <div id="deal-detail"class="automove">

<div id="tips"></div>
<div id="deal" class="deal">
    <div id="feedback_async" style="margin-top: .2rem">

<dl id="deal-terms" class="list" style="opacity: 1;">
    <dt gaevent="imt/deal/terms">活动须知</dt>
    <dd class="dd-padding">
        <ul>
                <li>
                    <div class="tip-title">有效期</div>
                        <div class="tip-des"><%=activityConfigBean.getStart_date() %> 至 <%=activityConfigBean.getEnd_date() %></div>
                </li>
                <li>
                    <div class="tip-title">活动详情</div>
                        <div class="tip-des"><%=activityConfigBean.getDescription() %></div>
                </li>
                <li>
                    <div class="tip-title">参与规则</div>
                        <ul class="ul">
                           <%if(!StringUtil.isNull(activityConfigBean.getRule()) && activityConfigBean.getRule().size() > 0){
                           for(ActivityRule rule:activityConfigBean.getRule()){
                           %>
                           	<li><%=StringUtil.convertNull(rule.getSeq()) %>、<%=StringUtil.convertNull(rule.getDescription()) %></li>
                           <%}} else{%>
                           <li>无规则</li>
                           <%} %>
                        </ul>
                </li>
        </ul>
    </dd>
</dl>

</div>

</div></div>
<%if(bean.getRemindList().size() == 0){ %>
	  <a><div class="skill-count" >立&nbsp即&nbsp参&nbsp与</div></a><%}else{ %>
	  <a><div class="skill-count setRemind" >已设置提醒</div></a>
	  <%} %>
	  <!--<a class="reminded">已有256人设置提醒</a>-->
	  </li>
   </ul> 
   <!--列表切换部分结束--> 
  </div>
    <%} if(returnInstanceList.size() == 0){%>
    <div class="skill-hot" > 
   <!--列表切换部分--> 
   <ul class="good-list bdr-b seckilling"> 
 
    <li class="bdr-bom">  
      <div class="skill-pic">
       <div class="img" style="height:200px">
        <img  style="height:200px" src="page/user/activity/signActivity/images/panda.png" style="animation: fade 400ms 0s;" />
       </div>
      </div>
	  <div id="deal-detail"class="automove">

<div id="tips"></div>
<div id="deal" class="deal">
    <div id="feedback_async" style="margin-top: .2rem">

<dl id="deal-terms" class="list" style="opacity: 1;">
    <dt gaevent="imt/deal/terms">活动须知</dt>
    <dd class="dd-padding">
        <ul>
                <li>
                    <div class="tip-title">有效期</div>
                        <div class="tip-des"><%=activityConfigBean.getStart_date() %> 至 <%=activityConfigBean.getEnd_date() %></div>
                </li>
                <li>
                    <div class="tip-title">活动详情</div>
                        <div class="tip-des"><%=activityConfigBean.getDescription() %></div>
                </li>
                <li>
                    <div class="tip-title">参与规则</div>
                        <ul class="ul">
                           <%if(!StringUtil.isNull(activityConfigBean.getRule()) && activityConfigBean.getRule().size() > 0){
                           for(ActivityRule rule:activityConfigBean.getRule()){
                           %>
                           	<li><%=StringUtil.convertNull(rule.getSeq()) %>、<%=StringUtil.convertNull(rule.getDescription()) %></li>
                           <%}} else{%>
                           <li>无规则</li>
                           <%} %>
                        </ul>
                </li>
        </ul>
    </dd>
</dl>

</div>

</div></div>
	  <a><div class="skill-count" >立即参与</div></a>
	  
	  </li>
   </ul> 
   <!--列表切换部分结束--> 
  </div>
    <%}%>
  
  
</div>

</div>
<div class="show-msg remind">
<div class="fn_css">
	<div class="hp-h3"><img style="float: left;padding-top: 10px;" alt="" src="page/user/activity/signActivity/images/Ok.png">
	<div class="remindmsg">设置提醒成功</div>
	<div class="remindmsg2">将在活动开始前2分钟提醒</div>
	</div>
	<div class="hp-a"><a></a></div>
</div></div>
<!-- <div class="show-msg alert">
<div class="fn_css">
	<div class="hp-h3"><img style="float: left;" alt="" src="page/user/activity/signActivity/images/alert.png">
	<div class="remindmsg">秒杀提醒已取消</div></div>
	<div class="hp-a"><a></a></div>
</div></div> -->
</body></html>