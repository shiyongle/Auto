<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>订单异常调度</title>
<script type="text/javascript">
	$(document).ready(function() {
		var orderid = $('#forderId').val();
		$("#userRoleId").combogrid({
			panelWidth: 500,
			panelHeight:200,
			idField: 'userRoleId',//提交值
			textField: 'driverName',//选中值
			//url: '${ctx}/pages/pc/order/datagrid_data1.json',
			url: '${ctx}/abnormity/controlList.do?orderid='+orderid,
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
		$("#error_submit").click(function() {
			if ($("#error_TForm").form("validate")) {
				var params = decodeURIComponent($("#error_TForm").serialize(), true);
// 				$("#createWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/abnormity/errorassign.do",
					data : params,
					dataType:"json",
					success : function(response) {
						if(response.success == "true") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#error_Window").window("close");
// 								$("#createWindow").mask("hide");
								$("#errorListTable").datagrid('reload');
							});
						}else {
// 							$("#createWindow").mask("hide");
							$.messager.alert('提示', response.data);
						}
					}
				});
			}
		});
		
		function applyToPass (value,rowData,rowIndex){
			if(value==0){
				return '<span style="color:blue;font-weight:bold;">否</span>';
			}
			else if(value==1){
				return '<span style="color:red;font-weight:bold;">是</span>';
			}
		}
	});
</script>
</head>
	<form id="error_TForm"  >
	<input type="hidden" id = "forderId" name="forderId" value="${forderId}" />
	<input type="hidden" id = "fabid" name="fabid" value="${fabid}" />
	
			<table class="add-user" id="error_createBursarInfo">
				<tr>
					<td class="m-info-title">指派司机<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="userRoleId" style="width: 154px; height: 25px;" name="userRoleId"></select>
					</td>
				</tr>
				<tr>
					<td class="m-info-title">&nbsp;</td>
					<td class="m-info-content"><div class="Mbutton25 createButton" id="error_submit">确定</div></td>
				</tr>
			</table>
	</form>
</html>
