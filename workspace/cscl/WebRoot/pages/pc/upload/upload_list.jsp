<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>

<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
</head>
<script type="text/javascript">
$(document).ready(function() {
	//查询按钮点击事件
	$('#CLUploadTable').datagrid({
		title : 'APP首页图片管理',
		loadMsg : '数据装载中......',
		url : '${ctx}/upload/list.do',
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
		toolbar : [{
					id : "addLucky",
					text : '上传优惠券图片',
					iconCls : 'icon-add',
					handler : function() { 
						$("#createWindow").window({
							title : "上传优惠券图片",
							href : '${ctx}/upload/addUpView.do',
							width : 625,
							height :600,
							modal : true
						});
					}
				}, '-',{
					id : "add",
					text : '批量上传',
					iconCls : 'icon-add',
					handler : function() { 
						$("#createWindow").window({
							title : "批量上传",
							href : '${ctx}/upload/add.do',
							width : 625,
							height :600,
							modal : true
							 
						});
					}
					}, '-',{
						id : "add",
						text : '单个上传',
						iconCls : 'icon-add',
						handler : function() { 
							$("#createWindow").window({
								title : "单个上传",
								href : '${ctx}/upload/addOne.do',
								width : 625,
								height :600,
								modal : true
								 
							});
						}
					}, '-', {
						id : "del",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $('#CLUploadTable').datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.messager.confirm("操作", "您确定要删除吗？",function(r){
							 		if(r){
							 			$.ajax({
								 			type : "POST",
											url : '${ctx}/upload/deleteImg.do?'+toStrId(rows),
											success : function(response) {
												if (response == "success") {
							                        $.messager.alert('提示', '操作成功', 'info', function() {
							                        	$('#CLUploadTable').datagrid('reload');
							                        });
							                    } else {
							                        $.messager.alert('提示', '操作失败');
							                    }
											}	
										});
							 		}
							 	});
							 }
						}
	} ],
		columns : [ [ 
				{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center'},
			    {title : '上传文件名称',field : 'name',rowspan : 1,align : 'center',width : '200'},
			    {title : '路径',field : 'url',rowspan : 1,align : 'center',width : '600'},
			    {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '300'}, 
			    {title : 'APP首页类型',field : 'type',rowspan : 1,align : 'center',width : '100'},
			    {title :'备注',field : 'remark' ,rowspan :1,align : 'center' ,width :'300'}
	     ] ]
	});
	$('#CLUploadTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});

});

 

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

</script>
<body>
     <div id="CLUploadTable"></div>
     <div id="createWindow"></div>
</body>
</html>
