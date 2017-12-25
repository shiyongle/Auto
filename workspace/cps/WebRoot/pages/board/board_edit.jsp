<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link type="text/css" href="${ctx}/css/board_list.css" rel="stylesheet"/>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<title>纸板订单下单</title>
</head>
<body>
	<div class="conterDiv">
		<div>
			<img alt="" src=""><span>新增地址</span>
			<img alt="" src=""><span>新增常用材料</span>
		</div>
		<div>
			<table>
				<tr>
					<td>客户标签：</td>
					<td colspan="3">	<select class="customerLable"></select><input type="button" value="删除标签"/></td>
				</tr>
				<tr>
					<td>制造商：</td>
					<td><select></select></td>
					<td>材料：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>箱型：</td>
					<td><select></select></td>
					<td>压线方式：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>下料规格：</td>
					<td><select></select></td>
					<td>纸箱规格：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>成型方式：</td>
					<td><select></select></td>
					<td>配送数量：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>横向公式：</td>
					<td><select></select></td>
					<td>纵向公式：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>横向压线：</td>
					<td><select></select></td>
					<td>纵向压线：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>配送时间：</td>
					<td><select></select></td>
					<td>配送地址：</td>
					<td><select></select></td>
				</tr>
				<tr>
					<td>特殊要求：</td>
					<td colspan="3"><select></select><input type="button" value="设为默认"/></td>
				</tr>
			</table>
			<div>
				<input type="button" value="立即下单"/>
				<input type="button" value="加入购物车"/>
			</div>
		</div>
	</div>
</body>
</html>