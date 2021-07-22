var dictionaryCache = new Object();
$.ajax({url:"dictionary/ajaxQueryAllDictionaryByCache.do",
	success:function(res){

    var list = eval("(" + res + ")");
    dictionaryCache.json = list;
    
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

