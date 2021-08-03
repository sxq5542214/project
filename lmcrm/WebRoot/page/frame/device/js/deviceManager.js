

var deviceManager =  new Vue({
    el: "#deviceManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	choseIndex : -1,
        checkAll: false,// 是否全选
        checkedRows: [],// 选中的行标，用于删除行
        priceList: [],// 表格数据
        priceName:'未选择价格',priceBase1:'待选择',priceTon1:'待选择',priceBase2:'待选择',priceTon2:'待选择',priceBase3:'待选择',
        p_lowprice:'待选择',p_lowamount:'待选择',p_limitamount:'待选择',p_other1:'待选择',p_other2:'待选择',
        deviceKindList : [] ,
        newRow:{}// 新增的行数据，用于新增行
    },
	created: function(){
		// 价格列表
		$.ajax({url:"admin/price/ajaxQueryPriceByCompany.do",
			type : "POST",
			data:{ 
				p_enabled :1
			},
			success:function(result){
		    var list = eval('(' + result + ')');
		    deviceManager.priceList = list;
		}});
		
		//设备种类列表
		$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
			type : "POST",
			data:{ },
			success:function(result){
		    var list = eval('(' + result + ')');
		    deviceManager.deviceKindList = list;
		}});
		
	},
    methods:{
    	getPriceData : function(index){
	    	var price = deviceManager.priceList[index];
	    	deviceManager.priceBase1 = price.p_base1;
	    	deviceManager.priceTon1 = price.p_ton1;
	    	deviceManager.priceBase2 = price.p_base2;
	    	deviceManager.priceTon2 = price.p_ton2;
	    	deviceManager.priceBase3 = price.p_base3;
	    	deviceManager.priceName = price.p_name;
	    	deviceManager.p_lowprice = price.p_lowprice;
	    	deviceManager.p_lowamount = price.p_lowamount;
	    	deviceManager.p_limitamount = price.p_limitamount;
	    	deviceManager.p_other1 = price.p_other1;
	    	deviceManager.p_other2 = price.p_other2;
			$("#price"+index).prop('checked',true);
	    },
		getDeviceKindData : function(index){
			$("#deviceKind"+index).prop('checked',true);
		}
    }
})

function readCard(){
	callWindowsClientMethod('readCard','{}' , function(result){
		$("#debugTextArea").val(result);
	});
	
}