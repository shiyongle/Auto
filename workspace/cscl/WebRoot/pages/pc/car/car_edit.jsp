<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>修改用户</title>
<script type="text/javascript">
$(document).ready(function() {

    $("#licenseType1").combobox({
        width: 145,
        required: true,
        editable: false,
        panelHeight: "auto",
        validType: "comboRequired",
        invalidMessage: "请选择",
        missingMessage: "请选择"
    }).combobox('select', "${car.licenseType}");

    $("#userRoleIdE").combobox({
        editable: true,
        width: 145,
        //mode: 'remote',
        url: "${ctx}/user/queryCarUser.do",
        disabled: "disabled",
        valueField: 'optionId',
        textField: 'optionName'
    }).combobox('select', "${car.userRoleId}");

    $("#carType2").combobox({
        url: "${ctx}/select/getAllCarSpecByCarType.do?optionTemp=" + "${car.carSpecId}",
        editable: true,
        width: 145,
        required: true,
        missingMessage: "全部",
        validType: "comboRequired",
        invalidMessage: "全部",
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        filter: searchItem
    }).combobox('select', "${car.carType}");
    /*2016年4月19日16:34:47*/

    $("#carSpecId2").combobox({
        url: "${ctx}/select/getAllCarType1.do",
        required: true,
        missingMessage: "全部",
        validType: "comboRequired",
        invalidMessage: "全部",
        editable: true,
        width: 145,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        onSelect: function(record) {
            $("#carType2").combobox({
                url: "${ctx}/select/getAllCarSpecByCarType.do?optionTemp=" + record.optionId,
                required: true,
                missingMessage: "全部",
                validType: "comboRequired",
                invalidMessage: "全部",
                editable: true,
                mode: 'remote',
                valueField: 'optionId',
                textField: 'optionName'
            });
        }
    });
        
     $("#area").combobox({
        url: "${ctx}/select/getAllArea.do?fcity_id=" +"${car.fcity_id}",
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        mode: 'remote',
        width: 120,
        valueField: 'optionId',
        textField: 'optionName'
    });
    $("#area").combobox('select',"${car.activeArea}"); 
    $("#city").combobox({
        url: "${ctx}/select/getAllCity.do?fprovince_id=" +"${car.fprovince_id}",
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        width: 120,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        onSelect:function(record){
        	var option;
        	if(record == null){
        		option = "${car.fcity_id}";
        	}else{
        		option =record.optionId;
        	}
        	$("#area").combobox({
		        url: "${ctx}/select/getAllArea.do?fcity_id=" + option,
		        required: true,
		        missingMessage: "请选择",
		        width: 120,
		        validType: "comboRequired",
		        invalidMessage: "请选择",
		        editable: true,
		        mode: 'remote',
		        valueField: 'optionId',
		        textField: 'optionName'
		    })
		    if(record == null){
        		$("#area").combobox('select',"${car.activeArea}");
        	}
        }
    });    
    $("#province").combobox({
        url: "${ctx}/select/getAllProvince.do",
        required: true,
        missingMessage: "请选择",
        validType: "comboRequired",
        invalidMessage: "请选择",
        editable: true,
        width: 120,
        mode: 'remote',
        valueField: 'optionId',
        textField: 'optionName',
        onSelect: function(record) {
            $("#city").combobox({
		        url: "${ctx}/select/getAllCity.do?fprovince_id=" + record.optionId,
		        required: true,
		        missingMessage: "请选择",
		        validType: "comboRequired",
		        invalidMessage: "请选择",
		        editable: true,
		        width: 120,
		        mode: 'remote',
		        valueField: 'optionId',
		        textField: 'optionName'
		    })
        }
    })
    $("#city").combobox("select", "${car.fcity_id}");
    $("#submit").click(function() {
        if ($("#TFormE").form("validate")) {
            var params = decodeURIComponent($("#TFormE").serialize(), true);
            //alert(params);
            $.ajax({
                type: "POST",
                url: "${ctx}/car/update.do",
                data: params,
                success: function(response) {
                    if (response == "success") {
                        $.messager.alert('提示', '修改成功', 'info',
                        function() {
                            $("#createWindow").window("close");
                            $("#CLCarTable").datagrid('reload');
                        });
                    } else {
                        $.messager.alert('提示', "错误！");
                    }
                }
            });
        }
    });
});
</script>
</head>
<div id="createWindow"></div>
<form id="TFormE">
	<input type="hidden" name="id" value="${car.id}" />
	<table class="add-user" id="createBursarInfo">
		<tr>
			<td class="m-info-title">用户<span class="red">*</span>:
			</td>
			<td class="m-info-content"><select id="userRoleIdE"
				style="width: 145px; height: 25px;" name="userRoleId" panelHeight="200">
					<option value="-1">请选择</option>
			</select></td>

			<td class="m-info-title">车辆规格<span class="red">*</span>:
			</td>
			<td class="m-info-content"><select id="carSpecId2"
				name="carSpecId" style="width: 145px; height: 25px;">
					<option value=${car.carSpecId}>${car.carSpecId}</option>
			</select></td>
		</tr>

		<tr>
			<td class="m-info-title">车辆类型:<span class="red">*</span>:
			</td>
			<td class="m-info-content"><select id="carType2" name="carType"
				style="width: 145px; height: 25px;">
					<option value="-1"></option>
			</select></td>
			
			<td class="m-info-title">牌照类型:<span class="red">*</span>:
			</td>
			<td class="m-info-content">
			<select id="licenseType1" style="width: 145px; height: 25px;" name="licenseType" missingMessage="牌照类型必须选择">
					<option value="-1">请选择</option>
					<option value="0">黄牌</option>
					<option value="1">蓝牌-市</option>
					<option value="2">蓝牌-县</option>
			</select>
			</td>
		</tr>

		<tr>
			<td class="m-info-title">车牌号<span class="red">*</span>:
			</td>
			<td class="m-info-content"><input type="text" name="carNum"
				value="${car.carNum}" class="easyui-validatebox" required="true"
				missingMessage="车牌号必须填写" style="width:145px" /></td>

			<td class="m-info-title">驾驶人<span class="red">*</span>:
			</td>
			<td class="m-info-content"><input type="text" name="driverName"
				value="${car.driverName}" class="easyui-validatebox" required="true"
				missingMessage="驾驶人必须填写" style="width:145px" /></td>
		</tr>

		<tr>
			<td class="m-info-title">驾驶证编码<span class="red">*</span>:
			</td>
			<td class="m-info-content"><input type="text" name="driverCode"
				value="${car.driverCode}" class="easyui-validatebox" required="true"
				missingMessage="驾驶证必须填写" style="width:145px" /></td>

			<td class="m-info-title">核载:
			</td>
			<td class="m-info-content"><input type="text" name="weight"
				value="${car.weight}" class="easyui-validatebox" style="width:145px" /></td>
		</tr>
		<tr>
			<td class="m-info-title">主要活动区域:</td>
			<td class="m-info-content" colspan="3">
				省:<select  id="province" name="fprovince_id">
					<option value="${car.fprovince_id}">${car.fprovince}</option>
				</select>
				市:<select id="city" name="fcity_id" >
					 <%-- <option value="${car.fcity_id}">${car.fcity}</option> --%>
					 <option value="-1">请选择</option>
				</select>
				区:<select name="activeArea" id="area">
					 <%-- <option value="${car.activeArea}">${car.area}</option> --%>
					 <option value="-1">请选择</option>
				</select>
			</td>
		</tr>
	</table>
	<div class="Mbutton25 createButton" id="submit"
		style="margin-right: 5%">修改</div>
</form>
</html>
