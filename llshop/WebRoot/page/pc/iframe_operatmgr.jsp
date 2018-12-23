<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <title>万大商城－操作员管理</title>
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

  <body style="background-color:#f7f7f7" onload="Operat.listCustomerAdmin();">
            <!-- Row Start -->
            <div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">
                      操作员管理
                    </div>
                  </div>
                  <div class="widget-body">
                  	<button class="btn btn-danger" onclick="Operat.batDel();">
						<i class="fa fa-minus-circle"></i> 删除
					</button>
					<a class="btn btn-success" href="javascript:window.location.href='../../admin/operatIns.do'">
						<i class="fa fa-plus-circle"></i> 添加
					</a>
					<!-- <button class="btn btn-success">
						<i class="fa fa-search"></i> 查询
					</button> -->
					<button class="btn btn-success" onclick="Operat.batStatus('0');">
						<i class="fa fa-ban"></i> 暂停
					</button>
					<button class="btn btn-success" onclick="Operat.batStatus('1');">
						<i class="fa fa-check-circle-o"></i> 恢复
					</button>
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
	<script src="../../js/Util.js"></script>
    <!-- Custom JS -->
    <script src="js/menu.js"></script>
    <script type="text/javascript" src="js/pc/operat.js"></script>
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

      //Data Tables
      /* $(document).ready(function () {
        $('#data-table').dataTable({
          "sPaginationType": "two_button"
        });
      }); */

    </script>

  </body>
</html>