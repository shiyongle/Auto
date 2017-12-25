<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>

    <title>设置密码成功</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/password_succeed.css" rel="stylesheet">

    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>

  </head>
<body>
	<!-- 内容 -->
	<div class="jumbotron reg_bg">
		<div class="container">
			<!-- 密码完成 -->
			<div class="row">
				<div class="col-lg-4 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12 password_succeed">
					<img class="img-responsive" src="${ctx}/pages/pcWeb/css/images/reg/reg_yes.png" />
					<h1 class="text">恭喜您“密码”设置成功</h1>
				</div>
				<!-- 按钮 -->
				<div class="col-lg-2 col-lg-offset-5 col-md-2 col-md-offset-6 col-sm-4 col-sm-offset-4 col-xs-12">	
					<button type="button" class="btn btn-danger btn-block " id="password-succeed-btn">立即登录</button>
				</div>
			</div>
		</div>
	</div>
<script>
$(document).ready(function(){
	$("#password-succeed-btn").click(function(){
		location.href="${ctx}/pages/pcWeb/login/login_in.jsp";	
	})
})
</script>
</body>
</html>