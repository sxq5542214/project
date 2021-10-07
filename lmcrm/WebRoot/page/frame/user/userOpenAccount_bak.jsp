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

			<div class="form-group align-items-center">
				<div class="row">
					<div class="col-10">
						<button type="button" class="btn btn-success"  onclick="return checkChose();">用户开户</button>
					</div>
					<div class="col-2">
						<!-- <button type="button" class="btn btn-primary"
						data-toggle="modal"
							data-target="#exampleModalCenter"
						onclick="queryUserData();">用户开户</button> -->
					</div>
				</div>

			</div>
		</div>



		<div class="container" style="overflow:scroll;">
			<table class="table table-striped table-hover table-sm"
				style="min-width:1800px;">
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
				<tbody>

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
						<h5 class="modal-title" id="exampleModalCenterTitle">用户开户</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
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
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/user/js/userOpenAccount.js"
	type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>