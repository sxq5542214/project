Login = function(){
	return {
		login:function(){
			var username = $('#username').val();
			var password = $('#userpass').val();
			var checkCode = $('#checkCode').val();
			if(checkCode == null || checkCode == ""){
				alert("验证码为空,请重新输入");
				myReload();
				return;
			}
			$.post("customer/login.do",{"username":username,"password":password,"checkCode":checkCode},function(data){
				if(data.indexOf('.do')>=0) window.location.href=data;
				else alert(data);
				myReload();
			});
		}
	};
}();



//更换验证码
function myReload(){
 	//document.createCheckCode.src="PictureCheckCode";
	document.getElementById("createCheckCode").src =   "login/LoginController/checkCode.do";
	document.getElementById("createCheckCode").src=document.getElementById("createCheckCode").src+"?nocache="+new Date().getTime();
 }