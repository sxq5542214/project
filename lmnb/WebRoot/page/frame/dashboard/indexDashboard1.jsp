<%@page import="com.yd.business.price.bean.PriceBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
    <html lang="zh-CN">

    <head>
    	<base href="<%=basePath%>">
        <meta charset="utf-8" />
        <title>营业看板</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- third party css -->
        <!-- third party css end -->

        <!-- App css -->
        <link href="/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />

		<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
		<script src="/staticFiles/vue/dist/vue.js"></script>
    	<script src="/staticFiles/echarts@4.7.0/dist/echarts.min.js" type="text/javascript"></script>

    </head>

    <body>

        <!-- Begin page -->
        <div class="wrapper" id="dashboardDiv">

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page" style="margin-left: 0px;">
                <div class="content">

                    <!-- Topbar Start -->
                    <div class="navbar-custom">
                        <ul class="list-unstyled topbar-right-menu float-right mb-0">

                            <li class="dropdown notification-list topbar-dropdown">
                                <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                    <img src="/staticFiles/bootstrap4/hyper/assets/images/flags/china.jpg" alt="user-image" class="mr-1" height="12"> <span class="align-middle">中文</span> <i class="mdi mdi-chevron-down"></i>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right dropdown-menu-animated topbar-dropdown-menu">

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <img src="/staticFiles/bootstrap4/hyper/assets/images/flags/china.jpg" alt="user-image" class="mr-1" height="12"> <span class="align-middle">中文</span>
                                    </a>

                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <img src="/staticFiles/bootstrap4/hyper/assets/images/flags/us.jpg" alt="user-image" class="mr-1" height="12"> <span class="align-middle">English</span>
                                    </a>

                                </div>
                            </li>

                           <!--  <li class="dropdown notification-list">
                                <a class="nav-link dropdown-toggle arrow-none" data-toggle="dropdown" href="#" role="button" aria-haspopup="false" aria-expanded="false">
                                    <i class="dripicons-bell noti-icon"></i>
                                    <span class="noti-icon-badge"></span>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right dropdown-menu-animated dropdown-lg">

                                    item
                                    <div class="dropdown-item noti-title">
                                        <h5 class="m-0">
                                            <span class="float-right">
                                                <a href="javascript: void(0);" class="text-dark">
                                                    <small>Clear All</small>
                                                </a>
                                            </span>Notification
                                        </h5>
                                    </div>

                                    <div class="slimscroll" style="max-height: 230px;">
                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon bg-primary">
                                                <i class="mdi mdi-comment-account-outline"></i>
                                            </div>
                                            <p class="notify-details">Caleb Flakelar commented on Admin
                                                <small class="text-muted">1 min ago</small>
                                            </p>
                                        </a>

                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon bg-info">
                                                <i class="mdi mdi-account-plus"></i>
                                            </div>
                                            <p class="notify-details">New user registered.
                                                <small class="text-muted">5 hours ago</small>
                                            </p>
                                        </a>

                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon">
                                                <img src="/staticFiles/bootstrap4/hyper/assets/images/users/avatar-2.jpg" class="img-fluid rounded-circle" alt="" /> </div>
                                            <p class="notify-details">Cristina Pride</p>
                                            <p class="text-muted mb-0 user-msg">
                                                <small>Hi, How are you? What about our next meeting</small>
                                            </p>
                                        </a>

                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon bg-primary">
                                                <i class="mdi mdi-comment-account-outline"></i>
                                            </div>
                                            <p class="notify-details">Caleb Flakelar commented on Admin
                                                <small class="text-muted">4 days ago</small>
                                            </p>
                                        </a>

                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon">
                                                <img src="/staticFiles/bootstrap4/hyper/assets/images/users/avatar-4.jpg" class="img-fluid rounded-circle" alt="" /> </div>
                                            <p class="notify-details">Karen Robinson</p>
                                            <p class="text-muted mb-0 user-msg">
                                                <small>Wow ! this admin looks good and awesome design</small>
                                            </p>
                                        </a>

                                        item
                                        <a href="javascript:void(0);" class="dropdown-item notify-item">
                                            <div class="notify-icon bg-info">
                                                <i class="mdi mdi-heart"></i>
                                            </div>
                                            <p class="notify-details">Carlos Crouch liked
                                                <b>Admin</b>
                                                <small class="text-muted">13 days ago</small>
                                            </p>
                                        </a>
                                    </div>

                                    All
                                    <a href="javascript:void(0);" class="dropdown-item text-center text-primary notify-item notify-all">
                                        View All
                                    </a>

                                </div>
                            </li> -->

                            <li class="dropdown notification-list">
                                <a class="nav-link dropdown-toggle nav-user arrow-none mr-0" data-toggle="dropdown" href="#" role="button" aria-haspopup="false"
                                    aria-expanded="false">
                                    <span class="account-user-avatar"> 
                                        <img src="/staticFiles/bootstrap4/hyper/assets/images/users/avatar-1.jpg" alt="user-image" class="rounded-circle">
                                    </span>
                                    <span>
                                        <span class="account-user-name">{{operator_name}}</span>
                                        <span class="account-position">{{operator_role}}</span>
                                    </span>
                                </a>
                                <div class="dropdown-menu dropdown-menu-right dropdown-menu-animated topbar-dropdown-menu profile-dropdown">
                                    <!-- item-->
                                    <div class=" dropdown-header noti-title">
                                        <h6 class="text-overflow m-0">欢迎使用 !</h6>
                                    </div>

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <i class="mdi mdi-account-circle mr-1"></i>
                                        <span>My Account</span>
                                    </a>

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <i class="mdi mdi-account-edit mr-1"></i>
                                        <span>Settings</span>
                                    </a>

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <i class="mdi mdi-lifebuoy mr-1"></i>
                                        <span>Support</span>
                                    </a>

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <i class="mdi mdi-lock-outline mr-1"></i>
                                        <span>Lock Screen</span>
                                    </a>

                                    <!-- item-->
                                    <a href="javascript:void(0);" class="dropdown-item notify-item">
                                        <i class="mdi mdi-logout mr-1"></i>
                                        <span>Logout</span>
                                    </a>

                                </div>
                            </li>

                        </ul>
                        <button class="button-menu-mobile open-left disable-btn">
                            <i class="mdi mdi-menu"></i>
                        </button>
                        <div class="app-search">
                           <!--  <form>
                                <div class="input-group">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <span class="mdi mdi-magnify"></span>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="submit">Search</button>
                                    </div>
                                </div>
                            </form> -->
                        </div>
                    </div>
                    <!-- end Topbar -->
                    
                    <!-- Start Content-->
                    <div class="container-fluid">

                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                        <form class="form-inline">
                                            <div class="form-group">
                                                <div class="input-group">
                                                    <input type="text" class="form-control form-control-light" id="dash-daterange">
                                                    <div class="input-group-append">
                                                        <span class="input-group-text bg-primary border-primary text-white">
                                                            <i class="mdi mdi-calendar-range font-13"></i>
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                            <a href="javascript: void(0);" class="btn btn-primary ml-2">
                                                <i class="mdi mdi-autorenew"></i>
                                            </a>
                                            <a href="javascript: void(0);" class="btn btn-primary ml-1">
                                                <i class="mdi mdi-filter-variant"></i>
                                            </a>
                                        </form>
                                    </div>
                                    <h4 class="page-title">主页经营看板</h4>
                                </div>
                            </div>
                        </div>
                        <!-- end page title -->

                        <div class="row">
                            <div class="col-xl-12">

                                <div class="row">
                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-account-multiple widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Number of Customers">本年实收总金额</h5>
                                                <h3 class="mt-3 mb-3">￥{{paidmoney_year_sum}}元</h3>
                                                <p class="mb-0 text-muted">
                                                    <span :class="[paidmoney_year_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[paidmoney_year_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i> {{paidmoney_year_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>  
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->

                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-cart-plus widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">本月实收总金额</h5>
                                                <h3 class="mt-3 mb-3">￥{{paidmoney_month_sum}}元</h3>
                                                <p class="mb-0 text-muted">
                                                    <span :class="[ paidmoney_month_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ paidmoney_month_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i> {{paidmoney_month_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->
                                    
                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-cart-plus widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">本日实收总金额</h5>
                                                <h3 class="mt-3 mb-3">￥{{paidmoney_day_sum}}元</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ paidmoney_day_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ paidmoney_day_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{paidmoney_day_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->
                                </div> <!-- end row -->

                                <div class="row">
                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-currency-usd widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Average Revenue">现有用户总数</h5>
                                                <h3 class="mt-3 mb-3">{{user_all_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ user_all_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ user_all_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{user_all_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->

                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-pulse widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Growth">已开户总数</h5>
                                                <h3 class="mt-3 mb-3">{{user_open_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ user_open_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ user_open_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{user_open_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->
                                    
                                    <div class="col-4">
                                        <div class="card widget-flat">
                                            <div class="card-body">
                                                <div class="float-right">
                                                    <i class="mdi mdi-pulse widget-icon"></i>
                                                </div>
                                                <h5 class="text-muted font-weight-normal mt-0" title="Growth">已报停总数</h5>
                                                <h3 class="mt-3 mb-3">{{user_stop_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ user_stop_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ user_stop_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{user_stop_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                            </div> <!-- end card-body-->
                                        </div> <!-- end card-->
                                    </div> <!-- end col-->
                                </div> <!-- end row -->


                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                    <div class="card-body">
                                        <div class="dropdown float-right">
                                            <a href="#" class="dropdown-toggle arrow-none card-drop" data-toggle="dropdown" aria-expanded="false">
                                                <i class="mdi mdi-dots-vertical"></i>
                                            </a>
                                            <div class="dropdown-menu dropdown-menu-right">
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item">Sales Report</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item">Export Report</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item">Profit</a>
                                                <!-- item-->
                                                <a href="javascript:void(0);" class="dropdown-item">Action</a>
                                            </div>
                                        </div>
                                        <h4 class="header-title mb-3">近三个月实收金额概览</h4>

                                        <div style="height: 263px;" class="chartjs-chart" id="nearThreeMonthPaidMoneyChart" >
                                            
                                        </div>
                                    </div> <!-- end card-body-->
                                </div> <!-- end card-->

                            </div> <!-- end col -->
                        </div>
                        
                        
                        

                         <div class="row">
                             <div class="col-6">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-account-multiple widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Number of Customers">轻松水表数</h5>
                                         <h3 class="mt-3 mb-3">{{device_qs_count}}个</h3>
                                         <p class="mb-0 text-muted">
                                              <span :class="[ device_qs_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ device_qs_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{device_qs_count_ratio}}% </span>
                                             <span class="text-nowrap">环比上月</span>  
                                         </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
	
                             <div class="col-6">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-cart-plus widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Number of Orders">鲁正水表数</h5>
                                         <h3 class="mt-3 mb-3">{{device_lz_count}}个</h3>
                                         <p class="mb-0 text-muted">
                                              <span :class="[ device_lz_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ device_lz_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{device_lz_count_ratio}}%</span>
                                             <span class="text-nowrap">环比上月</span>
                                         </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
                         </div> <!-- end row -->
	
	
						
                        
                        
                        
                        <!-- end row -->
                        <div class="row">
                        	 <div class="col-3">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-pulse widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Growth">疑户_3个月未缴费</h5>
                                         <h3 class="mt-3 mb-3">{{dubious_3_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ dubious_3_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ dubious_3_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{dubious_3_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
                        	 <div class="col-3">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-pulse widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Growth">疑户_6个月未缴费</h5>
                                         <h3 class="mt-3 mb-3">{{dubious_6_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ dubious_6_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ dubious_6_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{dubious_6_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
                        	 <div class="col-3">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-pulse widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Growth">疑户_9个月未缴费</h5>
                                         <h3 class="mt-3 mb-3">{{dubious_9_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ dubious_9_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ dubious_9_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{dubious_9_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
                        	 <div class="col-3">
                                 <div class="card widget-flat">
                                     <div class="card-body">
                                         <div class="float-right">
                                             <i class="mdi mdi-pulse widget-icon"></i>
                                         </div>
                                         <h5 class="text-muted font-weight-normal mt-0" title="Growth">疑户_12个月未缴费</h5>
                                         <h3 class="mt-3 mb-3">{{dubious_12_count}}户</h3>
                                                <p class="mb-0 text-muted">
                                                     <span :class="[ dubious_12_count_ratio >0 ? 'text-danger mr-2':'text-success mr-2' ]"><i :class="[ dubious_12_count_ratio >0 ? 'mdi mdi-arrow-up-bold':'mdi mdi-arrow-down-bold' ]"></i>  {{dubious_12_count_ratio}}%</span>
                                                    <span class="text-nowrap">环比上月</span>
                                                </p>
                                     </div> <!-- end card-body-->
                                 </div> <!-- end card-->
                             </div> <!-- end col-->
                        </div>


                    </div>
                    <!-- container -->

                </div>
                <!-- content -->

                <!-- Footer Start -->
                <footer class="footer" style="left:0px;">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-6">
                                2021  © 霍邱县龙马自来有限公司 -  版权所有
                            </div>
                            <div class="col-md-6">
                                <div class="text-md-right footer-links d-none d-md-block">
                                    <a href="javascript: void(0);">About</a>
                                    <a href="javascript: void(0);">Support</a>
                                    <a href="javascript: void(0);">Contact Us</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </footer>
                <!-- end Footer -->

            </div>

            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->


        </div>
        <!-- END wrapper -->


        <!-- bundle -->
        <script src="/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
		<script src="page/frame/dashboard/js/indexDashboard.js" type="text/javascript"></script>
		

    </body>

</html>