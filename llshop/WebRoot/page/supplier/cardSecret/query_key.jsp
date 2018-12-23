<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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
        background: #ede9da;
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

	<title>开心福利社-卡密兑换</title>
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
<script src="page/supplier/cardSecret/comm/share.js"></script><link rel="stylesheet" href="page/supplier/cardSecret/comm/share_style0_16.css"></head>
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
					<img src="page/supplier/cardSecret/comm/oppor9.jpg" style="width:100%;">
				</div>
			</div>
			<div class="wap_content_wrap">
				<form action="supplier/findCardSecret.do" id="form" class="form-rob" method="POST">
					<div class="mobile_wrap">
						<input type="hidden" name="isgame" id="isgame" value="1"> <!-- 是否启用抢流量游戏 -->
						<input type="hidden" name="gameval" id="gameval" value="1"> <!-- 是否启用摇一摇 -->
						<input type="text" class="phone-number" id="mobile" value="" placeholder="请输入卡密"  name="card_secret_key">
					</div>
					

					<input type="hidden" value="0" id="iscode" placeholder="判断是否显示验证码">
					<button class="btn btn-lll" type="submit" id="submit">卡密兑换</button>
					<button class="btn btn-rob" id="await" type="button" style="display:none;"><img src="page/supplier/cardSecret/comm/loadding.gif" class="auto_img"></button>
		        </form>
		        <ul class="award-list" id="award-list">
					
		        <li class="award-item move">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           137****2224
		                            <span class="count">用户兑换了<span class="span_red">30M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-21 18:04:20</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           189****2712
		                            <span class="count">用户兑换了<span class="span_red">30M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 15:08:04</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           136****3415
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 14:58:59</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           186****9669
		                            <span class="count">用户兑换了<span class="span_red">50M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 17:21:04</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           183****5848
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-23 01:48:04</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           150****3382
		                            <span class="count">用户兑换了<span class="span_red">30M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 12:09:51</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           153****9508
		                            <span class="count">用户兑换了<span class="span_red">30M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 17:14:39</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           136****7272
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 16:27:20</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           185****0030
		                            <span class="count">用户兑换了<span class="span_red">50M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 15:06:48</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           150****7591
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 11:29:22</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           133****5903
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 10:09:06</span>
		                        </div>
		                    </div>
		                </li><li class="award-item">
		                    <div class="image"></div>
		                    <div class="info">
		                        <div class="above">
		                           180****6606
		                            <span class="count">用户兑换了<span class="span_red">500M</span>流量</span>
		                        </div>
		                        <div class="below">
		                            <span class="time">2016-08-22 08:32:58</span>
		                        </div>
		                    </div>
		                </li></ul>
			</div>
			<div class="wap_content_line"></div>
			<!-- 活动规则抽离出来作为公共模块 -->
<div class="rule-panel">
    <div class="title">
        <span class="text"><span style="width: auto;left:50%;background-color: #ede9da;">活动规则</span></span>
    </div>
    <div class="content wap_w300" id="foot_content" style="color:black;">
        <p>1. 活动时间：2016-06-01 10:00至2016-06-30  24:00 </p><p>2. 活动时间内将累计赠送8000G流量，赠完即止。</p><p>3. 每个用户最高获得500M，且仅限参与一次。</p><p>4. 号码处于欠费停机等异常状态的均不能参与活动，请确保手机号码处于正常状态，否则运营商可能会充值失败。</p><p>5. 抢到的流量将在24小时内充值到手机号码中，请留意短信通知。当月有效，全国可用，且优先使用该 赠送流量。</p><p>6. 本次活动移动、联通、电信全国用户均可参与。若用户出现兑换异常，请及时致电客服0551-68182588寻求兑换帮助。</p><p>7. 如果你喜欢本次活动，请在右上角为我们点赞。</p>

       <!--  <script type="text/javascript">
        var aa = ['&lt;p style=&#34;white-space: normal;&#34;&gt;&lt;span style=&#34;color: rgb(255, 255, 0);&#34;&gt;&lt;strong&gt;本次活动为仿真体验活动，流量并不会真正到账；&lt;/strong&gt;&lt;/span&gt;&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;&lt;br/&gt;&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;活动规则由商家自行定义,一般包括如下内容:&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;1、活动时间&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;2、活动内容&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;3、参与方式&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;4、活动规则&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;5、奖品设置&lt;/p&gt;&lt;p style=&#34;white-space: normal;&#34;&gt;......&lt;/p&gt;&lt;p&gt;&lt;br/&gt;&lt;/p&gt;'];
        document.getElementById("foot_content").innerHTML = aa[0];
        
        </script>
 -->
      



    </div>
</div>
<!-- 底部 -->
<div class="wap_footer">
    <p class="provider">
    	
            流量活动方案提供商@吉讯
        
        
           <!--  <a class="wap_yellow" href="http://www.baidu.com/">认识吉讯</a> -->
        
    </p>
    <p class="provider_two">
       
        
    </p>
</div>
		</div>
	</div>
	<script src="page/supplier/cardSecret/comm/zepto.min.js"></script>
<script>
	//安卓风格的提醒方式
	/* var Tips = (function(){
	    var panel = $('<div class="tips-popup"></div>').appendTo('body');
	    var delay = 2000;
	    var timer;
	    var show =function(content){
	        clearTimeout(timer);
	        panel.text(content).show();
	        timer = setTimeout(hide,delay);
	    };
	    var hide = function(){
	        panel.hide();
	    };
	    return {
	        show:show
	    }
	})();
	//点赞操作
	$("#like").click(function(){
		var btn = $(this);
		if(btn.hasClass('clicked')){
            Tips.show('您已经赞过了');
            return false;
        }
        $.post('http://yfbhd.liumi.com/wap/hd/like?aid=1441937963620949893',{

        },function(res){
            if(res.ret==200){
                btn.addClass('clicked');
                var count = btn.siblings("p").text()-0;
                btn.siblings("p").text(count+1);
            }else{
                Tips.show(res.errmsg);
            }
        },'json');
	}); */
</script><div class="tips-popup"></div>

<script>

	//按钮一、按钮二
	$(".fl").click(function(){
		addcount(8,$(this).attr("url"));
	});
	$(".fr").click(function(){
		addcount(9,$(this).attr("url"));
	});
	//我也要抢
	$(".btn_me").click(function(){
		addcount(10,$(this).attr("url"));
	});
	//现在去关注
	$(".now_guanzhu").click(function(){
		addcount(6,$(this).attr("url"));
	});
	//狠心拒绝
	$(".henxin_no").click(function(){
		addcount(15,"");
	});

	//分享
	$(".dianji").click(function(){
		addcount(12,'');
	});
	
	//统计
	function addcount(type,url){
		//alert(COST.addcount);
		var parm = {
			"activityid" : COST.activityid,
			"type"       : type,
			"ip"         : ""
		}
		$.ajax({
	        url  : COST.addcount,
	        type : 'POST',
	        data : {'data':parm},
	        dataType: 'json',
	        success: function (data) {
	        	//alert("come on");
	        	//if(data.ret == 200){
	        		if(type == 6 || type == 8 || type == 9 || type == 10){
	        			if(url == "" || url == 0){
	        				location.href = "/wap/hd?aid="+COST.activityid;
	        			}else{
	        				location.href = url;
	        			}	        				
	        		}	        		
	        	//}
	        },
	        error: function(data){
	            //return false;
	        }
		});
	}
</script>

	<a class="hd_ly" href="http://yfbkf.liumi.com/wap/zh/mobilemessage?to=msg&aid=161"></a>
	<script>
		$(function(){
			//console.log(document.documentElement.clientWidth);
			//$(".wap_banner").css("width",document.documentElement.clientWidth);

			var award = $("#award-list");
			var regex = /^1[3|4|5|7|8]\d{9}$/i;
			var mobileInput = $("#mobile");
			var isSend = 0; //未获取验证码

			/* $(".btn-yzm").click(function(){
				var mobile = mobileInput.val();
		        if(mobile == ""){
		            Tips.show('请填写你的手机号码');
		            return false;
		        }
		        if(!regex.test(mobile)){
		            Tips.show('请填写正确的手机号码');
		            return false;
		        }
		        var parm = {
                	"phone" : mobile
            	}
            	if(isSend == 1){ return;}  //如果发送成功 还未在120秒之内 反复点击就retuen
            	$.ajax({
	                type : "POST",
	                data : {'data':parm},
	                url  : COST.vercode,
	                dataType : "json",
	                success:function(data){
	                    if(data.ret == 200){
	                        isSend = 1;
	                        //倒计时的操作
	                        var inow_run = function (i){
	                            //document.getElementById("getYan").innerHTML = i;
	                            $(".btn-yzm").val(i+"秒后重新获取");
	                            $(".btn-yzm").attr("temp",1);
	                            $(".btn-yzm").css("background","#ede9da");
	                            $(".btn-yzm").css("color","#aaaaaa");
	                            $(".btn-yzm").css("border-color","#aaaaaa");
	                        };
	                        var callback = function(){
	                            //倒计时结束了
	                            isSend = 0;  //恢复可点击状态
	                            $(".btn-yzm").val("获取验证码");
	                            $(".btn-yzm").css("background","#ede9da");
	                            $(".btn-yzm").css("color","#ee5500");
	                        };
	                        countDown(60,inow_run,callback);
	                    }else{
	                        isSend = 0;  //恢复可点击状态
	                        var errcode = ['','手机号不能为空','短信发送失败','验证码获取失败'];
	                        Tips.show(errcode[data.errcode]);
	                    }
	                },
	                error:function(data){
	                    isSend = 0;  //恢复可点击状态
	                    var errcode = ['','手机号不能为空','短信发送失败','验证码获取失败'];
	                    Tips.show(errcode[data.errcode]);
	                }
	            });
	            isSend = 1;
			}); */

			/* 点击领流量 */
			var isLocal = 1;//为1的时候可以点击，0不可点击
			$(".btn-lll").click(function(){
				if(isLocal == 0){
					return false;
				}
				isLocal = 0;
				var mobile = mobileInput.val();
		        if(mobile == ""){
		            Tips.show('请填写你的手机号码');
		            setTimeout(function(){
        				isLocal = 1;
        			}, 2000);
		            return false;
		        }
		        if(!regex.test(mobile)){
		            Tips.show('请填写正确的手机号码');
		            setTimeout(function(){
        				isLocal = 1;
        			}, 2000);
		            return false;
		        }
		        if($("#iscode").val() == "1"){
	        		if($("#vercode").val() == ""){
	        			Tips.show("请填写验证码");
	        			setTimeout(function(){
        				isLocal = 1;
        			}, 2000);
	        			return false;
	        		}
		        }
		        var veryid = 2;
		        if(veryid != 1){
		        	if(COST.excode == 0){
		        		Tips.show("您还没有获取参与资格");
		        		setTimeout(function(){
		        			isLocal = 1;
		        		},2000);
		        		return false;
		        	}
		        }
		        if($("#iscode").val() == "1"){
		        	var parms = {
			        	"phone"    : mobile,
			        	"verycode" : $("#vercode").val()
			        }
		        }else{
		        	var parms = {
			        	"phone"    : mobile,
			        	"verycode" : ""
			        }
		        }		        
		        $.ajax({
		        	beforeSend: function () {
	                    $("#submit").css("display","none");
	                    $("#await").css("display","block");
	                },
	                url:'',
	                timeout : 10000,
	                type: 'POST',
	                data: {'data': parms},
	                dataType: 'json',
	                success: function (data) {
	                	//console.log(data);
	                    if(data.ret == 200){
		        			addcount(7,"index");
		        			setTimeout(function(){
		        				isLocal = 1;
		        			}, 2000);

	                        //console.log(data);
	                        // location.href = "draw?aid=1432638610692861114&mobile="+mobile;
	                        //location.href = COST.go_draw+'&inow_phone='+mobile;
	                        var isgame = $("#isgame").val(); //是否启用抢流量游戏
	                        var gameval = $("#gameval").val(); //是否启用摇一摇
	                        if(isgame == "1"){
	                        	if(gameval == "1"){
	                        		$("#submit").css("display","block");
                        			$("#await").css("display","none");
	                        		var form = $("#form").attr('action');
			                        form = form+'&inow_phone='+ mobile;
			                        $("#form").attr('action',form);
			                        $("#sub_form").trigger("click");
			                        /*$("#form").submit();*/			                        
	                        	}else if(gameval == "3"){
	                        		$("#submit").css("display","block");
                        			$("#await").css("display","none");
	                        		var form = $("#form").attr('action');
			                        form = form+'&inow_phone='+ mobile;
			                        $("#form").attr('action',form);
			                        $("#sub_form").trigger("click");
	                        	}		                        	
	                        }else{
                        		var parms = {
		                            "activityid" : COST.activityid,
		                            "data" : {
		                                "phone" : mobile,
		                                "code"  : COST.excode
		                            }
		                        }
		                        //console.log(parms);
		                        $.ajax({
		                            type: 'POST',
		                            url:COST.yao,
		                            data: {'data': parms},
		                            dataType: 'json',
		                            timeout: 30000,
		                            success: function (yaodata) {
		                                //console.log(data);
		                                if (yaodata.ret == 200) {	                                		
			                                $("#submit").css("display","block");
	                        				$("#await").css("display","none");
		                                    location.href = COST.result+"&inow_phone="+mobile+"&extcode="+COST.excode;
		                                }else {  //1 活动已过期 2 手机号格式错误 3 手机号已抽过奖 4 添加失败
		                                	Tips.show(yaodata.errmsg);
		                                	setTimeout(function(){
		                                		location.reload();
		                                	},2000);
		                                    //location.reload();
		                                    $("#submit").css("display","block");
	                        				$("#await").css("display","none");
		                                }
		                            }
		                        });
	                        }								

	                    }else{	                    	
	                    	setTimeout(function(){
		        				isLocal = 1;
		        			}, 2000);                   		
                            $("#submit").css("display","block");
            				$("#await").css("display","none");
	                    	//console.log(data);		                        
	                        if(data.errcode == 2){
	                            //Tips.show("您输入的号码已参与过活动");
	                            $(".mobile_error").find(".mobile_tips").html("该号码已参加活动，请使用其它号码");
	                            $(".mobile_error").find(".btn-div").html('<a href="javascript:void(0)" class="btn btn-popup">知道了</a>');
	                            var of_top = $(".envelope").offset().top+155;
						        var w_left = (document.documentElement.clientWidth-260)/2;
					        	$("#mobile_error .content").css({'top':of_top+'px','left':w_left+'px'}).closest('.popup-panel').show();
	                            //$(".mobile_error").show();
	                        }
	                        if(data.errcode == 3){
	                            //Tips.show(data.errmsg);
	                            var href = "http://mp.weixin.qq.com/s?__biz=MzAwMDE5OTQyNw==&mid=205272724&idx=1&sn=3448a39c54ab021826b682a2f710deb9#rd";
	                            $(".mobile_error").find(".mobile_tips").html(data.errmsg);
	                            $(".mobile_error").find(".btn-div").html('<a href="'+href+'" class="btn btn-popup">查看运营商规定</a>');
	                            var of_top = $(".envelope").offset().top+155;
						        var w_left = (document.documentElement.clientWidth-260)/2;
					        	$("#mobile_error .content").css({'top':of_top+'px','left':w_left+'px'}).closest('.popup-panel').show();
	                            //$(".mobile_error").show();
	                        }else{
	                            $(".mobile_error").find(".mobile_tips").html(data.errmsg);
	                            $(".mobile_error").find(".btn-div").html('<a href="javascript:void(0)" class="btn btn-popup">知道了</a>');
	                            var of_top = $(".envelope").offset().top+155;
						        var w_left = (document.documentElement.clientWidth-260)/2;
					        	$("#mobile_error .content").css({'top':of_top+'px','left':w_left+'px'}).closest('.popup-panel').show();
	                            //$(".mobile_error").show();
	                        }
	                    }
	                },
	                error: function(data){
	                	setTimeout(function(){
	        				isLocal = 1;
	        			}, 2000);
	                    return false;
	                }
            	});
		        
			});

			$('body').on("click",".btn-popup",function(){
		        $(".mobile_error").hide();
		    })
		    $(".colse").click(function(){
		        $(".mobile_error").hide();
		    });

		    //弹出框
		    $('.popup-panel').on('click','.colse, .btn-right',function(){
		        $(this).closest('.popup-panel').hide();
		    });

		    setTimeout(function(){
		        var of_top = $(".envelope").offset().top+155;
		        var w_left = (document.documentElement.clientWidth-260)/2;
		        if($("#strong_sub .content").closest('.popup-panel').size() > 0){
		        	$("#strong_sub .content").css({'top':of_top+'px','left':w_left+'px'}).closest('.popup-panel').show();	
		        }			        
		    },2000);

		    var iNow = 8;
		    var out = setInterval(function(){
		        if(iNow < 1){
		            $('#strong_sub').hide();
		            clearTimeout(out);
		        }
		        $('.outhide').text(iNow--);
		    },1000);

			/*
		    * second    总秒数
		    * run       倒计时操作
		    * callback  倒计时结束回调
		    * */
		    var countDown = function(second, run, callback){
		       var i = second ? second :120;
		       var intervalid;
		       intervalid = setInterval(function(){
		           if (i == 0) {
		               //结束倒计时
		               clearInterval(intervalid);
		               callback();
		               return;
		           }
		           run(i);
		           i--;
		       }, 1000);
		    };

			//轮播
		    var loop = function(){
		        var items = award.find('.award-item');
		        if(items.length>3) {
		            items.eq(0).addClass('move');
		            setTimeout(function () {
		                items.eq(0).appendTo(award).removeClass('move');
		                loop();
		            }, 2000);
		        }
		    };
		    loop();
		});
	</script>


</body></html>