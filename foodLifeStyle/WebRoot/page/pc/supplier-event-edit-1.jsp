<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierEventBean"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
			
	SupplierEventBean bean = (SupplierEventBean)request.getAttribute("bean");
	
%>
<!DOCTYPE html >
<html >
<head>
<base href="<%=basePath%>" />
<title>商户活动编辑</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script src="js/jquery.js"></script>
<script src="js/shad-win-query.js"></script>
<script src="js/openDialog.js"></script>
<script src="js/ajaxfileupload.js"></script>
<script src="KindEditor/kindeditor-min.js"></script>
<script src="KindEditor/lang/zh_CN.js"></script>
<script src="<%=basePath%>js/My97DatePicker/WdatePicker.js"></script>
<script src="js/supplier/supplier_event.js"></script>

<link href="css/index-style.css" rel="stylesheet" type="text/css">
<link href="css/menu-style.css" rel="stylesheet" type="text/css">
<link href="css/bootstrap-cerulean.min.css" rel="stylesheet"
	type="text/css">
<link href="css/msg_style.css" rel="stylesheet" type="text/css">
<link href="css/define-style.css" rel="stylesheet" type="text/css">
<link href="css/common-style.css" rel="stylesheet" type="text/css">
<link href="KindEditor/themes/default/default.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
var editor;
KindEditor.ready(function(K) {
	editor = K.create('textarea[name="content"]', {
		fontSizeTable:['8pt','9pt','10pt','11pt','12pt','13pt','14pt','15pt','16pt','17pt','18pt','19pt','20pt','22pt','24pt','26pt','28pt','30pt','32pt','34pt','36pt','38pt','40pt'],
		uploadJson : 'KindEditor/jsp/upload_json.jsp',
        fileManagerJson : 'KindEditor/jsp/file_manager_json.jsp',
		allowFileManager : true
	});
});
</script>
</head>

<body onload="Art.initUpdate();">
	<form method="post" name="form" id="articleForm" action="supplierEvent/update.do" enctype="multipart/form-data">
		<div style="position:relative;">
			<div class="right-column" style="width:100%;">
				<div class="content">
					<div class="room-check-box" style="line-height:28px;">&nbsp;商户营销活动编辑</div>
					<div class="msgListContain" style=" height:auto; overflow:hidden;">
						<table width="100%" cellpadding="0" cellspacing="0"
							class="win-content-table">
							<tr>
								<th style="width:60%; vertical-align: top;">
									<table style="width: 100%; height: 100%" cellpadding="0" cellspacing="0">
										<tr>
											<th style="width:10%;">文章名称</th>
											<td style="width:30%;"><input type="text" value="<%=StringUtil.convertNull(bean.getTitle()) %>"
												class="form-control" id="title" name="title" placeholder="请输入文章标题">
											</td>
										</tr>
										<tr>
											<th>微信搜索名称</th>
											<td><input type="text" class="form-control" id="searchName" name="searchName"
												placeholder="请输入微信搜索名称"  value="<%=StringUtil.convertNull(bean.getSearchName()) %>" >
											</td>
										</tr>
										<tr>
											<th>外部链接</th>
											<td><input type="text" class="form-control" id="url" name="url"
												placeholder="请输入外部链接"  value="<%=StringUtil.convertNull(bean.getUrl()) %>" >
											</td>
										</tr>
										<tr>
											<th>消息的主图片链接</th>
											<td><input type="text" class="form-control" id="img_url" name="img_url"
												placeholder="请输入消息的主图片链接" value="<%=StringUtil.convertNull(bean.getImg_url()) %>" >
											</td>
										</tr>
										<tr>
											<th>消息的主图片上传</th>
											<td>
												<div id="imgPreview"
													style="height:160px; border:#e0e0e0 1px solid; text-align: center;">
													<img id="imageinfo" style="max-height: 160px;" name="imageinfo" src="<%=StringUtil.convertNull(bean.getImg_url())%>">
												</div>
												<div class=" btn btn-warning uploadWallpaperButton">
													选择图片 <input type="file" class="f" id="file" name="file"
														onchange="onchangeImage(this);" />
												</div>
											</td>
										</tr>
										<tr>
											<th>状态</th>
											<td>
											<% int status = 1;
											try{
												status = bean.getStatus();
											}catch(Exception e){status = 1;
											}
											 %>
											<select class="form-control" id="status" name="status" >
													<option value="1" <% if(status==1){%>selected="selected"<%} %>>可用</option>
													<option value="0" <% if(status==0){%>selected="selected"<%} %>>不可用</option>
											</select>
											</td>
										</tr>
										<tr>
											<th>活动抽奖方式说明</th>
											<td>
												<input type="text" class="form-control" id="lotteryInfo" name="lotteryInfo"
												placeholder="请输入抽奖方式"  value="<%=StringUtil.convertNull(bean.getLotteryInfo()) %>" >
											</td>
										</tr>
										<tr>
											<th>活动开奖时间</th>
											<td>
												<input type="text" class="form-control" id="lotteryDate" name="lotteryDate" 
												readonly class="content-form calendar-icon" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
												placeholder="请输入开奖时间"  value="<%=StringUtil.convertNull(bean.getLotteryDate()) %>" >
											</td>
										</tr>
										<tr>
											<th>活动已参与人数</th>
											<td>
												<input type="number" class="form-control" id="joinCount" name="joinCount" 
												placeholder="请输入已参与人数"  value="<%=bean.getJoinCount() %>" >
											</td>
										</tr>
										<tr>
											<th>活动开奖号码</th>
											<td>
												<input type="text" class="form-control" id="lotteryNumber" name="lotteryNumber"
												placeholder="请输入开奖号码（如沪深指数）"  value="<%=StringUtil.convertNull(bean.getLotteryNumber()) %>" >
											</td>
										</tr>
										
										<tr>
											<th>计算中奖号</th>
											<td>
												<input type="button" class="form-control" id="calcLottery" name="calcLottery" onclick="Art.calcLottery();"
												value="计算中奖号(开奖号码 /参与人数 取余数)" >
											</td>
										</tr>
										
									</table>
								</th>
								<td style="width:40%; height: 100%">
									<textarea id="content" name="content" style="width: 100%; height:580pt;"><%=StringUtil.convertNull(bean.getContent()) %></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div align="right">
						<input type="hidden" id="id" name="id" value="<%=StringUtil.convertNull(bean.getId()) %>">
						<input type="submit" onclick="return Art.validate();" class="btn btn-success btn-sm"
							id="save-infor-btn" value="保存"> <input type="button"
							class="btn btn-inverse btn-default btn-sm" value="返回"
							onclick="javascript:history.go(-1);">&nbsp;&nbsp; 
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
