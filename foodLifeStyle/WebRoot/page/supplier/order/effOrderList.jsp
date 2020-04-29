<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <base href="<%=basePath%>">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="/favicon.ico">

    <title>商户免费入驻</title>
	<link rel="stylesheet" href="css/supplier/signup.css">
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  </head>
  <body style="background-color: #eee">
	
    <div class="container-fluid">
      <div class="table-responsive">
      		<table class="table table-striped table-condensed">
      			<thead>
      				<tr>
      					<th>日期</th>
      					<th>预定内容</th>
      					<th>昵称</th>
      					<th>姓名</th>
      				</tr>
      			</thead>
      			<tbody>
      				<tr>
      					<td>4.28早</td>
      					<td>包子</td>
      					<td>ice</td>
      					<td>冰块</td>
      				</tr>
      				<tr>
      					<td>4.28中</td>
      					<td>包子</td>
      					<td>ice</td>
      					<td>冰块</td>
      				</tr>
      				<tr>
      					<td>4.28早</td>
      					<td>包子</td>
      					<td>ice</td>
      					<td>冰块</td>
      				</tr>
      				<tr>
      					<td>4.28晚</td>
      					<td>包子</td>
      					<td>ice</td>
      					<td>冰块</td>
      				</tr>
      			</tbody>
      		</table>
      </div>
    </div> <!-- /container -->

</body>
</html>
