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
			form.u_id.value = this.userList[index].u_id ;
			form.u_name.value = this.userList[index].u_name ;
	    	
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
function openAccount(){


	var canwrite = true;
	//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
	callWindowsClientMethod('readCard','{}' , function(result){
		if(result < 1){
			alert("操作卡失败，无法获取数据");
			return ;
		}
//		alert(result);
		var json = eval('(' + result + ')');
		var cardkind = json.iCardKind ;
		var user_no = json.stru_userparm.iUserNo;
		
		if(cardkind != 0 || user_no != 0 ){
			canwrite = false;
		}
	} );
	
	if(!canwrite){
		alert('该卡不是新卡，不能用于创建新用户写卡！  请使用新卡操作');
		return ;
	}
	
	var form = document.updateForm;
	var dk_id = form.di_dkid.value;
	var uid = form.u_id.value;
	var chargeMoney = form.chargeMoney.value;
	var device_company = form.device_company.value;

	if(dk_id == ''){
		alert('请选择表具类型！');
		return;
	}
	if(chargeMoney == ''){
		alert('请输入开户充值的金额！');
		return;
	}
	if(device_company == ''){
		alert('请选择水表厂商！');
		return;
	}
	$.ajax({
		url:"admin/card/ajaxOpenAccountCard.do",
		type : "POST",
		data :{ deviceKindId : dk_id ,userId : uid ,chargeMoney:chargeMoney,device_company:device_company},
		success:function(result){
//			alert(result);
		    var bean = result ; //eval('(' + result + ')');
		    var cdid = bean.chargeDetailId ;
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
			}else{
				//调用C#客户端，并提供回调方法，回调入参为调用状态 1成功 -1失败
				callWindowsClientMethod('openAccount',result , function(status){
					if(status == 1){
						// 写卡成功,更新状态
						$.ajax({url:"admin/chargeDetail/ajaxUpdateChargeDetailStatusToSuccess.do",
							type : "POST",async:false, data :{ cdid : cdid }});
						
						queryUserData();
						$('#exampleModalCenter').modal('hide');
					}
				} );
			}
			
//		    var list = eval('(' + result + ')');
		}});
}

function queryUserData(){

	$.ajax({url:"admin/user/ajaxQueryUnOpenAccountUser.do",
			type : "POST",
		success:function(result){
//			alert(result);
			if(result.length == 0){
			  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
			}
		    var list = result ;
		    userManager.userList = list;
		}});
	
	
	$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
		type : "POST",
		success:function(result){
		    var list = result ; // eval('(' + result + ')');
		    userManager.deviceKindList = list;
	}});
	
	
}

 queryUserData();


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
