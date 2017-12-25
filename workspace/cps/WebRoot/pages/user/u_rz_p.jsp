<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>申请认证-个人认证</title>
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
		width:1085px;
		height:auto;
		}
	#nav_top{
		width:1063px;
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
		width:1085px;
		height:380px;
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
	
	#nav_content .td1,#nav_content .td2{
		text-align:right;
		width:95px;
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
		background: #ccc;
		border: 1px solid #ccc;
		border-radius:4px;
		overflow: hidden;
		color: #1E88C7;
		text-decoration:none;
		text-indent: 0;
		line-height:110px;
		width:142px;
		text-align:center;
		height:110px;
		cursor:pointer;
		white-space: 0;
		word-spacing: 0;
		font-size:50px;
		color:#eee;
		}
	.front input {
		position:absolute;
		font-size: 100px;
		right: 0;
		opacity:0;
	}
	#nav_content table a:hover {
		background: #AADFFD;
		border-color: #78C3F3;
		color: #004974;
		text-decoration: none;
	}
	/*上传成功之后显示的img样式*/
	#nav_content .img1{
		width:138px;
		height:25px;
		margin-right:5px;
		font-family:"微软雅黑";
		font-size:14px;
		line-height:25px;
		cursor:pointer;
		}
	#nav_content .tip{
		height:35px;
		width:260px;
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
		width:255px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:red;
		font-size:12px;
		border:1px solid #FF0033;
	}
	#content .p1,#content .p2{
		width:138px;
	}
	#content .p1{
		float:left;
	}
	#content .p2{
		float:right;
		margin-right:15px;
	}
</style>
<script type="text/javascript">
	$(document).ready(function () {
		$("#zm_div").hide();
		$("#fm_div").hide();
	});
</script>
</head>

<body>
	<div id="nav">
    	<div id="nav_top">个人认证</div>
        <div id="nav_content">
        	<form id="p_rzform" >
        		<input type="hidden" id="id" name="fid" value="${customer.fid}"/>
            	<table cellpadding="0" cellspacing="0" width="725" border="0">
                	<tr height="60">
                    	<td class="td1">姓名<span class="red">*</span>：</td>
                        <td width="300"><input type="text" class="n5" id="fname" name="fname" maxlength="20" onblur="nameBlur();"  onfocus="nameFocus();"/></td>
                        <td id="tipFname"></td>
                    </tr>
                	<tr height="60">
                    	<td class="td1">身份证号码<span class="red">*</span>：</td>
                        <td><input type="text" class="n5" id="fbarcode" name="fbarcode"  maxlength="18" onblur="fbarcodeBlur();"  onfocus="fbarcodeFocus();"  /></td>
                        <td id="tipFbarcode"></td>
                    </tr>
                    <tr>
                    	<td class="td1">身份证照片<span class="red">*</span>：</td>
                        <td id="content">
                            <div id="zm_div" class="p1" style="margin-right:5px;">
                            	<img id="zm_p" src="" width="138" height="85"/><br />
                            	<input type="button" value="撤销" class="img1" onclick="revoke_zm()">
                            </div>
                            <a id="zm_photo" href="javascript:void(0);" onclick="dl_1click();"  class="front" title="身份证正面">
                            	<img src="${ctx}/css/images/DJJSZ.png" style="margin-top:30px;"/>
                            	<input type="file" id="fl" name ="fileupload" onchange="uploadImage_1();"/>
                            </a>
                            <div id="fm_div" class="p2">
                            	<img id="fm_p" src="" width="138" height="85"/><br />
                            	<input type="button" value="撤销" class="img1" onclick="revoke_fm()">
                            </div>
                            <a id="fm_photo" href="javascript:void(0);" onclick="dl_2click();" class="front" title="身份证反面">
                            	<img src="${ctx}/css/images/DJJSF.png" style="margin-top:30px;"/>
                            	<input type="file" id="fl2" name ="fileupload" onchange="uploadImage_2();"/>
                            </a>
                        </td>
                        <td id="tipPhoto"></td>
                    </tr>
                    <tr height="90">
                    	<td class="td1">&nbsp;</td>
                        <td style="text-align:center;"><input id="tj_rz" type="button" value="提交认证" class="_submit"/>&nbsp;<input type="button" value="返回" class="_submit"  onclick="javascript :history.back(-1);"/></td>
                        <td>&nbsp;</td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
<script type="text/javascript">
	function dl_1click(){
		document.getElementById('fl').click();
	}
	function dl_2click(){
		document.getElementById('fl2').click();
	}
	/**********************************校验-开始******************************************/
	//姓名
	function nameFocus(){
	 		$("#tipFname").html("<p class="+'tip'+">请输入正确的姓名！</p>");
	}
	//姓名校验
	 function nameBlur(){
	 	 if($("#fname").val()!=""){
			 var re = /^[\u4e00-\u9fa5]+$/;
		     if (re.test($("#fname").val())) {
		     	$("#tipFname").html("");
		     } else {
		       $("#tipFname").html("<p class="+'warn'+">请输入合法的持证人姓名！</p>");
		     }
	 	 }
	 }
	 //身份证
	function fbarcodeFocus(){
	 		$("#tipFbarcode").html("<p class="+'tip'+">请输入有效的身份证！</p>");
	}
	//身份证校验
	 function fbarcodeBlur(){
	 	 if($("#fbarcode").val()!=""){
			 var re = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i;
		     if (re.test($("#fbarcode").val())) {
		     	$("#tipFbarcode").html("");
		     } else {
		       $("#tipFbarcode").html("<p class="+'warn'+">请输入合法的持证人身份证！</p>");
		     }
	 	 }
	 }
	 /**********************************校验-结束******************************************/
	 //身份证正面上传
	 function uploadImage_1(){
	 	 var imageOne1 = $("#fl").val();
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
		    		   var size = document.getElementById("fl").files[0].size;
		    		   if(size>10*1024*1024) {
		    		  		layer.msg('文件大小不能超过10M,请重新选择上传文件');
    						return false;
    		   			}
    			}
    			var fid ="${customer.fid}";
				$.ajaxFileUpload({
					url : "${ctx}/productfile/uploadPhoto.net?fid="+fid,
					secureuri:false,
					fileElementId:'fl',
					type:'POST',
					dataType: 'json',
					success: function (response){
						if(response.success){
							layer.alert('操作成功！', function(index){
								layer.close(index);
								$("#zm_photo").hide();
								$("#zm_div").show();
								$("#zm_div img").attr("src",response.pathUrl);
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
	 //身份证反面上传
	 function uploadImage_2(){
	 	 var imageOne2 = $("#fl2").val();
	 	 if(imageOne2.length!=0){
	 	  	var reg = ".*\\.(jpg|png|gif|JPG|PNG|GIF)";
     		var r = imageOne2.match(reg);
     		if(r == null){
    			layer.msg('仅支持jpg.png.gif,请重新选择上传文件');
     		}else{
     			if(window.ActiveXObject) {
     				var image=new Image();
      				image.dynsrc=imageOne2;
		    		   if(image.fileSize>10*1024*1024){
		    		   	  layer.msg('文件大小不能超过10M,请重新选择上传文件');
		    		      return false;
		    		   }
    	        }else{  
		    		   var size = document.getElementById("fl2").files[0].size;
		    		   if(size>10*1024*1024) {
		    		  		layer.msg('文件大小不能超过10M,请重新选择上传文件');
    						return false;
    		   			}
    			}
    			var fid ="${customer.fid}";
				$.ajaxFileUpload({
					url : "${ctx}/productfile/uploadPhoto.net?fid="+fid,
					secureuri:false,
					fileElementId:'fl2',
					type:'POST',
					dataType: 'json',
					success: function (response){
						if(response.success){
							layer.alert('操作成功！', function(index){
								layer.close(index);
								$("#fm_photo").hide();
								$("#fm_div").show();
								$("#fm_div img").attr("src",response.pathUrl);
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
	 function revoke_zm(){
	 	var url =$("#zm_p").attr("src");
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
							$("#zm_div").hide();
							$("#zm_photo").show();
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
	  //身份证反面撤销
	 function revoke_fm(){
	 	var url =$("#fm_p").attr("src");
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
							$("#fm_div").hide();
							$("#fm_photo").show();
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
	 //提交前的校验
	 function valiCheck(){
	 	 var isval = true;
	 	 if($("#fname").val()==''){
    		 $("#tipFname").html("<p class="+'warn'+">姓名不能为空！</p>");
    		 isval =false;
    	 }else{
    	 	 var re = /^[\u4e00-\u9fa5]+$/;
		     if (re.test($("#fname").val())) {
		     	$("#tipFname").html("");
		     } else {
		       $("#tipFname").html("<p class="+'warn'+">请输入合法的持证人姓名！</p>");
		       isval =false;
		     }
    	 }
    	 if($("#fbarcode").val()==''){
    		 $("#tipFbarcode").html("<p class="+'warn'+">身份证不能为空！</p>");
    		 isval =false;
    	 }else{
    	 	 var re = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i;
		     if (re.test($("#fbarcode").val())) {
		     	$("#tipFbarcode").html("");
		     } else {
		       $("#tipFbarcode").html("<p class="+'warn'+">请输入合法的持证人身份证！</p>");
		        isval =false;
		     }
    	 }
    	 if($("#zm_p").attr("src")==""){
    	 	$("#tipPhoto").html("<p class="+'warn'+">请上传证件照！</p>");
		     isval =false;
    	 }
    	 if($("#fm_p").attr("src")==""){
    	 	$("#tipPhoto").html("<p class="+'warn'+">请上传证件照！</p>");
		     isval =false;
    	 }
    	 return isval;
	 }
	 $("#tj_rz").click(function() { 
		 if(valiCheck()==true){
		 	var options = {  
                    url : "${ctx}/customer/update.net",
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
            $("#p_rzform").ajaxSubmit(options);//绑定页面中form表单的id
		 }
	  });
	 
</script>
</body>
</html>
