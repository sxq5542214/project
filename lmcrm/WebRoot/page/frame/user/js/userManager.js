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
    	choseIndex : '',
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        userList: [],// 表格数据
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
//			form.u_group.value = this.userList[index].u_group +1;
	    	$("#selectGroup").val(this.userList[index].u_group +1);
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdateUser(){
	var form = document.updateForm;
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
			u_buildingid : userManager.buildingId
		},
		success:function(result){
		    if(result > 0){
		    	alert('操作成功！');
		    }
		    
		    queryUserData();
		    $('#exampleModalCenter').modal('hide');
		}});
}
function addUser(){
	 document.updateForm.u_id.value = '';
	 document.updateForm.u_name.value = '';
	 document.updateForm.u_paperwork.value = '';
	 document.updateForm.u_balance.value = '0';
	 document.updateForm.u_peoplesize.value = '0';
	 document.updateForm.u_phone.value = '';
	 if(userManager.buildingId == ''){
		 alert('请先选择下方最末级地址！');
	 }else{
		 
			$.ajax({url:"admin/area/ajaxQueryBuildingById.do?id="+userManager.buildingId,
				success:function(result){
					 var json = eval('(' + result + ')');
					 document.updateForm.u_address.value = json.full_name;
					 $('#exampleModalCenter').modal('show');
				}});
	 }
	 
}
function updateUser(){
	if(document.updateForm.u_id.value == ''){
		alert("请先选择要修改的用户！");
	}else{
		 $('#exampleModalCenter').modal('show');
	}
	
}

function queryUserData(){
	var u_phone = $("#u_phone").val();
	var u_name = $("#u_name").val();
	var u_paperwork = $("#u_paperwork").val();
	var u_buildingid = $("#u_buildingid").val();
	var u_areaid = $("#u_areaid").val();
	
	$.ajax({url:"admin/user/ajaxQueryUserByCompany.do",
			type : "POST",
			data:{
				u_phone: u_phone,
				u_name :u_name,
				u_paperwork :u_paperwork,
				u_buildingid : u_buildingid,
				u_areaid : u_areaid
			},
		success:function(result){
		    var list = eval('(' + result + ')');
		    userManager.userList = list;
		}});
}
function updateUserData(level,id,name){
	
	if(level == 3){
		userManager.buildingId = id;
		$("#u_buildingid").val(id);
		$("#u_areaid").val('');
		queryUserData();
	}else if(level == 2){
		userManager.buildingId = '';
		$("#u_areaid").val(id);
		$("#u_buildingid").val('');
		queryUserData();
	}else{
		userManager.buildingId = '';
		$("#u_buildingid").val('');
		$("#u_areaid").val('');
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
	
// Some logic to retrieve, or generate tree structure
//	dataArray = [
//		  {
//		    text: "Parent 1",
//		    href:  "javascript:updateTreeData(1);",
//		    nodes: [
//		      {
//		        text: "Child 1",
//		        nodes: [
//		          {
//		            text: "Grandchild 1"
//		          },
//		          {
//		            text: "Grandchild 2"
//		          }
//		        ]
//		      },
//		      {
//		        text: "Child 2"
//		      }
//		    ]
//		  },
//		  {
//			href:  "javascript:alert(2);",
//		    text: "Parent 2"
//		  },
//		  {
//				href:  "javascript:alert(3);",
//		    text: "Parent 3"
//		  },
//		  {
//				href:  "javascript:alert(4);",
//		    text: "Parent 4"
//		  },
//		  {
//		    text: "Parent 5"
//		  }
//		];
  return dataArray;
}
$('#tree').bstreeview({
	data: getTree(),
	openNodeLinkOnNewTab: false
});
// queryUserData();


$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do",
	type : "POST",
	data:{
		p_enabled : 1,
	},
	async : false,
	success:function(result){
	    var list = eval('(' + result + ')');
	    userManager.priceList = list;
}});
// alert(userManager.priceList.length);