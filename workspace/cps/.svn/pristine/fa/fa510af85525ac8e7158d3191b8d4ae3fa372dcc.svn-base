<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>设计服务商认证</title>
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/_common.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/MD5.js"></script>
<style type="text/css">
	.red{
		color:red;
	 }
	#foot table tr td .tip{
		height:35px;
		width:225px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #00CC00;
	}
	
	#foot table tr td .warn{
		height:35px;
		width:225px;
		padding-left:5px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:red;
		font-size:12px;
		border:1px solid #FF0033;
	}
	
	 *{
	margin:0px auto;
	padding:0px;
	}
#nav{
	margin-top:10px;
	
	width:1025px;
	}
#container{
	width:100%;
	height:70px;
	border-bottom:2px solid #999;
	line-height:70px;
	font-size:26px;
	font-family:Arial, Helvetica, sans-serif;
	}
#container .txt1,#container .txt2{
		width:165px;
		text-align:center;
		float:left;
		min-top:10px;
	}
#container .txt1{
		margin-left:260px;
		color:red;
		border-bottom:3px solid #F00;
	}
#container .txt2{
		color:#999;
		margin-left:60px;
	}
#foot{
	width:1025px;
	height:460px;
	border-collapse:0px;
	white-space-collapse:collapse;
		}
#foot table{
	margin-top:13px;
	}
#foot table tr td{
		height:60px;
		width:320px;
		line-height:35px;
		font-family:Arial, Helvetica, sans-serif;
		color:#666666;
		font-size:18px;
	}
#foot table .n5{
	width:250px;
	height:35px;
	line-height:35px;
	}
#foot table .tdd{
		width:200px;
		text-align:right;
		margin-right:10px;
	}
#foot table .n4{
		height:35px;
		width:172px;
		line-height:35px;
	}
#foot table .img{
		width:110px;
		height:38px;
		text-decoration:none;
		color:black;
		text-align:center;
		font-size:14px;
		margin-left:5px;
	}

#foot table tr td .tip{
		height:35px;
		width:220px;
		text-align:left;
		line-height:35px;
		margin-left:5px;
		color:#666;
		font-size:12px;
		border:1px solid #999;
		word-wrap:break-word;
	}
	a{text-decoration:none;color:blue;font-size:16px;} 
	a:hover {color: red}
	._submit_btn{
		cursor:pointer;
		width:180px;
		height:47px;
		font-size:20px;
		color:white;
		background-color:#CC0000;
		border:1px solid #CC0000;
		font-family:"微软雅黑";
		line-height:45px;
		margin-left:20px;
	}
	
	.uploader-item{
		float: left;
		width: 200px;
		height: 200px;
		line-height: 200px;
		border: 1px solid rgb(201,201,204);
		margin-left: 40px;
		position: relative;
		text-align: center;
		vertical-align: middle;
		color: rgb(45,45,45);
		background-color: rgb(230,230,230);
		
		
		/*非IE的主流浏览器识别的垂直居中的方法*/
        display: table-cell;

        /* 针对IE的Hack */
        *display: block;
	}
	
	.uploader-item img{
		display:block;
		margin:auto; 
	}
	
	.delBtn{
	    margin: 0px 0 0 -10px;
	    position: absolute;
	    background: url(${ctx}/css/images/del.png) no-repeat scroll 0 0 transparent;
	    width: 20px;
	    float: right;
	    height: 20px;
	    overflow: hidden;
	    text-indent: -9999px;
	    top: -5px;
	    right: -5px;
	    display:none;
	}
</style>
<script type="text/javascript">
$(function() {
	var JPlaceHolder = {
		//检测
		_check : function() {
			return 'placeholder' in document.createElement('input');
		},
		//初始化
		init : function() {
			if (!this._check()) {
				this.fix();
			}
		},
		//修复
		fix : function() {
			jQuery(':input[placeholder]').each(
					function(index, element) {
						var self = $(this), txt = self.attr('placeholder');
						self.wrap($('<span></span>').css({
							position : 'relative',
							zoom : '1',
							border : 'none',
							background : 'none',
							padding : 'none',
							margin : 'none'
						}));
						var pos = self.position(), h = self
								.outerHeight(true), paddingleft = self
								.css('padding-left');
						var holder = $('<span></span>').text(txt).css({
							position : 'absolute',
							left : pos.left,
							top : pos.top,
							height : h,
							paddingLeft : paddingleft,
							color : '#aaa'
						}).appendTo(self.parent());
						self.focusin(function(e) {
							holder.hide();
						}).focusout(function(e) {
							if (!self.val()) {
								holder.show();
							}
						});
						holder.click(function(e) {
							holder.hide();
							self.focus();
						});
					});
		}
	};
	//执行
	jQuery(function() {
		JPlaceHolder.init();
	});
});
</script>
</head>
<body style="background-color:#F2F2F2;">
	<div id="nav">
    	<div style="margin-top:40px;padding:20px;">
			<a href="${ctx}/index.jsp" title="返回首页"><img style="vertical-align:middle;border:none;" src="${ctx}/css/images/cps-red.png"/></a>
			<b><span style="font-size:35px; position: relative;top: 22px; ">|欢迎认证!</span></b>
		</div>
        <div id="foot" style="height:550px;border:1px solid  lightgrey;padding:30px;background-color:#ffffff;">
       		<c:choose>
				<c:when test="${designerCertificateInfo.fstatus== '0'}">  
			         正在审核中。。。
			   	</c:when>
			   	<c:when test="${designerCertificateInfo.fstatus== '1'}">  
			         您已经通过认证！
			   	</c:when>
			   	<c:otherwise> 
			     	<s:form id="certificate_form" onsubmit="return false;">
		            	<table cellpadding="0" cellspacing="0"  width="850" border="0">
		            		<tr>
		                        <td style="font-size:26px;" colspan="4">设计服务商认证</td>
		                    </tr>
		                	<tr>
		                    	<td style="width: 100px;"><span class="red">&nbsp;*&nbsp;</span>企业名称</td>
		                        <td><input type="text" class="n5" id="fcompanyname" name="fcompanyname" maxlength="80" value="${designerCertificateInfo.fcompanyname}"/></td>
		                        <td style="width: 100px;"><span class="red">&nbsp;*&nbsp;</span>所属行业</td>
		                        <td><input type="text" class="n5" id="findustry" name="findustry" maxlength="80" value="${designerCertificateInfo.findustry}"/></td>
		                    </tr>
	                     	<tr>
		                    	<td colspan="4" style="width: 100px;">
		                    		<span class="red">&nbsp;*&nbsp;</span>请上传营业执照、税务登记证、组织机构代码证
		                    		<span class="red" style="padding-left: 20px;">如三证合一，上传营业执照即可。</span>
		                    	</td>
		                    </tr>
		                    <tr>
		                    	<td colspan="4">
								    <div id="fileList" class="uploader-list">
								    	<div id="file1" class="uploader-item">点击上传</div>
								    	<div id="file2" class="uploader-item">点击上传</div>
								    	<div id="flie3" class="uploader-item">点击上传</div>
								    </div>
								    <div id="filePicker" style="display: none;">选择图片</div>
		                       	</td>
		                    </tr>
		                    <tr>
		                    	<td colspan="4" style="font-size:14px;">
		                        	<input type="checkbox" name="protocol" id="prot" checked/>我已阅读并接受&nbsp;&nbsp;&nbsp;《版权声明》和《隐私保护》
		                       	</td>
		                    </tr>
		                    <tr>
		                    	<td colspan="4" style="text-align:center;">
		                    		<input type="button" class="_submit_btn" value="立即认证" id="_submit" onclick="submitCertificate()"/>
		                    	</td>
		                    </tr>
				     	</table>
			      	</s:form>
				 </c:otherwise>
			</c:choose>
        </div>
    </div>
</body>

<script type="text/javascript">
	var $divfile = null;
	
	var images = ${images};
	
	for(var index in images){
		loadImage(images[index], index);
	}
	
	function loadImage(fid, index){
		var image=new Image();   
		image.src = "${ctx}/productfile/getFileSource.net?fid=" + fid;
		image.onload=function(){
			$obj=  $("#fileList").find("div:eq("+index+")");
			$obj.empty();
			$obj.append('<input type="hidden" name="'+$obj.attr("id")+'" value="'+fid+'"/>');
			var width = $obj.width();
			var height = $obj.height();
			if(image.width != 0 && image.height != 0){
				var b = image.width / image.height;
				if(b > width / height){
					height = width / b;
				} else {
					width  = b * height;
				}
			}
			//var margin_top = ($obj.height() - height) / 2;
			var margin_left = ($obj.width() - width) / 2;
			$obj.append("<img style='width:"+width+"px;height:"+height+"px;margin-left:"+margin_left+"px;' src="+ image.src +">");
			$obj.append('<a href="javascript:void(0);" class="delBtn" onclick="delImg(this)">删除</a>');
			$obj.unbind().mouseover(function(){showDel(this);}).mouseout(function(){hideDel(this);});
		};
	}
	
	initUplaodItem();
	function initUplaodItem(){
		$(".uploader-item").each(function(){
			if($(this).find("input").length == 0){
				$(this).unbind().click(function(){
					$divfile = $(this);
					$("#filePicker").find("label").trigger('click');
				});
			}
		});
	}
	
	var uploader = WebUploader.create({
	    auto: true,
	    multiple: false,
	    fileSingleSizeLimit: 10485760,
	    swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
	    server: window.getBasePath()+'/productfilenol/uploadImgDesignerCertificateInfo.net?',  // 文件接收服务端。
	    pick: {id: "#filePicker", multiple: false},
	    // 只允许选择图片文件。
	    accept: {
	        title: 'Images',
	        extensions: 'gif,jpg,jpeg,bmp,png',
	        mimeTypes: 'image/*'
	    },
	    thumb: {
	    	crop: false,
	    }
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
		$divfile.attr("fid", file.id);
	}); 
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
		$divfile.html('上传中...');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on('uploadSuccess', function(file,response) {
		$divfile.empty();
		$divfile.append('<input type="hidden" name="'+$divfile.attr("id")+'" value="'+response._raw+'"/>');
		
		/*var width = $divfile.width();
		var height = $divfile.height();
		if(response.width != 0 && response.height != 0){
			var b = response.width / response.height;
			if(b > width / height){
				height = width / b;
			} else {
				width  = b * height;
			}
		}*/
		uploader.makeThumb(file, function(error, src) {
			if (error) {
				$divfile.html('不能预览');
				return;
			}
			$img = $("<img src="+ src +">");
			/*var margin_top = ($divfile.height() - $img.height()) / 2;
			var margin_left = ($divfile.width() - $img.width()) / 2;
			$img.css("margin-top", margin_top + "px");
			$img.css("margin-left", margin_left + "px");*/
			$divfile.append($img);
			$divfile.append('<a href="javascript:void(0);" class="delBtn" onclick="delImg(this)">删除</a>');
		}, 200, 200);

		$divfile.unbind().mouseover(function(){showDel(this);}).mouseout(function(){hideDel(this);});
	});

	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function(file) {
		$divfile.text('上传失败');
	});
	
	/*** 当validate不通过时，会以派送错误事件的形式通知调用者*/
	 uploader.onError = function( type ) {
		  if(type=='F_EXCEED_SIZE'){
		      layer.msg("单个文件不允许超过10M！");
		  }
	 };
	
	//提交认证
	function submitCertificate(){
		if(uploader.isInProgress()){
			layer.msg('文件上传中，请稍等...');
		} else if($("#fname").val() == ''){
			layer.msg('请输入企业名称！');
		} else if($("#findustry").val() == ''){
			layer.msg('请输入所属行业！');
		} else if($("#fileList").find("input[name]").length == 0) {
			layer.msg('请上传营业执照、税务登记证、组织机构代码证');
		} else if(!$("#prot").attr("checked")){
			layer.msg('请接受条款！');
		} else {
			$.ajax({
				type : "POST",
				url : "${ctx}/usercenter/saveDesignerCertificateInfo.net",
				dataType:"text",
				async:false,
				data: $("#certificate_form").serialize(),  
				success : function(response) {
					if(response =="success"){
						layer.alert('提交成功，请耐心等待审核！', 
								{	closeBtn: 0,
									icon: 1,
									btn: ['进入首页'],
									yes:function(index){
										window.location.href="${ctx}/index.jsp";
										layer.close(index);
									}
								});
					}else{
						layer.alert('提交失败！', function(index){layer.close(index);});
					}
				},
				error:function (msg){
					layer.alert(msg, function(index){layer.close(index);});
				}
			});
		}
	};
	
	//显示删除按钮
	function showDel(me){
		if(me){
			$(me).find('.delBtn').show();
		}
	}
	//隐藏删除按钮
	function hideDel(me){
		if(me){
			$(me).find('.delBtn').hide();
		}
	}
	//删除图片
	function delImg(me){
		$.ajax({
			type : "POST",
			url : "${ctx}/productfilenol/deleteImg.net",
			dataType:"text",
			async:false,
			data: {fid: $(me).siblings("input").val()},  
			success : function(response) {
				if(response =="success"){
					$parent = $(me).parent();
					if(typeof($parent.attr("fid")) != "undefined"){
						uploader.removeFile( $parent.attr("fid") );
						$parent.removeAttr("fid");
					}
					
					$parent.empty().html("点击上传");
					setTimeout("initUplaodItem();", 100);
				}else{
					layer.alert('删除失败！', function(index){layer.close(index);});
				}
			},
			error:function (msg){
				layer.alert(msg, function(index){layer.close(index);});
			}
		});
	}
</script>
</html>
