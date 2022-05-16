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
});



function queryUserData(){
	
	$.ajax({url:"admin/sms/querySmsSendInfoList.do",
			type : "POST",
			data:{},
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

function initData(){
	
	
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
		    { "data": "id" , render : function(data,type,row,meta){
		    	return '<input type="radio" id="radio'+meta.row+'" name="u_id" value="'+ meta.row +'"  onclick="userManager.getData('+ meta.row+')"  >'+ ( Number(meta.row) + 1) ;
		    }},
		    { "data": "templateName" },
		    { "data": "send_content" },
		    { "data": "send_time" },
		    { "data": "send_operator" },
		    { "data": "send_count" },
		    { "data": "success_count" },
		    { "data": "error_count" },
//		    { "data":  function(row, type, set, meta){
//		    			if(type =='set') return;
//		    			return dictionaryCache.getDescByBeanAttrValue("user","u_status",row.u_status);} },
		    { "data":  function(row, type, set, meta){ return "查看详情" } }
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
