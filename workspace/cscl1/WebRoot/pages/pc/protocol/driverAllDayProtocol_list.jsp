<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>包天运费设置</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	//查询按钮点击事件
	$("#searchButton").click(function() {
		$('#allDaySettingTable').datagrid('load', JSON.parse(toJOSNStr($("#searchForm").serializeArray())));
	});
	$('#allDaySettingTable').datagrid({
		title : '设置',
		loadMsg : '数据装载中......',
		url:'${ctx}/protocol/driverLoad.do',
		queryParams:{fdriver_type:1},
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
								href : '${ctx}/protocol/addDriverAllDayRule.do',
								width : 245,
								height :230,
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
								var rows = $("#allDaySettingTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择规则记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
							    	 $("#createWindow").window({
										title : "修改",
										href : '${ctx}/protocol/editAllDayRule.do?id='+rows[0].id,
										width : 245,
										height :230,
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
							var rows = $("#allDaySettingTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/protocol/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#allDaySettingTable').datagrid('reload');
						                        });
						                    } else {
						                        $.messager.alert('提示', '协议已生成订单无法删除');
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
								var rows = $("#allDaySettingTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/protocol/change.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#allDaySettingTable').datagrid('reload');
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
									var rows = $("#allDaySettingTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
									 	$.ajax({
									 			type : "POST",
												url : '${ctx}/protocol/change.do?'+toStrId(rows),
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#allDaySettingTable').datagrid('reload');
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
					{title : '客户',field : 'cusName',rowspan : 1,align : 'center',width : '150'},
					{title : '状态',field : 'status',rowspan : 1,align : 'center',width : '150',formatter:applyToStatus},
					{title : '车型',field : 'carSpecName',rowspan : 1,align : 'center',width : '150'},
		            {title : '包天价格',field : 'startPrice',rowspan : 1,align : 'center',width : '200'},    
		            {title : '备注',field : 'pubRemark',rowspan : 1,align : 'center',width : '200'}		       
		            ]],
		onSelect : ableEditButton,
		onUnselect :ableEditButton,
		onSelectAll:ableEditButton,
		onUnselectAll:ableEditButton
	});
	$('#allDaySettingTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});
//状态
function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">生效</span>';
	}else if(value==0){
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
	var selections = $("#allDaySettingTable").datagrid('getSelections');
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
		if((selections[i].status==0)){
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
				<form action="" id="searchForm">
					<table style="margin: 0 auto; margin-top: 20px;">
						<td>
							<span class="m-info-title">用户名：</span>
							<input type="hidden" name="fdriver_type" value="1">
							<input type="text" id="searchKey"  class="Msearch-key" name="cusName"  style="width:200px;"/>
							<a id="searchButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>
					</table>
				</form>		
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
				<table id="allDaySettingTable">
				</table>
			</div>
			<div id="createWindow"style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>