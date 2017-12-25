<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新建</title>
<script>
	$(function(){
		//司机名
		$("#userName").combobox({
			url :"${ctx}/select/getAllCus.do",
			required : true,
			missingMessage : "请选择客户名",
			validType : "comboRequired",
			invalidMessage : "请选择客户名",
			editable : true,
			panelHeight:200,
			width:145,
			mode: 'local',
			filter:searchItem,//筛选
			valueField: 'optionId',
			textField: 'optionName'/* ,
			onSelect:function(record){
				$('#carType').val(record.optionWe);
				$('#carTypeId').val(record.optionFc);
			} */
		});
		$("#carSpecId").combobox({
			required : true,
			missingMessage : "请选择车型",
			validType : "comboRequired",
			invalidMessage : "请选择车型",
			editable : false,
			panelHeight:200,
			width:145,
			onSelect:function(rec){
				if(rec.value == '6'){
					$('#fadd_service').numberbox('disableValidation').numberbox('disable').numberbox('clear');
				}else{
					$('#fadd_service').numberbox('enableValidation').numberbox('enable');
				}
			} 
		})
		//保存按钮
		$("#saveButton").click(function() {
			if ($("#editForm").form("validate")){
				var params = decodeURIComponent($("#editForm").serialize(), true);
				console.log(params)
				$.ajax({
					type : "POST",
					url : "${ctx}/rule/saveDriverBill.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$('#freightSettingTable').datagrid('reload');
							});
						}else if(response == "failure"){
							$.messager.alert('提示', "操作失败,该车型规则已存在！");
						}else{
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	})
</script>
</head>
	<form id="editForm">
		<table>
			<tr>
				<td class="m-info-title">客户名<span class="red">*</span>:</td>
				<td>
					<select id="userName" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="客户名必须选择">
							<option value="-1">请选择</option>
					</select>
				</td>
			</tr>
			<!-- <tr>
				<td class="m-info-title">车型<span class="red">*</span>:</td>
				<td>
					<input  id="carType" style="width: 143px; height: 22px; outline:none; border:1px solid rgb(149, 184, 231);" type="text" readonly="readonly" />
					<input name="carSpecId" type="hidden" id="carTypeId">
				</td>
			</tr> -->
			<tr>
				<td class="m-info-title">车型<span class="red">*</span>:</td>
				<td>
					<select id="carSpecId" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="客户名必须选择">
							<option value="-1">请选择</option>
							<option value="1">面包车</option>
							<option value="2">2.5米</option>
							<option value="3">4.2米</option>
							<option value="4">5.2米</option>
							<option value="5">6.8米</option>							
							<option value="6">9.6米</option>
					</select>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">保底公里<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底公里必须填写"/></td>
			</tr>
			<tr>
				<td class="m-info-title">保底价<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价必须填写"/></td>
			</tr>
			<tr>
				<td class="m-info-title">超过公里/单价<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outKilometre" name="outKilometre" class="easyui-numberbox"  required="true" precision="2"  missingMessage="超过公里/单价必须填写"/></td>
			</tr>
			<tr>
			    <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
				<td class="m-info-content"><input type="text" id="outfopint" style="width: 145px; height: 25px;" name="foutopint" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超出点数单价必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
				<td class="m-info-content"><input type="text" id="fadd_service_1" style="width: 145px; height: 25px;" name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
				<td class="m-info-content"><input type="text" id="fadd_service_2"  style="width: 145px; height: 25px;" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">备注:</td>
				<td class="m-info-content"><textarea name="pubRemark" id="pubRemark" style="width:145px; height:50px; resize:none;"></textarea></td>
			</tr>
			<tr align="right">
				<td colspan="2">
					<div class="Mbutton25 createButton" id="saveButton" >保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>