var flag = "nowpage_blogList_owner";
var bag_height = 0;
var check_box = new CommentCheckMsgBox();
var replyDiv = "";
var conut = 0;
$(document).ready(function() {
	check_box = new CommentCheckMsgBox();
	check_box.checkMsgBoxHeight();
	//tab宽度自适应,数据自加载
	var tabNumber = 0;
	$(".comment_tab").each(function(n,item){
		$(item).css('background-color','rgb(255, 255, 255)');
		tabNumber ++;
	});
	var itemW = ($("#comment_title").width())/(tabNumber+0.5);
	$(".comment_tab").css("width",itemW+"px");
	$(".comment_tab").each(function(n,item){
		var comentTabId = $(item).attr("id");
		var tabList = "nowpage"+comentTabId.substring(comentTabId.indexOf("_"),comentTabId.length);
		//加载所有留言信息
		var commentId = getQueryString("commentId");
		ajaxData(commentId,tabList);
	});
	
	//tab框的点击事件判断
	$('body').on('click','.comment_tab',function(){
		
		$(".comment_tab").each(function(n,item){
			$(item).css('background-color','rgb(255, 255, 255)');
			var comentTabId = $(item).attr("id");
			var tabList = "#"+comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length);
			$(tabList).hide();
		});
		$(this).css('background-color','rgb(118, 207, 247)');
		comentTabId = $(this).attr("id");
		
		tabList = "#"+comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length);
		flag = "nowpage"+comentTabId.substring(comentTabId.indexOf("_"),comentTabId.length);
		$(tabList).show();
	});
	
	$("#tab_blogList_all").trigger("click");
	$('body').on('click', '.write_msg', function () {
		$(".comment_tab").each(function(n,item){
			var comentTabId = $(item).attr("id");
			var blog = "#"+comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length);
			if(bag_height < $(blog).height()){
				if($(blog).height() < 70){
					bag_height = 0;
				}else{
					bag_height = $(blog).height();
				}
			}
		});
		$("#comment_bag").css("height",bag_height+"px");
		$("#comment_bag").show();
		$(this).closest(".container").find(".msg_box_button").show();
		$(this).closest(".container").find(".msg_box").show();
		 document.getElementById('comment_textarea').focus();
	});
	$('body').on('click', '.pingl', function () {
		$(this).closest("li").find(".msg_box_button").show();
		$(this).parent().siblings(".remsg_div").show();
		$(this).parent().siblings(".remsg_div").find(".re_textarea").focus();
	});
	$('body').on('click', '.comment_replyuser', function () {
		prependReplyBox($(this).find("a").text(),$(this).closest("li"),true);
	});
	$('body').on('click', '.commentcancel', function () {
		$("#comment_bag").hide();
		$(this).parent(".msg_box").hide();
		$(this).siblings(".pingl").show();
		$(this).hide();
		$(this).siblings(".msg_box_button").hide();
		$(this).parent().siblings(".msg_box").hide();
		$(this).parent().siblings(".remsg_div").hide();
		 var height = $(".topnews").height();
	});
	//点赞事件
	$('body').on('click', '.zan,.zaned', function () {
		
		if($(this).hasClass("zan")){
			$(this).removeClass("zan");
			$(this).addClass("zan_loading");
			click_zan($("#user_open_id").val(),$(this).find(".parent_commentid").val(),0,$(this));
		}else{
			$(this).removeClass("zaned");
			$(this).addClass("zan_loading");
			click_zan($("#user_open_id").val(),$(this).find(".parent_commentid").val(),-1,$(this));
		}
	});
	$(document).on('click', '.loadmore', function () {
		var loadMore = $(this).find(".loading");//.
		$(this).parent().parent().find(".noShow").each(function(e){
			if($(this).is(":visible")){
				$(this).hide();
				$(loadMore).text("加载更多...");
			}else{
				$(this).show();
				$(loadMore).text("收起列表");
			} 
  		});  
	});
	
	$(".commit").live('click',function(){
		//检查是否有提交图片信息
//		var img_arr = "";
		var img_arr=new Array();
		$(".commnet_upload_img_temp_src").each(function(e,item){
//			img_arr = img_arr + "," + $(item).val();
			img_arr.push($(item).val());
		});
		var msgtext = $(this).parent().siblings().find("textarea").val();
		if(msgtext == "" || msgtext == null || msgtext.trim() == "" || msgtext.trim() == null){
			msgtext = $(this).siblings("#comment_textarea").val();
			if(msgtext == "" || msgtext == null || msgtext.trim() == "" || msgtext.trim() == null){
				alert("文本内容不可为空！");
				return false;
			}
		}
		if(!$(this).hasClass("commit")){
			alert("请勿重复提交！");
			return false;
		}
		$(this).removeClass("commit");
		var commitDemo = $(this);
		$(this).children().text("提交中");
		$(this).children().css("background","url(page/comment/images/loading16.gif) no-repeat right center");
		$(this).children().css("padding-right","20px");
		$(".commentcancel").hide();
		var openid = $("#user_open_id").val();
		var conf_code = $("#conf_code").val();
		var parentid = $(this).closest("ul").find(".parent_commentid").val();
		var replyid = $(this).closest("li").find(".commentid").val();
		//上传图片，并且提交留言信息
		syncUpload(img_arr,openid,msgtext,conf_code,parentid,replyid,commitDemo);
	});
	var obj = document.getElementById("t");
	var _getHeight = obj.offsetTop;

	$(".load_more").click(function(){
		var loadmore = $(this);
		setTimeout(function(){
		var idName = $(loadmore).siblings(".comment_for").val();
		ajaxData($("#commentId").val(),idName);
		},1000);
	});
		
	
	/**
	 * 点赞的异步请求
	 */
	function click_zan(openid,commentid,action,zan){
		var num = $(zan).find(".num").text();
		$.ajax({
			type : "POST",
			url : "comment/dealUserAgreeCommentInfo.do",
			//async : false,
			data : {"openid":openid,"commentid":commentid,"action":action},
			success : function(data){
				var result = eval("("+data+")");
				if(result.remark != null && result.remark != ""){
					$(zan).removeClass("zan_loading");
					$(zan).addClass("zan");
					var p = new CommentCallBack();
					//此处处理需要关注的提示信息
					p.toPage("comment_need_user_subscribe",result.remark);
				}else{
					if(action == 0){
						$(zan).removeClass("zan_loading");
						$(zan).addClass("zaned");
						num = num*1+1;
					}else if(action == -1){
						$(zan).removeClass("zan_loading");
						$(zan).addClass("zan");
						num = num*1-1;
					}
					$(zan).find(".num").text(num);
				}
			}
		});
	}
	//异步请求数据
function ajaxData(id,idName){
	var nowpage = $("#"+idName).val();
	//异步加载留言信息
	var comment = "";
	var code = $("#conf_code").val();
	var openid = "";
	openid = $("#user_open_id").val();
	//返回用户自己的留言信息
	if(idName != "nowpage_blogList_all"){
		if(openid == null || openid == ""){
			openid = "system_defult_no_openid";
		}
	}
	
	$.ajax({
		type : "POST",
		url : "comment/getCommentInfoList.do",
		data : {"code": code,"openid":$("#user_open_id").val(),"nowpage":nowpage,"id":id,"idName":idName},
		success : function(data) {
			if(data == "获取失败"){
				$('.load_more').unbind("click"); 
				$('.load_more').children().text("暂时没有留言");
				$('.load_more').children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
				$('.load_more').children().css("padding-right","20px");
				return false;
			}
			var result = eval("("+data+")");
			//加载页面数据
			if(result != null && result != "" & result.length > 0){
				var defalt_title = new CommentPageInit();
				if(idName == "nowpage_"+defalt_title.title_defalt_click){
					$("#tab_"+defalt_title.title_defalt_click).trigger("click");
				}
				$(".comment_tab").each(function(n,item){
					var comentTabId = $(item).attr("id");
					var tabList = "nowpage"+comentTabId.substring(comentTabId.indexOf("_"),comentTabId.length);
					if(idName == tabList){
						var bean = result[0];
						if(bean.remark == "NOSHOW"){
							$("#swrap").hide();
							return false;
						}
						for(var i = 0;i < result.length;i++){
							comment =comment + addComment(result[i],result[i].replyBeanList,"data");
							var nowpageId = "#"+tabList;
							$(nowpageId).val(result[i].nowpage);
						}
						var blogItemId = "#"+comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length);
						$(blogItemId).find(".load_more").before(comment);
						menuFixed("t",comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length));
					}
				});
			}else{
				$(".comment_for").each(function(){
					if($(this).val() == idName){
						$(this).siblings('.load_more').unbind("click"); 
						$(this).siblings('.load_more').children().text("已加载完全");
						$(this).siblings('.load_more').children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
						$(this).siblings('.load_more').children().css("padding-right","20px");
					}
				});
			}
			initPhotoSwipeFromDOM('.gallery');
		}
	});
}
	


function menuFixed(id,blogItemId){ 
    window.onscroll = function(){
    	$(".comment_for").each(function(n,item){
    		var comentTabId = $(item).val();
    		var tabList = "#"+comentTabId.substring(comentTabId.indexOf("_")+1,comentTabId.length);
//    		console.log($(tabList).height()+"=="+$(window).height())
    		if($(tabList).height() < $(window).height()){
    			$(tabList).children('.load_more').unbind("click"); 
    			$(tabList).children('.load_more').children().text("已加载完全");
    			$(tabList).children('.load_more').children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
    			$(tabList).children('.load_more').children().css("padding-right","20px");
    		}
    	});
        changePos(id,_getHeight,blogItemId);
    };
}
function changePos(id,height,blogItemId){
    var obj = document.getElementById(id);
    if(obj.offsetTop != 0 && obj.offsetTop >= _getHeight){
    	_getHeight = obj.offsetTop;
    }
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    $("#position").text(scrollTop);
//   console.log($(window).height()+"----"+scrollTop+"----"+height+"===="+document.body.scrollTop);
   $("#blogList_friend").css("min-height",(height*1)+"px");
   $("#blogList_all").css("min-height",(height*1)+"px");
   $("#blogList_owner").css("min-height",(height*1)+"px");

   if($(document).scrollTop() + $(window).height() == $(document).height()){
	   $(".comment_for").each(function(){
			if($(this).val() == flag){
				$(this).siblings(".load_more").trigger("click");
			}
		});
	  
   }
    if(scrollTop < height){
    	var hiddenbox = document.getElementById("hiddenbox");
    	hiddenbox.style.display="none";
        obj.style.position = 'relative';
        obj.style.width = '100%';
        var blogList = document.getElementById(blogItemId);
        blogList.style.paddingTop = '0px';
    }else{
    	var hiddenbox = document.getElementById("hiddenbox");
    	hiddenbox.style.display="block";
        obj.style.position = 'fixed';
        obj.style.width = '100%';
    }
}

});
//自定义回调函数，用于需要关注的跳转页面
function CommentCallBack(){
}
CommentCallBack.prototype = {
    toPage : function(data,msg){
    	//评论需要关注，参数为“comment_need_user_subscribe”
    	if(data == "comment_need_user_subscribe"){
    		//此处编辑逻辑代码
    		 alert(msg);
    		 
    	//点赞需要关注,参数为“comment_vote_need_user_subscribe”
    	}else if(data == "comment_vote_need_user_subscribe"){
    		alert(msg);
    	}else{
    		alert(data);
    	}
       
    }
};

function prependReplyBox(to_username,item,flag){
	if($(item).find(".remsg_div").length > 0 ){
		if($($(item).find(".remsg_div")[0]).is(":hidden")){
			$($(item).find(".remsg_div")[0]).show();
			$(item).find(".autor").children().show();
		}else{
			$($(item).find(".remsg_div")[0]).hide();
			$(item).find(".autor").children().hide();
		}
	}else{
		var box = "<p class='autor' style='margin-top: 2px;display:block;padding-bottom: 3px;'><span class='msg_box_button commit' style='display:block;  padding: 0px 10px; width: auto;'><a>提交</a></span><span class='msg_box_button commentcancel' style='display:block; padding: 0px 10px; width: auto;'><a>取消</a></span></p>" +
		"<div class='remsg_div' style='display:block;width:auto;position: relative;'><textarea placeholder=\"回复 "+to_username+"：\" class='re_textarea' name='textarea'rows='4' style='BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;    width: 96%;padding: 2%; border-radius: 4px;' />" +
		"</div>";
		$(item).append(box);
	}
}

function getQueryString(name) { 
	var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i"); 
	var r = window.location.search.substr(1).match(reg); 
	if (r != null) return unescape(r[2]); return null; 
	} 
//修改原型
//CommentCallBack.prototype.toPage = function(data){
//	//评论需要关注，参数为“comment_need_user_subscribe”
//    if(data == "comment_need_user_subscribe"){
//    	alert("需要关注（留言）已重写");
//    //点赞需要关注,参数为“comment_vote_need_user_subscribe”
//    }else if(data == "comment_vote_need_user_subscribe"){
//    	alert("需要关注（点赞）已重写");
//    
//    }else{
//    //此处代码不需要编辑，正常的提示信息
//    	alert(data);
//    }
//};


//初始化留言页面
function CommentPageInit(){
	this.title_name = ['我的留言','精选留言','好友留言'];
	this.title_code = ['blogList_owner','blogList_all','blogList_friend'];
	this.title_defalt_click = "blogList_owner";
	this.comment_openid = "";
	this.comment_id = "";
	this.comment_code = "";
}
CommentPageInit.prototype = {
    pageInit : function(){
    	//创建分页所需的nowpage input元素,表头，留言体的初始化
    	var comment_titles = "";
    	var comment_blogs = "<div id='comment_title_container'>";
    	var comment_top = "<article >"+
    	"<div class='l_box f_l'  style='background-color: white;'>"+
    	"<div id = 'hiddenbox' style='height:80px;width:100%;display:none'></div>"+
    	"<div class='topnews' id='t'>"+
    	"<input id='conf_code' type='hidden' value='"+this.comment_code+"'/>"+
    	"<input id='user_open_id' type='hidden' value='"+this.comment_openid+"'/>"+
    	"<input id='commentId' type='hidden' value='"+this.comment_id+"'/>";
    	for(var i=0;i<this.title_code.length;i++){
    		var comment_nowpage_input = "<input id='nowpage_"+this.title_code[i]+"' type='hidden' value='1'>";
    		comment_top = comment_top + comment_nowpage_input;
    		var comment_title = "<div class='comment_tab' id='tab_"+this.title_code[i]+"' style='background-color: rgb(118, 207, 247); width: 102.857px;'>"+this.title_name[i]+"</div>";
    		comment_titles = comment_titles + comment_title;
    		var comment_blog = "<div id = '"+this.title_code[i]+"' style='display:none'>"+
    							"<input class='comment_for' type='hidden' value='nowpage_"+this.title_code[i]+"'/>"+
    							"<div class='load_more'><span>加载中...</span></div></div>";
    		comment_blogs = comment_blogs + comment_blog;
    	}
    	comment_titles = comment_titles + "<div class='write_msg'></div>";
    	comment_titles = "<div class='container' ><div id='comment_title' style='height: 40px;width: 96%;padding: 0 2% 0 2%;border-bottom: rgb(255, 255, 255) 4px solid;'><div>"+
    	comment_titles + 
    	"</div></div><div class='msg_box'>" +
    	"<textarea placeholder='发表自己的留言' id='comment_textarea' name='textarea'rows='4' style='BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;    width: 96%;padding: 2%;height:95px;border-radius: 6px;    border: #B5B5B5 1px solid;'></textarea>" +
    	"<div style='position: absolute;' class='comment_upload_img_con'>"+
    	"</div>"+
    	"<div class='msg_box_button commit'><span>提交</span></div>" +
    	"<div class='msg_box_button commentcancel'><span>取消</span></div>" +
    	"<div class='comment_upload_img' id='chooseImage'><span></span></div>" +
    	"</div></div>";    	
    	comment_top = comment_top + comment_titles + "</div>";
    	comment_top = comment_top + comment_blogs + "</div></div><div id='comment_bag'></div></article>";
//    	console.log(comment_top);
    	$("#swrap").html(comment_top);
    	//追加弹出层框架
    	$("#swrap").after("<div class='pswp' tabindex='-1' role='dialog' aria-hidden='true'><div class='pswp__bg'></div><div class='pswp__scroll-wrap'><div class='pswp__container'><div class='pswp__item'></div><div class='pswp__item'></div><div class='pswp__item'></div></div><div class='pswp__ui pswp__ui--hidden'><div class='pswp__top-bar' style='opacity:0;'><div class='pswp__counter'></div><button class='pswp__button pswp__button--close' title='Close (Esc)'></button><button class='pswp__button pswp__button--share' title='Share'></button><button class='pswp__button pswp__button--fs' title='Toggle fullscreen'></button><button class='pswp__button pswp__button--zoom' title='Zoom in/out'></button><div class='pswp__preloader'><div class='pswp__preloader__icn'><div class='pswp__preloader__cut'><div class='pswp__preloader__donut'></div></div></div></div></div><button class='pswp__button pswp__button--arrow--left' title='Previous (arrow left)'></button><button class='pswp__button pswp__button--arrow--right' title='Next (arrow right)'></button><div class='pswp__caption'><div class='pswp__caption__center'></div></div></div></div></div>");
    }
};

function removeImg(img){
	$(img).parent().remove();
	check_box.checkMsgBoxHeight();
	}
function CommentCheckMsgBox(){
}
CommentCheckMsgBox.prototype={
		checkMsgBoxHeight : function(){
			var upload_ing_list = $("#comment_textarea").siblings(".comment_upload_img_con").children().find(".commnet_upload_img_temp");
			if(upload_ing_list.length >= 3){
				alert("提示：最多添加3张图片！");
				return false;
			}
			if(upload_ing_list.length > 0){
				var upload_img_height = $($(upload_ing_list)[0]).height()+10;
//				console.log(($("#comment_textarea").height()+"--"+upload_img_height));
				$("#comment_textarea").css("height",(95-(upload_img_height/2))+"px");
				$(".msg_box_button").css("margin-top",(upload_img_height+5)+"px");
				$(".msg_box_button").css("margin-top",(upload_img_height+5)+"px");
				$(".comment_upload_img").css("margin-top",(upload_img_height+5)+"px");
				$(".msg_box").css("height",(150+(upload_img_height/2))+"px");
			}else{
				$("#comment_textarea").css("height","95px");
				$(".msg_box_button").css("margin-top","0px");
				$(".comment_upload_img").css("margin-top","0px");
				$(".msg_box").css("height","150px");
			}
			return true;
		}
};

function addImg() {
            $(".comment_upload_img_con").append("<div style='float:left;position: relative;'><span class='comment_remove_img' onclick='removeImg(this);'></span><img class='commnet_upload_img_temp' src='http://img5.imgtn.bdimg.com/it/u=3814392514,3614119757&fm=11&gp=0.jpg'></img></div>");
            var check_msg_box = new CommentCheckMsgBox();
            check_msg_box.checkMsgBoxHeight();
}
function syncUpload(localIds,openid,msgtext,conf_code,parentid,replyid,commitDemo){
	var img_arr = "";
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
		          img_arr = img_arr + "," + res.serverId;
		          if (i < length) {
		            upload();
		          }else{
		        	  commitData(img_arr,openid,msgtext,conf_code,parentid,replyid,commitDemo);
		          }
		        },
		        fail: function (res) {
		          alert(JSON.stringify(res));
		        }
		      });
		    }
		    upload();
	}else{
		commitData(img_arr,openid,msgtext,conf_code,parentid,replyid,commitDemo);
	}
	}
	
/**
 * 留言的提交方法
 * @param img_arr
 * @param openid
 * @param msgtext
 * @param conf_code
 * @param parentid
 * @param replyid
 */
function commitData(img_arr,openid,msgtext,conf_code,parentid,replyid,commitDemo){
	$.ajax({
			type : "POST",
			url : "comment/toCommentInstert.do",
			data : {"openid": openid,"msgtext":msgtext,"conf_code":conf_code,"parentid":parentid,"replyid":replyid,"img_arr":img_arr},
			success : function(data) {
				if(data == "添加失败"){
					alert(data);
					return false;
				}
				$("#comment_bag").hide();
				var result = eval("("+data+")");
				var remark = result.remark;
				var id = result.id;
				if(id == "" || id == null){
					var p = new CommentCallBack();
					//此处处理需要关注的提示信息
					p.toPage("comment_vote_need_user_subscribe",remark);
					$(commitDemo).parent().siblings(".msg_box").hide();
					$(commitDemo).parent().siblings().find("textarea").val("");
					$(commitDemo).parent(".msg_box").hide();
					$(commitDemo).parent(".msg_box").find("textarea").val("");
				}else{
					$(commitDemo).children().text("提交成功");
					$(commitDemo).children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
					alert(remark);
					$(commitDemo).parent().siblings(".msg_box").hide();
					$(commitDemo).parent(".msg_box").hide();
					$(commitDemo).parent(".msg_box").find("textarea").val("");
				}
				setTimeout(function(){
					$(commitDemo).children().text("提交");
					$(commitDemo).children().css("background","");
					$(commitDemo).children().css("padding-right","0px");
					$(commitDemo).hide();
					$(commitDemo).parent().siblings(".remsg_div").hide();
					
					$(commitDemo).parent().siblings().find("textarea").val("");
					$(commitDemo).siblings(".write_msg").show();
					$(commitDemo).siblings(".pingl").show();
					$(commitDemo).addClass("commit");
					if(result.status == 1 || result.status == "1"){
						if(result.comment_parentid != null && result.comment_parentid != ""){
							var rename = "";
							if(result.parent_replyUserName != null && result.parent_replyUserName != ""){
								rename = "回复："+result.parent_replyUserName;	
							}
							$(commitDemo).closest("li").after("<li><div class='comment_replyuser'><h4><div class='rectangle1'style='background-size:32px 32px;background-image:url("+result.head_img+")'><input type='hidden' class='commentid' value="+result.id+"></div>" +
									"<a style='font-weight: 100;color: #607D8B;'>"+result.nick_name+rename+"</a></h4><div class='huifu'><p>'"+result.msgtext+"'</p>" +
									"</div></div></li>");
						}else{
							$("#blogList_owner").prepend(addComment(result,null,"temp"));
							$("#blogList_all").prepend(addComment(result,null,"temp"));
							$(".comment_upload_img_con").remove();
							check_box.checkMsgBoxHeight();
						}
					}
					initPhotoSwipeFromDOM('.gallery');
				},300);
				
			}
		});
}
function addComment(result,replayBean,model){
	
	var comment = "";
	var num = 0;
	if(replayBean != null && replayBean != ""){
		num = replayBean.length;
	}
	var zan = "zan";
	if(result.is_voted == 1){
		zan = "zaned";
	}else{
		zan = "zan";
	}
	var username = "未知用户";
	if(result.nick_name != null && result.nick_name != ""){
		username = result.nick_name;
	}
	var imgList = "";
	if(result.imgBeanList != null && result.imgBeanList != "" && model != "temp"){
		imgList = "<div class='gallery' data-pswp-uid='1'>";
		for(var n=0;n<result.imgBeanList.length;n++){
			imgList = imgList + "<a href='"+result.imgBeanList[n].img_url+"' data-size='"+result.imgBeanList[n].img_sizex+"' data-med='"+result.imgBeanList[n].img_url+"' data-med-size='"+result.imgBeanList[n].img_sizex+"'>"+
			"<img class='comment_img' src='"+result.imgBeanList[n].thumb_url+"' alt='' style='width:100px;height:100px;'>"+
		"</a>";
		}
		imgList = imgList + "</div>";
	}else if(model == "temp"){
		imgList = "<div class='gallery' data-pswp-uid='1'>";
		$(".commnet_upload_img_temp").each(function(e,item){
			var url = $(item).attr("src");
			imgList = imgList + "<a href='"+url+"' data-size='100x100' data-med='"+url+"' data-med-size='1920x1200'>"+
			"<img class='comment_img comment_img_temp' src='"+url+"' alt='' style='width:100px;height:100px;'>"+
		"</a>";
		});
		imgList = imgList + "</div>";
	}
	comment = comment + "<div class='blogs'><figure><img src='"+result.head_img+"'></figure>"+
			    "<ul><li><h3><a class='comment_username'>"+username+"</a><span class='pingl f_r'><a>"+result.reCount+"</a></span><span class='"+zan+"'><input type='hidden' class='parent_commentid' value='"+result.id+"'/><a class='num'>"+result.agreeCount+"</a></span></h3><p class='comment_msg'>'"+result.msgtext+"'</p>"+
			    imgList+
				"<p class='autor' style='margin-top: 5px;'><span class='dtime f_l'>"+result.createtime+"</span><span class='msg_box_button commit' style='display:none;  padding: 0px 10px; width: auto;'><a>提交</a></span><span class='msg_box_button commentcancel' style='display:none; padding: 0px 10px; width: auto;'><a>取消</a></span></p>" +
				"<div class='remsg_div' style='display:none'>" +
						"<textarea placeholder='回复 "+username+"：'  class='re_textarea' name='textarea'rows='4' style='BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;    width: 96%;padding: 2%;border-radius: 4px;' /></div></li>";
	if(replayBean != null && replayBean != ""){
		replyDiv = "";
		conut = 0;
		comment = comment + addReplyComment(replayBean,replyDiv,true);
		if(conut > 1){
			comment = comment + "<li><div class='loadmore'><span class='loading f_r'>加载更多...</span></div></li></ul></div>";
		}else{
			comment = comment + "</ul></div>";
		}
	}else{
		comment = comment + "</ul></div>";
	}
	return comment;			
	
	
}

function addReplyComment(replayBean,replyDiv,flag){
//	console.log(replyDiv);
	if(replayBean != null && replayBean != "" && replayBean.length > 0){
		for(var i = 0;i < replayBean.length;i++){
			conut ++;
			var name = "作者回复";
			if(replayBean[i].type == 1 || replayBean[i].type == "1"){
			}else{
				name = replayBean[i].nick_name;
			}
			if(replayBean[i].parent_replyUserName != null && replayBean[i].parent_replyUserName != ""){
				name = name + "<span style='font-weight: 100;color: ##474645;'> 回复：</span>"+ replayBean[i].parent_replyUserName;
			}
			if(flag && i == 0){
				replyDiv = replyDiv + "<li><div class='comment_replyuser'><h4><div class='rectangle1'style='background-size:32px 32px;background-image:url("+replayBean[i].head_img+")'><input type='hidden' class='commentid' value='"+replayBean[i].id+"'/></div><a style='font-weight: 100;color: #607D8B;'>"+name+"</a></h4><div class='huifu'><p>'"+replayBean[i].msgtext+"'</p></div>" +
				"</div></li>";
			}else{
				replyDiv = replyDiv + "<li class='noShow' style='display:none'><div class='comment_replyuser'><h4><div class='rectangle1'style='background-size:32px 32px;background-image:url("+replayBean[i].head_img+")'><input type='hidden' class='commentid' value='"+replayBean[i].id+"'/></div><a style='font-weight: 100;color: #607D8B;'>"+name+"</a></h4><div class='huifu'><p>'"+replayBean[i].msgtext+"'</p></div>" +
				"</div></li>";
			}
			replyDiv = addReplyComment(replayBean[i].replyBeanList,replyDiv,false);
		}
	}
	return replyDiv;
}