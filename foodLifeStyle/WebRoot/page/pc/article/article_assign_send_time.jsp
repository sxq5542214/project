<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	MsgCenterArticleBean bean = (MsgCenterArticleBean)request.getAttribute("article");
	
	if(bean.getAssign_send_status() == null){
		bean.setAssign_send_status(-1);
	}
	if(bean.getAssign_send_time() == null){
		bean.setAssign_send_time(DateUtil.getNowDateStr());
	}
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
    <title></title>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"> 
    <meta name="description" content="Blue Moon - Responsive Admin Dashboard" />
    <meta name="keywords" content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
    <meta name="author" content="Bootstrap Gallery" />
    <link rel="shortcut icon" href="page/pc/img/favicon.ico">
    
    <link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

    <link href="page/pc/css/new.css" rel="stylesheet">
    <!-- Important. For Theming change primary-color variable in main.css  -->

    <link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
    <script type="text/javascript" src="page/pc/js/pc/article/article_update.js"></script>
  </head>

  <body style="background-color:#f7f7f7">
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						指定文章发送时间
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form class="form-horizontal row-border" action="admin/msgcenter/article/updateArticleAssignSendTime.do">
						<input type="hidden" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
						
						<div class="form-group">
							<label class="col-md-2 control-label">文章名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<label class="control-label" ><%=StringUtil.convertNull(bean.getName()) %></label>
									</div>
									<div class="col-xs-1">
										<label class="control-label"></label>
									</div>
									<div class="col-xs-5">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">发送状态</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="assign_send_status" class="form-control">
										
				                            <option value="" <%= bean.getAssign_send_status() == -1 ? " selected=\"selected\"":"" %>>
				                              	未定义
				                            </option>
				                            <option value="0" <%= bean.getAssign_send_status() == MsgCenterArticleBean.ASSIGN_SEND_STATUS_WAIT ? " selected=\"selected\"":"" %>>
				                              	待发送
				                            </option>
				                            <option value="1" <%= bean.getAssign_send_status() == MsgCenterArticleBean.ASSIGN_SEND_STATUS_SUCCESS ? " selected=\"selected\"":"" %>>
				                              	已发送
				                            </option>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">指定发送时间</label>
									</div>
									<div class="col-xs-5">
										<input type="text" name="assign_send_time" value="<%=StringUtil.convertNull(bean.getAssign_send_time()) %>" class="form-control">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" id="submitSupplier" class="btn btn-info" value="确定"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
    <script src="page/pc/js/jquery.js"></script>
    <script src="page/pc/js/bootstrap.min.js"></script>
    <script src="page/pc/js/jquery.scrollUp.js"></script>
    <script src="page/pc/js/jquery.dataTables.js"></script>

    <!-- Custom JS -->
    <script src="page/pc/js/menu.js"></script>
    
    <script type="text/javascript">
      //ScrollUp
      $(function () {
        $.scrollUp({
          scrollName: 'scrollUp', // Element ID
          topDistance: '300', // Distance from top before showing element (px)
          topSpeed: 300, // Speed back to top (ms)
          animation: 'fade', // Fade, slide, none
          animationInSpeed: 400, // Animation in speed (ms)
          animationOutSpeed: 400, // Animation out speed (ms)
          scrollText: 'Top', // Text for element
          activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
        });
      });

     /*  //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
        "iDisplayLength":10,
        "bFilter":false,
        "bRetrieve":true,
        "bPaginate":false,
        "sPaginationType": "full_numbers",
        "bInfo": false,
        "bSort":false
        });
      }); */

    </script>

  </body>
</html>