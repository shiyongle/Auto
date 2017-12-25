<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
<link rel="stylesheet" type="text/css" href="extlib/resources/css/ext-all.css" />
<script type="text/javascript" src="extlib/ext-all.js"></script>
<script type="text/javascript" src="js/MD5.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<!-- <script src="js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script> -->
<style>
*{margin:0px auto;padding:0px;}
#extWin,#extWin *{margin:0px;}
#nav{width:1280px;height:2750px;}#d2,#d3,#d4,#d5,#d6{width:1280px;height:500px;text-align:center;}
#d3 img,#d4 img,#d6 img{margin-top:50px;}#d5 img{margin-top:5px;}
.loginText{margin-top:66px;margin-right:840px;}
.loginText form{width:336px;height:400px;border:1px solid #C00;}
.loginText form input{box-sizing:content-box;}
.loginText .uname{height:40px;width:253px;background:url(images/user.jpg) 0px 0px no-repeat;padding-left:45px;font-size:16px;border:1px solid lightgray;}
.loginText .upwd{height:40px;width:253px;font-size:16px;background:url(images/pwd.jpg) 0px 0px no-repeat;padding-left:45px;border:1px solid lightgray;}
.loginText .changeNum{height:40px;text-align:left;width:148px;font-size:14px;border:1px solid lightgray;}
.loginText .imag{vertical-align:middle;}
.loginText a{text-decoration:none;color:blue;font-size:14px;}
.loginText .tlt{height:60px;color:#666;font-size:32px;font-family:Arial, Helvetica, sans-serif;letter-spacing:20px;}
.loginText .info{color:red;text-align:left;padding-left:25px;font-size:12px;background:url(images/ts.png) 5px 5px no-repeat;border:1px solid red;}
.server{height:32px;}.server a{float:left;font-size:14px;margin-bottom:14px;color:gray;text-decoration:none;}.server .a_right{float:right;}
#yzmImg{
	cursor:pointer;
}
#foot{
	text-align: center;
	margin-bottom: 20px;
}
</style>
</head>

<body>
	<div id="nav">
        <div id="d1" style="height:200px;width:1280px;background:url(images/L0.png) 0px 0px no-repeat;"></div>
        <div id="d2" style="background:url(images/L1_1.png) 450px 0px no-repeat;">
        	<div class="loginText">
            	<form action="#" method="post" id="form">
                	<table cellpadding="0" cellspacing="0" width="300" height="357">
                    	<tr><td class="tlt" onclick="mmmm();">登录</td></tr>
                        <tr id="tr_verifier" style="display:none;"><td class="info" style="height:30px;" id="verifier">校验信息</td></tr>
                        <tr><td style="height:60px;"><input type="text" placeholder="用户名" class="uname" name="username" id="username" maxlength="30"/></td></tr>
                        <tr><td style="height:60px;"><input type="password" placeholder="密码" class="upwd" name="password" id="password"/></td></tr>
                        <tr class="yzm" id="yzm" style="display:none;"><td class="td5" style="height:62px;text-align:left;line-height:62px;">
                        	<input type="text" placeholder="请输入验证码" class="changeNum" id="validateCode"/>
                            <img id="yzmImg" src="images/shqk1.png" width="80" height="40" style="border:1px solid lightgray;" class="imag"/>
                            <a href="javascript:void(0)" id="yzmImg2">看不清?</a>
                        </td></tr>
                        <tr><td class="server"><a href="javascript:void(0)" onclick="Ext.create('DJ.System.phoneLogin.MyPhoneLoginWin').show();">立即注册</a><a href="javascript:void(0)" onclick="Ext.create('DJ.other.oneTimeLoginCode.OneTimeLoginCodeWin').show();" class="a_right">忘记密码?</a></td></tr>
                        <tr><td><input type="image" src="images/Snap5.png" id="loginBtn" /></td></tr>
                    </table>
                </form>
            </div>
        </div>
        <div id="d3" style=""><img src="images/L2.png" /></div>
        <div id="d4" style=""><img src="images/L3.png" /></div>
        <div id="d5" style=""><img src="images/L4.png" /></div>
        <div id="d6" style=""><img src="images/L5.png" /></div>
        <!-- <iframe src="all_foot.html" scrolling="no" width="1280px;"height="40px" frameborder="0"></iframe> -->
        <div id="foot"><a href="http://www.beianbeian.com/beianxinxi/9e526e15-4f4c-4c48-b683-63ddee52f30d.html" target="_blank">浙ICP备05037094号</a> | Copyright © 2014 Dongjing All Rights Reserved. | 服务热线：0577-55575265 55575245 | QQ号：1718733793</div>
	</div>
<script type="text/javascript">
	<!--获取验证码的那一行 用js隐藏样式不变-->
	/* $(function(){
		$(".yzm").hide();
		});
	var num=0;
	function mmmm(){
		num++;
		if(num>=3){
			$(".yzm").show();
			}
		} */
</script>
</body>
</html>
