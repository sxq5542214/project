var priceManager =  new Vue({
    el: "#priceManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : '',
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        priceList: [],// 表格数据
        newRow:{}// 新增的行数据，用于新增行
    },
    methods:{
	    created: function(){
	    },
	    getData: function(index){
//	    	alert(this.priceList[index].p_name );
	    	this.choseIndex = index;
	    	
	    	var form = document.updateForm;
			form.update_p_id.value = this.priceList[index].p_id ;
			form.update_p_name.value = this.priceList[index].p_name ;
			form.update_p_ladder.value = this.priceList[index].p_ladder ;
			form.update_p_settlemonth.value = this.priceList[index].p_settlemonth ;
			form.update_p_settleday.value = this.priceList[index].p_settleday ;
			form.update_p_base1.value = this.priceList[index].p_base1 ;
			form.update_p_other1.value = this.priceList[index].p_other1 ;
			form.update_p_other2.value = this.priceList[index].p_other2 ;
			form.update_p_lowprice.value = this.priceList[index].p_lowprice ;
			form.update_p_lowamount.value = this.priceList[index].p_lowamount ;
			form.update_p_enabled.value = this.priceList[index].p_enabled ;
			form.update_p_ton1.value = this.priceList[index].p_ton1 ;
			form.update_p_base2.value = this.priceList[index].p_base2 ;
			form.update_p_ton2.value = this.priceList[index].p_ton2 ;
			form.update_p_base3.value = this.priceList[index].p_base3 ;
			form.update_p_limitamount.value = this.priceList[index].p_limitamount ;
			form.update_p_storedamount.value = this.priceList[index].p_storedamount ;
			form.update_p_weakprompt.value = this.priceList[index].p_weakprompt ;
			form.update_p_overflat.value = this.priceList[index].p_overflat ;
	    	
			$("#radio"+index).prop('checked',true);
	    }
    }
})

function addOrUpdatePrice(){
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
		    
		    queryPriceData();
		    $('.close').click();
		}});
}
function addPrice(){
	 document.updateForm.update_p_id.value = '';
}
function updatePrice(){
	
}

function calcLowprice(){
	var update_p_lowamount =  document.updateForm.update_p_lowamount.value ;
	var update_p_base1 = document.updateForm.update_p_base1.value ;
	
	document.updateForm.update_p_lowprice.value = update_p_lowamount * update_p_base1;
}

function queryPriceData(){
	var p_enabled = $("#p_enabled").val();
	var p_name = $("#p_name").val();
	var p_ladder = $("#p_ladder").val();
	
	$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do",
			type : "POST",
			data:{p_name: p_name,
			p_enabled :p_enabled,
			p_ladder :p_ladder
			},
		success:function(result){
	    var list = result ; //eval('(' + result + ')');
	    priceManager.priceList = list;
	}});
}
queryPriceData();