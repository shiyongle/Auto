$(document).ready(function(){
//	var url=window.location.href;
//	var urid=url.split("=");
//	var userid=urid[urid.length-1];
	var url=GetRequest();
	$("#fuserroleid").val(url.urid);
	$('#btnReg').click(function(){
		if(validate())
		{
			$.ajax({
				type : "POST",
				dataType:"json",
				data: {"ftel":$("#phone").val(),"identCode":$("#qrcode").val()},
				url : "../../../app/user/regValiCode.do",//验证码校验
				success : function(response) {
					if(response.success=="false"){
						$('#phoneModal').modal('show');
						$('#font2').text(response.msg);
						$(function () { $('#phoneModal').modal({
							keyboard: true
						});
						});
					}else{
						var params = decodeURIComponent($("#myform").serialize(), true);
						$.ajax({
							type : "POST",
							dataType:"json",
							data:params,
							url : "../../../user/inviteReg.do",
							success : function(response) {
//								$('#successModal').modal('show');
//								$(function () { $('#successModal').modal({
//								keyboard: true
//								});
//								});
								if(response.success=="true")
								{
									$('#phoneModal').modal('show');
									$('#font2').text('注册成功，跳转中...');
									$(function () { 
										$('#phoneModal').modal({
											keyboard: false
										});
									});
									$('#phoneModal').on('shown.bs.modal', function (e) {
										var now = new Date();
										var exitTime = now.getTime() + 1000;
										while (true) {
											now = new Date();
											if (now.getTime() > exitTime){
//												var ua = navigator.userAgent.toLowerCase();	
//												if (/iphone|ipad|ipod/.test(ua)) {
//												document.location.href="https://appsto.re/cn/bdc57.i";
//												window.location.href="http://www.olcps.com/csclappdown/";
//												return;
//												} else if (/android/.test(ua)) {
//												window.location.href="http://fir.im/yejx";
//												return;
//												}
												window.location.href="http://www.olcps.com/csclappdown/";
												return;
											}
										}
									});
								}
								else if(response.msg=="exist")
								{
									$('#phoneModal').modal('show');
									$('#font2').text('该手机号已注册，正在跳转下载界面...');
									$(function () { $('#phoneModal').modal({
										keyboard: true
									});
									});
									$('#phoneModal').on('shown.bs.modal', function (e) {
										var now = new Date();
										var exitTime = now.getTime() + 1000;
										while (true) {
											now = new Date();
											if (now.getTime() > exitTime){
//												var ua = navigator.userAgent.toLowerCase();	
//												if (/iphone|ipad|ipod/.test(ua)) {
//												window.location.href="https://itunes.apple.com/us/app/yi-lu-hao-yun/id1004806337?mt=8";
//												return;
//												} else if (/android/.test(ua)) {
//												window.location.href="http://fir.im/yejx";
//												return;
//												}
												window.location.href="http://www.olcps.com/csclappdown/";
												return;
											}
										}
									});
								}
								else
								{
									$('#phoneModal').modal('show');
									$('#font2').text(response.msg);
									$(function () { $('#phoneModal').modal({
										keyboard: true
									});
									});
									$("#_time").html('获取验证码');
								}
							}
						});
					}
				}
			});
		}
	});
});

//表单验证
function validate()
{
	var phone = document.getElementById('phone').value;
	var pass1 = $("#password1").val();
	var pass2 = $("#password2").val();
	var qrcode = $("#qrcode").val();
	if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))||phone==""){ 
		$('#phoneModal').modal('show');
		$('#font2').text("手机号码错误，请重新输入。");
		$(function () { $('#phoneModal').modal({
			keyboard: true
		});
		});
		return false; 
	}
	if(qrcode=="")
	{
		$('#phoneModal').modal('show');
		$('#font2').text("请输入验证码");
		$(function () { $('#phoneModal').modal({
			keyboard: true
		});
		});
		return false;
	}
	if(pass1==""||pass2==""||pass1!=pass2)
	{
		$('#phoneModal').modal('show');
		$('#font2').text("2次密码输入不一致");
		$(function () { $('#phoneModal').modal({
			keyboard: true
		});
		});
		return false;
	}
	return true;
}

function GetRequest() { 
	var url = location.search; //获取url中"?"符后的字串
	var theRequest = new Object(); 
	if (url.indexOf("?") != -1) { 
		var str = url.substr(1); 
		strs = str.split("&"); 
		for(var i = 0; i < strs.length; i ++) { 
			theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]); 
		} 
	} 
	return theRequest; 
} 

//获取验证码
function reg_time(){

	var phone = document.getElementById('phone').value;
	if(!(/^1[3|4|5|7|8]\d{9}$/.test(phone))||phone==""){ 
		$('#phoneModal').modal('show');
		$(function () { $('#myModal').modal({
			keyboard: true
		});
		});
		return false; 
	}else{
		$("#_time").html("信息发送中...");
//		$("#btnCode").val("信息发送中...");
		$("#btnCode").attr("disabled","disabled");
		$("#_time").unbind("click");
		$.ajax({
			async:true,
			dataType:"json",
			url : "../../../app/user/getRegValidateVCodeTel.do?ftel="+phone,
			success : function(response) {
				if(response.success=="true"){
					var l_a;
					var l_i = 60;
//					修改html
					$("#_time").text('(60)秒后重新获取');
					$("#_time").removeAttr("onclick");
					_interval = setInterval(_validation, 1000);
					function _validation()
					{	
						$("#_time").unbind("click"); 
						l_a = --l_i;
						$("#_time").text('('+l_a+')'+'秒后重新获取');
						if(l_a <=0)
						{	
							clearInterval(_interval);
							$("#_time").html('重新获取验证码');
							$("#_time").bind("click");
							$("#btnCode").removeAttr("disabled");
							$("#_time").attr("onclick","reg_time();");
						}
					}
				}else{
					$('#phoneModal').modal('show');
					$('#font2').text(response.msg);
					$(function () { $('#phoneModal').modal({
						keyboard: true
					});
					});
					$("#btnCode").removeAttr("disabled");
					$("#_time").html('重新获取验证码');
					$("#_time").bind("click");
					$("#_time").attr("onclick","reg_time();");
					//如果返回的是fail，那么说明手机号已经被注册了
					$("#tipIdentCode").html("<p class="+'warn'+">手机号已经被注册</p>");
				}
			}
		});
	}
}