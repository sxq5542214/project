<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.business.comment.bean.CommentConfigBean"%>
<%@page import="com.yd.business.comment.bean.CommentInfoImgBean"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	PageinationData pd = (PageinationData) request.getAttribute("pd");
	CommentInfoBean commentBean = (CommentInfoBean) request.getAttribute("bean");
	List<CommentInfoBean> list = (List<CommentInfoBean>)pd.getDataList();	
	String status = request.getParameter("status");
	String type = request.getParameter("type");
	if(!StringUtil.isNotNull(status)){
		status = (String)request.getAttribute("status");
	}
	if(!StringUtil.isNotNull(type)){
		type = (String)request.getAttribute("type");
	}
	List<CommentConfigBean> configBeanList = (List<CommentConfigBean>) request.getAttribute("configBeanList");
	List<DictionaryBean> typeList = (List<DictionaryBean>) request.getAttribute("typeList");
	List<DictionaryBean> statusList = (List<DictionaryBean>) request.getAttribute("statusList");
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
<style type="text/css">
.icon{
	display:none;
}

</style>
</head>

<body style="background-color: #f7f7f7" onload="">
	<!-- Row Start -->
	<div class="row" >
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">留言管理</div>
				</div>
				<div class="widget-body">
					<form class="form-inline" action="admin/comment/showCommentListForManage.do" id="conditionFrom" method="post">
					<label for="formGroupExampleInput">所属活动：</label>
						<div class="btn-group" style="padding-right: 40px;">
							<a class="btn btn-default" style="min-width:136px;" href="javascript:return false;">
								<i class="icon-user icon-white"></i>
								<input class="form_item_value" id="activity_code" type="hidden" name="activity_code" value="<%=commentBean.getComment_conf_code() %>" />
							<span class="form_item_name">请选择</span></a> 
							<a class="btn btn-default data-clear" style="padding: 6px 4px;" href="javascript:return false;"><span >×</span>
							</a>
							<a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="javascript:return false;"><span class="caret"></span>
							</a>
							<ul class="dropdown-menu query-item">
							<%for(CommentConfigBean configBean : configBeanList){ %>
							<li><a href="javascript:return false;"><i class="icon"><%=configBean.getActivity_code() %></i><span><%=configBean.getName() %></span></a>
								</li>
							<%} %>
							</ul>
						</div>
						<label for="formGroupExampleInput">留言类型：</label>
						<div class="btn-group" style="padding-right: 40px;">
							<a class="btn btn-default" style="min-width:136px;" href="javascript:return false;"> 
								<i class="icon-user icon-white"></i>
								<input class="form_item_value" id="type" type="hidden" name="type" value="<%=commentBean.getType() %>" />
							<span class="form_item_name">请选择</span></a> 
							<a class="btn btn-default data-clear" style="padding: 6px 4px;" href="javascript:return false;"><span >×</span>
							</a>
							<a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="javascript:return false;">
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu query-item">
							<%for(DictionaryBean typeBean : typeList){ %>
							<li><a href="javascript:return false;"><i class="icon"><%=typeBean.getValue() %></i><span><%=typeBean.getDescription()%></span></a>
								</li>
							<%} %>
							</ul>
						</div>
						<label for="formGroupExampleInput">留言状态：</label>
						<div class="btn-group" style="padding-right: 40px;">
							<a class="btn btn-default" style="min-width:136px;" href="javascript:return false;"> 
								<i class="icon-user icon-white"></i>
								<input class="form_item_value" id="status" type="hidden" name="status" value="<%=commentBean.getStatus() %>" />
							<span class="form_item_name">请选择</span></a> 
							<a class="btn btn-default data-clear" style="padding: 6px 4px;" href="javascript:return false;"><span >×</span>
							</a>
							<a class="btn btn-default dropdown-toggle" data-toggle="dropdown" href="javascript:return false;">
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu query-item">
							<%for(DictionaryBean statusBean : statusList){ %>
							<li><a href="javascript:return false;"><i class="icon"><%=statusBean.getValue() %></i><span><%=statusBean.getDescription()%></span></a>
								</li>
							<%} %>
							</ul>
						</div>
						<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<input type="hidden" name="status" id="status" value="<%=status %>" />
						<input type="hidden" name="type" id="type"  value="<%=type %>"/>
						<button type="submit" class="btn btn-info">搜索</button>
					</form>
					<div id="dt_example" class="example_alt_pagination">
						<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
							<thead>
								<tr>
									<th style="width: 3%">
										<input type="checkbox" class="no-margin"  onclick="checkAll(this);" />
									</th>
									<th style="width: 5%">类型</th>
									<th style="width: 120px">所属模块</th>
									<th style="display:none;"></th>
									<th style="width: 15%">父级留言</th>
									<th style="width: 15%">父级评论</th>
									<th style="width: 8%">评论人</th>
									<th>内容</th>
									<th style="width: 3%">点赞</th>
									<th style="width: 3%">回复</th>
									<th style="width: 5%">状态</th>
									<th style="width: 10%">时间</th>
									<th style="width: 265px" class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<% for(CommentInfoBean bean : list ){ if(bean.getType() == CommentInfoBean.COMMENT_TYPE_USER){%>
							<tr class="gradeA ">
							<%}else if(bean.getType() == CommentInfoBean.COMMENT_TYPE_ADMIN){%>
							<tr class="gradeA warning">
								<%} else{%>
								<tr class="gradeA success">
								<%} %>
									<td><input type="checkbox" name="commentinfo" class="no-margin" value="<%=bean.getId() %>" /></td>
									<td class="item-type"><%=StringUtil.convertNull(bean.getDictValueByField("type")) %></td>
									<%for(CommentConfigBean configBean : configBeanList){ 
										if(configBean.getActivity_code().equals(bean.getComment_conf_code())){%>
										<td class="item-name"><%=StringUtil.convertNull(configBean.getName()) %></td>
										<%break;}
									} %>
									<td style="display:none;" class="item-code"><%=StringUtil.convertNull(bean.getComment_conf_code()) %></td>
									<td class="item-pmsgtext">
									<%if(!StringUtil.isNull(bean.getImgBeanList())){
									for(CommentInfoImgBean img : bean.getImgBeanList()){
									%>
									<img img-data="<%=StringUtil.convertNull(img.getId()) %>" src="<%=StringUtil.convertNull(img.getThumb_url()) %>" onclick="showBigImg(this,'<%=img.getImg_url() %>',<%=img.getId() %>)" alt="..." data-toggle="modal" data-target="#imgModal"   style="width: 40px;border-radius: 18%;"/>
									<%}} %>
									<%=StringUtil.convertNull(bean.getPmsgtext()) %></td>
									<td class="item-preplymsgtext"><%=StringUtil.convertNull(bean.getPreplymsgtext()) %></td>
									<td class="item-user">
									<img src="<%=StringUtil.convertNull(bean.getHead_img()) %>" alt="..." class="img-circle" style="width: 40px;">
									<span style="padding-left: 5px;"><%=StringUtil.convertNull(bean.getNick_name()) %></span></td>
									<td class="item-msgtext"><%=StringUtil.convertNull(bean.getMsgtext()) %></td>
									<td class="item-agree"><%=StringUtil.convertNull(bean.getAgreeCount()) %></td>
									<td class="item-reply"><%=StringUtil.convertNull(bean.getReCount()) %></td>
									<td class="item-status"><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
									
									<td class="item-time"><%=StringUtil.convertNull(bean.getCreatetime()) %></td>
									<td><div class="btn-group">
									<%if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKED && bean.getType() == CommentInfoBean.COMMENT_TYPE_COMMENTINFO){%>
										<%if(!StringUtil.isNull(bean.getIs_adminre()) && bean.getIs_adminre() == CommentInfoBean.COMMENT_ISADMINRE_YES){%>
										<a class="btn btn-primary" onclick="getReplyList(this);" data-toggle="modal" data-target="#myModal"  href="javascript:return false;" style="width:40px;padding: 6px 12px;margin-left:5px;">
											<i class="fa fa-comment"></i>
										</a>
										<%}else{ %>
										<a class="btn btn-primary" onclick="getReplyList(this);" data-toggle="modal" data-target="#myModal"  href="javascript:return false;" style="width:40px;padding: 6px 12px;margin-left:5px;">
											<i class="fa fa-comment-o"></i>
										</a>
										<%} %>
									<%}else if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_NOCHECK || bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKRECOVER) {%>
										<a class="btn btn-success" href="javascript:return false;"  onclick="dealCommentInfoStatus(this,'checked',<%=bean.getId() %>,<%=commentBean.getStatus() %>,<%=commentBean.getType() %>);" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa"></i>通过
										</a>
										<a class="btn btn-warning" href="javascript:return false;" onclick="dealCommentInfoStatus(this,'checkfail',<%=bean.getId() %>,<%=commentBean.getStatus() %>,<%=commentBean.getType() %>);" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa"></i>不通过
										</a>
										<%if(bean.getType() == CommentInfoBean.COMMENT_TYPE_COMMENTINFO){%>
										<a class="btn btn-primary" href="javascript:return false;"  onclick="getReplyList(this);"  data-toggle="modal" data-target="#myModal" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa"></i>评论
										</a>
										
										<%}%>
									<%}else if(bean.getStatus() == CommentInfoBean.COMMENT_STATUS_CHECKFAIL) {%>
										<a class="btn btn-warning" href="javascript:return false;"  onclick="dealCommentInfoStatus(this,'checkrecover',<%=bean.getId() %>,<%=commentBean.getStatus() %>,<%=commentBean.getType() %>);" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa"></i>恢复
										</a>
									<%} %>
									<a class="btn btn-danger del-data" href="javascript:return false;" onclick="dealCommentInfoStatus(this,'checkdel',<%=bean.getId() %>,<%=commentBean.getStatus() %>,<%=commentBean.getType() %>);" style="padding: 6px 12px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
										</div>
									</td>
								</tr>
							<%} %>
							</tbody>
						</table>
						 <div class="dataTables_info">当前第<%=pd.getNowpage() %> 页/共 <%=pd.getTotalpage() %> 页[<%=pd.getTotalcount() %> 条数据]</div>
                      
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
					<a class="btn btn-success" href="javascript:;" onclick="checkAllCommentInfo('checked',<%=status %>,<%=type %>)">
						<i class="fa fa-check-circle-o"></i> 批量审核通过
					</a>
					<a class="btn btn-warning" href="javascript:;" onclick="checkAllCommentInfo('checkfail',<%=status %>,<%=type %>)">
						<i class="fa fa-minus-circle"></i> 批量审核不通过
					</a>
					<a class="btn btn-danger" href="javascript:;" onclick="checkAllCommentInfo('checkdel',<%=status %>,<%=type %>)">
						<i class="fa fa-minus-circle"></i> 批量删除
					</a>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;margin:10px auto;" id="myModal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">评论列表</h4>
				</div>
				<div class="modal-body">
				<div class="form-group">
						<div class="col-md-10" id="material_div" style="width:100%;">
							<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table" style="margin-top: 14px;">
								<thead>
									<tr>
										<th width="130px">所属模块</th>
										<th style="display:none;"></th>
										<th width="170px">留言人</th>
										<th>留言内容</th>
										<th width="150px">留言时间</th>
										<th width="70px">状态</th>
									</tr>
								</thead>
								<tbody id="material_list">
									<tr class="gradeA ">
										<td class="item-name" style="margin: 0 auto;text-align: center;"></td>
										<td style="display:none;" class="item-code"></td>
										<td class="item-user" style="margin: 0 auto;text-align: center;"></td>
										<td class="item-msgtext"></td>
										<td class="item-time"></td>
										<td class="item-status"></td>
									</tr>
								</tbody>
							</table>
						</div>
					<ul id="myTab" class="nav nav-tabs">
						<li class="active">
							<a id="all_title" href="#all" data-toggle="tab">所有评论</a>
						</li>
						<li>
							<a id="uncheck_title" href="#uncheck" data-toggle="tab">未审核</a>
						</li>
						<li>
							<a id="adminre_title" href="#adminre" data-toggle="tab">管理员评论</a>
						</li>
						
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="all" style="height:200px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:200px;">
							
						</div>

						</div>
						<div class="tab-pane fade" id="uncheck" style="height:200px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:200px;">
							
						</div>

						</div>
						<div class="tab-pane fade" id="adminre" style="height:200px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:200px;">
							
						</div>

						</div>
					</div>
					<div id="dt_example" style="padding: 5px 0;">
					<div id="page_table_bottom" class="dataTables_paginate paging_full_numbers">
                      </div>
					</div></div>
						
					
				</div>
				<div class="modal-footer">
				<input type="hidden" name="parentid" id="parentid" value=""> 
						<input type="hidden" name="conf_code" id= "conf_code" value=""> 
						<div class="form-group">
							<div class="col-md-10" style="width:100%;">
								<div class="row">
									<div class="col-md-10" style="width:100%;padding-bottom: 10px;">
										<textarea class="form-control" rows="3" id="msgtext" style="    background-color: antiquewhite;"
											placeholder="请输入回复内容" name="msgtext"></textarea>
									</div>
								</div>
							</div>
						</div>
					<a type="back" href="javascript:window.history.go(-1);"
						class="btn btn-warning">返回</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button id="submitComment" type="button" class="btn btn-primary">提交</button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
	<div class="modal fade bs-example-modal-lg text-center" id="imgModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" >
          <div class="modal-dialog modal-lg" style="display: inline-block; width: auto;">
            <a class="btn btn-danger del-data" href="javascript:return false;" onclick="deleteImg(this)" style="padding: 6px 12px;margin-left:5px;">
											<input type="hidden" value="" class="comment_img_id" />
											<i class="fa"></i>删除
										</a>
            <div class="modal-content">
			
             <img  id="imgInModalID" style="height:700px;" src="" >
            </div>
          </div>
        </div>
	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	<script src="page/pc/js/pc/comment.js"></script>
	
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
    
    </script>

</body>
</html>