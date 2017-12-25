<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增规则</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#carSpecId").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "请选择车辆规格",
			validType : "comboRequired",
			invalidMessage : "请选择车辆规格",
			editable : true,
			width:175,
			mode: 'remote',
			editable:false,
			valueField: 'optionId',
			textField: 'optionName',
			onSelect:function(rec){
				if(rec.optionId == '3'){
					$('#startKilometre').numberbox('disableValidation').attr('readonly',true).numberbox('setValue','5');
						$('#kilometre5_20_price').numberbox('enableValidation').attr('disabled',false);
				}else if(rec.optionId == '5'){
					$('#startKilometre').numberbox('disableValidation').attr('readonly',true).numberbox('setValue','20');
					$('#kilometre5_20_price').numberbox('disableValidation').attr('disabled',true).numberbox('clear');
				}else{
					$('#startKilometre').numberbox('enableValidation').removeAttr('readonly').numberbox('clear'); 
					$('#kilometre5_20_price').numberbox('enableValidation').attr('disabled',false);
				}				
			}
		});
		
		$("#saveZCButton").click(function() {
			if ($("#createlngModuleForm").form("validate")) {
				var params = decodeURIComponent($("#createlngModuleForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/rule/save.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createCarloadRule_Window").window("close");
								$("#CLRuleTable").datagrid('reload');
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
	});
</script>
</head>
	<form id="createlngModuleForm" action="" method="post">
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content">
						<select  id="carSpecId" name="carSpecId" >
							<option value="-1">请选择</option>
						</select>
					</td>
			        <td class="m-info-title">起步公里数 (km)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2" missingMessage="起步公里数必须填写"/></td>
				</tr>
				<tr>
					<td class="m-info-title">起步价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'起步价必须填写'" /></td>
			        <td class="m-info-title">5-20公里单价<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input type="text" name="kilometre5_20_price"  id="kilometre5_20_price"class="easyui-numberbox"  data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'5-20公里单价必须填写'"/>
					</td>
				</tr>
				<tr>
					<td class="m-info-title">20-50公里单价<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input type="text" name="kilometre20_50_price" class="easyui-numberbox"    data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'20-50公里单价必须填写'"/>
					</td>
					<td class="m-info-title">50公里以上单价<span class="red">*</span>:</td>
					<td class="m-info-content">
						<input type="text" name="kilometre50_price" class="easyui-numberbox"    data-options="required:true,precision:2,validType:['lessOneMillon' ,'greatZero'],missingMessage:'50公里以上单价必须填写'"/>
					</td>
				</tr>
				<tr>
			        <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="outfopint" name="outfopint" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超出点数单价必须填写'"/></td>
				</tr>
				<tr>
					<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_1" name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'"/></td>
					<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_2" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'"/></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:50px;resize:none;" ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveZCButton">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
