String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};
String.prototype.trim = function(str){
	if(str != null){
		str +='';
		return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
};

function trim(str){
	if(str != null){
		str +='';
		return str.replace(/(^\s*)|(\s*$)/g, ""); 
	}
}

function isNull(str){
	if(str == null || trim(str) == '' || trim(str) == 'null'){
		return true;
	}
	return false;
}

function isNotNull(str){
	return !isNull(str);
}

function isNumberAndComma(str){
	var reg = /^(\d+,?)+$/;
	if(reg.test(str))
	     return true;
	else
		 return false;
}
function isNumber(str){
	var reg =  /^\d+$/g;
	if(reg.test(str)){
		return true;
	}else{
		return false;
	}
}


function commonQueryDictionary(tablename,attribute,domid,checkValue){
	//查询活动场景
    $.post("dictionary/queryDictionary.do",{"tablename":tablename,"attribute":attribute},function(data){
		var list = eval("("+data+")");
		for(var i=0; i < list.length ; i++){
			var str = "";
			var selected = '';
			if( checkValue != null && checkValue == list[i].value){
				selected = 'selected="selected"';
			}
			str +='<option value="'+ list[i].value +'" '+ selected +' >'+ list[i].description +'</option>';
			str = str.replaceAll("undefined","");
			$(domid).append(str); 
		}
		$(domid).multiselect('rebuild');
	});
}