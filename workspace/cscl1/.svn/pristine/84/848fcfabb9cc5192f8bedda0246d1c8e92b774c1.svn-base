<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>通用司机运价修改</title>
<script>
	$(function(){
		/*9.6米禁用 */
		$('#fadd_service').numberbox({
			required:true,
			validType:['lessOneMillon' , 'greatZero'],
			missingMessage:"增值服务必须填写"
		});		
		/* var type = "${rule.carSpecId}";
		if(type == 6){
			$('#fadd_service').numberbox('disableValidation').numberbox('disable');
		} */
		$('#kilometre5_20_price').numberbox({
			required:true,
			precision:2,
			validType:['lessOneMillon' ,'greatZero'],
			missingMessage:'5-20公里单价必须填写'
		});
		if("${rule.carSpecId}" == 5){
			$('#kilometre5_20_price').numberbox('disableValidation').attr('disabled',true);
		}
		$("#carType").combobox({
			url :"${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "请选择车型",
			validType : "comboRequired",
			invalidMessage : "请选择车型",
			editable : true,
			disabled:true,
			panelHeight:200,
			width:145,
			mode: 'local',
			filter:searchItem,//筛选
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select', "${rule.carSpecId}");
		//保存
		$("#saveButton").click(function() {
			if ($("#editForm").form("validate")) {
				var params = decodeURIComponent($("#editForm").serialize(), true);	
				$.ajax({
					type : "POST",
					url : "${ctx}/rule/updateBillRule.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$('#commonDriverPriceTable').datagrid('reload');
							});
						}
						else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	});
</script>
</head>
	<form id="editForm">
		<input type="hidden" name="id" value="${rule.id}"/>
		<table>
			<tr>
				<td class="m-info-title">车型<span class="red">*</span>:</td>
				<td>
					<select id="carType" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择">
						<option value="-1">请选择</option>
					</select>
				</td>
				<td class="m-info-title">保底价<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice"  name="startPrice" value="${rule.startPrice}" class="easyui-numberbox"  data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'保底价必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">5-20公里单价<span class="red">*</span>:</td>
				<td class="m-info-content">
				<input type="text" name="kilometre5_20_price" id="kilometre5_20_price" style="width: 145px; height: 25px;"   value="${rule.kilometre5_20_price}"/>
				<td class="m-info-title">保底公里<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" readonly ="readonly" type="text" id="startKilometre" name="startKilometre" value="${rule.startKilometre}" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true, missingMessage:'保底公里必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">20-50公里单价<span class="red">*</span>:</td>
				<td class="m-info-content">
					<input type="text" name="kilometre20_50_price" class="easyui-numberbox" style="width: 145px; height: 25px;"  data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'20-50公里单价必须填写'" value="${rule.kilometre20_50_price}"/>
				</td>
				<td class="m-info-title">50公里以上单价<span class="red">*</span>:</td>
				<td class="m-info-content">
					<input type="text" name="kilometre50_price" class="easyui-numberbox" style="width: 145px; height: 25px;"  data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'50公里以上单价必须填写'" value="${rule.kilometre50_price}"/>
				</td>
			</tr>

			<tr>
			    <td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
			    <td class="m-info-content"><input type="text" id="fadd_service_1" style="width: 145px; height: 25px;"  name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'" value="${rule.fadd_service_1}" /></td>
			    <td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
			    <td class="m-info-content"><input type="text" id="fadd_service_2"  style="width: 145px; height: 25px;" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'" value="${rule.fadd_service_2}" /></td>
			</tr>
			<tr>
			    <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
			    <td class="m-info-content"><input type="text" id="outfopint" style="width: 145px; height: 25px;" name="outfopint" value="${rule.outfopint}" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超出点数单价必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">备注:</td>
				<td class="m-info-content" colspan="3"><textarea name="pubRemark" id="pubRemark"  style="width:100%; height:50px; resize:none;" class="easyui-validatebox"  data-options="required:true, missingMessage:'备注必须填写',validType:['maxLength[50]']">${rule.pubRemark}</textarea></td>
			</tr>
			<tr align="right">
				<td colspan="4">
					<div class="Mbutton25 createButton" id="saveButton" >保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>