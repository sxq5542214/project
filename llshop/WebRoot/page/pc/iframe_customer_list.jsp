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
	List<CustomerBean> customerList = (List<CustomerBean>)request.getAttribute("customerList");
	
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
					<div class="title">客户管理</div>
				</div>
				<div class="widget-body">
					<!-- <a class="btn btn-success" href="javascript:window.location.href='admin/supplierTopic/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增客户
					</a> -->
					<div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:5%">
                                
                              </th>
                              <th style="width:5%">
                              ID
                            </th>
                            <th style="width:14%">
                              客户名称
                            </th>
                            <th style="width:8%">
                              登录名称
                            </th>
                            <th style="width:14%">
                              当前余额(元为单位)
                            </th>
                            <th style="width:14%">
                              客户状态
                            </th>
                            <th style="width:14%" >
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <% for(CustomerBean cb : customerList){
							 %>
                          <tr class="gradeX warning">
                          <td>
                           
                          </td>
                            <td>
                              <%=cb.getId() %>
                            </td>
                            <td id="name_<%=cb.getId() %>">
                              <%=cb.getName() %>
                            </td>
                            <td>
                              <%=cb.getUsername() %>
                            </td>
                            <td>
                              <span id="balance<%=cb.getId()%>"><%=cb.getBalance()/100d %></span>
                            </td>
                            <td>
                              <span id="status<%=cb.getId()%>"><%=cb.getDictValueByField("status") %></span>
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="javascritp:;" onclick="showAddBalanceModal(<%=cb.getId()%>)">添加余额</a>
                                    </li>
                                    <li>
                                      <a href="javascritp:;" onclick="showUpdateStatusModal(<%=cb.getId()%>)">修改客户状态</a>
                                    </li>
                                    <li>
                                      <a href="admin/channel/toChannelCustomerProductPage.do?customer_id=<%=cb.getId()%>">查看客户通道</a>
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
	
	
	<!-- 模态框（Modal）余额添加 -->
<div class="modal fade" id="myModal-originalList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="position: absolute;margin-top: 50%;width: 90%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">添加余额 <span style="color: red;" id="cust_name"></span> </h4>
            </div>
            <div class="modal-body" >
            	
            	 <div class="form-group" >
                    <label class="col-sm-3 control-label">添加金额(<span style="color: red;">元为单位</span>)</label>
                    <input id="customer_id" type="hidden" >
                    <div class="col-sm-9">
                      <input type="number" class="form-control" id="addBalance" name="addBalance"
						placeholder="元为单位"  value="" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-3 control-label">添加余额说明</label>
                    <input id="customer_id" type="hidden" >
                    <div class="col-sm-9">
                      <input type="text" class="form-control" id="remark" name="remark"
						placeholder="添加余额说明"  value="" >
                    </div>
                  </div>
            	<div class="form-actions">
            				<input type="button" class="btn btn-info pull-right" value="返回"
							data-dismiss="modal" >&nbsp;&nbsp; 
							<input type="button" onclick="addCustomerBalance()" class="btn btn-info pull-right"
							id="save-infor-btn" value="保存" > &nbsp;&nbsp; 
                          </div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	
	<!-- 模态框（Modal）修改状态 -->
<div class="modal fade" id="myModal-customer-status" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="position: absolute;margin-top: 50%;width: 90%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改客户状态： <span style="color: red;" id="status_cust_name"></span> </h4>
            </div>
            <div class="modal-body" >
            	
            	 <div class="form-group" >
                    <label class="col-sm-3 control-label">选择状态:</label>
                    <div class="col-sm-9">
                      <select id="chose_status" class="selector">
                      	<option value="1">可用</option>
                      	<option value="0">不可用</option>
                      </select>
                    </div>
                  </div>
            	<div class="form-actions">
            				<input type="button" class="btn btn-info pull-right" value="返回"
							data-dismiss="modal" >&nbsp;&nbsp; 
							<input type="button" onclick="updateCustomerStatus()" class="btn btn-info pull-right"
							id="save-infor-btn" value="保存" > &nbsp;&nbsp; 
                          </div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
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
        "bPaginate":true,"iDisplayLength":50,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
      
      function showAddBalanceModal(id){
      	
      	
      	var cust_name = $("#name_"+id).html();
      	$("#cust_name").html(cust_name);
      	
      	
      	$("#customer_id").val(id);
      	$("#addBalance").val("");
      	$("#myModal-originalList").modal("show");
      };
      
      
      function showUpdateStatusModal(id){
      	
      	
      	var cust_name = $("#name_"+id).html();
      	$("#status_cust_name").html(cust_name);
      	
      	$("#customer_id").val(id);
      	$("#myModal-customer-status").modal("show");
      };
      
      function updateCustomerStatus(){
      	
	    var customer_id = $("#customer_id").val();
	    var status = $("#chose_status").val();
	    
	    
	      	$.ajax({
				type : "POST",
				url : "admin/customer/updateCustomerStatus.do",
				data : {"customer_id":customer_id, "status":status },
				success : function(data){
					if(data == "success"){
						alert("添加成功");
						$("#status"+customer_id).html( $("#chose_status").find("option:selected").text() );
						
		      			$("#myModal-customer-status").modal("hide");
	      			}else{
	      				alert("添加失败,"+data);
	      			}
				}
			});
      	
      }
      
      
      function addCustomerBalance(){
      
	      var customer_id = $("#customer_id").val();
	      var addBalance  = $("#addBalance").val();
	      addBalance = parseInt(addBalance) * 100;
	      var remark = $("#remark").val();
	      
	      if(addBalance == ''){
	      	alert('请输入要添加的余额');
	      	return ;
	      }
	      	$.ajax({
				type : "POST",
				url : "admin/customer/addCustomerBalance.do",
				data : {"customer_id":customer_id, "add_balance":addBalance , "remark" : remark},
				success : function(data){
					if(data == "true"){
						alert("添加成功");
						var balance = parseInt($("#balance"+customer_id).html() * 100);
						balance = balance + parseInt(addBalance) ;
						$("#balance"+customer_id).html(  (balance /100).toFixed(2) );
						
		      			$("#myModal-originalList").modal("hide");
	      			}else{
	      				alert("添加失败");
	      			}
				}
			});
      	
      	
      };
      
    </script>

</body>
</html>