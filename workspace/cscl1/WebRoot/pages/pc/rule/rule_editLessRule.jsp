<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改用户</title>
<script type="text/javascript">
$(document).ready(function() { 
	$("#editLessButton").click(function() {
		if ($("#createlngModuleLessForm").form("validate")) {
			var params = decodeURIComponent($("#createlngModuleLessForm").serialize(), true);
			$.ajax({
				type : "POST",
				url : "${ctx}/rule/updateLessRule.do",
				data : params,
				success : function(response) {
					if(response == "success") {
						$.messager.alert('提示', '操作成功', 'info', function() {
							$("#createLessRule_Window").window("close");
							$("#CLRuleTable").datagrid('reload');
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
	<form id="createlngModuleLessForm" action="" method="post">
		    <input type="hidden" name="id" value="${rule.id}"/>
			<table class="add-user" id="createlngModule">
			    <tr>
			    	<td class="m-info-title">起步公里数(km)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id=startKilometre name="startKilometre" class="easyui-numberbox" required="true" precision="2" missingMessage="起步价必须填写" value="${rule.startKilometre}" /></td>
				</tr>
				<tr>
					<td class="m-info-title">起步价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startPrice" name="startPrice" class="easyui-numberbox" required="true" precision="2" missingMessage="起步价必须填写" value="${rule.startPrice}"/></td>
				</tr>
				<tr>
				    <td class="m-info-title">超出价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="outPrice" name="outPrice" class="easyui-numberbox" required="true" precision="2" missingMessage="超出价必须填写" value="${rule.outPrice}"/></td>
				</tr>
				<tr>
			        <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="outfopint" name="outfopint" class="easyui-numberbox" required="true" precision="2" missingMessage="超出点数单价必须填写"  value="${rule.outfopint}"/></td>
			   </tr>
			    <tr>    
			        <td class="m-info-title">保底点数<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fopint" name="fopint" class="easyui-numberbox" required="true"   missingMessage="保底点数必须填写"  value="${rule.fopint}"/></td>
				</tr>
				<tr>
					<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
			    	<td class="m-info-content"><input type="text" id="fadd_service_1"  name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'" value="${protocol.fadd_service_1}" /></td>
			    </tr>
			    <tr>
			    	<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
    				<td class="m-info-content"><input type="text" id="fadd_service_2"  name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'" value="${protocol.fadd_service_2}" /></td>
			    </tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
<%-- 					<input type="text" id="pubRemark" style="width:175px;height:50px;" name="pubRemark" value="${rule.pubRemark}"/> --%>
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:175px;height:50px;resize:none;" >${rule.pubRemark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="Mbutton25 createButton" id="editLessButton">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
