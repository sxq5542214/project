var ids = '';
Operat = function(){
	return {
		listRefresh:function(){
				$('#data-table').dataTable({
		          "sPaginationType": "full_numbers",
		          "iDisplayLength":10
		        });
		},
		listCustomerAdmin:function(){
			var list = $('#dt_example');
			list.empty();
			$.post("../../customerAdmin/admin/list.do",function(data){
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
				content += '<thead><tr><th style="width:5%"><input type="checkbox" class="no-margin" onchange="Operat.checkAll(this);" /></th>';
				content += '<th style="width:14%">用户昵称</th><th style="width:14%">登录账户</th><th style="width:14%">当前状态</th>';
				content += '<th style="width:14%" class="hidden-phone">操作</th></tr></thead><tbody id="operat_list">';
				var dataList = eval('('+data+')');
				for(var i=0;i<dataList.length;i++){
					var style = 'error';
					var status = dataList[i].status;
					var id = dataList[i].id;
					if(i%2==0) style='success';
					else if(i%3==0) style='error';
					content += '<tr class="gradeA '+style+'"><td><input type="checkbox" id="'+id+'" class="no-margin" /></td><td>';
                    content += Util.toStr(dataList[i].nickname)+'</td><td>'+Util.toStr(dataList[i].username)+'</td><td class="hidden-phone">'+Util.toStatus(status)+'</td><td><div class="btn-group">';
                    content += '<button data-toggle="dropdown" class="btn btn-default btn-xs dropdown-toggle">功能 <span class="caret"></span>';
                    content += '</button><ul class="dropdown-menu pull-right"><li><a href="javascript:window.location.href=\'../../admin/operatIns.do?id='+id+'\'">编辑</a></li><li><a onclick="Operat.updateStatusCustomerAdmin(\''+id+'\',\''+status+'\');">'+(status==0?'恢复':'暂停')+'</a></li>';
                    content += '<li><a onclick="Operat.deleteCustomerAdmin(\''+id+'\');">删除</a></li></ul></div></td></tr>';
				}
				content += '</tbody></table><div class="clearfix"></div>';
				list.append(content);
				Operat.listRefresh();//重新刷新一下表格分页
			});
		},
		insertCustomerAdmin:function(){
			var nickname = $('#nickname').val();
			var username = $('#username').val();
			var password = $('#password').val();
			var password_reset = $('#password_reset').val();
			var status = $('#status').val();
			if(Util.empty(nickname)) alert("请输入用户名称");
			else if(Util.empty(username)) alert("请输入登录账户");
			else if(Util.empty(password)) alert("请输入登录密码");
			else if(Util.empty(password_reset)) alert("请再次输入登录密码");
			else if(password!=password_reset){
				alert("两次密码输入不同，请确认后再提交！");
			}else{
				$.post("../../customerAdmin/admin/insert.do",{"nickname":nickname,"username":username,"password":password,"status":status},function(data){
					if(Util.empty(data)) window.location.href="../../admin/operat.do";
					else alert(data);
				});
			}
		},
		deleteCustomerAdmin:function(id){//删除操作员信息
			if(confirm("确定需要删除当前操作员信息吗？")){
				$.post("../../customerAdmin/admin/delete.do",{"id":id},function(data){
					if(Util.empty(data)) {
						Operat.listCustomerAdmin();
						alert("当前账号已删除");
					}else alert(data);
				});
			}
		},
		updateStatusCustomerAdmin:function(id,status){//更新操作员状态信息
			var s = status==0?1:0;
			var content = s==0?'暂停':'恢复';
			$.post("../../customerAdmin/admin/update.do",{"id":id,"status":s},function(data){
				if(Util.empty(data)) {
					Operat.listCustomerAdmin();
					alert("该账号已"+content);
				}else alert(data);
			});
		},
		editCustomerAdmin:function(){//修改界面加载
			var id = Util.getUrlParam("id");
			var add = $('#addCustomerAdmin');
			if(Util.notEmpty(id)){
				add.attr("value","修改操作员");
				add.attr("onclick","Operat.updateCustomerAdmin('"+id+"');");
				$.post("../../customerAdmin/admin/find.do",{"id":id},function(data){
					var obj = eval('('+data+')');
					$('#nickname').val(obj.nickname);
					$('#username').val(obj.username);
					$('#password').val(obj.password);
					$('#password_reset').val(obj.password);
					$('#status').val(obj.status);
				});
			}else{
				add.attr("onclick","Operat.insertCustomerAdmin();");
			}
		},
		updateCustomerAdmin:function(id){//更新操作员信息
			var nickname = $('#nickname').val();
			var username = $('#username').val();
			var password = $('#password').val();
			var password_reset = $('#password_reset').val();
			var status = $('#status').val();
			if(Util.empty(nickname)) alert("请输入用户名称");
			else if(Util.empty(username)) alert("请输入登录账户");
			else if(Util.empty(password)) alert("请输入登录密码");
			else if(Util.empty(password_reset)) alert("请再次输入登录密码");
			else if(password!=password_reset){
				alert("两次密码输入不同，请确认后再提交！");
			}else{
				$.post("../../customerAdmin/admin/update.do",{"id":id,"nickname":nickname,"username":username,"password":password,"status":status},function(data){
					if(Util.empty(data)) {
						alert("当前账号信息已更新成功");
						window.location.href="../../admin/operat.do";
					}else alert(data);
				});
			}
		},
		checkAll:function(obj){
			var checked = $(obj).prop('checked');
			$('input[type=checkbox]').prop('checked',checked);
		},
		batDel:function(){
			if(confirm("确定需要删除选中的操作员信息吗？")){
				Operat.ids();
				if(ids.length>0){
					$.post("../../customerAdmin/admin/batDel.do",{"ids":ids},function(data){
						if(Util.empty(data)) {
							Operat.listCustomerAdmin();
							alert("批量删除成功");
						}else alert(data);
					});
				}else alert("请至少选中一条需要删除的记录！");
			}
		},
		batStatus:function(value){
			Operat.ids();
			if(ids.length>0){
				$.post("../../customerAdmin/admin/batUpdate.do",{"ids":ids,"status":value},function(data){
					if(Util.empty(data)) {
						Operat.listCustomerAdmin();
						alert("更新成功");
					}else alert(data);
				});
			}else alert("请至少选中一条需要操作的记录！");
		},
		ids:function(){
			ids = '';
			$('#operat_list').find('input[type=checkbox]').each(function(){
				var obj = $(this);
				if(obj.prop('checked')){
					if(ids.length>0) ids+=',';
					ids += obj.attr('id');
				}
			});
		}
	};
}();