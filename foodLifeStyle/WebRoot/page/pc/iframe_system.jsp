<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
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
                      系统设置
                    </div>
                  </div>
                  <div class="widget-body">
                  	<button class="btn btn-success"><i class="fa fa-plus-circle"></i> 新增</button>
                    <button class="btn btn-danger"><i class="fa fa-minus-circle"></i> 删除</button>
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                                <input type="checkbox" class="no-margin" />
                              </th>
                            <th style="width:14%">
                              Month
                            </th>
                            <th style="width:14%">
                              Internet Explorer
                            </th>
                            <th style="width:14%">
                              Firefox
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              Chrome
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              Safari
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              Opera
                            </th>
                            <th style="width:14%" class="hidden-phone">
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                          <tr class="gradeX warning">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              December
                            </td>
                            <td>
                              14.7 %
                            </td>
                            <td>
                              31.1 %
                            </td>
                            <td class="hidden-phone">
                              46.9 %
                            </td>
                            <td class="hidden-phone">
                              4.2 %
                            </td>
                            <td class="hidden-phone">
                              2.1 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeC">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              November
                            </td>
                            <td>
                              15.1 %
                            </td>
                            <td>
                              31.2 %
                            </td>
                            <td class="hidden-phone">
                              46.3 %
                            </td>
                            <td class="hidden-phone">
                              4.4 %
                            </td>
                            <td class="hidden-phone">
                              2.0 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA success">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              October
                            </td>
                            <td>
                              16.1 %
                            </td>
                            <td>
                              31.8 %
                            </td>
                            <td class="hidden-phone">
                              44.9 %
                            </td>
                            <td class="hidden-phone">
                              4.3 %
                            </td>
                            <td class="hidden-phone">
                              2.0 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA error">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              September
                            </td>
                            <td>
                              16.4 %
                            </td>
                            <td>
                              32.2 %
                            </td>
                            <td class="hidden-phone">
                              44.1 %
                            </td>
                            <td class="hidden-phone">
                              4.2 %
                            </td>
                            <td class="hidden-phone">
                              2.1 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA warning">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              August
                            </td>
                            <td>
                              16.2 %
                            </td>
                            <td>
                              32.8 %
                            </td>
                            <td class="hidden-phone">
                              43.7 %
                            </td>
                            <td class="hidden-phone">
                              4.0 %
                            </td>
                            <td class="hidden-phone">
                              2.2 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA success">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              July
                            </td>
                            <td>
                              16.3 %
                            </td>
                            <td>
                              33.7 %
                            </td>
                            <td class="hidden-phone">
                              42.9 %
                            </td>
                            <td class="hidden-phone">
                              3.9 %
                            </td>
                            <td class="hidden-phone">
                              2.1 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA info">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              June
                            </td>
                            <td>
                              16.7 %
                            </td>
                            <td>
                              34.4 %
                            </td>
                            <td class="hidden-phone">
                              41.7 %
                            </td>
                            <td class="hidden-phone">
                              4.1 %
                            </td>
                            <td class="hidden-phone">
                              2.2 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA success">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              May
                            </td>
                            <td>
                              18.1 %
                            </td>
                            <td>
                              35.2 %
                            </td>
                            <td class="hidden-phone">
                              39.3 %
                            </td>
                            <td class="hidden-phone">
                              4.3 %
                            </td>
                            <td class="hidden-phone">
                              2.2 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA error">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              April
                            </td>
                            <td>
                              18.3 %
                            </td>
                            <td>
                              35.8 %
                            </td>
                            <td class="hidden-phone">
                              38.3 %
                            </td>
                            <td class="hidden-phone">
                              4.5 %
                            </td>
                            <td class="hidden-phone">
                              2.3 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA success">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              March
                            </td>
                            <td>
                              18.9 %
                            </td>
                            <td>
                              36.3 %
                            </td>
                            <td class="hidden-phone">
                              37.3 %
                            </td>
                            <td class="hidden-phone">
                              4.4 %
                            </td>
                            <td class="hidden-phone">
                              2.3 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
                          <tr class="gradeA error">
                          <td>
                            <input type="checkbox" class="no-margin" />
                          </td>
                            <td>
                              February
                            </td>
                            <td>
                              19.5 %
                            </td>
                            <td>
                              36.6 %
                            </td>
                            <td class="hidden-phone">
                              36.3 %
                            </td>
                            <td class="hidden-phone">
                              4.5 %
                            </td>
                            <td class="hidden-phone">
                              2.3 %
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    Action 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="#">Edit</a>
                                    </li>
                                    <li>
                                      <a href="#">Delete</a>
                                    </li>
                                  </ul>
                                </div>
                              </td>
                          </tr>
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