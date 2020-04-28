Util = function(){
	return {
		toStr:function(value){
			return value==null||value=='undefined'?'':value;
		},
		toStatus:function(value){
			return value==null||value=='undefined'?'':value==0?'不可用':'正常';
		},
		empty:function(value){
			return value==null||value=='undefined'||value==''?true:false;
		},
		notEmpty:function(value){
			return value==null||value=='undefined'||value==''||value=='null'?false:true;
		},
		toYN:function(value){
			return value==null||value=='undefined'?'':value==0?'不可以':'可以';
		},
		toInt:function(value){
			return value==null||value=='undefined'?0:value;
		},
		//获取url中的参数
		getUrlParam:function(name) {
		    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
		    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
		    if (r != null) return unescape(r[2]); return null; //返回参数值
		},
		validPhone:function(value){//校验手机号码是否正确
			if(!value.match(/^(((13[0-9]{1})|159|153)+\d{8})$/)) return true;
			else return false;
		}
	};
}();