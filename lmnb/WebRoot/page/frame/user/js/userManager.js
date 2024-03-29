var dataTables;
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
//	    	$("#selectGroup").val(this.userList[index].u_group +1);
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdateUser(){
	var form = document.updateForm;
	var chargeMoney = form.chargeMoney.value;
	var isUpdate = form.u_id.value == '' ? false : true;
	
	if(chargeMoney == '' && !isUpdate){
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
	if(form.u_priceid.value == ''){
		alert('请选择价格类型');
		return false;
	}
	if(form.di_dkid.value == '' && !isUpdate){
		alert('请选择表具类型');
		return false;
	}
	if(form.device_company.value == '' && !isUpdate){
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
		    		queryUserData();
		    	}
		    	
		    }
		    
//		    queryUserData();
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

	 document.updateForm.di_dkid.value = '';
	 document.updateForm.device_company.value = '';
	 document.updateForm.u_priceid.value = '';
	 document.updateForm.u_status.value = '6';
	 
	 
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
		alert('该卡不是新卡，不能用于创建新用户写卡！  用户已创建，请于【营业管理】【补开户】菜单中继续开户操作！');
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
//						alert('该用户已开户成功，请刷表！');
						if(confirm('该用户已开户写卡成功！ 是否打印票据？')){
							printBill(cdid);
						}
						
//						queryUserData();
//						$('#exampleModalCenter').modal('hide');
					}else{
						alert('用户未开户成功，请在【营业管理】【补开户】菜单中继续开户操作！');
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
		    
		    
		    
		    dataTables.fnClearTable();   //将数据清除  
		    if(list.length >0){
       				dataTables.fnAddData(list,true); 
		    }
		    
		}});
}


function printBill(cd_id){

	if(cd_id == null || typeof(cd_id) == 'undefined'){
		alert("未获取到要打印的充值记录！");
		
	}
	
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
//  	   调用grid++客户端打印 ， 但是有水印
//      args.report = "assets/print/grf/" + report + ".grf";
//      args.data = "admin/chargeDetail/ajaxQueryChargeDetailByPrint.do?cdid=" + data;
//      webapp_ws_ajax_run(args);
  	
  	//获取上下文路径
  	var webroot=document.location.href;
  	var scheme = webroot.substring(0,webroot.indexOf('//')+2);
  	var domain = webroot.substring(webroot.indexOf('//')+2,webroot.length);
  	var serverNameStr = domain.substring(domain.indexOf('/')+1,domain.length);
  	var domain = domain.substring(0,domain.indexOf('/')+1);
  	var serverName =serverNameStr.substring(0,serverNameStr.indexOf('/')+1);
  	var path = scheme + domain + serverName
//  	  alert(path);
  	
  	// 调用Gird++ c/s插件直接打印  
  	var printBill = {"printBill":{ 
  						dataUrl : path +"admin/chargeDetail/ajaxQueryChargeDetailByPrint.do?cdid=" + cd_id ,
  						grfFileDownloadPath : path  + printFileName ,
  						type: type
  							}};
  	callWindowsClientMethod('printBill',printBill , null);
      
//  	$.ajax({url:"chargeDetail/ajaxUpdateChargeDetailPrintSuccess.do",
//  		data:{	cd_id : cd_id	}
//  		});
      
  }
  else {
  	alert('打印没有数据！');
  	return;
//      args.report = "grf/" + report + ".grf";
//      args.data = "data/SQLParam.jsp";
//      args.baseurl = window.rootURL;
//      args.selfsql = true;
//
//      webapp_ws_run(args);
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
	
	
	var addrName ;
//  弹窗选择地址的树	
	//默认打开根节点
		$("#modalTree").on("ready.jstree", function (e, data) {
//			alert(data.instance.get_node(6));
			var id = e.target.firstChild.firstChild.id ; // 获取根节点
			data.instance.open_node(id);//打开根节点
		});
		//双击事件
		$("#modalTree").on("dblclick.jstree", function (e) {
			
			$("#addressModalCenter").modal('hide');
		});
		//初始化地址列表的弹框数据
		$('#modalTree').on('changed.jstree', function (e, data) {
			// 树形列表点击事件
		    var i, j, r ;
		    r = data.instance.get_node(data.selected[0]);
		    r = r.original;
		//  alert(r.id+","+ r.text+","+ r.level +","+ r.parent +"," + r.updateDate );
		    var parent_id = data.instance.get_parent(data.selected[0]) ;
		    var parent_name = data.instance.get_node(parent_id).text;
		    $('#addressId2').val(r.id);
		    $('#addressBTN2').text(r.full_name);
		    addrName = r.full_name ;
		  }).jstree({
			  //树形列表加载参数
			'core' : { 	'data': { 'url': 'admin/area/ajaxQueryAddressByParent.do' },
						'themes': {
				            'name': 'proton',
				            'responsive': true
				        }
					}
		});
		
		
		
		//  监听滚动条，固定地址树
		//获取要定位元素距离浏览器顶部的距离
		var navH = $("#treeDiv").offset().top;
		//滚动条事件
//		$(window).scroll(function(){
//			
//			//获取滚动条的滑动距离
//			var scroH = $(this).scrollTop();
//			var height = $("#tableDiv").height() ;
//			//滚动条的滑动距离大于等于定位元素距离浏览器顶部的距离，就固定，反之就不固定
//			if(scroH >= navH && height > 150){
//				$("#treeDiv").css({"position":"fixed","top":0 , "bottom" : 0 , "overflow-x": "scroll" });
//			}else if(scroH < navH){
//				$("#treeDiv").css({"position":"static"});
//			}
//		});

		
		dataTables = $('#userDataTable').dataTable({"columns": [
		    { "data": "u_no" , render : function(data,type,row,meta){
		    	return '<input type="radio" id="radio'+meta.row+'" name="u_id" value="'+ meta.row +'"  onclick="userManager.getData('+ meta.row+')"  >'+ ( Number(meta.row) + 1) ;
		    }},
		    { "data": "u_no" },
		    { "data": "u_name" },
		    { "data": "u_phone" },
		    { "data": "u_balance" },
		    { "data":  function(row, type, set, meta){
		    			if(type =='set') return;
		    			return dictionaryCache.getDescByBeanAttrValue("user","u_status",row.u_status);} },
		    { "data": "priceName" },
		    { "data": "device_company" },
		    { "data": "deviceKindName" },
		    { "data": "addressName" },
		    { "data": "u_paperwork" },
		    { "data": "u_cardno" },
		    { "data": "u_createdate" },
		    { "data": "u_updatedate" },
		    { "data": "u_remark" }
		  ],
		  	"columnDefs" : [{
		  		"defaultContent": " ",
		  		"targets": "_all"
		  	}], "order" : [] ,
			"oLanguage": {
		  		"sLengthMenu": "每页显示 _MENU_ 条记录",
		  		"sZeroRecords": "对不起，没有匹配的数据",
		  		"sInfo": "第 _START_ - _END_ 户 / 共 _TOTAL_ 户数据",
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
	  		},
	  		"ordering": false, //排序功能
		});
		$('#userDataTable').on("click","tr",function(e){
			

			if(e.target.toString().indexOf("Input") > 0){
				//点击的是 单选按钮 ， 放在input的 onclick中了
//				userManager.getData(e.target.value);

			}else{
				userManager.getData(e.target.parentNode.childNodes[0].childNodes[0].value);
			}
		});
}


initData();
