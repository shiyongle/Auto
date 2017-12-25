<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>方案设计</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link href="${ctx}/pages/schemeDesign/css/schemeDesign_list.css" rel="stylesheet">
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js" ></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager2.css"  />
<link type="text/css" rel="stylesheet" href="${ctx}/js/jqwidgets-ver3.9.1/jqx.base.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcore.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxscrollbar.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxbuttons.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxlistbox.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxdropdownlist.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/pages/schemeDesign/schemeDesign_list.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jqwidgets-ver3.9.1/jqxcombobox.js" ></script>
<style>
 .layui-layer-tips .layui-layer-content{
  font-size: 14px;
 }

</style>
</head>
<body>
<div>
	<div class="title">我的业务 > 我的设计</div>
	<div class="productdemand">
		<div class="button">
			<form id="myform" onSubmit="return false;" style="margin-left:10px;">
				<c:if test="${userType==3}" >
					<a style="width:50px;" class="link" data-operate="receiveDemand">接收</a>
				</c:if>
<!-- 				<a data-operate="approintScheme">设计师指定</a> -->
<!-- 				<a data-operate="backdemand" >需求退回</a> -->
<!-- 				<a onclick="addSchemeDesign()">新增方案</a> -->
<!-- 				<a data-operate="demandproduct">新增产品</a> -->
<!-- 				<a style="width:50px;" onclick="getProductDemanView()">查看</a> -->
<!-- 				<a data-operate="exceldemand">导出EXCEL</a> -->
				<a class="link" data-operate="approintScheme">设计师指定</a>
				<a class="link" data-operate="backdemand" >需求退回</a>
				<a class="link" data-operate="addSchemeDesign">新增方案</a>
				<a class="link" data-operate="demandproduct">新增产品</a>
				<a style="width:50px;" onclick="getProductDemanView()" class="history_available link">查看</a>
				<a class="history_available link" onclick="exceldemand()">导出EXCEL</a>
<!-- 				<select id="fstate"  name="schemedesignQuery.fstate"> -->
<!-- 	              <option value="">需求状态</option> -->
<!-- 	              <option value="已分配">已分配</option> -->
<!-- 	              <option value="已接收">已接收</option> -->
<!-- 	              <option value="已设计">已设计</option> -->
<!-- 	              <option value="已完成">已完成</option> -->
<!-- 	              <option value="确认方案">确认方案</option> -->
<!-- 	          </select> -->
	          <select id="ftype" name="schemedesignQuery.ftype">
          		  <option value="-1">请选择类型</option>
	              <option value="1">需求包</option>
	              <option value="0">高端设计</option>
	          </select>
	          <select id="fcustomer" name="schemedesignQuery.fcustomerid">
	              <option value="">全部客户</option>
	              <c:forEach var="customer" items="${customer}">
	              	<option value="${customer.fid}">${customer.fname}</option>
	              </c:forEach>
	          </select>
	          <input type="text" style="margin-left:10px;" placeholder="搜索" class="filter" name="schemedesignQuery.searchKey">
	          <input type="button" value="" class="_submit" id="searchButton">
	          <!-- 2016年3月17日14:08:59 HT-->
	          <input type="hidden" id="fstate"  name="schemedesignQuery.fstate">
              <!-- 2016年3月17日14:08:59 HT-->
          </form>
		</div>
		<!--2016年3月17日10:04:19  ht-->
		<div id="require">
		<table cellpadding="0" cellspacing="0" border="0" width="1055" id="tbl1" style="table-layout: fixed; ">   
		 <tr align="left">
		    <td colspan="5" style="border-bottom: 2px solid #f76350;">
		     <p id="fstate1">
		      <a href="javascript: void(0)" class="active">全部</a> 
		      <a href="javascript: void(0)">已接收</a> 
		      <a href="javascript: void(0)">已设计</a> 
		      <a href="javascript: void(0)">已完成</a> 
		      <a href="javascript: void(0)">确认方案</a>
		      <a href="javascript: void(0)" queryHistory="1">三月前数据</a>
		     </p>  
		    </td>
		   </tr> 
		</table>
		</div>
		<!--2016年3月17日10:04:19  ht-->
		<div class="productdemandTable" style="position: relative;">
			<div id="divTitle" style="position: absolute; width:100%; z-index: 999;"></div>
			<div style="height:100%; overflow-y: scroll; ">
				<table id="demandtable" style="margin-bottom: 35px;">
					<tr class="titleTr">
						<td style="width:44px;"><input type="checkbox" class="all"/></td>
						<td class="tdTitle">需求编号</td>
						<td>客户名称</td>
						<td class="tdTitle">需求名称</td>
						<td style="width:188px;">需求描述</td>
						<td class="tdTitle">发布时间</td>
						<td>客户电话</td>
						<td class="tdTitle">设计师</td>
						<td>类型</td>
						<td class="tdTitle">需求状态</td>
						<td>在线沟通</td>
					</tr>
				</table>
			</div>
			<div style="position: absolute; bottom: 0px; z-index: 999;padding:5px; marggin-right: 20px;background-color: white; height:30px; width:1030px;" >
				<div id="kkpager"></div>
			</div>
		</div>
	</div>
	<div class="schemeDesign">
		<span class="top unselect">&nbsp;&nbsp;&nbsp;</span>
		<div class="expandTop unselect">&nbsp;</div>
		<div class="button unselect">
			<a style="margin-left:10px;" class="link" data-operate="editSchemeDesign">修改</a>
			<a  class="link" data-operate="delScheme" >删除</a>   
			<a  class="link" data-operate="auditSchemeDesign">审核</a>			
			<a class="link"  data-operate="affirmSchemeDesign">确认</a>
			<a  class="link" data-operate="unaffirmSchemeDesign">取消确认</a>
			<a class="link" onclick="viewSchemeDesign()" id="schemeDesign_view" >查看</a>
			<input type="text" style="margin-left:120px;" placeholder="搜索" class="filter" name="schemesearchKey">
          <input type="button" value="" class="_submit" id="searchButton">
		</div>
		<div class="schemeDesignTable">
			<table id="schemetable">
				<tr class="titleTr">
					<td style="width:44px;"><input type="checkbox"/></td>
					<td class="tdTitle">方案编号</td>
					<td>客户名称</td>
					<td class="tdTitle">方案名称</td>
					<td>设计服务商</td>
					<td class="tdTitle">设计师</td>
					<td>新增时间</td>
					<td class="tdTitle">审核人</td>
					<td>是否确认</td>
					<td class="tdTitle">确认人</td>
					<td>确认时间</td>
					<td class="tdTitle">在线沟通</td>
				</tr>
			</table>
			<div id="kkpager2"></div>
		</div>
	</div>
	 <form action="#" method="post" id="hideschemeexcelform" target="_blank" style="display:none;" ></form>  
</div>
</body>
</html>