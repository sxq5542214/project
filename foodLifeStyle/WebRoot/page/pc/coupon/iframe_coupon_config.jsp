<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<SupplierCouponConfigBean>>  dicMap = (Map<String, List<SupplierCouponConfigBean>>)request.getAttribute("dicMap");
	List<SupplierCouponConfigBean> list = pd.getDataList();
	
	List<SupplierProductBean> supplierProductList =(List<SupplierProductBean>) request.getAttribute("supplierProductList");

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
                    <div class="title">优惠卷配置管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal" onclick="	ajaxShowProduct('')"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/supplier/supplierCouponController/queryAdminConponConfig.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">名称:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入优惠卷名称模糊查询" name="query_couponconfig_coupon_name" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">商户名称:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入商户名称模糊查询" name="query_dictionary_merchant_name" value=""/>
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
                          	<th style="width:3%"><input type="checkbox" class="no-margin" /></th>
                          	<th style="width:5%">优惠卷id</th>
                            <th style="width:4%">商户id</th>
                            <th style="width:6%">商户名称</th>
                            <th style="width:8%">编码</th>
                            <th style="width:3%">类型</th>
                           	<th style="width:4%">名称</th>
                            <th style="width:4%">折扣</th>
                            <th style="width:4%">抵扣</th>
                            <th style="width:4%">状态</th>
                            <th style="width:5%">目前数量</th>
                            <th style="width:7%">领取限制数量</th>
                            <th style="width:3%">总数</th>
                            <th style="width:6%">开始时间</th>
                            <th style="width:6%">结束时间</th>
                            <th style="width:4%">备注</th>
                            <th style="width:5%">背景色</th>
                            <th style="width:6%">可使用产品</th>
                            <th style="width:10%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_couponconfig_id_a"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_couponconfig_id"></td>
									<td class="table_couponconfig_merchant_id"></td>
									<td class="table_couponconfig_merchant_name"></td>
									<td class="table_couponconfig_code"></td>
									<td class="table_couponconfig_type"></td>
									<td class="table_couponconfig_coupon_name"></td>
									<td class="table_couponconfig_coupon_discount"></td>
									<td class="table_couponconfig_coupon_offsetmoney"></td>
									<td class="table_couponconfig_status"></td>
									<td class="table_couponconfig_number"></td>
									<td class="table_couponconfig_receive_limit_num"></td>
									<td class="table_couponconfig_coupon_count"></td>
									<td class="table_couponconfig_begin_time"></td>
									<td class="table_couponconfig_end_time"></td>
									<td class="table_couponconfig_remark"></td>
									<td class="table_couponconfig_coupon_backgroup"></td>
									<td class="table_couponconfig_couponshow_product"></td>
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
                        	SupplierCouponConfigBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_couponconfig_id_a"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_couponconfig_id"><%=StringUtil.convertNull(bean.getId())%></td>
                            <td class="table_couponconfig_merchant_id"><%=StringUtil.convertNull(bean.getSupplier_id())%></td>
                            <td class="table_couponconfig_merchant_name"><%=StringUtil.convertNull(bean.getSupplier_name())%></td>
                            <td class="table_couponconfig_code"><%=StringUtil.convertNull(bean.getCode())%></td>
                            <td class="table_couponconfig_type"><%=StringUtil.convertNull(bean.getType())%></td>
                            <td class="table_couponconfig_coupon_name"><%=StringUtil.convertNull(bean.getCoupon_name())%></td>
                            <td class="table_couponconfig_coupon_discount"><%=StringUtil.convertNull(bean.getCoupon_discount())%></td>
                            <td class="table_couponconfig_coupon_offsetmoney"><%=StringUtil.convertNull(bean.getCoupon_offsetmoney())%></td>
                          	<td class="table_couponconfig_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_couponconfig_status_value"><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
                            <td class="table_couponconfig_number"><%=StringUtil.convertNull(bean.getNumber())%></td>
                            <td class="table_couponconfig_receive_limit_num"><%=StringUtil.convertNull(bean.getReceive_limit_num())%></td>
                            <td class="table_couponconfig_coupon_count"><%=StringUtil.convertNull(bean.getCoupon_count())%></td>
                            <td class="table_couponconfig_begin_time"><%=StringUtil.convertNull(bean.getBegin_time())%></td>
                            <td class="table_couponconfig_end_time"><%=StringUtil.convertNull(bean.getEnd_time())%></td>
                            <td class="table_couponconfig_remark"><%=StringUtil.convertNull(bean.getRemark())%></td>
                            <td class="table_couponconfig_coupon_backgroup" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getCoupon_backgroup() %>"/></td>
                            <td class="table_couponconfig_coupon_backgroup_value"><%=StringUtil.convertNull(bean.getDictValueByField("coupon_backgroup")) %></td>
                            <td class="table_couponconfig_couponshow_product"><%=StringUtil.convertNull(bean.getCoupon_spid())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editCouponConfig(this);">
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
      
        <div class="modal-content"  >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">配置优惠卷详情</h4>
            </div>
             <ul id="myTab" class="nav nav-tabs">
						<li class="active">
							<a id="couponinfo_title" href="#couponinfo" data-toggle="tab">配置优惠卷基本信息</a>
						</li>
						<li>
							<a id="couponproduct_title" href="#couponproduct" data-toggle="tab">优惠卷使用产品</a>
						</li>
			</ul>
            <div class="modal-body tab-content" style="padding-right: 10%;" >
            <div class="tab-pane fade in active" id="couponinfo" >
				<form class="form-horizontal row-border" >
						<input class="form-control couponconfig" type="hidden" name="couponconfig_id" value="" >
						
						
						<div class="form-group">
							<label class="col-md-1 control-label">编码</label>
							<div class="col-md-3">
										<input class="form-control" placeholder="请输入编码" name="couponconfig_code"/>
							</div>
							<label class="col-md-1 control-label">类型</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入类型" name="couponconfig_type"/>
							</div>
							<label class="col-md-1 control-label">名称</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入优惠卷名称" name="couponconfig_coupon_name"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-1 control-label">抵扣</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入优惠卷抵扣现金" name="couponconfig_coupon_offsetmoney"/>
							</div>
							<label class="col-md-1 control-label">折扣</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入优惠卷折扣" name="couponconfig_coupon_discount"/>
							</div>
							<label class="col-md-1 control-label">状态</label>
							<div class="col-md-3">
									<select  name="couponconfig_status" class="form-control">
											<option value="">请选择优惠卷状态</option>
											<option value="<%=SupplierCouponConfigBean.STATUS_UP%>">生效</option>
											<option value="<%=SupplierCouponConfigBean.STATUS_DOWN%>">不生效</option>
									</select>														
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-1 control-label">商户id</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入商户id" name="couponconfig_merchant_id"/>
							</div>
							<label class="col-md-1 control-label">总数</label>
							<div class="col-md-3">
								<input class="form-control" placeholder="请输入优惠卷总数" name="couponconfig_coupon_count"/>
							</div>
							<label class="col-md-1 control-label">背景色</label>
							<div class="col-md-3">
								<select  name="couponconfig_coupon_backgroup" class="form-control">
									<option value="">请选择优惠卷背景颜色</option>
									<option value="#FF0000">红色</option>
									<option value="#FFFF00">黄色</option>
									<option value="#00BFFF">蓝色</option>
									<option value="#FF4500">橘红</option>
									<option value="#00FF00">绿色</option>
								</select>								
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">商户名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入商户名称" name="couponconfig_merchant_name"/>
									</div>
									<label class="col-md-2 control-label">备注</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入备注" name="couponconfig_remark"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">目前数量</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入目前优惠卷数量" name="couponconfig_number"/>
									</div>
									<label class="col-md-2 control-label">限制领取</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷限制领取数量" name="couponconfig_receive_limit_num"/>
									</div>
								</div>
							</div>
						</div>
						
							<div class="form-group">
							<label class="col-md-2 control-label">开始时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="时间格式(2016-09-30 00:00:00)" name="couponconfig_begin_time"/>
									</div>
									<label class="col-md-2 control-label">结束时间</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="时间格式(2016-09-30 00:00:00)" name="couponconfig_end_time"/>
								</div>
									</div>
							</div>
						</div>
					</form>
					</div>
			<div id="couponproduct" class="tab-pane fade">
             <button class="btn btn-success"  data-toggle="modal" data-target="#myModal-product-list"><i class="fa fa-plus-circle"></i>新增商品</button>
            <div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:200px;"></div>
            </div>
             <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="commitData(this);">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>



  <div class="modal fade" id="myModal-product-list" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">优惠卷产品选择</h4>
            </div>
            <div class="modal-body">
            <div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered " id="data-table-admin">
						<thead>
							<tr>
								<th style="width:20px;"><input type="checkbox"  onclick="checkAll(this);" />
								<input class="menu_id" type="hidden" />
								</th>
								<th>产品</th>
								<th>国内/省内</th>
							</tr>
						</thead>
						<tbody>
							<%for(SupplierProductBean supplierProductBean:supplierProductList){%>
								<tr class="gradeA">
									<td><input class="admin_id" type="checkbox" value="<%=supplierProductBean.getId() %>" /></td>
									<td><%=StringUtil.convertNull(supplierProductBean.getProduct_name()) %></td>
									<td><%=StringUtil.convertNull(supplierProductBean.getProduct_type_name()) %></td>
								</tr>
							<%} %>
						</tbody>
					</table>
					</div>
				</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary pull-left" id="commit_show_product_admin" onclick="addProduct()">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
	<script src="js/pc/conpon/coupon_config.js"></script>
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
        
        $('#data-table-admin').dataTable({
        //checkbox不排序
         "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
         "aaSorting": [[1, "desc"]],
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });


    </script>

  </body>
</html>