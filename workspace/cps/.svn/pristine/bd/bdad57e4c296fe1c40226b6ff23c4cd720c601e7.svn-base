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
			width:165px;
			height:22px;
			border:1px solid #ccc;
		}
		.red{
		    color:red;
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
			float: right;
			margin-top: 5px;
			background-color:#CC0000;
			line-height:22px;
			color:white;
			border:none;
		}
		.cancleButton{
			float:left;
			margin-top: 5px;
			background-color:#f57771;
			line-height:22px;
			color:white;
			border:none;
			margin-left:10px;
		}
	</style>
	<link rel="stylesheet" type="text/css" href="${ctx}/css/order_products.css" />
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
  </head>
  <body>
    <div id="raise">
       	<form id="updateForm" method="post" action="${ctx}/address/update.net" >
       		<input type="hidden" id="fid" name="address.fid" value="${address.fid}"/>
           	<table >
			    <tr>
					<td class="m-info-title">联系人<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="flinkman" name="address.flinkman" value="${address.flinkman}" /></td>
					<td class="m-info-title">电话<span class="red">*</span>:</td>
					<td class="m-info-content"><input type="text" id="fphone" name="address.fphone" value="${address.fphone}" /></td>
				</tr>

				<tr>
					<td class="m-info-title">详细地址<span class="red">*</span>:</td>
					<td class="m-info-content" colspan="3"><textarea id="fdetailaddress" name="address.fdetailaddress"  style="width:396px;height:80px;">${address.fdetailaddress}</textarea></td>
				</tr>
				<tr>
					<td colspan="2">
						<div class="Mbutton25 saveButton" id="updateButton">保存</div>
					</td>
					<td colspan="2">
						<div class="Mbutton25 cancleButton" id="cancel-newp">取消</div>
					</td>
				</tr>
             </table>
           </form>
     </div>
     
  </body>
  <script type="text/javascript">
   		//当前iframe层的索引
		var index = parent.layer.getFrameIndex(window.name);
		$("#cancel-newp").on('click', function(){
		    parent.layer.close(index);
		});
		
		$("#updateButton").click(function() {
				if($("#flinkman").val() =='' && $("#fphone").val() =='' && $("#fdetailaddress").val() ==''){
					layer.msg('红色*为必填项,请完善信息！');
				}else{
					var url = $("#updateForm").attr("action");
					$.post(url,$("#updateForm").serialize(),function(data){
					 		if(data=="success"){
					 			layer.alert('操作成功！', function(alIndex){
					 					parent.gridUaddressTable(1);
										layer.close(alIndex);
										parent.layer.close(index);
								});
					 		}else{
					 			layer.alert('操作失败！', function(alIndex){
										layer.close(alIndex);
										parent.layer.close(index);
								});
					 		}
					});
				}
		});
	</script>
</html>
