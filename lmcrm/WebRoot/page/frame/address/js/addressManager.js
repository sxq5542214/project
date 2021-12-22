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

function checkAddChose(){
	var a_name = document.updateForm.name.value ;
	if(addressId == '' ){
		alert('请先选择左侧地址！');
		return false;
	}
	$('#exampleModalCenter').modal('show');
	
}
function deleteAddress(){

	var parent_id = $("#parent_id").val();
	if(addressId == '' ){
		alert('请先选择左侧地址！');
		return false;
	}
	if(parent_id == '' ){
		alert('顶层地址不允许删除！');
		return false;
	}
	if(confirm("确定删除当前地址？")){
		$.ajax({url:"admin/area/ajaxDeleteAddressInfo.do",
			data:{
					addressId : addressId
			},
			success:function(result){
			   alert(result);
			   //操作成功则删除界面上的元素
			   if(result.indexOf('成功') > 0){
			    	var curNode = $.jstree.reference('#tree').get_node(addressId);
				   $.jstree.reference('#tree').delete_node(curNode);
			   }
			}});
	}
	
}
function addAddress(type){
	var a_level = $("#a_level").val();
	var parent_id = $("#parent_id").val();
	var a_name = document.updateForm.name.value ;
	var full_name = $("#full_name").val();
	if( a_name == ''){
		alert('请输入地址名称！');
		return ;
	}else{
		if(type == 2){ // 1为同级地址  2为下级地址
//			if(a_level >= 3) {
//				alert('已经是最末级，无法增加下级地址！');
//				return ;
//			}
			parent_id = addressId ;
			a_level = Number(a_level) +1;
			
		}
		
//		if(type == 1 && a_level == 1){
//			alert("顶层不能新增同级地址！");
//			return;
//		}
		
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

					//$.jstree.reference('#tree').create_node(parent_id,a_name);
			    	
					$.jstree.reference('#tree').refresh();
//					var curNode = $("#tree").jstree('get_node', parent_id+'' );
//					$.jstree.reference('#tree').select_node(curNode);
//				alert(curNode);
//					$.jstree.reference('#tree').open_node(curNode);
					

					$('#exampleModalCenter').modal('hide');
				   // window.location.reload();
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
		    	
		    	var curNode = $.jstree.reference('#tree').get_node(a_id);
		    	
		    	$("#tree").jstree('rename_node', curNode , a_name );
//				$('#exampleModalCenter').modal('hide');
			    //window.location.reload();
		    }
		}});
}
var levelname1= '';
var levelid1= '';
var levelname2;
var levelname3;
var addressId = '';
function updateUserData(level,id,name,parentid,parent_name){
	addressId = id;
	if(parentid =='#'){
		parentid = '';
	}
	if(level == 1){
		parent_name = '';
	}
	$("#a_level").val(level);
	$("#full_name").val(parent_name);
	$("#parent_id").val(parentid);

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
		    var company = result; //eval('(' + result + ')');
		    dataArray = company;
	}});
	
	$.ajax({url:"admin/area/ajaxQueryAreaByCompany.do",
		type : "POST",
		data:{			},
		success:function(result){
	    var list = result ; // eval('(' + result + ')');
	    addressManager.areaList = list;
	}});
  return dataArray;
}

var queryIndex = 1;
//默认打开根节点
$("#tree").on("ready.jstree", function (e, data) {
//	alert(data.instance.get_node(6));
	var id = e.target.firstChild.firstChild.id ; // 获取根节点
data.instance.open_node(id);//打开根节点
});
//树形菜单点击事件
$('#tree').on('changed.jstree', function (e, data) {
	// 树形列表点击事件
    var i, j, r ;
//    for(i = 0, j = data.selected.length; i < j; i++) {   如果多选，则需要循环
//      r= data.instance.get_node(data.selected[i]);
//    }
    r = data.instance.get_node(data.selected[0]);
    r = r.original;
    if(r ==null){
    	return ;
    }
//  alert(r.id+","+ r.text+","+ r.level +","+ r.parent +"," + r.updateDate );
    var parent_id = data.instance.get_parent(data.selected[0]) ;
    var parent_name = data.instance.get_node(parent_id).text;
    updateUserData(r.level,r.id,r.text,r.parent,parent_name);
    
    document.updateForm.parent_name.value = r.full_name ;
    
  }).jstree({
	  //树形列表加载参数
	'core' : { 	'data': { 	
							'url': 'admin/area/ajaxQueryAddressByParent.do' ,
							'data' : function (node) {  
								return { 'index' : queryIndex++ };
								}
						},
				'themes': {
		            'name': 'proton',
		            'responsive': true
		        },
		        'check_callback': true,
			},
	"contextmenu": {
	    items: {
	        "add": {
	            "label": "新增分组",
	            "action": function (data) {
	            	var inst = jQuery.jstree.reference(obj.reference);
                    var clickedNode = inst.get_node(obj.reference);
                    var newNode = inst.create_node(obj.reference,clickedNode.val);
                    var ty = inst.get_type(obj.reference);
                    inst.set_type(newNode, ty);
                    var Nodeobj = inst.get_json(newNode);
                    var str = {
                        "id" : Nodeobj.id,
                        "text" : Nodeobj.text,
                        "icon" : Nodeobj.icon,
                        "type" : Nodeobj.type,
                        "parentid" : clickedNode.id,
                        "tablelist" : null
                    };
                    var selectedTab = $('#t_map').tabs('getSelected');
                    var pageTitle = selectedTab.panel('options').title;
                    $.post("lwy/createNode.do", {changedata : JSON.stringify(str),pageTitle:pageTitle}, 
                            function(data) {});
                    inst.edit(newNode);
                    inst.open_node(obj.reference);
	            }
	        }
	        ,
	        "edit": {
	            "label": "修改分组",
	            "action": function (data) {
	            	var inst = jQuery.jstree.reference(obj.reference);
                    var clickedNode = inst.get_node(obj.reference);
                    inst.edit(obj.reference,clickedNode.val);
	            }
	        },
	        "del": {
	            "label": "删除分组",
	            "action": function (data) {
	            	var inst = jQuery.jstree.reference(obj.reference);
                    var clickedNode = inst.get_node(obj.reference);
                    inst.delete_node(obj.reference);
	            }
	        }
	    }
	}

});