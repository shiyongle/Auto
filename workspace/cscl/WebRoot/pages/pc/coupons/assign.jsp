<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指派好运券新建</title>
<script>
	$(document).ready(function() {
		//下拉框渲染
		$("#userRoleId").combobox({
			required : true,
			url : "${ctx}/select/getAllConsignor.do?couponsId="+"${recordId}",
			missingMessage : "请选择货主",
			validType : "comboRequired",
			invalidMessage : "请选择货主",
			editable : true,
			filter:searchItem,
			valueField: 'optionId',
			textField: 'optionName',
			panelHeight:200
		}).combobox('select', "-1");
				
				
		$("#saveCouponsAssign").click(function() {
			if ($("#addCouponsFormAssign").form("validate")) {
				$("#createCouponsAssignWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/couponsAssign/saveAssign.do",
					data : {"userRoleId":$('#userRoleId').combobox('getValue'),"couponsId":"${recordId}"},
					dataType:'json',
					success : function(response) {
						if (response.success == "success") {
							$.messager.alert('提示', '添加成功', 'info', function() {
								$("#createCouponsAssignWindow").window("close");
								$("#CLCouponsAssignTable").datagrid('reload');
								$("#createCouponsAssignWindow").mask("hide");
							});
						} else {
							$.messager.alert('提示',response.msg ,'info',function(){
								$("#createCouponsAssignWindow").window("close");
							});
							/* $("#createCouponsAssignWindow").hide(); */
							$('.mask,.mask-msg').hide();
						}
					}
				});
			}
		});
		
	});	
		//****************************************校验结束**********************************//
</script>
</head>
<form id="addCouponsFormAssign"  method="post">
	<table class="add-user" id="createCouponsAssign">
		<tr>
			<td class="m-info-title">指派货主<span class="red">*</span>:</td>
			<td class="m-info-content"><select id="userRoleId" style="width: 154px; height: 25px;" name="userRoleId"></select></td>
		</tr>
		<tr>
			<td colspan="2">
				<div class="Mbutton25 createButton" id="saveCouponsAssign">确定</div>
			</td>
		</tr>
	</table>
</form>
</html>