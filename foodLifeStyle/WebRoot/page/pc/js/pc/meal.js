var error = '';
Meal=function(){
	return{
		checkAll:function(o){
			var checked = $(o).prop('checked');
			$(o).closest('#data-table').find('input[type=checkbox]').each(function(){
				$(this).prop('checked',checked);
			});
		},
		change:function(){
			$('#money').text(0);
			$('.widget-body tr').each(function(){
				var checked = $(this).find('input[type=checkbox]').prop('checked');
				var price = parseFloat($(this).find('#price').text());
				var money = parseFloat($('#money').text());
				var num = parseFloat($(this).find('#num').val());
				if(checked){
					$('#money').text((price*num)+money);
				}
			});
		},
		bill:function(){
			var money = $('#money').text();
			var index = 0;
			var params = '{"money":'+money+',"data":[';
			$('.widget-body tr').each(function(){
				var checkObj = $(this).find('input[type=checkbox]');
				var checked = checkObj.prop('checked');
				var id = checkObj.attr('id');
				var num = parseInt($(this).find('#num').val());
				var name = $(this).find('#name').text();
				if(checked){
					if(num<=0) error = '选中的可售商品购买数量必须大于0';
					else{
						if(index>0) params+=',';
						params += '{"id":"'+id+'","name":"'+name+'","num":"'+num+'"}';
						index ++;
					}
				}
			});
			params+=']}';
			if(!Util.empty(error)) alert(error);
			else{
				$.post('../../apply/admin/meal.do',{"params":params},function(data){
					if(Util.empty(data)) alert("购买成功！");
					else if(data.indexOf("余额不足")>=0) if(confirm(data)) window.location.href="../../admin/recharge.do";
					else alert(data);
				});
			}
		}
	};
}();

Re=function(){
	return{
		
	};
}();

Api=function(){
	return{
		
	};
}();