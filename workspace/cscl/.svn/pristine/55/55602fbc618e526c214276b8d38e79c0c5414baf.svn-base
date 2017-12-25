<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.mincc.js" type="text/javascript"></script>
<style type="text/css">
#createWindow #Div0 {width: 100%;height: 663px;}
#createWindow #Div1 {float: left;width: 460;height: 662px;}
#createWindow #Div2 {float: left;width: 216px;height: 662px;}

</style>
<script type="text/javascript">
$(document).ready(function() {
	//查询按钮点击事件
	$("#searchMsgButton").click(function() {
		$('#CLMessageTable').datagrid('load', JSON.parse(toJOSNStr($("#searchMsgForm").serializeArray())));
	});
	
	$("#userid").combobox({
		url : "${ctx}/select/getAll.do",
		editable : true,
		width:140,
		mode: 'local',
		valueField: 'optionName',//提交值
		textField: 'optionName',//显示值
		filter:searchItem
	});
	
	$('#CLMessageTable').datagrid({
		title : '消息管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/message/load.do',
		fit:true,
		fitColumns:false,
		nowrap : true,
		striped : true,    //为true,显示斑马线效果。
		multiSort : true,  //定义是否允许多列排序
		remoteSort : true, //定义从服务器对数据进行排序
		pagination : true, //为true,则底部显示分页工具栏
		rownumbers : true, //为true,则显示一个行号列
// 		singleSelect:true,//只允许选择一行
		frozenColumns : [[{field : 'id1',checkbox : true}]],
		pageSize : 20,//每页显示的记录条数，默认为10 
		pageList : [ 10, 20, 50, 100 ],//可以设置每页记录条数的列表 
		toolbar : [ {
			id : "createMessageButton",
			text : '新建消息',
			iconCls : 'icon-add',
			handler : function() {
				$("#messageWindow").window({
					title : "新建消息",
					collapsible : false,
					minimizable : false,
					maximizable : false,
					resizable : false,
					shadow : false,
					href : '${ctx}/messagesend/create.do',
					width : 920,
					height : 650,
					modal : true
				});
	          }
		},'-',{
			id : "deleteMessageButton",
			text : '删除消息',
			iconCls : 'icon-remove',
			handler : function() {
				var rows = $("#CLMessageTable").datagrid('getSelections');
				 if(rows.length<=0){
		    	 	$.messager.alert('提示', '请选择记录！', 'info');
			     }else{
				 	$.ajax({
				 			type : "POST",
							url : '${ctx}/messagesend/delete.do?'+toStrId(rows),
							success : function(response) {
								if (response == "success") {
			                        $.messager.alert('提示', '操作成功', 'info', function() {
			                        	$("#CLMessageTable").datagrid('reload');
			                        });
			                    } else {
			                        $.messager.alert('提示', '操作失败');
			                    };
							}	
						});
				 };
			}
		},'-',{
			id : "updateMessageButton",
			text : '修改消息',
			iconCls : 'icon-edit',
			handler : function() {
				var rows = $("#CLMessageTable").datagrid('getSelections');
				if(rows.length<=0){
			    	 $.messager.alert('提示', '请选择记录！', 'info');
			    }else if (rows.length > 1) {
						$.messager.alert('提示', '每次只能选择一条记录！', 'info');
				}else{
					$("#messageWindow").window({
						title : "修改消息",
						href : '${ctx}/message/edit.do?id='+rows[0].id,
						width : 910,
					    height :550,
						modal : true
					});
				}
			}
		}],
		columns : [[
			{title : 'ID',field : 'id',hidden : true,rowspan : 1,align : 'center',sortable:true},
			{title : 'UserId',field : 'creatorId',hidden : true,rowspan : 1,align : 'center',sortable:true},
			{title : '类型',field : 'type',rowspan : 1,align : 'center',width : '100',formatter:applyToType},
			{title : '标题',field : 'title',rowspan : 1,align : 'center',width : '150'},
			{title : '创建人',field : 'creatorName',rowspan : 1,align : 'center',width : '100'},
			{title : '内容',field : 'content',rowspan : 1,align : 'center',width : '1042',formatter:applyToXmp},
			{title : '接收人',field : 'receiverName',rowspan : 1,align : 'center',width : '100'},
			{title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '150'}
		]]
	});
	
	$('#CLMessageTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
});


function applyToXmp(value,rowData,rowIndex){
	return "<xmp>"+value+"</xmp>";
}
function applyToType(value,rowData,rowIndex){
	  if(value==1){
		return '货主推送';
	}else if(value==2){
		return '司机推送';
	}else if(value==3){
		return '货主';
	}else if(value==4){
		return '司机';
	}else if(value==5){
		return '货主和司机';
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
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
		 <div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			<form id="searchMsgForm" method="post">
				<table style="margin: 0 auto; margin-top: 0px;">
					<tr>
						<td class="m-info-title">类型:</td>
						<td>
							<select  id="List_status"  name="type" class="easyui-combobox" style="width: 100px; height: 20px;"  >
								<option value="-1">请选择</option>
								<option value="3">货主消息</option>
								<option value="4">司机消息</option>
								<option value="1">货主推送</option>
								<option value="2">司机推送</option>
							</select>
						</td> 
						<td class="m-info-title">接收人：</td>
							<td>
							<select id="userid" name="fusername" panelHeight="200">
								<option value="">请选择</option>
						</select>
<!-- 							<input type="text" id="searchReceiver"  class="easyui-textbox" name="searchReceiver"  style="width:200px;"/> -->
							<a id="searchMsgButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>
						</td>	
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="CLMessageTable" style="text-align:'center';"></table>
		</div>
		<div id="messageWindow" style="margin: 0 auto;"></div>
	</div>
</body>
</html>