$(".delivery").hide();
function changeMaterial(option) {
	materialType = option.value;
	$(".delivery").hide();
	if (materialType == 1) {
		// 查询微信文本
		
		$.post("other/cinfigcrux/ajax/queryConfigCrux.do", {
					type:"wechat_notify_text"
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
							
							content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
							'<input type="radio" style="height: 100%" name="material_id"  value="'+ material.id +'" ' +
							'id="radio'+ material.id +'" title="'+ material.key +'"  onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'">';
							content += material.code +'<br>'+ material.value +'</td></tr>';
					}
					list.append(content);
					listRefresh();
				});
		
	} else if (materialType == 2) {
		// 查询短信文本
		
		$.post("sms/ajax/querySMSCustomer.do", {
					
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
							
							content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
							'<input type="radio" style="height: 100%" name="material_id" value="'+ material.id +'" ' +
							'id="radio'+ material.id +'" title="'+ material.smsTemplateCode +'"  onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'">';
							content += material.customername +'</td></tr>';
					}
					list.append(content);
					listRefresh();
				});
		
	} else if (materialType == 3) {
		// 查询微信素材

		$.post("wechat/ajax/queryWechatMaterialInfo.do", {
					"sucai_type" : "news"
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
						if(material.sucai_title == 'undefined' || material.sucai_title == null || material.sucai_title == ''){
							
							if(content.length >= 1){
								content +='</td></td>'; 
							}
							content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
							'<input type="radio" style="height: 100%" name="material_id" value="'+ material.id +'" ' +
							'id="radio'+ material.id +'" title="'+ material.sucai_media_id +'"  onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'" >';
						}else{
							content += material.sucai_title +'<br>';
							
						}
					}
					list.append(content);
					listRefresh();
				});
	} else if (materialType == 4) {
		// 查询系统文章素材

		$.post("supplierEvent/ajax/querySupplierEventByAjax.do", {
					
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
							
							content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
							'<input type="radio" style="height: 100%" name="material_id" value="'+ material.id +'" ' +
							'id="radio'+ material.id +'" title="'+ material.id +'"   onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'">';
							content += material.title +'</td></tr>';
					}
					list.append(content);
					listRefresh();
				});
	}else if (materialType == 5) {
		// 查询微信图文素材【批量】
		$(".delivery").show();
		$.post("wechat/ajax/queryWechatMaterialInfo.do", {
			"sucai_type" : "newsall"
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
						var materialList = material.materialList;
						content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
						'<input type="radio" style="height: 100%" name="material_id" value="'+ material.id +'" ' +
						'id="radio'+ material.id +'" title="'+ material.sucai_media_id +'"   onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'">';
						var title = "";
						if(materialList.length > 0){
							for(var n=0; n<materialList.length;n++ ){
								title += materialList[n].sucai_title+"<br>";
							}
						}
						content += title +'</td><td>['+material.deliveryOriginal+']</td></tr>';
					}
					list.append(content);
					listRefresh();
				});
	} else if (materialType == 6) {
		// 查询系统文章素材

		$.post("supplierTopic/ajax/querySupplierTopicByAjax.do", {
					
				}, function(data) {
					var materials = eval('('+data+')');
					var list = $("#material_list");
					list.empty();
					var content = "";
					for(var i = 0; i < materials.length;i++){
						var material = materials[i];
							
							content +='<tr class="gradeA " onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"><td  width="10%" style="margin: 0 auto;text-align: center;" > ' +
							'<input type="radio" style="height: 100%" name="material_id" value="'+ material.id +'" ' +
							'id="radio'+ material.id +'" title="'+ material.id +'"   onclick="choseInput(\'td'+material.id+'\',\'radio'+material.id+'\')"> </td><td id="td'+material.id+'">';
							content += material.title +'</td></tr>';
					}
					list.append(content);
					listRefresh();
				});
	}

}


function listRefresh(){
	$('#data-table').dataTable({
        "iDisplayLength":10,
        "bFilter":true,
        "bRetrieve":true,
        "bPaginate":false,
        "sPaginationType": "full_numbers",
        "bInfo": false,
        "bSort":false
        });
}

function choseInput(tdid,value){
	var radio = document.getElementById(value);
	var td = document.getElementById(tdid);
	radio.checked = true;
	$("#material_code").val(radio.title);
	$("#material_name").val(td.innerHTML);
	
}



