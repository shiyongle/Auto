<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>纸箱接单</title>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="${ctx}/css/daterangepicker-bs3.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/css/kkpager.css" />
<link rel="stylesheet" href="${ctx}/css/cardOrder.css">
<style>
	*{box-sizing:content-box;}
	.daterangepicker *{box-sizing:border-box;}
</style>
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<script type="text/javascript" src="${ctx}/js/djSelect.js"></script>
<script src="${ctx}/js/moment.js" type="text/javascript"></script>
<script src="${ctx}/js/daterangepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/pages/productplan/js/cardOrder.js"></script>
</head>
<body>
<div  class="conterDiv">
	<div class="title">
		<span>平台首页</span> &gt; <span>我的业务</span> &gt; <span>纸箱接单</span>
	</div>
	 <!--  <form id="searchForm" class="queryfilter clear" onsubmit="return false">
		<input type="text" placeholder="下单时间" class="querytime" id="querytime" readonly="readonly"/>
		<input type="text" class="query_text" name="deliverapplyQuery.searchKey" id="searchText">
		<input type="button" value="" class="_submit" id="searchButton">
		 <select id="fstate" name="deliverapplyQuery.fstate">
             <option value="" selected="selected">全部</option>
             <option value="0">未接收</option>
             <option value="1">已接收</option>
             <option value="2,3">已入库</option>
         </select>
	</form> 
	 -->
	<!-- 2016/03/17 hss 更改状态选项栏 -->
	 <table cellpadding="0" cellspacing="0" border="0" width="1045" id="tbl1" style="table-layout: fixed;">
			<tr height="20">
			</tr>
			<tr>
			<td colspan="5" style="border-bottom: 2px solid #f76350;">
					<p id="fstate1">
						<a href="javascript: void(0)" fstate="" class="active" >全部</a> 
						<a href="javascript: void(0)" fstate="0" >已下单</a> 
						<a href="javascript: void(0)" fstate="1">已接收</a>
						<a href="javascript: void(0)" fstate="2,3">已入库</a> 
					    <a href="javascript: void(0)" fstate="100">三月前数据</a>						
					</p>
					<form id="searchForm" class="queryfilter " onsubmit="return false">
					<input name="deliverapplyQuery.fstate" type="hidden" id="queryState" value=""/>
					<input type="text" placeholder="下单时间" class="querytime" id="querytime" readonly="readonly"/>
					<input type="text" class="query_text" placeholder="搜索" name="deliverapplyQuery.searchKey" id="searchText">
		            <input type="button" value="" class="_submit" id="searchButton">
					</form>
				</td>
			</tr>
		</table>
	<div>
		<table class="cardList" id="cardList">
			<tr class="titleTR">
				<td width="56px"><input type="checkbox" class="all_check"/>  全选</td>
				<td width="200px" class="tdBorder">产品</td>
				<td width="140px">交期</td>
				<td width="180px" class="tdBorder">地址</td>
				<td width="130px">出入库</td>
				<td width="90px" class="tdBorder">数量</td>
				<td width="160px" class="tdBorder">备注</td>
				<td width="120px">操作</td>
			</tr>
			<tr class="tableButton">
				<td colspan="7">
					<a id="excelBoard">导出Excel</a>
				</td>
			</tr>
			<!-- <tr class="order">
				<td colspan="7" class="order_info">
					客户名称：<span>东经</span>采购订单号：<span>NB1512130001</span>制造订单号：<span>23521452364</span>订单状态：<span>已下单</span>
				</td>
				<td class="order_detail">
					<a href="#">详情</a>
				</td>
			</tr>
			<tr class="product">
				<td><input type="checkbox"  class="choose"/></td>
				<td>
					<img src="" alt="1" />
					<p class="material">东力3号/5BC<br/>90X90X50</p>
				</td>
				<td>
					2015-12-16<br/>上午
				</td>
				<td>1</td>
				<td>入：200<br/>出：200</td>
				<td>100</td>
				<td>
					11111112X
				</td>
				<td class="buttonTR">
					<a href="#">接收</a>
					<a href="#">取消接收</a>
					<a href="#">出入库</a>
				</td>
			</tr>
			<tr class="space"></tr> -->
		</table>
		<div id="kkpager" align="right" style="margin:15px 40px 15px 0px;"></div>
	</div>
	     <form action="#" method="post" id="hidecardorderform" target="_blank" style="display:none;" ></form>  
	
</div>
</body>
</html>