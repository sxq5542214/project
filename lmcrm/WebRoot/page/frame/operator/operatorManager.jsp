<%@page import="com.yd.business.operator.bean.OperatorBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
  <head>
    <!-- 必须的 meta 标签 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    .container{padding: 1px; margin-right: 1px; margin-left : 1px;max-width:1940px; }
    </style>
  </head>
 
<body> 
<div id="operatorManagerDiv">
<div class="container">

	 <div class="form-group align-items-center">
	 
	 </div>
	 <div class="row form-group align-items-center">
	    <div class="col">
	      <select name="o_status" id="o_status" class="form-control">
			  <option value=""  selected>请选择启用状态</option>
			  <option value="<%=OperatorBean.STATUS_ENABLE%>">启用</option>
			  <option value="<%=OperatorBean.STATUS_DISABLE%>">禁用</option>
			</select>
	    </div>
	    <div class="col">
	    	<input type="text" class="form-control" id="o_name" name="o_name" placeholder="请输入员工名称">
	    </div>
 	 </div>
 	 
	 <div class="form-group align-items-center">
	 	<div class="row">
			 <div class="col-10">
			    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter" onclick="addOperator();" >新增员工</button>
		      	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModalCenter" onclick="updateOperator();">修改员工</button>
		      	<button type="button" class="btn btn-secondary" >修改密码</button>
		      	<button type="button" class="btn btn-warning"  onclick="return queryRole();"  >设置权限</button>
		      	<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#areaModalCenter" onclick="queryArea();"  >设置区域</button>
<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
 -->		    </div>
			 <div class="col-2">
		      	<button type="button" class="btn btn-primary" onclick="queryOperatorData();">查询员工</button>
		    </div>
	    </div>
	    
	 </div>
</div>

<div class="container" style="overflow:scroll;" >
	<table class="table table-striped table-hover table-sm" >
  <thead>
    <tr>
      <th scope="col">#编号</th>
      <th scope="col">员工名称</th>
      <th scope="col">权限类型</th>
      <th scope="col">启用状态</th>
      <th scope="col">审核状态</th>
      <th scope="col">当前代售额度</th>
      <th scope="col">创建时间</th>
      <th scope="col">修改时间</th>
      <th scope="col">营业所</th>
    </tr>
  </thead>
  <tbody>
  
  <tr v-for="(operator,index) in operatorList" @click="getData(index)" :for="'radio'+index" >
      <th> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{operator.o_id }}</th>
      <td>{{operator.o_name   }}</td>
      <td>{{getDescByBeanAttrValue("operator","o_kind",operator.o_kind)}}</td>
      <td>{{getDescByBeanAttrValue("operator","o_status",operator.o_status)}}</td>
      <td>{{getDescByBeanAttrValue("operator","o_openaudit",operator.o_openaudit)}}</td>
      <td>{{operator.o_limitmoney >= 0 ? operator.o_limitmoney : "关闭" }}</td>
      <td>{{operator.o_createdate }}</td>
      <td>{{operator.o_updatedate }}</td>
      <td>{{operator.company_name}}</td>
      
  </tr>
  
  </tbody>

</table>
</div>



<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered  modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">新增/修改员工</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
		    <div class="row">
		      <div class="col-md-2 align-self-center">员工名称</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="o_name" class="form-control" placeholder="请输入员工名称">
					<input type="hidden" name="o_id" class="form-control" >
				</div>
		      
		      <div class="col-md-2  align-self-center">登录密码</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="o_password2" class="form-control" placeholder="请输入登录密码">
				</div>
		      
		    </div>
		    <div class="row">
		      <div class="col-md-2 align-self-center">权限类型</div>
		      <div class="col-md-4">
		      		      <select name="o_kind" class="form-control">
							  <option value="<%=OperatorBean.KIND_USER %>">员工</option>
							  <option value="<%=OperatorBean.KIND_MANAGER %>">主管</option>
							</select>
		      	</div>
		      <div class="col-md-2  align-self-center">归属营业所</div>
		      <div class="col-md-4">
      		      <select name="o_companyid" class="form-control">
			 			<option   v-for=" company in companyList" value="{{company.c_id }}">{{company.c_name }} </option>
					</select>
			  </div>
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">启用状态</div>
		      <div class="col-md-4 ml-auto">

		      		      <select name="o_status" class="form-control">
							  <option value="<%=OperatorBean.STATUS_ENABLE %>">启用</option>
							  <option value="<%=OperatorBean.STATUS_ENABLE %>">禁用</option>
							</select>
			  </div>
		      
		      <div class="col-md-2  align-self-center">审核状态</div>
		      <div class="col-md-4 ml-auto">
					<select name="o_openaudit" class="form-control">
					  <option value="<%=OperatorBean.OPENAUDIT_NO %>">禁用</option>
					  <option value="<%=OperatorBean.OPENAUDIT_YES %>">启用</option>
					</select>
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">当前代售金额</div>
		      <div class="col-md-10 ml-auto">

					<input type="number" name="o_limitmoney" class="form-control" placeholder="请输入代售金额，不允许代售请填-1">
			  </div>
		    </div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关 闭</button>
        <button type="button" class="btn btn-primary" onclick="addOrUpdateOperator()">保 存</button>
      </div>
    </div>
  </div>
</div>
</form>





<form name="updateRoleForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="roleModalCenter" tabindex="-1" role="dialog" aria-labelledby="roleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered  modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="roleModalCenterTitle">修改权限</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
		   	<table class="table table-striped table-hover table-sm" >
			  <thead>
			    <tr>
			      <th scope="col" onclick="choseAllMenus()"><input type="checkbox" id="selectAllMenu" >全选</th>
			      <th scope="col">一级菜单</th>
			      <th scope="col">二级菜单</th>
			      <th scope="col">菜单状态</th>
			    </tr>
			  </thead>
			  <tbody>
			  
			  <tr v-for="(menu,index) in menuList"  :for="'menu'+index"  @click="checkRole(index)">
			      <th> <input type="checkbox" :id="'menu'+index" name="menu_ids" :value="menu.id" v-model="checkedRows" > </th>
			      <td>{{menu.parent_name   }}</td>
			      <td>{{menu.name   }}</td>
			      <td>{{getDescByBeanAttrValue("SystemMenuBean","status",menu.status)}}</td>
			      
			  </tr>
			  
			  </tbody>
			
			</table>


		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关 闭</button>
        <button type="button" class="btn btn-primary" onclick="addOrUpdateOperator()">保 存</button>
      </div>
    </div>
  </div>
</div>
</form>




</div>
</body>

<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/operator/js/operatorManager.js" type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>