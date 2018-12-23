<%@page import="com.yd.business.order.bean.OrderProductLogBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<OrderProductLogBean>>  dicMap = (Map<String, List<OrderProductLogBean>>)request.getAttribute("dicMap");
	List<OrderProductLogBean> list = pd.getDataList();
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
                    <div class="title">订购日志管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/order/orderProductLogController/queryAdmimOrderProductLog.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
					
					<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">用户id:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入用户id查询" name="query_orderproductlog_user_id" value=""/>
									</div>
									<div class="col-xs-2">
							<label class="control-label">订单号:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入订单号" name="query_orderproductlog_order_code" value=""/>
									</div>
								</div>
							</div>
						</div>
						</div>
						</div>
							<div class="form-group">
							<div class="col-sm-12">
								<input type="submit" id="submitSupplier" class="btn btn-info pull-right" value="搜索"/>  
								<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
							</div>
						</div>
					</form>
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table" style="word-break:break-all; word-wrap:break-all;">
                        <thead id="supplier_list_head">
                          <tr>
                          	<th></th>
                            <th style="width:4%">商户id</th>
                            <th style="width:3%">商户名称</th>
                            <th style="width:3%">产品id</th>
                            <th style="width:5%">产品名称</th>
                            <th style="width:5%">产品价格</th>
                            <th style="width:7%">订单号</th>
                            <th style="width:5%">订购号码</th>
                            <th style="width:5%">创建时间</th>
                            <th style="width:5%">修改时间</th>
                            <th style="width:5%">花费总额</th>
                            <th style="width:3%">积分</th>
                            <th style="width:3%">现金</th>
                            <th style="width:5%">花费余额</th>
                            <th style="width:4%">用户id</th>
                            <th style="width:3%">状态</th>
                            <th style="width:7%">描述</th>
                            <th style="width:4%">通道id</th>
                            <th style="width:5%">事件类型</th>
                            <th style="width:5%">红包金额</th>
                            <th style="width:6%">是否发红包</th>
                            <th style="width:8%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_orderproductlog_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_orderproductlog_supplier_id"></td>
									<td class="table_orderproductlog_supplier_name"></td>
									<td class="table_orderproductlog_supplier_product_id"></td>
									<td class="table_orderproductlog_product_name"></td>
									<td class="table_orderproductlog_product_price"></td>
									<td class="table_orderproductlog_order_code"></td>
									<td class="table_orderproductlog_order_account"></td>
									<td class="table_orderproductlog_create_time"></td>
									<td class="table_orderproductlog_modify_time"></td>
									<td class="table_orderproductlog_cost_price"></td>
									<td class="table_orderproductlog_cost_points"></td>
									<td class="table_orderproductlog_cost_money"></td>
									<td class="table_orderproductlog_cost_balance"></td>
									<td class="table_orderproductlog_user_id"></td>
									<td class="table_orderproductlog_status"></td>
									<td class="table_orderproductlog_remark"></td>
									<td class="table_orderproductlog_channel_id"></td>
									<td class="table_orderproductlog_event_type"></td>
									<td class="table_orderproductlog_lucky_money"></td>
									<td class="table_orderproductlog_is_sended"></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;"  style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
                        	
                        	<%
                        	if(list!=null&&list.size()>0)
                        	for(int i=0;i<list.size();i++){
                        	OrderProductLogBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_orderproductlog_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_orderproductlog_supplier_id"><%=StringUtil.convertNull(bean.getSupplier_id())%></td>
                            <td class="table_orderproductlog_supplier_name"><%=StringUtil.convertNull(bean.getSupplier_name())%></td>
                            <td class="table_orderproductlog_supplier_product_id"><%=StringUtil.convertNull(bean.getSupplier_product_id())%></td>
                            <td class="table_orderproductlog_product_name"><%=StringUtil.convertNull(bean.getProduct_name())%></td>
                            <td class="table_orderproductlog_product_price"><%=StringUtil.convertNull(bean.getProduct_price())%></td>
                            <td class="table_orderproductlog_order_code"><%=StringUtil.convertNull(bean.getOrder_code())%></td>
                            <td class="table_orderproductlog_order_account"><%=StringUtil.convertNull(bean.getOrder_account())%></td>
                            <td class="table_orderproductlog_create_time"><%=StringUtil.convertNull(bean.getCreate_time())%></td>
                            <td class="table_orderproductlog_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td>
                            <td class="table_orderproductlog_cost_price"><%=StringUtil.convertNull(bean.getCost_price())%></td>
                            <td class="table_orderproductlog_cost_points"><%=StringUtil.convertNull(bean.getCost_points())%></td>
                            <td class="table_orderproductlog_cost_money"><%=StringUtil.convertNull(bean.getCost_money())%></td>
                            <td class="table_orderproductlog_cost_balance"><%=StringUtil.convertNull(bean.getCost_balance())%></td>
                            <td class="table_orderproductlog_user_id"><%=StringUtil.convertNull(bean.getUser_id())%></td>
                            <td class="table_orderproductlog_status"><%=StringUtil.convertNull(bean.getStatus())%></td>
                            <td class="table_orderproductlog_remark"><%=StringUtil.convertNull(bean.getRemark())%></td>
                            <td class="table_orderproductlog_channel_id"><%=StringUtil.convertNull(bean.getChannel_id())%></td>
                            <td class="table_orderproductlog_event_type"><%=StringUtil.convertNull(bean.getEvent_type())%></td>
                            <td class="table_orderproductlog_lucky_money"><%=StringUtil.convertNull(bean.getLucky_money())%></td>
                            <td class="table_orderproductlog_is_sended"><%=StringUtil.convertNull(bean.getIs_sended())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editOrderProductLog(this);">
											<i class="fa"></i>修改
										</a>
									<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
									</a>
                              </td>
                          </tr>
                        	<%
                        	}
                        	 %>
                        </tbody>
                      </table>
                      <div class="clearfix">
                      </div>
                      	<div class="dataTables_info">当前第<%=pd.getNowpage()%> 页/共 <%=pd.getTotalpage()%> 页[<%=pd.getTotalcount()%> 条数据]</div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      	<a tabindex="0" class="paginate_button" onclick="toPage(1)" >首页</a>
                      	<%
                      		if (pd.getNowpage() > 1) {
                      	%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>
                      		<%for(int i = 1 ; i <= pd.getTotalpage(); i ++) {
                      			String classStr = "paginate_button";
                      			if(pd.getNowpage() == i)
                      				classStr = "paginate_active";
                      			if(i < pd.getNowpage()+5 && i > pd.getNowpage()-5){%>
                      				<a tabindex="0" class="<%=classStr %>" onclick="toPage(<%=i %>)" ><%=i %></a>
                      		<%}} %>
						<%if(pd.getNowpage()<pd.getTotalpage()){%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>                      	
                      	<a tabindex="0" class="paginate_button" onclick="toPage(<%=pd.getTotalpage() %>)" >尾页</a>
                      	</span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Row End -->




<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="width:70%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">配置订购日志信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control orderproductlog" type="hidden" name="orderproductlog_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">商户id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入商户id" name="orderproductlog_supplier_id"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">商户名称</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入商户名称" name="orderproductlog_supplier_name"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">产品id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入产品id" name="orderproductlog_supplier_product_id"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">产品名称</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入产品名称" name="orderproductlog_product_name"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">产品价格</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入产品价格" name="orderproductlog_product_price"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">订单号</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入订单号" name="orderproductlog_order_code"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">订购号码</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入订购号码" name="orderproductlog_order_account"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">花费总额</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入花费总额" name="orderproductlog_cost_price"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">花费积分</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入花费积分" name="orderproductlog_cost_points"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">花费现金</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入花费现金" name="orderproductlog_cost_money"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">花费余额</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入花费余额" name="orderproductlog_cost_balance"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">用户id</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入用户id" name="orderproductlog_user_id"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入状态" name="orderproductlog_status"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">描述</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入描述" name="orderproductlog_remark"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">通道id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入通道id" name="orderproductlog_channel_id"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">事件类型</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入事件类型" name="orderproductlog_event_type"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">红包金额</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入红包金额" name="orderproductlog_lucky_money"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">是否发红包</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入是否发红包 "  name="orderproductlog_is_sended"/>
									</div>
								</div>
							</div>
						</div>
					</form>
            </div>
             <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="commitData(this);">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>



    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
	<script src="js/pc/orderProductLog/orderProductLog.js"></script>
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


    </script>

  </body>
</html>