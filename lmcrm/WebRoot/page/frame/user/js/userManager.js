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
			form.u_group.value = this.userList[index].u_group ;
			form.u_remark.value = this.userList[index].u_remark ;
			form.u_materialfee.value = this.userList[index].u_materialfee ;
			form.u_constructioncost.value = this.userList[index].u_constructioncost ;
			form.u_prepayment.value = this.userList[index].u_prepayment ;
	    	
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdateUser(){
	var form = document.updateForm;
	$.ajax({url:"admin/price/ajaxAddOrUpdatePriceByCompany.do",
		type : "POST",
		data:{
			p_id: form.update_p_id.value,
			p_name: form.update_p_name.value,
			p_ladder : form.update_p_ladder.value,
			p_settlemonth: form.update_p_settlemonth.value,
			p_settleday: form.update_p_settleday.value,
			p_base1: form.update_p_base1.value,
			p_other1: form.update_p_other1.value,
			p_other2: form.update_p_other2.value,
			p_lowprice: form.update_p_lowprice.value,
			p_lowamount: form.update_p_lowamount.value,
			p_enabled: form.update_p_enabled.value,
			p_ton1: form.update_p_ton1.value,
			p_base2: form.update_p_base2.value,
			p_ton2: form.update_p_ton2.value,
			p_base3: form.update_p_base3.value,
			p_limitamount: form.update_p_limitamount.value,
			p_storedamount: form.update_p_storedamount.value,
			p_weakprompt: form.update_p_weakprompt.value,
			p_overflat: form.update_p_overflat.value
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
}
function updateUser(){
	
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
function updateUserData(level,id){
	if(level == 3){
		$("#u_buildingid").val(id);
		$("#u_areaid").val('');
		queryUserData();
	}else if(level == 2){
		$("#u_areaid").val(id);
		$("#u_buildingid").val('');
		queryUserData();
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