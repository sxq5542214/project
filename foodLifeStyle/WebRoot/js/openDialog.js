$(document).ready(function(){
	$('body').append('<div class="messagearea" id="msgShow"><div class="mTitle">&nbsp;<span class="unread" id="msgModalTitle"></span></div>'+
		    '<div class="msgListContain" style=" height:auto; overflow:hidden; text-align:center;">'+
		    '<div id="msgcontent" style="margin-bottom:40px;text-align:center;margin-top:40px;"></div>'+
		    '</div><div align="right"><input type="button" class="btn btn-success btn-sm" value="确定" onclick="cellquery();">'+
		    '<input type="button" class="btn btn-inverse btn-default btn-sm" value="关闭" onclick="cellquery();">&nbsp;&nbsp;</div></div>');
	
});
function openMsg(title,content){
	$('#msgModalTitle').html(title);
	$('#msgcontent').html(content);
	shadowMsgQuery("msgShow");//弹出窗口显示
}
function cellquery(){
	$("#msgShow").hide();
	$(".shadowScreen").remove();
	//页面出现滚动条
	$("body").css("overflow","auto");
}
function concellquery(){
	$(".messagearea").hide();
	$(".shadowScreen").remove();
	//页面出现滚动条
	$("body").css("overflow","auto");
}
//获取url中的参数
function getUrlParam(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
}
function shadowMsgQuery(a){
	var n=($(window).width()-$("#"+a).width())/2;
	var m=($(window).height()-$("#"+a).height())/2;
	$("#"+a).css("left",n+"px").css("top",m+"px");
	$("#"+a).show();
	//遮罩层同步浏览器显示屏幕高度
	var wh=$(window).height();
	$(".shadowScreenMsg").css("height",wh+"px");
	var sd="<div class='shadowScreenMsg' style='display:block;height:2000px'>";
	sd+="</div>";
	$("body").append(sd);
	//页面禁止滚动
	$("body").css("overflow","hidden");
}