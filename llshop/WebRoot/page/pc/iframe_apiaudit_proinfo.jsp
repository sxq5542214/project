<%@page import="com.yd.business.order.bean.PartnerApplyOrderBean"%>
<%@page import="com.yd.business.order.bean.PartnerApplyOrderProductBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<PartnerApplyOrderProductBean> list = (List<PartnerApplyOrderProductBean>) request.getAttribute("list");
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
                      商品审核列表
                    </div>
                  </div>
                  <div class="widget-body">
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                            <th style="width:14%">
                              商品名称
                            </th>
                            <th style="width:14%">
                              审核状态
                            </th>
                            <th style="width:14%">
                              备注
                            </th>
                            <th style="width:14%">
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <%
                        for(int i=0;i<list.size();i++){
                        	PartnerApplyOrderProductBean bean = list.get(i);
                        	%>
                        	<tr class="gradeX <%=i%2==0?"warning":"success"%>">
                            <td>
                              <%=bean.getProduct_name() %>
                            </td>
                            <td id="status">
                              <%=bean.getStatus()==PartnerApplyOrderProductBean.STATUS_W?"未审核":bean.getStatus()==PartnerApplyOrderProductBean.STATUS_N?"审核不通过":"审核通过" %>
                            </td>
                            <td>
                              <input placeholder="请输入备注信息" id="comments" type="text" class="form-control" value="<%=bean.getComments() %>">
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs" onclick="Pro.audit('<%=PartnerApplyOrderProductBean.STATUS_Y %>','<%=bean.getId()%>',this)">
                                    &nbsp;&nbsp;通过&nbsp;&nbsp;
                                  </button>
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs" onclick="Pro.audit('<%=PartnerApplyOrderProductBean.STATUS_N %>','<%=bean.getId()%>',this)">
                                    &nbsp;&nbsp;不通过&nbsp;&nbsp;
                                  </button>
                                </div>
                              </td>
                          </tr>
                        	<%
                        }
                        %>
                        </tbody>
                      </table>
                      <div class="clearfix" style="text-align: right;">
                      	<button type="button" class="btn btn-success" onclick="Pro.commit('<%=PartnerApplyOrderBean.STATUS_YES %>','<%=list!=null&&list.size()>0?list.get(0).getOrderid():-1 %>');">提交审核结果</button>
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
	<script src="js/pc/api.js"></script>
	<script src="../../js/Util.js"></script>
    <!-- Custom JS -->
    <script src="js/menu.js"></script>
    
    <script type="text/javascript">
					//ScrollUp
					$(function() {
						$.scrollUp({
							scrollName : 'scrollUp', // Element ID
							topDistance : '300', // Distance from top before showing element (px)
							topSpeed : 300, // Speed back to top (ms)
							animation : 'fade', // Fade, slide, none
							animationInSpeed : 400, // Animation in speed (ms)
							animationOutSpeed : 400, // Animation out speed (ms)
							scrollText : 'Top', // Text for element
							activeOverlay : false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
						});
					});
				</script>

  </body>
</html>