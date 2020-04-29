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

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">店铺免费入驻</h2>

      <form class="form-signup" action="" method="post">
        <label class="form-signup-heading">【昵称】请填：</label>
        
        <label for="supplierName" class="sr-only">店铺名称</label>
        <input type="text" id="supplierName" name="supplierName" class="form-control" placeholder="请输入店铺名称" required="required" autofocus="">
        <label for="phoneNo" class="sr-only">手机号</label>
        <input type="number" id="phoneNo" name="phoneNo" class="form-control" placeholder="请输入手机号" required="required">
        
<!--         <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me" checked="checked"> 同意入驻协议
          </label>
        </div> -->
        <button class="btn btn-lg btn-primary btn-block" type="submit">确定提交</button>
      </form>

    </div> <!-- /container -->

</body>
</html>
