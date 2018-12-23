Api = function(){
	return{
		checkAll:function(o){
			var checked = $(o).prop('checked');
			$(o).closest('#data-table').find('input[type=checkbox]').each(function(){
				$(this).prop('checked',checked);
			});
		},
		apipro:function(){
			var params = "[";
			var index = 0;
			$('tbody tr').each(function(){
				var checked = $(this).find('input[type=checkbox]').prop('checked');
				if(checked){
					if(index>0) params+=",";
					params += "{'name':'"+$(this).find('#name').text()+"','id':'"+$(this).find('input[type=checkbox]').attr('id')+"','comments':''}";
					index=index+1;
				}
			});
			params+="]";
			$.post("../../order/admin/commitApiProApply.do",{"params":params},function(data){
				if(Util.empty(data)) {
					if(confirm("申请提交成功，是否返回申请列表界面？")) window.location.href="../../admin/applylist.do";
				}else alert(data);
			});
		},
		audit:function(s,id){
			if(confirm("确定提交审核结果？")){
				$.post("../../api/admin/apiaudit.do",{"id":id,"status":s},function(data){
					if(Util.empty(data)) {
						if(confirm("审核结果提交成功，是否返回审核列表界面？")) window.location.href="../../admin/auditlist.do";
					}else alert(data);
				});
			}
		},
		valid:function(){
			var corporate =$('#corporate').val();
			var cardno = $('#cardno').val();
			var cardfirst = $('#cardfirst').val();
			var cardlast = $('#cardlast').val();
			var corporatephone = $('#corporatephone').val();
			var companyno = $('#companyno').val();
			var companyexpire = $('#companyexpire').val();
			var companyaddress = $('#companyaddress').val();
			var companycover = $('#companycover').val();
			var companylic = $('#companylic').val();
			if(Util.empty(corporate)) {
				alert("请填写公司法人信息！");
				return false;
			}else if(Util.empty(cardno)) {
				alert("请填写公司法人身份证号码！");
				return false;
			}else if(Util.empty(cardfirst)){
				alert("请上传公司法人身份证正面！");
				return false;
			}else if(Util.empty(cardlast)) {
				alert("请填写公司法人身份证反面！");
				return false;
			}else if(Util.empty(corporatephone)) {
				alert("请填写公司法人联系号码！");
				return false;
			}else if(Util.empty(companyno)) {
				alert("请填写公司注册号！");
				return false;
			}else if(Util.empty(companyexpire)){
				 alert("请填写公司有效期！");
				 return false;
			}else if(Util.empty(companyaddress)) {
				alert("请填写公司地址！");
				return false;
			}else if(Util.empty(companycover)) {
				alert("请填写公司经营范围！");
				return false;
			}else if(Util.empty(companylic)){
				alert("请上传公司营业执照！");
				return false;
			}else return true;
			
		}
	};
}();
Pro = function(){
	return {
		audit:function(s,id,o){
			var c=$(o).parent().parent().parent().find('#comments').val();
			$.post("../../order/admin/auditApiProApply.do",{"status":s,"id":id,"comments":c},function(data){
				if(Util.empty(data)) {
					$(o).parent().parent().parent().find('#status').text(s==1?"审核通过":"审核不通过");
				}
				else alert(data);
			});
		},
		commit:function(s,id){
			if(id==-1||id=='-1') alert("工单信息不完整！");
			else{
				$.post("../../order/admin/auditApiOrderApply.do",{"status":s,"id":id},function(data){
					if(Util.empty(data)) alert("工单审核提交成功");
					else alert(data);
				});
			}
		}
	};
}();
window.URL = window.URL || window.webkitURL;
var fileElem = document.getElementById("imgfile");
var fileList = document.getElementById("imgPreview");
//图片
function onchangeImage(obj,showid){
	var files = obj.files,
	img = new Image();
	if(window.URL){
	      $('#'+showid).attr('src',window.URL.createObjectURL(files[0]));
	}else if(window.FileReader){
		var reader = new FileReader();
		reader.readAsDataURL(files[0]);
		reader.onload = function(e){
			$('#'+showid).attr('src',this.result);
		}
	}else{
		//ie
		obj.select();
		obj.blur();
		var nfile = document.selection.createRange().text;
		$('#'+showid).attr('src',nfile);
	}
}