<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人中心-修改密码</title>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<style>
*{margin:0px auto;padding:0px;border-collapse:collapse;}.red{color:red;}#nav{width:1085px;height:700px;}#nav_top{height:40px;width:1060px;line-height:40px;text-align:left;background-color:#f2f2f2;font-family:"微软雅黑";font-size:15px;color:#666;padding-left:25px;margin-bottom:10px;}.td1{width:135px;height:60px;text-align:right;font-family:"微软雅黑";font-size:15px;}
	form table input[type=password]{width:300px;height:30px;border:1px solid lightgray;}
	._submit{
		width:110px;
		height:35px;
		border:none;
		background-color:#C00;
		color:white;
		font-family:"微软雅黑";
		font-size:18px;
		letter-spacing:5px;
		cursor:pointer;
	}
	table .tip{height:35px;width:260px;padding-left:5px;text-align:left;line-height:35px;margin-left:5px;color:#666;font-size:12px;border:1px solid #3C0;word-wrap:break-word;}table .warn{height:35px;width:255px;padding-left:5px;text-align:left;line-height:35px;margin-left:5px;color:red;font-size:12px;border:1px solid #FF0033;}
	input{padding-left:5px;}  
	input[type=text]{
		padding-left:5px;
		outline:none;
		border:1px solid lightgray;
	}
	#nav{
		width:100%;
		}
	#container{
		width:1080px;
		height:780px;
		}
	#container .p1{
		font-size:12px;
		line-height:60px;
		height:60px;
		width:1080px;
		}
	#container .tblpwd{
		width:1080px;
		height:715px;
		background-color:white;
		}
	.tblpwd .img_left{
		display:inline-block;
		float:left;
		margin-left:94px;
		+margin-left:61px;
		*+margin-left:61px;
		}
	.tblpwd .img_right{
		display:inline-block;
		float:left;
		}
	.tblpwd .title_add{
		display:inline-block;
		float:left;
		width:210px;
		text-align:center;
		font-family:"微软雅黑";
		font-size:24px;
		text-decoration:none;
		color:black;
		cursor:default;
		}
	#head{
		height:112px;
		}
	#foot{
		height:150px;
		}
	 table .tip{float:left;height:20px;width:200px;text-align:left;line-height:20px;color:#666;font-size:12px;border:1px solid #3C0;word-wrap:break-word;}
	table .warn{float:left;height:20px;width:200px;text-align:left;line-height:20px;color:red;font-size:12px;border:1px solid #FF0033;} 
</style>
</head>
<body>
	<div id="nav">
    	<div id="container">
    		<p class="p1">平台首页&nbsp;&nbsp;&gt;&nbsp;&nbsp;用户中心&nbsp;&nbsp;&gt;&nbsp;&nbsp;修改密码</p>
    		<div class="tblpwd">
			        <form id="setpwd">
			        	<input type="hidden" id="fid" name="fid" value="${TSysUser.fid}"/>
			        	<table cellpadding="0" cellspacing="0" border="0" width="1080" class="tbl_form" style="border-collapse:collapse;">
			        	 <tr height="124">
		                        	<td colspan="3">
		                            	<img src="${ctx}/css/images/hx-l.png" class="img_left"/>
		                                <a href="javascript:void(0);" class="title_add">修&nbsp;改&nbsp;密&nbsp;码&nbsp;</a>
		                                <img src="${ctx}/css/images/hx-r.png" class="img_right"/>
		                            </td>
		                        </tr>
			            	<tr>
			                	<td height="60" width="415px" align="right"><span class="red">*</span>&nbsp;原&nbsp;密&nbsp;码：</td>
			                    <td><input type="password" id="old_pwd" name="foldpassword"/></td>
			                    <td id="tipOldPwd">&nbsp;</td>
			                </tr>
			                <tr>
			                	<td height="60" width="415px" align="right"><span class="red">*</span>&nbsp;新&nbsp;密&nbsp;码：</td>
			                    <td><input type="password" id="new_pwd" name ="fpassword" /></td>
			                    <td id="tipNewPwd">&nbsp;</td>
			                </tr>
			                <tr>
			                	<td height="60" width="415px" align="right" ><span class="red">*</span>&nbsp;确认新密码：</td>
			                    <td><input type="password" id="new_pwd_again" /></td>
			                    <td id="tipNewPwdAgain">&nbsp;</td>
			                </tr>
			                <tr height="100">
			                	<!-- <td>&nbsp;</td> -->
			                    <td  colspan="3" align="center"><input id="saveButton" type="button" value="确认" class="_submit"/></td>
			                    <!-- <td>&nbsp;</td> -->
			                </tr>
			            </table>
			        </form>
		        </div>
	        </div>
    </div>
<script language="javascript">
		$(function(){
			window.getHtmlBodyHeight();			
		});
		$("#new_pwd").focus(function(){
			$("#tipNewPwd").html("<p class="+'tip'+">6-80位字符,建议由字母,数字和符号组合</p>");
		});
		$("#new_pwd").focusout(function(){
			$("td .tip").remove();
		});
		
		$("#old_pwd").focus(function(){
			$("#tipOldPwd").html("<p class="+'tip'+">请输入原始密码</p>");
		});
		$("#old_pwd").focusout(function(){
			$("td .tip").remove();
		});
		
		$("#new_pwd_again").focus(function(){
			$("#tipNewPwdAgain").html("<p class="+'tip'+">再次输入设置的新密码</p>");
		});
		$("#new_pwd_again").focusout(function(){
			$("td .tip").remove();
			var pas1,pas2;
			pas1=document.getElementById("new_pwd").value;
			pas2=document.getElementById("new_pwd_again").value;
			if(pas2==""){
				$("tipNewPwdAgain").html("<p class="+'warn'+">请再次输入密码</p>");
			}else if(!(pas1==pas2)){
				$("#tipNewPwdAgain").html("<p class="+'warn'+">两次输入密码不一致</p>");
			}
		});
		function valiCheck(){
			 var isval = true;
			 if($("#old_pwd").val()==''){
			 	$("#tipOldPwd").html("<p class="+'warn'+">请输入原始密码</p>");
			 	isval =false;
			 }
			 
			 if($("#new_pwd").val()==''){
			 	$("#tipNewPwd").html("<p class="+'warn'+">请输入新密码</p>");
			 	isval =false;
			 }
			 
			 if($("#new_pwd_again").val()==''){
			 	$("#tipNewPwdAgain").html("<p class="+'warn'+">请再次输入新密码</p>");
			 	isval =false;
			 }
			 var pas1,pas2;
			 pas1=document.getElementById("new_pwd").value;
			 pas2=document.getElementById("new_pwd_again").value;
			 if(!(pas1==pas2)){
				$("#tipNewPwdAgain").html("<p class="+'warn'+">两次输入密码不一致</p>");
				isval =false;
			 }
			 return isval;
		}
$("#saveButton").click(function() { 
	 if(valiCheck()==true){
	 	var options = {  
                   url : "${ctx}/usercenter/setPwd.net",
                   dataType:"json",
                   type : "POST",
                   success : function(msg) {
                       if(msg.success == "success"){
                    	   parent.layer.alert('操作成功！', function(alIndex){
                    		   parent.layer.close(alIndex);
                    		   parent.location.href="${ctx}/login.jsp";
						});
                       }else{
                       	$("#tipOldPwd").html("<p class="+'warn'+">原密码错误！</p>");
                       }
                   },
                   error:function(){
                	   parent.layer.alert('操作失败！', function(alIndex){
                		   parent.layer.close(alIndex);
					});
                   }
           };  
           $("#setpwd").ajaxSubmit(options);//绑定页面中form表单的id */
	 }
 });
</script>
</body>
</html>
