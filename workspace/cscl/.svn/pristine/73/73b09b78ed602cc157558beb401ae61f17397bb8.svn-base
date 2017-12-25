<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增包天规则</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		$("#userRoleIdDay").combobox({
// 			url :"${ctx}/select/getByRoleId.do",
// 			required : true,
// 			missingMessage : "请选择用户",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择用户",
// 			editable : true,
// 			width:145,
// 			//mode: 'remote',
// 			valueField: 'optionId',
// 			textField: 'optionName'
// 		});
		
    	$("#userRoleIdDay").combogrid({
			panelWidth: 250,
			panelHeight: 300,
			idField: 'id',//提交值
			textField: 'name',//选中显示的值
			required : true,
			missingMessage : "请选择客户名称",
			validType : "comboRequired",
			invalidMessage : "请选择客户名称",
			url: '${ctx}/user/combogrid.do',
			method: 'get',
			multiple: false,//可选择多行
			mode: 'remote',
// 			filter: function(q, row){
//  				var opts = $(this).combogrid('options');
//  				if(row.phone.indexOf(q)==0){
//  					return true;
//  				}
// 				return row[opts.textField].indexOf(q) == 0;
// 			},
			columns: [[
				{field:'id',title:'id',width:80,align:'center',hidden : true},
				{field:'name',title:'用户名',width:125,align:'center'},
				{field:'phone',title:'手机号',width:125,align:'center'}
			]],
			fitColumns: true//true表示所有列长度会适应panelWidth的长
		});
    	
		$("#carSpecId1").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "全部",
			validType : "comboRequired",
			invalidMessage : "全部",
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
		});

		$("#saveBTButton").click(function() {
			if ($("#createDAYForm").form("validate")) {
				var params = decodeURIComponent($("#createDAYForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/saveDayRule.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createDayRule_Window").window("close");
								$("#CLProtocolTable").datagrid('reload');
							});
						}else if(response=="failure") {
	                        $.messager.alert('提示', '操作失败,已有相同规则');
	                    }
						else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	});
</script>
</head>
	<form id="createDAYForm" action="" method="post">
			<table class="add-user" id="createlngModule3">
				<tr>
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
<!-- 					<select id="userRoleIdDay" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择" panelHeight="200"> -->
<!-- 							<option value="-1">请选择</option> -->
<!-- 					</select> -->
					<select id="userRoleIdDay" style="width: 145px; height: 25px;" name="userRoleId">
					</select>
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="carSpecId1" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>		
			    	<td class="m-info-title">包天单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="timePrice" name="timePrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="包天单价必须填写"/></td>
			
			    </tr>
			    <!-- <tr>
					<td class="m-info-title">装卸费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service" name="fadd_service" class="easyui-numberbox" required="true" precision="2" missingMessage="装卸费必须填写"/></td>
				</tr> -->
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveBTButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
