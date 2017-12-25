<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单调度</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		$("#userRoleId").combobox({
// 			required : true,
// 			url : "${ctx}/order/queryDriver.do",
// 			missingMessage : "请选择司机",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择司机",
// 			editable : true,
// 			valueField: 'userRoleId',
// 			textField: 'driverName'
// 		});
		//alert('${orderid}');
		/*司机下拉框combogrid*/
		$("#userRoleId").combogrid({
			panelWidth: 500,
			panelHeight:200,
			idField: 'userRoleId',//提交值
			textField: 'driverName',//选中值
			//url: '${ctx}/pages/pc/order/datagrid_data1.json',
			validType:"combogirdValidate['userRoleId','driverName']",
			required:true,
			missingMessage:'指派司机没选!',
			delay:6000,
			url: '${ctx}/order/controlList.do?id=${orderid}',
			method: 'get',
			multiple: false,//可选择多行
			columns: [[
				{field:'userRoleId',title:'id',width:80,align:'center',hidden : true},
				{field:'driverName',title:'司机',width:80,align:'center'},
				{field:'carFtel',title:'联系电话',width:120,align:'center'},
				{field:'fstraightStretch',title:'直线距离',width:120,align:'center'},
				{field:'fisOnline',title:'是否在线',width:60,align:'center',formatter:applyToPass}
			]],
			fitColumns: true,//true表示所有列长度会适应panelWidth的长
			mode:"local",
			filter: function(q, row){
				var opts = $(this).combogrid('options');
				if(row.driverName.indexOf(q)==0 || row.carFtel.indexOf(q)==0 ){
 					return true;
 				}
				if(row[opts.textField]){
				return row[opts.textField].indexOf(q) == 0;					
				}
			},
			onChange:function(newvalue,oldvalue){
				document.onkeydown=function(event){
		             var e = event || window.event || arguments.callee.caller.arguments[0];            
		             if(e && e.keyCode==13){//enter键
		            	 e.preventDefault();
		            }
		        }; 
			},
		});

		/***确认调度*/
		$("#submit").click(function() {
			if ($("#TForm").form("validate")) {
				var params = decodeURIComponent($("#TForm").serialize(), true);
				$("#createWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/order/assign.do",
					data : params,
					dataType:"json",
					success : function(response) {
						if(response.success == "true") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$("#createWindow").mask("hide");
								$("#CLOrderTable").datagrid('reload');
							});
						}else {
							$("#createWindow").mask("hide");
							$.messager.alert('提示', response.msg);
						}
					}
				});
			}
		});
	});
</script>
</head>
<div  id="createWindow"> </div>
	<form id="TForm"  >
			<table class="add-user" id="createBursarInfo">
				<tr>
					<td class="m-info-title">指派司机<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="userRoleId" style="width: 154px; height: 25px;" name="userRoleId"></select>
					</td>
				</tr>
				<tr>
					<td class="m-info-title">是否回程车<span class="red">*</span>:</td>
					<td>
						否<input name="freturn_car" type="radio" value="2">
						是<input name="freturn_car" type="radio" value="1">
					</td>
				</tr>
				<tr>
					<td class="m-info-title">&nbsp;</td>
					<td class="m-info-content"><div class="Mbutton25 createButton" id="submit">确定</div></td>
				</tr>
			</table>
	</form>
</html>
