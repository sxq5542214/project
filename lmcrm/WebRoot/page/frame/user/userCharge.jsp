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
        <title>用户充值</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

	<link  rel="stylesheet" href="https://cdn.jsdelivr.net/gh/orangehill/jstree-bootstrap-theme/dist/themes/proton/style.min.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/themes/default/style.min.css">
            <!-- App css -->
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
		<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
 		<script src="https://cdn.jsdelivr.net/npm/vue@2.6.14/dist/vue.min.js"></script>
		<script	src="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/jstree.min.js"></script>
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
                                    <h4 class="page-title">用户充值/补卡/换表</h4>
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
											<input type="text" class="form-control" id="u_name" name="u_name"
												placeholder="请输入用户姓名">
						
										</div>
										<div class="col">
											<input type="text" class="form-control" id="u_cardno" name="u_cardno"
												placeholder="请输入用户卡号">
						
										</div>
										<div class="col">
											<input type="text" class="form-control" id="u_phone" name="u_phone"
												placeholder="请输入用户手机号">
										</div>
										<div class="col">
											<input type="text" class="form-control" id="u_paperwork"
												name="u_paperwork" placeholder="请输入用户证件号码">
												 <input type="hidden" class="form-control" id="u_buildingid"
												name="u_buildingid">
												 <input type="hidden"
												class="form-control" id="u_areaid" name="u_areaid">
												 <input type="hidden"
												class="form-control" id="u_id" >
												
										</div>
			                         </div>
										<div class="row" style="margin-top: 10px;">
											<div class="col-10">
												<button type="button" class="btn btn-info" onclick="readCardAndQueryUser();">读卡查询</button>
												<button type="button" class="btn btn-info" name="button_charge" disabled="disabled" data-toggle="modal" data-target="#exampleModalCenter">用户充值</button>
												<button type="button" class="btn btn-secondary" name="button_updateCharge" disabled="disabled"  onclick="readCardAndUpdateCharge();">充值修改</button>
												<button type="button" class="btn btn-success"  name="button_repairCard" disabled="disabled" onclick="readCardAndRepairCard();">用户补卡</button>
						 						<button type="button" class="btn btn-primary"  name="button_changeMeter" disabled="disabled" onclick="readCardAndChangeMeter();">换表维护</button>
						 						<button type="button" class="btn btn-dark"  name="button_makeReceipt" disabled="disabled" onclick="alert('暂未实现');">补打票据</button>
 										    </div>
											 <div class="col-2">
												<button type="button" class="btn btn-info" onclick="queryUserData();">查询用户</button>
											</div>
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body" style="padding-top: 0px;">
                                    	
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
										  	<h4 class="header-title" style="padding-top: 1.5rem;font-size: 1.1rem;">用户列表</h4>
	                                        <div class="table-responsive" style="min-height: 100px;">
	                                            <table class="table  mb-0 table-hover table-centered text-nowrap table-bordered"  >
	                                                <thead>
	                                                    <tr>
															<th scope="col">用户名称</th>
															<th scope="col">价格类型</th>
															<th scope="col">表具厂商</th>
															<th scope="col">联系方式</th>
															<th scope="col">用户地址</th>
	                                                    </tr>
	                                                </thead>
	                                                <tbody >
														<tr v-for="(user,index) in userList" @click="getUserData(index)"
															:for="'radio'+index">
															<th><input type="radio" :id="'userRadio'+index" name="u_id"
																:value="index" v-model="checkedRows">{{user.u_name }}</th>
															<td>{{user.priceName }}</td>
															<td>{{user.device_company}}</td>
															<td>{{user.u_phone}}</td>
															<td>{{user.addressName}}{{user.u_address}}</td>
														</tr>
	                                                </tbody>
	                                            </table>
	                                        </div> <!-- end table-responsive-->
	                                        
	                                        <h4 class="header-title" style="padding-top: 1.5rem;font-size: 1.1rem;"> 用户充值记录</h4>
	                                         <div class="table-responsive" style="min-height: 150px;">
	                                            <table class="table  mb-0 table-hover table-centered text-nowrap table-bordered"  >
	                                                <thead>
	                                                    <tr>
															<th scope="col">用户卡号</th>
															<th scope="col">总序号</th>
															<th scope="col">充值序号</th>
															<th scope="col">用户名称</th>
															<th scope="col">实付金额</th>
															<th scope="col">充值金额</th>
															<th scope="col">充值量</th>
															<th scope="col">操作员工</th>
															<th scope="col">操作类型</th>
															<th scope="col">写卡时间</th>
															<th scope="col">刷表时间</th>
															<th scope="col">缴费方式</th>
															<th scope="col">打印状态</th>
															<th scope="col">充值状态</th>
															<th scope="col">上次余额</th>
															<th scope="col">本次余额</th>
															<th scope="col">基本金额</th>
															<th scope="col">排污费</th>
															<th scope="col">其他费</th>
	                                                    </tr>
	                                                </thead>
	                                                <tbody >
														<tr v-for="(user,index) in userChargeList" @click="getChargeData(index)"
															:for="'radio'+index">
															<th><input type="radio" :id="'chargeRadio'+index" name="charge_id"
																:value="index" v-model="checkedRows">{{user.user_cardno }}</th>
															<!-- <td>{{user.u_no}}</td> -->
															<!--   <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td> -->
															<td>{{user.cd_no }}</td>
															<td>{{user.cd_savingno }}</td>
															<td>{{user.user_name}}</td>
														<!-- 	<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)
																}}</td> -->
															<td>{{user.cd_paidmoney }}</td>
															<td>{{user.cd_chargemoney }}</td>
															<td>{{user.cd_chargeamount }}</td>
															<td>{{user.operator_name }}</td>
															<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_kindid",user.cd_kindid)}}</td>
															<td>{{user.cd_startdate }}</td>
															<td>{{user.cd_happendate }}</td>
															<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_order",user.cd_order)}}</td>
															<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_printstatus",user.cd_printstatus)}}</td>
															<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_charge",user.cd_charge)}}</td>
															<td>{{user.cd_lastbalance }}</td>
															<td>{{user.cd_balance }}</td>
															<td>{{user.cd_basemoney }}</td>
															<td>{{user.cd_othermoney1 }}</td>
															<td>{{user.cd_othermoney2 }}</td>
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

<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered   modal-dialog-scrollable" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">用户充值</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
	  <div class="modal-body" id="modalBodyDiv">
        <div class="container-fluid">
		    <div class="row">
				<div class="col-md-4 align-self-center">
					上次刷表日期
				</div>
				<div class="col-md-8 ml-auto">
					<input type="text" id="useDate" name="useDate" class="form-control"
						placeholder="" required="required" readonly="readonly"  >
					
				</div>
			</div>
			<div class="row">
				<div class="col-md-4 align-self-center">
					本次充值金额 <span style="color: red;">*</span>
				</div>
				<div class="col-md-8 ml-auto">
					<input type="number" id="chargeMoney" name="chargeMoney" class="form-control"
						placeholder="元为单位" required="required">
					
				</div>
			</div>

		</div>
	</div>
		<div class="modal-footer">
			<button type="button" class="btn btn-secondary"
				data-dismiss="modal">关 闭</button>
			<button type="button" class="btn btn-primary"
				onclick="writeCardByCharge()">确 定</button>
		</div>
    </div>
  </div>
</div>
</form>




<!-- 充值修改弹框 -->
		<form name="updateChargeForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="updateChargeModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="updateChargeModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered "
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="updateChargeModalCenterTitle">修改充值记录</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="updateChargeModalBodyDiv">
							<div class="container-fluid">

<!-- 
								<div class="row">
									<div class="col-md-4 align-self-center">当前余额</div>
									<div class="col-md-8 ml-auto">
										<input type="number" name="balance" class="form-control"
											placeholder="元为单位" readonly="readonly" >
										
									</div>
								</div> -->
								<div class="row">
									<div class="col-md-4 align-self-center">
										上次充值金额
									</div>
									<div class="col-md-8 ml-auto">
										<input type="text" id="lastCharge" class="form-control"
											placeholder="" required="required" readonly="readonly"  >
										
									</div>
								</div>
								<div class="row">
									<div class="col-md-4 align-self-center">
										本次充值金额 <span style="color: red;">*</span>
									</div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="updateChargeMoney" name="updateChargeMoney" class="form-control"
											placeholder="元为单位" required="required">
										
									</div>
								</div>

							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="writeCardByUpdateCharge()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		
<!-- 用户补卡弹框 -->
		<form name="repairCardForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="repairCardModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="repairCardModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered "
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="repairCardModalCenterTitle">用户补卡</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="repairCardModalBodyDiv">
							<div class="container-fluid">

<!-- 
								<div class="row">
									<div class="col-md-4 align-self-center">当前余额</div>
									<div class="col-md-8 ml-auto">
										<input type="number" name="balance" class="form-control"
											placeholder="元为单位" readonly="readonly" >
										
									</div>
								</div> -->
								
								<div class="row">
									<div class="col-md-4 align-self-center">
										补卡金额 <span style="color: red;">*</span>
									</div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="repairCardMoney" name="repairCardMoney" class="form-control" readonly="readonly"
											placeholder="元为单位" required="required">
										
									</div>
								</div>
								
								<div class="row">
									<div class="col-md-4 align-self-center">
										是否已刷卡
									</div>
									<div class="col-md-8 ml-auto">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <input type="radio" id="brushFlag0" name="brushFlag" class="form-check-input" checked value="0" >
										  <label class="form-check-label" for="brushFlag0" >未刷卡</label>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<!--   <input type="radio" id="brushFlag1" name="brushFlag" class="form-check-input" value="1">
										  <label class="form-check-label" for="brushFlag1" >已刷卡</label>
									 -->
									</div>
								</div>

							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="writeCardByRepairCard()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		
		
<!-- 换表维护弹框 -->
		<form name="changeMeterForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="changeMeterModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="changeMeterModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered "
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="changeMeterModalCenterTitle">用户换表</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="changeMeterModalBodyDiv">
							<div class="container-fluid">


								<div class="row">
									<div class="col-md-4 align-self-center">表上剩余金额<span style="color: red;">*</span></div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="changeMeterMoney" name="changeMeterMoney" class="form-control"
											placeholder="元为单位" value="0" >
										
									</div>
								</div> 
								<div class="row">
									<div class="col-md-4 align-self-center">旧表止码<span style="color: red;">*</span></div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="cm_oldmetercode" name="cm_oldmetercode" class="form-control"
											placeholder="元为单位" value="0" >
										
									</div>
								</div> 
								<div class="row">
									<div class="col-md-4 align-self-center">
										换表类型
									</div>
									<div class="col-md-8 ml-auto">
										<select id="cm_type" name="cm_type" class="form-control" onchange="changeCMType(this)">
											<option value="0">更换模块</option>
											<option value="1">更换整表</option>
											<option value="2">更换电池</option>
											<option value="3">其他</option>
											<option value="4">更换表具类型</option>
										</select>
									</div>
								</div>
								<div class="row"  id="div_device_company">
									<div class="col-md-4 align-self-center">
										新水表厂商<span style="color: red;">*</span>
									</div>
									<div class="col-md-8 ml-auto">
										<select name="device_company" class="form-control" id="device_company" >
											<option value="">请选择</option>
											<option value="轻松">轻松</option>
											<option value="鲁正">鲁正</option>
										</select>
									</div>
								</div>
								<div class="row">
									<div class="col-md-4 align-self-center">
										换表备注 
									</div>
									<div class="col-md-8 ml-auto">
										<input type="text" id="cm_remark" name="cm_remark" class="form-control"
											placeholder="请输入换表备注" required="required">
									</div>
								</div>

								<div class="row"  style="display: none;" id="div_cm_newmetercode">
									<div class="col-md-4 align-self-center">
										新表编号 
									</div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="cm_newmetercode" name="cm_newmetercode" class="form-control"
											placeholder="请输入新表编号" value="0">
									</div>
								</div>

								<div class="row" style="display: none;" id="div_cm_newmeterno">
									<div class="col-md-4 align-self-center">
										新表起码 
									</div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="cm_newmeterno" name="cm_newmeterno" class="form-control"
											placeholder="请输入新表起码"  value="0">
									</div>
								</div>
								
								<div class="row" style="display: none;" id="div_device_kind">
									<div class="col-md-4 align-self-center">
										新表具类型
									</div>
									<div class="col-md-8 ml-auto">
										<select id="device_kind" name="device_kind" class="form-control" >
											<option v-for="dk in deviceKindList" :key="" :value="dk.dk_id">{{dk.dk_name}}</option>
										</select>
									</div>
								</div>
								
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="writeCardByChangeMeter()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>



        </div>
        <!-- END wrapper -->


<!-- App js -->

		<script type="text/javascript" src="js/client/windowsClient.js"></script>
		<script src="page/frame/user/js/userCharge.js"	type="text/javascript"></script>
 
    </body>
</html>
