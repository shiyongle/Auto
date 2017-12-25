<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>运费上缴金额校验</title>
<script type="text/javascript">
$(function(){ 
 	$('#saveArrivePayButton').click(function() {
 		 $.messager.confirm('确认','您确认想要运费上缴吗？',function(r){    
		    if (r){    
		    	$.ajax({
					type : "POST",
					url : '${ctx}/order/offlinepay.do?type=1&id='+"${id}",
					dataType:'json',
					success : function(response){
						if(response.flag == "success") {
							$.messager.alert('提示', response.msg , 'info', function() {
								$("#createWindow").window("close");
								$("#CLOrderTable").datagrid('reload');
							});
						}else{
							$.messager.alert('提示', response.msg);
						}
					}
				}); 
		    }    
		});	
	})
}); 
</script>
</head>
	<form id="arrivePayForm">
			<table class="add-user" id="createlngModule">
			    <tr>
			    	<td class="m-info-title">该订单应上缴的运费金额为(元):</td>
					<td class="m-info-content"><input type="text" value="${allCost}" disabled="disabled" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="Mbutton25 createButton" id="saveArrivePayButton">运费上缴</div>
					</td>
				</tr>
			</table>
	</form>
</html>