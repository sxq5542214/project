<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String openid = (String)request.getAttribute("openid");
%>
<!DOCTYPE html>
<html lang="en"><head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta name="format-detection" content="telephone=no">
<link href="page/supplier/cardSecret/comm/style.css" rel="stylesheet">
<style>
    /* 公共样式配置写在这里 */
    /*页面背景色 start*/
    body{
        background: #ef4741;
    }
    #activity_page_main{
        background: #ef4741;
    }
    #activity_game .activity_midden .shake_div .shake_top{
        background: #ef4741;   
    }
    #activity_game .activity_midden .shake_div .shake_bottom{
        background: #ef4741;   
    }
    #activity_game .activity_bottom .title .text span{
        background-color: #ef4741;   
    }
    #mod_error .hd_Number_title .title_box span{
        background-color: #ef4741;   
    }
    /* 活动规则，跟背景颜色保持一致 */
    
    /*页面背景色 end*/

    /* 提示 */
    body .rule-panel .title .text span{
        background-color:#ef4741;
        color:#770000;
    }
    body .rule-panel .title .text{
        border-top: 1px solid #770000;
    }
    body #activity_page_main #activity_game .activity_bottom .title .text span{
          color:#770000;
    }
    body #activity_page_main #activity_game .activity_bottom .title .wire{
          border-bottom:1px solid #770000;
    }
    body #activity_page_main #activity_game .activity_bottom{
        color: #770000;
    }
    body #mod_error .hd_Number_title .title_box, body #mod_error .hd_Number_title,body #mod_error .yy_error_text{
        color: #770000;
    }
    body #count_djs,body #count_djs .hd_end, body #count_djs .hd_begin{
        color: #770000;
    }
    body .xt_prompt{
        color: #770000;
    }
    .llj_shake_phone,.llj_yy_phone{
        color: #770000;
    }
    .phone_line_l, .phone_line_r{
        border-top: 2px dotted #770000;
    }

    /* 倒计时 */
    body #count_djs .hd_countdown{
        color: #ffffff;
    }

    /* 按钮1 */
    body .rslm_wrap .btn.fl{
      
        color: #ffffff;
        border-color: #437ec6;
    }
    /* 按钮2 */
    body .rslm_wrap .btn.fr{
      
        color: #ffffff;
        border-color: #fbb03d;
    }
    /* 我也要抢 */
    body .btn_w290{
       
        color: #ffffff;
        border-color: #437ec6;
    }
    .footer .provider{
        color: #ff8275;
    }
    .footer .provider a{
        color: #ffee33;
    }

    .footer .provider_two{
        color: #ff8275;
    }
    .footer .provider_two a{
        color: #ffee33;
    }    
    .wap_footer .provider{
        color: #ff8275;
    }
    .wap_footer .provider a{
        color: #ffee33;
    }

    .wap_footer .provider_two{
        color: #ff8275;
    }
    .wap_footer .provider_two a{
        color: #ffee33;
    }

</style>

	<title>摇一摇抢流量红包</title>
	<style type="text/css">
		.rule-panel{
			min-width: 100%;
		}
		.rule-panel .title{
			width: 98%;
		}
		.rule-panel .title .text span{
			left: 47%;
			width: 6%;
			padding: 0px;
		}
		.wap_w300{
			min-width: 98%;
		}
		.rule-panel .content{
			margin-left: 2%;
		}
		.rule-panel .title .text{
			width: 100%;
		}
		@media screen and (max-width:959px){
		    .rule-panel .title .text span{
				left: 47%;
				width: 6%;
			} 
		}
		@media screen and (max-width:767px){
		    .rule-panel .title .text span{
				left: 46%;
				width: 8%;
			} 
		}
		@media screen and (max-width:639px){
		    .rule-panel .title .text span{
				left: 45%;
				width: 10%;
			} 
		}
		@media screen and (max-width:479px){
		    .rule-panel .title .text span{
				left: 42%;
				width: 16%;
			} 
		}
		.wap_banner{
			width: 100%;
			height: 100%;
		}
		.wap_banner_wrap{
			width: 100%;
			height: 100%;
		}
	</style>
	<link rel="stylesheet" href="page/supplier/cardSecret/comm/share_style0_16.css">
	<!-- <script src="page/supplier/cardSecret/comm/share.js"></script> -->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/user/activity/shakeActivity.js"></script>
</head>
<body>

	<!--弹出层 提示错误 -->
	<div class="popup-panel mobile_error" id="mobile_error" style="display:none;">
	    <div class="content">
	        <span class="colse"></span>
	        <div class="tips mobile_tips">参与抢流量活动，请先关注活动公众号。</div>
	        <div class="btn-div"></div>
	    </div>
	</div>

	<div class="wap_hd2">
		
<div id="wx_pic" style="margin:0 auto;display:none;">
<img src="page/supplier/cardSecret/comm/0076a37434d490662093b93edf253f5e.png" id="cover"></div>
		<script src="page/supplier/cardSecret/comm/zepto.min.js"></script>
<style type="text/css">
	
	.bdsharebuttonbox_s{cursor: pointer;}
    .bdsharebuttonbox{width:240px; padding:0 40px; border:0; height: 250px; position: relative; top: 50%; margin: -125px auto 0;}
    .bdsharebuttonbox a { width: 60px!important; height: 60px!important; margin: 0 auto 10px!important; float: none!important; padding: 0!important; display: block; }
    .bdsharebuttonbox a img { width: 60px; height: 60px; }
    .bdsharebuttonbox .bds_tsina { background: url(/static/wap/share_custom/img/share_tsina.png) no-repeat center center/60px 60px; }
    .bdsharebuttonbox .bds_qzone { background: url(/static/wap/share_custom/img/share_qzone.png) no-repeat center center/60px 60px; }
    .bdsharebuttonbox .bds_tqq { background: url(/static/wap/share_custom/img/share_tqq.png) no-repeat center center/60px 60px; }
    .bdsharebuttonbox .bds_weixin { background: url(/static/wap/share_custom/img/share_weixin.png) no-repeat center center/60px 60px; }
    .bdsharebuttonbox .bds_sqq { background: url(/static/wap/share_custom/img/share_sqq.png) no-repeat center center/60px 60px; }
    .bdsharebuttonbox .bds_copy { background: url(/static/wap/share_custom/img/share_tu6.png) no-repeat center center/60px 60px; }
    .bd_weixin_popup .bd_weixin_popup_foot { position: relative; top: -12px; }

    .gb_resLay{background:rgba(0,0,0,0.8); position: absolute; width: 100%; height: 100%; z-index:999; visibility:hidden; margin-top:-34px;}
    .gb_resItms{margin:0; width:100%; overflow:hidden;}
    .gb_resItms li{ margin-right: 30px; width:60px; float: left; text-align: center; padding-bottom: 22px; font-size: 12px; color:#fff;}
    .gb_resItms li a{width:60px; height:60px; background: #333;}
    .gb_resItms .gb_li3,.gb_resItms .gb_li6{margin-right: 0;}
    .close_x{width:18px; height:18px; background: url(/static/wap/share_custom/img/close_x.png) no-repeat center center/18px 18px; margin:10px auto 0; cursor: pointer;}

    .copy-tips{position:fixed;z-index:9999;bottom:50%;left:50%;margin:0 0 -20px -80px;background-color:rgba(0, 0, 0, 0.2);filter:progid:DXImageTransform.Microsoft.Gradient(startColorstr=#30000000, endColorstr=#30000000);padding:6px;}
    .copy-tips-wrap{padding:10px 20px;text-align:center;border:1px solid #F4D9A6;background-color:#FFFDEE;font-size:14px;}

</style>
		<div class="wap_hd2_wrap wap_index">
			<div class="wap_banner_wrap envelope">
				<div class="wap_banner">
					<img src="page/user/activity/comm/shake.jpg" style="width:100%;">
				</div>
			</div>
			<!-- <div class="wap_content_wrap">
				<form action="supplier/findCardSecret.do" id="form" class="form-rob" method="POST">
					<div class="mobile_wrap">
						<input type="hidden" name="isgame" id="isgame" value="1"> 是否启用抢流量游戏
						<input type="hidden" name="gameval" id="gameval" value="1"> 是否启用摇一摇
						<input type="text" class="phone-number" id="phone" value="" placeholder="请输入手机号后进行摇一摇"  name="phone">
					</div>
					

					<input type="hidden" value="0" id="iscode" placeholder="判断是否显示验证码">
					<div style="height: 40px;"></div>
					<button class="btn btn-rob" id="await" type="button" style="display:none;"><img src="page/supplier/cardSecret/comm/loadding.gif" class="auto_img"></button>
		        </form>
		        
			</div> -->
			<div class="wap_content_line"></div>
			<!-- 活动规则抽离出来作为公共模块 -->
<div class="rule-panel">
    <div class="title">
        <span class="text"><span>活动规则</span></span>
    </div>
    <div class="content wap_w300" id="foot_content">
         <p>1. 活动截止时间：2016-07-31 24:00（赠完为止） </p>
        <p>2. 活动时间内将累计赠送500000G流量，赠完即止。</p>
        <p>3. 每个用户最高获得1G，且仅限参与一次。</p>
        <p>4. 号码处于欠费停机等异常状态的均不能参与活动，请确保手机号码处于正常状态，否则运营商可能会充值失败。</p>
        <p>5. 抢到的流量将在1分钟内充值到手机号码中，请留意微信通知，流量当月有效。</p>
        <p>6. 本次活动仅限安徽移动、安徽电信用户参与。若用户出现兑换异常，请及时给客服留言寻求兑换帮助。</p>


      



    </div>
</div>
<input type="hidden" name="openid" id="openid" value="<%=openid %>">

<!-- 底部 -->
<div class="wap_footer">
    <p class="provider">
    	
            流量活动方案提供商@吉讯科技
        
        
           <!--  <a class="wap_yellow" href="http://www.baidu.com/">认识吉讯</a> -->
        
    </p>
    <p class="provider_two">
       <audio id="musicBox" style="display:none;height:0px;" preload="auto" controls="controls" src="page/user/activity/comm/shake.mp3"></audio>
        
    </p>
</div>
		</div>
	</div>
	<!-- <script src="page/supplier/cardSecret/comm/zepto.min.js"></script> -->
<div class="tips-popup"></div>

 <script> 
 
	    //播放声音
 var audioMedia = document.getElementById("musicBox");
 var speed = 30; //定义摇一摇加速度的临界值 值越小摇动的力度越小
var x = y = z = lastX = lastY = lastZ = 0; //初始化x,y,z上加速度的默认值
        var isHaveShaked = false;//用于记录是否在动画执行中
        function init() { 
       //判断系统是否支持html5摇一摇的相关属性
            if (window.DeviceMotionEvent) {
                window.addEventListener('devicemotion', deviceMotionHandler, false);
            } else {
                alert('您的浏览器不支持摇一摇功能！');
            }
        }
function deviceMotionHandler() {

    /*获取x,y,z方向的即时加速度*/
    var acceleration = event.accelerationIncludingGravity;
    x = acceleration.x;
    y = acceleration.y;
    z = acceleration.z;
    if (Math.abs(x - lastX) > speed || Math.abs(y - lastY) > speed
                || Math.abs(z - lastZ) > speed) { 
                
        //播放声音
        audioMedia.play();
                
                //摇一摇实际场景就是加速度的瞬间暴增爆减
    if (!isHaveShaked) {
//                    alert(x);   //自己测试各坐标的值。。
//                    alert(y)
//                    alert(z);
	   
	   setTimeout(function () {
	    	 //手机震动1秒
		    if (navigator.vibrate) {
		        navigator.vibrate(1000);//震动多少毫秒
		    } else if (navigator.webkitVibrate) {
		        navigator.webkitVibrate(1000);
		    } 
	      }, 1000);
	   
	   
	    //模拟网络请求做想干的事
	    isHaveShaked = true;
	    setTimeout(function () {
	    	
	        isHaveShaked = false;
	        //业务验证
			handlerBusiness();
			
	        }, 2000);
	    }
	}
    /*保存历史加速度*/
    lastX = x;
    lastY = y;
    lastZ = z;
}	
	function handlerBusiness(){
	 
		joinShakeActivity();
		
		
		//移除摇一摇事件
		window.removeEventListener('devicemotion', deviceMotionHandler);
	}
	init();
</script>
</body></html>