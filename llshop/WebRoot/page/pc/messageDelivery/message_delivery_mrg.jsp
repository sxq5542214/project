<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.wechat.bean.WechatOriginalInfoBean"%>
<%@page import="com.yd.business.wechat.bean.WechatMaterialBean"%>
<%@page import="com.yd.business.wechat.bean.WechatParentMaterialBean"%>
<%@page import="com.yd.basic.framework.pageination.PageinationData"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	
	
	List<WechatOriginalInfoBean> originalList = (List<WechatOriginalInfoBean>)request.getAttribute("originalList");
	WechatMaterialBean bean = (WechatMaterialBean)request.getAttribute("bean");
	String start_time = StringUtil.isNull(request.getAttribute("start_time"))?"":request.getAttribute("start_time").toString();
	String end_time = StringUtil.isNull(request.getAttribute("end_time"))?"":request.getAttribute("end_time").toString();
	//素材列表
	PageinationData pd = (PageinationData)request.getAttribute("pd");
	List<WechatParentMaterialBean> list = pd.getDataList();
	
%>
<!DOCTYPE html>
<html>
  <head>
  <base href="<%=basePath%>">
<title>万大商城－微信素材分发管理</title>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description"
	content="Blue Moon - Responsive Admin Dashboard" />
<meta name="keywords"
	content="Notifications, Admin, Dashboard, Bootstrap3, Sass, transform, CSS3, HTML5, Web design, UI Design, Responsive Dashboard, Responsive Admin, Admin Theme, Best Admin UI, Bootstrap Theme, Wrapbootstrap, Bootstrap, bootstrap.gallery" />
<meta name="author" content="Bootstrap Gallery" />
<link rel="shortcut icon" href="img/favicon.ico">

<link href="page/pc/css/bootstrap.min.css" rel="stylesheet">

<link href="page/pc/css/new.css" rel="stylesheet">
<!-- Important. For Theming change primary-color variable in main.css  -->

<link href="page/pc/fonts/font-awesome.min.css" rel="stylesheet">
<link href="page/pc/css/preview_base.css" rel="stylesheet">
<style type="text/css">
label{float: right;    font-family: "Helvetica Neue",Helvetica,Arial,sans-serif;}
.mater{background-color: rgba(76, 175, 80, 0.49);}
.childrenmater{background-color: rgba(196, 226, 253, 0.17);}
.hidden_body{
display:none;
}
.delivery_msg{
	    background: url(page/pc/js/pc/messageDelivery/images/loading.gif) no-repeat left center;
    height: 100px;
    background-color: #E8E4E7;
    width: 500px;
}
.delivery_msg a{float: left;
    padding-left: 120px;
    padding-top: 35px;
    font-size: -webkit-xxx-large;
    color: #67696B;}
</style>
	<script type="text/javascript">


function showImg(url) {

    var frameid = 'frameimg' + Math.random();

    //console.debug(frameid);

    //console.debug(url);

    window.img = '<img id="img" style="width:160px;height:80px" src=\'' + url + '?' + Math.random() + '\' /><script>window.onload = function() { parent.document.getElementById(\'' + frameid + '\').height = document.getElementById(\'img\').height+\'px\'; }<' + '/script>';
    document.write('<iframe id="' + frameid + '" src="javascript:parent.img;" frameBorder="0" scrolling="no" width="160px;height:80px"></iframe>');


}

</script>
</head>

<body style="background-color: #f7f7f7;" onload="">
<div class="row" >
		<div class="col-lg-12 col-md-12">
			<div class="widget">
				<div class="widget-header">
					<div class="title">
						微信素材分发管理
					</div>
					<span class="tools"> <i class="fa fa-cogs"></i>
					</span>
				</div>
				<div class="widget-body">
					<form id="conditionFrom" class="form-horizontal row-border" action="wechat/delivery/showWechatMaterialInfo.do" method="post">
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">素材标题：</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" placeholder="请输入素材标题" name="sucai_title" value="<%=StringUtil.convertNull(bean.getSucai_title()) %>"/>
									</div>
									<div class="col-xs-2">
										<label class="control-label">素材分类：</label>
									</div>
									<div class="col-xs-4">
										<select name="sucai_type" class="form-control">
										<option value="news" selected="selected">
				                           	微信图文消息
				                        </option>
				                        </select>
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">素材所属公众号：</label>
								</div>
									<div class="col-xs-4">
										<select name="originalid" class="form-control">
										<option value="0">全部</option>
											<%for(WechatOriginalInfoBean originalBean : originalList ){ 
												String selected = "";
												if(originalBean.getOriginalid() != null &&  originalBean.getOriginalid().equals(bean.getOriginalid())){
													selected = "selected=\"selected\"";
												}
											%>
											<option value="<%=originalBean.getOriginalid() %>" <%=selected %>><%=originalBean.getOriginal_name() %></option>
											<%} %>
				                        </select>
									</div>
									<div class="col-xs-2">
										<label class="control-label">顺序编号：</label>
									</div>
									<div class="col-xs-4">
										<input type="text" name="seq"  placeholder="顺序编号,越大越优先" class="form-control" value="">
									</div>
								</div>
							</div>
						</div>
						
						<div class="form-group">
							<div class="col-md-12">
								<div class="row">
								<div class="col-xs-2">
								<label class="control-label">开始时间：</label>
								</div>
									<div class="col-xs-4">
									
										<input class="form-control" type="text" name="start_time"  placeholder="请输入开始时间（年-月-日 时:分:秒）" value="<%=start_time %>">
									</div>
									
									<div class="col-xs-2">
								<label class="control-label">结束时间：</label>
								</div>
									<div class="col-xs-4">
										<input class="form-control" type="text" name="end_time"  placeholder="请输入结束时间（年-月-日 时:分:秒）" value="<%=end_time %>">
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
							<a style="margin-left: 10px;" class="btn btn-success pull-right" href="javascript:return false;" onclick="syncWechatMaterial();">
								<i class="fa fa-cloud-download"></i> 同步微信文章
							</a>
							<a style="margin-left: 10px;" type="cancle"  class="btn btn-warning pull-right" onclick="cleanBox();">清空</a>
								<input type="submit" id="submitSupplier" class="btn btn-info pull-right" value="搜索"/>  
								<input type="hidden" name="nowpage" id="nowpage" value="<%=pd.getNowpage() %>" >
							</div>
						</div>
					</form>
					<div id="dt_example" class="example_alt_pagination">
						<table class="table table-condensed table-striped table-hover table-bordered pull-left">
							<thead>
								<tr>
									<th style="width: 4%">
										<input type="checkbox" class="no-margin" onclick="checkAll(this);" />
									</th>
									<th style="width: 20%">主素材名称</th>
									<th style="width: 10%" class="hidden-phone">所属众号</th>
									<th style="width: 31%" class="hidden-phone">明细</th>
									
									<th style="width: 10%" class="hidden-phone">已分发公众号</th>
									<th style="width: 10%" class="hidden-phone">创建时间</th>
									<th style="width: 10%" class="hidden-phone">修改时间</th>
									<th style="width: 5%" class="hidden-phone">操作</th>
								</tr>
							</thead>
							<tbody id="supplier_list">
							<% for(WechatParentMaterialBean mater : list ){ %>
								<tr class="gradeA ">
									<th rowspan="<%=mater.getMaterialList().size()+1 %>">
									<input type="checkbox" class="materiterm" value="<%=mater.getSucai_media_id() %>" /></th>
									
									<th rowspan="<%=mater.getMaterialList().size()+1 %>">
									<div class="hotlinking">
									 <script type="text/javascript">showImg("<%=mater.getSucai_thumb_url() %>");</script>
									</div>									<h5><a style="margin-left: 8px;" href="<%=mater.getSucai_url() %>" target="_blank"><%=mater.getSucai_title() %></a>
									</h5>
									</th>
									<th class="orginal_name" rowspan="<%=mater.getMaterialList().size()+1 %>"><%=mater.getOriginal_name() %></th>
									<th rowspan="1" class="mater"></th>
                    				
                    				<th class="delivery_orginal_name" rowspan="<%=mater.getMaterialList().size()+1 %>">[<%=mater.getDeliveryOriginal() %>]</th>
                    				<th rowspan="<%=mater.getMaterialList().size()+1 %>"><%=StringUtil.convertNull(mater.getCreate_time()) %></th>
                    				<th rowspan="<%=mater.getMaterialList().size()+1 %>"><%=StringUtil.convertNull(mater.getModify_time()) %></th>
                    				<th rowspan="<%=mater.getMaterialList().size()+1 %>">
                    				<a id='<%=mater.getSucai_media_id() %>' data-toggle="popover" data-placement="left" class="btn btn-info" href="javascript:return false;"  style="padding: 6px 12px;margin-left:5px;">
											<i class="fa fa-bars" style="padding-right: 5px;"></i>选择公众号
									</a>
									<br>
									
									<!-- <a id='<%=mater.getSucai_media_id() %>' class="btn btn-warning preview_mater" href="javascript:return false;"  style="padding: 6px 12px;margin-left:5px;    margin-top: 5px;">
											<i class="fa fa-eye" style="padding-right: 5px;"></i>素 材  预 览
									</a> -->
									<a id='sendToAll' data-toggle="popover" data-placement="left" class="btn btn-warning preview_mater" href="javascript:return false;"  style="padding: 6px 12px;margin-left:5px;    margin-top: 5px;">
											<i class="fa fa-reply" style="padding-right: 5px;"></i>素 材  发 送
									</a>
									</th>
                    			</tr>
                    			<%for(WechatMaterialBean materBean : mater.getMaterialList() ){ %>
                    			<tr class="gradeA ">
                    				<th class="childrenmater"><a href="<%=materBean.getSucai_url() %>" target="_blank"><%=materBean.getSucai_title() %></a></th>
                				</tr>
							<%}} %>
							</tbody>
						</table>
						<div class="dataTables_info">当前第<%=pd.getNowpage() %> 页/共 <%=pd.getTotalpage() %> 页[<%=pd.getTotalcount() %> 条数据]</div>
                      
                      <div class="dataTables_paginate paging_full_numbers">
                      	<span>
                      	<a tabindex="0" class="paginate_button" onclick="toPage(1)" >首页</a>
                      	<%if(pd.getNowpage()>1){%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>
                      		<%for(int i = 1 ; i <= pd.getTotalpage(); i ++) {
                      			String classStr = "paginate_button";
                      			if(pd.getNowpage() == i)
                      				classStr = "paginate_active";
                      			if(i < pd.getNowpage()+5 && i > pd.getNowpage()-5){%>
                      				<a tabindex="0" class="<%=classStr %>" onclick="toPage(<%=i %>)" ><%=i %></a>
                      		<%}} %>
						<%if(pd.getNowpage()<pd.getTotalpage()){%>
                      		<a tabindex="0" class="paginate_button" >.....</a>
                      	<%} %>                      	
                      	<a tabindex="0" class="paginate_button" onclick="toPage(<%=pd.getTotalpage() %>)" >尾页</a>
                      	</span>
                      </div>
						<div class="clearfix"></div>
					</div>
					<button id='deliveryAll' class="btn btn-success" data-toggle="popover" data-placement="top">
						<i class="fa fa-upload"></i> 分发上传
					</button>
					
					<!--<a id='sendAll' class="btn btn-warning" data-toggle="popover" data-placement="top"  href="javascript:return false;" onclick="">
						<i class="fa fa-reply"></i> 素材发送
					</a>-->
					
					<button id='delAll' class="btn btn-danger" data-toggle="popover" data-placement="top" >
						<i class="fa fa-times-circle"></i> 删除素材
					</button>
				</div>
			</div>
		</div>
	</div>
	<div class="hidden_body" style="
       position: absolute;
    z-index: 1000;
    height: 100%;
    width: 100%;
   background-color: rgba(177, 177, 177, 0.68);"><div style="text-align: center;
    padding-top: 15%;padding-left: 35%;"><div class="delivery_msg"><a>数据提交中......</a></div>
</div>
</div>
<div class="wx_phone_preview_wrp jsPhoneView" style="display:none">
      <div class="wx_phone_preview">
        <span class="btn btn_default btn_phone_preview_closed jsPhoneViewClose">关闭</span>
        <div class="wx_phone jsPhoneViewMain">
          <div class="wx_phone_hd">霸都大机密</div>
          <div class="wx_phone_bd wx_phone_preview_card_wrp" style="display:none">
            <div class="msg_card wx_phone_preview_multi_card has_first_cover">
              <div class="msg_card_inner">
                <div class="card_cover_appmsg_item jsPhoneViewCard" data-index="0">
                  <div class="card_cover_appmsg_inner" style="background-image:url(&#39;http://mmbiz.qpic.cn/mmbiz_jpg/v04oGXVy4VK298SchosicgNaicd50ia1bMFncibiaBaUvbdMjAgYUnfyegpGELlJtpBmXCkChXHfeh82peabiayVD0uA/0&#39;);">
                    <!--<img class="card_cover_thumb" src="">--></div>
                  <strong class="card_cover_title" title="霸都大机密4">霸都大机密4</strong></div>
                <div class="card_appmsg_item dn jsPhoneViewCard" data-index="0">
                  <img class="card_appmsg_thumb" src="./微信公众平台_files/0">
                  <div class="card_appmsg_content" title="霸都大机密4">霸都大机密4</div></div>
                <div class="card_appmsg_item  jsPhoneViewCard" data-index="1">
                  <img class="card_appmsg_thumb" src="./微信公众平台_files/0(1)">
                  <div class="card_appmsg_content" title="标题4">标题4</div></div>
                <div class="card_appmsg_item  jsPhoneViewCard" data-index="2">
                  <img class="card_appmsg_thumb" src="./微信公众平台_files/0(2)">
                  <div class="card_appmsg_content" title="标题4">标题4</div></div>
              </div>
            </div>
          </div>
		  <div class="wx_phone_bd">
    <div class="wx_phone_preview_appmsg appmsg_wap">
        <div class="rich_media">
            <div class="rich_media_area_primary">
                <h2 class="rich_media_title" title="霸都大机密4">霸都大机密4</h2>
                <div class="rich_media_meta_list">
                    <em class="rich_media_meta rich_media_meta_text">2016-10-17</em>
                    <em class="rich_media_meta rich_media_meta_text">bibi</em>
                    <span class="rich_media_meta rich_media_meta_link" title="请发送到手机查看完整效果">霸都大机密</span>
                </div>
                <div class="rich_media_content">
					<!---->
                </div>
                
            </div>
        </div>
    </div>
</div>
        </div>
        <!--jsPhoneViewMain-->
        <div class="wx_view_container jsPhoneViewPlugin">
          <div>
            <ul class="wx_view_list">
              <li class="wx_view_item jsPhoneViewLink selected" data-id="card">图文消息</li>
              <li class="wx_view_item jsPhoneViewLink" data-id="appmsg">消息正文</li></ul>
			  <ul class="wx_article_crtl">        
        <li class="crtl_btn crtl_pre_btn disabled jsPhoneViewCard" data-index="-1">上一篇</li>        
        <li class="crtl_btn crtl_next_btn  jsPhoneViewCard" data-index="1">下一篇</li>
    </ul>
            <div class="btn_wx_phone_preview_wrp">
              <a class="btn_preview btn_default btn_wx_phone_preview jsPhoneViewPub">发送到手机预览</a></div>
          </div>
        </div>
      </div>
      <div class="mask"></div>
    </div>
	<script src="page/pc/js/jquery.js"></script>
	<script src="page/pc/js/bootstrap.min.js"></script>
	<script src="page/pc/js/jquery.scrollUp.js"></script>
	<script src="page/pc/js/jquery.dataTables.js"></script>

	<!-- Custom JS -->
	<script src="page/pc/js/menu.js"></script>
	
	<script src="js/Util.js"></script>
	<script src="page/pc/js/pc/messageDelivery/js/msg_delivery_mrg.js"></script>
	<script type="text/javascript">
	
	function cleanBox(){
		$("#conditionFrom").find("input[name=sucai_title]").val("");
		$("#conditionFrom").find("input[name=start_time]").val("");
		$("#conditionFrom").find("input[name=end_time]").val("");
		$("#conditionFrom").find("input[name=seq]").val("");
	}
      //ScrollUp
      $(function () {
        $.scrollUp({
          scrollName: 'scrollUp', // Element ID
          topDistance: '300', // Distance from top before showing element (px)
          topSpeed: 300, // Speed back to top (ms)
          animation: 'fade', // Fade, slide, none
          animationInSpeed: 400, // Animation in speed (ms)
          animationOutSpeed: 400, // Animation out speed (ms)
          scrollText: 'Top', // Text for element
          activeOverlay: false, // Set CSS color to display scrollUp active point, e.g '#00FFFF'
        });
        

        $('[data-toggle="popover"]').each(function (num,e) {
      //console.log(e.dataset.placement);
        var element = $(this);
        var id = element.attr('id');
        var mainOriginal = $(element).parent().siblings(".orginal_name").text();
        var deliveryOriginal = $(element).parent().siblings(".delivery_orginal_name").text();
        var txt = "请选择需要操作的公众号：";
        element.popover({
          trigger: 'manual',
          placement: e.dataset.placement, //top, bottom, left or right
          title: txt,
          html: 'true',
          content: conentTxt(id,mainOriginal,deliveryOriginal),
        }).on("click", function () {
          var _this = this;
          $(this).popover("show");
          $(this).siblings(".popover").on("mouseleave", function () {
            $(_this).popover('hide');
          });
        }).on("mouseleave", function () {
          var _this = this;
          setTimeout(function () {
            if (!$(".popover:hover").length) {
              $(_this).popover("hide");
            }
          }, 300);
        });
      });
        
      });
      //Data Tables
      $(document).ready(function () {
        $('#data-table').dataTable({
        	"bPaginate":true,"iDisplayLength":50,"bFilter":true,
         	"sPaginationType": "full_numbers"
        });
      });
      
      function syncWechatMaterial(){
      	
		$.post("wechat/ajax/syncdo", {
					
				}, function(data) {
					alert("同步完成");
				});
		alert("开始同步微信服务器上的素材，请稍后直接查看结果");
      }
      
      function conentTxt(txt,mainOriginal,deliveryOriginal){
      return "<table class='table table-bordered'><thead><tr><th style='width: 4%'>"+
      "<input type='checkbox' checked='true' onclick='checkAll(this);' /></th><th style='width: 20%'>公众号名称</th></tr></thead>"+
      "<tbody id='supplier_list' style='font-family: 微软雅黑;'>"+
      <%for(WechatOriginalInfoBean originalBean : originalList ){ %>
     "<tr class='gradeA ' style='background-color: #f7f7f7;'><th><input type='checkbox' checked='true' class='no-margin' value='<%=originalBean.getId() %>' /></th>"+
     "<th style='font-weight: normal;'><%=originalBean.getOriginal_name() %></th></tr>"+
     <%}%>
    "</tbody></table><input type='hidden' class='mainOriginal' value='"+mainOriginal+"'><input type='hidden' class='deliveryOriginal' value='"+deliveryOriginal+"'><input type='hidden' class='mater_media_id' value='"+txt+"'><a class='btn btn-success'  style='padding: 6px 12px;margin-left:5px;' onclick='delivery(this);'>"+
		"<i class='fa fa-check' style='padding-right: 5px;'></i>提交</a>";
      }
      function syncWechatMaterial(){
      	
		$.post("wechat/ajax/syncWechatMaterial.do", {
					
				}, function(data) {
					alert("同步完成");
				});
		alert("开始同步微信服务器上的素材，请稍后直接查看结果");
      }
      
    </script>

</body>
</html>