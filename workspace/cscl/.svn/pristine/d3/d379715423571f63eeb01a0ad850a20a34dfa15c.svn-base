<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>支付</title>
<!-- Bootstrap -->
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
<!--<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">-->
<link href="${ctx}/pages/pcWeb/css/payment2.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
<script src="${ctx}/pages/pcWeb/js/payment2.js"></script>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<div class="container main">
	<div class="main_top">
		<!--订单内容详情-->
		<div class="row">
		<div class="col-xs-12 col-sm-2 col-md-1">
			<img src="${ctx}/pages/pcWeb/css/images/payment/erweima.png"/>
		</div>
		<div class="col-xs-12 col-sm-10 col-md-11">
		<ul>
			<li>运单号：<span>201512210001</span></li>
			<li>用车时间：<span>2015-12-22 14:20</span></li>
			<li>提货点：<span>应晓雷，15845652563，温州市瓯海区东经一路1号</span></li>
			<li>卸货点：<span>杨秋芳，18825658954，温州市龙湾区经济开发区40号</span></li>
		</ul>
		</div>
		</div>
		<!--应付金额-->
		<div class="amount_payable">应付金额：<span>&yen;200</span></div>
		<!--订单详情标签-->
		<div class="order_details">订单详情</div>
	</div>
	
	<div class="main_bottom">
		<!--支付方式-->
		<div class="radio">
			<label>
				<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked><img src="${ctx}/pages/pcWeb/css/images/payment/balance.png"/> 账户余额 <span>500</span><span class="amount_payable2">支付<span>200</span>元</span>
			</label>
		</div>
		<!--其他支付方式-->
		<a href="">其他付款方式</a>
		<a href="">添加快捷/网银支付</a>
		<div>
			<button type="button" class="btn btn-danger btn-lg">确认付款</button>	
		</div>
	</div>
	
	<!--输入银行卡-->
	<div class="import">
		<div class="import_in">
			<div class="reson">
				<form class="form-inline">
					<p>合作银行/机构</p>
					<div class="input-group">
						<span class="input-group-btn">
						<button class="btn btn-default cardNumber_text" type="button">
							卡号：
						</button>
						</span>
						<input type="text" class="form-control" id="card_number" placeholder="输入银行卡号进行安全智能识别">
						<a id="cardNumber_err" data-placement="right" title="卡号不能为空"></a>
					</div>
					<a href="">按卡种选择</a>
				</form>
				<div><a href="#" class="btn btn-danger btn-a btn-next" id="next_step">下一步</a></div>
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>
</html>