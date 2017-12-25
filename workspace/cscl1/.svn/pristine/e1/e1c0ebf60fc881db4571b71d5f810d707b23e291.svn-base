<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>认证</title>
    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/identity_person.css" rel="stylesheet">    
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
    <script src="${ctx}/pages/pcWeb/js/identity_person.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
<%@ include file="/pages/pcWeb/top/top.jsp"%>
	<div class="container">
		
	<!--步骤-->
	<div class="row">
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step1_img">1</div>
			<div class="step_txt_red">注册</div>
			</div>
		</div>
		
		<div class="col-xs-3 bor">
			<div class="red_border"></div>
		</div>
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step2_img">2</div>
			<div class="step_txt_red">认证</div>
			</div>
		</div>
		
		<div class="col-xs-3 bor">
			<div class="gray_border"></div>
		</div>
		
		<div class="col-xs-2">
			<div class="step">
			<div class="step3_img">3</div>
			<div class="step_txt_gray">完成</div>
			</div>
		</div>
	</div>
	<!--步骤-->

	<!--个人用户，企业用户-->
	<div class="gray_bg">
			<div class="row">
				<div class="col-xs-6 text-center red_bg person"><a href="javascript:void(0)">个人用户</a></div>
				<div class="col-xs-6 text-center company" ><a href="${ctx}/pages/pcWeb/register_iden/identity_company.jsp">企业用户</a></div>
			</div>
	</div>	
	
	<div class="main">
		<form id="inden_form"  method="post" name="thisform" enctype="multipart/form-data" >
		<!--企业名称-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText com_img" id="comName" name="fname" placeholder="个人名称">
				<a id="comName_err"  data-placement="bottom" title="名称不能为空"></a>
			</div>
		</div>	
		</div>
		<!--营业执照-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText iden_img" id="iden" name="iden" placeholder="点击图片上传身份证"  onFocus="this.blur()">
			</div>
		</div>
		</div>
		<!--样图-->
		
		<div style="display:none">
    	<div id="filePicker1">选择图片1</div>
    	<div id="filePicker2">选择图片2</div>
    	</div>
		
		<div class="row" style="color: #8C8C8C;">
		<div class="col-xs-12 text-center col-md-3 col-md-offset-3">
			<img id="img1" src="${ctx}/pages/pcWeb/css/images/register_iden/sfz1.png" class="center-block"/>身份证正面照
      		<input type="file" name="fimg01"  id="fileField1"  style="display: none;" accept="image/jpeg,image/png,image/gif" /> 
		</div>
		<div class="col-xs-12 text-center col-md-3">
			<img id="img2" src="${ctx}/pages/pcWeb/css/images/register_iden/sfz2.png" class="center-block"/>身份证反面照
			<input type="file" name="fimg02"  id="fileField2"  style="display: none;" accept="image/jpeg,image/png,image/gif" />
		</div>
		</div>
		
		<!--按钮-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3">
			<a class="btn btn-danger btn_ignore" id="ignore" href="javascript:">跳过</a>
			<a class="btn btn-danger btn_next" id="next" href="javascipt:void(0)">下一步</a>
		</div>	
		</div>
		</form>
	</div>
</div>
<%@ include file="/pages/pcWeb/foot/foot.jsp"%>	
</body>
</html>

<script>
var imgMsg1,imgMsg2;
var fileObj1,fileObj2;
$(function(){
	//跳过认证后的操作
	$("#ignore").click(function(){		
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/upload/pcWebnextSkip.do?type=1",//跳过认证接口			
			dataType:"json",
			success:function(response){
			if(response.success=="true"){
				location.href="${ctx}/pages/pcWeb/register_iden/register_success1.jsp";
			}
			}
		});
		
	})
	
	/*图片提交*/
	$("#img1").click(function(){
		$("#fileField1").click();
		$("#fileField1").change(function(){
			fileObj1 = document.getElementById("fileField1").files[0];
			var fileSize=Number(fileObj1.size/1024/1024).toFixed(2);
			if(fileSize>5){
				layer.msg("上传图片大小不能超过5M");
				return false;
			}
			document.getElementById("img1").src=window.URL.createObjectURL(fileObj1);
		})
	})
	
	$("#img2").click(function(){
		$("#fileField2").click();
		$("#fileField2").change(function(){
			fileObj2 = document.getElementById("fileField2").files[0];
			var fileSize=Number(fileObj2.size/1024/1024).toFixed(2);
			if(fileSize>5){
				layer.msg("上传图片大小不能超过5M");
				return false;
			}
			document.getElementById("img2").src=window.URL.createObjectURL(fileObj2);
		})
	})
	/*表单提交*/
	$("#next").click(function(){
	var comName=$("#comName").val();//用户名
	var sfzUrl1=$("#img1").attr("src").indexOf("cscl/pages/pcWeb/css/images/register_iden/sfz1.png");
	var sfzUrl2=$("#img2").attr("src").indexOf("cscl/pages/pcWeb/css/images/register_iden/sfz2.png"); 
	
	if(comName==""||comName==null){		
		$("#comName").focus();
		$("#comName_err").tooltip('show');
		setTimeout(function(){$("#comName_err").tooltip('hide');},1000);
		return false;
	}
	if(sfzUrl1==1){
		layer.msg('请上传身份证正面');
		return false;
	}
	if(sfzUrl2==1){
		layer.msg('请上传身份证反面');
		return false;
	}
	else{
		layer.load();
		var FileController="${ctx}/pcWeb/upload/uploadImg.do?"+"&fname="+comName+"&type="+"2"; // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		form.append("fileField", fileObj1);
		form.append("fileField", fileObj2);// 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		xhr.onload = function(){
		location.href="/cscl/pages/pcWeb/register_iden/register_success.jsp";
		}
		xhr.send(form);
	}

})
})
</script>