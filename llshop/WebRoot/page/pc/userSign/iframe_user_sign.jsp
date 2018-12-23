<%@page import="com.yd.business.user.bean.UserSignBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<UserSignBean>>  dicMap = (Map<String, List<UserSignBean>>)request.getAttribute("dicMap");
	List<UserSignBean> list = pd.getDataList();
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
                    <div class="title">用户签到管理</div>
                  </div>
                  <div class="widget-body">
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/user/userController/queryAdmimUserSign.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
							<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">用户id:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入用户id模糊查询" name="query_usersign_user_id" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">签到日期:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入签到日期模糊查询" name="query_crons_sign_date" value=""/>
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
                          	<th style="width:5%"><input type="checkbox" class="no-margin" /></th>
                            <th style="width:11%">创建时间</th>
                            <th style="width:11%">修改时间</th>
                            <th style="width:5%">签到月份</th>
                            <th style="width:8%">签到日期</th>
                            <th style="width:5%">用户id</th>
                            <th style="width:5%">签到场景</th>
                            <th style="width:7%">当月签到数</th>
                            <th style="width:6%">连续签到数</th>
                            <th style="width:10%">最后一次签到日期</th>
                            <th style="width:10%">最后一次获得积分</th>
                            <th style="width:8%">当月获得积分</th>
                            <th style="width:10%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_usersign_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_usersign_create_time"></td>
									<td class="table_usersign_modify_time"></td>
									<td class="table_usersign_sign_month"></td>
									<td class="table_usersign_sign_date"></td>
									<td class="table_usersign_user_id"></td>
									<td class="table_usersign_sence_id"></td>
									<td class="table_usersign_sign_count"></td>
									<td class="table_usersign_serial_sign_count"></td>
									<td class="table_usersign_last_sign_date"></td>
									<td class="table_usersign_last_points"></td>
									<td class="table_usersign_points_month"></td>
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
                        	UserSignBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_usersign_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class=table_usersign_create_time><%=StringUtil.convertNull(bean.getCreate_time())%></td>
                            <td class="table_usersign_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td>
                            <td class="table_usersign_sign_month"><%=StringUtil.convertNull(bean.getSign_month())%></td>
                            <td class="table_usersign_sign_date"><%=StringUtil.convertNull(bean.getSign_date())%></td>
                            <td class="table_usersign_user_id"><%=StringUtil.convertNull(bean.getUser_id())%></td>
                            <td class="table_usersign_sence_id"><%=StringUtil.convertNull(bean.getSence_id())%></td>
                            <td class="table_usersign_sign_count"><%=StringUtil.convertNull(bean.getSign_count())%></td>
                            <td class="table_usersign_serial_sign_count"><%=StringUtil.convertNull(bean.getSerial_sign_count())%></td>
                            <td class="table_usersign_last_sign_date"><%=StringUtil.convertNull(bean.getLast_sign_date())%></td>
                            <td class="table_usersign_last_points"><%=StringUtil.convertNull(bean.getLast_points())%></td>
                            <td class="table_usersign_points_month"><%=StringUtil.convertNull(bean.getPoints_month())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editUserSign(this);">
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
                <h4 class="modal-title" id="myModalLabel">配置用户签到信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control usersign" type="hidden" name="usersign_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">签到月份</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入签到月份" name="usersign_sign_month"/>
									</div>
										<label class="col-md-2 control-label">签到日期</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入签到日期" name="usersign_sign_date"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">用户id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入用户id" name="usersign_user_id"/>
									</div>
										<label class="col-md-2 control-label">签到场景</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入签到场景" name="usersign_sence_id"/>
									</div>
								</div>
							</div>
						</div>
					<div class="form-group">
							<label class="col-md-2 control-label">当月签到数</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入当月签到数" name="usersign_sign_count"/>
									</div>
										<label class="col-md-2 control-label">连续签到数</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入连续签到数" name="usersign_serial_sign_count"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">最后一次签到日期</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入最后一次签到日期" name="usersign_last_sign_date"/>
									</div>
										<label class="col-md-2 control-label">最后一次获得积分</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入最后一次获得积分" name="usersign_last_points"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">当月获得积分</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入当月获得积分" name="usersign_points_month"/>
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
	<script src="js/pc/userSign/user_sign.js"></script>
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