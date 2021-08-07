<%@page import="com.yd.business.operator.bean.OperatorBean"%>
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
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    .container{padding: 1px; margin-right: 1px; margin-left : 1px;max-width:1940px; }
    </style>
  </head>
 
<body> 
<div id="changeDeviceDiv">
<div class="container">

	 <div class="form-group align-items-center">
	 
	 </div>
	 <div class="row form-group align-items-center">
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
			<input type="text" class="form-control" id="u_no" name="u_no"
				placeholder="请输入用户编号">
		</div>
		<div class="col">
			<input type="text" class="form-control" id="u_phone" name="u_phone"
				placeholder="请输入用户手机号">
		</div>
 	 </div>
 	 
	 <div class="form-group align-items-center">
	 	<div class="row">
			 <div class="col-10">
				<button type="button" class="btn btn-primary" onclick="readCardAndQueryChangeDevice();">读卡并查询</button>	
		    </div>
			 <div class="col-2">
		      	<button type="button" class="btn btn-primary" onclick="queryChangeDeviceData();">查询换表记录</button>
		    </div>
	    </div>
	    
	 </div>
</div>

<div class="container" style="overflow:scroll;" >
	<table class="table table-striped table-hover table-sm" >
  <thead>
    <tr>
      <th scope="col">#编号</th>
      <th scope="col">用户名称</th>
      <th scope="col">操作类型</th>
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
  <tbody>
  
  <tr v-for="(cm,index) in changeMeterList" @click="getData(index)" :for="'radio'+index" >
      <th> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{cm.cm_id }}</th>
      <td>{{cm.user_name   }}</td>
      <td>{{getDescByBeanAttrValue("ChangeMeterBean","cm_type",cm.cm_type)}}</td>
      <td>{{cm.operator_name}}</td>
      <td>{{cm.cm_happendate}}</td>
      <td>{{cm.cm_remark }}</td>
      <td>{{cm.cm_oldmeterno }}</td>
      <td>{{cm.cm_oldmetercode }}</td>
      <td>{{cm.cm_newmeterno }}</td>
      <td>{{cm.cm_newmetercode }}</td>
      <td>{{cm.user_address}}</td>
      
  </tr>
  
  </tbody>

</table>
</div>



</div>
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/user/js/userChangeDeviceQuery.js" type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>