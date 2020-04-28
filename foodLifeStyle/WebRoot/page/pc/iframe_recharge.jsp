<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/page/pc/";
	CustomerBean user = (CustomerBean) request.getAttribute("user");
	List<SupplierBean> list = (List<SupplierBean>) request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title></title>
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
					<div class="title">充值金额</div>
				</div>
				<div class="widget-body">
					<form class="form-horizontal" action="alipay/createCustomerRechargeUnifiedOrder.do?customer_id=<%=user.getId() %>" style="text-align: center;">
								<div class="form-group">
									<label for="money" class="col-sm-2 control-label">充值金额(元)</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" name="money" id="money" min="0"
											value="0" placeholder="请输入充值金额">
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">我的余额(元)</label>
									<div class="col-sm-10">
										<input type="number" class="form-control" disabled="disabled" min="0" value="<%=user.getBalance()/100d %>" placeholder="请输入充值金额">
									</div>
								</div>
								<div class="form-group">
									<label for="password" class="col-sm-2 control-label">选择充值对象</label>
									<div class="col-sm-10" style="text-align: left;">
										<div class="btn-group" data-toggle="buttons">
					                      <label class="btn btn-success" data-original-title="" title="" onclick="checkType(1,'<%=user.getId()%>')">
					                        <input type="radio" name="options" id="me">给本人充值
					                      </label>
					                      <label class="btn btn-success" data-original-title="" title="" onclick="checkType(2)">
					                        <input type="radio" name="options" id="sup">给商户充值
					                      </label>
					                    </div>
									</div>
								</div>
								<div class="form-group" id="selectSup" hidden>
									<label for="password" class="col-sm-2 control-label">选择商户</label>
									<div class="col-sm-10" style="text-align: left;">
										<div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                              </th>
                            <th style="width:14%">
                              商户名称
                            </th>
                            <th style="width:14%">
                              管理员
                            </th>
                            <th style="width:14%">
                              管理员号码
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        	<%
                        	for(int i=0;i<list.size();i++){
                        	SupplierBean sup = list.get(i);
                        	%>
                        	<tr class="gradeX">
                          <td>
                            <input type="radio" name="radio" class="no-margin" onclick="radioClick('<%=sup.getCustomer_id() %>')" />
                          </td>
                            <td>
                              <%=sup.getName() %>
                            </td>
                            <td>
                              <%=sup.getContacts_name() %>
                            </td>
                            <td >
                              <%=sup.getContacts_phone() %>
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
								<input type="text" name="customer_id" id="customer_id" value="<%=user.getId()%>" hidden>
								<div class="form-actions" style="text-align: center;">
									<button type="submit" onclick="return validate();" class="btn btn-info btn-block" id="balance">充值</button>
								</div>
							</form>
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
	<script src="../../js/Util.js"></script>
	<script type="text/javascript">
		var type = -1;
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
		function checkType(o,id){
			type = o;
			$('#customer_id').attr('value','');
			if(parseInt(o)==1) {
				$('#selectSup').attr('hidden','');
				$('#customer_id').attr('value',id);
			}else $('#selectSup').removeAttr('hidden');
		}
		function radioClick(id){
			$('#customer_id').attr('value',id);
		}
		 //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
      function validate(){
      	var id = $('#customer_id').val();
      	var result = false;
      	if(Util.empty(id)) alert("请选择充值对象！");
      	else result = true;
      	return result;
      }
	</script>

</body>
</html>