<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>新增整车规则</title>
<script type="text/javascript">
	$(document).ready(function() {
// 		$("#user_role_id").combobox({
// 			url : "${ctx}/select/getByRoleId.do",
// 			required : true,
// 			missingMessage : "请选择客户名称",
// 			validType : "comboRequired",
// 			invalidMessage : "请选择客户名称",
// 			editable : true,
// 			width:145,
// 			//mode: 'remote',
// 			valueField: 'optionId',
// 			textField: 'optionName'
// 		});
    	$("#user_role_id").combogrid({
			panelWidth: 250,
			panelHeight: 300,
			idField: 'id',//提交值
			textField: 'name',//选中显示的值
			required : true,
			missingMessage : "请选择客户名称",
			validType : "comboRequired",
			invalidMessage : "请选择客户名称",
			url: '${ctx}/user/combogrid.do',
			method: 'get',
			multiple: false,//可选择多行
			mode: 'remote',
// 			filter: function(q, row){
//  				var opts = $(this).combogrid('options');
//  				if(row.phone.indexOf(q)==0){
//  					return true;
//  				}
// 				return row[opts.textField].indexOf(q) == 0;
// 			},
			columns: [[
				{field:'id',title:'id',width:80,align:'center',hidden : true},
				{field:'name',title:'用户名',width:125,align:'center'},
				{field:'phone',title:'手机号',width:125,align:'center'}
			]],
			fitColumns: true//true表示所有列长度会适应panelWidth的长
		});
		$("#car_spec_id").combobox({
			url : "${ctx}/select/getAllCarType.do",
			required : true,
			missingMessage : "全部",
			//validType : "comboRequired",
			//invalidMessage : "全部",
			 validType: "comboxValidate['car_spec_id']",
			 delay: 1000,
			editable : true,
			width : 110,
			mode : 'remote',
			valueField : 'optionId',
			textField : 'optionName',
			onSelect:function(rec){  
				if(rec.optionId==1||rec.optionId==2)
				{
					$('#startPrice').numberbox('setValue',35);
					$('#startKilometre').numberbox('setValue',5);
					$('#outKilometre').numberbox('setValue',3);
					if(rec.optionId==2){
						$('#startPrice').numberbox('setValue',60);	
						$('#startKilometre').numberbox('setValue',5);
						$('#outKilometre').numberbox('setValue',4);
					}
					$('#foutopint').numberbox('setValue',10);
				}else if(rec.optionId==3)
				{
					$('#startPrice').numberbox('setValue',100);
					$('#startKilometre').numberbox('setValue',10);
					$('#outKilometre').numberbox('setValue',5);
					$('#foutopint').numberbox('setValue',10);
				}else if(rec.optionId==5)
				{
					$('#startPrice').numberbox('setValue',260);
					$('#startKilometre').numberbox('setValue',10);
					$('#outKilometre').numberbox('setValue',6);
					$('#foutopint').numberbox('setValue',10);
				}else if(rec.optionId==6)
				{
					$('#startPrice').numberbox('setValue',500);
					$('#startKilometre').numberbox('setValue',10);
					$('#outKilometre').numberbox('setValue',8);
					$('#foutopint').numberbox('setValue',10);
				}
	        }
		});
		
		
		$("#saveZCButton").click(function() {
			if ($("#createZCForm").form("validate")) {
				var params = decodeURIComponent($("#createZCForm").serialize(), true);
				$.ajax({
					type : "POST",
					url : "${ctx}/protocol/saveCarRule.do",
					data : params,
					success : function(response) {
						if(response == "success") {
							$.messager.alert('提示', '操作成功', 'info', function() {
								$("#createCarloadRule_Window").window("close");
								$("#CLProtocolTable").datagrid('reload');
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
	<form id="createZCForm" method="post">
			<table class="add-user" id="createZC">
				<tr>
					<td class="m-info-title">客户名称<span class="red">*</span>:</td>
					<td class="m-info-content">
<!-- 					<select id="user_role_id" style="width: 145px; height: 25px;" name="userRoleId" missingMessage="用户必须选择" panelHeight="200"> -->
<!-- 							<option value="-1">请选择</option> -->
<!-- 					</select>					 -->
					<select id="user_role_id" style="width: 145px; height: 25px;" name="userRoleId" >
							
					</select>	
					</td>
				 
			    </tr>
				<tr>
					<td class="m-info-title">车辆规格<span class="red">*</span>:</td>
					<td class="m-info-content">
					<select id="car_spec_id" style="width: 145px; height: 25px;" name="carSpecId" missingMessage="车辆规格必须选择">
							<option value="-1">请选择</option>
					</select>
					</td>		
			    	<td class="m-info-title">保底价格<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startPrice" name="startPrice" class="easyui-numberbox"  required="true" precision="2"  missingMessage="保底价格必须填写"/></td>
			
			    </tr>
				<tr>
					<td class="m-info-title">公里单价<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="outKilometre" name="outKilometre" class="easyui-numberbox" required="true" precision="2"  missingMessage="公里单价必须填写"/></td>
					
					<td class="m-info-title">保底公里<span class="red">*</span>:</td>
					<td class="m-info-content"><input style="width: 145px; height: 25px;" type="text" id="startKilometre" name="startKilometre" class="easyui-numberbox" required="true" precision="2"  missingMessage="保底公里必须填写" /></td>
				</tr>
					<tr>
			        <td class="m-info-title">超出点数单价(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="foutopint" name="foutopint" class="easyui-numberbox" required="true" precision="2" missingMessage="超出点数单价必须填写" /></td>
			        <td class="m-info-title">折扣<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fdiscount" name="fdiscount" class="easyui-numberbox" required="true"   missingMessage="折扣必须填写" required="true" min="0" max="100" precision="2" missingMessage="折扣百分比必须填写" style="width: 40%"/>%</td>
				</tr>
				<tr>
					<td class="m-info-title">装货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_1" name="fadd_service_1" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'装货费必须填写'"/></td>
					<td class="m-info-title">卸货费(元)<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fadd_service_2" name="fadd_service_2" class="easyui-numberbox" data-options="validType:['lessOneMillon' , 'greatZero'], required:true,precision:2, missingMessage:'卸货费必须填写'"/></td>
				</tr>
				<tr>
					<td class="m-info-title">备注:</td>
					<td class="m-info-content" colspan="3">
					<textarea type="text" id="pubRemark" name="pubRemark" style="width:470px;height:80px;resize:none;" ></textarea>
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<div class="Mbutton25 createButton" id="saveZCButton" align="center">保存</div>
					</td>
				</tr>
			</table>
	</form>
</html>
