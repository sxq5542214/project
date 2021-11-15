<%@page import="com.yd.business.operator.bean.OperatorBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
    <head>
    	<base href="<%=basePath%>">
        <meta charset="utf-8" />
        <title>员工管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/vendor/dataTables.bootstrap4.css" rel="stylesheet" type="text/css" />
<!--         <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/vendor/responsive.bootstrap4.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/vendor/buttons.bootstrap4.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/vendor/select.bootstrap4.css" rel="stylesheet" type="text/css" />
 -->        <!-- App css -->
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
	 	<script src="js/common/dictionaryData.js" type="text/javascript"></script>
    </head>

    <body>

        <!-- Begin page -->
        <div class="wrapper" id="operatorManagerDiv">

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page" style="margin-left: 0px;">
                <div class="content" >

                    <!-- Start Content-->
                    <div class="container-fluid">
                        
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                       <!--  <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Hyper</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Tables</a></li>
                                            <li class="breadcrumb-item active">Basic Tables</li>
                                        </ol> -->
                                    </div>
                                    <h4 class="page-title">员工管理</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
                                		<%-- <div class="row">
	                                        <div class="col">
										      <select name="o_status" id="o_status" class="form-control  " data-toggle="select">
												  <option value=""  selected>请选择启用状态</option>
												  <option value="<%=OperatorBean.STATUS_ENABLE%>">启用</option>
												  <option value="<%=OperatorBean.STATUS_DISABLE%>">禁用</option>
												</select>
										    </div>
										    <div class="col">
										    	<input type="text" class="form-control" id="o_name" name="o_name" placeholder="请输入员工名称">
										    </div>
										</div> --%>
										<div class="row" style="margin-top: 10px;">
											<div class="col-10">
											    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter" onclick="addOperator();" >新增员工</button>
										      	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModalCenter" onclick="updateOperator();">修改员工</button>
										      	<button type="button" class="btn btn-secondary" >修改密码</button>
										      	<button type="button" class="btn btn-warning"  onclick="return queryRole();"  >设置权限</button>
<!-- 										      	<button type="button" class="btn btn-danger" data-toggle="modal" data-target="#areaModalCenter" onclick="queryArea();"  >设置区域</button>
 -->									    	 </div>
											<!--  <div class="col-2">
										      	<button type="button" class="btn btn-primary" onclick="queryOperatorData();">查询员工</button>
										    </div> -->
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                        <div class="table-responsive-sm" style="min-height: 150px;">
                                            <table class="table table-centered mb-0 table-hover " id="id_query_data_table" >
                                                <thead>
                                                    <tr>
												      <th scope="col">#编号</th>
												      <th scope="col">员工名称</th>
												      <th scope="col">权限类型</th>
												      <th scope="col">启用状态</th>
												    <!--   <th scope="col">审核状态</th> -->
												    <!--   <th scope="col">当前代售额度</th> -->
												    <!--   <th scope="col">创建时间</th> -->
												      <th scope="col">修改时间</th>
												      <th scope="col">归属公司</th>
<!-- 												      <th scope="col">操作</th>
 -->                                                    </tr>
                                                </thead>
                                                <tbody >
												  <tr v-for="(operator,index) in operatorList" @click="getData(index)" :for="'radio'+index" >
												      <th> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{operator.o_id }}</th>
												      <td>{{operator.o_name   }}</td>
												      <td>{{getDescByBeanAttrValue("operator","o_kind",operator.o_kind)}}</td>
												      <td>{{getDescByBeanAttrValue("operator","o_status",operator.o_status)}}</td>
<!-- 												      <td>{{getDescByBeanAttrValue("operator","o_openaudit",operator.o_openaudit)}}</td>
												      <td>{{operator.o_limitmoney >= 0 ? operator.o_limitmoney : "关闭" }}</td>
												      <td>{{operator.o_createdate }}</td>
 -->												      <td>{{operator.o_updatedate }}</td>
												      <td>{{operator.company_name}}</td>
<!-- 												      <td class="table-action"><a href="javascript: void(0);" class="action-icon"> <i class="mdi mdi-pencil"></i></a></td>
 -->												  </tr>
                                                </tbody>
                                            </table>
                                           <!--  <div class="row">
	                                            <div class="col-sm-12 col-md-5">
	                                            <div class="dataTables_info" id="basic-datatable_info" role="status" aria-live="polite">
	                                            Showing 1 to 10 of 57 entries
	                                            </div>
	                                            </div>
	                                            <div class="col-sm-12 col-md-7">
	                                            <div class="dataTables_paginate paging_simple_numbers" id="basic-datatable_paginate">
	                                            <ul class="pagination pagination-rounded float-right">
	                                            <li class="paginate_button page-item previous disabled" id="basic-datatable_previous">
	                                            <a href="#" aria-controls="basic-datatable" data-dt-idx="0" tabindex="0" class="page-link">
	                                            <i class="mdi mdi-chevron-left"></i>
	                                            </a></li>
	                                            <li class="paginate_button page-item active">
	                                            <a href="#" aria-controls="basic-datatable" data-dt-idx="1" tabindex="0" class="page-link">1</a>
	                                            </li>
	                                            <li class="paginate_button page-item ">
	                                            <a href="#" aria-controls="basic-datatable" data-dt-idx="2" tabindex="0" class="page-link">2</a>
	                                            </li>
	                                            <li class="paginate_button page-item next" id="basic-datatable_next">
	                                            <a href="#" aria-controls="basic-datatable" data-dt-idx="3" tabindex="0" class="page-link">
	                                            <i class="mdi mdi-chevron-right"></i></a></li>
	                                            </ul>
	                                            </div>
	                                            </div>
                                            </div> -->
                                            
                                        </div> <!-- end table-responsive-->

                                    </div> <!-- end card body-->
                                </div> <!-- end card -->
                            </div><!-- end col-->

                        
                        </div>
                        <!-- end row-->

                        
                    </div> <!-- container -->

                </div> <!-- content -->

            </div>

            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->

<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered   modal-lg modal-dialog-scrollable" role="document">
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
		      <div class="col-md-2  align-self-center">归属公司</div>
		      <div class="col-md-4">
      		      <select id="updateform_companyid" name="o_companyid" class="form-control">
			 			<option   v-for=" company in companyList" :value="company.c_id">{{company.c_name }} </option>
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
        <!-- END wrapper -->


<!-- App js -->
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

<!--         <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/jquery.dataTables.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dataTables.bootstrap4.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dataTables.responsive.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/responsive.bootstrap4.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dataTables.buttons.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/buttons.bootstrap4.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/buttons.html5.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/buttons.flash.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/buttons.print.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dataTables.keyTable.min.js"></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dataTables.select.min.js"></script>
         -->
        <script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.min.js"></script>
		<script src="page/frame/operator/js/operatorManager.js" type="text/javascript"></script>
 
 
    </body>
</html>
