/**
 * 
 */
function callWindowsClientMethod(action,jsonstr,callback){
	
	var str  = 'javascript访问C#代码';
	var status = window.external.jsCallClient(action,jsonstr);
	if(typeof callback == "function") {
		if(status == 0){
			callback(1); 
		}else{
			callback(-1); 
		}
	}
	alertResultTips(status);
}

function alertResultTips(iret){
	resultStr = "未知错误";
    switch (iret)
    {
        case 0:
            resultStr = "操作成功";
            break;
        case 1:
            resultStr = "搜索写卡器失败";
            break;
        case 2:
            resultStr = "打开写卡器失败";
            break;
        case 3:
            resultStr = "读卡失败";
            break;
        case 4:
            resultStr = "写卡失败";
            break;
        case 5:
            resultStr = "写配置失败";
            break;
        case -1:
            resultStr = "未知错误";
            break;
    }
    alert(resultStr);
}

function callWindowsClientReadCardMethod(){
	
	var str  = 'javascript访问C#代码';
	window.external.readCard(str);
}

function callWindowsClientWriteSpaceCard(){

	window.external.writeSpaceCard();
}


function windowsCallWebJsAlert(status,msg,data){
	alert(msg);
}

function windowsCallWebJsReadCardSuccess(msg){
	var jsonObject = eval('(' + msg + ')');
//	alert(jsonObject);
	$("#textArea").html(msg);
}



