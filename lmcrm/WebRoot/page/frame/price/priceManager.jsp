<%@page import="com.yd.business.price.bean.PriceBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!doctype html>
<html lang="zh-CN">
  <head>
    <!-- 必须的 meta 标签 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.min.js" ></script>
 	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" ></script>
	<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <title>龙马水厂收费系统</title>
    <style type="text/css">
    .col-xs-8{padding: 1px;}
    .col-xs-4{padding: 1px;}
    .container{padding: 1px; margin-right: 1px; margin-left : 1px;max-width:1940px; }
    </style>
  </head>
 
<body> 

<div class="container">

	 <div class="form-group align-items-center">
	 
	 </div>
	 <div class="row form-group align-items-center">
	    <div class="col">
	      <select name="p_enabled" id="p_enabled" class="form-control">
			  <option value=""  selected>请选择启用状态</option>
			  <option value="<%=PriceBean.ENABLED_TRUE%>">启用</option>
			  <option value="<%=PriceBean.ENABLED_FALSE%>">禁用</option>
			</select>
	    </div>
	    <div class="col">
	    	<input type="text" class="form-control" id="p_name" name="p_name" placeholder="请输入价格名称">
	    </div>
	    <div class="col">

	      <select name="p_ladder" id="p_ladder" class="form-control">
			  <option value="" selected>请选择价格模式</option>
			  <option value="<%=PriceBean.LADDER_MONTH%>">月</option>
			  <option value="<%=PriceBean.LADDER_YEAR%>">年</option>
			</select>
	    </div>
 	 </div>
 	 
	 <div class="form-group align-items-center">
	 	<div class="row">
			 <div class="col-10">
			    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter" onclick="addPrice();" >新增价格</button>
		      	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModalCenter" onclick="updatePrice();">修改价格</button>
<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
 -->		    </div>
			 <div class="col-2">
		      	<button type="button" class="btn btn-primary" onclick="queryPriceData();">查询价格</button>
		    </div>
	    </div>
	    
	 </div>
</div>

<div class="container" style="overflow:scroll;" id="priceManagerDiv">
	<table class="table table-striped table-hover table-sm" style="min-width:1800px;">
  <thead>
    <tr>
      <th scope="col">#</th>
      <th scope="col">价格名称</th>
      <th scope="col">阶梯模式</th>
      <th scope="col">结算时间</th>
      <th scope="col">基本单价</th>
      <th scope="col">排污费</th>
      <th scope="col">其他费用</th>
      <th scope="col">合计价格</th>
      <th scope="col">保底金额</th>
      <th scope="col">保底量</th>
      <th scope="col">状态</th>
      <th scope="col">创建时间</th>
      <th scope="col">更新时间</th>
      <th scope="col">一阶吨限</th>
      <th scope="col">二阶单价</th>
      <th scope="col">二阶吨限</th>
      <th scope="col">二阶合计</th>
      <th scope="col">三阶单价</th>
      <th scope="col">三阶合计</th>
      <th scope="col">囤积量</th>
      <th scope="col">预存量</th>
      <th scope="col">报警量</th>
      <th scope="col">透支量</th>
    </tr>
  </thead>
  <tbody>
  
  <tr v-for="(price,index) in priceList" @click="getData(index)" :for="'radio'+index" >
      <th> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{price.p_id }}</th>
      <td>{{price.p_name}}</td>
      <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td>
      <td>{{price.p_ladder == 0 ? '每月'+price.p_settleday +'日' : price.p_settlemonth +'月'+ price.p_settleday +'日' }}</td>
      <td>{{price.p_base1   }}</td>
      <td>{{price.p_other1   }}</td>
      <td>{{price.p_other2   }}</td>
      <td>{{price.p_price1 }}</td>
      <td>{{price.p_lowprice }}</td>
      <td>{{price.p_lowamount }}</td>
      <td>{{getDescByBeanAttrValue("price","p_enabled",price.p_enabled) }}</td>
      <td>{{price.p_createdate }}</td>
      <td>{{price.p_updatedate }}</td>
      <td>{{price.p_ton1 }}</td>
      <td>{{price.p_base2 }}</td>
      <td>{{price.p_ton2 }}</td>
      <td>{{price.p_price2 }}</td>
      <td>{{price.p_base3 }}</td>
      <td>{{price.p_price3 }}</td>
      <td>{{price.p_limitamount }}</td>
      <td>{{price.p_storedamount }}</td>
      <td>{{price.p_weakprompt }}</td>
      <td>{{price.p_overflat }}</td>
  </tr>
  
    <tr data-toggle="collapse" href="#collapseExample" aria-expanded="true" aria-controls="collapseExample">
      <th scope="row">1</th>
      <td>Mark</td>
      <td>Otto</td>
      <td>@mdo</td>
    </tr>
  </tbody>

</table>
  <div class="collapse" id="collapseExample">
	  <div class="card card-body">
	    Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt sapiente ea proident.
	  </div>
	</div>
</div>



<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered  modal-lg" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalCenterTitle">新增/修改价格</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <div class="container-fluid">
		    <div class="row">
		      <div class="col-md-2 align-self-center">价格名称</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_name" class="form-control" placeholder="请输入价格名称">
				</div>
		      
		      <div class="col-md-2  align-self-center">基本单价</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_base1" class="form-control" placeholder="单位：元">
				</div>
		      
		    </div>
		    <div class="row">
		      <div class="col-md-2 align-self-center">阶梯模式</div>
		      <div class="col-md-4">
		      		      <select name="update_p_ladder" class="form-control">
							  <option value="<%=PriceBean.LADDER_MONTH%>">月</option>
							  <option value="<%=PriceBean.LADDER_YEAR%>">年</option>
							</select>
		      	</div>
		      <div class="col-md-2  align-self-center">结算时间</div>
		      <div class="col-md-4">
		      		 <div class="row">
		      		 	<div class="col-md-6">
		      		      <select name="update_p_settlemonth" class="form-control">
							  <option value="1">1月</option>
							  <option value="2">2月</option>
							  <option value="3">3月</option>
							  <option value="4">4月</option>
							  <option value="5">5月</option>
							  <option value="6">6月</option>
							  <option value="7">7月</option>
							  <option value="8">8月</option>
							  <option value="9">9月</option>
							  <option value="10">10月</option>
							  <option value="11">11月</option>
							  <option value="12">12月</option>
							</select>
							</div>
			      		 	<div class="col-md-6">
			      		      <select name="update_p_settleday" class="form-control">
								  <option   v-for=" count in 31" value="{{count+1}}">{{ count+1 }}日</option>
								</select>
							</div>
						</div>
				</div>
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">排污费</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_other1" class="form-control" placeholder="单位：元">
				</div>
		      
		      <div class="col-md-2  align-self-center">其他费用</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_other2" class="form-control" placeholder="单位：元">
				</div>
		      
		    </div>
		    
		    <div class="row">
		       <div class="col-md-2 align-self-center">保底金额</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_lowprice" class="form-control" placeholder="单位：元">
				</div>
		      
		      <div class="col-md-2  align-self-center">保底量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_lowamount" class="form-control" placeholder="单位：吨">
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">状态</div>
		      <div class="col-md-4 ml-auto">
					 <select name="update_p_enabled" class="form-control">
					  <option  value="<%=PriceBean.ENABLED_TRUE %>">启用</option>
					  <option  value="<%=PriceBean.ENABLED_FALSE %>">禁用</option>
					</select>
				</div>
		      
		      <div class="col-md-2  align-self-center">一阶吨限</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_ton1" class="form-control" placeholder="单位：吨">
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">二阶单价</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_base2" class="form-control" placeholder="单位：元">

				</div>
		      
		      <div class="col-md-2  align-self-center">二阶吨限</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_ton2" class="form-control" placeholder="单位：吨">
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">三阶单价</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_base3" class="form-control" placeholder="单位：元">

				</div>
		      
		      <div class="col-md-2  align-self-center">屯积量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_limitamount" class="form-control" placeholder="单位：吨" value="9999">
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">预存量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_storedamount" class="form-control" placeholder="单位：元">

				</div>
		      
		      <div class="col-md-2  align-self-center">报警量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_weakprompt" class="form-control" placeholder="单位：吨">
				</div>
		      
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">透支量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_overflat" class="form-control" placeholder="单位：元">

				</div>
		      
		      <div class="col-md-2  align-self-center">ID标识</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_id" class="form-control" disabled="disabled" placeholder="无需填写">
 				</div>
		    </div>
		</div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关 闭</button>
        <button type="button" class="btn btn-primary" onclick="addOrUpdatePrice()">保 存</button>
      </div>
    </div>
  </div>
</div>
</form>
</body>

<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>
<script src="page/frame/price/js/priceManager.js" type="text/javascript"></script>

<script type="text/javascript">


</script>
</html>