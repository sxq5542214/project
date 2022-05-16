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
	href="/staticFiles/bootstrap@4.6.0/dist/css/bootstrap.min.css">
<link rel="stylesheet"
	href="/staticFiles/bstreeview@1.2.0/dist/css/bstreeview.min.css">
<link
	href="/staticFiles/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<script
	src="/staticFiles/jquery@3.5.1/dist/jquery.min.js"></script>
<script
	src="/staticFiles/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="/staticFiles/vue/dist/vue.js"></script>
<script
	src="/staticFiles/bstreeview@1.2.0/dist/js/bstreeview.min.js"></script>
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
						placeholder="请输入用户编号">

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
						<button type="button" class="btn btn-info" onclick="readCardAndQueryUser();">读    卡</button>
						<button type="button" class="btn btn-info" onclick="modifyChargeDetail();">修改充值记录</button>
<!-- 							      	<button type="button" class="btn btn-secondary">删除价格</button>
 -->
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
						<th scope="col">充值序号</th>
						<th scope="col">充值金额</th>
						<th scope="col">充值量</th>
						<th scope="col">刷表状态</th>
						<th scope="col">用户编号</th>
						<th scope="col">用户名称</th>
						<th scope="col">联系方式</th>
						<th scope="col">价格类型</th>
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
							:value="index" v-model="checkedRows">{{user.user_no }}</th>
						<!-- <td>{{user.u_cardno}}</td> -->
						<!--   <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td> -->
						<td>{{user.cd_savingno }}</td>
						<td>{{user.cd_chargemoney }}</td>
						<td>{{user.cd_chargeamount }}</td>
						<td>{{getDescByBeanAttrValue("ChargeDetailBean","cd_brushflag",user.cd_brushflag)}}</td>
						<td>{{user.user_name}}</td>
						<td>{{user.user_phone}}</td>
						<td>{{user.price_name }}</td>
					<!-- 	<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)
							}}</td> -->
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
								onclick="writeCard()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/user/js/modifyCharge.js"	type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>