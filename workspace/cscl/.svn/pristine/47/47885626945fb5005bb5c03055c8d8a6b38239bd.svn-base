<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加用户</title>
<script type="text/javascript">
	$(document).ready(function() {
	    //规格
		$("#carSpecId1").combobox({
				url : "${ctx}/select/getAllCarType1.do",
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
		
		$("#userRoleId").combobox({
			url : "${ctx}/user/queryCarUser.do",
			required : true,
			missingMessage : "请选择用户",
			validType : "comboRequired",
			invalidMessage : "请选择用户",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName'
		});
		
		/***增加*/
		$("#submit").click(function() {
			if ($("#TForm").form("validate")) {
				var params = decodeURIComponent($("#TForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/car/save.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$("#CLCarTable").datagrid('reload');
							});
						}else {
							$.messager.alert('提示', "操作失败！");
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
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
						<select id="userRoleId" style="width: 154px; height: 25px;" name="userRoleId"><option value="-1">请选择</option></select>
					</td>
					<td class="m-info-title">车辆类型<span class="red">*</span>:</td>
		<!-- 		<td class="m-info-content"><input  type="text" name="carType" class="easyui-validatebox" required="true" missingMessage="车辆类型必须填写"/></td>
		 -->
		            <td>
		                   <select id="carSpecId1" name="carSpecId">
										<option value="-1">全部</option>
						   </select>
		            </td>
				</tr>
				<tr>
					<td class="m-info-title">车牌号<span class="red">*</span>:</td>
					<td class="m-info-content"><input  type="text" name="carNum"  class="easyui-validatebox" required="true" missingMessage="车牌号必须填写"/> </td>
					<td class="m-info-title">驾驶人<span class="red">*</span>:</td>
					<td class="m-info-content"> <input  type="text" name="driverName" class="easyui-validatebox" required="true" missingMessage="驾驶人必须填写"/></td>
				</tr>
				<tr>
					<td class="m-info-title">驾驶证编码<span class="red">*</span>:</td>
					<td class="m-info-content"><input  type="text" name="driverCode" class="easyui-validatebox" required="true" missingMessage="驾驶证必须填写"/></td>
				</tr>
			</table>
	      	<div class="Mbutton25 createButton" id="submit"> 保存</div>
	</form>
</html>
