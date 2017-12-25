<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<title>个人中心-申请认证</title>
	<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	#head_top{
		width:1270px;
		font-family:Arial, Helvetica, sans-serif;
		height:32px;
		line-height:40px;
		font-size:14px;
		padding-left:10px;
		background-color:#f2f2f2;
		color:#aaa;
	}
	#head_top .s2{margin-left:740px;}
	#head_top a{text-decoration:none;color:#aaa;}
	#head_top a:hover{text-decoration:underline;color:#c00;}
	#head{
		width:100%;
		height:84px;
		background-color:#C00;
		}
	.head_content{
		width:1270px;
		height:84px;
		line-height:84px;
		padding-left:10px;
		}
	.head_content a{
		text-decoration: none;
	}
	.head_content span{
		font-family:"黑体";
		font-size:24px;
		color:white;
		margin-left:20px;
		}
	.head_content form{
		float:right;
		}
	#container{
		width:1280px;
		height:auto;
		margin-top:15px;
		}
	.container_left,.container_right{
		float:left;
		}
	.container_left{
		width:120px;
		height:500px;
		}
	.container_left ul{
		height:300px;
		width:110px;
		padding-right:10px;
		}
	.container_left ul li{
		list-style:none;
		height:35px;
		line-height:35px;
		text-align:right;
		width:100px;
		margin-bottom:5px;
		cursor:pointer;
		font-family:"微软雅黑";
		}
	.container_left ul a{
		text-decoration:none;
		color:black;
		}
	.container_left ul a:hover{
		color:red;
		}
	.container_right{
		width:1150px;
		height:auto;
		}
	#foot{
		width:1280px;
		height:100px;
		}
	#container .container_left .selected a{
		color:#c00;
		font-family:"华文琥珀";
		}
</style>
</head>

<body>
	<div id="nav">
    	<div id="head_top">
	          	<img src="${ctx}/css/images/home.png" height="12px"/>
	           	<a href="${ctx}/all_index.jsp" target="_self">平台首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	           	<s:if test="#session.user == null">
	           		<a href="${ctx}/user_login.net" target="_self">请登录</a>&nbsp;&nbsp;&nbsp;
	           		<a href="user_reg.net" target="_self">免费注册</a>
	           	</s:if>
				<s:if test="#session.user!= null">Hi,
					<s:property value="#session.user.fname"></s:property>
					<a href="${ctx}/user_logout.net" target="_self">退出</a>
		       </s:if>
        </div>
    	<div id="head">
        	<div class="head_content">
            	<a href="${ctx}/index.jsp" title="返回首页"><img src="${ctx}/css/images/cps-w.png" style="vertical-align:middle;"/></a>
                <span>终端客户用户中心</span>
            </div>
        </div>
        <div id="container">
        	<div class="container_left">
            	<ul>
                	<li id="certificate" class="selected"><a href="javascript:void(0);"  onclick="certificate();" target="forTarget">申请认证</a></li>
                    <!--  <li><a href="javascript:void(0);"  onclick="onclickImg();" target="forTarget">权限配置</a></li>-->
                    <li id="my_address" ><a href="javascript:void(0);"  onclick="my_address();"  target="forTarget">我的地址</a></li>
                    <li id="my_baseinfo"><a href="javascript:void(0);"  onclick="my_baseinfo();" target="forTarget">基础资料</a></li>
                    <!--   <li id="u_param"    ><a href="javascript:void(0);"  onclick="u_param();"     target="forTarget">用户参数</a></li>-->
                    <li id="update_pwd" ><a href="javascript:void(0);"  onclick="update_pwd();"  target="forTarget">修改密码</a></li>
                    <li id="web_iemial" ><a href="javascript:void(0);"  onclick="web_iemial();"  target="forTarget">站内信</a></li>
                </ul>
            </div>
            <div class="container_right">
            	<iframe id="treeIframe" src="${ctx}/usercenter/certificate.net" frameborder="0" width="1145" height="700" scrolling="no" name="forTarget" id="iframepage"></iframe>
            </div>
        </div>
    </div>
<script type="text/javascript">
	$(window).resize(function(){
		if($(window).width()>$(".head_content").width()){
			$("#head").width("100%");
		}else{
			$("#head").width(1280);
		}
	});
	function certificate(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/certificate.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#certificate").addClass("selected");
		$("#certificate").siblings().removeClass("selected");
	}

	
	/*** 我的地址*/
	function my_address(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/my_address.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#my_address").addClass("selected");
		$("#my_address").siblings().removeClass("selected");
	}
	
	/*** 基础资料*/
	function my_baseinfo(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/base_info.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#my_baseinfo").addClass("selected");
		$("#my_baseinfo").siblings().removeClass("selected");
	}
	/***用户参数*/
	function u_param(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/u_param.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#u_param").addClass("selected");
		$("#u_param").siblings().removeClass("selected");
	}
	/***修改密码*/
	function update_pwd(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/update_pwd.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#update_pwd").addClass("selected");
		$("#update_pwd").siblings().removeClass("selected");
	}
	/***站内信*/
	function web_iemial(){
		if("${session.user.fname}" !=""){
			 $("#treeIframe").attr("src",window.getRootPath()+"/usercenter/web_iemial.net");
		}else{
			parent.location.href=window.getRootPath()+"/login.jsp";
		}
		$("#web_iemial").addClass("selected");
		$("#web_iemial").siblings().removeClass("selected");
	}
</script>
</body>
</html>
<%@ include file="/pages/foot2.jsp"%>