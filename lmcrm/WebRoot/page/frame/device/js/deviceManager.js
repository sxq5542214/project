

var deviceManager =  new Vue({
    el: "#deviceManagerDiv",
    data: {
    	getDescByBeanAttrValue : dictionaryCache.getDescByBeanAttrValue,
    	priceIndex : -1,
    	deviceKindIndex: -1,
        priceList: [],// 表格数据
        priceName:'未选择价格',priceBase1:'待选择',priceTon1:'待选择',priceBase2:'待选择',priceTon2:'待选择',priceBase3:'待选择',
        p_lowprice:'待选择',p_lowamount:'待选择',p_limitamount:'待选择',p_other1:'待选择',p_other2:'待选择',
        deviceKindList : [] ,
        cardKindName :'未读卡',
        cardUserNo : '未读卡',
        cardIsBrush : '未读卡',
        cardBrushDate : '未读卡',
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
		    var list = result ; //eval('(' + result + ')');
		    deviceManager.priceList = list;
		}});
		
		//设备种类列表
		$.ajax({url:"admin/device/ajaxQueryEnableDeviceKind.do",
			type : "POST",
			data:{ },
			success:function(result){
		    var list = result ; // eval('(' + result + ')');
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
			deviceManager.priceIndex = index;
	    },
		getDeviceKindData : function(index){
			$("#deviceKind"+index).prop('checked',true);
			deviceManager.deviceKindIndex = index;
		}
    }
})

function readCard(){
	callWindowsClientMethod('readCard','{}' , function(result){
		
		$("#debugTextArea").val(result);
		
		var json = eval('(' + result + ')');
		var cardKindName = "无法识别" ;
		var iCardKind = json.iCardKind;
		var cardUserNo = 0;
		var cardIsBrush = "无此项";
		var cardBrushDate = "无此项";
		switch (iCardKind) {
		case 0:
			cardKindName = "新卡/空白卡";
			break;
		case 1:
			cardKindName = "用户卡";
			cardUserNo = json.stru_userparm.iUserNo;
			var iflag = json.stru_userparm.stru_retparm.iFlag;
			if(iflag == 1){
				cardIsBrush = "已刷卡至水表";
				var iYear = json.stru_userparm.stru_retparm.iYear;
				var iMonth = json.stru_userparm.stru_retparm.iMonth;
				var iDay = json.stru_userparm.stru_retparm.iDay;
				var iMonth = iMonth <10 ? "0"+iMonth : iMonth;
				var iDay = iDay <10 ? "0"+iDay : iDay;
				cardBrushDate = "20"+iYear+"-"+ iMonth + "-" + iDay ;
			}else{
				cardIsBrush ="未刷卡" ;
				cardBrushDate = "未刷卡";
			}
			
			break;
		case 2:
			cardKindName = "设置卡";
			break;
		case 3:
			cardKindName = "清空卡";
			break;
		case 4:
			cardKindName = "检查卡";
			break;
		case 6:
			cardKindName = "阀门卡";
			break;
		case 7:
			cardKindName = "校时卡";
			break;
		default:
			break;
		}
		deviceManager.cardKindName = cardKindName;
		deviceManager.cardUserNo = cardUserNo;
		deviceManager.cardIsBrush = cardIsBrush;
		deviceManager.cardBrushDate = cardBrushDate;
	});
	
}
function writeCard(){
	var cardType = $("input[name='cardType']:checked").val();
	var priceIndex = deviceManager.priceIndex;
	var deviceKindIndex = deviceManager.deviceKindIndex;
	
	if(typeof(cardType) == 'undefined'  || priceIndex == -1 || deviceKindIndex == -1){
		alert("请先选择 价格类型/水表类型/卡类型 ！");
		return;
	}
	
	if(! confirm('确定写卡吗？ 操作不可撤销！')){
		return ;
	}
	
	var price = deviceManager.priceList[priceIndex];
	var dk = deviceManager.deviceKindList[deviceKindIndex];
	// 获取 imeterkind 参数
	$.ajax({url:"admin/card/ajaxQueryWriteCardParam.do",
		type : "POST",
		data:{ price_id : price.p_id , dk_id : dk.dk_id ,cardType :cardType},
		success:function(res){

			var bean = res ; // eval('(' + res + ')');
			if(bean.queryStatus == -1){
				alert(bean.queryResult);
				return;
			}
			switch (cardType) {
			case '0':
				callWindowsClientMethod('writeSpaceCard',res , function(result){});
				break;
			case '2':
				callWindowsClientMethod('writeSettingCard',res , function(result){});
				break;
			case '3':
				callWindowsClientMethod('writeClearCard',res , function(result){});
				break;
			case '4':
				callWindowsClientMethod('writeTestCard',res , function(result){});
				break;
			case '6':
				callWindowsClientMethod('writeValveCard',res , function(result){});
				break;
			case '7':
				callWindowsClientMethod('writeTimeCard',res , function(result){});
				break;
				
			default:
				alert(result);
				break;
			}
	}});
	
	
	
	
}


