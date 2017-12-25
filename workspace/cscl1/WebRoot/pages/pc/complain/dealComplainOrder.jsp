<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>受理</title>

</head>
<body>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		//退回理由
		$('#auditAndBackReasson').validatebox({
			required:true,
			missingMessage:'原因必须填写！'
		});
		//radio判断
		var accountType ="${complain.fdriverAccountDisable}";
		var freezeType ="${complain.fdriverFreezeDisable}";
		if(accountType==='true'){
			$('#accountChecked').attr('checked','checked');
		}else{
			$('#notAccountChecked').attr('checked','checked');
		}
		if(freezeType==='true'){
			$('#freezeChecked').attr('checked','checked');
		}else{
			$('#notFreezeChecked').attr('checked','checked');
		}
		//关闭按钮
		$("#closebtn").click(function(){
			alert(1);
				$("#docomplainOrder_Window").window("close");
		});
		//保存按钮
		$("#saveBtn").click(function() {
			if ($("#complainForm").form("validate")){
				if($("#passradio").is(":checked")){
						$.messager.confirm('提示', '确定要通过吗？', function(r) {
						    if (r) {
								var params = decodeURIComponent($("#complainForm").serialize(),true);
								$.ajax({
									type : "POST",
									url : "${ctx}/usercomplain/deal.do",
									data : params,
									dataType:'json',
									success : function(response) {
										if (response.success == "success") {
											$.messager.alert(
												'提示',
												response.msg,
												'info',
												function() {
													$("#docomplainOrder_Window").window("close");
													 $('#complainTable').datagrid('reload');
												});
										} else {
											$.messager.alert(
											'提示',
											response.msg,
											'info',
											function() {
												$("#docomplainOrder_Window").window("close");
											});
										}
									}
								});
							}
						});
				}else if($("#refuseradio").is(":checked")){
						$.messager.confirm('提示', '确定要驳回吗？', function(r) {
						    if (r) {
								var params = decodeURIComponent($("#complainForm").serialize(),true);
								$.ajax({
									type : "POST",
									url : "${ctx}/usercomplain/deal.do",
									data : params,
									dataType:'json',
									success : function(response) {
										if (response.success == "success") {
											$.messager.alert(
												'提示',
												response.msg,
												'info',
												function() {
													$("#docomplainOrder_Window").window("close");
													$('#complainTable').datagrid('reload');
												});
										} else {
											$.messager.alert(
												'提示',
												response.msg,
												'info',
												function() {
													$("#docomplainOrder_Window").window("close");
												});
										}
									}
								});
							}
						});
				}
				}
			});
	});
</script>
<style>
fieldset{margin:15px 0px;border:1px solid #ccc;}
		main{height:600px;overflow-y: scroll;padding:10px;}
		@media screen and (max-width:1280px) {
		main{height:350px;}
		}
		input,textarea{border:1px solid #ccc;border-radius:3px;}
		input[type=radio]{vertical-align: middle;}
		.dirverBox,.shipperBox,.reasonBox{ width:31.6%; float:left;}
		.dirverBox tr,.shipperBox tr,.reasonBox tr{ display:block; margin-bottom: 3px;}
		.shipperBox{padding:0 2%;}
		.dirverBox textarea,.shipperBox textarea,.reasonBox textarea{resize:none;}
</style>
<main>
	<form id="complainForm">
		<input type="hidden" value="${complain.fid}" name="fid">
		<!-- 投诉记录 -->
		<fieldset>
			<legend>
				<strong><h3>投诉记录</h3></strong>
			</legend>
			<table>
			<tr><td colspan="6" height="10px"></td></tr>
				<tr>
					<td colspan="6">
						<strong style="margin-left:28px;">订单号：</strong>
						<select class="easyui-combobox" panelHeight="200"  disabled="disabled">
							<option>${complain.orderNumber}</option>
						</select>						
						<strong style="margin-left:24px;">投诉人：</strong>
						<input  type="text"  style="width:110px;" disabled="disabled" value="${complain.fcomplainUserName}"/>
						<strong style="margin-left:24px;">投诉类型：</strong>
						<select  disabled="disabled" class="easyui-combobox">
							<option>${complain.complainType}</option>
						</select>
					</td>
			    </tr>		    
			    <tr>
			    	<td colspan="6" height="15px"></td>
			    </tr>				    
			    <tr style="margin:10px 0px;">
				    <td class="m-info-title" width="75">投诉记录：</td>
				    <td class="m-info-content" colspan="3">
				    <textarea disabled="disabled" style="width:320px;height:100px;resize:none;border:1px solid #ccc">
						${complain.fcomplainContent}
				    </textarea>
				    <td class="m-info-title">沟通记录：</td>
				    <td class="m-info-content" colspan="3">
				    <textarea  disabled="disabled" style="width:320px;height:100px;resize:none;border:1px solid #ccc">
						${complain.fcomplainCommunicateContent}
				    </textarea>
				    </td>
			    </tr>			
			</table>
		</fieldset>
									
		<!-- 订单信息 -->
		<fieldset>
			<legend><strong><h3>订单信息</h3></strong></legend>
			<div id="orderInfo">
				<table>
					<tr><td height="10px"></td></tr>
					<tr>
						<td class="m-info-title">订单号：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.orderNumber}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;">订单类型：</td>
						<td class="m-info-content">
						<input  type="text"   disabled="disabled" value="${complain.orderType}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;">用车时间：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.orderLoadTime}"/>
						</td>
				    </tr>
				    <tr><td height="10px"></td></tr>
				    <tr>
						<td class="m-info-title">货物类型：</td>
						<td class="m-info-content">
						<input  type="text"   disabled="disabled" value="${complain.orderGoodType}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">车型选择：</td>
						<td class="m-info-content">
						<input  type="text"   disabled="disabled" value="${complain.carType}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">增值服务：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled"  value="${complain.orderAddServer}"/>
						</td>
				    </tr>
				    <tr><td height="10px"></td></tr>
				    <tr>
						<td class="m-info-title">里程：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.orderMileage}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;">运费：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.orderPayAmount}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;">客户类型：</td>
						<td class="m-info-content">
						<input  type="text" disabled="disabled" value="${complain.userType}"/>
						</td>
				    </tr>
				    <tr><td height="10px"></td></tr>
				    <tr>
						<td class="m-info-title">客户名称：</td>
						<td class="m-info-content">
						<input  type="text" disabled="disabled" value="${complain.userName}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">客户电话：</td>
						<td class="m-info-content">
						<input  type="text" disabled="disabled" value="${complain.userPhone}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">提货点：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.sendAddress}"/>
						</td>
				    </tr>
				    <tr><td height="10px"></td></tr>
				    <tr>
						<td class="m-info-title">收货人：</td>
						<td class="m-info-content">
						<input  type="text"   disabled="disabled" value="${complain.receiveUser}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">收货人电话：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.receivePhone}"/>
						</td>
						<td class="m-info-title" style="padding-left:30px;">卸货点：</td>
						<td class="m-info-content">
						<input  type="text" disabled="disabled" value="${complain.receiveAddress}" />
						</td>
				    </tr>
				    <tr><td height="10px"></td></tr>
				    <tr>
						<td class="m-info-title">司机名称：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.driverName}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;">司机电话：</td>
						<td class="m-info-content">
						<input  type="text"  disabled="disabled" value="${complain.driverPhone}" />
						</td>
						<td class="m-info-title" style="padding-left:30px;"></td>
						<td class="m-info-content">
						</td>
				    </tr>	
				</table>
			</div>
		</fieldset>
		<div style="overflow:hidden;">
			<div class="dirverBox">
				<fieldset>
					<legend><strong><h3>司机处理结果</h3></strong></legend>
					<table>
						<tr>
							<td colspan="2" style="font-size:14px;"><strong>金额扣除:</strong></td>
						</tr>
						<tr>
							<td><strong>司机姓名:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.driverName}">
							</td>
						</tr>
						<tr>
							<td><strong>扣除金额:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.fdriverFineAmount}">
							</td>
						</tr>
						<tr>
							<td><strong>司机余额:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.driBalance }">
							</td>
						</tr>
						<tr>
							<td>
								<strong>是否禁号:</strong>
							</td>
							<td>
								是<input type="radio" disabled="disabled"  id="accountChecked">
								否<input type="radio" disabled="disabled"  id="notAccountChecked">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="text" style="width:97px; font-size:12px;" disabled="disabled" value="${complain.fdriverAccountDisableStringStart} "> - <input type="text" style="width:97px; font-size:12px;" disabled="disabled" value="${complain.fdriverAccountDisableStringEnd} ">
							</td>
						</tr>
						<tr>
							<td>
								<strong>是否冻结:</strong>
							</td>
							<td>
								是<input type="radio" disabled="disabled" 	id="freezeChecked" >
								否<input type="radio" disabled="disabled" id="notFreezeChecked">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<input type="text" style="width:97px; font-size:12px;" disabled="disabled" value="${complain.fdriverFreezeDisableStringStart}"> - 
								<input type="text" style="width:97px; font-size:12px;"disabled="disabled" value="${complain.fdriverFreezeDisableStringEnd}">
							</td>
						</tr>
						<tr style="border-top:1px dashed #ccc;">
							<td colspan="2">
								<strong style="font-size:14px;">补贴:</strong>
							</td>
						</tr>
						<tr>
							<td><strong>司机姓名:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.driverName}">
							</td>
						</tr>
						<tr>
							<td><strong>返还金额:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.fdriverRewardAmount}">
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<strong style="font-size:14px;">
									备注:
								</strong>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<textarea style="width:240px; height:60px;" disabled="disabled">
									${complain.fshipperRemark}
								</textarea>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<div class="shipperBox">
					<fieldset>
					<legend><strong><h3>货主处理结果</h3></strong></legend>
					<table>
						<tr>
							<td colspan="2"><strong style="font-size:14px;">返回费用:</strong></td>
						</tr>
						<tr>
							<td><strong>货主姓名:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.userName}">
							</td>
						</tr>
						<tr>
							<td><strong>返还金额:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.fshipperFineAmount}">
							</td>
						</tr>
						<tr>
							<td><strong>货主金额:</strong></td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.cusBalance }">
							</td>
						</tr>
						<tr style="border-top:1px dashed #ccc;">
							<td colspan="2"><strong style="font-size:14px;">好运券:</strong></td>
						</tr>
						<tr>
							<td>
								<strong>数量:</strong>
							</td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.fshipperRewardCouponsAmount}"> 张
							</td>
						</tr>
						<tr>
							<td>
								<strong>面额:</strong>
							</td>
							<td>
								<input type="text" disabled="disabled" style="width:90px;" value="${complain.fshipperRewardCouponsDollars}"> 元
							</td>
						</tr>
						<tr>
							<td>
								<strong style="font-size:12px;">期限:</strong>
							</td>
							<td>
								<input type="text"style="width:94px; font-size:10px;" disabled="disabled" value="${complain.fshipperRewardCouponsStringStart}" > - 
								<input type="text" style="width:94px; font-size:10px;" disabled="disabled" value="${complain.fshipperRewardCouponsStringEnd}">
							</td>
						</tr>
						<tr>
							<td style="height:69px;"></td>
						</tr>
						<tr>
							<td colspan="2">
								<strong style="font-size:14px;">
									备注:
								</strong>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<textarea style="width:240px; height:60px;" disabled="disabled">
									${complain.fshipperRemark}
								</textarea>
							</td>
						</tr>
					</table>
					</fieldset>								
			</div>
			<div class="reasonBox">
				<fieldset><legend><strong><h3>财务处理结果</h3></strong></legend>
					<table>
						<tr>
							<td colspan="2"><strong style="font-size:14px;">处理状态<span class="red">*</span>:</strong></td>
						</tr>
						<tr>
							<td colspan="2">
								通过<input type="radio" name="fisdeal" checked="checked" id="passradio" value="1">
								退回<input type="radio" name="fisdeal" id="refuseradio" value="2">
							</td>
								
						</tr>
						<tr>
							<td style="height:238px;"></td>
						</tr>
						<tr>
							<td colspan="2">
								<strong><span style="font-size:14px;">处理原因</span><span class="red">*</span>:</strong>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<textarea style="width:240px; height:60px;" id="auditAndBackReasson" name="fremark"></textarea>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
	</form>
<div  style="margin:0 auto; width:130px;">
<div class="Mbutton25 createButton" align="center" id="closebtn">取消</div>
<button class="Mbutton25 createButton" id="saveBtn" align="center" style="margin-right:8px;border:0px">保存</button>
</div>
</main>
</body>
</html>

