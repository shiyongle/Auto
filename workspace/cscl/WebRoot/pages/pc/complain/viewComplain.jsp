<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>异常查看</title>

</head>
<body>
<script src="${ctx}/pages/pc/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
			//取消按钮事件
			$("#closeDriverAndShipper").click(function(){
				$("#complainOrder_Window").window("close");
			});
			//财务处理结果	
			var fisdeal= "${ccinfo.fisdeal}";	
			if(fisdeal==0){
				$('#auditResult').html('未审核');
			}else if(fisdeal==1){
				$('#auditResult').html('<font style="color:green;">已审核</font>');
			}else if(fisdeal==2){				
				$('#auditResult').html('<font style="color:#CC0000;">驳回</font>');
			}
		//订单号下拉框
		$("#searchOrderNum").combobox({
			url : "${ctx}/select/getFinishOrders.do",
			required : true,
			mode: 'local',
			width:150,
			disabled:true,
			missingMessage:"订单号必须选择",
			invalidMessage : "订单号必须选择",//无效提示信息
			valueField: 'optionId',//提交值
			textField: 'optionName',//显示值
			disabled:true,
			onChange:function(item){
				   var orderId = $('#searchOrderNum').combobox('getValue');
				   var jsonNum={"orderId":orderId};
				   $.ajax({
						type : "POST",
						url : "${ctx}/usercomplain/loadOrderById.do",
						data : jsonNum,
						success : function(response) {
							/*选择订单号后赋值*/
							var jsRes = JSON.parse(response);
							$("#orderNumber").val(jsRes.orderNumber);
							$("#orderType").val(jsRes.orderType);
							$("#orderLoadTime").val(jsRes.orderLoadTime);
							$("#orderGoodType").val(jsRes.orderGoodType);
							$("#carType").val(jsRes.carType);
							$("#orderAddServer").val(jsRes.orderAddServer);
							$("#orderMileage").val(jsRes.orderMileage);
							$("#orderPayAmount").val(jsRes.orderPayAmount);
							$("#userType").val(jsRes.userType);
							$("#userName").val(jsRes.userName);
							$("#userPhone").val(jsRes.userPhone);
							$("#sendAddress").val(jsRes.sendAddress);
							$("#receiveUser").val(jsRes.receiveUser);
							$("#receivePhone").val(jsRes.receivePhone);
							$("#receiveAddress").val(jsRes.receiveAddress);
							$("#driverName").val(jsRes.driverName);
							$("#driverPhone").val(jsRes.driverPhone);
							$("#resultDriverName").val(jsRes.driverName);
							$("#resultShipperName").val(jsRes.userName);
							$("#subsidyDriverName").val(jsRes.driverName);
						}
					});
				   
			}
		}).combobox("select","${ccinfo.forderId}");
		
		//$("#fcomplainType").find("option[value='${ccinfo.fcomplainType}']").attr("selected",true);
		$("#fcomplainType").combobox({
			required : true,
			editable : false,
			missingMessage:"投诉类型必须选择",
			invalidMessage : "投诉类型必须选择",//无效提示信息
			validType : "comboRequired",
			panelHeight:"auto",//高度自适应
			disabled:true
		}).combobox('select',"${ccinfo.fcomplainType}");
		$("input[type=radio][name='fdriverAccountDisable'][value='${ccinfo.fdriverAccountDisable}']").attr("checked",'checked');
		$("input[type=radio][name='fdriverFreezeDisable'][value='${ccinfo.fdriverFreezeDisable}']").attr("checked",'checked'); 
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
.driverDiv,.shipperDiv{ width:48%; float:left;overflow:hidden;}
.driverDiv{border-right:1px dashed #ccc;padding-right:1%}
.shipperDiv{padding-left:1%;}
#orderInfo input{background:#eeeeee;width:150px;}
#fdriverAccountDisableStart,#fdriverAccountDisableEnd,#fdriverFreezeDisableStart,#fdriverFreezeDisableEnd{width:100px;}
#fshipperRewardCouponsStart,#fshipperRewardCouponsEnd{width:141px;}
#abnormalButton{ width:130px;margin:0 auto;}
</style>
<main>
<form id="complainForm" action="" method="post">	
<!-- by lu 这个老忘记写了要注意！！！！！-->
<input type="hidden" name="fid" value="${ccinfo.fid}" />
<input type="hidden" name="fisdeal" value="${ccinfo.fisdeal}" />
<!-- 投诉记录 -->
<fieldset>
<legend><strong><h3>投诉记录</h3></strong></legend>
			<table>
			<tr><td colspan="6" height="10px"></td></tr>
				<tr>
					<td colspan="6">
					<strong style="margin-left:28px;">订单号<span class="red">*</span>：</strong>
					<select id="searchOrderNum" name="forderId" panelHeight="200" >
					</select>
					
					<strong style="margin-left:24px;">投诉人<span class="red">*</span>：</strong>
					<input  type="text" disabled="disabled" id="fcomplainUserName" name="fcomplainUserName" value="${ccinfo.fcomplainUserName}" style="width:110px;"  class="easyui-validatebox"   />
					
					<strong style="margin-left:24px;">投诉类型<span class="red">*</span>：</strong>
					<select id="fcomplainType" name="fcomplainType">
							<option value="-1">请选择</option>
							<option value="1">行为不良</option>
							<option value="2">服务不好</option>
							<option value="3">货物破损</option>
							<option value="4">货物丢失</option>
							<option value="5">车辆迟到</option>
							<option value="6">回单异常(货主)</option>
							<option value="7">回单异常(司机)</option>
							<option value="8">其它</option>
					</select>
					</td>
			    </tr>
			    
			    <tr><td colspan="6" height="15px"></td></tr>
			    
			    <tr style="margin:10px 0px;">
			    <td class="m-info-title" width="80">投诉记录：</td>
			    <td class="m-info-content" colspan="3">
			    <textarea  id="fcomplainContent" name="fcomplainContent" 
			    style="width:300px;height:100px;resize:none;border:1px solid #ccc" disabled="disabled" >${ccinfo.fcomplainContent}</textarea>
			    <td class="m-info-title" >沟通记录：</td>
			    <td class="m-info-content" colspan="3">
			    <textarea  id="fcomplainCommunicateContent" name="fcomplainCommunicateContent" 
			    style="width:320px;height:100px;resize:none;border:1px solid #ccc" disabled="disabled" >${ccinfo.fcomplainCommunicateContent}</textarea>
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
					<input  type="text" id="orderNumber"  disabled="disabled" />
					</td>
					<td class="m-info-title" style="padding-left:50px">订单类型：</td>
					<td class="m-info-content">
					<input  type="text" id="orderType"  disabled="disabled" />
					</td>
					<td class="m-info-title" style="padding-left:50px">用车时间：</td>
					<td class="m-info-content">
					<input  type="text" id="orderLoadTime"  disabled="disabled" />
					</td>
			    </tr>
			    <tr><td height="10px"></td></tr>
			    <tr>
					<td class="m-info-title">货物类型：</td>
					<td class="m-info-content">
					<input  type="text" id="orderGoodType"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">车型选择：</td>
					<td class="m-info-content">
					<input  type="text" id="carType"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">增值服务：</td>
					<td class="m-info-content">
					<input  type="text" id="orderAddServer"  disabled="disabled" />
					</td>
			    </tr>
			    <tr><td height="10px"></td></tr>
			    <tr>
					<td class="m-info-title">里程：</td>
					<td class="m-info-content">
					<input  type="text" id="orderMileage"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">运费：</td>
					<td class="m-info-content">
					<input  type="text" id="orderPayAmount"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">客户类型：</td>
					<td class="m-info-content">
					<input  type="text" id="userType"  disabled="disabled" />
					</td>
			    </tr>
			    <tr><td height="10px"></td></tr>
			    <tr>
					<td class="m-info-title">客户名称：</td>
					<td class="m-info-content">
					<input  type="text" id="userName"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">客户电话：</td>
					<td class="m-info-content">
					<input  type="text" id="userPhone"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">提货点：</td>
					<td class="m-info-content">
					<input  type="text" id="sendAddress"  disabled="disabled" />
					</td>
			    </tr>
			    <tr><td height="10px"></td></tr>
			    <tr>
					<td class="m-info-title">收货人：</td>
					<td class="m-info-content">
					<input  type="text" id="receiveUser"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">收货人电话：</td>
					<td class="m-info-content">
					<input  type="text" id="receivePhone"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">卸货点：</td>
					<td class="m-info-content">
					<input  type="text" id="receiveAddress"  disabled="disabled" />
					</td>
			    </tr>
			    <tr><td height="10px"></td></tr>
			    <tr>
					<td class="m-info-title">司机名称：</td>
					<td class="m-info-content">
					<input  type="text" id="driverName"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px">司机电话：</td>
					<td class="m-info-content">
					<input  type="text" id="driverPhone"   disabled="disabled"/>
					</td>
					<td class="m-info-title" style="padding-left:50px"></td>
					<td class="m-info-content">
					</td>
			    </tr>
			    
			   
				
			</table>
	</div>
</fieldset>
	

<!-- 司机处理结果 -->
<div class="driverDiv">
<fieldset>
<legend><strong><h3>司机处理结果</h3></strong></legend>
			<table>
				<tr><td height="10px"></td></tr>
				<tr>
				<td colspan="4"><strong><h3>扣款金额：</h3></strong></td>
				</tr>
				
				<tr><td height="5px"></td></tr>
				<tr>
					<td class="m-info-title">司机姓名：</td>
					<td class="m-info-content">
					<input  type="text" id="resultDriverName" value="${ccinfo.fdriverName}" style="width:92px;" disabled="disabled" />
					</td>
					<td class="m-info-title" style="padding-left:10px">扣除金额：</td>
					<td class="m-info-content">
					<input  type="text" class="easyui-numberbox" precision="2" id="fdriverFineAmount" disabled="disabled"  name="fdriverFineAmount" value="${ccinfo.fdriverFineAmount}"  style="width:90px;"/> 元
					</td>
			    </tr> 
			    <tr>
			    	<td class="m-info-title">司机余额：</td>
					<td class="m-info-content">
					<input  type="text" id="cusBalance"  style="width:92px;" disabled="disabled" value="${ccinfo.driBalance }"/>
					</td>
			    </tr>    
			    <tr><td height="10px"></td></tr>
			    <tr>
				<td colspan="4">
				<strong>是否禁号：</strong>
				是<input type="radio" id="accountArohibitYes" disabled="disabled" name="fdriverAccountDisable" value="true" >
				否<input type="radio" id="accountArohibitNo" disabled="disabled" name="fdriverAccountDisable" value="false"  >
				<input type="text" id="fdriverAccountDisableStart"  disabled="disabled" class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'fdriverAccountDisableEnd\')}'})" name="fdriverAccountDisableStart" value="${ccinfo.fdriverAccountDisableStringStart}" /> -
				<input type="text" id="fdriverAccountDisableEnd" disabled="disabled"  class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'fdriverAccountDisableStart\')}'})" name="fdriverAccountDisableEnd" value="${ccinfo.fdriverAccountDisableStringEnd}" />
				</td>
				</tr>
				<tr><td height="10px"></td></tr>
			    <tr>
				<td colspan="4">
				<strong>是否冻结：</strong>
				是<input type="radio" id="accountFrozenYes" disabled="disabled" name="fdriverFreezeDisable" value="true">
				否<input type="radio" id="accountFrozenNo" disabled="disabled" name="fdriverFreezeDisable" value="false" >
				<input type="text" id="fdriverFreezeDisableStart" disabled="disabled"  class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'fdriverFreezeDisableEnd\')}'})" name="fdriverFreezeDisableStart" value="${ccinfo.fdriverFreezeDisableStringStart}" /> -
				<input type="text" id="fdriverFreezeDisableEnd"  disabled="disabled" class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'fdriverFreezeDisableStart\')}'})" name="fdriverFreezeDisableEnd" value="${ccinfo.fdriverFreezeDisableStringStart}" />
				</td>
				</tr>
				<tr><td height="10px" colspan="4"><hr style="border:0px;border-bottom:1px dashed #ccc; padding-top:5px;"/></td></tr>
				<tr><td colspan="4"><strong><h3>补贴：</h3></strong></td></tr>
				<tr>
				<td class="m-info-title">司机名称：</td>
				<td class="m-info-content">
				<input  type="text" id="subsidyDriverName" value="${ccinfo.fdriverName}" style="width:92px;" disabled="disabled"/>
				</td>
				<td class="m-info-title">返还金额：</td>
				<td class="m-info-content">
				<input  type="text" id="subsidyPrice" name="fdriverRewardAmount" disabled="disabled" disabled="disabled" value="${ccinfo.fdriverRewardAmount}" style="width:80px;" class="easyui-numberbox" precision="2"/> 元
				</td>
				</tr>
				<tr><td height="10px"></td></tr>
				<tr><td colspan="4"><strong>备注：</strong></td></tr>
				<tr>
				<td class="m-info-content" colspan="4">
				<textarea placeholder="" id="driverRemarks" name="fdriverRemark" style="width:370px;height:100px;resize:none;border:1px solid #ccc" disabled="disabled" >${ccinfo.fdriverRemark}</textarea>
				</td>
				</tr>
<!-- 				<tr><td height="45px"></td></tr>	 -->
			  				
			</table>

	</fieldset>
</div>

<!-- 货主处理结果 -->
<div class="shipperDiv">
<fieldset>
<legend><strong><h3>货主处理结果</h3></strong></legend>
			<table>
				<tr><td height="10px"></td></tr>
				<tr>
				<td colspan="4"><strong><h3>退返运费：</h3></strong></td>
				</tr>
				<tr><td height="5px"></td></tr>
				<tr>
					<td class="m-info-title">货主姓名：</td>
					<td class="m-info-content">
					<input  type="text" id="resultShipperName" value="${ccinfo.fuserName}" style="width:92px;" disabled="disabled" />
					</td>
					<td class="m-info-title" style="padding-left:6px">返还金额：</td>
					<td class="m-info-content">
					<input  type="text" disabled="disabled" id="resultShipperDedMoney" name="fshipperFineAmount" value="${ccinfo.fshipperFineAmount}" style="width:100px;" class="easyui-numberbox" precision="2" style="width:90px;"/> 元
					</td>
			    </tr>
			    <tr>
			    	<td class="m-info-title">货主余额：</td>
					<td class="m-info-content">
					<input  type="text" id="driBalance"  style="width:92px;" disabled="disabled" value="${ccinfo.cusBalance }" />
					</td>
			    </tr>   	
			   <tr><td height="10px" colspan="4"><hr style="border:0px;border-bottom:1px dashed #ccc; padding-top:5px;"/></td></tr>
				<tr>
				<td colspan="4"><strong><h3>好运券：</h3></strong></td>
				</tr>
				<tr>
					<td class="m-info-title">数量：</td>
					<td class="m-info-content">
					<input disabled="disabled"  type="number" id="shipperCouponsNum" name="fshipperRewardCouponsAmount"  value="${ccinfo.fshipperRewardCouponsAmount}" style="width:75px;" class="easyui-numberbox"/> 张
					</td>
					<td class="m-info-title" style="padding-left:10px">面额：</td>
					<td class="m-info-content">
					<input  disabled="disabled" type="text" id="shipperCouponsPrice" name="fshipperRewardCouponsDollars" value="${ccinfo.fshipperRewardCouponsDollars}" style="width:100px;" class="easyui-numberbox" precision="2"/> 元
					</td>
			    </tr> 
			    <tr>
			    <td class="m-info-title">期限:</td>
				<td class="m-info-content" colspan="3">
				<input type="text" style="width:138px"disabled="disabled"  id="fshipperRewardCouponsStart"  class="datePicker MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',maxDate:'#F{$dp.$D(\'fshipperRewardCouponsEnd\')}'})" name="fshipperRewardCouponsStart" value="${ccinfo.fshipperRewardCouponsStringStart}" /> -
				<input type="text" style="width:138px" disabled="disabled" id="fshipperRewardCouponsEnd"  class="MtimeInput" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH',minDate:'#F{$dp.$D(\'fshipperRewardCouponsStart\')}'})" name="fshipperRewardCouponsEnd" value="${ccinfo.fshipperRewardCouponsStringEnd}" />
				</td>
				</tr>
				<tr><td height="54px"></td></tr>
				<tr><td colspan="4"><strong>备注：</strong></td></tr>
				<tr>
				<td class="m-info-content" colspan="4">
				<textarea placeholder=""disabled="disabled"  id="shipperRemarks" name="fshipperRemark" style="width:370px;height:100px;resize:none;border:1px solid #ccc">${ccinfo.fshipperRemark}</textarea>
				</td>
				</tr>
<!-- 			  	<tr><td height="10px"></td></tr>	 -->
<!-- 			  	<tr> -->
<!-- 			  	<td colspan="4" style="text-align:right"> -->
<!-- 			  	<div class="Mbutton25 createButton" id="saveDriverAndShipper" align="center">保存</div> -->
<!-- 			  	</td> -->
<!-- 			  	</tr>		 -->
			</table>
	</fieldset>
</div>
<!-- 财务处理结果 -->
<div class="inancedDiv">
	<fieldset>
	<legend><strong><h3>财务处理结果</h3></strong></legend>
		<table>
			<tr><td><strong><h3>处理结果：</h3></strong></td><td><span id="auditResult"></span></td></tr>
			<tr><td><strong><h3>处理原因：</h3></strong></td><td>${ccinfo.fremark}</td></tr>
		</table>
	</fieldset>		
</div>
<div style="width:61px; margin:0 auto;">
	<div class="Mbutton25 createButton" align="center" id="closeDriverAndShipper" style="margin:0 auto;">关闭</div>
</div>
</form>
</main>
</body>
</html>
