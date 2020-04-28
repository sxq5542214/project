$(document).ready(function(){
	//循环li，使radio有默认值
	 $("li .eff_time ").each(function(index){
		 var dataDiv = $(this).closest(".oplData");
		 var id = $(dataDiv).find(".oplId").val();
		//获取当前时间
		 var d = new Date();
		 var nowMonth = d.getMonth()+1;
		 //获取预约时间
		 var eff_time = $(this).text();
		 var eff_date = eff_time.substring(eff_time.indexOf("：")+1,eff_time.length);
		 var strs= new Array();
		 if(eff_date != null && eff_date != ""){
			 strs = eff_date.split("-");
			 var eff_month = parseInt(strs[1]);
			 var effnum = eff_month - nowMonth;
			 if(effnum <= 0){
				 effnum = 0;
			 }
			 $("input[name='question"+id+"'][value="+effnum+"]").attr("checked",true); 

		 }
	 });
	$(".eff_num").click(function(){
		var inputSelf = this;
		var effNum = $("#effNum").val();
		var unEffNum = $("#unEffNum").val();
		var choiceDate = $(this).val(); 
		var id = $(inputSelf).closest(".oplData").find(".oplId").val();
		if(confirm("是否确定生效时间为："+$(inputSelf).closest(".oplData").find(".effparam"+$(this).val()).html())){
			if(choiceDate == 0 && !confirm("订单生效后无法再次修改预约时间，是否确定？")){
				return;
			}
			 var polData = {
					 choiceDate:choiceDate,
					 id:id
			        };
			 $.ajax({
                 type: "post",
                 url: "order/changeUserOrderEffDate.do",
                 data: polData,
                 success: function (result) {
                	 result = eval('('+result+')');
                	 var status = result.status;
                	 //网上级寻找父级节点，找到就返回（只会返回1个或不返回）
                	 var parentDemo = $(inputSelf).closest(".oplData");
                	 if(status == "1" || status == "2"){
                		 //实时生效订单，将生效后的列表转移到已生效里面
                		 $(parentDemo).find(".remark").html("订单备注："+result.remark);
                		 $(parentDemo).find(".eff_time").html(result.eff_time);
                		 $(parentDemo).append("<p><a class = \"execute_time\">生效时间："+result.execute_time+"</a></p>");
                		 $(parentDemo).find(".rowElem").remove();
                		 if($("#sp2").children(".ddlist").children().size() == 0){
                    		 $("#sp2").children(".ddlist").append("<li>"+$(parentDemo).html()+"</li>");
                		 }else{
                    		 $("#sp2").children(".ddlist").find('li:first').before("<li>"+$(parentDemo).html()+"</li>");
                		 }
                		 $(parentDemo).parent().remove();
                		 effNum = effNum*1 - 1;
                		 unEffNum = unEffNum*1 + 1;
                		 $("#unEffNum").val(unEffNum);
                		 $("#effNum").val(effNum);
                		 $("#reservation").children().html("可预约("+effNum+")");
                		 $("#effective").children().html("已生效("+unEffNum+")");
                		 if(status == "1"){
                			 alert("该预约订单已经实时生效！");
                		 }else{
                			 alert("该预约订单生效失败！请关注短信，商品所需金额已退还至您的账户！");
                		 }
                	 }else if(status == "0"){
                		 //修改预约时间
                		 $(parentDemo).find(".eff_time").html("预约时间："+result.eff_time);
                		 $(parentDemo).find(".remark").html("订单备注："+result.remark);
                		 $(parentDemo).find(".execute_time").html("生效时间："+result.execute_time);
                    	 alert("已成功生成预约定单！预约时间："+result.eff_time);
                	 }else if(!result.empty){
                		 alert("修改预约时间失败！");
                	 }
                 }
             });
		   }
	});
});