//about.js
//获取应用实例
var app = getApp()
Page({
  data: {
    logosrc: '',
    adrimg: '../../images/icon-home.png',
    clockimg: '../../images/icon-name.png',
    phoneimg: '../../images/icon-tele.png',
    jtimg: '../../images/icon-jt.png',
    picimg: '../../images/icon-pic.png',
    imgUrls: [
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3653754569,3662985299&fm=11&gp=0.jpg',
      'https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1100501207,3154863031&fm=26&gp=0.jpg',
      'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2367733610,1650606848&fm=26&gp=0.jpg'
    ],
    indicatorDots: false,
    autoplay: true,
    interval: 5000,
    duration: 1000
  },
  calling: function () {
    wx.makePhoneCall({
      phoneNumber: '4000019651',
    })
  },
  getLocation: function () {
    wx.openLocation({
      latitude: 29.53,
      longitude: 106.57,
      name: "南岸区城市之光",
      address: "重庆市南岸区城市之光30楼",
      scale: 28
    })
  },
  onLoad: function (options) {
    // 页面初始化 options为页面跳转所带来的参数
  },
  onReady: function () {
    // 页面渲染完成
  },
  onShow: function () {
    // 页面显示
  },
  onHide: function () {
    // 页面隐藏
  },
  onUnload: function () {
    // 页面关闭
  }
})
