<%@page import="com.yd.business.other.bean.AdvertisingBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.other.bean.ConfigAttributeBean"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<AdvertisingBean> list = pd.getDataList();
	
	ConfigAttributeBean attrBean = (ConfigAttributeBean)request.getAttribute("attrBean");
	
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
	<link href="css/bootstrap-fileinput.min.css" rel="stylesheet">
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
                    <div class="title">轮播图片管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/other/advertisingController/queryAdvertisingAdministration.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
					<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">编码:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入名称模糊查询" name="query_advertising_code" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">图片超链接:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入图片超链接模糊查询" name="query_advertising_picture_link" value=""/>
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
                            <th style="width:10%">编码</th>
                            <th style="width:10%">种类</th>
                            <th style="width:14%">图片位置链接</th>
                            <th style="width:15%">图片超链接</th>
                            <th style="width:6%">状态</th>
                            <th style="width:12%"> 创建时间</th>
                            <th style="width:12%"> 修改时间</th>
                            <th style="width:6%"> 排序</th>
                            <th style="width:10%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_advertising_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_advertising_code"></td>
									<td class="table_advertising_type"></td>
									<td class="table_advertising_picture"></td>
									<td class="table_advertising_picture_link"></td>
									<td class="table_advertising_status"></td>
									<td class="table_advertising_create_time"></td>
									<td class="table_advertising_modify_time"></td>
									<td class="table_advertising_seq"></td>
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
                        	AdvertisingBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_advertising_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_advertising_code"><%=StringUtil.convertNull(bean.getCode())%></td>
                            <td class="table_advertising_type"><%=StringUtil.convertNull(bean.getType())%></td>
                            <td class="table_advertising_picture"><img src="<%=StringUtil.convertNull(bean.getPicture())%>" alt="..."  style="width: 40px;"></td>
                            <td class="table_advertising_picture_link"><%=StringUtil.convertNull(bean.getPicture_link())%></td>
                            <td class="table_advertising_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_advertising_status_value"><%=bean.getDictValueByField("status") %></td>
                            <td class="table_advertising_create_time"><%=StringUtil.convertNull(bean.getCreate_time())%></td>
                            <td class="table_advertising_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td>
                            <td class="table_advertising_seq"><%=StringUtil.convertNull(bean.getSeq())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editAdvertising(this);">
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
                <h4 class="modal-title" id="myModalLabel">配置轮播信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control advertising" type="hidden" name="advertising_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">编码</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入编码" name="advertising_code"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">种类</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入种类" name="advertising_type"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">图片位置链接</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入图片位置链接" name="advertising_picture" id="advertising_picture"/>
										 <input id="upload-img" type="file" class="file form-control form-control-commit" name="postFile" title="活动页面图片链接"/>
										
									</div>
									<div class="col-xs-1">
										<label class="control-label">网址链接</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入图片超链接" name="advertising_picture_link"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select id="status" name="advertising_status" class="form-control">
											<option value="">请选择状态</option>
											<option value="1">可用</option>
											<option value="0">不可用</option>
										</select>									
										</div>
								<div class="col-xs-1">
										<label class="control-label">排序</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入图片排序位置" name="advertising_seq"/>
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


<input id="config_attribute_value" type="hidden" value="<%=attrBean.getValue()%>>">
    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
    <script src="js/bootstrap-fileinput.min.js"></script>
    <script src="js/init-file-input.js"></script>
	<script src="js/pc/advertising/advertising.js"></script>
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