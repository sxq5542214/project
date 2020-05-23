<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>			
<!DOCTYPE html>
<html lang="en">

<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="page/shop/order/css/payPrice.css">

<title>支付结果</title>

</head>
<body>

<div class="container">
  
      <div class="row">
        <div class="play col-xs-10 col-sm-10 col-md-10 col-lg-10">
          <div class="form-group" style="text-align: center;">
            <h1>完成支付</h1>
          </div>
        </div>
      </div>
      <div class="row" style="margin-top: 20%;">
        <div class="quick_amount col-xs-10 col-sm-10 col-md-10 col-lg-10" style="text-align: center;">
          <img alt="" src="images/user/pay_success.jpg">
          <h2>支付成功</h2>
        </div>
      </div>
      <div class="row">
        <div class="_submit col-xs-10 col-sm-10 col-md-10 col-lg-10">
          <input onclick="goHistory()"  type="submit" value="返回" class="btn btn-primary  submit-amount btn-lg">
        </div>
      </div>
  
</div>
</body>
<script type="text/javascript">
function goHistory(){
	window.history.go(-2);
}
</script>
</html>