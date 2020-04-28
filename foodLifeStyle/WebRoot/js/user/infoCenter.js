
function getBalanceCash(){
	var flag = false;
	var balance = document.getElementById('balance').innerHTML;
	var openid =  document.getElementById('openid').value;
	var appendStr = '';
	
	balance = (balance * 100).toFixed(0);
	var getCash = balance;
	if(balance <= 0 || balance < 100){
		alert('余额低于1元，无法提现！');
		return flag;
	}
	
	//以分为单位的
	if(balance >= 20000){
		appendStr = '余额大于200元，本次提现仅能提200元，由于微信限制，请一分钟后再提现！';
		getCash = 20000;
	}
	
	$.ajax({
			url : 'user/getBalanceCash.do',
			async : false,
			data : {
				openid : openid,
				getCash : getCash
			},
			type : 'POST',
			success : function(data) {
				 if(data == 'waitMinute'){
				 	alert('很抱歉，由于微信限制，请一分钟后再提现！');
				 	
				 }else if(data == 'success'){
				 	alert('提现成功，请查看微信红包！' + appendStr);
				 	
				 	balance = balance - getCash;
				 	document.getElementById('balance').innerHTML = (balance/100).toFixed(2);
				 }else{
				 	alert('提现失败！' );
				 }
			}
		});
	
	
	
	return flag;
}