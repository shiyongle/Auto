<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>包天规则修改</title>
<script>
	$(function(){
		//用户名
		$("#userName").combobox({
			url :"${ctx}/select/getAllCus.do",
			required : true,
			missingMessage : "请选择司机名",
			validType : "comboRequired",
			invalidMessage : "请选择司机名",
			editable : true,
			disabled:true,
			panelHeight:200,
			width:145,
			mode: 'local',
			filter:searchItem,//筛选
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select',"${protocol.userRoleId}");
		//车型
		$("#carType").combobox({
			url :"${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "请选择车型",
			validType : "comboRequired",
			invalidMessage : "请选择车型",
			editable : true,
			disabled:true,
			panelHeight:200,
			width:145,
			mode: 'local',
			filter:searchItem,//筛选
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select',"${protocol.carSpecId}");
		//保存按钮
		$("#saveButton").click(function() {
			if ($("#editForm").form("validate")) {
				var params = decodeURIComponent($("#editForm").serialize(), true);	
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/updateDriverProtocol.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$('#allDaySettingTable').datagrid('reload');
							});
						}
						else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	})
</script>
</head>
	<form id="editForm">
		<input type="hidden" name="id" value="${protocol.id}"/>
		<input type="hidden" value="1" name="fdriver_type">
		<table>
			<tr>
				<td class="m-info-title">客户名<span class="red">*</span>:</td>
				<td>
					<select id="userName" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户名必须选择">
							<option value="-1">请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">车型<span class="red">*</span>:</td>
				<td>
					<select id="carType" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择">
							<option value="-1">请选择</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">包天价格<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice" name="startPrice" value="${protocol.startPrice }" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价必须填写"/></td>
			</tr>
			<tr>
				<td class="m-info-title">备注:</td>
				<td class="m-info-content"><textarea name="pubRemark" id="pubRemark" style="width:145px; height:50px; resize:none;">${protocol.pubRemark } </textarea></td>
			</tr>
			<tr align="right">
				<td colspan="2">
					<div class="Mbutton25 createButton" id="saveButton" >保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>