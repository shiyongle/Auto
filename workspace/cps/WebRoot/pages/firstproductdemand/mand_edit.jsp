<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线下单</title>
<script type="text/javascript" language="javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/orderOnline.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/js/webuploader-0.1.5/webuploader.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/webuploader-0.1.5/webuploader.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.selectlist.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<style>
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
</style>
<script type="text/javascript">
function getFujian(){
		$.ajax({
				type : "POST",
				url : "${ctx}/productfile/loadByParentId.net?id="+"${firstproductdemand.fid }",
				dataType:"json",
				success : function(response) {
						$.each(response.data, function(i, ev) {
							 var toolBar =ev.fid;
							 var html =['<div id="WU_FILE_EIDT_"+i class="itemafter">',
											'<p class="infoafter">',ev.fname,'<a id="'+ev.fid+'" class="delfile" href="javascript:void(0);" style="display: none;">删除</a></p>',
											'<p class="state" style="display: none;">已上传</p>',
											'<div class="progress progress-striped active" style="display: none;">',
												'<div class="progress-bar" style="width: 100%;" role="progressbar"></div>',
											'</div>',
										'</div>'].join("");
							 $(html).appendTo("#thelist");
						 }); 
						 window.getHtmlLoadingAfterHeight();
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
				}
	    });
}


$(function(){
// 	window.getHtmlLoadingBeforeHeight();
	window.getHtmlBodyHeight();
	if(!WebUploader.Uploader.support()){
		parent.layer.open({
		    type: 1,
		    area: ['400px', '100px'],
		    closeBtn: 0,
		    title: false, //不显示标题
		    content: '<div style="width:397px;height:97px;border:1px solid #ccc;overflow:hidden;background-color:#eee;position:relative;display:table-cell;text-align:center;vertical-align:middle;"><p style="position:static;+position:relative;top:50%;*top:40%;font-size:12px;margin-left:auto;margin-right:auto;">为了更好体验,请安装/升级Flash,现在<a style="color:blue;font-size:12px;" href="https://get.adobe.com/flashplayer/?loc=cn"  target="_blank">安装</a>?安装后请刷新页面！</p></div>'
		});
	}
	/***********************************上传****************开始*******************************/
	var $list = $("#thelist");
	var fileId = null;
	var uploader = WebUploader.create({
			    swf: window.getBasePath()+'/js/webuploader-0.1.5/Uploader.swf', // swf文件路径
			    server: window.getBasePath()+'/productfilenol/uploadImg.net?fid='+"${firstproductdemand.fid}",  // 文件接收服务端。
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

	/***********************************上传****************结束*******************************/
	
	if("${firstproductdemand.fsupplierid}"!=""){
		//2015-11-04 IE 8 不支持 options[id..]
		//document.getElementById("supplier2").options["${firstproductdemand.fsupplierid}"].selected="selected";
		$("#supplier2 option[value='${firstproductdemand.fsupplierid}']").attr('selected','selected');
	}
	window.getFujian();
	var selectLength=$("#supplier2 option").size();
	$("#supplier2").selectlist({
		zIndex: selectLength,
		width: 390,
		height: 30
	});
	
	///存草稿
	$("#pack_ccg").click(function(){
			if(checkForm()==true){
				var options = {  
                    url : "${ctx}/firstproductdemand/update.net?isfb=2",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	parent.layer.alert('操作成功！', function(alIndex){
									/* window.history.back(); */
									parent.layer.close(alIndex);
							});
                        }else{
                        	parent.layer.alert('操作失败！', function(alIndex){
									parent.layer.close(alIndex);
							});
                        }
                        parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
                    	localStorage.gotoPage = "#tab_list :nth-child(4)";//需求包记录
                    },
                    error:function(){
                    	parent.layer.alert('操作失败！', function(alIndex){
									parent.layer.close(alIndex);
						});
                    	parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
                    	localStorage.gotoPage = "#tab_list :nth-child(4)";//需求包记录
                    }
              };  
              $("#mandForm").ajaxSubmit(options);//绑定页面中form表单的id
			}
		});
		/////提交
		$("#pack_fb").click(function(){
			if(checkForm()==true){
				var options = {  
                    url : "${ctx}/firstproductdemand/update.net?isfb=1",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	parent.layer.alert('操作成功！', function(alIndex){
									/* window.history.back(); */
									parent.layer.close(alIndex);
							});
                        }else{
                        	parent.layer.alert('操作失败！'+msg.success, function(alIndex){
									parent.layer.close(alIndex);
							});
                        }
                        parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
                    	localStorage.gotoPage = "#tab_list :nth-child(4)";//需求包记录
                    },
                    error:function(){
                    	parent.layer.alert('操作失败！', function(alIndex){
									parent.layer.close(alIndex);
						});
                    	parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
                    	localStorage.gotoPage = "#tab_list :nth-child(4)";//需求包记录
                    }
            };  
            $("#mandForm").ajaxSubmit(options);//绑定页面中form表单的id
			}
		});
});
//表单提交前的校验
function checkForm(){
		if($("#fname").val()==""){
			layer.tips('需求名称不能为空！', '#fname', {
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
	    }else{
			return true;
		}
}	

/********************************************************************************/
var swfload;
$(function(){
	swfload = new SWFUpload({
			upload_url: "${ctx}/productfilenol/uploadImg.net?fid="+"${firstproductdemand.fid}",	//提交路径
			file_post_name: "fileupload",//上传文件的名称
			file_size_limit : "10240",	// 不可超过10MB  */
			/* file_types : "*.jpg;*.jpeg;*.gif;*.png;", */
			file_upload_limit : "0",//单次上传最多5张
			file_queue_limit : "5",
			file_dialog_start_handler : fileDialogStart,
			file_queued_handler : fileQueued,
			file_queue_error_handler : fileQueueError,
			file_dialog_complete_handler : fileDialogComplete,
			upload_start_handler : uploadStart,
			upload_progress_handler : uploadProgress,
			upload_error_handler : uploadError,
		  //upload_success_handler : uploadSuccess,
			upload_success_handler : function myUploadSuccess(fileObj, server_data) { 
				/* var div = document.createElement('div');
				var span = document.createElement('span');
				var a2 = document.createElement('a'); */
				var div = $('<div/>');
				var span = $('<span/>');
				var a2 = $('<a/>');
				a2.attr('href','javascript:void(0);');
			    a2.attr("onclick",'delFile(\''+server_data+'\');');
				a2.text("删除");
				div.attr('id',server_data);
				div.attr('style','float:left;width:500px;height:35px;line-height:26px;overflow:hidden;white-space:nowrap;margin-right:20px;position:relative;');
				span.attr('style','float:left;display:block;border-radius:10px;border:1px solid #a1a1a1;color:#ffffff;background-color:#ffb155;');
				a2.attr('class','delfile');
				span.attr('class','showfile');
				span.text(fileObj.name);
				div.append(span);
				div.append(a2);
				$('#fileName').append(div);
				$('#filelist').children().remove();
				 $('.showfile').mouseover(function(){
					 $(this).next().show();
				 }).mouseout(function(){
					 $('.delfile').mouseover(function(){
						 $(this).show();
					 }).mouseout(function(){
						 $(this).hide();
					 });
					 $(this).next().hide();
				 });
		      },
			upload_complete_handler : uploadComplete,
			// 按钮的处理
			button_image_url :"${ctx}/css/images/button-4_03.png",//"${ctx}/js/swfUpload/images/XPButtonUploadText_61x22.png",
			button_placeholder_id : "ltc",
			button_width: 70,
			button_height: '19',
			button_text_top_padding : 130,
			// Flash Settings
			flash_url : "${ctx}/js/swfUpload/swfupload.swf",
			custom_settings : {
				/* progressTarget : "filelist", */
				cancelButtonId : "btnCancel1"
			},
			// Debug Settings
			debug: false,
			auto_upload:false
		});

});
 function uploadError(fileObject,errorCode,message){
	$('#filelist').children().remove();
	parent.layer.msg("上传失败，请检查文件格式！");
}
function fileQueueError(fileObject,errorCode,message){
	if(fileObject!=undefined){
		parent.layer.msg("文件超过10MB！");
		return false;
	}else{
		parent.layer.msg("最多只能上传5个文件！");
		return false;
	}
} 
function uploadStart(file){
	if($('#fileName').children().length>=5){
		swfload.cancelUpload(file.id,false);//取消上传的文件
		parent.layer.msg("最多只能上传5个文件！");
		return false;
	}
/* 	var div = document.createElement('div');
	var span1 = document.createElement('span');
	var p = document.createElement('p');
	var span2 = document.createElement('span');
	var span3 = document.createElement('span');
	var a = document.createElement('a'); */
	var div = $('<div/>');
	var span1 = $('<span/>');
	var p = $('<p/>');
	var span2 = $('<span/>');
	var span3 = $('<span/>');
	var a = $('<a/>');

	p.attr('class','adifile');
	div.attr('align','left');
	span1.attr('style','float:left;overflow:hidden;');
	span2.attr('id',file.id);
	span3.attr('id',file.id+"_1");
	span3.attr('style','float:left;');
	span1.text(file.name);
	a.attr('href','javascript:void(0);');
	a.attr('style','margin-left:10px;');
	a.attr('onclick',"$('#btnCancel1').click()");
	a.text("取消");
	p.append(span2);
	div.append(span1);
	div.append(p);
	div.append(span3);
	div.append(a);
	$('#filelist').append(div);
	
}
function uploadProgress(file, complete,bytes){
	/* $('#'+file.id).text("进度:"+complete*100/bytes+"%"); */
	var num = complete/bytes;
	num = num.toFixed(2);
	$('#'+file.id).attr('style',"width:"+eval(num*100)+"%;float:left");
	$('#'+file.id+"_1").text('上传'+eval(num*100)+"%");
}
//删除附件
function delFile(obj){
	$.ajax({
			type : "POST",
			url : "${ctx}/productfilenol/deleteImg.net",
			dataType:"text",
			async:false,
			data: {"fid":obj},  
			success : function(response) {
				if(response =="success"){
						$("#"+obj).remove();
				}else{
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
			},
			error:function (){
				layer.alert('操作失败！', function(index){layer.close(index);});
			}
	});
}
function toDecimal2(x) {  
    var f = parseFloat(x);  
    if (isNaN(f)) {  
        return false;  
    }  
    var f = Math.round(x*100)/100;  
    var s = f.toString();  
    var rs = s.indexOf('.');  
    if (rs < 0) {  
        rs = s.length;  
        s += '.';  
    }  
    while (s.length <= rs + 2) {  
        s += '0';  
    }  
    return s;  
} 

</script>
</head>
	<body>
		 <div>
	        <form id="mandForm" >
	        	<input type="hidden" id="fisdemandpackage" name="fisdemandpackage" value="1"/>
	        	<input type="hidden" id="fid" name="fid" value="${firstproductdemand.fid}"/>
		        <table id="tbl2" cellpadding="0" cellspacing="0" border="0" width="1045" style="float:left;margin-left:20px;width:1045px;">
		        	<tr height="84">
		            	<td colspan="3"><span style="font-size:20px;">订单需求信息</span></td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">需求名称:<span style="color:red;">*</span></td>
		                <td width="430"><input type="text" class="requireName" id="fname" name="fname" value="${firstproductdemand.fname}"/></td>
		                <td width="515">&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">采购单号</td>
		                <td><input type="text" class="orderNum" id="fpurordernumber" name="fpurordernumber" value="${firstproductdemand.fpurordernumber}"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">交货日期:<span style="color:red;">*</span></td>
		                <td>
		                	<input type="text" id="farrivetime"  name="farrivetime" class="_date" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})" value="${firstproductdemand.farrivetime}"/>
                            <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('farrivetime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${newday}'})" src="${ctx}/css/images/sj.png"/></a>
		                </td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">联系人</td>
		                <td><input type="text" style="width:200px;height:30px;line-height:30px;" id="flinkman" name="flinkman" value="${firstproductdemand.flinkman}"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">联系电话</td>
		                <td><input type="text" style="width:200px;height:30px;line-height:30px;" id="flinkphone" name="flinkphone" value="${firstproductdemand.flinkphone}"/></td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td width="100" height="58">供应商:<span style="color:red;">*</span></td>
		                <td>
		                	<select id="supplier2" name="fsupplierid">
		                    	<c:forEach var="entry" items="${supplier}">
									<option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
								</c:forEach>
		                   	</select>
		                </td>
		                <td>&nbsp;</td>
		            </tr>
		            <tr>
		            	<td colspan="3" height="58">说说您的具体要求:</td>
		            </tr>
		            <tr>
		            	<td colspan="3"><textarea class="demand" id="fdescription" name="fdescription" maxlength="2000">${firstproductdemand.fdescription}</textarea></td>
		            </tr>
		            <!--此行是上传插件-->
		            <tr height="100">
		                   <td colspan="3">
									<div id="uploader" class="wu-example">
									    <div class="btns">
									    	<span style="font-family: 微软雅黑; font-size: 14px;">&nbsp;单个文件大小不能超过10MB&nbsp;</span>
									        <div id="picker" >添加附件</div>
									    </div>
									    <div id="thelist" class="uploader-list"></div>
									</div>
							</td>
		            </tr>
		            <tr><td id="fileName" colspan="3"></td></tr>
					<tr><td colspan="3"><span id="filelist"></span></td></tr>
		            <tr height="100">
		            	<td colspan="3">
		            		<c:if test="${firstproductdemand.fstate=='存草稿' }">
		                	<input type="button" value="存草稿" class="tbl2_ccg"  id="pack_ccg"/>
		                	</c:if>
		                	<input type="button" value="提交" class="tbl2_submit" id="pack_fb"/>
		                </td>
		            </tr>
		        </table>
	        </form>
        </div>
	</body>
</html>