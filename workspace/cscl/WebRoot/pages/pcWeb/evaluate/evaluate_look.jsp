<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>查看评价(货主评价司机)</title>
<!-- Bootstrap -->
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
<!--<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">-->
<link href="${ctx}/pages/pcWeb/css/evaluate_look.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="jumbotron main">
	<!--用户信息-->
	<div class="main_top row">
		<div class="col-xs-3 col-sm-2 col-md-1">
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/user_head.png"/>
		</div>
		<div class="col-xs-9 col-sm-5 col-md-4">
			<p>运单号:<span>${data.orderNum}</span></p>
			<p><span>${data.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>${data.carNum}</span></p>
			<div class="star-show">
<%-- 			<img src="${ctx}/pages/pcWeb/css/images/evaluate/star_gold.png"/>			 --%>
<!-- 			<span class="score">4.5</span> -->
			</div>
		</div>
	</div>
	
	<div class="main_bottom">
		<div class="valuate-list">		
<%-- 		<img src="${ctx}/pages/pcWeb/css/images/evaluate/evaluate_good.png"/> --%>
<!-- 		<span>司机态度好，沟通顺畅，运输快捷！</span> -->
<!-- 		<span class="evaluate_time">[2016-06-14]</span>		 -->
		</div>
		<div class="star">
			<p><span>服&nbsp;务&nbsp;态&nbsp;度： </span></p>
			<p><span>时&nbsp;效&nbsp;性：</span></p>
			<p><span>货物完整情况：</span></p>
		</div>
	</div>
	</div>
</body>
</html>
<script>
$(function(){
	/*获取星星的数量*/
	var j="${星星数量}";
	for(var i=0;i<j;i++){
		$(".star-show").append('<img src="${ctx}/pages/pcWeb/css/images/evaluate/star_gold.png"/>');
	}
	$(".star-show").append('<span class="score">${星星数量}</span>');
	
	
	//最后的三排星星	
	var star=["${data.service}","${data.timeliness}","${data.complete}"]//分别代表服务态度，时效性，货物完整情况的星星个数
	for(var i=0;i<star.length;i++){		
		for(var j=0;j<star[i];j++){
			$(".star p").eq(i).append('<img src="${ctx}/pages/pcWeb/css/images/evaluate/star_gold_lg.png"/>');
		}
	}
})
</script>
