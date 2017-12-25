<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部引用页面</title>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/css/findPassword.css"/> --%>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<%-- <script type="text/javascript" language="javascript" src="${ctx}/js/loginValidation.js" ></script> --%>
<script type="text/javascript" language="javascript" src="${ctx}/js/MD5.js" ></script>
<style type="text/css">
	.red{
		color:red;
	 } 
	 *{
		margin:0px auto;
		padding:0px;
	}
	#nav{
		width:1280px;
	}
	#content{
		width:1180px;
		height:370px;
		overflow:hidden;
	}
	.content1 .tlt,.content2 .tlt,.content3 .tlt{
		height:60px;
		width:880px;
		line-height:60px;
		font-family:Arial, Helvetica, sans-serif;
		font-size:24px;
		color:#666;
		padding-left:300px;
		text-align:center;
		border-bottom:2px solid #999;
	}
	.tlt p{
	width:200px;
	height:60px;
	float:left;
	}
.tlt .stepOne{
	color:#FF0000;
	border-bottom:3px solid #C00;
	}
	.content1 table,.content2 table{
		font-size:20px;
		margin-top:20px;
		line-height:65px;
		/* height:300px; */
		font-family:Arial, Helvetica, sans-serif;
		color:#666;
		margin-left:300px;
	}
.tn1,.tn2{
		width:320px;
		height:35px;
	}
.content1 .tn3{
		display:block;
		float:left;
		width:207px;
		height:35px;
	}
.content1 .tn4{
		display:block;
		float:left;
		width:108px;
		height:36px;
		margin-left:5px;
		border:none;
		cursor: pointer;
	}
.content1 .td1,.content2 .td1{
		width:200px;
	}
	table tr td .tip{
		height:35px;
		width:255px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #00CC00;
	}
	table tr td .warn{
		height:35px;
		width:255px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:red;
		font-size:12px;
		border:1px solid #FF0033;
	}
	 .uname{height:40px;line-height:40px;width:3px;background:url(${ctx}/css/images/user1.png) 1px 1px no-repeat;padding-left:45px;font-size:16px;border:1px solid lightgray;}
	 .mobile{height:40px;line-height:40px;font-size:16px;background:url(${ctx}/css/images/mobile.png) 1px 1px no-repeat;padding-left:45px;border:1px solid lightgray;}
	 .upwd{height:40px;line-height:40px;font-size:16px;background:url(${ctx}/css/images/pwd.png) 1px 1px no-repeat;padding-left:45px;border:1px solid lightgray;}
	 .tn1,.tn2{
		width:270px;
		height:35px;
	}
	.content1 .tn3{
		display:block;
		float:left;
		width:188px;
		height:35px;
	}
	.content1,.content2,.content3{
		width:1180px;
		height:370px;
		font-family:"微软雅黑";
	}
	a{text-decoration:none;color:blue;font-size:16px;} 
 	a:hover {color: red}
</style>
 <script type="text/javascript">
 var bFound = true;  
	 /* $(document).ready(function () {
		   $('.content1').show();
		   $('.content2').hide();
		   $('.content3').hide();
	 }) */
	 $(function(){
			if(window.attachEvent){//设置IE默认值
				$('input').each(function(i,input){
					if(input.getAttribute('placeholder')&&$(this).attr('type')!='password'){
						$(this).val(input.getAttribute('placeholder'));
						$(this).focus(function(){
							if($(this).val()==input.getAttribute('placeholder')){
								$(this).val('');
							}
						}).blur(function(){
							if(!$(this).val()){
								$(this).val(input.getAttribute('placeholder'));
							}
						})
					}
				})
			}
		})
		//账号提示
	 function nameFocus(){
	 		$("#tipFname").html("<p class="+'tip'+">请输入用户名！</p>");
	 }
	//账号校验
	 function nameBlur(){
	 	 if($("#fname").val()!=""){
			 var re = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
		     if (re.test($("#fname").val())) {
		         //$("#tipFname").html("<p class="+'tip'+">支持汉字、字母、数字及'_'组合</p>");
		    	 bFound = true;  
		     } else {
		       $("#tipFname").html("<p class="+'warn'+">用户名格式错误！</p>");
		     }
	 	 }else {
	 		$("#tipFname").html("<p class="+'warn'+">用户名不能为空！</p>");
	 		bFound = false;  
	 	 }
	 }
	//手机号码提示
	 function telFocus(){
	 		$("#tipTel").html("<p class="+'tip'+">请输入账号关联的手机！</p>");
	 }
	var telVerify = false;
	//手机号码校验
	 function telBlur(){
	 	 if($("#ftel").val()!=""){
			 var re = /^1[3|4|5|8][0-9]{9}$/;
		     if (re.test($("#ftel").val())) {
		    		$.ajax({
						type:"POST",
						url:"${ctx}/user_getTelInfo.net",
						async:false,
						dataType:"json",
						data: {"fname":$("#fname").val(),"ftel":$("#ftel").val()},  
						success:function(response){
							if(response.success=="failure"){
								 $("#tipTel").html("<p class="+'warn'+">请输入注册时的手机号！</p>");
								 telVerify =false;
								 bFound=false;
							}else if(response.success=="success"){
								 $("#hidden_fid").val(response.fid);
								 telVerify = true;
								 bFound = true;  
							}
						}
				     });
		     } else {
		        $("#tipTel").html("<p class="+'warn'+">手机号码格式错误</p>");
		     }
	 	 }
	 }
		$(document).ready(function(){
			//默认显示第一个div，隐藏其他
			   $('.content1').show();
			   $('.content2').hide();
			   $('.content3').hide();
			   
		 	$("#fname").focusout(function(){
				$("td .tip").remove();
			});
			
			$("#ftel").focusout(function(){
				$("td .tip").remove();
			});
			
			$("#fpassword1").focus(function(){
				$("#tipPwdOne").html("<p class="+'tip'+">6-80位字符,建议由字母,数字和符号组合</p>");
			}).blur(function(){
				if(!$(this).val()){
					$("#tipPwdOne").html("<p class="+'warn'+">密码不能为空！</p>");
				}
			});
			$("#fpassword1").focusout(function(){
				$("td .tip").remove();
				if($("#fpassword1").val()!=''){
					if($("#fpassword1").val()!=$("#fpassword2").val()){
						$("#tipPwdTwo").html("<p class="+'warn'+">两次输入密码不一致</p>");
						return false;
					}
				}
			});
			
			$("#fpassword2").focus(function(){
				$("#tipPwdTwo").html("<p class="+'tip'+">请再次输入密码</p>");
			});
			$("#fpassword2").focusout(function(){
				$("td .tip").remove();
				if($("#fpassword1").val()!=$("#fpassword2").val()){
					$("#tipPwdTwo").html("<p class="+'warn'+">两次输入密码不一致</p>");
				}
			});
			$("#IdentCode").focus(function(){
				$("#tipIdentCode").html("<p class="+'tip'+">请输入6位验证码</p>");
			});
			//验证码输入之后进行格式的校验
			$("#IdentCode").focusout(function(){
				$("td .tip").remove();
				var reg=/^[0-9]{6}$/;
				if(curCount<=0){
					if($(this).val()){
						if(!(reg.test($("#IdentCode").val()))){
						$("#tipIdentCode").html("<p class="+'warn'+">验证码输入有误</p>");
						}
					}else{
						$("#tipIdentCode").html("<p class="+'warn'+">验证码不能为空</p>");
					}
				}
				
			});
			
	     });
	     //通过短信发送验证码
	    var InterValObj; //timer变量，控制时间
		var count = 60; //间隔函数，1秒执行
		var curCount=0;//当前剩余秒数
	    function SetRemainTime(){
	    	if($("#fname").val()!=undefined && $("#fname").val()!=''){
	    		if(!telVerify){
		    		layer.msg('请输入注册时的手机号');
		    		return false;
		    	}
			}
	    	
	    	curCount = count;      
	    	$("#identCode").attr("disabled", "true");
	        //$("#IdentCode").val("请在" + curCount + "秒内输入"); 
	        InterValObj = window.setInterval(function(){countDown();},1000); //启动计时器，1秒执行一次
	    }
		//点击发送验证码
	    function countDown(){
	    	$("#tipIdentCode").html("");
	    	if (curCount == 0) {    
	                window.clearInterval(InterValObj);//停止计时器
	                $("#identCode").removeAttr("disabled");//启用按钮
	                $("#identCode").val("重新发送验证码");
	           }
	      else {
	                curCount--;
	                $("#identCode").val("请在" + curCount + "秒内输入");
	                $("#identCode").attr("disabled", "true");//禁用按钮
	           }
	    }
	 	/*** 第一个div点击下一步验证*/
		function form1Validation(){
			/* var bFound = true;   */
			if(curCount<=0){
				if($("#fname").val()==''){
			   		 $("#tipFname").html("<p class="+'warn'+">帐号不能为空！</p>");
			   		 bFound =false;
		    	}
				if($("#ftel").val()==''){
				    $("#tipTel").html("<p class="+'warn'+">手机号码不能为空！</p>");
		    		bFound =false;
				}
				if($("#IdentCode").val()==undefined || $("#IdentCode").val()==""){
					$("#tipIdentCode").html("<p class="+'warn'+">验证码不能为空</p>");
					return false;
				}
			}
			if(bFound == false){
				return false;
			}
			//在规定时间过后，需要重新发送验证码，在这里form表单提交的时候，让他return 
	        /* if(curCount==0){
	        	$("#IdentCode").val(" ");
	        	$("#tipIdentCode").html("<p class="+'warn'+">验证码输入超时</p>");
	        	bFound=false;
	        } */
	    	if($("#fname").val()==''){
		   		 $("#tipFname").html("<p class="+'warn'+">帐号不能为空！</p>");
		   		 bFound =false;
	    	}else{
	    		$.ajax({
					type:"POST",
					url:"${ctx}/user_isUnique.net",
					async:false,  
					data: {"fname":$("#fname").val()},  
					success:function(response){
						if(response=="failure"){
							 $("#tipFname").html("<p class="+'warn'+">该帐号未注册！</p>");
							  bFound =false;
						}
					}
			    });
	    	}
			if($("#ftel").val()==''){
			    $("#tipTel").html("<p class="+'warn'+">手机号码不能为空！</p>");
	    		bFound =false;
			}else{
				$.ajax({
					type:"POST",
					url:"${ctx}/user_getTelInfo.net",
					async:false,
					dataType:"json",
					data: {"fname":$("#fname").val(),"ftel":$("#ftel").val()},  
					success:function(response){
						if(response.success=="failure"){
							 $("#tipTel").html("<p class="+'warn'+">请输入注册时的手机号！</p>");
							  bFound =false;
						}else if(response.success=="success"){
							 $("#hidden_fid").val(response.fid);
							 bFound = true;  
						}
					}
			     });
			}
			//验证码的校验
			if(!$("#IdentCode").val()==''){
				$.ajax({
						type:"POST",
						url:"${ctx}/user_valiCode.net",
						async:false,
						dataType:"text", 
						data: {"fname":$("#fname").val(),"identCode":$("#IdentCode").val()},  
						success:function(response){
							if(response=="fail"){
								 $("#tipIdentCode").html("<p class="+'warn'+">验证码输入错误</p>");
								 bFound=false;
							}else if(response=="success"){
								if(bFound!=false){
									$("#content1").hide();
									$("#content2").show();
									$("#content3").hide();
								}
							}
						}
				});
			}
			
	 	 	return bFound;
		}
		//发送验证码
		 function sendMobileCode(){
			//用户名检验;
			if(bFound != false){
				if($("#fname").val()==''){
			   		 $("#tipFname").html("<p class="+'warn'+">帐号不能为空！</p>");
			   		bFound = false;  
		    	}else{
		    		$.ajax({
						type:"POST",
						url:"${ctx}/user_isUnique.net",
						async:false,  
						data: {"fname":$("#fname").val()},  
						success:function(response){
							if(response=="failure"){
								 $("#tipFname").html("<p class="+'warn'+">该帐号未注册！</p>");
								 bFound = false;  
							}else if(response=="success"){
								bFound = true;  
							}
						}
				    });
		    	}
			}
	    	
	    	//手机号检验;
	    	if(bFound != false){
	    		if($("#ftel").val()==''){
				    $("#tipTel").html("<p class="+'warn'+">请先输入手机号！</p>");
		    		bFound =false;
				}else{
					$.ajax({
						type:"POST",
						url:"${ctx}/user_getTelInfo.net",
						async:false,
						dataType:"json",
						data: {"fname":$("#fname").val(),"ftel":$("#ftel").val()},  
						success:function(response){
							if(response.success=="failure"){
								 $("#tipTel").html("<p class="+'warn'+">请输入注册时的手机号！</p>");
								 telVerify = false;
								 bFound =false;
							}else if(response.success=="success"){
								 $("#tipTel").html("");
								 $("#hidden_fid").val(response.fid);
								 telVerify = true;
								 bFound = true;  
							}
						}
				     });
				}
	    	}
	    	
	    	if(bFound == false){
	    		if($("#fname").val()==''){
			   		 $("#tipFname").html("<p class="+'warn'+">帐号不能为空！</p>");
		    	}
	    		if($("#ftel").val()==''){
				    $("#tipTel").html("<p class="+'warn'+">请先输入手机号！</p>");
				}
			    window.clearInterval(InterValObj);
			    curCount=0;
			    $("#identCode").removeAttr("disabled");//启用按钮
	            $("#identCode").val("点击发送验证码");
	    		return;
			}else{
				SetRemainTime();
			}
	        //异步请求发送验证码
	    	$.ajax({
						type:"POST",
						url:"${ctx}/user_getMobileCodeByfindPassword.net",
						async:true,
						data: {"fname":$("#fname").val(),"ftel":$("#ftel").val()}, 
						dataType:"text", 
						success:function(response){
							if(response=="fail"){
								 $("#tipIdentCode").html("<p class="+'warn'+">手机号已经被注册或者输入有误</p>");
								 $("#identCode").removeAttr("disabled");//启用按钮
								 $("#identCode").val("重新获取验证码");
							}
						}
				 });
	    }
		/*** 第2个div点击提交验证*/
		function valiCheckPwd(){
		    var ispass =true;
			if($("#fpassword1").val()==''){
	    		 $("#tipPwdOne").html("<p class="+'warn'+">密码不能为空！</p>");
	    		 return false;
	    	}
	    	if($("#fpassword2").val()==''){
	    		 $("#tipPwdTwo").html("<p class="+'warn'+">密码确认不能为空！</p>");
	    		  return false;
	    	}
	    	if($("#fpassword1").val()!=$("#fpassword2").val()){
	    		return  false;
	    	}
	    	if($("#fpassword1").val()!='' && $("#fpassword2").val()!=''){
				 $("#hidden_fname").val($("#fname").val());
				 $("#hidden_ftel").val($("#ftel").val());
				 $("#hidden_identCode").val($("#identCode").val());
				 $("#fpassword1").val(MD5($("#fpassword1").val()));
				 $("#fpassword2").val(MD5($("#fpassword2").val()));
		    }
		    if(ispass){
				var params = $("#form2").serialize();
				$.ajax({
					type:"POST",
					url:"${ctx}/user_update.net",
					async:false,
					dataType:"json",
					data: params,  
					success:function(response){
						if(response.success=="success"){
							$("#content1").hide();
							$("#content2").hide();
							$("#content3").show();
						}
					}
			     });
		    }
		    return ispass;
		}
	 function login(m){
		 var i =layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['350px', '310px'],
			    content: "${ctx}/pages/smallLogin/smallLogin.jsp"
			});
		 layer.style(i,{
				'border-radius':'25px',
				'border':'2px solid #a1a1a1'
			})
	 }
 </script>
</head>
<body style="background-color:#F2F2F2;">
	<div id="nav">
		<div style="margin-top:35px;padding:20px;">
			<a href="${ctx}/index.jsp" title="返回首页"><img style="vertical-align:middle;border:none;" src="${ctx}/css/images/cps-red.png"/></a>
			<span style="font-size:35px; position: relative;top: 22px;"><b>|密码找回!</b> </span>
			<div style="display:inline;float:right;font-size:20px;"><a href="javascript:void(0);" onclick="login(this)" style="color:grey;">登录</a>&nbsp;<a style="color:grey;" href="${ctx}/user_smallReg.net">注册</a></div>
			
		</div>
        <div id="content" style="height:500px;border:1px solid lightgrey;padding:30px;margin-top:0px;background-color:#ffffff;">
        <p id="info">忘记密码</p>
        	<!--第一个-->
        	<div class="content1" id="content1">
            	<div class="tlt"><p class="stepOne">身份验证</p><p>设置新密码</p><p>完成</p></div>
                <form id="form1" action="javascript:void(0)">
                	<table cellpadding="0" cellspacing="0"  width="750" >
                    	<tr>
                        	<td style="text-align:right;width:150px;"><span class="red">&nbsp;*&nbsp;</span></td>
                            <td><input placeholder="请输入用户名"  type="text" class="tn1 uname"  id="fname" name="fname" maxlength="80" onblur="nameBlur();"  onfocus="nameFocus();"/></td>
                            <td id="tipFname" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><span class="red">&nbsp;*&nbsp;</span></td>
                            <td><input  placeholder="验证手机"  type="text" class="tn2 mobile" id="ftel" name="ftel" maxlength="11" onblur="telBlur();"  onfocus="telFocus();"/></td>
                            <td id="tipTel" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><span class="red">&nbsp;*&nbsp;</span></td>
                            <td><input placeholder="短信验证码"  style="font-size:16px;padding-left:15px;" type="text" class="tn3" id="IdentCode"/><input type="button" class="tn4" id="identCode" name="identCode" value="获取短信验证码" onclick="SetRemainTime();sendMobileCode();"/></td>
                            <td id="tipIdentCode" class="td1"></td>
                        </tr>
                        <tr>
                        	<td colspan="3" style="text-align:left;height:100px;width:590px;padding-left:150px;"><input type="image" src="${ctx}/css/images/next.png" onclick="form1Validation()" onkeypress="if (event.keyCode == 13) _form1Validation();"/></td>
                        </tr>
                    </table>
                </form>
            </div>
            <!--第二个-->
            <div class="content2" id="content2">
            	<div class="tlt"><p>身份验证</p><p class="stepOne">设置新密码</p><p>完成</p></div>
                <form action="javascript:void(0)"  id="form2">
                	<table cellpadding="0" cellspacing="0"  width="750">
                    	<tr>
                        	<td style="text-align:right;width:150px;"><span class="red">&nbsp;*&nbsp;</span></td>
                            <td>
                            	<input type="hidden" id ="hidden_fid" name="fid"></input>
                    			<input type="hidden" id ="hidden_fname" name="fname"></input>
                    			<input type="hidden" id ="hidden_ftel" name="ftel"></input>
                    			<input type="hidden" id ="hidden_identCode" name="identCode"></input>
                            	<input type="password" class="tn1 upwd" id="fpassword1" name="fpassword" placeholder="设置新密码" />
                            </td>
                            <td id="tipPwdOne" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><span class="red">&nbsp;*&nbsp;</span></td>
                            <td><input type="password" class="tn2 upwd" id="fpassword2" placeholder="确认密码"/></td>
                            <td id="tipPwdTwo" class="td1"></td>
                        </tr>
                        <tr>
                        	<td colspan="3" style="text-align:left;height:100px;width:600px;padding-left:150px;">
                        	<input type="image" src="${ctx}/css/images/tj.png" onclick="valiCheckPwd();" onkeypress="if (event.keyCode == 13) _valiCheckPwd();"/>
                        	</td>
                        </tr>
                    </table>
                </form>
            </div>
            <!--第三个-->
            <div class="content3" id="content3">
            	<div class="tlt"><p>身份验证</p><p>设置新密码</p><p class="stepOne">完成</p></div>
            	<div style="margin-top:50px;padding:20px;" align="center">
					<img style="vertical-align:middle;margin-left:0px;margin-right:70px;" src="${ctx}/css/images/success1.png"/>
					恭喜你，密码修改成功!
	            	<div style="margin-top:50px"><input type="image" src="${ctx}/css/images/ljlogin.png" onclick="login(this);"/></div>
				</div>
            </div>
        </div>
    </div>
</body>
</html>
