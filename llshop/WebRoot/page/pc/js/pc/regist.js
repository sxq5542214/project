Reg = function(){
	return {
		regist:function(){
			var params = {};
			var name = $('#name').val();
			var address = $('#address').val();
			var contacts_name = $('#contacts_name').val();
			var contacts_phone = $('#contacts_phone').val();
			var remark = $('#remark').val();
			var password = $('#password').val();
			var passwordreset = $('#passwordreset').val();
			
			if(Util.empty(name)) alert(' 请输入商户名称');
			else if(Util.empty(address)) alert(' 请输入商户地址');
			else if(Util.empty(contacts_name)) alert('请输入联系人');
			else if(Util.empty(contacts_phone)) alert('请输入联系号码');
			else if(password!=passwordreset) alert('两次密码输入不通，请重新输入！');
			else{
				params.name = name;
				params.password = password;
				params.address = address;
				params.passwordreset = passwordreset;
				params.concats_name = contacts_name;
				params.concats_phone = contacts_phone;
				params.remark = remark;
				var postUrl = "../../customer/insert.do";
				$.post(postUrl,params,function(data){
					if(Util.empty(data)) if(confirm("注册成功，返回登录页？")) window.history.go(-1);
					else alert(data);
				});
			}
		}
	};
}();