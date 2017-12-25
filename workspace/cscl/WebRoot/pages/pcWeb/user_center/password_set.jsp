<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>设置密码--一路好运</title>
<link href="../css/bootstrap.min.css" rel="stylesheet"/>
<link href="../css/common.css" rel="stylesheet" />
<link href="../css/password_set.css" rel="stylesheet"/>
<script type="text/javascript" src="../js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="../js/bootstrap.min.js" ></script>
<script type="text/javascript" src="../js/public.js"></script>
<script type="text/javascript" src="../js/password_set.js"></script>
</head>
<body>
	<div class="container main">
		<p>设置密码</p>
		<p class="remind"><img src="../css/images/user_center/remind.png"/>&nbsp;&nbsp;定期更换密码可以让你的账户更安全。建议密码采用字母和数字混合，并且不短于6位。</p>
		<!--输入信息-->
		<div class="row addIformation">
			<div class="col-xs-6 col-xs-offset-3">
				<p class="account_name">账户名： <span>15067753595</span></p>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<form class="form-inline">
				<div class="form-group oldPassword">	
					<span>原始密码：</span>
      				<input type="password" class="form-control" id="oldPassword" placeholder="">
      				<a href="">忘记密码？</a>
				</div>
				</form>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<form class="form-inline">
				<div class="form-group newPassword">	
					<span>新密码：</span>
      				<input type="password" class="form-control" id="newPassword" placeholder="">
				</div>
				</form>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<p class="newPassword_remind">必须是6-20个英文字母、数字或符号，不能是纯数字</p>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<form class="form-inline">
				<div class="form-group ">	
					<span>确认新密码：</span>
      				<input type="password" class="form-control" id="newPassword_confirm" placeholder="">
				</div>
				</form>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<button type="button" class="btn btn-danger btn-lg">确定</button>	
			</div>
		</div>
	</div>
</body>
</html>
