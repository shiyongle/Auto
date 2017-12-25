<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>网银支付--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_epay.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_epay.js"></script>
</head>
<body>
	<div class="jumbotron wallet_epay">
		<div class="title"><span style="padding: 0 10px;">银行卡</span></div>
		<div class="main">
			<div>网银支付</div>
			<div>
			<ul>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li><img src="${ctx}/pages/pcWeb/css/images/wallet/gsyh_img.png"/></li>
				<li class="more">更多银行</li>
			</ul>
			</div>
			<div>
				<a href="#" class="btn btn-danger btn-a">跳转网银并支付</a>
			</div>
    	</div>

    	
	
	
	
		
	<!--删除银行卡-->
	<div class="del">
		<div class="del_in">
			<div class="del_tit row">
				<div class="col-xs-12">
				支付
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="colse"/>
				</div>
			</div>
			
			<div class="reson">
				<div class="text-center">请您在新打开的网银页面进行支付，支付完成请不要关闭该窗口</div>
			<div class="text-center row">
				<a href="#" class="btn btn-default col-xs-6 left">已完成充值</a>
				<a href="#" class="btn btn-default col-xs-6  right">支付遇到问题</a>
			</div>
			</div>
			
			
		</div>
	</div>
</div>
</body>
</html>