<%@page import="com.yd.business.price.bean.PriceBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!doctype html>
<html lang="zh-CN">
<head>
<!-- 必须的 meta 标签 -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<base href="<%=basePath%>">

<!-- Bootstrap 的 CSS 文件 -->


<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css"
	integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bstreeview@1.2.0/dist/css/bstreeview.min.css">
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet"
	integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bstreeview@1.2.0/dist/js/bstreeview.min.js"></script>
<title>龙马水厂收费系统</title>
<style type="text/css">
.col-xs-8 {
	padding: 1px;
}

.col-xs-4 {
	padding: 1px;
}

.container {
	padding: 1px;
	margin-right: 1px;
	margin-left: 1px;
	max-width: 1940px;
}
</style>
</head>

<body>
	<div id="userManagerDiv">
		<div class="container">


			<div class="form-group align-items-center"></div>
			<div class="row form-group align-items-center">
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
						name="u_paperwork" placeholder="请输入用户证件号码"> <input
						type="hidden" class="form-control" id="u_buildingid"
						name="u_buildingid"> <input type="hidden"
						class="form-control" id="u_areaid" name="u_areaid">
				</div>
			</div>

			<div class="form-group align-items-center">
				<div class="row">
					<div class="col-10">
			<!-- 			<button type="button" class="btn btn-success" onclick="readCard();">读  卡</button> -->
						<button type="button" class="btn btn-primary" onclick="readCardAndQueryUser();">读卡并充值</button>
						<button type="button" class="btn btn-secondary" onclick="readCardAndUpdateCharge();">充值修改</button>
<!-- 						<button type="button" class="btn btn-info" onclick="readCardAn();">用户退费</button>
 -->						<button type="button" class="btn btn-success" onclick="readCardAndRepairCard();">用户补卡</button>
 						<button type="button" class="btn btn-info" onclick="readCardAndChangeMeter();">换表维护</button>
 
					</div>
					<div class="col-2">
						<button type="button" class="btn btn-primary"
							onclick="queryUserData();">查询充值记录</button>
					</div>
				</div>

			</div>

		</div>



		<div class="container" style="overflow:scroll;">
			<table class="table table-striped table-hover table-sm"
				style="min-width:1800px;">
				<thead>
					<tr>
						<th scope="col">用户卡号</th>
						<th scope="col">总序号</th>
						<th scope="col">充值序号</th>
						<th scope="col">用户名称</th>
						<th scope="col">联系方式</th>
						<th scope="col">价格类型</th>
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
				<tbody>

					<tr v-for="(user,index) in userList" @click="getData(index)"
						:for="'radio'+index">
						<th><input type="radio" :id="'radio'+index" name="u_id"
							:value="index" v-model="checkedRows">{{user.user_cardno }}</th>
						<!-- <td>{{user.u_no}}</td> -->
						<!--   <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td> -->
						<td>{{user.cd_no }}</td>
						<td>{{user.cd_savingno }}</td>
						<td>{{user.user_name}}</td>
						<td>{{user.user_phone}}</td>
						<td>{{user.price_name }}</td>
					<!-- 	<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)
							}}</td> -->
						<td>{{user.cd_chargemoney }}</td>
						<td>{{user.cd_chargeamount }}</td>
						<td>{{user.operator_name }}</td>
						<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_kindid",user.cd_kindid)}}</td>
						<td>{{user.cd_enddate }}</td>
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
		</div>

<!-- 充值弹框 -->
		<form name="updateForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="exampleModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true">
				<div class="modal-dialog modal-dialog-centered  modal-lg"
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalCenterTitle">用户充值</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="modalBodyDiv">
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
				<div class="modal-dialog modal-dialog-centered  modal-lg"
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
				<div class="modal-dialog modal-dialog-centered  modal-lg"
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
										是否已刷卡
									</div>
									<div class="col-md-8 ml-auto">
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <input type="radio" id="brushFlag0" name="brushFlag" class="form-check-input" checked value="0" >
										  <label class="form-check-label" for="brushFlag0" >未刷卡</label>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <input type="radio" id="brushFlag1" name="brushFlag" class="form-check-input" value="1">
										  <label class="form-check-label" for="brushFlag1" >已刷卡</label>
									
									</div>
								</div>
								<div class="row">
									<div class="col-md-4 align-self-center">
										本次充值金额 <span style="color: red;">*</span>
									</div>
									<div class="col-md-8 ml-auto">
										<input type="number" id="repairCardMoney" name="repairCardMoney" class="form-control"
											placeholder="元为单位" required="required">
										
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
				<div class="modal-dialog modal-dialog-centered  modal-lg"
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="changeMeterModalCenterTitle">用户补卡</h5>
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
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/user/js/userCharge.js"	type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>