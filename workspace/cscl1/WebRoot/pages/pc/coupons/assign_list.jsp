<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指派好运券管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/datagrid-detailview.js" type="text/javascript" ></script>
<script src="${ctx}/pages/pc/js/messageTips.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#searchCouponsAssignTableButton").click(function() {
		$("#CLCouponsAssignTable").datagrid('load', JSON.parse(toJOSNStr($("#searchCouponsAssignForm").serializeArray())));
	});
	
	$("#CLCouponsAssignTable").datagrid({
		title : '好运券管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/couponsAssign/load.do',
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
					id:"NEWCouponsAssignButton",
					text : '新建指派券',
					iconCls : 'icon-add',
					handler : function() {
						 	$("#createCouponsAssignWindow").window({
								title : "新建指派好运券",
								href : '${ctx}/couponsAssign/create.do?',
								width : 500,
							    height :280,
							    draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								inline:false,
								modal : true
							});
				    }
				  },'-', {
						id : "delAssignButton",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#CLCouponsAssignTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/coupons/del.do?'+toStrId(rows),
										dataType:'json',
										success : function(response) {
											if (response.success == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$("#CLCouponsAssignTable").datagrid('reload');
						                        });
						                    } else {
						                        $.messager.alert('提示', response.msg);
						                    }
										}	
								});
							 }
						}
			     },'-', {
						id : "couponsAssignButton",
						text : '指派',
						iconCls : 'icon-tip',
						handler : function() {
							var rows = $("#CLCouponsAssignTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else if(rows.length>1){
						     	$.messager.alert('提示', '请选择1条记录！', 'info');
						     }else{
						     	$("#createCouponsAssignWindow").window({
										title : "指派好运券",
										href : '${ctx}/couponsAssign/assign.do?id='+rows[0].id,
										width : 280,
									    height :150,
									    draggable:true,
										resizable:false,
										maximizable:false,
										minimizable:false,
										collapsible:false,
										inline:false,
										modal : true
								});
						     }
					    }
			     }, '-', {
							id : "effectiveAssignButton",
							text : '有效',
							disabled : true,
							iconCls : 'icon-mini-edit',
							handler : function() {
								var rows = $("#CLCouponsAssignTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else{
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/coupons/effective.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$("#CLCouponsAssignTable").datagrid('reload');
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
								id : "unEffectiveAssignButton",
								text : '失效',
								disabled : true,
								iconCls : 'icon-mini-edit',
								handler : function() {
									var rows = $("#CLCouponsAssignTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else{
								    	 $.messager.confirm('提示','该操作会回收所有已分配给客户的好运劵，请确认!',function(r){
								    		 if(r){
								    			 $.ajax({
											 			type : "POST",
														url : '${ctx}/coupons/unEffective.do?'+toStrId(rows),
														success : function(response) {
															if (response == "success") {
										                        $.messager.alert('提示', '操作成功', 'info', function() {
										                        	$("#CLCouponsAssignTable").datagrid('reload');
										                        });
										                    } 
										                    else {
										                    	  $.messager.alert('提示', '操作失败');
										                    }
														}	
													});
								    		 }
								    	 });
									 	
									 }
								}
		}],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '优惠券类型',field : 'type',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
		            {title : '开始时间',field : 'startTimeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '结束时间',field : 'endTimeString',rowspan : 1,align : 'center',width : '110'},
		            {title : '面额',field : 'dollars',rowspan : 1,align : 'center',width : '100'},
		            {title : '满N元可用',field : 'compareDollars',rowspan : 1,align : 'center',width : '100'},
		            {title : '是否有效',field : 'isEffective',rowspan : 1,align : 'center',width : '100',formatter:applyToIsEffective},
		            {title : '描述',field : 'describes',rowspan : 1,align : 'center',width : '100'},
		            {title : '创建人',field : 'creatorName',rowspan : 1,align : 'center',width : '100'},
		            {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '130'}
		           ]],
		onLoadSuccess : function(data) {
				$('#CLCouponsAssignTable').datagrid('doCellTip',{cls:{'background-color':'#FFFF99'},delay:500});   
		},
		onSelect : function(rowIndex, rowData) {
				var selections = $("#CLCouponsAssignTable").datagrid('getSelections');
				multipleButtonS(selections,"isEffective",0,$("#effectiveAssignButton"));//有效
				multipleButtonS(selections,"isEffective",1,$("#unEffectiveAssignButton"));//失效
			},
			onUnselect : function(rowIndex, rowData) {
				var selections = $("#CLCouponsAssignTable").datagrid('getSelections');
				multipleButtonS(selections,"isEffective",0,$("#effectiveAssignButton"));//有效
				multipleButtonS(selections,"isEffective",1,$("#unEffectiveAssignButton"));//失效
			},
			onSelectAll:function(){
				var selections = $("#CLCouponsAssignTable").datagrid('getSelections');
				multipleButtonS(selections,"isEffective",0,$("#effectiveAssignButton"));//有效
				multipleButtonS(selections,"isEffective",1,$("#unEffectiveAssignButton"));//失效
			},
			onUnselectAll:function(){
				var selections = $("#CLCouponsAssignTable").datagrid('getSelections');
				multipleButtonS(selections,"isEffective",0,$("#effectiveAssignButton"));//有效
				multipleButtonS(selections,"isEffective",1,$("#unEffectiveAssignButton"));//失效
			}
	});
	
	$("#CLCouponsAssignTable").datagrid('getPager').pagination({
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

//datagird列的格式化
function applyToType(value,rowData,rowIndex){
	if(value==3){
		return '<span style="color:red;">指派优惠券</span>';
	}
}
//datagird列的格式化
function applyToIsEffective(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:green;">有效</span>';
	}else{
		return '<span style="color:#DDDDDD;">失效</span>';
	}
}
//批量Id
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

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="bCouponsAssign-left: 0px; bCouponsAssign-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; bCouponsAssign: 0px;">
			       <form id="searchCouponsAssignForm" method="post">
						<table style="margin: 0 auto; margin-top: 20px;">
							<tr>
								<td class="m-info-title">活动时间：</td>
								<td >
									<input type="text" id="startTime2" class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTime2\')}'})" name="startTime" value="" /> -
									<input type="text" id="endTime2" class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime2\')}'})" name="endTime" value="" />
								</td>
								<td class="m-info-title">是否有效：</td>
								<td>
									<select id="isEffective2" class="easyui-combobox" data-options="editable:false" style="width: 80px; height: 27px;" name="isEffective">
										<option value="">请选择</option>
										<option value="1">有效</option>
										<option value="0">失效</option>
									</select>
									<a id="searchCouponsAssignTableButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			</div>
			<div data-options="region:'center',title:'',bodyCls:'MnolrbbCouponsAssign'"  >
				<table id="CLCouponsAssignTable"></table>
			</div>
			<div id="createCouponsAssignWindow"  style="margin: 0 auto;" ></div>
	</div>
</body>
</html>