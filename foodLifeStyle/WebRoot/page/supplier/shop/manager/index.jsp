<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@page import="com.yd.basic.framework.context.WebContext"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String openid = (String)WebContext.getObjectBySession(WebContext.SESSION_ATTRIBUTE_USER_OPENID);
SupplierBean supplier = (SupplierBean)request.getAttribute("supplier");
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

    <title>商铺主页</title>
	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
	<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/echarts@4.7.0/dist/echarts.min.js" type="text/javascript"></script>
  </head>
  <body>
  
  <h2 class="form-signup-heading" style="text-align: center;">商铺主页</h2>
<div class="panel panel-primary">
	<div class="panel-heading">店铺近期预订单</div>
	<div class="panel-body">
		
		<div id="latelyChart" style="width: 100%;height:250px;"></div>
	</div>
</div>
<div class="panel panel-info" onclick="toCategoryPage()">
	<div class="panel-heading">店铺商品分类</div>
	<div class="panel-body">
		
		<div id="productChart" style="width: 100%;height:250px;"></div>
	</div>
</div>
<div class="panel panel-success">
	<div class="panel-heading">客户清单</div>
	<div class="panel-body">
		
		<div id="consumerChart" style="width: 100%;height:250px;"></div>
	</div>
</div>

    <div class="container">
        
		
    </div> <!-- /container -->

</body>
 <script type="text/javascript">
 	var openid = '<%=openid%>';
 	var supplierId = '<%=supplier.getId()%>';
    // 基于准备好的dom，初始化echarts实例
    var latelyChart = echarts.init(document.getElementById('latelyChart'));
    var productChart = echarts.init(document.getElementById('productChart'));
    var consumerChart = echarts.init(document.getElementById('consumerChart'));

    // 指定图表的配置项和数据
    var latelyChartOption = {
    	tooltip: {show:true,showContent:true},
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 20, 36, 10, 10, 20]
        }]
    };
    // 指定图表的配置项和数据
    var productChartOption = {
        
    	tooltip: {show:true,showContent:true},
        series: [{
            type: 'pie',
            data: [ {name: 'A', value: 1212},
            		{name: 'B', value: 1212},
            		{name: 'C', value: 1212},
            		{name: 'D', value: 1212},
            		{name: 'E', value: 1212}
            	  ]
        }]
    };
    // 指定图表的配置项和数据
    var consumerChartOption = {
    	tooltip: {show:true,showContent:true},
        xAxis: {
            data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: [5, 25, 40, 50, 60, 80]
        },{
            name: '销量',
            type: 'line',
            data: [5, 25, 40, 50, 60, 80]
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    latelyChart.setOption(latelyChartOption);
    productChart.setOption(productChartOption);
    consumerChart.setOption(consumerChartOption);
    
    function toCategoryPage(){
    	location.href = "wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+supplierId ;
    }
</script>
</html>
