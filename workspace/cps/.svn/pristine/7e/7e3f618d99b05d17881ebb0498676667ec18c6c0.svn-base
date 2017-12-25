<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请认证-企业认证</title>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<style>
	*{
		margin:0px auto;
		padding:0px;
		}
	.red{
		color:red;
	 }
	#nav{
		width:1150px;
		height:auto;
		}
	#nav_top{
		width:1123px;
		height:40px;
		line-height:40px;
		background-color:#f2f2f2;
		font-family:"微软雅黑";
		font-size:14px;
		color:#aaaaaa;
		text-align:left;
		padding-left:22px;
		}
	#nav_content{
		width:1100x;
		height:585px;
		padding-left:45px;
		}
	#nav_content table{
		float:left;
		font-family:"微软雅黑";
		font-size:14px;
		border-collapse:collapse;
		}
	#nav_content table input{
		width:290px;
		height:35px;
		border:1px solid lightgray;
		font-size:16px;
		}
	#nav_content table p{
		border:1px solid lightgray;
		width:320px;
		height:35px;
		line-height:35px;
		text-align:left;
		padding-left:10px;
		}
	#nav_content .td1,#nav_content .td2{
		text-align:right;
		width:100px;
		line-height:60px;
		}
	#nav_content ._submit{
		width:110px;
		height:40px;
		background-color:#C00;
		border:none;
		color:white;
		font-family:"微软雅黑";
		cursor:pointer;
		}
	/*上传按钮的样式美化*/
	#nav_content .front{
		position:relative;
		display: inline-block;
		background: #D0EEFF;
		border: 1px solid #99D3F5;
		border-radius:4px;
		overflow: hidden;
		color: #1E88C7;
		text-decoration:none;
		text-indent: 0;
		line-height:40px;
		width:288px;
		text-align:center;
		height:160px;
		cursor:pointer;
		}
	.front input {
		position:absolute;
		font-size: 100px;
		right: 0;
		top: 0;
		opacity: 0;
	}
	#nav_content table a:hover {
		background: #AADFFD;
		border-color: #78C3F3;
		color: #004974;
		text-decoration: none;
	}
	#nav_content .tip{
		height:35px;
		width:280px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #3C0;
		word-wrap:break-word;
	}
	#nav_content .warn{
		height:35px;
		width:285px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:red;
		font-size:12px;
		border:1px solid #FF0033;
	}
	
	/*上传成功之后显示的img样式*/
	#nav_content .img1{
		width:290px;
		height:25px;
		margin-right:5px;
		font-family:"微软雅黑";
		font-size:14px;
		line-height:25px;
		cursor:pointer;
		margin-top:5px;
		}
</style>
<script type="text/javascript">
	$(document).ready(function () {
	  $("#photo_div").hide();
	});
</script>
</head>

<body>
	<div id="nav">
    	<div id="nav_top">企业认证</div>
        <div id="nav_content">
        	<form id="e_rzform">
        		<input type="hidden" id="id" name="fid" value="${customer.fid}"/>
            	<table cellpadding="0" cellspacing="0" border="0" width="755">
                	<tr height="60">
                    	<td class="td1">企业名称<span class="red">*</span>：</td>
                        <td width="300"><input type="text" class="n5" id="fname" name="fname"  onblur="nameBlur();"  onfocus="nameFocus();"/></td>
                        <td id="tipFname"></td>
                    </tr>
                    <tr>
                    	<td class="td1">法人代表<span class="red">*</span>：</td>
                        <td><input type="text" id="fartificialperson" name="fartificialperson"  onblur="personBlur();"  onfocus="personFocus();"/></td>
                        <td id="tipFartificialperson"></td>
                    </tr>
                    <tr height="60">
                    	<td class="td1">法人联系号码：</td>
                        <td><input type="text" id="fartificialpersonphone" name="fartificialpersonphone" /></td>
                    </tr>
                    <tr height="60">
                    	<td class="td1">注册号<span class="red">*</span>：</td>
                        <td><input type="text" id="ftxregisterno" name="ftxregisterno" onblur="ftxregisternoBlur();"  onfocus="ftxregisternoFocus();"/></td>
                        <td id="tipFtxregisterno"></td>
                    </tr>
                    <tr>
                    	<td class="td1">营业执照<span class="red">*</span>：</td>
                        <td id="content">
                        	<div id="photo_div">
	                        	<img id="ph_img" src="" width="291" height="145"/>
	                            <input type="button" value="撤销" class="img1" onclick="revoke()">
                            </div>
                            <a id="ph_a" href="javascript:void(0)" onclick="click_ui();" class="front" title="点击上传图片">
                            	<img  src="${ctx}/css/images/DJJS.png" style="margin-top:50px;"/>
                            	<input type="file" id="uploadPhoto" name ="fileupload" onchange="uploadPhotos();"/>
                            </a>
                        </td>
                        <td id="tipPhoto"></td>
                    </tr>
                    <tr height="90">
                    	<td>&nbsp;</td>
                        <td>
                        	<input id="tj_erz" type="button" value="提交认证" class="_submit"/>&nbsp;&nbsp;
                        	<input type="button" value="返回" class="_submit" onclick="javascript :history.back(-1);"/></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
<script type="text/javascript">
	/***提示***********************************************************开始*******************/
	function nameFocus(){
	 	$("#tipFname").html("<p class="+'tip'+">请填写工商局注册的公司全称</p>");
	}
	 function nameBlur(){
	 	 if($("#fname").val()!=""){
			 var re = /^[\u4e00-\u9fa5]+$/;
		     if (re.test($("#fname").val())) {
		     	$("#tipFname").html("");
		     } else {
		       $("#tipFname").html("<p class="+'warn'+">请输入工商局注册的公司全称</p>");
		     }
	 	 }
	 }
	function personFocus(){
	 	 $("#tipFartificialperson").html("<p class="+'tip'+">请填写公司的法人代表</p>");
	}
	function personBlur(){
		if($("#fartificialperson").val()!=""){
			 var re = /^[\u4e00-\u9fa5]+$/;
		     if (re.test($("#fartificialperson").val())) {
		     	$("#tipFartificialperson").html("");
		     } else {
		       $("#tipFartificialperson").html("<p class="+'warn'+">请输入公司法人代表</p>");
		     }
	 	 }
	}	
	function ftxregisternoFocus(){
	 	 $("#tipFtxregisterno").html("<p class="+'tip'+">营业执照、组织机构代码、税务登记证、三选其一</p>");
	} 
	function ftxregisternoBlur(){
		 if($("#ftxregisterno").val()!=""){
		    $("#tipFtxregisterno").html("");
	 	 }
	}
	
	/***提示***********************************************************结束*******************/
	function click_ui(){
		document.getElementById('uploadPhoto').click();
	}
	function uploadPhotos(){
	  var imageOne1 = $("#uploadPhoto").val();
	 	 if(imageOne1.length!=0){
	 	  	var reg = ".*\\.(jpg|png|gif|JPG|PNG|GIF)";
     		var r = imageOne1.match(reg);
     		if(r == null){
    			layer.msg('仅支持jpg.png.gif,请重新选择上传文件');
     		}else{
     			if(window.ActiveXObject) {
     				var image=new Image();
      				image.dynsrc=imageOne1;
		    		   if(image.fileSize>10*1024*1024){
		    		   	  layer.msg('文件大小不能超过10M,请重新选择上传文件');
		    		      return false;
		    		   }
    	        }else{  
		    		   var size = document.getElementById("uploadPhoto").files[0].size;
		    		   if(size>10*1024*1024) {
		    		  		layer.msg('文件大小不能超过10M,请重新选择上传文件');
    						return false;
    		   			}
    			}
    			var fid ="${customer.fid}";
				$.ajaxFileUpload({
					url : "${ctx}/productfile/uploadPhoto.net?fid="+fid,
					secureuri:false,
					fileElementId:'uploadPhoto',
					type:'POST',
					dataType: 'json',
					success: function (response){
						if(response.success){
							layer.alert('操作成功！', function(index){
								layer.close(index);
								$("#ph_a").hide();
								$("#photo_div").show();
								$("#ph_img").attr("src",response.pathUrl);
								$("#tipPhoto").html("");
							});
						}else{
							layer.alert('操作失败！', function(index){layer.close(index);});
						}
					},
					error:function (){
						layer.alert('操作失败！', function(index){layer.close(index);});
					}
				});
     		}
	 	 }else{
	 	  	layer.msg('请选择上传文件');
	 	 }
	}
	
	 //身份证正面撤销
	 function revoke(){
	 	var url =$("#ph_img").attr("src");
	 	var fid =url.substring(url.lastIndexOf("/")+1,url.lastIndexOf("."));
	 	$.ajax({
				type : "POST",
				url : "${ctx}/productfile/deleteImg.net",
				dataType:"text",
				async:false,
				data: {"fid":fid},  
				success : function(response) {
					if(response =="success"){
						layer.alert('操作成功！', function(index){
							layer.close(index);
							$("#photo_div").hide();
							$("#ph_a").show();
							}
						);
					}else{
						layer.alert('操作失败！', function(index){layer.close(index);});
					}
				},
				error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
		});
	 }
	 
	 function valiCheckui(){
	    var isVal =true;
	     if($("#fname").val()==''){
    		 $("#tipFname").html("<p class="+'warn'+">企业名称不能为空！</p>");
    		 isVal =false;
    	 }
    	 if($("#fartificialperson").val()==''){
    		 $("#tipFartificialperson").html("<p class="+'warn'+">法人代表不能为空！</p>");
    		 isVal =false;
    	 }
    	 if($("#ftxregisterno").val()==''){
    		 $("#tipFtxregisterno").html("<p class="+'warn'+">注册号不能为空！</p>");
    		 isVal =false;
    	 }
    	 if($("#ph_img").attr("src")==""){
    	 	$("#tipPhoto").html("<p class="+'warn'+">请上传营业执照！</p>");
		     isVal =false;
    	 }
    	 return isVal;
	 }
	 
	 $("#tj_erz").click(function() { 
		 if(valiCheckui()==true){
		 	var options = {  
                    url : "${ctx}/customer/companyUpdate.net",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									location.href="${ctx}/usercenter/certificate.net";
							});
                        }else{
                        	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
							});
                        }
                    },
                    error:function(){
                    	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
						});
                    }
            };  
            $("#e_rzform").ajaxSubmit(options);//绑定页面中form表单的id
		 }
	  });
</script>
</body>
</html>
