Apy = function(){
	return{
		listRefresh : function() {
			$('#data-table').dataTable({
				"bPaginate":true,"iDisplayLength":10,"bFilter":true,
				"sPaginationType" : "full_numbers"
			});
		},
		loadMyApplyList:function(){//我的申领工单列表
			var list = $('#dt_example');
			list.empty();
			$.post("../../apply/admin/mylist.do",function(data){
				var dataList = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
                content += '<thead><tr><th style="width:5%"><input type="checkbox" class="no-margin" /></th><th style="width:14%">申领商品';
                content += '</th><th style="width:10%">申领数量</th><th style="width:10%">当前状态</th><th style="width:10%" class="hidden-phone">';
                content += '当前库存</th><th style="width:14%" class="hidden-phone">申领时间</th><th style="width:14%" class="hidden-phone">';
                content += '审核时间</th><th style="width:14%" class="hidden-phone">备注</th><th style="width:14%" class="hidden-phone">';
                content += '操作</th></tr></thead><tbody>';
                for(var i=0;i<dataList.length;i++){
                	var obj = dataList[i];
                	var status = obj.status==0?'未审核':obj.status==1?'审核通过':obj.status==2?'审核不通过':'已取消';
                	var style = i%2==0?'error':'success';
                	var action = obj.status==0?'<div class="btn-group"><button class="btn btn-warning" onclick="Apy.cancelApply(\''+obj.id+'\');">取消</button></div>':'';
                	content += '<tr class="gradeA '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'" /></td>';
                    content += '<td>'+Util.toStr(obj.product_name)+'</td><td>'+Util.toStr(obj.apply_num)+'</td><td>'+status+'</td><td>'+Util.toStr(obj.supplier_store_num)+'</td>';
                    content += '<td class="hidden-phone">'+obj.create_time+'</td><td class="hidden-phone">'+obj.modify_time+'</td>';
                    content += '<td class="hidden-phone">'+obj.remark+'</td><td>';
                    content += action+'</td></tr>';
                }
                content += '</tbody></table><div class="clearfix"></div>';
                list.append(content);
                Apy.listRefresh();
			});
		},
		loadMyAuditList:function(){//我的审核列表
			var status = $('#status').val();
			var product_name = $('#product_name').val();
			var supplier_name = $('#supplier_name').val();
			var list = $('#dt_example');
			list.empty();
			$.post("../../apply/admin/myauditlist.do",{"status":status,"product_name":product_name,"supplier_name":supplier_name},function(data){
				var dataList = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
                content += '<thead><tr><th style="width:14%">申领商户</th><th style="width:14%">申领商品';
                content += '</th><th style="width:8%">申领数量</th><th style="width:8%">当前状态</th><th style="width:10%" class="hidden-phone">';
                content += '当前库存</th><th style="width:10%" class="hidden-phone">申领时间</th><th style="width:10%" class="hidden-phone">';
                content += '审核时间</th><th style="width:14%" class="hidden-phone">备注</th><th style="width:10%" class="hidden-phone">';
                content += '操作</th></tr></thead><tbody>';
                for(var i=0;i<dataList.length;i++){
                	var obj = dataList[i];
                	var status = obj.status==0?'未审核':obj.status==1?'审核通过':obj.status==2?'审核不通过':'已取消';
                	var style = i%2==0?'error':'success';
                	var action = obj.status==0?'<div class="btn-group"><button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">操作 <span class="caret"></span></button><ul class="dropdown-menu pull-right"><li><a href="#" data-toggle="modal" onclick="Apy.loadMinusSupplier(\''+obj.id+'\',\''+obj.apply_num+'\')">审核通过</a></li><li><a onclick="Apy.auditNoApply(\''+obj.id+'\');">审核不通过</a></li></ul></div>':'';
                	content += '<tr class="gradeA '+style+'">';
                    content += '<td>'+obj.supplier_name+'</td><td>'+obj.product_name+'</td><td>'+obj.apply_num+'</td><td>'+status+'</td><td>'+obj.supplier_store_num+'</td>';
                    content += '<td class="hidden-phone">'+obj.create_time+'</td><td class="hidden-phone">'+obj.modify_time+'</td>';
                    content += '<td class="hidden-phone">'+obj.remark+'</td><td>'+action+'</td></tr>';
                }
                content += '</tbody></table><div class="clearfix"></div>';
                list.append(content);
                $('#dt_example_sup').append(content);
                $('#dt_example_sup table').dataTable({
    		          "sPaginationType": "full_numbers"
    		        });
                Apy.listRefresh();
			});
		},
		loadApplyCustomer:function(){//可以申领的客户列表
			var tab1 = $('#dt_example');
			tab1.empty();
			$.post("../../apply/admin/applyCustList.do",function(data){
				var dataList = eval('('+data+')');
				var content = ' <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
				content += '<thead><tr><th style="width: 5%"></th><th style="width: 20%">客户名称</th>';
				content += '<th style="width: 25%">客户地址</th><th style="width: 20%">联系人</th><th style="width: 20%">联系号码</th></tr></thead><tbody>';
				for(var i=0;i<dataList.length;i++){
					var obj = dataList[i];
					var style = i%2==0?'success':'warn';
					content += '<tr class="gradeA '+style+'"><td><input type="checkbox" onclick="Apy.checkCustOne(this);" class="no-margin" id="'+obj.id+'" /></td><td>'+Util.toStr(obj.name)+'</td><td>'+Util.toStr(obj.address)+'</td><td>'+Util.toStr(obj.contacts_name)+'</td><td>'+Util.toStr(obj.contacts_phone)+'</td></tr>';
				}
				content += '</tbody></table>';
				tab1.append(content);
				Apy.listRefresh();
			});
		},
		checkCustOne:function(o){
			var isChecked = $(o).prop('checked');
			$('#data-table tbody input[type=checkbox]').each(function(){
				$(this).prop('checked',false);
			});
			$(o).prop('checked',isChecked);
		},
		checkAll:function(obj){
			var checked = $(obj).prop('checked');
			$('#apply_list').find('input[type=checkbox]').prop('checked',checked);
		},
		cancelApply:function(id){
			$.post("../../apply/admin/cancelApply.do",{"id":id},function(data){
				if(Util.empty(data)){
					alert("申领取消成功！");
					Apy.loadMyApplyList();
				} else alert(data);
			});
		},
		loadMinusSupplier:function(id,num){
			$('#apply_num').val(num)
			$.post("../../apply/admin/supprocustlist.do",{"id":id},function(data){
				var list = eval('('+data+')');
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="supplier_list">';
				content += '<thead><tr><th style="width: 5%"></th><th style="width: 14%">商户名称</th><th style="width: 14%">商户地址</th>';
				content += '<th style="width: 14%">联系人</th><th style="width: 14%" class="hidden-phone">联系号码</th></tr></thead><tbody>';
				for(var i=0;i<list.length;i++){
					var obj = list[i];
					var style = i%2==0?'success':'warn';
					content += '<tr class="gradeX '+style+'"><td><input onclic="Apy.checkSupOne(this)" type="checkbox" class="no-margin" id="'+obj.id+'" /></td>';
					content += '<td>'+obj.name+'</td><td>'+obj.address+'</td><td>'+obj.contacts_name+'</td><td class="hidden-phone">'+obj.contacts_phone+'</td></tr>';
				}
				$('#dt_supplier').empty();
				$('#dt_supplier').append(content);
				$('#supplier_list').dataTable({"sPaginationType": "full_numbers"});
				$('#auditcommit').attr('onclick','Apy.auditYesApply(\''+id+'\',\''+num+'\');');
			});
			$('#modalMd').modal();
		},
		auditNoApply:function(id){
			if(confirm("确认审核不通过？")){
				Apy.auditApply(id,2);
			}
		},
		auditYesApply:function(id,num){
			var isChecked = false;
			var supid = 0;
			$('#supplier_list tbody input[type=checkbox]').each(function(){
				var checked = $(this).prop('checked');
				if(checked){
					isChecked = true;
					supid = $(this).attr("id");
				}
			});
			if(!isChecked) alert("请选择一个商户");
			else Apy.auditApply(id,1,supid,num);
		},
		auditApply:function(id,status,supid,num){
			if(parseInt(num)<=0){
				alert("审核通过数据不能小于等于0！");
			}else{
				$.post("../../apply/admin/audit.do",{"id":id,"status":status,"supid":supid,"num":num},function(data){
					if(Util.empty(data)){
						alert("审核成功！");
						$('#modalMd').modal('hide');
						Apy.loadMyAuditList();
					}else alert(data);
				});
			}
		},
		checkSupOne:function(o){
			var isChecked = $(o).prop('checked');
			$('#supplier_list tbody input[type=checkbox]').each(function(){
				$(this).prop('checked',false);
			});
			$(o).prop('checked',isChecked);
		}
	};
}();