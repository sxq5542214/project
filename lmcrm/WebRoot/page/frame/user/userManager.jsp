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
	<link href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css" rel="stylesheet">

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
					<button type="button" class="btn btn-success" onclick="addUser();">新增用户</button>
					<button type="button" class="btn btn-info"  onclick="updateUser();">修改用户</button>
					<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
 -->
				</div>
				<div class="col-2">
					<button type="button" class="btn btn-primary"
						onclick="queryUserData();">查询用户</button>
				</div>
			</div>

		</div>
	</div>



	<div class="container" style="overflow:scroll;" >

		<div class="row">
			<div class="col-3">
				<div id="tree"></div>
			</div>

			<div class="col-9">
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
							<td>{{user.deviceKindName }}</td>
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
		</div>
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
						<h5 class="modal-title" id="exampleModalCenterTitle">新增/修改用户</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body" id="modalBodyDiv">
						<div class="container-fluid">
							<div class="row">
								<div class="col-md-2 align-self-center">用户名称</div>
								<div class="col-md-4 ml-auto">
									<input type="text" name="u_name" class="form-control" required="required"
										placeholder="请输入用户名称">
								</div>

								<div class="col-md-2  align-self-center">联系号码</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_phone" class="form-control" required="required"
										placeholder="请输入联系号码">
								</div>

							</div>
							<div class="row">
								<div class="col-md-2 align-self-center">用户余额</div>
								<div class="col-md-4">
									<input type="number" name="u_balance" class="form-control" value="0">
								</div>
								<div class="col-md-2  align-self-center">用户状态</div>
								<div class="col-md-4">
									<select name="u_status" class="form-control">
										<option value="6">待开户</option>
										<option value="7">已开户</option>
										<option value="8">已报停</option>
									</select>
								</div>
							</div>
							<div class="row" >
								<div class="col-md-2 align-self-center">价格类型</div>
								<div class="col-md-4 ml-auto" >
									<select name="u_priceid" class="form-control" >
										<option v-for="price in priceList" :key="" :value="price.p_id">{{price.p_name}}</option>
									</select>
								</div>

								<div class="col-md-2  align-self-center">用户地址</div>
								<div class="col-md-4 ml-auto">
									<input type="text" name="u_address" required="required"
										class="form-control" placeholder="请输入地址">
								</div>

							</div>

							<div class="row">
								<div class="col-md-2 align-self-center">证件号码</div>
								<div class="col-md-4 ml-auto">
									<input type="text" name="u_paperwork"
										class="form-control" placeholder="请输入证件号码">
								</div>

								<div class="col-md-2  align-self-center">人口数</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_peoplesize"
										class="form-control" placeholder="请输入人口数">
								</div>

							</div>
							<div class="row">
								<div class="col-md-2 align-self-center">所属组</div>
								<div class="col-md-4 ml-auto">
									<select name="u_group" id="selectGroup" class="form-control">
										<option v-for="index in 20" :value="index">{{index}}</option>
									</select>
								</div>

								<div class="col-md-2  align-self-center">备注</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_remark" class="form-control"
										placeholder="请输入备注">
								</div>


							</div>
							<div class="row">
							
								<div class="col-md-2  align-self-center">材料费(元)</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_materialfee" class="form-control" value="0"
										placeholder="单位：元">
								</div>
								
								<div class="col-md-2 align-self-center">施工费(元)</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_constructioncost" class="form-control"
										placeholder="单位：元" value="0">

								</div>

							</div>
							<div class="row">
								<div class="col-md-2 align-self-center">自动管阀</div>
								<div class="col-md-4 ml-auto">
									<select name="u_prepayment" class="form-control">
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</div>

								<div class="col-md-2  align-self-center">ID标识</div>
								<div class="col-md-4 ml-auto">
									<input type="number" name="u_id" class="form-control"
										disabled="disabled" placeholder="无需填写">
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal">关 闭</button>
						<button type="button" class="btn btn-primary"
							onclick="addOrUpdateUser()">保 存</button>
					</div>
				</div>
			</div>
		</div>
	</form>
</div>
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/user/js/userManager.js" type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>