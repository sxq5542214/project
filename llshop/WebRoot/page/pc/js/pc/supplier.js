var ids = '';
Sup = function(){
	return {
		udpateSupplierStatus:function(id,status,issale){
			$.post("../../supplier/admin/update.do",{"id":id,"status":status,"issale":issale},function(data){
				if(Util.empty(data)) {
					Sup.listSupplier();
					alert("更新成功");
				}else alert(data);
			});
		},
		batchUpdateStatus:function(status,issale){
			Sup.ids();
			if(ids.length>0){
				$.post("../../supplier/admin/batchUpdate.do",{"ids":ids,"status":status,"issale":issale},function(data){
					if(Util.empty(data)) {
						Sup.listSupplier();
						alert("更新成功");
					}else alert(data);
				});
			}else alert('请至少选中一条需要操作的记录!');
		},
		importExcel:function(){
			var disgroupid = '';
			$('#data-content input[type=checkbox]').each(function(){
				var obj =$(this);
				if(obj.prop('checked')) discount+=obj.prop('id');
			});
			if(Util.empty(disgroupid)) alert('请至少选择一个折扣规则！');
			else{
				$.post("../../supplier/admin/import.do",{"params":outtext,"disgroupid":disgroupids},function(data){
					alert("导入成功");
				});
			}
		},
		insertUiLoad:function(){
			Sup.loadDis();
			var id = Util.getUrlParam("id");
			var submit = $('#submitSupplier');
			if(Util.empty(id)){
				submit.attr("value","添加商户");
			}else {
				submit.attr("value","修改商户");
				Sup.loadSupplierById(id);
			}
			submit.attr("onclick","Sup.insertSupplier('"+id+"');");
		},
		loadSupplierById:function(id){
			$.post("../../supplier/admin/find.do",{"id":id},function(data){
				var obj = eval('('+data+')');
				$('#name').val(obj.name);
				$('#status').val(obj.status);
				$('#type').val(obj.type);
				$('#balance').val(obj.balance);
				$('#iscreate').val(obj.iscreate);
				$('#address').val(obj.address);
				$('#points').val(obj.points);
				$('#credit').val(obj.credit);
				$('#deposit_money').val(obj.deposit_money);
				$('#discount').val(obj.discount);
				$('#contacts_name').val(obj.contacts_name);
				$('#contacts_phone').val(obj.contacts_phone);
				$('#remark').val(obj.remark);
			});
		},
		listRefresh:function(){
			$('#data-table').dataTable({
				"bPaginate":true,"iDisplayLength":10,"bFilter":true,
		          "sPaginationType": "full_numbers",
		        });
		},
		listSupplier:function(){
			var list = $('#dt_example');
			list.empty();
			var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left"	id="data-table">';
				content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Sup.checkAll(this);" /></th><th style="width: 12%">商户名称</th>';
				content += '<th style="width: 8%">状态</th>';
				content += '<th style="width: 13%" class="hidden-phone">商户地址</th>';
				content += '<th style="width: 9%" class="hidden-phone">账户保证金</th><th style="width: 8%" class="hidden-phone">账户余额</th>';
				content += '<th style="width: 7%" class="hidden-phone">管理员</th><th style="width: 7%" class="hidden-phone">管理员号码</th>';
				content += '<th style="width: 8%" class="hidden-phone">折扣额度</th><th style="width: 10%" class="hidden-phone">操作</th>';
				content += '</tr></thead><tbody id="supplier_list">';
				$.post("../../supplier/admin/list.do",function(data){
					var listObj = eval('('+data+')');
					for ( var i = 0; i < listObj.length; i++) {
						var obj = listObj[i];
						var style = 'error';
						var status = obj.status;
						if(i%2==0) style='success';
						content += '<tr class="gradeA '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'" /></td><td>'+obj.name+'</td><td>'+Util.toStatus(status)+'</td>';
						content += '<td>'+Util.toStr(obj.address)+'</td>';
						content += '<td>¥'+Util.toInt(obj.deposit_money)+'</td><td>¥'+Util.toInt(obj.balance)+'</td><td class="hidden-phone">'+Util.toStr(obj.contacts_name)+'</td>';
						content += '<td class="hidden-phone">'+Util.toStr(obj.contacts_phone)+'</td><td class="hidden-phone">'+Util.toStr(obj.discount_name)+'</td>';
						content += '<td><div class="btn-group"><button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>';
						content += '</button><ul class="dropdown-menu pull-right"><li><a href="javascript:window.location.href=\'../../admin/supIns.do?id='+obj.id+'\'">编辑</a></li>';
						content += '<li><a onclick="Sup.udpateSupplierStatus(\''+obj.id+'\',\''+(status==0?1:0)+'\',\''+(status==0?1:0)+'\')">'+(status==0?'恢复':'暂停')+'</a></li></ul></div></td></tr>';
					}
					content += '</tbody></table><div class="clearfix"></div>';
					list.append(content);
					Sup.listRefresh();//重新刷新一下表格分页
				});
		},
		insertSupplier:function(id){
			var params = {};
			var name = $('#name').val();
			var type = $('#type').val();
			var balance = $('#balance').val();
			var iscreate = $('#iscreate').val();
			var address = $('#address').val();
			var points = $('#points').val();
			var credit = $('#credit').val();
			var deposit_money = $('#deposit_money').val();
			var discount = '';
			var contacts_name = $('#contacts_name').val();
			var contacts_phone = $('#contacts_phone').val();
			var remark = $('#remark').val();
			$('#data-content input[type=checkbox]').each(function(){
				var obj =$(this);
				if(obj.prop('checked')) discount+=obj.prop('id');
			});
			if(Util.empty(name)) alert(' 请输入商户名称');
			else if(Util.empty(contacts_name)) alert('请输入管理员');
			else if(Util.empty(contacts_phone)) alert('请输入管理员号码');
			else if(Util.validPhone(contacts_phone)) alert("请输入正确的手机号码");
			else{
				params.name = name;
				params.type = type;
				params.address = address;
				params.balance = balance;
				params.points = points;
				params.deposit_money = deposit_money;
				params.contacts_name = contacts_name;
				params.contacts_phone = contacts_phone;
				params.remark = remark;
				params.disgroupid = discount;
				params.credit = credit;
				params.iscreate = iscreate;
				var postUrl = "../../supplier/admin/insert.do";
				if(Util.notEmpty(id)) {
					params.id = id;
					postUrl = "../../supplier/admin/update.do";
				}
				$.post(postUrl,params,function(data){
					if(Util.empty(data)) window.location.href = '../../admin/sup.do';
					else alert(data);
				});
			}
		},
		checkAll:function(obj){
			var checked = $(obj).prop('checked');
			$('input[type=checkbox]').prop('checked',checked);
		},
		ids:function(){
			ids = '';
			$('#supplier_list').find('input[type=checkbox]').each(function(){
				var obj = $(this);
				if(obj.prop('checked')){
					if(ids.length>0) ids+=',';
					ids += obj.attr('id');
				}
			});
		},
		loadDis:function(){
			var dis = $('#data-content');
			dis.empty();
			$.post("../../discount/admin/listDisGroup.do",function(data){
				var list = eval('('+data+')');
				for(var i=0;i<list.length;i++){
					var style = i%2==0?"success":"warn";
					dis.append('<tr class="gradeX '+style+'"><td><input type="checkbox" class="no-margin" id="'+list[i].id+'" /></td><td>'+list[i].name+'</td><td>'+list[i].disinfo+'</td><td>'+list[i].remark+'</td></tr>');
				}
				Sup.listRefresh();//重新刷新一下表格分页
			});
		}
	};
}();