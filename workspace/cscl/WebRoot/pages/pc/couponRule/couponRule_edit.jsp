<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加用户</title>
<script type="text/javascript">
	$(document).ready(function() {
		/***增加*/
		$("#submit").click(function() {
			if ($("#TForm").form("validate")) {
				var params = decodeURIComponent($("#TForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/couponRule/updata.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createCouponRule_Window").window("close");
								$("#CLCouponRuleTable").datagrid('reload');
							});
						}else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
	});
</script>
</head>
<div  id="createCouponRule_Window"> </div>
	<form id="TForm"  >
	       <input type="hidden" name="id" value="${couponRule.id}"/>
			<table class="add-user" id="createBursarInfo">
				<tr>
					<td class="m-info-title">面额<span class="red">*</span>:</td>
					<td class="m-info-content"><input  type="text" name="dollars"  class="easyui-numberbox" required="true" missingMessage="面额必须填写" value="${couponRule.dollars}"/> </td>
				</tr>
				<tr>
					<td class="m-info-title">比面额<span class="red">*</span>:</td>
					<td class="m-info-content"><input  type="text" name="compareDollars"  class="easyui-numberbox" required="true" missingMessage="比面额必须填写" value="${couponRule.compareDollars}"/> </td>
				</tr>
				<tr>
				    <td class="m-info-title">消费面额<span class="red">*</span>:</td>
					<td class="m-info-content"> <input  type="text" name="consumption" class="easyui-numberbox" required="true" missingMessage="消费面额必须填写"  value="${couponRule.consumption}"/></td>
			
				</tr>
			</table>
	      	<div class="Mbutton25 createButton" id="submit"> 保存</div>
	</form>
</html>
