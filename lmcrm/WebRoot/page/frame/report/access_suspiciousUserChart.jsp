<%@page import="com.yd.util.DateUtil"%>
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
        <title>疑户查询</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

	<link  rel="stylesheet" href="/staticFiles/jstree-bootstrap-theme/dist/themes/proton/style.min.css">
	<link rel="stylesheet" href="/staticFiles/jstree@3.3.12/dist/themes/default/style.min.css">
            <!-- App css -->
        <link href="/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
		<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
 		<script src="/staticFiles/vue/dist/vue.min.js"></script>
		<script	src="/staticFiles/jstree@3.3.12/dist/jstree.min.js"></script>
	 	<script src="js/common/dictionaryData.js" type="text/javascript"></script>
	 	<script src="js/common/exportExcel.js" type="text/javascript"></script>
	 	
</head>

    <body>

        <!-- Begin page -->
        <div class="wrapper" id="userManagerDiv">

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
                                    <h4 class="page-title">疑户查询</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
                                	<div class="row">
			                         	<%-- <div class="col">
											起始时间：<input type="text" id="start_date" value="<%=DateUtil.getNowOlnyDateStr() %>"  name="start_date" class="form-control date" data-date-format="yyyy-mm-dd" data-provide="datepicker" data-date-autoclose="true" >
										</div>
										<div class="col">
											结束时间：<input type="text" id="end_date"  value="<%=DateUtil.getTomorrowDateStr() %>" name="end_date" class="form-control date"  data-date-format="yyyy-mm-dd" data-provide="datepicker" data-date-autoclose="true">
										</div>
										<div class="col">
											操作员：
											<select name="operator_id" id="operator_id" class="form-control">
												<option value="-1">全部</option>
												<option v-for="operator in operatorList" :value="operator.o_id">{{operator.o_name}}</option>
											</select>
										</div>
										<div class="col">
											价格类型：
											<select name="price_id" id="price_id" class="form-control">
												<option value="-1">全部</option>
												<option v-for="price in priceList" :value="price.p_id">{{price.p_name}}</option>
											</select>
										</div>
										<div class="col">
											水表类型：
											<select name="dk_id" id="dk_id" class="form-control">
												<option value="-1">全部</option>
												<option v-for="dk in deviceKindList" :value="dk.dk_id">{{dk.dk_name}}</option>
											</select>
											<input
												type="hidden" class="form-control" id="addressId"
												name="addressId">
										</div>
			                         </div> --%>
											<div class="col-8">
												
 										    </div>
											 <div class="col-4">
												<button type="button" class="btn btn-success" onclick="queryBillData();">查询数据</button>
												&nbsp;&nbsp;&nbsp;&nbsp;
												<button type="button" class="btn btn-info" onclick="exportExcel()">导出数据</button>
											
											</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body" style="padding-top: 0px;">
                                    	
									  <div class="row">
									   <!--    <div class="col-3 " style="padding: 0">
									      		<div class="card">
									      			<div class="card-header">地址列表</div>
									      			<div class="card-body">
														<div id="tree"></div>
													</div>
									      		</div>
										  </div> -->
										  <div class="col-12">
										  	<h4 class="header-title" style="padding-top: 1.5rem;font-size: 1.1rem;">疑户清单</h4>
	                                        <div class="table-responsive" style="min-height: 150px;">
	                                            <table class="table  mb-0 table-hover table-centered text-nowrap table-bordered"  >
	                                                <thead>
	                                                    <tr>
															<th v-for="name in columnNames" scope="col">{{name}}</th>
													<!-- 		<th scope="col">地址</th>
															<th scope="col">水表类型</th>
															<th scope="col">价格名称</th>
															<th scope="col">支付金额</th>
															<th scope="col">充值金额</th>
															<th scope="col">充值量</th>
															<th scope="col">基本价格</th>
															<th scope="col">操作类型</th>
															<th scope="col">操作员</th>
															<th scope="col">操作时间</th> -->
	                                                    </tr>
	                                                </thead>
	                                                <tbody >
														<tr v-for="(user,index) in dataList" @click="getUserData(index)">
														
															<td v-for="code in columnCodes" scope="col">{{user[code] }} </td>
														</tr>
	                                                </tbody>
	                                            </table>
	                                        </div> <!-- end table-responsive-->
	                                        
										  </div>
									   </div>
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
		<script src="/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
 <script src="page/frame/report/js/access_suspiciousUserChart.js"	type="text/javascript"></script>
        
    </body>
</html>
