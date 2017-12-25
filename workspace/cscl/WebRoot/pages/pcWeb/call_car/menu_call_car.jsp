<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>我要叫车--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/menu_call_car.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/menu_call_car.js"></script>
</head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
<div class="container menu_main">
	<div class="row">
	<div class=" col-md-2 menu_container">	
	
		<div class="menu">
			<div class="menu_parent"> 				
			<span></span>
			<font class="menu_title">个人用户</font>
			</div>
			<div class="menu_list">
				<ul id="call_car_list"></ul>
			</div>
		</div>

	</div>
		
		<div class="col-md-10">	
		<iframe src="${ctx}/pages/pcWeb/temporary_car/temporary_car.jsp" id="iframepage" width="100%" frameborder="0" scrolling="no"  onload="iFrameHeight()"></iframe>
		</div>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>
</body>
</html>

<script type="text/javascript">
$(function(){
	if("${username}" ==""){//未登录
		window.login();
	}
	
	/*权限不同展示不同菜单*/
	if("个人用户"){
		var html='<li><a href="javascript:void(0)" class="active" onclick="temporary_car_jsp()">临时用车</a></li>';
		$("#call_car_list").empty().append(html);
	}
	if("企业用户"){
		var html='<li><a href="javascript:void(0)" class="active" onclick="temporary_car_jsp()">临时用车</a></li>'+
				 '<li><a href="javascript:void(0)" onclick="agreement_car_jsp()">协议用车</a></li>';
		$("#call_car_list").empty().append(html);
	}
})
/*临时用车界面*/
function temporary_car_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/temporary_car/temporary_car.jsp");
}else{
	window.login();
}
}

/*协议用车界面*/
function agreement_car_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/agreement_car/agreement_car.jsp");
}else{
	window.login();
}
}


</script>

