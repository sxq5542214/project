<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	CustomerBean user = (CustomerBean)request.getAttribute("user");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>万大商城－折扣规则管理</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <meta name="description" content="Blue Moon - Responsive Admin Dashboard" />
    <meta name="keywords" content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
    <meta name="author" content="Bootstrap Gallery" />
    <link rel="shortcut icon" href="img/favicon.ico">
    
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="css/new.css" rel="stylesheet">
    <!-- Important. For Theming change primary-color variable in main.css  -->

    <link href="fonts/font-awesome.min.css" rel="stylesheet">
  </head>

  <body style="background-color:#f7f7f7" onload="Dis.listDisGroup();">
            <!-- Row Start -->
            <div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">
                      折扣规则管理
                    </div>
                  </div>
                  <div class="widget-body">
                  	<%if(user.getType()==CustomerBean.TYPE_ADM){ %>
                  	<button class="btn btn-danger" onclick="Dis.batchDeleteDis();"><i class="fa fa-minus-circle"></i> 删除</button>
                  	<button class="btn btn-success" onclick="open_insert();"><i class="fa fa-plus-circle"></i> 新增</button>
                  	<% }%>
                    <div id="dt_example" class="example_alt_pagination">
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Row End -->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
	<script src="js/pc/discount.js"></script>
	<script src="../../js/Util.js"></script>
    <!-- Custom JS -->
    <script src="js/menu.js"></script>
    
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
      <%if(user.getType()==CustomerBean.TYPE_ADM){ %>
      function open_insert(){
    	  parent.document.getElementById("iframe-content").contentWindow.location.href = '../../admin/discountIns.do';
      }
      <%}%>
    </script>

  </body>
</html>