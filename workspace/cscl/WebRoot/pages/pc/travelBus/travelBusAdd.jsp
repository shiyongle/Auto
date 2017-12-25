<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>货运班车下单</title>
<style  type="text/css">
#addTriverBus tr{
	display:block;
	width:750px;
	height:30px;
	margin:10px auto;
}
.order-title{width:100px}
.submitOrder{
	text-align:center;
}
.order-btn{
	display:inline-block;
	margin:0 20px;
}
input{
	border: 1px solid #95B8E7;
    margin: 0;
    padding: 0 2px;
    vertical-align: middle;
    width:150px;
}
input.input-disable{
	background:#ddd;
}
</style>
<script type="text/javascript">
$(function(){
	//费用计算
	$("#total-charge").on("input propertychange",totalMoney);
	$("#total-charge").on("blur",totalMoney);
	$("#transfer-charge").on("input propertychange",transferMoney);
	
	//提交表单
	$("#saveBusOrder").click(function() {		
		if ($("#addTriverBusForm").form("validate")) {			
			$.ajax({
				type : "POST",
				url : "${ctx}/travelBus/saveTravelBus.do",//保存货运班车订单接口
				data : formToJOSN("addTriverBusForm"),
				success : function(response) {
					if(response == "success") {
						$.messager.alert('提示', '操作成功', 'info', function() {
							$("#triverBus_Window").window("close");
							$("#triverBusTable").datagrid('reload');
						});
					}else {
						$.messager.alert('提示', "操作失败！");
					}
				}
			});
		}
	});
})		
/*取消下单*/
function cancel(){
	$("#triverBus_Window").window("close");
}

/*总费用*/
function totalMoney(){
		var totalCharge=$("#total-charge").val(),//总费用
			transferCharge=0;//转运费
		$("#delivery-charge").val(totalCharge/2);//提货站点费用
		if($("#transfer-charge").val()!=""){//转运费用判断
			transferCharge=$("#transfer-charge").val();
		};
		$("#bus-charge").val(totalCharge*0.2-transferCharge);//班车费用：总费用20%-转运费用
		$("#send-charge").val(totalCharge*0.3);//派送费用：总费用30%
}

/*转运费*/
function transferMoney(){
		var transferCharge=$("#transfer-charge").val(),//转运费
			totalCharge=$("#total-charge").val();//总费用
		if(transferCharge>totalCharge*0.2){
			$.messager.alert('提示', '输入数据须小于总运费20%', 'info');
			$("#bus-charge").val(totalCharge*0.2);
			$(this).val("");		
		}else{
			$("#bus-charge").val(totalCharge*0.2-transferCharge);//班车费用：总费用20%-转运费用
		}
			
	}
</script>
</head>
	<form id="addTriverBusForm" method="post">
			<table class="add-user" id="addTriverBus" style="padding:10px;">
				<tr>
					<td class="m-info-title">
						<div class="order-title">日期:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fbizDate" class="easyui-validatebox" data-options="required:true"   missingMessage="日期必须填写" onClick="WdatePicker()"/>
					</td>				 

					<td class="m-info-title">
						<div class="order-title">业务经理:</div></td>
					<td class="m-info-content">
						<input type="text" name="fbizManager" class="easyui-textbox"   missingMessage="业务经理必须填写"/>
					</td>				 

					<td class="m-info-title">
						<div class="order-title">提货站点:</div></td>
					<td class="m-info-content">
						<input type="text" name="fpickupSite" class="easyui-textbox"    missingMessage="提货站点必须填写"/>
					</td>				 
			    </tr>
			    
			    <tr>
					<td class="m-info-title">
						<div class="order-title">数量:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="famount" class="easyui-numberbox" data-options="min:0"/>
					</td>				 

					<td class="m-info-title">
						<div class="order-title">公斤:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fkilo" class="easyui-numberbox" data-options="min:0,precision:2"/>
					</td>
					
					<td class="m-info-title">
						<div class="order-title">送达站点:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fdeliverySite" class="easyui-textbox"/>
					</td>					
									 
			    </tr>
				
				<tr>
					<td class="m-info-title">
						<div class="order-title">收货地址:</div></td>
					<td class="m-info-content">
						<input type="text" name="freceiptAddress" class="easyui-textbox"    missingMessage="收货地址必须填写"/>
					</td>				 

					<td class="m-info-title">
						<div class="order-title">货运班车车牌:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fbusPlate" class="easyui-textbox"/>
					</td>
					
					<td class="m-info-title">
						<div class="order-title">转运班车车牌:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="ftransferPlate" class="easyui-textbox"    />
					</td>					
									 
			    </tr>
			    
				<tr>
					<td class="m-info-title">
						<div class="order-title">总费用:</div>
					</td>
					<td class="m-info-content">					
						<input type="text" name="fallCost" class="easyui-validatebox easyui-numberbox" data-options="required:true" id="total-charge" data-options="min:0,precision:2" missingMessage="总费用必须填写"/>						
					</td>				 

					<td class="m-info-title">
						<div class="order-title">提货站点<br/><span class="red">费用50%</span>:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fdeliverySiteCost" class="easyui-textbox input-disable" id="delivery-charge"  readonly  missingMessage="提货站点费用必须填写"/>
					</td>
					
					<td class="m-info-title">
						<div class="order-title">货运班车<br/><span class="red">费用</span>:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fbusCost" class="easyui-textbox input-disable" readonly id="bus-charge"  missingMessage="班车费用必须填写"/>
					</td>					
									 
			    </tr>		

				<tr>
					<td class="m-info-title">
						<div class="order-title">转运班车<br/><span class="red">费用</span>:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="ftransferCost" class="easyui-textbox" id="transfer-charge" />
					</td>				 

					<td class="m-info-title">
						<div class="order-title">派送站点:<br/><span class="red">费用30%</span>:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fsendCost" class="easyui-textbox input-disable" id="send-charge" readonly />
					</td>
					
					<td class="m-info-title">
						<div class="order-title">运费支付方:</div>						
					</td>
					<td class="m-info-content">
						<input type="text" name="fFreightPaymentParty" class="easyui-textbox"   />
					</td>					
									 
			    </tr>	
			    
			    <tr>
					<td class="m-info-title">
						<div class="order-title">代收货款:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fcollectionDelivery" class="easyui-textbox"   />
					</td>				 

					<td class="m-info-title">
						<div class="order-title">运费结算情况:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fFreightSettlement" class="easyui-textbox"    />
					</td>
					
					<td class="m-info-title">
						<div class="order-title">单据编号:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fnumber" class="easyui-textbox"/>
					</td>					
									 
			    </tr>		
			    
			    <tr>
<!-- 					<td class="m-info-title"> -->
<!-- 						<div class="order-title">下单人员:</div> -->
<!-- 					</td> -->
<!-- 					<td class="m-info-content"> -->
<!-- 						<input type="text" name="fcreator" class="easyui-textbox"   /> -->
<!-- 					</td>				  -->

<!-- 					<td class="m-info-title"> -->
<!-- 						<div class="order-title">下单时间:</div> -->
<!-- 					</td> -->
<!-- 					<td class="m-info-content"> -->
<!-- 						<input type="text" name="fcreateTime" class="easyui-textbox"   onClick="WdatePicker()"/> -->
<!-- 					</td> -->
					
					<td class="m-info-title">
						<div class="order-title">备注:</div>
					</td>
					<td class="m-info-content">
						<input type="text" name="fremark" class="easyui-textbox"/>
					</td>					
									 
			    </tr>	
			    	   
			</table>
			<div class="submitOrder">
			<div class="Mbutton25 order-btn" id="saveBusOrder" align="center">保存</div>
			<div class="Mbutton25 order-btn" id="cancelBusOrder" align="center" onclick="cancel()">取消</div>
			</div>
	</form>
</html>
