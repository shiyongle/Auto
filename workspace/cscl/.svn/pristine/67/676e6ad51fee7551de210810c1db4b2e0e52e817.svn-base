<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加用户</title>
<script type="text/javascript">
	$(document).ready(function() {
	    //规格
	    
		$("#carSpecId1").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "请选择车厢",
			validType : "comboRequired",
			invalidMessage : "请选择车厢",
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
			onSelect : function(record){
				$("#carTypeId1").combobox({
				            disabled: false,
				            url : "${ctx}/select/getAllCarSpecByCarType.do?optionTemp="+record.optionId,
				            valueField: 'optionId',
				            textField: 'optionName'
			    }).combobox('select', "-1");
			    $("#driverId").combobox('select', "-1");
		   }
		});
		
		$("#carTypeId1").combobox({
			width:110,
			valueField:'id',    
		    textField:'text',
			missingMessage : "请选择车型",
			validType : "comboRequired",
			invalidMessage : "请选择车型",
			editable : true,
			onSelect : function(record){
				$("#driverId").combobox({
				            disabled: false,
				            url : "${ctx}/select/getDriverByCarTypeId.do?optionTemp="+record.optionId+"&carSpecId="+$("#carSpecId1").combobox('getValue'),
				            valueField: 'optionId',
				            textField: 'optionName'
			    }).combobox('select', "-1");
		   }
		});
		$("#driverId").combobox({
			width:110,
			valueField:'id',    
		    textField:'text',
			missingMessage : "请选择司机",
			validType : "comboRequired",
			invalidMessage : "请选择司机",
			editable : true,
			panelHeight:200
		});
		
		$("#saveButtonDriver").click(function() {
			/* var params = decodeURIComponent($("#TFormsss").serialize(), true); */
			if ($("#TFormsss").form("validate")) {
				$("#createWindow").mask();
				$.ajax({
					type : "POST",
					url : "${ctx}/order/assign.do",
					data :  {
						"userRoleId":$("#driverId").combobox('getValue'),
						"freturn_car":$('input[type="radio"]:checked').val()
					},
					dataType:"json",
					success : function(response) {
						if(response.success == "true") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$("#createWindow").mask("hide");
								$("#CLOrderTable").datagrid('reload');
							});
						}else {
							$("#createWindow").mask("hide");
							$.messager.alert('提示',response.msg);
						}
					}
				});
			}
		});
		
	});
</script>
</head>
<div  id="createWindow"> </div>
	<form id="TFormsss"  >
			<table class="add-user" id="createBursarInfo">
				<tr>
					<td class="m-info-title">车厢<span class="red">*</span>:</td>
		            <td><select id="carSpecId1" name="carSpecId"><option value="-1">请选择</option></select></td>
				</tr>
				<tr>
					<td class="m-info-title">车型<span class="red">*</span>:</td>
		            <td><select id="carTypeId1" name="carTypeId"><option value="-1">请选择</option></select></td>
				</tr>
				<tr>
					<td class="m-info-title">司机<span class="red">*</span>:</td>
		            <td><select id="driverId" name="driverId"><option value="-1">请选择</option></select></td>
				</tr>
				<tr>
					<td class="m-info-title">是否回程车<span class="red">*</span>:</td>
					<td>
						否<input name="freturn_car" type="radio" value="2">
						是<input name="freturn_car" type="radio" value="1">
					</td>
				<tr>
					<td colspan="2"><div class="easyui-linkbutton createButton" id="saveButtonDriver">保存</div></td>
			    </tr>
			</table>
	</form>
</html>
