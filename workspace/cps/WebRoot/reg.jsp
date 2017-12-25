<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册页面</title>
<link rel="stylesheet" href="css/reg.css" type="text/css"/>
<script type="text/javascript" language="javascript" src="js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="js/loginValidation.js" ></script>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript" src="js/MD5.js"></script>
<style type="text/css">
	.red{
		color:red;
	 }
	#foot table tr td .tip{
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
	
	#foot table tr td .warn{
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

</head>
<body>
	<div id="nav">
    	<div id="head">
    		<a href="${ctx}/all_index.jsp" target="_self"><img src="css/images/logo.png"/></a>
    	</div>
        <div id="container">
        	<span class="txt1">账号设置</span>
            <span class="txt2">注册成功</span>
        </div>
        <div id="foot">
       		 <s:form action="user_save"  method="post" onsubmit="return valiCheck();">
	            	<table cellpadding="0" cellspacing="0"  width="760" border="0">
	                	<tr>
	                    	<td class="tdd" width="200"><span class="red">*</span>登录账号：</td>
	                        <td width="290"><input type="text" class="n5" id="fname" name="fname" maxlength="80" onblur="fnameblur();"/></td>
	                        <td id="tipFname"></td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">*</span>设置密码：</td>
	                        <td><input type="password" class="n5" id="fpassword1" name="fpassword" onblur="fpassword1blur();"/></td>
	                        <td id="tipPwdOne"></td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">*</span>确认密码：</td>
	                        <td><input type="password" class="n5" id="fpassword2" onblur="fpassword2blur();"/></td>
	                        <td id="tipPwdTwo"></td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">*</span>手机号码：</td>
	                        <td><input type="text" class="n5" id="ftel" name="ftel"  maxlength="11" onblur="ftelblur();"/></td>
	                        <td id="tipTel"></td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">*</span>验证码：</td>
	                        <td>
	                        	<input type="text" class="n4" id="identCode" name="identCode" onblur="identCodeblur();"/>
	                        	<input type="button" class="img" id="IdentCode" value="点击获取验证码" onclick="sendMobileCode();" />
	                        </td>
	                        <td id="tipIdentCode"></td>
	                    </tr>
	                    <tr>
	                    	<td colspan="3" style="text-align:center;font-size:14px;padding-right:70px;width:620px;">
	                        	<input type="checkbox" name="protocol" onclick="checkboxClick(this);" id="prot"/>我已阅读并同意《CPS相关服务条款和协议》
	                       	</td>
	                    </tr>
	                    <tr>
	                    	<td colspan="3" style="text-align:center;padding-right:100px;width:620px;"><input type="image" src="css/images/bukeyong.png" id="_submit" disabled="disabled"/></td>         
	                    </tr>
	                </table>
           	</s:form>
        </div>
    </div>
</body>
</html>
