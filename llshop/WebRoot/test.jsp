<%@page import="com.yd.business.user.util.EmojiUtil"%>
<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@page import="com.yd.business.comment.service.ICommentInfoService"%>
<%@page import="com.yd.factory.ServiceFactory"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";



%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
		<meta charset="utf-8">
	    <meta name="viewport" content="initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />

	
	<script type="text/javascript" src="js/jquery.js"></script>
	<script src="http://192.168.1.100:8080/emoji/jquery.emoji.js" type="text/javascript"></script>
	
	<script src="http://git.emojione.com/demos/2.2.4/lib/js/emojione.js"></script>
	<script type="text/javascript" src="js/emoji/emojimap.js"></script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body id="body" style="width: 100%">
    This is my JSP page. <br>
    <input id="aaa" type="text" >
    <button onclick="query();">确定</button>
    
    dis3:<span id="dis3"></span>
    dis:<span id="dis"></span>
    dis2:<span id="dis2"></span>
  </body>
  
  <script type="text/javascript">
  
  
      $(document).ready(function(){
      
      });
      
      function query(){
     	var val = $('#aaa').val();
//     	var dval = decToHex(val);
		
		var cval = unify(val);
      	$('#dis3').html(cval);
		
		
     	var dval = emojione.toShort(cval);
     	
      	$('#dis').html(dval);
     	
      	var output1 = emojione.toImage(cval);
      	
      	$('#dis2').html(output1);
      	
      	
        $('#body').each(function(i, d){
          $(d).emoji();
        });
      }
      
      
      
      /*
*js Unicode编码转换
*/ 
var decToHex = function(str) {
    var res=[];
    for(var i=0;i < str.length;i++)
        res[i]=("00"+str.charCodeAt(i).toString(16)).slice(-4);
    return "\\u"+res.join("\\u");
};
var hexToDec = function(str) {
    str=str.replace(/\\/g,"%");
    return unescape(str);
};
    </script>
</html>
