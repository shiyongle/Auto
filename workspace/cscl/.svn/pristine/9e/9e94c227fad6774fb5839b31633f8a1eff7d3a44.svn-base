<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改增值服务</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#submit").click(function() {
			if ($("#TForm").form("validate")) {
				var params = decodeURIComponent($("#addForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}//save.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$("#addserviceTable").datagrid('reload');
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
	<form id="addForm">
			<input value="${addservice.faddserviceId}">
			<input value="${addservice.faddserviceType}">
			<table class="add-user" id="createBursarInfo">
				<tr>
					<td class="m-info-title">增值服务名称<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input name="faddserviceName"  value="${addservice.faddserviceName}" class="easyui-validatebox" data-options=" required:true,validType:['maxLength[10]'], missingMessage:'增值服务必须填写！！'">
					</td>
				</tr>
				<tr>
					<td class="m-info-title">增值服务价格<span class="red">*</span>:</td>
					<td>
						<input  type="text"  value="${addservice.faddservicePrice}" name="faddservicePrice" class="easyui-numberbox"  data-options="required:true,missingMessage:'增值服务价格！',validType:['maxLength[15]']" />
					</td>
				</tr>
				<tr>
					<td class="m-info-title">备注<span class="red">*</span>:</td>
					<td class="m-info-content">
						<textarea name="fremark"  value="${addservice.fremark}" class="easyui-validatebox" data-options=" required:true,validType:['maxLength[50]'], missingMessage:'备注必须填写！！'"></textarea>
					</td>
				</tr>
			</table>
	      	<div class="Mbutton25 createButton" id="submit"> 保存</div>
	</form>
</html>
