<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心-基础资料</title>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/layer/layer.js"></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	.red{color:red;}
	#nav{
		width:1080px;
		height:700px;
		}
	#nav_top{
		height:40px;
		width:1055px;
		line-height:40px;
		font-family:"微软雅黑";
		font-size:15px;
		color:#666;
		padding-left:25px;
		background-color:#f2f2f2;
		}
	#nav_content{
		height:auto;
		width:1080px;
		margin-top:5px;
		}
	#nav_content table{
		float:left;
		margin-left:20px;
		border-collapse:collapse;
		font-family:"微软雅黑";
		font-size:14px;
		}
	#nav_content table tr{
		height:60px;
		}
	#nav_content table .td1{
		width:100px;
		text-align:right;
		}
	#nav_content input{
		height:25px;
		width:210px;
		font-family:"微软雅黑";
		font-size:14px;
		}
	#nav_content ._submit{
		height:35px;
		width:110px;
		line-height:35px;
		background-color:#c00;
		color:white;
		border:none;
		font-size:16px;
		font-weight:bolder;
		letter-spacing:5px;
		}
	#yzm{
		display:none;
		}
	#nav_content .checkNum{
		height:25px;
		width:120px;
		margin-right:10px;
		}
	table .tip{height:20px;width:200px;padding-left:3px;text-align:left;line-height:20px;margin-left:3px;color:#666;font-size:12px;border:1px solid #3C0;word-wrap:break-word;}
	table .warn{height:20px;width:200px;padding-left:3px;text-align:left;line-height:20px;margin-left:3px;color:red;font-size:12px;border:1px solid #FF0033;}
</style>
</head>

<body>
	<div id="nav">
    	<div id="nav_top">
        	账号信息&nbsp;&nbsp;&nbsp;&nbsp;(创建时间:2015-06-28)
        </div>
        <div id="nav_content">
        	<form id="user_info" >
        		<input type="hidden" id="fid" name="fid" value="${TSysUser.fid}"/>
        		<input type="hidden" id="fname" name="fname" value="${TSysUser.fname}"/>
            	<table cellpadding="0" cellspacing="0" width="582" border="0">
                	<tr>
                    	<td class="td1">用户账号：</td>
                        <td width="232"><p>${TSysUser.fname}</p></td>
                        <td width="250">&nbsp;</td>
                    </tr>
                    <tr>
                    	<td class="td1">客户名称：</td>
                        <td><p>${TSysUser.fcustomername}</p></td>
                    </tr>
                    <tr>
                    	<td class="td1">手机号码<span class="red">*</span>：</td>
                        <td><input id="ftel" name="ftel" type="text" value="${TSysUser.ftel}" /></td>
                        <td id="tipTel"></td>
                    </tr>
                    <tr id="yzm">
                    	<td class="td1">验证码<span class="red">*</span>：</td>
                        <td>
                            <input id="identCode" name="identCode" type="text" maxlength="6" class="checkNum" style="width:70px;"/>
                            <input id="IdentCode" type="button" value="获取验证码" class="IdentCode" style="width:130px;height:30px;" onclick="getYzm()"/>
                         </td>
                         <td id="tipIdentCode"></td>
                    </tr>
                    <tr>
                    	<td class="td1">电话：</td>
                        <td><input id="fphone" name="fphone" type="text" value="${TSysUser.fphone}"  /></td>
                        <td id="tipPhone"></td>
                    </tr>
                    <tr>
                    	<td class="td1">邮箱：</td>
                        <td><input id="femail" name="femail" type="text" value="${TSysUser.femail}"/></td>
                        <td id="tipEmail"></td>
                    </tr>
                    <tr>
                    	<td class="td1">传真：</td>
                        <td><input id="ffax" name="ffax" type="text" value="${TSysUser.ffax}"/></td>
                        <td id="tipFax"></td>
                    </tr>
                    <tr>
                    	<td class="td1">&nbsp;</td>
                        <td><input id="saveButton" type="button" value="保存" class="_submit"/></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
<script type="text/javascript">
	var beginValue=$("#ftel").val();
	var c_time = 60;
	var t_time;
	/*********************校验-提示****************开始******************************/
	$("#femail").focus(function(){
		$("#tipEmail").html("<p class="+'tip'+">请输入正确的邮箱地址</p>");
	});
	$("#femail").focusout(function(){
		$("td .tip").remove();
		if($("#femail").val()!=''){
			var reg=/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
			if(!(reg.test($("#femail").val()))){
			 	$("#tipEmail").html("<p class="+'warn'+">输入的邮箱格式错误！</p>");
			}
		}
	});
	$("#ffax").focus(function(){
		$("#tipFax").html("<p class="+'tip'+">请输入传真</p>");
	});
	$("#ffax").focusout(function(){
		$("td .tip").remove();
		if($("#ffax").val()!=''){
			var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
			if(!(reg.test($("#ffax").val()))){
			 	$("#tipFax").html("<p class="+'warn'+">输入的传真格式错误！</p>");
			}
		}
	});
	$("#fphone").focus(function(){
		$("#tipPhone").html("<p class="+'tip'+">请输入电话</p>");
	});
	$("#fphone").focusout(function(){
		$("td .tip").remove();
		if($("#fphone").val()!=''){
			var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
			if(!(reg.test($("#fphone").val()))){
			 	$("#tipPhone").html("<p class="+'warn'+">输入的电话格式错误！</p>");
			}
		}
	});
	
	$("#identCode").focus(function(){
		$("#tipIdentCode").html("<p class="+'tip'+">请输入6为验证码</p>");
	});
	$("#identCode").focusout(function(){
		$("td .tip").remove();
		var reg =/^\d{6}$/;
		if($("#identCode").val()!=''){
			if(!(reg.test($("#identCode").val()))){
				 	$("#tipIdentCode").html("<p class="+'warn'+">输入的验证码格式错误！</p>");
			}
		}
	});
	
	$("#ftel").focus(function(){
		$("#tipTel").html("<p class="+'tip'+">请输入手机号码</p>");
	});
	$("#ftel").focusout(function(){
		$("td .tip").remove();
		$("#tipIdentCode").html("");
		if($("#ftel").val()!=''){
			var reg=/^1[3|4|5|8][0-9]{9}$/;
			if(!(reg.test($("#ftel").val()))){
			 	$("#tipTel").html("<p class="+'warn'+">输入的手机格式错误！</p>");
			}else{
				var afterValue = $("#ftel").val();
				if(afterValue!=beginValue){
					$.ajax({
							type:"POST",
							url:"${ctx}/user_checkFtel.net",
							async:false,
							data: {"ftel":$("#ftel").val()}, 
							dataType:"text", 
							success:function(response){
								if(response=="fail"){
									  $("#tipTel").html("<p class="+'warn'+">该手机号已经被注册</p>");
									  $("#yzm").hide();
								}else{
									  $("#yzm").show();
								}
							}
					});
				}else{
					 $("#yzm").hide();
				}
			}
		}
	});
	
function timedCount(){
	if(c_time>0){
		c_time--;
		$("#IdentCode").val("请在" + c_time + "秒内输入");
		t_time=setTimeout("timedCount()",1000);
	}else{
		clearTimeout(t_time);
		$("#IdentCode").removeAttr("disabled");
        $("#IdentCode").val("重新发送验证码");
	}
}

function getYzm(){
			c_time =60;
			$("#IdentCode").val("正在发送中");
			$("#IdentCode").attr("disabled", "true");
	    	$.ajax({
					type:"POST",
					url:"${ctx}/user_getMobileCode.net",
					async:false,
					data: {"fname":"${TSysUser.fname}","ftel":$("#ftel").val()}, 
					dataType:"text", 
					success:function(response){
						if(response=="fail"){
							  c_time =60;
				    		  clearTimeout(t_time);
			    		 	  $("#IdentCode").removeAttr("disabled");
		               		  $("#IdentCode").val("重新发送验证码");
						}else{
							  timedCount();
						}
					}
			});
}

function valiCheckInfo(){
	if($("#fphone").val()!=''){
		var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
		if(!(reg.test($("#fphone").val()))){
		 	$("#tipPhone").html("<p class="+'warn'+">输入的电话格式错误！</p>");
		 	return false;
		}
	 }
	 if($("#ffax").val()!=''){
		var reg=/^((\+?[0-9]{2,4}\-[0-9]{3,4}\-)|([0-9]{3,4}\-))?([0-9]{7,8})(\-[0-9]+)?$/;
		if(!(reg.test($("#ffax").val()))){
		 	$("#tipFax").html("<p class="+'warn'+">输入的传真格式错误！</p>");
		 	return false;
		}
	 }
	 if($("#femail").val()!=''){
		var reg=/^[a-zA-Z0-9_\-]{1,}@[a-zA-Z0-9_\-]{1,}\.[a-zA-Z0-9_\-.]{1,}$/;
		if(!(reg.test($("#femail").val()))){
		 	$("#tipEmail").html("<p class="+'warn'+">输入的邮箱格式错误！</p>");
		 	return false;
		}
	 }
	if($("#ftel").val()==''){
		$("#tipTel").html("<p class="+'tip'+">手机号码不能为空</p>");
		return false;
	}else{
		if($("#ftel").val()!=beginValue){
			var ispass;
			$.ajax({
					type:"POST",
					url:"${ctx}/user_checkFtel.net",
					async:false,
					data: {"ftel":$("#ftel").val()}, 
					dataType:"text", 
					success:function(response){
						if(response=="fail"){
							  $("#tipTel").html("<p class="+'warn'+">该手机号已经被注册</p>");
							  ispass= false;
						}else{
							if($("#identCode").val()!=''){
							  		var reg =/^\d{6}$/;
									if(!(reg.test($("#identCode").val()))){
										 $("#tipIdentCode").html("<p class="+'warn'+">输入的验证码格式错误！</p>");
										 ispass= false;
									}else if(c_time <=0){
							    		$("#tipIdentCode").html("<p class="+'warn'+">验证码超时,请重新发送</p>");
							    		$("#identCode").val("");
							    		 clearTimeout(t_time);
						    		 	 $("#IdentCode").removeAttr("disabled");
					               		 $("#IdentCode").val("重新发送验证码");
							    		ispass= false;
							    	}else{
										ispass= true;
									}
							}else{
								$("#tipIdentCode").html("<p class="+'warn'+">请输入验证码！</p>");
							  	 ispass= false;
							}
						}
					}
			});
			return ispass;
		}else{
			return true;
		}
	}
	
}
	
	
$("#saveButton").click(function() { 
	 if(valiCheckInfo()==true){
	 	var options = {  
                   url : "${ctx}/usercenter/saveUserInfo.net",
                   dataType:"json",
                   type : "POST",
                   success : function(msg) {
                       if(msg.success == "success"){
                       	    layer.alert('操作成功！', function(alIndex){
                       	    layer.close(alIndex);
                       	    $('#certificate', parent.document).addClass("selected");
                       	    $('#certificate', parent.document).siblings().removeClass("selected");
                       	    location.href="${ctx}/usercenter/certificate.net";
                       	    });
                       }else{
	                       	$("#tipIdentCode").html("<p class="+'warn'+">输入验证码错误!</p>");
	                       }
                   },
                   error:function(){
	                   	layer.alert('操作失败！', function(alIndex){layer.close(alIndex);});
                   }
        };  
        $("#user_info").ajaxSubmit(options);//绑定页面中form表单的id
	 }
 });

</script>
</body>
</html>
