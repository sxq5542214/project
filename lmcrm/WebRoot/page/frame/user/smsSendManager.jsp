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
    <meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
    <base href="<%=basePath%>">
    
    <!-- Bootstrap 的 CSS 文件 -->


	<link  rel="stylesheet" href="/staticFiles/jstree-bootstrap-theme/dist/themes/proton/style.min.css">
	<link href="/staticFiles/bootstrap4/hyper/assets/css/icons.min.css" rel="stylesheet" type="text/css" />
    <link href="/staticFiles/bootstrap4/hyper/assets/css/app.min.css" rel="stylesheet" type="text/css" />
        
	<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
	
	<link rel="stylesheet" href="/staticFiles/jstree@3.3.12/dist/themes/default/style.min.css">
	<script src="/staticFiles/jquery@3.5.1/dist/jquery.min.js" ></script>
    <script src="/staticFiles/bootstrap4/hyper/assets/js/app.min.js"></script>
	<script src="/staticFiles/vue/dist/vue.js"></script>
<script
	src="/staticFiles/jstree@3.3.12/dist/jstree.min.js"></script>
	<script type="text/javascript" src="js/client/windowsClient.js"></script>
<script src="js/common/dictionaryData.js" type="text/javascript"></script>

    <title>短信发送模块</title>
  </head>
 
<body style="padding-bottom: 0px;"> 

        <!-- Begin page -->
	<div class="wrapper" id="userManagerDiv">

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
                                <!--      <ol class="breadcrumb m-0">
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Hyper</a></li>
                                            <li class="breadcrumb-item"><a href="javascript: void(0);">Tables</a></li>
                                            <li class="breadcrumb-item active">Basic Tables</li>
                                        </ol> -->
                                    </div>
                                    <h4 class="page-title">短信发送管理</h4>
                                </div>
                            </div>
                        </div>     
                        <!-- end page title --> 

                        <div class="row">
                            <div class="col-xl-12">
                                <div class="card">
                                	<div class="card-header">
										<div class="row" style="margin-top: 10px;">
											<div class="col-10">
											     <button type="button" class="btn btn-success"  onclick="showSendSMS();">发送短信</button>
<!-- 										      	<button type="button" class="btn btn-danger" onclick="reSendSMS();">重新发送失败短信</button>
 -->									<!-- 	      	<button type="button" class="btn btn-secondary" onclick="deleteUser();">删除用户</button>
										      	<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#queryUserModalCenter" >查询用户</button>
								 -->			
								<!-- 		      	<button type="button" class="btn btn-secondary">删除价格</button>
								 -->		    </div>
									<!-- 		 <div class="col-2">
										      	<button type="button" class="btn btn-primary" onclick="queryUserData();">查询用户</button>
										    </div> -->
										</div>
	                                        <!-- <h4 class="header-title">Striped rows</h4>
	                                        <p class="text-muted font-14 mb-4">
	                                            Use <code>.table-striped</code> to add zebra-striping to any table row
	                                            within the <code>&lt;tbody&gt;</code>.
	                                        </p> -->
                                	</div>
                                    <div class="card-body">
                                    	
                                    	
									  <div class="row">
									      <div class="col-3" style="padding: 0px;">
									      		<div class="card"  id="treeDiv">
									      			<div class="card-header">地址列表</div>
									      			<div class="card-body">
														<div id="tree" style=""></div>
													</div>
									      		</div>
										  </div>
										  <div class="col-9">
                                        <div class="table-responsive" style="min-height: 150px;" id="tableDiv">
                                            <table class="table  mb-0 table-hover table-centered text-nowrap dt-responsive" id="userDataTable" >
                                                <thead>
                                                    <tr>
														<th scope="col">操作</th>
														<th scope="col">模版名称</th>
														<th scope="col">发送人</th>
														<th scope="col">失败数</th>
														<th scope="col">成功数</th>
														<th scope="col">总数</th>
														<th scope="col">发送时间</th>
														<th scope="col">发送内容</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
												 <!--  <tr v-for="(user,index) in userList" @click="getData(index)"
													:for="'radio'+index">
													<th><input type="radio" :id="'radio'+index" name="u_id"
														:value="index" v-model="checkedRows">{{user.u_no }}</th>
													<td>{{user.u_name}}</td>
													<td>{{user.u_phone}}</td>
													<td>{{user.u_balance }}</td>
													<td>{{getDescByBeanAttrValue("user","u_status",user.u_status)
														}}</td>
													<td>{{user.priceName }}</td>
													<td>{{user.device_company }}</td>
													<td>{{user.deviceKindName }}</td>
													<td>{{user.addressName}}{{user.u_address }}</td>
													<td>{{user.u_paperwork }}</td>
													<td>{{user.u_cardno }}</td>
													<td>{{user.u_createdate }}</td>
													<td>{{user.u_updatedate }}</td>
													<td>{{user.u_remark }}</td>
												</tr> -->
												  
                                                </tbody>
                                            </table>
                                        </div> <!-- end table-responsive-->
										</div><!-- end col8 -->
										</div><!-- end row -->
                                    </div> <!-- end card body-->
                                </div> <!-- end card -->
                            </div><!-- end col-->

                        
                        </div>
                        <!-- end row-->

                        
                    </div> <!-- container -->

                </div> <!-- content -->



            </div>




		

		<form name="updateForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="exampleModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="exampleModalCenterTitle"
				aria-hidden="true"> 
				<div class="modal-dialog modal-dialog-centered  modal-lg modal-dialog-scrollable"
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalCenterTitle">发送短信</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="modalBodyDiv">
							<div class="container-fluid">
								<div class="row">
									<div class="col-md-2 align-self-center">发送原由 <span style="color: red;">*</span> </div>
									<div class="col-md-4 ml-auto">
										
										<input type="text" id="sms_name" name="sms_name" class="form-control" required="required" >
									</div>
									
									<div class="col-md-2 align-self-center"> </div>
									<div class="col-md-4 ml-auto">
										
									</div>
								</div>
								<div class="row">
									<div class="col-md-2 align-self-center">短信模板 <span style="color: red;">*</span> </div>
									<div class="col-md-10 ml-auto">
										
										<input type="text" name="sms_template" class="form-control" required="required" readonly="readonly"
											:value="sms_template">
											
											
										  <input type="hidden" class="form-control" id="addressIds" name="addressIds">
										  <input type="hidden" class="form-control" id="sms_templateId" name="sms_templateId" :value="sms_templateId">
									</div>
								</div>
								<div class="row" v-for="num in paramNum">
									<div class="col-md-2 align-self-center">参数{{num}} <span style="color: red;">*</span> </div>
									<div class="col-md-10 ml-auto" >
										
										<input type="text" name="params" :id="'params'+num" class="form-control" required="required" >
									</div>
	
								</div>
								
								<div class="row" >
									<div class="col-md-2 align-self-center">实际内容 <span style="color: red;">*</span> </div>
									<div class="col-md-10 ml-auto" >
										
										<textarea id="send_content" name="send_content" class="form-control" readonly="readonly">{{send_content}}</textarea>
									</div>
									
								</div>
								<div class="row" >
									<div class="col-md-2 align-self-center"> </div>
									<div class="col-md-10 ml-auto" >
										 当前<span style="color: red;">{{sms_content_count}}</span>字
										 <button type="button" class="btn btn-primary"  onclick="checkContentCount();">应用参数并校验字数</button>
										 <span style="color: red;">  *超过64字会拆分成多条短信下发</span>
									</div>
								</div>
								
								
								<div class="row" >
									<div class="col-md-2 align-self-center">涉及地址  </div>
									<div class="col-md-10 ml-auto" >
										
										<textarea id="addressNames" name="addressNames" class="form-control" readonly="readonly"></textarea>
									</div>
									
								</div>
								
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
							<button type="button" class="btn btn-primary"
								onclick="sendSMS()">确 定</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	
	
		

		<form name="phoneInfoForm" action="#">
			<!-- Modal -->
			<div class="modal fade" id="phoneInfoModalCenter" tabindex="-1"
				role="dialog" aria-labelledby="phoneInfoModalCenterTitle"
				aria-hidden="true"> 
				<div class="modal-dialog modal-dialog-centered  modal-lg modal-dialog-scrollable"
					role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="phoneInfoModalCenterTitle">号码明细</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body" id="phoneInfoModalBodyDiv">
							<div class="container-fluid">
								<table class="table  mb-0 table-hover table-centered text-nowrap dt-responsive" id="userDataTable" >
                                                <thead>
                                                    <tr>
														<th scope="col">序号</th>
														<th scope="col">号码</th>
														<th scope="col">计费条数</th>
														<th scope="col">失败类型</th>
														<th scope="col">失败原因</th>
														<th scope="col">请求编号</th>
                                                    </tr>
                                                </thead>
                                                <tbody >
												  <tr v-for="(user,index) in phoneInfoList" >
													<td>{{index+1}}</td>
													<td>{{user.phone}}</td>
													<td>{{user.fee }}</td>
													<td>{{user.code}}</td>
													<td>{{user.message }}</td>
													<td>{{user.request_id }}</td>
												</tr>
												  
                                                </tbody>
                                            </table>
							</div>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关 闭</button>
						</div>
					</div>
				</div>
			</div>
		</form>
	</div>

	
<script type="text/javascript"  src="/staticFiles/dataTables@1.10.21/js/jquery.dataTables.min.js"></script>
        
<script src="page/frame/user/js/smsSendManager.js" type="text/javascript"></script>
</body>


</html>