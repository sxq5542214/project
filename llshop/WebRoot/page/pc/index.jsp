<%@page import="com.yd.business.system.bean.SystemMenuBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	CustomerBean bean = (CustomerBean) request.getAttribute("user");
	Map<String,List<SystemMenuBean>> menuMap = (Map<String,List<SystemMenuBean>>)request.getAttribute("menuMap");
	List<SystemMenuBean> firstMenuList = menuMap.get("firstMenu");
	
	
	
%>
<!DOCTYPE html>
<html>
  <head>
  	<base href="<%=basePath%>">
    <title>万大商城管理平台</title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <meta name="description" content="Blue Moon - Responsive Admin Dashboard" />
    <meta name="keywords" content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
    <meta name="author" content="Bootstrap Gallery" />
    <link rel="shortcut icon" href="img/favicon.ico">
    
    <link href="page/pc/css/bootstrap.min.css" rel="stylesheet">
    <link href="page/pc/css/new.css" rel="stylesheet"> 
    <link href="page/pc/css/charts-graphs.css" rel="stylesheet">
    <!-- Datepicker CSS -->
    <link rel="stylesheet" type="text/css" href="page/pc/css/datepicker.css">

    <link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">

    <!-- HTML5 shiv and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="js/html5shiv.js"></script>
      <script src="js/respond.min.js"></script>
    <![endif]-->
  </head>

  <body>

    <!-- Header Start -->
    <header>
      <div class="pull-right">
        <ul id="mini-nav" class="clearfix">
          <li class="list-box user-profile">
            <a id="drop7" href="#" role="button" class="dropdown-toggle user-avtar" data-toggle="dropdown">
              <img src="page/pc/img/user5.png" alt="Bluemoon User">
            </a>
            <ul class="dropdown-menu server-activity">
              <li onclick="javascript:ifmPage('admin/person.do');">
                <p><i class="fa fa-cog text-info"></i> 个人信息修改</p>
              </li>
              <li>
                <div class="demo-btn-group clearfix">
                  <a href="loginAccount.html" class="btn btn-danger">
                    系统退出
                  </a>
                </div>
              </li>
            </ul>
          </li>
        </ul>
      </div>
    </header>
    <!-- Header End -->

    <!-- Main Container start -->
    <div class="dashboard-container">

      <div class="container">
        <!-- Top Nav Start -->
        <div id='cssmenu'>
          <ul>
            <li class='active'>
              <a href="javascript:ifmPage('admin/ifmIdx.do');">
                <i class="fa fa-home"></i>
                首页
              </a>
            </li>
            
            <% for(SystemMenuBean menu : firstMenuList) {%>
            <li class='' id="<%=menu.getId()%>">
              <a href="javascript:ifmPage('<%=menu.getPath()%>');">
                <i class="fa fa-comments-o"></i>
              	<span class="fa-title"><%=menu.getName() %></span>
              </a>
            </li>
            
            <%} %>
            
            <%-- <li class=''>
              <a href="javascript:ifmPage('discount.do');"><i class="fa fa-flask"></i>折扣管理</a>
            </li>
            <%
            if(bean.getIscreate()==CustomerBean.ISCREATE_YES){
            %>
            <li>
              <a href="javascript:ifmPage('sup.do');">
                <i class="fa fa-align-justify"></i>
                商户管理
              </a>
            </li>
            <%
            }
             %>
            <li class=''>
              <a href="javascript:ifmPage('power.do');"><i class="fa fa-users"></i>授权管理</a>
            </li>
            <li>
              <a href="javascript:ifmPage('operat.do');"><i class="fa fa-bar-chart-o"></i>操作员管理</a>
            </li>
            <li class=''>
              <a href="javascript:ifmPage('productmgr.do');"><i class="fa fa-dropbox"></i>商品管理</a>
            </li>
            <%if(bean.getType()==CustomerBean.TYPE_REG|| bean.getType()==CustomerBean.TYPE_ADM){%>
            <li class=''>
              <a href="javascript:ifmPage('product.do');"><i class="fa fa-table"></i>可售商品</a>
            </li>
            <li class=''>
              <a href="javascript:ifmPage('recharge.do');"><i class="fa fa-jpy"></i>充值</a>
            </li>
            <li class=''>
              <a href="javascript:ifmPage('<%if(bean.getType()==CustomerBean.TYPE_REG){%>api.do<%}else{%>auditlist.do<%}%>');"><i class="fa fa-font"></i><%=bean.getType()==CustomerBean.TYPE_ADM?"API审核":"API申请" %></a>
            </li>
            
            <%if(bean.getType() == CustomerBean.TYPE_ADM) {%>
            	<li class=''>
	              <a href="javascript:ifmPage('supplier/toCreateCardSecretPage.do');"><i class="fa fa-pencil-square-o"></i>生成卡密</a>
	            </li>
            <%} }%>
            <li>
              <a href="javascript:ifmPage('supplier/cardSecret_query.do');">
                <i class="fa fa-ticket"></i>
                卡密管理
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('system.do');">
                <i class="fa fa-cogs"></i>
                系统设置
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('person.do');">
                <i class="fa fa-male"></i>
                个人信息
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('listSms.do');">
                <i class="fa fa-comment-o"></i>
                短信管理
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('msgcenter/article/toQueryArticleListPage.do');">
                <i class="fa fa-file-text"></i>
              	  消息文章管理
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('msgcenter/article/toQueryActionArticleRelationListPage.do');">
                <i class="fa fa-retweet"></i>
              	  动作与文章关系
              </a>
            </li>
            <li>
              <a href="javascript:ifmPage('comment/showCommentListForManage.do');">
                <i class="fa fa-comments-o"></i>
              	<span class="fa-title">留言管理</span>
              </a>
            </li>
            <li>
            <!-- fa图标网址  http://www.yeahzan.com/fa/facss.html -->
              <a href="javascript:ifmPage('../wechat/delivery/showWechatMaterialInfo.do');">
                <i class="fa fa-exchange"></i>
              	 素材分发管理
              </a>
            </li>--%>
          </ul>
        </div> 
       	<!-- Sub Nav End -->
        <div class="sub-nav hidden-sm hidden-xs">
          <ul>
            <li><a href="#" class="heading">首页</a></li>
          	<%for(String key : menuMap.keySet())
          	if(!"firstMenu".equals(key)){
          		List<SystemMenuBean> menus = menuMap.get(key);
          		for(SystemMenuBean menu : menus){
          	%>
          	<li style="display: none;" name="id<%=menu.getParentid()%>" >	
          		<a href="javascript:ifmPage('<%=menu.getPath() %>')" class="heading"><%=menu.getName() %></a>
   			</li>
      		<%}} %>
    	
          	
          </ul>
        </div>
        <!-- Sub Nav End -->
        <div class="dashboard-wrapper-lg">
			<iframe src="admin/ifmIdx.do" width="100%" scrolling="yes" onload="this.height=this.contentWindow.document.documentElement.scrollHeight" frameborder="0" id="iframe-content"></iframe>
        </div>
 		<footer>
          <p>万大商城管理平台</p>
        </footer>
      </div>
    </div>
    <!-- Main Container end -->

    <script src="page/pc/js/jquery.js"></script>
    <script src="page/pc/js/bootstrap.min.js"></script>
    
    <!-- Custom JS -->
    <script src="js/Util.js"></script>
    <script src="page/pc/js/menu.js"></script>
    <script type="text/javascript">
    	
    
		function ifmPage(url){
			$('#iframe-content').attr('src',url);
			$('#iframe-content').load(function(){
				var offsetHeight = document.body.offsetHeight-200;
				var height = this.contentWindow.document.documentElement.scrollHeight;
				console.log(offsetHeight+"=-=-=-=-"+height);
				if(height>offsetHeight) this.height = height;
				else this.height=offsetHeight;
			});
		}
      //ScrollUp
      $(function () {
		$('#cssmenu ul li').click(function(){
			var n = $('#cssmenu ul li').index(this);
			var title = $(this).find(".fa-title").html();
			$('#cssmenu ul li').removeClass('active');
			$('#cssmenu ul li').eq(n).addClass('active');
			
			var id = $(this).attr('id');
			
			$('.sub-nav ul li').hide();
			var li = document.getElementsByName('id'+id);
			for(var i = 0 ; i < li.length; i++){
				li[i].style.display = 'block' ;
			}
			
			
			<%-- 
			if(n==0) {
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li><a href="javascript:ifmPage(\'ifmIdx.do\');" class="heading">首页</a></li>');
			}else if(n==1){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'discount.do\');" class="heading">折扣管理</a><li><a href="javascript:ifmPage(\'discount.do\');">折扣规则管理</a></li><li><a href="javascript:ifmPage(\'suppower.do\');">分配折扣规则</a></li></li>');
			}
			<%
            if(bean.getIscreate()==CustomerBean.ISCREATE_YES){
            %>
			if(n==2) {
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'sup.do\');" class="heading">商户管理</a><li><a href="javascript:ifmPage(\'sup.do\');">子商户管理</a></li><li><a href="javascript:ifmPage(\'mysup.do\');">我的商户</a></li><li><a href="javascript:ifmPage(\'supplierEvent/list.do\');">活动管理</a></li><li><a href="javascript:ifmPage(\'supplierTopic/list.do\');">话题管理</a></li></li>');
			}
			else if(n==3){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'power.do\');" class="heading">授权管理</a><li><a href="javascript:ifmPage(\'propower.do\');">商品授权</a></li></li>');
			}else if(n==4){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'operat.do\');" class="heading">操作员管理</a></li>');
			}else if(n==5) {
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'productmgr.do\');" class="heading">商品管理</a><li><a href="javascript:ifmPage(\'applymgr.do\');">我的申领</a></li><li><a href="javascript:ifmPage(\'applyAdt.do\');">申领审核</a></li><li><a href="javascript:ifmPage(\'productdesign.do\');">商品指派</a></li><li><a href="javascript:ifmPage(\'toPowerLog.do\');">商品授权记录</a></li></li>');
			}
			<%if(bean.getType()==CustomerBean.TYPE_REG|| bean.getType()==CustomerBean.TYPE_ADM){%>
			else if(n==6){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'product.do\');" class="heading">可售商品</a></li>');
			}else if(n==7){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'recharge.do\');" class="heading">充值</a></li>');
			}else if(n==8){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'<%if(bean.getType()==CustomerBean.TYPE_REG){%>api.do<%}else{%>auditlist.do<%}%>\');" class="heading"><%=bean.getType()==CustomerBean.TYPE_ADM?"API审核":"API申请" %></a><%if(bean.getType()==CustomerBean.TYPE_REG&&bean.getAuditstatus()==CustomerBean.AUDITSTATUS_YES){%><li><a href="javascript:ifmPage(\'applylist.do\');">我的申请</a></li><%}if(bean.getType()==CustomerBean.TYPE_ADM){%><li><a href="javascript:ifmPage(\'auditlist.do\');">资质审核</a></li><li><a href="javascript:ifmPage(\'auditProductList.do\');">商品审核</a></li><%}%></li>');
			}else if(n==9){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/toCreateCardSecretPage.do\');" class="heading">生成卡密</a></li>');
			}else if(n==10){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/cardSecret_query.do\');" class="heading">卡密查询</a></li>');
			}else if(n==11){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'system.do\');" class="heading">系统设置</a></li>');
			}else if(n==12){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'person.do\');" class="heading">个人信息</a></li>');
			}else if(n==13){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'listSms.do\');" class="heading">短信管理</a><li><a href="javascript:ifmPage(\'listSms.do\');">短信管理</a></li><li><a href="javascript:ifmPage(\'listSmsSign.do\');">签名管理</a></li><li><a href="javascript:ifmPage(\'listSmsCust.do\');">客户短信模板管理</a></li><li><a href="javascript:ifmPage(\'sendSmsTest.do\');">短信测试</a></li></li>');
			}
			<%}else{%>
			else if(n==6){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/cardSecret_query.do\');" class="heading">卡密查询</a></li>');
			}else if(n==7){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'system.do\');" class="heading">系统设置</a></li>');
			}else if(n==8){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'person.do\');" class="heading">个人信息</a></li>');
			}
			<%}%>
			<%}else{
			%>
			else if(n==2){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'power.do\');" class="heading">授权管理</a><li><a href="javascript:ifmPage(\'propower.do\');">商品授权</a></li></li>');
			}else if(n==3){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'operat.do\');" class="heading">操作员管理</a></li>');
			}else if(n==4) {
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'productmgr.do\');" class="heading">商品管理</a><li><a href="javascript:ifmPage(\'applymgr.do\');">我的申领</a></li><li><a href="javascript:ifmPage(\'applyAdt.do\');">申领审核</a></li><li><a href="javascript:ifmPage(\'toPowerLog.do\');">商品授权记录</a></li></li>');
			}
			<%if(bean.getType()==CustomerBean.TYPE_REG|| bean.getType()==CustomerBean.TYPE_ADM){%>
			else if(n==5){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'product.do\');" class="heading">可售商品</a></li>');
			}else if(n==6){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'recharge.do\');" class="heading">充值</a></li>');
			}else if(n==7){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'<%if(bean.getType()==CustomerBean.TYPE_REG){%>api.do<%}else{%>auditlist.do<%}%>\');" class="heading"><%=bean.getType()==CustomerBean.TYPE_ADM?"API审核":"API申请" %></a><%if(bean.getType()==CustomerBean.TYPE_REG&&bean.getAuditstatus()==CustomerBean.AUDITSTATUS_YES){%><li><a href="javascript:ifmPage(\'applylist.do\');">我的申请</a></li><%}if(bean.getType()==CustomerBean.TYPE_ADM){%><li><a href="javascript:ifmPage(\'auditlist.do\');">资质审核</a></li><li><a href="javascript:ifmPage(\'auditProductList.do\');">商品审核</a></li><%}%></li>');
			}else if(n==8){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/toCreateCardSecretPage.do\');" class="heading">生成卡密</a></li>');
			}else if(n==9){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/cardSecret_query.do\');" class="heading">卡密查询</a></li>');
			}else if(n==10){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'system.do\');" class="heading">系统设置</a></li>');
			}else if(n==11){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'person.do\');" class="heading">个人信息</a></li>');
			}else if(n==12){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'listSms.do\');" class="heading">短信管理</a><li><a href="javascript:ifmPage(\'listSms.do\');">短信管理</a></li><li><a href="javascript:ifmPage(\'listSmsSign.do\');">签名管理</a></li><li><a href="javascript:ifmPage(\'listSmsCust.do\');">客户短信模板管理</a></li><li><a href="javascript:ifmPage(\'sendSmsTest.do\');">短信测试</a></li></li>');
			}
			<%}else{%>
			else if(n==5){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'supplier/cardSecret_query.do\');" class="heading">卡密查询</a></li>');
			}else if(n==6){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'system.do\');" class="heading">系统设置</a></li>');
			}else if(n==7){
				$('.sub-nav ul li').remove();
				$('.sub-nav ul').append('<li class="hidden-sm hidden-xs"><a href="javascript:ifmPage(\'person.do\');" class="heading">个人信息</a></li>');
			}
			<%}%>
			<%
			}%> --%>
			
			$('.sub-nav ul li a').click(function(){
				var n = $('.sub-nav ul li a').index(this);
				$('.sub-nav ul li a').removeClass('selected');
				$('.sub-nav ul li a').eq(n).addClass('selected');
			});
		});
      });
    </script>

  </body>
</html>