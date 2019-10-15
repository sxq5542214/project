<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<SupplierProductBean> list = (List<SupplierProductBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－可售商品列表</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="page/pc/img/favicon.ico">

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
					<div class="title">
						商品管理－<a id="inputs">可售商品管理</a>
					</div>
				</div>
				<div class="widget-body">
					
					<a class="btn btn-success" href="admin/supplierProduct/toUpdateSupplierProductPage.do">
						<i class="fa fa-plus-circle"></i> 新增可售商品
					</a>
					<div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th >
                              商户名称
                            </th>
                            <th >
                              商品名称
                            </th>
                            <th >
                              商品售价（分）
                            </th>
                            <th >
                              商品原价（分）
                            </th>
                            <th >
                              折扣（百分比）
                            </th>
                            <th >
                              积分抵扣（分）
                            </th>
                            <th >
                              排序
                            </th>
                            <th >
                              首页标识
                            </th>
                            <th >
                              商品品牌
                            </th>
                            <th >
                              商品分类
                            </th>
                            <th >
                              生效日期
                            </th>
                            <th >
                              失效日期
                            </th>
                            <th >库存</th>
                            <th >操作</th>
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
                              <%=NumberUtil.toString(bean.getProduct_real_price()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getDiscount()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getProduct_offset_points()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getSeq()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getHome_flag()) %>
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
                            <td>
                            	 <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                  	 操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="admin/supplierProduct/toUpdateSupplierProductPage.do?id=<%=bean.getId() %>">编辑</a>
                                    </li>
                                    <%-- <li>
                                      <a onClick="deleteProduct(<%=bean.getId()%>)">删除</a>
                                    </li> --%>
                                  </ul>
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

	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
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
        "bPaginate":true,"iDisplayLength":50,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
    </script>

</body>
</html>