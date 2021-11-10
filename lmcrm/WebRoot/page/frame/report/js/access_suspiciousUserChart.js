var userManager =  new Vue({
    el: "#userManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        columnCodes: [],// 表格数据
        columnNames : [],
        priceList : [],
        deviceKindList : [],
        operatorList : [],
        dataList : [] ,
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getUserData: function(index){
	    	this.choseIndex = index;
	    	
	    }
    }
})


var code = "chart.report.adminSuspiciousUserChart";
function queryBillData(){
	

	$.ajax({url:"admin/report/ajaxCommonChartDataByCode.html",
		data:{
			code : code 
		},
	success:function(result){
		if(result.length == 0){
		  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
		}else {

		    var list = result.dataList ; 
		    userManager.dataList = list;
		}
	    
	}});
	
	$.NotificationApp.send("请注意","正在查询，请稍后！","top-center","rgba(0,0,0,0.2)","info");
}

function initParam(){
	
	$.ajax({url:"admin/report/ajaxQueryReportSimpleByCode.html",
		data:{
			code : code 
		},
		success:function(result){
			userManager.columnNames  = result.column_names.split(",");
			userManager.columnCodes  = result.column_codes.replace(" ", "").split(",");
		}});

	
	
	
}

initParam();

