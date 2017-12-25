<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请认证</title>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	#nav1{
		width:1085px;
		height:320px;
		}
	#sqrz_top{
		height:115px;
		width:1085px;
		background-color:#f1f1f1;
		overflow:hidden;
		}
	#sqrz_mind{
		height:115px;
		width:1085px;
		background-color:#f1f1f1;
		overflow:hidden;
		margin-top:30px;
		}
	.p1,.p2,.p3{
		float:left;
		}
	.p1{
		height:80px;
		width:160px;
		line-height:80px;
		text-align:center;
		margin-top:17px;
		font-family:"微软雅黑";
		font-weight:bolder;
		font-size:20px;
		border-right:1px solid lightgray;
		}
	.p2{
		height:80px;
		width:726px;
		margin-top:17px;
		padding-left:40px;
		}
	.p2 .s1{
		font-family:"微软雅黑";
		font-size:18px;
		line-height:35px;
		}
	.p2 .s2{
		font-family:"微软雅黑";
		font-size:14px;
		color:red;
		line-height:35px;
		}
	.p3{
		display:block;
		width:155px;
		height:115px;
		line-height:115px;
		text-align:center;
		background-color:#60c672;
		text-decoration:none;
		color:white;
		font-family:"微软雅黑";
		font-size:24px;
		border:none;
		cursor:pointer;
		}
	._no{
		background-color:#C5241C;
		}
</style>
<script type="text/javascript">
$(document).ready(function(){
	if("${customer.fisinternalcompany}"==1){
		if("${customer.fbarcode}"==""){
			/*** 身份证为空说明为企业认证*/
			$("#sqrz_top").hide();
			$("#e_rz").attr("class", "p3");//企业认证-已认证
			$("#e_rz").val("已认证"); 
			$("#e_rz").attr('disabled',true);
		}else{
			/*** 身份证不为空说明为个人认证*/
			$("#sqrz_mind").hide();
			$("#p_rz").attr("class", "p3");//个人认证-已认证
			$("#p_rz").val("已认证"); 
			$("#p_rz").attr('disabled',true);
		}
	}else{
		$("#p_rz").attr("class", "p3 _no");//个人认证-未认证
		$("#e_rz").attr("class", "p3 _no");//企业认证-未认证
		if("${customer.ftxregisterno}"!=""){
			$("#e_rz").val("认证中"); 
			$("#e_rz").attr('disabled',true);
			$("#sqrz_top").hide();
		}
	}
});
</script>
</head>
<body>
	<div id="nav1">
    	<div id="sqrz_top">
        	<p class="p1">个人认证</p>
            <p class="p2"><span class="s1">及时开通，无需等待</span><br /><span class="s2">①点击&nbsp;&nbsp;&nbsp;"立即认证"&nbsp;&nbsp;>>>&nbsp;&nbsp;②输入姓名、身份证、上传证件照片&nbsp;>>>&nbsp;&nbsp;③确认信息,认证完成。</span></p>
            <input type="button" id="p_rz" value="立即认证" class="p3" onclick="location.href='${ctx}/usercenter/p_certificate.net'"/>
        </div>
        <div id="sqrz_mind">
        	<p class="p1">公司认证</p>
            <p class="p2"><span class="s1">2小时内审核完成</span><br /><span class="s2">①点击&nbsp;&nbsp;&nbsp;"立即认证"&nbsp;&nbsp;>>>&nbsp;&nbsp;②输入企业名、法人代表、上传营业执照等&nbsp;>>>&nbsp;&nbsp;③确认信息,认证完成。</span></p>
            <input type="button" id="e_rz" value="立即认证" class="p3" onclick="location.href='${ctx}/usercenter/e_certificate.net'"/>
        </div>
    </div>
</body>
</html>