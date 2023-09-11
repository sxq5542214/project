<%@page import="com.yd.business.operator.bean.OperatorBean"%>
<%@page import="com.yd.business.system.bean.SystemMenuBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
OperatorBean op = (OperatorBean)request.getAttribute("operator");
List<SystemMenuBean> menuList = (List<SystemMenuBean> )request.getAttribute("menuList");
if(menuList == null) menuList = Collections.EMPTY_LIST;

%> 
<!DOCTYPE html>
    <html lang="zh-CN">

    <head>
        <base href="<%=basePath%>">
        <meta charset="utf-8" />
        <title>霍邱县农饮智慧云平台</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    	<meta http-equiv="content-Type" content="text/html; charset=utf-8" />
    	<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
        <meta content="ice" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- third party css -->
        <link href="/staticFiles/bootstrap4/hyper/assets/css/vendor/jquery-jvectormap-1.2.2.css" rel="stylesheet" type="text/css" />
        <!-- third party css end -->

        <!-- App css -->
        <link href="/staticFiles/bootstrap4/hyper/assets/css/icons1.min.css" rel="stylesheet" type="text/css" />
        <link href="/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
	
	
	
    
	
<style type="text/css">
.side-nav .side-nav-link{font-size: 1.2rem;}
</style>
    </head>

    <body style="padding-bottom: 0px;" >

        <!-- Begin page -->
        <div class="wrapper" >

            <!-- ========== Left Sidebar Start ========== -->
            <div class="left-side-menu" id="menuManagerDiv">

                <div class="slimscroll-menu" id="left-side-menu-container">

                    <!-- LOGO -->
                    <a href="javascript: void(0);" class="logo text-center">
                        <span class="logo-lg">
                            <img src="/staticFiles/bootstrap4/hyper/assets/images/logo.png" alt="" height="16">
                        </span>
                        <span class="logo-sm">
                            <img src="/staticFiles/bootstrap4/hyper/assets/images/logo_sm.png" alt="" height="16">
                        </span>
                    </a>

                    <!--- Sidemenu -->
                    <ul class="metismenu side-nav">

						<li class="side-nav-title side-nav-item" style="font-size: 1.2rem;">导航菜单  </li>

                        <li class="side-nav-item">
                            <a onclick="changeIframe('page/frame/dashboard/indexDashboard1.jsp',this)" href="javascript: void(0);" class="side-nav-link">
                                <i class="dripicons-meter"></i>
                                <!-- <span class="badge badge-success float-right">3</span> -->
                                <span> 首页 <label style="color:springgreen;float: right;">【<%=op.getO_name() %>】 </label>  </span>
                            </a>
                        </li>


            
       					<% for(SystemMenuBean menu : menuList){
       						if(menu.getParentid() == null){
       					 %>
                        <li class="side-nav-item"  v-for="(menu1,index) in menuLevel1List" >
                            <a href="javascript: void(0);" class="side-nav-link">
                                <i class="<%=menu.getIcon_path()%>"></i>
                                <span> <%=menu.getName() %></span>
                                <span class="menu-arrow"></span>
                            </a>
                            <ul class="side-nav-second-level" aria-expanded="false">
                            
                            <%
                            	for(SystemMenuBean menu2 : menuList){
                            	if(menu2.getParentid() == menu.getId()){
                             %>
                                <li >
                                    <a class="<%=menu2.getIcon_path()%>" onclick="<%=menu2.getPath()%>" href="javascript: void(0);"> <%=menu2.getName()%></a>
                                </li>
                                <%}} %>
                            </ul>
                        </li>
            			<%}} %>
            
                    </ul>

                    <!-- Help Box -->
 <!--                   <div class="help-box text-white text-center" style="margin-top: 0px;">
                        <a href="javascript: void(0);" class="float-right close-btn text-white">
                            <i class="mdi mdi-close"></i>
                        </a>
                        <img src="/staticFiles/bootstrap4/hyper/assets/images/help-icon.svg" height="90" alt="Helper Icon Image" />
                        <h5 class="mt-3">更多功能建议？</h5>
                        <p class="mb-3">请点击这里。。。</p>
                       <a href="javascript: alert('建议功能正在建设中');" class="btn btn-outline-light btn-sm">请点这里</a>
                   </div> -->
                    <!-- end Help Box -->
                    <!-- End Sidebar -->

                    <div class="clearfix"></div>

                </div>
                <!-- Sidebar -left -->

            </div>
            <!-- Left Sidebar End -->

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page">
                <div class="content">
			        
						<div style="background-color: grey;" class="embed-responsive  " id="iframeDiv" >  
							        <iframe src="page/frame/dashboard/indexDashboard1.jsp" id="iframPage" class="embed-responsive-item" width="100%" scrolling="auto" ></iframe>  
						</div>
				</div>
				
			</div>
		</div>
				
	  <script src="/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

	<script >
		  	function logout(){
		    	if(confirm("确定退出系统？")){
		    		$.ajax({url:"login/unloginByWeb.do",
		    		success:function(res){
		    			window.location.href = '<%=basePath%>';
					}});
					window.location.href = '<%=basePath%>';
		    	}
		    }
		  	function setTitle(title){
		  		$("#navTitle").html(title) ;
		  	}
		  	function changeIframe(srcUrl , ele){
		  		var title = ele.innerHTML ;
		  		document.getElementById("iframPage").src = srcUrl ;
		  		$('.navbar-collapse').collapse('hide');
		  		$("#navTitle").html(title) ;
		  	}
		  	
		  	
		  	function initData(){
		  	
		  
		  	//设定frame的高度，少1像素，不出现双重滚动条
			  	var screenHeight = window.innerHeight    ;  
			//  	var screenHeight = window.screen.height    ;   
			// h	var navMenuHeight = $("#navMenu").height() ;
				var navMenuHeight = 0;
			  	var frameHeight = screenHeight - navMenuHeight;   
			  	
			 // 	alert( window.screen.height +","+ window.innerHeight +"," + window.screen.availHeight +"," + window.document.body.offsetHeight );
			 	$("#iframeDiv").height(frameHeight - 20) ;
			  	$("#iframPage").height(frameHeight - 20) ;
			//  	$("#iframPage").width($("#iframPage").width()-100) ;
			  	
		  		
				
		  	}
		  	
		  	initData();
		
	</script>
	
       
    </body>

</html>