<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.system.bean.SystemRoleBean"%>
<%@page import="com.yd.business.system.bean.SystemMenuBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<SystemRoleBean>  roleList = (List<SystemRoleBean>)request.getAttribute("roleList");
	List<SystemMenuBean>  menuList = (List<SystemMenuBean>)request.getAttribute("menuList");
	List<CustomerBean>  customerList = (List<CustomerBean>)request.getAttribute("customerList");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>系统权限管理</title>
<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="page/pc/css/new.css" rel="stylesheet">
<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">

<style type="text/css">
.htmleaf-header {
	margin-bottom: 15px;
	font-family: "Segoe UI", "Lucida Grande", Helvetica, Arial,
		"Microsoft YaHei", FreeSans, Arimo, "Droid Sans",
		"wenquanyi micro hei", "Hiragino Sans GB", "Hiragino Sans GB W3",
		"FontAwesome", sans-serif;
}

.htmleaf-icon {
	color: #fff;
}
.col-md-1, .col-md-10, .col-md-11, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11,  .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11,  .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
    padding-left: 0px;
}
</style>
<!--[if IE]>
		<script src="http://libs.useso.com/js/html5shiv/3.7/html5shiv.min.js"></script>
	<![endif]-->
</head>
<body style="background-color: #f7f7f7;">
	<div class="htmleaf-container">
		<div class="container">
	  <div class="row">
	  <div class="col-sm-6">
		<div class="widget">
		<div class="widget-header">
			<div class="title">权限组</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class="column">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
				<input type="hidden" class="form-control form-control-commit" id="rela_menu_id" />
    			<label class="btn btn-primary" title="添加" onclick="showModalBox('ADD');">
    				<i class="fa fa-plus"></i>
    			</label>
    			<label class="btn btn-success btn-menu-role" name="modify-menu-role-status" title="生效" onclick="changeRoleStatusById(this,1);">
        			<i class="fa fa-check"></i>
    			</label>
    			<label class="btn btn-warning btn-menu-role"  name="modify-menu-role-status-un" title="失效" onclick="changeRoleStatusById(this,0);">
        			<i class="fa fa-ban"></i>
    			</label>
    			<label class="btn btn-danger btn-menu-role" name="delete-menu-role" title="删除" onclick="deleteRoles(this);">
        			<i class="fa fa-trash-o"></i>
    			</label>
			</div>
			<div id="dt_example" class="example_alt_pagination">
			<table id="system_role_table" class="table table-condensed table-striped table-hover table-bordered pull-left dataTable" style="word-break:break-all; word-wrap:break-all;">
				<thead>
					<tr>
						<th style="width:10px;"> <input type="checkbox" onclick="checkAll(this);" /> </th>
						<th style="width:90px;"> 名称 </th>
						<th style="display:none;"> 名称 </th>
						<th style="width:90px;"> 编码 </th>
						<th style="width:60px;"> 状态 </th>
						<th style="width:60px;"> 操作 </th>
						<th style="display:none;"> 描述 </th>
					</tr>
				</thead>
				<tbody id="system_role_table_body">
							<%for(SystemRoleBean roleBean:roleList){
								String color = "#E91E63";
								if(roleBean.getStatus() == 1){
									color = "#333";
								}
							%>
								<tr class="gradeA role-list" style="cursor: pointer; color:<%=color %>" > 
									<td class="config_role_table_default">
									<input  class="config_role_table_id" type="checkbox" value="<%=roleBean.getId() %>" />
									<input  class="config_role_table_status" type="hidden" value="<%=roleBean.getStatus() %>" />
									</td>
									<td style="display:none;"style="display:none;"></td>
									<td class="config_role_table_name"><%=roleBean.getName() %></td>
									<td class="config_role_table_code"><%=roleBean.getCode() %></td>
									<td class="config_role_table_status_value"><%=roleBean.getDictValueByField("status") %></td>
									<td><div class="btn-group" data-toggle="buttons">
									<label class="btn btn-primary btn-menu-role btn-xs"  title="修改" onclick="editConfigComment(this);">
        								<i class="fa fa-pencil"></i>
    								</label>
    								<label class="btn btn-danger btn-menu-role btn-xs"  title="删除" onclick="Sup.delSupplier(this,<%=roleBean.getId() %>,'singl');">
        								<i class="fa fa-trash-o"></i>
    								</label>
    								<label class="btn btn-primary btn-menu-role-open btn-xs"  title="展示关联信息" onclick="Sup.listSupplier(<%=roleBean.getId() %>);">
        								<i class="fa fa-folder-open"></i>
    								</label>
    								</div></td>
									<td class="config_role_table_description" style="display:none;"><%=StringUtil.convertNull(roleBean.getDescription()) %></td>
								</tr>
							<%} %>
				</tbody>
			</table>
			</div>
		</div>
	</div>
</div>
		</div>
		</div>
          
        </div>
        <div class="col-sm-3">
		<div class="widget">
		<div class="widget-header">
			<div class="title">权限【系统管理员】下菜单列表</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class="column">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
				<input type="hidden" class="form-control form-control-commit" id="rela_menu_id" />
    			<label class="btn btn-primary"  id="add-menu-role" title="添加" onclick="getItemListNotExist();">
    				<i class="fa fa-plus"></i>
    			</label>
    			<label class="btn btn-success btn-menu-role" name="modify-rela-status" title="生效" onclick="dealItemRoleRale(this,'menu','all','eff');">
        			<i class="fa fa-check"></i>
    			</label>
    			<label class="btn btn-warning btn-menu-role"  name="modify-rela-status-un" title="失效" onclick="dealItemRoleRale(this,'menu','all','loseEff');">
        			<i class="fa fa-ban"></i>
    			</label>
    			<label class="btn btn-danger btn-menu-role" name="delete-rela" title="删除" onclick="dealItemRoleRale(this,'menu','all','delete');">
        			<i class="fa fa-trash-o"></i>
    			</label>
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_role_menu column">
			
			</div>
		</div>
	</div>
</div>
		</div>
		</div>
          
        </div><div class="col-sm-3">
		<div class="widget">
		<div class="widget-header">
			<div class="title">权限【系统管理员】下用户列表</div>
		</div>
		<div class="widget-body">
		<div class="container">
	    <div class="row clearfix">
		<div class="column">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
				<input type="hidden" class="form-control form-control-commit" id="rela_menu_id" />
    			<label class="btn btn-primary"  id="add-admin-role" title="添加">
    				<i class="fa fa-plus"></i>
    			</label>
    			<label class="btn btn-success btn-menu-role" name="modify-rela-status" title="生效"  onclick="dealItemRoleRale(this,'admin','all','eff');">
        			<i class="fa fa-check"></i>
    			</label>
    			<label class="btn btn-warning btn-menu-role"  name="modify-rela-status-un" title="失效" onclick="dealItemRoleRale(this,'admin','all','loseEff');">
        			<i class="fa fa-ban"></i>
    			</label>
    			<label class="btn btn-danger btn-menu-role" name="delete-rela" title="删除" onclick="dealItemRoleRale(this,'admin','all','delete');">
        			<i class="fa fa-trash-o"></i>
    			</label>
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_role_admin">
			</div>
		</div>
	</div>
</div>
		</div>
		</div>
          
        </div>
        
        
      </div>
      </div>
	</div>
	
	<!-- 模态框（新增或者修改权限组） -->
	<div class="modal fade" id="myModal-role" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">权限详细信息</h4>
            </div>
            <div class="modal-body">
            <div class="container">
	<div class="row clearfix">
            <div class=" col-md-12 column">
            <form role="form" id="form_role" class="form-horizontal">
				<div class="form-group">
					<input type="hidden" class="form-control control_role control_role_id" id="form_role_id" name="control_role_id" />
					<div class=" col-md-12">
					<label for="">名称</label><input type="text" class="form-control control_role control_role_name" id="form_role_name" name="control_role_name" />
					</div>
				</div>
				<div class="form-group">
					<div class=" col-md-12">
					 <label for="">编码</label><input type="text" class="form-control control_role control_role_code" id="form_role_code" name="control_role_code" />
					</div>
				</div>
				<div class="form-group">
				<div class=" col-md-12">
					 <label for="">状态</label>
					 <select class="form-control control_role control_role_status"  id="form_role_status"name="control_role_status">
					 <%for(DictionaryBean dic : dicMap.get("status") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
				</div>
				</div>
				<div class="form-group">
				<div class=" col-md-12">
					 <label for="">权限描述</label>
					   <textarea rows="2" class="form-control control_role control_role_description" id="form_role_description" name="control_role_description"></textarea> 
				</div>
				</div>
			</form>
			</div>
			</div>
			</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary pull-left" id="commit_role" onclick="commitData();">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	
	<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal-menu" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">系统菜单选择</h4>
                <h6 class="modal-title">[选择系统的子菜单]</h6>
            </div>
            <div class="modal-body">
            <div id="dt_example" class="example_alt_pagination_menu">
					
					</div>
				</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary pull-left" id="commit_role_menu">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<div class="modal fade" id="myModal-admin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">系统用户选择</h4>
            </div>
            <div class="modal-body">
            <div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered " id="data-table-admin">
						<thead>
							<tr>
								<th style="width:20px;"><input type="checkbox"  onclick="checkAll(this);" />
								<input class="menu_id" type="hidden" />
								</th>
								<th>用户名</th>
								<th>账号</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody>
							<%for(CustomerBean customer:customerList){%>
								<tr class="gradeA">
									<td><input class="admin_id" type="checkbox" value="<%=customer.getId() %>" /></td>
									<td class="admin_name"><%=StringUtil.convertNull(customer.getName()) %></td>
									<td><%=StringUtil.convertNull(customer.getUsername()) %></td>
									<td><%=StringUtil.convertNull(customer.getDictValueByField("status")) %></td>
								</tr>
							<%} %>
						</tbody>
					</table>
					</div>
				</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary pull-left" id="commit_role_admin">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

	<script src="page/pc/js/jquery.js"></script>	
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/fnReloadAjax.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-treeview.js"></script>
	<script src="js/Util.js"></script>
	<script src="page/pc/systemRole/js/config_role.js"></script>
		
	
	<script type="text/javascript">
      //Data Tables
      $(document).ready(function () {
        $('#system_role_table').dataTable({
        //checkbox不排序
         "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
         "aaSorting": [[1, "desc"]],
          "bPaginate":true,
          "iDisplayLength":15,
          "sPaginationType": "full_numbers"
        });
        
      });

    </script>
</body>
</html>