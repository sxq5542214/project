var dashboard =  new Vue({
    el: "#dashboardDiv",
    data: {
   // 	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	paidmoney_year_sum : 'xx',
    	paidmoney_month_sum : 'xx',
    	paidmoney_day_sum : 'xx',
    	user_all_count : 'xx',
    	user_open_count : 'xx',
    	user_stop_count : 'xx',
    	device_qs_count : 'xx',
    	device_lz_count : 'xx',
    	dubious_3_count : 'xx',
    	dubious_6_count : 'xx',
    	dubious_9_count : 'xx',
    	dubious_12_count : 'xx' ,
    	
    	paidmoney_year_ratio : 'xx',
    	paidmoney_month_ratio : 'xx',
    	paidmoney_day_ratio : 'xx',
    	user_all_count_ratio : 'xx',
    	user_open_count_ratio : 'xx',
    	user_stop_count_ratio : 'xx',
    	device_qs_count_ratio : 'xx',
    	device_lz_count_ratio : 'xx',
    	dubious_3_count_ratio : 'xx',
    	dubious_6_count_ratio : 'xx',
    	dubious_9_count_ratio : 'xx',
    	dubious_12_count_ratio : 'xx' ,
    	
    	operator_name : '加载中',
    	operator_role : '加载中',
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
//    	var code = 'chart.dashboard.ajaxQueryWeekCreatedUsers';
//    	$.ajax({
//            type : "POST",
//            //请求地址
//            url : "admin/report/ajaxCommonChartDataByCode.html",
//            //数据，json字符串
//            data : {  code: code},
//            //请求成功
//            success : function(resultstr) {
//            	var result = eval('('+resultstr+")");
//            	if(resultstr == 'error')
//            	{
//                	alert("数据查询失败！" + resultstr);
//                }else{
//                	for(var i = 0 ; i < result.dataList.length;i++){
//                		 var name = result.dataList[i].name;
//                		 var num = result.dataList[i].num;
//                		 dashboard.cardTitle1 = '【'+ name +'】';
//                		 dashboard.cardText1 = '最近7日新增用户数为【'+ num +'】人';
//                	}
//                }
//            },
//            //请求失败，包含具体的错误信息
//            error : function(e){
//                alert("数据查询失败！" + e.responseText);
//            }
//        });
    	
    	
    }

});


    // 基于准备好的dom，初始化echarts实例
    var nearThreeMonthPaidMoneyChart = echarts.init(document.getElementById('nearThreeMonthPaidMoneyChart'));
//    var productChart = echarts.init(document.getElementById('productChart'));
//    var consumerChart = echarts.init(document.getElementById('consumerChart'));

    var curDate = new Date();
    var lastdate1 = new Date();
    var lastdate2 = new Date(curDate.setMonth(curDate.getMonth() -1 ,1 ));
    var lastdate3 = new Date(curDate.setMonth(curDate.getMonth() -1 ,1 ));
    // 指定图表的配置项和数据
    var nearThreeMonthPaidMoneyChartOption = {
    	tooltip: {show:true,showContent:true},
    	grid:{top:10,bottom:20},
    	legend: {
    	    data: [ lastdate1.getMonth()+1 +'月', lastdate2.getMonth()+1 +'月',lastdate3.getMonth()+1 +'月']
    	  },
        xAxis: {
            data: ["待加载"]
        },
        yAxis: {},
        series: [{
            name: lastdate1.getMonth()+1 +'月',
            type: 'line',
            data: [0]
        },{
            name: lastdate2.getMonth()+1 +'月',
            type: 'line',
            data: [0]
        },{
            name: lastdate3.getMonth()+1 +'月',
            type: 'line',
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
    
    nearThreeMonthPaidMoneyChart.showLoading();
//    productChart.showLoading();
//    consumerChart.showLoading();
    
	
//	updateProductChart();
//	updateConsumerChart();
//	updateShopOrderEffLatelyData();
//	updateShopYesterdayInfo();
	initImportantCard();
	var o_kind = 3;
	function updateNearThreeMonthPaidMoneyChart(){
	 	var xs = [];
	 	var ys = [];
	 	var ds1 = [];
	 	var ds2 = [];
	 	var ds3 = [];
	 	var code = 'chart.dashboard.ajaxNearThreeMonthPaidMoneyChartData' + o_kind;
		$.ajax({
            type : "POST",
            //请求地址
            url : "admin/report/ajaxCommonChartDataByCode.html",
            //数据，json字符串
            data : {  code: code},
            //请求成功
            success : function(result) {
            	for(var i = 0 ; i < result.dataList.length;i++){
            		 xs.push(result.dataList[i].date);
            		 if(typeof(result.dataList[i].nearOneMonthPaidSum) == 'undefined'){
                		 ds1.push(0);
            		 }else{
                		 ds1.push(result.dataList[i].nearOneMonthPaidSum);
                		 ds2.push(result.dataList[i].nearTwoMonthPaidSum);
                		 ds3.push(result.dataList[i].nearThreeMonthPaidSum);
            		 }
            	}
            	if(result.dataList.length == 0){
            		xs = ['无数据'];
            		ds = [0];
            	}
            	nearThreeMonthPaidMoneyChartOption.xAxis.data = xs;
            	nearThreeMonthPaidMoneyChartOption.series[0].data = ds1;
            	nearThreeMonthPaidMoneyChartOption.series[1].data = ds2;
            	nearThreeMonthPaidMoneyChartOption.series[2].data = ds3;
            	nearThreeMonthPaidMoneyChart.setOption(nearThreeMonthPaidMoneyChartOption);
            	nearThreeMonthPaidMoneyChart.hideLoading();
            },
            //请求失败，包含具体的错误信息
            error : function(e){
                alert("数据查询失败！" + e.responseText);
            }
        });
    }
function initImportantCard(){
	
	// 查询当前操作员信息
	$.ajax({
        //请求地址
        url : "admin/operator/ajaxQueryCurrentOperator.do", async: false,
        //请求成功
        success : function(result) {
        	dashboard.operator_role = result.o_rank99 ;
        	dashboard.operator_name = result.o_name;
        	
        	o_kind = result.o_kind;
        	updateNearThreeMonthPaidMoneyChart();
        	
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            alert("数据查询失败！" + e.responseText);
        }
    });
	
	// 查询主界面的展示数据
	$.ajax({
        type : "POST",
        //请求地址
        url : "admin/report/ajaxCommonChartDataByCode.html",
        //数据，json字符串
        data : {  code: 'chart.dashboard.ajaxCompanyCRMData'+ o_kind },
        //请求成功
        success : function(result) {
        	var result = result ;
        	if(result == 'error')
        	{
            	alert("数据查询失败！" + result);
            }else{
            	if(result.dataList.length > 0){
                	dashboard.paidmoney_year_sum =  result.dataList[0].paidmoney_year_sum;
                	dashboard.paidmoney_month_sum =  result.dataList[0].paidmoney_month_sum;
                	dashboard.paidmoney_day_sum =  result.dataList[0].paidmoney_day_sum;
                	dashboard.user_all_count =  result.dataList[0].user_all_count;
                	dashboard.user_open_count =  result.dataList[0].user_open_count;
                	dashboard.user_stop_count =  result.dataList[0].user_stop_count;
                	dashboard.device_qs_count =  result.dataList[0].device_qs_count;
                	dashboard.device_lz_count =  result.dataList[0].device_lz_count;
                	dashboard.dubious_3_count =  result.dataList[0].dubious_3_count;
                	dashboard.dubious_6_count =  result.dataList[0].dubious_6_count;
                	dashboard.dubious_9_count =  result.dataList[0].dubious_9_count;
                	dashboard.dubious_12_count =  result.dataList[0].dubious_12_count;
                	
                	var paidmoney_year_sum_lastmonth = result.dataList[0].paidmoney_year_sum_lastmonth == 0 ? 1 : result.dataList[0].paidmoney_year_sum_lastmonth ;
                	var paidmoney_month_sum_lastmonth = result.dataList[0].paidmoney_month_sum_lastmonth == 0 ? 1 : result.dataList[0].paidmoney_month_sum_lastmonth ;
                	var paidmoney_day_sum_lastmonth = result.dataList[0].paidmoney_day_sum_lastmonth == 0 ? 1 : result.dataList[0].paidmoney_day_sum_lastmonth ;
                	var user_all_count_lastmonth = result.dataList[0].user_all_count_lastmonth == 0 ? 1 : result.dataList[0].user_all_count_lastmonth ;
                	var user_open_count_lastmonth = result.dataList[0].user_open_count_lastmonth == 0 ? 1 : result.dataList[0].user_open_count_lastmonth ;
                	var user_stop_count_lastmonth = result.dataList[0].user_stop_count_lastmonth == 0 ? 1 : result.dataList[0].user_stop_count_lastmonth ;
                	var device_qs_count_lastmonth = result.dataList[0].device_qs_count_lastmonth == 0 ? 1 : result.dataList[0].device_qs_count_lastmonth ;
                	var device_lz_count_lastmonth = result.dataList[0].device_lz_count == 0 ? 1 : result.dataList[0].device_lz_count ;
                	var dubious_3_count_lastmonth = result.dataList[0].dubious_3_count_lastmonth == 0 ? 1 : result.dataList[0].dubious_3_count_lastmonth ;
                	var dubious_6_count_lastmonth = result.dataList[0].dubious_6_count_lastmonth == 0 ? 1 : result.dataList[0].dubious_6_count_lastmonth ;
                	var dubious_9_count_lastmonth = result.dataList[0].dubious_9_count_lastmonth == 0 ? 1 : result.dataList[0].dubious_9_count_lastmonth ;
                	var dubious_12_count_lastmonth = result.dataList[0].dubious_12_count_lastmonth == 0 ? 1 : result.dataList[0].dubious_12_count_lastmonth ;
                	
                	
                	dashboard.paidmoney_year_ratio = ((result.dataList[0].paidmoney_year_sum / paidmoney_year_sum_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.paidmoney_month_ratio = ((result.dataList[0].paidmoney_month_sum / paidmoney_month_sum_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.paidmoney_day_ratio = ((result.dataList[0].paidmoney_day_sum / paidmoney_day_sum_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.user_all_count_ratio = ((result.dataList[0].user_all_count / user_all_count_lastmonth -1 ) * 100 ).toFixed(2) ;
                	dashboard.user_open_count_ratio = ((result.dataList[0].user_open_count / user_open_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.user_stop_count_ratio = ((result.dataList[0].user_stop_count / user_stop_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.device_qs_count_ratio = ((result.dataList[0].device_qs_count / device_qs_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.device_lz_count_ratio = ((result.dataList[0].device_lz_count / device_lz_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.dubious_3_count_ratio = ((result.dataList[0].dubious_3_count / dubious_3_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.dubious_6_count_ratio = ((result.dataList[0].dubious_6_count / dubious_6_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.dubious_9_count_ratio = ((result.dataList[0].dubious_9_count / dubious_9_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
                	dashboard.dubious_12_count_ratio = ((result.dataList[0].dubious_12_count / dubious_12_count_lastmonth  -1 ) * 100 ).toFixed(2) ;
            	}else{
            		alert('已完成查询，但没有数据！');
            	}
            	
            	
            }
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            alert("数据查询失败！" + e.responseText);
        }
    });
}


