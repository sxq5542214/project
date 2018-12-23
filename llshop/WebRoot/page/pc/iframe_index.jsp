<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>万大商城管理平台－首页</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <meta name="description" content="Blue Moon - Responsive Admin Dashboard" />
    <meta name="keywords" content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
    <meta name="author" content="Bootstrap Gallery" />
    <link rel="shortcut icon" href="img/favicon.ico">
    
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="css/new.css" rel="stylesheet"> 
    <link href="css/charts-graphs.css" rel="stylesheet">

    <link href="fonts/font-awesome.min.css" rel="stylesheet">

  </head>

  <body style="background-color:#f7f7f7">
          <div class="row">
            <div class="col-lg-3 col-md-3 col-sm-6">
              <div class="mini-widget">
                <div class="mini-widget-heading clearfix">
                  <div class="pull-left">应用在线人数</div>
                  <div class="pull-right"><i class="fa fa-angle-up"></i> 12.2<sup>%</sup></div>
                </div>
                <div class="mini-widget-body clearfix">
                  <div class="pull-left">
                    <i class="fa fa-globe"></i>
                  </div>
                  <div class="pull-right number">8757</div>
                </div>
<!--                 <div class="mini-widget-footer center-align-text">
                  <span>Better than last week</span>
                </div> -->
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6">
              <div class="mini-widget">
                <div class="mini-widget-heading clearfix">
                  <div class="pull-left">应用管理资源数</div>
                  <div class="pull-right"><i class="fa fa-angle-up"></i> 18.3<sup>%</sup></div>
                </div>
                <div class="mini-widget-body clearfix">
                  <div class="pull-left">
                    <i class="fa fa-twitter"></i>
                  </div>
                  <div class="pull-right number">3780</div>
                </div>
<!--                 <div class="mini-widget-footer center-align-text">
                  <span>Better than last week</span>
                </div> -->
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6">
              <div class="mini-widget">
                <div class="mini-widget-heading clearfix">
                  <div class="pull-left">应用最新版本</div>
                  <div class="pull-right"><i class="fa fa-angle-down"></i> 21.9<sup>%</sup></div>
                </div>
                <div class="mini-widget-body clearfix">
                  <div class="pull-left">
                    <i class="fa fa-upload"></i>
                  </div>
                  <div class="pull-right number">12658</div>
                </div>
<!--                 <div class="mini-widget-footer center-align-text">
                  <span>Better than last week</span>
                </div> -->
              </div>
            </div>
            <div class="col-lg-3 col-md-3 col-sm-6">
              <div class="mini-widget mini-widget-grey">
                <div class="mini-widget-heading clearfix">
                  <div class="pull-left">应用已注册人数</div>
                  <div class="pull-right"><i class="fa fa-angle-up"></i> 67.1<sup>%</sup></div>
                </div>
                <div class="mini-widget-body clearfix">
                  <div class="pull-left">
                    <i class="fa fa-coffee"></i>
                  </div>
                  <div class="pull-right number">1135</div>
                </div>
<!--                 <div class="mini-widget-footer center-align-text">
                  <span>Better than last week</span>
                </div> -->
              </div>
            </div>
          </div>
          <!-- Row ends -->

          <!-- Row Start -->
          <div class="row">
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
              <div class="widget">
                <div class="widget-header">
                  <div class="title">
                    应用当前会话数
                  </div>
                </div>
                <div class="widget-body">
                  <div id="area-chart" class="chart-height-md"></div>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
              <div class="widget">
                <div class="widget-header">
                  <div class="title">
                    终端功能使用比例
                  </div>
                </div>
                <div class="widget-body">
                  <div id="mobVsDesk" class="chart-height-md"></div>
                </div>
              </div>
            </div>
            <div class="col-lg-4 col-md-4 col-sm-4 col-xs-12">
              <div class="widget">
                <div class="widget-header">
                  <div class="title">
                    专业资源管理比例
                  </div>
                </div>
                <div class="widget-body">
                  <div id="mob-desktop" class="chart-height-md"></div>
                </div>
              </div>
            </div>
          </div>
       
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <!-- jQuery UI JS -->
    <script src="js/jquery-ui-v1.10.3.js"></script>

    <!-- Just Gage -->
    <script src="js/justgage/justgage.js"></script>
    <script src="js/justgage/raphael.2.1.0.min.js"></script>

    <!-- Flot Charts -->
    <script src="js/flot/jquery.flot.js"></script>
    <script src="js/flot/jquery.flot.orderBar.min.js"></script>
    <script src="js/flot/jquery.flot.stack.min.js"></script>
    <script src="js/flot/jquery.flot.pie.min.js"></script>
    <script src="js/flot/jquery.flot.tooltip.min.js"></script>
    <script src="js/flot/jquery.flot.resize.min.js"></script>

    <!-- Custom JS -->
    <script src="js/menu.js"></script>
    <script src="js/custom-index2.js"></script>
    
    <script type="text/javascript">
      //ScrollUp
      $(function () {
        $.scrollUp({
          scrollName: 'scrollUp', // Element ID
          topDistance: '300', // Distance from top before showing element (px)
          topSpeed: 300, // Speed back to top (ms)
          animation: 'fade', // Fade, slide, none
          animationInSpeed: 400, // Animation in speed (ms)
          animationOutSpeed: 400, // Animation out speed (ms)
          scrollText: 'Top', // Text for element
          activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
        });
      });
    </script>

  </body>
</html>