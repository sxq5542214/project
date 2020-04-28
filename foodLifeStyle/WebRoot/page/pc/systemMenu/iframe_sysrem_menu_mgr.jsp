<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.system.bean.SystemRoleBean"%>
<%@page import="com.yd.business.system.bean.SystemMenuBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<SystemRoleBean>  roleList = (List<SystemRoleBean>)request.getAttribute("roleList");
	List<SystemMenuBean>  menuList = (List<SystemMenuBean>)request.getAttribute("menuList");
%>
<!DOCTYPE html>
<html lang="zh">
<head>
  <base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>系统菜单管理</title>
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
        <div class="col-sm-3">
		<div class="widget">
		<div class="widget-header">
			<div class="title">菜单</div>
		</div>
		<div class="widget-body">
		<div class="form-group">
            <input type="text" class="form-control" id="input-expand-node" placeholder="输入菜单名称模糊查询..." value="">
          </div>
          <hr>
          <div id="treeview-expandible" class="" style="overflow: auto;"></div>
        </div>
		</div>
		</div>
        <div class="col-sm-6">
		<div class="widget">
		<div class="widget-header">
			<div class="title">详细信息</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class=" col-md-12 column">
			<ul class="breadcrumb">
				<li class='active'>菜单详细</li>
			</ul>
			<hr>
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-children-node">
        			 添加子级菜单
    			</label>
    			<label class="btn btn-primary" id="add-siblings-node">
        			添加同级菜单
    			</label>
    			<label class="btn btn-danger" id="delete-node">
        			删除菜单
    			</label>
			</div>
			<form role="form" id="form_menu" class="form-horizontal">
				<div class="form-group">
					<input type="hidden" class="form-control form-control-commit" id="form_menu_id" name="id" />
					<input type="hidden" class="form-control" id="form_menu_nodeId" />
					<div class=" col-md-6">
					<label for="">名称</label><input type="text" class="form-control form-control-commit" id="form_menu_text" name="name" />
					</div>
					<div class=" col-md-6">
					 <label for="">编码</label><input type="text" class="form-control form-control-commit" id="form_menu_code" name="code" />
					</div>
				</div>
				<div class="form-group">
				<div class=" col-md-6">
					 <label for="">状态</label>
					 <select class="form-control form-control-commit"  id="form_menu_status"name="status">
					 <%for(DictionaryBean dic : dicMap.get("status") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
				</div>
				<div class=" col-md-6">
					 <label for="">类型</label>
					 <select class="form-control form-control-commit" id="form_menu_type" name="type">
					 <%for(DictionaryBean dic : dicMap.get("type") ){ %>
					<option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
				</div>
				</div>
				<div class="form-group">
				
				<div class=" col-md-4">
					 <label for="">所属上级</label>
					 <select class="form-control form-control-commit" id="form_menu_parentid" name="parentid">
					 <%for(SystemMenuBean menu : menuList ){ %>
					<option value="<%=menu.getId() %>"><%=menu.getName() %></option>
		  			<%} %>
					 </select>
				</div>
				<div class=" col-md-4">
					 <label for="" style="width:100%">菜单图标</label>
					 <div class="btn-group">
					 <a class="btn btn-default" style="width: 20%;" target="_blank" href="http://www.thinkcmf.com/font/icons#web-application">
					 <i id="ico-show" class="fa fa-comments-o"></i></a> 
					 <input class="form-control form-control-commit" id="form_menu_icon_path" style="width:80%;"  type="text" name="icon_path" placeholder="请输入图标链接..."  value="" /> 
					</div>
				</div>
				<div class=" col-md-4">
					 <label for="" style="width:100%">排序</label>
					 <div class="btn-group"> 
           				<a class="btn btn-default btn-add" style="width: 20%;" href="javascript:return false;"onclick="minusNum(this);"><span>-</span></a> 
           				<input style="width: 60%;" type="text" name="seq" placeholder="请输入..." class="btn btn-default form-control form-control-commit" value="" id="form_menu_seq" /> 
           				<a class="btn btn-default btn-minus" style="width: 20%;" href="javascript:return false;" onclick="addNum(this);"><span>+</span></a> 
          			</div> 
				</div>
				</div>
				<div class="form-group">
					 <label for="">跳转链接</label>
					 <input type="text" class="form-control form-control-commit" id="form_menu_path" name="path" />
				</div>
				<div class="form-group">
					 <label for="">菜单描述</label>
					   <textarea rows="2" class="form-control form-control-commit" id="form_menu_description" name="description"></textarea> 
				</div>
				 <button type="button" class="btn btn-default disabled" id="submit-form" >保存</button>
			</form>
		</div>
	</div>
</div>
		</div>
		</div>
          
        </div>
        
        <div class="col-sm-3">
		<div class="widget">
		<div class="widget-header">
			<div class="title">实时分配到权限组</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class="column">
			<ul class="breadcrumb">
				<li class='active'>权限组详细</li>
			</ul>
			<hr>
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
				<input type="hidden" class="form-control form-control-commit" id="rela_menu_id" />
    			<label class="btn btn-primary"  id="add-menu-role" title="添加">
    				<i class="fa fa-plus"></i>
    			</label>
    			<label class="btn btn-success btn-menu-role" name="modify-menu-role-status" title="生效">
        			<i class="fa fa-check"></i>
    			</label>
    			<label class="btn btn-warning btn-menu-role"  name="modify-menu-role-status-un" title="失效">
        			<i class="fa fa-ban"></i>
    			</label>
    			<label class="btn btn-danger btn-menu-role" name="delete-menu-role" title="删除">
        			<i class="fa fa-trash-o"></i>
    			</label>
			</div>
			<table id="system_menu_role_relation_table" class="table table-condensed table-striped table-hover table-bordered pull-left dataTable">
				<thead>
					<tr>
						<th style="width:10px"> <input type="checkbox" onclick="checkAll(this);" /> </th>
						<th> 名称 </th>
						<th style="width:55px"> 状态 </th>
					</tr>
				</thead>
				<tbody id="system_menu_role_relation_table_body">
				</tbody>
			</table>
		</div>
	</div>
</div>
		</div>
		</div>
          
        </div>
        
      </div>
      </div>
	</div>
	<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">权限组选择</h4>
            </div>
            <div class="modal-body">
            <div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered " id="roles-table">
						<thead>
							<tr>
								<th style="width:20px;"><input type="checkbox"  onclick="checkAll(this);" />
								<input class="menu_id" type="hidden" />
								</th>
								<th>名称</th>
								<th>编码</th>
								<th>创建时间</th>
							</tr>
						</thead>
						<tbody>
							<%for(SystemRoleBean roleBean:roleList){%>
								<tr class="gradeA">
									<td><input class="role_id" type="checkbox" value="<%=roleBean.getId() %>" /></td>
									<td class="role_name"><%=roleBean.getName() %></td>
									<td><%=roleBean.getCode() %></td>
									<td><%=roleBean.getCreate_time() %></td>
								</tr>
							<%} %>
						</tbody>
					</table>
					</div>
				</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default pull-left" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary pull-left" id="commit_role">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
	<script src="page/pc/js/jquery.js"></script>	
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-treeview.js"></script>
	<script src="page/pc/systemMenu/js/config_menu.js"></script>
	<script type="text/javascript">
      //Data Tables
      $(document).ready(function () {
        $('#roles-table').dataTable({
        //checkbox不排序
         "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
         "aaSorting": [[1, "desc"]],
          "bPaginate":true,
          "iDisplayLength":5,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });

    </script>
</body>
</html>