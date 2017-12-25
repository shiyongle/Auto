<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>头部引用页面</title>
<link rel="stylesheet" type="text/css" href="css/findPassword.css"/>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript" src="js/loginValidation.js" ></script>
<script type="text/javascript" language="javascript" src="js/MD5.js" ></script>
<style type="text/css">
	.red{
		color:red;
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
</style>
 <script type="text/javascript">
 	$(document).ready(function(){
	 	$("#fname").focusout(function(){
			$("td .tip").remove();
		});
		
		$("#ftel").focusout(function(){
			$("td .tip").remove();
		});
		
		$("#fpassword1").focus(function(){
			$("#tipPwdOne").html("<p class="+'tip'+">6-80位字符,建议由字母,数字和符号组合</p>");
		});
		$("#fpassword1").focusout(function(){
			$("td .tip").remove();
			if($("#fpassword1").val()!=''){
				if($("#fpassword1").val()!=$("#fpassword2").val()){
					$("#tipPwdTwo").html("<p class="+'warn'+">两次输入密码不一致</p>");
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
			if(!(reg.test($("#IdentCode").val()))){
			$("#tipIdentCode").html("<p class="+'warn'+">验证码输入有误</p>");
			}
		});
		
     });
     //通过短信发送验证码
    var InterValObj; //timer变量，控制时间
	var count = 30; //间隔函数，1秒执行
	var curCount=0;//当前剩余秒数
    function SetRemainTime(){
    	curCount = count;      
    	$("#identCode").attr("disabled", "true");
        //$("#IdentCode").val("请在" + curCount + "秒内输入"); 
        InterValObj = window.setInterval(function(){countDown();},1000); //启动计时器，1秒执行一次
    }
	//点击发送验证码
    function countDown(){
    	if (curCount == 0) {    
                window.clearInterval(InterValObj);//停止计时器
                $("#identCode").removeAttr("disabled");//启用按钮
                $("#identCode").val("重新发送验证码");
           }
      else {
                curCount--;
                $("#identCode").val("请在" + curCount + "秒内输入");
           }
    }
 	/*** 第一个div点击下一步验证*/
	function form1Validation(){
		var bFound = true;  
		if($("IdentCode").val()==""){
			$("#tipIdentCode").html("<p class="+'warn'+">验证码不能为空</p>");
			return false;
		}
		//在规定时间过后，需要重新发送验证码，在这里form表单提交的时候，让他return 
        if(curCount==0){
        	$("#IdentCode").val(" ");
        	$("#tipIdentCode").html("<p class="+'warn'+">验证码输入超时</p>");
        	bFound=false;
        }
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
					}else{
						 $("#hidden_fid").val(response.fid);
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
					data: {"fname":$("#fname").val(),"IdentCode":$("#IdentCode").val()},  
					success:function(response){
						if(response=="fail"){
							 $("#tipIdentCode").html("<p class="+'warn'+">验证码输入错误</p>");
							 bFound=false;
						}
					}
			});
		}
		if(bFound!=false){
			$("#content1").hide();
			$("#content2").show();
			$("#content3").hide();
		}
 	 	return bFound;
	}
	//发送验证码
	 function sendMobileCode(){
    	//如果手机号为空，那么必选先输入手机号
    	if($("#ftel").val()==''){
		    $("#tipIdentCode").html("<p class="+'warn'+">请先输入手机号</p>");
		    window.clearInterval(InterValObj);
		    $("#identCode").removeAttr("disabled");//启用按钮
            $("#identCode").val("点击发送验证码");
    		return;
		}
        //异步请求发送验证码
    	$.ajax({
					type:"POST",
					url:"${ctx}/user_getMobileCode.net",
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
    		  bFound =false;
    	}
    	if($("#fpassword2").val()==''){
    		 $("#tipPwdTwo").html("<p class="+'warn'+">密码确认不能为空！</p>");
    		  ispass =false;
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
 </script>
</head>
<body>
	<div id="nav">
        <p id="info">找回密码</p>
        <div id="content">
        	<!--第一个-->
        	<div class="content1" id="content1">
            	<div class="tlt"><p class="stepOne">身份验证</p><p>设置新密码</p><p>完成</p></div>
                <form id="form1" action="javascript:void(0)">
                	<table cellpadding="0" cellspacing="0"  width="750" >
                    	<tr>
                        	<td style="text-align:right;width:150px;"><span class="red">*</span>登录账号：</td>
                            <td><input type="text" class="tn1" id="fname" name="fname" maxlength="80" onblur="nameBlur();"  onfocus="nameFocus();"/></td>
                            <td id="tipFname" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><span class="red">*</span>已验证手机号：</td>
                            <td><input type="text" class="tn2" id="ftel" name="ftel" maxlength="11" onblur="telBlur();"  onfocus="telFocus();"/></td>
                            <td id="tipTel" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;"><span class="red">*</span>验证码：</td>
                            <td><input type="text" class="tn3" id="IdentCode"/><input type="button" class="tn4" id="identCode" name="identCode" value="点击发送验证码" onclick="SetRemainTime();sendMobileCode();"/></td>
                            <td id="tipIdentCode" class="td1"></td>
                        </tr>
                        <tr>
                        	<td colspan="3" style="text-align:left;height:100px;width:590px;padding-left:150px;"><input type="image" src="css/images/next.png" onclick="form1Validation()" onkeypress="if (event.keyCode == 13) _form1Validation();"/></td>
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
                        	<td style="text-align:right;width:150px;">设置新密码：</td>
                            <td>
                            	<input type="hidden" id ="hidden_fid" name="fid"></input>
                    			<input type="hidden" id ="hidden_fname" name="fname"></input>
                    			<input type="hidden" id ="hidden_ftel" name="ftel"></input>
                    			<input type="hidden" id ="hidden_identCode" name="identCode"></input>
                            	<input type="password" class="tn1" id="fpassword1" name="fpassword"/>
                            </td>
                            <td id="tipPwdOne" class="td1"></td>
                        </tr>
                        <tr>
                        	<td style="text-align:right;">确认密码：</td>
                            <td><input type="password" class="tn2" id="fpassword2"/></td>
                            <td id="tipPwdTwo" class="td1"></td>
                        </tr>
                        <tr>
                        	<td colspan="3" style="text-align:left;height:100px;width:600px;padding-left:150px;">
                        	<input type="image" src="css/images/tj.png" onclick="valiCheckPwd();" onkeypress="if (event.keyCode == 13) _valiCheckPwd();"/>
                        	</td>
                        </tr>
                    </table>
                </form>
            </div>
            <!--第三个-->
            <div class="content3" id="content3">
            	<div class="tlt"><p>身份验证</p><p>设置新密码</p><p class="stepOne">完成</p></div>
                <img src="css/images/dg.png" class="dg"/>
        	<p class="tlt1">修改密码成功!<br /><br />
	

            <span>
            请牢记您的新密码。<a href="${ctx}/index.jsp" >返回首页</a></span></p>
            </div>
        </div>
    </div>
</body>
</html>
