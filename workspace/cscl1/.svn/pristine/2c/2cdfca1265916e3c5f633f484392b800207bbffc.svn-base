<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  <meta charset="utf-8">
	<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
	<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
    <title>个人用户--企业认证</title>
    <!-- Bootstrap -->
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
    <link href="${ctx}/pages/pcWeb/css/user_identity_company.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
    <script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
    <script src="${ctx}/pages/pcWeb/js/user_identity_company.js"></script>
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
<body>
	<div class="jumbotron">
	<div class="main">
	<form id="inden_form"  method="post" name="thisform" enctype="multipart/form-data" >
		<!--企业名称-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText com_img" id="comName" placeholder="企业名称">
				<a id="comName_err"  data-placement="bottom" title="名称不能为空"></a>
			</div>
		</div>	
		</div>
		<!--营业执照-->
		<div class="row">
		<div class="col-xs-12 col-md-6 col-md-offset-3 ">
			<div class="form-group form_warp">
				<input type="text" class="form-control form-commomText iden_img" id="iden" placeholder="上传营业执照、组织机构代码"  onFocus="this.blur()">
			</div>
		</div>
		</div>
		<!--样图-->
		<div class="row" style="color: #8C8C8C;">
		<div class="col-xs-12 text-center col-md-3 col-md-offset-3">
			<img src="${ctx}/pages/pcWeb/css/images/register_iden/licenceCode.png" id="img1" class="img-responsive center-block"/>营业执照
			<input type="file" name="fimg01"  id="fileField1"  style="display: none;" accept="image/jpeg,image/png,image/gif" />
		</div>
		<div class="col-xs-12 text-center col-md-3">
			<img src="${ctx}/pages/pcWeb/css/images/register_iden/organizationCode.png" id="img2" class="img-responsive center-block"/>组织机构代码
			<input type="file" name="fimg02"  id="fileField2"  style="display: none;" accept="image/jpeg,image/png,image/gif" />
		</div>
		</div>
		<!--说明-->
		<div class="row">
			<div class="col-xs-12 redClolr col-md-4 col-md-offset-3">
				<p>*如五证合一，上传营业执照即可。</p>
				<p>*非五证合一，请上传营业执照和组织机构代码。</p>
			</div>
		</div>
		<!--按钮-->
		<div class="row">
		<div class="col-xs-4 col-xs-offset-4">
			<a class="btn btn-danger btn_next" id="finish" href="${ctx}/pages/pcWeb/user_center/identity_success.jsp">完成</a>
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

	/*完成*/
	$("#finish").click(function(){
		var comName=$("#comName").val();//姓名
		var sfzUrl1=$("#img1").attr("src").indexOf("cscl/pages/pcWeb/css/images/register_iden/licenceCode.png");
		var sfzUrl2=$("#img2").attr("src").indexOf("cscl/pages/pcWeb/css/images/register_iden/organizationCode.png"); 
		if(comName==""||comName==null){		
			$("#comName").focus();
			$("#comName_err").tooltip('show');
			setTimeout(function(){$("#comName_err").tooltip('hide');},1000);
			return false;
		}
		if(sfzUrl1==1){
			layer.msg('请上传营业执照');
			return false;
		}
		if(sfzUrl2==1){
			layer.msg('请上传组织机构代码');
			return false;
		}
		else{
			layer.load();
			var FileController="/cscl/pcWeb/upload/uploadImg.do?"+"&fname="+comName+"&type="+"1"; // 接收上传文件的后台地址 
			// FormData 对象
			var form = new FormData();
			form.append("fileField", fileObj1);
			form.append("fileField", fileObj2);	
			// XMLHttpRequest 对象
			var xhr = new XMLHttpRequest();
			xhr.open("post", FileController, true);
			xhr.onload = function(){
				location.href="${ctx}/pages/pcWeb/register_iden/register_success.jsp";
			}
			xhr.send(form);
		}
	})
	
});
</script>
</body>
</html>