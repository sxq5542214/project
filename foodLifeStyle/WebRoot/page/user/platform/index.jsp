<%@page import="com.yd.util.NumberUtil"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

List<SupplierBean> listMyVisit = (List<SupplierBean>)request.getAttribute("listMyVisit");
String openid = request.getParameter("openid");
%>
<Suppl!DOCTYPE html>
<!-- base版本 --> 
<html>
<head>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta charset="utf-8" />

<title>爱周边爱生活</title>
<base href="<%=basePath %>">
<meta content="" name="keywords" />
<meta content="" name="description" />
<meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0;" name="viewport" />
<meta content="yes" name="apple-mobile-web-app-capable" />
<meta content="black" name="apple-mobile-web-app-status-bar-style" />
<meta content="telephone=no" name="format-detection" />

<link href="css/user/platform/LFtouch.css" rel="stylesheet" type="text/css" />

<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
</head>
<body>



<div id="container">
    <div class="header">
        <h1><a >爱周边爱生活<!-- <img src="images/wap/touch/logo.png"> --></a></h1>
        <div class="ourWords">
            <span>发现周边的美丽，发现周边的生活</span>
        </div>
    </div>
    
<!--     <div class="topSrh">
        <form>
            <input type="text" class="input_text0" value="请搜索"  onfocus="this.value='';this.className='input_text'" onBlur="if(this.value==''){this.value='雅诗兰黛红石榴'};this.className='input_text0'"/>
            <input type="button" class="btn_srh" value="搜索"/>
        </form>
    </div>
     -->
    
    
    <div class="main">
        <div class="tCtn">
            
            <div class="idxRow2">
            	
                <ul class="idxPdtLst2Nav">
                	<li class="curr"><a  >我访问过的店铺</a></li>
                </ul>
            
            	<ul class="idxPdtLst2">
            		<%for(SupplierBean supplier : listMyVisit){
            			String img = "images/shop/noimg.png";
            			if(StringUtil.isNotNull(supplier.getSupplier_img())){ img = supplier.getSupplier_img(); }
            		 %>
                	<li>
                    	<a href="wx/supplier/shop/toSupplierShopPage.html?sid=<%=supplier.getId() %>&fromOpenid=<%=openid %>" style="width: 160px;" class="tImg" ><img src="<%=img %>" style="width: 100%;height: 100%;"/></a>
                        <a href="wx/supplier/shop/toSupplierShopPage.html?sid=<%=supplier.getId() %>&fromOpenid=<%=openid %>" style="width: 50%;margin-left: 5px;" class="tLnk" >
                            <div class="tNm"><%=supplier.getName() %></div>
                        	<div class="tTit"><%=StringUtil.convertNull(supplier.getSupplier_title()) %></div>
                            <div class="tPrc">平均消费 &nbsp;&nbsp;&nbsp;&nbsp; &yen;<b><%=NumberUtil.convertNull(supplier.getAvg_price())/100d %></b> </div>
                        </a>
                    </li>
                	<%}
                	if(listMyVisit.size() == 0){ %>
                		<li>
                    	<a style="width: 160px;" class="tImg" ><img src="images/shop/noimg.png" style="width: 100%;height: 100%;"/></a>
                        <a style="width: 50%;margin-left: 5px;background-image: none;" class="tLnk" >
                            <div class="tNm">您还没有访问过的店铺！</div>
                        	<div class="tTit"></div>
                            <div class="tPrc"></div>
                        </a>
                    </li>
                	<%} %>
                </ul>
            </div>
            
        </div>
    </div>
    
    <div class="footer">
    
<!--     	<div class="lnks">
            <a href="bt.html">护肤</a>
            <a href="bt.html">香水</a>
            <a href="bt.html">彩妆</a>
            <a href="bt.shtml@id=38">男士</a>
            <a href="bt.shtml@id=30">美发</a>
            <a href="bt.shtml@id=31">瘦身</a>
            <a href="bt.shtml@id=32">全身护理</a>
            <a href="bt.shtml@id=37">美容工具</a>
		</div> -->
    
    	<div class="phone" style="width: 100%;position: fixed;bottom: 5px;">为您提供生活周边的小美好</div>
        
  <!--       <div class="copyright">
            <a  class="logo"><img src="images/wap/touch/logo.png"/></a>
            <div class="ourWords">
                <div>中国电商协会认证网站</div>
                <div>合作伙伴</div>
            </div>
        </div> -->
    
    </div>
    
    
    

</div>
<script type="text/javascript">
  	var screenWidth = window.screen.width ;
	$(".tLnk").width(screenWidth - 175);
</script>

</body>
</html>
