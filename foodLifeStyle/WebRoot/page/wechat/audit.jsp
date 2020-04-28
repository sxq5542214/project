<%@page import="com.yd.business.customer.bean.CustomerApplyProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/wechat/";
List<CustomerApplyProductBean> list = (List<CustomerApplyProductBean>) request.getAttribute("list");
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
<link rel="stylesheet" type="text/css" href="css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="css/style.css">
<link rel="stylesheet" type="text/css" href="css/person.css">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script type="text/javascript" src="js/Adaptive.js"></script>
</head>

<body>
<!-- 用于防止header将下方内容给遮住 -->
	<div style="height: 1.35rem; text-align: center;">
		<header>
			<a> <input type="text" class="sch" id="name" /></a>
			<button onclick="javascript:window.location.href='../../app/apply/auditlist.do?name='+encodeURI(document.getElementById('name').value)" type="button" class="btn btn-info btn-xs" style="margin-left: 10px; margin-top: 0.24rem">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</button>
		</header>
	</div>
<div id="_contain">
	<div class="favorable">
		<%
			for(CustomerApplyProductBean bean : list){
				%>
		<div class="whe">
			<div class="whe-lt whe-br">
		    	<p class="lf-tp"><%=bean.getSupplier_name() %></p>
		    	<p class="lf-good"><%=bean.getProduct_name() %></p>
		        <p class="lf-bt"><span><%=bean.getApply_num() %></span><span><%=bean.getCreate_time() %></span></p>
		    </div>
		    <div class="whe-lt-right">
		    	<p class="lf-tp-right"><button onclick="javascript:window.location.href='../../app/apply/audit.do?id=<%=bean.getId() %>'" type="button" class="btn btn-primary btn-xs">&nbsp;&nbsp;同意&nbsp;&nbsp;</button></p>
		    	<p class="lf-tp-right"><button type="button" class="btn btn-danger btn-xs" onclick="Apy.audit(<%=bean.getId()%>,2);">不同意</button></p>
		    </div>
		</div>
				<%
			}
		%>
	</div>
</div>

<footer>
	<a href="../../app/apply/myapplylist.do" class="foot">
    	<img src="images/foot/ft_3.png">
        <p>我的申领</p>
    </a>
	<a href="../../app/operat/list.do" class="foot">
    	<img src="images/foot/ft_4.png">
        <p>操作员管理</p>
    </a>
	<a href="../../app/apply/auditlist.do"><div class="foot act">
    	<img src="images/foot/ft_1_1.png">
        <p>我的审核</p>
    </div></a>
</footer>    
    
<script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
<script src="js/apply.js"></script>
<script src="../../js/Util.js"></script>
</body>
</html>
