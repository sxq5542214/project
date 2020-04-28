<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.activity.bean.ActivityConfigBean"%>
<%@page import="com.yd.business.activity.bean.ActivityPrize"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	List<ActivityPrize> prizeList = (List<ActivityPrize>)request.getAttribute("prizeList");
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
						奖品管理
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form id="conditionFrom" class="form-horizontal row-border" action="admin/activity/getCommentConfigList.do" method="post">
						<div class="form-group">
							<div class="col-sm-12">
								<a style="margin-left: 10px;" type="add" class="btn btn-success pull-left"  onclick="editActivityConfig(null);">新增</a> 
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
									<th style="width: 150px;">名称</th>
									<th style="width: 80px;">类别</th>
									<th style="width: 80px;">剩余数</th>
									<th style="width: 80px;">备注</th>
									<th style="width: 50px;">状态</th>
									<th style="width: 105px;">操作</th>						
								</tr>
							</thead>
							<tbody id="supplier_list">
								<%for(ActivityPrize prize:prizeList){ %>
								<tr class="gradeA">
									<td class="config_prize_table_id">
										<input type="checkbox" class="config_prize_table_id" value="<%=StringUtil.convertNull(prize.getId())%>" />
										<input type="hidden" class="config_prize_table_status" value="<%=StringUtil.convertNull(prize.getStatus())%>" />
										<input type="hidden" class="config_prize_table_category" value="<%=StringUtil.convertNull(prize.getCategory())%>" />
										<input type="hidden" class="config_prize_table_prize_img_url" value="<%=StringUtil.convertNull(prize.getPrize_img_url())%>" />
									</td>
									<td class="config_prize_table_prize_name"><%=StringUtil.convertNull(prize.getPrize_name() )%></td>
									<td class="config_prize_table_category_value"><%=StringUtil.convertNull(prize.getDictValueByField("category") )%></td>
									<td class="config_prize_table_remain_num"><%=StringUtil.convertNull(prize.getRemain_num() )%></td>
									<td class="config_prize_table_remark"><%=StringUtil.convertNull(prize.getRemark() )%></td>
									<td class="config_prize_table_status_value"><%=StringUtil.convertNull(prize.getDictValueByField("status") )%></td>
									<td>
										<a class="btn btn-success " style="padding: 0px 4px;margin-left:5px;" onclick="editParam(this,'myModal-editConf','form_config_prize');" >
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
								<%} %>
							</tbody>
						</table>
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
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">奖品详细信息</h4>
            </div>
            <div class="modal-body">
            <div class="row"> 
   <div class="col-sm-12" style="padding-right:15px;"> 
    <div class="widget"> 
     <div class="widget-header"> 
      <div class="title" id="info-title">
       详细信息
      </div> 
     </div> 
     <div class="widget-body"> 
      <div class="container"> 
       <div class="row clearfix"> 
        <div class=" col-md-12 column"> 
         <div class="activity_config_info_baseinfo"> 
          <form role="form" id="form_config_prize" class="form-horizontal"> 
           <input type="hidden" class="form-control form-control-commit" id="form_activity_id" name="id" /> 
           <div class="form-group"> 
            <div class=" col-md-1"> 
             <label class="control-label">名&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;称</label> 
            </div> 
            <div class=" col-md-11"> 
             <input type="text" class="form-control form-control-commit" id="form_prize_prize_name" name="prize_name" /> 
            </div> 
           </div> 
           <div class="form-group"> 
            <div class=" col-md-1"> 
             <label class="control-label">活动类型</label> 
            </div> 
            <div class=" col-md-3"> 
             <select class="form-control form-control-commit" id="form_activity_category" name="category"> 
             <%for(DictionaryBean dic : dicMap.get("category") ){ %> <option value="<%=dic.getValue() %>">
             <%=dic.getDescription() %></option> <%} %> </select> 
            </div> 
            
            <div class=" col-md-1"> 
             <label class="control-label">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态</label> 
            </div> 
            <div class=" col-md-3"> 
             <select class="form-control form-control-commit" id="form_activity_status" name="status"> 
             <%for(DictionaryBean dic : dicMap.get("status") ){ %> <option value="<%=dic.getValue() %>">
             <%=dic.getDescription() %></option> <%} %> </select> 
            </div> 
            <div class=" col-md-1"> 
             <label class="control-label">剩余数</label> 
            </div> 
            <div class=" col-md-3"> 
              <input type="text" class="form-control form-control-commit" id="form_prize_remain_num" name="remain_num" /> 
            </div> 
           </div> 
           <div class="form-group"> 
            <div class=" col-md-1"> 
             <label class="control-label">备注</label> 
            </div> 
            <div class=" col-md-11"> 
             <input type="text" class="form-control form-control-commit" id="form_prize_remark" name="remark" /> 
            </div> 
           </div> 
           <div class="form-group"> 
            <div class=" col-md-1"> 
             <label class="control-label">图片跳转</label> 
            </div> 
            <div class=" col-md-11"> 
             <input type="text" class="form-control form-control-commit" id="form_prize_prize_img_url" name="prize_img_url"/> 
            </div> 
           </div>            
           <button type="button" class="btn btn-primary disabled" id="form-commit" onclick="submit_itemRela('admin/prize/commitActivityPrize.do','myModal-editConf','form_config_prize');">提交更改</button> 
          </form> 
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


	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-datetimepicker.js"></script>
	<script src="page/pc/js/bootstrap-treeview.js"></script>
	<script src="page/pc/prize/js/config_prize.js"></script>
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