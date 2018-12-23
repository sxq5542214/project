<%@page import="com.yd.business.comment.bean.CommentInfoBean"%>
<%@page import="com.yd.business.comment.bean.WechatCommentBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<WechatCommentBean> beanList = (List<WechatCommentBean>)(request.getAttribute("beanList"));
	String openid = request.getParameter("openid");
	String adminopenid = request.getParameter("adminopenid");
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
 <script type="text/javascript" src="page/customer/js/customer_reply.js"> </script>
  </head>
  
<body onLoad="window.document.body.scrollTop = document.body.scrollHeight; ">    
<div id="swrap">
      <article>
        <div class="l_box f_l" style="background-color: white;">
          <div id="hiddenbox" style="height: 40px; width: 100%; display: block;"></div>
          <div class="topnews" id="t" style="position: fixed; width: 96%;">
            <input id="openid" type="hidden" value="<%=openid %>">
            <input id="adminopenid" type="hidden" value="<%=adminopenid %>">
            <input id="nowpage" type="hidden" value="2">
            <input id="commentid" type="hidden" value="290">
            <div>
              <div class="container">
                <div style="height: 30px;width: 100%;margin-bottom: 5px;    padding-top: 10px;">
                  <span style="font-size: 16px; padding-top: 6px;font-weight: bold;">与<%=beanList.get(0).getNick_name() %> | <%=StringUtil.convertNull(beanList.get(0).getOriginal_name()) %> 对话中</span><span class="write_msg">刷新</span></div>
              </div>
            </div>
          </div>
          <div id="blogList">
          <%for(WechatCommentBean bean:beanList){ %>
          <div class="blogs customer" style="border-bottom :rgba(204, 204, 204, 0) 1px solid;padding-bottom:5px">
              <figure>
                <%if(!StringUtil.isNull(bean.getHead_img())){%>
			  <img src="<%=bean.getHead_img() %>"></figure>
			  <%} else{%>
                <img src="page/customer/images/Alien.png"></figure>
                <%} %>            
              <ul>
                <li>
				<div class="comment_body">
				<div class="comment_box">
				<span><%=bean.getAction_value() %></span>
				<div class="nav nav-border"></div>
  <div class="nav nav-background"></div>
				</div>
				</div>
                  <p class="autor">
                    <span class="dtime f_l"><%=bean.getCreate_time() %></span>
                  </p>
                </li>
              </ul>
              <input type="hidden" class="commentid" value="<%=bean.getId() %>" />
            </div>
            <%if(!StringUtil.isNull(bean.getReplyBeanList()) &&bean.getReplyBeanList().size() > 0){
            List<CommentInfoBean> commentList = bean.getReplyBeanList();
            for(CommentInfoBean comment:commentList){%>
            <div class="blogs" style="border-bottom :rgba(204, 204, 204, 0) 1px solid;padding-bottom:5px">
              
              <ul  style="float: left;">
                <li>
				<div class="comment_body">
				<div class="comment_box_admin">
				<span><%=comment.getMsgtext() %></span>
				<div class="nav_r nav-border_r"></div>
				<div class="nav_r nav-background_r"></div>
				</div>
				</div>
                  <p class="autor">
                    <span class="dtime f_r"><%=comment.getCreatetime() %></span>
                  </p>
                </li>
              </ul>
			  <figure style="float: right;">
			  <%if(!StringUtil.isNull(comment.getHead_img())){%>
			  <img src="<%=comment.getHead_img() %>"></figure>
			  <%} else{%>
                <img src="page/customer/images/Portrait.png"></figure><%} %>
            </div>
            <%}} %>
          <%} %>
          </div>
          <div id="reply_more">
		  <textarea class="reply_textarea" name="textarea" rows="3" style="BORDER-BOTTOM: 1px solid; BORDER-LEFT: 0px solid; BORDER-RIGHT: 0px solid; BORDER-TOP: 0px solid;"></textarea>
            <input type="hidden" value="123" class="commentid"/>
            <div><span class="returnList" onclick="javascript:history.back(-1);">返回</span><span class="reply_commit" >回复</span></div>
            </div>
        </div>
      </article>
    </div>
  </body>

</html>