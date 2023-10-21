<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
  <head>
	<meta charset="utf-8">	
    <base href="<%=basePath%>">
    
    <title>请登录</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" href="/staticFiles/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="/staticFiles/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
<!-- 	<script src="/staticFiles/vue/dist/vue.js"></script>
 -->  
<style>
html,body {height: 100%;}
body {display: -ms-flexbox;display: -webkit-box;display: flex;-ms-flex-align: center;-ms-flex-pack: center;-webkit-box-align: center;align-items: center;-webkit-box-pack: center;justify-content: center;padding-top: 40px;padding-bottom: 40px;background-color: #f5f5f5;}
.form-signin {width: 100%;max-width: 330px;padding: 15px;}
.form-signin .checkbox {font-weight: 400;}
.form-signin .form-control {position: relative;box-sizing: border-box;height: auto;padding: 10px;font-size: 16px;}
.form-signin .form-control:focus {z-index: 2;}
.form-signin input[type="email"] {margin-bottom: -1px;border-bottom-right-radius: 0;border-bottom-left-radius: 0;}
.form-signin input[type="password"] {margin-bottom: 10px;border-top-left-radius: 0;border-top-right-radius: 0;}
</style> 
</head>
  
  <body class="text-center">
<form class="form-signin">
      <img class="mb-4" src="images/login/bootstrap-solid.svg" alt="" width="72" height="72">
      <h1 class="h3 mb-3 font-weight-normal">请登录</h1>
      <label for="inputName" class="sr-only">用户名</label>
      <input type="text" id="inputName" class="form-control" placeholder="请输入用户名" required autofocus>
      <label for="inputPassword" class="sr-only">密码</label>
      <input type="password" id="inputPassword" class="form-control" placeholder="请输入密码" required>
      <div class="checkbox mb-3">
        <label>
          <input type="checkbox" value="remember-me" id="remember"> Remember me
        </label>
      </div>
      <button class="btn btn-lg btn-primary btn-block" type="button" onclick="login();">登录</button>
      <p class="mt-5 mb-3 text-muted">&copy; 2021 龙马水厂</p>
    </form>

<script type="text/javascript" src="js/common/cookieUtil.js"></script>
<script type="text/javascript">
var username = getCookie("username");
var password = getCookie("password");
var remember = getCookie("remember");
if(username != null && username != ''){
	$("#inputName").val(username);
}
if(password != null && password != ''){
	$("#inputPassword").val(password);
}
if(remember != null && remember != ''){
	$("#remember").prop('checked',remember);
}

function login(){
	
	var username = $("#inputName").val();
	var password = $("#inputPassword").val();
  	if($("#remember").prop('checked')){
  		setCookie("username", username);
  		setCookie("password", password);
  		setCookie("remember", true);
  	}
  	
	$.ajax({url:"login/loginByWeb.do",
			type : "POST",
			data:{
				username: username,
				password :password
			},
		success:function(result){
	   		if(result != ''){
	   			alert(result);
	   		}else{
	   			window.location.href = '<%=basePath%>page/frame/indexFrame.jsp';
	   		}
		},
		error:function(jqXHR, textStatus, errorThrown){
			alert("登录请求失败请检查后端服务！" );
		}
		
	});
}


if(top!=self){
	alert('您的登录状态已过期，请重新登录！');
   if(top.location != self.location){
        top.location = self.location;
   }
}


function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
//    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1 ;
    var isIE11 = userAgent.indexOf('Trident/7') > -1  ;
    
     if(isIE11) {
        return 11; //IE11 
     }
     else if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }   
    } else if(isEdge) {
        return 'edge';//edge
    } else{
        return -1;//不是ie浏览器
    }
}
var ver = IEVersion();
if(ver < 11){
	alert('请升级IE浏览器版本至11以上再使用！ ');
}
</script>
  </body>
</html>
