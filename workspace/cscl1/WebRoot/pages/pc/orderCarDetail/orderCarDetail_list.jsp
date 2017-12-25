<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单车辆信息管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#CLOrderCarDetailTable').datagrid({
		title : '订单车辆信息管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/orderCarDetail/load.do?orderId='+"${orderId}",
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
				id : "backButton",
				text : '返回',
				disabled : false,
				iconCls : 'icon-back',
				handler : function() {
					document.location.href = "${ctx}/order/list.do";
				}
		}],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '订单流水号',field : 'or_number',rowspan : 1,align : 'center',width : '150'},
		            {title : '车辆规格',field : 'cspName',rowspan : 1,align : 'center',width : '200'},
		            {title : '车辆类型',field : 'ctpName',rowspan : 1,align : 'center',width : '200'},
		            {title : '其他',field : 'cotherName',rowspan : 1,align : 'center',width : '200'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '200'}
		           ]]
	});
	$('#CLOrderCarDetailTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});
 
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			       <form id="searchRuleForm" method="post">
						<table style="margin: 0 auto; margin-top: 0px;">
					
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLOrderCarDetailTable"></table>
			</div>
	</div>
</body>
</html>