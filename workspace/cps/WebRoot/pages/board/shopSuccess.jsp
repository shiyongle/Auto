<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<title>备货提示</title>
<script>
	$(document).ready(function(){
		$('.btn1').click(function(){
			parent.layer.closeAll();
//			getShopListTable(1);
		})
		$('.btn2').click(function(){
			parent.$('#7a403c6ed40df9351325af3b5cfdce5b').click();
			parent.layer.closeAll();
		})
	})
</script>
<style>
*{
	font-family:微软雅黑;
	cursor:default;
}
.content{
	width:580px;
	height:250px;
}
.title{
	height:auto;
	width:580px;
	text-align:left;
	margin:20px 0px 0px 35px;
	font-size:16px;
	color:#ff3333;
}
.button{
	width:160px;
	height:38px;
	color:#fff;
	background-color:#f53D47;
	border:none;
	font-size:14px;
	cursor:pointer;
}
.imageContent{
	height:150px;
	margin:50px 0px 20px 60px;
}

</style>
</head>
<script type="text/javascript">

</script>
<body>
<div align="center">
	<div  class="content">
		<div class="imageContent">
			<image src="${ctx}/css/images/success2.png" style="float:left;margin-top:10px;"/>
			<div style="width:300px;text-align:left;margin-left:100px;">
				<p style="color:#545454;font-size:28px;height:25px;">下单成功</p>
				<p style="color:#545454;font-size:14px;">您的订单已经生成，等待制造商接收。</p>
				<p style="color:#545454;font-size:14px;">可前往订单记录中查看详情</p>
			</div>
		</div>
		<div>
			<input type="button" value="继续下单" style="margin-right:83px;" class="button btn1"/>
			<input type="button" value="前往订单记录" class="button btn2"/>
		</div>
	</div>
	

</div>

</body>
</html>