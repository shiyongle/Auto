/****************其他页面需要校验的js*************/
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
		 $("#tipFname").html("<p class="+'tip'+">支持汉字、字母、数字及'_'组合</p>");
	});
	//密码提交前校验
	$("#fpassword1").focus(function(){
		$("#tipPwdOne").html("<p class="+'tip'+">6-16位字符,建议由字母,数字和符号组合</p>");
	});
	//确认密码提交前校验
	$("#fpassword2").focus(function(){
		$("#tipPwdTwo").html("<p class="+'tip'+">请再次输入密码</p>");
	});
	//输入手机号的校验
	$("ftel").focus(function(){
		$("#tipTel").html("<p class="+'tip'+">完成验证后,您可以使用该手机号找回密码</p>");
	});
	//验证码的提交前校验和提示
	$("#identCode").focus(function(){
		$("#tipIdentCode").html("<p class="+'tip'+">请输入6位验证码</p>");
	});
	//页面加载判断checkbox选中的时候，提交按钮可用
	if($("#prot").is(":checked")){
		$("#_submit").attr("disabled",false);
		$("#_submit").attr("src","css/images/zhuce.png");
		$("#_submit").css("cursor","pointer");
	}else{
		$("#_submit").attr("src","css/images/bukeyong.png");
		$("#_submit").attr("disabled",true);
		$("#_submit").css("cursor","not-allowed");
	}
	//页面重新加载或者刷新的时候总是启用发送验证码的按钮
	$("#IdentCode").removeAttr("disabled");//启用按钮
 });
 //checkbox的点击事件
 function checkboxClick(t){
	 if($(t).is(":checked")){
		 $("#_submit").attr("src","css/images/zhuce.png");
		 $("#_submit").attr("disabled",false);
		 $("#_submit").css("cursor","pointer");
	 }else if($(t).is(":checked")==false){
		 $("#_submit").attr("src","css/images/bukeyong.png");
		 $("#_submit").attr("disabled",true);
		 $("#_submit").css("cursor","not-allowed");
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
						 $("#tipFname").html("<p class="+'warn'+">该帐号已被注册！</p>");
						 flag=false;
					}else{
						$("#tipFname").html("<img src='css/images/xg.png'/>");
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
			$("#tipPwdOne").html("<img src='css/images/xg.png'/>");
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
		$("#tipPwdTwo").html("<p class="+'warn'+">两次输入密码不一致</p>");
		flag=false;
	}else{
		$("#tipPwdTwo").html("<img src='css/images/xg.png'/>");
		flag=true;
	}
 }
 function ftelblur(){
	 var re = /^1[3|4|5|8][0-9]{9}$/;
     if (re.test($("#ftel").val())) {
    	$("#tipTel").html("<img src='css/images/xg.png'/>");
    	flag=true;
     } else {
        $("#tipTel").html("<p class="+'warn'+">手机号码格式错误</p>");
        flag=false;
     }
 }
 function identCodeblur(){
	 if($("#identCode").val()==""){
			$("#tipIdentCode").html("<p class="+'warn'+">验证码不能为空!</p>");
			flag=false;
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
						 $("#tipIdentCode").html("<img src='css/images/xg.png'/>");
						 flag=true;
					}
				}
		});
 }
//发送验证码的点击事件
var count=30;//设置倒计时的变量
var InterValObj=null; //timer变量，控制时间
function sendMobileCode(){
	//每次用户点击发送验证码，先清空提示，以便用户更换手机号码时仍然显示不好的提示
	$("#tipIdentCode").html("");
	//点击之前先判断手机号码
	var re = /^1[3|4|5|8][0-9]{9}$/;
	if($("#ftel").val()==""){
		$("#tipIdentCode").html("请填写手机号，方便接受验证码。");
		flag=false;
	}else if(!re.test($("#ftel").val())){
		$("#tipIdentCode").html("手机号码格式不正确。");
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
				if(response=="success"){
					//如果手机号没有被注册过并且合法,那么就倒计时开始发送验证码
					InterValObj=window.setInterval(function(){countDown();},1000); //启动计时器，1秒执行一次
				}else{
					//如果返回的是fail，那么说明手机号已经被注册了
					$("#tipIdentCode").html("<p class="+'warn'+">手机号已经被注册</p>");
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
	if (count == 0) {
			$("#tipIdentCode").html("<p class="+'warn'+">验证码已经过时，请重新发送。</p>");
            window.clearInterval(InterValObj);//停止计时器
            $("#IdentCode").removeAttr("disabled");//启用按钮
            $("#IdentCode").val("重新发送验证码");
            count=30;
            lvs=false;
            return;
       }
  else {
            count--;
            $("#IdentCode").val("请在" + count + "秒内输入");
            $("#IdentCode").attr("disabled", "true");
       }
}
//表单提交校验
function valiCheck(){
	if(lvs==false){
		flag=false;
		$("#tipIdentCode").html("<p class="+'warn'+">验证码已经过时，请重新发送。</p>");
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
	if($("#ftel").val()==""){
		$("#tipTel").html("<p class="+'warn'+">请输入手机号!</p>");
		flag=false;
	}
	if($("#identCode").val()==""){
		$("#tipIdentCode").html("<p class="+'warn'+">请输入验证码!</p>");
		flag=false;
	}
	//alert(flag);
	//fnameblur()&&fpassword1blur()&&fpassword2blur()&&ftelblur()&&identCodeblur()&&sendMobileCode();
	return flag;
}