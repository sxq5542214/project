<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
    <head>
		<meta charset="utf-8">	
	    <base href="<%=basePath%>">
        <title>霍邱县农饮智慧云平台-请登录</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
    	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
        <!-- App favicon -->
        <link rel="shortcut icon" href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- App css -->
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
<style type="text/css">
.custom-control-input:checked~.custom-control-label::before {
    color: #0147FF;
    border-color: #0147FF;
    background-color: #0147FF;
}
.bg-primary {
    background-color: #0147FF!important;
}
</style>
    </head>

    <body class="authentication-bg" style="font-family: 宋体 ;background: url('images/login/login_background2.png');background-size: cover;background-position: center;">
		<div style="position: fixed;">
			<img alt="" src="images/login/logo.png" style="width: 75%;margin-left: 25px;">
		</div>
        <div class="account-pages mt-5 mb-5" >
            <div class="container">
                <div class="row justify-content-center" style="float: right;width: 400px;">
                    <div class="col-12">
                        <div class="card" style="margin-top: 45%;width: 100%;">

                            <!-- Logo -->
           <!--             		<div class="card-header text-center bg-primary">
                                     <a href="javascript:void(0);">
                                    <span><img src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/images/logo.png" alt="" height="18"></span>
                                </a>
                                <div class="text-center w-75 m-auto">
                                    <h3 class=" text-center font-weight-bold" style="color: white;">霍邱县农饮智慧云平台</h3>
                                </div>
                            </div>  -->

                            <div class="card-body p-4 " style="padding-bottom: 1rem!important;padding-top:  1rem!important;">
                                
                                
								<span style="color: #2E76FF;font-size: 1.2rem;"><strong> 欢迎登陆霍邱县智慧农饮云平台</strong></span>
                                <div style="width: 100%;border: 1px;height: 1.5px;background-color: #2E76FF;margin: 0 auto;"></div>
                                
                                <form action="javascript:void(0);">

                                    <div class="form-group row" style="margin-top: 15px;">
                                       <!--  <label for="emailaddress">用户名</label> -->
                                       <div class="col-1" style="padding-right: 0px;">
                                        	<i class="form-control mdi mdi-18px mdi-zodiac-taurus mdi-rotate-180" style="background-color:transparent;border:0;"></i>
                                        </div>
                                        <div class="col-11">
                                        	<input class="form-control"  type="text" id="inputName" name="inputName" required placeholder="请输入用户名" style="background-color:transparent;border:0;padding-bottom: 0;">
                                        </div>
                                        <div style="width: 90%;border: 1px;height: 1px;background-color: #e3eaef;margin: 0 auto;"></div>
                                    </div>

 									<div class="form-group row" style="margin-top: 10px;">
                                       <!--  <label for="emailaddress">密码</label> -->
                                       <div class="col-1" style="padding-right: 0px;">
                                        	<i class="form-control mdi mdi-18px mdi-windows glyphicon glyphicon-lock" style="background-color:transparent;border:0;"></i>
                                        </div>
                                        <div class="col-11">
                                        	<input class="form-control"  type="password" id="inputPassword" required placeholder="请输入密码" style="background-color:transparent;border:0;padding-bottom: 0;">
                                        </div>
                                        <div style="width: 90%;border: 1px;height: 1px;background-color: #e3eaef;margin: 0 auto;"></div>
                                    </div>



                                    <div class="form-group mb-0 text-center">
                                        <button class="btn btn-rounded" style="height:50px;background :#0147FF;width: 90%;color: white;margin-top: 20px;" type="submit" onclick="login();"> 登录平台</button>
                                    </div>

                                    <div class="form-group " style="margin-top: 20px;">
                                        <div class="custom-control custom-checkbox">
                                            <input type="checkbox" class="custom-control-input" id="remember" checked>
                                            <label class="custom-control-label" for="checkbox-signin">记住密码</label>
                                        </div>
                                    </div>
                                </form>
                            </div> <!-- end card-body -->
                        </div>
                        <!-- end card -->

                        <div class="row mt-3">
                            <div class="col-12 text-center">
<!--                                 <p class="text-muted">Don't have an account? <a href="pages-register.html" class="text-muted ml-1"><b>Sign Up</b></a></p>
 -->                            </div> <!-- end col -->
                        </div>
                        <!-- end row -->

                    </div> <!-- end col -->
                </div>
                <!-- end row -->
            </div>
            <!-- end container -->
        </div>
        <!-- end page -->

        <footer class="footer footer-alt" style="color: white;font-size: 1.1rem;">
           Copyright   ©   2021   霍邱县龙马自来水有限公司 
        </footer>

        <!-- App js -->
        <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

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
  	
  	
  	$.NotificationApp.send("请稍等！","正在登录中，请稍后","bottom-center","rgba(0,0,0,0.2)","info");
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
    var isEdge = userAgent.indexOf("Edg") > -1 && !isIE; //判断是否IE的Edge浏览器  
//    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1 ;
    var isIE11 = userAgent.indexOf('Trident/7') > -1  ;
//alert(userAgent);    
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

if( 'edge' == ver || ver == 11 ){
}else{
	alert('请使用edge浏览器 或 升级IE浏览器版本至11以上再使用！ ');
}



</script>

    </body>
</html>
