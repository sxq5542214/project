var dataTables ;
var changeDevice =  new Vue({
    el: "#changeDeviceDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        changeMeterList: [],// 表格数据
        newRow:{}// 新增的行数据，用于新增行
    },
    created: function(){
    	
    },
    methods:{
	    getData: function(index){
//	    	alert(this.operatorList[index].p_name );
	    	this.choseIndex = index;
	    	
			$("#radio"+index).prop('checked',true);
	    }
    }
})


function readCardAndQueryChangeDevice(){

	//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
	callWindowsClientMethod('readCard','{}' , function(result){
		if(result < 1){
			alert("操作卡失败，无法获取数据");
			return ;
		}
//		alert(result);
		var json = eval('(' + result + ')');
		var user_no = json.stru_userparm.iUserNo;
		var iSavingNo = json.stru_userparm.iSavingNo;
		var iUserFlag = json.stru_userparm.stru_retparm.iUserFlag;
		var iSetFlag = json.stru_userparm.stru_retparm.iSetFlag;
		var iFlag = json.stru_userparm.stru_retparm.iFlag;
		var iYear = json.stru_userparm.stru_retparm.iYear;
		var iMonth = json.stru_userparm.stru_retparm.iMonth;
		var iDay = json.stru_userparm.stru_retparm.iDay;
		var iMonth = iMonth <10 ? "0"+iMonth : iMonth;
		var iDay = iDay <10 ? "0"+iDay : iDay;
		
//		alert(user_no+"," + iSavingNo +"," + iUserFlag +"," + iSetFlag +"," + iFlag );
		
		
		$("#u_cardno").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		queryChangeDeviceData();

		if(iFlag == 1){
			var useDate = "20"+iYear+"-"+ iMonth + "-" + iDay;
			// 读卡成功,更新状态
			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
				type : "POST",async:false  ,
				data : {u_cardno : $("#u_cardno").val() , useDate : useDate }
			});
			
		}else{
//			alert("上次充值未刷卡至表中，请先刷卡至表中后再操作！");
		}
	} );
}


function queryChangeDeviceData(){
	var cm_type = $("#cm_type").val();
	var u_name = $("#u_name").val();
	var u_cardno = $("#u_cardno").val();
	var u_phone = $("#u_phone").val();

	$.ajax({url:"admin/device/changemeter/ajaxQueryUserChangeMeterList.do",
			type : "POST",
			data:{
				cm_type: cm_type,
				u_name :u_name,
				u_cardno :u_cardno,
				u_phone :u_phone
			},
		success:function(result){
			if(result.length == 0){
			  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
			}
			
		    var list = result ; // eval('(' + result + ')');
		    changeDevice.changeMeterList = list;
		    

			dataTables.fnClearTable();   //将数据清除  
			dataTables.fnAddData(list,true); 
	}});
}
function initData(){
	

	dataTables = $('#dataTable').dataTable({"columns": [
	    { "data": "cm_id" },
	    { "data": "user_name" },
	    { "data":  function(row, type, set, meta){
	    			if(type =='set') return;
	    			return dictionaryCache.getDescByBeanAttrValue("ChangeMeterBean","cm_type",row.cm_type)} },
	    { "data": "old_device_company" },
	    { "data": "new_device_company" },
	    { "data": "operator_name" },
	    { "data": "cm_happendate" },
	    { "data": "cm_remark" },
	    { "data": "cm_oldmeterno" },
	    { "data": "cm_oldmetercode" },
	    { "data": "cm_newmeterno" },
	    { "data": "cm_newmetercode" },
	    { "data": "user_address" }
	  ],
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
  		}
	
	});
}

initData();
