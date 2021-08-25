var datas = [
        {
            code: "A2017-001",
            name: "3800充电器",
            states: "正常",
            date: "2017-01-21",
            admin: "andy"
        },
        {
            code: "A2017-002",
            name: "Lenovo Type-c转接器",
            states: "正常",
            date: "2017-01-21",
            admin: "zero"
        }];


var userManager =  new Vue({
    el: "#userManagerDiv",
//	el:"#selectDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : '-1',
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
        areaList : [],
        priceList : [] ,
        buildingId : '',
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getData: function(index){
//	    	alert(this.userList[index].p_name );
	    	this.choseIndex = index;
	    	
//	    	var form = document.updateForm;
//			form.u_id.value = this.userList[index].u_id ;
//			form.u_name.value = this.userList[index].u_name ;
//			form.u_phone.value = this.userList[index].u_phone ;
//			form.u_balance.value = this.userList[index].u_balance ;
//			form.u_status.value = this.userList[index].u_status ;
//			form.u_priceid.value = this.userList[index].u_priceid ;
//			form.u_address.value = this.userList[index].u_address ;
//			form.u_paperwork.value = this.userList[index].u_paperwork ;
//			form.u_peoplesize.value = this.userList[index].u_peoplesize ;
//			form.u_remark.value = this.userList[index].u_remark ;
//			form.u_materialfee.value = this.userList[index].u_materialfee ;
//			form.u_constructioncost.value = this.userList[index].u_constructioncost ;
//			form.u_prepayment.value = this.userList[index].u_prepayment ;
//			form.u_group.value = this.userList[index].u_group +1;
//	    	$("#selectGroup").val(this.userList[index].u_group +1);
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function readCardAndQueryUser(){

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
		
		alert(user_no+"," + iSavingNo +"," + iUserFlag +"," + iSetFlag +"," + iFlag );
		
		
//		$("#u_cardno").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		$("#u_paperwork").val('');
		queryUserData(user_no);

	} );
}
function modifyAddress(uid){
	if($("#u_address"+uid).prop("disabled") == true){
		alert("请选择右侧要变更为的地址");
		
	}else{
		var u_buildingid = $("#u_buildingid"+uid).val();
		var u_address = $("#u_address"+uid).val();
		

		$.ajax({url:"admin/user/ajaxAddOrUpdateUser.do",
			type : "POST",
			data:{
				u_id: uid,
				u_address: u_address,
				u_buildingid : u_buildingid
			},
			success:function(result){
			    if(result > 0){
			    	alert('操作成功！');
			    }
			}});
		
	}
	 
}

function queryUserData(u_cardno){
	var u_phone = $("#u_phone").val();
	var u_name = $("#u_name").val();
	var u_paperwork = $("#u_paperwork").val();
	var u_buildingid = $("#u_buildingid").val();
	var u_cardno = u_cardno == -1 ? '':u_cardno;
	
	$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
			type : "POST",
			data:{
				u_phone: u_phone,
				u_name :u_name,
				u_paperwork :u_paperwork,
				u_buildingid : u_buildingid,
				u_cardno : u_cardno
			},
		success:function(result){
		    var list = eval('(' + result + ')');
		    userManager.userList = list;
		}});
}
function updateUserData(level,id,name){
	
	if(level == 3){
		
//		$("#u_address"+userManager.userList[userManager.choseIndex].u_id).prop("disabled",false);
		var index = userManager.choseIndex; 
		if(index == '-1'){
			alert("请先查询并在左侧选择用户！");
			return;
		}
		var addressInput = $("#u_address"+userManager.userList[index].u_id);
		addressInput.prop("disabled",false);

		$.ajax({url:"admin/area/ajaxQueryBuildingById.do",
			type : "POST",
			data:{		id: id	},
			success:function(result){
			    var json = eval('(' + result + ')');
			    
			    for(var i = 0 ; i <userManager.areaList.length;i++ ){
			    	if(userManager.areaList[i].a_id == json.b_areaid){
			    		addressInput.val(userManager.areaList[i].a_name+name);
			    		
						$("#button"+userManager.userList[index].u_id).attr("class","btn btn-success");
						$("#button"+userManager.userList[index].u_id).val("确认修改");
						$("#u_buildingid"+userManager.userList[index].u_id).val(id);
			    		break;
			    	}
			    }
			}});
		
		
		
	}else if(level == 2){
		
	}else{
		
	}
}

function getTree() {

	var dataArray = new Array();
	$.ajax({
		url:"admin/area/ajaxQueryAreaTreeByOperator.do",
		async:false,
		success:function(result){
		    var company = eval('(' + result + ')');
		    dataArray = company;
	}});

	$.ajax({url:"admin/area/ajaxQueryAreaByCompany.do",
		type : "POST",
		data:{			},
		success:function(result){
	    var list = eval('(' + result + ')');
	    userManager.areaList = list;
	}});
  return dataArray;
}
$('#tree').bstreeview({
	data: getTree(),
	openNodeLinkOnNewTab: false
});


