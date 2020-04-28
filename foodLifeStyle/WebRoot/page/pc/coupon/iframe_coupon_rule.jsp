<%@page import="com.yd.business.supplier.bean.SupplierCouponRuleBean"%>
<%@page import="com.yd.business.supplier.bean.SupplierCouponConfigBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<SupplierCouponConfigBean> couponList = (List<SupplierCouponConfigBean>) request.getAttribute("couponInfo");
	
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<SupplierCouponRuleBean>>  dicMap = (Map<String, List<SupplierCouponRuleBean>>)request.getAttribute("dicMap");
	List<SupplierCouponRuleBean> list = pd.getDataList();
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
                    <div class="title">优惠卷规则管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/supplier/supplierCouponController/queryAdminConponRule.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
										<label class="control-label">规则:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入规则模糊查询" name="query_couponrule_rule_name" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">SQL:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入SQL模糊查询" name="query_crons_SQL" value=""/>
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
                            <th style="width:10%">优惠卷id</th>
                            <th style="width:10%">规则名称</th>
                            <th style="width:5%">规则解释</th>
                            <th style="width:5%">状态</th>
                           	<th style="width:5%">类别</th>
                            <th style="width:10%">开始时间</th>
                            <th style="width:10%">截至时间</th>
                            <th style="width:10%">SQL</th>
                            <th style="width:7%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_couponrule_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_couponrule_coupon_id"></td>
									<td class="table_couponrule_rule_name"></td>
									<td class="table_couponrule_explain"></td>
									<td class="table_couponrule_status"></td>
									<td class="table_couponrule_type"></td>
									<td class="table_couponrule_begin_time"></td>
									<td class="table_couponrule_end_time"></td>
									<td class="table_couponrule_SQL"></td>
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
                        	SupplierCouponRuleBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_couponrule_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_couponrule_coupon_id"><%=StringUtil.convertNull(bean.getCoupon_id())%></td>
                            <td class="table_couponrule_rule_name"><%=StringUtil.convertNull(bean.getRule_name())%></td>
                            <td class="table_couponrule_explain"><%=StringUtil.convertNull(bean.getExplain())%></td>
                            <td class="table_couponrule_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_couponrule_status_value"><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
                            <td class="table_couponrule_type"><%=StringUtil.convertNull(bean.getType())%></td>
                            <td class="table_couponrule_begin_time"><%=StringUtil.convertNull(bean.getBegin_time())%></td>
                            <td class="table_couponrule_end_time"><%=StringUtil.convertNull(bean.getEnd_time())%></td>
                            <td class="table_couponrule_SQL"><%=StringUtil.convertNull(bean.getSQL())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editCouponRule(this);">
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
                <h4 class="modal-title" id="myModalLabel">配置优惠卷规则基本信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control dictionary" type="hidden" name="couponrule_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">优惠卷id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select id="disabledSelect" class="form-control couponrule" name="couponrule_coupon_id">
											<%
												for (SupplierCouponConfigBean couponInfo : couponList) {
													String selected = "";
													if (couponInfo.getId() != null || "".equals(couponInfo.getId())) {
														selected = "selected=\"selected\"";
													}
											%>
												<option  value="<%=couponInfo.getId()%>"
												<%=selected%>><%=couponInfo.getId()%></option>
											<%} %>
										</select>
									</div>
									<label class="col-md-2 control-label">优惠卷规则</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入优惠卷规则" name="couponrule_rule_name"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">规则解释</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入规则解释" name="couponrule_explain"/>
									</div>
									<label class="col-md-2 control-label">状态</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入状态" name="couponrule_status"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">类型</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入类型" name="couponrule_type"/>
									</div>
									<label class="col-md-2 control-label">开始时间</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入开始时间" name="couponrule_begin_time"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">结束时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入结束时间" name="couponrule_end_time"/>
									</div>
									<label class="col-md-2 control-label">SQL</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入SQL" name="couponrule_SQL"/>
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
	<script src="js/pc/conpon/coupon_rule.js"></script>
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