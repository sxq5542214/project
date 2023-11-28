var app = getApp();

Page({
  data: {
    item: {
      lockkey: "",
      lockname: "",
      create_time: "",
      create_date: "",
      update_time: ""
    
    },
    key:"",
    openid: "",
    isNew: false,
    focus: true
  },
  onSubmit: function (e) {
    var lockname = e.detail.value.lockname;
    var lockkey = e.detail.value.lockkey;

    if(lockname == null || lockname == ''){
      wx.showToast({
        title: '请输入门锁名称',
        icon: 'none',
        duration: 2000
      })
    } else {
      if(lockkey.length < 6 || lockkey.length >10){
        wx.showToast({
          title: "输入的密钥需要在6-10位之间",
          icon: "none"
        });
        return ;
      }

      this.data.item.lockname = lockname;
      this.data.item.lockkey = lockkey;
      this.data.item.create_time = new Date();
      this.data.item.create_date = app.dateFromat("yyyy-MM-dd", new Date());
      console.log("lockname:" + this.data.item.lockname);
      app.saveData(lockname, this.data.item);

      wx.showToast({
        title: "保存成功",
        duration: 2000
      });

      setTimeout(function(){
        wx.navigateBack({
          delta: 1
        })
      },2000);


    }

  },

  onLoad:function(){

    // 初始化随机的管理密码
    // var key = this.getRndInteger(99999999, 10000000);
    this.setData({
      // key : key ,
      openid: app.globalData.openid
    });
  },
  getRndInteger: function (min, max) {
    return Math.floor(Math.random() * (max - min + 1)) + min;
  }
})
