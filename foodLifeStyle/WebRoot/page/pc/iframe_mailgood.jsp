<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.product.bean.ProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	Map<String, List<ProductBean>> list = (Map<String, List<ProductBean>>) request.getAttribute("list");
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
                      可售商品
                    </div>
                  </div>
                  <div class="widget-body" style="text-align: right;">
                  	<%
                  	for(String key : list.keySet()){
                  		List<ProductBean> pList = list.get(key);
                  		%>
                  		<div class="widget">
		                  <div class="widget-header">
		                    <div class="title">
		                      <%=key %>
		                    </div>
		                  </div>
						<div class="widget-body">
							<div class="stylish-lists">
								<div id="dt_example" class="example_alt_pagination">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%; text-align: center;"><input type="checkbox" class="no-margin" onclick="Meal.checkAll(this);"/></th>
												<th style="width: 14%">商品名称</th>
												<th style="width: 14%">商品描述</th>
												<th style="width: 14%">商品价格</th>
												<th style="width: 14%">购买数量</th>
											</tr>
										</thead>
										<tbody>
											<%
											for(int j=0;j<pList.size();j++){
												ProductBean bean = pList.get(j);
												%>
												<tr class="gradeA error">
													<td style="text-align: center;"><input type="checkbox" id="<%=bean.getId() %>" class="no-margin" onchange="Meal.change();"/></td>
													<td id="name"><%=bean.getName() %></td>
													<td><%=NumberUtil.toString(bean.getProduct_desc()) %></td>
													<td><a id="price"><%=bean.getProduct_price()/100 %></a></td>
													<td><input type="number" id="num" min="0" value="0" onchange="Meal.change();" placeholder="请输入购买数量"></td>
												</tr>
												<%
											}
											%>
										</tbody>
									</table>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
                  		<%
                  	}
                  	%>
                  
                    <div style="margin-bottom: 20px;">
                    	<span class="label label-warning">合计金额：¥<a style="font-size: 25px;" id="money">0.0</a></span>
                    </div>
                    <button class="btn btn-success" onclick="Meal.bill();"><i class="fa fa-usd"></i> 结算</button>
                  </div>
                </div>
              </div>
            </div>
            <!-- Row End -->

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
	<script src="js/pc/meal.js"></script>
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