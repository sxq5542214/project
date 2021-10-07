/**
 * 
 */
function callWindowsClientMethod(action,jsonstr,callback){

//	alert( window.external.jsCallClient  );
	if( typeof(window.external.jsCallClient ) == "undefined"){
		alert( "未运行在客户端环境中，无法读写卡！" );
		return ;
	}
	
	var str  = 'javascript访问C#代码';

	if(typeof(jsonstr) != 'string'){
		jsonstr = JSON.stringify(jsonstr);
	}

	
	var status = window.external.jsCallClient(action,jsonstr);
	
	if(typeof callback == "function") {
		
		
		// 类型为字符串并且长度大于2时，说明是返回的具体内容
		if( status.length > 2 ){
			callback(status); 
		}else{
			status = Number(status);
			if(status == 0){
				callback(1); 
			}else{
				callback(-1); 
			}
		}
	}
	if(typeof status == "number"){
		alertResultTips(status);
	}
}

function alertResultTips(iret){
	resultStr = "未知错误";
    switch (iret)
    {
        case 0:
            resultStr = "写卡操作成功";
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



