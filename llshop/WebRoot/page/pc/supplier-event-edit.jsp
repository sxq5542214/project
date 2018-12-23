<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	SupplierEventBean bean = (SupplierEventBean)request.getAttribute("bean");
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－商户活动编辑</title>
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
var editor2;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		fontSizeTable:['8pt','9pt','10pt','11pt','12pt','13pt','14pt','15pt','16pt','17pt','18pt','19pt','20pt','22pt','24pt','26pt','28pt','30pt','32pt','34pt','36pt','38pt','40pt'],
		uploadJson : 'KindEditor/jsp/upload_json.jsp',
        fileManagerJson : 'KindEditor/jsp/file_manager_json.jsp',
		allowFileManager : true
	});
	
	editor2 = K.create('textarea[name="prize_content"]', {
		fontSizeTable:['8pt','9pt','10pt','11pt','12pt','13pt','14pt','15pt','16pt','17pt','18pt','19pt','20pt','22pt','24pt','26pt','28pt','30pt','32pt','34pt','36pt','38pt','40pt'],
		uploadJson : 'KindEditor/jsp/upload_json.jsp',
        fileManagerJson : 'KindEditor/jsp/file_manager_json.jsp',
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
                      商户营销活动编辑
                    </div>
                    <span class="tools">
                      <i class="fa fa-cogs"></i>
                    </span>
                  </div>
                  <div class="widget-body">
                    <div class="row">
                      <form class="form-horizontal" method="post" name="form" id="articleForm" action="supplierEvent/update.do" enctype="multipart/form-data">
                      <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                          <div class="form-group">
                            <label class="col-sm-3 control-label">文章名称</label>
                            <div class="col-sm-9">
                              <input type="text" value="<%=StringUtil.convertNull(bean.getTitle()) %>" class="form-control" id="title" name="title" placeholder="请输入文章标题">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">文章名称后缀</label>
                            <div class="col-sm-9">
                              <input type="text" value="<%=StringUtil.convertNull(bean.getTitle_attached()) %>" class="form-control" id="title_attached" name="title_attached" placeholder="请输入文章标题后缀">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">微信搜索名称</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="searchName" name="searchName"
												placeholder="请输入微信搜索名称"  value="<%=StringUtil.convertNull(bean.getSearchName()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">外部链接</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="url" name="url"
												placeholder="请输入外部链接"  value="<%=StringUtil.convertNull(bean.getUrl()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">消息的主图片链接</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="img_url" name="img_url"
												placeholder="请输入消息的主图片链接" value="<%=StringUtil.convertNull(bean.getImg_url()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">消息的主图片上传</label>
                            <div class="col-sm-9">
                              <div id="imgPreview"
													style="height:160px; border:#e0e0e0 1px solid; text-align: center;">
													<img id="imageinfo" style="max-height: 160px;" name="imageinfo" src="<%=StringUtil.convertNull(bean.getImg_url())%>">
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
													<option value="1" <% if(status==1){%>selected="selected"<%} %>>可用</option>
													<option value="0" <% if(status==0){%>selected="selected"<%} %>>不可用</option>
											</select>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">活动抽奖方式说明</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="lotteryInfo" name="lotteryInfo"
												placeholder="请输入抽奖方式"  value="<%=StringUtil.convertNull(bean.getLotteryInfo()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">活动截止时间</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="end_time" name="end_time" 
												class="content-form calendar-icon" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												placeholder="请输入活动截止时间"  value="<%=StringUtil.convertNull(bean.getEnd_time()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">活动开奖时间</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="lotteryDate" name="lotteryDate" 
												class="content-form calendar-icon" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												placeholder="请输入开奖时间"  value="<%=StringUtil.convertNull(bean.getLotteryDate()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">活动已参与人数</label>
                            <div class="col-sm-9">
                              <input type="number" class="form-control" id="joinCount" name="joinCount" 
												placeholder="请输入已参与人数"  value="<%=bean.getJoinCount() %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">领奖信息</label>
                            <div class="col-sm-9">
                              <textarea  id="lotteryPlace" name="lotteryPlace" style="width: 100%; height:100pt;"
												placeholder="请输入领奖信息"  ><%=bean.getLotteryPlace() %></textarea>
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">活动开奖号码</label>
                            <div class="col-sm-9">
                              <input type="text" class="form-control" id="lotteryNumber" name="lotteryNumber"
												placeholder="请输入开奖号码（如沪深指数）"  value="<%=StringUtil.convertNull(bean.getLotteryNumber()) %>" >
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="col-sm-3 control-label">计算中奖号</label>
                            <div class="col-sm-9">
                              <input type="button" class="form-control" id="calcLottery" name="calcLottery" onclick="Art.calcLottery();"
												value="计算中奖号(开奖号码 /参与人数 取余数)" >
                            </div>
                          </div>
                          <div class="form-actions">
                          	<input type="hidden" id="id" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
						<input type="submit" onclick="return Art.validate();" class="btn btn-info pull-right"
							id="save-infor-btn" value="保存"> &nbsp;&nbsp; <input type="button"
							 class="btn btn-info pull-right" value="返回"
							onclick="javascript:history.go(-1);">&nbsp;&nbsp; 
                          </div>
                      </div>
                      <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                      	文章信息
                        <textarea id="content" name="content" style="width: 100%; height:380pt;"><%=StringUtil.convertNull(bean.getContent()) %></textarea>
                        <br>
                      </div>
                      	
                       <div class="col-lg-4 col-md-4 col-sm-12 col-xs-12">
                    	 奖品信息
                        <textarea id="prize_content" name="prize_content" style="width: 100%; height:280pt;"><%=StringUtil.convertNull(bean.getPrize_content()) %></textarea>
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
	<script src="js/supplier/supplier_event.js"></script>
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