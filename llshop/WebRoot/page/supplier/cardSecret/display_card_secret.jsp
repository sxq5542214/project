<%@page import="com.yd.business.supplier.bean.SupplierCardSecretBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

SupplierCardSecretBean scs = (SupplierCardSecretBean)request.getAttribute("scs");
%>
<!DOCTYPE html>
<html lang="en"><head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta charset="UTF-8">
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
	<title>
		
	</title>
<script src="page/supplier/cardSecret/comm/share.js"></script><link rel="stylesheet" href="page/supplier/cardSecret/comm/share_style0_16.css"></head>
<body>
	<div class="wap_hd2">
		
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
		<div class="wap_hd2_wrap wap_w300" style="padding:0 10px;">
			<div class="gx_wrap mt_25" style="height: 150px;">
				<div class="gx_top" style="height: 122px;">
					<div class="logo-40" style="width: 100%;height: 60px;margin-top: 0px;">
						<span  style="width:190px;height:60px;background-image: url(page/supplier/cardSecret/comm/oppo_logo.png );"></span>
					</div>
						
					<%if(scs == null ){ %>
						<p style="width:100%; margin: 0 auto;text-align: center;">卡密不正确！</p>
					<%}else if(scs.getStatus() == SupplierCardSecretBean.STATUS_USED){ %>
						<p style="width:100%;margin: 0 auto;text-align: center;">此卡密已被使用过了！</p>
					
					<%}else{ %>
						<%-- <p style="width:100%;margin: 0 auto;text-align: center;"><%=scs.getProduct_name() %>，连续<%=scs.getMonth_count() %>个月</p>
					 --%>
					<%} %>
				</div>
				<div class="gx_btm">
					<span>
						<%-- <%if(scs!= null){ %>
							请于失效日期：<%=scs.getDff_time() %> 前使用
						<%} %> --%>
						已生效流量，当月有效，全国可用，且优先使用该赠送流量。
					</span>
				</div>
			</div>
			
			<%if(scs!= null){ %>
			<div class="wap_content_wrap" style="background: none;margin: 0 auto;width: 90%;">
			<form style="width: 100%" action="supplier/useCardSecret.do" id="form" class="form-rob" method="POST" onsubmit="return check();">
					<div class="mobile_wrap">
						<input style="width: 100%" type="number" class="phone-number" id="phone" maxlength="11" placeholder="请输入手机号"  name="phone">
						<input type="hidden" name="secret_key" value="<%=scs.getSecret_key()%>">
						
						<!-- <select name="isNow">
							<option value="true">当月生效</option>
							<option value="false">下月生效</option>
						</select> -->
						
						<table>
							<tr>
								<td>
									<input style="height: 20px;" type="radio" name="isNow" value="true" checked="checked">当月生效&nbsp;&nbsp;&nbsp;&nbsp;
								</td>
								<td>
									<input style="height: 20px;" type="radio" name="isNow" value="false">下月生效
								</td>
								
							</tr>
						</table>
						
					</div>
					
					<button class="btn btn-lll" style="width: 100%" type="submit" >卡密兑换</button>
					<button class="btn btn-rob" id="await" type="button" style="display:none;"><img src="page/supplier/cardSecret/comm/loadding.gif" class="auto_img"></button>
		        </form> 
			</div> 
			<%} %>
			<!-- <a class="btn btn_w290 btn_me" href="javascript:void(0)" url="http://yfbcp.liumi.com/wap/cj/tgfive">马上去仿真体验流米流量活动</a>
			
		    	
		    		<button class="btn btn-lm" type="submit">认识流米</button>
		    		<a class="btn fl" href="javascript:void(0)" url="http://m.liumi.com/">找流量方案商流米合作</a>
		    		<a class="btn fr" href="javascript:void(0)" url="http://mp.weixin.qq.com/s?__biz=MzAwMDE5OTQyNw==&amp;mid=204134102&amp;idx=1&amp;sn=be0573356c3f44f94771d11f08ec7a33#rd">关注流米</a>
		    	 -->
		    
		    
		     
			<!-- 活动规则抽离出来作为公共模块 -->
			
<div class="rule-panel"> 
    <div class="title">
        <span class="text"><span style="width: auto;left: 35%;">活动最终解释权归主办方所有</span></span>
    </div>
    <!--
    <div class="content wap_w300" id="foot_content">
       
          <p>1. 活动时间：2016-06-01 10:00至2016-06-30  24:00 </p><p>2. 活动时间内将累计赠送8000G流量，赠完即止。</p><p>3. 每个用户最高获得500M，且仅限参与一次。</p><p>4. 号码处于欠费停机等异常状态的均不能参与活动，请确保手机号码处于正常状态，否则运营商可能会充值失败。</p><p>5. 抢到的流量将在24小时内充值到手机号码中，请留意短信通知。当月有效，全国可用，且优先使用该 赠送流量。</p><p>6. 本次活动移动、联通、电信全国用户均可参与。若用户出现兑换异常，请及时致电客服0551-68182588寻求兑换帮助。</p><p>7. 如果你喜欢本次活动，请在右上角为我们点赞。</p>
        

     



    </div> -->
</div>
<!-- 底部 -->
<div class="wap_footer">
    <p class="provider">
    	
            流量活动方案提供商@吉讯
        
        
            <!-- <a class="wap_yellow" href="http://www.baidu.com/">认识吉讯</a> -->
        
    </p>
    <p class="provider_two">
       
        
    </p>
</div>
		</div>
		<script src="page/supplier/cardSecret/comm/zepto.min.js"></script>
<script>
	function check(){
		var phone = document.getElementById('phone').value;
		
		if(phone == null || phone.length != 11){
			alert('请正确的输入手机号');
			return false;
		}
		
		return true;
	}
	
	
</script>



	</div><div class="tips-popup"></div>
	

</body></html>