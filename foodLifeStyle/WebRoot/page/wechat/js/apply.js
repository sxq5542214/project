Apy = function(){
	return {
		audit:function(id,s){
			var num = $('#applynum').val();
			var params = {"status":s,"id":id};
			if(parseInt(s)==1){
				params.num = num;
				params.supid = $('.todo li.todo-done').attr('id');
			}
			$.post("../../app/apply/auditcommit.do",params,function(data){
				if(Util.empty(data)) {
					alert("审核成功");
					window.location.href='../../app/apply/auditlist.do';
				}else {
					alert(data);
					if(parseInt(s)==2) window.location.href='../../app/apply/auditlist.do';
				}
			});
		},
		apply:function(parcustid){
			$('.todo li.todo-done').each(function(){
				var id = $(this).attr('id');
				var proid = $(this).attr('name');
				var num = $(this).find('#num').val();
				if(Util.empty(num)) alert('请输入申请数量');
				else{
					$.post("../../app/apply/apply.do",{"id":id,"proid":proid,"custid":parcustid,"applynum":num,"remark":"微信端申领"},function(data){
						if(Util.empty(data)) alert("工单申请提交成功");
    					else alert(data);
					});
				}
			});
		}
	};
}();