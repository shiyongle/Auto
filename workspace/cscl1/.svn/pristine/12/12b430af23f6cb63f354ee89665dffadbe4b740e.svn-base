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
	$('#CLUserTable').datagrid({
		title : '用户管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/user/load.do',
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
		toolbar :[
					{
						id : 'permissionUserButton',
						text : '权限分配',
						iconCls : 'icon-edit',
						handler : function() {
							var rows = $("#CLUserTable").datagrid('getSelections');
							if(rows.length<=0)
								{
									$.messager.alert('提示', '请选择一个用户！', 'info');
								}
							else if (rows.length > 1) {
								$.messager.alert('提示', '每次只能选择一个用户！', 'info');
								}
							else{
								$("#user_Window").window({
								title : "权限分配",
								href : '${ctx}/menu/userroot.do?fuserRoleId='+rows[0].id,
								width : 600,
								height : 540,
								draggable:true,//是否拖动
								resizable:false,//是否可以拉动大小
								maximizable:false,//最大化
								minimizable:false,//最小化
								collapsible:false,//折叠按钮是否显示
								modal : true//模式化窗口
		    					});
							}
          				}
     				},{
     					id : "editUserButton",
     					text : '修改银行信息',
     					iconCls : 'icon-edit',
     					handler : function() {
     						var rows = $("#CLUserTable").datagrid('getSelections');
     						 if(rows.length<=0){
     				    	 	$.messager.alert('提示', '请选择记录！', 'info');
     					     }else if (rows.length > 1) {
     							$.messager.alert('提示', '每次只能选择一条记录！', 'info');
     						 }else if (rows[0].roleId!=2) {
     							$.messager.alert('提示', '只有车主可以更改银行账户信息！', 'info');
     						 }
     					     else{
     							$("#user_Window").window({
     								title : "修改消息",
     								href : '${ctx}/user/editAccount.do?id='+rows[0].id,
     								width : 400,
     							    height :300,
     								modal : true
     							});
     						 };
     					}
     				},'-',{
     					id : "setMonthPayButton",
     					text : '设置/取消月结',
     					iconCls : 'icon-tip',
     					handler : function() {
     						var rows = $("#CLUserTable").datagrid('getSelections');
     						 if(rows.length<=0){
     				    	 	$.messager.alert('提示', '请选择记录！', 'info');
     					     }else if (rows.length > 1) {
     							$.messager.alert('提示', '每次只能选择一条记录！', 'info');
     						 }else if (rows[0].roleId!=1) {
     							$.messager.alert('提示', '只有货主可以设置月结支付！', 'info');
     						 }
     					     else{
     							$.ajax({
    					 			type : "POST",
    								url : '${ctx}/user/updateUserProtocol.do?id='+rows[0].id,
    								success : function(response) {
    									if (response == "success") {
    				                        $.messager.alert('提示', '操作成功', 'info', function() {
    				                        	$('#CLUserTable').datagrid('reload');
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
				{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '角色类型',field : 'roleId',rowspan : 1,align : 'center',width : '120',formatter:applyToRole},
		        {title : '用户名',field : 'fname',rowspan : 1,align : 'center',width : '180'},
		        {title : '手机号',field : 'vmiUserPhone',rowspan : 1,align : 'center',width : '180'},
		        {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '250'},
		        {title : '支付宝帐号',field : 'alipayId',rowspan : 1,align : 'center',width : '230'},
		        {title : '银行卡帐号',field : 'bankAccount',rowspan : 1,align : 'center',width : '230'},
		        {title : '开户行',field : 'bankName',rowspan : 1,align : 'center',width : '120'},
		        {title : '开户行所在地',field : 'bankAddress',rowspan : 1,align : 'center',width : '260'},
		        {title : '是否月结',field : 'protocol',rowspan : 1,align : 'center',width : '70',formatter:applyToProtocol}		        
		        ]]
	});
	
    /*toolbar上方工具栏 by ht 2016年4月24日15:06:30*/
  	window.toolbarSearch("searchStatementForm","vmiUserName","vmiUserName","searchButton",0);
    $('#searchButton').click(function(){
    	$('#CLUserTable').datagrid('load', JSON.parse(toJOSNStr($("#searchStatementForm").serializeArray())));
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
  	/**toolbar上方工具栏结束*/
  	
	$('#CLUserTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	if('${userRoleId}' != 2283){
		$('#permissionUserButton').css('display','none');
	}
});
//角色类型列渲染
function applyToRole(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:blue;">货主</span>';
	}else if(value==2){
		return '<span style="color:red;font-weight:bold;">车主</span>';
	}else if(value==3){
		return '<span style="color:red;">平台</span>';
	}
}

//是否协议列渲染
function applyToProtocol(value,rowData,rowIndex){
	if(!value){
		return '否';
	}else if(value){
		return '<span style="color:red;">是</span>';
	}
}

</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		<table id="CLUserTable"></table>
	</div>
	<div id="user_Window" style="margin: 0 auto; overflow:hidden;"></div>
</body>


</html>