<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('#userDiaryTable').datagrid({
		title : '用户管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/user/loadLog.do',
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
		columns : [[
				//{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '用户名',field : 'userName',rowspan : 1,align : 'center',width : '180'},
		        {title : '登录时间',field : 'floginTime',rowspan : 1,align : 'center',width : '180',formatter:fbilldateType},
		        {title : '最后操作时间',field : 'flastOperateTime',rowspan : 1,align : 'center',width : '250',formatter:fbilldateType}        
		        ]]
	});
	$('#userDiaryTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	// 时间戳转换 
	function fbilldateType(value,rowData,rowIndex){
			var d = new Date(value);    //根据时间戳生成的时间对象
			var date = (d.getFullYear()) + "-" + toDou(d.getMonth() + 1)+"-"+toDou(d.getDate())+" "+toDou(d.getHours())+":"+toDou(d.getMinutes())+":"+toDou(d.getSeconds());
			return date;
	}
	//时间补零
	function toDou(n){
		return n<10?"0"+n:""+n;
	}
});
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<table id="userDiaryTable"></table>
	</div>
</body>


</html>