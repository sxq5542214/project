<%@page import="com.yd.business.price.bean.PriceBean"%>
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
        <title>用户换表查询</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- App css -->
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
        
        
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
	 	<script src="js/common/dictionaryData.js" type="text/javascript"></script>
    </head>

    <body>

        <!-- Begin page -->
        <div class="wrapper" id="changeDeviceDiv">

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
                                    <h4 class="page-title">用户换表查询</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
                                	
                                		<div class="row">
                                			<div class="col">
												<select id="cm_type" name="cm_type" class="form-control" >
													<option value="">全部</option>
													<option value="0">更换模块</option>
													<option value="1">更换整表</option>
													<option value="2">更换电池</option>
													<option value="3">其他</option>
													<option value="4">更换表具类型</option>
												</select>
										    </div>
										    <div class="col">
												<input type="text" class="form-control" id="u_name" name="u_name"
													placeholder="请输入用户姓名">
											</div>
											<div class="col">
												<input type="text" class="form-control" id="u_cardno" name="u_cardno"
													placeholder="请输入用户编号">
											</div>
											<div class="col">
												<input type="text" class="form-control" id="u_phone" name="u_phone"
													placeholder="请输入用户手机号">
											</div>
                                		</div>
										<div class="row" style="margin-top: 10px;">
											 <div class="col-10">
												<button type="button" class="btn btn-success" onclick="readCardAndQueryChangeDevice();">读卡并查询</button>	
										    </div>
											 <div class="col-2">
										      	<button type="button" class="btn btn-primary" onclick="queryChangeDeviceData();">查询换表记录</button>
										    </div>
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                        <div class="table-responsive" style="min-height: 150px;">
                                            <table class="table  mb-0 table-hover table-centered text-nowrap" id="dataTable"  >
                                                <thead>
                                                    <tr>
												      <th scope="col">#编号</th>
												      <th scope="col">用户名称</th>
												      <th scope="col">操作类型</th>
												      <th scope="col">旧表厂商</th>
												      <th scope="col">新表厂商</th>
												      <th scope="col">操作员</th>
												      <th scope="col">操作时间</th>
												      <th scope="col">备注</th>
												      <th scope="col">旧表表号</th>
												      <th scope="col">旧表止码</th>
												      <th scope="col">新表表号</th>
												      <th scope="col">新表止码</th>
												      <th scope="col">用户地址</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
												<!--   <tr v-for="(cm,index) in changeMeterList" @click="getData(index)" :for="'radio'+index" >
												      <th> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{cm.cm_id }}</th>
												      <td>{{cm.user_name   }}</td>
												      <td>{{getDescByBeanAttrValue("ChangeMeterBean","cm_type",cm.cm_type)}}</td>
												      <td>{{cm.old_device_company}}</td>
												      <td>{{cm.new_device_company}}</td>
												      <td>{{cm.operator_name}}</td>
												      <td>{{cm.cm_happendate}}</td>
												      <td>{{cm.cm_remark }}</td>
												      <td>{{cm.cm_oldmeterno }}</td>
												      <td>{{cm.cm_oldmetercode }}</td>
												      <td>{{cm.cm_newmeterno }}</td>
												      <td>{{cm.cm_newmetercode }}</td>
												      <td>{{cm.user_address}}</td>
												  </tr> -->
                                                </tbody>
                                            </table>
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

        </div>
        <!-- END wrapper -->


<!-- App js -->
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

 		<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.min.js"></script>
 		<script type="text/javascript"  src="https://cdn.jsdelivr.net/npm/datatables.net@1.11.3/js/jquery.dataTables.min.js"></script>
 		
		<script type="text/javascript" src="js/client/windowsClient.js"></script>
		<script src="page/frame/user/js/userChangeDeviceQuery.js" type="text/javascript"></script>
 
    </body>
</html>
