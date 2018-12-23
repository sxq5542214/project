<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.other.bean.ConfigCruxBean"%>
<%@page import="com.yd.util.*"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	Map<String, List<DictionaryBean>>  dicMap = (Map<String, List<DictionaryBean>>)request.getAttribute("dicMap");
	List<ConfigCruxBean>  cruxList = (List<ConfigCruxBean>)request.getAttribute("cruxList");
	List<WechatOriginalInfoBean> list = pd.getDataList();
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
    
    <link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

    <link href="page/pc/css/new.css" rel="stylesheet">
    <!-- Important. For Theming change primary-color variable in main.css  -->

    <link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
  </head>

  <body style="background-color:#f7f7f7">


            <!-- Row Start -->
            <div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget">
                  <div class="widget-header">
                    <div class="title">微信公众号管理</div>
                  </div>
                  <div class="widget-body">
                  <button class="btn btn-success"  data-toggle="modal" data-target="#myModal"><i class="fa fa-plus-circle"></i> 新增</button>
                  <form id="conditionFrom" class="form-horizontal row-border" action="../../admin/wechat/wechatOriginalInfoCrontroller/queryAdmimWechatOriginalInfo.do" method="post">
					<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">公众号名称:</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入公众号名称模糊查询" name="query_wechatoriginalinfo_original_name" value=""/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">域名:</label>
									</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入域名模糊查询" name="query_wechatoriginalinfo_server_domain" value=""/>
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
                            <th style="width:5%">公众号id</th>
                            <th style="width:6%">公众号名称</th>
                            <th style="width:4%">方法名</th>
                            <th style="width:6%">微信appid</th>
                            <th style="width:4%">秘密</th>
                            <th style="width:6%">公众号域名</th>
                            <th style="width:3%">权重</th>
                            <th style="width:9%">微信token</th>
                            <th style="width:5%">token过期时间</th>
                            <th style="width:6%">token令牌</th>
                            <th style="width:6%">商户名称</th>
                            <th style="width:4%">商户id</th>
                            <th style="width:4%">认证路劲</th>
                            <th style="width:5%">服务URL</th>
                            <th style="width:6%">微信JS</th>
                            <th style="width:4%">支付签名key</th>
                            <th style="width:6%">修改时间</th>
                            <th style="width:8%" class="hidden-phone">操作</th>
                          </tr>
                        </thead>
                        <tbody id="supplier_list_head">
                        		<tr class="gradeA" style="display:none;">
									<td class="table_wechatoriginalinfo_id"><input type="checkbox" class="no-margin" value="" /></td>
									<td class="table_wechatoriginalinfo_originalid"></td>
									<td class="table_wechatoriginalinfo_original_name"></td>
									<td class="table_wechatoriginalinfo_method_name"></td>
									<td class="table_wechatoriginalinfo_appid"></td>
									<td class="table_wechatoriginalinfo_secret"></td>
									<td class="table_wechatoriginalinfo_server_domain"></td>
									<td class="table_wechatoriginalinfo_weight"></td>
									<td class="table_wechatoriginalinfo_access_token"></td>
									<td class="table_wechatoriginalinfo_expires_in"></td>
									<td class="table_wechatoriginalinfo_token"></td>
									<td class="table_wechatoriginalinfo_mch_name"></td>
									<td class="table_wechatoriginalinfo_mch_id"></td>
									<td class="table_wechatoriginalinfo_pay_cert_file_path"></td>
									<td class="table_wechatoriginalinfo_server_url"></td>
									<td class="table_wechatoriginalinfo_jsapi_ticket"></td>
									<td class="table_wechatoriginalinfo_pay_wechat_sign_key"></td>
									<td class="table_wechatoriginalinfo_modify_time"></td>
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
                        	WechatOriginalInfoBean bean = list.get(i);
                        	%>
                        	<tr class="gradeA">
                            <td class="table_wechatoriginalinfo_id"><input type="checkbox" class="no-margin"  value="<%=bean.getId() %>"/></td>
                            <td class="table_wechatoriginalinfo_originalid"><%=StringUtil.convertNull(bean.getOriginalid())%></td>
                          	<td class="table_wechatoriginalinfo_original_name"><%=StringUtil.convertNull(bean.getOriginal_name())%></td>
							<td class="table_wechatoriginalinfo_method_name"><%=StringUtil.convertNull(bean.getMethod_name())%></td>
							<td class="table_wechatoriginalinfo_appid"><%=StringUtil.convertNull(bean.getAppid())%></td>
							<td class="table_wechatoriginalinfo_secret"><%=StringUtil.convertNull(bean.getSecret())%></td>
							<td class="table_wechatoriginalinfo_server_domain"><%=StringUtil.convertNull(bean.getServer_domain())%></td>
							<td class="table_wechatoriginalinfo_weight"><%=StringUtil.convertNull(bean.getWeight())%></td>
							<td class="table_wechatoriginalinfo_access_token"><%=StringUtil.convertNull(bean.getAccess_token())%></td>
							<td class="table_wechatoriginalinfo_expires_in"><%=StringUtil.convertNull(bean.getExpires_in())%></td>
							<td class="table_wechatoriginalinfo_token"><%=StringUtil.convertNull(bean.getToken())%></td>
							<td class="table_wechatoriginalinfo_mch_name"><%=StringUtil.convertNull(bean.getMch_name())%></td>
							<td class="table_wechatoriginalinfo_mch_id"><%=StringUtil.convertNull(bean.getMch_id())%></td>
							<td class="table_wechatoriginalinfo_pay_cert_file_path"><%=StringUtil.convertNull(bean.getPay_cert_file_path())%></td>
							<td class="table_wechatoriginalinfo_server_url"><%=StringUtil.convertNull(bean.getServer_url())%></td>
							<td class="table_wechatoriginalinfo_jsapi_ticket"><%=StringUtil.convertNull(bean.getJsapi_ticket())%></td>
							<td class="table_wechatoriginalinfo_pay_wechat_sign_key"><%=StringUtil.convertNull(bean.getPay_wechat_sign_key())%></td>
							<td class="table_wechatoriginalinfo_modify_time"><%=StringUtil.convertNull(bean.getModify_time())%></td>
                            <td>
                            <div class="btn-group">
										<button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>
										</button>
										<ul class="dropdown-menu pull-right">
											<li><a data-toggle="modal" data-target="#myModal" onclick="editDictionary(this);">编辑</a></li>
											<li><a onclick="syncData(this);">同步菜单</a></li>
											<li><a data-toggle="modal" data-target="#myModal-menu" onclick="editOriginalMenu(this);">菜单管理</a></li>
											<li><a onclick="deleteitem(this);">删除</a></li>
										</ul>
										</div>
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
                <h4 class="modal-title" id="myModalLabel">配置微信公众号信息</h4>
            </div>
            <div class="modal-body" style="padding-right: 10%;">
				<form class="form-horizontal row-border" >
						<input class="form-control wechatoriginalinfo" type="hidden" name="wechatoriginalinfo_id" value="" >
						<div class="form-group">
							<label class="col-md-2 control-label">公众号id</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入公众号id" name="wechatoriginalinfo_originalid"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">公众号名称</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入公众号名称" name="wechatoriginalinfo_original_name"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">方法名</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入方法名" name="wechatoriginalinfo_method_name"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">微信appid</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入微信appid" name="wechatoriginalinfo_appid"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">秘密</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入秘密" name="wechatoriginalinfo_secret"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">公众号域名	</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入公众号域名	" name="wechatoriginalinfo_server_domain"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">权重</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入权重" name="wechatoriginalinfo_weight"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">微信token</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入微信token" name="wechatoriginalinfo_access_token"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">token过期时间</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入token过期时间" name="wechatoriginalinfo_expires_in"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">token令牌</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入token令牌" name="wechatoriginalinfo_token"/>
									</div>
								</div>
							</div>
						</div>
							<div class="form-group">
							<label class="col-md-2 control-label">商户名称</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入商户名称" name="wechatoriginalinfo_mch_name"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">商户id</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入商户id" name="wechatoriginalinfo_mch_id"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">认证路劲</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入认证路劲" name="wechatoriginalinfo_pay_cert_file_path"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">服务URL</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入服务URL" name="wechatoriginalinfo_server_url"/>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">微信JS</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<input class="form-control" placeholder="请输入微信JS" name="wechatoriginalinfo_jsapi_ticket"/>
									</div>
									<div class="col-xs-1">
										<label class="control-label">支付签名key</label>
									</div>
									<div class="col-xs-6">
										<input class="form-control" placeholder="请输入支付签名key" name="wechatoriginalinfo_pay_wechat_sign_key"/>
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


<div class="modal fade" id="myModal-menu" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:900px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">权限组选择</h4>
            </div>
            <div class="modal-body">
	  <div class="row">
        <div class="col-sm-4">
		<div class="widget">
		<div class="widget-header">
			<div class="title">菜单</div>
		</div>
		<div class="widget-body">
		<div class="form-group">
            <input type="text" class="form-control" id="input-expand-node" placeholder="输入菜单名称模糊查询..." value="">
          </div>
          <hr>
          <div id="treeview-expandible" class="" style="overflow: auto;"></div>
        </div>
		</div>
		</div>
        <div class="col-sm-8">
		<div class="widget">
		<div class="widget-header">
			<div class="title">详细信息</div>
		</div>
		<div class="widget-body">
		<div class="container">
	<div class="row clearfix">
		<div class=" col-md-12 column">
			<ul class="breadcrumb">
				<li class='active'>菜单详细</li>
			</ul>
			<hr>
			<div class="btn-group" data-toggle="buttons" style="padding-bottom: 10px;">
    			<label class="btn btn-primary" id="add-children-node">
        			 添加子级菜单
    			</label>
    			<label class="btn btn-primary" id="add-siblings-node">
        			添加同级菜单
    			</label>
    			<label class="btn btn-danger" id="delete-node">
        			删除菜单
    			</label>
			</div>
			<form role="form" id="form_menu" class="form-horizontal">
				<div class="form-group">
					<input type="hidden" class="form-control form-control-commit" id="form_menu_id" name="id" />
					<input type="hidden" class="form-control form-control-commit" id="form_menu_parent_id" name="parent_id" />
					<input type="hidden" class="form-control" id="form_menu_nodeId" />
					<input type="hidden" class="form-control form-control-commit" id="form_menu_original_id" name="original_id" />
					<div class=" col-md-6">
					<label for="">名称</label><input type="text" class="form-control form-control-commit" id="form_menu_text" name="name" />
					</div>
					<div class=" col-md-6">
					 <label for="">类型</label>
					 <select class="form-control form-control-commit" id="form_menu_type" name="type">
					 <%for(DictionaryBean dic : dicMap.get("type") ){ %>
					<option value="<%=dic.getValue() %>" title="<%=dic.getCommons() %>"><%=dic.getDescription() %></option>
		  			<%} %>
					 </select>
				</div>
				</div>
				<div class="form-group">
				<div class=" col-md-4">
					 <label for="">关键字</label>
					 <input type="text" class="form-control form-control-commit" id="form_menu_menu_key"  name="menu_key"/>
				</div>
				<div class=" col-md-8">
					 <label for="">素材</label>
					 <input type="hidden" class="form-control form-control-commit" id="form_menu_media_id" name="media_id"  />
					 <input type="text" class="form-control form-control-commit" id="form_menu_media_code" name="media_code" onclick="showMaterialList(1);"/>
				</div>
				</div>
				<div class="form-group">
				<div class=" col-md-4">
					 <label for="">跳转</label>
					  <select class="form-control form-control-commit" id="form_menu_url_code" name="url_code">
					 <%for(ConfigCruxBean crux : cruxList ){ %>
					<option value="<%=crux.getKey() %>" ><%=crux.getCode() %></option>
		  			<%} %>
					 </select>
					 </div>
				<div class=" col-md-8">
					 <label for="">自定义跳转</label>
					 <input type="text" class="form-control form-control-commit" id="form_menu_url" name="url"/>
				</div>
				</div>
				 <button type="button" class="btn btn-default disabled" id="submit-form" title="暂时保存在本地，如需提交到微信服务器，请点击《提交至微信》" >保存至本地</button>
                <button type="button" class="btn btn-primary" onclick="commitToWeiXin();">提交至微信</button>
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
    </div><!-- /.modal -->
    
    <div class="modal fade" id="myModal-material" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" style="width:900px;margin:10px auto;" id="myModal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"aria-hidden="true">&times;</button>
					<h4 class="modal-title" id="myModalLabel">素材列表</h4>
				</div>
				<div class="modal-body">
				<div class="form-group">
					<ul id="myTab" class="nav nav-tabs">
						<li class="active">
							<a id="news_title" href="#news" data-toggle="tab">图文素材</a>
						</li>
						<li>
							<a id="image_title" href="#image" data-toggle="tab">图片素材</a>
						</li>
						<li>
							<a id="voice_title" href="#voice" data-toggle="tab">音频素材</a>
						</li>
						<li>
							<a id="video_title" href="#video" data-toggle="tab">视频素材</a>
						</li>
						
					</ul>
					<div id="myTabContent" class="tab-content">
						<div class="tab-pane fade in active" id="news" style="height:400px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:400px;">
						</div>

						</div>
						<div class="tab-pane fade" id="image" style="height:400px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:400px;">
							
						</div>

						</div>
						<div class="tab-pane fade" id="voice" style="height:400px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:400px;">
							
						</div>

						</div>
						<div class="tab-pane fade" id="video" style="height:400px;">
							<div class="col-md-10" id="material_div" style=" overflow-y:auto; overflow-x:auto; width:100%; max-height:400px;">
							
						</div>

						</div>
					</div>
					<div id="dt_example" style="padding: 5px 0;">
					<div id="page_table_bottom" class="dataTables_paginate paging_full_numbers">
                      </div>
					</div></div>
				</div>
				<div class="modal-footer">
					<a type="back" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭 </button>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal -->
	</div>
    
    
    <script src="page/pc/js/jquery.js"></script>
    <script src="page/pc/js/bootstrap.min.js"></script>
    <script src="page/pc/js/jquery.scrollUp.js"></script>
    <script src="page/pc/js/jquery.dataTables.js"></script>
	<script src="page/pc/js/pc/wechatOriginalInfo/wechat_original_info.js"></script>
		<script src="page/pc/js/bootstrap-treeview.js"></script>
	
	<script src="page/pc/wechatOriginalInfo/js/original_info.js"></script>
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


    </script>

  </body>
</html>