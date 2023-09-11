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

	<link rel="stylesheet" href="/staticFiles/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="/staticFiles/jquery@3.5.1/dist/jquery.slim.min.js" ></script>
 <script src="/staticFiles/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
 
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    	.navbar{align-items:baseline;}
    	.container{max-width: 1440px;}
    </style>
  </head>

<body >
<div class="row" style="margin-left: 0; margin-right: 0;" >

	<div style="width: 15%;flex:12%;"  >
		<nav class="navbar navbar-expand-lg navbar-light bg-light " id="navMenu" >
		<!--   <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		
		  <div class="collapse navbar-collapse" id="navbarSupportedContent"> -->
		    <ul class="nav flex-column" style="color:black;">
		    
		      <li class="nav-item active">
			  	<a class="navbar-brand" >&nbsp;&nbsp;龙马水厂</a>
			  </li>
		      <li class="nav-item active">
		        <a class="nav-link" onclick="changeIframe('page/frame/dashboard/indexDashboard.jsp',this)" >首页 <span class="sr-only">(current)</span></a>
		        
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          档案管理
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" onclick="changeIframe('page/frame/operator/operatorManager.jsp',this)">员工管理</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/price/priceManager.jsp',this)">价格管理</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userManager.jsp',this)">用户管理</a>
		<!--           <a class="dropdown-item" href="#">批量建户</a>
		 -->          <a class="dropdown-item" onclick="changeIframe('page/frame/address/addressManager.jsp',this)">地址管理</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/modifyAddress.jsp',this)">位置变更</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	营业管理 
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userOpenAccount.jsp',this)">用户开户</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userCharge.jsp',this)">用户充值</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userCharge.jsp',this)">充值修改</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userCharge.jsp',this)">用户补卡</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userCharge.jsp',this)">换表维护</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	抄表管理 
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">生成计划</a>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">手工录入</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">远程抄表</a>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">批量抄表</a>
		        </div>
		      </li>
		      
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	查询管理
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" onclick="changeIframe('admin/report/toSimpleReportPage.do',this)">报表查询</a>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">收费统计</a>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">疑户查询</a>
		          <div class="dropdown-divider"></div>
		          <a class="dropdown-item" href="javascript:alert('暂未实现此功能');">日志查询</a>
		        </div>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          	维护管理 
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
		          <a class="dropdown-item" onclick="changeIframe('page/frame/user/userCharge.jsp',this)">换表维护</a>
		          <a class="dropdown-item"  onclick="changeIframe('page/frame/user/userChangeDeviceQuery.jsp',this)">换表查询</a>
		          <a class="dropdown-item" onclick="changeIframe('page/frame/device/deviceManager.jsp',this)">表具管理</a>
		        </div>
		      </li>
		      
		        
			      <li class="nav-item dropdown">
			        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
			          	 系统管理 
			        </a>
			        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
			          <a class="dropdown-item"  onclick="changeIframe('page/frame/system/modifyPassword.jsp',this)">修改密码</a>
			          <a class="dropdown-item" href="#">退出系统</a>
			        </div>
			      </li>
		        
		      
		    <!--   <li class="nav-item">
		        <a class="nav-link disabled" href="#" tabindex="-1" aria-disabled="true">Disabled</a>
		      </li> -->
		    </ul>
		<!--     <form class="form-inline my-2 my-lg-0">
		      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
		      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
		    </form> -->
		<!--   </div> -->
		</nav>
	
	
	</div>
	
	<div style="width: 75%;flex:87%;" >
		<div style="background-color: grey;" class="embed-responsive  " id="iframeDiv" >  
			        <iframe src="page/frame/dashboard/indexDashboard.jsp" id="iframPage" class="embed-responsive-item" width="100%" scrolling="yes" ></iframe>  
		</div>
	</div>
</div>
</body>

</html>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
  <script type="text/javascript">
  	function setTitle(title){
  		$("#navTitle").html(title) ;
  	}
  	function changeIframe(srcUrl , ele){
  		var title = ele.innerHTML ;
  		document.getElementById("iframPage").src = srcUrl ;
  		$('.navbar-collapse').collapse('hide');
  		$("#navTitle").html(title) ;
  	}
  
  	//设定frame的高度，少1像素，不出现双重滚动条
  	var screenHeight = window.innerHeight    ;  
//  	var screenHeight = window.screen.height    ;   
// h	var navMenuHeight = $("#navMenu").height() ;
	var navMenuHeight = 0;
  	var frameHeight = screenHeight - navMenuHeight;   
  	
 // 	alert( window.screen.height +","+ window.innerHeight +"," + window.screen.availHeight +"," + window.document.body.offsetHeight );
 	$("#iframeDiv").height(frameHeight - 20) ;
  	$("#iframPage").height(frameHeight - 20) ;
  	

  </script>