<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新建</title>
<script>
	$(function(){
		/*9.6米禁用 */
		$('#fadd_service').numberbox({
			required:true,
			validType:['lessOneMillon' , 'greatZero'],
			missingMessage:"增值服务必须填写"
		});		
		var type = "${protocol.carSpecId}";
		if(type == 6){
			$('#fadd_service').numberbox('disableValidation').numberbox('disable');
		}
		
		//保存按钮
		$("#saveButton").click(function() {
			if ($("#editForm").form("validate")) {
				var params = decodeURIComponent($("#editForm").serialize(), true);	
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/updateDriverProtocol.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$('#freightSettingTable').datagrid('reload');
							});
						}
						else {
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
		<input type="hidden" name="id" value="${protocol.id}"/>
		 <input type="hidden" name="userRoleId" value="${protocol.userRoleId} "/>
		<table>
			<tr>
				<td class="m-info-title">客户名<span class="red">*</span>:</td>
				<td>
					<input style="width: 145px; height: 25px;" type="text" class="easyui-validatebox" required="true" id="cusName" name="cusName" disabled="true" value="${protocol.cusName }" />
				</td>
			</tr>
			<tr>
				<td class="m-info-title">车型<span class="red">*</span>:</td>
				<td>
					<input style="width: 145px; height: 25px;" type="text"  class="easyui-validatebox" required="true"id="carSpecName" name="carSpecName" disabled="true" value="${protocol.carSpecName}"/>
				</td>
			</tr>
			<tr>
				<td class="m-info-title">保底公里<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startKilometre" name="startKilometre" value="${protocol.startKilometre }" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'保底公里必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">保底价<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice" name="startPrice" value="${protocol.startPrice }" class="easyui-numberbox"  data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'保底价必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">超过公里/单价<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outKilometre" name="outKilometre" value="${protocol.outKilometre }" class="easyui-numberbox"  data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超过公里/单价必须填写'"/></td>
			</tr>
			<tr>
			    <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
				<td class="m-info-content"><input type="text" id="outfopint" style="width: 145px; height: 25px;" name="foutopint" value="${protocol.foutopint}" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'超出点数单价必须填写'"/></td>
			</tr>
			<tr>
				<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
		    	<td class="m-info-content"><input type="text" id="fadd_service_1" style="width: 145px; height: 25px;" name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'" value="${protocol.fadd_service_1}" /></td>
		    </tr>
		    <tr>
		    	<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
    			<td class="m-info-content"><input type="text" id="fadd_service_2"  style="width: 145px; height: 25px;" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'" value="${protocol.fadd_service_2}" /></td>
		    </tr>
			<tr>
				<td class="m-info-title">备注:</td>
				<td class="m-info-content"><textarea name="pubRemark" id="pubRemark" style="width:145px; height:50px; resize:none;">${protocol.pubRemark } </textarea></td>
			</tr>
			<tr align="right">
				<td colspan="2">
					<div class="Mbutton25 createButton" id="saveButton" >保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>