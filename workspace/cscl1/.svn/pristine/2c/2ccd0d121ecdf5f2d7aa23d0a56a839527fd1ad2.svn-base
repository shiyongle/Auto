<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增规则</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#carSpecId").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "请选择车辆规格",
			validType : "comboRequired",
			invalidMessage : "请选择车辆规格",
			editable : true,
			width:175,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName',
		}).combobox("setValues","${rule.carSpecId}");
		
		$("#saveZCButton").click(function() {
			if ($("#createlngModuleForm").form("validate")) {
				var params = decodeURIComponent($("#createlngModuleForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/rule/saveBill.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createBillRule_Window").window("close");
								$("#CLRuleTable").datagrid('reload');
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
	<form id="createlngModuleForm" action="" method="post">
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="carSpecId" name="carSpecId" data-options="editable:false,panelHeight:'auto'"/></td>
			        <td class="m-info-title">起步公里数 (km)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2" missingMessage="起步公里数必须填写"/></td>
				</tr>
				<tr>
					<td class="m-info-title">起步价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startPrice" name="startPrice" class="easyui-numberbox" required="true" precision="2" missingMessage="起步价必须填写" /></td>
			        <td class="m-info-title">公里单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="kilometrePrice" name="kilometrePrice" class="easyui-numberbox" required="true" precision="2" missingMessage="公里单价必须填写"/></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:50px;resize:none;" ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveZCButton">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
