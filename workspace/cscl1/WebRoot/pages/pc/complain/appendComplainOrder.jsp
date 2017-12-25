<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>追加投诉</title>
<script type="text/javascript">
	$(document).ready(function() {		
		$("#saveAppendComplainButton").click(function() {
			$(this).attr("disabled",true);
			if ($("#appendComplainForm").form("validate")) {
				var params = decodeURIComponent($("#appendComplainForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/usercomplain/addComplainEntry.do",
					data : params,
					success : function(response) {
						var json = JSON.parse(response);
						if(json.flag == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#appendcomplainOrder_Window").window("close");
								$("#complainTable").datagrid('reload');
							});
						}
						else {
							$.messager.alert('提示', json.msg);
						}
					}
				});
			}
		});
	});

</script>
<style>
fieldset.append{margin:10px;border:1px solid #cc0000;float:left}
</style>
</head>
	<form id="appendComplainForm">
		<input  name="fparentid" type="hidden" value="${fparentid}"/>
		<input  name="shipperId" type="hidden" value="${shipperId}"/>
		<input  name="driverId" type="hidden" value="${driverId}"/>
		<table>
				<tr>
					<td class="m-info-title">订单号:</td>
					<td colspan="3" class="m-info-content">${forderNum}</td>
			    </tr>
		</table>
		
		<!--司机 -->
		<fieldset class="append">
		<legend><strong><h3>司机追加</h3></strong></legend>
		<table>
				<tr>
					<td class="m-info-title">司机:</td>
					<td class="m-info-content">${fdriverName}</td>		
				</tr>
				<tr>
					<td class="m-info-title">扣除金额：</td>
					<td class="m-info-content">
					<input type="text" class="easyui-numberbox" precision="2" id="fdriverAddFAmount" name="fdriverAddFAmount" style="width:140px"/>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">返还金额:</td>
					<td class="m-info-content">
					<input type="text" class="easyui-numberbox" precision="2" id="fdriverAddRAmount" name="fdriverAddRAmount" style="width:140px"/>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content">
					<textarea type="text"  id="fdriverAddRemark" name="fdriverAddRemark" style="width:135px;height:40px;resize:none;border:1px solid #cc;"></textarea>
					</td>
			    </tr>
			</table>
		</fieldset>
		
		<!--货主 -->
		<fieldset class="append">
		<legend><strong><h3>货主追加</h3></strong></legend>
		<table>
				<tr>	
			    	<td class="m-info-title">货主:</td>
					<td class="m-info-content">${fuserName}</td>
				</tr>
				<tr>
					<td class="m-info-title">扣除金额：</td>
					<td class="m-info-content">
					<input type="text" class="easyui-numberbox" precision="2" id="fuserAddFAmount" name="fuserAddFAmount" style="width:140px"/>
					</td>
			    </tr>
			    <tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content">
					<textarea type="text"  id="fuserRemark" name="fuserAddRemark" style="width:135px;height:40px;resize:none;border:1px solid #cc;"></textarea>
					</td>
			    </tr>
			    <tr>
					<td height="30"></td>
			    </tr>
			</table>
		</fieldset>
		<button class="Mbutton25 createButton" id="saveAppendComplainButton" style="margin-right:20px;border:0px" align="center">保存</button>
	</form>
</html>
