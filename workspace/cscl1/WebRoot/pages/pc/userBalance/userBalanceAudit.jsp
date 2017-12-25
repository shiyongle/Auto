<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>固定距离设置</title>
	<script>
		$(function(){
			$("#passButton").click(function() {
				if($("#passradio").is(":checked")){
						$.messager.confirm('提示', '确定要通过吗？', function(r) {
						    if (r) {
								var params = decodeURIComponent($("#auditBalance").serialize(),true);
								console.log(params);
								$.ajax({
									type : "POST",
									url : "${ctx}/userBalance/audit.do",
									data : params,
									success : function(response) {
										if (response == "success") {
											$.messager.alert(
												'提示',
												'添加成功',
												'info',
												function() {
													$("#createWindow").window("close");
													 $('#balanceTable').datagrid('reload');
												});
										} else {
											$.messager.alert(
											'提示',
											'错误！',
											'info',
											function() {
												$("#createWindow").window("close");
											});
										}
									}
								});
							}
						});
				}else if($("#refuseradio").is(":checked")){
						$.messager.confirm('提示', '确定要驳回吗？', function(r) {
						    if (r) {
								var params = decodeURIComponent($("#auditBalance").serialize(),true);
								$.ajax({
									type : "POST",
									url : "${ctx}/userBalance/notAudit.do",
									data : params,
									success : function(response) {
										if (response == "success") {
											$.messager.alert(
												'提示',
												'添加成功',
												'info',
												function() {
													$("#createWindow").window("close");
													$('#balanceTable').datagrid('reload');
												});
										} else {
											$.messager.alert(
												'提示',
												'错误！',
												'info',
												function() {
													$("#createWindow").window("close");
												});
										}
									}
								});
							}
						});
				}
			});
	//操作余额
	var type = "${balance.foperate_type}";
	var changeMoney ="${balance.foperate_money}";
	var $changeMoeny = $('#foperate_money');
	if(type==1){
		$changeMoeny.val('增加了'+changeMoney+'元');
	}else{
		$changeMoeny.val('减少了'+changeMoney+'元');
	}
	//状态
	function getState(value, row, index){
		if(value=="0"){
			return '<font color="red">未认证</font>';
			}else if(value=="1"){
			return '<font color="green">已认证</font>';
			}else{
			return '<font color="black">已驳回</font>';
		}
	}	
	// 时间戳转换 
		function fbilldateType(value,rowData,rowIndex){
			if(value==null){
				return "- -";
			}else{
				var d = new Date(value);    //根据时间戳生成的时间对象
				var date = (d.getFullYear()) + "-" + toDou(d.getMonth() + 1)+"-"+toDou(d.getDate())+" "+toDou(d.getHours())+":"+toDou(d.getMinutes())+":"+toDou(d.getSeconds());
				return date;
			}
		}
		//时间补零
		function toDou(n){
			return n<10?"0"+n:""+n;
		}
		});
	</script>
</head>
<form id="auditBalance">
		<input type="hidden" name="fid"  value="${balance.fid}"/>
		<input type="hidden" name="fuser_role_id"  value="${balance.fuser_role_id}"/>
		<input type="hidden" name="ftype"  value="${balance.ftype}"/>
		<input type="hidden" name="foperator"  value="${balance.foperator}"/>
		<input type="hidden" name="foperate_time"  value="${balance.foperate_time}"/>
		<input type="hidden"  name="foperate_money"   value="${balance.foperate_money}"/>		
		<input type="hidden"  name="foperate_type"   value="${balance.foperate_type}"/>
		<input type="hidden"  name="money"   value="${balance.money}"/>
	<table>
		<tr>
		<td>用户名<span class="red">*</span>:</td>
		<td class="m-info-content">
            <input type="text" name="fuser_name" style="width: 145px; height:20px; outline:none; border:1px solid #95b8e7; background-color:rgb(235, 235, 228);" value="${balance.fuser_name}" readonly = "readonly"/> 
        </td>
		</tr>
		<tr>
			<td>余额<span class="red">*</span>:</td>
			<td class="m-info-content">
	            <input type="text" name="" style="width: 145px; height:20px; outline:none; border:1px solid #95b8e7; background-color:rgb(235, 235, 228);" value="${balance.money}" readonly = "readonly"/> 
	        </td>
		</tr>
		<tr>
			<td>操作金额<span class="red">*</span>:</td>
			<td class="m-info-content">
	            <input type="text" id="foperate_money"  style="width: 145px; height:20px; outline:none; border:1px solid #95b8e7; background-color:rgb(235, 235, 228);" readonly = "readonly"/> 
	        </td>
		</tr>
		<tr>
			<td>调整原因<span class="red">*</span>:</td>
			<td class="m-info-content">
	            <input type="text" name="fremark" style="width: 145px; height:50px; outline:none; border:1px solid #95b8e7; background-color:rgb(235, 235, 228);" value="${balance.fremark}" readonly = "readonly"/> 
	        </td>
		</tr>
		<tr>
			<td colspan="2">审核结果<span class="red">*</span>:
			<input type="radio" name="fis_pass_identify" value="1" checked="checked" id="passradio"/>通过<input type="radio" name="fis_pass_identify" value="2" id="refuseradio"/>驳回</td>
		</tr>
		<tr>
			<td colspan="2"><div class="Mbutton25 createButton" id="passButton" id="passButton" style="float:left; margin-left:70px;">审核完成</div></td>
		</tr>
	</table>
</form>
</html>