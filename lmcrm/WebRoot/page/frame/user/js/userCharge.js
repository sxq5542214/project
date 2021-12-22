var userManager =  new Vue({
    el: "#userManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseUserIndex : -1,
    	choseChargeIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
        userChargeList : [],
        priceList : [] ,
        deviceKindList : [],
        userCard : {iCardKind:'未读卡',iSysCode:'未读卡',iUserNo:'未读卡',iSavingNo:'未读卡',iAmount:'未读卡',iTon1:'未读卡',iTon2:'未读卡',iPrice1:'未读卡',iPrice2:'未读卡',iPrice3:'未读卡',iFlag:'未读卡'},
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getChargeData: function(index){
	    	$("#chargeRadio"+index).prop("checked",true);
	    	this.choseChargeIndex = index;
	    	var form = document.updateForm;
//			form.u_name.value = this.userList[index].u_name ;
	    	
	    	// 点击某条记录再启用菜单
	    	var btnlist = $("button[name='button_makeReceipt']");
	    	btnlist.attr("disabled",false);
	    	
	    },
	    getUserData: function(index){
	    	$("#userRadio"+index).prop("checked",true);
	    	this.choseUserIndex = index;
//	    	var form = document.updateForm;
	    	
	    	var u_id = this.userList[index].u_id ;
	    	var cardno = this.userList[index].u_cardno ;

	    	$("#u_cardno").val(cardno);
	    	$("#u_id").val(u_id);
	    	
	    	//充值等按钮启用
	    	var btnlist = $("button[name^='button_']");
	    	btnlist.attr("disabled",false);
	    	// 补打票据仍然禁用
	    	$("button[name='button_makeReceipt']").attr("disabled",true);
	    	
			queryUserChargeData();
	    }
    }
})

function checkChose(){
	if(userManager.choseUserIndex == -1){
		alert('请选择需要开户的用户');
	}else{
		$('#exampleModalCenter').modal('show');
	}
}
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
		
//		alert(user_no+"," + iSavingNo +"," + iUserFlag +"," + iSetFlag +"," + iFlag );

		//加载卡中数据到界面上
		userManager.userCard.iCardKind = json.iCardKind == 1 ? '用户卡':'非用户卡';
		userManager.userCard.iSysCode = json.iSysCode;
		userManager.userCard.iUserNo = user_no ;
		userManager.userCard.iFlag = iFlag == 1 ? '已刷卡':'未刷卡';
		userManager.userCard.iAmount = (json.stru_userparm.iAmount / 10).toFixed(2);
		userManager.userCard.iSavingNo = iSavingNo ;
		userManager.userCard.iTon1 = json.stru_priceparm.iTon1;
		userManager.userCard.iTon2 = json.stru_priceparm.iTon2;
		userManager.userCard.iPrice1 = (json.stru_priceparm.iPrice1 / 100).toFixed(2);
		userManager.userCard.iPrice2 = (json.stru_priceparm.iPrice2 / 100).toFixed(2);
		userManager.userCard.iPrice3 = (json.stru_priceparm.iPrice3 / 100).toFixed(2);


		
		if(user_no == 0 ){
			alert("该卡没有用户数据，请重新查询！");
			clearUserData();
			return ;
		}
		
		$("#u_cardno").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		$("#u_paperwork").val('');
		$("#u_id").val('');
		queryUserData('');

		if(iFlag == 1){
			$("#useDate").val("20"+iYear+"-"+ iMonth + "-" + iDay);
			// 读卡成功,更新状态
			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
				type : "POST",async:false  ,
				data : {u_cardno : $("#u_cardno").val() , useDate : $("#useDate").val() }
			});
			

//			$('#exampleModalCenter').modal('show');
			
		}else{
			alert("上次充值未刷卡至表中，请先刷卡至表中后再操作！");
//			$("#u_no").val('');
		}
	} );
}

//充值修改
function readCardAndUpdateCharge(){
	var u_id = $("#u_id").val();
	var user = userManager.userList[userManager.choseUserIndex] ;
	if(u_id == ""){
		alert("请先选择用户！");
		return ;
	}
	
	//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
	callWindowsClientMethod('readCard','{}' , function(result){
		if(result < 1){
			alert("操作卡失败，无法获取数据");
			return ;
		}
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
		
		

		if(user_no == 0 ){
			alert("该卡没有用户数据，请重新查询！");
			return ;
		}
		if(user.u_cardno != user_no ){
			alert('该卡不属于当前用户的卡，请确认卡是否属于当前用户！');
			return ;
		}
		
//		$("#u_cardno").val(user_no);
//		$("#u_phone").val('');
//		$("#u_name").val('');
//		$("#u_paperwork").val('');
////		$("#u_id").val('');    充值修改时，需要保证读卡的卡号与用户ID一致
//		queryUserData('');

		if(iFlag == 1){
			alert("上次充值已刷卡至表中，无法进行修改！");
		}else{


			var iAmount = json.stru_userparm.iAmount;

			$("#lastCharge").val((iAmount/10).toFixed(2));
			$('#updateChargeModalCenter').modal('show');
			
		}
	} );
}

//用户补卡
function readCardAndRepairCard(){

	var u_id = $("#u_id").val();
	if(u_id == ''){
		alert("请先查询并选择用户！");
		return ;
	}
	
	
	//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
	callWindowsClientMethod('readCard','{}' , function(result){
		if(result < 1){
			alert("操作卡失败，无法获取数据");
			return ;
		}
//		alert(result);
		var json = eval('(' + result + ')');
		var iCardKind = json.iCardKind;
		if(iCardKind != 0){
			alert("该卡不是新卡，不能进行补卡！");
			return ;
		}else{
			var len = userManager.userChargeList.length ;
			var lastChargeMoney = 0;
			if(len > 0){
				lastChargeMoney = userManager.userChargeList[0].cd_chargemoney;
			}else{
				alert('请注意，该用户没有充值记录！');
			}
			
			$("#repairCardMoney").val(lastChargeMoney);
			$('#repairCardModalCenter').modal('show');
			
		}
	} );
}


//用户换表
function readCardAndChangeMeter(){
//
//	//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
//	callWindowsClientMethod('readCard','{}' , function(result){
//		if(result < 1){
//			alert("操作卡失败，无法获取数据");
//			return ;
//		}
////		alert(result);
//		var json = eval('(' + result + ')');
//		var user_no = json.stru_userparm.iUserNo;
//		var iSavingNo = json.stru_userparm.iSavingNo;
//		var iUserFlag = json.stru_userparm.stru_retparm.iUserFlag;
//		var iSetFlag = json.stru_userparm.stru_retparm.iSetFlag;
//		var iFlag = json.stru_userparm.stru_retparm.iFlag;
//		var iYear = json.stru_userparm.stru_retparm.iYear;
//		var iMonth = json.stru_userparm.stru_retparm.iMonth;
//		var iDay = json.stru_userparm.stru_retparm.iDay;
//		var iMonth = iMonth <10 ? "0"+iMonth : iMonth;
//		var iDay = iDay <10 ? "0"+iDay : iDay;
//		
////		alert(user_no+"," + iSavingNo +"," + iUserFlag +"," + iSetFlag +"," + iFlag );
//		
//		if(user_no == 0 ){
//			alert("该卡没有用户数据，请重新查询！");
//			return ;
//		}
//		$("#u_cardno").val(user_no);
//		$("#u_phone").val('');
//		$("#u_name").val('');
//		$("#u_paperwork").val('');
//		$("#u_id").val('');
//		queryUserData('');

		$('#changeMeterModalCenter').modal('show');
		
		
		// 获取 设备列表
		$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
			type : "POST",
			success:function(result){
			    var list = result ; // eval('(' + result + ')');
			    userManager.deviceKindList = list;
		}});
		
		
//	} );
}



function writeCardByCharge(){

	var u_cardno = $("#u_cardno").val();
	var u_id = $("#u_id").val();
	
	if(u_cardno == '' ||  u_id == ''){
		alert("请先选择用户再充值");
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxChargeMoneyCard.do",
		type : "POST",async:false, 
		data :{ u_cardno :  u_cardno , u_id:u_id, chargeMoney : $("#chargeMoney").val() },
		success:function(result){
			var bean = result ; // eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('chargeMoney',result , function(status){
					if(status == 1){
						// 写卡成功,更新写卡状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do",
							type : "POST",async:false, data :{ cdid : cdid }});
						
						if(confirm('充值写卡成功！ 是否打印票据？')){
							printBill(cdid);
						}
						
						$('#exampleModalCenter').modal('hide');
						
						queryUserChargeData();
					}
				} );
			}
		}
	
	});
}

// 修改充值记录
function writeCardByUpdateCharge(){

	var u_cardno = $("#u_cardno").val();
	var u_id = $("#u_id").val();
	
	if(u_cardno == '' ||  u_id == ''){
		alert("请先选择用户再充值");
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxUpdateLastChargeMoneyCard.do",
		type : "POST",async:false, 
		data :{ u_cardno :  u_cardno , u_id : u_id ,
			updateChargeMoney : $("#updateChargeMoney").val() },
		success:function(result){
			var bean = result ; // eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('chargeMoney',result , function(status){
					if(status == 1){
						// 写卡成功,更新写卡状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do",
							type : "POST",async:false, 
							data :{ cdid : cdid  },
							success:function(result){}
						});
						
						alert("充值写卡成功！");
						$('#updateChargeModalCenter').modal('hide');
						queryUserChargeData();
					}
				} );
			}
		}
	
	});
}

function writeCardByRepairCard(){


	var u_cardno = $("#u_cardno").val();
	var u_id = $("#u_id").val();
	
	if(u_cardno == '' ||  u_id == ''){
		alert("请先选择用户再充值");
		return ;
	}
	var money = $("#repairCardMoney").val();
	if(money == ''){
		alert("请输入充值金额！");
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxRepairCard.do",
		type : "POST",async:false, 
		data :{ u_cardno :  u_cardno ,u_id : u_id, brushFlag :  $('input[name="brushFlag"]:checked').val() , 
			repairCardMoney : $("#repairCardMoney").val() },
		success:function(result){
			var bean = result ; // eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('chargeMoney',result , function(status){
					if(status == 1){
						// 写卡成功,更新写卡状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do",
							type : "POST",async:false, 
							data :{ cdid : cdid  },
							success:function(result){}
						});
						
						alert("充值写卡成功！");
						$('#repairCardModalCenter').modal('hide');
						
						queryUserChargeData();
					}
				} );
			}
		}
	
	});
}


function writeCardByChangeMeter(){

	var u_cardno = $("#u_cardno").val();
	var u_id = $("#u_id").val();
	var device_company = $("#device_company").val();
	alert(u_cardno +"," + u_id);
	if(u_cardno == '' || u_cardno == '0' || u_cardno == 0 ||  u_id == ''){
		alert("请先选择用户再充值");
		return ;
	}
	if(device_company == ""){
		alert('请选择新水表厂商！');
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxChangeMeterCard.do",
		type : "POST",async:false, 
		data :{ u_cardno :  u_cardno ,  
				u_id : u_id,
				changeMeterMoney :  $('#changeMeterMoney').val() ,
				cm_oldmetercode : $("#cm_oldmetercode").val() ,
				cm_type : $("#cm_type").val() ,
				cm_remark : $("#cm_remark").val() ,
				cm_newmetercode : $("#cm_newmetercode").val() ,
				cm_newmeterno : $("#cm_newmeterno").val() ,
				device_kind : $("#device_kind").val() ,
				device_company : $("#device_company").val()
		},
		success:function(result){
			var bean = result; // eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('chargeMoney',result , function(status){
					if(status == 1){
						// 写卡成功,更新写卡状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do",
							type : "POST",async:false, 
							data :{ cdid : cdid  },
							success:function(result){}
						});
						
						alert("换表维护写卡成功！");
						$('#changeMeterModalCenter').modal('hide');
						
						queryUserChargeData();
					}
				} );
			}
		}
	
	});
}

function printBill(cd_id){

	var u_id = $("#u_id").val();
	var user = userManager.userList[userManager.choseUserIndex] ;
	if(u_id == ""){
		alert("请先选择用户！");
		return ;
	}

	if(cd_id == null || typeof(cd_id) == 'undefined'){

		if(userManager.choseChargeIndex == -1 ){
			alert("请先选择要打印的充值记录！");
			return ;
		}
		var charge = userManager.userChargeList[userManager.choseChargeIndex] ;
		cd_id = charge.cd_id
	}
	
//	alert(charge.cd_id);
	
	
	$.ajax({url:"admin/company/ajaxQueryCompanyPrintFileName.do",
		data:{		},
	success:function(companyNo){
		webapp_start(companyNo, cd_id , 'preview');
	}});
	
}

//应用URL协议启动WEB报表客户端程序，根据参数 option 调用对应的功能   type:print/pdf/xls/preview/rtf/csv/txt/img/grd
function webapp_start(report, data, type) {
    var args = {
        type: type
    };

    if (data) {
        args.report = "assets/print/grf/" + report + ".grf";
        args.data = "admin/chargeDetail/ajaxQueryChargeDetailByPrint.do?cdid=" + data;

        webapp_ws_ajax_run(args);
    }
    else {
    	alert('打印没有数据！');
    	return;
//        args.report = "grf/" + report + ".grf";
//        args.data = "data/SQLParam.jsp";
//        args.baseurl = window.rootURL;
//        args.selfsql = true;
//
//        webapp_ws_run(args);
    }
}
// 清除用户数据
function clearUserData(){

	userManager.userList = [];
	userManager.userChargeList = [];
	userManager.choseUserIndex = -1;
	userManager.choseChargeIndex = -1;
}
function changeCMType(sel){
	var cm_type = $("#cm_type").val() ;
	var hide = "hide";
	if(cm_type == 4){
		$("#div_cm_newmetercode").show();
		$("#div_cm_newmeterno").show();
		$("#div_device_kind").show();
		
	}else{
		$("#div_cm_newmetercode").hide();
		$("#div_cm_newmeterno").hide();
		$("#div_device_kind").hide();
	}
	
	
}

function queryUserData(addressId){
	var u_phone = $("#u_phone").val();
	var u_name = $("#u_name").val();
	var u_paperwork = $("#u_paperwork").val();
	var u_cardno = $("#u_cardno").val();
	$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
		data:{
			addressId : addressId,
			u_phone: u_phone,
			u_name :u_name,
			u_paperwork :u_paperwork,
			u_cardno : u_cardno
		},
	success:function(result){

	    var list = result ; 
	    userManager.userList = list;
	    
		if(result.length == 0){
		  	$.NotificationApp.send("请注意","已完成用户查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
		  	
		  	//清除充值记录的展示及选中
		  	clearUserData();
		  	
	    	//充值等按钮禁用
	    	var btnlist = $("button[name^='button_']");
	    	btnlist.attr("disabled",true);
		  	
		}else if(result.length == 1){
//			alert(result[0].u_cardno);
	    	$("#u_cardno").val(result[0].u_cardno);
			
		}else if(result.length >1 && u_cardno != ''){
			alert('用户卡号不唯一，充值会导致问题，建议重新为用户开户！');
			
		}
			
		if(result.length > 0){
			setTimeout(function() { // 直接调用选中不行，界面上的列表未加载，找不到对应的id dom对象
				// 如果就默认调用第一条的点击事件
				userManager.getUserData(0);
				queryUserChargeData();
	    	}, 100);
		}
		
	}});
}

function queryUserChargeData(){

//	$.ajax({url:"admin/user/ajaxQueryAccountUser.do",
//			type : "POST",
//		success:function(result){
//		    var list = eval('(' + result + ')');
//		    userManager.userList = list;
//		}});
	

	var u_phone = $("#u_phone").val();
	var u_name = $("#u_name").val();
	var u_paperwork = $("#u_paperwork").val();
	var u_buildingid = $("#u_buildingid").val();
	var u_areaid = $("#u_areaid").val();
	var u_cardno = $("#u_cardno").val();
	var u_id = $("#u_id").val();
	
	if(u_phone == '' && u_name == '' && u_paperwork =='' && u_cardno ==''){
		alert('请先填写查询条件');
		return ;
	}
	
	
	$.ajax({url:"admin/price/chargeDetail/ajaxQueryChargeListByUser.do",
			type : "POST",
			data:{
				u_phone: u_phone,
				u_name :u_name,
				u_paperwork :u_paperwork,
				u_buildingid : u_buildingid,
				u_cardno : u_cardno,
				u_areaid : u_areaid,
				u_id : u_id
			},
		success:function(result){
			if(result.length == 0){
			  	$.NotificationApp.send("请注意","已完成充值记录查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
			}
		    var list = result ; // eval('(' + result + ')');
		    userManager.userChargeList = list;
		    
		    if(list.length > 0 ){ //默认调用第一条数据的点击事件
		    	setTimeout(function() {
			    	userManager.getChargeData(0);
		    	}, 500);
		    }
		    
		}});
	
}

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
	$("#u_cardno").val('');
	$("#u_id").val('');
    
    queryUserData(addressId); //查询用户
    
//	$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
//			data:{
//				addressId : addressId
//			},
//		success:function(result){
//			if(result.length == 0){
//			  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
//			}
//		    var list = result ; 
//		    userManager.userList = list;
//		}});
    
  }).jstree({
	  //树形列表加载参数
	'core' : { 	'data': { 'url': 'admin/area/ajaxQueryAddressByParent.do' },
				'themes': {
		            'name': 'proton',
		            'responsive': true
		        }
			}
});



