<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=1.0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>子商户管理</title>
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/flat-ui.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>

<body>
<!-- 用于防止header将下方内容给遮住 -->
<div style="height:1.35rem">
	<header style="text-align: center; color: white;">
	    操作员编辑
	</header>
</div>
<div>
	<div class="form-group-sm">
		<label class="col-md-2 control-label">操作员名称</label>
		<div class="col-md-10">
			<input type="text" id="name" class="form-control input-sm" placeholder="请输入操作员名称">
		</div>
	</div>
	<div class="form-group-sm">
		<label class="col-md-2 control-label">操作员登录账号</label>
		<div class="col-md-10">
			<input type="text" id="loginname" class="form-control input-sm" placeholder="请输入操作员登录账号">
		</div>
	</div>
	<div class="form-group-sm">
		<label class="col-md-2 control-label">状态</label>
		<div class="col-md-10">
			<div class="form-group">
            <select data-toggle="select" id="status" class="form-control select select-default select-sm">
              	<option value="0">可用</option>
                <option value="1">不可用</option>
            </select>
          </div>
		</div>
	</div>
	<div class="form-group-sm">
		<label class="col-md-2 control-label">登录密码</label>
		<div class="col-md-10">
			<input type="password" id="password" class="form-control input-sm" placeholder="请输入登录密码">
		</div>
	</div>
	<div class="form-group-sm">
		<label class="col-md-2 control-label">请再次输入登录密码</label>
		<div class="col-md-10">
			<input type="password" id="passwordrest" class="form-control input-sm" placeholder="请再次输入登录密码">
		</div>
	</div>
	<div class="container row" style="margin-top: 10px">
		<div class="col-xs-6"><input onclick="Opt.insert();" type="button" class="btn btn-primary btn-sm btn-block" value="添加"></div>
		<div class="col-xs-6"><input onclick="javascript:window.location.href='../../app/operat/list.do'" type="button" class="btn btn-danger btn-sm btn-block" value="取消"></div>
	</div>
</div>
<footer>
	<a href="../../app/apply/myapplylist.do" class="foot">
    	<img src="images/foot/ft_3.png">
        <p>我的申领</p>
    </a>
	<a href="../../app/operat/list.do" class="foot act">
    	<img src="images/foot/ft_4_4.png">
        <p>操作员管理</p>
    </a>
	<a href="../../app/apply/auditlist.do">
		<div class="foot">
    		<img src="images/foot/ft_1.png">
        	<p>我的审核</p>
    	</div>
    </a>
</footer>    
    
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script type="text/javascript" src="js/flat-ui.js"></script>
<script type="text/javascript" src="js/application.js"></script>
<script src="../../js/Util.js"></script>
<script type="text/javascript" src="js/operator.js"></script>
</body>
</html>
