<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增零担规则</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		$("#userRoleId2").combobox({
// 			url : "${ctx}/select/getByRoleId.do",
// 			required : true,
// 			missingMessage : "请选择用户",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择用户",
// 			editable : true,
// 			width:145,
// 			//mode: 'remote',
// 			valueField: 'optionId',
// 			textField: 'optionName'
// 		});
		
    	$("#userRoleId2").combogrid({
			panelWidth: 250,
			panelHeight: 300,
			idField: 'id',//提交值
			textField: 'name',//选中显示的值
			required : true,
			missingMessage : "请选择客户名称",
			validType : "comboRequired",
			invalidMessage : "请选择客户名称",
			url: '${ctx}/user/combogrid.do',
			method: 'get',
			multiple: false,//可选择多行
			mode: 'remote',
// 			filter: function(q, row){
//  				var opts = $(this).combogrid('options');
//  				if(row.phone.indexOf(q)==0){
//  					return true;
//  				}
// 				return row[opts.textField].indexOf(q) == 0;
// 			},
			columns: [[
				{field:'id',title:'id',width:80,align:'center',hidden : true},
				{field:'name',title:'用户名',width:125,align:'center'},
				{field:'phone',title:'手机号',width:125,align:'center'}
			]],
			fitColumns: true//true表示所有列长度会适应panelWidth的长
		});
		
		/**协议用车零担单位类型*/
		$("#funitId").combobox({
			url : "${ctx}/select/getByOtherTypeByUnit.do",
			required : true,
			missingMessage : "请选择单位类型",
			validType : "comboRequired",
			invalidMessage : "请选择单位类型",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName',
		});
		
		$("#saveLDButton").click(function() {
			if ($("#createLDForm").form("validate")) {
				var params = decodeURIComponent($("#createLDForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/saveLessRule.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createLessRule_Window").window("close");
								$("#CLProtocolTable").datagrid('reload');
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
	<form id="createLDForm" action="" method="post">
			<table class="add-user" id="createlngModule2">
				<tr>
					<td class="m-info-title">用户<span class="red">*</span>:</td>
					<td class="m-info-content">
<!-- 					<select id="userRoleId2" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择" panelHeight="200"> -->
<!-- 							<option value="-1">请选择</option> -->
<!-- 					</select> -->
					<select id="userRoleId2" style="width: 145px; height: 25px;" name="userRoleId" >
					</select>
					</td>
					<td class="m-info-title">保底价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价格必须填写"/></td>
			    </tr>
				<tr>		       

					<td class="m-info-title">单位类型<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="funitId" style="width: 145px; height: 25px;" name="funitId" missingMessage="单位类型必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>
					<td class="m-info-title">超出数量价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outNumprice" name="outNumprice" class="easyui-numberbox" required="true" precision="2" missingMessage="超出数量价格必须填写"/></td>	
			    </tr>
			    <tr>
					<td class="m-info-title">超出公里单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outKilometre" name="outKilometre" class="easyui-numberbox" required="true" precision="2"   missingMessage="超出公里单价必须填写"/></td>		
					<td class="m-info-title">保底公里<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2"   missingMessage="保底公里必须填写"/></td>								
				</tr>
				<tr>
				  <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
				  <td class="m-info-content"><input type="text" id="foutopint" name="foutopint" class="easyui-numberbox" required="true" precision="2" missingMessage="超出点数单价必须填写"  /></td>
				  <td class="m-info-title">保底数量<span class="red">*</span>:</td>
				  <td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startNumber" name="startNumber" class="easyui-numberbox" required="true" precision="2"  missingMessage="保底数量必须填写"/></td>									
				</tr>
				<tr>
					<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_1" style="width: 145px; height: 25px;" name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'"/></td>
					<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_2" style="width: 145px; height: 25px;" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'"/></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:80px;resize:none;" ></textarea>
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
