<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>忘记密码</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/forget.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/forget.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<!-- 内容 -->
	<div class="container reg_bg forgetMain">
		<div class="container">
			<!-- 找回密码 -->
			<div class="row">
				<div class="col-lg-9 col-lg-offset-4 col-md-8 col-md-offset-4 col-sm-9 col-sm-offset-3 col-xs-12 ">
					<h1 class="reg_title">找回密码</h1>
				</div>
				<!-- 手机号 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<div class="form-group myform">
						<input type="text" class="form-control form-register phone_img" id="mobile" placeholder="请输入手机号码" >
						<!-- 提示 -->
						<a class="tooltip-hide" id="mobile_err" data-toggle="tooltip" data-placement="right" title="手机号码不能为空"></a>
						<a class="tooltip-hide" id="mobile2_err" data-toggle="tooltip" data-placement="right" title="手机号码错误"></a>
					</div>
				</div>
				<!-- 验证码 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<div class="form-group myform">
						<input type="text" class="form-control form-register information_img" id="information" placeholder="请输入验证码">
						<!-- 提示 -->
						<a class="tooltip-hide" id="information_err" data-toggle="tooltip" data-placement="right" title="验证码错误"></a>
						<span id="obtain" onclick="sendCode('obtain')">发送验证码</span>
					</div>
				</div>
				<!-- 按钮 -->
				<div class="col-lg-5 col-lg-offset-4 col-md-5 col-md-offset-4 col-sm-6 col-sm-offset-3 col-xs-12">	
					<button type="button" onclick="testCode()" class="btn btn-danger btn-block">下一步</button>
				</div>
		</div>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>
<script type="text/javascript">
var change;
//发送验证码
function sendCode(t,s){
	var mobile=$("#mobile").val();//电话
	if(mobile==""||mobile==null){		
		$("#mobile").focus();
		$("#mobile_err").tooltip('show');
		setTimeout(function(){
			$("#mobile_err").tooltip("hide");
		},1000);
		return false;
	}
		
	var re = /^1[3|4|5|7|8][0-9]\d{8}$/;//手机号码正则表达式
	if(!(re.test(mobile))){
		$("#mobile").focus();
		$("#mobile2_err").tooltip('show');
		setTimeout(function(){
			$("#mobile2_err").tooltip("hide");
		},1000);
		return false;
	}
	
	$("#"+t).text("发送中...");
	$.ajax({
		type:"POST",
		url:"${ctx}/app/user/getFndValidateVCodeTel.do",
		data:{"ftel":mobile},
		dataType:'json',
		success:function(response){
			console.log(response);
			if(response.success=="false"){
				$("#"+t).text(response.msg);
				//$("#"+t).removeAttr("onclick");
				return false;
			}
			else{
				if(s==null){
					s=60;
				}
				$("#"+t).removeAttr("onclick");
				change=setInterval(function(){
					$("#"+t).text(s+"s后重新发送！");
					s--;
					if(s<0){
						clearInterval(change);
						$("#"+t).text("点击重发！");
						$("#"+t).attr('onclick','sendCode("obtain")');
					}
				},1000);
			}
		}
	});
}
//验证验证码
function testCode(){
	var mobile=$("#mobile").val();//电话
	var information=$("#information").val();//验证码
	if(mobile==""||mobile==null){		
		$("#mobile").focus();
		$("#mobile_err").tooltip('show');
		setTimeout(function(){
			$("#mobile_err").tooltip("hide");
		},1000);
		return false;
	}
	var re = /^1[3|4|5|7|8][0-9]\d{8}$/;//手机号码正则表达式
	if(!(re.test(mobile))){
		$("#mobile").focus();
		$("#mobile2_err").tooltip('show');
		setTimeout(function(){
			$("#mobile2_err").tooltip("hide");
		},1000);
		return false;
	}
	if(information==""||information==null||information.length!=6){		
		$("#information").focus();
		$("#information_err").tooltip('show');
		setTimeout(function(){
			$("#information_err").tooltip("hide");
		},1000);
		return false;
	}
// 	location.href="${ctx}/pages/pcWeb/pass_word/new_password.jsp";1
	$.ajax({
		type:"POST",
		url:"${ctx}/app/user/checkLVC.do",
		data:{"ftel":mobile,"lvc":information},
		dataType:'json',
		success:function(response){
			console.log(response);
			if(response.success=="false"){
				$("#information_err").tooltip('show');
				setTimeout(function(){
					$("#information_err").tooltip("hide");
				},1000);
				return false;
			}else{	//验证码通过				
				location.href="${ctx}/pages/pcWeb/pass_word/new_password1.jsp?ftel="+mobile+"&fcode="+information;
			}
		}
	});	
	
}
</script>
</html>
