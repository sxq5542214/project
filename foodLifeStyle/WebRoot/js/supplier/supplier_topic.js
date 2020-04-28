var ids = '';
var isSuccess = false;
var upload_success = false;
Art = function(){
	return {
		preview:function(){
			var id = getUrlParam("id");
			$.post("supplierTopic/find.do",{"id":id},function(data){
				var article = eval('('+data+')');
				$('#title').append(article.title);
				$('#content').append(article.content);
			});
		},
		init:function(page){
			Art.initList();
			$.post('supplierTopic/list.do',{"nowpage":page,"title":$('#title').val(),"status":$('#status').val()},function(data){
				var page = eval('('+data+')');
				var list = page.dataList;
				Art.fenye(page.totalcount,page.nowpage,page.totalpage);
				var content = '';
				for(var i=0;i<list.length;i++){
					var obj = list[i];
					var status = obj.status;
					var title = obj.title!=null&&obj.title.length>15?obj.title.substr(0,15)+"...":obj.title;
					title = title==null?'':title;
					//var ct = obj.title.length>15?obj.title.substr(0,15)+"...":obj.title;
					var url = obj.url!=null?obj.url.length>20?obj.url.substr(0,20)+"...":obj.url:'';
					var img_url = obj.img_url.length>20?obj.img_url.substr(0,20)+"...":obj.img_url;
					var read_num = obj.read_num!=null?obj.read_num:'0';
					if(status==0) status = '不可用';
					else if(status==1) status = '可用';
					else status = '';
					content += '<tbody id="indefin-tbody'+obj.id+'"><tr class="table-title">';
					content += '<td><input type="checkbox" name="personitem" class="opacity50" onchange="Art.checked(this,'+obj.id+');" id="'+obj.id+'"></td>';
                    content += '<td style="text-align: center;">'+obj.id+'</td><td style="text-align: center;">'+title+'</td>';
                    content += '<td style="text-align: center;"><a href="'+obj.url+'">'+url+'</a></td><td style="text-align: center;"><img style="width:50px; height:50px;" src="'+obj.img_url+'"></td>';
                    content += '<td style="text-align: center;">'+status+'</td><td style="text-align: center;">'+obj.create_time+'</td>';
                    content += '<td style="text-align: center;">'+obj.modify_time+'</td><td style="text-align: center;">'+read_num+'</td>';
                    content += '<td style="text-align: center;"><span class="del-tr" onClick="Art.openNewWin(\'userRead/readEvent.do?eventId='+obj.id+'\');">预览</span><span class="del-tr" onClick="Art.updateWin(\''+obj.id+'\');">编辑</span><span class="del-tr" onClick="Art.del('+obj.id+')">删除</span></td></tr></tbody>';
				}
				$('#indefine-tableId').append(content);
			});
		},
		del:function(id){
			
			var flag = confirm('确定删除此条记录？');
			if(flag){
				$.post('supplierTopic/delete.do',{"id":id},function(data){
					if(data=='0'){
						openMsg('提示信息','删除失败');
					}else{
	//					Art.init(1);
						document.getElementById('tr'+id).style.display = 'none';
						openMsg('提示信息','删除成功');
					}
				});
			};
		},
		batchDel:function(){
			$('#indefine-tableId').find('input[type=checkbox]').each(function(){
				var obj = $(this);
				if(obj.prop('checked')){
					if(ids.length>0) ids+=',';
					ids += obj.attr('id');
				}
			});
			$.post('supplierArticle/batDel.do',{"ids":ids},function(data){
				if(data=="1") {
					$('#check-all').attr('value','全选');
					$('#delet-checked').attr('disabled','disabled');
					$('input[type=checkbox]').prop('checked',false);
					Art.init(1);
					openMsg('提示信息','删除成功');
				}else{
					openMsg('提示信息','删除失败');
				}
			});
		},
		initList:function(){
			$('#indefine-tableId').empty();
			$('#indefine-tableId').append('<colgroup><col width="3%"><col width="3%"><col width="18%"><col width="15%"><col width="15%"><col width="5%"><col width="10%"><col width="10%"><col width="5%"><col width="auto"></colgroup>');
		},
		fenye:function(total,currentPage,totalPage){
			$('#total-dish-number').html(total);
			$('#now-page').html(currentPage);
			$('#total-page').html(totalPage);
			$('#prePageNum').attr("onclick","Art.prePage("+currentPage+");");
			$('#nextPageNum').attr('onclick',"Art.nextPage("+currentPage+","+totalPage+");");
		},
		prePage:function(page){
			if(page!=1)Art.init(parseInt(page)-1);
		},
		nextPage:function(nowPage,totalPage){
			if(nowPage<totalPage)Art.init(parseInt(nowPage)+1);
		},
		checkAll:function(obj){
			var val = $(obj).val();
			if(val == '全选') {
				$(obj).attr('value','取消全选');
				$("#delet-checked").removeAttr("disabled","disabled");
				$('input[type=checkbox]').prop('checked',true);
			}else{
				$(obj).attr('value','全选');
				$('#delet-checked').attr('disabled','disabled');
				$('input[type=checkbox]').prop('checked',false);
			}
		},
		checked:function(obj,id){
			var ischecked = false;
			$('input[type=checkbox]').each(function(){
				if($(this).prop('checked')) {
					ischecked = true;
					$("#delet-checked").removeAttr("disabled","disabled");
				}else{
					if(!ischecked)$('#delet-checked').attr('disabled','disabled');
				}
			});
		},
		openInsert:function(){
			$('#save-infor-btn').attr('onclick','Art.fileUpload();');
			$('#name').val('');
			$('#url').val('');
			$('#img_url').val('');
			$('#content').val('');
			shadowFocusQuery("msgWin");
		},
		initUpdate:function(){
//			var id = getUrlParam("id");
//			if(id&&id!='undefined'){
//				$('#save-infor-btn').attr('onclick','Art.validate(\'supplierArticle/update.do?id='+id+'\');');
//				$.post('supplierArticle/find.do',{"id":id},function(data){
//					var obj = eval('('+data+')');
//					$('#title').val(obj.title);
//					$('#url').val(obj.url);
//					$('#img_url').val(obj.img_url);
//					$('#imageinfo').attr("src",obj.img_url);
//				    //UE.getEditor('content').setContent(obj.content);
//				    editor.insertHtml(obj.content);
//				});
//			}else{
				//$('#save-infor-btn').attr('onclick','Art.validate(\'supplierArticle/insert.do\');');
//				$('#articleForm').attr('action','supplierArticle/insert.do');
//			}
		},
		updateWin:function(id){
			parent.document.getElementById("page-frame").src="supplierTopic/toUpdatePage.do?id="+id;
		},
		openWin:function(url){
			parent.document.getElementById("page-frame").src=url;
		},
		openNewWin:function(url){
			window.open(url);
		},
		validate:function(act){
			var img_url = $('#imgfile').val();
			var joinCount = $('#joinCount').val();
			if(joinCount!=null&&joinCount!=''){
				var result = false;
				if(img_url!=null&&img_url!=''){
					$('#articleForm').attr('action',act);
					result = true;//Art.fileUpload(act);
				}else {
					$('#articleForm').attr('action',act);
					result = true;
				}
				if(!result){
					openMsg("提示信息","文件上传失败，请确认网络是否正常！");
				}
				return result;
			}else {
				openMsg("提示信息","请输入活动参与人数");
				return false;
			}
		},
		fileUpload:function(act){
			var id = getUrlParam("id");
			var img_url = $('#imgfile').val();
			var params = '?id='+id+'&img_url='+img_url;
			$.ajaxFileUpload({
				url : 'supplierTopic/fileUpload.do'+params,
				secureuri : false,
				fileElementId : 'imgfile',
				dataType : 'JSON',
				success : function(data, status) {
					var msg = data.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','');
					msg = msg.replace('</pre>','');
					if(msg=="error") {
						openMsg("提示信息","文件上传失败");
						upload_success = false;
					}else {
						alert('1');
						$('#img_url').val(msg);
						upload_success = true;
					}
				},
				error : function(data, status, e) {
					upload_success = false;
				}
			});
			alert(upload_success);
			return upload_success;
		},
		insert:function(){/*
			var name = $('#name').val();
			var url = $('#url').val();
			var img_url = $('#img_url').val();
			var content = UE.getEditor('content').getContent();
			var status = $('#editstatus').val();
			if(!name) openMsg('提示信息','请输入文章标题');
			else{
				var params = '?title='+name+'&content='+content+'&url='+url+'&img_url='+img_url+'&status='+status;
				$.ajaxFileUpload({
					url : encodeURI('supplierArticle/insert.do'+params),
					secureuri : false,
					fileElementId : 'imgfile',
					dataType : 'JSON',
					success : function(data, status) {
						var msg = data.replace('<pre style="word-wrap: break-word; white-space: pre-wrap;">','');
						msg = msg.replace('</pre>','');
						if(status!="success") {
							openMsg("提示信息",data);
						}else if(msg.length>0){
							openMsg("提示信息",msg);
						}else{
							$('#imageinfo').attr("src","");
							$('#imgfile').attr('value','');
							Art.init(1);
							concellquery();
						}
					},
					error : function(data, status, e) {
						openMsg("提示信息","上传文件出错");
					}
				});
				
				$.post('supplierArticle/insert.do',{"title":name,"content":content,"url":url,"img_url":img_url,"status":status},function(data){
					Art.init(1);
					concellquery();
					openMsg('提示信息',data);
				});
			}
		*/}
	};
}();

/////

/////

window.URL = window.URL || window.webkitURL;
var fileElem = document.getElementById("imgfile");
var fileList = document.getElementById("imgPreview");
//图片
function onchangeImage(obj){
	var files = obj.files,
	img = new Image();
	//$('#imgPreview').empty();
	if(window.URL){
//	      img.src = window.URL.createObjectURL(files[0]); //创建一个object URL，并不是你的本地路径
//	      img.width = 200;
//	      img.id = "upimageinfo";
//	      img.onload = function(e) {
//	         window.URL.revokeObjectURL(this.src); //图片加载后，释放object URL
//	      }
	      $('#imageinfo').attr('src',window.URL.createObjectURL(files[0]));
	      //fileList.appendChild(img);
	}else if(window.FileReader){
		var reader = new FileReader();
		reader.readAsDataURL(files[0]);
		reader.onload = function(e){
			$('#imageinfo').attr('src',this.result);
//			img.src = this.result;
//			img.width = 200;
//			fileList.appendChild(img);
		}
	}else{
		//ie
		obj.select();
		obj.blur();
		var nfile = document.selection.createRange().text;
		$('#imageinfo').attr('src',nfile);
//		document.selection.empty();
//		img.src = nfile;
//		img.width = 200;
//		fileList.appendChild(img);
	}
}