<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

String hour =  "17" ;//request.getParameter("hour");
String min =  "16";//request.getParameter("min");

%>
<!doctype html>
<html>
<head>
    <base href="<%=basePath%>">

<meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=no" />
<meta charset="utf-8">

<link type="text/css" rel="stylesheet" href="css/hongbao.css"/>
<script type="text/javascript" src="js/jquery.js"></script>

<title>抢红包界面</title>
</head>
<body>
<div class="paper">
	<div class="rob">
    	<h3>拼手速，拼运气</h3> 
        <div class="hang">
        	开抢倒计时：
        	
        	<table style="margin:0px auto;" >
        		<tr>
        			<td width="20px"><span class="min" id="m">--</span></td>
        			<td width="20px">分</td>
        			<td width="20px"><span id="second" >--</span></td>
        			<td width="20px">秒</td>
        			<td width="20px"><span id="ms">--</span></td>
        			<td width="40px">毫秒</td>
        		</tr>
        	</table>
        </div>
        <div id="show">
        	<button class="bnt" id="btn" onclick="hongbao()">点击拆红包</button>
        </div>
        <div id="loader" hidden="true" >
        	<img alt="正在查询" src="images/ajax-loader-7.gif"><br>
        	<!-- 正在使劲抢啊抢啊抢，请耐心等待 -->
        </div>
    </div>
</div>
</body>
</html>

<script  type="text/javascript" >
var da = new Date();
var server_hour = <%=hour%>;
var server_min = <%=min%>;

var s = 60 - da.getSeconds();
var m = server_min - da.getMinutes() -1;
var ms = 100;//da.getMilliseconds() ;

clearInterval(settime);
if(da.getHours() < server_hour || (da.getHours() == server_hour && m >= 0)){
	var settime = setInterval(function(){
    showtime();
	},10);
}else{
	showdiv();
}

function showtime(){
    ms = ms-1;
    document.getElementById('m').innerHTML = m;
    document.getElementById('second').innerHTML = s;
    document.getElementById('ms').innerHTML = ms;
    var btn = document.getElementById('btn');
    
    if(m==0 && s==0 && ms==0){
  		showdiv();
  		clearInterval(settime);
    }
    if(ms == 0){
    	s = s-1;
    	ms = 100;
    }
    if(s== -1){
        m = m -1;
        s = 59;
    }
    
}

function showdiv() { 
 document.getElementById("show").style.display ="block";
 document.getElementById("loader").hidden = true;
}
function hidediv() {
 document.getElementById("show").style.display ='none';
 document.getElementById("loader").hidden = false;
}

function hongbao(){
	hidediv();
	$.post('activity/userGrabActivity.do',
		{activity_id:1,user_id:1},
		function(data){
			alert(data);
			showdiv();
		});
	
}


</script>