<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增客户资料</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#ftype").combobox({
			width:145,
			required : true,
			missingMessage:"客户类型必须选择",
			invalidMessage : "客户类型必须选择",//无效提示信息
			validType : "comboRequired",
			panelHeight:"auto"//高度自适应
		});
		
		$("#fsalesManId").combobox({
			url : "${ctx}/user/queryCarUser.do",
			required : true,
			missingMessage : "请选择业务员",
			validType : "comboRequired",
			invalidMessage : "请选择业务员",
			editable : true,
			width:145,
			//mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName',
			onSelect : function(record){
				if(record.optionName){
					$("#fsalesMan").val(record.optionName);	
				}				
			}
		});

		$("#saveKHZLButton").click(function() {
			if ($("#createlngModuleForm").form("validate")) {
				var params = decodeURIComponent($("#createlngModuleForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/usercustomer/saveCustomer.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createCustomer_Window").window("close");
								$("#CLCustomerTable").datagrid('reload');
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
	<form id="createlngModuleForm" action="" method="post">
			<input type="hidden" id="fsalesMan" name="fsalesMan"  />
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">客户编号:<input type="hidden" name="fnumber" id="fnumber" value="${custinfo.fnumber}"/></td>
					<td class="m-info-content" id="td_fnumber">${custinfo.fnumber}</td>
			    </tr>
				<tr>
					<td class="m-info-title">客户类型<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="ftype" style="width: 145px; height: 25px;" name="ftype" missingMessage="客户类型必须选择" >
							<option value="-1">请选择</option>
							<option value="B2B物流">B2B物流</option>
							<option value="快递物流">快递物流</option>
							<option value="商超">商超</option>
					</select>		
					<td class="m-info-title">客户名称<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" id="fname" name="fname"  class="easyui-validatebox"  required="true"   missingMessage="客户名称必须填写"  />
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">客户所在区域<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" id="fregion" name="fregion" class="easyui-validatebox"  required="true"   missingMessage="客户所在区域必须填写" />
					</td>
					<td class="m-info-title">业务员<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="fsalesManId" style="width: 145px; height: 25px;" name="fsalesMan" missingMessage="业务员必须选择" panelHeight="200">
							<option value="-1">请选择</option>
					</select>
					</td>		
					
			    </tr>
				<tr>
					<td class="m-info-title">业务员所在部门<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" id="fsalesManDept" name="fsalesManDept" class="easyui-validatebox"  required="true"   missingMessage="业务员所在部门必须填写" />
					</td>
					<td class="m-info-title">结算周期<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" id="fsettleCycle" name="fsettleCycle" class="easyui-validatebox"  required="true"   missingMessage="结算周期必须填写" />
					</td>
				</tr>
				<tr>
					<td class="m-info-title">客户相关注意事项:</td>
					<td class="m-info-content" colspan="3">
					<textarea id="fattention" name="fattention" style="width:400px;height:80px;resize:none;"></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveKHZLButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
