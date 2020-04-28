L = function(){
	return {
		login:function(){
			var username = $('#username').val();
			var password = $('#password').val();
			var isAdmin = $('#isadmin').prop('checked');
			var type = isAdmin?"2":"1";
			$.post("../../wechat/login.do",{"username":username,"password":password,"type":type},function(data){
				if(data.indexOf('.do')>=0) window.location.href=data;
				else alert(data);
			});
		}
	};
}();