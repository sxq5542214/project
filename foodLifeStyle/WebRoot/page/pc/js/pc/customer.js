Cust = function(){
	return {
		update:function(id){
			var username = $('#username').val();
			var password = $('#password').val();
			var password_reset = $('#password_reset').val();
			var name = $('#name').val();
			var contactsname = $('#contactsname').val();
			var contactsphone = $('#contactsphone').val();
			var address = $('#address').val();
			var remark = $('#remark').val();
			if(password==password_reset){
				$.post("../../customer/admin/update.do",{"id":id,"username":username,"password":password,"name":name,"contacts_name":contactsname,"contacts_phone":contactsphone,"address":address,"remark":remark},function(data){
					alert(data);
				});
			}else if(Util.validPhone(contactsphone)) alert("请输入正确的手机号码");
			else alert("两次密码输入不同，请重新输入！");
		}
	};
}();