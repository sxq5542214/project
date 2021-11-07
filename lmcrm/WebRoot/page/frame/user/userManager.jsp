<%@page import="com.yd.business.price.bean.PriceBean"%>
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
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link  rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orangehill/jstree-bootstrap-theme/dist/themes/proton/style.min.css">
	<link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
	
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/themes/default/style.min.css">
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
    <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/jstree.min.js"></script>
	<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>

    <title>用户管理</title>
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
                                    <h4 class="page-title">用户管理</h4>
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
											    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter"  onclick="addUser();">新增用户</button>
										      	<button type="button" class="btn btn-danger" onclick="updateUser();">修改用户</button>
										      	<button type="button" class="btn btn-secondary" onclick="deleteUser();">删除用户</button>
										      	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#queryUserModalCenter" >查询用户</button>
											
								<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
								 -->		    </div>
									<!-- 		 <div class="col-2">
										      	<button type="button" class="btn btn-primary" onclick="queryUserData();">查询用户</button>
										    </div> -->
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                    	
									  <div class="row">
									      <div class="col-4 ">
									      		<div class="card">
									      			<div class="card-header">地址列表</div>
									      			<div class="card-body">
														<div id="tree"></div>
													</div>
									      		</div>
										  </div>
										  <div class="col-8">
                                        <div class="table-responsive" style="min-height: 150px;">
                                            <table class="table  mb-0 table-hover table-centered text-nowrap"  >
                                                <thead>
                                                    <tr>
														<th scope="col">用户编号</th>
														<th scope="col">用户名称</th>
														<th scope="col">联系方式</th>
														<th scope="col">账户余额</th>
														<th scope="col">用户状态</th>
														<th scope="col">价格类型</th>
														<th scope="col">表具厂商</th>
														<th scope="col">表具类型</th>
														<th scope="col">用户地址</th>
														<th scope="col">证件号码</th>
														<th scope="col">人口数</th>
														<th scope="col">用户卡号</th>
								<!-- 						<th scope="col">材料费</th>
														<th scope="col">施工费</th> -->
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
													<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)
														}}</td>
													<td>{{user.priceName }}</td>
													<td>{{user.device_company }}</td>
													<td>{{user.deviceKindName }}</td>
													<td>{{user.addressName}}{{user.u_address }}</td>
													<td>{{user.u_paperwork }}</td>
													<td>{{user.u_peoplesize }}</td>
													<td>{{user.u_cardno }}</td>
													<!-- <td>{{user.u_materialfee }}</td>
													<td>{{user.u_constructioncost }}</td> -->
													<td>{{user.u_group +1 }}</td>
													<td>{{user.u_remark }}</td>
													<td>{{user.u_createdate }}</td>
													<td>{{user.u_updatedate }}</td>
						
												</tr>
												  
                                                </tbody>
                                            </table>
                                        </div> <!-- end table-responsive-->
										</div><!-- end col8 -->
										</div><!-- end row -->
                                    </div> <!-- end card body-->
                                </div> <!-- end card -->
                            </div><!-- end col-->

                        
                        </div>
                        <!-- end row-->

                        
                    </div> <!-- container -->

                </div> <!-- content -->



            </div>




		

		<form name="updateForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="exampleModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true"> 
				<div class="modal-dialog modal-dialog-centered  modal-full-width modal-dialog-scrollable"
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalCenterTitle">新增/修改用户</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="modalBodyDiv">
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-2 align-self-center">用户名称 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto">
										<input type="text" name="u_name" class="form-control" required="required"
											placeholder="请输入用户名称">
									</div>
	
									<div class="col-md-2  align-self-center">联系号码</div>
									<div class="col-md-4 ml-auto">
										<input type="number" name="u_phone" class="form-control" placeholder="请输入联系号码">
									</div>
	
								</div>
								
								<div class="row" >
									<div class="col-md-2 align-self-center">价格类型 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto" >
										<select name="u_priceid"   class="form-control" >
											<option v-for="price in priceList" :key="" :value="price.p_id">{{price.p_name}}</option>
										</select>
									</div>
	
									<div class="col-md-2 align-self-center">证件号码   </div>
									<div class="col-md-4 ml-auto">
										<input type="text" name="u_paperwork"
											class="form-control" placeholder="请输入证件号码">
									</div>
								</div>
								
								
								<div class="row">
								
									<div class="col-md-2  align-self-center">水表厂商 <span style="color: red;">*</span></div>
									<div class="col-md-4 ml-auto">
										<select name="device_company" class="form-control" id="device_company" >
											<option value="">请选择</option>
											<option value="轻松">轻松</option>
											<option value="鲁正">鲁正</option>
										</select>
									</div>
									
									
									<div class="col-md-2 align-self-center">表具类型 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto" >
										<select name="di_dkid" class="form-control" id="di_dkid" >
											<option value="">请选择</option>
											<option v-for="device in deviceKindList" :key="" :value="device.dk_id">{{device.dk_name}}</option>
										</select>
									</div>
									
									
								</div>
								
								
								<div class="row">
	
									<div class="col-md-2  align-self-center">用户地址 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto">
										<button type="button" class="btn btn-info" data-toggle="modal" data-target="#addressModalCenter" name="addressBTN2" id="addressBTN2">选择地址</button>
										
										<input type="hidden" name="addressId" id="addressId2">
									</div>
									
									
									
									<div class="col-md-2 align-self-center">充值金额  <span style="color: red;">*</span> </div>
									<div class="col-md-4">
										<input type="number" name="chargeMoney" class="form-control" placeholder="元为单位" required="required" value="">
										<input type="hidden" name="u_materialfee" class="form-control" value="0"
											placeholder="单位：元">
										<input type="hidden" name="u_constructioncost" class="form-control"
											placeholder="单位：元" value="0">
	
										<input type="hidden" name="u_prepayment" class="form-control"
											placeholder="单位：元" value="0">
									</div>
									
								</div>
								
								<div class="row">
									<div class="col-md-2  align-self-center">位置说明  </div>
									<div class="col-md-4 ml-auto">
										<input type="text" name="u_address" required="required"
											class="form-control" placeholder="请输入位置说明">
									</div>
									
									<div class="col-md-2 align-self-center">用户余额  </div>
									<div class="col-md-4">
										<input type="number" name="u_balance" class="form-control" value="0">
									</div>
								</div>
								
	
								<div class="row">
	
									
									<div class="col-md-2  align-self-center">用户状态 <span style="color: red;">*</span> </div>
									<div class="col-md-4">
										<select name="u_status" class="form-control">
											<option value="6">待开户</option>
											<option value="7">已开户</option>
											<option value="8">已报停</option>
										</select>
									</div>
									
									<div class="col-md-2  align-self-center">人口数  </div>
									<div class="col-md-4 ml-auto">
										<input type="number" name="u_peoplesize" value="0"
											class="form-control" placeholder="请输入人口数">
									</div>
	
								</div>
								<div class="row">
					<!-- 				<div class="col-md-2 align-self-center">所属组 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto">
										<select name="u_group" id="selectGroup" class="form-control">
											<option v-for="index in 20" :value="index">{{index}}</option>
										</select>
									</div> -->
	
									<div class="col-md-2  align-self-center">ID标识</div>
									<div class="col-md-4 ml-auto">
										<input type="number" name="u_id" class="form-control"
											disabled="disabled" placeholder="无需填写">
											
										<input type="hidden" name="u_group" value="9">
									</div>
									
									<div class="col-md-2  align-self-center">备注</div>
									<div class="col-md-4 ml-auto">
										<input type="number" name="u_remark" class="form-control"
											placeholder="请输入备注">
									</div>
	
								</div>
						<!-- 		<div class="row">
								
									<div class="col-md-2  align-self-center">材料费(元)</div>
									<div class="col-md-4 ml-auto">
										<input type="hidden" name="u_materialfee" class="form-control" value="0"
											placeholder="单位：元">
									</div>
									
									<div class="col-md-2 align-self-center">施工费(元)</div>
									<div class="col-md-4 ml-auto">
										<input type="hidden" name="u_constructioncost" class="form-control"
											placeholder="单位：元" value="0">
	
									</div>
	
								</div> -->
								
					<!-- 			<div class="row">
									
									
									
									<div class="col-md-2 align-self-center"></div>
									<div class="col-md-4 ml-auto">
										
									</div>
								</div> -->
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="addOrUpdateUser()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	
	

	<div class="modal fade" id="addressModalCenter" tabindex="-1"
					role="dialog" aria-labelledby="addressModalCenterTitle"
					aria-hidden="true"> 
			<div class="modal-dialog modal-dialog-centered  modal-lg modal-dialog-scrollable"
				role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalCenterTitle">请选择用户地址</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" >
						<div class="container-fluid">
							<div class="row">
								<div id="modalTree"></div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关 闭</button>
						<button type="button" class="btn btn-primary"
							data-dismiss="modal">确定</button>
					</div>
				</div>
			</div>
		</div>
		
		
<!-- 用户查询弹框 -->
		<form name="queryUserForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="queryUserModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="queryUserModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered  modal-full-width modal-dialog-scrollable"
					role="document">
					<div class="modal-content">
						<div class="modal-header" style="border-bottom: 0;">
						
							 <ul class="nav nav-tabs mb-3" style="width: 100%;">
                                <li class="nav-item">
                                    <a href="#home" data-toggle="tab" aria-expanded="false" class="nav-link active">
                                        <i class="mdi mdi-home-variant d-lg-none d-block mr-1"></i>
                                        <span class="d-none d-lg-block">条件查询</span>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="javascript:void(0);" onclick="readCardAndQueryUser();"  class="nav-link btn btn-info">
                                        <i class="mdi mdi-account-circle d-lg-none d-block mr-1"></i>
                                        <span class="d-none d-lg-block" style="color: white;">读卡查询</span>
                                    </a>
                                </li>
                            </ul>
							<!-- <h5 class="modal-title" id="queryUserModalCenterTitle">查询用户</h5> -->
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="changeMeterModalBodyDiv">
							<div class="container-fluid">


								<div class="row">
									<div class="col-md-2 align-self-center">用户姓名</div>
									<div class="col-md-4 ml-auto">
										 <input type="text" class="form-control" id="u_name" name="u_name"
												placeholder="请输入用户姓名">
									</div>
									<div class="col-md-2 align-self-center">手机号</div>
									<div class="col-md-4 ml-auto">
										 <input type="text" class="form-control" id="u_phone" name="u_phone"
													placeholder="请输入用户手机号">
										
									</div>
								</div> 
								<div class="row">
									<div class="col-md-2 align-self-center">
										证件号码
									</div>
									<div class="col-md-4 ml-auto">
										<input type="text" class="form-control" id="u_paperwork"
													name="u_paperwork" placeholder="请输入用户证件号码"> 
									</div>
									
									
									<div class="col-md-2 align-self-center">
										用户卡号
									</div>
									<div class="col-md-4 ml-auto">
										    	<input type="text" class="form-control" id="u_cardno" name="u_cardno"
													placeholder="请输入用户卡号">
									
										  <input type="hidden" class="form-control" id="u_buildingid" name="u_buildingid">
										  <input type="hidden" class="form-control" id="u_areaid" name="u_areaid">
										  <input type="hidden" class="form-control" id="addressId" name="addressId">
						<!-- 			<div class="col-md-2 align-self-center">
										用户地址 
									</div>
									<div class="col-md-4 ml-auto">
										<button type="button" class="btn btn-info" data-toggle="modal" data-target="#addressModalCenter" id="addressBTN">请选择地址</button>
									</div> -->
								</div>
							</div>
						</div>
						<div class="modal-footer">
<!-- 							<button type="button" class="btn btn-success" style="margin-left: 0;">读卡查询</button>
 -->							
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="queryUserData()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	
	
		
	</div>
	
	
<script src="page/frame/user/js/userManager.js" type="text/javascript"></script>
</body>



</html>