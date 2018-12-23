<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.util.StringUtil"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%><%	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<ActivityConfigBean> futureActivityConfigBeanList = (List<ActivityConfigBean>)request.getAttribute("futureActivityConfigBeanList");
List<ActivityConfigBean> nowActivityConfigBeanList = (List<ActivityConfigBean>)request.getAttribute("nowActivityConfigBeanList");
List<ActivityConfigBean> historyActivityConfigBeanList = (List<ActivityConfigBean>)request.getAttribute("historyActivityConfigBeanList");
UserWechatBean user = (UserWechatBean)request.getAttribute("user");
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
	
    <title>优惠促销</title>
</head>
<body>
    <!-- header -->
    <header class="favor-header-bar">
        <ul class="tabs">
            <li class="default" style="width: 35%;"><a href="javascript:void(0);" id="btn1" hidefocus="true">当前热门</a>
            <%if(nowActivityConfigBeanList.size() > 0){ %><span>
            <img src="page/user/activity/signActivity/images/hot.png"></span><%} %></li>
	
	            <li style="width: 35%;"><a href="javascript:void(0);" id="btn3" hidefocus="true">即将推出</a></li>
         <li style="width: 30%;"><a href="javascript:void(0);" id="btn2" hidefocus="true">历史活动</a></li>
         
<!--   				<li><a href="javascript:void(0);" id="btn4" hidefocus="true">充值</a></li>
 -->        </ul>
    </header>
   <!-- slideTo tab -->
   <div class="swiper-container favor-list">
        <div class="swiper-wrapper">
            <div class="swiper-slide">
            <%if(nowActivityConfigBeanList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(ActivityConfigBean bean:nowActivityConfigBeanList){ %>
                <a href="activity/user/showActivityInfo.do?openid=<%=user.getOpenid() %>&code=<%=bean.getCode()%>">
                    <dl>
                        <dt>
                            <img  style="width: 100%;height: 73px;"  src="<%=StringUtil.convertNull(bean.getShow_list_img()) %>">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%=bean.getName() %></h2>
                            <p style="white-space: normal;line-height: normal;font-size: 0.87rem;"><%=bean.getDescription() %></p>
                            <small>活动截止到<%=bean.getEnd_date() %></small>
                        </dd>
                    </dl>                    
                </a>
                <%} %>
                 <dl><dd><p></br></p></dd></dl> 
            </div>
            <div class="swiper-slide margin19">
            <%if(futureActivityConfigBeanList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(ActivityConfigBean bean:futureActivityConfigBeanList){ %>
                <a href="javascript:;">
                    <dl>
                        <dt>
                            <img  style="width: 100%;height: 73px;"  src="<%=StringUtil.convertNull(bean.getShow_list_img()) %>">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%=bean.getName() %></h2>
                            <p style="white-space: normal;line-height: normal;font-size: 0.77rem;"><%=bean.getDescription() %></p>
                            <small>活动截止到<%=bean.getEnd_date() %></small>
                        </dd>
                    </dl>                    
                </a>
                <%} %>
            </div>
            <div class="swiper-slide">
               <%if(historyActivityConfigBeanList.size() == 0){ %>
            <div class="nofavor">
                    <img src="page/user/activity/signActivity/images/panda.png">
               </div> 
            <%}for(ActivityConfigBean bean:historyActivityConfigBeanList){ %>
                <a href="javascript:;">
                    <dl>
                        <dt>
                            <img src="page/user/activity/signActivity/images/pic3-favor.png">
                            <span></span>
                        </dt>
                        <dd>
                            <h2><%=bean.getName() %></h2>
                            <p style="white-space: normal;"></p>
                            <small>活动截止到<%=bean.getEnd_date() %></small>
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

