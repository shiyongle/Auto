<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>登录页面</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script src="js/css3-mediaqueries.js" type="text/javascript" language="javascript"></script>
<script src="js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script src="js/MD5.js" type="text/javascript" language="javascript"></script>
<style type="text/css">
*{margin:0px auto;padding:0px;}
#d1 p{
	float:left;
	font-family:"楷体";
	font-weight:bolder;
	font-size:30px;
	width:150px;
	height:120px;
	line-height:120px;
	text-align:center;
	float:left;
}
#d1 a{
	float:left;
	display:block;
	width:216px;
	height:63px;
	margin-top:28px;
	margin-left:10px;
	text-decoration: none;
}
#d1 img{border:none;}/*hss*/
#d2{width:100%;height:600px;text-align:center;background:url(css/images/BJ.png) #CAA254 center no-repeat;position:absolute;top:120px;left:o;}
.loginText{width:1120px;height:500px;padding-top:100px;position:relative;z-index:3;}
.loginText .loginDiv{width:336px;height:400px;background-color:white;position:relative;left:380px;z-index:4;overflow:visible;}
.loginText .uname{height:40px;line-height:40px;width:253px;background:url(css/images/user1.png) 1px 1px no-repeat;padding-left:45px;font-size:16px;border:1px solid lightgray;}
.loginText .upwd{height:40px;line-height:40px;width:253px;font-size:16px;background:url(css/images/pwd.png) 1px 1px no-repeat;padding-left:45px;border:1px solid lightgray;}
.loginText .changeNum{height:40px;text-align:left;width:148px;font-size:14px;border:1px solid lightgray;}
.loginText .imag{vertical-align:middle;}
.loginText a{text-decoration:none;color:blue;font-size:14px;}
.loginText .tlt{height:60px;color:#666;font-size:32px;font-family:Arial, Helvetica, sans-serif;letter-spacing:20px;}
.loginText .info{color:red;text-align:left;padding-left:25px;font-size:12px;background:url(css/images/ts.png) 5px 5px no-repeat;border:1px solid red;}
.server{height:32px;}.server a{float:left;font-size:14px;margin-bottom:14px;color:gray;text-decoration:none;}.server .a_right{float:right;}
#tbl p{
	height:30px;
	width:255px;
	line-height:30px;
	background:url(css/images/ts.png) 5px 5px no-repeat;
	text-align:left;
	padding-left:35px;
	border:1px solid red;
	}
</style>
<script type="text/javascript">
	$(document).ready(function(){
  		$(".yzm").css("display","none");
 	   if(localStorage.getItem("username"))
		   $("#fname").val(localStorage.getItem("username"));
	});
	$(window).resize(function(){
		if($(window).width()>$(".loginText").width()){
			$("#d2").width("100%");
		}else{
			$("#d2").width(screen.width);
		}
	});
	var num=0;
	function formValidate(){
			var ispass = true;
			if($('#fname').val()==''){
				var html ="<td colspan="+'2'+" style="+'color:red;font-size:14px; '+"><p>请输入用户名!</p></td>";
		    	$("#tiips").html(html);
		    	ispass = false;
			}
			if($('#fpassword').val()==''){
				var html ="<td colspan="+'2'+" style="+'color:red;font-size:14px; '+"><p>请输入密码!</p></td>";
		    	$("#tiips").html(html);
				ispass = false;
			}
			if($('#fpassword').val()!=''&&$('#fname').val()!=''){
				$("#fpassword").val(MD5($("#fpassword").val()));
				var params = $("#loginForm").serialize();
				$.ajax({
					type:"POST",
					url:"${ctx}/user_checkUser.net",
					data:params,
					async: false,//同步
					success:function(response){
						//2016-4-2  by les帐号禁用提示
						if(response=="failure" ||response=="uneffect"){
							var tipString = '用户名与密码不匹配!';
							if(response=="uneffect"){
								tipString = '帐号已经禁用,请联系管理员!';
							}
							var html ="<td colspan="+'2'+" style="+'color:red;font-size:14px; '+"><p>"+tipString+"</p></td>";
			    			$("#fpassword").attr("value","");
			    			$("#tiips").html(html);
			    			ispass = false;
			    			num++;
			    			if(num==3){
			    				$(".yzm").css("display","block");   
			    			}
						}
						else{
							localStorage.setItem("username",$('#fname').val());
						}
					}
			   });
			}
			if(num>=3){
				if($("#identCode").val()==""){
					var html ="<td colspan="+'2'+" style="+'color:red;font-size:14px; '+"><p>请输入验证码!</p></td>";
			    	$("#tiips").html(html);
			    	ispass = false;
				}else{
					$.ajax({
					type:"POST",
					url:"${ctx}/user_checkIdentCode.net",
					data:{"identCode":$("#identCode").val()},
					async: false,//同步
					success:function(response){
						if(response=="fail"){
							var html ="<td colspan="+'2'+" style="+'color:red;font-size:14px; '+"><p>验证码错误，请重新输入!</p></td>";
			    			$("#tiips").html(html);
			    			ispass = false;
						}
					}
			   });
			}
			}
			return ispass;
		}
		function getYzm(){
			var date=new Date();
			$(".imag").attr("src","${ctx}/pages/common/image.jsp?"+date.getMilliseconds());
		}
</script>
</head>

<body>

	<div id="nav">
        <div id="d1" style="height:120px;width:1280px;">
	        <a href="${ctx}/index.jsp" title="返回首页"><img src="${ctx}/css/images/cps-red.png"/></a>
	        <p>欢迎登录</p>
        </div>
        <div id="d2">
        	<div class="loginText">
        		<div class="loginDiv">
            	<form id="loginForm" action="user_logon.net" method="post" onsubmit="return formValidate(this)">
                	<table id="tbl" cellpadding="0" cellspacing="0" width="300" height="357">
                    	<tr><td class="tlt">登录</td></tr>
                        <tr id="tiips"><td></td></tr>
                        <tr><td><input type="text"  id="fname"   name="user.fname" placeholder="用户名" class="uname" value=""/></td></tr>
                        <tr><td><input type="password" id ="fpassword" name="user.fpassword" placeholder="密码" class="upwd" value=""/></td></tr>
                        <tr class="yzm">
                        	<td class='td5' style='height:62px;text-align:left;line-height:62px;'>
                        	<input type='text' placeholder='请输入验证码' class='changeNum' name='identCode' id="identCode"/>
                        	<img src="${ctx}/pages/common/image.jsp" width='80' height='40' style='border:1px solid lightgray;' class='imag'/>
                        	<a href="javascript:void(0);" onclick="getYzm();">看不清?</a></td>
                        </tr>
                        <tr>
                        	<td class="server">
                        		<a href="${ctx}/user_smallReg.net">立即注册</a>
                        		<a href="${ctx}/user_smallFindPwd.net" class="a_right">忘记密码?</a>
                        	</td>
                        </tr>
                        <tr><td><input type="image" src="css/images/Snap5.png"/></td></tr>
                    </table>
                </form>
                </div>
        	</div>
        </div>
        <!--  <div id="foot"><iframe src="all_foot.html" scrolling="no" width="1280px;"height="40px" frameborder="0"></iframe></div>-->
	</div>
</body>
</html>
