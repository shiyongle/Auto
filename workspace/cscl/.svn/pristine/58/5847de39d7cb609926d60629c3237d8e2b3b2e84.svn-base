<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>设置新密码</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/forget.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/forget.js"></script>
    <script src="${ctx}/pages/pcWeb/js/new_password.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<!-- 内容 -->
	<div class="container reg_bg">
		<div class="container main_in">
		  <input type="hidden" id="lvc" val="${lvc}" />
		  <input type="hidden" id="ftel" val="${ftel}" />
			<!-- 找回密码 -->
			<div class="row">
				<div class="col-lg-9 col-lg-offset-4 col-md-8 col-md-offset-4 col-sm-9 col-sm-offset-3 col-xs-12">
					<h1 class="reg_title">设置新密码</h1>
				</div>				
				<!-- 新密码 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<div class="form-group myform">
						<input type="password" class="form-control form-register key_img" id="pass_word1" placeholder="输入您的新密码">
						<a class="tooltip-hide" id="password1_err" data-toggle="tooltip" data-placement="right" title="请输入新密码"></a>
						<a class="tooltip-hide" id="password1_err2" data-toggle="tooltip" data-placement="right" title="两次密码不同"></a>
						<a class="tooltip-hide" id="password1_err3" data-toggle="tooltip" data-placement="right" title="密码为6-8位的数字字母组合"></a>
					</div>
				</div>
				<!-- 确认新密码 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<div class="form-group myform">
						<input type="password" class="form-control form-register key_img" id="pass_word2" placeholder="确认您的新密码" >
						<a class="tooltip-hide" id="password2_err" data-toggle="tooltip" data-placement="right" title="请再次输入密码"></a>
						<a class="tooltip-hide" id="password2_err3" data-toggle="tooltip" data-placement="right" title="密码为6-8位的数字字母组合"></a>
						<!-- 提示 -->
					</div>
				</div>
				<!-- 按钮 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<button type="button" class="btn btn-danger btn-block" id="new_password_btn" onclick="testPassword()">下一步</button>
				</div>
		</div>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>
<script>
$(document).ready(function(){
	//点击变红锁
	 $("#ftel").val("${ftel}");
     $("#lvc").val("${ftel}");
	$(".key_img").focus(function(){
		$(this).css("background-image","url(../css/images/reg/password_2.png)");
	});
	$(".key_img").blur(function(){
		$(this).css("background-image","url(../css/images/reg/password_1.png)");
	})
	
	//判断屏幕宽度
	if($(window).width()>1000){
		$(".reg_bg").css({"margin":"100px auto"});
	}
})
//验证重复输入密码并发送密码
function testPassword(){
	var password1 = $("#pass_word1").val();
	var password2 = $("#pass_word2").val();
	if(password1==""){
		$("#pass_word1").focus();
		$("#password1_err").tooltip('show');
		setTimeout(function(){
			$("#password1_err").tooltip("hide");
		},1000);
		return false;
	}
	if(password2==""){
		$("#pass_word2").focus();
		$("#password2_err").tooltip('show');
		setTimeout(function(){
			$("#password2_err").tooltip("hide");
		},1000);
		return false;
	}
	if(password1!=password2){
		$("#pass_word1").val("");
		$("#pass_word2").val("");
		$("#pass_word1").focus();
		$("#password1_err2").tooltip('show');
		$("#password2_err2").tooltip('show');
		setTimeout(function(){
			$("#password1_err2").tooltip("hide");
			$("#password2_err2").tooltip("hide");
		},1000);
		return false;
	}
	//修改密码限制6-8位数字字母组合。
	var password_reg = /^[a-zA-Z0-9]{6,8}$/;
	if(!password_reg.test(password2)){
		$("#pass_word1").val("");
		$("#pass_word2").val("");
		$("#pass_word1").focus();
		$("#password1_err3").tooltip('show');
		$("#password2_err3").tooltip('show');
		setTimeout(function(){
			$("#password1_err3").tooltip("hide");
			$("#password2_err3").tooltip("hide");
		},1000);
		return false;
	}
	$.ajax({
		type:"post",
		url:"${ctx}/app/user/changePWByLVC.do",
		data:{"password":password1,"lvc":$("#lvc").val(),"ftel":$("#ftel").val()},
		dataType:'json',
		success:function(response){
			 console.log(response);
			if(response.success=="false"){
				//发送失败
				return false;
			}else{
				top.location.href="${ctx}/pages/pcWeb/pass_word/password_succeed1.jsp";
			}
		}
	});
}
</script>
</body>
</html>