<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值明细</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js"
	type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#balanceTable').datagrid({
		title : '账户余额明细',
		loadMsg : '数据装载中......',
		url:"${ctx}/financebill/billLoad.do",
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
	/*  	toolbar : [{
			id : "exportBalanceExecl",
			text : '导出',
			iconCls : 'icon-excel',
			handler : function() {
				$("#balanceTable").datagrid("loading");
					var params = $("#searchRechargeForm").serialize();
				$.ajax({
					type : "POST",
					dataType:"json",
					url : "${ctx}/financebill/exportExecl.do",
						data : params,
					success : function(response) {
						if(response.success){
							window.location.href ="${ctx}/excel/"+response.url;
							$("#balanceTable").datagrid("loaded");
						}else{
							$.messager.alert('提示', '操作失败');
							$("#balanceTable").datagrid("loaded");
						} 
					}
				});
			}
		}
        ],  */
		columns : [[
		        {title : '客户名',field : 'uname',rowspan : 1,align : 'center',width : '220'}, 
		        {title : '登录账号',field : 'fname',rowspan : 1,align : 'center',width : '200'},
		        {title : '登录手机',field : 'vmiUserPhone',rowspan : 1,align : 'center',width : '220'},
		    /*      {title : '收入金额',field : 'userPhone',rowspan : 1,align : 'center',width : '180'},
		        {title : '支出金额',field : 'fmoney',rowspan : 1,align : 'center',width : '200'}, */
		        {title : '账户余额',field : 'fbalance',rowspan : 1,align : 'center',width : '150'} ,
		      ]]
	});
	
	$('#balanceTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	//余额查询按钮点击事件

	$("#balanceButton").click(function() {
		$('#balanceTable').datagrid('load', JSON.parse(toJOSNStr($("#searchBalanceForm").serializeArray())));
	});
});

//表单信息转JSON
function toJOSNStr(jObject) {
	var results = '{';
	jQuery.each(jObject, function(i, field) {
		if (i == 0) {
			results += '"' + field.name + '":"' + field.value + '"';
		} else {
			results += ',"' + field.name + '":"' + field.value + '"';
		}
	});
	return results + '}';
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'"
			style="height: 120px; border: 0px;">
			<form id="searchBalanceForm" method="post">
				<table style="margin: 0 auto; margin-left:500px;  margin-top: 20px;">
					<tr>
						<td class="m-info-title">客户名:</td>
						<td>
						<input id="search_fman" name="fname" style="width: 100px;"/>
						</td>
						<td class="m-info-title">登录账号:</td>
						<td>
						<input id="search_userName" name="vmiUserName" style="width: 100px;"/>
						</td>
						<td class="m-info-title">登录手机:</td>
						<td>
						<input id="search_Phone" name="vmiUserPhone" style="width: 100px;"/>
						</td>
						<td>
						<a id="balanceButton" href="javascript:;"
							class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
				<div id="account"></div>
			</form>
		</div>	
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="balanceTable"></table>
		</div>
	</div>
</body>
</html>
