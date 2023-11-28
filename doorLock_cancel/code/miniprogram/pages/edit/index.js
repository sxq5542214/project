var app = getApp();
var copyRecords = require("../../utils/copyRecords.js");

Page({
  data: {
        item: {
          lockname: "",
          create_time: "",
          create_date: "",
          update_time: ""
        },
        items: [],
        currentIndex: -1,
        isNew: false,
        focus: true
    },

    /**
     * 页面首次加载事件
     */
    onLoad: function(options) {
      this.data.currentIndex = options.index;
    },

    
  deleteRecord : function(event){
    var index = event.currentTarget.dataset.key ;

    copyRecords.deleteCopyRecord(index);
    
    wx.showToast({
      title: '删除成功',
      icon: 'success',
      duration: 1000
    });

    this.onShow();

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

    /**
     * 页面渲染事件
     */
    onShow: function() {
//        this.loadData(this.data.item.key);        
      var dataList = app.getAllData();
      var index = this.data.currentIndex;
      for(var i = 0; i < dataList.length ; i ++){
        if(dataList[i].index == index){
          this.setData({
            item: dataList[i]
          });
        }
      }
      
      var arrays = [];
      var records  = copyRecords.getAllCopyRecords();
      for(var i = 0 ; i < records.items.length ; i++){
        if(records.items[i].index == this.data.item.index){
          arrays.push(records.items[i]);
        }
      }
      this.setData({
        items: arrays
      });
    },
    
    /**
     * 保存数据事件
     */
  onSubmit: function (event) {
    var lockname = event.detail.value.lockname;
    var lockkey = event.detail.value.lockkey;
    
    if(lockkey.length < 6 || lockkey.length >10){
      wx.showToast({
        title: "输入的密钥需要在6-10位之间",
        icon: "none"
      });
      return ;
    }
    
    var newData = this.data.item;
    newData.lockname = lockname;
    newData.lockkey = lockkey;
      newData.update_time = new Date();
        this.setData({
          item: newData
        });
      app.updateData(newData);

      wx.showToast({
        title: "修改成功",
        duration: 1500
      });

      setTimeout(function () {
        wx.navigateBack({
          delta: 1
        })
      }, 1500);
    },
    
    /**
     * 请求服务器保存数据
     */
    saveData: function() {
        var item = this.data.item;
        var now = Date.parse(new Date()) / 1000;
        item.update_time = now;
        this.setData({
            item: item
        });
    },

    /**
     * 删除事件
     */
  onDelete: function (event) {
    var index = this.data.item.index;
    
      wx.showModal({
        title: '提示',
        content: '确定要删除这个门锁吗？',
        success(res) {
          if (res.confirm) {
            app.deleteData(index);
            //弹框提示
            wx.showToast({
              title: "删除成功",
              duration: 1500
            });
            //过段时间后跳转
            setTimeout(function () {
              wx.navigateBack({
                delta: 1
              })
            }, 1500);
          } else if (res.cancel) {
            console.log('用户点击取消')
          }
        }
      })
      
    },

    /**
     * 获取数据
     */
    loadData: function(key) {
        
    }
});