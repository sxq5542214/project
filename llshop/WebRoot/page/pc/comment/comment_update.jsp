<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	CommentInfoBean bean = (CommentInfoBean)request.getAttribute("comment");
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
						新增评论信息
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
				<div class="form-group">
							<label class="col-md-2 control-label">留言内容</label>
							<div class="col-md-10" id="material_div">
								<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
									<thead>
										<tr>
											<th width="10%" >留言人</th>
											<th width="65%"> 留言内容 </th>
											<th width="15%"> 留言时间</th>
											<th width="10%"> 留言状态</th>
										</tr>
									</thead>
									<tbody id="material_list">
										<tr class="gradeA ">
											<td style="margin: 0 auto;text-align: center;"> <%=StringUtil.convertNull(bean.getNick_name()) %> </td>
											<td><%=StringUtil.convertNull(bean.getMsgtext()) %> </td>
											<td><%=StringUtil.convertNull(bean.getCreatetime()) %> </td>
											<td><%=StringUtil.convertNull(bean.getDictValueByField("status")) %> </td>
										</tr>
									</tbody>
								</table>
							</div>
							
							<label class="col-md-2 control-label">您的评论内容</label>
							<div class="col-md-10" id="material_div">
								<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
									<thead>
										<tr>
											<th width="10%"> 评论人 </th>
											<th> 评论内容 </th>
											<th width="15%"> 评论时间 </th>
											<th width="10%"> 操作 </th>
										</tr>
									</thead>
									<tbody id="material_list">
									<%for(CommentInfoBean reBean:bean.getReplyBeanList()){ %>
										<tr class="gradeA ">
											<td  style="margin: 0 auto;text-align: center;"><%=StringUtil.convertNull(reBean.getNick_name()) %> </td>
											<td><%=StringUtil.convertNull(reBean.getMsgtext()) %> </td>
											<td><%=StringUtil.convertNull(reBean.getCreatetime()) %> </td>
											<td><div class="btn-group">
											<input type="hidden" class="beanid" name="id" value="<%=StringUtil.convertNull(reBean.getId()) %>">
												<button data-toggle="button" class="btn btn-default btn-del">删除</button>
												</div>
											</td>
										</tr>
										<%} %>
									</tbody>
								</table>
							</div>
							
                     	<div class="clearfix"></div>
						</div>
					<form class="form-horizontal row-border" action="admin/comment/toCommentInstertForAdmin.do" method="post">
						<input type="hidden" name="parentid" value="<%=StringUtil.convertNull(bean.getId()) %>">
						<input type="hidden" name="conf_id" value="<%=StringUtil.convertNull(bean.getComment_conf_id()) %>">
						<input type="hidden" name="nowpage" value="<%=StringUtil.convertNull(bean.getNowpage()) %>">
						
						<div class="form-group">
							<label class="col-md-2 control-label">您的评论</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-md-10">
										<textarea class="form-control" rows="5" id="msgtext" placeholder="请输入评论内容" name="msgtext"></textarea>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" id="submitSupplier" class="btn btn-info" value="保存评论"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
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

       //Data Tables
      $(document).ready(function () {
        $(".btn-del").click(function(){
        	var id = $(this).siblings(".beanid").val();
        	var comment = $(this);
        	$.ajax({type : "POST",
					url : "admin/comment/deleteCommentInfo.do",
					data : {"id": id},
					success : function(data) {
						alert(data);
						$(comment).parent().parent().parent().remove();
					}
					});
        });
      }); 

    </script>

  </body>
</html>