<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%><!doctype html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title></title>
<script type="text/javascript">
	$(document).ready(function(){
		//默认的commbox
			$("#userRoleId_edit").combobox({
				url :"${ctx}/select/getAllCus.do",
				required : true,
				missingMessage : "请选择用户",
				validType : "comboRequired",
				invalidMessage : "请选择用户",
				editable : true,
				panelHeight:200,
				width:145,
				mode: 'local',
				filter:searchItem,//筛选
				valueField: 'optionId',
				textField: 'optionName',
				onSelect:function(record){
					$('#uesrname').val(record.optionName);
					$('#foperate_money,#add,#reduce').removeAttr('disabled');								
					$('#foperate_money').val("");
					$('#after_money').val("");		
					$.ajax({
						url:"${ctx}/userBalance/loadUserBill.do?userRoleId="+record.optionId,
						type:'post',
						success:function(response){
							var json  = JSON.parse(response);
							var money = parseFloat(json.money);
							$('#before_money').val(money);
							$('#foperate_money').on('input propertychange',function(){
									if($("#add").is(":checked")){
										if(parseFloat($('#foperate_money').val())<0){
											$('#foperate_money').val("");
										}												 
										$('#after_money').val(isNaN(money+parseFloat($('#foperate_money').val()))?"":numAdd(money, parseFloat($('#foperate_money').val())));
									}else{	
										 if(parseFloat($('#foperate_money').val())<0){
											$('#foperate_money').val("");
										}
										$('#after_money').val(isNaN(money-parseFloat($('#foperate_money').val()))?"":numSub(money, parseFloat($('#foperate_money').val())));
										if($('#after_money').val()=="0.0"){$('#after_money').val(0)};
									}
								});
							$('.radio_box input[type="radio"]').click(function(){
								$('#foperate_money').val("");
								$('#after_money').val("");		
							});
						}
					});
				}
			});
		//客户 combobox
		$("#customer").on('click',function(){	
			$('#distanceChangeWhy').val("");	
			$('#before_money').val("");	
			$('#foperate_money').val("");
			$('#after_money').val("");		
			$("#userRoleId_edit").combobox('clear');
			$("#userRoleId_edit").combobox("reload","${ctx}/select/getAllCus.do");
		});
		//司机 combobox
		$("#driver").on('click',function(){
			$('#distanceChangeWhy').val("");	
			$('#before_money').val("");	
			$('#foperate_money').val("");
			$('#after_money').val("");	
			$("#userRoleId_edit").combobox('clear');	
			$("#userRoleId_edit").combobox("reload","${ctx}/select/getAllDriver.do");				
		});
		//保存按钮
		$("#saveButton").click(function() {
			if ($("#editDistanceForm").form("validate")) {
				var params = decodeURIComponent($("#editDistanceForm").serialize(), true);
				console.log(params);	
				$.ajax({
					type : "POST",
					url : "${ctx}/userBalance/save.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createWindow").window("close");
								$('#balanceTable').datagrid('reload');
							});
						}
						else {
							$.messager.alert('提示', "操作失败！");
						}
					}
				});
			}
		});
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
	//
	$.extend($.fn.validatebox.defaults.rules, {
    	greaterZero: {
			validator: function(value,param){
				return parseFloat(value)>=0;
			},
			message: '余额必须大于等于零！'
   	 	},
   	 	notEqualZero:{
   	 		validator: function(value,param){
				return parseFloat(value)!=0;
			},
			message: '调整余额不能为零！'
   	 	},
   	 	twoDecimal:{ 	 
   	 		validator:function(value,param){
   	 			var reg = /^\d+(\.\d{1,2})?$/;
   	 			return reg.test(value);
   	 		},
   	 		message:'最多小数点后两位！'  	 	
   	 	}
	});
//加法
function numAdd(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch (e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch (e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    return (num1 * baseNum + num2 * baseNum) / baseNum;
};
//减法
function numSub(num1, num2) {
    var baseNum, baseNum1, baseNum2;
    var precision;// 精度
    try {
        baseNum1 = num1.toString().split(".")[1].length;
    } catch (e) {
        baseNum1 = 0;
    }
    try {
        baseNum2 = num2.toString().split(".")[1].length;
    } catch (e) {
        baseNum2 = 0;
    }
    baseNum = Math.pow(10, Math.max(baseNum1, baseNum2));
    precision = (baseNum1 >= baseNum2) ? baseNum1 : baseNum2;
    return ((num1 * baseNum - num2 * baseNum) / baseNum).toFixed(precision);
};
	
	});
</script>
</head>
	<form id="editDistanceForm" >
		<input type="hidden" name="fuser_name" id="uesrname"/>
		<table class="add-user" id="editDistance">
			<tr>
				<td class="m-info-title">
					选择用户类型<span class="red">*</span>:
				</td>
				<td>
					<input type="radio" checked="checked" id="customer" name="ftype" value="1">客户
					<input type="radio" id="driver" name="ftype" value="2">司机
				</td>
			</tr>
			<tr>
				<td class="m-info-title">用户<span class="red">*</span>:</td>
				<td class="m-info-content">
				<select id="userRoleId_edit" style="width: 145px; height: 25px;" name="fuser_role_id" missingMessage="用户必须选择">
					 <option value="-1" >请选择</option>
				</select>
				</td>
		    </tr>
		    <tr>
		    	<td class="m-info-title">调整之前账户余额（元)<span class="red">*</span>:</td>
				<td>			
					<input type="text"  style="width: 145px; height: 20px; border:1px solid #95b8e7;  background-color:rgb(235, 235, 228)" id="before_money"  name="money" readonly = "readonly">
				</td>
		    </tr>
		    <tr>
					<td class="m-info-title">请选择调整方式<span class="red">*</span>:</td>
				<td class="radio_box">
					<input type="radio" checked="checked" id="add" name="foperate_type" value="1" disabled="disabled">加
					<input type="radio" id="reduce" name="foperate_type" value="-1" disabled="disabled">减
				</td>
			</tr>
			<tr>
				<td class="m-info-title">调整金额(元)<span class="red">*</span>:</td>
				<td class="m-info-content"><input style="width: 145px; height: 20px; border:1px solid #95b8e7;"  type="number"  class=" easyui-validatebox " data-options="required:true, missingMessage:'调整金额必须填写',validType:['notEqualZero','twoDecimal']"  step="0.01"  min="0"   id="foperate_money" name="foperate_money"   disabled="disabled"></td>
			</tr>
			<tr>
				<td class="m-info-title">调整之后账户余额（元)<span class="red">*</span>:</td>
				<td>			
					<input type="text" id="after_money" class="easyui-validatebox" data-options="required:true,validType:'greaterZero',missingMessage:'余额必须大于等于零'" style="width: 145px; height: 20px; border:1px solid #95b8e7; background-color:rgb(235, 235, 228);"  readonly = "readonly">
				</td>
			</tr>	       
			<tr>
				<td class="m-info-title">调整原因<span class="red">*</span>:</td>
				<td class="m-info-content"><input  class="easyui-validatebox" data-options="required:true,missingMessage:'调整原因必须填写！'" style="width: 145px; height: 20px; border:1px solid #95b8e7;" name="fremark"  id="distanceChangeWhy"></td>
			</tr>		       
			<tr>
				<td colspan="2">
					<div class="Mbutton25 createButton" id="saveButton" >保存</div>
				</td>
			</tr>
		</table>
	</form>
</html>