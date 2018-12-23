<%@page import="com.yd.business.lottery.bean.ExpertResultAndFlowBean"%>
<%@page import="com.yd.util.DateUtil"%>
<%@page import="com.yd.business.lottery.bean.ExpertBean"%>
<%@page import="com.yd.business.lottery.bean.CqsscInfoBean"%>
<%@page import="com.yd.util.StringUtil"%>
<%@page import="com.yd.business.lottery.bean.ExpertResultBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path +"/";
	
	List<ExpertResultAndFlowBean> list = (List<ExpertResultAndFlowBean>)request.getAttribute("list");
	List<ExpertBean> expertList = (List<ExpertBean>)request.getAttribute("expertList");
	
	if(list == null) list = Collections.EMPTY_LIST;
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="shortcut icon" href="http://caipiao.163.com/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="http://pimg1.126.net/caipiao/wap/img/touchLogo.png?t=2">

<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no,minimal-ui">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta content="telephone=no" name="format-detection">
<meta name="renderer" content="webkit">
<meta name="keywords" content="专家中奖查询">
<meta name="description" content="专家中奖查询">
<meta name="renderer" content="webkit">
<title>【专家中奖查询】</title>
<link rel="canonical" href="http://caipiao.163.com/award/">

<link rel="stylesheet" href="./page/lottery/sportLottery/css/combo_cd2b5c8.css">

<link rel="stylesheet" href="./page/lottery/sportLottery/css/award.css">
<link rel="stylesheet" href="./page/lottery/sportLottery/css/iconfont.css">
<script src="js/jquery.js"></script>
<style>
.gmu-media-detect {
	undefinedtransition: width 0.001ms;
	width: 0;
	position: relative;
	bottom: -999999px;
}

@media screen and (width: 375px) {
	#gmu-media-detect0 {
		width: 100px;
	}
}
</style>
<link rel="stylesheet" type="text/css" media="screen" charset="UTF-8"
	href="./page/lottery/sportLottery/css/topHi.css">
</head>
<body class="" style="" id="notInWebView">
	<article class="docBody clearfix showAwardBottom">
		<div id="header-sticky-wrapper" class="sticky-wrapper"
			style="height: 3.66rem;">
			<header id="header">
				<h1>专家开奖公告</h1>
				<!-- <a class="goBack " href="javascript:;" cpurl="/t/index.html"
					target="_self" rel="nofollow">首页</a> 
				<div class="rightBox">
					<a class="indexIcon "
						href="https://m3.ttacp8.com/nfop/tgnrrz/index.htm?auto=start"></a>
				</div>
				-->
			</header>
		</div>
		
		<section class="awardList">
			<dl>
				<dd>
					<form action="lottery/createExpertResultInfo.do" method="post" id="form">
					<input type="hidden" id="result_id" name="id">
					<table style="width: 100%">
						<tr>
							<td nowrap>专家</td>
							<td>
								<select name="expert_id" id="expertid" onchange="clearHidden()">
									<option value="-1">请选择</option>
									<%for(ExpertBean bean : expertList){ %>
									<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
									<%} %>
								</select>
							</td>
							<td nowrap >&nbsp;百元赔率&nbsp;</td>
							<td colspan="2"><input name="odds" style="width: 98%" id="odds" ></td>
						</tr>
						<tr>
							<td nowrap>是否中奖&nbsp;</td>
							<td>
								<select name="lottery_result">
									<option value="<%=ExpertResultBean.LOTTER_RESULT_WAIT %>">待开</option>
									<option value="<%=ExpertResultBean.LOTTER_RESULT_TRUE %>">中奖</option>
									<option value="<%=ExpertResultBean.LOTTER_RESULT_FALSE %>">未中</option>
									<option value="<%=ExpertResultBean.LOTTER_RESULT_REST %>">休息</option>
								</select>
							</td>
							<td nowrap> &nbsp;本次应投&nbsp;</td>
							<td colspan="2"><input name="join_money" style="width: 98%" id="join_money" onclick="calcCurrentJoinMoney()"></td>
						</tr>
						<tr>
							<td nowrap>场次编码&nbsp;</td>
							<td colspan="4">
								<input id="sports_lottery_code" name="sports_lottery_code" style="width: 98%">
							</td>
						</tr>
						<!-- <tr>
							<td nowrap>&nbsp;期数&nbsp;</td>
							<td colspan="3"><input id="lottery_period" name="lottery_period" style="width: 98%" value=""></td>
							<td style="text-align: right;"><input type="submit" value=" 提  交 "  > &nbsp;&nbsp;</td>
						</tr> -->
						<tr>
							<td nowrap>录入时间&nbsp;</td>
							<td colspan="3"><input id="lottery_date" name="enter_time" style="width: 98%" value="<%=DateUtil.getNowOnlyDateStr() %>"></td>
							<td style="text-align: right;"><input type="submit" value=" 提  交 "  > &nbsp;&nbsp;</td>
						</tr>
					</table>
					</form>
				</dd>
				<dd>
					<form action="lottery/displayExpertResultInfo.do" method="post">
						<table style="width: 100%">
							<tr>
								<td nowrap>专家</td>
								<td>
									<select name="expert_id" >
										<option value="">请选择</option>
										<%for(ExpertBean bean : expertList){ %>
										<option value="<%=bean.getId()%>"><%=bean.getName() %></option>
										<%} %>
									</select>
								</td>
								<td nowrap >&nbsp;编码&nbsp;</td>
								<td colspan="2"><input name="sports_lottery_code" style="width: 98%" ></td>
							</tr>
							<tr>
								<td nowrap>录入时间</td>
								<td colspan="2"><input name="enter_time" style="width: 98%" value="<%=DateUtil.getNowOnlyDateStr() %>"></td>
								<td>&nbsp;&nbsp;&nbsp;
									<select name="lottery_result">
										<option value="">全部</option>
										<option value="<%=ExpertResultBean.LOTTER_RESULT_WAIT %>">待开</option>
										<option value="<%=ExpertResultBean.LOTTER_RESULT_TRUE %>">中奖</option>
										<option value="<%=ExpertResultBean.LOTTER_RESULT_FALSE %>">未中</option>
										<option value="<%=ExpertResultBean.LOTTER_RESULT_REST %>">休息</option>
									</select>
								</td>
								<td style="text-align: right;"><input type="submit" value=" 查    询 "  > &nbsp;&nbsp;</td>
							</tr>
						</table>
					</form>
				</dd>
				<%-- <dd>
					<form action="" method="post">
						<table style="width: 100%">
							<tr>
								<td nowrap>录入时间&nbsp;</td>
								<td colspan="3"><input id="" name="" style="width: 98%" value="<%=DateUtil.getNowOnlyDateStr() %>"></td>
							
								
							</tr>
						</table>
					</form>
				</dd> --%>
				
				<% for(ExpertResultAndFlowBean bean : list){ %>
				<dd id="dd_<%=bean.getId()%>" onclick="changeBackground(this,<%=bean.getId()%>);">
					<a href="javascript: void(0);" >
						<input type="hidden" id="expert_id_<%=bean.getId() %>" value="<%=bean.getExpert_id()%>">
						<input type="hidden" id="sports_lottery_code_<%=bean.getId() %>" value="<%=bean.getSports_lottery_code()%>">
						<h2>
							<em id="expert_name_<%=bean.getId() %>"><%=bean.getExpert_name() %></em>
							<span id="lottery_date_<%=bean.getId() %>"><%=bean.getEnter_time() %></span> 
							<span id="result_id_<%=bean.getId() %>">系统ID:<%=bean.getId() %></span> 
							&nbsp;<span ><%=bean.getSports_lottery_code() %></span> 
						</h2>
						<!-- <p>
							<em class="smallRedball" rel="12">9</em>
							<em class="smallRedball" rel="16">5</em>
							<em class="smallRedball" rel="21">3</em>
							<em class="smallRedball" rel="29">6</em>
							<em class="smallRedball" rel="29">6</em>
							
							<em	class="smallBlueball" rel="16">2</em>  总和大于等于23就是大
						</p> -->
						<p>
							<span class="date">投入金额：<span style="color: black;" id="join_money_<%=bean.getId() %>"><%=bean.getJoin_money() %></span></span>
							<% 	String color = "black;";
								if(ExpertResultBean.LOTTER_RESULT_TRUE.equalsIgnoreCase(bean.getLottery_result() )){
									color = "red;";
								}else if(ExpertResultBean.LOTTER_RESULT_FALSE.equalsIgnoreCase(bean.getLottery_result())){
									color = "green;" ;
								} %>
							<span class="date">中奖：<span style="color: <%=color %>;" id="lottery_result_<%=bean.getId() %>"><%=bean.getLottery_result() %></span></span>
							 赔率：<span id="odds_<%=bean.getId() %>"><%=bean.getOdds() %></span> 
							<span>中奖金额：<span style="color: red;"><%=StringUtil.convertNull(bean.getLottery_money()) %></span>
						
						<br> 
							 个人盈亏：<% if(bean.getMoney_sum() > 0){ %>
							 <span style="color: red;"><%=bean.getMoney_sum() %></span>  
							 <%}else{ %>
							 <span style="color: green;"><%=bean.getMoney_sum() %></span>  
							 <%} %>
							  本次资金池剩余：<span style="color: red;"><%=bean.getCash_pool() %></span>
							  已连续<span style="color: black;"><%=bean.getCurrent_loss_days() %>次</span>不中
						</p> 
		<!--  <i class="righredw"></i> -->
				<%} %>
				
			</dl>
		</section>

		<!--  底部浮动框 -->
		<section class="awardBottom " style="display: none;">
			<a class="clientDownloadButtonInAward"
				href="https://m3.rrzcp8.com/nfop/tgnrrz/index.htm?auto=start">下载客户端</a>
			<a class="buyButtonInAward"
				href="https://m3.ttacp8.com/nfop/tgnbutton/index.htm?from=kjb">领大神方案</a>
		</section> 
	</article>

	<div class="gmu-media-detect" id="gmu-media-detect0"></div>
	<div id="forTap"
		style="color: White;opacity:0;border-radius: 60px; position: absolute; z-index: 99999; width: 60px; height: 60px;left:-999px;top:-999px;"></div>

	<!--  靠近右下固定位置的浮动框  -->
	<div class="topSwipeWrap fixed_right_bottom topHiPos4"
		style="display: none;">
		<div>
			<div class="topSwipeItem">
				<a href="https://m3.ttacp8.com/nfop/tgn3-2/index.htm?from=award"><img
					src="" style="width: 5.16667rem;"></a>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var ddid = 0;
function changeBackground(ele,id){
	if(ddid != 0){
		var dd = document.getElementById(ddid);
		dd.style.backgroundColor = 'transparent' ;
	}
	ddid = ele.id;
	ele.style.backgroundColor = '#D3D3D3' ;
	
	var expert_id = $("#expert_id_"+id).val() ;
	$("#expertid").find("option[value='"+expert_id+"']").attr("selected",true);
	
	var result = document.getElementById("lottery_result_"+id).innerHTML;
	if(result == "<%=ExpertResultBean.LOTTER_RESULT_WAIT %>"){
		
//		var expert_name = $("#expert_name_"+id).html() ;
		var odds = $("#odds_"+id).html();
		var sports_lottery_code = $("#sports_lottery_code_"+id).val();
		var lottery_date = $("#lottery_date_" + id).html();
		var join_money = $("#join_money_"+id).html();
		$("#result_id").val(id);
		$("#expertid").find("option[value='"+expert_id+"']").attr("selected",true);
		$("#odds").val(odds);
		$("#sports_lottery_code").val(sports_lottery_code);
		$("#lottery_date").val(lottery_date);
		$("#join_money").val(join_money);
	}
}

function calcCurrentJoinMoney(){
	var odds = $("#odds").val();
	var expertid = $("#expertid").val();
	if(expertid <=0 || odds ==''){
		alert('请先选择专家 和 百元赔率，会自动计算！');
		return;
	}
	$.ajax({ url: "lottery/calcCurrentJoinMoney.do?odds="+odds+"&expertid="+expertid, 
		success: function(res){
        	$("#join_money").val(res);
      }});
}
function clearHidden(){
	$("#result_id").val("");
	$("#odds").val("");
	$("#join_money").val("");
	$("#sports_lottery_code").val("");
}

</script>
</html>
