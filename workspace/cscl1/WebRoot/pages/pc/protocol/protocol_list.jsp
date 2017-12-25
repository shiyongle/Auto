<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>协议用车计费规则管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#search_userRoleId").combobox({
		url : "${ctx}/select/getByRoleId.do",
		editable : true,
		width:100,
		mode: 'local',
		valueField: 'optionName',
		textField: 'optionName',
		filter:searchItem
	  
	});
	
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
		$('#CLProtocolTable').datagrid('load', JSON.parse(toJOSNStr($("#searchRuleForm").serializeArray())));
	});
	$('#CLProtocolTable').datagrid({
		title : '协议用车规则管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/protocol/load.do',
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
					text : '新建整车规则',
					iconCls : 'icon-add',
					handler : function() {
							$("#createCarloadRule_Window").window({
								title : "新建整车规则",
								href : '${ctx}/protocol/addCarRule.do',
								width : 540,
								height : 250,
								width : 600,
								height : 340,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				          }
			          },{
							text : '新建零担规则',
							iconCls : 'icon-add',
							handler : function() {
									$("#createLessRule_Window").window({
										title : "新建零担规则",
										href : '${ctx}/protocol/addLessRule.do',
										width : 600,
										height : 330,
										draggable:true,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										modal : true
								    });
						          }
			         	 },'-',{
								text : '新建包天规则',
								iconCls : 'icon-add',
								handler : function() {
										$("#createDayRule_Window").window({
											title : "新建包天规则",
											href : '${ctx}/protocol/addDayRule.do',
											width :  540,
											height : 180,
											draggable:true,
											resizable:false,
											maximizable:false,
											minimizable:false,
											collapsible:false,
											modal : true
									    });
							          }
				         	},'-',{
							id:"EditCarRule",
							text:'修改规则',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#CLProtocolTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择规则记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
									if(rows[0].type==1){
							    	 $("#createCarloadRule_Window").window({
										title : "修改整车规则",
										href : '${ctx}/protocol/editCarRule.do?id='+rows[0].id,
										width : 600,
									    height :370,
										modal : true
									});
									}
									else if(rows[0].type==2){
								    	 $("#createLessRule_Window").window({
											title : "修改零担规则",
											href : '${ctx}/protocol/editLessRule.do?id='+rows[0].id,
											width : 600,
										    height :370,
											modal : true
										});
										}
									else if(rows[0].type==3){
								    	 $("#createDayRule_Window").window({
											title : "修改包天规则",
											href : '${ctx}/protocol/editDayRule.do?id='+rows[0].id,
											width : 540,
										    height :170,
											modal : true
										});
										}
									else{
									  $.messager.alert('提示', '请选择整车规则记录！', 'info');
									}
								} 
							}
					},'-', {
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#CLProtocolTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/protocol/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#CLProtocolTable').datagrid('reload');
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
								var rows = $("#CLProtocolTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/protocol/change.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#CLProtocolTable').datagrid('reload');
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
									var rows = $("#CLProtocolTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
									 	$.ajax({
									 			type : "POST",
												url : '${ctx}/protocol/change.do?'+toStrId(rows),
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#CLProtocolTable').datagrid('reload');
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
					{title : '客户名称',field : 'userName',rowspan : 1,align : 'center',width : '100'},
					{title : '客户手机号',field : 'userPhone',rowspan : 1,align : 'center',width : '100'},
		            {title : '协议名称',field : 'fname',rowspan : 1,align : 'center',width : '100'},
		            {title : '协议类型',field : 'type',rowspan : 1,align : 'center',width : '150',formatter:applyToType},
		            {title : '车辆规格',field : 'carSpecName',rowspan : 1,align : 'center',width : '100'},
		            {title : '保底价格',field : 'startPrice',rowspan : 1,align : 'center',width : '100'},
		            {title : '保底公里',field : 'startKilometre',rowspan : 1,align : 'center',width : '100'},
		            {title : '公里单价(元)',field : 'outKilometre',rowspan : 1,align : 'center',width : '100'},
		            {title : ' 装货费(元)',field : 'fadd_service_1',rowspan : 1,align : 'center',width : '100'},
		            {title : '卸货费(元)',field : 'fadd_service_2',rowspan : 1,align : 'center',width : '100'},
		            {title : '状态',field : 'status',rowspan : 1,align : 'center',width : '100',formatter:applyToStatus},
		            {title : '备注',field : 'pubRemark',rowspan : 1,align : 'center',width : '200'},
		            {title : '零担种类',field : 'otherTypeName',rowspan : 1,align : 'center',width : '200'},
		            {title : '包天价格',field : 'timePrice',rowspan : 1,align : 'center',width : '100'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '250'},
		            {title : '保底点数',field : 'fopint',rowspan : 1,align : 'center',width : '200'},
		            {title : '超出点数单价',field : 'foutopint',rowspan : 1,align : 'center',width : '200'},
		            {title : '折扣',field : 'fdiscount',rowspan : 1,align : 'center',width : '100',formatter:applyToDiscount}
		       
		            ]],
	   onSelect : function(rowIndex, rowData) {
	   	var selections = $("#CLProtocolTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onUnselect : function(rowIndex, rowData) {
			var selections = $("#CLProtocolTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onSelectAll:function(){
		var selections = $("#CLProtocolTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		},
		onUnselectAll:function(){
		 var selections = $("#CLProtocolTable").datagrid('getSelections');
			ableEditButton(selections);/***修改*/
		}
	});
	$('#CLProtocolTable').datagrid('getPager').pagination({
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
		return '<span style="color:red; ">生效</span>';
	}else if(value==0){
		return '<span style="color:blue; " >失效</span>';
	}
}
function applyToDiscount(value,rowData,rowIndex){
	if(value==0||value==null){
		var a = "无"
		return a;
	}
	var a=value*100+"%";  
	return a;
}
function applyToType(value,rowData,rowIndex){
	if(value==1){
		return '整车规则';
	}else if(value==2){
		return '零担规则';
	}else if(value==3){
		return '包天规则';
	}
}
function applyToPrice(value,rowData,rowIndex){
	if(value==1){
		return '保底价格';
	}else if(value==2){
		return '保底件数';
	}else if(value==3){
		return '包车';
	}
}
function applyToOtherType(value,rowData,rowIndex){
	if(value==1){
		return '件';
	}else if(value==2){
		return '托';
	}
	else if(value==3){
		return '面积';
	}
	else if(value==4){
		return '体积';
	}
	else if(value==5){
		return '重量';
	}
}

/*** 修改*/
function ableEditButton(selections){
	for(var i = 0;i<selections.length;i++){
	   for(var j=0;j<selections.length;j++){
		  if( selections[j].status!=selections[i].status)
			  {	$("#EditCarRule").linkbutton('disable');
				$("#EditLessRule").linkbutton('disable');
				$("#EditDayRule").linkbutton('disable');
				$("#del").linkbutton('disable');
				$("#changeok").linkbutton('disable');
				$("#changeout").linkbutton('disable');
				return
			 }
	     }
		if((selections[i].status==0 )){
			$("#EditCarRule").linkbutton('disable');
			$("#EditLessRule").linkbutton('disable');
			$("#EditDayRule").linkbutton('disable');
			$("#del").linkbutton('enable');
			$("#changeok").linkbutton('enable');
			$("#changeout").linkbutton('disable');
		}else{
			$("#EditCarRule").linkbutton('enable');
			$("#EditLessRule").linkbutton('enable');
			$("#EditDayRule").linkbutton('enable');
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
			       <form id="searchRuleForm" method="post">
						<table style="margin: 0 auto; margin-top: 0px;">
							<tr>						
								<td class="m-info-title">客户名称:</td>
								<td>
									<select id="search_userRoleId" name="userName" panelHeight="200">
										<option value="">请选择</option>
									</select>
									<a id="searchRuleButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			  
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLProtocolTable"></table>
			</div>
			<div id="createCarloadRule_Window" style="margin: 0 auto; overflow:hidden;"></div>
			<div id="createLessRule_Window" style="margin: 0 auto; overflow:hidden;"></div>
			<div id="createDayRule_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>