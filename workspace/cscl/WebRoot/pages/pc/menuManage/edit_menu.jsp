<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>增加菜单管理</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#fparentid").combobox({
			url : "${ctx}/menu/getAllParentMenus.do",
			required : true,
// 			missingMessage : "请选择一级菜单",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择一级菜单",
			editable : true,
			width:145,
			mode: 'remote',
			valueField: 'optionId',
			textField: 'optionName'
		}).combobox('setValue','${menu.fparentid}');
		
		$("#saveMenuButton").click(function() {
			if ($("#editMenuForm").form("validate")) {
				var params = decodeURIComponent($("#editMenuForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/menu/update.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createMenu_Window").window("close");
								$("#menuManageTable").datagrid('reload');
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
	<form id="editMenuForm" action="" method="post">
	<input type="hidden" name="fid" id="fid" value="${menu.fid}"/>
			<table class="add-user" id="createlngModule">			
				<tr>
					<td class="m-info-title">一级菜单:<input type="hidden" name="fisleaf" id="fisleaf" value="${menu.fisleaf}"/></td>
					<td class="m-info-content">
					<select id="fparentid" style="width: 145px; height: 25px;" name="fparentid" missingMessage="一级菜单必须选择" >
							<option value="-1">请选择</option>
					</select>
					</td>
				</tr>
				<tr>
					<td class="m-info-title">菜单名称<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${menu.fname}" id="fname" name="fname"  class="easyui-validatebox"  required="true"   missingMessage="菜单名称必须填写" />
					</td>
			    </tr>
				<tr>
					<td class="m-info-title">菜单url<span class="red">*</span>:</td>
					<td class="m-info-content">
					<input style="width: 145px; height: 25px;border:1px solid #ccc;" type="text" value="${menu.furl}" id="furl" name="furl"  class="easyui-validatebox" missingMessage="菜单url必须填写" />
					</td>
			    </tr>
				<tr>
					<td colspan="2">
						<div class="Mbutton25 createButton" id="saveMenuButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
