function toPage(n){
	$('#nowpage').val(n);
	$('#conditionFrom').submit();
}

function checkAll(obj){
	var checked = $(obj).prop('checked');
	$(obj).closest(".table").find('input[type=checkbox]').prop('checked',checked);
}

////预览窗口打开
//$(".preview_mater").click(function(){
//	$(".wx_phone_preview_wrp").show();
//});
////关闭预览窗口
//$(".btn_phone_preview_closed").click(function(){
//	$(".wx_phone_preview_wrp").hide();
//});

function delivery(obj){
	var media_ids = "";
	var originalids = "";
	var original_names = "";
	$(obj).siblings(".table").find('input[type=checkbox]');
	var media_id = $(obj).siblings(".mater_media_id").val();
	//批量操作(分发或删除)
	if(media_id == "deliveryAll" || media_id == "delAll" || media_id == "sendAll"){
		var flag = true;
		$("input[type=checkbox][class=materiterm]").each(function(num,e){
			if($(e).is(":checked")){
				flag = false;
				media_ids = media_ids + "," +$(e).val();
			}
		});
		if(flag){
			alert("请勾选需要操作的消息！");
			return false;
		}
	}else{
		media_ids = $(obj).closest(".gradeA").find('input[type=checkbox]').val();
	}
	//判断是否有元素选中
	if(!$(obj).siblings(".table").find('input[type=checkbox][class=no-margin]').is(':checked')){
		alert("请勾选相应的公众号！");
		return false;
	}
	var mainOriginal = $(obj).siblings(".mainOriginal").val();
	var deliveryOriginal = $(obj).siblings(".deliveryOriginal").val();
	deliveryOriginal = mainOriginal + "],[" + deliveryOriginal.substring(1,deliveryOriginal.length-1);
	var originalList = deliveryOriginal.split("],[");
	$(obj).siblings(".table").find('input[type=checkbox][class=no-margin]').each(function(num,e){
		if($(e).is(":checked")){
			if($.inArray($(e).parent().siblings().text(), originalList) == "-1" && media_id == "sendToAll"){
				alert($(e).parent().siblings().text()+"未分发素材，无法发送！");
				return false;
			}
			originalids = originalids + "," + $(e).val();
			original_names = original_names + "," + $(e).parent().siblings().text();
		}
	});
	
	
	var action = "";
	var msg = "";
	//分发操作
	if(media_id == "delAll"){
		action = "DEL";
		msg = "确定将素材从["+original_names+"]里删除！";
	}else if(media_id == "sendAll" || media_id == "sendToAll" ){
		action = "SEND";
		msg = "发送前请确定公众号都已经分发过素材，没有分发素材的公众号无法发送素材消息，确定向["+original_names+"]全员发送素材！";
	}else{
		action = "ADD";
		msg = "确定将素材分发到["+original_names+"]里！";
	}
	
	if(originalids != null && originalids != "" &&confirm(msg)){
		$(".hidden_body").show();
		if(action == "SEND"){
			$.ajax({
				type : "POST",
				url : "wechat/delivery/sendWechatMaterialToUserAll.do",
				data : {"media_ids":media_ids,"originalids":originalids},
				success : function(data){
					console.log("SEND");
					alert("发送请求提交完成，等待发送完成！");
					$(".hidden_body").hide();
					location.reload();
				}
			});
		}else{
			$.ajax({
				type : "POST",
				url : "wechat/delivery/deliveryWechatMaterialInfo.do",
				data : {"media_ids":media_ids,"originalids":originalids,"action":action},
				success : function(data){
					var result = eval("("+data+")");
					var successnum = 0;
					var failnum = 0;
					if(result.length > 0){
						for(var i=0;i<result.length;i++){
							if(result[i].errorcode == "0" || result[i].errorcode == 0){
								successnum++;
							}else{
								failnum++;
							}
						}
					}
					alert("分发完成，共"+successnum+"条，成功；"+failnum+"条，失败！");
					$(".hidden_body").hide();
					location.reload();
				}
			});
		}		
	}
	

	
	
}