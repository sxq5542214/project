var userDataTables , chargeDataTables;
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
        readCardUserNo : -1,
        repeatCardUserList : [],
        userCard : {iCardKind:'未读卡',iSysCode:'未读卡',iUserNo:'未读卡',iSavingNo:'未读卡',iAmount:'未读卡',iTon1:'未读卡',iTon2:'未读卡',iPrice1:'未读卡',iPrice2:'未读卡',iPrice3:'未读卡',iFlag:'未读卡'},
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getChargeData: function(index){
	    	//如果选中的是最后一个，即“合计” 则不选中
	    	if(index == this.userChargeList.length -1 ){  
	    		return;
	    	}
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
			userManager.readCardUserNo = user_no ;
			$("#useDate").val("20"+iYear+"-"+ iMonth + "-" + iDay);
			// 读卡成功,更新状态
			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
				type : "POST",async:false  ,
				data : {u_cardno : $("#u_cardno").val() , useDate : $("#useDate").val() },
				success : function(data){
					// 失败的情况
					if( data.resultCode == -1 ){
						alert(data.resultDesc);
						$("#repeat_u_id").val('');
						
						userManager.repeatCardUserList = data.dataList;
						$('#repeatCardNoModalCenter').modal('show');
					}
				}
			});
			

//			$('#exampleModalCenter').modal('show');
			
		}else{
			alert("上次充值未刷卡至表中，请先刷卡至表中后再操作！");
//			$("#u_no").val('');
		}
	} );
}




function updateChargeDetailBrushFlagByRepeatCard(){
	var form = document.repeatCardNoForm;
	var u_id = $("#repeat_u_id").val();
	// 读卡成功,更新状态
	$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
		type : "POST",async:false  ,
		data : { u_id : u_id , useDate : $("#useDate").val() },
		success : function(data){
			// 失败的情况
			if( data.resultCode == -1 ){
				alert(data.resultDesc);
			}else{
				$('#repeatCardNoModalCenter').modal('hide');
			}
		}
	});
}
function clickRepeatRow(u_id,row){
	$("#repeat_u_id").val(u_id);
 	$("#repeatCardRow"+u_id).prop("checked",true);
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
	if(userManager.readCardUserNo != u_cardno ){
		alert('您可能未刷卡至表中，请先读卡查询再做操作！');
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
						userManager.readCardUserNo = -1 ; // 写卡成功后重置用户信息，避免再次写卡
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
//	alert(u_cardno +"," + u_id);
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

function resetChargeMoney(){
	$("#chargeMoney").val('');
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
		data:{	cd_id : cd_id	},
	success:function(printFileName){
		webapp_start(printFileName, cd_id , 'preview');
	}});
	
}

//应用URL协议启动WEB报表客户端程序，根据参数 option 调用对应的功能   type:print/pdf/xls/preview/rtf/csv/txt/img/grd
function webapp_start(printFileName, cd_id, type) {
    var args = {
        type: type
    };

    if (cd_id) {
//    	   调用grid++客户端打印 ， 但是有水印
//        args.report = "assets/print/grf/" + report + ".grf";
//        args.data = "admin/chargeDetail/ajaxQueryChargeDetailByPrint.do?cdid=" + data;
//        webapp_ws_ajax_run(args);
    	
    	//获取上下文路径
    	var webroot=document.location.href;
    	var scheme = webroot.substring(0,webroot.indexOf('//')+2);
    	var domain = webroot.substring(webroot.indexOf('//')+2,webroot.length);
    	var serverNameStr = domain.substring(domain.indexOf('/')+1,domain.length);
    	var domain = domain.substring(0,domain.indexOf('/')+1);
    	var serverName =serverNameStr.substring(0,serverNameStr.indexOf('/')+1);
    	var path = scheme + domain + serverName
//    	  alert(path);
    	
    	// 调用Gird++ c/s插件直接打印  
    	var printBill = {"printBill":{ 
    						dataUrl : path +"admin/chargeDetail/ajaxQueryChargeDetailByPrint.do?cdid=" + cd_id ,
    						grfFileDownloadPath : path  + printFileName ,
    						type: type
    							}};
    	callWindowsClientMethod('printBill',printBill , null);
        
//    	$.ajax({url:"chargeDetail/ajaxUpdateChargeDetailPrintSuccess.do",
//    		data:{	cd_id : cd_id	}
//    		});
        
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
	    

	    userDataTables.fnClearTable();   //将数据清除  
	    if(list.length >0){
    		userDataTables.fnAddData(list,true); 
	    }
       		userManager.userChargeList = [];  //用户列表查询时，清除充值记录列表
       		
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
	    	userManager.getUserData(0);
	    	queryUserChargeData();
		}else if(result.length >1 && u_cardno != ''){
			alert('用户卡号有重复，请确认用户姓名再充值！');
			
		}
			
//		查询用户后自动加载充值记录
//		if(result.length > 0){
//			setTimeout(function() { // 直接调用选中不行，界面上的列表未加载，找不到对应的id dom对象
//				// 如果就默认调用第一条的点击事件
//				userManager.getUserData(0);
//				queryUserChargeData();
//	    	}, 100);
//		}
		
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
		    
		    //计算合计
		    var sumObj = new Object();
		    sumObj.user_cardno = '合计';
		    sumObj.cd_paidmoney = 0;
		    sumObj.cd_chargemoney = 0;
		    sumObj.cd_chargeamount = 0;
		    sumObj.cd_othermoney1 = 0;
		    sumObj.cd_othermoney2 = 0;
		    for(var i = 0 ; i < list.length ; i ++){
		    	sumObj.cd_paidmoney = (Number(sumObj.cd_paidmoney) + Number(list[i].cd_paidmoney)).toFixed(2);
		    	sumObj.cd_chargemoney = (Number(sumObj.cd_chargemoney) +  Number(list[i].cd_chargemoney)).toFixed(2);
		    	sumObj.cd_chargeamount = (Number(sumObj.cd_chargeamount) +  Number(list[i].cd_chargeamount)).toFixed(2);
		    	sumObj.cd_othermoney1 = (Number(sumObj.cd_othermoney1) +  Number(list[i].cd_othermoney1)).toFixed(2);
		    	sumObj.cd_othermoney2 = (Number(sumObj.cd_othermoney2) +  Number(list[i].cd_othermoney2)).toFixed(2);
		    }
		    list.push(sumObj);
		    userManager.userChargeList = list;
		    
		    
		    if(list.length > 0 ){ //默认调用第一条数据的点击事件
		    	setTimeout(function() {
			    	userManager.getChargeData(0);
		    	}, 500);
		    }
		    
		}});
	
}



function initData(){
	
	userDataTables = $('#userDataTable').dataTable({"columns": [
		{ "data": "u_id" ,render : function(data,type,row,meta){
	    	return '<input type="radio" id="userRadio'+meta.row +'" name="u_id" value="'+ meta.row +'" onclick="userManager.getUserData('+ meta.row+')" >'+ ( Number(meta.row) + 1)  ;} },
	    { "data": "u_name" },
	    { "data": "priceName" },
	    { "data": "device_company" },
	    { "data": "u_phone" }, 
	    { "data":  function(row, type, set, meta){
			if(type =='set') return;
			return row.addressName + row.u_address} }
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
		  		},
  		},
  		"ordering": false, //排序功能
  		"searching": false,//本地搜索
  		"lengthChange": false,//是否允许用户自定义显示数量
        "bFilter": false //列筛序功能
	});

	$('#userDataTable').on("click","tr",function(e){
		if(e.target.toString().indexOf("Input") > 0){
			//点击的是 单选按钮 ， 放在input的 onclick中了
//			userManager.getUserData(e.target.value);

		}else{
			userManager.getUserData(e.target.parentNode.childNodes[0].childNodes[0].value);
		}
	});
	
	
	
	
	
	//默认打开根节点
	$("#tree").on("ready.jstree", function (e, data) {
//		alert(data.instance.get_node(6));
		var id = e.target.firstChild.firstChild.id ; // 获取根节点
	data.instance.open_node(id);//打开根节点
	});
	$('#tree').on('changed.jstree', function (e, data) {
		// 树形列表点击事件
	    var i, j, r ;
//	    for(i = 0, j = data.selected.length; i < j; i++) {   如果多选，则需要循环
//	      r= data.instance.get_node(data.selected[i]);
//	    }
	    r = data.instance.get_node(data.selected[0]);
	    r = r.original;
	//  alert(r.id+","+ r.text+","+ r.level +","+ r.parent +"," + r.updateDate );
	    var addressId = r.id ;
		$("#u_cardno").val('');
		$("#u_id").val('');
	    
	    queryUserData(addressId); //查询用户
	    
//		$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
//				data:{
//					addressId : addressId
//				},
//			success:function(result){
//				if(result.length == 0){
//				  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
//				}
//			    var list = result ; 
//			    userManager.userList = list;
//			}});
	    
	  }).jstree({
		  //树形列表加载参数
		'core' : { 	'data': { 'url': 'admin/area/ajaxQueryAddressByParent.do' },
					'themes': {
			            'name': 'proton',
			            'responsive': true
			        }
				}
	});
}



initData();


