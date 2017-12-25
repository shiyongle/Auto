<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>增值服务</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css"	type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"	rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js"	type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#addserviceTable').datagrid({
			title : '增值服务',
			loadMsg : '数据装载中......',
			url : '${ctx}//load.do',
			fit : true,
			fitColumns : false,
			nowrap : true,
			striped : true, //为true,显示斑马线效果。
			multiSort : true, //定义是否允许多列排序
			remoteSort : true, //定义从服务器对数据进行排序
			pagination : true, //为true,则底部显示分页工具栏
			rownumbers : true, //为true,则显示一个行号列
			frozenColumns : [ [ {field : 'id1',checkbox : true}] ],
			pageSize : 20,//每页显示的记录条数，默认为10 
			pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
			toolbar : [
			          {	
				 			id : "add",
							text : '新建',
							iconCls : 'icon-add',
							handler : function() {
								$("#createWindow").window({
									title : "新建",
									href : '${ctx}//add.do',
									width : 480,
									height :180,
									modal : true
								});
							}
						},'-',
						{
							id:"Edit",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#addserviceTable").datagrid('getSelections');
							     if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							     }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								 }else{
							    	 $("#createWindow").window({
										title : "修改",
										href : '${ctx}//edit.do?id='+rows[0].faddserviceId,
										width :540,
									    height :250,
										modal : true
									});
								} 
							}
						},'-',{
							id:"changeType",
							text:'改变状态',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#addserviceTable").datagrid('getSelections');
							     if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							     }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								 }else{
									 $.messager.confirm('提示', '你确定要改状态', function(r){
										if (r){
											$.ajax({
												url:'',
												type:'post',
												dataType:'json',
												success:function(res){
													if(res == 'success' ){
														$.messager.alert('提示', '操作成功', 'info', function() {
															$("#addserviceTable").datagrid('reload');
														});
													}else{
														$.messager.alert('提示', '操作失败', 'info');
													}
												}
											});
										}	
									});
								} 
							}
						}],
			columns : [ [ 
					{title : 'ID',field : 'faddserviceId',hidden : true,rowspan : 1,align : 'center'},
					{title : '增值服务名称',field : 'faddserviceName',rowspan : 1,align : 'center',width:'160'},					
				    {title : '增值服务价格',field : 'faddservicePrice',rowspan : 1,align : 'center',width : '160'},
				    {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '110',formatter:applyToRole},
				    {title : '状态',field : 'fstatus',rowspan : 1,align : 'center',width : '160'}, 
				    {title : '对应规则类型',field : 'ftype',rowspan : 1,align : 'center',width : '160'}
		     ] ]
		});
		
		$('#addserviceTable').datagrid('getPager').pagination({
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
</script>
</head>
<body>
 	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
			<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			</div>
			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  ><table id="addserviceTable"> </table></div>
			<div id="createWindow"  style="margin: 0 auto;" ></div>
	</div>
</body>
</html>