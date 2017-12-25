<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单详情</title>
<script>
	$(document).ready(function() {
		$('#p').panel({    
			  width:630,    
			  height:300,   
			  cls:"m-audit-info"
		});  
	});
</script>
</head>

	<div id="p" >
		<c:forEach var="clo" items="${clo}">
		<hr style="border:0;background-color:#E0ECFF;height:1px;" />		
		<table id="detail" border="0">
				<tr>
					<td style="color:red;font-size:16px;" class="m-info-title" width="80">订单编号</td>
					<td style="color:red;font-size:16px;" class="m-info-content"  colspan="5">${clo.orderNum}</td>
				</tr>
				<tr >
					<td class="m-info-title">订单类型</td>
					<td class="m-info-content">${clo.orderType}</td>
					<td class="m-info-title">产品类型</td>
					<td class="m-info-content" width="120">${clo.productType}</td>
					<td class="m-info-title">货物重量</td>
					<td class="m-info-content" width="120">${clo.productWeight}</td>
				</tr>
				<tr >
					<td class="m-info-title">车辆类型</td>
					<td class="m-info-content">${clo.carType}</td>
					<td class="m-info-title">是否装箱</td>
					<td class="m-info-content">${clo.binning}</td>
					<td class="m-info-title">货物体积</td>
					<td class="m-info-content">${clo.productVolume}</td>
				</tr>
				<tr >
					<td class="m-info-title" width="80">创建人</td>
					<td class="m-info-content" width="80">${clo.createId}</td>
					<td class="m-info-title" width="85">创建时间</td>
					<td class="m-info-content" width="85">${clo.createTimeString}</td>
					<td class="m-info-title" width="85">订单状态</td>
					<td class="m-info-content" width="85">${clo.orderState}</td>
				</tr>
				<tr >
					<td class="m-info-title">收货人</td>
					<td class="m-info-content">${clo.reciver}</td>
					<td class="m-info-title">用车时间</td>
					<td class="m-info-content">${clo.useCarTimeString}</td>
					<td class="m-info-title">收货电话</td>
					<td class="m-info-content">${clo.recivePhone}</td>
				</tr>
				<tr>
					<td class="m-info-title" width="75">发货地址</td>
					<td class="m-info-content" colspan="5">${clo.takeAddresss}</td>
				</tr>
				<tr>
					<td class="m-info-title" width="75">收货地址</td>
					<td class="m-info-content" colspan="5">${clo.getAddress}</td>
				</tr>
				<tr >
					<td class="m-info-title">备注</td>
					<td class="m-info-content" colspan="5" >${clo.remark}</td>
				</tr>
		</table>
		</c:forEach>
	</div>
	
</html>
