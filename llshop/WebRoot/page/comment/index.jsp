<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
 <link href="page/comment/css/buju.css" rel="stylesheet">
 <link href="page/comment/css/index.css" rel="stylesheet">
 <script type="text/javascript" src="js/jquery.js"></script>
 <script type="text/javascript" src="page/comment/js/comment.js"> </script>
 
</head>
<body>
<div id="swrap">
<div class="blogs">
		      <figure>
			     <img src="page/comment/images/01.jpg">
			   </figure>
			   <ul>
			   <li>
			     <h3><a href="/">住在手机里的朋友</a>
				 <span class="zan"><a class="num">60</a></span>
				 </h3>
				  <p>"通信时代，无论是初次相见还是老友重逢，交换联系方式，常常是彼此交换名片，然后郑重或是出于礼貌用手机记下对方的电话号码 ..."</p>
			     <p class="autor">
					 <span class="dtime f_l">2016-02-16</span>
					 <span class="pingl f_r">	
					    评论（<a href="/">60</a>）</span>
				  </p>
				  </li>
				  <li>
				  <h4><div class="rectangle0"></div><a href="/">作者回复3</a>
				 </h4>
				  <div class="huifu">
				  <p>"通信时代，无论是初次相见还是老友重逢，..."</p>
			     <p class="autor">
					 <span class="dtime f_l ">2016-02-16</span>
					  <!--<span class="pingl f_r">	
					    评论（<a href="/">60</a>）</span> -->
				  </p>
				  </div>
			   </li>
			   <li class="noShow" style="display:none">
				  <h4><div class="rectangle1"></div><a href="/">作者回复3</a>
				 </h4>
				  <div class="huifu">
				  <p>"通信时代，无论是初次相见还是老友重逢，..."</p>
				   <p class="autor">
					 <span class="dtime f_l ">2016-02-16</span>
					  <!--<span class="pingl f_r">	
					    评论（<a href="/">60</a>）</span> -->
				  </p>
				  </div>
			   </li>
			   <li class="noShow" style="display:none">
			<h4><div class="rectangle2"></div><a href="/">作者回复3</a>
				 </h4>
				  <div class="huifu">
				  <p>"通信时代，无论是初次相见还是老友重逢，..."</p>
				   <p class="autor">
					 <span class="dtime f_l ">2016-02-16</span>
					  <!--<span class="pingl f_r">	
					    评论（<a href="/">60</a>）</span> -->
				  </p>
				  </div>
			   </li>
			   <li class="noShow" style="display:none">
				  <h4><div class="rectangle3"></div><a href="/">作者回复3</a>
				 </h4>
				  <div class="huifu">
				  <p>"通信时代，无论是初次相见还是老友重逢，..."</p>
				   <p class="autor">
					 <span class="dtime f_l ">2016-02-16</span>
					  <!--<span class="pingl f_r">	
					    评论（<a href="/">60</a>）</span> -->
				  </p>
				  </div>
			   </li>
			   <li>
			   <div class="loadmore"><span class="loading f_r">加载更多...</span></div>
			   </li>
			   </ul>
	       </div>
  <article>
    <div class="l_box f_l"><div id = "hiddenbox" style="height:80px;width:100%;display:none"></div>
	   <div class="topnews" id="t">
	   <input id="activity_code" type="hidden" value="one"/>
	   <input id="user_open_id" type="hidden" value="123"/>
	   <input id="conf_id" type="hidden" value="2"/>
	   <h2> 
	    精选留言
	   </h2>
	   <div>
	   <div class="container" >
	   <div style="height: 30px;width: 100%;margin-bottom: 5px;">
	   <span class="write_msg">写留言</span>
	   <div class="msg_box_button commit"><span>提交</span></div>
	   <div class="msg_box_button cancel"><span>取消</span></div>
	   </div>
	   <div class="msg_box"><textarea id="textarea" name="textarea"rows="4" style="BORDER-BOTTOM: 0px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;    width: 100%; height: 100px;border-radius: 6px;"></textarea></div>
	   
	   
</div>
	   </div>
	   </div>
	   <div id = "blogList">
	   </div>
	   <div class="load_more"><span>加载中...<span></div>
	 
  </div>
  </article>

  </div>
</body>
</html>