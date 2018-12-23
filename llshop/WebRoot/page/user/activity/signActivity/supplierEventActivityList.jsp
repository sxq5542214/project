<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<SupplierEventBean> nowEventList = (List<SupplierEventBean>)request.getAttribute("nowEventList");
List<SupplierEventBean> futureEventList = (List<SupplierEventBean>)request.getAttribute("futureEventList");
List<SupplierEventBean> historyEventList = (List<SupplierEventBean>)request.getAttribute("historyEventList");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
WechatOriginalInfoBean originalinfo = (WechatOriginalInfoBean)request.getAttribute("originalinfo");
%>
<!DOCTYPE html>
<html lang="en">
<head>
	<base href="<%=basePath%>">
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no;" name="viewport" />
    <meta content="yes" name="apple-mobile-web-app-capable"/>
    <meta content="black" name="apple-mobile-web-app-status-bar-style"/>
 
    <link rel='stylesheet' href='page/user/activity/signActivity/css/style.css'>
    <link rel='stylesheet' href='page/user/activity/signActivity/css/swiper.min.css'>  
	  
  	<script type="text/javascript" src="js/jquery.js"></script>
    <script src="page/user/activity/signActivity/js/swiper.min.js"></script>  
    <script src="page/user/activity/signActivity/js/idangerous.swiper.min.js"></script> 
	
    <title>免费福利</title>
</head>
<body>
    <!-- header -->
    <header class="favor-header-bar">
        <ul class="tabs">
            <li class="default" style="width: 35%;"><a href="javascript:void(0);" id="btn1" hidefocus="true">当前热门</a>
           <span>
            <img src="page/user/activity/signActivity/images/hot.png"></span>
            </li>
	
	        <li style="width: 35%;"><a href="javascript:void(0);" id="btn3" hidefocus="true">即将推出</a></li>
        	<li style="width: 30%;"><a href="javascript:void(0);" id="btn2" hidefocus="true">历史活动</a></li>
         
<!--   				<li><a href="javascript:void(0);" id="btn4" hidefocus="true">充值</a></li>
 -->        </ul>
    </header>
   <!-- slideTo tab -->
   <div class="swiper-container favor-list">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
            <%if(nowEventList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(SupplierEventBean bean: nowEventList){
            	String url = bean.getUrl().replaceAll("#server_domain#", originalinfo.getServer_domain());
             	url = url.replaceAll("#server_url#", originalinfo.getServer_url());
             	url = url.replaceAll("#fromUserName#", user.getOpenid());
             %>
                <a href="<%=url%>">
                    <dl>
                        <dt>
                            <img  style="width: 100%;height: 73px;"  src="<%=bean.getImg_url() %>">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%= user.getParentid() == null?  bean.getTitle() : bean.getTitle_attached() %></h2>
                            <p style="white-space: normal;line-height: normal;font-size: 0.87rem;">当前参加人数：<%=bean.getJoinCount() +3 %></p>
                            <small>活动截止到<%=bean.getEnd_time() %></small>
                        </dd>
                    </dl>                    
                </a>
                <%} %>
                 <dl><dd><p></br></p></dd></dl> 
            </div>
            <div class="swiper-slide margin19">
            <%if(futureEventList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(SupplierEventBean bean:futureEventList){ %>
                <a href="javascript:;">
                    <dl>
                        <dt>

                            <img  style="width: 100%;height: 73px;"  src="<%=bean.getImg_url() %>">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%= user.getParentid() == null?  bean.getTitle() : bean.getTitle_attached() %></h2>
                            <p style="white-space: normal;line-height: normal;font-size: 0.87rem;">当前参加人数：<%=bean.getJoinCount() +3 %></p>
                            <small>活动截止日期：<%=bean.getEnd_time() %></small>
                        </dd>
                    </dl>                    
                </a>
                <%} %>
            </div>
            <div class="swiper-slide">
               <%if(historyEventList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(SupplierEventBean bean : historyEventList){ 
            	String url = bean.getUrl().replaceAll("#server_domain#", originalinfo.getServer_domain());
             	url = url.replaceAll("#server_url#", originalinfo.getServer_url());
             	url = url.replaceAll("#fromUserName#", user.getOpenid());
             %>
                <a href="<%=url%>">
                    <dl>
                        <dt>
                            <img  style="width: 100%;height: 73px;"  src="<%=bean.getImg_url() %>">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%= user.getParentid() == null?  bean.getTitle() : bean.getTitle_attached() %></h2>
                            <p style="white-space: normal;line-height: normal;font-size: 0.87rem;">参加总人数：<%=bean.getJoinCount() %></p>
                            <small>活动已结束，结束时间：<%=bean.getEnd_time() %></small>
                        </dd>
                    </dl>                    
                </a>
                <%} %>                               
            </div>
        </div>
   </div>
   <script>
    var mySwiper = new Swiper('.swiper-container',{
        autoHeight: true,
        onSlideChangeStart: function(){
          $(".tabs .default").removeClass('default');
          $(".tabs li").eq(mySwiper.activeIndex).addClass('default');
        }
    });
     $(".tabs li").on('click',function(e){
        e.preventDefault();
        $(".tabs .default").removeClass('default');
        $(this).addClass('default');
        mySwiper.swipeTo($(this).index());
      });
      $(".tabs li").click(function(e){
        e.preventDefault();
      });

    </script>
</body>
</html>

