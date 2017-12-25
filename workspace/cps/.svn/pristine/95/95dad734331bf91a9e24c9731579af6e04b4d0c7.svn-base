<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<title>头部</title>
<style>
@charset "utf-8";
*{
	margin:0px auto;
	padding:0px;
	font-family:"宋体";
	}
#head_top{
	width:100%;
	height:32px;
	line-height:32px;
	background-color:#f1f1f1;
	}
#head_top .top_content{
	width:1280px;
	height:32px;
	font-size:12px;
	}
#head_top .top_content .quit{
	text-decoration:none;
	}
#head_top .top_content .quit:hover{
	color:#C00;
	}
#head_bottom{
	width:100%;
	height:80px;
	background-color:#C00;
	}
#head_bottom .bottom_content{
	width:1280px;
	height:80px;
	}
.bottom_content .logo{
	text-decoration:none;
	display:block;
	width:110px;
	height:40px;
	margin-top:20px;
	*+margin-top:10px;
	+margin-top:10px;
	}
.bottom_content .form_search{
	width:1085px;
	height:80px;
	}
.form_search .forSearch{
	width:290px;
	height:25px;
	border:none;
	font-family:"微软雅黑";
	font-size:16px;
	float:right;
	margin-top:27px;
	*+margin-top:14px;
	+margin-top:14px;
	}
.form_search .forSubmit{
	border:none;
	background-color:white;
	height:25px;
	float:right;
	margin-top:27px;
	*+margin-top:14px;
	+margin-top:14px;
	margin-right:90px;
	*+margin-top:45px;
	+margin-top:45px;
	cursor:default;
	}
.bottom_content .personMind{
	border:none;
	width:80px;
	height:80px;
	background:url(${ctx}/css/images/user.png) #C00 30px 30px no-repeat;
	cursor:pointer;
	float:right;
	}
.bottom_content .logo{
	float:left;
}
</style>
</head>

<body>
	<div id="nav">
        <div id="head_top">
            <div class="top_content">
           		<img src="${ctx}/css/images/home.png" height="12px"/>
            	<a href="${ctx}/index.jsp" target="_self">平台首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            	<s:if test="#session.user == null">
            		<a href="${ctx}/user_login.net" target="_self">请登录</a>&nbsp;&nbsp;&nbsp;
            		<a href="${ctx}/user_smallReg.net" target="_self">立即注册</a>
            	</s:if>
				<s:if test="#session.user!= null">Hi,
					<s:property value="#session.user.fname"></s:property>
					<a href="${ctx}/user_logout.net" target="_self" style="font-size:12px;">退出</a>
				</s:if>
            </div>
        </div>
        <div id="head_bottom">
           	<div class="bottom_content">
            	<a href="${ctx}/index.jsp"  target="_self" class="logo" title="返回首页"><img src="${ctx}/css/images/LOGO2.png" width="108" height="38"/></a>
                <input type="button" class="personMind" title="个人中心"  onclick="onclickImg();" />
            </div>
        </div>
    </div>
<script type="text/javascript">
	//百分比宽度缩放
	$(window).resize(function(){
		if($(window).width()>1280){
			$("#head_top").width("100%");
			$("#head_bottom").width("100%");
		}else{
			$("#head_top").width(1280);
			$("#head_bottom").width(1280);
		}
	});
</script>
</body>
</html>
