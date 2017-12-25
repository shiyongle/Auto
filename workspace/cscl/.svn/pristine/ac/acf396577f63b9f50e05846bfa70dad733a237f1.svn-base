<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增收款</title>
<script type="text/javascript">
	$(document).ready(function() {
		//下拉框渲染
		$("#fuserId_add").combobox({
			required : true,
			url : "${ctx}/select/getAllConsignor.do?couponsId="+"${recordId}",
			missingMessage : "付款人必须选择",
			validType : "comboRequired",
			invalidMessage : "付款人必须选择",
			editable : true,
			width:100,
			panelHeight:150,
			valueField: 'optionId',
			textField: 'optionName',
			filter:searchItem
		}).combobox('select', "-1");
		
		$("#saveReceivablesButton").click(function() {
			if ($("#createReceivablesForm").form("validate")) {
				var params = decodeURIComponent($("#createReceivablesForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/receipt/saveReceipt.do",
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
	<form id="createReceivablesForm" action="" method="post" style="width:95%;margin:0 auto">
			
			<table>
				<tr>
					<td class="m-info-title">付款人:</td>
					<td class="m-info-content">
					<select id="fuserId_add"  name="fuserId" missingMessage="付款人必须选择" >
							<option value="-1">请选择</option>
					</select>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">金额:</td>
					<td class="m-info-content">
					<input id="famount"  name="famount" style="width: 145px;height: 20px;" class="easyui-numberbox"  precision="1"/>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">收款方式:</td>
					<td class="m-info-content">
					<select id="fuserId_add"  name="fpaymentMethod" class="easyui-combobox">
							<option value="-1">请选择</option>
							<option value="0">承兑汇票</option>
							<option value="1">转账</option>
							<option value="2">现金</option>
							<option value="3">微信</option>
							<option value="4">支付宝</option>
							<option value="5">电汇</option>
					</select>
					</td>
			    </tr>
			    <tr>
			    <td class="m-info-title">备注:</td>
					<td class="m-info-content">
					<input id="fremark"  name="fremark" style="width: 145px;height: 20px;"  precision="1"/>
					</td></tr>
			    <tr>
			    <td colspan="2">
			    <div class="Mbutton25 createButton" id="saveReceivablesButton" align="center">保存</div>
			    </td>
			    </tr>
			</table>
	</form>
</html>
