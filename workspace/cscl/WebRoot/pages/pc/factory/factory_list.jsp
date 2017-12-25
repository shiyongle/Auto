<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>工厂管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js"type="text/javascript"></script>
<script type="text/javascript" src="https://webapi.amap.com/maps?v=1.3&key=08dc9d81ec525d98f8a71b19bdd60647&plugin=AMap.Geocoder,AMap.Autocomplete,AMap.PlaceSearch"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#factoryTable').datagrid({
		title : '工厂管理',
		loadMsg : '数据装载中......',
		url:"${ctx}/factory/load.do",
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		singleSelect:true,
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
					text : '新建',
					iconCls : 'icon-add',
					handler : function() {
							$("#createWindow").window({
								title : "新建",
								href : '${ctx}/factory/create.do',
								width : 595,
								height :515,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				          }
			          },'-',{
							id:"eidt",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#factoryTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择规则记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
							    	 $("#createWindow").window({
										title : "修改",
										href : '${ctx}/factory/edit.do?id='+rows[0].factory_id	,
										width : 595,
										height :515,
										draggable:true,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										modal : true
									});
								} 
							}
					}],
		columns : [[
				{title : 'ID',field : 'factory_id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '工厂名称',field : 'factory',rowspan : 1,align : 'center',width : '150',formatter:function(value,rowData,rowIndex){
                	return "<a href=\"javascript:void(0)\" style=\"text-decoration:none;\" onclick=\"createwindow("+rowData.factory_id+")\">"+ rowData.factory + "</a>";
               	}}, 
		        {title : '地址',field : 'faddress',rowspan : 1,align : 'center',width : '350'},
		        {title : '联系人',field : 'flinkman',rowspan : 1,align : 'center',width : '120'},
		        {title : '联系电话',field : 'flinkphone',rowspan : 1,align : 'center',width : '200'},
		        {title : '状态',field : 'fstatus',rowspan : 1,align : 'center',width : '120',formatter:function(value,rowData,rowIndex){
		        	if(rowData.fstatus == 1){
		        		return '<font style="color:green;">启用</font>';
		        	}else{
		        		return '<font style="color:red;">停用</font>';
		        	}
		        }},
		   		{title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '400'}
		      ]]	
	})
	//分页
	$('#factoryTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	//余额查询按钮点击事件
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
//查看弹窗
function createwindow(data){
	$("#createWindow").window({
		title : "查看",
		href : '${ctx}/factory/view.do?id='+data,
		width : 595,
		height :490,
		draggable:true,
		resizable:false,
		maximizable:false,
		minimizable:false,
		collapsible:false,
		modal : true
	});										
}	
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<!-- <div data-options="region:'north',title:'查询条件'"
			style="height: 120px; border: 0px;">
		</div> -->	
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="factoryTable"></table>
		</div>
		<div id="createWindow"style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>
