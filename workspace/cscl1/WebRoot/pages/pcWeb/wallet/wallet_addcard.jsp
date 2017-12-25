<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>添加银行卡--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_addcard.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_addcard.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bankcard.js"></script>
    <style>
		input::-webkit-outer-spin-button,
		input::-webkit-inner-spin-button{
		    -webkit-appearance: none !important;
		    margin: 0; 
		}

		input[type="number"]{-moz-appearance:textfield;}
	</style>
</head>
<body>
	<input type="hidden"  id="fid" name="fid"/>
	<!-- <input type="hidden"  id="data" name="BankInfoFID"/> -->
	<input type="hidden" id="fbankName" name="fbankName">
	<input type="hidden" id="fbankCardType" name="fbankCardType">
	<input type="hidden" id="fbankCardID" name="fbankCardID ">
	<input type="hidden" id="fcardpaytype" name="fcardpaytype">
	<div class="jumbotron main">
		<div class="row">
			<div class="col-md-10 col-md-offset-1">添加银行卡</div>
		</div>		
		<div class="row">
			<div class="col-md-10 col-md-offset-1 tip col-xs-12">
				<img src="${ctx}/pages/pcWeb/css/images/wallet/tip.png" />请填写以下信息，用于开通快捷支付等功能，绑卡需要扣取金额0.01元。
			</div>
		</div>
		
		<form class="form-horizontal" name="bankModel" id="apply_form">			
	
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="name" class="col-sm-2 control-label">真实姓名：</label>
   	 				 <div class="col-sm-8">   	 				 	
	    				<input type="text" class="form-control" name="fname" id="name">    				
	    				<a id="name_err" data-placement="right" title="姓名不能为空"></a>
   	 				 </div>
 				 </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				<label for="sfz" class="col-sm-2 control-label">身份证号：</label>
   	 				<div class="col-sm-8 myForm">   	 				 	
	    				<input type="text" class="form-control" name="fcard" id="sfz">
	    				<a id="sfz_err" data-placement="right" title="身份证号不能为空"></a>
	    				<a id="sfz_err2" data-placement="top" title="身份证号格式错误"></a>
   	 				</div>
 				 </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="bank" class="col-sm-2 control-label">银行卡号：</label>
   	 				 <div class="col-sm-8 myForm">   	 				 	
	    				<input type="number" class="form-control" name="fcardNumber" id="bank">
	    				<input type="hidden"  name="BankName" id="bank_owner_name">
	    				<a id="bank_err" data-placement="right" title="银行卡号不能为空"></a>
	    				<a id="bank_err2" data-placement="top" title="银行卡号应为16-19位"></a>
	    				<span style="color: #999;" id="bankname">输入卡号后会智能识别银行和卡种</span>
   	 				 </div>
 				 </div>
			</div>
		</div>
				
		<div class="row cvnBox" style="display:none;">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="bank" class="col-sm-2 control-label">CVN(安全码)：</label>
   	 				 <div class="col-sm-8 myForm">   	 				 	
	    				 <input type="number" class="form-control" name="fcreditCardCvn" id="CVN">    
	    				 <a id="CVN_err" data-placement="right" title="CVN不能为空"></a>	
	    				 <a id="CVN_lengtherr" data-placement="right" title="CVN只能三位数"></a>	
	    				 <span style="color: #999;">若银行卡为信用卡，此项为必填<!-- ，  年月必填 -->！</span>			
   	 				 </div>
 				 </div>
			</div>
		</div>
		<div class="row bankTimeBox" style="display:none;">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="bank" class="col-sm-2 control-label">逾期时间：</label>
   	 				 <div class="col-sm-8 myForm">   	 				 	    				
    				<input type="text"  class="form-control" name="BankTime" id="BankTime" onclick="WdatePicker({dateFmt:'MM/yy'})"/>
    				<span style="color: #999;">若银行卡为信用卡，此项为必填<!-- ， 年月必填 -->！</span>
    				<a id="BankTime_err" data-placement="right" title="逾期时间不能为空"></a>    				    				
   	 				 </div>
 				 </div>
			</div>
		</div>
		<!-- <div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="bank" class="col-sm-2 control-label">银行卡月份：</label>
   	 				 <div class="col-sm-8 myForm">   	 				 	
    				 <input type="text" class="form-control" name="BankM" id="BankTime" onclick="WdatePicker({dateFmt:'MM'})"/>
    				 <a id="BankM_err" data-placement="right" title="银行卡月份不能为空"></a>    				
   	 				 </div>
 				 </div>
			</div>
		</div> -->
		
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="mobile" class="col-sm-2 control-label">手机号码：</label>
   	 				 <div class="col-sm-8 myForm">   	 				 	
    				<input type="number" class="form-control" name="ftel" id="mobile">
    				<a id="mobile_err" data-placement="right" title="手机号码不能为空"></a>
    				<a id="mobile_err2" data-placement="right" title="手机号码格式错误"></a>
    				<span style="color: #999;">请填写您在银行预留的手机号码，以验证银行卡是否属于您本人</span>
   	 				 </div>
 				 </div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="" class="col-sm-2 control-label"></label>
   	 				 <div class="col-sm-8 myForm">   	 				 	
    				<input type="checkbox" style="width: 15px;height: 15px;vertical-align:sub" id="agreement">
    				<a href="javascript:window.open('${ctx}/pages/pcWeb/wallet/wallet_agreement.jsp','_blank','width='+screen.width+',height='+screen.height
+',top=0,left=0,toolbar=no,menubar=no,scrollbars=yes,resizable=no,location=no,status=o');">同意《用户协议》</a>
    				<a id="agreement_err" data-placement="right" title="请阅读并勾选用户协议"></a>
   	 				 </div>
 				 </div>
			</div>
		</div><div class="row">
			<div class="col-md-6 col-md-offset-3 col-sm-12">
				<div class="form-group">   	 				
   	 				 <label for="" class="col-sm-2 control-label col-xs-0"></label>
   	 				 <div class="col-sm-8 col-xs-12">   	 				 	
    				<a href="#" class="btn btn-danger btn-a btn-sure" onclick="formValidate()">确定</a>
   	 				 </div>
 				 </div>
			</div>
		</div>
		</form>
	</div>
	
	
	<!--手机验证-->
	<div class="tel_yz">
		<div class="tel_yz_in">
			<div class="del_tit row">
				<div class="col-xs-12">
				手机验证
				<img src="${ctx}/pages/pcWeb/css/images/wallet/del.png" class="right" id="colse"/>
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
						<div class="left" style=" line-height: 35px;">没收到短信，<a href="javascript:void(0)" id="yzm_tishi" class="redClolr"></a></div>
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
<script> 
$(function(){
// 	var date=new Date(),
// 	 	thisYear=date.getFullYear(),
// 	 	yearAgo=[];	
	//验证身份证格式
	$("#sfz").blur(function(){
		var sfz18=0;	//身份证第18位
		var sfz=$("#sfz").val();
		if(sfz.length!=18&&sfz.length!=0){	//	身份证不是18位
			$("#sfz_err2").tooltip("show");
			return false;
		}
		if(sfz.length==18){
			var sfz_array=sfz.split("");
			var test=[7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2];
			for(var i=0;i<17;i++){
				sfz18+=test[i]*sfz_array[i];
			}
			sfz18%=11;
			var test2=[1,0,"X",9,8,7,6,5,4,3,2];
			sfz18=test2[sfz18];
			if(sfz18!=sfz_array[17]){	//身份证的第18位按一定规律计算
				$("#sfz_err2").tooltip("show");
				return false;
			}
		}
	})
	$("#sfz").focus(function(){
		$("#sfz_err2").tooltip("hide");
	})
	
	//验证银行卡格式及归属
	$("#bank").on("input propertychange",function(){//实时监听
		var num=$("#bank").val();
// 		var cardbank=testcredit(num);
// 		if(cardbank==false){	//卡号错误
// 			$("#bank_err2").tooltip("show");
// 			$("#bank").css("background-image","none");
// 			return false;
// 		}
		/*卡号识别*/
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/bank/findBankName.do?bankCard="+num,			
			dataType:"json",
			success:function(response){
				//console.log(response);
				if(response.success=="true"){
					var data = response.data;
					var fbankName=data.fbankName; /* 银行显示名字 */
					var fbankCardType = data.ftype; 	/* 银行卡类型（1借记卡2信用卡） */
					var fbankCardID  =data.fid; /*卡类型ID*/
					var fcardpaytype = data.fvercode; /* 银行类型验证码 */
					/* var cardimg="url("+card_img(bankName)+")"; */
					if(fbankCardType == 2){
						$(".bankTimeBox,.cvnBox").show();
						getHtmlLoadingAfterHeight();
					}else{
						$(".bankTimeBox,.cvnBox").hide();
					}
					var cardimg="url("+card_img(data.ficon)+")";
/* 					$("#bank").css({"background-image":cardimg,"background-repeat":"no-repeat","background-position":"97% 40%"});
 */					$("#fbankName").val(fbankName);
					$("#fbankCardType").val(fbankCardType);
					$("#fbankCardID").val(fbankCardID);
					$("#fcardpaytype").val(fcardpaytype);
					$("#bankname").text(fbankName);
				}else{
					$(".bankTimeBox,.cvnBox").hide();
					$("#bankname").text('输入卡号后会智能识别银行和卡种');
				}
			}
		});	
	})

	$("#colse").click(function(){
		$(".tel_yz,.masker").hide();
	});
});
	
var change;
function formValidate(){
	/*表单验证提示*/
	var val = ["name","sfz","bank","mobile"];
	for(var i=0;i<val.length;i++){
		var name=$("#"+val[i]).val();
		if(name==""||name==null){
			$("#"+val[i]).focus();
			$("#"+val[i]+"_err").tooltip("show");
			setTimeout(function(){
				$("#"+val[i]+"_err").tooltip("hide");
			},1000);
			return false;
		}
	}
	
	var re = /^1[3|4|5|7|8][0-9]\d{8}$/;//手机号码正则表达式
	if(!(re.test($("#mobile").val()))){
		$("#mobile").focus();
		$("#mobile_err2").tooltip('show');
		setTimeout(function(){
			$("#mobile_err2").tooltip("hide");
		},1000);
		return false;
	}
	
	
	/* 银行卡号验证 */
	var bankReg = /^\d{10,20}$/
	if(!bankReg.test($('#bank').val())){
		$("#bank").focus();
		$("#bank_err2").tooltip('show');
		setTimeout(function(){
			$("#bank_err2").tooltip("hide");
		},1000);
		return false;
	}
	

	
	/*信用卡填写后验证*/
	var fbankCardType  = $('#fbankCardType').val();
	var cvn=$("#CVN").val(),
		bankTime=$("#BankTime").val();
	//当信用卡才验证；
	if(fbankCardType==2){
		//cvn 限制在三位
		if(cvn.length!=3){
			$("#CVN").focus();
			$("#CVN_lengtherr").tooltip("show");
			setTimeout(function(){
				$("#CVN_lengtherr").tooltip("hide");
			},1000);
			return false;
		}
		if(cvn!=""){
			if(bankTime==""){
				$("#BankTime").focus();
				$("#BankTime_err").tooltip('show');
				setTimeout(function(){
					$("#BankTime_err").tooltip("hide");
				},1000);
				return false;
			}
		}
		if(cvn==""&&bankTime!=""){
			$("#CVN").focus();
			$("#CVN_err").tooltip("show");
			setTimeout(function(){
				$("#CVN_err").tooltip("hide");
			},1000);
			return false;
		}
	}

	/*需勾选用户协议*/
	var agreement=$("#agreement").is(':checked');
	if(agreement==false){
		$("#agreement").focus();
		$("#agreement_err").tooltip("show");
		setTimeout(function(){
			$("#agreement_err").tooltip("hide");
		},1000);
		return false;
	}
	else{
		var loading = layer.load();
	}
	
	/*提交绑卡信息*/
	var bankTime= $('#BankTime').val();
	var bankM = bankTime.split('/')[0];
	var bankY = bankTime.split('/')[1];
	/* var result=$("#apply_form").serialize(); */
	$.ajax({
		type:"post",
		url:"${ctx}/pcWeb/bank/BankBanDing.do",
		dataType:'json',
		data:{
			fname:$('#name').val(),
			fcard:$('#sfz').val(),
			fcardNumber:$('#bank').val(),
			fcreditCardCvn:$('#CVN').val(),
			fcreditCardYear:bankY,
			fcreditCardMonth:bankM,		 
			ftel:$('#mobile').val(),
			fbankName:$('#fbankName').val(),
			fbankCardType:$('#fbankCardType').val(),
			fbankCardID:$('#fbankCardID').val(),
			fcardpaytype:$('#fcardpaytype').val()
		},
		success:function(response){
			layer.close(loading);
			console.log(response);
	       if(response.success=="true"){//银行卡绑定成功
	            $("#fid").val(response.data);//fid; 验证时用；
	    	    $(".masker,.tel_yz").show();
	    		var mobile=$("#mobile").val();
	    		$("#Myphone").text(mobile.substring(0,3)+"****"+mobile.substring(7));
// 	    		$("#yzm_tishi").attr('onclick','sendCode("yzm_tishi")');
				var s=60;					
				sendMsg=setInterval(function(){					
					$("#yzm_tishi").text(s+"s后重新发送！");
					s--;
					if(s<0){
						clearInterval(sendMsg);
						$("#yzm_tishi").text("点击重发！");
						$("#yzm_tishi").attr('onclick','sendCode(\'yzm_tishi\')');
					}
				},1000);
// 	            	location.href="${ctx}/pages/pcWeb/wallet/wallet_addCardSuccess.jsp";
	      }
	       else if(response.success=="false"){//银行卡绑定失败
	    	   top.layer.alert(response.msg); 
// 	    	   $('#apply_form')[0].reset();//清空表单  
	       }
           /*  $("#data").val(response.data);  */        
		}
	});
	
	
	/*高度获取*/
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
	
	
}

/*验证码验证*/
function formValidate2(){	
	var mobile=$("#mobile").val();
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
	else{
// 		var loading=layer.load();
	}
	$.ajax({
		type:"POST",
		url:"${ctx}/pcWeb/bank/VerificationCode.do",
		data:{"ftel":mobile,"VerificationCode":yzm,/* "BankInfoFID": $("#data").val(), */"fid":$("#fid").val(),"BankCardNumber":$("#bank").val()},
		dataType:'json',
		success:function(response){
// 			layer.close(loading);
			if(response.success=="false"){
				$("#yzm_err2").tooltip('show');
				setTimeout(function(){
					$("#yzm_err2").tooltip("hide");
				},1000);
				return false;
			}else{//验证码校验成功后
				location.href="${ctx}/pages/pcWeb/wallet/wallet_addCardSuccess.jsp";
			}
		}
	}); 
}					
	
/*验证码重新获取及倒计时*/
function sendCode(t,s){
	/* var result=$("#apply_form").serialize(); */
	var bankTime= $('#BankTime').val();
	var bankM = bankTime.split('/')[0];
	var bankY = bankTime.split('/')[1];
	$("#"+t).text("发送中...");
	$.ajax({
		type:"POST",
		url:"${ctx}/pcWeb/bank/BankBanDing.do",
		data:{
			fname:$('#name').val(),
			fcard:$('#sfz').val(),
			fcardNumber:$('#bank').val(),
			fcreditCardCvn:$('#CVN').val(),
			fcreditCardYear:bankY,
			fcreditCardMonth:bankM,		 
			ftel:$('#mobile').val(),
			fbankName:$('#fbankName').val(),
			fbankCardType:$('#fbankCardType').val(),
			fbankCardID:$('#fbankCardID').val(),
			fcardpaytype:$('#fcardpaytype').val()
		},
		dataType:'json',
		success:function(response){
			//console.log(response);
			if(response.success=="false"){
				$("#"+t).text(response.msg);
				$("#"+t).removeAttr("onclick");
				return false;
			}else{			
				var s = s || 60;
				$("#"+t).removeAttr("onclick");
				change=setInterval(function(){
					$("#"+t).text(s+"s后重新发送！");
					s--;
					if(s<0){
						clearInterval(change);
						$("#"+t).text("点击重发！");
						$("#"+t).attr('onclick','sendCode(\'yzm_tishi\')');
					}
				},1000);
			}
		}
	});
}
</script>
</body>
</html>