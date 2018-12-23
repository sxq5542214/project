<%@page import="com.yd.business.activity.bean.ActivityUserRelationBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%	String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
List<ActivityUserRelationBean> relationBean = (List<ActivityUserRelationBean>)request.getAttribute("relationBean"); 
%>
<!DOCTYPE html>
<html lang="en">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
	<title>兑奖活动</title>
	<link rel="stylesheet" type="text/css" href="page/user/activity/signActivity/css/default.css">
	<link href="page/user/activity/signActivity/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="page/user/activity/signActivity/css/bootstrap-theme.min.css" rel="stylesheet" type="text/css" />
    <link href="page/user/activity/signActivity/css/site.css" rel="stylesheet" type="text/css" />
   
</head>
<body>
	<div class="htmleaf-container">
		<header class="htmleaf-header bgcolor-10">
			<h1>积分兑奖活动</h1>
			<h6>开奖时间：20160909</h6>
		</header>
		<div class="container" style="padding-top:6px">
			<div class="row">
				<div class="col-md-4">
					<div class="panel panel-default">
						<div class="panel-heading">
							<span class="glyphicon glyphicon-list-alt"></span><b>中奖列表，共10人中奖</b></div>
						<div class="panel-body" style="padding:0px 15px 0px 15px">
							<div class="row"  id="wrapper">
							
								<div class="col-xs-12" id="scroller">
									<ul class="demo1"  id="thelist">
									<%for(ActivityUserRelationBean bean:relationBean){ %>
										<li class="news-item">
											<table cellpadding="2">
											  <tr>
    											<td rowspan="2"><img src="<%=bean.getHead_img() %>" width="30" class="img-circle" /></td>
    											<td style="font-size:12px">恭喜<%=bean.getNick_name() %>   获得：	<a><%=bean.getProduct_name() %></a></td>
  											  </tr>
  											  <tr>
    											<td style="font-size:12px">获奖时间：<%=bean.getCreate_time() %></td>
  											  </tr>
											</table>
										</li>
										<%} %>
									</ul>
								</div>
								
							</div>
						</div>
						<div class="panel-footer" style="padding: 5px 15px;">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="page/user/activity/signActivity/js/jquery.bootstrap.newsbox.min.js"></script>
	
<script type="text/javascript" src="http://www.163css.com/myweb/hihilinxuan/template/hihilinxuan/cssjs/2012/12/ifengtouch/js/jquery.touchSwipe.min.js"></script>
	<script type="text/javascript">
    $(function () {
		var h2 = $(".panel-heading").height();
		var wh = $(window).height();
		var bodyH = wh-h2-160;
		var count = parseInt(bodyH/50);
        $(".demo1").bootstrapNews({
            newsPerPage: count,
            autoplay: true,
			pauseOnHover:true,
			handmove: true,
            direction: 'up',
            newsTickerInterval: 2200,
            onToDo: function () {
                //console.log(this);
            }
        });
    });
    

</script>

</body>
</html>
