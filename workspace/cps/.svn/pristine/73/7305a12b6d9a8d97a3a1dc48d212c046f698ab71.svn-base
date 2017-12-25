<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/line_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>在线设计-编辑</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/order_products.css" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" src="${ctx}/js/JPlaceHolder.js"></script>
<style>
		*{
	margin:0px auto;
	padding:0px;
	}
	#container_right .lst{
	width:495px;
	height:30px;
	font-size:14px;
	border:1px solid #666;
	}
.tab li {
	height: 50px;
	width: 200px;
	line-height: 50px;
	float: left;
	text-align: center;
	font-family: "微软雅黑";
	font-size: 20px;
	cursor: pointer;
	color: #999;
}
#nav{
	width:100%;
	height:auto;
	}
#container {
	width: 900px;
	margin-top: 20px;
	height: 800px;
}

#container_right {
	width: 900px;
	margin-left: 20px;
	height: auto;
}

.formlist {
	width: auto;
	padding-top: 15px;
}
/*尾部*/
#foot{
	background-color:#363636;
	width:100%;
	text-align:center;
	clear:both;
	}
#container_right .td1 {
	text-align: justify;
	text-align-last: justify;
}
		
		#productForm {
			padding-left: 70px;
		}
		#productForm input[type=text]:focus {
		    border: 1px solid #F19149;
		}
		
		#productForm textarea:focus {
		    border: 1px solid #F19149;
		}
		
		
		#container_right .link{
	/* display:block; */
	width:285px;
	/* float:left; */
	height:30px;
	line-height:30px;
	border:1px solid #666;
	margin:10px 10px 10px 0px; 
	}
	#container_right .am{
	/* display:block; */
	float:left;
	width:65px;
	height:28px;
	border:1px solid #666;
	margin-top:6px;
	line-height:28px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:black;
	margin-left:20px;
	}
#container_right .pm{
	/* display:block; */
	float:left;
	width:65px;
	height:28px;
	border:1px solid #666;
	margin-top:6px;
	line-height:28px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:black;
	margin-left:10px;
	}
	#container_right form table{
	line-height:50px;
	font-size:18px;
	font-family:"微软雅黑";
	color:#555;
	}
	#container_right .for_date{
	display:block;
	float:left;
	height:28px;
	padding-top:2px;
	width:28px;
	border:1px solid #666;
	border-left:none;
	margin-top:6px;
	cursor:progress;
	}
	.for_date img{
	border:none;
	}
	#container_right .date_{
	display:block;
	float:left;
	width:180px;
	height:30px;
	border:1px solid #666;
	border-right:none;
	margin-top:6px;
	line-height:30px;
	}
	#container_right ._btn{
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
	#container_right ._submit{
	cursor:pointer;
	width:180px;
	height:47px;
	font-size:20px;
	color:white;
	background-color:#CC0000;
	border:none;
	font-family:"微软雅黑";
	line-height:47px;
	}
	.formlist .public_st{
	width:705px;
	height:75px;
	padding-left:115px;
	padding-top:25px;
	}
	.formlist  input,.formlist  textarea{
		padding-left:5px;
		font-family:"宋体";
		font-size:14px;
	}
	.formlist span{
		color:#333333;
		font-size:16px;
	}
	#container_right ._color{
	border:2px solid #C00;
	}
	a{text-decoration:none;color:black;font-size:16px;} 
	a:hover {color: red}
	.delfile {
	    margin: 0px 0 0 -10px;
	    position: absolute;
	    background: url(${ctx}/css/images/close-hover.gif) no-repeat scroll 0 0 transparent;
	    width: 15px;
	   /*  top: -10px;
	    right: -7px; */
	    display: none;
	    float:left;
	    height: 16px;
	    overflow: hidden;
	    text-indent: -9999px;
	}
	.flashTip{width:397px;height:97px;border:1px solid #ccc;overflow:hidden;background-color:#eee;position:relative;display:table-cell;text-align:center;vertical-align:middle}.flashp{position:static;+position:relative;top:50%;*top:40%;font-size:12px;margin-left:auto;margin-right:auto}.flasha{color:blue;font-size:12px}
	
</style>
<script type="text/javascript">
var c_time = 60;
var t_time;
$(document).ready(function () {
	if(!WebUploader.Uploader.support()){
		layer.open({
		    type: 1,
		    area: ['400px', '100px'],
		    closeBtn: 0,
		    title: false, //不显示标题
		    content: '<div class="flashTip"><p class="flashp">为了更好体验,请安装/升级Flash,现在<a class="flasha" href="https://get.adobe.com/flashplayer/?loc=cn"  target="_blank">安装</a>?安装后请刷新页面！</p></div>'
		});
	}
	/***********************************上传****************开始*******************************/
	var $list = $("#thelist");
	var fileId = null;
	var uploader = WebUploader.create({
			    swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
			    server: window.getBasePath()+'/productfilenol/uploadImg.net?fid='+"${demand.fid}",  // 文件接收服务端。
			    pick: '#picker',
			    fileSingleSizeLimit: 10485760,
			    auto : true //设置为 true后,不需要手动调用上传,有文件选择即开始上传
    });
    /*** 当文件被加入队列以后触发*/
	uploader.on( 'fileQueued', function( file ) {
	    $list.append( '<div id="' + file.id + '" class="item">'+
	    					'<p class="info">' + file.name + '<a id="' + file.id + '" href="javascript:void(0);"  class="delfile" >删除</a></p>'+
	    					'<p class="state">等待上传...</p>'+
	    			'</div>' );
	});
	/*** 上传过程中触发，携带上传进度*/
	uploader.on( 'uploadProgress', function( file, percentage ) {
    	var $li = $( '#'+file.id ),
        $percent = $li.find('.progress .progress-bar');
	    // 避免重复创建
	    if ( !$percent.length ) {
	        $percent = $('<div class="progress progress-striped active">' +
	          				'<div class="progress-bar" role="progressbar" style="width: 0%"></div>' +
	        			'</div>').appendTo( $li ).find('.progress-bar');
	    }
	    $li.find('p.state').text('上传中');
	    $percent.css( 'width', percentage * 100 + '%' );
	});
	/*** 当文件上传成功时触发*/
	uploader.on( 'uploadSuccess', function( file ,response) {
    	$( '#'+file.id ).find('p.state').text('已上传');
    	$( '#'+file.id ).find('.delfile').attr('id',response._raw);
    	$( '#'+file.id ).attr("class","itemafter");
    	$( '#'+file.id ).find('p').first().attr("class","infoafter");
    	$('.infoafter').mouseover(function(){
	    	$(this).find('a').css('display','inline-block');
	    	var aId = $(this).find('a').attr('id');
	    	$('#'+aId).unbind().click( function () {
	    			$.ajax({
							type : "POST",
							url : "${ctx}/productfilenol/deleteImg.net",
							dataType:"text",
							async:false,
							data: {"fid":aId},  
							success : function(response) {
								if(response =="success"){
										var divId =$("#"+aId).parent("p").parent("div").attr('id');
										//uploader.removeFile(divId,true);
										$("#"+divId).remove();
								}else{
									layer.alert('操作失败！', function(index){layer.close(index);});
								}
							},
							error:function (){
								layer.alert('操作失败！', function(index){layer.close(index);});
							}
					});
	    	});
	    });
	    $('.infoafter').mouseout(function(){
	    	$(this).find('a').css('display','none');
	    });
	    
	});
	
	/*** 不管成功或者失败，文件上传完成时触发*/
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').fadeOut();
	    $( '#'+file.id ).find('.state').fadeOut();
	});
	
	/*** 当validate不通过时，会以派送错误事件的形式通知调用者*/
	uploader.onError = function( type ) {
		if(type=='F_EXCEED_SIZE'){
		    layer.msg("单个文件不允许超过10M！");
		}
	};
		
		
	$('.infoafter').mouseover(function(){
		    	$(this).find('a').css('display','inline-block');
		    	var aId = $(this).find('a').attr('id');
		    	$('#'+aId).unbind().click( function () {
		    			$.ajax({
								type : "POST",
								url : window.getBasePath()+'/productfilenol/deleteImg.net',
								dataType:"text",
								async:false,
								data: {"fid":aId},  
								success : function(response) {
									if(response =="success"){
											var divId =$("#"+aId).parent("p").parent("div").attr('id');
											//uploader.removeFile(divId,true);
											$("#"+divId).remove();
									}else{
										layer.alert('操作失败！', function(index){layer.close(index);});
									}
								},
								error:function (){
									layer.alert('操作失败！', function(index){layer.close(index);});
								}
						});
		    	});
		});
	    $('.infoafter').mouseout(function(){
	    	$(this).find('a').css('display','none');
	    });
	    /***********************************上传****************结束*******************************/
		
		
		if("${isdenglu}" =='true'){
			//$("#fname").attr("disabled","disabled");//再改成disabled  
			 $("#yzm_btn").hide();
			 $("#yzm").hide();
			// $("#flinkman").val("${session.user.fname}");
			 $("#iput_yzm").remove();
			 $("#yzm_btn").remove();
			 $("#tipYzm").remove();
		}
		if($("#fiszhiyang").val()=="true"){
			$("#yes_").addClass("_color");
			$("#yes_").siblings().removeClass("_color");
		}else{
			$("#no_").addClass("_color");
			$("#no_").siblings().removeClass("_color");
		}
		$("#yes_").click(function(){
			$(this).addClass("_color");
			$(this).siblings().removeClass("_color");
			$("#fiszhiyang").val(true);
	    });
		$("#no_").click(function(){
			$(this).addClass("_color");
			$(this).siblings().removeClass("_color");
			$("#fiszhiyang").val(false);
		});
		if('${demand}'){
			if('${demand.fiszhiyang}'=='true'){
				$('#yes_').click();
			}else{
				
				$('#no_').click();
			}
		}
		
		///存草稿
		$("#ccgButton").click(function(){
			$("input[name='isfb']").val("0");
			if(checkForm()==true){
				var options = {  
                    url : "${ctx}/firstproductdemand/_update.net?isfb=2",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									window.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
									localStorage.gotoPage = "#ccg_count";//浏览器缓存 操作完成跳转草稿箱
							});
                        }else{
                        	layer.alert('操作失败！'+msg.success, function(alIndex){
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
            $("#productForm").ajaxSubmit(options);//绑定页面中form表单的id
			}
		});
		/////提交
		$("#tj_Button").click(function(){
			if(checkForm()==true){
				var options = {  
                    url : "${ctx}/firstproductdemand/_update.net?isfb=1",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	layer.alert('操作成功！', function(alIndex){
									layer.close(alIndex);
									window.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";
							});
                        }else{
                        	layer.alert('操作失败！'+msg.success, function(alIndex){
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
            $("#productForm").ajaxSubmit(options);//绑定页面中form表单的id
			}
		});
		
		$("#flinkphone").focusout(function(){
		  	if($("#flinkphone").val()!='' && !"${session.user.fname}"){
				var reg=/^1[3|4|5|8][0-9]{9}$/;
				if(!(reg.test($("#flinkphone").val()))){
					$("#tipPhone").html("输入的手机格式错误！");
				}
			}
	     });
	     
	     $("#iput_yzm").focusout(function(){
	     	if($("#iput_yzm").val()!='' && $("#iput_yzm").val().length==6){
	     		if(c_time <=0){
		    		$("#tipYzm").html("验证码超时,请重新发送");
		    		$("#iput_yzm").val("");
		    		clearTimeout(t_time);
	    		 	$("#IdentCode").removeAttr("disabled");
               		$("#IdentCode").val("重新发送验证码");
				}else{
					$.ajax({
							type:"POST",
							url:"${ctx}/user_lineDes.net",
							async:false,
							data: {"identCode":$("#iput_yzm").val(),"linkphone":$("#flinkphone").val(),"linkman":$("#flinkman").val()}, 
							dataType:"text", 
							success:function(response){
								if(response=="fail"){
									  $("#tipYzm").html("输入验证码错误！");
								}else{
									 $("#iput_yzm").remove();
									 $("#yzm_btn").remove();
									 $("#tipYzm").remove();
								}
							}
					});
				}
	     	}
	     });
	
});
function timedCount(){
	if(c_time>0){
		c_time--;
		$("#yzm_btn").val("请在" + c_time + "秒内输入");
		t_time=setTimeout("timedCount()",1000);
	}else{
		clearTimeout(t_time);
		$("#yzm_btn").removeAttr("disabled");
        $("#yzm_btn").val("重新发送验证码");
	}
}

function checkGetYzm(){
	if($("#flinkman").val()==''){
		layer.tips('联系人不能为空！', '#flinkman', {tips: [1, '#F7874E'],time: 4000});
		return false;
	}else if($("#flinkphone").val()==''){
		layer.tips('手机号码不能为空！', '#flinkphone', {tips: [1, '#F7874E'],time: 4000});
		return false;
	}else if($("#flinkphone").val()!=''){
		var reg=/^1[3|4|5|8][0-9]{9}$/;
		if(!(reg.test($("#flinkphone").val()))){
			$("#tipPhone").html("输入的手机格式错误！");
			return false;
		}else{
			return true;
		}
	}else{
		return true;
	}
}

//获取验证码
function getYzm(){
		if(checkGetYzm()==true){
				$("#tipYzm").html("");
				c_time =60;
				$("#yzm_btn").val("正在发送中");
				$("#yzm_btn").attr("disabled", "true");
		    	$.ajax({
						type:"POST",
						url:"${ctx}/user_getMobileCode.net",
						async:false,
						data: {"fname":$("#flinkphone").val(),"ftel":$("#flinkphone").val()}, 
						dataType:"text", 
						success:function(response){
							if(response=="fail"){
								  c_time =60;
					    		  clearTimeout(t_time);
				    		 	  $("#yzm_btn").removeAttr("disabled");
			               		  $("#yzm_btn").val("重新发送验证码");
							}else{
								  timedCount();
							}
						}
				});
			}
}
//表单提交前的校验
function checkForm(){
		if($("#fname").val()==""){
			layer.tips('需求名称不能为空！', '#fname', {
			    tips: [1, '#F7874E'],
			    time: 4000
			});
		    return false;
	    }else if($("#flinkman").val()==""){
	       layer.tips('联系人不能为空！', '#flinkman', {
			    tips: [1, '#F7874E'],
			    time: 4000
			});
		    return false;
	    }else if($("#flinkphone").val()==""){
	       layer.tips('手机号码不能为空！', '#flinkphone', {
			    tips: [1, '#F7874E'],
			    time: 4000
			});
		    return false;
	    }else if($("#farrivetime").val()==""){
	       layer.tips('交货日期不能为空！', '#farrivetime', {
			    tips: [1, '#F7874E'],
			    time: 4000
			});
		    return false;
	    }else if($("#flinkphone").val()!='' && !"${session.user.fname}"){
			var reg=/^1[3|4|5|8][0-9]{9}$/;
			if(!(reg.test($("#flinkphone").val()))){
				$("#tipPhone").html("输入的手机格式错误！");
				return false;
			}else{
				return true;
			}
		}else{
			return true;
		}
}

jQuery(function(){
    JPlaceHolder.init();    
});

</script>
</head>
<body style="background-color:#F0F0F0;">
	<div style="width:100%;height:260px;"><img alt="" width="100%" height="100%" src="${ctx}/css/images/designOnline.png"></div>
	<div id="nav">
		<div id="container">
			<div id="container_right">
				<div align="left"><a href="${ctx}/index.jsp" target="_self">平台首页</a></div>
				<div class="formlist" style="background-color:#FFFFFF;margin-top:10px;">
					<div align="center">
							<img alt="" src="${ctx}/css/images/hx-l.png" style="float: left; border: none;"> 
							<img alt="" src="${ctx}/css/images/hx-r.png" style="margin-top: 6px; float: right; border: none;"> 
							<span style="font-family: 宋体; font-size: 24px;"><b>需求信息</b></span>
							<div style="font-family: Dotum; font-size: 16px;">Demand	information</div>
					</div>
					<form id="productForm" >
						<input type="hidden" id="fiszhiyang" name="fiszhiyang" value="${demand.fiszhiyang}"/>
						<input type="hidden" id="isfb" name="isfb" value="1"/>
						<input type="hidden" id="fid" name="fid" value="${demand.fid}"/>
						<input type="hidden" id="fisdemandpackage" name="fisdemandpackage" value="0"/>
						<table cellspacing="0" cellpadding="0" width="600px" style="display: block;">
							<tr>
								<td width="110"><b>产品名称</b><span></span><br/>
								<input type="text" class="lst" id="fname"	name="fname"  value="${demand.fname}" /></td>
							</tr>
							<tr>
								<td>
									<span><b>请确认你的联系方式，我们才知道怎么联系你哦</b></span>
									<div><input placeholder="&nbsp;联系人"  type="text" class="link"  id="flinkman" name="flinkman" value="${demand.flinkman}" /></div>
									<div><input placeholder="&nbsp;请输入手机号码"  type="text" class="link" id="flinkphone" name="flinkphone"   value="${demand.flinkphone}"/><span style="font-size:12px; color:red;" id="tipPhone"></span></div>
									<input placeholder="&nbsp;输入手机验证码"  type="text" class="link"  style="width:150px;" id="iput_yzm"	maxlength="6"/>
									<input  id="yzm_btn" style="margin-bottom:10px;width:120px;height:32px;" type="button"  value="获取手机验证码" onclick="getYzm();"/>
									<span style="font-size:12px; color:red;" id="tipYzm"></span>
								</td>
							</tr>
							<tr>
								<td align="middle">
										<span style="float:left;"><b>是否制样：</b></span>
										<a href="javascript:void(0);" class="am "  id="yes_" style="margin-top:10px;" >是</a>
                        				<a href="javascript:void(0);" class="pm " id="no_" style="margin-top:10px;" >否</a>
								</td>
							</tr>
							<tr>
								<td >
									<span style="float:left;"><b>交货日期&nbsp;:</b>&nbsp;</span>
									<input type="text" id="farrivetime" class="date_" name="farrivetime" class="_date" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})" value="${demand.farrivetime}"/>
                                    <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('farrivetime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})" src="${ctx}/css/images/sj.png"/></a>
								</td>
							</tr>
							<tr>
								<td>
									<span>说说你的具体要求：</span>
									<textarea style="width:497px;height:300px;resize: none;" id="fdescription" name="fdescription" >${demand.fdescription }</textarea>
								</td>
							</tr>
							<tr>
								<td>
									<div id="uploader" class="wu-example">
									    <div class="btns">
									    	<span style="font-family: 微软雅黑; font-size: 14px;">&nbsp;单个文件大小不能超过10MB&nbsp;</span>
									        <div id="picker" >添加附件</div>
									    </div>
									    <div id="thelist" class="uploader-list">
									    	<c:forEach var="entry" items="${filelist}" varStatus="status">
										    	<div id="WU_FILE_EIDT_${status.index}" class="itemafter">
														<p class="infoafter">${entry.fname}<a id='${entry.fid}' class="delfile" href="javascript:void(0);" style="display: none;">删除</a></p>
														<p class="state" style="display: none;">已上传</p>
														<div class="progress progress-striped active" style="display: none;">
															<div class="progress-bar" style="width: 100%;" role="progressbar"></div>
														</div>
												</div>
											</c:forEach>
									    </div>
									</div>
								</td>
							</tr>
						</table>
					
						<p class="public_st">
							<c:if test="${demand.fstate=='存草稿' }">
							<input type="button" value="存草稿" class="_submit" id="ccgButton"  />
							</c:if>
							<input type="button" value="提交"   class="_btn"    id="tj_Button"  />
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
   <div id="foot">
       	<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width="1280px" height="150" frameborder="0" id="allfoot"></iframe>
   </div>
</body>
</html>