<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>添加子账号--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/childAccount_add.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<%-- <script type="text/javascript" src="${ctx}/pages/pcWeb/js/childAccount_add.js"></script> --%>
</head>
<body>
	<div class="main jumbotron">
		<p>增加子账号</p>
		<p class="remind"><img src="${ctx}/pages/pcWeb/css/images/user_center/remind.png"/>&nbsp;&nbsp;您可以将其他账户添加到您的子账号下，添加后可以查看子账号的信息</p>
		<!--输入信息-->
		<form id="childForm">
		<div class="row accountIformation">
			<div class="col-xs-6 col-xs-offset-3">
				<p>输入添加的账户信息：</p>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<div class="form-inline">
				<div class="form-group user_name myform">	
					<span>用户名</span>
      				<input type="text" class="form-control" id="user_name" name="vmiUserName" placeholder="">
      				<a id="user_name_err"  data-placement="right" title="用户名不能为空"></a>
				</div>
				</div>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<div class="form-inline">
				<div class="form-group myform">	
					<span>手机号码</span>
      				<input type="text" class="form-control" id="phone_number" name="vmiUserPhone" placeholder="">
      				<a id="phone_number_err"  data-placement="right" title="手机号码不能为空"></a>
      				<a id="phone_number_err2"  data-placement="right" title="手机号码格式错误"></a>
				</div>
				</div>
			</div>	
			<div class="col-xs-6 col-xs-offset-3">
				<div class="form-inline">
				<div class="form-group myform">	
					<span>　　密码</span>
      				<input type="text" class="form-control" id="password" name="fpassword" placeholder="">
      				<a id="pass_err"  data-placement="right" title="密码不能为空"></a>
				</div>
				</div>
			</div>
			<div class="col-xs-6 col-xs-offset-3">
				<button type="button" id="add" class="btn btn-danger">添加</button>	
			</div>
		</div>
		</form>
	</div>
<script>
$(document).ready(function(){
	$("#add").click(function(){
		var user_name=$("#user_name").val();//用户名
		var phone_number=$("#phone_number").val();//手机号
		var password=$("#password").val();//密码
		if(user_name==""||user_name==null){		
			$("#user_name").focus();
			$("#user_name_err").tooltip('show')
			setTimeout(function(){
				$("#user_name_err").tooltip("hide");
			},1000);
			return false;
		}
		if(phone_number==""||phone_number==null){		
			$("#phone_number").focus();
			$("#phone_number_err").tooltip('show')
			setTimeout(function(){
				$("#phone_number_err").tooltip("hide");
			},1000);
			return false;
		}
		var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
		if(!(re.test(phone_number))){
			$("#phone_number_err2").tooltip('show')
			setTimeout(function(){
				$("#phone_number_err2").tooltip("hide");
			},1000);
			return false;
		}
		if(password==""||password==null){		
			$("#password").focus();
			$("#pass_err").tooltip('show')
			setTimeout(function(){
				$("#pass_err").tooltip("hide");
			},1000);
			return false;
		}
		var data=$("#childForm").serialize();
// 		alert(data);
		$.ajax({
			type:"POST",
			url:"${ctx}/pcWeb/user/subNumsave.do",//增加子帐号接口
			data:data,			
			dataType:'json',
			success:function(response){
				if(response.success=="false"){
					//$("").show();   提示提交失败
					layer.msg(response.msg);
// 					$('#user_name').val("");
// 					$('#phone_number').val("");
					$("#childForm")[0].reset();
				}else{
					//提交成功					
					layer.confirm(response.msg, {icon: 1, title:'提示'}, function(index){	
						$(window.parent.document).find("#iframepage").attr("src","${ctx}/pages/pcWeb/user_center/childAccount_manage.jsp");
						layer.close(index);	  
						});
					
				}				
			}
		});
	})
})
</script>
</body>
</html>
