<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	MsgCenterArticleBean bean = (MsgCenterArticleBean)request.getAttribute("article");
	List<MsgCenterArticleTypeBean> articleTypeList = (List<MsgCenterArticleTypeBean>) request.getAttribute("articleTypeList"); 
	if(bean.getSend_type() == null ){
		bean.setSend_type(-1);
	}
	if(bean.getSex_type() == null){
		bean.setSex_type(-1);
	}
	if(bean.getStatus() == null){
		bean.setStatus(-1);
	}
	if(StringUtil.isNull(bean.getStart_time())){
		bean.setStart_time(DateUtil.getNowDateStr());
	}
	if(StringUtil.isNull(bean.getEnd_time())){
		bean.setEnd_time(DateUtil.getNowDateStr());
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
						新增/修改消息文章
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form class="form-horizontal row-border" action="admin/msgcenter/article/updateArticle.do" method="post">
						<input type="hidden" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
						<input type="hidden" id="material_code" name="material_code" value="<%=StringUtil.convertNull(bean.getMaterial_code()) %>">
						<input type="hidden" id="material_name" name="material_name" value="<%=StringUtil.convertNull(bean.getMaterial_name()) %>">
						
						<div class="form-group">
							<label class="col-md-2 control-label">文章名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入文章名称" name="name" value="<%=StringUtil.convertNull(bean.getName()) %>"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">文章分类</label>
									</div>
									<div class="col-xs-5">
										<select name="type" class="form-control">
											<% for(MsgCenterArticleTypeBean type : articleTypeList){ 
												String selected = "";
												if(bean.getType() != null &&  type.getId().intValue() == bean.getType()){
													selected = "selected=\"selected\"";
												}
											%>
				                            <option value="<%=type.getId()%>" <%=selected %> >
				                              	<%=type.getArcticle_type_name() %>
				                            </option>
				                            <%} %>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">性别分类</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="sex_type" class="form-control">
				                            <option value="">
				                              	全部
				                            </option>
				                            <option value="0" <%= bean.getSex_type() == 0? " selected=\"selected\"":"" %>>
				                              	未知
				                            </option>
				                            <option value="1" <%= bean.getSex_type() == 1? " selected=\"selected\"":"" %>>
				                              	男性
				                            </option>
				                            <option value="2" <%= bean.getSex_type() == 2? " selected=\"selected\"":"" %>>
				                              	女性
				                            </option>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">区域</label>
									</div>
									<div class="col-xs-5">
										<input type="text" name="area" value="<%=StringUtil.convertNull(bean.getArea()) %>" class="form-control">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">消息发送类型</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="send_type" class="form-control" onchange="changeMaterial(this)">
											<option>
												请选择
											</option>
				                            <option value="1" <%= bean.getSend_type() == 1? " selected=\"selected\"":"" %>>
				                              	微信文本通知
				                            </option>
				                            <option value="2" <%= bean.getSend_type() == 2? " selected=\"selected\"":"" %>>
				                              	短信通知
				                            </option>
				                            <option value="3" <%= bean.getSend_type() == 3? " selected=\"selected\"":"" %>>
				                              	微信图文消息
				                            </option>
				                            <option value="4" <%= bean.getSend_type() == 4? " selected=\"selected\"":"" %>>
				                              	系统文章
				                            </option>
				                            <option value="5" <%= bean.getSend_type() == 5? " selected=\"selected\"":"" %>>
				                              	微信图文消息【批量】
				                            </option>
				                            <option value="6" <%= bean.getSend_type() == 6? " selected=\"selected\"":"" %>>
				                              	每日话题
				                            </option>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">顺序编号</label>
									</div>
									<div class="col-xs-6">
										<input type="text" name="seq"  placeholder="顺序编号,越大越优先" class="form-control" value="<%=StringUtil.convertNull(bean.getSeq()) %>">
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<label class="col-md-2 control-label">启用时间（年-月-日 时:分:秒）</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="text" name="start_time"  placeholder="请输入启用时间" value="<%=StringUtil.convertNull(bean.getStart_time()) %>">
									</div>
									
									<div class="col-xs-1">
										<label class="control-label">状态</label>
									</div>
									<div class="col-xs-6">
										<select name="status" class="form-control">
				                            <option value="1" <%= bean.getStatus() == 1? " selected=\"selected\"":"" %>>
				                              	已启用
				                            </option>
				                            <option value="0" <%= bean.getStatus() == 0? " selected=\"selected\"":"" %>>
				                              	未启用
				                            </option>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">结束时间（年-月-日 时:分:秒）</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="text" name="end_time"  placeholder="请输入结束时间" value="<%=StringUtil.convertNull(bean.getEnd_time()) %>">
									</div>
									
									<div class="col-xs-1">
										<label class="control-label">消息参数(可不填)</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="text" placeholder="请输入消息参数" name="param" value="<%=StringUtil.convertNull(bean.getParam()) %>" >
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">指定时间（年-月-日 时:分:秒）</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" type="text" readonly="readonly" name="assign_send_time"  placeholder="请输入指定发送时间" value="<%=StringUtil.convertNull(bean.getAssign_send_time()) %>">
									</div>
									<div class="col-xs-1">
										<label class="control-label">说明(可不填)</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" type="text" placeholder="请输入说明" name="remark" value="<%=StringUtil.convertNull(bean.getRemark()) %>">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">素材内容</label>
							<div class="col-md-10" id="material_div">
								<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">
									<thead>
										<tr>
											<th width="10%" ></th>
											<th> 消息素材内容 </th>
											<th class="delivery" width="10%"> 已分发公众号 </th>
										</tr>
									</thead>
									<tbody id="material_list">
										<tr class="gradeA ">
											<td style="margin: 0 auto;text-align: center;">  </td>
											<td><%=StringUtil.convertNull(bean.getMaterial_name()) %> </td>
											<td  class="delivery"> [<%=StringUtil.convertNull(bean.getDelivery_original_name()) %>] </td>
										</tr>
									</tbody>
								</table>
							</div>
							
                     	<div class="clearfix"></div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" id="submitSupplier" class="btn btn-info" value="保存文章"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
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