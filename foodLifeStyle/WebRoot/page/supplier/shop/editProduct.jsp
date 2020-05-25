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

    <title>我的商铺</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
  	<link rel="stylesheet" href="css/bootstrap/fileinput/fileinput.min.css">
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
  	<script type="text/javascript" src="js/bootstrap/fileinput/fileinput.min.js"></script>
  	<script type="text/javascript" src="js/bootstrap/fileinput/i18n/zh.js"></script>
  </head>
  <body style="background-color: #eee">

    <div class="container">
        <h2 class="form-signup-heading" style="text-align: center;">商品设置</h2>

      <form>
	    <!-- <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
	        <div class="modal-dialog modal-lg" role="document">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	                    <h4 class="modal-title" id="myModalLabel">请选择Excel文件</h4>
	                </div>
	                <div class="modal-body">
	                    <a href="~/Data/ExcelTemplate/Order.xlsx" class="form-control" style="border:none;">下载导入模板</a>
	                    <input type="file" name="txt_file" id="txt_file"  class="file-loading" />
	                </div></div>
	        </div>
	    </div> -->
	    <label class="control-label">您可以直接点击这里 或 点击下发按钮</label>
	    <input id="input-b1" name="input-b1" type="file" class="file" data-browse-on-zone-click="true">
	</form>

    </div> <!-- /container -->

</body>
<script>
$("#input-b1").fileinput({
    language: "zh",
    uploadUrl: "/file-upload-batch/2",
    maxFileSize: 1024,
    showRemove: false,
    allowedFileExtensions: ["jpg", "png", "gif"]
});
</script>
</html>
