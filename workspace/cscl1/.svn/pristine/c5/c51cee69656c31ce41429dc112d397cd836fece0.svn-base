<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>驳回</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#rejectType").combobox({
			url : "",
			required : true,
			missingMessage : "请选择处理方式",
			validType : "comboRequired",
			invalidMessage : "请选择处理方式",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'rejectId',
			textField: 'rejectName'
		});
		
		$("#rejectButton").click(function() {
			if ($("#rejectForm").form("validate")) {
				var params = decodeURIComponent($("#rejectForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/app/finance/reject.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createFinance_Window").window("close");
								$("#financeTable").datagrid('reload');
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
	<form id="rejectForm" action="" method="post">
	<input type="hidden" name="fid" value="${fid}" />
	<input type="hidden" name="famount" value="${famount}" />
	<input type="hidden" name="fuserId" value="${fuserId}" />
			<table class="add-user" id="createlngModule2">
				<tr>
					<td class="m-info-title">驳回理由：<span class="red">*</span>:</td>
					<td class="m-info-content">
<!-- 						<input style="width: 142px; height: 16px;" type="text" id="ftreatment" name="ftreatment"  required="true"   missingMessage="处理方式必须填写"/> -->
					<select id="rejectType" style="width: 250px; height: 25px;" name="frejecttype" missingMessage="请选择处理方式">
							<option value="-1">请选择</option>
							<option value="0">请联系客服</option>
							<option value="1">姓名不正确</option>
							<option value="2">帐号信息有误</option>
							<option value="3">开户行不正确</option>
							<option value="4">开户行所在地不正确</option>
					</select>
					</td>
				</tr>

				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="rejectButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
