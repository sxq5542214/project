<%@page import="com.yd.business.product.bean.ProductTypeBean"%>
<%@page import="com.yd.business.product.bean.ProductBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierTopicBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	ProductBean bean = (ProductBean)request.getAttribute("bean");
	List<ProductTypeBean> brandList = (List<ProductTypeBean>)request.getAttribute("brandList");
	List<ProductTypeBean> typeList = (List<ProductTypeBean>)request.getAttribute("typeList");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－基础商品编辑</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="page/pc/css/wysi/bootstrap-wysihtml5.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<link rel="stylesheet" href="page/pc/css/color-picker/jquery.minicolors.css">

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
<script src="KindEditor/kindeditor-min.js"></script>
<script src="KindEditor/lang/zh_CN.js"></script>
<link href="KindEditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="product_desc"]', {
		fontSizeTable:['8pt','9pt','10pt','11pt','12pt','13pt','14pt','15pt','16pt','17pt','18pt','19pt','20pt','22pt','24pt','26pt','28pt','30pt','32pt','34pt','36pt','38pt','40pt'],
		uploadJson : '<%=path%>/KindEditor/jsp/upload_json.jsp',
        fileManagerJson : '<%=path%>/KindEditor/jsp/file_manager_json.jsp',
		allowFileManager : true
	});
	
});
</script>
</head>

<body style="background-color: #f7f7f7">
	<div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget no-margin">
                  <div class="widget-header">
                    <div class="title">
                      基础商品编辑
                    </div>
                    <span class="tools">
                      <i class="fa fa-cogs"></i>
                    </span>
                  </div>
                  <div class="widget-body">
                    <div class="row">
                      <form class="form-horizontal" method="post" name="form" id="articleForm" action="admin/product/updateProduct.do" enctype="multipart/form-data">
                      <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品名称</label>
                            <div class="col-sm-9">
                              <input type="text" value="<%=StringUtil.convertNull(bean.getName()) %>" class="form-control" id="name" name="name" placeholder="请输入商品名称">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品编码</label>
                            <div class="col-sm-9">
                              <input type="text" value="<%=StringUtil.convertNull(bean.getCode()) %>" class="form-control" id="code" name="code" placeholder="商品编码可不填">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品市场价（单位为分）</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_price" name="product_price"
												placeholder="请输入商品市场价"  value="<%=StringUtil.convertNull(bean.getProduct_price()) %>" >
                            </div>
                          </div><%-- 
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品品牌</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_brand" name="product_brand"
												placeholder="请输入商品品牌"  value="<%=StringUtil.convertNull(bean.getProduct_brand()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品类型</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_type" name="product_type"
												placeholder="请输入商品类型" value="<%=StringUtil.convertNull(bean.getProduct_type()) %>" >
                            </div>
                          </div> --%>
                           <div class="form-group">
                            <label class="col-sm-3 control-label">商品品牌</label>
                            <div class="col-sm-9">
                            	<select class="form-control" id="product_brand" name="product_brand" >
                            		<%for(ProductTypeBean brand : brandList){ %>
                            			<option value="<%=brand.getId() %>" <% if(bean.getProduct_brand() != null && brand.getId().intValue() == bean.getProduct_brand()){%>selected="selected"<%} %>><%=brand.getName() %></option>
                            		<%} %>
								</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品类型</label>
                            <div class="col-sm-9">
                            	<select class="form-control" id="product_type" name="product_type" >
                            		<%for(ProductTypeBean type : typeList){ %>
                            			<option value="<%=type.getId() %>" <% if(bean.getProduct_type() != null && type.getId().intValue() == bean.getProduct_type()){%>selected="selected"<%} %>><%=type.getName() %></option>
                            		<%} %>
								</select>
                            </div>
                          </div>
                          
                          
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品详情标题</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_title" name="product_title"
												placeholder="请输入商品详情标题"  value="<%=StringUtil.convertNull(bean.getProduct_title()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品的主图片上传</label>
                            <div class="col-sm-9">
                              <div id="imgPreview"
													style="height:160px; border:#e0e0e0 1px solid; text-align: center;">
													<img id="imageinfo" style="max-height: 160px;" name="imageinfo" src="<%=StringUtil.convertNull(bean.getHead_img())%>">
												</div>
												<div class=" btn btn-warning uploadWallpaperButton">
													选择图片 <input type="file" class="f" id="file" name="file"
														onchange="onchangeImage(this);" />
												</div>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">状态</label>
                            <div class="col-sm-9">
                              <% int status = bean.getStatus() == null ? 1:bean.getStatus() ;
											 %>
											<select class="form-control" id="status" name="status" >
													<option value="0" <% if(status==0){%>selected="selected"<%} %>>未上架</option>
													<option value="1" <% if(status==1){%>selected="selected"<%} %>>上架</option>
													<option value="2" <% if(status==2){%>selected="selected"<%} %>>下架</option>
											</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">购后赠送积分</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="give_points" name="give_points" 
                              	value="<%=StringUtil.convertNull(bean.getGive_points()) %>" >
                            </div>
                          </div>
                          <div class="form-actions">
                          	<input type="hidden" id="id" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
							<input type="submit" class="btn btn-info pull-right"
							id="save-infor-btn" value="保存"> &nbsp;&nbsp; <input type="button"
							 class="btn btn-info pull-right" value="返回"
							onclick="javascript:history.go(-1);">&nbsp;&nbsp; 
                          </div>
                      </div>
                      <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                      	商品详情描述
                        <textarea id="product_desc" name="product_desc" style="width: 100%; height:380pt;"><%=StringUtil.convertNull(bean.getProduct_desc()) %></textarea>
                        <br>
                      </div>
                      	
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>

	<script src="page/pc/js/wysi/wysihtml5-0.3.0.min.js"></script>
	<script src="jpage/pc/s/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/wysi/bootstrap3-wysihtml5.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="js/ajaxfileupload.js"></script>
	<!-- Color Picker -->
	<script src="page/pc/js/color-picker/jquery.minicolors.js"></script>
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