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

var addressManager =  new Vue({
    el: "#addressManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : '',
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        areaList: [],// 表格数据
        buildingList: [],// 表格数据
        newRow:{}// 新增的行数据，用于新增行
    },
    methods:{
	    created: function(){
	    },
	    getData: function(index){
	    	this.choseIndex = index;
	    	
	    	var form = document.updateForm;
			form.a_id.value = this.addressList[index].a_id ;
	    	$("#a_level").val(2);
	    	
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdateAddress(){
	var form = document.updateForm;
	$.ajax({url:"admin/area/ajaxAddOrUpdateAddressByCompany.do",
		type : "POST",
		data:{
			a_id: form.a_id.value,
			a_name: form.a_name.value,
			a_level : form.a_level.value
		},
		success:function(result){
		    if(result > 0){
		    	alert('操作成功！');
		    }
		    
		    queryAddressData();
		    $('#exampleModalCenter').modal('hide');
		}});
}
function addAddress(type){
	var a_level = $("#a_level").val();
	var parent_id = $("#parent_id").val();
	var a_name = $("#a_name").val();
	var full_name = $("#full_name").val();
	if(addressId == '' || a_name == ''){
		alert('请输入地址名称 和 选择左侧地址！');
		return ;
	}else{
		if(type == 2){ // 1为同级地址  2为下级地址
			if(a_level >= 3) {
				alert('已经是最末级，无法增加下级地址！');
				return ;
			}
			parent_id = addressId ;
			a_level = Number(a_level) +1;
			
		}
		if(type == 1 && a_level == 1){
			alert("顶层不能新增同级地址！");
			return;
		}
		
		$.ajax({url:"admin/area/ajaxAddAddressInfo.do",
			type : "POST",
			data:{
					a_level: a_level,
					parent_id:parent_id,
					a_name:a_name,
					full_name : full_name,
					addressId : addressId
			},
			success:function(result){
			    if(result == 0){
			    	alert('操作失败！');
			    }else{
			    	alert('操作成功！');
				    window.location.reload();
			    }
			}});
	}
}
function updateAddress(){
	var a_level = $("#a_level").val();
	var parent_id = $("#parent_id").val();
	var a_name = $("#a_name").val();
	var a_id = $("#a_id").val();
	if(a_id == '' || a_name == ''){
		alert('请输入地址名称 和 选择左侧地址！');
		return ;
	}

	if( a_level == 1){
		alert("顶层不能修改！");
		return;
	}
	$.ajax({url:"admin/area/ajaxUpdateAddressInfo.do",
		type : "POST",
		data:{
				a_level: a_level,
				a_id:a_id,
				parent_id:parent_id,
				a_name:a_name
		},
		success:function(result){
		    if(result == 0){
		    	alert('操作失败！');
		    }else{
		    	alert('操作成功！');
			    window.location.reload();
		    }
		}});
}
var levelname1= '';
var levelid1= '';
var levelname2;
var levelname3;
var addressId = '';
function updateUserData(level,id,name){
	addressId = id;
	if(level ==1){
		$("#a_level").val(1);
		$("#full_name").val('');
		$("#parent_id").val(id);
		levelname1 = name;
		levelid1 = id;
	}else if (level == 2){
		$("#parent_id").val(levelid1);
		$("#a_level").val(2);
		$("#full_name").val(levelname1);
		levelname2 = name;
	}else if (level == 3){

		$("#a_level").val(3);
		levelname3 = name;

		$.ajax({url:"admin/area/ajaxQueryBuildingById.do",
			type : "POST",
			data:{		id: id	},
			success:function(result){
			    var json = eval('(' + result + ')');
			    
			    for(var i = 0 ; i <addressManager.areaList.length;i++ ){
			    	if(addressManager.areaList[i].a_id == json.b_areaid){
						$("#full_name").val(addressManager.areaList[i].a_name);
						$("#parent_id").val(json.b_areaid);
			    	}
			    }
			}});
	}
	$("#a_name").val(name);
	$("#a_id").val(id);
	
	
}

function queryAreaData(){
	
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
	
	$.ajax({url:"admin/area/ajaxQueryAreaByCompany.do",
		type : "POST",
		data:{			},
		success:function(result){
	    var list = eval('(' + result + ')');
	    addressManager.areaList = list;
	}});
  return dataArray;
}
$('#tree').bstreeview({
	data: getTree(),
	openNodeLinkOnNewTab: false
});