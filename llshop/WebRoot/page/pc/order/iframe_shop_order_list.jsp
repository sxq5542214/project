<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.order.bean.ShopOrderInfoBean"%>
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
	ShopOrderInfoBean condition = (ShopOrderInfoBean)request.getAttribute("condition");
	if(condition == null) condition = new ShopOrderInfoBean();
	List<ShopOrderInfoBean> orderList = (List<ShopOrderInfoBean>)request.getAttribute("orderList");
	List<DictionaryBean> dictList = (List<DictionaryBean>) request.getAttribute("dictList");
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－订单管理</title>
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
					<div class="title">订单管理</div>
				</div>
				
					<form action="admin/order/shop/toShopOrderListMgr.do" id="conditionFrom">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" style="border: 0">
						<tr  align="center">
							<td style="width: 10%;vertical-align : middle;border: 0;" >
								订单号
							</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="order_code"
											placeholder="订单号" value="<%=StringUtil.convertNull(condition.getOrder_code()) %>">
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;">联系人</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="contact_name"
											placeholder="联系人" value="<%=StringUtil.convertNull(condition.getContact_name()) %>">
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;">联系电话</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="contact_phone"
											placeholder="联系电话" value="<%=StringUtil.convertNull(condition.getContact_phone()) %>">
							</td>
							
						</tr>
						<tr  align="center">
							<td style="width: 10%;vertical-align : middle;border: 0;" >
								状态
							</td>
							<td style="width: 20%;border: 0;">
								<select name="status" class="form-control">
									<option selected="selected" value="">请选择</option>
									<% for(DictionaryBean dict : dictList){
										String selected = "";
										if(condition.getStatus() != null &&   dict.getValue().equals(String.valueOf(condition.getStatus())) ){
											selected = "selected=\"selected\"";
										}
									 %>
									<option value="<%=dict.getValue() %>" <%=selected %> ><%=dict.getDescription() %></option>
									
									<%} %>
								</select>
							
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;"></td>
							<td style="width: 20%;border: 0;">
								
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;"></td>
							<td style="width: 20%;border: 0;">
								<button type="submit" class="btn btn-info">查询</button>
								<input type="hidden" name="nowpage" id="nowpage" >
							</td>
							
						</tr>
					</table>
					</form>
				
				<div class="widget-body" style="padding: 5px;">
					<!-- <a class="btn btn-success" href="javascript:window.location.href='admin/supplierTopic/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增客户
					</a> -->
					<div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
					 <thead>
                          <tr>
                              <th >
                              ID
                            </th>
							<th >
                               下单时间
                            </th>
                            <th >
                         订单名称    
                            </th>
                            <th >
                               定单状态
                            </th>
                            <th >
                              下单金额
                            </th>
                         </tr>
                         <tr>
                            <th colspan="2">
                              快递单号
                            </th>
                            <th colspan="2">
                              收货信息
                            </th>
                            
                            <th >
                              操作
                            </th>
                          </tr>
                        </thead>
					
					
					
       <!--                  <thead>
                          <tr>
                              <th >
                               用户昵称
                            </th>
                            <th >
                         订单名称    
                            </th>
                            <th >
                              订单号
                            </th>
                            <th >
                              下单时间
                            </th>
                            <th >
                              下单金额
                            </th>
                            <th >
                              定单状态
                            </th>
                            <th >
                              收货信息
                            </th>
                            <th >
                              配送状态
                            </th>
                            <th >
                              配送单号
                            </th>
                            <th >
                              操作
                            </th>
                          </tr>
                        </thead> -->
                        <tbody>
                        <%  int i = 0 ; 
                        	for(ShopOrderInfoBean order : orderList){
							String str = "";
							if(i++ %2 ==0){ str = "gradeX warning"; }
							 %>
                          <tr class="<%=str %>">
                         <%--  <td>
                              <%=order.getNick_name() %>
                          </td> --%>
                          
                            <td>
                              <%=order.getId() %>
                            </td>  <td>
                              <%=order.getCreate_time() %>
                            </td>
                            <td>
                              <%=order.getOrder_name() %>
                            </td>
                            <td>
                              <%=order.getDictValueByField("status") %>
                            </td>
                           
                            <td>
                              <span ><%=order.getCost_price() /100d %>元</span>
                            </td>
                          </tr>
                          
                          <tr class="<%=str %>" >
                          
                           <%--  <td>
                              <%=order.getOrder_code() %>
                            </td>
                            
                            <td id="expressMode<%=order.getId()%>" price="<%=order.getExpress_price()%>">
                              <%=order.getExpress_mode() == null ? "未发货":order.getExpress_mode() %>
                            </td>
                             --%>
                             
                             <td colspan="2" id="expressMode<%=order.getId()%>" price="<%=order.getExpress_price()%>">
                              <%=order.getExpress_order_code() == null ? "未发货":order.getExpress_order_code() %>
                            </td>
                             <td colspan="2">
                              <%=order.getContact_name()+","+order.getContact_phone() +","+order.getContact_address() %>
                            </td>
                            
                            <td>
                                <div class="btn-group">
                                  <button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">
                                    操作 
                                    <span class="caret"></span>
                                  </button>
                                  <ul class="dropdown-menu pull-right">
                                    <li>
<%--                                       <a href="user/toUserShopOrderPage.do?order_code=<%=order.getOrder_code() %>" data-toggle="modal" data-target="#myModal-orderProductList">查看详情</a>
 --%>                                    
                                      <a href="javascritp:;" onclick="aaa('<%=order.getOrder_code()%>')">查看详情</a>
 									</li>
                                    <li>
                                      <a href="javascritp:;" onclick="showUpdateExpressModal(<%=order.getId()%>)">修改配送信息</a>
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
	
	
	<!-- 模态框（Modal）配送信息 -->
<div class="modal fade" id="myModal-originalList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">修改配送信息</h4>
            </div>
            <div class="modal-body">
            	 <div></div>
            	 <div class="form-group">
                    <label class="col-sm-4 control-label">配送方式（顺丰、圆通等）</label>
                    <input id="order_id" type="hidden" >
                    <div class="input-group col-sm-6 ">
                      <input type="text" class="form-control" id="express_mode" name="express_mode"
						placeholder="顺丰、圆通等"  value="圆通" >
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-4 control-label">配送单号</label>
                    <div class="input-group col-sm-6">
                      <input type="text" class="form-control" id="express_order_code" name="express_order_code"
						placeholder="配送单号"  value="" >  <button>点我扫码单号</button> 
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="col-sm-4 control-label">配送价格（单位：分）</label>
                    <div class="input-group col-sm-6">
                      <input type="number" class="form-control" id="express_price" name="express_price"
						placeholder="配送价格,单位为分"  value="0" >
                    </div>
                  </div>
            		<div class="form-actions">
           				<input type="button" class="btn btn-info pull-right" value="返回"
						data-dismiss="modal" >&nbsp;&nbsp; 
						<input type="button" onclick="updateOrderExpress()" class="btn btn-info pull-right"
						id="save-infor-btn" value="保存" > &nbsp;&nbsp; 
                    </div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	
	<!-- 模态框（Modal）定单的商品列表 -->
<div class="modal fade" id="myModal-orderProductList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">定单商品列表</h4>
            </div>
            <div class="modal-body">
            	
            	
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
	
      /* $("#myModal-orderProductList").on("hidden.bs.modal", function() {  
		    $(this).removeData("bs.modal");  
		}); */  
		
		function aaa(order_code){
			window.showModalDialog('<%=basePath%>user/toUserShopOrderPage.do?order_code='+order_code);
		}
		
		
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
      
      function showUpdateExpressModal(id){
      	
      	$("#order_id").val(id);
      	
 //     	var price = $("#expressMode"+id).attr("price");
 //     	$("#express_price").val(price);
      	$("#myModal-originalList").modal("show");
      };
      
      
      function updateOrderExpress(){
      
	      var order_id = $("#order_id").val();
	      var express_mode  = $("#express_mode").val();
	      var express_order_code = $("#express_order_code").val();
	      var express_price = $("#express_price").val();
	      
	/*       if(express_mode == ''){
	      	alert('请输入配送方式');
	      	return ;
	      }
	      if(express_order_code == ''){
	      	alert('请输入配送单号');
	      	return ;
	      } */
	      	$.ajax({
				type : "POST",
				url : "admin/order/shop/updateShopOrderExpress.do",
				data : {"order_id":order_id, "express_mode":express_mode, "express_order_code" : express_order_code,"express_price":express_price},
				success : function(data){
					if(data == "true"){
						alert("修改成功");
						
						$("#expressMode"+order_id).html(express_mode);
						$("#expressOrderCode"+order_id).html(express_order_code);
						
						$("#expressMode"+order_id).attr("price",express_price);
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