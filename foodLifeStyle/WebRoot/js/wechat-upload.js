//在引用此js之前，请先引入js\wechat\jweixin-1.0.0.js，本js是基于微信的相关上传功能
//1、20161110：实现图片的上传，预览和下载功能
var images = {
	localId: [],//本地预览id
	serverId: []//服务器id
};
wx.ready(function () {
//**************************************************IMG-UPLOAD-START******************************************************//
//拍照、本地选图，定义图片对象

/**
 * 选择图片
 */
//拍照或从手机相册中选图接口  
document.querySelector('#chooseImage').onclick = function () {
	var check_msg_box = new CommentCheckMsgBox();
	if(check_msg_box.checkMsgBoxHeight()){
		images.serverId = [];
		 wx.chooseImage({  
			 	count: 3,
		        sizeType: ['compressed'], // 可以指定是原图还是压缩图，默认二者都有  
		        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有  
		        success: function (data) {
		        	var localIdList = data.localIds;
		        	if(localIdList.length > 0){
//		        		syncUpload(data.localIds);
		        			for(var n=0;n<localIdList.length;n++){
			        			$(".comment_upload_img_con").append("<div style='float:left;position: relative;'>" +
			        					"<input class='commnet_upload_img_temp_src' type='hidden' value='"+localIdList[n]+"'/>" +
			        							"<span class='comment_remove_img' onclick='removeImg(this);'></span>" +
			        							"<img class='commnet_upload_img_temp' src='"+localIdList[n]+"'></img></div>");
			        			if(!check_msg_box.checkMsgBoxHeight()){
			        				break;
			        			}
			        		}
		        	}
		        },  
		        fail: function (res) {  
		            alterShowMessage("操作提示", JSON.stringify(res), "1", "确定", "", "", "");  
		        }  

		    });
	}
     
};



});
function syncUpload(localIds){
	if(localIds != null && localIds != ""){
		if (localIds.length == 0) {
		      alert('请先选择图片');
		      return;
		    }
		    var i = 0, length = localIds.length;
		    images.serverId = [];
		    function upload() {
		      wx.uploadImage({
		        localId: localIds[i],
		        isShowProgressTips: 0, // 默认为1，显示进度提示
		        success: function (res) {
		          i++;
		          $('input[class=commnet_upload_img_temp_src]').each(function(e,item){
		        	  if($(item).val() == localIds[i-1]){
		        		  $(item).val(res.serverId);
		        	  }
		          });
		          images.serverId.push(res.serverId);
		          if (i < length) {
		            upload();
		          }
		        },
		        fail: function (res) {
		          alert(JSON.stringify(res));
		        }
		      });
		    }
		    upload();
	}
	}
function CommentUploadImg(){}
CommentUploadImg.prototype = {
		syncUpload : function(localIds){
			if(localIds != null && localIds != ""){
				if (localIds.length == 0) {
				      alert('请先选择图片');
				      return;
				    }
				    var i = 0, length = localIds.length;
				    images.serverId = [];
				    function upload() {
				      wx.uploadImage({
				        localId: localIds[i],
				        success: function (res) {
				          i++;
				          $('input[class=commnet_upload_img_temp_src]').each(function(e,item){
				        	  if($(item).val == localIds[i-1]){
				        		  $(item).val(res.serverId);
				        	  }
				          });
				          images.serverId.push(res.serverId);
				          if (i < length) {
				            upload();
				          }
				        },
				        fail: function (res) {
				          alert(JSON.stringify(res));
				        }
				      });
				    }
				    upload();
			}
		},
		downloadImg : function(images){
			if (images.serverId.length === 0) {
			      alert('请先使用 uploadImage 上传图片');
			      return;
			    }
			    var i = 0, length = images.serverId.length;
			    alert("downloadImg"+images.serverId[i]);
			    images.localId = [];
			    function download() {
			      wx.downloadImage({
			        serverId: images.serverId[i],
			        success: function (res) {
			          i++;
			          alert('已下载：' + i + '/' + length+"--"+res.localId);
			          images.localId.push(res.localId);
			          if (i < length) {
			            download();
			          }
			        }
			      });
			    }
			    download();
		}
};
//**************************************************IMG-UPLOAD-END********************************************************//
