<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>提现管理</title>
<link href="${ctx}/pages/pc/css/style.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css"
	type="text/css" rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"
	rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js"
	type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {	
	/*司机下拉框*/
	$("#fusername").combobox({
		url : "${ctx}/select/getAllDrivers.do",
		editable : true,
		width:140,
		mode: 'local',//加载后本地操作搜索
		valueField: 'optionName',//查询提交的值
		textField: 'optionName',//显示的值
		filter:searchItem
	});
	
 	//查询按钮点击事件
	$("#searchFinanceButton").click(function() {
		$('#financeTable').datagrid('load', JSON.parse(toJOSNStr($("#searchFinanceForm").serializeArray())));
	});
	$('#financeTable').datagrid({
		title : '规则管理',
		loadMsg : '数据装载中......',
		url:'${ctx}/finance/load.do',
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
		onHeaderContextMenu : gridHeaderMenu,
		toolbar : [{
					text : '支付',
					iconCls : 'icon-add',
					handler : function() {
						var row = $("#financeTable").datagrid('getSelected');//获取选中的行
						if(row!=null && row.fserialNum==null){//选择一条已通过的数据进行修改，但是不能修改状态,并且需要对象是车主	
							$("#createFinance_Window").window({
								title : "支付",
								href : '${ctx}/finance/update.do?fid='+row.fid+'&famount='+row.famount+'&fuserId='+row.fuserId,
								width : 280,
								height : 150,
								draggable:true,
								resizable:false,
								maximizable:false,
								minimizable:false,
								collapsible:false,
								modal : true
						    });
				        }else if(row==null)
				        {
				        	$.messager.alert('提示','请选择一条记录！'); 
				        }else if(row.fserialNum!='')
				        {
				        	$.messager.alert('提示','该提现申请已支付，请勿重复支付！'); 
				        }
					}
				},'-',{
					text : '驳回申请',
					iconCls : 'icon-remove',
					handler : function() {
						var row = $("#financeTable").datagrid('getSelected');//获取选中的行
						if(row.length<=0){
					    	 $.messager.alert('提示', '请选择记录！', 'info');
					    }else if (row.length > 1) {
								$.messager.alert('提示', '每次只能选择一条记录！', 'info');
						}else if(row==null)
				        {
				        	$.messager.alert('提示','请选择一条记录！'); 
				        }else if(row.fstate==1)
				        {
				        	$.messager.alert('提示','该提现申请已支付，不能驳回！'); 
				        }else if(row.fstate==2)
				        {
				        	$.messager.alert('提示','该提现申请已驳回，请勿重复提交！'); 
				        }
				        else{//	流水号为空才可以驳回
							$("#createFinance_Window").window({
								title : "驳回",
								href : '${ctx}/finance/rejectload.do?fid='+row.fid+'&famount='+row.famount+'&fuserId='+row.fuserId,
								width : 320,
								height : 150,
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
			  		id : "exportFinanceExecl",
					text : '导出',
					iconCls : 'icon-excel',
					handler : function() {
						$("#financeTable").datagrid("loading");
						var rows = $("#financeTable").datagrid('getSelections');
						var params = $("#searchFinanceForm").serialize();
						$.ajax({
							type : "POST",
							dataType:"json",
							url : "${ctx}/finance/exportExecl.do",
							data : getFinanceId(rows)+ "&" +params,
							success : function(response) {
								if(response.success){
									window.location.href ="${ctx}/excel/"+response.url;
									$("#financeTable").datagrid("loaded");
								}else{
									$.messager.alert('提示', '操作失败');
									$("#financeTable").datagrid("loaded");
								} 
							}
						});
					}
				}
			],
		columns : [[
			{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
			{title : '用户ID',field : 'fuserId',hidden : true,rowspan : 1,align : 'center'},
		    {title : '申请时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '120'},
		    {title : '提现单号',field : 'number',rowspan : 1,align : 'center',width : '150'},
		    {title : '司机名称',field : 'fusername',rowspan : 1,align : 'center',width : '100'},
		    {title : '申请金额',field : 'famount',rowspan : 1,align : 'center',width : '100'},
		    {title : '提现方式',field : 'fwithdrawType',rowspan : 1,align : 'center',width : '80',formatter:withdrawType},
		    {title : '支付宝账号',field : 'falipayId',rowspan : 1,align : 'center',width : '100'},
		    {title : '银行卡号',field : 'fbankAccount',rowspan : 1,align : 'center',width : '160'},
		    {title : '开户行',field : 'fbankName',rowspan : 1,align : 'center',width : '120'},
		    {title : '开户行所在地',field : 'fbankAddress',rowspan : 1,align : 'center',width : '220'},
		    {title : '处理人',field : 'fhandlername',rowspan : 1,align : 'center',width : '80'},
		    {title : '支付时间',field : 'paymentTimeString',rowspan : 1,align : 'center',width : '170'},
		    {title : '处理方式',field : 'ftreatment',rowspan : 1,align : 'center',width : '100'},
		    {title : '支付流水号',field : 'fserialNum',rowspan : 1,align : 'center',width : '180'},
		    {title : '申请状态',field : 'fstate',rowspan : 1,align : 'center',width : '80',formatter:state},
		    {title : '驳回理由',field : 'frejectType',rowspan : 1,align : 'center',width : '120',formatter:rejectType}
		]]
	});//.datagrid("hideColumn","id").datagrid("columnMoving");
	$('#financeTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
});
/***订单流水号***/
function getFinanceId(rows) {
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

function withdrawType(value,rowData,rowIndex){
	if(value==1){
		return '支付宝';
	}else if(value==2){
		return '银行转账';
	}else{
		return value;
	}
}
function rejectType(value,rowData,rowIndex){
	if(value==0){
		return '请联系客服';
	}else if(value==1){
		return '姓名不正确';
	}else if(value==2){
		return '帐号信息有误';
	}else if(value==3){
		return '开户行不正确';
	}else if(value==4){
		return '开户行所在地不正确';
	}
}

function state(value,rowData,rowIndex){
	if(value==0){
		return '待处理';
	}else if(value==1){
		return '成功';
	}else if (value==2){
		return '驳回';
	}else
	{
		return value;
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
function applyToStatus(value,rowData,rowIndex){
	if(value==1){
		return '<span style="color:red; ">有效</span>';
	}else if(value==2){
		return '<span style="color:blue; " >失效</span>';
	}
}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" style="border-left: 0px; border-right: 0px;">
	
		<div data-options="region:'north',title:'查询条件'" style="height: 100px; border: 0px;">
			<form id="searchFinanceForm" method="post">
				<table style="margin: 0 auto; margin-top: 20px;">
					<tr>
						<td class="m-info-title">日期:</td>
								<td><input type="text" id="createTimeBegin"
							class="datePicker MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})"
							name="createTimeBegin" /> - <input type="text"
							id="createTimeEnd" class="MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeBegin\')}'})"
							name="createTimeEnd" />
								</td>
						<td class="m-info-title">司机名称:</td>
						<td>
						<select id="fusername" name="fusername" panelHeight="200">
								<option value="">请选择</option>
						</select>
<!-- 						<input id="fusername" name="fusername" style="width: 100px; height: 16px;margin-top:5px"/> -->
						
						<td class="m-info-title">状态:</td>
							<td>
								<select  id="orderStatus"  name="fstate" class="easyui-combobox" style="width: 100px; height: 20px;" data-options="editable:false">
									<option value="">全部</option>
									<option value="0">待处理</option>
									<option value="1">成功</option>
									<option value="2">驳回</option>
								</select>
								<a id="searchFinanceButton" href="javascript:;" class="easyui-linkbutton" style="width: 60px;">查询</a>		
							</td>
                   	</tr>
				</table>
			</form>
		</div>
		
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="financeTable"></table>
		</div>
		<div id="createFinance_Window" style="margin: 0 auto; overflow: hidden;"></div>
	</div>
</body>
</html>