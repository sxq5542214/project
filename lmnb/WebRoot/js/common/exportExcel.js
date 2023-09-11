document.write('<script	src="/staticFiles/sheetjs@0.17.5/dist/xlsx.core.min.js"></script>');
function exportExcel(tableIndex){ 
	if(tableIndex == null){
		tableIndex = 0;
	}
	var sheet = XLSX.utils.table_to_sheet($('table')[tableIndex]) ;
	var blob = sheet2blob(sheet) ;
	openDownloadDialog(blob, "导出数据.xlsx");
}
		/**
 * 通用的打开下载对话框方法，没有测试过具体兼容性
 * @param url 下载地址，也可以是一个blob对象，必选
 * @param saveName 保存文件名，可选
 */
function openDownloadDialog(blob, saveName)
{
	var url ;
	if(typeof blob == 'object' && blob instanceof Blob)
	{
		url = URL.createObjectURL(blob); // 创建blob地址
	}
	
	if(typeof(window.navigator.msSaveBlob) != 'undefined' ){
		window.navigator.msSaveBlob(blob,saveName);
	}else{
		var aLink = document.createElement('a');
		aLink.href = url;
		aLink.download = saveName || ''; // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
	
		var event;
	
		if(true) {
			event = document.createEvent('MouseEvents');
			event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);
			
		}
		else
		{
			event = new window.MouseEvent('click');
		}
		aLink.dispatchEvent(event);
	
	}
}

// 将一个sheet转成最终的excel文件的blob对象，然后利用URL.createObjectURL下载
function sheet2blob(sheet, sheetName) {
	sheetName = sheetName || 'sheet1';
	var workbook = {
		SheetNames: [sheetName],
		Sheets: {}
	};
	workbook.Sheets[sheetName] = sheet;
	// 生成excel的配置项
	var wopts = {
		bookType: 'xlsx', // 要生成的文件类型
		bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
		type: 'binary'
	};
	var wbout = XLSX.write(workbook, wopts);
	var blob = new Blob([s2ab(wbout)], {type:"application/octet-stream"});
	// 字符串转ArrayBuffer
	function s2ab(s) {
		var buf = new ArrayBuffer(s.length);
		var view = new Uint8Array(buf);
		for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
		return buf;
	}
	return blob;
}