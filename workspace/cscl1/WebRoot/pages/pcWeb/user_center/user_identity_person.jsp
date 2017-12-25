<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>个人用户--个人认证</title>
    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/user_identity_person.css" rel="stylesheet">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
    <script src="${ctx}/pages/pcWeb/js/user_identity_person.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
	<div class="jumbotron">

	<div class="main">
		<form id="inden_form">
		<!--企业名称-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText com_img" id="comName" name="comName" placeholder="个人名称">
				<a id="comName_err"  data-placement="bottom" title="名称不能为空"></a>
			</div>
		</div>	
		</div>
		<!--营业执照-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText iden_img" id="iden" name="iden" placeholder="点击下方图片上传身份证"  onFocus="this.blur()">
			</div>
		</div>
		</div>
		<!--样图-->
		<div class="row" style="color: #8C8C8C;">
		<div class="col-xs-12 text-center col-md-3 col-md-offset-3">
			<img id="img1" src="${ctx}/pages/pcWeb/css/images/register_iden/sfz1.png" class="img-responsive center-block"/>身份证正面照
      <input type="file" name="fileField1"  id="fileField1"  style="display: none;" accept="image/jpeg,image/png,image/gif" /> 
		</div>
		<div class="col-xs-12 text-center col-md-3">
			<img id="img2" src="${ctx}/pages/pcWeb/css/images/register_iden/sfz2.png" class="img-responsive center-block"/>身份证反面照
			<input type="file" name="fileField2"  id="fileField2"  style="display: none;" accept="image/jpeg,image/png,image/gif" />
		</div>
		</div>
		<!--按钮-->
		<div class="row">
		<div class="col-xs-4 col-xs-offset-4">
			<a class="btn btn-danger btn_next" id="next" href="${ctx}/pages/pcWeb/user_center/identity_success.jsp">完成</a>
		</div>	
		</div>
		</form>
	</div>
</div>
<script>
//var imgMsg1,imgMsg2;
var fileObj1,fileObj2;
$(function(){	
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
	
	/*下一步*/
	$("#next").click(function(){
		var comName=$("#comName").val();//用户名
		if(comName==""||comName==null){		
			$("#comName").focus();
			$("#comName_err").tooltip('show')
			$("#comName").change(function(){
			$("#comName_err").tooltip('hide')
			})
			return false;
		}
		var FileController="/cscl/pcWeb/upload/uploadImg.do?"+"&fname="+comName+"&type="+"1"; // 接收上传文件的后台地址 
		// FormData 对象
		var form = new FormData();
		form.append("fileField", fileObj1);
		form.append("fileField", fileObj2);// 文件对象
		// XMLHttpRequest 对象
		var xhr = new XMLHttpRequest();
		xhr.open("post", FileController, true);
		xhr.onload = function(){
			location.href=" ";
		}
		xhr.send(form);
	})
	
	
})

//图片上传预览功能
//fileField:上传组件的ID
//img:显示的图片
// function setImagePreviews(fileField,img){
// 	var _file = document.getElementById(fileField);
// 	var _img=document.getElementById(img);
//     var fileList = _file.files[0];      	
//     _img.src = window.URL.createObjectURL(fileList);
//     return fileList;
// }  
</script>
</body>
</html>