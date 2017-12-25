<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>取消排队</title>
<script type="text/javascript">
$(document).ready(function() {
	//
	var carTpyeText = "${carLine.carType}";
	if(carTpyeText==1){
		$('#carTpyeText').val("小面7座") ;
	}
	if(carTpyeText==2){
		$('#carTpyeText').val("小面9座") ;
	}
	if(carTpyeText==3||carTpyeText==7||carTpyeText==11||carTpyeText==15||carTpyeText==19){
		$('#carTpyeText').val("任意车型") ;
	}
	if(carTpyeText==4||carTpyeText==8||carTpyeText==12||carTpyeText==16||carTpyeText==20){
		$('#carTpyeText').val("厢式货车") ;
	}
	if(carTpyeText==5||carTpyeText==9||carTpyeText==13||carTpyeText==17||carTpyeText==21){
		$('#carTpyeText').val("高栏货车") ;
	
	}
	if(carTpyeText==6||carTpyeText==10||carTpyeText==14||carTpyeText==18||carTpyeText==22){
		$('#carTpyeText').val("低栏货车") ;
	}
	var carSpecText = "${carLine.carSpec}";
	//规格
	if(carSpecText == null){
		$('#carSpecText').val(" ");
	}else if(carSpecText == 1){
		$('#carSpecText').val("面包车") ;
	} else if (carSpecText == 2) {
		$('#carSpecText').val("2.5") ;
	} else if (carSpecText == 3) {
		$('#carSpecText').val("4.2") ;
	} else if (carSpecText == 4) {
		$('#carSpecText').val("5.2") ;
	} else if (carSpecText == 5) {
		$('#carSpecText').val("6.8") ;
	} else if (carSpecText == 6){
		$('#carSpecText').val("9.6") ;
	} else {
		$('#carSpecText').val("规格有误") ;
	}

	$('#cancelBtn').click(function(){
		if($('#cancelForm').form('validate')){
			$.messager.confirm('提示','你确定要取消签到吗?',function(r){
				if(r){
					$.ajax({
						url:'${ctx}/carLine/cancel.do?id='+"${carLine.fcar_line_id}"+'&fremark='+$('#fremark').val(),
						type:'POST',
						dataType:'json',
						success:function(reponse){
							 if(reponse.success == 'success'){
							 	 $.messager.alert('提示', '取消排队成功', 'info',function(){
							 	 	$("#createWindow").window("close");
							 	 	$("#carLineTable").datagrid('reload');
							 	 });
							 }else{
							 	$.messager.alert('提示', '取消排队失败');
							 }
						}
					});
				}
			});
		}
	});
});
</script>
</head>
<div id="createWindow"></div>
<form id="cancelForm">
	<table class="add-user">
		<tr>
			<td class="m-info-title">车牌号:
			</td>
			<td class="m-info-content">
                <input type="text" class="easyui-validatebox" style="width:145px" readonly="readonly" value="${carLine.carNum}">         
            </td>

			<td class="m-info-title">司机姓名:
			</td>
			<td class="m-info-content">
                <input type="text" class="easyui-validatebox" style="width:145px" readonly="readonly" value="${carLine.driverName}">           
            </td>
		</tr>
        <tr>
            <td class="m-info-title">车辆规格:
            </td>
            <td class="m-info-content">
                <input type="text" class="easyui-validatebox" style="width:145px" readonly="readonly" id="carSpecText">         
            </td>

            <td class="m-info-title">车辆类型:
            </td>
            <td class="m-info-content">
                <input type="text" class="easyui-validatebox" style="width:145px" readonly="readonly" id="carTpyeText">           
            </td>
        </tr>
        <tr>
            <td colspan="4"><b>取消原因:</b></td>
        </tr>
        <tr>
            <td colspan="4">
                <textarea name="fremark" id="fremark"style="width:100%; height:50px;" class="easyui-validatebox" data-options=" required:true,validType:['maxLength[50]'], missingMessage:'取消原因必须填写！！'"></textarea>
            </td>
        </tr>
	</table>
	<div class="Mbutton25 createButton" id="cancelBtn" style="margin-right: 5%">确定</div>
</form>
</html>
