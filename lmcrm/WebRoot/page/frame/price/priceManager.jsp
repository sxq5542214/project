<%@page import="com.yd.business.price.bean.PriceBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh-CN">
    <head>
    	<base href="<%=basePath%>">
        <meta charset="utf-8" />
        <title>价格管理</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
        <meta content="Coderthemes" name="author" />
        <!-- App favicon -->
        <link rel="shortcut icon" href="/staticFiles/bootstrap4/hyper/assets/images/favicon.ico">

        <!-- App css -->
        <link href="/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
        <link href="/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
		<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
	 	<script src="js/common/dictionaryData.js" type="text/javascript"></script>
    </head>

    <body>

        <!-- Begin page -->
        <div class="wrapper" id="priceManagerDiv">

            <!-- ============================================================== -->
            <!-- Start Page Content here -->
            <!-- ============================================================== -->

            <div class="content-page" style="margin-left: 0px;">
                <div class="content" >

                    <!-- Start Content-->
                    <div class="container-fluid">
                        
                        <!-- start page title -->
                        <div class="row">
                            <div class="col-12">
                                <div class="page-title-box">
                                    <div class="page-title-right">
                                       <!--  <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Hyper</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Tables</a></li>
                                            <li class="breadcrumb-item active">Basic Tables</li>
                                        </ol> -->
                                    </div>
                                    <h4 class="page-title">价格管理</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
                                		<%-- <div class="row">
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
										</div> --%>
										<div class="row" style="margin-top: 10px;">
											<div class="col-10">
											    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#exampleModalCenter" onclick="addPrice();" >新增价格</button>
										      	<button type="button" class="btn btn-info" data-toggle="modal" data-target="#exampleModalCenter" onclick="updatePrice();">修改价格</button>
								<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
								 -->		    </div>
								<!-- 			 <div class="col-2">
										      	<button type="button" class="btn btn-primary" onclick="queryPriceData();">查询价格</button>
										    </div> -->
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                        <div class="table-responsive" style="min-height: 150px;">
                                            <table class="table  mb-0 table-hover table-centered text-nowrap"  >
                                                <thead>
                                                    <tr>
												      <th scope="col" style="width: 20px;">#</th>
												      <th scope="col" style="width: 20px;">价格名称</th>
												<!--       <th scope="col" style="width: 20px;">阶梯模式</th>
												      <th scope="col" style="width: 20px;">结算时间</th> -->
												      <th scope="col" style="width: 20px;">基本单价</th>
												  <!--     <th scope="col">排污费</th>
												      <th scope="col">其他费用</th>
												      <th scope="col">合计价格</th> -->
												      <th scope="col">保底金额</th>
												      <th scope="col">保底量</th>
												      <th scope="col">囤积量</th>
												      <th scope="col">报警量</th>
												      <th scope="col">状态</th>
												      <th scope="col">创建时间</th>
												      <th scope="col">更新时间</th>
												      <!-- <th scope="col">一阶吨限</th>
												      <th scope="col">二阶单价</th>
												      <th scope="col">二阶吨限</th>
												      <th scope="col">二阶合计</th>
												      <th scope="col">三阶单价</th>
												      <th scope="col">三阶合计</th> -->
												      <!-- <th scope="col">预存量</th> -->
												      <!-- <th scope="col">透支量</th> -->
                                                    </tr>
                                                </thead>
                                                <tbody >
												  <tr v-for="(price,index) in priceList" @click="getData(index)" :for="'radio'+index" >
												      <th scope="row"> <input type="radio" :id="'radio'+index" name="p_id" :value="index" v-model="checkedRows" >{{price.p_id }}</th>
												      <td>{{price.p_name}}</td>
												    <!--   <td>{{getDescByBeanAttrValue("price","p_ladder",price.p_ladder)}}</td>
												      <td>{{price.p_ladder == 0 ? '每月'+price.p_settleday +'日' : price.p_settlemonth +'月'+ price.p_settleday +'日' }}</td>
											 -->	      <td>{{price.p_base1   }}</td>
												 <!--      <td>{{price.p_other1   }}</td>
												      <td>{{price.p_other2   }}</td>
												      <td>{{price.p_price1 }}</td> -->
												      <td>{{price.p_lowprice }}</td>
												      <td>{{price.p_lowamount }}</td>
												      <td>{{price.p_limitamount }}</td>
												      <td>{{price.p_weakprompt }}</td>
												      <td>{{getDescByBeanAttrValue("price","p_enabled",price.p_enabled) }}</td>
												      <td>{{price.p_createdate }}</td>
												      <td>{{price.p_updatedate }}</td>
											<!-- 	      <td>{{price.p_ton1 }}</td>
												      <td>{{price.p_base2 }}</td>
												      <td>{{price.p_ton2 }}</td>
												      <td>{{price.p_price2 }}</td>
												      <td>{{price.p_base3 }}</td>
												      <td>{{price.p_price3 }}</td> -->
												   <!--    <td>{{price.p_storedamount }}</td> -->
												<!--       <td>{{price.p_overflat }}</td> -->
												  </tr>
												  
                                                </tbody>
                                            </table>
                                        </div> <!-- end table-responsive-->

                                    </div> <!-- end card body-->
                                </div> <!-- end card -->
                            </div><!-- end col-->

                        
                        </div>
                        <!-- end row-->

                        
                    </div> <!-- container -->

                </div> <!-- content -->

            </div>

            <!-- ============================================================== -->
            <!-- End Page content -->
            <!-- ============================================================== -->

<form name="updateForm" action="#" >
<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered   modal-lg modal-dialog-scrollable" role="document">
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
		      <div class="col-md-2 align-self-center">价格名称  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_name" class="form-control" placeholder="请输入价格名称">
			  		
			  		<input type="hidden" name="update_p_ladder" class="form-control" value="<%=PriceBean.LADDER_MONTH%>">
   		      		<input type="hidden" name="update_p_settlemonth" class="form-control" value="1">
   		      		<input type="hidden" name="update_p_settleday" class="form-control" value="1">
		      		<input type="hidden" name="update_p_other1" class="form-control" value="0">
		      		<input type="hidden" name="update_p_other2" class="form-control" value="0">
					<input type="hidden" name="update_p_ton1" class="form-control" value="0">
					<input type="hidden" name="update_p_base2" class="form-control" value="0">
					<input type="hidden" name="update_p_ton2" class="form-control" value="0">
					<input type="hidden" name="update_p_base2" class="form-control" value="0">
					<input type="hidden" name="update_p_base3" class="form-control" value="0">
					<input type="hidden" name="update_p_storedamount" class="form-control" value="0">
					<input type="hidden" name="update_p_overflat" class="form-control" value="0">
		      
			  </div>
		      
		      <div class="col-md-2  align-self-center">基本单价  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_base1" class="form-control"  onchange="calcLowprice()"  placeholder="单位：元">
				</div>
		      
		    </div>
		    
		    <div class="row">
		      <div class="col-md-2  align-self-center">保底量  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_lowamount" onchange="calcLowprice()" class="form-control" placeholder="单位：吨">
				</div>
				
		       <div class="col-md-2 align-self-center">保底金额  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_lowprice" class="form-control" disabled="disabled" placeholder="单位：元">
				</div>
		    </div>
		    
		    <div class="row">
		       <div class="col-md-2 align-self-center">状态  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					 <select name="update_p_enabled" class="form-control">
					  <option  value="<%=PriceBean.ENABLED_TRUE %>">启用</option>
					  <option  value="<%=PriceBean.ENABLED_FALSE %>">禁用</option>
					</select>
				</div>
		      
		      <div class="col-md-2  align-self-center">ID标识</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_id" class="form-control" disabled="disabled" placeholder="无需填写">
				</div>
		      
		    </div>
		    
		    <div class="row">
		      
		      <div class="col-md-2  align-self-center">报警量  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_weakprompt" class="form-control" placeholder="单位：吨" value="10">
				</div>
		      
		      <div class="col-md-2  align-self-center">屯积量  <span style="color: red;">*</span></div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_limitamount" class="form-control" placeholder="单位：吨" value="9999">
				</div>
		      
		    </div>
		    
		   <%--  <div class="row">
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
	--%>
		  <!--   <div class="row">
		       <div class="col-md-2 align-self-center">排污费</div>
		      <div class="col-md-4 ml-auto">
					<input type="text" name="update_p_other1" class="form-control" placeholder="单位：元">
				</div>
		      
		      <div class="col-md-2  align-self-center">其他费用</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_other2" class="form-control" placeholder="单位：元">
				</div>
		      
		    </div> -->
		    
		<!--     <div class="row">
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
		      
		      <div class="col-md-2  align-self-center">ID标识</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_id" class="form-control" disabled="disabled" placeholder="无需填写">
 				</div>
		    </div>
		    <div class="row">
		       <div class="col-md-2 align-self-center">预存量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_storedamount" class="form-control" placeholder="单位：元">

				</div>
		      
		       <div class="col-md-2 align-self-center">透支量</div>
		      <div class="col-md-4 ml-auto">
					<input type="number" name="update_p_overflat" class="form-control" placeholder="单位：元">

				</div>
		    </div> -->
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



        </div>
        <!-- END wrapper -->


<!-- App js -->
        <script src="/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>

 		<script src="/staticFiles/vue/dist/vue.min.js"></script>

		<script src="page/frame/price/js/priceManager.js" type="text/javascript"></script> 
 
    </body>
</html>
