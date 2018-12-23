<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.comment.bean.CommentConfigBean"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	List<WechatOriginalInfoBean> originalList = (List<WechatOriginalInfoBean>)request.getAttribute("originalList");
	CommentConfigBean bean = (CommentConfigBean)request.getAttribute("bean");
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	List<CommentConfigBean> list = pd.getDataList();
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－微信素材分发管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="page/pc/css/bootstrap-datetimepicker.css" rel="stylesheet">
<link href="page/pc/css/bootstrap-datetimepicker.min.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->
<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
<link href="page/pc/css/preview_base.css" rel="stylesheet">
<link href="KindEditor/themes/default/default.css" rel="stylesheet" type="text/css">
<style type="text/css">
.col-md-1, .col-md-10, .col-md-11, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11,  .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11,  .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
    padding-right: 0px;
}
.input-group-addon {
    padding: 6px 6px;
}
.input-group[class*=col-] {
    float: left;
</style>
</head>

<body style="background-color: #f7f7f7;" >
<div class="row" >
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						留言配置管理
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form id="conditionFrom" class="form-horizontal row-border" action="admin/comment/getCommentConfigList.do" method="post">
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
									<div class="col-xs-1">
										<label class="control-label">名称：</label>
									</div>
									<div class="col-xs-3">
										<input class="form-control config_comment_query" placeholder="请输入名称模糊查询" name="name" value="<%=StringUtil.convertNull(bean.getName())%>" />
									</div>
									<div class="col-xs-1">
										<label class="control-label">编码：</label>
									</div>
									<div class="col-xs-3">
										<input type="text" name="activity_code" placeholder="输入编码模糊查询" class="form-control config_comment_query" value="">
									</div>
									<div class="col-xs-1">
										<label class="control-label">是否展示：</label>
									</div>
									<div class="col-xs-3">
         								<select class="form-control config_comment_query" name="is_show"> 
         								<option value="">全部</option>
         								<%for(DictionaryBean dic : dicMap.get("is_show") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
									<div class="col-xs-1">
										<label class="control-label">重复留言：</label>
									</div>
									<div class="col-xs-3">
         								<select class="form-control config_comment_query" name="is_repeat"> 
         								<option value="">全部</option>
         								<%for(DictionaryBean dic : dicMap.get("is_repeat") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
									</div>
									<div class="col-xs-1">
										<label class="control-label">需要审核：</label>
									</div>
									<div class="col-xs-3">
          								<select class="form-control config_comment_query" name="is_admin_review"> 
         								<option value="">全部</option>
          								<%for(DictionaryBean dic : dicMap.get("is_admin_review") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
									</div>
									<div class="col-xs-1">
										<label class="control-label">投票期限：</label>
									</div>
									<div class="col-xs-3">
          								<select class="form-control config_comment_query" name="is_voted_expired"> 
         								<option value="">全部</option>
          								<%for(DictionaryBean dic : dicMap.get("is_voted") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %> </select> 
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
									<div class="col-xs-1">
										<label class="control-label">留言关注：</label>
									</div>
									<div class="col-xs-3">
          								<select class="form-control config_comment_query" name="is_user_subscribe"> 
         								<option value="">全部</option>
          								<%for(DictionaryBean dic : dicMap.get("is_user_subscribe") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %> </select> 
									</div>
									<div class="col-xs-1">
										<label class="control-label">投票关注：</label>
									</div>
									<div class="col-xs-3">
          								<select class="form-control config_comment_query" name="vote_is_user_subscribe"> 
         								<option value="">全部</option>
          								<%for(DictionaryBean dic : dicMap.get("is_user_subscribe") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %> </select> 
									</div>
									<div class="col-xs-1">
										<label class="control-label">留言时效：</label>
									</div>
									<div class="col-xs-3">
										<div>
											<label class="checkbox-inline"> <input class="config_comment_query" type="checkbox" name="expir"  value="no_expired">未过期</label> 
											<label class="checkbox-inline"> <input class="config_comment_query" type="checkbox" name="expir" value="expired">已过期</label> 
											<label class="checkbox-inline"> <input class="config_comment_query" type="checkbox" name="expir" value="expiring">进行中</label> 
											<label class="checkbox-inline"> <input class="config_comment_query" type="checkbox" name="expir" value="becoming_expired">即将过期</label> 
										</div>

									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-12">
								<a style="margin-left: 10px;" type="add" class="btn btn-success pull-left" data-toggle="modal" data-target="#myModal" onclick="getDefaultData();">新增</a> 
								<a style="margin-left: 10px;" type="cancle" class="btn btn-warning pull-right" onclick="cleanBox();">清空</a>
								<input type="submit" id="submitSupplier" class="btn btn-info pull-right" value="搜索" /> 
								<input type="hidden" name="nowpage" id="nowpage" value="">
							</div>
						</div>
					</form>
					<div id="dt_example" class="example_alt_pagination">
						<table id="accordion" class="table table-condensed table-striped table-hover table-bordered pull-left" style="word-break:break-all; word-wrap:break-all;">
							<thead id="supplier_list_head">
								<tr>
									<th style="width:20px ">
										<input type="checkbox" class="no-margin" onclick="checkAll(this);" />
									</th>
									<th style="width:40px">展开</th>
									<th style="width: 150px;">名称</th>
									<th style="width: 150px;">编码</th>
									<th style="width: 155px;">生效时间</th>
									<th style="width: 155px;">失效时间</th>
									<th style="width: 60px;">投票周期</th>
									<th style="width: 60px;">投票次数</th>
									<th style="width: 90px;">留言关注</th>
									<th style="width: 90px;">是否显示</th>
									<th style="width: 60px;">投票关注</th>
									<th style="width: 60px;">需要审核</th>			
									<th style="width: 105px;">操作</th>						
								</tr>
							</thead>
							<tbody id="supplier_list">
								<tr class="gradeA" style="display:none;">
									<td class="config_comment_table_id">
										<input type="checkbox" class="config_comment_table_id" value="" />
										<input type="hidden" class="config_comment_table_is_repeat" value="" />
										<input type="hidden" class="config_comment_table_voted_cycle" value="" />
										<input type="hidden" class="config_comment_table_is_user_subscribe" value="" />
										<input type="hidden" class="config_comment_table_is_show" value="" />
										<input type="hidden" class="config_comment_table_vote_is_user_subscribe" value="" />
										<input type="hidden" class="config_comment_table_is_admin_review" value="" />
										<input type="hidden" class="config_comment_table_is_voted_expired" value="" />
									</td>
									<td>
										<a class="detail-icon" data-toggle="collapse"  data-target="#coll">
											<i class="glyphicon glyphicon-plus icon-plus"></i>
										</a>
									</td>
									<td class="config_comment_table_name"></td>
									<td class="config_comment_table_activity_code"></td>
									<td class="config_comment_table_start_time"></td>
									<td class="config_comment_table_end_time"></td>
									<td class="config_comment_table_is_repeat_value" style="display:none;"></td>
									<td class="config_comment_table_page_size" style="display:none;"></td>
									<td class="config_comment_table_voted_cycle_value"></td>
									<td class="config_comment_table_per_voted_num"></td>
									<td class="config_comment_table_is_user_subscribe_value"></td>
									<td class="config_comment_table_is_show_value"></td>
									<td class="config_comment_table_create_time" style="display:none;"></td>
									<td class="config_comment_table_modify_time" style="display:none;"></td>
									<td class="config_comment_table_vote_is_user_subscribe_value"></td>
									<td class="config_comment_table_is_admin_review_value"></td>
									<td class="config_comment_table_is_voted_expired_value" style="display:none;"></td>
									<td class="config_comment_table_link_url" style="display:none;"></td>
									<td class="config_comment_table_orderby" style="display:none;"></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<tr class="collapse out" id="">
									
								</tr>
								<%for(CommentConfigBean configBean:list){ %>
								<tr class="gradeA">
									<td class="config_comment_table_id">
										<input type="checkbox" class="config_comment_table_id" value="<%=StringUtil.convertNull(configBean.getId())%>" />
										<input type="hidden" class="config_comment_table_is_repeat" value="<%=StringUtil.convertNull(configBean.getIs_repeat())%>" />
										<input type="hidden" class="config_comment_table_voted_cycle" value="<%=StringUtil.convertNull(configBean.getVoted_cycle())%>" />
										<input type="hidden" class="config_comment_table_is_user_subscribe" value="<%=StringUtil.convertNull(configBean.getIs_user_subscribe())%>" />
										<input type="hidden" class="config_comment_table_is_show" value="<%=StringUtil.convertNull(configBean.getIs_show())%>" />
										<input type="hidden" class="config_comment_table_vote_is_user_subscribe" value="<%=StringUtil.convertNull(configBean.getVote_is_user_subscribe())%>" />
										<input type="hidden" class="config_comment_table_is_admin_review" value="<%=StringUtil.convertNull(configBean.getIs_admin_review())%>" />
										<input type="hidden" class="config_comment_table_is_voted_expired" value="<%=StringUtil.convertNull(configBean.getIs_voted_expired())%>" />
									</td>
									<td>
										<a class="detail-icon" data-toggle="collapse"  data-target="#coll<%=StringUtil.convertNull(configBean.getId() )%>">
											<i class="glyphicon glyphicon-plus icon-plus"></i>
										</a>
									</td>
									<td class="config_comment_table_name"><%=StringUtil.convertNull(configBean.getName() )%></td>
									<td class="config_comment_table_activity_code"><%=StringUtil.convertNull(configBean.getActivity_code() )%></td>
									<td class="config_comment_table_start_time"><%=StringUtil.convertNull(configBean.getStart_time() )%></td>
									<td class="config_comment_table_end_time"><%=StringUtil.convertNull(configBean.getEnd_time() )%></td>
									<td class="config_comment_table_is_repeat_value" style="display:none;"><%=StringUtil.convertNull(configBean.getDictValueByField("is_repeat") )%></td>
									<td class="config_comment_table_pageSize" style="display:none;"><%=StringUtil.convertNull(configBean.getPageSize() )%></td>
									<td class="config_comment_table_voted_cycle_value"><%=StringUtil.convertNull(configBean.getDictValueByField("voted_cycle") )%></td>
									<td class="config_comment_table_per_voted_num"><%=StringUtil.convertNull(configBean.getPer_voted_num() )%></td>
									<td class="config_comment_table_is_user_subscribe_value"><%=StringUtil.convertNull(configBean.getDictValueByField("is_user_subscribe") )%></td>
									<td class="config_comment_table_is_show_value"><%=StringUtil.convertNull(configBean.getDictValueByField("is_show") )%></td>
									<td class="config_comment_table_create_time" style="display:none;"><%=StringUtil.convertNull(configBean.getCreate_time() )%></td>
									<td class="config_comment_table_modify_time" style="display:none;"><%=StringUtil.convertNull(configBean.getModify_time() )%></td>
									<td class="config_comment_table_vote_is_user_subscribe_value"><%=StringUtil.convertNull(configBean.getDictValueByField("vote_is_user_subscribe") )%></td>
									<td class="config_comment_table_is_admin_review_value"><%=StringUtil.convertNull(configBean.getDictValueByField("is_admin_review") )%></td>
									<td class="config_comment_table_is_voted_expired_value" style="display:none;"><%=StringUtil.convertNull(configBean.getDictValueByField("is_voted_expired") )%></td>
									<td class="config_comment_table_link_url" style="display:none;"><%=StringUtil.convertNull(configBean.getLink_url() )%></td>
									<td class="config_comment_table_orderby" style="display:none;"><%=StringUtil.convertNull(configBean.getOrderby() )%></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editConfigComment(this);" >
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<tr class="collapse out" id="coll<%=StringUtil.convertNull(configBean.getId() )%>">
									<td colspan="2"></td>
									<td colspan="11">
										<p>
											<b>每页显示:</b> <a><%=StringUtil.convertNull(configBean.getPageSize())%></a>
										</p>
										<p>
											<b>跳转链接:</b> <a><%=StringUtil.convertNull(configBean.getLink_url() )%></a>
										</p>
										<p>
											<b>重复留言:</b> <a><%=StringUtil.convertNull(configBean.getDictValueByField("is_repeat") )%></a>
										</p>
										<p>
											<b>投票期限:</b> <a><%=StringUtil.convertNull(configBean.getDictValueByField("is_voted_expired") )%></a>
										</p>
										<p>
											<b>排序规则:</b> <a><%=StringUtil.convertNull(configBean.getOrderby() )%></a>
										</p>
										<p>
											<b>创建时间:</b> <a><%=StringUtil.convertNull(configBean.getCreate_time() )%></a>
										</p>
										<p>
											<b>修改时间:</b> <a><%=StringUtil.convertNull(configBean.getModify_time() )%></a>
										</p>
									</td>
								</tr>
								<%} %>
							</tbody>
						</table>
						<div class="dataTables_info">当前第<%=pd.getNowpage()%> 页/共 <%=pd.getTotalpage()%> 页[<%=pd.getTotalcount()%> 条数据]</div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      	<a tabindex="0" class="paginate_button" onclick="toPage(1)" >首页</a>
                      	<%
                      		if (pd.getNowpage() > 1) {
                      	%>
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
					<button id='delAll' class="btn btn-danger" data-toggle="popover" data-placement="top" onclick="deleteAllitem();">
						<i class="fa fa-times-circle"></i> 批量删除
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"> 
   <div class="modal-dialog" style="width:90%"> 
    <div class="modal-content"> 
     <div class="modal-header"> 
      <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button> 
      <h4 class="modal-title" id="myModalLabel">编辑配置信息</h4> 
     </div> 
     <div class="modal-body" style="padding-right: 10%;"> 
      <form class="form-horizontal" role="form" id="config_comment_form"> 
       <input class="form-control config_comment" type="hidden" name="config_comment_id" value="" /> 
       <div class="form-group"> 
       	<div class="form-group1">
       		<label class="col-sm-1 control-label">名称：</label> 
        	<div class="col-sm-3"> 
         		<input class="form-control config_comment" type="text" name="config_comment_name" placeholder="请输入名称..." /> 
        	</div>
       	</div>
        <div class="form-group1">
        <label class="col-sm-1 control-label">编码：</label> 
        <div class="col-sm-3"> 
         <input class="form-control config_comment" type="text" name="config_comment_activity_code" placeholder="请输入编码..." /> 
        </div>
        </div>
        <div class="form-group1">
        <label for="disabledSelect" class="col-sm-1 control-label">是否展示：</label> 
        <div class="col-sm-3"> 
         <select class="form-control config_comment" name="config_comment_is_show"> <%for(DictionaryBean dic : dicMap.get("is_show") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
        </div> 
        </div>
       </div> 
       <div class="form-group"> 
       <div class="form-group1">
        <label for="" class="col-sm-1 control-label">每页展示：</label> 
        <div class="col-sm-3"> 
         <div class="btn-group"> 
          <a class="btn btn-default btn-add" style="width: 20%;" href="javascript:return false;" onclick="minusNum(this);"><span>-</span></a> 
          <input style="width: 60%;" type="text" name="config_comment_pageSize" placeholder="请输入数量..." class="btn btn-default config_comment" value="0" /> 
          <a class="btn btn-default btn-minus" style="width: 20%;" href="javascript:return false;" onclick="addNum(this);"><span>+</span></a> 
         </div> 
        </div> 
        </div>
        <div class="form-group1">
        <label for="disabledSelect" class="col-sm-1 control-label">重复留言：</label> 
        <div class="col-sm-3"> 
         <select class="form-control config_comment" name="config_comment_is_repeat"> <%for(DictionaryBean dic : dicMap.get("is_repeat") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
        </div> 
        </div>
        <div class="form-group1"> 
         <label for="disabledSelect" class="col-sm-1 control-label">需要审核：</label> 
         <div class="col-sm-3"> 
          <select class="form-control config_comment" name="config_comment_is_admin_review"> <%for(DictionaryBean dic : dicMap.get("is_admin_review") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
         </div> 
        </div> 
       </div> 
       <div class="form-group"> 
        <div class="form-group1"> 
         <label for="disabledSelect" class="col-sm-1 control-label">点赞周期：</label> 
         <div class="col-sm-3"> 
          <select class="form-control config_comment" name="config_comment_voted_cycle"> <%for(DictionaryBean dic : dicMap.get("voted_cycle") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
         </div> 
        </div> 
        <div class="form-group1"> 
         <label for="inputPassword" class="col-sm-1 control-label">点赞次数：</label> 
         <div class="col-sm-3"> 
          <div class="btn-group"> 
           <a class="btn btn-default btn-add" style="width: 20%;" href="javascript:return false;"onclick="minusNum(this);"><span>-</span></a> 
           <input style="width: 60%;" type="text" name="config_comment_per_voted_num" placeholder="请输入数量..." class="btn btn-default config_comment" value="0" /> 
           <a class="btn btn-default btn-minus" style="width: 20%;" href="javascript:return false;" onclick="addNum(this);"><span>+</span></a> 
          </div> 
         </div> 
        </div> 
        <div class="form-group1"> 
         <label for="disabledSelect" class="col-sm-1 control-label">点赞期限：</label> 
         <div class="col-sm-3"> 
          <select class="form-control config_comment" name="config_comment_is_voted_expired"> <%for(DictionaryBean dic : dicMap.get("is_voted") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %> </select> 
         </div> 
        </div> 
       </div> 
       <div class="form-group"> 
        <div class="form-group1"> 
         <label for="disabledSelect" class="col-sm-1 control-label">留言关注：</label> 
         <div class="col-sm-3"> 
          <select class="form-control config_comment" name="config_comment_is_user_subscribe"> <%for(DictionaryBean dic : dicMap.get("is_user_subscribe") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %> </select> 
         </div> 
        </div> 
        <div class="form-group1"> 
         <label for="disabledSelect" class="col-sm-1 control-label">点赞关注：</label> 
         <div class="col-sm-3"> 
          <select class="form-control config_comment" name="config_comment_vote_is_user_subscribe"><%for(DictionaryBean dic : dicMap.get("is_user_subscribe") ){ %><option value="<%=dic.getValue() %>"><%=dic.getDescription() %></option> <%} %></select> 
         </div> 
        </div> 

       </div> 
       <div class="form-group form-group1"> 
        <label class="col-sm-1 control-label">创建时间：</label> 
        <div class="col-sm-3"> 
         <input class="form-control " type="text" name="config_comment_create_time" placeholder="<%=DateUtil.getNowDateStr() %>" disabled="" /> 
        </div> 
        <label for="inputPassword" class="col-sm-1 control-label">修改时间：</label> 
        <div class="col-sm-3"> 
         <input class="form-control " type="text" name="config_comment_modify_time" placeholder="" disabled="" /> 
        </div> 
       </div> 
       <div class="form-group"> 
        <div class="form-group1"> 
         <label for="dtp_input1" class="col-sm-1 control-label">生效时间：</label> 
         <div style="padding-left:15px;" class="input-group date form_datetime col-sm-3" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="dtp_input1"> 
          <input class="form-control config_comment" size="16" name="config_comment_start_time" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input1" value="" /> 
        </div> 
        <div class="form-group1"> 
         <label for="dtp_input2" class="col-sm-1 control-label">失效时间：</label> 
         <div style="padding-left:15px;" class="input-group date form_datetime col-sm-3" data-date="" data-date-format="yyyy-mm-dd hh:ii:ss" data-link-field="dtp_input2"> 
          <input class="form-control config_comment" size="16" name="config_comment_end_time" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input2" value="" /> 
        </div> 
       </div> 
       <div class="form-group form-group1"> 
        <label for="inputPassword" class="col-sm-1 control-label">跳转链接：</label> 
        <div class="col-sm-11"> 
         <input class="form-control config_comment" type="text" name="config_comment_link_url" placeholder="请输入跳转链接..." /> 
        </div> 
       </div> 
       <div class="form-group form-group1"> 
        <label for="inputPassword" class="col-sm-1 control-label">排序参数：</label> 
        <div class="col-sm-11"> 
         <input class="form-control config_comment" type="text" name="config_comment_orderby" placeholder="请输入排序参数..." /> 
        </div> 
       </div> 
      </form> 
     </div> 
     <div class="modal-footer"> 
      <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button> 
      <button type="button" class="btn btn-primary" onclick="commitData(this);">提交</button> 
     </div> 
    </div>
    <!-- /.modal-content --> 
   </div>
   <!-- /.modal-dialog --> 
  </div>
	<script src="page/pc/js/jquery.js"></script>
	
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-datetimepicker.js"></script>
	<script src="KindEditor/kindeditor-min.js"></script>
	<script src="KindEditor/lang/zh_CN.js"></script>
	<script src="page/pc/comment/js/config_comment.js"></script>
	

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
        $('.form_datetime').datetimepicker({
        //language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		forceParse: 0,
        showMeridian: 1
    });
	$('.form_date').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
	$('.form_time').datetimepicker({
        language:  'fr',
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 1,
		minView: 0,
		maxView: 1,
		forceParse: 0
    });
    </script>

</body>
</html>