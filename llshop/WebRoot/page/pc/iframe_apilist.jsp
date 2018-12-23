<%@page import="com.yd.business.order.bean.PartnerApplyOrderBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<PartnerApplyOrderBean> list = (List<PartnerApplyOrderBean>) request.getAttribute("list");
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
                      我的申请
                    </div>
                  </div>
                  <div class="widget-body">
                  	<button class="btn btn-success" onclick="javascript:window.location.href='../../admin/proApply.do'"><i class="fa fa-plus-circle"></i> 申请商品</button>
                    <button class="btn btn-danger"><i class="fa fa-minus-circle"></i> 取消申请</button>
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                                <input type="checkbox" class="no-margin" />
                              </th>
                            <th style="width:14%">
                              工单编码
                            </th>
                            <th style="width:14%">
                              工单标题
                            </th>
                            <th style="width:14%">
                              申请时间
                            </th>
                            <th style="width:14%">
                              当前状态
                            </th>
                            <th style="width:14%">
                              申请人名称
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <%
                        for(int i=0;i<list.size();i++){
                        	PartnerApplyOrderBean bean = list.get(i);
                        	%>
                        	<tr class="gradeX <%=i%2==0?"warning":"success"%>">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              <%=bean.getCode() %>
                            </td>
                            <td>
                              <%=bean.getName() %>
                            </td>
                            <td>
                              <%=bean.getCreate_time() %>
                            </td>
                            <td class="hidden-phone">
                              <%=bean.getStatus() %>
                            </td>
                            <td class="hidden-phone">
                              <%=bean.getApply_customer_name() %>
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs">
                                    查看 
                                  </button>
                                </div>
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
        "bPaginate":true,"iDisplayLength":10,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });

    </script>

  </body>
</html>