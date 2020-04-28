<%@page import="com.yd.business.user.bean.UserQrCodeBean"%>
<%@page import="com.yd.business.activity.bean.ActivityOlympicGuessBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	
	UserWechatBean user = (UserWechatBean)request.getAttribute("user");
	UserQrCodeBean ticket = (UserQrCodeBean) request.getAttribute("ticket");
	List<ActivityOlympicGuessBean> list = (List<ActivityOlympicGuessBean>)request.getAttribute("list");
	
	Integer joinedNum = list.size();
	Integer winNum = (Integer)request.getAttribute("winNum");
	int day = DateUtil.getNowDayOfMonth();
	boolean showQRCode = (Boolean)request.getAttribute("showQRCode");
	int user_shared = user.getShare_time() == null? ActivityOlympicGuessBean.SHARED_NO:ActivityOlympicGuessBean.SHARED_YES;
	boolean hasUser = (Boolean)request.getAttribute("hasUser");
	//如果没有用户，参加数和中奖数自己定义
	if(!hasUser){
		joinedNum = 62965;
		winNum = 1527;
	}
	
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>奥运活动！最高12G！猜奖牌赢免费流量！</title>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width" />
<meta name="viewport" content="initial-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet" type="text/css" href="page/user/activity/olympicActivity/css/style5.css">
<link type="text/css"
	href="page/user/activity/olympicActivity/css/css.css" rel="stylesheet" />
<link href="page/user/activity/olympicActivity/css/lanrenzhijia.css"
	type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="page/user/activity/olympicActivity/css/alert.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="page/user/activity/olympicActivity/js/olympicActivity.js"></script>
<script type="text/javascript" src="js/wechat/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
<script>
	function setTab(name, cursel) {
		cursel_0 = cursel;
		for ( var i = 1; i <= links_len; i++) {
			var menu = document.getElementById(name + i);
			var menudiv = document.getElementById("con_" + name + "_" + i);
			if (i == cursel) {
				menu.className = "off";
				menudiv.style.display = "block";
			} else {
				menu.className = "";
				menudiv.style.display = "none";
			}
		}
	}
	function Next() {
		cursel_0++;
		if (cursel_0 > links_len)
			cursel_0 = 1;
		setTab(name_0, cursel_0);
	}
 	var name_0 = 'one';
	var cursel_0 = 1;
	var ScrollTime = 300000;//循环周期，可任意更改（毫秒）
	var links_len, iIntervalId;
	onload = function() {
		var links = document.getElementById("tab1").getElementsByTagName('li');
		links_len = links.length;
		for ( var i = 0; i < links_len; i++) {
			links[i].onmouseover = function() {
				clearInterval(iIntervalId);
				this.onmouseout = function() {
			//		iIntervalId = setInterval(Next, ScrollTime);
				};
			};
		}
		/* document.getElementById("con_" + name_0 + "_" + links_len).parentNode.onmouseover = function() {
			clearInterval(iIntervalId);
			this.onmouseout = function() {
		//		iIntervalId = setInterval(Next, ScrollTime);
				;
			};
		}; */
	//	setTab(name_0, cursel_0);
		// iIntervalId = setInterval(Next, ScrollTime);
	};
</script>
<script type="text/javascript">
	
	$(document).ready(function($) {
		var share_time_ms = weixinInit.getShare_time_ms();
		setTimeout(' $("#body_div").show(); ',2000);
		
		weixinInit.setShareLink('<%=basePath%>wechat/user/toOlympicActivity.do?fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
		weixinInit.setShareTitle('猜奖牌赢免费1G流量！最高12G！为中国奥运加油！');
		weixinInit.setShareImg('<%= user == null? basePath+"images/icon/4g.png":user.getHead_img() %>');
		weixinInit.setShareDesc('猜奖牌赢免费1G流量！最高12G！为中国奥运加油！');
		
	  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
		weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
	});
</script>
<title>最高12G！猜奖牌赢免费流量！</title>
</head>
<body style="background:#2b9e73;">
	<div id="body_div" style="display: none;">
	<img src="page/user/activity/olympicActivity/img/olympic_top.png" style="width:100%;height: auto;"
		height="100%" class="index_bj" />
		
		<%if(!hasUser || ticket != null){ %>
		<div class="hintl" style="display: block;height: auto;">
			<div class="hintl-in1">
				<div class="hintl1"></div>
			</div>
			<table class="hintl-in2" style="margin-top: 5px;line-height: 60px;">
				<tr>
					<td>请长按下方二维码参加竞猜活动，赢取最高12G流量</td>
				</tr>
				<tr>
					<td>
						<% if(showQRCode){  
							if(ticket == null){
						 %>
						 	<img style="width: 50%;margin: 0 auto;" alt="" src="<%=user.getHead_img() %>">
						 
						 <%}else{ %>
						<img style="width: 50%;margin: 0 auto;"  alt="" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>">
					<%}}else{ %>
						<input id="guess_num" type="number" placeholder="请输入明天、后天2天中国队可能夺得的奖牌数之和" class="innnn" onkeyup="this.value=this.value.replace(/\D/g,'');" maxlength="3">
					<%} %>
					</td>
				</tr>
			</table>
			<div class="hintl-in3" onclick="alert('请长按上方二维码参加竞猜活动，赢取最高12G流量');">
					<img src="page/user/activity/olympicActivity/img/42.png" width="200" />
			</div>
			
		</div>
		
		
		
		<%}else{ %>
		
	<div style="color:white; clear:both;padding-top: 10px;width: 80%;text-align: center;margin: 5px auto;">
		猜中1次即可赢得30M免费流量或5元流量红包！<br>连续猜中5次可赢得1G免费流量！<br>连续猜中10次可赢得12G免费流量！
	</div>
	<div class="kanjia">
		<div class="touxiang" style="margin-top: 0px;">
			<img src="<%=user.getHead_img() %>" width="80px;"  style="margin: 0 auto;"/>
		</div>
		<div class="touxiang_text">
			<p><%=user.getNick_name() %></p>
			<p>竞猜次数：<%=joinedNum %>次</p>
			<p>中奖次数：<%=winNum %>次</p>
		</div>
	</div>
	<div style="clear:both;"></div>
	
	
	<div ><span style="font-size: 22px;">奥运会进程</span></div>
	<div class="mainFlow clearfix">
               <div class="flowBar">
                   <div class="flowBgMain">
                       <div class="flowBgSon">
                      	 
                       </div>
                      <ul class="pointS">
                           <li <%=day==6?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==7?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==8?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==9?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==10?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==11?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==12?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==13?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                       </ul>
                   </div>
               </div>
               <div>
	               <ul class="day">
	                   <!-- <li class="current">1天</li> -->
	                   <li style="color: black;">5枚<br>06日</li>
	                   <li  style="color: black;">3枚<br>07日</li>
	                   <li style="color: black;">5枚<br>08日</li>
	                   <li style="color: black;">4枚<br>09日</li>
	                   <li style="color: black;">6枚<br>10日</li>
	                   <li style="color: black;">7枚<br>11日</li>
	                   <li style="color: black;">7枚<br>12日</li>
	                   <li style="color: black;">4枚<br>13日</li>
	               </ul>
               </div>
               <div class="flowBar">
                   <div class="flowBgMain">
                       <div class="flowBgSon">
                     	
                       </div>
                      <ul class="pointS">
                           <li <%=day==14?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==15?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==16?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==17?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==18?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==19?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==20?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                           <li <%=day==21?" class=\"current\"":"" %>><span><img src="page/user/activity/olympicActivity/img/cartoon.png" alt=""></span></li>
                       </ul>
                   </div>
               </div>
               <ul class="day">
                   <!-- <li class="current">1天</li> -->
                   <li style="color: black;">4枚<br>14日</li>
                   <li style="color: black;">1枚<br>15日</li>
                   <li style="color: black;">4枚<br>16日</li>
                   <li style="color: black;">4枚<br>17日</li>
                   <li style="color: black;">3枚<br>18日</li>
                   <li style="color: black;">5枚<br>19日</li>
                   <li style="color: black;">5枚<br>20日</li>
                   <li style="color: black;">3枚<br>21日</li>
               </ul>
           </div>   
	
	<div class="hintl" style="display: block;height: auto;">
			<div class="hintl-in1">
				<div class="hintl1"></div>
			</div>
			<table class="hintl-in2" style="margin-top: 5px;line-height: 60px;">
				<tr>
					<td>猜中国明天后天夺得奖牌总数之和(活动结束)</td>
				</tr>
				<tr>
					<td>
						<% if(showQRCode){  
							if(ticket == null){
						 %>
						 	<img style="width: 50%;margin: 0 auto;" alt="" src="<%=user.getHead_img() %>">
						 
						 <%}else{ %>
						<img style="width: 50%;margin: 0 auto;"  alt="" src="https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=<%=ticket.getTicket() %>">
					<%}}else{ %>
<!-- 						<input id="guess_num" type="number" placeholder="请输入明天、后天中国队预估夺得牌数总数之和" class="innnn" onkeyup="this.value=this.value.replace(/\D/g,'');" maxlength="3">
 -->					
 							<input id="guess_num" type="number" placeholder="活动已经结束啦，谢谢大家参与" class="innnn" onkeyup="this.value=this.value.replace(/\D/g,'');" maxlength="3" disabled="disabled" readonly="readonly">
 					<%} %>
					</td>
				</tr>
			</table>
			<div class="hintl-in3" onclick="joinOlympicGuessActivity()">
					<img src="page/user/activity/olympicActivity/img/42.png" width="200" />
			</div>
			
		</div>
		
		
		
	
	<!-- <div class="indexanniu">
		<div class="anniu_left">
			<img src="page/user/activity/olympicActivity/img/12.png" width="100%" />
		</div>
		<div class="anniu_right">
			<img src="page/user/activity/olympicActivity/img/11.png" width="100%" />
		</div>
		<div class="hint">
			<div class="hint-in1">
				<div class="hint3"></div>
			</div>
			<div class="hint-in2">必须先关注安荣虫子才能帮TA砍</div>
			<div class="hint-in3">
				<img src="page/user/activity/olympicActivity/img/7.png" width="200" />
			</div>
		</div>

		<div class="hintl">
			<div class="hintl-in1">
				<div class="hintl1"></div>
				<div class="hint3"></div>
			</div>
			<table class="hintl-in2">
				<tr>
					<td>姓名：</td>
					<td><input type="text" placeholder="输入您真实姓名" class="innnn">
					</td>
				</tr>
				<tr>
					<td>手机：</td>
					<td><input type="text" placeholder="输入您手机号码" class="innnn">
					</td>
				</tr>
			</table>
			<a href="page/user/activity/olympicActivity/chenggong.html"><div
					class="hintl-in3">
					<img src="page/user/activity/olympicActivity/img/9.png" width="200" />
				</div>
			</a>
		</div>
	</div>
	 -->
	
	<div style="clear:both;"></div>
	<div class="indexmain" style="height: auto;margin-bottom: 10px;margin-top: 10px;">
		<h4></h4>
		<div class="tab1" id="tab1" style="padding-top: 5px;">
			<div class="menu">
				<ul>
					<li id="one1" onClick="setTab('one',1)">我的竞猜</li>
					<li id="one2" class="off" onClick="setTab('one',2)">活动规则</li>
				</ul>
			</div>
			<div class="menudiv">
				<div id="con_one_1" style="display:none;">
					<ul class="haoyou">
						<% for(int i = list.size() -1 ; i >= 0 ;i--) {
							ActivityOlympicGuessBean bean = list.get(i);
							String button = "";
							if(bean.getStatus() == ActivityOlympicGuessBean.STATUS_WAIT){
								button = "<a style=\"color:red;\" href=\"activity/user/dealOlympicActivityResult.do?openid="+user.getOpenid()+"&guess_id="+bean.getId()+"\">领奖点这里</a>";
							}
						%>
						<li><strong></strong><span style="color: black;">明天+后天中国队将会获得<%=bean.getGuess_num() %>枚奖牌</span><br>
						<span ><%=button %> &nbsp;&nbsp;&nbsp;&nbsp; <span style="color: black;"><%=bean.getGuess_date() %></span>  </span> 
						</li>
						<div style="clear:both;line-height: 1px;padding: 5px;"></div>
						<%} %>
						
					</ul>
				</div>
				<div id="con_one_2" style="display:block;">
				<p style="text-indent:0;">
						1、您在今天（T日）预估中国代表团明天（T+1日）和后天（T+2日）的奖牌总数之和<br>
						2、我们将在大后天（T+3日）对您提交的竞猜结果进行比对开奖，中国代表团每日所获奖牌总数以央视新闻播报数据为准 <br>
						3、每个用户每天都可以竞猜一次 <br>
						4、本活动仅限安徽移动、安徽电信用户参加 <br>
						</p>
				</div>
			</div>
		</div>
	</div>
	<input type="hidden" id="openid" value="<%=user.getOpenid() %>">
	<input type="hidden" id="shared" value="false">
	<input type="hidden" id="showQRCode" value="<%=showQRCode%>">
	<input type="hidden" id="user_shared" value="<%=user_shared %>">
	</div>
	<%} %>
</body>
</html>
