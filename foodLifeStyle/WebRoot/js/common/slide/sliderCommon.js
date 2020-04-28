function querySliderData(pageCode,callback) {
	$.ajax({
		url : 'other/queryAdvertisingInfo.do',
		data : {
			"code" : pageCode
		},
		type : 'POST',
		success : function(data) {
			var result = eval("(" + data + ")");
			callback(result);
		}
	});
}