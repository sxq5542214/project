Opt=function(){
	return {
		insert:function(){
			var name = $('#name').val();
			var loginname = $('#loginname').val();
			var status = $('#status').val();
			var password = $('#password').val();
			var passwordrest = $('#passwordrest').val();
			if(Util.empty(name)) alert('请输入操作员名称');
			else if(Util.empty(loginname)) alert('请输入登陆账号');
			else if(Util.empty(password)) alert('请输入密码');
			else if(Util.empty(passwordrest)) alert('请再次输入密码');
			else if(password!=passwordrest) alert('两次密码输入不同，请确认');
			else{
				$.post('../../app/operat/add.do',{"nickname":name,"username":loginname,"password":password,"status":status},function(data){
					if(Util.empty(data)){
						if(!confirm("是否继续添加？")) window.location.href='../../app/operat/list.do';
					}else alert(data);
				});
			}
		}
	};
}();