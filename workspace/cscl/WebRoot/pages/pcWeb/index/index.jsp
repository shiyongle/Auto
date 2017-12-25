<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>首页</title>

    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/index.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script> 
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/index.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
<!-- 轮播 -->
<div id="myCarousel" class="carousel slide">
	<!-- 轮播（Carousel）指标 -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol> 
	<!-- 轮播（Carousel）项目 -->
	<div class="carousel-inner">
		<div class="item active">
			<img src="${ctx}/pages/pcWeb/css/images/index/banner1.png" alt="First slide">
		</div>
		<div class="item">
			<img src="${ctx}/pages/pcWeb/css/images/index/banner1.png" alt="Second slide">
		</div>
		<div class="item">
			<img src="${ctx}/pages/pcWeb/css/images/index/banner1.png" alt="Third slide">
		</div>
	</div>
	<!-- 轮播图固定图片 -->
	<div class="img-title">
		<img class="img-responsive" src="${ctx}/pages/pcWeb/css/images/index/down.png"/>
		<ul class="img_down">
			<li>
				<img src="${ctx}/pages/pcWeb/css/images/index/And_down.png">
			</li>
			<li>
				<img src="${ctx}/pages/pcWeb/css/images/index/iphone_down.png">
			</li>
		</ul>
		<div class="img-erwei">
			<img src="${ctx}/pages/pcWeb/css/images/index/er_wei.png">
		</div>
	</div>
</div> 
<!-- 4保障 -->
<div class="container">
	<div class="row icon-title">
		<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">
			<img class="click_tiaozhuan" src="${ctx}/pages/pcWeb/css/images/index/index_icon1.png"/>
		</div>
		<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">
			<img class="click_tiaozhuan" src="${ctx}/pages/pcWeb/css/images/index/index_icon2.png"/>
		</div>
		<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">
			<img class="click_tiaozhuan" src="${ctx}/pages/pcWeb/css/images/index/index_icon3.png"/>
		</div>
		<div class="col-lg-3 col-md-3 col-sm-4 col-xs-6">
			<img class="click_tiaozhuan" src="${ctx}/pages/pcWeb/css/images/index/index_icon4.png"/>
		</div>
	</div>
</div>
<!-- 三个pic -->
<div class="container container-bg click_tiaozhuan">
	<div class="container">
		<div class="row ">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pic">
				<img src="${ctx}/pages/pcWeb/css/images/index/pic_1.png"/>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 oh">
				<h2 class="pic-title">靠谱司机入驻</h2>
				<p class="pic-text">司机均通过专业培训认证，为您提供优质货运服务</p>
			</div>
		</div>
	</div>
</div>
<div class="container-fluid click_tiaozhuan">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 oh">
				<h2 class="pic-title">运单实时跟踪</h2>
				<p class="pic-text">专业客户团队进行运单跟踪，您可实时查看运单状态</p>
			</div>
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pic">
				<img src="${ctx}/pages/pcWeb/css/images/index/pic_2.png"/>
			</div>	
		</div>
	</div>
</div>
<div class="container container-bg click_tiaozhuan">
	<div class="container">
		<div class="row">
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 pic">
				<img src="${ctx}/pages/pcWeb/css/images/index/pic_3.png"/>
			</div>	
			<div class="col-lg-6 col-md-6 col-sm-6 col-xs-12 oh">
				<h2 class="pic-title">安全无忧运输</h2>
				<p class="pic-text">保险服务，为您的货物安全保驾护航</p>
			</div>
		</div>
	</div>
</div>
<!-- 使用流程 -->
<div class="container">
	<div class="row">
		<div class="col-lg-12 pic-text">
			<h3>使用流程</h3>
		</div>
		<div class="col-lg-11 col-lg-offset-1 pic-img">
			<img class="img-responsive" src="${ctx}/pages/pcWeb/css/images/index/car.png">
		</div>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>
</body>
</html>