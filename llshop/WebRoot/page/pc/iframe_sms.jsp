<%@page import="com.yd.business.sms.bean.SmsBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<SmsBean> list = (List<SmsBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <title></title>
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

  <body style="background-color:#f7f7f7">


            <!-- Row Start -->
            <div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">
                      短信模板管理
                    </div>
                  </div>
                  <div class="widget-body">
                  	<button class="btn btn-success" onclick="javascript:window.location.href='../../admin/toInsertSmsPage.do'"><i class="fa fa-plus-circle"></i> 新增</button>
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                                <input type="checkbox" class="no-margin" />
                              </th>
                            <th style="width:14%">
                              模板ID
                            </th>
                            <th style="width:14%">
                              模板名称
                            </th>
                            <th style="width:14%">
                              添加时间
                            </th>
                            <th style="width:14%">
                              模板内容
                            </th>
                            <th style="width:14%">
                              当前状态
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        	<%
                        	if(list!=null&&list.size()>0)
                        	for(int i=0;i<list.size();i++){
                        	SmsBean bean = list.get(i);
                        	%>
                        	<tr class="gradeX">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              <%=bean.getTemplate_id() %>
                            </td>
                            <td>
                              <%=bean.getTemplate_name() %>
                            </td>
                            <td>
                              <%=bean.getAdd_time() %>
                            </td>
                            <td>
                              <%=bean.getContent() %>
                            </td>
                            <td>
                              <%=bean.getStatus() %>
                            </td>
                            <td>
                                <button class="btn btn-default btn-xs" onclick="javascript:window.location.href='../../admin/deleteSms.do?id=<%=bean.getId()%>'">
                                    删除 
                                  </button>
                              </td>
                          </tr>
                        	<%
                        	}
                        	 %>
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

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>

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

      //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });

    </script>

  </body>
</html>