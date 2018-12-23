<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.activity.bean.ActivityUserRelationBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	ActivityUserRelationBean relation = (ActivityUserRelationBean)request.getAttribute("relation");
	UserWechatBean user = (UserWechatBean)request.getAttribute("user");
	String title = "1G流量免费送,限时领取！实测有效！拿走不谢！" ;
	String desc = "全民领流量!1G流量免费送！名额有限！先到先得" ; 
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

	<title>1G流量免费送,限时领取！实测有效！拿走不谢！</title>
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
	
	<script src="js/jquery.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
	<script src="page/supplier/cardSecret/comm/share.js"></script>
	<script src="js/user/activity/shakeAddPhone.js"></script>
</head>
 <script type="text/javascript">
  	
	weixinInit.setShareLink('<%=basePath%>wechat/user/liuliang1GActivity.htm?fromOpenid=<%=relation.getOpenid() %>');
	weixinInit.setShareTitle('1G流量免费送,限时领取！实测有效！拿走不谢！');
  	weixinInit.setShareImg('<%= user == null? basePath+"images/icon/headLogo.png":user.getHead_img() %>');
  	weixinInit.setShareDesc('1G流量免费送,限时领取！实测有效！名额有限！先到先得！');
  	
  	weixinInit.setOnShareAppMessageSuccess(shareFriendSuccess);
	weixinInit.setOnShareTimelineSuccess(shareTimelineSuccess);
  </script>
<body>

	<!--弹出层 提示错误 -->
	<div class="popup-panel mobile_error" id="mobile_error" style="display:none;">
	    <div class="content">
	        <span class="colse"></span>
	        <div class="tips mobile_tips">参与免费抢流量活动，请先关注活动公众号。</div>
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
			<div class="gx_wrap mt_25" style="margin: 0">
				<div class="gx_top" style="text-align: center;">
					<!-- <div class="logo-40">
						<span id="test_btn" style="background-image: url( http://yfbwx.liumi.com/uploads/server/2016/04/28/4133797aa2eb16c32cbb6f13eecd7d3c_56.png);"></span>
					</div> -->
					
						<p style="width: 100%;">恭喜您获得了： <%=relation.getProduct_name() %>！</p>
					
				</div>
				<div class="gx_btm">
					<span>
						
						
					</span>
				</div>
			</div>
			<!-- <div class="wap_banner_wrap envelope">
				<div class="wap_banner">
					<p  style="width:100%;"></p>
				</div>
			</div> -->
			<div class="wap_content_wrap">
				<form action="activity/shakeAddPhone.do" id="form" class="form-rob" method="POST">
					<div class="mobile_wrap">
						<input type="number" class="phone-number" id="phone" value="" placeholder="请输入手机号领取"  name="phone">
					</div>
					
					<input type="hidden" name="openid" id="openid" value="<%=relation.getOpenid() %>">
					<input type="hidden" name="relation_id" id="relation_id" value="<%=relation.getId() %>">
					<div style="height: 40px;"></div>
					<div style="text-align: center;display: none;" id="loadimg">
					<img src="page/supplier/cardSecret/comm/loadding.gif"  class="auto_img" >
					</div>
					<button class="btn btn-rob" id="fromButton" type="submit" onclick="return submitFrom()">确认领取</button>
		        </form>
		        
			</div>
			<div class="wap_content_line"></div>
			<!-- 活动规则抽离出来作为公共模块 -->
<div class="rule-panel">
    <div class="title">
        <span class="text"><span>活动规则</span></span>
    </div>
    <div class="content wap_w300" id="foot_content" >
        <p style="color: white;">1. 活动截止时间：2016-08-15 24:00 </p>
        <p style="color: white;">2. 活动限额100000名，赠完即止。</p>
        <p style="color: white;">3. 号码处于欠费停机等异常状态的均不能参与活动，请确保手机号码处于正常状态，否则运营商可能会充值失败。</p>
        <p style="color: white;">4. 获得的流量将在72小时内充值到手机号码中，请留意微信通知，流量当月有效。</p>
        <p style="color: white;">5. 本次活动仅限安徽移动、安徽电信用户参与。若用户出现兑换异常，请及时给客服留言寻求兑换帮助。</p>


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
<div class="tips-popup"></div>

</body></html>