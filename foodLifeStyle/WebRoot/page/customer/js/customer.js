//var timer = setInterval( function(){
//	reflashCount();
//}, 10000);
$(document).ready(function() {
	//$(document).on('click', '.write_msg', function () {
//	$(".write_msg").live('click',function(){
	$(".container").click(function(){
		$("#blogList").html("");
		$('#load_more').children().text("加载中");
		$('#load_more').children().css("background","url(page/comment/images/loading16.gif) no-repeat right center");
		$('#load_more').children().css("padding-right","20px");
		ajaxData($("#commentId").val(),1,"");
	});
	//$(document).on('click', '.pingl', function () {
	$('body').on('click','.pingl',function(){
		$(this).siblings(".msg_box_button").show();
		$(this).hide();
		$(this).parent().siblings(".remsg_div").show();
	});
	//$(document).on('click', '.cancel', function () {
//	$(".cancel").live('click',function(){
	$('body').on('click','.cancel',function(){
		$(this).siblings(".write_msg").show();
		$(this).siblings(".pingl").show();
		$(this).hide();
		$(this).siblings(".msg_box_button").hide();
		$(this).parent().siblings(".msg_box").hide();
		$(this).parent().siblings(".remsg_div").hide();
		 var height = $(".topnews").height();
	});
	//点赞事件
	$('body').on('click', '.loadmore', function () {
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
		var msgtext = $(this).parent().siblings().find("textarea").val();
		if(msgtext == "" || msgtext == null || msgtext.trim() == "" || msgtext.trim() == null){
			alert("文本内容不可为空！");
			return false;
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
		$(".cancel").hide();
		var commentid = $(this).parent().siblings().find(".commentid").val();
		$.ajax({
			type : "POST",
			url : "comment/replyMsgToWeChatUser.do",
			data : {"msgtext":msgtext,"commentid":commentid,"adminopenid":$("#openid").val()},
			success : function(data) {
				if(data == null || data == ""){
					alert("无数据")
					return false;
				}
				var result = eval("("+data+")");
				if(result.remark != null && result.remark != ""){
					alert(result.remark);
				}
				var t = setTimeout(function(){
					$(commitDemo).children().text("提交");
					$(commitDemo).children().css("background","");
					$(commitDemo).children().css("padding-right","0px");
					$(commitDemo).hide();
					$(commitDemo).parent().siblings(".remsg_div").hide();
					$(commitDemo).parent().siblings().find("textarea").val("")
					$(commitDemo).siblings(".write_msg").show(500);
					$(commitDemo).siblings(".pingl").show(500);
					$(commitDemo).addClass("commit");
					if(result.length > 0){
						var obj = result[0];
						var id = result.id;
						$(commitDemo).parent().parent().append("<li><h4><div class='rectangle0'></div><a>客服回复</a></h4><div class='huifu'><p>'"+obj.msgtext+"'</p><p class='autor'><span class='dtime f_l '>"+obj.createtime+"</span></p></div></li>");
						$(commitDemo).siblings(".pingl").html("<a>回复（"+result.length+"）</a>");
					}
				},1000);
			}
		});
	});
	var obj = document.getElementById("t");
	var _getHeight = obj.offsetTop;
	ajaxData($("#commentId").val(),$("#nowpage").val(),"");
	$("#load_more").click(function(){
		var page = $("#nowpage").val()*1+1;
		var loadmore = $(this);
		setTimeout(function(){
		ajaxData($("#commentId").val(),page,"",loadmore);
//		console.log(nowpage);
		},1000);
	});
	
function ajaxData(id,nowpage,openid,loadmore){
	//异步加载留言信息
	var comment = "";
	var code = $("#activity_code").val();
	var openid = $("#openid").val();
	if(openid == null || openid == "" || openid=="XXX"){
		$('#load_more').unbind("click"); 
		$('#load_more').children().text("已加载完全");
		$('#load_more').children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
		$('#load_more').children().css("padding-right","20px");
		alert("非法访问")
		return false;
	}
//	console.log(nowpage);
	$.ajax({
		type : "POST",
		url : "comment/getMsgCenterUserCommentList.do",
		data : {"code": code,"openid":openid,"nowpage":nowpage,"id":id},
		
		success : function(data) {
			if(data == null || data == ""){
				alert("无数据")
				return false;
			}
			var returnData = eval("("+data+")");
			var result = returnData.dataList;
			$("#nowpage").val(returnData.nowpage);
//			console.log(returnData.nowpage);
			//加载页面数据
			if(result != null && result != "" & result.length > 0){
				for(var i = 0;i < result.length;i++){
					comment =comment + addComment(result[i],result[i].replyBeanList);
					
				}
				$("#blogList").append(comment);
				menuFixed("t");
//				$(loadmore).children().text("加载中");
//				$(loadmore).children().css("background","url(page/comment/images/loading16.gif) no-repeat right center");
//				$(loadmore).children().css("padding-right","20px");
//				$(loadmore).children().text("点击加载更多...");
//				$(loadmore).children().css("background","");
//				$(loadmore).children().css("padding-right","0px");
			}else{
				$('#load_more').unbind("click"); 
				$('#load_more').children().text("已加载完全");
				$('#load_more').children().css("background","url(page/comment/images/yes_16px.png) no-repeat right center");
				$('#load_more').children().css("padding-right","20px");
			}
		}
	});
}
	
function addComment(result,replayBean){
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
	var original_name = result.original_name;
	if(result.original_name == null || result.original_name == ""){
		original_name = "";
	}
	comment = comment + "<div class='blogs' ><a  href='comment/showWechatUserCommentList.do?adminopenid="+$("#openid").val()+"&openid="+result.openid+"'><figure><img src='"+result.head_img+"' ></figure></a>"+
			    "<ul><li><h3><a  href='comment/showWechatUserCommentList.do?adminopenid="+$("#openid").val()+"&openid="+result.openid+"'>"+result.nick_name  +"  | <a style='font-size: 12px;color: #673AB7;'>"+original_name+"</a><span class='"+zan+"'><input type='hidden' class='commentid' value='"+result.id+"'/></span></a></h3><p>'"+result.action_value+"'</p>"+
				"<p class='autor'><span class='dtime f_l'>"+result.create_time+"</span><span class='msg_box_button commit' style='display:none;  padding: 0px 10px;'><a>提交</a></span><span class='msg_box_button cancel' style='display:none; padding: 0px 10px;'><a>取消</a></span><span class='pingl f_r'>	回复（<a>"+num+"</a>）</span></p><div class='remsg_div' style='display:none'><textarea  class='re_textarea' name='textarea'rows='4' style='BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;    width: 100%; height: 100px;border-radius: 4px;' /></div></li>";
	if(replayBean != null && replayBean != ""){
		for(var i = 0;i < replayBean.length;i++){
			var number = i - ((Math.floor(i/10))*10);
			if(replayBean[i].type == 1){
				number = 0;
			}
			
			var name = "客服回复";
//			if(replayBean[i].openid != null && replayBean[i].openid != ""){
//				if(replayBean[i].nick_name == null || replayBean[i].nick_name == ""){
//					name = "未知用户";
//				}else{
//					name = replayBean[i].nick_name;
//				}
//				
//				
//			}
			if(i == 0){
				comment = comment + "<li><h4><div class='rectangle"+number+"'></div><a>"+name+"</a></h4><div class='huifu'><p>'"+replayBean[i].msgtext+"'</p><p class='autor'>"+
				"<span class='dtime f_l '>"+replayBean[i].createtime+"</span></p></div></li>";
			}else{
				comment = comment + "<li  class='noShow' style='display:none'><h4><div class='rectangle"+number+"'></div><a>"+name+"</a></h4><div class='huifu'><p>'"+replayBean[i].msgtext+"'</p><p class='autor'>"+
				"<span class='dtime f_l '>"+replayBean[i].createtime+"</span></p></div></li>";
			}
			
		}
		if(replayBean.length > 1){
			comment = comment + "<li><div class='loadmore'><span class='loading f_r'>加载更多...</span></div></li></ul></div>";
		}else{
			comment = comment + "</ul></div>";
		}
	}else{
		comment = comment + "</ul></div>";
	}
	return comment;			
	
	
}
	
//	
//$(".pingl").click(function(){
//	
//	$(this).parent().siblings(".comment_replay").show();
//	
//});
function menuFixed(id){  

    window.onscroll = function(){
        changePos(id,_getHeight);
    }
}
function changePos(id,height){
    var obj = document.getElementById(id);
    var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
    $("#position").text(scrollTop);
    //console.log(scrollTop+"----"+height+"===="+nowpage+"----"+document.body.scrollTop);
   if($(document).scrollTop() + $(window).height() == $(document).height()){
	   document.getElementById("load_more").click();
   }
    if(scrollTop < height){
    	var hiddenbox = document.getElementById("hiddenbox");
    	hiddenbox.style.display="none";
        obj.style.position = 'relative';
        obj.style.width = '100%';
        var blogList = document.getElementById("blogList");
        blogList.style.paddingTop = '0px';
    }else{
    	var hiddenbox = document.getElementById("hiddenbox");
    	hiddenbox.style.display="block";
        obj.style.position = 'fixed';
        obj.style.width = '96%';
        var blogList = document.getElementById("blogList");
       // blogList.style.paddingTop = $(".topnews").height()+"px";
    }
}
});
function reflashCount(){
	$.ajax({
		type : "POST",
		url : "comment/getNewMsg.do",
		data : {},
		success : function(data) {
			$("#title").html("用户咨询,有["+data+"]新留言");
			console.log(1)
		}
	});
}



