<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改客户资料</title>
<script type="text/javascript">
	$(document).ready(function() {
		
		$("#fsalesManId").combobox({
			url : "${ctx}/user/queryCarUser.do",
			required : true,
			missingMessage : "请选择业务员",
			validType : ["comboxValidate['fsalesManId']",'comboRequired'],
			editable : true,
			width:145,
			//mode: 'remote',
			valueField: 'optionId',//提交值
			textField: 'optionName',//显示值
			onBeforeLoad: function(param){
				param.roleid = 3; 	
			},
			onSelect : function(record){
				if(record.optionName){
					$("#fsalesMan").val(record.optionName);	
				}				
			}
		}).combobox("setValue","${custinfo.fsalesManId}");//setValue一定要用这个方法,用select会触发onselect事件导致报错

		$("#ftype").combobox({
			width:145,
			required : true,
			missingMessage:"客户类型必须选择",
			invalidMessage : "客户类型必须选择",
			validType : "comboRequired"
		}).combobox('select','${custinfo.ftype}');
		
		/*判断客户的类型*/
// 		$("#ftype").val("${custinfo.ftype}");
		
		$("#saveKHZLButton").click(function() {		
			if ($("#createlngModuleForm").form("validate")) {
				var params = decodeURIComponent($("#createlngModuleForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/usercustomer/updateCustomer.do",
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
	        <input type="hidden" name="fid" value="${custinfo.fid}" />
	        <input type="hidden" id="fsalesMan" name="fsalesMan" value="${custinfo.fsalesMan}" />
			<table class="add-user" id="createlngModule">
				<tr>
					<td class="m-info-title">客户编号:</td>					
					<td class="m-info-content">										
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fnumber}"  id="fnumber" name="fnumber"  class="easyui-validatebox"  readonly="readonly" />
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">客户类型<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="ftype" style="width: 145px; height: 25px;" name="ftype" missingMessage="客户类型必须选择">
							<option value="-1">请选择</option>
							<option value="快递公司">快递公司</option>
							<option value="物流公司">物流公司</option>
							<option value="商 超">商超</option>
							
							<option value="民营企业">民营企业</option>
							<option value="纸板团购">纸板团购</option>
							<option value="包装类">包装类</option>
							<option value="其 它">其它</option>
					</select>
					</td>	
					<td class="m-info-title">客户名称<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fname}"  id="fname" name="fname"  class="easyui-validatebox"  required="true"   missingMessage="客户名称必须填写" />
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">客户简称<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fsimplename}" id="" name="fsimplename" class="easyui-validatebox"  required="true"   missingMessage="客户简称必须填写" />
					</td>
					<td class="m-info-title">客户所在区域<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fregion}" id="fregion" name="fregion" class="easyui-validatebox"  required="true"   missingMessage="客户所在区域必须填写" />
					</td>						
			    </tr>
				<tr>
					<td class="m-info-title">业务员<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="fsalesManId" style="width: 145px; height: 25px;"  name="fsalesManId" missingMessage="业务员必须选择" panelHeight="200">
							<option value="-1">请选择</option>
					</select>
					</td>	
					<td class="m-info-title">业务员所在部门<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fsalesManDept}" id="fsalesManDept" name="fsalesManDept" class="easyui-validatebox"  required="true"   missingMessage="业务员所在部门必须填写" />
					</td>
				</tr>
				<tr>
					<td class="m-info-title">结算周期<span class="red">*</span>:</td>
					<td class="m-info-content" colspan="3">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${custinfo.fsettleCycle}" id="fsettleCycle" name="fsettleCycle" class="easyui-validatebox"  required="true"   missingMessage="结算周期必须填写" />
					</td>
				</tr>
				<tr>
					<td class="m-info-title">客户相关注意事项:</td>
					<td class="m-info-content" colspan="3">
					<textarea id="fattention" name="fattention" style="width:400px;height:80px;resize:none;">${custinfo.fattention}</textarea>
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
