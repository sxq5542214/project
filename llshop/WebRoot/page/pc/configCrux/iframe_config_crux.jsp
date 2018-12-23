 <%@page import="com.yd.business.other.bean.ConfigCruxBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
    List<DictionaryBean> dictionarylist = (List<DictionaryBean>) request.getAttribute("dictionarylist");
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<ConfigCruxBean> list = pd.getDataList();
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
                   系统关键词管理
                    </div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/other/configCruxAjaxController/queryAdminConfigCrux.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
					<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">名称：</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入名称模糊查询" name="query_config_crux_name" value=""/>
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
                            <th style="width:10%">名称</th>
                            <th style="width:10%">编码</th>
                            <th style="width:10%">种类</th>
                            <th style="width:10%">类别</th>
                            <th style="width:20%">发送短信内容</th>
                            <th style="width:5%">状态</th>
                            <th style="width:10%"> 创建时间</th>
                            <th style="width:10%"> 修改时间</th>
                            <th style="width:10%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_conf_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_conf_name"></td>
									<td class="table_conf_code"></td>
									<td class="table_conf_type"></td>
									<td class="table_conf_key"></td>
									<td class="table_conf_value"></td>
									<td class="table_conf_status"></td>
									<td class="table_conf_create_time"></td>
									<td class="table_conf_modify_time"></td>
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
                        	ConfigCruxBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_conf_id">
                            <input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/>
                            </td>
                            <td class="table_conf_name"><%=StringUtil.convertNull(bean.getName())%></td>
                            <td class="table_conf_code"><%=StringUtil.convertNull(bean.getCode())%></td>
                            <td class="table_conf_type"><%=bean.getType() %></td>
                            <td class="table_conf_key"><%=bean.getKey() %></td>
                            <td class="table_conf_value"><%=StringUtil.convertNull(bean.getValue())%></td>
                            <td class="table_conf_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_conf_status_value"><%=bean.getDictValueByField("status") %></td>
                            <td class="table_conf_create_time"><%=StringUtil.convertNull(bean.getCreate_time())%></td>
                            <td class="table_conf_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td> 
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editConfigAttribute(this);">
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
                <h4 class="modal-title" id="myModalLabel">配置短信内容</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control config_crux" type="hidden" name="config_crux_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入名称" name="config_crux_name"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">编码</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入编码" name="config_crux_code"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">种类</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">

										<select id="disabledSelect" class="form-control config_attribute" name="config_crux_type">
											<%
												for (DictionaryBean dictionaryBean : dictionarylist) {
													String selected = "";
													if (dictionaryBean.getValue() != null || "".equals(dictionaryBean.getValue())) {
														selected = "selected=\"selected\"";
													}
											%>
												<option value="<%=dictionaryBean.getValue()%>"
												<%=selected%>><%=dictionaryBean.getValue()%></option>
											<%} %>
										</select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">类别</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入类别" name="config_crux_key">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select id="status" name="config_crux_status" class="form-control">
											<option value="">请选择状态</option>
											<option value="1">可用</option>
											<option value="0">不可用</option>
										</select>									
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">短信内容</label>
							<div class="col-md-10">
								<textarea class="form-control" rows="5"  placeholder="请输入模板内容" name="config_crux_value"></textarea>
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
	<script src="js/pc/configcrux/config_crux.js"></script>
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