<%@page import="com.yd.business.msgcenter.bean.MsgCenterActionDefineBean"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterActionArticleRelationBean"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleTypeBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.msgcenter.bean.MsgCenterArticleBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	MsgCenterActionArticleRelationBean bean = (MsgCenterActionArticleRelationBean)request.getAttribute("bean");
	List<MsgCenterActionDefineBean> actionDefineList = (List<MsgCenterActionDefineBean>) request.getAttribute("actionDefineList");
	List<MsgCenterArticleTypeBean> articleTypeList = (List<MsgCenterArticleTypeBean>) request.getAttribute("articleTypeList");
	List<MsgCenterArticleBean> articleList = (List<MsgCenterArticleBean>) request.getAttribute("articleList");
	
	if(bean.getArticle_type() == null){
		bean.setArticle_type(-1);
	} 
	if(bean.getArticle_id() == null){
		bean.setArticle_id(-1);
	}
	if(bean.getStatus() == null){
		bean.setStatus(-1);
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
						新增/修改用户动作与消息文章关系
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form class="form-horizontal row-border" action="admin/msgcenter/article/updateActionArticleRelation.do">
						<input type="hidden" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
						<input type="hidden" id="action_name" name="action_name" value="<%=StringUtil.convertNull(bean.getAction_name()) %>">
						<input type="hidden" id="article_type_name" name="article_type_name" value="<%=StringUtil.convertNull(bean.getArticle_type_name()) %>">
						<input type="hidden" id="article_name" name="article_name" value="<%=StringUtil.convertNull(bean.getArticle_name())%>">
						
						
						<div class="form-group">
							<label class="col-md-2 control-label">文章分类</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="article_type" id="article_type" class="form-control" onchange="change_article_type(this)">
											<option value="">请选择</option>
											<% for(MsgCenterArticleTypeBean type : articleTypeList){ 
												String selected = "";
												if(bean.getArticle_type().intValue() == type.getId() ){
													selected = "selected=\"selected\"";
												}
											%>
				                            <option value="<%=type.getId()%>" <%=selected %> title="<%=type.getArcticle_type_name() %>">
				                              	<%=type.getArcticle_type_name() %>
				                            </option>
				                            <%} %>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">用户动作</label>
									</div>
									<div class="col-xs-5">
										<select name="action_type" id="action_type" class="form-control" onchange="change_action_define(this)">
											<option value="">请选择</option>
											<% for(MsgCenterActionDefineBean define : actionDefineList){ 
												String selected = "";
												if(define.getAction_type().equals(bean.getAction_type())){
													selected = "selected=\"selected\"";
												}
											%>
				                            <option value="<%=define.getAction_type()%>" <%=selected %> title="<%=define.getAction_name() %>" >
				                              	<%=define.getAction_name() %>
				                            </option>
				                            <%} %>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">指定文章</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="article_id" id="article_id" class="form-control" onchange="change_article(this)">
											<option value="">不指定（按文章分类的规则）</option>
											<% for(MsgCenterArticleBean article : articleList){ 
												String selected = "";
												if(bean.getArticle_id().intValue() == article.getId() ){
													selected = "selected=\"selected\"";
												}
											%>
				                            <option value="<%=article.getId()%>" <%=selected %> title="<%=article.getName() %>">
				                              	<%=article.getName() %>
				                            </option>
				                            <%} %>
				                        </select>
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
							<label class="col-md-2 control-label">延迟时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input type="text" name="delay_time"  placeholder="文章发送的延迟时间" class="form-control" value="<%=StringUtil.convertNull(bean.getDelay_time()) %>">
										
									</div>
									<div class="col-xs-1">
										<label class="control-label"></label>
									</div>
									<div class="col-xs-6">
										
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" id="submitSupplier" class="btn btn-info" value="保存"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
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

    function change_article_type(option){
    	$("#article_type_name").val($("#"+option.id).find("option:selected").text());
    }
    function change_article(option){
    	$("#article_name").val($("#"+option.id).find("option:selected").text());
    }
    function change_action_define(option){
    	$("#action_name").val($("#"+option.id).find("option:selected").text());
    }

    </script>

  </body>
</html>