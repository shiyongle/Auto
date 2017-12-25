<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>固定距离修改</title>
<script type="text/javascript">
	$(document).ready(function() {
	   $("#nameSel").combobox({
	    	url : "${ctx}/select/getAllCustomers.do",
	     	required : true,
	     	editable : true,
	      	width:145,
	     	panelHeight:200,
	     	missingMessage : "用户名必须选择",
			validType : "comboRequired",
			invalidMessage : "用户名必须选择",
	    	mode : 'local',
			filter:searchItem,
	     	valueField: 'optionId',//提交值
	      	textField: 'optionName',//显示值
	     	disabled:true  		
	   });
	   
	   //发货地址
	    $("#addressDel").combobox({
			url :" ${ctx}/select/getAllAddress.do?optionTemp=1&userRoleId="+"${distance.fcustomer_id}",
			required : true,
			panelHeight:200,
			missingMessage : "发货地址必须选择！",
			validType : "comboRequired",
			invalidMessage : "发货地址必须选择！",
			editable : true,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionVal',
		});
	   
		//卸货地址
		$("#addressRec").combobox({
			url : "${ctx}/select/getAllAddress.do?optionTemp=2&userRoleId="+"${distance.fcustomer_id}",
			required : true,
			missingMessage : "卸货地址必须选择！",
			panelHeight:200,
			validType : "comboRequired",
			invalidMessage : "卸货地址必须选择！",
			editable : true,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionVal',
		});
		
		//取消按钮
	    $("#closeButton").click(function(){
			$("#createWindow").window("close");
		});
		
		//提交按钮    
		$("#submit").click(function() {
			if ($("#createForm").form("validate")) {
				var params = decodeURIComponent($("#createForm").serialize(), true);
					params = params+'&faddressDel=' + $('#addressDel').combobox('getText') + '&faddressRec=' + $('#addressRec').combobox('getText');
				$.ajax({
					type : "POST",
					url : "${ctx}/distance/edit.do",
					data : params,
					success : function(response) {
						if (response == "success") {
							$.messager.alert('提示', '修改成功', 'info', function() {
								$("#createWindow").window("close");
								$("#CLDistanceTable").datagrid('reload');
							});
						} 
						else {
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
<form id="createForm" action="" method="post">
	<input type="hidden" name="fid" value="${distance.fid}" />
	<input type="hidden" name="fcreater_id" value="${distance.fcreater_id}"/>
	<input type="hidden" name="fcreate_time" value="${distance.fcreate_time}"/>
	<table class="add-user" id="createlngModule">
        <tr>
            <td class="m-info-title">客户名称<span class="red">*</span>:</td>
            <td class="m-info-content">
            <select id="nameSel" style="width: 145px; height: 25px;" name="fcustomer" missingMessage="客户名称必须填写" >
                    <option value="${distance.fcustomer}">${distance.fcustomer}</option>
            </select>
           </td>
            <td class="m-info-title">发货地址<span class="red">*</span>:</td>
            <td class="m-info-content">
            <select id="addressDel" style="width: 145px; height: 25px;" name="faddressDel_id" missingMessage="发货地址必须选择" >
                    <option value="${distance.faddressDel_id}">${distance.faddressDel}</option>
            </select>
            </td>
        </tr>
        <tr>
            <td class="m-info-title" style="line-height:50px">卸货地址<span class="red">*</span>:</td>
            <td class="m-info-content">
                <select id="addressRec"style="width: 145px; height: 25px;" name="faddressRec_id"  >
                	<option value="${distance.faddressRec_id}">${distance.faddressRec}</option>
                </select>
            </td>
            <td class="m-info-title">公里数(km)<span class="red">*</span>:</td>
            <td class="m-info-content">
                <input type="text" name="fmileage" style="width: 145px; height:20px; outline:none; border:1px solid #95b8e7;" value="${distance.fmileage}"class="easyui-numberbox" precision="2" /> 
            </td>
        </tr>
        <tr style="text-align:center;">
            <td colspan="2">
                <div class="Mbutton25" id="submit" align="center" style="float:right; margin-top:10px;">保存</div>
            </td>
            <td colspan="2">
                <div class="Mbutton25" style="float:left; margin-top:10px; margin-left:20px;" id="closeButton" align="center">取消</div>
            </td>
        </tr>
     </table>
</form>
</html>
