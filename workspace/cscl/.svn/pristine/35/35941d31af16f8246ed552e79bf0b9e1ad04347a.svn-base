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
	$('#rechargeTable').datagrid({
		title : '充值明细',
		loadMsg : '数据装载中......',
		url:'${ctx}/financeStatement/loadRecharge.do',
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
			id : "exportRechargeExecl",
			text : '导出',
			iconCls : 'icon-excel',
			handler : function() {
				$("#rechargeTable").datagrid("loading");
					var params = $("#searchRechargeForm").serialize();
				$.ajax({
					type : "POST",
					dataType:"json",
					url : "${ctx}/financeStatement/exportRechargeExecl.do",
						data : params,
					success : function(response) {
						if(response.success){
							window.location.href ="${ctx}/excel/"+response.url;
							$("#rechargeTable").datagrid("loaded");
						}else{
							$.messager.alert('提示', '操作失败');
							$("#rechargeTable").datagrid("loaded");
						} 
					}
				});
			}
		}
        ],
		columns : [[
// 				{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '客户名称',field : 'fcustName',rowspan : 1,align : 'center',width : '220'},
		        {title : '业务员',field : 'fsaleMan',rowspan : 1,align : 'center',width : '200'},
		        {title : '部门',field : 'fmanDept',rowspan : 1,align : 'center',width : '220'},
		        {title : '联系方式',field : 'userPhone',rowspan : 1,align : 'center',width : '180'},
		        {title : '预充值金额',field : 'fmoney',rowspan : 1,align : 'center',width : '200'},
		        {title : '充值送金额',field : 'faddMoney',rowspan : 1,align : 'center',width : '150'},
		        {title : '充值合计',field : 'ftotalMoney',rowspan : 1,align : 'center',width : '150',formatter:function(value,rowData,rowIndex){return eval(rowData.fmoney)+eval(rowData.faddMoney);}},
		        {title : '充值时间',field : 'fcreateTime',rowspan : 1,align : 'center',width : '200',formatter:localTime},
		        {title : '充值方式',field : 'fbankname',rowspan : 1,align : 'center',width : '100'}
		  ]]
	});
	$('#rechargeTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	//用户管理查询按钮点击事件
	$("#searchButton").click(function() {
       var opts = $("#rechargeTable").datagrid("options");
	    opts.url = '${ctx}/financeStatement/loadRecharge.do';
		$('#rechargeTable').datagrid('load', JSON.parse(toJOSNStr($("#searchRechargeForm").serializeArray())));
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
			<form id="searchRechargeForm" method="post">
				<table style="margin: 0 auto; margin-left:200px;  margin-top: 20px;">
					<tr>
					<td class="m-info-title">业务员:</td>
						<td>
						<input id="search_fsaleman" name="fsaleMan" style="width: 100px;"/>
						</td>
						<td class="m-info-title">客户:</td>
						<td>
						<input id="search_fcustname" name="fcustName" style="width: 100px;"/>
						</td>
						<td class="m-info-title">部门:</td>
						<td>
						<input id="search_fmandept" name="fmanDept" style="width: 100px;"/>
						</td>
						<td class="m-info-title">充值时间:</td>
						<td><input type="text" id="createTimeBegin"
							class="datePicker MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})"
							name="createTimeBegin" /> - <input type="text"
							id="createTimeEnd" class="MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeBegin\')}'})"
							name="createTimeEnd" />
						<a id="searchButton" href="javascript:;"
							class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
				<div id="account"></div>
			</form>
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="rechargeTable"></table>
		</div>
	</div>
</body>
</html>