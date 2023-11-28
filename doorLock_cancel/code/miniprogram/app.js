//app.js
var dynPWD = require("utils/user_password_dynamic.js");
var saveKey = "lockObject";
wx.cloud.init();
const db = wx.cloud.database();
const user_lock = db.collection('user_lock');
const t_user_info = db.collection('user_info');
const user_data = null;
var user_info = null;
var openid = null;
App({
    /**
     * 启动事件
     */
    onLaunch: function () {
      if (!wx.cloud) {
        console.error('请使用 2.2.3 或以上的基础库以使用云能力')
      } else {
        wx.cloud.init({
          // env 参数说明：
          //   env 参数决定接下来小程序发起的云开发调用（wx.cloud.xxx）会默认请求到哪个云环境的资源
          //   此处请填入环境 ID, 环境 ID 可打开云控制台查看
          //   如不填则使用默认环境（第一个创建的环境）
          // env: 'my-env-id',
          traceUser: true,
        })
      }
      this.globalData = {};
      
      
      // 获取用户信息
      wx.getSetting({
        success: res => {
          if (res.authSetting['scope.userInfo']) {
            // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
            wx.getUserInfo({
              success: res => {
                user_info = res.userInfo;
                
              }
            })
          }
        }
      })
      
      // 调用云函数，获得OPENID
      wx.cloud.callFunction({
        name: 'login',
        data: {},
        success: res => {
          console.log('[云函数] [login] user openid: ', res.result.userInfo.openId );
// wx.showToast({
//   title: '' + res.result.userInfo.openId ,
// })
          openid = res.result.userInfo.openId;
// var sum = 0;
// for (var i = 0; i < openid.length ; i++){
//   sum += openid.charCodeAt(i);
// console.log("openid[%d]: %d" , sum);
// }
          this.globalData.openid = openid;
          t_user_info.where({_id:openid}).get().then(res => {
              if(res.data.length == 0){
                //增加用户表数据
                t_user_info.add({
                  data: {
                    _id: openid, 
                    userinfo: user_info
                  }
                });
              }else{
                // 更新数据，暂时不更新
                // t_user_info.doc(openid).update({
                //   data:{
                //     userinfo: res.userInfo
                //   }
                // });
              }
            });

        },
        fail: err => {
console.error('[云函数] [login] 调用失败', err)
        }
      })

    },

    /**
     * 保存数据
     */
    saveData: function(key,item) {
      var lockObject = null
      try {
        lockObject = wx.getStorageSync(saveKey);
      }catch(e){
        wx.showToast({
          title: '获取本地存储数据失败',
          icon : 'none',
          duration: 2000
        })
      }
      if (lockObject == null || lockObject == 'undefined' || lockObject.length == 0){
        lockObject = {"count":0,"items":[]} ;
      }

      if (key != null && item != null){
        item.index = new Date().getTime();


        lockObject.items.push(item);
        lockObject.count = lockObject.count + 1;
      }


      wx.setStorage({
        key: saveKey,
        data: lockObject,
      });

      this.updateServerData(openid, lockObject);
    },

  /**
   * 更新数据
   */
  updateData: function(item){
    var lockObject = wx.getStorageSync(saveKey);
    var itemList = lockObject.items;
    //找到数据后删除
    for (var i = 0; i < itemList.length; i++) {
      if (itemList[i].index == item.index) {
        itemList[i] = item;
        break;
      }
    }

    // 重新设置存储
    wx.setStorage({
      key: saveKey,
      data: lockObject,
    });
    this.updateServerData(openid, lockObject);

  },
    /**
     * 删除数据
     */
    deleteData: function(index) {
      var lockObject = wx.getStorageSync(saveKey);
      var itemList = lockObject.items;
      //找到数据后删除
      for (var i = 0; i < itemList.length; i++) {
        if (itemList[i].index == index) {
          itemList.splice(i, 1);
          lockObject.count = lockObject.count - 1 ;
          break;
        }
      }

      // 重新设置存储
      wx.setStorage({
        key: saveKey,
        data: lockObject
      });
console.log(openid+","+lockObject);
      this.updateServerData(openid, lockObject);

    },
    /**
     * 获取全部数据
     */
    getAllData: function () {
      var lockObject = null;
      try {
        lockObject = wx.getStorageSync(saveKey);
      } catch (e) {
        wx.showToast({
          title: '获取本地存储数据失败',
          icon: 'none',
          duration: 2000
        })
      }

      if (lockObject == null || lockObject == 'undefined' || lockObject == '') {
        lockObject = { "count": 0, "items": [] };
      }
      return lockObject.items;
    },
    /**
     * 根据索引获取指定数据
     */
    findDataByIndex: function(index){
      var lockObject = wx.getStorageSync(saveKey);
      var itemList = lockObject.items;
      //找到数据后删除
      for (var i = 0; i < itemList.length; i++) {
        if (itemList[i].index == index) {
          return itemList[i];
        }
      }
    },
  /**
   * 更新云端数据
   */
  updateServerData: function (openid, lockObject){
    //查找是否已有数据
    user_lock.where({
      _openid: openid
    }).get({
      success: function (res) {
        // res.data 是包含以上定义的两条记录的数组
        if (res.data.length == 0) {
          //新增数据
          user_lock.add({
            data:{
              _id:openid,
              lockObject: lockObject
            },
            success: function (res) {
              console.log("user_lock.add.success:"+res)
            },
            fail: function (res) {
              console.log("user_lock.add.fail:" + res)
            }
          });
        }else{
          user_lock.doc(openid).update({
            data: {
              lockObject: lockObject
            },
            success: function (res) {
              console.log("user_lock.update.success:"+res.data)
            }
          });

        }
      }
    })
  },
  /**
   * 获取用户openid
   */
  getUserOpenid: function() {
    console.log("getUserOpenid......");
    // 调用云函数
    wx.cloud.callFunction({
      name: 'login',
      data: {},
      success: res => {
        console.log('[云函数] [login] user openid: ', res.result.openid);
        app.globalData.openid = res.result.openid
      },
      fail: err => {
        console.error('[云函数] [login] 调用失败', err)
      }
    })
  },
  /**
   * 日期格式化
   */
  dateFromat: function(fmt,date) {
    var o = {
      "M+": date.getMonth() + 1,                 //月份 
      "d+": date.getDate(),                    //日 
      "h+": date.getHours(),                   //小时 
      "m+": date.getMinutes(),                 //分 
      "s+": date.getSeconds(),                 //秒 
      "q+": Math.floor((date.getMonth() + 3) / 3), //季度 
      "S": date.getMilliseconds()             //毫秒 
    };
    if (/(y+)/.test(fmt)) {
      fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
      if (new RegExp("(" + k + ")").test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      }
    }
    return fmt;
  }       
})