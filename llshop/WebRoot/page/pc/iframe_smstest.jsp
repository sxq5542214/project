<%@page import="com.yd.business.sms.bean.SmsSignBean"%>
<%@page import="com.yd.business.sms.bean.SmsBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	List<SmsBean> list = (List<SmsBean>) request.getAttribute("list");
	List<SmsSignBean> listSign = (List<SmsSignBean>) request.getAttribute("signList");
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
					<div class="title">
						短信测试
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form class="form-horizontal row-border" action="../../admin/sendsms.do">
						<div class="form-group">
							<label class="col-md-2 control-label">选择签名</label>
							<div class="col-md-10">
								<div class="row">
									<div class="col-xs-5">
										<select name="sign_name" class="form-control">
										<option value="">
				                              请选择短信签名
				                            </option>
											<%
											if(listSign!=null)
											for(int i=0;i<listSign.size();i++){
											SmsSignBean bean = listSign.get(i);
											%>
											<option value="<%=bean.getSign_name()%>">
				                              <%=bean.getSign_name()%>
				                            </option>
											<%
											}
											 %>
				                        </select>
									</div>
									<div class="col-xs-1">
										<label class="control-label">选择模板</label>
									</div>
									<div class="col-xs-6">
										<select name="temp_id" class="form-control">
				                            <option value="">
				                              请选择短信模板
				                            </option>
				                            <%
											if(list!=null)
											for(int i=0;i<list.size();i++){
											SmsBean bean = list.get(i);
											%>
											<option value="<%=bean.getTemplate_id()%>">
				                              <%=bean.getTemplate_name()%>
				                            </option>
											<%
											}
											 %>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">发送号码</label>
							<div class="col-md-10">
								<textarea class="form-control" rows="5" name="rec_num" placeholder="请输入需要发送的号码，以,分割"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">短信内容</label>
							<div class="col-md-10">
								<textarea class="form-control" rows="5" name="params" placeholder="请输入短信内容，示例：{'code':'大鱼测试'}"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" id="submitSupplier" class="btn btn-info" value="发送短信"/>  <a type="submit" href="javascript:window.history.go(-1);" class="btn btn-warning">返回</a>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

    <script src="js/jquery.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/jquery.scrollUp.js"></script>
    <script src="js/jquery.dataTables.js"></script>

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

      //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
          "bPaginate":true,
          "iDisplayLength":10,
          "bFilter":true,
          "sPaginationType": "full_numbers"
        });
      });

    </script>

  </body>
</html>