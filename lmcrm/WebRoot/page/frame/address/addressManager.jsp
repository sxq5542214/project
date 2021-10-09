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
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/themes/default/style.min.css">
    
	<link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
 	  
<!-- 	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<link href="https://cdn.jsdelivr.net/npm/font-awesome@4.7.0/css/font-awesome.min.css" rel="stylesheet">
 	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
 -->	
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
	<script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
	
<script
	src="https://cdn.jsdelivr.net/npm/jstree@3.3.12/dist/jstree.min.js"></script>
	
	<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>

    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    .row{margin-top: 10px;margin-bottom: 10px;}
    .container{padding: 1px; margin-right: 1px; margin-left : 1px; }
    </style>
  </head>
 
<body> 

<div class="container">

	 <div class="form-group align-items-center">
	 	
	 </div>
	<%--  <div class="row form-group align-items-center">
	    <div class="col">
	      <select name="p_enabled" id="p_enabled" class="form-control">
			  <option value=""  selected>请选择启用状态</option>
			  <option value="<%=PriceBean.ENABLED_TRUE%>">启用</option>
			  <option value="<%=PriceBean.ENABLED_FALSE%>">禁用</option>
			</select>
	    </div>
	    <div class="col">
	    	<input type="text" class="form-control" id="p_name" name="p_name" placeholder="请输入价格名称">
	    </div>
	    <div class="col">

	      <select name="p_ladder" id="p_ladder" class="form-control">
			  <option value="" selected>请选择价格模式</option>
			  <option value="<%=PriceBean.LADDER_MONTH%>">月</option>
			  <option value="<%=PriceBean.LADDER_YEAR%>">年</option>
			</select>
	    </div>
 	 </div>
 	  --%>
	 <div class="form-group align-items-center">
	 	
	    
	 </div>
</div>

<div class="container  "   id="addressManagerDiv">

	 <div class="row  ">
	      <div class="col-5 ">
	      		<div class="card">
	      			<div class="card-header">地址列表</div>
	      			<div class="card-body">
						<div id="tree"></div>
					</div>
	      		</div>
		  </div>
	      <div class="col-7 border bg-light" style="position: fixed;right:15px;">
			    <div class="row">
			      <div class="col-md-3 align-self-center">地址级别</div>
			      <div class="col-md-9">
			      		      <select name="a_level" id="a_level" class="form-control" disabled="disabled">
<!-- 								  <option value="1">公司</option>
 -->								  <option value="1">乡镇</option>
								  <option value="2">村组</option>
								  <option value="3">村组</option>
								  <option value="4">村组</option>
								  <option value="5">村组</option>
								  <option value="6">村组</option>
								</select>
			      	</div>
			    </div>
				<div class="row">
			      <div class="col-md-3 align-self-center">地址名称</div>
			      <div class="col-md-9 ml-auto">
						<input type="text" name="a_name" id="a_name" class="form-control" placeholder="请输入地址名称">
					</div>
			    </div>
			    
			    <div class="row">
			    
			      <div class="col-md-3  align-self-center">上级地址</div>
			      <div class="col-md-9 ml-auto">
						<input type="text" name="full_name" id="full_name" class="form-control" readonly="readonly">
						<input type="hidden" name="a_id" id="a_id" class="form-control" >
						<input type="hidden" name="parent_id" id="parent_id" class="form-control" >
					</div>
			    </div>
			    
			    <div class="row ">
				 	<div class="col-0" ></div>
				 	<div class="col-12" style="text-align: center;" >
				      	<button type="button" class="btn btn-info" onclick="updateAddress();" >修改地址名称</button>
 					    <button type="button" class="btn btn-success"  onclick="checkAddChose();" style="margin-left: 80px;">新增下级地址</button>
<!-- 				      	<button type="button" class="btn btn-secondary" onclick="deleteAddress();">删除地址</button>
 -->			   		</div>
		    	</div>
		    	
		    	
		    	
	<!-- 		    <div class="row ">
				 	<div class="col-0" ></div>
				 	<div class="col-12" style="text-align: center;" >
 					    <button type="button" class="btn btn-success" onclick="addAddress(1);" >新增为同级地址</button>
			   		</div>
		    	</div> -->
		  </div>
		  
		  
		<form name="updateForm" action="#" >
		<!-- Modal -->
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered  modal-full-width modal-dialog-scrollable" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalCenterTitle">新增下级地址</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <div class="container-fluid">
				    <div class="row">
				      <div class="col-md-3 align-self-center">上级地址</div>
				      <div class="col-md-9 ml-auto">
							<input type="text" name="parent_name" class="form-control" readonly="readonly">
						</div>
				    </div>
				      
				    <div class="row">
				      <div class="col-md-3  align-self-center">当前地址名称</div>
				      <div class="col-md-9 ml-auto">
							<input type="text" name="name" class="form-control" placeholder="请输入地址名称">
						</div>
				    </div>
				</div>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">关 闭</button>
		        <button type="button" class="btn btn-primary" onclick="addAddress(2);">保 存</button>
		      </div>
		    </div>
		  </div>
		</div>
		</form>
		  
		  
		  
	 </div>



<script src="page/frame/address/js/addressManager.js" type="text/javascript"></script>

</body>



</html>