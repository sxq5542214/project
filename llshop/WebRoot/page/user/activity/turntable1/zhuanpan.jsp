<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventCodeBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserWechatBean user = (UserWechatBean)request.getAttribute("user");
// String serverUrl = BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url();
List<SupplierEventCodeBean> list = (List<SupplierEventCodeBean>)request.getAttribute("list");
SupplierEventBean supplierEvent = (SupplierEventBean)request.getAttribute("supplierEvent");
ActivityPrize[] prize = new ActivityPrize[]{new ActivityPrize(),new ActivityPrize(),new ActivityPrize(),new ActivityPrize()};
%>
<!DOCTYPE html>
<html >
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>抽奖</title>
    <link rel="stylesheet" href="page/user/activity/turntable1/css/common.css">
    <link rel="stylesheet" href="page/user/activity/turntable1/css/index.css">
    <!-- 移动端适配 -->
    <script>
        var html = document.querySelector('html');
        changeRem();
        window.addEventListener('resize', changeRem);

        function changeRem() {
            var width = html.getBoundingClientRect().width;
            html.style.fontSize = width / 10 + 'px';
        }
    </script>
</head>

<body>
<div id="wrap">
    <div class="header clearfix">
        <p class="rule fl">活动规则</p>
        <a href="page/user/activity/turntable1/personal-center.html" id="myWin">
            <p class="my fr">我的奖品</p>
        </a>
        <div class="title"></div>
    </div>
    <!--轮盘-->
    <div class="rotate">
        <div class="lunpai">
            <ul class="prize running">
                <li>
                 <div>
                    <span></span>
                    <p>8折优惠卷</p>
                  </div>
                </li>
                <li>
                 <div>
                    <span></span>
                    <p>1元现金红包</p>
                  </div>
                </li>
                <li>
                   <div>
                    <span></span>
                    <p>5元现金红包</p>
                    </div>
                </li>
                <li>
                  <div>
                    <span></span>
                    <p>开心果兑换卷</p>
                  </div>  
                </li>
                <li>
                  <div>
                    <span></span>
                    <p>10元代金券</p>
                  </div>
                </li>
                <li>
                   <div>
                    <span></span>
                    <p>谢谢参与</p>
                   </div>
                </li>
            </ul>
        </div>
        <div class="ring"></div>
        <div id="btn"></div>
    </div>
    <div class="border">
        您今日还有 <span id="change"> 1</span> 次抽奖机会
    </div>
    <!--滚动信息-->
    <div class="scroll" >
        <p></p>
        <div  class="sideBox">
           <div class="bd" id="txtMarqueeTop">
            <ul>
                <li>
                    恭喜<span class="start-num">180</span>****<span class="end-num">0182</span>
                    获得<span class="info">50元话费</span>
                </li>
                <li>
                    恭喜<span class="start-num">183</span>****<span class="end-num">1143</span>
                    获得<span class="info">500M流量</span>
                </li>
                <li>
                    恭喜<span class="start-num">173</span>****<span class="end-num">2150</span>
                    获得<span class="info">20元话费</span>
                </li>
                 <li>
                    恭喜<span class="start-num">134</span>****<span class="end-num">1950</span>
                    获得<span class="info">300M流量</span>
                </li>
                <li>
                    恭喜<span class="start-num">177</span>****<span class="end-num">2107</span>
                    获得<span class="info">20元话费</span>
                </li>
                 <li>
                    恭喜<span class="start-num">137</span>****<span class="end-num">1059</span>
                    获得<span class="info">300M流量</span>
                </li>
            </ul>
            </div>
        </div>
    </div>
    <!--游戏规则弹窗-->
    <div id="mask-rule">
        <div class="box-rule">
            <h2>活动规则说明</h2>
            <span id="close-rule"></span>
            <div class="con">
                <div class="text">
                    <p>1.活动时间：2018年10月18日——2018年12月31日。<br/>
2.本次活动为福建某某公司专属特权活动，仅针对目标用户参与。<br/>
3.活动期间，福建某某公司专属客户每天可参与抽奖一次。<br/>
4.本次活动奖品为20元话费、50元话费、300M省内加餐包、500M省内加餐包、1G省内加餐包。<br/>
5.每个用户当月获得话费和流量奖品将于次月到账。</p>
                </div>
            </div>
        </div>
    </div>
    <!--中奖提示-->
    <div id="mask">
        <div class="blin"></div>
        <div class="caidai"></div>
        <div class="winning">
            <p><b>恭喜您</b><br/>抽中了<span id="text1"></span></p>
            <a href="javascript: return false;" target="_self" class="btn">确定</a>
         
        </div>
    </div>
    <!--中奖提示-->
    <div id="mask2">
        <div class="blin"></div>
        <div class="caidai"></div>
        <div class="winning">
            <p><br/><b>谢谢参与</b></p>
            <a href="javascript: return false;" target="_self" class="btn">确定</a>
         
        </div>
    </div>
    
    
</div>

<script src="page/user/activity/turntable1/js/jquery-1.11.3.min.js"></script>
<script src="page/user/activity/turntable1/js/jquery.rotate.js"></script>
<script src="page/user/activity/turntable1/js/h5_game_common.js"></script>
<script src="page/user/activity/turntable1/js/index.js"></script>
<script src="page/user/activity/turntable1/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">jQuery("#txtMarqueeTop").slide({ mainCell:"ul",autoPlay:true,effect:"topMarquee",interTime:50,vis:6  });</script>
</body>
</html>







