<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>注册页面</title>
<!-- <link rel="stylesheet" href="${ctx}/css/reg.css" type="text/css"/> -->
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/MD5.js"></script>
<style type="text/css">
	.red{
		color:red;
	 }
	#foot table tr td .tip{
		height:35px;
		width:225px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #00CC00;
	}
	
	#foot table tr td .warn{
		height:35px;
		width:225px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:red;
		font-size:12px;
		border:1px solid #FF0033;
	}
	.uname{height:40px;line-height:40px;width:3px;background:url(${ctx}/css/images/user1.png) 1px 1px no-repeat;padding-left:45px;font-size:16px;border:1px solid lightgray;}
	 .upwd{height:40px;line-height:40px;font-size:16px;background:url(${ctx}/css/images/pwd.png) 1px 1px no-repeat;padding-left:45px;border:1px solid lightgray;}
	 .mobile{height:40px;line-height:40px;font-size:16px;background:url(${ctx}/css/images/mobile.png) 1px 1px no-repeat;padding-left:45px;border:1px solid lightgray;}
	 
	 *{
	margin:0px auto;
	padding:0px;
	}
#nav{
	margin-top:10px;
	
	width:1025px;
	}
#container{
	width:100%;
	height:70px;
	border-bottom:2px solid #999;
	line-height:70px;
	font-size:26px;
	font-family:Arial, Helvetica, sans-serif;
	}
#container .txt1,#container .txt2{
		width:165px;
		text-align:center;
		float:left;
		min-top:10px;
	}
#container .txt1{
		margin-left:260px;
		color:red;
		border-bottom:3px solid #F00;
	}
#container .txt2{
		color:#999;
		margin-left:60px;
	}
#foot{
	width:1025px;
	height:460px;
	border-collapse:0px;
	white-space-collapse:collapse;
		}
#foot table{
	margin-top:13px;
	}
#foot table tr td{
		height:60px;
		width:320px;
		line-height:35px;
		font-family:Arial, Helvetica, sans-serif;
		color:#666666;
		font-size:18px;
	}
#foot table .n5{
	width:250px;
	height:35px;
	line-height:35px;
	}
#foot table .tdd{
		width:200px;
		text-align:right;
		margin-right:10px;
	}
#foot table .n4{
		height:35px;
		width:172px;
		line-height:35px;
	}
#foot table .img{
		width:110px;
		height:38px;
		text-decoration:none;
		color:black;
		text-align:center;
		font-size:14px;
		margin-left:5px;
	}

#foot table tr td .tip{
		height:35px;
		width:220px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #999;
		word-wrap:break-word;
	}
	a{text-decoration:none;color:blue;font-size:16px;} 
	a:hover {color: red}
</style>

</head>
<body style="background-color:#F2F2F2;">
	<div id="nav">
    	<div style="margin-top:40px;padding:20px;">
			<a href="${ctx}/index.jsp" title="返回首页"><img style="vertical-align:middle;border:none;" src="${ctx}/css/images/cps-red.png"/></a>
			<b><span style="font-size:35px; position: relative;top: 22px; ">|欢迎注册!</span></b>
		</div>
        <div id="foot" style="height:550px;border:1px solid  lightgrey;padding:30px;background-color:#ffffff;">
	    	<!-- <div style="font-size:30px;text-align:center;"><span>新用户注册</span></div> -->
       		 <s:form   action="user_save"  method="post" onsubmit="return register();">
	            	<table cellpadding="0" cellspacing="0"  width="850" border="0">
	            		<tr>
	                    	<td class="tdd" width="200"></td>
	                        <td width="290" style="font-size:26px;">新用户注册</td>
	                        <td></td>
	                    </tr>
	                	<tr>
	                    	<td class="tdd"><span class="red">&nbsp;*&nbsp;</span></td>
	                        <td><input placeholder="请输入用户名" type="text" class="n5 uname" id="fname" name="fname" maxlength="80" onblur="fnameblur();"/></td>
	                        <td id="tipFname">&nbsp;</td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">&nbsp;*&nbsp;</span></td>
	                        <td><input placeholder="设置密码" type="password" class="n5 upwd" id="fpassword1" name="fpassword" onblur="fpassword1blur();"/></td>
	                        <td id="tipPwdOne">&nbsp;</td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">&nbsp;*&nbsp;</span></td>
	                        <td><input placeholder="确认密码" type="password" class="n5 upwd" id="fpassword2" onblur="fpassword2blur();"/></td>
	                        <td id="tipPwdTwo">&nbsp;</td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">&nbsp;*&nbsp;</span></td>
	                        <td><input placeholder="验证手机" type="text" class="mobile n5" id="ftel" name="ftel"  maxlength="11" onblur="ftelblur();"/></td>
	                        <td id="tipTel">&nbsp;</td>
	                    </tr>
	                    <tr>
	                    	<td class="tdd"><span class="red">&nbsp;*&nbsp;</span></td>
	                        <td>
	                        	<input placeholder="短信验证码"  style="font-size:16px;" type="text" class="n4" id="identCode" name="identCode" onblur="identCodeblur();"/>
	                        	<input  type="button" class="img" id="IdentCode" value="获取短信验证码" onclick="sendMobileCode();" />
	                        </td>
	                        <td id="tipIdentCode">&nbsp;</td>
	                    </tr>
	                    <tr>
	                    	<td colspan="3" style="text-align:center;font-size:14px;padding-right:150px;width:680px;">
	                        	<input type="checkbox" name="protocol" onclick="checkboxClick(this);" id="prot" checked/>我已阅读并接受&nbsp;&nbsp;&nbsp;《版权声明》和《隐私保护》
	                       	</td>
	                    </tr>
	                    <tr>
	                    	<td colspan="3" style="text-align:center;padding-right:150px;width:680px;">
	                    		<input type="image" src="${ctx}/css/images/zhuce.png" id="_submit"/><%-- ${ctx}/css/images/bukeyong.png" --%>
	                    	</td>         
	                    </tr>
	                    <tr>
	                    	<td colspan="3" style="text-align:center;width:680px;padding-right:55px;">
	                    		已有帐号？<a href="javascript:void(0);" onclick="login()">马上登录>></a>
	                    	</td>         
	                    </tr>
	                </table>
           	</s:form>
        </div>
    </div>
</body>
<script type="text/javascript">
$(function(){
	var JPlaceHolder = {
		    //检测
		    _check : function(){
		        return 'placeholder' in document.createElement('input');
		    },
		    //初始化
		    init : function(){
		        if(!this._check()){
		            this.fix();
		        }
		    },
		    //修复
		    fix : function(){
		        jQuery(':input[placeholder]').each(function(index, element) {
		            var self = $(this), txt = self.attr('placeholder');
		            self.wrap($('<span></span>').css({position:'relative', zoom:'1', border:'none', background:'none', padding:'none', margin:'none'}));
		            var pos = self.position(), h = self.outerHeight(true), paddingleft = self.css('padding-left');
		            var holder = $('<span></span>').text(txt).css({position:'absolute', left:pos.left, top:pos.top, height:h,  paddingLeft:paddingleft, color:'#aaa'}).appendTo(self.parent());
		            self.focusin(function(e) {
		                holder.hide();
		            }).focusout(function(e) {
		                if(!self.val()){
		                    holder.show();
		                }
		            });
		            holder.click(function(e) {
		                holder.hide();
		                self.focus();
		            });
		        });
		    }
		};
		//执行
		jQuery(function(){
		    JPlaceHolder.init();    
		});
		//2015-10-25 IE8支持改成上述方法 
// 	if(window.attachEvent){//设置IE默认值
// 		$('input').each(function(i,input){
// 			if(input.getAttribute('placeholder')&&$(this).attr('type')!='password'){
// 				$(this).val(input.getAttribute('placeholder'));
// 				$(this).focus(function(){
// 					if($(this).val()==input.getAttribute('placeholder')){
// 						$(this).val('');
// 					}
// 				}).blur(function(){
// 					if(!$(this).val()){
// 						$(this).val(input.getAttribute('placeholder'));
// 					}
// 				})
// 			}
// 		})
// 	}
})
//跳转登录
function login(){
	 var i = layer.open({
		    title:'',move:false,
		    type: 2,
		    anim:true,
		    area: ['350px', '310px'],
		    content: window.getRootPath()+"/pages/smallLogin/smallLogin.jsp"
		});
	 layer.style(i,{
			'border-radius':'25px',
			'border':'2px solid #a1a1a1'
		})
}
//账号提示
function nameFocus(){
		$("#tipFname").html("<p class="+'tip'+">支持汉字、字母、数字及'_'组合</p>");
}
//账号校验
function nameBlur(){
	 if($("#fname").val()!=""){
		 var re = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
	     if (re.test($("#fname").val())) {
	         //$("#tipFname").html("<p class="+'tip'+">支持汉字、字母、数字及'_'组合</p>");
	     } else {
	       $("#tipFname").html("<p class="+'warn'+">支持汉字、字母、数字及'_'组合</p>");
	     }
	 }
}
//手机号码提示
function telFocus(){
		$("#tipTel").html("<p class="+'tip'+">完成验证后,您可以使用该手机号找回密码</p>");
}
//手机号码校验
function telBlur(){
	 if($("#ftel").val()!=""){
		 var re = /^1[3|4|5|8][0-9]{9}$/;
	     if (re.test($("#ftel").val())) {
	        //$("#tipTel").html("<p class="+'tip'+">完成验证后,您可以使用该手机号找回密码</p>");
	     } else {
	        $("#tipTel").html("<p class="+'warn'+">手机号码格式错误</p>");
	     }
	 }
}
/************结束，注册页面不引用上述js********************/
//输入栏提示以及表单校验
$(document).ready(function(){
	 //账号提交前校验
	 $("#fname").focus(function(){
		 $("#tipFname").html("<p class="+'tip'+">支持汉字、字母、数字及“_”组合</p>");
	});
	//密码提交前校验
	$("#fpassword1").focus(function(){
		$("#tipPwdOne").html("<p class="+'tip'+">建议由字母、数字、符号、两种以上组合</p>");
	});
	//确认密码提交前校验
	$("#fpassword2").focus(function(){
		$("#tipPwdTwo").html("<p class="+'tip'+">请再次输入密码</p>");
	});
	//输入手机号的校验
	$("#ftel").focus(function(){
		$("#tipTel").html("<p class="+'tip'+">验证完后您可以用手机号码登陆或找密码</p>");
	});
	//验证码的提交前校验和提示
	$("#identCode").focus(function(){
		$("#tipIdentCode").html("<p class="+'tip'+">请输入6位验证码</p>");
	});
	//页面加载判断checkbox选中的时候，提交按钮可用
	if($("#prot").is(":checked")){
		$("#_submit").attr("disabled",false);
		$("#_submit").attr("src","${ctx}/css/images/zhuce.png");
		$("#_submit").css("cursor","pointer");
	}else{
		$("#_submit").attr("src","${ctx}/css/images/bukeyong.png");
		$("#_submit").attr("disabled",true);
		$("#_submit").css("cursor","not-allowed");
	}
	//页面重新加载或者刷新的时候总是启用发送验证码的按钮
	$("#IdentCode").removeAttr("disabled");//启用按钮
});
//checkbox的点击事件
function checkboxClick(t){
	 if($(t).is(":checked")){
		 $("#_submit").attr("src","${ctx}/css/images/zhuce.png");
		 $("#_submit").attr("disabled",false);
		 $("#_submit").css("cursor","pointer");
	 }else if($(t).is(":checked")==false){
		 /* $("#_submit").attr("src","${ctx}/css/images/bukeyong.png"); */
		 /* $("#_submit").attr("disabled",true); */
		 /* $("#_submit").css("cursor","not-allowed"); */
	 }
}
//申明一个变量来判断表单提交
var flag=true;
function fnameblur(){
	var re = /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/;
	    if (re.test($("#fname").val())) {
	    	$.ajax({
				type:"POST",
				url:"${ctx}/user_isUnique.net",
				async:false,  
				data: {"fname":$("#fname").val()},  
				success:function(response){
					if(response=="success"){
						 $("#tipFname").html("<p class="+'warn'+" style='line-height:20px;height:38px;'>该用户名已被使用,请重新输入。如果您是该用户，请立即<a href='javascript:void(0);' onclick='login(this)' style='font-size:12px;'>登陆</a></p>");
						 flag=false;
					}else{
						$("#tipFname").html("<img src='${ctx}/css/images/xg.png'/>");
						flag=true;
					}
				}
	    	});
	     } else {
	        $("#tipFname").html("<p class="+'warn'+">账号存在不合法字符!</p>");
	        flag=false;
	     }
	}
function fpassword1blur(){
	 if($("#fpassword1").val().indexOf(" ")==-1){
		if($("#fpassword1").val().length<6||$("#fpassword1").val().length>16){
			$("#tipPwdOne").html("<p class="+'warn'+">密码长度为6-16位!</p>");
			flag=false;
		}else{
			$("#tipPwdOne").html("<img src='${ctx}/css/images/xg.png'/>");
			flag=true;
		}
	}else if($("#fpassword1").val().indexOf(" ")!=-1){
		$("#tipPwdOne").html("<p class="+'warn'+">密码不能包含空格!</p>");
		flag=false;
	}else if($("fpassword1").val()==""){
		$("#tipPwdOne").html("<p class="+'warn'+">密码不能为空!</p>");
		flag=false;
	}
}
function fpassword2blur(){
	 if($("#fpassword2").val()!=$("#fpassword1").val()){
		$("#tipPwdTwo").html("<p class="+'warn'+">两次密码输入不一致!</p>");
		flag=false;
	}else if (!$("#fpassword2").val()==""){
		$("#tipPwdTwo").html("<img src='${ctx}/css/images/xg.png'/>");
		flag=true;
	}else
	{
		$("#tipPwdTwo").html("");
		flag=false;
	}
}
function ftelblur(){
	 var re = /^1[3|4|5|7|8][0-9]{9}$/;
   if (re.test($("#ftel").val())) {
	 	$.ajax({
			type:"POST",
			url:"${ctx}/user_isUnique.net",
			async:false,  
			data: {"fname":$("#ftel").val()},  
			success:function(response){
				if(response=="success"){
					 $("#tipTel").html("<p class="+'warn'+" style='line-height:20px;height:38px;'>该手机号已被注册。如果您是该手机用户，可立即<a href='javascript:void(0);' onclick='login(this)' style='font-size:12px;'>登陆</a></p>");
					 flag=false;
				}else{
					$("#tipTel").html("<img src='${ctx}/css/images/xg.png'/>");
					flag=true;
				}
			}
    	});
   } else {
      $("#tipTel").html("<p class="+'warn'+">手机号码格式错误</p>");
      flag=false;
   }
}
function identCodeblur(){
		var reg=/^[0-9]{6}$/;
		if($("#identCode").val()){
			if(!(reg.test($("#identCode").val()))){
				flag=false;
				$("#tipIdentCode").html("<p class="+'warn'+">验证码输入有误</p>");
				return;
			}
		}else{
			flag=false;
			if(curCount<=0){
				$("#tipIdentCode").html("<p class="+'warn'+">验证码不能为空</p>");
			}
			return;
		}
		$.ajax({
				type:"POST",
				url:"${ctx}/user_valiCode.net",
				async:false,
				dataType:"text", 
				data: {"fname":$("#fname").val(),"identCode":$("#identCode").val()},  
				success:function(response){
					if(response=="fail"){
						 $("#tipIdentCode").html("<p class="+'warn'+">验证码输入错误</p>");
						 flag=false;
					}else{
						if(!$("#identCode").val()==""){
						 $("#tipIdentCode").html("<img src='${ctx}/css/images/xg.png'/>");
						 flag=true;}
					}
				}
		});
}
//发送验证码的点击事件
var count = 60; //间隔函数，1秒执行
var curCount=0;//当前剩余秒数
var InterValObj=null; //timer变量，控制时间
function sendMobileCode(){
	//每次用户点击发送验证码，先清空提示，以便用户更换手机号码时仍然显示不好的提示
	$("#tipIdentCode").html("");
	//点击之前先判断手机号码
	var re = /^1[3|4|5|7|8][0-9]{9}$/;
	if($("#ftel").val()==""){
		$("#tipIdentCode").html("<p class="+'warn'+">请填写手机号,方便接受验证码。</p>");
		flag=false;
	}else if(!re.test($("#ftel").val())){
		$("#tipIdentCode").html("<p class="+'warn'+">手机号码格式不正确。</p>");
		flag=false;
	}else{
		$("#IdentCode").val("正在发送中...");
		$("#IdentCode").attr("disabled", "true");
		//发送验证码
		$.ajax({
			type:"POST",
			url:"${ctx}/user_getMobileCode.net",
			async:true,
			data: {"fname":$("#fname").val(),"ftel":$("#ftel").val()}, 
			dataType:"text", 
			success:function(response){
				curCount = count;
				if(response=="success"){
					//如果手机号没有被注册过并且合法,那么就倒计时开始发送验证码
					InterValObj=window.setInterval(function(){countDown();},1000); //启动计时器，1秒执行一次
				}else if(response=="fail"){
					//如果返回的是fail，那么说明手机号已经被注册了
					$("#tipIdentCode").html("<p class="+'warn'+">手机号已经被注册</p>");
					  $("#IdentCode").removeAttr("disabled");//启用按钮
			          $("#IdentCode").val("获取短信验证码");
					flag=false;
				}else
				{
					$("#tipIdentCode").html("<p class="+'warn'+">在限定时间15分钟内无法重复发送</p>");
					$("#IdentCode").removeAttr("disabled");//启用按钮
			        $("#IdentCode").val("获取短信验证码");
			        flag=false;
				}
			}
		});
	}
}
//定时器内倒计时的函数
//在定时器中拿不无法覆盖flag变量，通过判断额外变量来决定flag的值
var lvs=true;
function countDown(){
	$("#tipIdentCode").html("");
	if (curCount == 0) {
          window.clearInterval(InterValObj);//停止计时器
          $("#IdentCode").removeAttr("disabled");//启用按钮
          $("#IdentCode").val("重新发送验证码");
          lvs=false;
          return;
     }
else {
	curCount--;
          $("#IdentCode").val("请在" + curCount + "秒内输入");
          $("#IdentCode").attr("disabled", "true");
          lvs = true;
     }
}
//表单提交校验
function valiCheck(){
	if(!$('input[type=checkbox]')[0].checked){
		layer.msg('请接受服务条款');
		return false;
	}
	
	if(lvs==false){
		flag=false;
		/* $("#tipIdentCode").html("<p class="+'warn'+">验证码已经过时，请重新发送。</p>");  */
      window.clearInterval(InterValObj);//停止计时器
      $("#IdentCode").removeAttr("disabled");//启用按钮
      $("#IdentCode").val("重新发送验证码");
	}
	if($("#fname").val()==""){
		$("#tipFname").html("<p class="+'warn'+">请输入用户名!</p>");
		flag=false;
	}
	if($("#fpassword1").val()==""){
		$("#tipPwdOne").html("<p class="+'warn'+">请输入密码!</p>");
		flag=false;
	}
	if($("#fpassword2").val()==""){
		$("#tipPwdTwo").html("<p class="+'warn'+">确认密码不能为空!</p>");
		flag=false;
	}
	if($("#fpassword2").val()!="" && $("#fpassword1").val()!="" ){
		if($("#fpassword2").val()!=$("#fpassword1").val()){
			$("#tipPwdTwo").html("<p class="+'warn'+">两次密码输入不一致!</p>");
			flag=false;
		} else{
			$("#tipPwdTwo").html("<img src='${ctx}/css/images/xg.png'/>");
		} 
	}
	if($("#ftel").val()==""){
		$("#tipTel").html("<p class="+'warn'+">请输入手机号!</p>");
		flag=false;
	}
	if($("#identCode").val()==""){
		$("#tipIdentCode").html("<p class="+'warn'+">请输入验证码!</p>");
		flag=false;
	}
	
	if($("#user_save").find(".warn").length > 0){
		flag = false;
	}
	//alert(flag);
	//fnameblur()&&fpassword1blur()&&fpassword2blur()&&ftelblur()&&identCodeblur()&&sendMobileCode();
	return flag;
}


function register(){
	if(valiCheck()){
		$.ajax({
			type:"POST",
			url:"${ctx}/user_saveAjax.net",
			async:true,
			data: $("#user_save").serialize(), 
			dataType:"text", 
			success:function(response){
				if(response=="success"){
					location.href="${ctx}/index.jsp";
// 					layer.alert('账号注册成功，是否立即申请认证？</br>认证后能获得更多你想要的权限哦！', 
// 							{	closeBtn: 0,
// 								icon: 1,
// 								btn: ['是', '否'],
// 								yes:function(index){
// 									window.location.href="${ctx}/usercenter/d_certificate.net";
// 									layer.close(index);
// 								},
// 								no:function(index){
// 									location.href="${ctx}/index.jsp";
// 									layer.close(index);
// 								}
// 							});
				}else if(response=="usercheck_fail"){
					$("#tipFname").html("<p class="+'warn'+" style='line-height:20px;height:38px;'>该用户名已被使用,请重新输入。如果您是该用户，请立即<a href='javascript:void(0);' onclick='login(this)' style='font-size:12px;'>登陆</a></p>");
					flag=false;
				}else if(response == "identcode_fail")
				{
					$("#tipIdentCode").html("<p class="+'warn'+">验证码输入错误</p>");
					flag=false;
				}
			}
		});
	}
	
	return false;
}

</script>
</html>
