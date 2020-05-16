<%@page import="com.yd.business.supplier.bean.SupplierBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	SupplierBean supplier = (SupplierBean)request.getAttribute("supplier");		
	
%>			
<!DOCTYPE html>
<html lang="en">

<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="css/supplier/shop/payPrice.css">
<script src="https://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.12.4.min.js" type="text/javascript" ></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<title>会员支付</title>

</head>
<body>

<div class="container">
<!--     <div class="row">
      <div class="container_logo">
        <div class="play col-xs-10 col-sm-10 col-md-10 col-lg-10">
          <img src="./images/logo.png" />
        </div>
      </div>
    </div> -->
  
      <div class="row">
        <div class="play col-xs-10 col-sm-10 col-md-10 col-lg-10">
          <div class="form-group">
            <input type="hidden" class="getId" name="id">
            <h1>支付金额</h1>
            <div class="number_amount">
              <label>￥</label>
              <input type="number" name="amount" id="price"  min="0.0">
            </div>
          </div>
        </div>
      </div>
      <div class="row">
        <div class="quick_amount col-xs-10 col-sm-10 col-md-10 col-lg-10">
<!--         <p class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='5000'>50</p> 
        <p class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='5000'>100</p> 
        <p class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='5000'>200</p>  
        <div style="width: 100%;height: 1px;border: 1px;background:black; border: 1px solid #eee"> &nbsp;</div>
  -->         <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>7</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>8</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>9</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>4</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>5</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>6</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>1</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>2</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>3</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>.</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>0</button>
          <button class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='100'>删</button>
<!--           <p class="col-xs-3 col-sm-3 col-md-3 col-lg-3" data-item='5000'>.</p> -->
        </div>
      </div>
      <div class="row">
        <div class="_submit col-xs-10 col-sm-10 col-md-10 col-lg-10">
          <input type="submit" value="支付" class="btn btn-primary  submit-amount btn-lg">
        </div>
      </div>
  
  </div>
  </div>
  <div class="modal fade" tabindex="-1" role="dialog" id='exampleModal'>
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
          <h4 class="modal-title">提示</h4>
        </div>
        <div class="modal-body">
          <p>输入金额不能超出5000元</p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-primary" data-dismiss="modal" aria-label="Close">确定</button>
        </div>
      </div>
    </div>
</div>
<div class="mask"></div>
<script>
 var $amountInput = $('[type="number"]');
var amount = '';
var lastChar ='';
$(".quick_amount button").off("click").on("click", function () {
  var price = $("#price");
  amount = $(this).text();
  var value = $amountInput.val();
  if(amount == '删'){
  	$amountInput.val(value.substr(0, value.length -1));
  	return false;
  }if(amount == '.'){
  	if(value.indexOf('.')>0){
  		return false;
  	}
  	value = value + '.0' ;
  }else{
  	
  	if(lastChar == '.'){
  		value = value.substr(0, value.length - 1);
  	}
  	value = value + '' + amount;
  	price.val(value);
  }
 document.getElementById("price").value = value;
	lastChar = amount;
})

// $('#exampleModal').modal('show');
</script>
<%-- <div style="text-align:center;">
<p>本次付款至商户【<%=supplier.getn %>】</p>
</div> --%>
</body>
</html>