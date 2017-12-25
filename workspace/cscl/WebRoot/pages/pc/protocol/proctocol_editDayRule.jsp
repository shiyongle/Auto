<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改包天规则</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userRoleId_edit3").combobox({
			url : "${ctx}/select/getByRoleId.do",
			required : true,
			missingMessage : "请选择用户",
			validType : "comboRequired",
			invalidMessage : "请选择用户",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',//提交值
			textField: 'optionName'//显示值
		}).combobox('select',"${protocol.userRoleId}").combobox('disable');
		
		$("#carSpecId_edit3").combobox({
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
		}).combobox('select',"${protocol.carSpecId}");
		  
	});		
		
		$("#saveLDAYTutton").click(function() {
			if ($("#editDayForm").form("validate")) {
				var params = decodeURIComponent($("#editDayForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/updateDayZT.do",
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

</script>
</head>
	<form id="editDayForm"  >
		<input  name="id" type="hidden" value="${protocol.id}"/>
			<table class="add-user" id="editDay">
				<tr>
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="userRoleId_edit3" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择" >
							<option value="-1">请选择</option>
					</select>
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="carSpecId_edit3" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择"  >
							<option value="-1">请选择</option>
					</select>
					</td>		
			    	<td class="m-info-title">包天单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" value="${protocol.timePrice}" id="timePrice" name="timePrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="包天单价必须填写"/></td>
			    </tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveLDAYTutton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
