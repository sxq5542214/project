<%@page import="com.yd.business.report.bean.ReportSimpleBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierTopicBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String pagePath = basePath + "page/pc/" ;
	List<ReportSimpleBean> list = (List<ReportSimpleBean>)request.getAttribute("list");
	
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－自定义报表</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
</head>
<body style="background-color: #f7f7f7">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">自定义报表管理</div>
				</div>
				<div class="widget-body">
					<!-- <a class="btn btn-success" href="javascript:window.location.href='admin/supplierTopic/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增自定义报表
					</a> -->
					<div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                                <input type="checkbox" class="no-margin" />
                              </th>
                              <th style="width:5%">
                              ID
                            </th>
                            <th style="width:14%">
                              报表名称
                            </th>
                            <th style="">
                              报表说明
                            </th>
                            <th style="width:14%" >
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <% for(ReportSimpleBean ab : list){
							 %>
                          <tr class="gradeX warning">
                          <td>
                            <input type="checkbox" id="<%=ab.getId()%>" class="no-margin" />
                          </td>
                            <td>
                              <%=ab.getId() %>
                            </td>
                            <td>
                              <%=ab.getName() %>
                            </td>
                            <td >
                              <%=ab.getRemark() %>
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="admin/report/toSingleReportPage.do?id=<%=ab.getId() %>">查看</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <%} %>
                        </tbody>
                      </table>
                      <div class="clearfix">
                      </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	<script src="js/Util.js"></script>
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
      $(document).ready(function () {
        $('#data-table').dataTable({
        "bPaginate":true,"iDisplayLength":100,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
    </script>

</body>
</html>