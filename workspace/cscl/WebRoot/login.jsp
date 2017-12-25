<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>同城供应链-登录</title>
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/MD5.js" type="text/javascript" language="javascript"></script>
<style>
*{margin:0 auto;padding:0}#nav{width:auto;height:auto}#header{width:1080px;height:80px;background:url(pages/pc/css/images/logo.png) 50px 20px no-repeat}
#login{height:280px;width:auto;background-color:#d80c18}#login form{height:260px!important;width:1080px;padding-top:20px;background:url(pages/pc/css/images/banner.png) 0 0 no-repeat}
#login table{border-collapse:collapse;background-color:white;text-align:center;height:240px;width:378px;margin-right:20px}
#login .user,#login .pwd{width:265px!important;height:36px;border:1px solid #ccc;outline:0;font-size:15px;font-family:"微软雅黑";padding-left:40px;+width:305px}
#login .user{background:url(pages/pc/css/images/yh.png) 0 0 no-repeat}#login .pwd{background:url(pages/pc/css/images/mm.png) 0 0 no-repeat}#login .info{font-size:12px}
.info span,info a{display:inline-block}.info span{width:130px;float:left}.info a{text-decoration:none;color:red;float:right;width:115px}
#login ._submit{width:305px;height:40px;background-color:#d80c18;border:0;border-radius:5px;cursor: pointer;color:white;font-family:"微软雅黑";font-size:18px}
</style>
<script type="text/javascript">
function formValidate(){
	var ispass = true;
	if($('#fname').val()==''){
		var html ="<p>请输入用户名!</p>";
    	$("#tiips").html(html);
    	ispass = false;
    	return ispass;
	}
	if($('#fpassword').val()==''){
		var html ="<p>请输入密码!</p>";
    	$("#tiips").html(html);
		ispass = false;
		return ispass;
	}
	$.ajax({
			type:"POST",
			url:"${ctx}/user/checkuser.do",
			data:{"fname":$('#fname').val(),"fpassword":MD5($('#fpassword').val())},
			async: false,//同步
			dataType:'json',
			success:function(response){
				if(response=="failure"){
					var html ="<p>用户名与密码不匹配!</p>";
	    			$("#fpassword").attr("value","");
	    			$("#tiips").html(html);
	    			ispass = false;
				}else{
					if(response.success=="false"){
						var html ="<p>"+response.msg+"</p>";
		    			$("#fpassword").attr("value","");
		    			$("#tiips").html(html);
		    			ispass = false;
					}
					$("#vmiUserFid").val(response.fid);
					$("#fname").val(response.fvmiName);
				}
			}
	});
	return ispass;
}
</script>
</head>
<body>
	<div id="nav">
    	<div id="header"></div>
        <div id="login">
        	<form id="loginForm" action="${ctx}/index.do" method="post" onsubmit="return formValidate(this)">
        		<input type="hidden" id="vmiUserFid" name="vmiUserFid" ></input>
            	<table cellpadding="0" cellspacing="0" border="0" width="378">
            		<tr height="30"><td style="font-family:''微软雅黑';letter-spacing: 5px;">同城物流登录平台</td></tr>
            		<tr height="30"><td id="tiips" style="font-family:'幼圆';font-size:14px;color:red;"></td></tr>
                	<tr height="60"><td><input type="text" class="user" id="fname" name="fname"/></td></tr>
                    <tr height="60"><td><input type="password" class="pwd"  id="fpassword" name="fpassword"/></td></tr>
                    <tr height="60"><td><input type="submit" value="登&nbsp;&nbsp;录" class="_submit"/></td></tr>
                </table>
            </form>
        </div>
    </div>
</body>
</html>
