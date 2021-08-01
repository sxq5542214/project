var dashboard =  new Vue({
    el: "#dashboardDiv",
    data: {
   // 	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	cardTitle1:'' ,
    	cardText1:'' ,
    	cardTitle2:'' ,
    	cardText2:'内容加载中。。。。。。。。' ,
    	cardTitle3:'' ,
    	cardText3:'内容加载中。。。。。。。。' 
    },
    methods:{
        getData: function(index){
        }
    },
    mounted:function(){
    	var code = 'chart.dashboard.ajaxQueryWeekCreatedUsers';
    	$.ajax({
            type : "POST",
            //请求地址
            url : "admin/report/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : {  code: code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	for(var i = 0 ; i < result.dataList.length;i++){
                		 var name = result.dataList[i].name;
                		 var num = result.dataList[i].num;
                		 dashboard.cardTitle1 = '【'+ name +'】';
                		 dashboard.cardText1 = '最近7日新增用户数为【'+ num +'】人';
                	}
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    	
    	
    }

});


    // 基于准备好的dom，初始化echarts实例
    var weekChargeInfoChart = echarts.init(document.getElementById('weekChargeInfoChart'));
//    var productChart = echarts.init(document.getElementById('productChart'));
//    var consumerChart = echarts.init(document.getElementById('consumerChart'));
    

    // 指定图表的配置项和数据
    var weekChargeInfoChartOption = {
    	tooltip: {show:true,showContent:true},
    	grid:{top:10,bottom:20},
        xAxis: {
            data: ["待加载"]
        },
        yAxis: {},
        series: [{
            name: '当日总金额',
            type: 'bar',
            data: [0]
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
    
    weekChargeInfoChart.showLoading();
//    productChart.showLoading();
//    consumerChart.showLoading();
	updateWeekChargeInfoChart();
//	updateProductChart();
//	updateConsumerChart();
//	updateShopOrderEffLatelyData();
//	updateShopYesterdayInfo();
	initImportantCard();
	
	function updateWeekChargeInfoChart(){
	 	var xs = [];
	 	var ys = [];
	 	var ds = [];
	 	var code = 'chart.dashboard.ajaxWeekChargeInfoChartData';
		$.ajax({
            type : "POST",
            //请求地址
            url : "admin/report/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : {  code: code},
            //请求成功
            success : function(resultstr) {
            	var result = eval('('+resultstr+")");
            	if(resultstr == 'error')
            	{
                	alert("数据查询失败！" + resultstr);
                }else{
                	for(var i = 0 ; i < result.dataList.length;i++){
                		 xs.push(result.dataList[i].date);
                		 if(typeof(result.dataList[i].money) == 'undefined'){
                    		 ds.push(0);
                		 }else{
                    		 ds.push(result.dataList[i].money);
                		 }
                	}
                	if(result.dataList.length == 0){
                		xs = ['无数据'];
                		ds = [0];
                	}
                	weekChargeInfoChartOption.xAxis.data = xs;
                	weekChargeInfoChartOption.series[0].data = ds;
                	weekChargeInfoChart.setOption(weekChargeInfoChartOption);
                	weekChargeInfoChart.hideLoading();
                }
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
function initImportantCard(){
	
	
	
}



	
	
//    function updateConsumerChart(){
//	 	var xs = [];
//	 	var ys = [];
//	 	var ds = [];
//    	var code = "supplier.chart.ajaxShopOrderLatelyBalanceData";
//		$.ajax({
//            type : "POST",
//            //请求地址
//            url : "supplier/chart/ajaxCommonChartDataByCode.html",
//            //数据，json字符串
//            data : { openid:openid , sid : sid , code:code},
//            //请求成功
//            success : function(resultstr) {
//            	var result = eval('('+resultstr+")");
//            	if(resultstr == 'error')
//            	{
//                	alert("数据查询失败！" + resultstr);
//                }else{
//                	for(var i = 0 ; i < result.dataList.length;i++){
//                		 ds.push(result.dataList[i].date);
//                		 xs.push(result.dataList[i].numsum);
//                		 ys.push(result.dataList[i].numday);
//                		 
//                	}
//                	if(result.dataList.length == 0){
//                		ds = ['无数据'];
//                		xs = [0];
//                		ys = [0];
//                	}
//                	consumerChartOption.xAxis.data = ds;
//                	consumerChartOption.series[0].data = xs;
//                	consumerChartOption.series[1].data = ys;
//    				consumerChart.setOption(consumerChartOption);
//    				consumerChart.hideLoading();
//                }
//            },
//            //请求失败，包含具体的错误信息
//            error : function(e){
//                alert("数据查询失败！" + e.responseText);
//            }
//        });
//    }
//    
//    function updateShopOrderEffLatelyData(){
//    	var code = "supplier.chart.ajaxShopOrderLatelyData";
//		$.ajax({
//            type : "POST",
//            //请求地址
//            url : "supplier/chart/ajaxCommonChartDataByCode.html",
//            //数据，json字符串
//            data : { openid:openid , sid : sid ,code: code},
//            //请求成功
//            success : function(resultstr) {
//            	var result = eval('('+resultstr+")");
//            	if(resultstr == 'error')
//            	{
//                	alert("数据查询失败！" + resultstr);
//                }else{
//                	var shopOrderEffLatelyData = $("#shopOrderEffLatelyData");
//                	var str = '';
//                	for(var i = 0 ; i < result.dataList.length;i++){
//                		var da = result.dataList[i];
//                		var remark = da.remark;
//                		if(typeof(remark) == "undefined") remark = '无备注';
//                		str += '<li class="list-group-item">【'+ da.nick_name +'】提交了原价【'+ 
//                		da.cost_price  +'元】的订单。订单包括【 '+ da.order_name+'】 订单状态：【'+ da.statusStr +'】</li>';
//                	}
//                	shopOrderEffLatelyData.html(str);
//                }
//            },
//            //请求失败，包含具体的错误信息
//            error : function(e){
//                alert("数据查询失败！" + e.responseText);
//            }
//        });
//    }
//    
//    function updateShopYesterdayInfo(){
//    	var code = "supplier.chart.ajaxShopYesterdayData";
//		$.ajax({
//            type : "POST",
//            //请求地址
//            url : "supplier/chart/ajaxCommonChartDataByCode.html",
//            //数据，json字符串
//            data : { openid:openid , sid : sid ,code: code},
//            //请求成功
//            success : function(resultstr) {
//            	var result = eval('('+resultstr+")");
//            	if(resultstr == 'error')
//            	{
//                	alert("数据查询失败！" + resultstr);
//                }else{
//                	var shopYesterdayInfo = $("#shopYesterdayInfo");
//                	var str = '';    
//                	for(var i = 0 ; i < result.dataList.length;i++){
//                		str += result.dataList[i].day;
//                		str += '新增客户【' + result.dataList[i].daycustomer + '】个，';
//                		str += '累计客户【' + result.dataList[i].customercount + '】个，';
//                		str += '新增订单【' + result.dataList[i].dayordercount + '】个，';
//                		str += '订单总金额【' + (result.dataList[i].daymoney / 100).toFixed(2) + '】元<br>';
//                	}
//                	
//                	str += '以下是最近5笔订单信息：';
//                	shopYesterdayInfo.html(str);
//                	
//                }
//            },
//            //请求失败，包含具体的错误信息
//            error : function(e){
//                alert("数据查询失败！" + e.responseText);
//            }
//        });
//    }