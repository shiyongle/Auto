<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>支付方式--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_payway.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/jquery.qrcode.min.js" type="text/javascript"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_payway.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bankcard.js"></script>
</head>
<body>
   	<input type="hidden" value="${fid}" id="fid"/>
   	<input type="hidden" id="stateFid">
   	<input type="hidden" id="verifyCheckCode">
	<div class="jumbotron wallet_payway">
		<div class="title">支付方式
		<span class="right" style="font-size: 14px;">充值<font class="redClolr">${money}</font>元</span>
		</div> 				 	
		<div class="main">
    	

    		<table class="table table-hover" id="bank">
<!--     			<tr> -->
<%--     				<td><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/jsyh_bank.png" />中国建设银行</td> --%>
<!--     				<td>**4534</td> -->
<!--     				<td>储蓄卡 | 网银</td> -->
<!--     			</tr> -->
<!--     			<tr> -->
<%--     				<td><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/jsyh_bank.png" />中国建设银行</td> --%>
<!--     				<td>**4534</td> -->
<!--     				<td>储蓄卡 | 网银</td> -->
<!--     			</tr> -->
<!--     			<tr> -->
<%--     				<td><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/jsyh_bank.png" />中国建设银行</td> --%>
<!--     				<td>**4534</td> -->
<!--     				<td>储蓄卡 | 网银</td> -->
<!--     			</tr> -->
<!--     			<tr> -->
<%--     				<td><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/jsyh_bank.png" />中国建设银行</td> --%>
<!--     				<td>**4534</td> -->
<!--     				<td>储蓄卡 | 网银</td> -->
<!--     			</tr> -->
<!--     			<tr> -->
<%--     				<td colspan="3" class="zfb_pay"><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/zfb.png" />支付宝</td>    	 --%>
<!--     			</tr> -->
<!--     			<tr> -->
<%--     				<td colspan="3" class="wx_pay"><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/wx.png" />微信</td> --%>
<!--     			</tr> -->
    		</table>
    		
    		<div class="row">
    			<div class="col-xs-12"><a href="${ctx}/pages/pcWeb/wallet/wallet_addcard.jsp" class="redClolr">添加快捷/网银付款</a></div>
    		</div>
    		
    		<div class="row">
    			<div class="col-xs-12 redClolr">
    				<a href="javascript:void(0)" class="btn btn-danger btn-a" id="payway_submit">确认</a>
    			</div>
    		</div>
		</div>
	</div>
	
	
	
		<!--微信扫一扫-->
	<div class="wx">
		<div class="wx_in">
			<div class="del_tit row">
				<div class="col-xs-12">
			
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="close"/>
				</div>
			</div>
			
			<div class="reson">
				<div class="text-center">
					请使用<span class="redClolr">微信</span><img src="${ctx}/pages/pcWeb/css/images/wallet/wx_sys.png" class="wx_img" />扫一扫
				</div>
				<div class="text-center">
					安全支付
				</div>
				<div class="text-center order-weixin">
<%-- 					<img src="${ctx}/pages/pcWeb/css/images/wallet/ewm.png" /> --%>
				</div>
				<div class="text-center" style="color: #999;">
					二维码有效时长为2小时，请尽快支付
				</div>
				<div class="text-center" id="wx_info"style="color: #999;">
					<!-- <a class="btn btn-danger btn-a" id="wx_ok">确认支付</a> -->
				</div>
				
			</div>
		</div>
	</div>
	
		<!--支付宝扫一扫-->
	<div class="zfb">
		<div class="zfb_in">
			<div class="del_tit row">
				<div class="col-xs-12">
			
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="close2"/>
				</div>
			</div>
			
			<div class="reson">
				<div class="text-center">
					请使用<span class="redClolr">支付宝</span><img src="${ctx}/pages/pcWeb/css/images/wallet/wx_sys.png" class="wx_img" />扫一扫
				</div>
				<div class="text-center">
					安全支付
				</div>
				<div class="text-center order-zfb">
<%-- 					<img src="${ctx}/pages/pcWeb/css/images/wallet/ewm.png" /> --%>
				</div>
				<div class="text-center" style="color: #999;">
					二维码有效时长为2小时，请尽快支付
				</div>
				<div class="text-center" id="zfb_info" style="color: #999;">
					<!-- <a class="btn btn-danger btn-a" id="zfb_ok">确认支付</a> -->
				</div>
				
			</div>
		</div>
	</div>
	
		<!--手机验证-->
	<div class="tel_yz">
		<div class="tel_yz_in">
			<div class="del_tit row">
				<div class="col-xs-12">
				手机验证
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="colse_yzm"/>
				</div>
			</div>
			
			<div class="reson">
				<div>
					<img src="${ctx}/pages/pcWeb/css/images/wallet/tip.png" style="vertical-align: sub;"/>
					我们向您填写的手机号<span id="Myphone"></span>发送了一条验证码，请收后填写，请勿泄露。
				</div>
				
				<div class="row">
					<div class="col-xs-12">
						
						<div class="left myForm">
						验证码：<input type="text" id="yzm" class="yzm" />
						<a id="yzm_err" data-placement="top" title="验证码不能为空"></a>
						<a id="yzm_err2" data-placement="top" title="验证码输入错误"></a>
						</div>
						<div class="left" style=" line-height: 35px;">没收到短信，<a href="javascript:void(0)" id="yzm_tishi" class="redClolr">发送验证码</a></div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12">
						<a href="#" class="btn btn-danger btn-a btn-confirm" id="yzm-ok" onclick="formValidate2()">确定</a>
					</div>
				</div>
			</div>
		</div>
	</div>		
	<div class="masker"></div>
</body>
</html>
<script>
	var bankFid="";
$(window).load(function(){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
// 	var bankFid="";
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
})
$(function(){
	/*已绑定银行卡列表*/
$.ajax({
	type:"post",
	url:"${ctx}/pcWeb/bank/findByBankList.do",
	dataType:"json",
	success:function(response){
		if(response.success=="true"){
			var html="";
			$("#bank").empty();
			$.each(response.data, function(i,e){
				html+='<tr class="bankpay">'+
	// 			      '<input type="hidden" value="e.fid" id="bankfid"/>'+
					  '<td class="bankfid" data-fid="'+e.fid+'"><input type="radio" name="pay_style"/><img style="width:20px; vertical-align:top;" src="${ctx}/bankIco/'+e.ficon+'" />'+e.fbankName+'</td>'+
					  '<td>****'+e.fcardNumber+'</td>'+
					  '<td>'+e.fbankName+'</td>'+
					  '</tr>';
			})
			html+='<tr>'+
				'<td colspan="3" class="zfb_pay" id="alipay"><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/zfb.png" />支付宝</td>'+    	
				'</tr>'+
				'<tr>'+
				'<td colspan="3" class="wx_pay" id="wxpay"><input type="radio" name="pay_style"/><img src="${ctx}/pages/pcWeb/css/images/wallet/wx.png" />微信</td>'+
				'</tr>';
			$("#bank").append(html);
			getHtmlLoadingAfterHeight();
		}
	}
});
	
/*选择支付方式的效果*/
$(document).on("click","#bank tr",function(){
	$(this).addClass('table_border_red').siblings("tr").removeClass("table_border_red");
	$(this).find("input[type=radio]").prop("checked",true).parents("tr").siblings().find("input[type=radio]").prop("checked",false);
	bankFid=$(this).find('.bankfid').data("fid");
// 	alert(bankFid);
})
/*微信*/
 	var wx_fid;
 	var wx_timer =null;
	function wxpay(){
		clearInterval(wx_timer);
		$(".masker,.wx").show();
		var loading_wx = layer.load();
		$(".order-weixin").empty();
		/*微信二维码*/
		var imgSrc="${ctx}/pages/pcWeb/css/images/payment/wx.png";
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/pcWebPay/pcWebVoucher3.do?money=${money}&type=2",
			dataType:'json',
	// 		async:false,
			success:function(response){
				layer.close(loading_wx);
				wx_fid=response.fid;
				if(response.success=="true"){
					$('.order-weixin').qrcode({
						text: response.data,
						src: imgSrc,
						width: 140,
						height: 140
					});
					var wxNumber = response.data1;
					wxlunxun(wxNumber)();
					wx_timer =setInterval(wxlunxun(wxNumber),1000);
				}else if(response.success="false"){
					$(".wx,.masker").hide();
					layer.msg(response.msg);
					layer.close(loading_wx);					
				}			
			}
		});
	}
	//微信轮寻
	function wxlunxun(wxNumber){
		return function(){
			$.ajax({
				type:'post',
				url:'${ctx}/pcWeb/pcWebPay/pcWebPayPolling.do?number='+wxNumber,
				dataType:'json',
				success:function(response){
					if(response.success=="true"){
						clearInterval(wx_timer)
						location.href="${ctx}/pages/pcWeb/wallet/wallet_addsuccess.jsp";//充值成功
					}else if(response.success=="false1"){
						clearInterval(wx_timer);
						/* $("#wx_info").text('支付失败'); */
						layer.msg('支付失败');
					}else{

					}
				}
			})
		}
	}
	/*支付宝*/
	var zfb_fid;
	var zfb_timer =null;
	function alipay(){
		clearInterval(zfb_timer);
		$(".masker,.zfb").show();
		var loading_zfb = layer.load();
		$(".order-zfb").empty();
		/*支付宝二维码*/
		var imgSrc="${ctx}/pages/pcWeb/css/images/payment/zfb.png";
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/pcWebPay/pcWebVoucher3.do?money=${money}&type=1",
			dataType:'json',
			success:function(response){			
				if(response.success=="true"){
					layer.close(loading_zfb);
					zfb_fid=response.fid;
					$('.order-zfb').qrcode({
						text: response.data,
						src: imgSrc,
						width: 140,
						height: 140
					});
					var zfbNumber = response.data1; //明细编码
					zfblunxun(zfbNumber)();
					zfb_timer = setInterval(zfblunxun(zfbNumber),1000);
				}else if(response.success="false"){
					$(".zfb,.masker").hide();
					layer.msg(response.msg);
					layer.close(loading_zfb);					
				}
			}
		});
	}
	//支付宝轮寻
	function zfblunxun(zfbNumber){
		return function(){
			$.ajax({
				type:'post',
				dataType:'json',
				url:'${ctx}/pcWeb/pcWebPay/pcWebPayPolling.do?number='+zfbNumber,
				success:function(response){
					if(response.success=="true"){
						clearInterval(zfb_timer);
						location.href="${ctx}/pages/pcWeb/wallet/wallet_addsuccess.jsp";//充值成功
					}else if(response.success=="false1"){
						clearInterval(zfb_timer);
						/* $("#zfb_info").text('支付失败'); */
						layer.msg('支付失败');
					}else{

					}
				}
			});
		}
	}	
	var change =null,
		change2=null;
	function sendCodeing(){
		var s =60;
		change=setInterval(function(){
			$("#yzm_tishi").text(s+"s后重新发送！");
			s--;
			if(s<0){
				clearInterval(change);
				$("#yzm_tishi").text("点击重发！");
				$("#yzm_tishi").attr('onclick','sendCode()');
			}
		},1000);
	}
	//获取验证码
	function sendCode(){
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/pcWebPay/BangDingPay.do?fid="+bankFid+"&fmoney=${money}",
			dataType:"json",
			success:function(response){

			}
		});
		$("#yzm_tishi").removeAttr('onclick','sendCode()');
		var s = 60;
		change2=setInterval(function(){
			$("#yzm_tishi").text(s+"s后重新发送！");
			s--;
			if(s<0){
				clearInterval(change2);
				$("#yzm_tishi").text("点击重发！");
				$("#yzm_tishi").attr('onclick','sendCode()');
			}
		},1000);
	}	
	$("#close").click(function(){
		$(".wx,.masker").hide();
		clearInterval(wx_timer);
	})
	$("#close2").click(function(){
		$(".zfb,.masker").hide();
		clearInterval(zfb_timer);
	})
		
	$("#colse_yzm").click(function(){
		$(".tel_yz,.masker").hide();
		$("#yzm_tishi").text("");
		clearInterval(change2);
		clearInterval(change);
	})
	//确定
	$("#payway_submit").click(function(){
		var a= $("input[type=radio]:checked").length;
		if(a!=0){	
			if($('#alipay input[type="radio"]').is(':checked')){
				alipay();
				return false;
			}
			if($('#wxpay input[type="radio"]').is(':checked')){
				wxpay();
				return false;
			}
			if($('.bankpay input[type=radio]:checked').length){	
				$.ajax({
					type:"post",
					url:"${ctx}/pcWeb/pcWebPay/BangDingPay.do?fid="+bankFid+"&fmoney=${money}",
					dataType:"json",
					success:function(response){
						var data = response.data;
						$("#verifyCheckCode").val(data);
						$('#stateFid').val(response.data1);
						if(response.success=="true"){
							$(".masker,.tel_yz").show();
							sendCodeing();
							return false;
						}else{
							layer.msg(response.msg);
						}
					}
				});
			}	
		}else{
			layer.msg("请选择一种支付方式！");
			return false;
		}		
	})
})


/*验证码验证*/
function formValidate2(){
	var yzm=$("#yzm").val();
	/*验证码不为空*/
	if(yzm==""||yzm==null){
		$("#yzm").focus();
		$("#yzm_err").tooltip("show");
		setTimeout(function(){
			$("#yzm_err").tooltip("hide");
		},1000);
		return false;
	}
	if(yzm.length!=6){
		$("#yzm_err2").tooltip('show');
		setTimeout(function(){
			$("#yzm_err2").tooltip("hide");
		},1000);
		return false;
	}
	$.ajax({
		type:"POST",
		url:"${ctx}/pcWeb/bank/VerificationCodeGo.do",
		data:{
			/*"ftel":mobile,*/
			"VerificationCode":yzm,
			/*"BankInfoFID": $("#data").val(),*/
			"fid":bankFid,
			"verifyCheckCode":$("#verifyCheckCode").val(),
			"stateFid":$('#stateFid').val()
		},
		dataType:'json',
		success:function(response){
			//console.log(response);
			if(response.success=="false"){
				$("#yzm_err2").tooltip('show');
				setTimeout(function(){
					$("#yzm_err2").tooltip("hide");
				},1000);
				return false;
			}else{

			 location.href="${ctx}/pages/pcWeb/wallet/wallet_addsuccess.jsp";//充值成功

			}
		}
	}); //验证的ajax结束
}	

</script>