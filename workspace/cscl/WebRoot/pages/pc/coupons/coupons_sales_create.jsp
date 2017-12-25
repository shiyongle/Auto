<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务员好运券新建</title>
<script>
	$(document).ready(function() {
		//****************************************校验开始**********************************//
		
		$("#startTimeS").validatebox({
			required : true,
			missingMessage : "请输入开始时间"
		});

		$("#endTimeS").validatebox({
			required : true,
			missingMessage : "请输入结束时间"
		});
		
		$("#salesMan").validatebox({
			required : true,
			missingMessage : "请输入业务员名称",
			invalidMessage : "请输入业务员名称"
		});
		
		$("#saveCouponsS").click(function() {
			if ($("#addCouponsFormS").form("validate")) {
				var params = decodeURIComponent($("#addCouponsFormS").serialize(), true);
				$("#createCouponsSalesWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/couponsSales/save.do",
					data : params,
					success : function(response) {
						if (response == "success") {
							$.messager.alert('提示', '添加成功', 'info', function() {
								$("#createCouponsSalesWindow").window("close");
								$("#CLCouponsSalesTable").datagrid('reload');
								$("#createCouponsSalesWindow").mask("hide");
							});
						} else {
							$.messager.alert('提示', '操作失败');
							$("#createCouponsSalesWindow").hide();
						}
					}
				});
			}
		});
		
	});	
		//****************************************校验结束**********************************//
</script>
</head>
<form id="addCouponsFormS"  method="post">
	<table class="add-user" id="createCouponsS">
		<tr>
			<td class="m-info-title">开始时间:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="startTimeS" name="startTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endTimeS\')}'})"/></td>
			<td class="m-info-title">结束时间:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="endTimeS" name="endTime" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTimeS\')}'})" /></td>
		</tr>
		<tr>
			<td class="m-info-title">总金额:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="totalDollars" name="totalDollars" class="easyui-numberbox" required="true"  missingMessage="面额必须填写" /></td>
			<td class="m-info-title">总数量:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="totalQuantity" name="totalQuantity"  class="easyui-numberbox" required="true" missingMessage="满N元可用必须填写"  /></td>
		</tr>
		<tr>
			<td class="m-info-title">业务员:<span class="red">*</span></td>
			<td class="m-info-content"><input type="text" id="salesMan" name="salesMan"  /></td>
		</tr>
		<tr>
			<td class="m-info-title">描述:</td>
			<td class="m-info-content" colspan="3"><textarea   maxlength="255" id="describesS" style="width:370px;resize:none;height:120px;" name="describes" /></td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="Mbutton25 createButton" id="saveCouponsS">保存</div>
			</td>
		</tr>
	</table>
</form>
</html>