<%@page import="com.yd.business.channel.bean.ChannelCustomerBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.channel.bean.ChannelBean"%>
<%@page import="com.yd.business.channel.bean.ChannelProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	String pagePath = basePath + "page/pc/" ;
	CustomerBean customer = (CustomerBean) request.getAttribute("customer");
	List<ChannelCustomerBean> channelCustomerList = (List<ChannelCustomerBean>) request.getAttribute("channelCustomerList");
	
	List<ChannelBean> channelList = (List<ChannelBean>)request.getAttribute("channelList");
	List<ChannelProductBean> channelProductList = (List<ChannelProductBean>)request.getAttribute("channelProductList");
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－客户通道管理</title>
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
					<div class="title">客户默认通道(如果没有启用的个性化通道)</div>
				</div>
				<div class="widget-body">
					<!-- <a class="btn btn-success" href="javascript:window.location.href='admin/supplierTopic/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增客户
					</a> -->
					<div id="dt_example_default" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" >
                        <thead>
                          <tr>
                          	<th style="width:5%">
                            </th>
                            <th >
                              通道名称
                            </th>
                            <th >
                              客户名称
                            </th>
                            <th >
                              省份
                            </th>
                            <th >
                              权重
                            </th>
                            <th >
                              状态
                            </th>
                            <th >
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <% for( ChannelCustomerBean bean : channelCustomerList ) {%>
                          <tr class="gradeX warning">
                            <td>
                            </td>
                            <td id="default_channel_<%=bean.getId()%>">
                              <%=bean.getChannel_name() %>
                            </td>
                            <td>
                              <span ><%=customer.getName() %></span>
                            </td>
                            <td>
                              <span ><%=bean.getProvince() %></span>
                            </td>
                            <td>
                              <span><%=bean.getWeight() %></span>
                            </td>
                            <td>
                              <span><%=bean.getDictValueByField("status") %></span>
                            </td>
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
                                      <a href="javascritp:;" onclick="showUpdateDefaultChannelModal(<%=bean.getId()%>)">修改此默认通道</a>
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
	
	
	
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">个性化通道（优先走个性化通道）</div>
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
                              <th >
          	商品名称
                            </th>
                            <th >
                              通道名称
                            </th>
                            <th >
                              客户名称
                            </th>
                            <th>
                              省份
                            </th>
                            <th >
                              状态
                            </th>
                            <th >
                              操作
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <% for(ChannelProductBean cb : channelProductList){
							 %>
                          <tr class="gradeX warning">
                          <td>
                           
                          </td>
                            <td>
                              <%=cb.getProduct_name() %>
                            </td>
                            <td id="name_<%=cb.getId() %>">
                              <%=cb.getChannel_name() %>
                            </td>
                            <td>
                              <%=customer.getName() %>
                            </td>
                            <td>
                              <span ><%=cb.getProvince() %></span>
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
                                      <a href="javascritp:;" onclick="updateChannelProductStatus(<%=cb.getId()%>,1)">启用</a>
                                    </li>
                                    <li>
                                      <a href="javascritp:;" onclick="updateChannelProductStatus(<%=cb.getId()%>,0)">停用</a>
                                    </li>
                                    <li>
                                      <a href="javascritp:;" onclick="showUpdateChannelProductModal(<%=cb.getId()%>)">修改通道</a>
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
                <h4 class="modal-title" id="myModalLabel">修改默认通道 <span style="color: red;" id="cust_name"></span> </h4>
            </div>
            <div class="modal-body" >
            	<input type="hidden" id="hidden_id">
            	 <div class="form-group" >
                    <label class="col-sm-3 control-label">请选择通道:</label>
                    <div class="col-sm-9">
                      <select id="chose_default_channel" class="span3">
                      	<%for(ChannelBean bean : channelList){ %>
                      		<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
                      	<%} %>
                      </select>
                    </div>
                  </div>
            	<div class="form-actions">
            				<input type="button" class="btn btn-info pull-right" value="返回"
							data-dismiss="modal" >&nbsp;&nbsp; 
							<input type="button" onclick="updateDefaultCustomerChannel()" class="btn btn-info pull-right"
							id="save-infor-btn" value="保存" > &nbsp;&nbsp; 
                          </div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	
	<!-- 模态框（Modal）修改状态 -->
<div class="modal fade" id="myModal-product-channnel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="position: absolute;margin-top: 50%;width: 90%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改产品通道： <span style="color: red;" id="status_cust_name"></span> </h4>
            </div>
            <div class="modal-body" >
            	
            	 <div class="form-group" >
                    <label class="col-sm-3 control-label">请选择通道:</label>
                    <div class="col-sm-9">
                      <select id="chose_product_channel" class="selectpicker">
                      	<%for(ChannelBean bean : channelList){ %>
                      		<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
                      	<%} %>
                      </select>
                    </div>
                  </div>
            	<div class="form-actions">
            				<input type="button" class="btn btn-info pull-right" value="返回"
							data-dismiss="modal" >&nbsp;&nbsp; 
							<input type="button" onclick="updateChannelCustomerProduct()" class="btn btn-info pull-right"
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
      });data-table
      //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
        "bPaginate":true,"iDisplayLength":50,"bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });
      
      function updateDefaultCustomerChannel(){
      	
      	var id = $("#hidden_id").val();
      	var channel_id = $("#chose_default_channel").val();
      	var channel_name = $("#chose_default_channel").find("option:selected").text();
	    
      	$.ajax({
			type : "POST",
			url : "admin/channel/updateChannelCustomerChannel.do",
			data : { "id":id,"channel_id":channel_id, "channel_name":channel_name },
			success : function(data){
				if(data == "true"){
					alert("修改成功");
					
					$("#default_channel_"+id).html( channel_name );
					
	      			$("#myModal-originalList").modal("hide");
      			}else{
      				alert("修改失败,"+data);
      			}
			}
		});
      	
      }
      
      
      function showUpdateDefaultChannelModal(id){
      	
      	$("#hidden_id").val(id);
      	$("#myModal-originalList").modal("show");
      };
      
      
      function showUpdateChannelProductModal(id){
      
      	$("#hidden_id").val(id);
      	$("#myModal-product-channnel").modal("show");
      };
      
      function updateChannelCustomerProduct(){
      	
      	var id = $("#hidden_id").val();
      	var channel_id = $("#chose_product_channel").val();
      	var channel_name = $("#chose_product_channel").find("option:selected").text();
	    
      	$.ajax({
			type : "POST",
			url : "admin/channel/updateCustomerProductChannel.do",
			data : { "id":id,"channel_id":channel_id, "channel_name":channel_name },
			success : function(data){
				if(data == "true"){
					alert("修改成功");
					
					$("#name_"+id).html( channel_name );
					
	      			$("#myModal-product-channnel").modal("hide");
      			}else{
      				alert("修改失败,"+data);
      			}
			}
		});
      	
      }
      
      
      function updateChannelProductStatus(id,status){
      	var str = "";
      	if(status == 1){
      		str = "启用";
      	}else{
      		str = "停用";
      	}
      	
	     $.ajax({
				type : "POST",
				url : "admin/channel/updateChannelProductStatus.do",
				data : {"id":id, "status":status },
				success : function(data){
					if(data == "true"){
						alert("修改成功");
						$("#status"+id).html(  str );
						
	      			}else{
	      				alert("修改失败");
	      			}
				}
			});
      	
      	
      };
      
    </script>

</body>
</html>