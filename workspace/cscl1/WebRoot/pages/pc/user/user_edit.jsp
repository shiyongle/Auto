<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改用户</title>
<script type="text/javascript">
	function update() {

		if ($("#createUserForm").form("validate")) {
			var params = decodeURIComponent($("#createUserForm").serialize(),
					true);
			$.ajax({
				type : "POST",
				url : "${ctx}/user/updateAccount.do",
				data : params,
				success : function(response) {
					if (response == "success") {
						$.messager.alert('提示', '操作成功', 'info', function() {
							$("#user_Window").window("close");
							$("#CLUserTable").datagrid('reload');
						});
					} else {
						$.messager.alert('提示', "操作失败！");
					}
				}
			});
		}
	}
	$(document).ready(function() {
		$.extend($.fn.validatebox.defaults.rules, {
			Number : {//value值为文本框中的值  
				validator : function(value) {
					var reg = /^(\d{13}|\d{16}|\d{19})$/;
					return reg.test(value);
				},
				message : '请输入正确的银行卡号...'
			}
		});
	});
</script>
</head>
<div class="content">
	<form method="post" id="createUserForm" action="">
		<input type="hidden" name="id" value="${role.id}" />
		<table>
			<tr>
				<td class="m-info-title" style="width: 100px;">支付宝帐号：</td>
				<td><input class="m-info-content" size="30" type="text"
					name="alipayId" value="${role.alipayId }" /></td>
			</tr>
			<tr>
				<td class="m-info-title">银行卡帐号：</td>
				<td><input size="30" type="text" id="bankAccount"
					name="bankAccount" value="${role.bankAccount }"
					class="easyui-validatebox textbox"
					data-options="validType:'Number'" /></td>
			</tr>
			<tr>
				<td class="m-info-title">开户行：</td>
				<%-- 				<td><input class="m-info-content" size="30" type="text" name="bankName" value="${role.bankName }"/></td> --%>
				<td><input value="${role.bankName }" name="bankName" class="easyui-combobox" panelHeight="200"
					data-options="valueField: 'label',textField: 'value',
					data: [{label: '工商银行',value: '工商银行'},
							{label: '光大银行',value: '光大银行'},
							{label: '华夏银行',value: '华夏银行'},
							{label: '建设银行',value: '建设银行'},
							{label: '交通银行',value: '交通银行'},
							{label: '民生银行',value: '民生银行'},
							{label: '农业银行',value: '农业银行'},
							{label: '平安银行',value: '平安银行'},
							{label: '浦发银行',value: '浦发银行'},
							{label: '上海银行',value: '上海银行'},
							{label: '兴业银行',value: '兴业银行'},
							{label: '中国银行',value: '中国银行'},
							{label: '中国邮政储蓄银行',value: '中国邮政储蓄银行'},
							{label: '中信银行',value: '中信银行'},
							{label: '广东农村信用社',value: '广东农村信用社'},
							{label: '杭州银行',value: '杭州银行'},
							{label: '江苏银行',value: '江苏银行'},
							{label: '深圳农村商业银行',value: '深圳农村商业银行'},
							{label: '渤海银行',value: '渤海银行'},
							{label: '北京银行',value: '北京银行'},
							{label: '广发银行',value: '广发银行'},
							{label: '徽商银行',value: '徽商银行'},
							{label: '上海农商行',value: '上海农商行'},
							{label: '北京农商行',value: '北京农商行'},
							{label: '重庆银行',value: '重庆银行'}]" />
				</td>
			</tr>
			<tr class="m-info-title" style="height: 0px;">
				<td>开户行所在地：</td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td><textarea maxlength="100" cols="35" rows="5"
						name="bankAddress">${role.bankAddress }</textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="Mbutton25 createButton" id="editUserButton1"
						onclick="update()">保存</div>
				</td>
			</tr>
		</table>
	</form>
</div>

</html>