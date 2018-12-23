<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@page import="com.yd.business.product.bean.ProductBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	SupplierProductBean bean = (SupplierProductBean)request.getAttribute("bean");
	List<ProductBean> productList = (List<ProductBean>) request.getAttribute("productList");
	List<SupplierBean> supplierList = (List<SupplierBean>) request.getAttribute("supplierList");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－可售商品编辑</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">
<link href="page/pc/css/bootstrap-select.css" rel="stylesheet">
<link href="page/pc/css/wysi/bootstrap-wysihtml5.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<link rel="stylesheet" href="page/pc/css/color-picker/jquery.minicolors.css">

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
</head>

<body style="background-color: #f7f7f7">
	<div class="row">
              <div class="col-lg-12 col-md-12">
                <div class="widget no-margin">
                  <div class="widget-header">
                    <div class="title">
                      可售商品编辑
                    </div>
                    <span class="tools">
                      <i class="fa fa-cogs"></i>
                    </span>
                  </div>
                  <div class="widget-body">
                    <div class="row">
                      <form class="form-horizontal" method="post" name="form" id="articleForm" action="admin/supplierProduct/updateSupplierProduct.do" >
                      <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                          <div class="form-group">
                            <label class="col-sm-3 control-label">基础商品模板</label>
                            <div class="col-sm-9">
									
								 <select id="product_id" name="product_id" class="selectpicker" style="width: 100%;" data-live-search="true" title="请选择基础商品模板" onchange="changeProduct(this);">
							        <% for(ProductBean prod : productList){ 
							        	String selected = "";
							        	if(bean.getProduct_id() != null && prod.getId().intValue() == bean.getProduct_id()){
							        		selected = "selected=\"selected\"";
							        	}
							          %>
							        	<option id="prod_<%=prod.getId() %>" <%=selected %>  value="<%=prod.getId()%>" price="<%=prod.getProduct_price() %>" brand_id="<%=prod.getProduct_brand() %>" brand_name="<%=prod.getProduct_brand_name() %>"
							        	type_id=<%=prod.getProduct_type() %> type_name="<%=prod.getProduct_type_name() %>" discount="<%=prod.getDiscount() %>" ><%=prod.getName() %> </option>
							        <%} %>
							      </select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">所属商户</label>
                            <div class="col-sm-9">
									
								 <select id="supplier_id" name="supplier_id" class="form-control" style="width: 100%;" data-live-search="true" title="请选择所属商户" >
							        <% for(SupplierBean supplier : supplierList){ 
							        	String selected = "";
							        	if(bean.getSupplier_id() != null && supplier.getId().intValue() == bean.getSupplier_id()){
							        		selected = "selected=\"selected\"";
							        	}
							          %>
							        	<option <%=selected %>  value="<%=supplier.getId()%>" ><%=supplier.getName() %> </option>
							        <%} %>
							      </select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品名称</label>
                            <div class="col-sm-9">
                              <input type="text" value="<%=StringUtil.convertNull(bean.getProduct_name()) %>" class="form-control" id="product_name" name="product_name" placeholder="请输入商品名称">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品售价（单位为分）</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="product_price" name="product_price"
												placeholder="请输入商品售价"  value="<%=StringUtil.convertNull(bean.getProduct_price()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品市场价（单位为分）</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_real_price" name="product_real_price"
												placeholder="请输入商品市场价"  value="<%=StringUtil.convertNull(bean.getProduct_real_price()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品品牌</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_brand_name" name="product_brand_name"  readonly="readonly"
												placeholder="请输入商品品牌"  value="<%=StringUtil.convertNull(bean.getProduct_brand_name()) %>" >
                            </div>
                          </div>
                          <input type="hidden" id="product_brand" name="product_brand" value="<%=StringUtil.convertNull(bean.getProduct_brand()) %>">
                          <input type="hidden" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
                          <input type="hidden" id="product_type" name="product_type" value="<%=StringUtil.convertNull(bean.getProduct_type()) %>">
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品类型</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_type_name" name="product_type_name"  readonly="readonly"
												placeholder="请输入商品类型" value="<%=StringUtil.convertNull(bean.getProduct_type_name()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">商品库存</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="store_num" name="store_num" 
												placeholder="请输入商品库存"  value="<%=StringUtil.convertNull(bean.getStore_num()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">折扣(百分比，售价之上再折扣)</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="discount" name="discount" 
                              	value="<%=StringUtil.convertNull(bean.getDiscount()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">积分抵扣（折扣之上再抵扣）</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="product_offset_points" name="product_offset_points" 
                              	value="<%=StringUtil.convertNull(bean.getGive_points()) %>" >
                            </div>
                          </div>
                           <div class="form-group">
                            <label class="col-sm-3 control-label">生效时间</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="eff_time" name="eff_time" 
												class="content-form calendar-icon" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												placeholder="请输入生效时间"  value="<%=StringUtil.convertNull(bean.getEff_time()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">失效时间</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="dff_time" name="dff_time" 
												class="content-form calendar-icon" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												placeholder="请输入失效时间"  value="<%=StringUtil.convertNull(bean.getDff_time()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">分享最少获得红包金额（分）</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="min_luckymoney" name="min_luckymoney" 
												class="content-form calendar-icon" placeholder="请输入分享最少获得红包金额"  value="<%=StringUtil.convertNull(bean.getMin_luckymoney()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">分享最多获得红包金额（分）</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="max_luckymoney" name="max_luckymoney" 
												class="content-form calendar-icon" placeholder="请输入分享最多获得红包金额"  value="<%=StringUtil.convertNull(bean.getMax_luckymoney()) %>" >
                            </div>
                          </div>
                          <div class="form-actions">
                          	<input type="hidden" id="id" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
							<input type="submit"  class="btn btn-info pull-right"
							id="save-infor-btn" value="保存"> &nbsp;&nbsp; <input type="button"
							 class="btn btn-info pull-right" value="返回"
							onclick="javascript:history.go(-1);">&nbsp;&nbsp; 
                          </div>
                      </div>
                     
                      </form>
                    </div>
                  </div>
                </div>
              </div>
            </div>
	
	<script src="page/pc/js/wysi/wysihtml5-0.3.0.min.js"></script>
	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/bootstrap-select.js"></script>
	<script src="page/pc/js/wysi/bootstrap3-wysihtml5.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="js/ajaxfileupload.js"></script>
	<script src="js/supplier/supplier_topic.js"></script>
	<!-- Color Picker -->
	<script src="page/pc/js/color-picker/jquery.minicolors.js"></script>
	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	<script type="text/javascript" src="<%=basePath %>js/My97DatePicker/WdatePicker.js"></script>

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
      
      function changeProduct(sel){
      	
      	var op = $("#prod_"+sel.value);
      	$("#product_name").val(op.html());
      	$("#product_price").val(op.attr("price"));
      	$("#product_real_price").val(op.attr("price"));
      	$("#product_brand_name").val(op.attr("brand_name"));
      	$("#product_brand").val(op.attr("brand_id"));
      	$("#product_type_name").val(op.attr("type_name"));
      	$("#product_type").val(op.attr("type_id"));
      	$("#discount").val(op.attr("discount"));
      	
      }
      
    </script>

</body>
</html>