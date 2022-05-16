<%@page import="com.yd.business.report.bean.ReportParamsBean"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.yd.business.report.bean.ReportSimpleBean"%>
<%@page import="com.yd.basic.framework.context.BaseContext"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

	ReportSimpleBean report = (ReportSimpleBean)request.getAttribute("report");
	List<Map<String,Object>> list = report.getDataList();
	List<ReportParamsBean> paramsList = report.getParamsList();
	
	String[] column_codes = report.getColumn_codes().split(",");
	String[] column_names = report.getColumn_names().split(",");
	String start_date = (String)request.getAttribute("start_date");
	String end_date = (String)request.getAttribute("end_date");
	List<BigDecimal> sumArray = new ArrayList<BigDecimal>(column_codes.length){};
	
	BigDecimal notNumberFlag = new BigDecimal(-1);
	for(int i = 0 ; i < column_codes.length ; i++){
		sumArray.add(new BigDecimal(0));
	}
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>龙马水厂－自定义报表</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

	<link rel="stylesheet" href="/staticFiles/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">

<link href="page/frame/report/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

	<link href="/staticFiles/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body style="background-color: #f7f7f7">
	<!-- Row Start -->
	<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title"><%=report.getName() %></div>
				</div>
				
				<form action="admin/report/toSingleReportPage.do?code=<%=report.getCode() %>">
					<input type="hidden" name="code" value="<%=report.getCode()%>">
					<div class="widget-body">
					<% for(int i = 0 ; i < paramsList.size(); i ++){
						ReportParamsBean param = paramsList.get(i);
						if(i% 3 == 0){
					 %>
						<div class="row" style="margin: 0 auto;">
						<%} %>
							<div class="col-4">
								<%switch(param.getParam_type()) {
									case "date": 
									String value = "";
									if("string".equals(param.getParam_category())){
										value = (String)((Map<String,Object>)param.getDataList().get(0)).get(param.getParam_code());
									}
								%>
								<%=param.getParam_name() %>：<input type="<%=param.getParam_type() %>" id="<%=param.getParam_code() %>" name="<%=param.getParam_code() %>" value="<%=value %>" data-date-format="yyyy-mm-dd" >
								<%	break;
									case "selector":
									List<Map<String,Object>> selectList = param.getDataList();
								%>
									<div class="row text-center align-items-center">
										<div class="col-4">
											<label><%=param.getParam_name() %>： </label>
										</div>
										<div class="col-8">
											<select name="<%=param.getParam_code() %>" id="<%=param.getParam_code() %>" class="form-control">
											  <option value="-1"  selected>请选择</option>
											  <% for(Map<String,Object> map : selectList ) { %>
											  <option value="<%=map.get("value") %>"><%=map.get("key") %></option>
											  <%} %>
											</select>
										</div>
									</div>
								<% 
									break;
								} %>
							</div>
						<%-- 	<div class="col-4">
								结束时间：<input type="date" id="end_date" name="end_date" value="<%=end_date %>" data-date-format="yyyy-mm-dd">
							</div> --%>
						
					<%if(i%3 ==2 || i == paramsList.size() -1 ){%>
						</div>
					<%}}  %>
							<div class="col-12 text-right">
								<input type="submit" class="btn btn-success" value="查询">
							</div>
					</div>
				</form>
				<div class="widget-body">
					<!-- <a class="btn btn-success" href="javascript:window.location.href='admin/supplierTopic/toUpdatePage.do'">
						<i class="fa fa-plus-circle"></i> 新增自定义报表
					</a> -->
					<div id="dt_example" class="example_alt_pagination">
					<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">
                        <thead>
                          <tr>
                          	<% for(String name : column_names){ %>
                              <th >
                              <%=name %>
                            </th>
                            <%} %>
                          </tr>
                        </thead>
                        <tbody>
                        <% for(int i = 0 ; i < list.size() ; i++){
                       	 	Map<String,Object> map = list.get(i);
							 %>
                          <tr class="gradeX warning">
                          	<%for(int x = 0 ; x < column_codes.length; x++ ){
                          		String code = column_codes[x];
                          		Object value = map.get(code.trim());
                          		if(value != null && value instanceof Number){
                          			BigDecimal sumValue = sumArray.get(x).add(new BigDecimal(value.toString()));
                          			sumArray.set(x, sumValue);
                          		}else if(value != null && !(value instanceof Number)){
                          			sumArray.set(x, notNumberFlag);
                          		}
                          	 %>
                            <td>
                              <%=value%>
                            </td>
                            <%} %>
                          </tr>
                          <%} %>
                          
                          <tr>
                          	<% String tips = "合计：";
                          	for(int x = 0 ; x < column_codes.length; x++ ){
                          	 %>
                            <td>
                              <%
                              	if(sumArray.get(x).compareTo(notNumberFlag) == 0){
                              		%> <%=tips %>  <%  tips = "";
                              	}else{
                              		%> <%=sumArray.get(x) %> <% 
                              	}
                               %>
                            </td>
                            <%} %>
                          </tr>
                        </tbody>
                      </table>
                      <div class="clearfix">
                      </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Row End -->

	<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="/staticFiles/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
	<script src="/staticFiles/jquery.scrollup@2.4.1/dist/jquery.scrollUp.min.js"></script>
	<script src="/staticFiles/dataTables@1.10.21/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/common/date.format.js"></script>
	<!-- Custom JS -->
	<script type="text/javascript">
	
	/*   var date = new Date();
	  var start_date = date.format("Y-m-d");
	  var date2 = new Date(date.getTime() + 43200000); // 加一天
	  var end_date = date2.format("Y-m-d");
	  $("#start_date").val(start_date);
	  $("#end_date").val(end_date); */
	  
	  
	  
	
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
    </script>

</body>
</html>