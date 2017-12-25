<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>新建</title>
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
		#raise .keep{width:60px;height:20px;font-size:15px;background-color:#CC0000;line-height:22px;color:white;border:none;}
		#raise .cancel{width:60px;height:20px;font-size:15px;background-color:#f57771;line-height:22px;color:white;border:none;margin-left:10px;}
	</style>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
  </head>
  <body>
    <div id="raise">
       	<form id="updateForm" method="post" action="${ctx}/custproduct/update.net" >
       		<input type="hidden" id="id" name="custproduct.fid" value="${custproduct.fid}"/>
           	<table cellpadding="0" cellspacing="0" >
                   <tr>
	                   	<td class="m-info-title">产品名称:</td>
	                    <td class="m-info-content"><input type="text" id="fname" name="custproduct.fname" value="${custproduct.fname}"/></td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">产品规格:</td>
	                    <td class="m-info-content"><input type="text" id="fspec" name="custproduct.fspec" value="${custproduct.fspec}"/></td>
                   </tr>
                   <tr>
                   	   <td class="td1" height="60">&nbsp;</td>
                       <td><input id="updateButton" type="button" value="保存" class="keep"/><input type="button" value="取消" id="cancel-edit" class="cancel"/></td>
                   </tr>
               </table>
           </form>
     </div>
  </body>
  <script type="text/javascript">
   		//当前iframe层的索引
		var editIndex = parent.layer.getFrameIndex(window.name);
		$("#cancel-edit").on('click', function(){
		    parent.layer.close(editIndex);
		});
		
		$("#updateButton").click(function() {
				var url = $("#updateForm").attr("action");
				$.post(url,$("#updateForm").serialize(),function(data){
				 		if(data=="success"){
				 			layer.alert('操作成功！', function(alIndex){
									parent.gridCustproductTable(1);
									layer.close(alIndex);
									parent.layer.close(editIndex);
							});
				 		}else{
				 			layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(editIndex);
							});
				 		}
				});
		});
	</script>
</html>
