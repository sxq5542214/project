var userManager =  new Vue({
    el: "#userManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
        userChargeList : [],
        priceList : [] ,
        deviceKindList : [],
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getChargeData: function(index){
	    	this.choseIndex = index;
	    	var form = document.updateForm;
//			form.u_name.value = this.userList[index].u_name ;
//			form.u_phone.value = this.userList[index].u_phone ;
//			form.u_id.value = this.userList[index].u_id ;
	    	
	    	
//	    	var no = this.userChargeList[index].user_cardno ;
//	    	$("#u_cardno").val(no);
			$("#chargeRadio"+index).prop('checked',true);
	    },
	    getUserData: function(index){
	    	this.choseIndex = index;
//	    	var form = document.updateForm;
	    	
	    	
	    	var u_id = this.userList[index].u_id ;
	    	var cardno = this.userList[index].u_cardno ;

	    	$("#u_cardno").val(cardno);
	    	$("#u_id").val(u_id);
	    	
	    	//充值等按钮启用
	    	var btnlist = $("button[name^='button_']");
	    	btnlist.attr("disabled",false);
	    	
			queryUserChargeData();
			$("#userRadio"+index).prop('checked',true);
	    }
    }
})

function checkChose(){
	if(userManager.choseIndex == -1){
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

		if(user_no == 0 ){
			alert("该卡没有用户数据，请重新查询！");
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


function readCardAndUpdateCharge(){

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
		
		$("#u_cardno").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		$("#u_paperwork").val('');
		$("#u_id").val('');
		queryUserData('');

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

	var u_cardno = $("#u_cardno").val();
	if(u_cardno == ''){
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


			$('#repairCardModalCenter').modal('show');
			
		}
	} );
}


//用户换表
function readCardAndChangeMeter(){

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
		
		if(user_no == 0 ){
			alert("该卡没有用户数据，请重新查询！");
			return ;
		}
		$("#u_cardno").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		$("#u_paperwork").val('');
		$("#u_id").val('');
		queryUserData('');

		$('#changeMeterModalCenter').modal('show');
		
		
		// 获取 设备列表
		$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
			type : "POST",
			success:function(result){
			    var list = result ; // eval('(' + result + ')');
			    userManager.deviceKindList = list;
		}});
		
		
	} );
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
						
						alert("充值写卡成功！");
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
	
	if(u_cardno == '' || u_cardno == '0' || u_cardno == 0 ||  u_id == ''){
		alert("请先选择用户再充值");
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
						
						alert("充值写卡成功！");
						$('#changeMeterModalCenter').modal('hide');
						
						queryUserChargeData();
					}
				} );
			}
		}
	
	});
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
		if(result.length == 0){
		  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
		  	
	    	//充值等按钮禁用
	    	var btnlist = $("button[name^='button_']");
	    	btnlist.attr("disabled",true);
		  	
		}else if(result.length == 1){
//			alert(result[0].u_cardno);
	    	$("#u_cardno").val(result[0].u_cardno);
			queryUserChargeData();
		}else if(result.length >1 && u_cardno != ''){
			alert('用户卡号不唯一，充值会导致问题，请重新为用户开户！');
		}
	    var list = result ; 
	    userManager.userList = list;
	    
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
			  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
			}
		    var list = result ; // eval('(' + result + ')');
		    userManager.userChargeList = list;
		}});
	
}


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



