<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改零担规则</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#userRoleId_edit2").combobox({
			url : "${ctx}/select/getByRoleId.do",
			required : true,
			missingMessage : "请选择用户",
			validType : "comboRequired",
			invalidMessage : "请选择用户",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select', "${protocol.userRoleId}").combobox('disable');
		
		
		/**协议用车零担单位类型*/
		$("#otherType1").combobox({
			url : "${ctx}/select/getByOtherTypeByUnit.do",
			required : true,
			missingMessage : "全部",
			validType : "comboRequired",
			invalidMessage : "全部",
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
		}).combobox('select',"${protocol.funitId}");
		
		$("#saveLDButton").click(function() {
			if ($("#editLessForm").form("validate")) {
				var params = decodeURIComponent($("#editLessForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/updateLessRule.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createLessRule_Window").window("close");
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
	<form id="editLessForm" >
	<input type="hidden" name="id" value="${protocol.id}"/>
			<table class="add-user" id="editLess">
				<tr>
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="userRoleId_edit2" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择">
						 <option value="-1">请选择</option>
					</select>
					</td>
					<td class="m-info-title">保底价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" value="${protocol.startPrice}" type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价格必须填写"/></td>
			    </tr>
				<tr>		       

					<td class="m-info-title">单位类型<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="otherType1" style="width: 145px; height: 25px;" name="funitId" missingMessage="单位类型必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>
					<td class="m-info-title">超出数量价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" value="${protocol.outNumprice}" id="outNumprice" name="outNumprice" class="easyui-numberbox" required="true" precision="2" missingMessage="超出数量价格必须填写"/></td>	
			    </tr>
			    <tr>
					<td class="m-info-title">超出公里单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" value="${protocol.outKilometre}" id="outKilometre" name="outKilometre" class="easyui-numberbox" required="true" precision="2"   missingMessage="超出公里单价必须填写"/></td>		
					<td class="m-info-title">保底公里<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" value="${protocol.startKilometre}" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2"   missingMessage="保底公里必须填写"/></td>								
				</tr>
				<tr>
				    <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
				    <td class="m-info-content"><input type="text" id="foutopint" name="foutopint" class="easyui-numberbox" required="true" precision="2" missingMessage="超出点数单价必须填写"  value="${protocol.foutopint}"/></td>
				</tr>
				<tr>
					<td class="m-info-title">保底数量<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" value="${protocol.startNumber}" id="startNumber" name="startNumber" class="easyui-numberbox" required="true" precision="2"  missingMessage="保底数量必须填写"/></td>									
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
						<div class="Mbutton25 createButton" id="saveLDButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
