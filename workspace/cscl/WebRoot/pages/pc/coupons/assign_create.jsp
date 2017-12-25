
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>指派好运券新建</title>
<script>
	$(document).ready(function() {
		//****************************************校验开始**********************************//
		
		$("#startTimeTwo").validatebox({
			required : true,
			missingMessage : "请输入开始时间"
		});

		$("#endTimeTwo").validatebox({
			required : true,
			missingMessage : "请输入结束时间"
		});
		
		$("#saveCouponsTwo").click(function() {
			if ($("#addCouponsFormTwo").form("validate")) {
				var params = decodeURIComponent($("#addCouponsFormTwo").serialize(), true);
				$("#createCouponsAssignWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/couponsAssign/saveTwo.do",
					dataType:'json',
					data : params,
					success : function(response) {
						if (response.success == "success") {
							$.messager.alert('提示', '添加成功', 'info', function() {
								$("#createCouponsAssignWindow").window("close");
								$("#CLCouponsAssignTable").datagrid('reload');
								$("#createCouponsAssignWindow").mask("hide");
							});
						} else {
							$.messager.alert('提示', response.msg);
							 /* $("#createCouponsAssignWindow").hide(); */
							/*$("#createCouponsAssignWindow").window("close"); */
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
<form id="addCouponsFormTwo"  method="post">
	<table class="add-user" id="createCouponsTwo">
	 
		<tr>
			<td class="m-info-title">开始时间:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="startTimeTwo" name="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTimeTwo\')}'})"/></td>
			<td class="m-info-title">结束时间:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="endTimeTwo" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTimeTwo\')}'})" /></td>
		</tr>
		<tr>
			<td class="m-info-title">面额:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="dollarsTwo" name="dollars" class="easyui-numberbox" data-options=" required:true, precision:2, validType:['lessOneMillon'],missingMessage:'面额必须填写'" /></td>
			<td class="m-info-title">满N元可用:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="compareDollarsTwo" name="compareDollars"  class="easyui-numberbox" data-options="required:true, precision:2, validType:['lessOneMillon'],missingMessage:'满N元可用必须填写'" /></td>
		</tr>
		<tr>
			<td class="m-info-title">描述:</td>
			<td class="m-info-content" colspan="3"><textarea   maxlength="255" id="describesTwo" style="width:370px;resize:none;height:120px;" name="describes" /></td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="Mbutton25 createButton" id="saveCouponsTwo">保存</div>
			</td>
		</tr>
	</table>
</form>
</html>