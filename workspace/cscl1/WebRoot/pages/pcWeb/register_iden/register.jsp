<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>注册</title>
    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/register.css" rel="stylesheet">

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
<%@ include file="/pages/pcWeb/top/top.jsp"%>	
	<div class="container">
		
	<!--步骤-->
	<div class="row">
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step1_img">1</div>
			<div class="step_txt_red">注册</div>
			</div>
		</div>
		
		<div class="col-xs-3 bor">
			<div class="gray_border"></div>
		</div>
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step2_img">2</div>
			<div class="step_txt_gray">认证</div>
			</div>
		</div>
		
		<div class="col-xs-3 bor">
			<div class="gray_border"></div>
		</div>
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step3_img">3</div>
			<div class="step_txt_gray">完成</div>
			</div>
		</div>
	</div>
	<!--步骤-->
	<div class="register_mian">
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4">
			<div style="text-align: right;">
			<label>已有账号？</label>
			<a href="${ctx}/pages/pcWeb/login/login_in.jsp" class="redClolr">马上登陆</a>
			</div>
		</div>	
		</div>
		<form autocomplete="off" id="register_form">
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText user_img" id="ftel" name="ftel" placeholder="请输入手机号">
				<a id="tel_err"  data-placement="top" title="手机号不能为空"></a>
				<a id="tel_err1"  data-placement="top" title="手机号格式错误"></a>
			</div>
		</div>	
		</div>
		
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText information_img" id="information" name="identCode" placeholder="请输入验证码">
				<span id="obtain" onclick="sendCode()">获取验证码</span>
				<a id="information_err" autocomplete="off"  data-placement="top" title="验证码不能为空"></a>
				<a id="information_err1" autocomplete="off"  data-placement="top" title="验证码输入有误"></a>
			</div>
		</div>	
		</div>
		
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4">
			<div class="form-group form_warp">
				<input type="password" class="form-control form-commomText lock_img" id="fpassword" name="fpassword" placeholder="请输入密码">
				<a id="password_err"  data-placement="top" title="密码不能为空"></a>
				<a id="password_err2"  data-placement="top" title="密码为6-8位的数字字母组合"></a>
			</div>
		</div>	
		</div>
		
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4 ">
				<div class="row">
					<h4 style="font-size:16px;">角色选择：</h4>
					<div class="col-xs-4 col-xs-offset-2 text-center">
						<div class="role_img"><img class="customer" src="${ctx}/pages/pcWeb/css/images/register_iden/custorm_checked.png"/></div><label>货主</label>
					</div>
					<div class="col-xs-4 text-center ">
					<div class="role_img"><img class="driver" src="${ctx}/pages/pcWeb/css/images/register_iden/driver.png" /></div><label>司机</label>
					</div>
					<input type="hidden" id="ftype" name="ftype" value="1"/>
				</div>
		</div>	
		</div>
		
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4">
			<div class="form-group form_warp">
		<input type="checkbox" id="agree" class="checkbox-inline" style="width: 20px;height: 20px;" /><a href="#" class="redClolr fuxy">同意《服务协议》</a>
		<a id="agree_err"  data-placement="top" title="请勾选服务协议"></a>
		</div>
		</div>	
		</div>
		
		<div class="row">
		<div class="col-xs-12 col-md-4 col-md-offset-4 text-center">
			<a class="btn btn-danger nextBtn" id="next" href="javascript:void(0)">下一步</a>
		</div>	
		</div>
		</form>
	</div>
</div>	
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>
<script>
var change;
$(function(){
	$("#next").click(function(){
		var tel=$("#ftel").val();//电话
		var information=$("#information").val();//验证码
		var password_m=$("#fpassword").val();//密码
		var agree=$("#agree");
	if(tel==""||tel==null){		
		$("#ftel").focus();
		$("#tel_err").tooltip('show');
		setTimeout(function(){$("#tel_err").tooltip('hide');},1500);
		return false;
	}
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
	if(!(re.test(tel))){
		$("#ftel").focus();
		$("#tel_err1").tooltip('show');
		setTimeout(function(){$("#tel_err1").tooltip('hide');},1500);
		return false;
	}
	if(information==""||information==null){		
		$("#information").focus();
		$("#information_err").tooltip('show');		
		setTimeout(function(){$("#information_err").tooltip('hide');},1500);
		return false;
	}
	
	if(password_m==""||password_m==null){		
		$("#fpassword").focus();
		$("#password_err").tooltip('show');		
		setTimeout(function(){$("#password_err").tooltip('hide');},1500);
		return false;
	}
	//密码限制在6-8位数字和字母
	var password_reg = /^[0-9A-Za-z]{6,8}$/;
	if(!password_reg.test(password_m)){
		$("#fpassword").focus();
		$("#password_err2").tooltip('show');		
		setTimeout(function(){$("#password_err2").tooltip('hide');},1500);
		return false;
	}
	if(!agree.is(":checked")){
		$("#agree_err").tooltip('show');
		setTimeout(function(){$("#agree_err").tooltip('hide');},1500);
		return false;
	}
	
	//用户填完验证码后校验
	var codeState=true;
	if(codeState){
		if(information.length!=6){
			$("#information_err1").tooltip('show');
			setTimeout(function(){$("#information_err1").tooltip('hide');},1500);
		}
	$.ajax({
		type:"POST",
		url:"${ctx}/pcWeb/user/regValiCode.do",
		data:{"ftel":tel,"identCode":information},
		dataType:'json',
		success:function(response){			
			if(response.success=="false"){
				$("#information_err1").tooltip('show');
				setTimeout(function(){$("#information_err1").tooltip('hide');},1500);
				return false;
			}
			else{
				//验证码通过
				var result=$("#register_form").serialize();	
				$.ajax({
					type:"POST",
					url:"${ctx}/pcWeb/user/reg.do",
					data:result,
					success:function(response){					
						var re=JSON.parse(response);
						if(re.success=="true"){
							var ftype=$("#ftype").val();
							if(ftype==1){//货主								
								location.href="${ctx}/pages/pcWeb/register_iden/identity_person.jsp";
							}
							if(ftype==2){//司机								
								location.href="${ctx}/pages/pcWeb/register_iden/identity_driver.jsp";
							}
						}
						else{
							$("body").append('<div class="window_message">注册失败</div>');
							setTimeout(function(){
								$(".window_message").fadeOut();
							},1500);
							return false;
						}
						}
				})
			}
			}
	});	

	}
	
	
	
	});
	
	//用户填写手机号码后
	$("#ftel").blur(function(){
		clearInterval(change);
		$("#obtain").text("获取验证码");
		$("#obtain").attr('onclick','sendCode("obtain")');
	});
	
	

	
})

/*验证码获取倒计时*/
function sendCode(t,s){
	var ftel=$("#ftel").val();
	//没填手机号直接发送验证码，则提示
	if(ftel==""||ftel==null){		
		$("#ftel").focus();
		$("#tel_err").tooltip('show');		
		setTimeout(function(){$("#tel_err").tooltip('hide');},1000);
		return false;
	}
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
	if(!(re.test(ftel))){
		$("#ftel").focus();
		$("#tel_err1").tooltip('show');
		setTimeout(function(){$("#tel_err1").tooltip('hide');},1000);
		return false;
	}
	$("#"+t).text("发送中...");
	$.ajax({
		type:"POST",
		url:"${ctx}/pcWeb/user/getRegValidateVCodeTel.do",
		data:{"ftel":ftel},
		dataType:'json',
		success:function(response){
			console.log(response);
			if(response.success=="false"){
				$("#"+t).text(response.msg);
				$("#"+t).removeAttr("onclick");
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
			$("#"+t).attr('onclick','sendCode("'+t+'")');
			}
			},1000);
			}
		}
	});
	

	}
</script>
</html>