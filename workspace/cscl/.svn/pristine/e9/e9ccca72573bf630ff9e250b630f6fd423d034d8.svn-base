<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#menuManageTable').datagrid({
		title : '菜单管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/menu/load.do',
		fit:true,//自适应大小
		fitColumns:false,//True会自动扩大或缩小列的尺寸以适应表格的宽度并且防止水平滚动
		nowrap : true,//数据显示为一行
		striped : true,    //为true,显示斑马线效果，奇偶行使用不同背景色
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [{
					id:"CreateMenu",
					text : '新建',
					iconCls : 'icon-add',
					handler : function() {
							$("#createMenu_Window").window({
								title : "新建",
								href : '${ctx}/menu/add.do',
								width : 250,
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
							id:"EditMenu",
							text:'修改',
							iconCls : "icon-edit",
							handler : function() {
								var rows = $("#menuManageTable").datagrid('getSelections');
								if(rows.length<=0){
							    	 $.messager.alert('提示', '请选择记录！', 'info');
							    }else if (rows.length > 1) {
										$.messager.alert('提示', '每次只能选择一条记录！', 'info');
								}else{
								 
							    	 $("#createMenu_Window").window({
										title : "修改",
										href : '${ctx}/menu/edit.do?id='+rows[0].fid,
										width : 250,
									    height :200,
										modal : true
									});
									}
								} 
					},'-', {
						id : "DelMenu",
						text : '删除',
						iconCls : 'icon-remove',
						handler : function() {
							var rows = $("#menuManageTable").datagrid('getSelections');
							 if(rows.length<=0){
					    	 	$.messager.alert('提示', '请选择记录！', 'info');
						     }else{
							 	$.ajax({
							 			type : "POST",
										url :'${ctx}/menu/del.do?'+toStrId(rows),
										success : function(response) {
											if (response == "success") {
						                        $.messager.alert('提示', '操作成功', 'info', function() {
						                        	$('#menuManageTable').datagrid('reload');
						                        });
						                    } else {
						                        $.messager.alert('提示', '操作失败');
						                    }
										}	
									});
							 }
						}
			          }
			          ],
		columns : [[
					{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
					{title : '父级菜单',field : 'fparentMenuName',rowspan : 1,align : 'center',width : '200'},
		            {title : '菜单名称',field : 'fname',rowspan : 1,align : 'center',width : '200'},
		            {title : '菜单地址',field : 'furl',rowspan : 1,align : 'center',width : '300'}		            
		           ]],
	});
	$('#menuManageTable').datagrid('getPager').pagination({
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
				paramStr += 'ids=' + rows[i].fid;
			} else {
				paramStr += "&ids=" + rows[i].fid;
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

			<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'"  >
				<table id="menuManageTable"></table>
			</div>
			<div id="createMenu_Window" style="margin: 0 auto; overflow:hidden;"></div>
	</div>
</body>
</html>