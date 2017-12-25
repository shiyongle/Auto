<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>固定距离设置</title>
<script type="text/javascript">
$(document).ready(function() {
    //客户名称
    $("#nameSel").combobox({
        //          url : "${ctx}/select/getAllCustomers.do?optionTemp=&userRoleId=",
        url: "${ctx}/select/getAllCustomers.do",
        required: true,
        editable: true,
        width: 145,
        panelHeight: 200,
        missingMessage: "用户名必须选择",
        validType: "comboRequired",
        invalidMessage: "用户名必须选择",
        mode: 'local',
        filter: searchItem,
        valueField: 'optionWe',
        //提交值
        textField: 'optionName',
        //显示值
        onSelect: select,
        onChange: function(newValue, oldValue) {
            $.ajax({
                url: "${ctx}/select/getAllCustomers.do",
                success: function(str) {
                    var arr = JSON.parse(str);
                    for (var i = 0; i < arr.length; i++) {
                        if (newValue == arr[i].optionName) {
                            $("#nameSel").combobox('select',arr[i].optionWe);
                            break;
                        }
                    }
                }
            });
        }
    });
    
	//onselect事件
    function select(record) {
		
        //发货地址
        $("#addressDel").combobox({
            url: " ${ctx}/select/getAllAddress.do?optionTemp=1&userRoleId=" + record.optionWe,
            required: true,
            missingMessage: "发货地址必须选择！",
            validType: "comboRequired",
            invalidMessage: "发货地址必须选择！",
            editable: true,
            panelHeight: 200,
            mode: 'remote',
            valueField: 'optionId',
            textField: 'optionVal'
        });
        
        //卸货地址
        $("#addressRec").combobox({
            url: "${ctx}/select/getAllAddress.do?optionTemp=2&userRoleId=" + record.optionWe,
            required: true,
            missingMessage: "卸货地址必须选择！",
            validType: "comboRequired",
            invalidMessage: "卸货地址必须选择！",
            editable: true,
            mode: 'remote',
            panelHeight: 200,
            valueField: 'optionId',
            textField: 'optionVal'
        });
    }
    
  	//取消按钮
    $("#closeButton").click(function() {
        $("#createWindow").window("close");
    });
  	
    //提交按钮     
    $("#saveButton").click(function() {
        if ($("#createMenuForm").form("validate")) {
            var params = decodeURIComponent($("#createMenuForm").serialize(), true);
            params = params + '&fcustomer=' + $('#nameSel').combobox('getText') + '&faddressDel=' + $('#addressDel').combobox('getText') + '&faddressRec=' + $('#addressRec').combobox('getText');
            $.ajax({
                type: "POST",
                url: "${ctx}/distance/save.do",
                data: params,
                success: function(response) {
                    if (response == "success") {
                        $.messager.alert('提示', '操作成功', 'info',
                        function() {
                            $("#createWindow").window("close");
                            $("#CLDistanceTable").datagrid('reload');
                        });
                    } else if (response == "false") {
                        $.messager.alert('提示', '请勿重复录入相同路线', 'info',
                        function() {
                            $("#CLDistanceTable").datagrid('reload');
                        });
                    } else {
                        $.messager.alert('提示', "操作失败！");
                    }
                }
            });
        }
    });
});
</script>
</head>
<form id="createMenuForm" action="" method="post">
	<table class="add-user" id="createlngModule">
		<tr>
			<td class="m-info-title">客户名称<span class="red">*</span>:
			</td>
			<td class="m-info-content">
				<select id="nameSel" style="width: 145px; height: 25px;" name="fcustomer_id">
					<option>请选择</option>
				</select>
			</td>
			<td class="m-info-title">发货地址<span class="red">*</span>:
			</td>
			<td class="m-info-content">
				<select id="addressDel" style="width: 145px; height: 25px;" name="faddressDel_id">
					<option>请选择</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="m-info-title">卸货地址<span class="red">*</span>:
			</td>
			<td class="m-info-content">
				<select id="addressRec" style="width: 145px; height: 25px;" name="faddressRec_id">
						<option>请选择</option>
				</select>
			</td>
			<td class="m-info-title">公里数(km)<span class="red">*</span>:
			</td>
			<td class="m-info-content">
				<input type="text" name="fmileage" style="width: 145px; height:20px; outline:none; border:1px solid #95b8e7;" class="easyui-numberbox" precision="2" required="true"
				missingMessage="请填写公里数" />
			</td>
		</tr>
		<tr style="text-align:center;">
			<td colspan="2">
				<div class="Mbutton25" id="saveButton" align="center" style="float:right; margin-top:10px;">
					保存
				</div>
			</td>
			<td colspan="2">
				<div class="Mbutton25" style="float:left; margin-top:10px; margin-left:20px;" id="closeButton" align="center">
					取消
				</div>
			</td>
		</tr>
	</table>
</form>
</html>

