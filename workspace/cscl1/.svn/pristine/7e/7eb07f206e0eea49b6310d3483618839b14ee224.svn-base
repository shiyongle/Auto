<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改整车规则</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userRoleId_edit1").combobox({
			url :"${ctx}/select/getByRoleId.do",
			required : true,
			missingMessage : "请选择用户",
			validType : "comboRequired",
			invalidMessage : "请选择用户",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select',"${protocol.userRoleId}").combobox('disable');
 
		$("#carSpecId").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "全部",
			validType : "comboRequired",
			invalidMessage : "全部",
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
		}).combobox('select',"${protocol.carSpecId}");
		
		$("#editZCButton").click(function() {
			if ($("#editCarForm").form("validate")) {
				var params = decodeURIComponent($("#editCarForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/updateCarXY.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createCarloadRule_Window").window("close");
								$("#CLProtocolTable").datagrid('reload');
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
	<form id="editCarForm"  >
	<input type="hidden" name="id" value="${protocol.id}"/>
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="userRoleId_edit1" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="carSpecId" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>		
			    	<td class="m-info-title">保底价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" value="${protocol.startPrice}" type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价格必须填写"/></td>
			
			    </tr>
				<tr>
				    <td class="m-info-title">公里单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outKilometre" name="outKilometre" class="easyui-numberbox" required="true" precision="2"  missingMessage="公里单价必须填写" value="${protocol.outKilometre}"/></td>
					<td class="m-info-title">保底公里<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" value="${protocol.startKilometre}" type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2"  missingMessage="保底公里必须填写" /></td>
				</tr>
				</tr>
					<tr>
			        <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="foutopint" name="foutopint" class="easyui-numberbox" required="true" precision="2" missingMessage="超出点数单价必须填写"   value="${protocol.foutopint}"/></td>
			        <td class="m-info-title">折扣<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fdiscount" name="fdiscount" class="easyui-numberbox"     missingMessage="保底点数必须填写"   required="true"  precision="2" missingMessage="折扣百分比必须填写" style="width: 40%" value="${protocol.fdiscount}"/>%</td>
				</tr>
				<tr>
				    <td class="m-info-title">保底点数<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fopint" name="fopint" class="easyui-numberbox" required="true"    missingMessage="保底点数必须填写"   value="${protocol.fopint}"/></td>
				</tr>
				<tr>
				    <td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
				    <td class="m-info-content"><input type="text" id="fadd_service_1"  name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'" value="${protocol.fadd_service_1}" /></td>
				    <td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
				    <td class="m-info-content"><input type="text" id="fadd_service_2"  name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'" value="${protocol.fadd_service_2}" /></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:80px;resize:none;" >${protocol.pubRemark}</textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="editZCButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
