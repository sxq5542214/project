<%@page import="com.yd.business.supplier.bean.SupplierCouponRecordBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<SupplierCouponRecordBean>>  dicMap = (Map<String, List<SupplierCouponRecordBean>>)request.getAttribute("dicMap");
	List<SupplierCouponRecordBean> list = pd.getDataList();
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
                    <div class="title">优惠卷记录管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/supplier/supplierCouponController/queryAdminConponRecord.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">userid:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入userid模糊查询" name="query_couponrecord_userid" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">订单号:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入优惠卷订单号模糊查询" name="query_couponrecord_order_code" value=""/>
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
                          	<th style="width:2%"><input type="checkbox" class="no-margin" /></th>
                            <th style="width:4%">userid</th>
                            <th style="width:4%">产品id</th>
                            <th style="width:5%">产品名称</th>
                            <th style="width:5%">优惠卷id</th>
                           	<th style="width:4%">规则id</th>
                            <th style="width:7%">订单号</th>
                            <th style="width:5%">订购产品</th>
                            <th style="width:11%">创建时间</th>
                            <th style="width:11%">修改时间</th>
                            <th style="width:11%">使用时间</th>
                            <th style="width:11%">到期时间</th>
                            <th style="width:4%">状态</th>
                            <th style="width:5%">状态描述</th>
                            <th style="width:3%">备注</th>
                            <th style="width:8%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_couponrecord_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_couponrecord_userid"></td>
									<td class="table_couponrecord_supplier_id"></td>
									<td class="table_couponrecord_supplier_name"></td>
									<td class="table_couponrecord_coupon_id"></td>
									<td class="table_couponrecord_order_id"></td>
									<td class="table_couponrecord_order_code"></td>
									<td class="table_couponrecord_product_name"></td>
									<td class="table_couponrecord_create_time"></td>
									<td class="table_couponrecord_modify_time"></td>
									<td class="table_couponrecord_use_time"></td>
									<td class="table_couponrecord_expire_time"></td>
									<td class="table_couponrecord_status"></td>
									<td class="table_couponrecord_status_description"></td>
									<td class="table_couponrecord_remark"></td>
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
                        	SupplierCouponRecordBean bean = list.get(i);
                        	%>

                        	<tr class="gradeA">
                            <td class="table_couponrecord_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_couponrecord_userid"><%=StringUtil.convertNull(bean.getUserid())%></td>
                            <td class="table_couponrecord_supplier_id"><%=StringUtil.convertNull(bean.getSupplier_id())%></td>
                            <td class="table_couponrecord_supplier_name"><%=StringUtil.convertNull(bean.getSupplier_name())%></td>
                            <td class="table_couponrecord_coupon_id"><%=StringUtil.convertNull(bean.getCoupon_id())%></td>
                            <td class="table_couponrecord_order_id"><%=StringUtil.convertNull(bean.getOrder_id())%></td>
                            <td class="table_couponrecord_order_code"><%=StringUtil.convertNull(bean.getOrder_code())%></td>
                            <td class="table_couponrecord_product_name"><%=StringUtil.convertNull(bean.getProduct_name())%></td>
                            <td class="table_couponrecord_create_time"><%=StringUtil.convertNull(bean.getCreate_time())%></td>
                            <td class="table_couponrecord_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td>
                            <td class="table_couponrecord_use_time"><%=StringUtil.convertNull(bean.getUse_time())%></td>
                            <td class="table_couponrecord_expire_time"><%=StringUtil.convertNull(bean.getExpire_time())%></td>
                            <td class="table_couponrecord_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_couponrecord_status_value"><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
                            <td class="table_couponrecord_status_description"><%=StringUtil.convertNull(bean.getStatus_description())%></td>
                            <td class="table_couponrecord_remark"><%=StringUtil.convertNull(bean.getRemark())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editCouponRecord(this);">
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
                <h4 class="modal-title" id="myModalLabel">配置优惠卷记录基本信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control couponrecord" type="hidden" name="couponrecord_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">userid</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入userid" name="couponrecord_userid"/>
									</div>
									<label class="col-md-2 control-label">产品id</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入产品id" name="couponrecord_supplier_id"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">产品名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入产品名称" name="couponrecord_supplier_name"/>
									</div>
									<label class="col-md-2 control-label">优惠卷id</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷id" name="couponrecord_coupon_id"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">规则id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷规则id" name="couponrecord_order_id"/>
									</div>
									<label class="col-md-2 control-label">订单编号</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入订单编号" name="couponrecord_order_code"/>
									</div>
								</div>
							</div>
						</div>
							<div class="form-group">
							<label class="col-md-2 control-label">产品名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入产品名称" name="couponrecord_product_name"/>
									</div>
									<label class="col-md-2 control-label">优惠卷状态</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷状态" name="couponrecord_create_time"/>
									</div>
								</div>
							</div>
						</div>
							<div class="form-group">
							<label class="col-md-2 control-label">修改时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入修改时间" name="couponrecord_modify_time"/>
									</div>
									<label class="col-md-2 control-label">使用时间</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入使用时间" name="couponrecord_use_time"/>
									</div>
								</div>
							</div>
						</div>
							<div class="form-group">
							<label class="col-md-2 control-label">到期时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷到期时间" name="couponrecord_expire_time"/>
									</div>
									<label class="col-md-2 control-label">状态</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷状态" name="couponrecord_status"/>
									</div>
								</div>
							</div>
						</div>
							<div class="form-group">
							<label class="col-md-2 control-label">状态描述</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入请输入优惠卷状态描述" name="couponrecord_status_description"/>
									</div>
									<label class="col-md-2 control-label">状态</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入状态" name="couponrecord_remark"/>
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
	<script src="js/pc/conpon/coupon_record.js"></script>
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