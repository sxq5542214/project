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
	    	
	    	var u_id = this.dataList[index] ;
	    	
			$("#userRadio"+index).prop('checked',true);
	    }
    }
})


var code = "chart.report.adminChargeCountChart";
function queryBillData(){
	var start_date = $("#start_date").val();
	var end_date = $("#end_date").val();
	var operator_id = $("#operator_id").val();
	var address_id = $("#addressId").val();
	var price_id = $("#price_id").val();
	var dk_id = $("#dk_id").val();
	
	if(operator_id == '' && price_id == '' && dk_id== '' ){
		alert('查询条件不可为空，请输入！');
		return ;
	}
	if(address_id == ''){
		address_id = -1 ;
	}
	$.NotificationApp.send("请注意","正在查询，请稍后！","top-center","rgba(0,0,0,0.2)","info");

	$.ajax({url:"admin/report/ajaxCommonChartDataByCode.html",
		data:{
			address_id : address_id,
			start_date: start_date,
			end_date :end_date,
			operator_id :operator_id,
			price_id :price_id,
			dk_id :dk_id,
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

	$.ajax({url:"admin/operator/ajaxQueryOperatorList.do",
		success:function(result){
			userManager.operatorList  = result;
		}});
	

	$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do", data: { p_enabled : 1 },
		success:function(result){
			userManager.priceList  = result;
		}});
	

	$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
		success:function(result){
			userManager.deviceKindList  = result;
		}});
	
	
	
	
}

initParam();

$('#tree').on('changed.jstree', function (e, data) {
	// 树形列表点击事件
    var i, j, r ;
//    for(i = 0, j = data.selected.length; i < j; i++) {   如果多选，则需要循环
//      r= data.instance.get_node(data.selected[i]);
//    }
    r = data.instance.get_node(data.selected[0]);
    r = r.original;
//  alert(r.id+","+ r.text+","+ r.level +","+ r.parent +"," + r.updateDate );
    var addressId = r.id ;
    $("#addressId").val(addressId);
    
  }).jstree({
	  //树形列表加载参数
	'core' : { 	'data': { 'url': 'admin/area/ajaxQueryAddressByParent.do' },
				'themes': {
		            'name': 'proton',
		            'responsive': true
		        }
			}
});


