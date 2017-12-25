<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>开票</title>
<script type="text/javascript">
	$(document).ready(function() {
		//开票员下拉框渲染
		$("#billingMan_add").combobox({
			required : true,
			url : ""+"",
			missingMessage : "开票员必须选择",
			validType : "comboRequired",
			invalidMessage : "开票员必须选择",
			editable : true,
			width:145,
			panelHeight:150,
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('select', "-1");
		
		$("#saveBillingButton").click(function() {
			if ($("#createBillingForm").form("validate")) {
				var params = decodeURIComponent($("#createBillingForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createReceivables_Window").window("close");
								$("#receivablesTable").datagrid('reload');
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
	<form id="createBillingForm" action="" method="post" style="width:95%;margin:0 auto">
			
			<table>
				<tr>
					<td class="m-info-title">开票员:</td>
					<td class="m-info-content">
					<select id="billingMan_add"  name="billingMan" missingMessage="开票员必须选择" >
							<option value="-1">请选择</option>
					</select>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">开票金额:</td>
					<td class="m-info-content">
					<input id="billingMoney_add"  name="billingMoney" style="width: 145px;height: 20px;" class="easyui-numberbox"  precision="1"/>
					</td>
			    </tr>
			    <tr>
			    <td colspan="2">
			    <div class="Mbutton25 createButton" id="saveBillingButton" align="center">保存</div>
			    </td>
			    </tr>
			</table>
	</form>
</html>
