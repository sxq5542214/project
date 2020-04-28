<%@page import="com.yd.business.customer.bean.CustomerDiscountBean"%>
<%@page import="com.yd.business.customer.bean.CustomerDiscountGroupBean"%>
<%@page import="com.yd.business.customer.bean.CustomerBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/page/pc/";
	CustomerBean user = (CustomerBean) request.getAttribute("user");
	CustomerDiscountGroupBean group = (CustomerDiscountGroupBean) request.getAttribute("group");
	Map<String, List<CustomerDiscountBean>> list = (Map<String, List<CustomerDiscountBean>>) request.getAttribute("list");
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

  <title>商户管理-折扣规则编辑</title>
  <link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/new.css" rel="stylesheet">
<link href="css/style.default.css" rel="stylesheet">
</head>

<body>
<div class="row">
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						折扣管理－<a id="inputs">折扣规则编辑</a>
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
              		<div class="form-horizontal row-border">
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">折扣规则名称</label>
							<div class="col-md-10">
								<input type="text" id="name" value="<%=group.getName() %>" placeholder="请输入规则名称" class="form-control">
							</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">规则列表</label>
                    		<div class="col-md-10" id="dis_list">
	                    		<div id="dt_example" class="example_alt_pagination">
									<table
										class="table table-condensed table-striped table-hover table-bordered pull-left"
										id="data-table">
										<thead>
											<tr>
												<th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Dis.checkAll(this);" /></th>
												<th style="width: 30%">品牌名称</th>
												<th style="width: 65%">规则信息</th>
											</tr>
										</thead>
										<tbody>
											<%
												Iterator<String> it=list.keySet().iterator();
												int i=0;
												while(it.hasNext()){
													String key = it.next();
													List<CustomerDiscountBean> ls = list.get(key);
													String style = i%2==0?"success":"warn";
													%>
											<tr class="gradeX <%=style%>">
												<td><input type="checkbox" class="no-margin"/></td>
												<td><%=key %></td>
												<td>
													<%
																for(int j=0;j<ls.size();j++){
																	CustomerDiscountBean cdb = ls.get(j);
																	%>
													<table>
														<tbody id="pro_list_dis">
															<tr id="<%=cdb.getId() %>" brand="<%=cdb.getProduct_brand()%>">
																<td><a></a></td>
																<td align="center"><div class="input-group">
																		<input type="number" disabled="disabled" value="<%=cdb.getMin_price() %>" old="<%=cdb.getMin_price() %>" placeholder="最小金额"
																			id="minprice" class="form-control"><span
																			class="input-group-addon">元</span>
																	</div></td>
																<td>至</td>
																<td><div class="input-group">
																		<input type="number" disabled="disabled" value="<%=cdb.getMax_price() %>" old="<%=cdb.getMax_price() %>" placeholder="最大金额"
																			id="maxprice" class="form-control"><span
																			class="input-group-addon">元</span>
																	</div></td>
																<td><div class="input-group">
																		<input type="number" placeholder="折扣额度(0-100)"
																			value="<%=cdb.getDiscount() %>" id="discount" old="<%=cdb.getDiscount() %>" class="form-control"><span
																			class="input-group-addon">折</span>
																	</div></td>
															</tr>
														</tbody>
													</table>
													<%
																}
															%>
												</td>
											</tr>
											<%
												}
												%>
										</tbody>
									</table>
									<div class="clearfix"></div>
								</div>
							</div>
                    	</div>
                    	<div class="form-group">
                    		<label class="col-md-2 control-label">备注</label>
							<div class="col-md-10">
								<textarea rows="5" class="form-control" id="remark" placeholder="备注信息"><%=group.getRemark() %></textarea>
							</div>
                    	</div>
                    	<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="button" onclick="Dis.update();" class="btn btn-info" value="确认修改"/> <a type="submit" href="javascript:window.location.href='../../admin/discount.do';" class="btn btn-warning">返回折扣列表</a>
							</div>
						</div>
                    </div>
              	</div>
			</div>
		</div>
	</div>
<script src="js/jquery.js"></script>
<script src="js/bootstrap.min.js"></script>
<script src="js/bootstrap-wizard.min.js"></script>
<script src="js/jquery.dataTables.js"></script>
<script src="js/pc/discount.js"></script>
<script src="../../js/Util.js"></script>
<script>
	$(document).ready(function () {
	  $('#data-table').dataTable({
	  "bPaginate":true,"iDisplayLength":10,"bFilter":true,
	    "sPaginationType": "full_numbers"
	  });
	});
</script>
</body>
</html>
