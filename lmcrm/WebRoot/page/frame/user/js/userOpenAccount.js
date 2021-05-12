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
			form.u_name.value = this.userList[index].u_name ;
			form.u_phone.value = this.userList[index].u_phone ;
			form.u_id.value = this.userList[index].u_id ;
	    	
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

	var form = document.updateForm;
	var dk_id = form.di_dkid.value;
	var uid = form.u_id.value;
	var chargeMoney = form.chargeMoney.value;
	if(chargeMoney == ''){
		alert('请输入开户充值的金额！');
		return;
	}
	$.ajax({
		url:"admin/card/ajaxOpenAccountCard.do",
		type : "POST",
		data :{ deviceKindId : dk_id ,userId : uid ,chargeMoney:chargeMoney},
		success:function(result){
//			alert(result);
		    var bean = eval('(' + result + ')');
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
		    var list = eval('(' + result + ')');
		    userManager.userList = list;
		}});
	
	
	$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
		type : "POST",
		success:function(result){
		    var list = eval('(' + result + ')');
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
