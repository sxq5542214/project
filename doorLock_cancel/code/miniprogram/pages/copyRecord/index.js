var app = getApp();
var copyRecords = require("../../utils/copyRecords.js");

Page({
  data: {
    items: []
  },

  deleteRecord : function(event){
    var index = event.currentTarget.dataset.key ;
    console.log(index);
    console.log(event.currentTarget);

    copyRecords.deleteCopyRecord(index);
    
    wx.showToast({
      title: '删除成功',
      icon: 'success',
      duration: 1000
    });

    this.onLoad();

  },
  copyRecord:function(event){

    var index = event.currentTarget.dataset.key ;
    var item = this.data.items[index];

    wx.setClipboardData({
      data: item.recordStr ,
      success() {
        wx.showToast({
          title: '复制成功',
          icon: 'success',
          duration: 1000
        });
      }
    });

  },
  onLoad:function(){
    var records  = copyRecords.getAllCopyRecords();
    this.setData({
      items: records.items
    });
  }
})
