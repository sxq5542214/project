<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.other.bean.ConfigAttributeBean"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	List<WechatOriginalInfoBean> originalList = (List<WechatOriginalInfoBean>)request.getAttribute("originalList");
	ConfigAttributeBean bean = (ConfigAttributeBean)request.getAttribute("bean");
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<ConfigAttributeBean> list = pd.getDataList();
	
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<title>万大商城－微信素材分发管理</title>
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
<link href="page/pc/css/preview_base.css" rel="stylesheet">
<link href="KindEditor/themes/default/default.css" rel="stylesheet" type="text/css">
</head>

<body style="background-color: #f7f7f7;" >
<div class="row" >
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						配置信息管理
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form id="conditionFrom" class="form-horizontal row-border" action="admin/system/queryConfigAttribute.do" method="post">
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">名称：</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入名称模糊查询" name="query_config_attribute_name" value="<%=StringUtil.convertNull(bean.getName()) %>"/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">值：</label>
									</div>
									<div class="col-xs-4">
										<input type="text" name="query_config_attribute_value"  placeholder="输入值模糊查询" class="form-control" value="">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">所属公众号：</label>
								</div>
									<div class="col-xs-4">
										<select name="originalid" class="form-control">
										<option value="-1">全部</option>
											<%for(WechatOriginalInfoBean originalBean : originalList ){ 
												String selected = "";
												if(originalBean.getOriginalid() != null &&  originalBean.getOriginalid().equals(bean.getOriginalid())){
													selected = "selected=\"selected\"";
												}
											%>
											<option value="<%=originalBean.getOriginalid() %>" <%=selected %>><%=originalBean.getOriginal_name() %></option>
											<%} %>
				                        </select>
									</div>
									<div class="col-xs-2">
										<label class="control-label">状态：</label>
									</div>
									<div class="col-xs-4">
										<select name="query_config_attribute_status" class="form-control">
										<option value="-1">全部</option>
										<%for(DictionaryBean dic : dicMap.get("status") ){ 
											String selected = "";
											if(dic.getValue() != null &&  dic.getValue().equals(bean.getStatus())){
												selected = "selected=\"selected\"";
											}%>
										<option value="<%=dic.getValue() %>" <%=selected %>><%=dic.getDescription() %></option>
		  								<%} %>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
							<a style="margin-left: 10px;" type="modify"  class="btn btn-success pull-left" data-toggle="modal" data-target="#myModal">新增</a>
							<a style="margin-left: 10px;" type="cancle"  class="btn btn-warning pull-right" onclick="cleanBox();">清空</a>
								<input type="submit" id="submitSupplier" class="btn btn-info pull-right" value="搜索"/>  
								<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
							</div>
						</div>
					</form>
					<div id="dt_example" class="example_alt_pagination">
						<table class="table table-condensed table-striped table-hover table-bordered pull-left" style="word-break:break-all; word-wrap:break-all;">
							<thead id="supplier_list_head">
								<tr>
									<th style="width: 4%">
										<input type="checkbox" class="no-margin" onclick="checkAll(this);" />
									</th>
									<th style="width: 14%">名称</th>
									<th style="width: 14%">编码</th>
									<th style="width: 7%">所属众号</th>
									<th style="width: 25%">值</th>
									<th style="width: 10%">创建时间</th>
									<th style="width: 10%">修改时间</th>
									<th style="width: 5%">状态</th>
									<th style="width: 12%">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<tr class="gradeA" style="display:none;">
									<td class="table_conf_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_conf_name"></td>
									<td class="table_conf_code"></td>
									<td class="table_conf_original_name"></td>
									<td class="table_conf_value"></td>
									<td class="table_conf_create_time"></td>
									<td class="table_conf_modify_time"></td>
									<td class="table_conf_status_value"></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<%
									for (ConfigAttributeBean mater : list) {
								%>
								<tr class="gradeA ">
									<td class="table_conf_id">
									<input type="checkbox" class="table_conf_id" name="config_attribute" value="<%=mater.getId() %>" />
									<input type="hidden" class="table_conf_status" value="<%=StringUtil.convertNull(mater.getStatus()) %>" />
									<input type="hidden" class="table_conf_originalid" value="<%=StringUtil.convertNull(mater.getOriginalid()) %>" />
									</td>
									<td class="table_conf_name"><%=StringUtil.convertNull(mater.getName())%></td>
									<td class="table_conf_code"><%=StringUtil.convertNull(mater.getCode())%></td>
									<td class="table_conf_original_name"><%=StringUtil.convertNull(mater.getOriginal_name())%></td>
									<td class="table_conf_value"><%=StringUtil.convertNull(mater.getValue())%></td>
									<td class="table_conf_create_time"><%=StringUtil.convertNull(mater.getCreate_time())%>
									</td>
									<td class="table_conf_modify_time"><%=StringUtil.convertNull(DateUtil.formatDate(mater.getModify_time()))%>
									</td>
									<td class="table_conf_status_value"><%=StringUtil.convertNull(mater.getDictValueByField("status"))%>
									</td>
									<td>
										<a class="btn btn-success " href="javascript:return false;" data-toggle="modal" data-target="#myModal" onclick="editConfigAttribute(this);" style="padding: 0px 4px;margin-left:5px;">
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
						<div class="clearfix"></div>
					</div>
					<button id='delAll' class="btn btn-danger" data-toggle="popover" data-placement="top" onclick="deleteAllitem();">
						<i class="fa fa-times-circle"></i> 批量删除
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="width:70%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">编辑配置信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
            	<form class="form-horizontal" role="form" id = "config_attribute_form">
            	<input class="form-control config_attribute" type="hidden" name="config_attribute_id" value="" >
  <div class="form-group form-group1">
    <label class="col-sm-2 control-label">名称：</label>
    <div class="col-sm-10">
      <input class="form-control config_attribute" type="text" name="config_attribute_name" placeholder="请输入名称..." >
    </div>
  </div>
  <div class="form-group form-group1">
    <label for="inputPassword" class="col-sm-2 control-label">编码：</label>
    <div class="col-sm-10">
      <input class="form-control config_attribute"  type="text" name="config_attribute_code" placeholder="请输入编码..." >
    </div>
  </div>
    <div class="form-group">
    <div class="form-group1">
      <label for="disabledSelect" class="col-sm-2 control-label">公众号：</label>
      <div class="col-sm-4">
        <select  class="form-control config_attribute" name="config_attribute_originalid">
          <%for(WechatOriginalInfoBean originalBean : originalList ){%>
			<option value="<%=originalBean.getOriginalid() %>"><%=originalBean.getOriginal_name() %></option>
		  <%} %>
        </select>
      </div>
      </div>
      <div class="form-group1">
       <label for="disabledSelect" class="col-sm-2 control-label">状态：</label>
      <div class="col-sm-4">
        <select  class="form-control config_attribute" name="config_attribute_status">
          <%for(DictionaryBean dic : dicMap.get("status") ){ 
				%>
			<option value="<%=dic.getValue() %>" ><%=dic.getDescription() %></option>
		  <%} %>
        </select>
      </div>
       </div>
    </div>
    <div class="form-group form-group1">
    <label class="col-sm-2 control-label">创建时间：</label>
    <div class="col-sm-4">
      <input class="form-control " type="text" name="config_attribute_create_time" placeholder="<%=DateUtil.getNowDateStr() %>" disabled>
    </div>
    <label for="inputPassword" class="col-sm-2 control-label">修改时间：</label>
    <div class="col-sm-4">
      <input class="form-control "  type="text" name="config_attribute_modify_time" placeholder="" disabled>
    </div>
  </div>
      <div class="form-group form-group1">
    <label for="inputPassword" class="col-sm-2 control-label">值：</label>
    <div class="col-sm-10">
  <textarea class="form-control config_attribute" id="content" name="config_attribute_value" style="width: 100%; height: 200pt; display: none;"></textarea> 
  <br />
    </div>
  </div>
</form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" onclick="commitData(this);">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
	<script src="page/pc/js/jquery.js"></script>
	
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="KindEditor/kindeditor-min.js"></script>
	<script src="KindEditor/lang/zh_CN.js"></script>
	<script src="page/pc/attribute/js/config_attribute.js"></script>

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
    </script>

</body>
</html>