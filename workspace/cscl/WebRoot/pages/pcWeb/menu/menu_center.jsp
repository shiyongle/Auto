<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>菜单--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/menu.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/menu.js"></script>
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
				<ul>
					<li><a href="javascript:void(0)" class="active" onclick="temporary_car_jsp()">临时用车</a></li>
					<li><a href="javascript:void(0)" onclick="agreement_car_jsp()">协议用车</a></li>
					<li><a href="javascript:void(0)" onclick="my_order_jsp()">我的订单</a></li>
					<li><a href="javascript:void(0)" onclick="history_order_jsp()">历史订单</a></li>
					<li><a href="javascript:void(0)" onclick="common_order_jsp()">常用订单</a></li>
					<li><a href="javascript:void(0)" onclick="my_index_jsp()">我的首页</a></li>
				</ul>
			</div>
		</div>
		
		<div class="menu">
			<div class="menu_parent">				
			<span></span>
			<font class="menu_title">用户中心</font>
			</div>
			<div class="menu_list">
				<ul>
					<li><a href="javascript:void(0)" onclick="address_manage_jsp()">地址管理</a></li>
					<li><a href="javascript:void(0)" onclick="identity_jsp()">认证</a></li>
					<li><a href="javascript:void(0)" onclick="wallet_jsp()">钱包</a></li>
					<li><a href="javascript:void(0)" onclick="account_manage_jsp()">账号管理</a></li>
					<li><a href="javascript:void(0)" id="child" onclick="childAccount_manage_jsp()">子账号管理</a></li>
				</ul>
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
	//从个人首页点击子账号跳转过来，打开子账号管理页面。
	var type=window.location.search||"";
	if(type!=""){
		type=type.split("=")[1];
		if(type=="$01"){
			$("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/childAccount_manage.jsp");
			$(".menu li a").removeClass("active");
			$("#child").addClass("active");
		}
	}
	
	var username="${username}";
	if(username==""){
		window.login();	
	}	
})
/*临时用车界面*/
function temporary_car_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/temporary_car/temporary_car.jsp");
}else{
	window.login()
}
}

/*协议用车界面*/
function agreement_car_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/agreement_car/agreement_car.jsp");
}else{
	window.login()
}
}

/*我的订单界面*/
function my_order_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/individual_user/my_order.jsp");
}else{
	window.login()
}
}

/*历史订单界面*/
function history_order_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/individual_user/history_order.jsp");
}else{
	window.login()
}
}

/*常用订单界面*/
function common_order_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/individual_user/common_order.jsp");
}else{
	window.login()
}
}

/*地址管理界面*/
function address_manage_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/address_manage.jsp");
}else{
	window.login()
}
}

/*我的首页*/
function my_index_jsp(){
	if("${isPassIdentify}"=="4"&&"${username}" !=""){//个人认证用户
		location.href="${ctx}/pages/pcWeb/index/index_customer.jsp";
		return false;
	}
	if("${isPassIdentify}"=="5"&&"${username}" !=""){//企业认证用户
		location.href="${ctx}/pages/pcWeb/index/index_customer_qy.jsp";
		return false;
	}
	else{
		window.login();
	}
}

/*认证*/
function identity_jsp(){
	var username="${username}",
		isPassIdentify="${isPassIdentify}";
	if(username!=""){
		//判断是否认证过		
		switch(isPassIdentify){//1 个人认证跳过，2 个人认证驳回，3 个人认证审核中，4 个人已认证，5 企业已认证		
		case "1":
			layer.open({
				  title: '请选择',
				  content: '<a href=\'javascript:$("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/user_identity_person.jsp");layer.closeAll();\' class="btn">个人认证</a>'+
				  		   '<a href=\'javascript:$("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/user_identity_company.jsp");layer.closeAll();\' class="btn">企业认证</a>',
				});   
			break;
		case "2":	layer.alert("个人认证已驳回！");
				break;
		case "3":layer.alert("个人认证审核中！");
				break;
		case "4":$("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/user_identity_company.jsp");
				break;
		case "5":layer.alert("您已经认证过了！");
				break;
		}

// 		if("是车主"){
// 			$("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/user_identity_driver.jsp");		
// 		}
	 
}else{
	window.login()
}
}

/*钱包*/
function wallet_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/wallet/wallet_index.jsp");
}else{
	window.login()
}
}

/*账号管理*/
function account_manage_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/account_manage.jsp");
}else{
	window.login();
}
}

/*子账号管理*/
function childAccount_manage_jsp(){
	if("${username}" !=""){
	 $("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/childAccount_manage.jsp");
}else{
	window.login()
}
}

//根据用户类型判断是否显示“子账号管理”
if("${isSub}"=="false"&&"${isPassIdentify}"=="5"&&"${username}" !=""){//企业认证，非子账号登录显示
	$("#child").css("display","block");	
}else{
	$("#child").remove();
}
</script>

