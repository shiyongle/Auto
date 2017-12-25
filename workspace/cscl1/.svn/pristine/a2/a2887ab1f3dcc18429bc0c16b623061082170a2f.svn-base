<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>通用司机运价</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('#commonDriverPriceTable').datagrid({
		title : '设置',
		loadMsg : '数据装载中......',
		url:'${ctx}/rule/driverLoad.do',
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
					text : '新建',
					iconCls : 'icon-add',
					handler : function() {
							$("#createWindow").window({
								title : "新建",
								href : '${ctx}/rule/createDriverBill.do',
								width : 505,
								height :310,
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
								var rows = $("#commonDriverPriceTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择规则记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
							    	 $("#createWindow").window({
										title : "修改",
										href : '${ctx}/rule/editBillRule.do?id='+rows[0].id,
										width : 505,
									    height :310,
									    draggable:true,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										modal : true
									});
								} 
							}
					},'-', {
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#commonDriverPriceTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/rule/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#commonDriverPriceTable').datagrid('reload');
						                        });
						                    } 
						                    else {
						                        $.messager.alert('提示', '规则已使用，不得删除'); 
						                    }
										}	
									});
							 }
						}
			          }, '-', {
							id : "changeok",
							text : '有效',
							iconCls : 'icon-mini-edit',
							handler : function() {
								var rows = $("#commonDriverPriceTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/rule/driverChange.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#commonDriverPriceTable').datagrid('reload');
							                        });
							                    } 
							                    else {
							                    	  $.messager.alert('提示', '操作失败');
							                    }
											}	
										});
								 }
							}
				          }, '-', {
								id : "changeout",
								text : '失效',
								iconCls : 'icon-mini-edit',
								handler : function() {
									var rows = $("#commonDriverPriceTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
									 	$.ajax({
									 			type : "POST",
												url : '${ctx}/rule/driverChange.do?'+toStrId(rows),
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#commonDriverPriceTable').datagrid('reload');
								                        });
								                    } 
								                    else {
								                    	  $.messager.alert('提示', '操作失败');
								                    }
												}	
											});
									 }
								}
					          }
			          	],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
					{title : '车型',field : 'carSpecName',rowspan : 1,align : 'center',width : '120'},
					{title : '状态',field : 'status',rowspan : 1,align : 'center',width : '120',formatter:applyToStatus},
					{title : '保底公里',field : 'startKilometre',rowspan : 1,align : 'center',width : '120'},
		            {title : '保底价',field : 'startPrice',rowspan : 1,align : 'center',width : '120'},
		            {title : '超出点数单价(元)',field : 'outfopint',rowspan : 1,align : 'center',width : '200'},
		           	{title : '5-20公里单价',field :'kilometre5_20_price',rowspan : 1,align : 'center',width : '150'},
		        	{title : '20-50公里单价',field :'kilometre20_50_price',rowspan : 1,align : 'center',width : '150'},
		        	{title : '50公里以上单价',field :'kilometre50_price',rowspan : 1,align : 'center',width : '150'},
		            {title : '装货费',field : 'fadd_service_1',rowspan : 1,align : 'center',width : '120'},
		            {title : '卸货费',field : 'fadd_service_2',rowspan : 1,align : 'center',width : '120'},
		            {title : '备注',field : 'pubRemark',rowspan : 1,align : 'center',width : '250'}    
		            ]],
		onSelect : ableEditButton,
		onUnselect :ableEditButton,
		onSelectAll:ableEditButton,
		onUnselectAll:ableEditButton
	});
	$('#commonDriverPriceTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});

function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">有效</span>';
	}else if(value==2){
		return '<span style="color:blue; " >失效</span>';
	}
}

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
//批量删除
function toStrId(rows) {
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
function ableEditButton(){
	var selections = $("#commonDriverPriceTable").datagrid('getSelections');
	for(var i = 0;i<selections.length;i++){
	   for(var j=0;j<selections.length;j++){
		  if( selections[j].status!=selections[i].status){	
		  		$("#eidt").linkbutton('disable');
				$("#del").linkbutton('disable');
				$("#changeok").linkbutton('disable');
				$("#changeout").linkbutton('disable');
				return
			 }
	     }
		if((selections[i].status==2 )){
			$("#eidt").linkbutton('disable');
			$("#del").linkbutton('enable');
			$("#changeok").linkbutton('enable');
			$("#changeout").linkbutton('disable');
		}else{
			$("#eidt").linkbutton('enable');
			$("#del").linkbutton('disable');
			$("#changeok").linkbutton('disable');
			$("#changeout").linkbutton('enable');
			return
		}
	}
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">		
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="commonDriverPriceTable">
			</table>
		</div>
		<div id="createWindow"style="margin:0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>