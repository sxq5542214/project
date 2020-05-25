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
 	<script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js" type="text/javascript" ></script>
	<script src="https://cdn.jsdelivr.net/npm/echarts@4.7.0/dist/echarts.min.js" type="text/javascript"></script>
  </head>
  <body>
 <div class="panel panel-primary">
  <div class="panel-heading">我的店铺【<%=supplier.getName() %>】重要信息总览</div>
  <div class="panel-body" id="shopYesterdayInfo">
    昨日新增客户XX个，昨日新增订单XX个，昨日营收XX元，当前客户总量XX位。
  </div>
  <ul class="list-group" id="shopOrderEffLatelyData">
    <li class="list-group-item">【时间】【姓名】【商品】【备注】</li>
  </ul>
</div>
<div class="panel panel-info">
	<div class="panel-heading">店铺近期订单总览</div>
	<div class="panel-body">
		
		<div id="effOrderChart" style="width: 100%;height:250px;"></div>
	</div>
</div>
<div class="panel panel-success" >
	<div class="panel-heading">店铺分类下商品总览</div>
	<div class="panel-body">
		
		<div id="productChart" style="width: 100%;height:250px;"></div>
	</div>
</div>
<div class="panel panel-danger">
	<div class="panel-heading">近期营收总览</div>
	<div class="panel-body">
		
		<div id="consumerChart" style="width: 100%;height:250px;"></div>
	</div>
</div>

    <div class="container">
        
		
    </div> <!-- /container -->

</body>
 <script type="text/javascript">
 	var openid = '<%=openid%>';
 	var sid = '<%=supplier.getId()%>';
    // 基于准备好的dom，初始化echarts实例
    var effOrderChart = echarts.init(document.getElementById('effOrderChart'));
    var productChart = echarts.init(document.getElementById('productChart'));
    var consumerChart = echarts.init(document.getElementById('consumerChart'));

    // 指定图表的配置项和数据
    var effOrderChartOption = {
    	tooltip: {show:true,showContent:true},
    	grid:{top:10,bottom:20},
        xAxis: {
            data: ["待加载"]
        },
        yAxis: {},
        series: [{
            name: '当日单量',
            type: 'bar',
            data: [0]
        }]
    };
    // 指定图表的配置项和数据
    var productChartOption = {
        
    	tooltip: {show:true,showContent:true},
    	grid:{top:10,bottom:20},
        series: [{
            type: 'pie',
            data: []
        }]
    };
    // 指定图表的配置项和数据
    var consumerChartOption = {
    	tooltip: {show:true,showContent:true},
    	grid:{top:10,bottom:20},
        xAxis: {
            data: []
        },
        yAxis: {},
        series: [{
            name: '店铺收入',
            type: 'bar',
            data: [5, 25, 40, 50, 60, 80]
        },{
            name: '支付次数',
            type: 'line',
            data: [5, 25, 40, 50, 60, 80]
        }]
    };
    
    effOrderChart.showLoading();
    productChart.showLoading();
    consumerChart.showLoading();
	updateEffOrderChart();
	updateProductChart();
	updateConsumerChart();
	updateShopOrderEffLatelyData();
	updateShopYesterdayInfo();
	
    // 使用刚指定的配置项和数据显示图表。
//    productChart.setOption(productChartOption);
//    consumerChart.setOption(consumerChartOption);
    
    function toCategoryPage(){
    	location.href = "wx/supplier/shop/toManagerCategoryPage.html?openid="+openid+"&sid="+supplierId ;
    }
    function updateEffOrderChart(){
	 	var xs = [];
	 	var ys = [];
	 	var ds = [];
	 	var code = 'supplier.chart.ajaxShopOrderSuccessLatelyData';
		$.ajax({
            type : "POST",
            //请求地址
            url : "supplier/chart/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : { openid:openid , sid : sid , code: code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	for(var i = 0 ; i < result.dataList.length;i++){
                		 xs.push(result.dataList[i].date);
                		 ds.push(result.dataList[i].num);
                	}
                	if(result.dataList.length == 0){
                		xs = ['无数据'];
                		ds = [0];
                	}
                	effOrderChartOption.xAxis.data = xs;
                	effOrderChartOption.series[0].data = ds;
    				effOrderChart.setOption(effOrderChartOption);
    				effOrderChart.hideLoading();
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
    function updateProductChart(){
	 	var xs = [];
	 	var ys = [];
	 	var ds = [];
		$.ajax({
            type : "POST",
            //请求地址
            url : "supplier/chart/ajaxShopProductCountData.html",
            //数据，json字符串
            data : { openid:openid , sid : sid},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	for(var i = 0 ; i < result.dataList.length;i++){
                		 ds.push(result.dataList[i]);
                	}
                	if(result.dataList.length == 0){
                		ds = [{name: '无数据' ,value:0}];
                	}
                	productChartOption.series[0].data = ds;
    				productChart.setOption(productChartOption);
    				productChart.hideLoading();
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
    
    function updateConsumerChart(){
	 	var xs = [];
	 	var ys = [];
	 	var ds = [];
    	var code = "supplier.chart.ajaxShopOrderLatelyBalanceData";
		$.ajax({
            type : "POST",
            //请求地址
            url : "supplier/chart/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : { openid:openid , sid : sid , code:code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	for(var i = 0 ; i < result.dataList.length;i++){
                		 ds.push(result.dataList[i].date);
                		 xs.push(result.dataList[i].numsum);
                		 ys.push(result.dataList[i].numday);
                		 
                	}
                	if(result.dataList.length == 0){
                		ds = ['无数据'];
                		xs = [0];
                		ys = [0];
                	}
                	consumerChartOption.xAxis.data = ds;
                	consumerChartOption.series[0].data = xs;
                	consumerChartOption.series[1].data = ys;
    				consumerChart.setOption(consumerChartOption);
    				consumerChart.hideLoading();
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
    
    function updateShopOrderEffLatelyData(){
    	var code = "supplier.chart.ajaxShopOrderLatelyData";
		$.ajax({
            type : "POST",
            //请求地址
            url : "supplier/chart/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : { openid:openid , sid : sid ,code: code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	var shopOrderEffLatelyData = $("#shopOrderEffLatelyData");
                	var str = '';
                	for(var i = 0 ; i < result.dataList.length;i++){
                		var da = result.dataList[i];
                		var remark = da.remark;
                		if(typeof(remark) == "undefined") remark = '无备注';
                		str += '<li class="list-group-item">【'+ da.nick_name +'】提交了原价【'+ 
                		da.cost_price  +'元】的订单。订单包括【 '+ da.order_name+'】 订单状态：【'+ da.statusStr +'】</li>';
                	}
                	shopOrderEffLatelyData.html(str);
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
    
    function updateShopYesterdayInfo(){
    	var code = "supplier.chart.ajaxShopYesterdayData";
		$.ajax({
            type : "POST",
            //请求地址
            url : "supplier/chart/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : { openid:openid , sid : sid ,code: code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	var shopYesterdayInfo = $("#shopYesterdayInfo");
                	var str = '';    
                	for(var i = 0 ; i < result.dataList.length;i++){
                		str += result.dataList[i].day;
                		str += '新增客户【' + result.dataList[i].daycustomer + '】个，';
                		str += '累计客户【' + result.dataList[i].customercount + '】个，';
                		str += '新增订单【' + result.dataList[i].dayordercount + '】个，';
                		str += '订单总金额【' + (result.dataList[i].daymoney / 100).toFixed(2) + '】元<br>';
                	}
                	
                	str += '以下是最近5笔订单信息：';
                	shopYesterdayInfo.html(str);
                	
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
    
</script>
</html>
