Pow = function(){
	return {
		powerCancel:function(id){
			if(confirm("确认需要取消当前商户以及其关联的商户该授权信息？")){
				$.post("../../supplier/admin/cancelPower.do",{"id":id,"supplierid":Util.getUrlParam('supid')},function(data){
					if(Util.empty(data)) alert("取消成功");
					else alert(data);
				});
			}
		},
		groupInfoBySupplier:function(){
			var supid = Util.getUrlParam('supid');
			var custid = Util.getUrlParam('custid');
			$('#dt_example').empty();
			$.post("../../discount/admin/listDisGroupInfo.do",{"custid":custid},function(data){
				var list = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"id="data-table"><thead><tr>';
				content += '<th style="width: 20%">授权折扣规则名称</th><th style="width: 70%">授权折扣规则详情</th><th style="width: 10%" class="hidden-phone">操作</th></tr></thead><tbody>';
				for ( var i = 0; i < list.length; i++) {
					var obj = list[i];
					$('#name').val(obj.customer_name);
					var style = i%2==0?'warn':'success';
					content += '<tr class="gradeX '+style+'"><td>'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.disinfo)+'</td><td><button class="btn btn-default btn-xs" onclick="Pow.powerCancel(\''+obj.id+'\')">取消授权</button></td></tr>';
				}
				content += '</tbody></table><div class="clearfix"></div>';
				$('#dt_example').append(content);
				Pow.tableRefresh('data-table');
			});
		},
		powerSupplier:function(){
			$('#dt_example').empty();
			$.post("../../supplier/admin/powerList.do",function(data){
				var list = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table"><thead>';
                content += '<tr><th style="width:5%"><input type="checkbox" class="no-margin" /></th><th style="width:14%">商户名称</th><th style="width:14%">';
                content += '当前状态</th><th style="width:28%">授权规则信息</th><th style="width:14%" class="hidden-phone">添加时间</th>';
                content += '<th style="width:14%">操作</th></tr></thead><tbody>';
                for ( var i = 0; i < list.length; i++) {
					var obj = list[i];
					var style = i%2==0?'warn':'success';
					content += '<tr class="gradeX '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'"/></td><td>'+Util.toStr(obj.name);
                    content += '</td><td>'+Util.toStatus(obj.status)+'</td><td>'+Util.toStr(obj.disinfo)+'</td><td>'+Util.toStr(obj.create_time)+'</td>';
                    content += '<td><div class="btn-group"><button class="btn btn-default" onclick="javascript:window.location.href=\'../../admin/powerinfo.do?supid='+obj.id+'&custid='+obj.customer_id+'\'">';
                    content += '查看授权信息</button></div></td></tr>';
				}
                content += '</tbody></table><div class="clearfix"></div>';
                $('#dt_example').append(content);
                Pow.tableRefresh('data-table');
			});
		},
		tableRefresh:function(obj){
			$('#'+obj).dataTable({
				"bPaginate":true,"iDisplayLength":10,"bFilter":true,
		          "sPaginationType": "full_numbers"
		        });
		},
		loadCust:function(){
			var tab1 = $('#dt_example').empty();
			$.post("../../supplier/admin/list.do",function(data){
				var list = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
				content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Pow.checkAll(\'data-table\',this);" /></th><th style="width: 25%">商户名称</th>';
				content += '<th style="width: 25%">商户地址</th><th style="width: 20%">联系人</th><th style="width: 25%">联系号码</th></tr></thead><tbody>';
				for(var i=0;i<list.length;i++){
					var obj = list[i];
					var style = i%2==0?'warn':'success';
					content += '<tr class="gradeA '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'" custid="'+obj.customer_id+'" /></td><td><label id="suppliername">'+Util.toStr(obj.name)+'</label></td>';
					content += '<td>'+Util.toStr(obj.address)+'</td><td>'+Util.toStr(obj.contacts_name)+'</td><td>'+Util.toStr(obj.contacts_phone)+'</td></tr>';
				}
				content += '</tbody></table>';
				tab1.append(content);
				Pow.tableRefresh('data-table');
			});
		},
		checkAll:function(id,obj){
			$('#'+id+' tbody').find('input[type=checkbox]').prop('checked',$(obj).prop('checked'));
		},
		loadDisGroup:function(){
			var tab2 = $('#dtab2 div[id=dt_example]').empty();
        	$.post("../../discount/admin/listDisGroup.do",function(data){
        		var list = eval('('+data+')');
        		var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table-group">';
				content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Pow.checkAll(\'data-table-group\',this);" /></th><th style="width: 25%">折扣规则名称</th>';
				content += '<th style="width: 50%">折扣规则详情</th><th style="width: 25%">备注</th></tr></thead><tbody>';
        		for(var i=0;i<list.length;i++){
        			var obj = list[i];
        			var style = i%2==0?'warn':'success';
        			content += '<tr class="gradeA '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'" custid="'+obj.customer_id+'" /></td><td><label id="groupname">'+Util.toStr(obj.name)+'</label></td><td>'+Util.toStr(obj.disinfo)+'</td><td>'+Util.toStr(obj.remark)+'</td></tr>';
        		}
        		content += '</tbody></table>';
        		tab2.append(content);
        		Pow.tableRefresh('data-table-group');
        	});
		},
		commitData:function(){
			var group = '[';
			var index = 0;
			$('#data-table-group tbody tr').each(function(){
				var obj = $(this).find('input[type=checkbox]');
				if(obj.prop('checked')){
					if(index!=0) group += ',';
					group += '{\'group_id\':\''+obj.prop('id')+'\',\'group_name\':\''+$(this).find('#groupname').text()+'\'}';
					index++;
				}
			});
			group += ']';
			var result = '[';
			var i = 0;
			$('#data-table tbody tr').each(function(){
				var obj = $(this).find('input[type=checkbox]');
				if(obj.prop('checked')){
					if(i!=0) result += ',';
					result += '{\'supid\':'+obj.prop('id')+',\'supname\':\''+$(this).find('#suppliername').text()+'\',\'custid\':\''+obj.attr("custid")+'\',\'groups\':'+group+'}';
					i++;
				}
			});
			result += ']';
			
			var returnObj = $('#result');
			$.post("../../discount/admin/power.do",{"params":result},function(data){
				if(Util.empty(data)) {
					returnObj.text("授权成功");
				}else returnObj.text(data);
			});
		}
	};
}();