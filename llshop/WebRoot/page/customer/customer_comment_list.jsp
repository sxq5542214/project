<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.business.comment.bean.WechatCommentBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.yd.util.StringUtil"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object openid = request.getAttribute("openid");
	List<WechatCommentBean> beanList = (List<WechatCommentBean>)(request.getAttribute("beanList"));
	if(StringUtil.isNull(openid)){
		openid = "123";
	}
%>
<!doctype html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
 <link href="page/customer/css/buju.css" rel="stylesheet">
 <link href="page/customer/css/index.css" rel="stylesheet">
 <script type="text/javascript" src="js/jquery.js"></script>
 <script type="text/javascript" src="page/customer/js/customer.js"> </script>
 
</head>
<body>
<div id="swrap" >
  <article >
    <div class="l_box f_l"  style="background-color: white;">
    <div id = "hiddenbox" style="height:40px;width:100%;display:none"></div>
	   <div class="topnews" id="t">
	   <input id="activity_code" type="hidden" value="one"/>
	   <input id="openid" type="hidden" value="<%=String.valueOf(openid) %>"/>
	   <input id="nowpage" type="hidden" value="1"/>
	   <input id="commentId" type="hidden" value="290"/>
	   <div>
	   <div class="container" >
	   <div style="height: 30px;width: 100%;margin-bottom: 5px;    padding-top: 10px;">
	   <span style="font-size: 16px; padding-top: 6px;font-weight: bold;">用户咨询</span><span class="write_msg">刷新</span>
	   </div>
		</div>
	   </div>
	   </div>
	   <div id = "blogList">

	   	
	   </div>
	   <div id="load_more"><span>点击加载...</span></div>
	 
  </div>
  </article>

  </div>
</body>
</html>