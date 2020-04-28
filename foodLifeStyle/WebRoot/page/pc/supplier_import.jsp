<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" href="images/favicon.png" type="image/png">

  <title>商户管理申领商品</title>
<link href="css/new.css" rel="stylesheet">
  <link href="css/style.default.css" rel="stylesheet">
	
</head>

<body>
<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						商户管理－<a id="inputs">商品导入</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
				<form class="form-horizontal row-border" action="#">
                  		<div class="form-group">
							<label class="col-md-2 control-label">选择导入文件</label>
							<div class="col-md-10">
								<input type="file" id="importfile" placeholder="请输入商户名称" class="form-control">
								<textarea hidden id="out"></textarea>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-2 control-label">选择折扣规则</label>
							<div class="col-md-10">
								<div id="dt_example" class="example_alt_pagination">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%"><input type="checkbox" onclick="Sup.checkAll(this);"
													class="no-margin" /></th>
												<th style="width: 45%">折扣规则名称</th>
												<th style="width: 50%">备注</th>
											</tr>
										</thead>
										<tbody id="data-content">
										</tbody>
									</table>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="button" id="submitSupplier" class="btn btn-info" onclick="Sup.importExcel();" value="确认导入"/>
								<input type="button" class="btn btn-danger" onclick="javascript:window.history.go(-1)" value="取消导入">
							</div>
						</div>
                  	</form>
              </div>
			</div>
		</div>
	</div>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-wizard.min.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script src="../../js/xlsx/jszip.js"></script>
<script src="../../js/xlsx/xlsx.js"></script>
<script src="../../js/xlsx/ods.js"></script>
<script src="js/pc/supplier.js"></script>
<script>
var X = XLSX;
var wtf_mode = false;

function fixdata(data) {
	var o = "", l = 0, w = 10240;
	for(; l<data.byteLength/w; ++l) o+=String.fromCharCode.apply(null,new Uint8Array(data.slice(l*w,l*w+w)));
	o+=String.fromCharCode.apply(null, new Uint8Array(data.slice(l*w)));
	return o;
}

function to_json(workbook) {
	var result = {};
	workbook.SheetNames.forEach(function(sheetName) {
		var roa = X.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
		if(roa.length > 0){
			result[sheetName] = roa;
		}
	});
	return result;
}
var outtext = '';
function process_wb(wb) {
	outtext = JSON.stringify(to_json(wb), 2, 2);
}
var xlf = document.getElementById('importfile');
function handleFile(e) {
	var files = e.target.files;
	var f = files[0];
	{
		var reader = new FileReader();
		var name = f.name;
		reader.onload = function(e) {
			var data = e.target.result;
			var wb;
			var arr = fixdata(data);
				wb = X.read(btoa(arr), {type: 'base64'});
			process_wb(wb);
		};
		reader.readAsArrayBuffer(f);
	}
}

if(xlf.addEventListener) xlf.addEventListener('change', handleFile, false);
</script>
</body>
</html>
