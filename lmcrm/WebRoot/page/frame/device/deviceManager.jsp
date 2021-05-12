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

	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
  	<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    </style>
  </head>
 
<body>

<div class="container">
	<div class="row">
	  <div class="col-xs-4">
		<div class="list-group">
		  <a  class="list-group-item active">
		   水表价格
		  </a>
		  <a class="list-group-item">Dapibus ac facilisis in</a>
		  <a class="list-group-item">Morbi leo risus</a>
		</div>
	  </div>
	  <div class="col-xs-8">
		  <a  class="list-group-item active">
		    参数信息
		  </a>
		<div class="table-responsive">
		  <table class="table table-condensed table-striped">
		   <thead>
		   	<tr><th>1</th><th>2</th><th>1</th><th>2</th></tr>
		   </thead>
		   <tbody>
		   	<tr><td>1</td><td>1</td><td>1</td><td>1</td></tr>
		   	<tr><td>2</td><td>1</td><td>1</td><td>1</td></tr>
		   </tbody>
		  </table>
		</div>
	  </div>
	</div>
	<div class="row">
	  <div class="col-xs-4">
		<div class="list-group">
		  <a  class="list-group-item active">
		   水表类型
		  </a>
		  <a  class="list-group-item">Dapibus ac facilisis in</a>
		  <a  class="list-group-item">Morbi leo risus</a>
		  <a  class="list-group-item">Porta ac consectetur ac</a>
		  <a  class="list-group-item">Vestibulum at eros</a>
		  <a  class="list-group-item">
		  <div class="row">
		  	<div class="col-xs-6">
		  		<button onclick="callWindowsClientReadCardMethod();" type="button" class="btn btn-primary" >读卡</button>
			</div>
		   <div class="col-xs-6">
		   		<button onclick="callWindowsClientWriteSpaceCard();" type="button" class="btn btn-primary">写卡</button>
			</div>
		  </div>
		  </a>
		  
		   
		</div>
	  </div>
	  <div class="col-xs-8">
		  <div class="col-xs-4">
			  <a  class="list-group-item active">
			   水表类型
			  </a>
		  <a onclick="this.firstChild.checked=true;" class="list-group-item"><input type="radio" name="cardType" value="0"> 新卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="2"> 设置卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="3"> 清空卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="4"> 检查卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="6"> 阀门卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="7"> 校时卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="13"> 报停/恢复卡</a>
		  <a onclick="this.firstChild.checked=true;"   class="list-group-item"><input type="radio" name="cardType" value="1"> 清零卡</a>
			  
		  </div>
		  <div class="col-xs-8">
		  	  <div class="panel panel-warning">
			  <div class="panel-heading">读卡内容</div>
			
			  
			  <table class="table table-condensed table-striped">
			   <thead>
			   	<tr><th>数据项</th><th>数据值</th></tr>
			   </thead>
			   <tbody>
			   	<tr><td>1</td><td>1</td></tr>
			   	<tr><td>2</td><td>1</td></tr>
			   	<tr><td>1</td><td>1</td></tr>
			   	<tr><td>2</td><td>1</td></tr>
			   	<tr><td>1</td><td>1</td></tr>
			   	<tr><td>2</td><td>1</td></tr>
			   	<tr><td>1</td><td>1</td></tr>
			   	<tr><td>2</td><td>1</td></tr>
			   </tbody>
			  </table>
			</div>
		  </div>
	  </div>
	</div>
</div>
调试数据：  <textarea  rows="5" cols="120" id="textArea"></textarea>
</body>
<script type="text/javascript" src="js/client/windowsClient.js"></script>
</html>