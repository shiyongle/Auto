<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js"
	type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {		
		$("#List_carSpecId").combobox({
			url : "${ctx}/select/getAllCarType.do",
			editable : true,
			width:100,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName',
		  
		});
 	//查询按钮点击事件
	$("#searchRuleButton").click(function() {
		$('#CLRuleTable').datagrid('load', JSON.parse(toJOSNStr($("#searchRuleForm").serializeArray())));
	});
	$('#CLRuleTable').datagrid({
		title : '规则管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/rule/load.do',
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
		onHeaderContextMenu : gridHeaderMenu,
		toolbar : [{
					text : '新建整车规则',
					iconCls : 'icon-add',
					handler : function() {
							$("#createCarloadRule_Window").window({
								title : "新建整车规则",
								href : '${ctx}/rule/createCarLoad.do',
								width : 580,
								height : 310,
								draggable:false,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true	
						    });
				          }
			          },/* {
							text : '新建零担规则',
							iconCls : 'icon-add',
							handler : function() {
									$("#createLessRule_Window").window({
										title : "新建零担规则",
										href : '${ctx}/rule/createLessRule.do',
										width : 340,
										height : 330,
										draggable:false,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										modal : true
								    });
						          }
			         	 },'-',{
								text : '司机运价设置',
								iconCls : 'icon-add',
								handler : function() {
										$("#createBillRule_Window").window({
											title : "司机运价设置",
											href : '${ctx}/rule/createBillLoad.do',
											width :  590,
											height : 210,
											draggable:false,
											resizable:false,
											maximizable:false,
											minimizable:false,
											collapsible:false,
											modal : true
									    });
							          }
				         	 },'-', */{
							id:"EditCarload",
							text:'修改规则',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#CLRuleTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
									if(rows[0].type==1){
							    	 $("#createCarloadRule_Window").window({
										title : "修改整车规则",
										href : '${ctx}/rule/editCarLoad.do?id='+rows[0].id,
										width : 610,
									    height :310,
										modal : true
									});
									}
									else if(rows[0].type==3){
								    	 $("#createBillRule_Window").window({
												title : "修改司机运费规则",
												href : '${ctx}/rule/editBillRule.do?id='+rows[0].id,
												width : 550,
											    height :300,
												modal : true
											});
									}
									else if(rows[0].type==2){
										$("#createLessRule_Window").window({
											title : "修改零担规则",
											href : '${ctx}/rule/editLessRule.do?id='+rows[0].id,
											width : 550,
										    height :340,
											modal : true
										});
										} 
									else{
									  $.messager.alert('提示', '请选择整车记录！', 'info');
									}
								} 
							}
					},'-', {
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#CLRuleTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/rule/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#CLRuleTable').datagrid('reload');
						                        });
						                    } else {
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
								var rows = $("#CLRuleTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/rule/change.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#CLRuleTable').datagrid('reload');
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
									var rows = $("#CLRuleTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
									 	$.ajax({
									 			type : "POST",
												url : '${ctx}/rule/change.do?'+toStrId(rows),
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#CLRuleTable').datagrid('reload');
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
					{title : 'ID',field : 'id',rowspan : 1,align : 'center',sortable:true},
		            {title : '状态',field : 'status',rowspan : 1,align : 'center',width : '100',formatter:applyToStatus},
		            {title : '规则类型',field : 'type',rowspan : 1,align : 'center',width : '150',formatter:applyToType},
		            {title : '车辆规格',field : 'carSpecName',rowspan : 1,align : 'center',width : '100'},
		            {title : '起步公里数 (km)',field : 'startKilometre',rowspan : 1,align : 'center',width : '100'},
		           	{title : '5-20公里单价',field :'kilometre5_20_price',rowspan : 1,align : 'center',width : '150'},
		        	{title : '20-50公里单价',field :'kilometre20_50_price',rowspan : 1,align : 'center',width : '150'},
		        	{title : '50公里以上单价',field :'kilometre50_price',rowspan : 1,align : 'center',width : '150'},
		        	{title : '起步价(元)',field : 'startPrice',rowspan : 1,align : 'center',width : '100'},
		            {title : '装货费(元)',field : 'fadd_service_1',rowspan : 1,align : 'center',width : '150'},
		            {title : '卸货费(元)',field : 'fadd_service_2',rowspan : 1,align : 'center',width : '150'},
		            {title : '其他类型',field : 'otherType',rowspan : 1,align : 'center',width : '200',formatter:applyToOtherType},
		            {title : '超出货物单价',field : 'outPrice',rowspan : 1,align : 'center',width : '100'},
		            {title : '备注',field : 'pubRemark',rowspan : 1,align : 'center',width : '250'},
		            {title : '保底点数',field : 'fopint',rowspan : 1,align : 'center',width : '150'},
		            {title : '超出点数单价(元)',field : 'outfopint',rowspan : 1,align : 'center',width : '150'}
		            ]],
	   onSelect : function(rowIndex, rowData) {
	   	var selections = $("#CLRuleTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onUnselect : function(rowIndex, rowData) {
			var selections = $("#CLRuleTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onSelectAll:function(){
		var selections = $("#CLRuleTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onUnselectAll:function(){
		 var selections = $("#CLRuleTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		}
	}).datagrid("hideColumn","id").datagrid("columnMoving");
	// 2016-4-22 by les  hideColumn方法里面有列顺序记忆，列显示隐藏记忆
	$('#CLRuleTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
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
function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">有效</span>';
	}else if(value==2){
		return '<span style="color:blue; " >失效</span>';
	}
}
function applyToType(value,rowData,rowIndex){
	if(value==1){
		return '整车规则';
	}else if(value==2){
		return '零担规则';
	}else if(value==3){
		return '司机运费规则';
	}
}
function applyToOtherType(value,rowData,rowIndex){
	if(value==1){
		return '体积';
	}else if(value==2){
		return '重量';
	}
}

/*** 修改*/
function ableEditButton(selections){
	for(var i = 0;i<selections.length;i++){
	   for(var j=0;j<selections.length;j++){
		  if( selections[j].status!=selections[i].status)
			  {	$("#EditLess").linkbutton('disable');
				$("#EditCarload").linkbutton('disable');
				$("#del").linkbutton('disable');
				$("#changeok").linkbutton('disable');
				$("#changeout").linkbutton('disable');
				return
			 }
	     }
		if((selections[i].status==1 )){
			$("#EditCarload").linkbutton('enable');
			$("#EditLess").linkbutton('enable');
			$("#del").linkbutton('disable');
			$("#changeok").linkbutton('disable');
			$("#changeout").linkbutton('enable');
		}else{
			$("#EditCarload").linkbutton('disable');
			$("#EditLess").linkbutton('disable');
			$("#del").linkbutton('enable');
			$("#changeok").linkbutton('enable');
			$("#changeout").linkbutton('disable');
			return
		}
	}
}
 
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'"
			style="height: 100px; border: 0px;">
			<form id="searchRuleForm" method="post">
				<table style="margin: 0 auto; margin-top: 0px;">
					<tr>
						<td class="m-info-title">状态:</td>
						<td><select id="List_status" name="status"
							class="easyui-combobox" style="width: 100px; height: 20px;">
								<option value="">全部</option>
								<option value="1">有效</option>
								<option value="2">失效</option>
						</select></td>
						<td class="m-info-title">规则类型:</td>
						<td><select id="List_type" name="type"
							class="easyui-combobox" style="width: 100px; height: 20px;">
								<option value="">全部</option>
								<option value="1">整车规则</option>
								<option value="2">零担规则</option>
								<option value="3">计费规则</option>
						</select></td>
						<td class="m-info-title">车辆规格:</td>
						<td><select id="List_carSpecId" name="carSpecId">
								<option value="">全部</option>
						</select> <a id="searchRuleButton" href="javascript:;"
							class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
			</form>


		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="CLRuleTable"></table>
		</div>
		<div id="createCarloadRule_Window"
			style="margin: 0 auto; overflow: hidden;"></div>
		<div id="createLessRule_Window"
			style="margin: 0 auto; overflow: hidden;"></div>
		<div id="createBillRule_Window"
			style="margin: 0 auto; overflow: hidden;"></div>
	</div>
</body>
</html>