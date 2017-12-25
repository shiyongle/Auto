<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>安全无忧</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/index_introduce.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script> 
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<div class="container main">
		<div class="row">
			<div class="col-md-1 col-md-offset-2 col-sm-2 col-sm-offset-1 col-xs-4">
				<img src="${ctx}/pages/pcWeb/css/images/index/insuredTraffic.png"/>
			</div>
			<div class="col-md-5 col-md-offset-1 col-sm-7 col-sm-offset-1 col-xs-8">
				<h3>安全无忧运输</h3>
				<p>严格的司机认证考核，专业服务培训</p>
				<p>从源头保障服务质量，极致服务体验</p>
			</div>
		</div>
	</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>
</body>
</html>