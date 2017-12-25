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
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
<link href="${ctx}/pages/pcWeb/css/payment.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
<script src="${ctx}/pages/pcWeb/js/jquery.qrcode.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pcWeb/js/payment.js"></script>
<script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bankcard.js"></script>
<script src="${ctx}/pages/pc/js/MD5.js" type="text/javascript" language="javascript"></script>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<input type="hidden" id="stateFid">
   	<input type="hidden" id="verifyCheckCode">
    <div class="jumbotron payment-main">
	    <div class="payment-main-in">
	    <!--订单内容详情-->
	    	<div class="main_top row">
		        <div class="col-xs-12 col-sm-12 col-md-12" id="payment-detial">
			        <ul>
			            <li>运单号：<span>${data.number}</span></li>
			            <li>用车时间：<span>${data.createTimeString }</span></li>
			            <li>提货点：<span>${data.takelinkman}, ${data.takephone},${data.takeAddress} </span></li>
			            <li>卸货点：<span>${data.reclinkman},${data.recphone},${data.recAddress}</span></li>
			            <li>卸货点数：<span>${data.fopint}</span></li>
			            <li>里程：<span>${data.mileage}</span></li>
			            <li>货物类型：<span>${data.goodsTypeName}</span></li>
			            <li>所需车型：<span>${data.carTypeName}</span></li>
			            <li>其他：<span>${data.carSpecName}</span></li>
			            <li>注意事项：<span>${data.fremark}</span></li>
			            <li>增值服务：<span>${data.fincrementServe}</span></li>
			            <li>代收货款：<span>${data.purposeAmount}</span></li>
			        </ul>
		        </div>
		        <!--应付金额-->
		        <div class="amount_payable">应付金额：<span>&yen;${data.freight}</span></div>
		        <!--订单详情标签-->
		        <div class="order_details">订单详情</div>
		    </div>
		
		    <div class="main_bottom" id="bank_card">
		  		
		    </div>
			<div class="add_card">
				<a href="${ctx}/pages/pcWeb/wallet/wallet_addcard.jsp">添加快捷/网银付款</a>
			</div>
			<div id="password_box" class="password_box">
				<div class="safe"><img src="${ctx}/pages/pcWeb/css/images/payment/safety.png">你在安全环境中,请放心使用</div>
				<div>账户余额支付密码<span style="color:#ccc; font-size:12px;">(默认为登录密码)</span></div>
				<div class="password form-inline">
					<input type="password" class="password_txt form-control " >&nbsp;<a href="#">忘记密码？</a>
					<a id="passwordErr" data-placement="right" title="密码错误"></a>
					<a id="passwordErr2" data-placement="right" title="密码不为空"></a>
				</div>
			</div>
		 	<div> 
		       <button type="button" class="btn btn-danger btn-lg" id="payOk">确认付款</button>  
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
                </div>
                <div class="text-center" style="color: #999;">
                    	二维码有效时长为2小时，请尽快支付
                </div>
                <div class="text-center" style="color: #999;" id="wx_info">
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
                </div>
                <div class="text-center" style="color: #999;">
                    二维码有效时长为2小时，请尽快支付
                </div>
                <div class="text-center" style="color: #999;" id="zfb_info">
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
                        <div class="left" style="line-height: 35px;">没收到短信，<a href="javascript:void(0)" id="yzm_tishi" class="redClolr">发送验证码</a></div>
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
    <!-- 余额不足 -->
    <div class="yebz">
        <div class="yebz_in">
            <div class="del_tit row">
                <div class="col-xs-12"> 
                	 余额不足     
                    <img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="close_yebz"/>
                </div>
            </div>          
            <div class="clearfix" style="padding:50px 40px;">
                <div class="left">
                    <img class="img-responsive center-block" src="${ctx}/pages/pcWeb/css/images/payment/Dongdong.png">
                </div>
                <div class="right" style="width:280px; padding-top:30px;font-size:18px;	">
                    <!-- 还差多少钱 -->
                  	  哦哟~亲亲你的余额不够呢，还差<span id="D_value" style="color:#E5494E; font-size:22px;"></span>元呢，选择組合方式支付吧~
                </div>              
            </div>
            <div class="text-center" style="color: #999;">
                <a class="btn btn-danger btn-a" id="mixpay" >组合支付</a>
            </div>
        </div>
    </div>
    <div class="masker"></div>
<script>
var pay_fid="";
$(window).load(function(){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
})
$(function(){
	/*隐藏空数据*/
	$("#payment-detial li span").each(function(i,e){
		var text=$(e).text();
		if(text==""||text==null){
			$(e).parent().remove();
		}
	});		 	
//弹窗关闭按钮	
$("#close").click(function(){
	$(".wx,.masker").hide();
	clearInterval(wx_timer);
});
$("#close2").click(function(){
	$(".zfb,.masker").hide();
	clearInterval(zfb_timer);
});	
$("#close_yebz").click(function(){
	$(".yebz,.masker").hide();
});

//混合支付
$('#mixpay').click(function(){
	location.href="${ctx}/pcWeb/pcWebPay/mixPay.do?orderId=${data.id}";//跳轉到混合支付
});
//得到银行卡信息
	$.ajax({
        type:"post",
        url:"${ctx}/pcWeb/bank/findByBankList.do",
        dataType:"json",
  		success:function(response){ 			
    		if(response.success=="true"){    			
    			$("#bank_card").empty();
        		var html='<div class="radio yezf yezf_checked" id="yezf">'+
        				'<label>'+
    					'<input type="radio" name="optionsRadios"   value="option1"><img src="${ctx}/pages/pcWeb/css/images/payment/balance.png"/> 账户余额：￥${balance} <span class="amount_payable2">支付<span>${data.freight}</span>元</span>'+
    					'</label>'+
    					'</div>'+
    					'<div class="radio" id="alipay">'+
    					'<label>'+
    					'<input type="radio" name="optionsRadios" value="option1"><img src="${ctx}/pages/pcWeb/css/images/payment/Alipay.png"/> 支付宝支付'+
    					'</label>'+
    					'</div>'+
    					'<div class="radio" id="weixin">'+
    					'<label>'+
    					'<input type="radio" name="optionsRadios"   value="option1"><img src="${ctx}/pages/pcWeb/css/images/payment/weixin.png"/> 微信支付'+
    					'</label>'+
    					'</div>';	
    			$.each(response.data, function(i,e){    				
					html+='<div class="radio radio-input">'+
							'<label>'+
							'<input type="hidden" value="'+e.fid+'" class="bankFid">'+
							'<input type="radio"  name="optionsRadios" value="option1"><img style="width:20px;" src="${ctx}/bankIco/'+e.ficon+'" />'+e.fbankName.substring(0,(e.fbankName.indexOf('行')+1))+'<span class="card_number">**'+e.fcardNumber+'</span> <span class="card_type">储蓄卡</span>'+
							'</label>'+
							'</div>';     					  
    			});
    			 //多个卸货点 运费到付隐藏
    		    if("${data.fopint}"=='1'){
    		    	html+='<div class="radio yfdf" id="yfdf">'+
					'<label>'+
					'<input type="radio" name="optionsRadios"   value="option1"><img src="${ctx}/pages/pcWeb/css/images/payment/freight_collect.png"/> 运费到付'+
					'</label>'+
					'</div>'
    		    }
    			
    			$("#bank_card").append(html);
    			getHtmlLoadingAfterHeight();
    		}   		      
  		}
    });
   
    
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
			url:"${ctx}/pcWeb/pcWebPay/BangDingPay.do",
			data:{
				fid:$('.radio-input input[type=radio]:checked').siblings('.bankFid').val(),
				orderFid:"${data.id}"
			},
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
	
	$("#colse_yzm").click(function(){
		$(".tel_yz,.masker").hide();
		$("#yzm_tishi").text("");
		clearInterval(change2);
		clearInterval(change);
	});


	/*取到fid*/
	/* var bankFid;
	$(document).on("click","#bank_card .radio-input",function(){
		 bankFid=$(this).find('.bankfid').data("fid");
	}); */
	
	/*微信*/
 	var wx_timer =null;
	function wxpay(){
		mask();
		clearInterval(wx_timer);
		$(".masker,.wx").show();
		var loading_wx = layer.load();
		$(".order-weixin").empty();
		/*微信二维码*/
		var imgSrc="${ctx}/pages/pcWeb/css/images/payment/wx.png";
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/pcWebPay/pcWebVoucher3.do?money=${money}&type=2&orderId=${data.id}",
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
						location.href="${ctx}/pages/pcWeb/payment/payment_success.jsp";//跳转到支付成功
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
	var zfb_timer =null;
	function alipay(){
		mask();
		clearInterval(zfb_timer);
		$(".masker,.zfb").show();
		var loading_wx = layer.load();
		$(".order-zfb").empty();
		/*支付宝二维码*/
		var imgSrc="${ctx}/pages/pcWeb/css/images/payment/zfb.png";
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/pcWebPay/pcWebVoucher3.do?money=${money}&type=1&orderId=${data.id}",
			dataType:'json',
			success:function(response){			
				if(response.success=="true"){
					layer.close(loading_wx);
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
					layer.close(loading_wx);					
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
						location.href="${ctx}/pages/pcWeb/payment/payment_success.jsp";//跳转到支付成功
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
	
	/*确认付款*/
	$(document).on("click","#payOk",function(){	
		if($("input[type=radio]:checked").length){
			if($('#weixin input[type="radio"]').is(':checked')){
				wxpay();
				return;
			}
			if($('#alipay input[type="radio"]').is(':checked')){
				alipay();
				return;
			}
			//余额支付
			if($('#yezf input[type="radio"]').is(':checked')){
				if($('.password_txt').val() == ''){
					$('#passwordErr2').tooltip('show');
					$('.password_txt').focus();
					setTimeout(function(){
						$('#passwordErr2').tooltip('hide');
					},1000);
					return false;
				}
				//提交
				$.ajax({
					type:'post',
					url:'${ctx}/pcWeb/pcWebPay/balancePay.do',
					data:{
						payPWD:MD5($('.password_txt').val()),
						fid:"${data.id}"
					},
					dataType:'json',
					success:function(response){
						console.log(response);
						if(response.success == 'true'){
							/* 余额支付成功并且跳转 */
							location.href="${ctx}/pages/pcWeb/payment/payment_success.jsp";//跳转到支付成功
						}else if(response.success == 'false1'){
							// 密码错误
							$('#passwordErr').tooltip('show');
							$('.password_txt').focus();
							setTimeout(function(){
								$('#passwordErr').tooltip('hide');
							},1000);
							return false;
						}else if(response.success == 'false2'){
							//支付失败
							layer.msg(response.msg);
						}else if(response.success == 'false3'){
							//余额不足合并支付
							$('#D_value').text(response.data2);
							$(".masker,.yebz").show();
							return false;
						}
					}
				})
				return;
			}
			//运费到付
			if($('#yfdf input[type="radio"]').is(':checked')){
				$.ajax({
						type:"POST",
						url:"${ctx}/pcWeb/pcWebPay/PayOrderInDao.do",
						data:{"fid":"${data.id}"},
						dataType:'json',
						async:"false",
						success:function(response){		
							if(response.success=="false"){
								layer.msg("运费到付失败！");
								return false;
							}else{
								location.href="${ctx}/pages/pcWeb/payment/payment_success.jsp";//跳转到支付成功
							}
						}
					});
					return false;
			}
			//银行卡支付
			if($(".radio-input").find("input[type=radio]:checked").length){
				$.ajax({
					type:"post",
					url:"${ctx}/pcWeb/pcWebPay/BangDingPay.do",
					data:{
						fid:$('.radio-input input[type=radio]:checked').siblings('.bankFid').val(),
						orderFid:"${data.id}"
					},
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
});

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
			"VerificationCode":yzm,
			"fid":$('.radio-input input[type=radio]:checked').siblings('.bankFid').val(),
			"verifyCheckCode":$("#verifyCheckCode").val(),
			"stateFid":$('#stateFid').val()
		},
		dataType:'json',
		success:function(response){
			if(response.success=="false"){
				$("#yzm_err2").tooltip('show');
				setTimeout(function(){
					$("#yzm_err2").tooltip("hide");
				},1000);
				return false;
			}else{

				location.href="${ctx}/pages/pcWeb/payment/payment_success.jsp";

			}
		}
	}); //验证的ajax结束
}	

function mask(){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});
}
</script>
</body>
</html>