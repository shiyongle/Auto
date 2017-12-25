<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>收款管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {	
	/*付款人*/
	//下拉框渲染
		$("#fuserId").combobox({
			required : true,
			url : "${ctx}/select/getAllConsignor.do?couponsId="+"${recordId}",
			missingMessage : "请选择货主",
			validType : "comboRequired",
			invalidMessage : "请选择货主",
			editable : true,
			width:100,
			valueField: 'optionId',
			textField: 'optionName',
			filter:searchItem
		}).combobox('select', "-1");
// 	$("#fpayname").combobox({
// 		url : "",
// 		editable : true,
// 		width:140,
// 		mode: 'local',//加载后本地操作搜索
// 		valueField: '',//查询提交的值
// 		textField: '',//显示的值
// 		filter:function(q, row){
// 			var opts = $(this).combobox('options');
// 			return row[opts.textField].indexOf(q) == 0;
// 		}
// 	});
	
 	//查询按钮点击事件
	$("#searchReceivablesButton").click(function() {
		$('#receivablesTable').datagrid('load', JSON.parse(toJOSNStr($("#searchReceivablesForm").serializeArray())));
	});
	$('#receivablesTable').datagrid({
		title : '收款管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/receipt/load.do',
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
		//onHeaderContextMenu : gridHeaderMenu,//配合邮件菜单隐藏使用
		toolbar : [{
					text : '核销',
					iconCls : 'icon-ok',
					handler : function() {
						var rows = $("#receivablesTable").datagrid('getSelections');//获取选中的行
						if(rows.length<=0){
					    	 $.messager.alert('提示', '请选择记录！', 'info');
					    }else if(rows.length > 1) {
							$.messager.alert('提示', '每次只能选择一条记录！', 'info');
						}else if(rows[0].fremainAmount==0)
						{
							$.messager.alert('提示', '该款项可核销金额为零！', 'info');
						}else
						{
							$("#createReceivables_Window").window({
								title : "核销",
								href : '${ctx}/receipt/loadverify.do?fid='+rows[0].fid+'&fuserId='+rows[0].fuserId+'&famount='+rows[0].famount+'&fusername='+rows[0].fusername+'&fremainAmount='+rows[0].fremainAmount,
								width : 620,
								height : 520,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				        }
					}
		},'-',{
			text : '新建收款',
			iconCls : 'icon-add',
			handler : function() {
					$("#createReceivables_Window").window({
						title : "新建收款",
						href : '${ctx}/receipt/loadReceipt.do',
						width : 320,
						height : 220,
						draggable:true,
						resizable:false,
						maximizable:false,
						minimizable:false,
						collapsible:false,
						modal : true
				    });
			}
		},'-',{
			text : '开票',
			iconCls : 'icon-add',
			handler : function() {
					var rows = $("#receivablesTable").datagrid('getSelections');//获取选中的行
					if(rows.length<=0){
				    	 $.messager.alert('提示', '请选择记录！', 'info');
				    }else if(rows.length > 1) {
						$.messager.alert('提示', '每次只能选择一条记录！', 'info');
					}
				    else {
				    	if(rows[0].fisbilling==1){
				    		$.messager.alert('提示', '该条记录已经开过票了', 'info');
				   		 }
				    	else
				    	{
				    		$("#createReceivables_Window").window({
								title : "开票",
								href : '${ctx}/pages/pc/finance/create_billing.jsp?fid='+rows[0].fid,
								width : 320,
								height : 180,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				    	}
					}
					
			}
		}],
		columns : [[
			{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
			{title : '付款人ID',field :'fuserId',hidden : true,rowspan : 1,align : 'center',width : '100'},
			{title : '创建人ID',field :'fcreatorId',hidden : true,rowspan : 1,align : 'center',width : '100'},
			{title : '付款人',field :'fusername',rowspan : 1,align : 'center',width : '100'},
		    {title : '金额',field : 'famount',rowspan : 1,align : 'center',width : '150'},
		    {title : '未核销金额',field : 'fremainAmount',rowspan : 1,align : 'center',width : '150'},
		    {title : '收款方式',field : 'fpaymentMethod',rowspan : 1,align : 'center',width : '100',formatter:paymentMethod},
		    {title : '创建人',field : 'fcreatorname',rowspan : 1,align : 'center',width : '100'},
		    {title : '创建时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '150'},
		    {title : '备注',field : 'fremark',rowspan : 1,align : 'center',width : '150'},
		    {title : '开票',field : 'fisbilling',rowspan : 1,align : 'center',width : '150',formatter:isBilling}
		]]
	});//.datagrid("hideColumn","fid").datagrid("columnMoving");
	
	$('#receivablesTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});

function paymentMethod(value,rowData,rowIndex){
	if(value==0){
		return '承兑汇票';
	}else if(value==1){
		return '转账';
	}else if(value==2){
		return '现金';
	}else if(value==3){
		return '微信';
	}else if(value==4){
		return '支付宝';
	}else if(value==5){
		return '电汇';
	}else{
		return '其它方式';
	}
}

//是否开票
function isBilling(value,rowData,rowIndex){
	if(value==0){
		return '否';
	}else{
		return '是';
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
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
	
		<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			<form id="searchReceivablesForm" method="post">
				<table style="margin: 0 auto; margin-top: 20px;">
					<tr>
						<td class="m-info-title">日期:</td>
								<td>
								<input type="text" id="receivablesTimeBegin" class="datePicker MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'receivablesTimeEnd\')}'})" name="receivablesTimeBegin" /> - 
								<input type="text" id="receivablesTimeEnd" class="MtimeInput" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'receivablesTimeBegin\')}'})" name="receivablesTimeEnd" />
								</td>
						<td class="m-info-title">付款人:</td>
						<td>
						<select id="fuserId" name="fuserId" panelHeight="200">
								<option value="">请选择</option>
						</select>
						<a id="searchReceivablesButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="receivablesTable"></table>
		</div>
		<div id="createReceivables_Window" style="margin: 0 auto; overflow: hidden;"></div>
	</div>
</body>
</html>