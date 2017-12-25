<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>充值--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_recharge.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_recharge.js"></script>
</head>
<body>
	<div class="jumbotron wallet_recharge">
		<div class="title">充值金额</div> 				 	
		<div class="main">
	    	<form class="form-horizontal" id="recharge_form">
	    		<div class="form-group">
	    			<span for="" class="col-sm-2 text-right">充值账号：</span>
	   				<span class="col-sm-10">${data.vmiUserPhone}</span>
	  			</div>
	  			<div class="form-group myform">
	    			<label for="money" class="col-sm-2 text-right">充值金额：</label>
	   				<span class="col-sm-5 col-xs-11">
	     			<input type="number" class="form-control" id="money" name="money">
	     			<a id="money_err" data-placement="right" title="充值金额格式异常"></a>
	    			</span>
	    			<span class="col-sm-1 col-xs-1" style="margin-left: -15px;margin-top: 5px;">
	     			元
	    			</span>
	  			</div>
	  			<div class="form-group">
	    			<label class="col-sm-2 col-xs-0"></label>
	   				<span class="col-sm-10 col-xs-12" style="color: #999;">
	     			请注意：支持国内主流银行储存卡充值，最低充值金额为10块，在线支付成功后，充值金额会在1分钟内到账；如果需要提现，请致电一路好运客服办理。
	    			</span>
	    		
	  			</div>
	  			<div class="form-group">
	    			<label class="col-sm-2"></label>
	   				
					<span class=" tip col-sm-6 col-xs-12">
					<img src="${ctx}/pages/pcWeb/css/images/wallet/tip.png" />客服电话：0577-83591111       服务时间：周一至周日8：00-17：30
					</span>
				</div>
	    		
	  			<div class="form-group">
	    			<label class="col-sm-2"></label>
	   				<div class="col-sm-10" >
	     			<a href="javascript:void(0)" class="btn btn-danger btn-a btn-next" id="submit">下一步</a>
	    			</div>
	    		
	  			</div>  		
	  			
	    	
				
			<!--小提示-->	
				<div class="form-group">
	    			<label class="col-sm-2"></label>
	   				<div class="col-sm-8" >
		     			<div class="row message">
							<div class="col-xs12">
								温馨提示：	
							</div>	
							<div class="col-xs12">
							1. 充值成功后，余额可能存在延迟现象，一般会在1到5分钟内到账，如有问题，请咨询客服；	
							</div>
							<div class="col-xs12">
							2. 充值余额输入值必须是不小于10且不大于50000的正整数；
							</div>
							<div class="col-xs12">
							3. 您只能用储存卡进行充值，如遇到任何支付问题可以查看在线支付帮助；
							</div>
							<div class="col-xs12">
							4. 充值完成后，您可以进入钱包查看余额。
							</div>
						</div>
	    			</div>
	  			</div>  
			</form>
		</div>
	</div>
</body>
</html>
<script>
	$("#submit").click(function(){
		var money=$("#money").val();
		/*充值金额不能为空*/
		if(money==""||money==null/* ||money<10||!/^[1-9]\d+$/.test(money) */){
			$("#money").focus();
			$("#money_err").tooltip("show");
			setTimeout(function(){
				$("#money_err").tooltip("hide");
			},1000);
			return false;
		}
		else{
			location.href="${ctx}/pcWeb/pcWebPay/pcWebVoucher2.do?money="+money;	
		}
	})
</script>