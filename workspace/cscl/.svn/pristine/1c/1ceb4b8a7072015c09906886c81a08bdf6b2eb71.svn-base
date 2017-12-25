<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务员销售额报表</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#salesReportTable').datagrid({
		title : '业务员销售额报表',
		loadMsg : '数据装载中......',
		url:'',
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
		toolbar :[
					{
					id : "exportReportExecl",
					text : '导出',
					iconCls : 'icon-excel',
					handler : function() {
					$("#salesReportTable").datagrid("loading");
					var rows = $("#salesReportTable").datagrid('getSelections');
					var params = $("#searchSalesReportForm").serialize();
					$.ajax({
					type : "POST",
					dataType:"json",
					url : "",
					data : getOrderId(rows)+ "&" +params,
					success : function(response) {
					if(response.success){
					window.location.href ="${ctx}/excel/"+response.url;
					$("#salesReportTable").datagrid("loaded");
				}else{
					$.messager.alert('提示', '操作失败');
					$("#salesReportTable").datagrid("loaded");
				} 
			}
		});
	}
},'-',
					
		          ],
		columns : [[
				{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '客户名称',field : ' ',rowspan : 1,align : 'center',width : '120'},
		        {title : '业务员',field : ' ',rowspan : 1,align : 'center',width : '100'},
		        {title : '部门',field : ' ',rowspan : 1,align : 'center',width : '100'},
		        {title : '应收',field : ' ',rowspan : 1,align : 'center',width : '150'},
		        {title : '应付',field : ' ',rowspan : 1,align : 'center',width : '150'},
		        ]]
	});
	$('#salesReportTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	//业务员下拉框配置
	$("#search_salesMan").combobox({
		url : "",
		editable : true,
		width:140,
		mode: 'local',
		valueField: 'optionName',//提交值
		textField: 'optionName',//显示值
		filter:searchItem
	});
	
	//用户管理查询按钮点击事件
	$("#searchsalesReportButton").click(function() {
		$('#salesReportTable').datagrid('load', JSON.parse(toJOSNStr($("#searchSalesReportForm").serializeArray())));
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

/***导出多条明细***/
function getOrderId(rows) {
	var paramStr = '';
	for ( var i = 0; i < rows.length; i++) {
		if (i == 0) {
			paramStr += 'ids=' + rows[i].id;
		} else {
			paramStr += "&ids=" + rows[i].id;
		}
	}
	return paramStr;
}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
	
<div data-options="region:'north',title:'业务员销售额报表查询'" style="height:100px;">		       
<div class="reportTop" style="margin-top:20px;">

<form id="searchSalesReportForm" method="post">
<table style="margin: 0 auto; margin-top:5px;">
<tr>
<td class="m-info-title">日期:</td>
<td>
<input type="text" id="loadedTimeBegin" class="datePicker MtimeInput"  onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'loadedTimeEnd\')}'})" name="loadedTimeBegin"  /> -
<input type="text" id="loadedTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'loadedTimeBegin\')}'})" name="loadedTimeEnd"  />
</td>
<td class="m-info-title">业务员:</td>
<td>
<select id="search_salesMan" name="salesMan">
<option value="">请选择</option>
</select>
</td>
<td class="m-info-title">部门:</td>
<td>
<input type="text" id="search_department" name="department" style="width: 150px; height: 16px;margin-top:4px;"/>
<a id="searchsalesReportButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
</td>
</tr>
</table>
</form>

</div>
</div>


		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
		<table id="salesReportTable"></table>
		</div>
		
		
</div>

</body>


</html>