<%@page import="com.yd.business.user.bean.UserInfoCenterPageBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserInfoCenterPageBean userInfoPage = (UserInfoCenterPageBean)request.getAttribute("userInfoPage");
UserWechatBean user = userInfoPage.getUserWechat();
Boolean isShop = (Boolean) request.getAttribute("isShop");
if(isShop == null)isShop = false;
int level1 = userInfoPage.getLevelOneFriendCount();
int level2 = userInfoPage.getLevelTwoFriendCount();
int level3 = userInfoPage.getLevelThreeFriendCount();
int level4 = userInfoPage.getLevelFourFriendCount();

%>
<html><head>
	<title>个人中心</title>
	<base href="<%=basePath%>">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, maximum-scale=1.0"/>
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="black" name="apple-mobile-web-app-status-bar-style" />
    <meta content="telephone=no" name="format-detection" />
    <link href="./page/user/infoCenterCss/comm.css" rel="stylesheet" type="text/css">
    
    <link href="./page/user/infoCenterCss/member.css" rel="stylesheet" type="text/css">
    <link href="./page/user/infoCenterCss/common.css" rel="stylesheet" type="text/css">
  	<script type="text/javascript" src="js/jquery.js"></script>
  	<script type="text/javascript" src="js/user/infoCenter.js" ></script>
	</head>

	<body style="zoom: 1;">
		
 <div class="h5-1yyg-v11">
    

    <section class="clearfix g-member">
    	<input type="hidden" id="userId" value="17855">
    	<input type="hidden" id="openid" value="<%=user.getOpenid() %>">
	    <div class="clearfix m-round m-name">
	    	<div class="fl f-Himg" style="text-align: center;margin: 0 auto;">
	    		<a href="#" onclick="javascript:return false;" class="z-Himg"  > 
	    			<img src="<%=user.getHead_img() %>" border="0"></a>
		  <span class="z-class-icon01 gray02"><!-- ID:17855 --></span>
		 <!--
           	<span class="z-class-icon05"><s></s>全民好运少校</span>
		    -->
	    </div>
		
	    <div class="m-name-info"><p class="u-name"><b class="z-name gray01"><%=user.getNick_name() %></b><em></em></p>
	    	<ul class="clearfix u-mbr-info">
	    <li style="width: 60%;">积分账户:￥<span class="orange"><%=user.getPoints() /100d %></span>元</li>
	    
	    
	    <li style="width: 40%">好友数量：<span class="orange"><%=user.getOffline_num() %></span></li>
<!--	    
	   	<li>经验值 <span class="orange">602550</span></li>
-->
	    
	    <li>现金账户 :￥<span class="orange" id="balance"><%=user.getBalance()/100d %></span>元  
	    	<a href="#" onclick="return getBalanceCash();" class="fr z-Recharge-btn">现金账户提现</a></li>
	    </ul>
	    </div>
	    </div>
	  

	  
<!--   修改之后
	    <div class="m-name-info"><p class="u-name"><b class="z-name gray01">ice_冰块</b><a style="position:relative;
left:30px " href="javascript:;()" id="xiugainicheng" onclick="xiugai();">修改昵称</a>></p>
	
<p class="u-name">
	   &nbsp&nbsp&nbsp余额 <span class="orange">￥939789.0</span><a href="/user/UserRecharge.html" class="fr z-Recharge-btn" style="height:25px;line-height:25px">去充值</a>
	   </p>
	   </div></div>

 -->


	    <div class="m-round m-member-nav">
		    <ul id="ulFun">
		    	<li><a href="user/toUserSignPage.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>今日签到</a></li>
		    	<%if(isShop) {%>
		    	<li><a href="user/toUserShopOrderListPage.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>订单列表</a></li>
			    <%}else{ %>
			    <li><a href="user/queryOrderProductLog.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>充值记录</a></li>
			    <li><a href="user/queryEffOwnerOrderLog.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>预约订单</a></li>
			    <%} %>
			    <li><a href="user/queryCommissionPoint.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>积分明细</a></li>
          		<li><a href="user/queryBalanceLog.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>现金明细</a></li>
         		<li><a href="/login/ssoToIndexPage.html?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>一元购流量</a></li>
        	    <li><a href="supplier/supplierCouponController/queryMycouponPage.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>我的优惠卷</a></li>       	    
          	    <li><a href="supplier/supplierCouponController/receiveCouponPage.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>领取优惠卷</a></li>       	    
          		
 	<!--
          <li><a href="user/queryFriendLevelOne.do?openid=<%=user.getOpenid()%>"><b class="z-arrow"></b>一级好友，共<%=user.getOffline_num() %>人，有<%=level1 %>人购买过<span style="float: right;"></span></a></li>

			    <li><a href="#" onclick="return false;"><b ></b>二级好友<span style="float: right;"><%=level2 %>位好友</span></a></li>
			    <li><a href="#" onclick="return false;"><b ></b>三级好友<span style="float: right;"><%=level3 %>位好友</span></a></li>
			    <li><a href="#" onclick="return false;"><b ></b>四级好友<span style="float: right;"><%=level4 %>位好友</span></a></li>
		-->	    
		    </ul>
	    </div>
    </section>
    
    
    
    <!-- <a style="bottom: 90px;z-index: 99;position: fixed;" href="http://www.51xxf.cc/help/guize.shtml">	
		<img style="width: 40px" src="./page/user/infoCenterCss/mini_guize.png" alt="中奖规则">
		</a> -->
</div>
<div style="width: 95%;margin: 0 auto;">
	<span style="font-size: 14px; color: red;"><strong>积分能用来做什么？</strong><br></span>
	<span style="font-size: 14px;">积分就是现金！譬如8折特惠时，当您购买50元的商品时，只需要支付40元现金，剩余部分用积分付款10元就可以啦！</span>
	<p><br></p>
	<span style="font-size: 14px; color: red;"><strong>怎么使用积分抵扣现金？</strong><br></span>
	<span style="font-size: 14px;">和在淘宝上购物一样，在您支付的时候选择使用积分抵扣就可以啦，非常简单！</span>
  <p><br></p>
	
	<span style="font-size: 14px; color: red;"><strong>积分使用有没有什么限制？</strong><br></span>
	<span style="font-size: 14px;">促销活动不同，对积分使用限制不同，满足一定条件，还可以全额用积分兑换商品哟！</span><br>
	<p><br></p>
	
	
	<span style="font-size: 14px; color: red;"><strong>如何获取积分？</strong></span><br>
	<span style="font-size: 14px;">1、每次购买商品后返利 5% 积分</span></span><br>
	<span style="font-size: 14px;">2、参加签到等活动获得积分</span></span><br>
	<p><br></p>
	
</div>
<!-- <footer class="next">
	<ul>
    	<li class="w20"><a href="http://www.51xxf.cc/index.html"><i class="fa fa-home fa-lg"></i><br>首页</a></li>
    	<li class="w20"><a href="http://www.51xxf.cc/list/index.html"><i class="fa fa-suitcase fa-lg"></i><br>全部商品</a></li>
    	<li class="w20"><a href="http://www.51xxf.cc/list/newIndex369.html"><i class="fa fa-leaf fa-lg"></i><br>最新揭晓</a></li>
    	<li class="w20"><a href="http://www.51xxf.cc/mycart/index.html"><i class="fa fa-cart-plus fa-lg"></i><br>购物车</a></li>
    	<li class="w20 noline"><a href="javascript:void(0);" class="red"><i class="fa fa-user fa-lg"></i><br>我</a></li>
    </ul>
</footer> -->

   <!--底部-->
 


</body></html>