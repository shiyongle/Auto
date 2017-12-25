<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>支付</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		$("#payStyle").combobox({
// 			url : "",
// 			required : true,
// 			missingMessage : "请选择处理方式",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择处理方式",
// 			editable : true,
// 			width:145,
// 			mode: 'remote',
// 			valueField: 'payStyleId',
// 			textField: 'payStyleName'
// 		});
		
		$("#savePayButton").click(function() {
			if ($("#payForm").form("validate")) {
				var params = decodeURIComponent($("#payForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/finance/pay.do",
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
	<form id="payForm" action="" method="post">
	<input type="hidden" name="fid" value="${fid}" />
	<input type="hidden" name="famount" value="${famount}" />
	<input type="hidden" name="fuserId" value="${fuserId}" />
			<table class="add-user" id="createlngModule2">
				<tr>
					<td class="m-info-title">处理方式<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input style="width: 142px; height: 16px;" type="text" id="ftreatment" name="ftreatment"  required="true"   missingMessage="处理方式必须填写"/>
<!-- 					<select id="payStyle" style="width: 145px; height: 25px;" name="payStyle" missingMessage="请选择处理方式"> -->
<!-- 							<option value="-1">请选择</option> -->
<!-- 					</select> -->
					</td>
				</tr>
				<tr>
					<td class="m-info-title">支付流水号<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 142px; height: 16px;" type="text" id="fserialNum" name="fserialNum"  required="true"   missingMessage="支付流水号必须填写"/></td>
			    </tr>

				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="savePayButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
