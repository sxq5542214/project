<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.other.bean.ConfigCruxBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	List<WechatOriginalInfoBean> originalList = (List<WechatOriginalInfoBean>)request.getAttribute("originalList");
	ActivityConfigBean bean = (ActivityConfigBean)request.getAttribute("bean");
	String img_server_url = request.getAttribute("img_server_url").toString();
	//素材列表
	List<ActivityConfigBean> list = (List<ActivityConfigBean>)request.getAttribute("confList");
	List<ActivityPrize> prizeList = (List<ActivityPrize>)request.getAttribute("prizeList");
		List<ConfigCruxBean>  cruxList = (List<ConfigCruxBean>)request.getAttribute("cruxList");
		List<ConfigCruxBean>  cruxImgList = (List<ConfigCruxBean>)request.getAttribute("cruxImgList");
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	Map<String, List<DictionaryBean>>  paramDicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("paramDicMap");
	Map<String, List<DictionaryBean>>  prizeDicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("prizeDicMap");
	
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
<link href="page/pc/css/bootstrap-fileinput.min.css" rel="stylesheet">

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
						活动配置管理
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form id="conditionFrom" class="form-horizontal row-border" action="admin/activity/getCommentConfigList.do" method="post">
						<div class="form-group">
							<div class="col-sm-12">
								<a style="margin-left: 10px;" type="add" class="btn btn-success pull-left"  onclick="editActivityConfig(null);">新增</a> 
								<input type="hidden" name="nowpage" id="nowpage" value="">
								<input id="img-server-url" type="hidden"  value="<%=img_server_url %>">
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
									<th style="width: 80px;">活动类型</th>
									<th style="width: 80px;">开始日期</th>
									<th style="width: 80px;">结束日期</th>
									<th style="width: 60px;">开始小时</th>
									<th style="width: 50px;">状态</th>
									<th style="width: 50px;">重复参与</th>
									<th style="width: 50px;">周期</th>
									<th style="width: 120px;">创建时间</th>
									<th style="width: 105px;">操作</th>						
								</tr>
							</thead>
							<tbody id="supplier_list">
								<tr class="gradeA" style="display:none;">
									<td class="config_activity_table_id">
										<input type="checkbox" class="config_activity_table_id" value="" />
										<input type="hidden" class="config_activity_table_is_repeat" value="" />
									</td>
									<td>
										<a class="detail-icon" data-toggle="collapse"  data-target="#coll">
											<i class="glyphicon glyphicon-plus icon-plus"></i>
										</a>
									</td>
									<td class="config_activity_table_name"></td>
									<td class="config_activity_table_activity_code"></td>
									<td class="config_activity_table_start_time"></td>
									<td class="config_activity_table_end_time"></td>
									<td class="config_activity_table_is_repeat_value" style="display:none;"></td>
									<td class="config_activity_table_page_size" style="display:none;"></td>
									<td class="config_activity_table_voted_cycle_value"></td>
									<td class="config_activity_table_per_voted_num"></td>
									<td class="config_activity_table_is_user_subscribe_value"></td>
									<td class="config_activity_table_is_show_value"></td>
									<td class="config_activity_table_create_time" style="display:none;"></td>
									<td class="config_activity_table_modify_time" style="display:none;"></td>
									<td class="config_activity_table_vote_is_user_subscribe_value"></td>
									<td class="config_activity_table_is_admin_review_value"></td>
									<td class="config_activity_table_is_voted_expired_value" style="display:none;"></td>
									<td class="config_activity_table_link_url" style="display:none;"></td>
									<td class="config_activity_table_orderby" style="display:none;"></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<tr class="collapse out" id="coll">
									<td>11111</td>
								</tr>
								<%for(ActivityConfigBean configBean:list){ %>
								<tr class="gradeA">
									<td class="config_activity_table_id">
										<input type="checkbox" class="config_activity_table_id" value="<%=StringUtil.convertNull(configBean.getId())%>" />
										<input type="hidden" class="config_activity_table_is_repeat" value="<%=StringUtil.convertNull(configBean.getIs_repeat())%>" />
									</td>
									<td>
										<a class="detail-icon" data-toggle="collapse"  data-target="#coll<%=StringUtil.convertNull(configBean.getId() )%>">
											<i class="glyphicon glyphicon-plus icon-plus"></i>
										</a>
									</td>
									<td class="config_activity_table_name"><%=StringUtil.convertNull(configBean.getName() )%></td>
									<td class="config_activity_table_code"><%=StringUtil.convertNull(configBean.getCode() )%></td>
									<td class="config_activity_table_activity_type_value"><%=StringUtil.convertNull(configBean.getDictValueByField("activity_type") )%></td>
									<td class="config_activity_table_start_date"><%=StringUtil.convertNull(configBean.getStart_date() )%></td>
									<td class="config_activity_table_end_date"><%=StringUtil.convertNull(configBean.getEnd_date() )%></td>
									<td class="config_activity_table_start_hour"><%=StringUtil.convertNull(configBean.getPageSize() )%></td>
									<td class="config_activity_table_status_value"><%=StringUtil.convertNull(configBean.getDictValueByField("status") )%></td>
									<td class="config_activity_table_is_repeat_value"><%=StringUtil.convertNull(configBean.getDictValueByField("is_repeat") )%></td>
									<td class="config_activity_table_frequency_value"><%=StringUtil.convertNull(configBean.getDictValueByField("frequency") )%></td>
									<td class="config_activity_table_create_time"><%=StringUtil.convertNull(configBean.getCreate_time() )%></td>
									<td>
										<a class="btn btn-success " style="padding: 0px 4px;margin-left:5px;" onclick="editActivityConfig(<%=StringUtil.convertNull(configBean.getId() )%>);" >
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<tr class="collapse out" id="coll<%=StringUtil.convertNull(configBean.getId() )%>">
									<td colspan="2"></td>
									<td colspan="13">
										<p>
											<b>每页显示:</b> <a><%=StringUtil.convertNull(configBean.getPageSize())%></a>
										</p>
										<p>
											<b>实际最大参与数:</b> <a><%=StringUtil.convertNull(configBean.getMax_join_num() )%></a>
										</p>
										<p>
											<b>实际最大中奖数:</b> <a><%=StringUtil.convertNull(configBean.getMax_win_num() )%></a>
										</p>
										<p>
											<b>消耗积分:</b> <a><%=StringUtil.convertNull(configBean.getCost_points() )%></a>
										</p>
										<p>
											<b>跳转地址:</b> <a><%=StringUtil.convertNull(configBean.getActivity_jump_url() )%></a>
										</p>
										<p>
											<b>活动页面图片:</b><img src="<%=StringUtil.convertNull(configBean.getActivity_img() )%>" alt="..." class="img-circle" style="width: 40px;"><a><%=StringUtil.convertNull(configBean.getActivity_img() )%></a>
										</p>
										<p>
											<b>活动页面图片跳转:</b> <a><%=StringUtil.convertNull(configBean.getActivity_img_jump_url() )%></a>
										</p>
										<p>
											<b>活动描述:</b> <a><%=StringUtil.convertNull(configBean.getDescription() )%></a>
										</p>
									</td>
								</tr>
								<%} %>
							</tbody>
						</table>
						<div class="dataTables_info"></div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      	<a tabindex="0" class="paginate_button" onclick="toPage(1)" >首页</a>
                                        	
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
	<!-- 模态框（Modal） -->
<div class="modal fade" id="myModal-editConf" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:1200px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">活动配置详细信息</h4>
            </div>
            <div class="modal-body">
			<div class="row">
        <div class="col-sm-3">
		<div class="widget">
		<div class="widget-header">
			<div class="title">信息列表</div>
		</div>
		<div class="widget-body">
          <div id="treeview-expandible" class="" style="overflow: auto;"></div>
        </div>
		</div>
		</div>
        <div class="col-sm-9" style="padding-right:15px;">
		<div class="widget">
		<div class="widget-header">
			<div class="title" id="info-title">详细信息</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class=" col-md-12 column">
			<div class="activity_config_infos">
			<div class="activity_config_info_baseinfo">
			<form role="form" id="form_config_activity" class="form-horizontal">
			<input type="hidden" class="form-control form-control-commit" id="form_activity_id" name="id" />
				<div class="form-group">
				<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</label>
					</div>
					
					<div class=" col-md-3">
					<input type="text" class="form-control form-control-commit item-property" id="form_activity_name" name="name" />
					</div>
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">编&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码</label>
					 </div>
					 <div class=" col-md-3">
					 <input type="text" class="form-control form-control-commit item-property" id="form_activity_code" name="code" />
					</div>
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</label>
					 </div>
					 <div class=" col-md-3">
					 <select class="form-control form-control-commit item-property"  id="form_activity_status"name="status">
					 <%for(DictionaryBean dic : dicMap.get("status") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>	
					</div>
					</div>
				</div>
				<div class="form-group">
				<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">生效时间</label>
					</div>
					<div class=" col-md-3">
					 <div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"> 
          <input class="form-control form-control-commit item-property" size="16" name="start_date" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input1" value="" /> 
					</div>
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">失效时间</label>
					 </div>
					 <div class=" col-md-3">
<div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"> 
          <input class="form-control form-control-commit item-property" size="16" name="end_date" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input1" value="" /> 					
         </div>
         </div>
         <div class="form-group1">
         <div class=" col-md-1">
					 <label class="control-label">消耗积分</label>
					 </div>
					 <div class=" col-md-3">
					 <input type="text" class="form-control form-control-commit item-property" id="form_activity_cost_points" name="cost_points" />
					</div>
					</div>
         </div>	
				<div class="form-group">
				<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">活动类型</label>
					</div>
					<div class=" col-md-2">
					<select class="form-control form-control-commit item-property"  id="form_activity_activity_type" name="activity_type">
					 <%for(DictionaryBean dic : dicMap.get("activity_type") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>					
					 </div>
					 </div>
					 <div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">展示详情</label>
					 </div>
					 <div class=" col-md-2">
					 <select class="form-control form-control-commit item-property"  id="form_activity_is_show_detail"name="is_show_detail">
					 <%for(DictionaryBean dic : dicMap.get("is_show_detail") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>	
					</div>
					</div><div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">重复参加</label>
					 </div>
					 <div class=" col-md-2">
					 <select class="form-control form-control-commit item-property"  id="form_activity_is_repeat"name="is_repeat">
					 <%for(DictionaryBean dic : dicMap.get("is_repeat") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>	
					</div>
					</div><div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">生命周期</label>
					 </div>
					 <div class=" col-md-2">
					 <input type="text" class="form-control form-control-commit item-property" id="form_activity_life_age" name="life_age" />
					</div>
					</div>
				</div>
         				<div class="form-group">
         				<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">开始小时</label>
					</div>
					<div class=" col-md-2">
					<input type="text" class="form-control form-control-commit item-property" id="form_activity_start_hour" name="start_hour" title="" placeholder="1,2,3,4...."  />
					</div>
					</div>
					<div class=" col-md-1">
					<label class="control-label">开始分钟</label>
					</div>
					<div class=" col-md-2">
					<input type="text" class="form-control form-control-commit" id="form_activity_start_minute" name="start_minute" title="" placeholder="1,2,3,4...."  />
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">频&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;率</label>
					 </div>
					 <div class=" col-md-2">
					 <select class="form-control form-control-commit item-property"  id="form_activity_frequency" name="frequency">
					 <%for(DictionaryBean dic : dicMap.get("frequency") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>	
					</div>
					</div><div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">开始时间</label>
					</div>
					<div class=" col-md-2">
					<input type="text" class="form-control form-control-commit item-property" id="form_activity_start_times" name="start_times" title="频率是周，就代表着星期；月的话就是几号" placeholder="（1,2,3,4....）"  />
					</div>
					</div>
				</div>
				<div class="form-group">
				<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">最大参与</label>
					</div>
					<div class=" col-md-2">
					 <input type="text" class="form-control form-control-commit item-property" id="form_activity_max_join_num" name="max_join_num" title="实际最大参加人数" />
					</div>
					</div><div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">最大中奖</label>
					</div>
					<div class=" col-md-2">
           				<input type="text" name="max_win_num" placeholder="请输入..." class="form-control form-control-commit item-property" value="" title="实际最大中奖人数" /> 
					</div>
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					 <label class="control-label">参加人数</label>
					 </div>
					 
					 <div class=" col-md-2">
           				<input type="text" name="outside_join_num" placeholder="请输入..." class="form-control form-control-commit item-property" value="" title="对外宣称可参加人数" /> 
					</div>
					</div>
					<div class=" col-md-1">
					<label class="control-label">总金额</label>
					</div>
					<div class=" col-md-2">
           				<input type="text" name="outside_total_money" placeholder="请输入..." class="form-control form-control-commit form-control-money" value="" title="对外宣称总金额" /> 
					</div>
				</div>				
				<div class="form-group">
					<div class=" col-md-1">
					<label class="control-label">最大金额</label>
					</div>
					<div class=" col-md-2">
           				<input type="text" name="single_max" placeholder="请输入..." class="form-control form-control-commit form-control-money" value="" title="单个最大金额,如果是优惠卷活动此字段无用" /> 
					</div>
					<div class=" col-md-1">
					<label class="control-label">最小金额</label>
					</div>
					<div class=" col-md-2">
           				<input type="text" name="single_min" placeholder="请输入..." class="form-control form-control-commit form-control-money" value="" title="单个最小金额,如果是优惠卷活动此字段无用" /> 
					</div>
					<div class="form-group1">
					<div class=" col-md-1">
					<label class="control-label">中奖人数</label>
					</div>
					<div class=" col-md-2">
           				<input type="text" name="outside_win_num" placeholder="请输入..." class="form-control form-control-commit item-property" value="" title="对外宣称可中奖人数" /> 
					</div>
					</div>
					<div class=" col-md-1">
					 <label class="control-label">总金额</label>
					 </div>
					 <div class=" col-md-2">
           				<input type="text" name="total_money" placeholder="请输入..." class="form-control form-control-commit form-control-money" value=""  title="活动总金额,如果是优惠卷活动此字段无用"/> 
					</div>
				</div>				
         		<div class="form-group">
         		<div class=" col-md-1">
					<label class="control-label">图片链接</label>
					</div>
				<div class=" col-md-3">
				<input data-toggle="popover"  type="text" class="btn btn-default form-control form-control-commit" name="activity_img" id="activity_img_url" />
				<input id="upload-img" type="file" class="file  " name="postFile" title="活动页面图片链接"/>
				</div>
				<div class=" col-md-1">
					<label class="control-label">详情图片</label>
					</div>
				<div class=" col-md-3">
					   <input data-toggle="popover" type="text" class="form-control form-control-commit" id="description_img_url" name="description_img" title="活动详情链接"/>
					<input id="upload-img-description" type="file" class="file  " name="postFile" title="活动页面图片链接"/>
				</div>
				<div class=" col-md-1">
					<label class="control-label">列表图片</label>
					</div>
				<div class=" col-md-3">
					   <input data-toggle="popover" type="text" class="form-control form-control-commit" id="show_list_img_url" name="show_list_img" title="活动列表链接"/>
					<input id="upload-img-list" type="file" class="file  " name="postFile" title="活动页面图片链接"/>
				</div>
				</div>	
				<div class="form-group">
         		<div class=" col-md-1">
					<label class="control-label">跳转地址</label>
					</div>
				<div class=" col-md-4">
				<select class="form-control form-control-commit"  id="form_activity_activity_jump_url_code" name="activity_jump_url_code">
					 <%for(ConfigCruxBean crux : cruxList ){ %>
					<option value="<%=crux.getKey() %>" ><%=crux.getCode() %></option>
		  			<%} %>
					 </select>
				</div>
				<div class=" col-md-1">
					<label class="control-label">自定义</label>
					</div>
				<div class=" col-md-6">
					   <input type="text" class="form-control form-control-commit" id="form_activity_activity_jump_url" name="activity_jump_url" title="活动跳转地址"/> 
				</div>
				</div>				
				<div class="form-group">
         		<div class=" col-md-1">
					<label class="control-label">图片跳转</label>
					</div>
				<div class=" col-md-4">
				<select class="form-control form-control-commit"  id="form_activity_activity_img_jump_url_code" name="activity_img_jump_url_code">
					 <%for(ConfigCruxBean crux : cruxImgList ){ %>
					<option value="<%=crux.getKey() %>" ><%=crux.getCode() %></option>
		  			<%} %>
					 </select>
				</div>
				<div class=" col-md-1">
					<label class="control-label">自定义</label>
					</div>
				<div class=" col-md-6">
					   <input type="text" class="form-control form-control-commit" id="form_activity_activity_img_jump_url" name="activity_img_jump_url" title="活动图片跳转链接"/>
				</div>
				</div>				
				<div class="form-group">
         		<div class=" col-md-1">
					<label class="control-label">活动描述</label>
					</div>
				<div class=" col-md-11">
					   <textarea rows="2" class="form-control form-control-commit" id="form_activity_description" name="description"></textarea> 
				</div>
				</div>
				<button type="button" class="btn btn-primary disabled" id="form-commit" onclick="commitActivityInfo();">提交更改</button>			
			</form>
			</div>
			<div class="activity_config_info_param">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-param">
        			 添加限制
    			</label>
    			<label class="btn btn-danger" id="delete-node" onclick="deleteAllitem('admin/activity/deleteActivityParam.do');">
        			批量删除
    			</label>
    			
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_param column">
			</div>
			</div>
			<div class="activity_config_info_rule">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-rule">
        			 添加规则
    			</label>
    			<label class="btn btn-danger" id="delete-rule" onclick="deleteAllitem('admin/activity/deleteActivityRule.do');">
        			批量删除
    			</label>
    			
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_rule column">
			</div>
			</div>
			<div class="activity_config_info_prize">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-prize">
        			 添加奖品
    			</label>
    			<label class="btn btn-danger" id="delete-node" onclick="deleteAllitem('admin/activity/deleteActivityPrize.do');">
        			批量删除
    			</label>
    			
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_prize column">
			</div>
			</div>
			<div class="activity_config_info_original">
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-original">
        			 关联公众号
    			</label>
    			<label class="btn btn-danger" id="delete-node"  onclick="deleteAllitem('admin/activity/deleteActivityOriginal.do');">
        			批量删除
    			</label>
    			
			</div>
			<div id="dt_example" class="example_alt_pagination dt_example_original column">
			</div>
			</div>
			</div>
		</div>
	</div>
</div>
		</div>
		</div>
        </div>
      </div>
			</div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<!-- 模态框（Modal）规则添加 -->
<div class="modal fade" id="myModal-param" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">为活动添加规则</h4>
            </div>
            <div class="modal-body">
				<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form class="form-horizontal" role="form" id="form-param">
			<input type="hidden" class="form-control" name="activity_id" />
			<input type="hidden" class="form-control" name="id" />
				<div class="form-group">
					 <label class="col-sm-2 control-label">描述</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="remark" />
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-2 control-label">状态</label>
					<div class="col-sm-4">
						<select class="form-control form-control-commit"  name="status">
					 <%for(DictionaryBean dic : dicMap.get("status") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
					</div>
					<label for="inputPassword3" class="col-sm-2 control-label">类型</label>
					<div class="col-sm-4">
						<select class="form-control form-control-commit" name="type">
					 <%for(DictionaryBean dic : paramDicMap.get("type") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
					</div>
				</div>
				<div class="form-group">
					 <label for="inputPassword3" class="col-sm-2 control-label">规则SQL</label>
					<div class="col-sm-10">
						<textarea rows="4" class="form-control" name="param_sql"></textarea>
					</div>
				</div>
				
			</form>
		</div>
	</div>
</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submit-param" onclick="submit_itemRela('admin/activity/commitActivityParam.do','myModal-param','form-param');">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<!-- 模态框（Modal）规则添加 -->
<div class="modal fade" id="myModal-rule" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">为活动添加规则</h4>
            </div>
            <div class="modal-body">
				<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form class="form-horizontal" role="form" id="form-rule">
			<input type="hidden" class="form-control" name="activity_id" />
			<input type="hidden" class="form-control" name="id" />
				<div class="form-group">
					 <label class="col-sm-2 control-label">规则描述</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="description" />
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-2 control-label">排序</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="seq" />
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submit-param" onclick="submit_itemRela('admin/activity/commitActivityRule.do','myModal-rule','form-rule');">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


<!-- 模态框（Modal）奖品添加 -->
<div class="modal fade" id="myModal-prize" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">为活动添加奖品</h4>
            </div>
            <div class="modal-body">
				<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form class="form-horizontal" role="form" id="form-prize">
			<input type="hidden" class="form-control" name="activity_id" />
			<input type="hidden" class="form-control" name="prize_id" />
			<input type="hidden" class="form-control" name="id" />
				<div class="form-group">
					 <label class="col-sm-2 control-label">选择奖品</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="product_name" onclick="showPrize();" />
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-2 control-label">实际数量</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="real_num" />
					</div>
					 <label class="col-sm-2 control-label">展示数量</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="show_num" />
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-2 control-label">权重类型</label>
					<div class="col-sm-4">
						<select class="form-control form-control-commit"  name="weight_type">
					 <%for(DictionaryBean dic : prizeDicMap.get("weight_type") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
					</div>
					<label for="inputPassword3" class="col-sm-2 control-label">权重值</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="weight" />
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-2">生效时间</label>
					<div class=" col-md-4">
					 <div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"> 
          <input class="form-control form-control-commit" size="16" name="eff_time" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input1" value="" /> 
					</div>
					 <label class="control-label col-sm-2">失效时间</label>
					 <div class=" col-md-4">
<div class="input-group date form_datetime" data-date="" data-date-format="yyyy-mm-dd" data-link-field="dtp_input1"> 
          <input class="form-control form-control-commit" size="16" name="dff_time" type="text" value="" readonly="" /> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span> 
          <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span> 
         </div> 
         <input type="hidden" id="dtp_input1" value="" /> 					
         </div>
         </div>	
			</form>
		</div>
	</div>
</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submit-param" onclick="submit_itemRela('admin/activity/commitActivityPrize.do','myModal-prize','form-prize');">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 模态框（Modal）公众号添加 -->
<div class="modal fade" id="myModal-original" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">为活动添加奖品</h4>
            </div>
            <div class="modal-body">
				<div class="container">
	<div class="row clearfix">
		<div class="col-md-12 column">
			<form class="form-horizontal" role="form" id="form-original">
			<input type="hidden" class="form-control" name="activity_code" />
			<input type="hidden" class="form-control" name="original_id" />
			<input type="hidden" class="form-control" name="id" />
				<div class="form-group">
					 <label class="col-sm-2 control-label">公众号</label>
					<div class="col-sm-4">
						<input type="text" class="form-control" name="original_name" onclick="showOriginal();" />
					</div>
					 <label class="col-sm-2 control-label">状态</label>
					<div class="col-sm-4">
						<select class="form-control form-control-commit"  name="status">
					 <%for(DictionaryBean dic : dicMap.get("status") ){ %>
					<option value="<%=Integer.valueOf(dic.getValue()) %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
					</div>
				</div>
				<div class="form-group">
					 <label class="col-sm-2 control-label">备注</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" name="remark"  />
					</div>
				</div>
			</form>
		</div>
	</div>
</div>
			</div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="submit-param" onclick="submit_itemRela('admin/activity/commitActivityOringinal.do','myModal-original','form-original');">提交</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 模态框（Modal）奖品添加 -->
<div class="modal fade" id="myModal-prizeList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">奖品列表</h4>
            </div>
            <div class="modal-body">
				<div id="dt_example" class="example_alt_pagination">
				<table id="prizeList-table" class="table table-condensed table-striped table-hover table-bordered " style="word-break:break-all; word-wrap:break-all;">
							<thead id="supplier_list_head">
								<tr>
									<th style="width:20px ">
										<input type="checkbox" class="no-margin" onclick="checkAll(this);" />
									</th>
									<th style="width:140px">奖品名称</th>
									<th style="width: 150px;">备注</th>
									<th style="width: 50px;">状态</th>
									<th style="width: 105px;">操作</th>						
								</tr>
							</thead>
							<tbody id="supplier_list">
								<%for(ActivityPrize prize:prizeList){ %>
								<tr class="gradeA">
									<td class="config_activity_table_id">
										<input type="checkbox" class="config_prize_table_id" value="<%=StringUtil.convertNull(prize.getId())%>" />
									</td>
									<td class="config_prize_table_name"><%=StringUtil.convertNull(prize.getPrize_name() )%></td>
									<td class="config_prize_table_remrk"><%=StringUtil.convertNull(prize.getRemark() )%></td>
									<td class="config_activity_table_status"><%=StringUtil.convertNull(prize.getDictValueByField("status") )%></td>
									<td>
										<a class="btn btn-danger " href="javascript:return false;" onclick="choiceItem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>选中
										</a>
									</td>
								</tr>
								<%} %>
							</tbody>
						</table>
				</div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!-- 模态框（Modal）奖品添加 -->
<div class="modal fade" id="myModal-originalList" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">奖品列表</h4>
            </div>
            <div class="modal-body">
				<div id="dt_example" class="example_alt_pagination">
				<table id="originalList-table" class="table table-condensed table-striped table-hover table-bordered " style="word-break:break-all; word-wrap:break-all;">
							<thead id="supplier_list_head">
								<tr>
									<th style="width:20px ">
										<input type="checkbox" class="no-margin" onclick="checkAll(this);" />
									</th>
									<th style="width:140px">公众号名</th>
									<th style="width: 105px;">操作</th>						
								</tr>
							</thead>
							<tbody id="supplier_list">
								<%for(WechatOriginalInfoBean original:originalList){ %>
								<tr class="gradeA">
									<td class="config_activity_table_id">
										<input type="checkbox" class="config_original_table_id" value="<%=StringUtil.convertNull(original.getOriginalid())%>" />
									</td>
									<td class="config_original_table_name"><%=StringUtil.convertNull(original.getOriginal_name() )%></td>
									<td>
										<a class="btn btn-danger " href="javascript:return false;" onclick="choiceOriginal(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>选中
										</a>
									</td>
								</tr>
								<%} %>
							</tbody>
						</table>
				</div>
			</div>
            <div class="modal-footer">
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-datetimepicker.js"></script>
	<script src="page/pc/js/bootstrap-fileinput.min.js"></script>
	<script src="page/pc/js/bootstrap-treeview.js"></script>
	<script src="page/pc/js/init-file-input.js"></script>
	<script src="page/pc/activity/js/config_activity.js"></script>
	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	<script src="js/Util.js"></script>
	<script type="text/javascript">
      //ScrollUp
      $(function () {
      $('#prizeList-table').dataTable({
        //checkbox不排序
         "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
         "aaSorting": [[1, "desc"]],
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      $('#originalList-table').dataTable({
        //checkbox不排序
         "aoColumnDefs": [{ "bSortable": false, "aTargets": [0]}],
         "aaSorting": [[1, "desc"]],
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
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
        
        
       $('[data-toggle="popover"]').each(function (num,e) {
        var element = $(this);
        var txt = "图片预览";
        element.popover({
          trigger: 'manual',
          placement: top, 
          title: txt,
          html: 'true',
        }).on("mouseenter", function () {
          var _this = this;
          console.log($(this).content);
          $(this).popover("show");
          $(this).siblings(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
          });
        }).on("mouseleave", function () {
          var _this = this;
          setTimeout(function () {
            if (!$(".popover:hover").length) {
              $(_this).popover("hide");
            }
          }, 300);
        });
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