var userManager =  new Vue({
    el: "#userManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : '',
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
        priceList : [] ,
        deviceKindList : [],
        buildingId : '',
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
	},
    methods:{
	    getData: function(index){
//	    	alert(this.userList[index].p_name );
	    	this.choseIndex = index;
	    	
	    	var form = document.updateForm;
			form.u_id.value = this.userList[index].u_id ;
			form.u_name.value = this.userList[index].u_name ;
			form.u_phone.value = this.userList[index].u_phone ;
			form.u_balance.value = this.userList[index].u_balance ;
			form.u_status.value = this.userList[index].u_status ;
			form.u_priceid.value = this.userList[index].u_priceid ;
			form.u_address.value = this.userList[index].u_address ;
			form.u_paperwork.value = this.userList[index].u_paperwork ;
			form.u_peoplesize.value = this.userList[index].u_peoplesize ;
			form.u_remark.value = this.userList[index].u_remark ;
			form.u_materialfee.value = this.userList[index].u_materialfee ;
			form.u_constructioncost.value = this.userList[index].u_constructioncost ;
			form.u_prepayment.value = this.userList[index].u_prepayment ;
			form.addressId.value = this.userList[index].addressId ;
			form.device_company.value = this.userList[index].device_company ;
			
			 $('#addressBTN2').text(this.userList[index].addressName );
//			$("#di_dkid").val();
			
//			form.u_group.value = this.userList[index].u_group +1;
	    	$("#selectGroup").val(this.userList[index].u_group +1);
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdateUser(){
	var form = document.updateForm;
	var chargeMoney = form.chargeMoney.value;
	if(chargeMoney == '' ){
		alert('请输入开户充值的金额！');
		return;
	}
	if(form.addressId.value == ''){
		alert('请选择地址');
		return false;
	}
	if(form.u_name.value == ''){
		alert('请输入用户姓名');
		return false;
	}
	if(form.u_phone.value == ''){
		alert('请输入用户号码');
		return false;
	}
	if(form.di_dkid.value == ''){
		alert('请选择表具类型');
		return false;
	}
	if(form.device_company.value == ''){
		alert('请选择水表厂商');
		return false;
	}
	$.ajax({url:"admin/user/ajaxAddOrUpdateUser.do",
		type : "POST",
		data:{
			u_id: form.u_id.value,
			u_name: form.u_name.value,
			u_phone : form.u_phone.value,
			u_balance: form.u_balance.value,
			u_status: form.u_status.value,
			u_priceid: form.u_priceid.value,
			u_address: form.u_address.value,
			u_paperwork: form.u_paperwork.value,
			u_peoplesize: form.u_peoplesize.value,
			u_group: form.u_group.value,
			u_remark: form.u_remark.value,
			u_materialfee: form.u_materialfee.value,
			u_constructioncost: form.u_constructioncost.value,
			u_prepayment: form.u_prepayment.value,
//			u_buildingid : userManager.buildingId , 
			addressId :  form.addressId.value
		},
		success:function(result){
		    if(result > 0){
//		    	alert(result);
		    	if(document.updateForm.u_id.value == ''){	//没有ID，是新增场景
			    	$.NotificationApp.send("请注意","已完成用户新增，正在写卡开户，请稍等！","top-center","rgba(0,0,0,0.2)","info");
			    	document.updateForm.u_id.value = result ;
			    	openAccount();
			    	document.updateForm.u_id.value = ''; //开户后，将用户ID重置
		    	}else{
		    		alert('修改用户成功');
		    	}
		    	
		    }
		    
		    queryUserData();
		    $('#exampleModalCenter').modal('hide');
		}});
}

function deleteUser(){
	var uid = document.updateForm.u_id.value;
	if( uid == ''){
		alert("请先选择要删除的用户！");
		return false;
	}else{
		
		if(confirm("确定删除该用户？")){
			$.ajax({url:"admin/user/ajaxDeleteUser.do",
				type : "POST", async: false,
				data:{
					u_id: uid
				},
				success:function(result){
				    if(result > 0){
				    	alert('删除用户成功');
				    	queryUserData();
				    }else{
				    	alert('删除失败！');
				    }
				}});
		}
	}
	
	
}
function addUser(){
	 document.updateForm.u_id.value = '';
	 document.updateForm.u_name.value = '';
	 document.updateForm.u_paperwork.value = '';
	 document.updateForm.u_balance.value = '0';
	 document.updateForm.u_peoplesize.value = '0';
	 document.updateForm.u_phone.value = '';
	 document.updateForm.u_address.value = '';
	 document.updateForm.addressId.value = '';
	 document.updateForm.di_dkid.disabled = false ;
	 document.updateForm.chargeMoney.disabled = false ;
	 
	 $('#addressBTN2').text('选择地址');
}
function updateUser(){
	if(document.updateForm.u_id.value == ''){
		alert("请先选择要修改的用户！");
		return false;
	}else{
		 document.updateForm.di_dkid.disabled = true ;
		 document.updateForm.chargeMoney.disabled = true ;
		 
		 
//		var addrId = document.updateForm.addressId ;
		//查询用户地址数据并更新
		var index = userManager.choseIndex ;
		document.updateForm.addressId.value = userManager.userList[index].addressId ;
		$('#addressBTN2').text(userManager.userList[index].addressName );
		
		$('#exampleModalCenter').modal('show');
	}
	
}
function openAccount(){

	var form = document.updateForm;
	var dk_id = form.di_dkid.value;
	var uid = form.u_id.value;
	var chargeMoney = form.chargeMoney.value;
	var device_company = form.device_company.value;
	if(chargeMoney == '' ){
		alert('请输入开户充值的金额！');
		return;
	}
	if(uid == ''){
		alert('请确定需要开户的用户');
		return ;
	}
	if(dk_id == ''){
		alert('请确定需要开户的表具类型');
		return ;
	}
	
	
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
		alert('该卡不是新卡，不能用于创建新用户写卡！  用户已创建，请于【营业管理】【用户开户】菜单中继续开户操作！');
		return ;
	}
	
	$.ajax({
		url:"admin/card/ajaxOpenAccountCard.do",
		type : "POST",
		async: false,
		data :{ deviceKindId : dk_id ,userId : uid ,chargeMoney:chargeMoney , device_company : device_company},
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
						
//						queryUserData();
//						$('#exampleModalCenter').modal('hide');
					}
				} );
			}
			
//		    var list = eval('(' + result + ')');
		}});
}

function queryUserData(){
	var u_phone = $("#u_phone").val();
	var u_name = $("#u_name").val();
	var u_paperwork = $("#u_paperwork").val();
	var u_buildingid = $("#u_buildingid").val();
	var u_areaid = $("#u_areaid").val();
	var addressId = $("#addressId").val();
	var u_cardno = $("#u_cardno").val();
	
	if(u_phone == '' && u_name == '' && addressId == '' && u_paperwork == '' && u_cardno == ''){
		alert('请输入查询条件');
		return false;
	}
	
	$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
			type : "POST",
			data:{
				u_phone: u_phone,
				u_name :u_name,
				u_paperwork :u_paperwork,
				u_buildingid : u_buildingid,
				u_areaid : u_areaid,
				addressId : addressId,
				u_cardno : u_cardno
			},
		success:function(result){
			if(result.length == 0){
			  	$.NotificationApp.send("请注意","已完成查询，但没有数据！","top-center","rgba(0,0,0,0.2)","error");
			}
		    var list = result ; // eval('(' + result + ')');
		    userManager.userList = list;
		    $('#queryUserModalCenter').modal('hide');
		    
		}});
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
		
// alert(user_no+"," + iSavingNo +"," + iUserFlag +"," + iSetFlag +"," + iFlag );
		
		if(user_no == 0 ){
			alert('该卡无用户数据，请检查！');
		}else{
			
			$("#u_cardno").val(user_no);
			$("#u_phone").val('');
			$("#u_name").val('');
			$("#u_paperwork").val('');
			var addressId = $("#addressId").val();
			$("#addressId").val('');
			
			
			queryUserData();
		    $('#queryUserModalCenter').modal('hide');

			$("#addressId").val(addressId);
		}

	} );
}

function initData(){
	

	//初始化查询价格列表
	$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do",
		type : "POST",
		data:{
			p_enabled : 1
		},
		async : false,
		success:function(result){
			
		    var list =   result ;  //eval('(' + result + ')');
		    userManager.priceList = list;
		    
	}});
	// alert(userManager.priceList.length);

	//初始化查询表具类型列表
	$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
		type : "POST",
		success:function(result){
		    var list = result ; // eval('(' + result + ')');
		    userManager.deviceKindList = list;
	}});

	
	
	//默认打开根节点
	$("#tree").on("ready.jstree", function (e, data) {
//		alert(data.instance.get_node(6));
		var id = e.target.firstChild.firstChild.id ; // 获取根节点
		data.instance.open_node(id);//打开根节点
	});
	//初始化地址列表的弹框数据
	$('#tree').on('changed.jstree', function (e, data) {
		// 树形列表点击事件
	    var i, j, r ;
//	    for(i = 0, j = data.selected.length; i < j; i++) {   如果多选，则需要循环
//	      r= data.instance.get_node(data.selected[i]);
//	    }
	    r = data.instance.get_node(data.selected[0]);
	    r = r.original;
	//  alert(r.id+","+ r.text+","+ r.level +","+ r.parent +"," + r.updateDate );
	    var parent_id = data.instance.get_parent(data.selected[0]) ;
	    var parent_name = data.instance.get_node(parent_id).text;
	    $('#addressId').val(r.id);
	    $('#addressBTN2').text(r.text);
	    document.updateForm.u_address.value = r.full_name;
	    
	    var addressId = r.id ;
		$("#u_cardno").val('');
		$("#u_id").val('');
		$("#addressId").val(addressId);
	    queryUserData(); //查询用户
	    
	    
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
