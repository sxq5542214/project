<%@page import="com.yd.business.dictionary.bean.DictionaryBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.supplier.bean.SupplierCardSecretBean"%>
<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierProductBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	
	PageinationData pd = (PageinationData) request.getAttribute("pd");
	SupplierCardSecretBean condition = (SupplierCardSecretBean)request.getAttribute("condition");
	List<SupplierCardSecretBean> list = (List<SupplierCardSecretBean>)pd.getDataList() ;
	List<DictionaryBean> dictList = (List<DictionaryBean>)request.getAttribute("dictList");
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－卡密查询</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="css/bootstrap.min.css" rel="stylesheet">

<link href="css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						卡密查询－<a id="inputs">商品管理</a>
					</div>
				</div>
				<div class="widget-body">
					<form action="admin/supplier/cardSecret_query.do" id="conditionFrom">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" style="border: 0">
						<tr  align="center">
							<td style="width: 10%;vertical-align : middle;border: 0;" >
								商品编号
							</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="id"
											placeholder="商品编号" value="<%=StringUtil.convertNull(condition.getId()) %>">
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;">使用号码</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="phone"
											placeholder="使用号码" value="<%=StringUtil.convertNull(condition.getPhone()) %>">
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;">卡密</td>
							<td style="width: 20%;border: 0;">
								<input type="text" class="form-control"  name="secret_key"
											placeholder="卡密" value="<%=StringUtil.convertNull(condition.getSecret_key()) %>">
							</td>
							
						</tr>
						<tr  align="center">
							<td style="width: 10%;vertical-align : middle;border: 0;" >
								状态
							</td>
							<td style="width: 20%;border: 0;">
								<select name="status" class="form-control">
									<option selected="selected">请选择</option>
									<% for(DictionaryBean dict : dictList){
										String selected = "";
										if(condition.getStatus() != null &&   dict.getValue().equals(condition.getStatus()) ){
											selected = "selected=\"selected\"";
										}
									 %>
									<option value="<%=dict.getValue() %>" <%=selected %> ><%=dict.getDescription() %></option>
									
									<%} %>
								</select>
							
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;"></td>
							<td style="width: 20%;border: 0;">
								
							</td>
							<td style="width: 10%;vertical-align : middle;border: 0;"></td>
							<td style="width: 20%;border: 0;">
								<button type="submit" class="btn btn-info">查询</button>
								<input type="hidden" name="nowpage" id="nowpage" >
							</td>
							
						</tr>
					</table>
					</form>
				
					<div id="dt_example" class="example_alt_pagination">
                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<th style="width:6%">
                              商品编号
                            </th>
                          	<th style="width:19%">
                              商品名称
                            </th>
                            <th style="width:14%">
                              使用号码
                            </th>
                            <th style="width:10%">
                              状态
                            </th>
                            <th style="width:15%">
                              卡密
                            </th>
                            <th style="width:8%">
                              订购次数
                            </th>
                            <th style="width:12%">
                              商品生效日期
                            </th>
                            <th style="width:12%">
                              商品失效日期
                            </th>
                          </tr>
                        </thead>
                        <tbody>
                        <%
                        for(int i=0;i<list.size();i++){
                        	SupplierCardSecretBean bean = list.get(i);
                        	%>
                        	<tr class="gradeX <%=i%2==0?"warning":"success"%>">
                          <td>
                            <%=bean.getId() %>
                          </td>
                          	<td>
                            <%=NumberUtil.toString(bean.getProduct_name()) %>
                          </td>
                            <td>
                              <%=NumberUtil.toString(bean.getPhone()) %>
                            </td>
                            <td>
                              <%=bean.getDictValueByField("status") %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getSecret_key()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getMonth_count()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getEff_time()) %>
                            </td>
                            <td>
                              <%=NumberUtil.toString(bean.getDff_time()) %>
                            </td>
                          </tr>
                        	<%
                        }
                        %>
                        </tbody>
                      </table>
                      <div class="dataTables_info">共 <%=pd.getTotalcount() %> 条数据</div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      		<%for(int i = 1 ; i <= pd.getTotalpage(); i ++) {
                      			String classStr = "paginate_button";
                      			if(pd.getNowpage() == i)
                      				classStr = "paginate_active";
                      		%>
                      		<a tabindex="0" class="<%=classStr %>" onclick="toPage(<%=i %>)" ><%=i %></a>
                      		<%} %>
                      	</span>
                      </div>
                      <div class="clearfix">
                      </div>
                    </div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.scrollUp.js"></script>
	<script src="js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="../../js/Util.js"></script>
	<script type="text/javascript">
		function toPage(n){
			$('#nowpage').val(n);
			$('#conditionFrom').submit();
		}
	
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
      		/* "processing": true,
	     	"serverSide": false,
	        "sPaginationType": "full_numbers",
	        "oLanguage": {
				"sLengthMenu": "每页显示 _MENU_ 条记录",
				"sZeroRecords": "抱歉， 没有找到",
				"sInfo": "从 _START_ 到 _END_ /共 _TOTAL_ 条数据",
				"sInfoEmpty": "没有数据",
				"sInfoFiltered": "(从 _MAX_ 条数据中检索)",
				"oPaginate": {
				"sFirst": "首页",
				"sPrevious": "前一页",
				"sNext": "后一页",
				"sLast": "尾页"
				}
			} */
        });
      });
    </script>

</body>
</html>