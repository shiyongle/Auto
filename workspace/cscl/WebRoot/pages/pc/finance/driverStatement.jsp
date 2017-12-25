<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>司机收支明细</title>
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
	$('#driverStatementTable').datagrid({
		title : '收支明细',
		loadMsg : '数据装载中......',
// 		url:'${ctx}/financeStatement/driverload.do',
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
				id : "exportReportExecl",
				text : '导出',
				iconCls : 'icon-excel',
				handler : function() {
					$("#driverStatementTable").datagrid("loading");
					var rows = $("#driverStatementTable").datagrid('getSelections');
					var params = $("#searchStatementForm").serialize();
					$.ajax({
						type : "POST",
						dataType:"json",
						url : "${ctx}/financeStatement/exportExecl.do",
						data : getOrderId(rows)+ "&" +params,
						success : function(response) {
							if(response.success){
							window.location.href ="${ctx}/excel/"+response.url;
							$("#driverStatementTable").datagrid("loaded");
						}else{
							$.messager.alert('提示', '操作失败');
							$("#driverStatementTable").datagrid("loaded");
						} 
					}
				});
			}
		},'-',],
		columns : [[
				{title : 'ID',field : 'fid',hidden : true,rowspan : 1,align : 'center',sortable:true},
				{title : '用户ID',field : 'fuserid',hidden : true,rowspan : 1,align : 'center',sortable:true},
				{title : '用户角色ID',field : 'fuserroleId',hidden : true,rowspan : 1,align : 'center',sortable:true},
		        {title : '订单号',field : 'forderId',rowspan : 1,align : 'center',width : '220'},		
	            {title : '订单运费',field : 'freight',rowspan : 1,align : 'center',width : '110'},
				{title : '订单创建时间',field : 'createTimeString',rowspan : 1,align : 'center',sortable:true},
		        {title : '交易时间',field : 'createTimeString',rowspan : 1,align : 'center',width : '220'},
		        {title : '交易号',field : 'number',rowspan : 1,align : 'center',width : '200'},
		        {title : '业务类型',field : 'fbusinessType',rowspan : 1,align : 'center',width : '180',formatter:applyToBusinessType},
		        {title : '金额',field : 'famount',rowspan : 1,align : 'center',width : '200'},
		        {title : '收/支',field : 'ftype',rowspan : 1,align : 'center',width : '150',formatter:applyToAmountType},
		        {title : '司机名称',field : 'driverName',rowspan : 1,align : 'center',width : '200'},
		        {title : '支付类型',field : 'fpayType',rowspan : 1,align : 'center',width : '100',formatter:applyToPayType},
		        {title : '备注',field : 'cfstate',rowspan : 1,align : 'center',width : '100',formatter:applyToWithdraw},
				{title : '相关表ID',field : 'frelatedId',hidden : true,rowspan : 1,align : 'center',width : '120'}
		        ]]
			});
	$('#driverStatementTable').datagrid('getPager').pagination({
		beforePageText : '第',//页数文本框前显示的汉字 
		afterPageText : '页    共 {pages} 页',
		displayMsg : '当前显示 {from} - {to} 条记录   共 {total} 条记录',
		onBeforeRefresh : function(pageNumber, pageSize) {
			$(this).pagination('loading');
		}
	});
	
	$("#userid").combobox({
		url : "${ctx}/select/getAllDrivers.do",
		editable : true,
		width:140,
		mode: 'local',
		valueField: 'optionId',//提交值
		textField: 'optionName',//显示值
		filter:searchItem,
		onSelect:function(record){
			$.ajax({
				type : "POST",
				dataType:"json",
				url : "${ctx}/statement/loadDriverBill.do?userroleid="+record.optionId,
				success : function(response) {
					$("#account").empty();
					var html= '<table id="information" style="padding-left:300px">'+
					'<tr><td class="m-info-title" style="padding-left:35px">当前余额:'+response.fbalance+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">锁定余额:'+response.flocked+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">可提现余额:'+response.favailable+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">运费收入:'+response.fdriverfee+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">其它收入:'+response.freceive+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">提现支出:'+response.fwithdraw+'</td>'+
					'<td class="m-info-title" style="padding-left:35px">其它支出:'+response.fpay+'</td></tr>';
					$("#account").append(html);
				}
			});
        },
	});
	
	$("#ftype").combobox({
		//url : "${ctx}/select/getByRoleId.do",
		//editable : true,
		width:100,
		mode: 'local',
		valueField: 'id',//提交值
		textField: 'name',//显示值
		data:[{"id":"","name":"全部"},{"id":"1","name":"收入"},{"id":"-1","name":"支出"}]
	});
	
	$("#fpayType").combobox({
		//url : "${ctx}/select/getByRoleId.do",
		//editable : true,
		width:100,
		mode: 'local',
		valueField: 'id',//提交值
		textField: 'name',//显示值
		filter:function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		data:[{"id":"","name":"全部"},{"id":"0","name":"余额"},{"id":"1","name":"支付宝"},{"id":"2","name":"微信"},{"id":"3","name":"银联"},{"id":"4","name":"运费到付"},{"id":"5","name":"月结"}]
	});
	
	$("#fbusinessType").combobox({
		//url : "${ctx}/select/getByRoleId.do",
		//editable : true,
		width:100,
		mode: 'local',
		valueField: 'id',//提交值
		textField: 'name',//显示值
		filter:function(q, row){
			var opts = $(this).combobox('options');
			return row[opts.textField].indexOf(q) == 0;
		},
		data:[{"id":"","name":"全部"},{"id":"1","name":"下单支付"},{"id":"2","name":"补交货款"},{"id":"3","name":"运营异常"},{"id":"4","name":"订单完成"},{"id":"5","name":"提现"},{"id":"6","name":"充值"},{"id":"7","name":"转介绍奖励"},{"id":"8","name":"货主退款"}]
	});
	/**toolbar上方工具栏结束*/
	
	//用户管理查询按钮点击事件
	$("#searchButton").click(function() {
// 		alert(toJOSNStr($("#searchStatementForm").serializeArray()));
	    var opts = $("#driverStatementTable").datagrid("options");
	    opts.url = '${ctx}/financeStatement/driverload.do';
		$('#driverStatementTable').datagrid('load', JSON.parse(toJOSNStr($("#searchStatementForm").serializeArray())));
		
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

//业务类型
function applyToBusinessType(value,rowData,rowIndex){
	if(value==0){
		return '余额调整';
	}else if(value==1){
		return '下单支付';
	}
	else if(value==2){
		return '补交货款';
	}
	else if(value==3){
		return '运营异常';
	}
	else if(value==4){
		return '订单完成';
	}
	else if(value==5){
		return '提现';
	}
	else if(value==6){
		return '充值';
	}
	else if(value==7){
		return '转介绍奖励';
	}
	else if(value==8){
		return '货主退款';
		
	}else if(value==9){
		return '绑卡';
		
	}else if(value==10){
		return '订单运费调整';
		
	}else if(value==11){
		
		return '运费上缴';
	}else if(value==12){
		return '司机运费线上支付';
		
	}
}

//收支
function applyToAmountType(value,rowData,rowIndex){
	if(value==1){
		return '收入';
	}else if(value==-1){
		return '支出';
	}
}

//收支
function applyToWithdraw(value,rowData,rowIndex){
	if(value==0){
		return '待处理';
	}else if(value==1){
		return '成功';
	}else if(value==2){
		return '驳回';
	}
}

//支付类型
function applyToPayType(value,rowData,rowIndex){
	if(value==0){
		return '余额';
	}else if(value==1){
		return '支付宝';
	}else if(value==2){
		return '微信';
	}else if(value==3){
		return '银联';
	}else if(value==4){
		return '运费到付';
	}else if(value==5){
		return '月结';
	}else{
		return '其他方式';
	}
}

/***导出多条明细***/
function getOrderId(rows) {
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
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true"
		style="border-left: 0px; border-right: 0px;">
		<div data-options="region:'north',title:'查询条件'"
			style="height: 120px; border: 0px;" id="demo">
			<form id="searchStatementForm" method="post">
				<input type="hidden" name="roleid" value="2" />
				<table style="margin: 0 auto; margin-left: 200px; margin-top: 20px;">
					<tr>
						<td class="m-info-title">日期:</td>
						<td><input type="text" id="createTimeBegin"
							class="datePicker MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'createTimeEnd\')}'})"
							name="createTimeBegin" /> - <input type="text"
							id="createTimeEnd" class="MtimeInput"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'createTimeBegin\')}'})"
							name="createTimeEnd" /></td>
						<td class="m-info-title">司机名称:</td>
						<td><select id="userid" name="fuserid" panelHeight="200">
								<option value="">请选择</option>
						</select></td>
						<td class="m-info-title">收支:</td>
						<td><select id="ftype" name="ftype">
								<option value="">请选择</option>
						</select></td>
						<td class="m-info-title">支付类型:</td>
						<td><select id="fpayType" name="fpayType">
								<option value="">请选择</option>
						</select></td>
						<td class="m-info-title">业务类型:</td>
						<td><select id="fbusinessType" name="fbusinessType">
								<option value="">请选择</option>
						</select> <a id="searchButton" href="javascript:;"
							class="easyui-linkbutton" style="width: 60px;">查询</a></td>
					</tr>
				</table>
				<div id="account"></div>
			</form>
			<%-- <table id="information">
				<tr>
					<td>当前余额:${fbalance}</td>
					<td>锁定余额:${flocked}</td>
					<td>可提现余额:${favailable}</td>
					<td>运费收入:${fdriverfee}</td>
					<td>其它收入:${freceive}</td>
					<td>提现支出:${fwithdraw}</td>
					<td>其它支出:${fpay}</td>
				</tr>
			</table> --%>
		</div>
		<div data-options="region:'center',title:'',bodyCls:'Mnolrbborder'">
			<table id="driverStatementTable"></table>
		</div>
	</div>
</body>


</html>