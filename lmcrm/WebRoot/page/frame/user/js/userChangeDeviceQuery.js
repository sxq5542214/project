
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
		
		
		$("#u_no").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		queryChangeDeviceData();

		if(iFlag == 1){
			var useDate = "20"+iYear+"-"+ iMonth + "-" + iDay;
			// 读卡成功,更新状态
			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
				type : "POST",async:false  ,
				data : {u_no : $("#u_no").val() , useDate : useDate }
			});
			
		}else{
//			alert("上次充值未刷卡至表中，请先刷卡至表中后再操作！");
		}
	} );
}


function queryChangeDeviceData(){
	var cm_type = $("#cm_type").val();
	var u_name = $("#u_name").val();
	var u_no = $("#u_no").val();
	var u_phone = $("#u_phone").val();

	$.ajax({url:"admin/device/changemeter/ajaxQueryUserChangeMeterList.do",
			type : "POST",
			data:{
				cm_type: cm_type,
				u_name :u_name,
				u_no :u_no,
				u_phone :u_phone
			},
		success:function(result){
		    var list = eval('(' + result + ')');
		    changeDevice.changeMeterList = list;
	}});
}
