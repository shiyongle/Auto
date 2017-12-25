<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pc/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>上传</title>
<link href="${ctx}/pages/pc/js/swfUpload/css/default.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/pages/pc/js/swfUpload/swfupload.js"></script>
<script type="text/javascript" src="${ctx}/pages/pc/js/swfUpload/swfupload.queue.js"></script>
<script type="text/javascript" src="${ctx}/pages/pc/js/swfUpload/fileprogress.js"></script>
<script type="text/javascript" src="${ctx}/pages/pc/js/swfUpload/handlers.js"></script>
<style type="text/css">
 
</style>
<script type="text/javascript">
var swfload;
var remark;
var sstype=$("#type").val();
$(function(){
	    $('#type').combobox({
	    	editable:false,
	    	panelMaxHeight:'50px',
	    	onChange:function(newValue,oldValue){
	    		 sstype=newValue;
	    		 swfload.setUploadURL("${ctx}/upload/choosefile.do?type="+sstype);
	     	}
	    });
		swfload = new SWFUpload({
				upload_url: "${ctx}/upload/choosefile.do?type="+sstype+"&&remark="+remark,	//提交路径
				file_post_name: "file",//上传文件的名称
				file_size_limit : "10240",	// 不可超过10MB
				file_types : "*.jpg;*.jpeg;*.gif;*.png;",
				file_upload_limit : "1",//单次上传最多1张
				file_queue_limit : "1",
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
			   //upload_success_handler : uploadSuccess,
				upload_success_handler : function myUploadSuccess(fileObj, server_data) { 
				   
					try
			      {
			       	var progress = new FileProgress(fileObj, this.customSettings.progressTarget);
			       	progress.setComplete();
			       	var fileSize = fileObj.size;
			       	var i = 0;
			       	while(fileSize>1024&&i<2){
			       		fileSize = fileSize/1024;
			       		i++;
			       	}
			       	var sizeDW;
			       	if(i==0){
			       		sizeDW = "B";
			       	}else if(i==1){
			       		sizeDW = "K";
			       	}else if(i==2){
			       		sizeDW = "M";
			       	}
			        { 
						if("faliure"==server_data) {
							progress.setStatus(toDecimal2(fileSize)+sizeDW+"  上传失败");
							$.messager.alert("提示","上传失败,已满5张请先删除一张","info");
						}	
						else{
						progress.setStatus(toDecimal2(fileSize)+sizeDW+"  上传完成");	
						
						}
			        }
					$('#CLUploadTable').datagrid('reload');
					progress.toggleCancel(false);
			       	var filelist = document.getElementById("progressContainer"+fileObj.id);
			       	filelist.style.paddingLeft="50px";
			       	filelist.style.background="#F0F5FF url('${ctx}/pages/pc/js/file-icon/"+fileObj.type.replace('.','')+".png') no-repeat 5px 5px";
			       	parent.refreshImg();//刷新父级div
			       	//删除附件按钮
				 	var deleteObject = document.createElement("a");
					deleteObject.className = "myProgressBt";
					deleteObject.href = "javascript:void(0);";
					deleteObject.appendChild(document.createTextNode("删除"));
					//alert(deleteObject);
					deleteObject.onclick = function(){
						//this.parentNode.parentNode.style.display='none';
						//var progress = new FileProgress(fileObj, this.customSettings.progressTarget);
						progress.setDelete();
						var idObj = document.getElementById("arrId"+fileObj.id);
						var fid =document.getElementById("arrId"+fileObj.id).value;
						$.ajax({
								type : "POST",
								url : "${ctx}/upload/deleteImg.do",
								dataType:"text",
								async:false,
								data: {"fid":fid},  
								success : function(response) {
									if(response =="success"){
										$.messager.alert('操作成功！', function(index){
											idObj.parentNode.removeChild(idObj);
											parent.refreshImg();//刷新父级div
											$('#CLUploadTable').datagrid('reload');
											
										});
									}else{
										$.messager.alert('操作失败！' );
									}
								},
								error:function (){
									$.messager.alert('操作失败！' );
								}
						});
						
					};
					filelist.appendChild(deleteObject);
					//action 返回的附件ID
			       	var idObj = document.createElement("input");
			       	idObj.setAttribute("type","hidden"); 
				 	idObj.setAttribute("name","arrId");
				 	idObj.setAttribute("value",server_data);
				 	idObj.setAttribute("id","arrId"+fileObj.id);
				 	filelist.appendChild(idObj);
					var clearObj = document.createElement("div");
					clearObj.className = "clear";	
					filelist.appendChild(clearObj);
			       } catch (ex) { this.debug(ex); } 
			      },
				upload_complete_handler : function uploadComplete (fileObj){
					 $('#createWindow').window('close');
				},
				// 按钮的处理
				button_image_url : "${ctx}/pages/pc/js/swfUpload/images/XPButtonUploadText_61x22.png",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_disabled : true,
				button_width: 61,
				button_height: 22,
				// Flash Settings
				flash_url : "${ctx}/pages/pc/js/swfUpload/swfupload.swf",
				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},
				// Debug Settings
				debug: false
			});
	    $("#remark").change();
	    $("#remark").blur(function(){
	    	if($.trim($("#remark").val())==""){
	    		swfload.setButtonDisabled(true);
	    	}
        });
	   

});
//制保留2位小数，如：2，会在2后面补上00.即2.00  
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
 
function setVal(){
	if($.trim($("#remark").val())!=""){
		swfload.setButtonDisabled(false);
		remark=$("#remark").val();
	    swfload.setUploadURL("${ctx}/upload/choosefile.do?type="+sstype+"&&remark="+remark);
    }
}

</script>	
<div  id="createWindow"> </div>
<div id="content">
    <form method="post" name="thisform" enctype="multipart/form-data"   >
		<table>
			 <tr>
				<td style="height:25px;line-height:25px;vertical-align:middle;font-style:italic;color:blue;">
					<img src="${ctx}/pages/pc/images/tip.png"  width="18" height="18" style="vertical-align:middle;"/>
					建议像素800X800以上。建议类型&nbsp;&nbsp;jpg/jpeg/gif/png&nbsp;&nbsp;格式！
				</td>
				 
			 </tr>
			  <tr>
			      <td class="m-info-title" style="text-align: left;">请输入需要跳转的链接<span class="red" style="background-color: #FFFFFF; border: #FFFFFF;">*</span>:</td> 
			 </tr>
			 <tr>  
				 <td class="m-info-content"><input id="remark" type="text" name="remark"  class="easyui-validatebox" required="true" missingMessage="跳转的url必须填写"  onchange="setVal()" style="width: 80%; height: 20px"/></td>
			 </tr>
			 
			<tr valign="top">
				<td>
					<div>
						<div style="padding-left: 5px;">
							<span id="spanButtonPlaceholder1" ></span>
							<input id="btnCancel1" type="hidden" value="取消上传" onclick="cancelQueue(swfload);"  style="margin-left: 2px; height: 22px; font-size: 8pt;  " />
							<br />
						</div>
						<div class="fieldset flash" id="fsUploadProgress1" style="margin-top: 20px;">
							<span class="legend">文件上传</span>
						</div>
					</div>
				</td>
			</tr>
			<tr ><span style="color: red;font-size:16px ">App首页管理类型选择单个&nbsp; :&nbsp;  </span> 
			     <select id="type" class="easyui-combobox" name="type" style="width:90px; " panelAlign:"center">   
    				      <option value="1">货主</option> 
    				      <option value="2" >车主</option>   
			     </select>  
			</tr>
			</table>
    </form>
 </div>
</head>
</html>
