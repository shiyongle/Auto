<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册成功</title>
<script src="js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" href="css/reg.css" type="text/css"/>
</head>

<body>
	<div id="nav">
    	<div id="head">
    		<img src="css/images/logo.png"/>
        </div>
        <div id="container">
        	<span class="txt1" style="color:#444444;border-bottom:none;">账号设置</span>
            <span class="txt2" style="
            color:red;
		border-bottom:3px solid #F00;">注册成功</span>
        </div>
        <div id="foot">
        	<img src="css/images/dg.png" class="dg"/>
        	<p class="tlt1">恭喜您注册成功!<br /><br />
				<span>
            	  您可以去<a href="#">完善资料</a>
            	           或者 <a href="${ctx}/index.jsp" >返回首页</a>
            	</span>
          	</p>
        </div>
    </div>
</body>
</html>
