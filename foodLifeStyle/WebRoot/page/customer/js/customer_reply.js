var timer = setInterval( function(){
	reflashCount();
}, 10000);
$(document).ready(function() {
	$(document).on('click', '.container', function () {
		 location.reload();
	});

	var height = "";
		$(".reply").click(function(){
			$(".reply_textarea").show();
			$(this).hide();
			$(".reply_canlce").show();
			$(".reply_commit").show();
		});

		$(".reply_commit").click(function(){
			var msgtext = $(this).parent().siblings(".reply_textarea").val();
			if(msgtext == "" || msgtext == null || msgtext.trim() == "" || msgtext.trim() == null){
				alert("文本内容不可为空！");
				return false;
			}
			var commentid = $(this).closest("#reply_more").siblings("#blogList").children(".customer").last().find(".commentid").val();
			var commitDemo = $(this);
			$(".reply_textarea").val("");
			$(this).css("background","url(page/comment/images/loading16.gif) no-repeat 50px center");
			$.ajax({
				type : "POST",
				url : "comment/replyMsgToWeChatUser.do",
				data : {"msgtext":msgtext,"commentid":commentid,"adminopenid":$("#adminopenid").val()},
				success : function(data) {
					if(data == null || data == ""){
						alert("无数据")
						return false;
					}
					var result = eval("("+data+")");
					if(result.remark != null && result.remark != ""){
						alert(result.remark);
					}
//					if(result.length > 0){
						var obj = result;
						var id = result.id;
						$("#blogList").append("<div class='blogs' style='border-bottom :rgba(204, 204, 204, 0) 1px solid;padding-bottom:5px'>"+
								"<ul style='float: left;'><li>"+
								"<div class='comment_body'>"+
								"<div class='comment_box_admin'>"+
								"<span>"+obj.msgtext+"</span>"+
								"<div class='nav_r nav-border_r'></div>"+
								"<div class='nav_r nav-background_r'></div></div></div>"+
								"<p class='autor'><span class='dtime f_r'>"+obj.createtime+"</span></p></li></ul>"+
								"<figure style='float: right;'><img src='"+obj.head_img+"'></figure></div>");
						$(commitDemo).css("background","url(page/comment/images/yes_16px.png) no-repeat 50px center");
//					}
				}
			});
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
	
	var obj = document.getElementById("t");
	var _getHeight = obj.offsetTop;
	

	
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
	var commentid = $("#blogList").children(".customer").last().find(".commentid").val();
	$.ajax({
		type : "POST",
		url : "comment/getNewMsgForOwner.do",
		data : {"commentid":commentid},
		success : function(data) {
			if(data != null && data != ""){
				var result = eval("("+data+")");
				if(result.length > 0){
					for(var i=0;i<result.length;i++){
						var head_img = "page/customer/images/Alien.png";
						if(result[i].head_img != null && result[i].head_img != ""){
							head_img = result[i].head_img;
						}
						
						var comment = "<div class='blogs customer' style='border-bottom :rgba(204, 204, 204, 0) 1px solid;padding-bottom:5px'>"+
							"<figure><img src='"+head_img+"'></figure><ul>"+
							"<li><div class='comment_body'>"+
							"<div class='comment_box'>"+
							"<span>"+result[i].action_value+"</span>"+
							"<div class='nav nav-border'></div>"+
							"<div class='nav nav-background'></div>"+
							"</div></div><p class='autor'><span class='dtime f_l'>"+result[i].create_time+"</span>"+
							"</p></li></ul><input type='hidden' class='commentid' value='"+result[i].id+"'></div>";
						$("#blogList").append(comment);
					}
				}
			}
		}
	});
}



