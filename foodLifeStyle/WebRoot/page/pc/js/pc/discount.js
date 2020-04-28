var ids = '';
var updateParams = '';
Dis = function(){
	return {
		listRefresh:function(){
			$('#data-table').dataTable({
		          "sPaginationType": "full_numbers",
		          "iDisplayLength":10
		    });
		},
		listProType:function(){
			var tab1 = $('#dtab1');
			tab1.empty();
			$.post("../../discount/admin/listProductTypeBrand.do",{},function(data){
				var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table">';
				content += '<thead><tr><th style="width: 5%"><input type="checkbox" class="no-margin" onclick="Dis.checkAll(this,\'pro_list\');" /></th>';
				content += '<th style="width: 45%">品牌名称</th><th style="width: 50%">类型</th></tr></thead><tbody id="pro_list">';
				var dataList = eval('('+data+')');
				for(var i=0;i<dataList.length;i++){
					var obj = dataList[i];
					var style = i%2==0?'warn':'success';
					content += '<tr class="gradeA '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'"/></td><td><label id="proname">'+obj.name+'</label></td><td><label id="protype">'+(obj.type==1?'品牌':'分类')+'</label></td></tr>';
				}
				content += '</tbody></table>';
				tab1.append(content);
			});
		},
		listDisGroup:function(){
			var list = $('#dt_example');
			list.empty();
            $.post("../../discount/admin/listDisGroup.do",{},function(data){
            	var content = '<table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table"><thead><tr><th style="width:5%">';
            	content += '<input type="checkbox" class="no-margin" onclick="Dis.checkAll(this)" /></th><th style="width:20%">规则名称</th><th style="width:40%">规则详情</th><th style="width:20%">备注</th><th style="width:15%" class="hidden-phone">操作</th></tr></thead><tbody id="dis_list">';
            	var dataList = eval('('+data+')');
            	for ( var i = 0; i < dataList.length; i++) {
					var obj = dataList[i];
					var style = i%2==0?'warning':'success';
					content += '<tr class="gradeX '+style+'"><td><input type="checkbox" class="no-margin" id="'+obj.id+'"/></td><td>'+Util.toStr(obj.name);
                    content += '</td><td>'+Util.toStr(obj.disinfo)+'</td><td>'+Util.toStr(obj.remark)+'</td><td><div class="btn-group">';
                    content += '<a class="btn btn-default" href="javascript:window.location.href=\'../../admin/discountEdt.do?id='+obj.id+'\'">编辑</a>';
                    content += '</div></td></tr>';
				}
            	content += '</tbody></table><div class="clearfix"></div>';
            	list.append(content);
            	Dis.listRefresh();
            });
		},
		deleteDisGroup:function(id){
			$.post("../../discount/admin/delete.do",{"id":id},function(data){
				if(Util.empty(data)) {
					alert("删除成功！");
					Dis.listDisGroup();
				}else alert(data);
			});
		},
		insertDisGroup:function(){
			var params = "{'name':";
			var name = $('#name').val();
			var remark = $('#remark').val();
			params+=name+",'remark':'"+remark+"','data':[";
			var index = 0;
			$('#pro_list_set input[type=checkbox]').each(function(){
            	var checkbox = $(this);
            	var proListDis = checkbox.parent().parent().find('#pro_list_dis');
            	if(checkbox.prop('checked')){
            		isChecked = true;
            		var proid = checkbox.prop('id');
            		proListDis.find('tr').each(function(){//获取品牌规则信息
            			var obj = $(this);
            			var minprice = obj.find('#minprice').val();
            			var maxprice = obj.find('#maxprice').val();
            			var discount = obj.find('#discount').val();
            			if(index>0)params+=",";
                		params+="{'brand':"+proid+",'name':"+name+",'minprice':"+minprice+",'maxprice':"+maxprice+",'discount':"+discount+"}";
                		index++;
            		});
            	}
            });
			params+="]}";
			if(Util.empty(name)) alert("请输入折扣规则名称");
			else{
				$.post("../../discount/admin/insert.do",{"params":params},function(data){
					if(Util.empty(data)){
						alert("折扣规则新增成功");
						window.location.href="../../admin/discount.do";
					}else alert(data);
				});
			}
		},
		update:function(){
			var name = $('#name').val();
			var remark = $('#remark').val();
			var id = Util.getUrlParam("id");
			var updateParams = '{"name":'+name+',"remark":'+remark+',"id":'+id+',list:[';
			var error = '';
			$('#data-table tbody tr').each(function(){
				var checked = $(this).find('input[type=checkbox]').prop('checked');
				if(checked){
					var index = 0;
					$(this).find('#pro_list_dis tr').each(function(){
						var disid = $(this).prop('id');
						var brand = $(this).attr('brand');
						var type = 1;
						var discount = parseInt($(this).find('#discount').val());
						var olddiscount = parseInt($(this).find('#discount').attr('old'));
						var min = $(this).find('#minprice').val();
						var max = $(this).find('#maxprice').val();
						if(Util.empty(disid)) type = 0;
						if(index>0) updateParams += ',';
						updateParams += '{"id":'+disid+',"productbrand":'+brand+',"minprice":'+min+',"maxprice":'+max+',"discount":'+discount+',"type":'+type+'}';
						index ++ ;
						
						if(discount<olddiscount) error+='['+min+'-'+max+']折扣额度不可小于原始额度，请修改';
					});
				}
			});
			updateParams+=']}';
			if(Util.empty(error)){
				$.post("../../discount/admin/update.do",{"params":updateParams},function(data){
					if(Util.empty(data)) alert("修改成功");
					else alert(data);
				});
			}else alert(error);
		},
		batchDeleteDis:function(){
			Dis.ids();
			if(ids.length>0){
				$.post("../../discount/admin/batchDelete.do",{"ids":ids},function(data){
					if(Util.empty(data)){
						alert("删除成功");
						Dis.listDisGroup();
					}else alert(data);
				});
			}else alert("请至少选择一个需要删除的记录！");
		},
		checkAll:function(obj,id){
			var checked = $(obj).prop('checked');
			if(Util.empty(id))$('input[type=checkbox]').prop('checked',checked);
			else $('#'+id+' input[type=checkbox]').prop('checked',checked);
		},
		ids:function(){
			ids = '';
			$('#dis_list').find('input[type=checkbox]').each(function(){
				var obj = $(this);
				if(obj.prop('checked')){
					if(ids.length>0) ids+=',';
					ids += obj.attr('id');
				}
			});
		}
	};
}();