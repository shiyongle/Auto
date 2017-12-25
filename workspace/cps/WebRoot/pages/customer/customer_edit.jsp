<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>客户管理编辑界面</title>
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<style type="text/css">
*{
	margin:0px;
	padding:0px;
}
.content{
	text-align: center;
	padding:20px;
}
.content table{
	width:710px;
}
.content table tr{
	height:40px;
}
.content table input[type=text]{
	width:240px;
	height:25px;
	border:1px solid lightgray;
    outline: none;
    padding-left:5px;
    line-height:25px;
}
.content table textarea{
	width:670px;
	height:100px;
	resize:none;
	border:1px solid lightgray;
	outline: none;
	padding-left:5px;
}
.button a{
	width:80px;
	height:24px;
	border:1px solid lightgray;
    display: inline-table;
    text-decoration: none;
   	cursor: pointer;
    line-height: 24px;
    margin-top:20px;
}
.button a:hover{
	color:#fff;
	background-color:red;
}
</style>
<script type="text/javascript">
function closeAll(){
	parent.layer.closeAll();
}
function save(){
	var obj = $('.content form').serializeArray();
	if($.isEmptyObject($('.content input[name=fname]').val())){
		parent.layer.alert("请输入客户名称!");
		return false;
	}
	$.ajax({
			url:'${ctx}/customer/saveOrUpdateCustomer.net',
			type:'post',
			dataType:'json',
			data:obj,
			success:function(response){
				if(response.success){
					parent.layer.alert(response.msg);
					var win = parent.$('#iframepage')[0].contentWindow;
					win.getCustomerList(win.$('#kkpager_btn_go_input').val()||1);
					parent.layer.close(parent.layer.getFrameIndex(window.name));
				}else{
					parent.layer.alert(response.msg);
				}
			}
	});
}
</script>
</head>
<body>
<div class="content">
	<form method="post">
		<table>
			<tr>
				<td style="width: 113px;">客户名称：</td>
				<td><input type="text" name="fname" value="${customer.fname }"/>
						<input type="hidden" name="fid" value="${customer.fid }"/>
						<input type="hidden" name="fcreatetime" value="${customer.fcreatetime }"/>
						<input type="hidden" name="fcreatorid" value="${customer.fcreatorid }"/>
				</td>
				<td>联系人：</td>
				<td><input type="text" name="flinkman" value="${customer.flinkman }"/></td>
			</tr>
			<tr>
				<td>手机号：</td>
				<td><input type="text" name="fphone" value="${customer.fphone }"/></td>
				<td>座机号：</td>
				<td><input type="text" name="fartificialpersonphone" value="${customer.fartificialpersonphone }"/></td>
			</tr>
			<tr>
				<td>传真：</td>
				<td><input type="text" name="ffax" value="${customer.ffax }"/></td>
				<td></td>
				<td></td>
			</tr>
			<tr style="height:0px;"><td>备注：</td><td></td><td></td><td></td></tr>
			<tr>
				<td colspan="4"><textarea  maxlength="40" name="fdescription">${customer.fdescription }</textarea></td>
			</tr>
		</table>
		<div class="button">
			<a onclick="save()">保存</a>
			<a onclick="closeAll()">关闭</a>
		</div>
	</form>
</div>
</body>
</html>