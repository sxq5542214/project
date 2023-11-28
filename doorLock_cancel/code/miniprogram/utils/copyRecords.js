var saveKey = "copyRecords";
var recordsJson ;

function getAllCopyRecords(){
  var recordsJson = null;
  try {
    recordsJson = wx.getStorageSync(saveKey);
  } catch (e) {
    wx.showToast({
      title: '获取本地存储数据失败',
      icon: 'none',
      duration: 2000
    })
  }

  if (recordsJson == null || recordsJson == 'undefined' || recordsJson == '') {
    recordsJson = { "count": 0, "items": [] };
  }
  return recordsJson;
}
function deleteCopyRecord(index){
  var recordsJson = getAllCopyRecords();
//  var itemList = recordsJson.items;
  recordsJson.items.splice(index,1);
  recordsJson.count = recordsJson.count - 1;
  wx.setStorage({
    key: saveKey,
    data: recordsJson,
  });

  console.log("recordsJson : %o" , recordsJson);



}
function saveCopyRecord(recordStr,keyStr,typeStr,limitStr,lockName,lockIndex){
  var recordsJson = getAllCopyRecords();
  console.log(recordsJson );

  var itemList = recordsJson.items;
  //找到数据后删除
  for (var i = 0; i < itemList.length; i++) {
    if (itemList[i].keyStr == keyStr) {
      return ;
    }
  }
  var item = {};
  item.time = formatDate("yyyy-MM-dd hh:mm:ss",new Date());
  item.recordStr = recordStr;
  item.typeStr = typeStr;
  item.keyStr = keyStr;
  item.limitStr = limitStr;
  item.lockName = lockName;
  item.index = lockIndex;

  recordsJson.items.splice(0,0,item);
  recordsJson.count = recordsJson.count + 1;
  wx.setStorage({
    key: saveKey,
    data: recordsJson,
  });

  console.log("recordsJson : %o" , recordsJson);
}
function formatDate(fmt,date) { 
  var o = { 
     "M+" : date.getMonth()+1,                 //月份 
     "d+" : date.getDate(),                    //日 
     "h+" : date.getHours(),                   //小时 
     "m+" : date.getMinutes(),                 //分 
     "s+" : date.getSeconds(),                 //秒 
     "q+" : Math.floor((date.getMonth()+3)/3), //季度 
     "S"  : date.getMilliseconds()             //毫秒 
 }; 
 if(/(y+)/.test(fmt)) {
         fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length)); 
 }
  for(var k in o) {
     if(new RegExp("("+ k +")").test(fmt)){
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
      }
  }
 return fmt; 
}
module.exports.deleteCopyRecord = deleteCopyRecord;
module.exports.saveCopyRecord = saveCopyRecord;
module.exports.getAllCopyRecords = getAllCopyRecords;