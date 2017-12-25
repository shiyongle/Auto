<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>规则管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
 
	//查询按钮点击事件
	$("#searchRuleButton").click(function() {
		$('#CLCouponRuleTable').datagrid('load', JSON.parse(toJOSNStr($("#searchRuleForm").serializeArray())));
	});
	$('#CLCouponRuleTable').datagrid({
		title : '规则管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/couponRule/load.do',
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
							$("#createCouponRule_Window").window({
								title : "新建",
								href : '${ctx}/couponRule/createLoad.do',
								width : 300,
								height :200,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				          }
				     },'-',{
							id:"EditCouponRuleload",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#CLCouponRuleTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
								 
							    	 $("#createCouponRule_Window").window({
										title : "修改",
										href : '${ctx}/couponRule/editCouponLoad.do?id='+rows[0].id,
										width : 300,
									    height :200,
										modal : true
									});
									}
								} 
					},'-', {
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#CLCouponRuleTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else if(rows[0].isEffective==0){
							 	$.ajax({
							 			type : "POST",
										url : '${ctx}/couponRule/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#CLCouponRuleTable').datagrid('reload');
						                        });
						                    } else {
						                        $.messager.alert('提示', '操作失败');
						                    }
										}	
									});
							 }
						     else{
						    	 $.messager.alert('提示', '请选择失效记录!');
						     }
						}
			          }, '-', {
							id : "changeok",
							text : '有效',
							iconCls : 'icon-mini-edit',
							handler : function() {
								var rows = $("#CLCouponRuleTable").datagrid('getSelections');
								 if(rows.length<=0){
						    	 	$.messager.alert('提示', '请选择记录！', 'info');
							     }else if(rows[0].isEffective==0){
								 	$.ajax({
								 			type : "POST",
											url : '${ctx}/couponRule/change.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#CLCouponRuleTable').datagrid('reload');
							                        });
							                    } 
							                    else {
							                    	  $.messager.alert('提示', '操作失败');
							                    }
											}	
										});}
							     else{
							    	 $.messager.alert('提示', '请选择失效信息');
							     }
								 }
				          }, '-', {
								id : "changeout",
								text : '失效',
								iconCls : 'icon-mini-edit',
								handler : function() {
									var rows = $("#CLCouponRuleTable").datagrid('getSelections');
									 if(rows.length<=0){
							    	 	$.messager.alert('提示', '请选择记录！', 'info');
								     }else if(rows[0].isEffective==1)
								        {
									 	$.ajax({
									 			type : "POST",
												url : '${ctx}/couponRule/change.do?'+toStrId(rows),
												success : function(response) {
													if (response == "success") {
								                        $.messager.alert('提示', '操作成功', 'info', function() {
								                        	$('#CLCouponRuleTable').datagrid('reload');
								                        });
								                    } 
								                    else {
								                    	  $.messager.alert('提示', '操作失败');
								                    }
												}	
											});
									 }
								     else{
								    	 $.messager.alert('提示', '请选择有效信息'); 
								     }
								}
					          }
			          ],
		columns : [[
					{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		            {title : '面额',field : 'dollars',rowspan : 1,align : 'center',width : '200'},
		            {title : '比面额',field : 'compareDollars',rowspan : 1,align : 'center',width : '250'},
		            {title : '消费面额',field : 'consumption',rowspan : 1,align : 'center',width : '200'},
		            {title : '是否失效',field : 'isEffective',rowspan : 1,align : 'center',width : '200',formatter:applyToStatus},
		            {title : '时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '200'},
		            {title : '创建人',field : 'vminame',rowspan : 1,align : 'center',width : '200'}
		               ]],
	});
	$('#CLCouponRuleTable').datagrid('getPager').pagination({
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
	}else if(value==0){
		return '<span style="color:blue; " >失效</span>';
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
								<td class="m-info-title">是否有效:</td>
								<td>
									<select id="isEffective" class="easyui-combobox" style="width: 100px; height: 20px;" name="isEffective">
										<option value="-1">全部</option>
										<option value="0">失效</option>
										<option value="1">有效</option>
									</select>
									<a id="searchRuleButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
								</td>
							</tr>
						</table>
					</form>
			
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="CLCouponRuleTable"></table>
			</div>
			<div id="createCouponRule_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>