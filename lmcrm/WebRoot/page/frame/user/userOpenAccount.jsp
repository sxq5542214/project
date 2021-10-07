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
        <title>用户开户</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- App css -->
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
	 	<script src="js/common/dictionaryData.js" type="text/javascript"></script>
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
                                    <h4 class="page-title">用户开户</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
										<div class="row" style="margin-top: 10px;">
											<div class="col-10">
												<button type="button" class="btn btn-info"  onclick="return checkChose();">用户开户</button>
										    </div>
											 <div class="col-2">
<!-- 										      	<button type="button" class="btn btn-primary" onclick="queryPriceData();">查询价格</button>
 -->										    </div>
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                        <div class="table-responsive" >
                                            <table class="table  mb-0 table-hover table-centered text-nowrap"  >
                                                <thead>
                                                    <tr>
														<th scope="col">用户编号</th>
														<th scope="col">用户名称</th>
														<th scope="col">联系方式</th>
														<th scope="col">账户余额</th>
														<th scope="col">用户状态</th>
														<th scope="col">价格类型</th>
														<th scope="col">表具类型</th>
														<th scope="col">用户地址</th>
														<th scope="col">证件号码</th>
														<th scope="col">人口数</th>
														<th scope="col">用户卡号</th>
														<th scope="col">材料费</th>
														<th scope="col">施工费</th>
														<th scope="col">所属组</th>
														<th scope="col">备注信息</th>
														<th scope="col">创建时间</th>
														<th scope="col">更新时间</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
													<tr v-for="(user,index) in userList" @click="getData(index)"
														:for="'radio'+index">
														<th><input type="radio" :id="'radio'+index" name="u_id"
															:value="index" v-model="checkedRows">{{user.u_no }}</th>
														<!-- <td>{{user.u_no}}</td> -->
														<!--   <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td> -->
														<td>{{user.u_name}}</td>
														<td>{{user.u_phone}}</td>
														<td>{{user.u_balance }}</td>
														<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)}}</td>
														<td>{{user.priceName }}</td>
														<td>{{user.u_deviceinfo }}</td>
														<td>{{user.u_address }}</td>
														<td>{{user.u_paperwork }}</td>
														<td>{{user.u_peoplesize }}</td>
														<td>{{user.u_cardno }}</td>
														<td>{{user.u_materialfee }}</td>
														<td>{{user.u_constructioncost }}</td>
														<td>{{user.u_group +1 }}</td>
														<td>{{user.u_remark }}</td>
														<td>{{user.u_createdate }}</td>
														<td>{{user.u_updatedate }}</td>
													</tr>
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

<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered  modal-full-width modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">用户开户</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" id="modalBodyDiv">
        <div class="container-fluid">
		    
				<div class="row">
					<div class="col-md-2 align-self-center">表具类型</div>
					<div class="col-md-4 ml-auto" >
						<select name="di_dkid" class="form-control"  >
							<option v-for="device in deviceKindList" :key="" :value="device.dk_id">{{device.dk_name}}</option>
						</select>
					</div>
					<div class="col-md-2 align-self-center">充值金额  <span style="color: red;">*</span> </div>
					<div class="col-md-4">
						<input type="number" name="chargeMoney" class="form-control" placeholder="元为单位" required="required" >
						<input type="hidden" name="u_id" class="form-control"  readonly="readonly">
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-2 align-self-center">用户名称</div>
					<div class="col-md-4 ml-auto">
						<input type="text" name="u_name" class="form-control" readonly="readonly"
							placeholder="请输入用户名称">
					</div>

					<div class="col-md-2 align-self-center">联系方式</div>
					<div class="col-md-4 ml-auto" >
						<input type="text" name="u_phone" class="form-control" readonly="readonly"
							placeholder="请输入用户名称">
					</div>

				</div>

			</div>
		</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-secondary"
				data-dismiss="modal">关 闭</button>
			<button type="button" class="btn btn-primary"
				onclick="openAccount()">确 定</button>
		</div>
    </div>
  </div>
</div>
</form>



        </div>
        <!-- END wrapper -->


<!-- App js -->
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

 		<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.min.js"></script>
		<script type="text/javascript" src="js/client/windowsClient.js"></script>
		<script src="page/frame/user/js/userOpenAccount.js" type="text/javascript"></script>
 
    </body>
</html>
