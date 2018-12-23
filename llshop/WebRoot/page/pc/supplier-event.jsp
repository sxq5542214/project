<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String pagePath = basePath + "page/pc/" ;
	CustomerBean user = (CustomerBean)request.getAttribute("user");
	PageinationData pd = (PageinationData)request.getAttribute("PageinationData");
	List<SupplierEventBean> list = pd.getDataList();
	int totalCount = pd.getTotalcount();
	int totalPage = pd.getTotalpage();
	int nowPage = pd.getNowpage();
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－商户管理</title>
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
					<div class="title">商户营销活动管理</div>
				</div>
				<div class="widget-body">
					<a class="btn btn-success" href="javascript:window.location.href='admin/supplierEvent/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增文章
					</a>
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
                              文章标题
                            </th>
                            <th style="width:8%">
                              外部链接
                            </th>
                            <th style="width:14%">
                              消息主图片链接
                            </th>
                            <th style="width:8%">
                              当前状态
                            </th>
                            <th style="width:14%" >
                              创建时间
                            </th>
                            <th style="width:14%" >
                              修改时间
                            </th>
                            <th style="width:6%" >
                              阅读数
                            </th>
                            <th style="width:14%" >
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <% for(SupplierEventBean ab : list){
							 %>
                          <tr class="gradeX warning">
                          <td>
                            <input type="checkbox" id="<%=ab.getId()%>" class="no-margin" />
                          </td>
                            <td>
                              <%=ab.getId() %>
                            </td>
                            <td>
                              <%=ab.getTitle() %>
                            </td>
                            <td>
                              <a href="<%=ab.getUrl() %>">链接点我</a>
                            </td>
                            <td >
                              <img style="width:50px; height:50px;" src="<%=ab.getImg_url() %>" />
                            </td>
                            <td >
                              <%=ab.getStatus() %>
                            </td>
                            <td >
                              <%=StringUtil.convertNull(ab.getCreate_time()) %>
                            </td>
                            <td >
                              <%=StringUtil.convertNull(ab.getModify_time()) %>
                            </td>
                            <td >
                              <%=ab.getRead_num() %>
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="<%=ab.getUrl() %>">预览</a>
                                    </li>
                                    <li>
                                      <a href="admin/supplierEvent/toUpdatePage.do?id=<%=ab.getId() %>">编辑</a>
                                    </li>
                                    <li>
                                      <a onClick="Art.del(<%=ab.getId()%>)">删除</a>
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
        "bPaginate":true,"iDisplayLength":10,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
    </script>

</body>
</html>