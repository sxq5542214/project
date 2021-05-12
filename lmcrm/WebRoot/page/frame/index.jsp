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
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" ></script>
 <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
    <title>龙马水厂收费系统</title>
    
  </head>

<body>
<nav class="navbar navbar-light bg-light" id="navMenu">
  <div class="container-fluid">
      <a class="navbar-brand" href="#">龙马水厂</a> 
      
      
      
  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item active">
        <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="#">Link</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <div class="dropdown-divider"></div>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
      <li class="nav-item">
        <a class="nav-link disabled" href="#">Disabled</a>
      </li>
    </ul>
    <form class="form-inline my-2 my-lg-0">
      <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
      <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
    </form>
  </div>
  
  
    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li ><a href="#">首页<span class="sr-only">(current)</span></a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">档案管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">员工管理</a></li>
            <li><a href="#">价格管理</a></li>
            <li><a href="#">用户管理</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">批量建户</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">位置变更</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">营业管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">用户开户</a></li>
            <li><a href="#">用户充值</a></li>
            <li><a href="#">扣费结算</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">用户退费</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">充值修改</a></li>
            <li><a href="#">用户补卡</a></li>
            <li><a href="#">资费调整</a></li>
            <li><a href="#">换表充值</a></li>
            <li><a href="#">补打票据</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">抄表管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">生成计划</a></li>
            <li><a href="#">手工录入</a></li>
            <li><a href="#">远程抄表</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">批量抄表</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">查询管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">台账查询</a></li>
            <li><a href="#">收费统计</a></li>
            <li><a href="#">疑户查询</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">日志查询</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">维护管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">换表维护</a></li>
            <li><a href="#">换表查询</a></li>
            <li><a href="#">表具管理</a></li>
          </ul>
        </li>
      </ul>
      <!-- <form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form> -->
      <ul class="nav navbar-nav navbar-right">
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">系统管理 <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">修改密码</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">退出系统</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>


<div style="background-color: grey;" class="embed-responsive" id="iframeDiv" >  
	        <iframe src="wx/supplier/shop/toShopManagerIndexPage.html" id="iframPage" class="embed-responsive-item" width="100%" scrolling="yes" ></iframe>  
	    </div>
</body>

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
  	var screenHeight = window.screen.height ;
  	var navMenuHeight = $("#navMenu").height() ;
  	var frameHeight = screenHeight - navMenuHeight;
  	$("#iframeDiv").height(frameHeight - 1) ;
  	$("#iframPage").height(frameHeight - 1) ;
  </script>
</html>