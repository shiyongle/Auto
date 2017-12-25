<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户中心-基础资料-手机修改</title>
<script src="${ctx }/js/_common.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	#allcontent{
		width:400px;
		height:275px;
/* 		border:1px solid black; */
		}
	#allcontent table{
		border-collapse:collapse;
		font-size:12px;
		}
	#allcontent ._submit{
		width:100px;
		height:40px;
		background-color:#C00;
		border:none;
		color:white;
		font-size:14px;
		letter-spacing:5px;
		margin-left:100px;
		+margin-left:50px;
		*+margin-left:50px;
		cursor:pointer;
		}
	#allcontent input[type=text]{
		width:140px;
		height:20px;
		}
	#allcontent .tip{
		color:#999;
		}
	#allcontent .warn{
		color:#F90;
		}
	#allcontent .logo,#allcontent ._text{
		display:inline-block;
		float:left;
		}
	#allcontent .logo{
		margin-left:10px;
		+margin-left:5px;
		*+margin-left:5px;
		}
	#allcontent ._text{
		width:80px;
		text-align:center;
		}
	#allcontent .yzm{
		border:none;
		background-color:#C00;
		color:white;
		margin-left:10px;
		+margin-left:5px;
		*+margin-left:5px;
		cursor:pointer;
		}
	table .warn{float:left;height:20px;text-align:left;line-height:20px;color:red;font-size:12px;border: #FF0033;}		
</style>
</head>
<script type="text/javascript">
$(document).ready(function(){
	 $("#tipTel").html("");
	 $("#tipYzm").html("");		 
	 $("#ftel").focus(function(){
		 $("#tipTel").html("");
		 $("#tipYzm").html("");		 
	});
	 $("#yzm").focus(function(){
		 $("#tipYzm").html("");		 
	 });	 
	 
	 $("#updateTelBtn").click(function() { 
		 var index = parent.layer.getFrameIndex(window.name);
		 if(valiCheck()==true){
		 	var options = {  
	                  url : "${ctx}/usercenter/setTel.net",
	                  dataType:"json",
	                  type : "POST",
	                  success : function(msg) {
	                      if(msg.success == "success"){
	                   	   layer.alert('操作成功！', function(alIndex){
	                   			parent.document.getElementById("ftel").value =  $("#ftel").val();
		                   		layer.close(alIndex);
								parent.layer.close(index);
							});
	                      }
	                  },
	                  error:function(){
	                	  layer.alert('操作失败！', function(alIndex){
	                		  layer.close(alIndex);
						});
	                  }
	          };  
	          $("#settel").ajaxSubmit(options);//绑定页面中form表单的id */
		 }
	});
});
var InterValObj=null; //timer变量，控制时间
var count = 900;
function countDown(){
	if (count == 0) {
			$("#tipTel").html("<p class="+'warn'+">验证码已经过时，请重新发送。</p>");
          window.clearInterval(InterValObj);//停止计时器
          $("#IdentCode").removeAttr("disabled");//启用按钮
          $("#IdentCode").val("重新发送验证码");
          count=900;
          return;
     }
else {
          count--;
          $("#IdentCode").val("请在" + count + "秒内输入");
          $("#IdentCode").attr("disabled", "true");
     }
}

var flag = true;
function getYzm(){
	flag = true;
	var oldTel = ${TSysUser.ftel};
	if($("#ftel").val()!=''){
		var newTel = $("#ftel").val();
		if(oldTel ==newTel ){
			flag = false;
			$("#tipTel").html("<p class="+'warn'+">手机号相同，不需重新修改</p>");
		}
		else{
			ftelblur();
			var reg=/^1[3|4|5|8][0-9]{9}$/;
			if(!(reg.test($("#ftel").val()))){
				flag = false;
			 	$("#tipTel").html("<p class="+'warn'+">输入的手机格式错误！</p>");			 	
			}
		}
	}
	else{
		flag = false;
		$("#tipTel").html("<span class='warn'><img src='${ctx}/css/images/cha.png'/>手机号码不能为空</span>");
	}
	//2015-11-09 发送验证码方法
	if(flag){	
		count = 900;
		 $("#IdentCode").val("请稍候......");
		$("#IdentCode").attr("disabled", "true");
		$.ajax({
			type:"POST",
			url:"${ctx}/user_getMobileCode.net",
			async:true,
			data: {"fname":"${TSysUser.fname}","ftel":$("#ftel").val()}, 
			dataType:"text", 
			success:function(response){
				if(response=="success"){
					//如果手机号没有被注册过并且合法,那么就倒计时开始发送验证码
					$("#tipYzm").html("<span class='tip'><img src='${ctx}/css/images/gou.png'/>校验码发送成功，请注意查收</span>");
					InterValObj=window.setInterval(function(){countDown();},1000); //启动计时器，1秒执行一次
				}else{
					  $("#IdentCode").removeAttr("disabled");//启用按钮
			          $("#IdentCode").val("获取验证码");
				}
			}
		});
	}
	}

function ftelblur(){
	 var re = /^1[3|4|5|8][0-9]{9}$/;
	  if (re.test($("#ftel").val())) {
		  $("#tipTel").html("<img src='${ctx}/css/images/xg.png'/>");
		 	$.ajax({
				type:"POST",
				url:"${ctx}/user_isUnique.net",
				async:false,  
				data: {"fname":$("#ftel").val()},  
				success:function(response){
					if(response=="success"){
						flag = false;
						 $("#tipTel").html("<p class="+'warn'+">该手机号已经被注册</p>");
					}else{
						$("#tipTel").html("<img src='${ctx}/css/images/xg.png'/>");
					}
				}
	   	});
	  } else {
			flag = false;
	    	 $("#tipTel").html("<p class="+'warn'+">手机号码格式错误</p>");
	  }
}

function valiCheck(){
	var ischecked = true;
	if($("#ftel").val()==""){
		$("#tipTel").html("<p class="+'warn'+">请输入手机号!</p>");
		ischecked = false;
	}
	if($("#yzm").val()==""){
		$("#tipYzm").html("<p class="+'warn'+">请输入验证码!</p>");
		ischecked = false;
	}
	var reg=/^[0-9]{6}$/;
	if(!(reg.test($("#yzm").val()))){
		$("#tipYzm").html("<p class="+'warn'+">验证码输入有误</p>");
		ischecked = false;
	}
	if(ischecked){
		$.ajax({
			type:"POST",
			url:"${ctx}/user_valiCode.net",
			async:false,
			dataType:"text", 
			data: {"fname":"${TSysUser.fname}","identCode":$("#yzm").val()},  
			success:function(response){
				if(response=="fail"){
					 $("#tipYzm").html("<p class="+'warn'+">验证码输入错误</p>");				 
					 ischecked = false;
				}else{
					if(!$("#yzm").val()==""){
					 $("#tipYzm").html("<img src='${ctx}/css/images/xg.png'/>");
					 }
				}
			}
	  });	
	}
	return ischecked;
}

</script>
<body>
	<div id="allcontent">
    	<form id="settel">
    	<table cellpadding="0" cellspacing="0" border="0" width="400">
        	<tr height="70">
            	<td colspan="2"><img src="${ctx}/css/images/SHOUJI.png" class="logo"/><label class="_text">手机更改</label></td>
            </tr>
            <tr height="40">
            	<td width="110" align="right">新号码：</td>
                <td width="300"><input type="text" id="ftel" name="ftel"  onblur="ftelblur();" /><input type="button"  id="IdentCode" value="获取验证码" class="yzm"  onclick="getYzm()" /></td>
            </tr>
            <tr height="25">
            	<td>&nbsp;</td>
                <td id="tipTel"><span class="warn"><img src="${ctx}/css/images/cha.png"/>手机号码不能为空</span></td>
            </tr>
            <tr height="40">
            	<td align="right">验证码：</td>
                <td><input type="text" id="yzm"/></td>
            </tr>
            <tr height="25">
            	<td>&nbsp;</td>
                <td id="tipYzm"><span class="tip"><img src="${ctx}/css/images/gou.png"/>校验码发送成功，请注意查收</span></td>
            </tr>
            <tr height="75">
            	<td colspan="2">
                	<input type="button" id = "updateTelBtn" value="修改" class="_submit"/>
                </td>
            </tr>
            <tr>
            <td><input type="hidden" id="fid" name="fid" value="${TSysUser.fid}"/></td>
            </tr>
        </table>
        </form>
    </div>
</body>
</html>
