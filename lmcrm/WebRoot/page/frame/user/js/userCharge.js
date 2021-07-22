var userManager =  new Vue({
    el: "#userManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
        priceList : [] ,
        deviceKindList : [],
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getData: function(index){
	    	this.choseIndex = index;
	    	var form = document.updateForm;
//			form.u_name.value = this.userList[index].u_name ;
//			form.u_phone.value = this.userList[index].u_phone ;
//			form.u_id.value = this.userList[index].u_id ;
	    	
			$("#radio"+index).prop('checked',true);
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
		
		
		$("#u_no").val(user_no);
		$("#u_phone").val('');
		$("#u_name").val('');
		$("#u_paperwork").val('');
		queryUserData();

		if(iFlag == 1){
			$("#useDate").val("20"+iYear+"-"+ iMonth + "-" + iDay);
			// 读卡成功,更新状态
			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
				type : "POST",async:false  ,
				data : {u_no : $("#u_no").val() , useDate : $("#useDate").val() }
			});
			

			$('#exampleModalCenter').modal('show');
			
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
		$("#u_paperwork").val('');
		queryUserData();

		if(iFlag == 1){
			alert("上次充值已刷卡至表中，无法进行修改！");
		}else{

//			$("#useDate").val("20"+iYear+"-"+ iMonth + "-" + iDay);
//			// 读卡成功,更新状态
//			$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailBrushFlagToSuccess.do",
//				type : "POST",async:false  ,
//				data : {u_no : $("#u_no").val() , useDate : $("#useDate").val() }
//			});

			var iAmount = json.stru_userparm.iAmount;

			$("#lastCharge").val((iAmount/10).toFixed(2));
			$('#updateChargeModalCenter').modal('show');
			
		}
	} );
}

function writeCardByCharge(){
	
	var u_no = $("#u_no").val();
	
	if(u_no == ''){
		
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxChargeMoneyCard.do",
		type : "POST",async:false, 
		data :{ u_no :  u_no , chargeMoney : $("#chargeMoney").val() },
		success:function(result){
			var bean = eval('(' + result + ')');
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
					}
				} );
			}
		}
	
	});
}

// 修改充值记录
function writeCardByUpdateCharge(){
	
	var u_no = $("#u_no").val();
	
	if(u_no == ''){
		
		return ;
	}
	
	$.ajax({url:"admin/card/ajaxUpdateLastChargeMoneyCard.do",
		type : "POST",async:false, 
		data :{ u_no :  u_no , 
			updateChargeMoney : $("#updateChargeMoney").val() },
		success:function(result){
			var bean = eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('chargeMoney',result , function(status){
					if(status == 1){
						// 写卡成功,更新写卡状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeInfo.do",
							type : "POST",async:false, 
							data :{ cdid : cdid  },
							success:function(result){}
						});
						
						alert("充值写卡成功！");
						$('#updateChargeModalCenter').modal('hide');
					}
				} );
			}
		}
	
	});
}

function queryUserData(){

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
	var u_no = $("#u_no").val();
	
	if(u_phone == '' && u_name == '' && u_paperwork =='' && u_no ==''){
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
				u_no : u_no,
				u_areaid : u_areaid
			},
		success:function(result){
		    var list = eval('(' + result + ')');
		    userManager.userList = list;
		}});
	
}

// queryUserData();


//$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do",
//	type : "POST",
//	data:{
//		p_enabled : 1,
//	},
//	async : false,
//	success:function(result){
//	    var list = eval('(' + result + ')');
//	    userManager.priceList = list;
//}});
