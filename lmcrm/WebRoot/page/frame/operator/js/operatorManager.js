var operatorManager =  new Vue({
    el: "#operatorManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        operatorList: [],// 表格数据
        companyList: [],// 表格数据
        menuList :[],
        areaList :[],
        newRow:{}// 新增的行数据，用于新增行
    },
    methods:{
	    created: function(){
	    },
	    getData: function(index){
//	    	alert(this.operatorList[index].p_name );
	    	this.choseIndex = index;
	    	var form = document.updateForm;
			form.o_id.value = this.operatorList[index].o_id ;
			form.o_name.value = this.operatorList[index].o_name ;
			form.o_kind.value = this.operatorList[index].o_kind ;
			form.o_status.value = this.operatorList[index].o_status ;
//			form.o_companyid.value = this.operatorList[index].o_companyid ;
			form.o_openaudit.value = this.operatorList[index].o_openaudit ;
			form.o_limitmoney.value = this.operatorList[index].o_limitmoney ;
			$("#updateform_companyid").val(""+this.operatorList[index].o_companyid);
			
			var pass = this.operatorList[index].o_password2;
			if(typeof(pass) == "undefined") pass = '';
			form.o_password2.value = pass ;
	    	
			$("#radio"+index).prop('checked',true);
	    },
	    checkRole: function(index){
	    	$("#selectAllMenu").prop("checked", false);
	    	
	    	$("#menu"+index).prop("checked", !$("#menu"+index).prop("checked"));
	    }
    }
})

function addOrUpdateOperator(){
	var form = document.updateForm;
	$.ajax({url:"admin/operator/ajaxAddOrUpdateOperator.do",
		type : "POST",
		data:{
			o_id: form.o_id.value,
			o_name: form.o_name.value,
			o_password2: form.o_password2.value,
			o_kind: form.o_kind.value,
			o_status: form.o_status.value,
			o_companyid: form.o_companyid.value,
			o_openaudit: form.o_openaudit.value,
			o_limitmoney: form.o_limitmoney.value
		},
		success:function(result){
		    if(result > 0){
		    	alert('操作成功！请设置权限及区域！');
		    }else{
		    	alert('操作失败，请检查数据');
		    }

		    queryOperatorData();
		    $('.close').click();
		}});
}
function addOperator(){
	 document.updateForm.o_id.value = '';
}
function updateOperator(){
	
}
function queryRole(){
	if(operatorManager.choseIndex < 0){
		alert("请先选则员工！");
		return false;
	}else{
		$.ajax({url:"admin/system/queryLastChildMenu.do",
				type : "POST",
				data:{
				},
			success:function(result){
			    var list = result ; // eval('(' + result + ')');
			    operatorManager.menuList = list;
		}});
	    $('#roleModalCenter').modal('show');

	}
}

function choseAllMenus(){
	$("#selectAllMenu").prop("checked", true);
	 $(":checkbox[name='menu_ids']").prop("checked", true);
}

function queryArea(){
	
}
//function initDataTable(){
//	
//	$("#id_query_data_table").dataTable({
//    	"paging" : true,
//    	"ordering" : true,
//    	"info" : true,
//    	"retrieve" : true ,
//    	buttons :  ['copy', 'excel', 'pdf']
//    });
//	
//}

function queryOperatorData(){
	var o_status = $("#o_status").val();
	var o_name = $("#o_name").val();
	
	$.ajax({url:"admin/operator/ajaxQueryOperatorList.do",
			type : "POST",
			data:{
				o_status: o_status,
				o_name :o_name
			},
		success:function(result){
		    var list = result ; // eval('(' + result + ')');
		    operatorManager.operatorList = list;
//		    setTimeout("initDataTable()","100"); 
	    
	}});
	

	$.ajax({url:"admin/company/ajaxQueryCompanyList.do",
			type : "POST",
			data:{
			},
		success:function(result){
	    var list = result ; // eval('(' + result + ')');
	    operatorManager.companyList = list;
	}});
}
queryOperatorData();