<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	PageinationData pd = (PageinationData) request.getAttribute("pd");
	List<CommentInfoBean> list = (List<CommentInfoBean>)pd.getDataList();	
	String status = request.getParameter("status");
	String type = request.getParameter("type");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－留言信息管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7" onload="">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">消息文章管理</div>
				</div>
				<div class="widget-body">
					<a class="btn btn-success" href="javascript:;" onclick="checkAllCommentInfo('checked',<%=status %>,<%=type %>)">
						<i class="fa fa-check-circle-o"></i> 批量审核通过
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="checkAllCommentInfo('checkfail',<%=status %>,<%=type %>)">
						<i class="fa fa-minus-circle"></i> 批量审核不通过
					</a>
					
					<div id="dt_example" class="example_alt_pagination">
						<form action="admin/comment/getAllCommentInfoByBean.do" id="conditionFrom">
						<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>"  />
						<input type="hidden" name="status" id="status" value="<%=status %>" />
						<input type="hidden" name="type" id="type"  value="<%=type %>"/>
					</form>
						<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
							<thead>
								<tr>
									<th style="width: 3%">
										<input type="checkbox" class="no-margin"  onclick="checkAll(this);" />
									</th>
									<th style="width: 5%">所属模块</th>
									<th style="width: 19%">所属留言</th>
									<th style="width: 8%">评论人</th>
									<th style="width: 30%">评论内容</th>
									<th style="width: 5%">评论状态</th>
									<th style="width: 8%">评论时间</th>
									<th style="width: 27%" class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<% for(CommentInfoBean bean : list ){ %>
								<tr class="gradeB ">
									<td><input type="checkbox" name="commentinfo" class="no-margin" value="<%=bean.getId() %>" /></td>
									<td><%=StringUtil.convertNull(bean.getComment_conf_code()) %></td>
									<td><%=StringUtil.convertNull(bean.getPmsgtext()) %></td>
									<td><%=StringUtil.convertNull(bean.getNick_name()) %></td>
									<td><%=StringUtil.convertNull(bean.getMsgtext()) %></td>
									<td><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
									<td><%=StringUtil.convertNull(bean.getCreatetime()) %></td>
									<td><div class="btn-group">
									<input type="hidden" class="beanid" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
												
									<%if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKED && bean.getType() == CommentInfoBean.COMMENT_TYPE_COMMENTINFO){%>
									
										<button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a href="admin/msgcenter/article/toUpdateArticlePage.do?id=<%=bean.getId()%>">展开未审核评论</a></li>
											<li><a href="admin/msgcenter/article/toUpdateArticlePage.do?id=<%=bean.getId()%>">展开已审核评论</a></li>
											<li><a href="admin/msgcenter/article/toArticleAssignSendTimePage.do?id=<%=bean.getId()%>">展开所有评论</a></li>
											<li><a href="admin/msgcenter/article/toArticleAssignSendTimePage.do?id=<%=bean.getId()%>">展开审核不通过评论</a></li>
										</ul>
										
									<%}else if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_NOCHECK) {%>
										<a class="btn btn-success" href="javascript:return false;"  onclick="javascript:window.location.href='admin/comment/dealCommentInfoStatus.do?id=<%=bean.getId() %>&action=checked&status=0&type=2'" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa fa-check-circle-o"></i>通过
										</a>
										<a class="btn btn-danger" href="javascript:return false;" onclick="javascript:window.location.href='admin/comment/dealCommentInfoStatus.do?id=<%=bean.getId() %>&action=checkfail&status=0&type=2'" style="padding: 6px 12px;margin:0px 5px;">
											<i class="fa fa-minus-circle"></i>不通过
										</a>
									<%} %>
									<button data-toggle="button" class="btn btn-danger btn-del" >删除</button>
										</div>
									</td>
								</tr>
							<%} %>
							</tbody>
						</table>
						<div class="dataTables_info">当前第<%=StringUtil.convertNull(pd.getNowpage()) %> 页/共 <%=StringUtil.convertNull(pd.getTotalpage()) %> 页[<%=StringUtil.convertNull(pd.getTotalcount()) %> 条数据]</div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      	<a tabindex="0" class="paginate_button" onclick="toPage(1)" >首页</a>
                      	<%if(pd.getNowpage()>1){%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>
                      		<%for(int i = 1 ; i <= pd.getTotalpage(); i ++) {
                      			String classStr = "paginate_button";
                      			if(pd.getNowpage() == i)
                      				classStr = "paginate_active";
                      			if(i < pd.getNowpage()+5 && i > pd.getNowpage()-5){%>
                      				<a tabindex="0" class="<%=classStr %>" onclick="toPage(<%=i %>)" ><%=i %></a>
                      		<%}} %>
						<%if(pd.getNowpage()<pd.getTotalpage()){%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>                      	
                      	<a tabindex="0" class="paginate_button" onclick="toPage(<%=pd.getTotalpage() %>)" >尾页</a>
                      	</span>
                      </div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/pc/comment.js"></script>

	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	
	<script src="js/Util.js"></script>
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

        $('#data-table').dataTable({
        "bPaginate": false,
        "bInfo": false,
        "bSort":false
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