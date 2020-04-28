// JavaScript Document
//弹出层+窗口
function shadowFocusQuery(a){
	var n=($(window).width()-$("#"+a).width())/2;
	var m=($(window).height()-$("#"+a).height())/2;
	$("#"+a).css("left",n+"px").css("top",m+"px");
	$("#"+a).show();
	//遮罩层同步浏览器显示屏幕高度
	var wh=$(window).height();
	$(".shadowScreen").css("height",wh+"px");
	var sd="<div class='shadowScreen' style='display:block;height:2000px'>";
	sd+="</div>";
	$("body").append(sd);
	//页面禁止滚动
	$("body").css("overflow","hidden");
}