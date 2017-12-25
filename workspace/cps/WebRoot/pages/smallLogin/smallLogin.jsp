<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新建</title>
    <style>
		 .uname{margin-top:5px;height:40px;line-height:40px;width:253px;font-size:16px;border:1px solid lightgray;}
		 .upwd{height:40px;line-height:40px;width:253px;font-size:16px;border:1px solid lightgray;}
		 .changeNum{height:40px;line-height:40px;width:140px;font-size:16px;border:1px solid lightgray;}
		 .imag{vertical-align:middle;}
		 a{text-decoration:none;color:blue;font-size:16px;} 
		 a:hover {color: red}
		 .tlt{height:60px;color:#666;font-size:32px;font-family:Arial, Helvetica, sans-serif;letter-spacing:20px;}
		 .info{color:red;text-align:left;padding-left:25px;font-size:12px;background:url(${ctx}/css/images/ts.png) 5px 5px no-repeat;border:1px solid red;}
		.server{height:32px;}
		/* .server a{float:left;font-size:14px;margin-bottom:14px;color:gray;text-decoration:none;} */
		.server .a_right{float:right;*float:false;}
		#tbl p{
			height:30px;
			width:255px;
			line-height:30px;
			background:url(css/images/ts.png) 5px 5px no-repeat;
			text-align:left;
			padding-left:35px;
			border:1px solid red;
			}
			*{
			margin:0px auto;
			padding:0px;
			}
	</style>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<script src="${ctx}/js/css3-mediaqueries.js" type="text/javascript" language="javascript"></script>
	<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
	<script src="${ctx}/js/MD5.js" type="text/javascript" language="javascript"></script>
	<script type="text/javascript" src="${ctx}/js/JPlaceHolder.js"></script>
  </head>
  <body>
  <div>
    <div align="center" style="margin-top:10px">
       	<img width="100"  height="30" src="${ctx}/css/images/cps-red.png">
       	<div style="margin-top:10px">
       	<em style="text-decoration:line-through;">&nbsp;&nbsp;&nbsp;</em>
       	<span>欢迎使用一站式包装交易平台</span>
       	<em style="text-decoration:line-through;">&nbsp;&nbsp;&nbsp;</em>
       	</div>
     </div>
		 <div id="d2">
            	<form id="loginForm" method="post" onsubmit="return formValidate(this)">
                	<table id="tbl" cellpadding="0" cellspacing="0" width="300" height="230">
                        <tr height="60px"><td>
                        	<input type="text" class="uname"  disabled="disabled" style="z-index:9999;position:absolute;float:left;width:40px;background:url(${ctx}/css/images/user1.png) 1px 1px no-repeat;"/>
                        	<input type="text"  id="fname"   name="user.fname" placeholder="请输入用户名/邮箱/手机" class="uname"  style="padding-left:45px;" value=""/>
                        </td></tr>
                        <tr height="50px"><td>
                        	<input type="text" class="upwd"  disabled="disabled" style="z-index:9999;position:absolute;float:left;width:40px;background:url(${ctx}/css/images/pwd.png) 1px 1px no-repeat;"/>
                        	<input type="password" id ="fpassword" name="user.fpassword" placeholder="密码" class="upwd" style="padding-left:45px;" value=""/>
                        </td></tr>
                        <tr class="yzm">
                        	<td class='td5' style='height:50px;text-align:left;line-height:50px;'>
                        	<input type='text' placeholder='&nbsp;&nbsp;请输入验证码' class='changeNum imag' name='identCode' id="identCode"/>
                        	<img src="${ctx}/pages/common/image.jsp" width='80' height='40' style='border:1px solid lightgray;' class='imag' onclick="getYzm();"/>
                        	<a href="javascript:void(0);" onclick="getYzm();">看不清?</a></td>
                        </tr>
                        <tr height="30px"><td id="J_Message" style="" align="left"><%-- <img src="${ctx}/css/images/delete.png"/> --%>
		                       <span class="error"  style="color:red;"></span>			
	                  </td></tr>
                         <tr><td><input type="image" src="${ctx}/css/images/Snap5.png"/></td></tr>
                        <tr>
                        	<td class="server"><%-- ${ctx}/user_findPwd.net --%>
                        		<a href="javascript:void(0);" onclick="goTo(this)" link="${ctx}/user_smallFindPwd.net">忘记密码>></a>
                        		<span class="a_right">还没有账号？<a link="${ctx}/user_smallReg.net"  href="javascript:void(0);" onclick="goTo(this)">立即注册>></a><span>
                        	</td>
                        </tr>
                    </table>
                </form>
                </div>
        	</div>
  </body>
<script type="text/javascript">
   $(document).ready(function () {
	   $(".yzm").css("display","none"); 
	   $("#J_Message").css("display","none"); 
	   if(localStorage.getItem("username"))
		   $("#fname").val(localStorage.getItem("username"));
   });
   function goTo(me){
	   var index = parent.layer.getFrameIndex(window.name);
	   var href = me.getAttribute('link');
	   parent.location=href; 
	   parent.layer.close(index);
   }
   
   function loginToindex(params){
	   $.ajax({
			type:"POST",
			url:"${ctx}/user_wlogon.net",
			data:params,
			dataType:'json',
			async: false,//同步
			success:function(response){
				if(response.type == 2){
					parent.location.href = "${cty}/"+response.url;
					parent.localStorage.setItem("username",$('#fname').val());
				}else{
					parent.location.href = "${ctx}/"+response.url;
					//2015-11-16  若实用cookie 跨iframe 需要通过parent调用父窗口setcookie方法
					//用localStorage 可去掉parent，效果一样
					parent.localStorage.setItem("username",$('#fname').val());
// 					parent.setCookie('username',response.username,30) 
				}			
				parent.layer.close(parent.layer.index);
			}
		});
   }
   
	var num=0;
	function formValidate(){
			var ispass = true;
			if($('#fname').val()==''){
				var html ="<span>请输入用户名!</span>";
		    	$(".error").html(html);
		    	 $("#J_Message").css("display","");
		    	return false;
			}
			if($('#fpassword').val()==''){
				var html ="<span>请输入密码!</span>";
		    	$(".error").html(html);
		    	$("#J_Message").css("display","");
		    	return false;
			}
			var passvalue =$('#fpassword').val();
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
						if(response=="failure" ||response=="uneffect" ){
							var html ="<span>用户名与密码不匹配!</span>";
							if(response=="uneffect"){
								var html ="<span>帐号已经禁用,请联系管理员!</span>";
							}							
			    			$("#fpassword").val('');
			    			$("#fpassword").focus();
			    			$(".error").html(html);
			    			$("#J_Message").css("display","");
			    			num++;
			    			if(num>=3){
			    				$(".yzm").css("display","block");   
			    				parent.layer.style(parent.layer.index,{
			    					height:'340px'
			    				});
			    			}
			    			ispass = false;
						}else if(response=="success"){
							if(num<3){
								loginToindex(params);
							}
						}
					}
			   });
			}
			if(num>=3){
				if($("#identCode").val()==""){
					var html ="<span>请输入验证码!</span>";
					$("#fpassword").val(passvalue);
					$("#identCode").focus();
			    	$(".error").html(html);
			    	$("#J_Message").css("display","");
			    	ispass = false;
				}else{
					var params = $("#loginForm").serialize();
					$.ajax({
					type:"POST",
					url:"${ctx}/user_checkIdentCode.net",
					data:{"identCode":$("#identCode").val()},
					async: false,//同步
					success:function(response){
						if(response=="fail"){
							$("#fpassword").val(passvalue);
							$("#identCode").focus();
							var html ="<span>验证码错误，请重新输入!</span>";
							$(".error").html(html);
					    	$("#J_Message").css("display","");
			    			ispass = false;
						}else if(response=="success"){
							$.ajax({
								type:"POST",
								url:"${ctx}/user_checkUser.net",
								data:params,
								async: false,//同步
								success:function(response){
									if(response=="failure"){
										var html ="<span>用户名与密码不匹配!</span>";
						    			$("#fpassword").val('');
						    			$("#fpassword").focus();
						    			$(".error").html(html);
						    			$("#J_Message").css("display","");
						    			num++;
						    			if(num>=3){
						    				$(".yzm").css("display","block");   
						    				parent.layer.style(parent.layer.index,{
						    					height:'335px'
						    				});
						    			}
						    			ispass = false;
									}else if(response=="success"){
										loginToindex(params);
									}
								}
						   });
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
jQuery(function(){
	    JPlaceHolder.init();    
	});
</script>
</html>
