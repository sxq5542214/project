var dictionaryCache = new Object();
$.ajax({url:"dictionary/ajaxQueryAllDictionaryByCache.do",
	async : false , //设置为同步请求
	success:function(res){

    var list =  res;  //eval("(" + res + ")");
    dictionaryCache.json = list.data;
    
//    alert(dictionaryCache.getDescByBeanAttrValue("price","p_ladder",0));
}});

dictionaryCache.getDescByBeanAttrValue = function(beanname,attr,value){ 
	
	return dictionaryCache.json[beanname][attr][value];
//	for(i = 0 ; i < list.length ; i++){
//		if(list[i].value == (value+"")){
//			return list[i].description;
//		}
//	}
}

