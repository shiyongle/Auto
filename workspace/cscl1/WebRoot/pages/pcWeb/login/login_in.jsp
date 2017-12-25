<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head> 
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>登录--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/login_in.css" rel="stylesheet" charset="utf-8"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/qrcode.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script src="${ctx}/pages/pc/js/MD5.js" type="text/javascript" language="javascript"></script>
</head>
<body>
	<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<div class="container main">
		<div class="row">
			<!--图-->
			<div class="col-xs-12 col-sm-12 col-md-7 ourway">
				<img src="${ctx}/pages/pcWeb/css/images/login/ourway.png"/>
			</div>
						
			<div class="col-xs-12  col-md-3 col-md-offset-1">
				<!--登录-->
				
				<div class="right" id="login">
					
					<!--第一行-->
					<div class="row line">
						
						<div class="col-xs-6" style="font-size: 16px;"><span class="welcome">欢迎回来</span></div>
						<div class="col-xs-6">
							<!--二维码切换-->
							<div class="login_change"><img src="${ctx}/pages/pcWeb/css/images/login/login_qh.png"/></div>
						</div>
					</div>
				
				<!--登录第一版面-->
				<div class="page1">
				<form id="login_form" action="${ctx}/pcWeb/index_pcWeb.do" method="post" onsubmit="return formValidate(this)">
					<!--第二行-->
					<div class="row line">
						<div class="col-xs-12">
							<div class="form-group form_warp">								
							<input type="text" class="form-control form-commomText user_img" id="fname" name="fname" placeholder="请输入手机/邮箱/用户名" />
							<a id="user_err"  data-placement="right" title="账号不能为空"></a>
							</div>
						</div>
					</div>
					
					<!--第三行-->
					<div class="row line" style="position:relative;overflow: visible;">
						<div class="col-xs-12">
							<div class="form-group form_warp">								
							<input type="password" class="form-control form-commomText pass_img" id="fpassword" name="fpassword" placeholder="请输入密码" />
							<a id="pass_err"  data-placement="right" title="密码不能为空"></a>
							</div>
						</div>
						<div id="login_error" class="redClolr" style="display:none;position:absolute;left:17px;top:50px">账号或密码错误</div>
						<div id="login_error1" class="redClolr" style="display:none;position:absolute;left:17px;top:50px">该账号无权登录</div>
						<div id="login_error2" class="redClolr" style="display:none;position:absolute;left:17px;top:50px"></div>
					</div>
					
		
					
					<!--第四行-->
					<div class="row line">
						<div class="col-xs-12">
							<div class="form-group">								
							<input type="submit" class="btn btn-danger btn-login" value="登录"  />
							</div>
						</div>
					</div>
					</form>
					<!--第五行-->
					<div class="row line line_bottom">
						<div class="col-xs-12">
							<div class="left"><a href="${ctx}/pages/pcWeb/pass_word/for_get.jsp" class="redClolr">忘记密码？</a></div>
							<div class="right">还没有账号？<a href="${ctx}/pages/pcWeb/register_iden/register.jsp" class="redClolr">立即注册</a></div>
						</div>
					</div>
				</div>
				<!--登录第二版面-->
				<div class="page2" style="display: none;">
					<!--第一行-->
					<div class="row line">
						<div class="col-xs-12 text-center">
							手机扫码，安全登录
						</div>
					</div>
					
					<!--第二行-->
					<div class="row line">
						<div class="col-xs-12 text-center">
							<div id="ewm_login" class="center-block" style="width:140px;"></div>
							<img src="${ctx}/pages/pcWeb/css/images/login/ewm_err.png" id="ewm_err" style="display: none;"/>	
						</div>
					</div>
					<!--第三行-->
					<div class="row line line_bottom">
						<div class="col-xs-12 text-center">
							<img class="sys" src="${ctx}/pages/pcWeb/css/images/login/sys.png"/>									
							&nbsp;&nbsp;打开<span class="redClolr">一路好运APP</span><br/>扫一扫登录
						</div>
					</div>					
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>
</html>
<script>
 $(function(){
var qrcode = new QRCode($('#ewm_login')[0],{
	width : 140,
	height : 140
});
//轮询
function checkScan(rnd,id){
	return function(){
		$.ajax({
            type:'post',
            url:'${ctx}/user/QRLogin.do?UUID='+rnd+'&fid='+id,
            success:function(data){
        		var data = JSON.parse(data);
        		if(data.success =="success"){
    				var fname = data.Uname;
    				var fpassword = data.UPWD; 
    				var fpw = MD5(fpassword);
    				$.ajax({
							type:"POST",
							url:"${ctx}/pcWeb/user/logon.do",
							data:{"fname":fname,"fpassword":fpw},
							async: false,//同步
							dataType:'json',
							success:function(response){
								if(response.success=="false"){
								
								}else if(response.success=="false1"){
									
								}else if(response.success=="false2"){
									
								}else{
										var temp = $('<form action="${ctx}/pcWeb/index_pcWeb.do" method="post"><input name="fname" value="'+fname+'"/><input name="fpassword" value="'+fpassword+'"/></form>');//创建表单
										 temp.hide(); 
										document.body.appendChild(temp[0]);
										clearInterval(timer);
										temp.submit();//提交 
								}											
							}
					});
        		}
            }
        });
	};
}
var timer= null;
//生成二维码
function makeQrcode(){
	clearInterval(timer);	
	$.ajax({
		url:'${ctx}/user/uuid.do',
		dataType:'json',
		success:function(data){
			var random = data.UUID;
			var fid = data.fid; 
			qrcode.makeCode(random+'|'+fid);
	 		timer =setInterval(checkScan(random,fid),1000);
		}
	});
}
//切换
var bFlag =false;
$('.login_change').click(function(){
	if(bFlag){
		clearInterval(timer);
	}else{
		if(qrcode != null){
			qrcode.clear();
		}
		makeQrcode();
	}
	bFlag = !bFlag;
})
$('#ewm_err').click(function(){
		qrcode.clear();
		makeQrcode();
});	
/*切换二维码*/
var judge=true;
$(".login_change").click(function(){
	if(judge){			
	$(this).find("img").stop().animate({"margin-top":"-59px","margin-left":"-59px"},300);
	$(".page1").hide();
	$(".page2").fadeIn();
	$(".welcome").fadeOut();
	judge=false;
	}
	else{
	$(this).find("img").stop().animate({"margin-top":"0px","margin-left":"0px"},300);
	$(".page2").hide();
	$(".page1,.welcome").fadeIn();
	judge=true;
	}
	
	ewm_timeout(200);
});
	
$("#ewm_err").click(function(){
		$("#ewm_login").show();
		ewm_timeout(200);
});
/*二维码失效*/
var timing;
function  ewm_timeout(t){
	clearTimeout(timing);
	$("#ewm_login").show();
	$("#ewm_err").hide();
	t=t*1000;
	timing=setTimeout(function(){
		$("#ewm_login").hide();
		$("#ewm_err").show();
		clearInterval(timer);	
	},t);
}		
//表单提交+验证
	  if("${username}"!=""){
		  var html1='<li id="nav_login"><a href="${ctx}/pages/pcWeb/login/login_in.jsp" >登录</a></li>'+
			'<li class="li_border">|</li>'+
			'<li id="nav_register"><a href="${ctx}/pages/pcWeb/register_iden/register.jsp" class="redClolr">注册</a></li>';
		  $("#nav_ul").children("#nav_user_name").remove();
		  $("#nav_ul").children("#nav_exit").remove();
		  $("#nav_ul").append(html1);
	  }
	  
	  $("#fname,#fpassword").on("focus",function(){
		  $("#login_error").hide();
	  });
 });
function formValidate(){
	 var ispass = true;
		var user=$("#fname").val();
		var pass=$("#fpassword").val();
		/*账号不为空*/
		if(user==""||user==null){
			$("#fname").focus();
			$("#user_err").tooltip("show");
			setTimeout(function(){
				$("#user_err").tooltip("hide");
			},1000);
			ispass = false;
	    	return ispass;
		}
		/*密码不为空*/
		if(pass==""||pass==null){
			$("#fpassword").focus();
			$("#pass_err").tooltip("show");
			setTimeout(function(){
				$("#pass_err").tooltip("hide");
			},1000);
			ispass = false;
	    	return ispass;
		}
		$.ajax({
			type:"POST",
			url:"${ctx}/pcWeb/user/logon.do",
			data:{"fname":$('#fname').val(),"fpassword":MD5($('#fpassword').val())},
			async: false,//同步
			dataType:'json',
			success:function(response){
				if(response.success=="false"){
					$("#login_error").show();
					$('#userName').val("");
					$('#password').val("");
	    			ispass = false;
				}else if(response.success=="false1"){
					$("#login_error1").show();
					$('#userName').val("");
					$('#password').val("");
	    			ispass = false;
				}else if(response.success=="false2"){
					$("#login_error2").show().text(response.msg);
					$('#userName').val("");
					$('#password').val("");
	    			ispass = false;
				}else{
					$("#login_error").hide();
					$("#login_error1").hide();
				}
				
			}
		});
		return ispass;
}
</script>
