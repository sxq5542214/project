var app = getApp();
var totp = require("../../utils/totp.js");
var confuse = require("../../utils/confuse.js");
var confuseSEQ = require("../../utils/confuse_seq.js");
var copyRecords = require("../../utils/copyRecords.js");
var dynPWD = require("../../utils/user_password_dynamic.js");
var start_time = new Object();
var end_time = new Object();
var expire_date = new Object();

var Dates = new Date();
var Y = Dates.getFullYear();
var M = Dates.getMonth() + 1;
var D = Dates.getDate();
var nowDate = Y + (M < 10 ? "-0" : "-") + M + (D < 10 ? "-0" : "-") + D;

Page({
  data: {
    items: [],
    lockName:"请先选择门锁",
    objectMultiArray: [0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24],
    notsOpenNumberArray: [10,9,8,7,6,5,4,3,2,1],
    dynamicSecret:"",
    dynamicSecret2:"",
    dynamicSecret3:"",
    dynamicSecret4:"",
    limitTime:"",
    limitDate: "请点击下方门锁，生成密码",
    limitDate4: "请点击下方门锁，生成密码",
    indexKey: "",
    startDateSlot: "请点击选择起始日期",
    endDateSlot: "请点击选择截止日期",
    endDateTimeSlot: "请点击选择截止日期",
    startTimeSlot: "起始时间",
    endTimeSlot: "截止时间",
    currentTab: 0,
    winWidth: 0,
    winHeight: 0,
    chooseKey: 0,
    lockNameOpacity : 0.5,
    notsOpenNumber : "请点击选择开锁次数",
    nowDate : nowDate
  },

  /**
   * 首次渲染事件
   */
  onShow: function() {

    var dataList = app.getAllData();
    if (dataList != null){
      this.setData({
        items: dataList
      });
      // 获取数据
    }
  },
  onLoad: function (options) {
    var page = this;
    wx.getSystemInfo({
      success: function (res) {
        console.log(res);
        page.setData({ winWidth: res.windowWidth });
        page.setData({ winHeight: res.windowHeight });
      },
    });

  },
  /**
   * 新增笔记事件
   */
  onNewItem: function(event) {
    wx.navigateTo({
      url: "../savelock/index"
    })
  },

  /**
   * 编辑笔记事件
   */
  onEditItem: function(event) {
    wx.navigateTo({
       url: '../edit/index?index=' + event.currentTarget.dataset.key
    })
  },

  /**
   * 获取数据事件
   */
  onLoadData: function() {
    console.log("index onLoadData....");
  },

  /**
   *  日期段密码的起始时间选择
   */
  bindStartDateSlotChange: function (event) {
    this.setData({
      startDateSlot: event.detail.value,
      dynamicSecret3 : ""
    })
  },

  /**
   * 日期段密码的结束时间选择
   */
  bindEndDateSlotChange: function (event) {
   
    this.setData({
      endDateSlot: event.detail.value ,
      dynamicSecret3 : ""
    })
  },
  /**
   * 生成日期段密码
   * @param {} event 
   */
  generateSDEDPWD : function(event){
    
    var index = this.data.indexKey;
    if(index == null || index == ''){
      wx.showToast({
        title: '请先点击下方需要生成动态密码的门锁',
        icon: 'none'
      });
      return ;
    }else{
      var item = app.findDataByIndex(index);
      var key = item.lockkey;
    }

    var startDate = this.data.startDateSlot;
    var endDate = this.data.endDateSlot;
    if (startDate.length != 10 || endDate.length != 10 ) {
      wx.showToast({
        title: '请选择起始日期或截止日期',
        icon: 'none'
      });
      return;
    }else if( startDate > endDate){
      wx.showToast({
        title: '起始日期需要小于截止日期',
        icon: 'none'
      });
      return;
    } else {

      var dateStart = new Date(startDate);
      var dateEnd = new Date(endDate);
      var param = new Object();
      param.start_date = new Object();
      param.expire_date = new Object();
      param.start_date.year = dateStart.getFullYear();
      param.start_date.month = dateStart.getMonth() +1;
      param.start_date.day = dateStart.getDate();
      param.expire_date.year = dateEnd.getFullYear();
      param.expire_date.month = dateEnd.getMonth() +1;
      param.expire_date.day = dateEnd.getDate();

      var pwd0 = dynPWD.generateSDEDPWD(key, param ,0).trim();
      var pwd1 = dynPWD.generateSDEDPWD(key, param ,1).trim();

      var formatPassword0 = pwd0.substr(0,4) +" " + pwd0.substr(4,4) +" " + pwd0.substr(8,4) +" " + pwd0.substr(12) ;
      var formatPassword1 = pwd1.substr(0,4) +" " + pwd1.substr(4,4) +" " + pwd1.substr(8,4) +" " + pwd1.substr(12) ;

      this.setData({
        dynamicSecret3 : "开锁码：" + formatPassword0 +" \r\n取消码：" + formatPassword1,
      });

      
      var limitStr = "有效期为：【 " + this.data.startDateSlot  +" 至 "+ this.data.endDateSlot +" 】";
      var lockName = this.data.lockName ;
      var str = "您的【" +lockName +"】"+ limitStr+"\r\n开锁码：" + formatPassword0 +" \r\n 取消码：" + formatPassword1 ;
      copyRecords.saveCopyRecord(str,formatPassword0,"日期段密码",str,lockName,item.index);
    }
  },
  /**
   * 时间段密码的结束时间选择
   */
  bindEndDateTimeSlotChange: function (event) {

      this.setData({
        endDateTimeSlot: event.detail.value,
        dynamicSecret2 : ""
      })
    
  },
  bindStartTimeChange: function (e) {
    var hour = this.data.objectMultiArray[ e.detail.value] ;
   
    start_time.hour = parseInt(hour);
    this.setData({
      startTimeSlot: hour+":00",
      dynamicSecret2 : ""
    })
  },
  bindEndTimeChange: function (e) {
    var hour = this.data.objectMultiArray[ e.detail.value] ;
    end_time.hour = parseInt(hour);
    this.setData({
      endTimeSlot: hour +":00",
      dynamicSecret2 : ""
    })
  },

  /**
   *  次数密码次数修改事件
   * @param {*} event 
   */
  bindNotsOpenNumberChange:function(event){
console.log(event.detail.value  )
    var value = this.data.notsOpenNumberArray[event.detail.value];

    this.setData({
      notsOpenNumber: value
    }) 
    this.showSecret4( null );

  },

  /**
   * 
   * @param {生成时间段密码} event 
   */
  generateEDTQPWD :function(event){
    var index = this.data.indexKey;
    if(index == null || index == ''){
      wx.showToast({
        title: '请先点击下方需要生成动态密码的门锁',
        icon: 'none'
      });
    }else{
      var item = app.findDataByIndex(index);
      var key = item.lockkey;

      if(this.data.endDateTimeSlot.length != 10){
        wx.showToast({
          title: '请先选择截止日期',
          icon: 'none'
        });
        return ;
      }
      if(this.data.endTimeSlot.length > 5 || this.data.startTimeSlot.length > 5){
        wx.showToast({
          title: '请先选择起始时间和截至时间',
          icon: 'none'
        });
        return ;
      }
 
      if( parseInt(this.data.endTimeSlot.split(":")[0]) <= parseInt(this.data.startTimeSlot.split(":")[0]) ){
        wx.showToast({
          title: '截止时间必须大于起始时间',
          icon: 'none'
        });
        return ;
      }


      // 计算密码
      var param = new Object();
      param.expire_date = new Object();
      param.expire_date.year = parseInt(this.data.endDateTimeSlot.split('-')[0]);
      param.expire_date.month = parseInt(this.data.endDateTimeSlot.split('-')[1]);
      param.expire_date.day = parseInt(this.data.endDateTimeSlot.split('-')[2]);
      param.st_hour = parseInt(this.data.startTimeSlot.split(":")[0]);
      param.et_hour = parseInt(this.data.endTimeSlot.split(":")[0]);
      var pwd0 = dynPWD.generateEDTQPWD(key, param ,0).trim();
      var pwd1 = dynPWD.generateEDTQPWD(key, param ,1).trim();

      var formatPassword0 = pwd0.substr(0,4) +" " + pwd0.substr(4,4) +" " + pwd0.substr(8,4) +" " + pwd0.substr(12) ;
      var formatPassword1 = pwd1.substr(0,4) +" " + pwd1.substr(4,4) +" " + pwd1.substr(8,4) +" " + pwd1.substr(12) ;

      this.setData({
        // dynamicSecret2: formatPassword0,
        dynamicSecret2: "开锁码：" + formatPassword0 +" \r\n取消码：" + formatPassword1
      })
     
      
      var lockName = this.data.lockName ;
       var  limitStr = "锁【"+item.lockname+"】的有效期为：【 " + this.data.endDateTimeSlot  +"前每天"+ this.data.startTimeSlot +"至"+ this.data.endTimeSlot +"】 \r\n 开锁码：" + formatPassword0 +" \r\n 取消码：" + formatPassword1

      // var str = "您的【" + lockName+"】开锁密码为：【" + formatPassword + "】  "+limitStr;
       copyRecords.saveCopyRecord(limitStr,formatPassword0,"时间段密码",limitStr,lockName,item.index);
    }
  },

/**
 *  生成动态密码，新版 以日期、时间生成临时密码
 */
  showSecret4 : function(event){
    var index = null; 
    if(event == null){
      index = this.data.indexKey;
    }else{
      index = event.currentTarget.dataset.key;
    }
    if (index == null || index == '') {
      wx.showToast({
        title: '请先从下方点击需要生成动态密码的门锁',
        icon: 'none'
      });
    } else {
      if(this.data.notsOpenNumber == '请点击选择开锁次数'){
        this.setData({ notsOpenNumber : 10 });
      } 

      var item = app.findDataByIndex(index);
      var key = item.lockkey;
console.log("key...." + key +"," + this.data.notsOpenNumber) ;      
      // 计算有效时间
      var curDate = new Date();
      var param = new Object();
      param.date_time = new Object();
      param.date_time.time = new Object();
      param.date_time.date = new Object();
      param.nbr_of_times = this.data.notsOpenNumber;
      param.date_time.date.year = curDate.getFullYear();
      param.date_time.date.month = curDate.getMonth() +1 ;
      param.date_time.date.day = curDate.getDate() ;
      param.date_time.time.hour = curDate.getHours();
      param.date_time.time.minute = curDate.getMinutes();
      var pwd0 = dynPWD.generateNOTSPWD(key, param ,0).trim();
      var pwd1 = dynPWD.generateNOTSPWD(key, param ,1).trim();

      //调用算法
      // var password = confuseSEQ.generateSEQPWD(key, date_time)
      // console.log("key[%s] , date_time[%s] ,pass[%s]]", key, date_time, password); 
      // console.log(date_time );

      var formatPassword0 = pwd0.substr(0,4) +" " + pwd0.substr(4,4) +" " + pwd0.substr(8,4) +" " + pwd0.substr(12) ;
      var formatPassword1 = pwd1.substr(0,4) +" " + pwd1.substr(4,4) +" " + pwd1.substr(8,4) +" " + pwd1.substr(12) ;
      var limitDate4 =  "开锁码：" +formatPassword0 +" \r\n 取消码：" + formatPassword1 +" \r\n 可开 "+ param.nbr_of_times +" 次【"+ item.lockname + "】锁"
      this.setData({
        lockName: item.lockname,
        dynamicSecret4: formatPassword0,
        limitDate4: limitDate4 ,
        indexKey: index,
        // chooseKey: event.currentTarget.id,
        lockNameOpacity: 0.9
      })

      copyRecords.saveCopyRecord(limitDate4,formatPassword0,"次数密码",limitDate4,item.lockname,item.index);
    }
  },

  /**
   * 临时密码，查看密钥 生成动态密码， 旧版 TOTP算法
   */
  showSecret: function(event){
    var index = event.currentTarget.dataset.key;
  
 
    if(index == null || index == ''){
      wx.showToast({
        title: '请先从下方点击需要生成动态密码的门锁',
        icon: 'none'
      });
    }else{

      var item = app.findDataByIndex(index);
      var key = item.lockkey;
      var lastTime = this.data.limitTime ;
      // 计算有效时间
      var time = new Date().getTime();
      // if (lastTime == null || lastTime == '' || (new Date().getTime() - lastTime >= 0)) {
        var expDateTime = time + (3 * 60 * 1000); //3分钟
        lastTime = expDateTime;
      // }


      var date = app.dateFromat("yyyy-MM-dd hh:mm:ss", new Date(lastTime));
      //调用算法
      var password = totp.getTOTPCode(key);
      
      var formatPassword = password.substr(0,4) +" " + password.substr(4,4) +" " + password.substr(8) ;
      this.setData({
        lockName: item.lockname,
        dynamicSecret: formatPassword,
        limitDate: "请将锁的时间与手机时间保持一致 \r\n"+ date +" 前单次有效" ,
        indexKey: index ,
        limitTime: lastTime,
        chooseKey :event.currentTarget.id ,
        lockNameOpacity : 0.9
      });
      
//  console.log( (new Date().getTime() - lastTime )  +","+time +","+ lastTime +","+ this.data.limitDate);
    // var codeStr = "";
    // for(var i = 0 ; i < code.length ; i++){
    //   codeStr += code.charAt(i) +"  ";
    // }
    // wx.showModal({
    //   title: codeStr,
    //   content: '初次生成密码的3分钟内有效',
    //   showCancel: false,
    //   success(res) {
    //     if (res.confirm) {

    //     } 
    //   }
    // })
    }


  },
  copySecret: function(){
    var secret = this.data.dynamicSecret;
    if(secret == null || secret == ''){
      wx.showToast({
        title: '没有密码可以复制',
        icon: 'none'
      })
    }else{
      var str = "您的【" + this.data.lockName+"】开锁密码为：【" + secret + "】, " + this.data.limitDate  +"";
      wx.setClipboardData({
        data: str,
        success() {
          wx.showToast({
            title: '复制成功',
            icon: 'success',
            duration: 1000
          })
        }
      });
    }
  },

  copySecret2: function(){
    var secret = this.data.dynamicSecret2;
    if(secret == null || secret == ''){
      wx.showToast({
        title: '没有密码可以复制',
        icon: 'none'
      })
    }else{
      var lockName = this.data.lockName ;
      var limitStr = "有效期为：【 " + this.data.endDateTimeSlot  +"前每天"+ this.data.startTimeSlot +"点至"+ this.data.endTimeSlot +"点】";
      var str = "您的【" + lockName+"】【" + secret + "】  "+limitStr;
      wx.setClipboardData({
        data: str,
        success() {
          wx.showToast({
            title: '复制成功',
            icon: 'success',
            duration: 1000
          });
        }
      });
    }
  },
  
  copySecret3: function(){
    var secret = this.data.dynamicSecret3;
    if(secret == null || secret == ''){
      wx.showToast({
        title: '没有密码可以复制',
        icon: 'none'
      })
    }else{
      var limitStr = "有效期为：【 " + this.data.startDateSlot  +" 至 "+ this.data.endDateSlot +" 】";
      var lockName = this.data.lockName ;
      var str = "您的【" +lockName +"】【" + secret + "】  " + limitStr; 
      wx.setClipboardData({
        data: str,
        success() {
          wx.showToast({
            title: '复制成功',
            icon: 'success',
            duration: 1000
          });
        }
      });
    }
  },
  
  copySecret4: function(){
    var secret = this.data.dynamicSecret4;
    console.log(11111111);
    if(secret == null || secret == ''){
        console.log(2222222);
      wx.showToast({
        title: '没有密码可以复制',
        icon: 'none'
      })
    }else{
      // var str = "您的【" + this.data.lockName+"】开锁密码为：【" + secret + "】" ;
      // + this.data.limitDate4  +"";

      console.log(333333);
      var str = this.data.limitDate4 ;
      console.log(444444);
      wx.setClipboardData({
        data: str,
        success() {
            console.log(555555);
          wx.showToast({
            title: '复制成功',
            icon: 'success',
            duration: 1000
          })
          console.log(6666666);
        }
      });
    }
  },
  /**
   * 下拉刷新事件, 数据同步
   */ 
  onPullDownRefresh: function() {
    wx.showToast({
      title: '正在同步数据',
      icon: 'loading'
    });

    // 临时变量
    var tempData = this.data.items;
  },
  switchNav: function (e) {
    var page = this;
    if (this.data.currentTab == e.target.dataset.current) {
      return false;
    }
    else {
      page.setData({ currentTab: e.target.dataset.current });
    }
  },
  bindChange: function (e) {
    var that = this;
    that.setData({
      currentTab: e.detail.current
    });
  },
  showCopyRecordList : function(e){
    wx.navigateTo({
      url: '../copyRecord/index' 
   })
  }
})
