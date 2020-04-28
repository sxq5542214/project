<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－商品管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						申领管理－<a id="inputs">商品管理</a>
					</div>
				</div>
				<div class="widget-body">
					<div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:14%">
                              商户名称
                            </th>
                            <th style="width:14%">
                              商品名称
                            </th>
                            <th style="width:10%">
                              商品价格
                            </th>
                            <th style="width:10%">
                              商品品牌
                            </th>
                            <th style="width:10%">
                              商品分类
                            </th>
                            <th style="width:14%">
                              商品生效日期
                            </th>
                            <th style="width:14%">
                              商品失效日期
                            </th>
                            <th style="width:10%">商品库存</th>
                          </tr>
                        </thead>
                        <tbody>
                        <%
                        for(int i=0;i<list.size();i++){
                        	SupplierProductBean bean = list.get(i);
                        	%>
                        	<tr class="gradeX <%=i%2==0?"warning":"success"%>">
                          <td>
                            <%=NumberUtil.toString(bean.getSupplier_name()) %>
                          </td>
                            <td>
                              <%=NumberUtil.toString(bean.getProduct_name()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getProduct_price()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getProduct_brand_name()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getProduct_type_name()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getEff_time()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getDff_time()) %>
                            </td>
                            <td><%=NumberUtil.toString(bean.getStore_num()) %></td>
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
	<script src="../../js/Util.js"></script>
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