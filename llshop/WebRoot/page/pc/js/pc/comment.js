var replyDiv = "";
$(document).ready(function() {
//	window.onscroll = function(){
//		scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
//		console.log(scrollTop);
//    };
	$(".item-time").each(function(){
		$(this).text(cutDateStr($(this).text()));
	});
	$('body').on('click', '.btn-del', function () {
    	var id = $(this).siblings(".beanid").val();
    	var comment = $(this);
    	$.ajax({type : "POST",
				url : "admin/comment/deleteCommentInfo.do",
				data : {"id": id},
				success : function(data) {
					alert(data);
					$(comment).parent().parent().parent().remove();
				}
				});
    });
	
	$("#submitComment").click(function(){
		var conf_code = $("#conf_code").val();
		var parentid = $("#parentid").val();
		var msgtext = $("#msgtext").val();
		
		$.ajax({type : "POST",
			url : "admin/comment/toCommentInstertForAdmin.do",
			data : {"conf_code": conf_code,"parentid":parentid,"msgtext":msgtext},
			success : function(data) {
				console.log(data);
				var result = eval("("+data+")");
				var preplymsgtext = "";
				  if(result.preplymsgtext != null && result.preplymsgtext != ""){
					  preplymsgtext = result.preplymsgtext;
				  }
				var comment = "<tr class='gradeA '>" +
						"<td class='no'>1</td>" +
						"<td style='margin: 0 auto;text-align: center;'>"+result.nick_name+"</td>" +
						"<td>"+preplymsgtext+"</td>" +
						"<td>"+result.msgtext+"</td>" +
						"<td>"+cutDateStr(result.createtime)+"</td>" +
						"<td><div class='btn-group'><input type='hidden' class='beanid' name='id' value='"+result.id+"'>" +
						"<button data-toggle='button' class='btn btn-danger btn-xs btn-del'>删除</button></div></td></tr>";
				$("#all").find("#material_list").prepend(comment);
				$("#adminre").find("#material_list").prepend(comment);
				$("#all").find("#material_list").find("tr").each(function(n,e){
					$(e).find(".no").text(n+1);
				});
				$("#msgtext").val("");
				alert("回复成功！");
			}
			});
	});
	
	$(".icon").each(function(){
		var value = $(this).text();
		var newValue = $(this).closest(".btn-group").find(".form_item_value").val();
		if(value == newValue){
			$(this).closest(".btn-group").find(".form_item_name").text($(this).siblings("span").text());
		}
	});
	$(".query-item").each(function(){
		$(this).find("li").click(function(e){
//			console.log($(this).find("span").text());
			$(this).closest(".btn-group").find(".form_item_name").text($(this).find("span").text());
			$(this).closest(".btn-group").find(".form_item_value").val($(this).find(".icon").text());
		});
		
	});
	$(".data-clear").click(function(){
		$(this).closest(".btn-group").find(".form_item_name").text("请选择");
		$(this).closest(".btn-group").find(".form_item_value").val(null);
	});
});

//对于留言管理的单个操作，异步
function dealCommentInfoStatus(item,action,id,status,type){
	console.log(1);
	var msg = "";
  	if(action == "checked"){
  		msg = "是否确定审核通过！";
  	}else if(action == "checkfail"){
  		msg = "是否确定审核不通过！";
  	}else if(action == "checkdel"){
  		msg = "是否确定删除！";
  	}else if(action == "checkrecover"){
  		msg = "是否确定恢复！";
  	}
  	if(confirm(msg)){
		$.ajax({
			type : "POST",
			url : "admin/comment/dealCommentInfoStatus.do",
			data : {"action":action,"id":id,"status":status,"type":type},
			success : function(data) {
				if(data == "失败"){
					alert("操作失败！");
					return false;
				}
				var result = eval("("+data+")");
				if(result.length > 0){
					if(action == "checked"){
						$(item).closest(".gradeA").find(".item-status").text("审核通过");
						$(item).addClass("disabled");
					}else{
						$(item).closest(".gradeA").remove();
					}
					alert("操作完成！");
				}
			}
		});
  	}
}
var n1 = 0;
var n2 = 0;
var n3 = 0;
var scrollTop = 0;
function delete_data(action,id,status,type){
	if(confirm("确定删除数据？")){
  		checkAllCommentInfoStatus(action,valuelist,status,type);
  	}
}
	function toPage(n){
			$('#nowpage').val(n);
			$('#conditionFrom').submit();
		}
	
     function checkAll(obj){
			var checked = $(obj).prop('checked');
			$('input[type=checkbox]').prop('checked',checked);
		}
      
      function checkAllCommentInfoStatus(action,id,status,type){
		$.post("admin/comment/dealCommentInfoStatus.do",
			{"action":action,"id":id,"status":status,"type":type},
			function(msg) {
					alert("操作完成！");
					location.replace(location.href);
			});
      }
      
      function checkAllCommentInfo(action,status,type){
      	var valuelist = "";
      	$("input[name^='commentinfo']").each(function() {
      		if (this.checked) {
                valuelist += $(this).val() + ",";
            }
      	});
      	if(valuelist == null || valuelist == ""){
      		alert("请至少勾选一条数据！");
      		return false;
      	}
      	var msg = "";
      	if(action == "checked"){
      		msg = "是否确定全部审核通过！";
      	}else if(action == "checkfail"){
      		msg = "是否确定全部审核不通过！";
      	}else if(action == "checkpass"){
      		msg = "是否确定全部忽略！";
      	}else if(action == "checkdel"){
      		msg = "是否确定全部删除！";
      	}
      	if(confirm(msg)){
      		checkAllCommentInfoStatus(action,valuelist,status,type);
      	}
      }
      

      /**
		 * 获取评论信息
		 * 
		 * @param item
		 */
function getReplyList(item) {
    var blogList = document.getElementById("myModal-dialog");
	console.log($(item).offset().top);	
    blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
	var parentid = $(item).closest(".gradeA").find('input[type=checkbox]').val();
	var item_code = $(item).closest(".gradeA").find('.item-code').text();
	$("input[name='parentid']").each(function(n, e) {
		$(e).val(parentid);
	});
	$("input[name='conf_code']").each(function(n, e) {
		$(e).val(item_code);
	});
	// 初始化tab页面
	n1 = 0;
	n2 = 0;
	n3 = 0;
	initTable(null,parentid);
	// 异步加载留言的评论信息
	ajaxDataForReplyList(parentid,1);
	    	/**
			 * 当弹出框显示的时候，异步加载留言的评论数据
			 */
	$('#myModal').on('shown.bs.modal', function() {
		var modal = $(this);
		// 向弹出框跟新留言数据
		$(item).closest(".gradeA").find("td").each(function(n, e) {
			var className = $(e)[0].className.replace(" ", "");
			var text = $(e).html();
			$(modal).find("#material_list").find("td").each(function(nn, ee) {
				var modalTdClassName = $(ee)[0].className.replace(" ", "");
				if (modalTdClassName == className) {
					$(ee).html(text);
				}
			});
		});
	});
}
      
      /**
		 * 创建评论列表的通用方法
		 * 
		 * @param reply
		 */
      function createTableBody(replyList,type){
    	  //在此新建评论的table的header
    	  var table_header = "<table class='table table-condensed table-striped table-hover table-bordered pull-left' id='data-table'>" +
			"<thead><tr><th width='5%'>序号</th><th width='10%'>评论人</th><th width='20%'>父级评论</th><th>评论内容</th><th width='20%'>评论时间</th><th width='130px;'>操作</th></tr></thead>";
    	  var table_body = "<tbody id='material_list'>";
    	  if(replyList != null && replyList.length > 0){
    		  table_body = table_body + addReplyComment(replyList[0].replyBeanList,replyDiv,type);
    	  }
    	  table_body = table_body + "</tbody>";
    	  var table = table_header + table_body + "</table>";
    	  return table;
      }
      /**
       * 递归排列评论顺序
       * @param replayBean
       * @param replyDiv
       * @param flag
       * @returns
       */      
      function addReplyComment(replyList,replyDiv,type){
//    		console.log(replyDiv);
    		if(replyList != null && replyList != "" && replyList.length > 0){
    			for(var i = 0;i < replyList.length;i++){
    				var preplymsgtext = "";
    				  if(replyList[i].preplymsgtext != null && replyList[i].preplymsgtext != ""){
    					  preplymsgtext = replyList[i].preplymsgtext;
    				  }
    				  var parent_replyUserName = replyList[i].nick_name;
    				  if(replyList[i].parent_replyUserName != null && replyList[i].parent_replyUserName != ""){
    					  parent_replyUserName = parent_replyUserName + "<span style='padding: 0 2px;color: #74b749;' class='glyphicon glyphicon-circle-arrow-right'></span>" + replyList[i].parent_replyUserName;
    				  }
    	    		  if(type == "all" && replyList[i].status == 1 ){
    	    			  n1++;
    	    			  var body_item = "<tr class='gradeA '><td class='no'>"+n1+"</td>" +
    	    			"<td style='margin: 0 auto;text-align: left;'>"+parent_replyUserName+"</td>" +
    	    			"<td>"+preplymsgtext+"</td>" +
    	  		  		"<td>"+replyList[i].msgtext+"</td>" +
    	  		  		"<td>"+cutDateStr(replyList[i].createtime)+"</td>" +
    	  		  		"<td><div class='btn-group'><input type='hidden' class='beanid' name='id' value='"+replyList[i].id+"'>" +
    	  		  		"<button data-toggle='button' class='btn btn-danger btn-xs btn-del'>删除</button></div></td>" +
    	  		  		"</tr>";
    	    			  replyDiv = replyDiv + body_item;
    	    		  }else if(type == "adminre" && replyList[i].type == 1){
    	    			  n3++;
    	    			  var body_item = "<tr class='gradeA '><td class='no'>"+n3+"</td>" +
    	      			"<td style='margin: 0 auto;text-align: left;'>"+parent_replyUserName+"</td>" +
    	      			"<td>"+preplymsgtext+"</td>" +	
    	      			"<td>"+replyList[i].msgtext+"</td>" +
    	    		  		"<td>"+cutDateStr(replyList[i].createtime)+"</td>" +
    	    		  		"<td><div class='btn-group'><input type='hidden' class='beanid' name='id' value='"+replyList[i].id+"'>" +
    	    		  		"<button data-toggle='button' class='btn btn-danger btn-xs btn-del'>删除</button></div></td>" +
    	    		  		"</tr>";
    	    			  replyDiv = replyDiv + body_item;
    	    		  }else if(type == "uncheck" && replyList[i].status == 0){
    	    			  n2 = n2 + 1;
    	    			  var body_item = "<tr class='gradeA '><td class='no'>"+n2+"</td>" +
    	        			"<td style='margin: 0 auto;text-align: left;'>"+parent_replyUserName+"</td>" +
    	        			"<td>"+preplymsgtext+"</td>" +
    	        			"<td>"+replyList[i].msgtext+"</td>" +
    	      		  		"<td>"+cutDateStr(replyList[i].createtime)+"</td>" +
    	      		  		"<td><div class='btn-group'><input type='hidden' class='beanid' name='id' value='"+replyList[i].id+"'>" +
    	      		  		"<a data-toggle='button' onclick='dealCommentInfoStatus(this,\"checked\","+replyList[i].id+",0,0);' class='btn btn-success btn-xs btn-checked'>通过</a>" +
    	      		  		"<a data-toggle='button' onclick='dealCommentInfoStatus(this,\"checkfail\","+replyList[i].id+",0,0);' class='btn btn-warning btn-xs btn-uncheck'>不通过</a>"+
    	      		  		"<a data-toggle='button' class='btn btn-danger btn-xs btn-del'>删除</button>"+
    	      		  		"</div></td>" +
    	      		  		"</tr>";
    	    			  replyDiv = replyDiv + body_item;
    	    		  }
	    			  
    				replyDiv = addReplyComment(replyList[i].replyBeanList,replyDiv,type);
    			}
    		}
    		return replyDiv;
    	}
      
      /**
       * 请求评论列表信息
       * @param parentid
       * @param nowpage
       */
      function ajaxDataForReplyList(parentid,nowpage){
    		$.ajax({
    			type : "POST",
    			url : "admin/comment/getCommentReplyInfoFroAjax.do",
    			data : {
    				"parentid" : parentid,"nowpage":nowpage
    			},
    			success : function(data) {
    				if (data != null && data != "") {
    					var result = eval("(" + data + ")");
//    					var replyList = result.dataList;
    					n1 = 0;
    					n2 = 0;
    					n3 = 0;
    					initTable(result,parentid);
    				}
    			}
    		});
      }
      
      function initTable(result,parentid){
    	  var dataList;
    	  if(result == null){
    		  dataList = null;
    	  }else{
    		  dataList = result.dataList;
    	  }
			//新建tab1（所有评论的table）包含管理员以及用户的评论，**都是审核通过的
			$("#all").children().html(createTableBody(dataList,"all"));
			
			//新建tab2（未审核评论的table）包含用户的评论
			$("#uncheck").children().html(createTableBody(dataList,"uncheck"));
			$("#uncheck_title").text("未审核"+"("+n2+")");
			//新建tab3（管理员评论的table）包含管理员的评论
			$("#adminre").children().html(createTableBody(dataList,"adminre"));
			$("#adminre_title").text("管理员评论"+"("+n3+")");
			$("#page_table_bottom").html(setPageButton(result,parentid));
      }
      function cutDateStr(dataStr){
    	  if(dataStr != null && dataStr.length > 19){
    		  return dataStr.substr(0,19);
    	  }else{
    		  return dataStr;
    	  }
      }
      
      function setPageButton(pd,parentid){
    	  var pageDiv = "";
    	  if(pd != null){
    		  $("#all_title").text("所有评论"+"("+pd.totalcount+")");
    		  pageDiv = pageDiv + "<span><a tabindex='0' class='paginate_button' onclick='ajaxDataForReplyList("+parentid+","+pd.nowpage+")'>首页</a>";
    		  console.log(pd.nowpage);
        	  console.log(pd.totalpage);
        	  if(pd.nowpage>1){
        		  pageDiv = pageDiv + "<a tabindex='0' class='paginate_button' >.....</a>";
        	  } 
        	  for(var i = 1 ; i <= pd.totalpage; i ++) {
        		    var classStr = "paginate_button";
        		    if(pd.nowpage == i){
        		    	classStr = "paginate_active";
        		    }
        		    if(i < (pd.nowpage*1+5) && i > (pd.nowpage*1-5)){
        		       pageDiv = pageDiv + "<a tabindex='0' class='"+classStr +"' onclick='ajaxDataForReplyList("+parentid+","+i+")' >"+i+" </a>";
        		    }
        	  }
        	  if(pd.nowpage < pd.totalpage){
        		  pageDiv = pageDiv + "<a tabindex='0' class='paginate_button' >.....</a>";
        	  }
        	  pageDiv = pageDiv + "<a tabindex='0' class='paginate_button' onclick='ajaxDataForReplyList("+parentid+","+pd.totalpage+")'>尾页</a></span>";
    	  }
    	  return pageDiv;
     }
      
      
      function showBigImg(item,url,id){
    	  var blogList = document.getElementById("imgModal");
    	    blogList.style.paddingTop = ($(item).offset().top*1-300)+"px";
    	  $("#imgModal").find("img").attr("src",url);
    	  $("#imgModal").find(".comment_img_id").val(id);
      }
      
      function deleteImg(item,thumb){
    	  console.log($(thumb));
    	  var id =$(item).find(".comment_img_id").val();
    	  if(confirm("确定删除图片？")){
    		  $.ajax({
    			  type : "POST",
      				url : "admin/comment/deleteCommentInfoImg.do",
      				data : {"id" : id},
      				success : function(data) {
      					alert(data);
      					$('#imgModal').modal('hide');
      					var Imgarray = document.getElementsByTagName("img");
      					for ( var i = 0; i < Imgarray.length; i++) {
      						if(Imgarray[i].getAttribute("img-data") == id){
      							Imgarray[i].remove();
  							}
      					}
      			}
    		  });
    		  
    	  }
      }
      