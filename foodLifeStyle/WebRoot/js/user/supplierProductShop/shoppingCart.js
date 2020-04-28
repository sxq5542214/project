var shopCartTotalNum = 0;
var shopCartTotalPrice = 0.0;

function addProductToCart(spid, num) {

	var productInfo = getCookie("productInfo");

	if (productInfo != null && productInfo != '') {
		var productJson = eval('(' + productInfo + ')');

		var productInfos = productJson.productInfos;
		var notFind = true;
		var len = productInfos.length;
		
		// 循环，找到相同ID的，然后设置数量
		for (var i = 0; i < len; i++) {
			var spInfo = productInfos[i];
			if (spInfo.spid == spid) {
				notFind = false;
				if(productInfos[i].num == 0 && num <0 ){
					productInfos[i].spid = -1;
					productInfos.splice(i,1); //如果已经没有了，则删除
				}else{
					productInfos[i].num =  productInfos[i].num + num;
					
					if(productInfos[i].num == 0){
						productInfos[i].spid = -1;
						productInfos.splice(i,1);  //如果已经没有了，则删除
					}
					
				}
				break;
			}
		}
		if (notFind) {
			productInfos[len] = new Object();
			productInfos[len].spid = spid;
			productInfos[len].num = num;
		}
		setCookie("productInfo", JSON.stringify(productJson));
		shopCartTotalNum = shopCartTotalNum + num;
	} else {
		setCookie("productInfo", "{productInfos:[]}");
		addProductToCart(spid, num);
	}
	
}

function deleteProductToCart(spid) {

	var productInfo = getCookie("productInfo");
	if (productInfo != null) {
		var productJson = eval('(' + productInfo + ')');

		var productInfos = productJson.productInfos;
		var len = productInfos.length;

		// 循环，找到相同ID的，然后设置数量
		for (var i = 0; i < len; i++) {
			
			var spInfo = productInfos[i];
			if (typeof(spInfo) != "undefined" &&  spInfo.spid == spid) {
				shopCartTotalNum = shopCartTotalNum - productInfos[i].num;
				productInfos[i].spid = -1;
				productInfos[i].num = 0;
				productInfos.splice(i,1);
			}
		}
		
		setCookie("productInfo", JSON.stringify(productJson));
	}
	
}

function calcShopCartInfo(){
	var productInfo = getCookie("productInfo");
	var totalNum = 0;
	var totalPrice =0.0;
	if (productInfo != null) {
		var productJson = eval('(' + productInfo + ')');

		var productInfos = productJson.productInfos;
		var len = productInfos.length;

		// 循环，找到相同ID的，然后设置数量
		for (var i = 0; i < len; i++) {
			var spInfo = productInfos[i];
			totalNum = totalNum + productInfos[i].num;
//			totalPrice = productInfos[i].num * productInfos[i].
		}
	}
	shopCartTotalNum = totalNum;
}
