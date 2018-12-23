<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<UserWechatBean>>  dicMap = (Map<String, List<UserWechatBean>>)request.getAttribute("dicMap");
	List<UserWechatBean> list = pd.getDataList();
	
	List<WechatOriginalInfoBean> wechatOriginalList = (List<WechatOriginalInfoBean>)request.getAttribute("wechatOriginalInfoList");
	
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
    <link rel="shortcut icon" href="img/favicon.ico">
    
    <link href="css/bootstrap.min.css" rel="stylesheet">

    <link href="css/new.css" rel="stylesheet">
    <!-- Important. For Theming change primary-color variable in main.css  -->

    <link href="fonts/font-awesome.min.css" rel="stylesheet">
  </head>

  <body style="background-color:#f7f7f7">


            <!-- Row Start -->
            <div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">用户管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/user/userWechatController/queryAdmimWechatUser.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">名称:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入名称模糊查询" name="query_wechatuser_nick_name" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">城市:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入城市模糊查询" name="query_wechatuser_city" value=""/>
									</div>
								</div>
							</div>
						</div>
						</div>
							<div class="form-group">
							<div class="col-sm-12">
								<input type="submit" id="submitSupplier" class="btn btn-info pull-right" value="搜索"/>  
								<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
							</div>
						</div>
					</form>
                    <div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table" style="word-break:break-all; word-wrap:break-all;">
                        <thead id="supplier_list_head">
                          <tr>
                          	<th style="width:3%"><input type="checkbox" class="no-margin" /></th>
                            <th style="width:5%">openid</th>
                            <th style="width:5%">创建时间</th>
                            <th style="width:5%">微信名称</th>
                            <th style="width:4%">电话</th>
                            <th style="width:3%">性别</th>
                            <th style="width:3%">省份</th>
                            <th style="width:3%">城市</th>
                            <th style="width:3%">状态</th>
                            <th style="width:3%">到期时间</th>
                            <th style="width:3%">父id</th>
                            <th style="width:3%">级别</th>
                            <th style="width:3%">下线数量</th>
                            <th style="width:5%">头像</th>
                            <th style="width:9%">最后访问时间</th>
                            <th style="width:5%">积分(单位:分)</th>
                            <th style="width:5%">余额(单位:分)</th>
                            <th style="width:5%">分享类型</th>
                            <th style="width:5%">分享时间</th>
                            <th style="width:4%">最后订购时间</th>
                            <th style="width:5%">公众号原始id</th>
                            <th style="width:3%">用户类别</th>
                            <th style="width:8%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_wechatuser_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_wechatuser_openid"></td>
									<td class=table_wechatuser_create_time></td>
									<td class="table_wechatuser_nick_name"></td>
									<td class="table_wechatuser_phone"></td>
									<td class="table_wechatuser_sex"></td>
									<td class="table_wechatuser_province"></td>
									<td class="table_wechatuser_city"></td>
									<td class="table_wechatuser_status"></td>
									<td class="table_wechatuser_expire_date"></td>
									<td class="table_wechatuser_parentid"></td>
									<td class="table_wechatuser_level"></td>
									<td class="table_wechatuser_offline_num"></td>
									<td class="table_wechatuser_head_img"></td>
									<td class="table_wechatuser_last_access_time"></td>
									<td class="table_wechatuser_points"></td>
									<td class="table_wechatuser_balance"></td>
									<td class="table_wechatuser_share_type"></td>
									<td class="table_wechatuser_share_time"></td>
									<td class="table_wechatuser_last_order_time"></td>
									<td class="table_wechatuser_originalid"></td>
									<td class="table_wechatuser_type"></td>
									<td>
										<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>修改
										</a>
										<a class="btn btn-danger " href="javascript:return false;"  style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
										</a>
									</td>
								</tr>
                        	
                        	<%
                        	if(list!=null&&list.size()>0)
                        	for(int i=0;i<list.size();i++){
                        	UserWechatBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_wechatuser_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_wechatuser_openid"><%=StringUtil.convertNull(bean.getOpenid())%></td>
							<td class=table_wechatuser_create_time><%=StringUtil.convertNull(bean.getCreate_time())%></td>
							<td class="table_wechatuser_nick_name"><%=StringUtil.convertNull(bean.getNick_name())%></td>
							<td class="table_wechatuser_phone"><%=StringUtil.convertNull(bean.getPhone())%></td>
							<td class="table_wechatuser_sex" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getSex() %>"/></td>
                            <td class="table_wechatuser_sex_value"><%=StringUtil.convertNull(bean.getDictValueByField("sex")) %></td>
							<td class="table_wechatuser_province"><%=StringUtil.convertNull(bean.getProvince())%></td>
							<td class="table_wechatuser_city"><%=StringUtil.convertNull(bean.getCity())%></td>
							<td class="table_wechatuser_status" style="display:none;"><input type="hidden" class="no-margin"  value="<%=bean.getStatus() %>"/></td>
                            <td class="table_wechatuser_status_value"><%=StringUtil.convertNull(bean.getDictValueByField("status")) %></td>
							<td class="table_wechatuser_expire_date"><%=StringUtil.convertNull(bean.getExpire_date())%></td>
							<td class="table_wechatuser_parentid"><%=StringUtil.convertNull(bean.getParentid())%></td>
							<td class="table_wechatuser_level"><%=StringUtil.convertNull(bean.getLevel())%></td>
							<td class="table_wechatuser_offline_num"><%=StringUtil.convertNull(bean.getOffline_num())%></td>
						 	<td class="table_wechatuser_head_img"><img src="<%=StringUtil.convertNull(bean.getHead_img())%>" alt="..." class="img-circle" style="width: 40px;"></td>
							<td class="table_wechatuser_last_access_time"><%=StringUtil.convertNull(bean.getLast_access_time())%></td>
							<td class="table_wechatuser_points"><%=StringUtil.convertNull(bean.getPoints())%></td>
							<td class="table_wechatuser_balance"><%=StringUtil.convertNull(bean.getBalance())%></td>
							<td class="table_wechatuser_share_type"><%=StringUtil.convertNull(bean.getShare_type())%></td>
							<td class="table_wechatuser_share_time"><%=StringUtil.convertNull(bean.getShare_time())%></td>
							<td class="table_wechatuser_last_order_time"><%=StringUtil.convertNull(bean.getLast_order_time())%></td>
							<td class="table_wechatuser_originalid"><%=StringUtil.convertNull(bean.getOriginalid())%></td>
							<td class="table_wechatuser_type"><%=StringUtil.convertNull(bean.getType())%></td>
                            <td>
           							<a class="btn btn-success " data-toggle="modal" data-target="#myModal" style="padding: 0px 4px;margin-left:5px;" onclick="editWechatUser(this);">
											<i class="fa"></i>修改
										</a>
									<a class="btn btn-danger " href="javascript:return false;" onclick="deleteitem(this);" style="padding: 0px 4px;margin-left:5px;">
											<i class="fa"></i>删除
									</a>
                              </td>
                          </tr>
                        	<%
                        	}
                        	 %>
                        </tbody>
                      </table>
                      <div class="clearfix">
                      </div>
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
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <!-- Row End -->




<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" >
    <div class="modal-dialog" style="width:70%">
        <div class="modal-content" >
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">配置用户</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control userwechat" type="hidden" name="wechatuser_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">openid</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入openid" name="wechatuser_openid"/>
									</div>
									<label class="col-md-2 control-label">微信名称</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入微信名称" name="wechatuser_nick_name"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">电话</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入电话" name="wechatuser_phone"/>
									</div>
									<label class="col-md-2 control-label">性别</label>
									<div class="col-xs-5">
										<select  class="form-control"  name="wechatuser_sex">
												<option value="">请输入性别</option>
												<option value="0">未知</option>
												<option value="1">男</option>
												<option value="2">女</option>
										</select>							
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">省份</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入省份" name="wechatuser_province"/>
									</div>
									<label class="col-md-2 control-label">城市</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入城市" name="wechatuser_city"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">状态</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select  class="form-control"  name="wechatuser_status">
												<option value="">请输入状态</option>
												<option value="0">不可用</option>
												<option value="1">正常</option>
										</select>							
									</div>
									<label class="col-md-2 control-label">父id</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入父id" name="wechatuser_parentid"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">级别</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入级别" name="wechatuser_level"/>
									</div>
									<label class="col-md-2 control-label">下线数量</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入下线数量" name="wechatuser_offline_num"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">头像</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入头像" name="wechatuser_head_img"/>
									</div>
									<label class="col-md-2 control-label">积分</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入积分(以分为单位)" name="wechatuser_points"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">余额</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入余额(以分为单位)" name="wechatuser_balance"/>
									</div>
									<label class="col-md-2 control-label">分享类型</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入分享类型" name="wechatuser_share_type"/>
									</div>
								</div>
						</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">公众号原始id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
									
									
									<select id="disabledSelect" class="form-control wechatuser" name="wechatuser_originalid">
											<%
												for (WechatOriginalInfoBean wechatOriginalInfoBean : wechatOriginalList) {
													String selected = "";
													if (wechatOriginalInfoBean.getOriginalid() != null || "".equals(wechatOriginalInfoBean.getOriginalid())) {
														selected = "selected=\"selected\"";
													}
											%>
												<option value="<%=wechatOriginalInfoBean.getOriginalid()%>"
												<%=selected%>><%=wechatOriginalInfoBean.getOriginalid()%></option>
											<%} %>
										</select>
									</div>
									<label class="col-md-2 control-label">用户类别</label>
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入用户类别" name="wechatuser_type"/>
									</div>
								</div>
							</div>
						</div>
			</form>
            </div>
             <div class="modal-footer">
                <button type="button" class="btn btn-primary" onclick="commitData(this);">提交</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

 

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>
	<script src="js/pc/userWechat/user_wechat.js"></script>
    <!-- Custom JS -->
    <script src="js/menu.js"></script>
    
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


    </script>

  </body>
</html>