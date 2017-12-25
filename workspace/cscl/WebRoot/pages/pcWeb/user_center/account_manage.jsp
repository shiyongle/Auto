<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>账号管理--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/account_manage.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
</head>
<body>
	<div class="jumbotron main">
		<!--我的订单数据列表-->
		<div class="table-responsive myTable">
		<table class="table  table-hover">
			
			<tr>
				<td>头像</td>
				<td>
				<img id="img1" src="${ctx}/pages/pcWeb/css/images/user_center/head_img.png"/>
				<input type="file" name="fimg01"  id="fileField1"  style="display: none;" accept="image/jpeg,image/png,image/gif" />
				</td>
				<td><a href="javscript:void(0)" id="uploadImg">上传头像</a></td>
			</tr>
			
			<tr>
				<td>用户名</td>
				<td>${ftel}</td>
				<td></td>
			</tr>
			
			<tr>
				<td>手机</td>
				<td id="accountTel"></td>
				<td></td>
			</tr>
			
			<tr>
				<td>登录密码</td>
				<td>登录一路好运时需要的密码</td>
				<td><a href="${ctx}/pages/pcWeb/pass_word/new_password.jsp">重置</a></td>
			</tr>
			
<!-- 			<tr> -->
<%-- 				<td><a href="${ctx}/pages/pcWeb/user_center/childAccount_add.jsp">添加子账号</a></td> --%>
<%-- 				<td><a href="${ctx}/pages/pcWeb/user_center/childAccount_manage.jsp">子账号管理</a></td> --%>
<!-- 				<td></td> -->
<!-- 			</tr> -->
		</table>
		</div>
	</div>
</body>
</html>
<script>
$(function(){
	var tel="${username}";
		tel=tel.substr(0,3)+"****"+tel.substr(-4,4);
		$("#accountTel").text(tel);
	
	$("#uploadImg").click(function(){
		$("#fileField1").click();
		$("#fileField1").change(function(){
			fileObj1 = document.getElementById("fileField1").files[0];
			document.getElementById("img1").src=window.URL.createObjectURL(fileObj1);
			var FileController="/cscl/pcWeb/upload/uploadImg.do?"; // 接收上传文件的后台地址 
			// FormData 对象
			var form = new FormData();
			form.append("fileField", fileObj1);
// 			form.append("fileField", fileObj2);// 文件对象
			// XMLHttpRequest 对象
			var xhr = new XMLHttpRequest();
			xhr.open("post", FileController, true);
			xhr.onload = function(){
		
			}
			xhr.send(form);
		})
	})
})

//图片上传预览功能
//fileField:上传组件的ID
//img:显示的图片
    function setImagePreviews(fileField,img){
        var _file = document.getElementById(fileField);
        var _img=document.getElementById(img);
        var fileList = _file.files[0];      	
        _img.src = window.URL.createObjectURL(fileList);
        return fileList;
    }  
</script>
