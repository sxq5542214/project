<%@page import="com.yd.business.activity.bean.ActivityWinHisBean"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityPrizeRelationBean"%>
<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

UserWechatBean user = (UserWechatBean)request.getAttribute("user");
// String serverUrl = BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url();
List<ActivityPrizeRelationBean> prizeList = (List<ActivityPrizeRelationBean>)request.getAttribute("prizeList");
ActivityConfigBean activity = (ActivityConfigBean)request.getAttribute("activity");
ActivityWinHisBean myWinInfo = (ActivityWinHisBean)request.getAttribute("myWinInfo");
List<ActivityWinHisBean> winHisList = (List<ActivityWinHisBean>)request.getAttribute("winHisList");
String prizeNameStr = "[";

%>
<!DOCTYPE html>
<html >
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=no">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>【美味坚果】和【现金红包】，祝您好运</title>
    <link rel="stylesheet" href="page/user/activity/turntable1/css/common.css">
    <link rel="stylesheet" href="page/user/activity/turntable1/css/index.css">
    
    
<script	src="page/user/supplierEvent/common/jquery-1.10.2-min.js"></script>
<script type="text/javascript" src="<%=request.getScheme()  %>://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
<script type="text/javascript" src="js/wechat/weixinInit.js"></script>
<script src="page/user/activity/turntable1/js/index.js"></script>
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
        <a href="activity/prize/toReceivePrizePage.html?openid=<%=user.getOpenid() %>&activityId=<%=activity.getId() %>" id="myWin">
            <p class="my fr">我的奖品</p>
        </a>
        <div class="title"></div>
    </div>
    <!--轮盘-->
    <div class="rotate">
        <div class="lunpai">
            <ul class="prize running">
            	<% for( ActivityPrizeRelationBean prize : prizeList  ){
            		prizeNameStr = prizeNameStr + "\'" + prize.getPrize_name()+"\',";
            	 %>
                <li>
                 <div>
                    <span></span>
                    <p><%=prize.getPrize_name() %></p>
                  </div>
                </li>
                <%} %>
               
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
    	<%if(myWinInfo == null){ %>
    	        本次活动您还有 <span id="change"> 1</span> 次抽奖机会
    	<%}else{ %>
    	   	您已中奖,点击 <a href="activity/prize/toReceivePrizePage.html?openid=<%=user.getOpenid() %>&activityId=<%=activity.getId() %>" >【我的奖品】</a>领奖吧
    	   	<span id="change" style="display: none;"> 0</span>
    	<%} %>
    </div>
    <!--滚动信息-->
    <div class="scroll" >
        <p></p>
        <div  class="sideBox">
           <div class="bd" id="txtMarqueeTop">
            <ul>
             <!--    <li>
                    恭喜<span class="start-num">180</span>****<span class="end-num">0182</span>
                    获得<span class="info">50元话费</span>
                </li> -->
                <% for(ActivityWinHisBean his : winHisList){    %>
                <li>
                    恭喜<span class="start-num"><%=his.getUser_name() %></span><span class="end-num"></span>
                    获得<span class="info"><%=his.getPrize_name() %></span>
                </li>
                <%} %>
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
                    <p>1.活动时间：2019年12月1日——2019年12月31日。<br/>
2.本次活动为【WE坚果汇】公众号专属特权活动，仅针对目标用户开放参与。<br/>
3.活动周期内，客户均可参与抽奖一次。<br/>
4.本活动奖品全部真实有效。<br/>
5.现金红包将由公众号内直接以<strong>微信红包</strong>方式发放，各类奖品均需要关注公众号后才可领取。</p>
6.现金红包为裂变式现金红包（也可称拼手气红包），用户获奖后可分享给好友共同瓜分。</p>
                </div>
            </div>
        </div>
    </div>
    <!--中奖提示-->
    <div id="mask">
        <div class="blin"></div>
        <div class="caidai"></div>
        <div class="winning">
            <p style="position: relative;"><b>恭喜您</b><br/>抽中了<span id="text1"></span></p><br>
            
            <a href="javascript: return false;" target="_self" class="btn">确定</a>
         
        </div>
    </div>
    <!--未中奖提示-->
    <div id="mask2">
        <div class="blin"></div>
        <div class="caidai"></div>
        <div class="winning">
            <p><br/><b>谢谢参与</b></p>
            <a href="javascript: return false;" target="_self" class="btn">确定</a>
         
        </div>
    </div>
    
    
</div>

<script src="page/user/activity/turntable1/js/jquery.rotate.js"></script>
<script src="page/user/activity/turntable1/js/h5_game_common.js"></script>
<script src="page/user/activity/turntable1/js/jquery.SuperSlide.2.1.1.js"></script>
<script type="text/javascript">
jQuery("#txtMarqueeTop").slide({ mainCell:"ul",autoPlay:true,effect:"topMarquee",interTime:50,vis:3  });

var openid = '<%=user.getOpenid()%>';
var activityId = <%=activity.getId() %> ;
var activityCode = '<%=activity.getCode()%>';

var prizeStr = <%=prizeNameStr + "'谢谢参与~' ]"%> ;
window.prizes = prizeStr;

	weixinInit.setShareTitle("<%="必中！【美味坚果】和【现金红包】免费送，限量1000份，再迟就没有了！" %>");
	weixinInit.setShareDesc("<%="必中！【美味坚果】和【现金红包】免费送，限量1000份，再迟就没有了！" %>");
	weixinInit.setShareLink("<%=BaseContext.getWechatOriginalInfo(user.getOriginalid()).getServer_url() %>activity/user/toTurntable1Activity.html?fromOpenid=<%=user.getOpenid()%>&shareType=2");
	weixinInit.setShareImg("http://m.jg-shop.cn/jgshop/page/user/activity/freeCutActivity/resource/share_img.jpg");
	
</script>
</body>
</html>








