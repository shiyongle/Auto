<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>文件上传</title>
    <script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
    <script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
	<style>
		.m-info-title{
			text-align:right;
			height:30px;
			font-weight:400;
			padding-right:10px;
		}
		.m-info-content input{
			width:170px;
			height:22px;
			border:1px solid #ccc;
		}
		.Mbutton25 {
			width: 61px;
			height: 26px;
			line-height: 26px;
			text-align: center;
			color: #2977A8;
			font-weight: 700;
			cursor: pointer;	
			font-size: 13px;
		}
		.saveButton{
			float: left;
			margin-top: 10px;
			margin-left:200px;
			background-color:#CC0000;
			line-height:22px;
			color:white;
			border:none;
		}
		.cancleButton{
			float:left;
			margin-top: 10px;
			background-color:#f57771;
			line-height:22px;
			color:white;
			border:none;
			margin-left:10px;
		}
		
</style>
  </head>
<body>
  <div class="file-box">
	  <form id ="fileForm" action="${ctx}/productfile/uploadImg.net?fid=<%=request.getParameter("fid")%>" method="post" enctype="multipart/form-data">
    			文件:<input type="file"  id ="fileupload"  name="fileupload"  accept=".jpg,.png"/>
		        <input id ="upfile" class="Mbutton25 saveButton" type="button" value="上传">
		        <input id ="cancel" class="Mbutton25 cancleButton" type="button" value="取消"> 
	  </form>
   </div>
</body>
<script type="text/javascript">
   		//当前iframe层的索引
		var fileIndex = parent.layer.getFrameIndex(window.name);
		$("#cancel").on('click', function(){
		    parent.layer.close(fileIndex);
		});
		
		$("#upfile").click(function() { 
			if($("#fileupload").val().length <=0){
				layer.alert('请选择上传文件！', function(alIndex){layer.close(alIndex);});
				return;
			}
			var options = {  
                    url : "${ctx}/productfile/uploadImg.net?fid=<%=request.getParameter("fid")%>",
                    type : "POST",
                    success : function(msg) {
                        if(msg == "success"){
                        	layer.alert('操作成功！', function(alIndex){
									parent.refreshImg();
									layer.close(alIndex);
									parent.layer.close(fileIndex);
							});
                        }else{
                        	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(fileIndex);
							});
                        }
                    },
                    error:function(){
                    	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(fileIndex);
						});
                    }
            };  
          $("#fileForm").ajaxSubmit(options);//绑定页面中form表单的id
		});
		
		
		/* $("#upfile").click(function() {
				
				/* $.post(url,new FormData($("#fileForm")[0]),function(data){
				 		if(data=="success"){
				 			layer.alert('操作成功！', function(alIndex){
									parent.refreshImg();
									layer.close(alIndex);
									parent.layer.close(fileIndex);
							});
				 		}else{
				 			layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(fileIndex);
							});
				 		}
				}); */
	</script>
</html>
