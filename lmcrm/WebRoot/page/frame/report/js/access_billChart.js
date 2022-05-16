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



var code = "chart.report.adminBillChart";
function queryBillData(){
	var start_date = $("#start_date").val();
	var end_date = $("#end_date").val();
	var operator_id = $("#operator_id").val();
	var address_id = $("#addressId").val();
	var price_id = $("#price_id").val();
	var dk_id = $("#dk_id").val();
	var cd_kindid = $("#cd_kindid").val();
	
	if(start_date > end_date){
		alert('起始时间不能晚于结束时间！');
		return ;
	}
	
	if(operator_id == '' && price_id == '' && dk_id== '' && cd_kindid =='' ){
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
			cd_kindid :cd_kindid,
			code : code 
		},
	success:function(result){
		if(result.length == 0){
		  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
		}else {

		    var list = result.dataList ; 
		    userManager.dataList = list;
		    
		    dataTables.fnClearTable();   //将数据清除  
			if(list.length >0){
				dataTables.fnAddData(list,true); 
			}
		}
	    
	}});
}

function initParam(){
	
	$.ajax({url:"admin/report/ajaxQueryReportSimpleByCode.html", async: false,
		data:{
			code : code 
		},
		success:function(result){
			userManager.columnNames  = result.column_names.split(",");
			userManager.columnCodes  = result.column_codes.split(",");
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
	
	
	var colums = [];
	for(var i=0;i< userManager.columnCodes.length ; i++ ){
		var obj = {"data": userManager.columnCodes[i] , "title": userManager.columnNames[i] };
		colums.push(obj);
	}
	
	// 前端分页
	dataTables = $('#dataTable').dataTable({"columns": colums,
	  	"columnDefs" : [{
	  		"defaultContent": " ",
	  		"targets": "_all"
	  	}], "order" : [] ,
		"oLanguage": {
	  		"sLengthMenu": "每页显示 _MENU_ 条记录",
	  		"sZeroRecords": "对不起，没有匹配的数据",
	  		"sInfo": "第 _START_ - _END_ 条 / 共 _TOTAL_ 条数据",
	  		"sInfoEmpty": "没有匹配的数据",
	  		"sInfoFiltered": "(数据表中共 _MAX_ 条记录)",
	  		"sProcessing": "正在加载中...",
	  		"sSearch": "表内搜索：",
	  		"oPaginate": {
		  		"sFirst": "第一页",
		  		"sPrevious": " 上一页 ",
		  		"sNext": " 下一页 ",
		  		"sLast": " 最后一页 "
		  		}
  		},
  		"ordering": false, //排序功能
	});

	
}

initParam();

//默认打开根节点
$("#tree").on("ready.jstree", function (e, data) {
//	alert(data.instance.get_node(6));
	var id = e.target.firstChild.firstChild.id ; // 获取根节点
    data.instance.open_node(id);//打开根节点
});

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



