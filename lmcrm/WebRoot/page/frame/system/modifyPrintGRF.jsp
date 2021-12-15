<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
  <head>
	<meta charset="utf-8">	
    <base href="<%=basePath%>">
    
    <title>请修改打印文件</title>
    
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
  <!--   <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
	 -->
	 
	 
	<!-- bootstrap 5.x or 4.x is supported. You can also use the bootstrap css 3.3.x versions -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" crossorigin="anonymous">
 
 
<!-- the fileinput plugin styling CSS file -->
<link href="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@4.5.3/css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
 
 
<!-- the jQuery Library -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js" crossorigin="anonymous"></script>
 
<!-- piexif.min.js is needed for auto orienting image files OR when restoring exif data in resized images and when you
    wish to resize images before upload. This must be loaded before fileinput.min.js -->
<script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@4.5.3/js/plugins/piexif.min.js" type="text/javascript"></script>
 
<!-- sortable.min.js is only needed if you wish to sort / rearrange files in initial preview. 
    This must be loaded before fileinput.min.js -->
<script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@4.5.3/js/plugins/sortable.min.js" type="text/javascript"></script>
 <!-- popper.min.js below is needed if you use bootstrap 4.x. You can also use the bootstrap js 
   3.3.x versions without popper.min.js. -->
 <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.0/dist/umd/popper.min.js"></script>
 
<!-- bootstrap.bundle.min.js below is needed if you wish to zoom and preview file content in a detail modal
    dialog. bootstrap 5.x or 4.x is supported. You can also use the bootstrap js 3.3.x versions. -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.min.js" crossorigin="anonymous"></script>
 
<!-- the main fileinput plugin script JS file -->
<script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@4.5.3/js/fileinput.min.js"></script>
 
 
<!-- optionally if you need translation for your language then include the locale file as mentioned below (replace LANG.js with your language locale) -->
<script src="https://cdn.jsdelivr.net/gh/kartik-v/bootstrap-fileinput@4.5.3/js/locales/zh.js"></script>
	
<style>
html,body {height: 100%;}
</style> 
</head>
  
  <body style="width: 80%;margin: 0 auto;margin-top: 20px;">
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <h4 class="header-title m-t-0">修改票据打印设置</h4>
                    <p class="text-muted font-13 m-b-30">
                        请点击下方空白区或将要上传的打印设置文件拖入下方后，点击上传按钮 &nbsp;&nbsp;&nbsp;&nbsp; <a href="assets/print/grf/example_print.zip" download="打印设置样例.zip"  class="btn btn-primary">下载模板</a>
                    </p>

                    <form action="/" method="post" class="dropzone" id="myAwesomeDropzone" data-plugin="dropzone" data-previews-container="#file-previews"
                        data-upload-preview-template="#uploadPreviewTemplate">
                        <div class="fallback">
                            <input id="printGRF" name="printGRF" type="file" class="file" data-browse-on-zone-click="true">
                        </div>
					
                    </form>

                </div>
                <!-- end card-body -->
            </div>
            <!-- end card-->
        </div>
        <!-- end col-->
    </div>
    <!-- end row -->
  
<!-- <script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
	<script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/vendor/dropzone.min.js"></script>
	<script src="https://cdn.jsdelivr.net/gh/sxq5542214/staticFiles/bootstrap4/hyper/assets/js/ui/component.fileupload.js"></script>
	 -->
	 <script type="text/javascript">
	 $("#printGRF").fileinput({
	    language: "zh",
	    dropZoneTitle:"请通过本地打印软件确认打印预览无误再上传~<br>点击这里上传1M以内的【.grf】打印设置文件",
	    dropZoneClickTitle: '',
	    maxFileCount: 1,
	    uploadUrl: 'admin/company/ajaxUploadPrintGRF.do',
	//    maxFileSize: 3072,
	    allowedFileExtensions: ['grf'] // ['jpg', 'png', 'jpeg', 'gif', 'bmp']
	    
	}).on("fileuploaded", function (event, data, previewId, index) {
	// 上传完成后的回调
        var response = data.response;
        alert("上传成功");
    });
	 </script>
  </body>
</html>
