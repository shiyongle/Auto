<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改用户</title>
<script type="text/javascript">
$(document).ready(function() { 

	$('#kilometre5_20_price').numberbox({
		 required:true,
		 precision:2,
		 validType:['lessOneMillon' ,'greatZero'],
		 missingMessage:'5-20公里单价必须填写'
	});
	
	if( "${rule.carSpecId}" ==5 ){
		$('#kilometre5_20_price').attr('disabled',true).numberbox('disableValidation');
	}
	
	$("#carSpecId").combobox({
		url : "${ctx}/select/getAllCarType.do",
		required : true,
		missingMessage : "请选择车辆规格",
		validType : "comboRequired",
		invalidMessage : "请选择车辆规格",
		editable : true,
		width:145,
		mode: 'remote',
		valueField: 'optionId',
		textField: 'optionName',
	}).combobox("setValues","${rule.carSpecId}");
});

$("#editZCButton").click(function() {
	if ($("#createlngModuleForm").form("validate")) {
		var params = decodeURIComponent($("#createlngModuleForm").serialize(), true);
		$.ajax({
			type : "POST",
			url : "${ctx}/rule/updateCarLoad.do",
			data : params,
			success : function(response) {
				if(response == "success") {
					$.messager.alert('提示', '操作成功', 'info', function() {
						$("#createCarloadRule_Window").window("close");
						$("#CLRuleTable").datagrid('reload');
					});
				}else {
					$.messager.alert('提示', "操作失败！");
				}
			}
		});
	}
});

</script>
</head>
	<form id="createlngModuleForm" action="" method="post">
	        <input type="hidden" name="id" value="${rule.id}"/>
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">起步公里数 (km)<span class="red">*</span>:</td>	
					<td class="m-info-content"><input type="text" id="startKilometre" readonly="readyonly" name="startKilometre" class="easyui-numberbox" required="true" precision="2" missingMessage="起步公里数必须填写" value="${rule.startKilometre}" /></td>
				</tr>
				<tr>
					<td class="m-info-title">起步价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startPrice" name="startPrice" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'起步价必须填写'"  value="${rule.startPrice}"/></td>
					<td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="outfopint" name="outfopint" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超出点数单价必须填写'"  value="${rule.outfopint}"/></td>
				</tr>
				<tr>
			        <td class="m-info-title">5-20公里单价<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input type="text" name="kilometre5_20_price"    id="kilometre5_20_price"  data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'5-20公里单价必须填写'" value="${rule.kilometre5_20_price}"/>
					</td>
					<td class="m-info-title">20-50公里单价<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input type="text" name="kilometre20_50_price" class="easyui-numberbox"   data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'20-50公里单价必须填写'" value="${rule.kilometre20_50_price}"/>
					</td>
				</tr>
				<tr>
					<td class="m-info-title">50公里以上单价<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input type="text" name="kilometre50_price" class="easyui-numberbox" data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'50公里以上单价必须填写'" value="${rule.kilometre50_price}"/>
					</td>
					 <td class="m-info-title">保底点数<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fopint" name="fopint" class="easyui-numberbox" data-options="required:true,validType:['lessOneMillon' ,'greatZero'],missingMessage:'保底点数必须填写'"   value="${rule.fopint}"/></td>
				</tr>
				<tr>
					<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_1"  name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'" value="${rule.fadd_service_1}" /></td>
					<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_2"  name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'" value="${rule.fadd_service_2}" /></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
<%-- 					<input type="text" id="pubRemark" style="width:470px;height:50px;" name="pubRemark" value="${rule.pubRemark}" /> --%>
						<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:50px;resize:none;" >${rule.pubRemark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="editZCButton">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
