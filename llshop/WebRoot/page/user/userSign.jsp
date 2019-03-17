<%@page import="com.yd.business.user.bean.UserSenceLog"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserSignBean"%>
<%@page import="com.yd.business.activity.bean.ActivityUserRelationBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String openid = (String)request.getAttribute("openid");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
Object sign = request.getAttribute("sign");
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
	    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
	    <title>签到领积分</title>
		<base href="<%=basePath%>">
		<script type="text/javascript" src="js/jquery.js"></script>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
		 <link href="page/comment/css/buju.css" rel="stylesheet">
 <link href="page/comment/css/index.css" rel="stylesheet">
 
 	<link href="page/comment/css/photoswipe.css" rel="stylesheet" />
	<link href="page/comment/css/default-skin.css" rel="stylesheet" />
	<script src="js/photoswipe.min.js"></script>
	<script src="js/photoswipe-ui-default.min.js"></script>
	<script src="page/comment/js/initPhotoSwipeFromDOM.js"></script>
 <script type="text/javascript" src="js/wechat-upload.js"> </script>
 <script type="text/javascript" src="page/comment/js/comment.js"> </script>
 
	    <style type="text/css">
	    a:link {
 text-decoration: none;
}
a:visited {
 text-decoration: none;
}
a:hover {
 text-decoration: none;
}
a:active {
 text-decoration: none;
}
			*{
	    		padding: 0;
	    		margin: 0;
	    		list-style: none;
	    	}
	    	body{
	    		background: #01aded;
	    		position: relative;
	    	}
			.banner1{
	    		display: block;
	    		width: 100%;
	    		height: auto;
	    		overflow: hidden;
	    	}
			.ggl{
	    		position: relative;
	    		width: 85.6%;
	    		height: 90px;
	    		margin: 2px auto;
	    		background: url(page/user/activity/scratch/img/ggl.png) no-repeat center center;
	    		background-size: 100% 100%;
	    	}
	    	.canvas{
				position:absolute;
				top:2px;
				left:2.5%;
				width:95%;
				height:82px;
				line-height:82px;
				text-align:center;
				z-index: 2;
				
			}
			.info{
				position:absolute;
				top:2px;
				left:2.5%;
				width:95%;
				height:82px;
				text-align:center;
			}
			.info span{
				display: block;
				font-size: 18px;
			}
			#prompt{
				line-height: 40px;
			}
			.btn{
				position: relative;
				width: 50%;
				height: 35px;
				line-height: 35px;
				background: #df412b;
				color: #fff;
				border-radius: 5px;
				margin: 0 auto;
				z-index: 1;
			}
			.guize{
				display: block;
				width: 85.6%;
				height: auto;
				margin: 5% auto 10% auto;
				border-radius: 5px;
			}
			.num{
				width: 100%;
				height: 30px;
				line-height: 30px;
				text-align: center;
				font-size:14px;
				margin-top: 5%;
			}
			#ok, #no{display: none;}
			.pop{
				position: fixed;
			 	left: 0;
			 	top: 0;
			 	z-index: 3;
			 	width: 100%;
			 	height: 100%;
			 	background: rgba(0,0,0,0.6);
			 	display: none;
			}
			.pop img{
				width: 100%;
				height: auto;
				overflow: hidden;
				margin: 15% auto;
			}
			.tfrom-mask{position:absolute;left:0;top:0;z-index:10;background:black;opacity:0.4;filter:alpha(opacity=40);display:none;}
			.progressbar{display:none;z-index:11;position:absolute;left:30%;}
			.todaysignbtn{color: white;width: 100%;padding: 20px;text-decoration:none;}
			
	    </style>
	</head>
	<body>
		<div style="position: absolute;" onclick="javascript:history.go(-1);">
			<img style="width: 1.05rem; margin-top: 8px; margin-left: 8px;" src="page/shop/order/images/c_back_btn.png">
		</div>
		<img src="page/user/activity/scratch/img/sign_top.jpg" class="banner1"/>
		<div  class = "progressbar" ><img src='page/user/infoCenterCss/img/loding2.gif'></div>
		<div class="ggl" style="margin: 0 auto;width: 95%;height: 120px;background: white;border-radius: 5px;word-wrap: break-word;">
			<div style="float: left;width: 20%;height:90%;text-align: center;margin: 0 auto;padding: 5px;align-items: center;display: -webkit-flex;" >
				<img style="width: 100%;border-radius: 10px;" alt="" src="<%=user.getHead_img()%>">
			</div>
			
			<div style="float: left;width: 40%;height:90%;text-align: center;margin: 0 auto;padding: 5px;align-items: center;display: -webkit-flex;" >
				<div style="width: 100%;word-break: normal;">
					<span style="font-size: 20px;"><strong><%=user.getNick_name() %></strong></span> <br><%-- 
					<span>本月签到<span style="color: red;"><%=sign.getSign_count() %></span>天 </span> <br> --%>
					<span id="pointsout"><span id="points">积分<span style="color: red;"><%=user.getPoints()/100d %></span>元</span></span> <br>
					
				</div>
			</div>
			<div style="float: right;width:30%;height:90%;text-align: center;margin: 0 auto;padding: 5px;align-items: center;display: -webkit-flex;" >
				<div>
					<!-- <div style="width: 100%;height:30px; margin: 10px 5px 10px 5px;border-radius: 5%;background-color: #d92b44;align-items: center;display: -webkit-flex;">
						<span style="color: white;width: 100%;padding: 10px;" onclick="alert('即将开放，敬请期待');"><strong>今日排行</strong></span>
					</div> -->
					<div style="width: 100%;height:30px; margin: 10px 5px 10px 5px;border-radius: 5%;background-color: #d92b44;align-items: center;display: -webkit-flex;">
						<a href="activity/user/getSignActivityList.do?openid=<%=openid %>"><span style="color: white;width: 100%;padding: 10px;"><strong>积分兑换</strong></span></a>
					</div>
				</div>
			</div>
		</div>
		
		<div style="height: 40px;width: 90%; align-items: center;display: -webkit-flex;text-align: center;margin: 5px auto;">
			
			
				<div style="width: 40%;height:90%; margin: 0 auto;border-radius: 5%;background-color: #d92b44;align-items: center;display: -webkit-flex;">
				<span id="todaysign" class="todaysignbtn" onclick="showScratchDiv();"><strong>今日签到</strong></span>
				</div>
			
			<div style="width: 40%;height:90%; margin: 0 auto;border-radius: 5%;background-color: #d92b44;align-items: center;display: -webkit-flex;">
				<span style="color: white;width: 100%;padding: 10px;" onclick="alert('即将开放，敬请期待');"><strong>补签</strong></span>
			</div>
		</div>
		
		<div class="ggl" id="top" style="display: none;">
			<div class="info" id="prize">
				<span id="prompt"></span>
				<span class="btn" id="ok">查看积分</span>
				<span class="btn" id="no">再来一次</span>
			</div>
			<canvas id="c1" class="canvas"></canvas>
		</div>
		<!-- <div class="num">
			您还有<span class="num1"></span>次刮卡机会
		</div> -->
		
<!-- 		<img src="page/user/activity/scratch/img/guize_empty.jpg" class="guize" />
 -->	
 		
<div style="width: 80%;margin: 0 auto;font-family: 微软雅黑;" >
	<span style="font-size: 16px; color: white;"><br>签到规则<br></span>
	<span  id="mid" style="font-size: 16px;">一、每位用户每天都可签到一次！<br></span>
	<span style="font-size: 16px;">二、签到积分每天随机赠送！<br></span>
	<span style="font-size: 16px;">三、每月签到多次可参加更多优惠活动！<br></span>
	<p><br></p>
	
</div>
		
		<!-- 遮罩层1抽奖次数已经用完-->
		<div class="pop pop1">
			<img src="page/user/activity/scratch/img/pop1.png" />
		</div>
		<div class="pop pop2">
			<img src="page/user/activity/scratch/img/pop2.png" id="pop2" />
		</div>
		 	<div class="tfrom-mask" ></div>
	<div id="swrap">
	</div>
	<script>
			//控制刮卡次数
			var t = 1; 
			//初始化所有数据并且随机产生奖品
			var initialize  = function () {
				//剩余刮卡次数
//				$('.num1').html(2-t);
				//随机数
				function getRandomNum(lbound, ubound) {
					return (Math.floor(Math.random() * (ubound - lbound)) + lbound);
				}
				var r = getRandomNum(1,100);
				var btn = document.getElementsByClassName("btn");
				for(var i=0; i<btn.length; i++){
					btn[i].style.zIndex = '1';
				}
				document.getElementById("no").style.display = "none";
				document.getElementById("ok").style.display = "none";
				
				//初始化涂抹面积
				isOk = 0;
				
				
//				if(r<t*33){

						var ok =document.getElementById("ok");
//						ok.style.display = "block";
						//点击领取奖品
						ok.onclick = function () {
							window.location.href="wechat/user/toUserInfoCenter.do?openid=<%=openid%>";
						};
//				}else{
//					document.getElementById("prompt").innerHTML="很遗憾，未中奖！"
//					document.getElementById("no").style.display = "block";
//				}
			};
		
			var c1;				//画布
			var ctx;			//画笔
			var ismousedown;	//标志用户是否按下鼠标或开始触摸
			var isOk=0;			//标志用户是否已经刮开了一半以上
			var fontem = parseInt(window.getComputedStyle(document.documentElement, null)["font-size"]);//这是为了不同分辨率上配合@media自动调节刮的宽度

			/* 页面加载后开始初始化画布 */
//			window.onload = function(){	
			function drawCanvas(){
				initialize();
				c1 = document.getElementById("c1");
				
				//这里很关键，canvas自带两个属性width、height,我理解为画布的分辨率，跟style中的width、height意义不同。
				//最好设置成跟画布在页面中的实际大小一样
				//不然canvas中的坐标跟鼠标的坐标无法匹配
				c1.width=c1.clientWidth;
				c1.height=c1.clientHeight;
				ctx = c1.getContext("2d");
				
				//PC端的处理
				c1.addEventListener("mousemove",eventMove,false);
				c1.addEventListener("mousedown",eventDown,false);
				c1.addEventListener("mouseup",eventUp,false);
			
				//移动端的处理
				c1.addEventListener('touchstart', eventDown,false);
		    	c1.addEventListener('touchend', eventUp,false);
		    	c1.addEventListener('touchmove', eventMove,false);
				
				//初始化
				initCanvas();
			};

			//初始化画布，画灰色的矩形铺满
			function initCanvas(){
				//网上的做法是给canvas设置一张背景图片，我这里的做法是直接在canvas下面另外放了个div。
				//c1.style.backgroundImage="url(中奖图片.jpg)";
				ctx.globalCompositeOperation = "source-over";
				ctx.fillStyle = '#aaaaaa';
				ctx.fillRect(0,0,c1.clientWidth,c1.clientHeight);
				ctx.fill();
				
				ctx.font = "Bold 30px Arial";
						ctx.textAlign = "center";
						ctx.fillStyle = "#999999";
						ctx.fillText("今日签到积分",c1.width/2,50);
				
				//把这个属性设为这个就可以做出圆形橡皮擦的效果
				//有些老的手机自带浏览器不支持destination-out,下面的代码中有修复的方法
				ctx.globalCompositeOperation = 'destination-out';
			}

			//鼠标按下 和 触摸开始
			function eventDown(e){
				e.preventDefault();
				ismousedown=true;
			}
			
			//鼠标抬起 和 触摸结束
			function eventUp(e){
				e.preventDefault();
				
				//得到canvas的全部数据
				var a = ctx.getImageData(0,0,c1.width,c1.height);
				var j=0;
				for(var i=3;i<a.data.length;i+=4){
						if(a.data[i]==0)j++;
				}
			
				//当被刮开的区域等于一半时，则可以开始处理结果
				if(j>=a.data.length/8){
					isOk = 1;
				}
				ismousedown=false;
			}
			
			//鼠标移动 和 触摸移动
			function eventMove(e){
				 e.preventDefault();
				if(ismousedown) {
					 if(e.changedTouches){
						 e=e.changedTouches[e.changedTouches.length-1];
					 }
				var topY = document.getElementById("top").offsetTop;
				var oX = c1.offsetLeft,
			    	oY = c1.offsetTop+topY;
				
				var x = (e.clientX + document.body.scrollLeft || e.pageX) - oX || 0,
					y = (e.clientY + document.body.scrollTop || e.pageY) - oY || 0;
			
					//画360度的弧线，就是一个圆，因为设置了ctx.globalCompositeOperation = 'destination-out';
					//画出来是透明的
					 ctx.beginPath();
					 ctx.arc(x, y, fontem*1.2, 0, Math.PI * 2,true);
					 
					 //下面3行代码是为了修复部分手机浏览器不支持destination-out
					 //我也不是很清楚这样做的原理是什么
					 c1.style.display = 'none';
					 c1.offsetHeight;
					 c1.style.display = 'inherit'; 
					 
					 ctx.fill();
				}
				
				if(isOk){
					var btn = document.getElementsByClassName("btn");
					for(var i=0; i<btn.length; i++){
						btn[i].style.zIndex = '3';
					}
					document.getElementsByClassName("btn")[0].style.zIndex="3";
				}
			}
			
			//没有中奖再来一次
			$("#no").click(function() {
				if(t>3){
					//因该弹出遮罩层提示您的次数已经用完了
					$('.pop1').show();
					$('.pop1 img').click(function(){
						$('.pop1').hide();
					});
				}else{
					t++;
					//初始化按钮
					document.getElementById("no").style.display = "none";
					document.getElementById("ok").style.display = "none";
					window.onload();
					initCanvas();
					
				}
			});
			
    var share_time_ms = weixinInit.getShare_time_ms();
	function shareTimeFirendSuccess(){
	  	$.ajax({
			type : "POST",
			url : "user/handleUserShare.do",
			data : {"openid": '<%=user.getOpenid() %>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_LINE %> 
			, share_from:"<%=UserSenceLog.SHARE_FROM_USER_SIGN %>",share_time_ms:share_time_ms },
			success : function(data) {
			}
		});
  	}
  	function shareOneFirendSuccess(){
	  	$.ajax({
			type : "POST",
			url : "user/handleUserShare.do",
			data : {"openid": '<%=user.getOpenid() %>',"share_type":<%=UserSenceLog.SHARE_TYPE_FIREND_ONE %> 
			, share_from:"<%=UserSenceLog.SHARE_FROM_USER_SIGN %>",share_time_ms:share_time_ms },
			success : function(data) {
			}
		});
  	}
    
	weixinInit.setShareLink('<%=BaseContext.getDefault_share_url()%>?fromOpenid=<%=user.getOpenid() %>&share_time_ms='+share_time_ms);
	weixinInit.setShareTitle('<%=BaseContext.getDefault_share_title()%>');
	weixinInit.setShareImg('<%= user == null? basePath+"images/icon/4g.png":user.getHead_img() %>');
	weixinInit.setShareDesc('<%=BaseContext.getDefault_share_title()%>');
	
  	weixinInit.setOnShareAppMessageSuccess(shareOneFirendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimeFirendSuccess);
	
	function showScratchDiv(){
		todaySign();
		$('#top').show();
		drawCanvas();
	}
	

	
	// 处理用户签到 
	function todaySign(){
	$(".progressbar").css("display","block");
	$(".tfrom-mask").css("height",document.body.scrollHeight+"px").css("width",document.body.scrollWidth+"px").css("display","block");
			$.ajax({
			type : "POST",
			url : "user/todaySign.do",
			data : {"openid": '<%=user.getOpenid() %>' },
			success : function(data) {
			var result = eval("(" + data + ")");
					 $(".tfrom-mask").css("display","none");
					 $(".progressbar").css("display","none");
					 $( "#todaysign" ).html("<strong>今日已签</strong>");
					 $( "#todaysign" ).removeAttr("onclick" );
				     $( "#todaysign" ).css("color","#c0c0c0");
					 $("#points").remove();
		   			 $("#pointsout").append("<span>积分<span style='color: red;'>"+result.sence_id/100+"</span>元</span>");		
					 document.getElementById("prompt").innerHTML="恭喜!今天获得了"+result.last_points/100+"元积分！";
	
			}
		});
	}
	
	
$(function (){
 	<%
 	if(!StringUtil.isNull(sign)){
 	%>
 	$( "#todaysign" ).html("<strong>今日已签</strong>");
	$( "#todaysign" ).removeAttr("onclick" );
    $( "#todaysign" ).css("color","#c0c0c0");

   <%} %>
 });
 
//修改原型
CommentCallBack.prototype.toPage = function(data,msg){
	//评论需要关注，参数为“comment_need_user_subscribe”
    if(data == "comment_need_user_subscribe"){
    	alert(msg);
    //点赞需要关注,参数为“comment_vote_need_user_subscribe”
    }else if(data == "comment_vote_need_user_subscribe"){
    	alert(msg);
    }else{
    //此处代码不需要编辑，正常的提示信息
    	alert(data);
    }
};
//修改原型
var p = new CommentPageInit();
//三个tab的名称
p.title_name=["我的留言","精选留言","好友留言"];
//必要的参数传递
p.comment_openid = "<%=user.getOpenid() %>";
p.comment_code = "usersign";
//初始化页面
p.pageInit();
</script>
</body>
</html>