<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
		<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
		<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>成功</title>
    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/register_success.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/pages/pcWeb/css/common.css" />
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
    <script src="${ctx}/pages/pcWeb/js/register.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<!-- 跳过认证 -->
<%@ include file="/pages/pcWeb/top/top.jsp"%>	
<div class="container main">	
	<!--步骤-->
	<div class="row">	
		<div class="col-xs-2">
			<div class="step">
			<div class="step1_img">1</div>
			<div class="step_txt_red">注册</div>
			</div>
		</div>
		<div class="col-xs-3 bor ">
			<div class="red_border"></div>
		</div>
		<div class="col-xs-2 ">
			<div class="step">
			<div class="step2_img">2</div>
			<div class="step_txt_red">认证</div>
			</div>
		</div>
		<div class="col-xs-3 bor ">
			<div class="red_border"></div>
		</div>
		<div class="col-xs-2">
			<div class="step">
			<div class="step2_img">3</div>
			<div class="step_txt_red">完成</div>
			</div>
		</div>
	</div>
	<!--成功提示及登录-->
	<div class="register_iden">
		<div class="row">
			<div class="success_img col-xs-6 col-sm-6">
				<img src="${ctx}/pages/pcWeb/css/images/register_iden/success.png"/>
			</div>
			<div class="success_button col-xs-6 col-sm-6">
				<p>恭喜您，注册成功</p>
				<p>您未提交认证信息！</p>
<%-- 				<a href="${ctx}/pages/pcWeb/login/login_in.jsp" onclick="loginIn()">立即登录</a> --%>
					<a href="javascript:" onclick="loginIn()">立即登录</a>
			</div>	
		</div>
		
		
	</div>
</div>	
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>

</html>
<script>
function loginIn(){
	window.location.href="${ctx}/pcWeb/logout.do" ;
}
</script>