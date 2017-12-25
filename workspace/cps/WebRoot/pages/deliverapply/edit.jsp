<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改</title>
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
			text-align:center;
			color: #2977A8;
			font-weight:700;
			cursor: pointer;	
			font-size: 13px;
		}
		.saveButton{
			margin-top: 8px;
			background-color:#CC0000;
			line-height:22px;
			color:white;
			float:left;
			border:none;
			margin-left:150px;
		}
		.cancleButton{
			float:left;
			margin-top: 8px;
			background-color:#f57771;
			line-height:22px;
			color:white;
			border:none;
			margin-left:10px;
		}
		
		.plus,.reduce{
			display:block;
			float:left;
			width:30px;
			height:29px;
			font-size:15px;
			padding:2px 5px;
			background-color:#e9e9e9;
			border:1px solid #ccc;
			text-decoration:none;
			color:#585858;
			line-height:20px
		}
		.reduce,.plus:hover{
			cursor:pointer;
			color:#000;
			font-size:20px;
		}
		.amount{
			text-align:center;
			display:block;
			float:left;
			height:23px;
			width:80px;
		}
		.date_{
			display:block;
			float:left;
			width:150px;
			height:28px;
			border:1px solid #666;
			border-right:none;
			margin-top:6px;
		}
		.for_date{
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
	
		.am{
			display:block;
			float:left;
			width:70px;
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
		.pm{
			display:block;
			float:left;
			width:70px;
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
		.lst{
			width:357px;
			font-size:16px;
			border:1px solid #ccc;
		}
		._color{
			border:2px solid #C00;
		}
		
	</style>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
  </head>
  <body>
    <div id="raise">
       	<form id="updateForm" method="post" action="${ctx}/custproduct/update.net" >
       		<input type="hidden" id="id" name="deliverapply.fid" value="${deliverapply.fid}"/>
       		<input type="hidden" id="hours" name="deliverapply.hours" />
           	<table cellpadding="0" cellspacing="0" >
                   <tr>
	                   	<td class="m-info-title">数量:</td>
	                    <td >
                   			<input id="min_quantity" type="button" value="－" class="reduce" />
                           	<input id="amount" type="text" class="amount" name="deliverapply.famount" value="${deliverapply.famount}"/>
                           	<input id="add_quantity" type="button" value="＋" class="plus" />
	                    </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">交期:</td>
	                   	<td>
                            	<input type="text" id="jdate" class="date_" name="deliverapply.farrivetime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" value="${date}"/>
                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('jdate')})"   src="${ctx}/css/images/sj.png"/></a>
                                <a href="javascript:void(0);" id="am" class="am" >上午</a>
                                <a href="javascript:void(0);" id="pm" class="pm" >下午</a>
                        </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">地址:</td>
	                    <td class="m-info-content">
	                    		<select id="addressId" name="deliverapply.faddressid" class="lst">
									<option value="0">--请选择--</option>
									<c:forEach var="entry" items="${address}">
										<option value="${entry.fid}" id="${entry.fid}" >${entry.fdetailaddress}</option>
									</c:forEach>
								</select>
	                    </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">备注:</td>
	                    <td class="m-info-content"><textarea id="farrivetime" name="deliverapply.fdescription" style="width:350px;height:80px;"></textarea></td>
                   </tr>
                   <tr>
                      	<td colspan="2">
						    <div class="Mbutton25 saveButton" id="updateButton">保存</div><div class="Mbutton25 cancleButton" id="cancel-edit">取消</div>
					    </td>
                   </tr>
               </table>
           </form>
     </div>
  </body>
  <script type="text/javascript">
  $(document).ready(function () {
  	document.getElementById("addressId").options["${deliverapply.faddressid}"].selected="selected";
  	if("${hours}" <= 13){
		$("#am").addClass("_color");
		$("#am").siblings().removeClass("_color");
		$("#hours").val("am");
	}else{
		$("#pm").addClass("_color");
		$("#pm").siblings().removeClass("_color");
		$("#hours").val("pm");
	}
  	
  	//点击设置【上午】/【下午】
	$("#am").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		$("#hours").val("am");
	});
	$("#pm").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		$("#hours").val("pm");
	});
  
  
  	//数量
	$("#min_quantity").mouseover(function(){
  		if($("#amount").val()<=1){
			$("#min_quantity").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}else{
			$("#min_quantity").css({cursor:"pointer",color:"#000",'font-size':"20px"});
		}
	});
	//数量+
    $("#add_quantity").click(function(){       
        $("#amount").val(parseInt($("#amount").val())+1);
    });
    //数量-
	$("#min_quantity").click(function(){  
    		if($("#amount").val()>1){
	        	$("#amount").val(parseInt($("#amount").val())-1);
   			}else{
   				$("#min_quantity").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
   			}
	});
	
	
	//当前iframe层的索引
	var editIndex = parent.layer.getFrameIndex(window.name);
	$("#cancel-edit").on('click', function(){
	    parent.layer.close(editIndex);
	});
	
	$("#updateButton").click(function() {
			$.ajax({
				type : "POST",
				url : "${ctx}/deliverapply/update.net",
				data:$("#updateForm").serialize(),
				success : function(response) {
					if (response == "success") {
						layer.alert('操作成功！', function(alIndex){
								parent.gridDeliverapplyTable($("#queryForm").serialize(),1);
								layer.close(alIndex);
								parent.layer.close(editIndex);
						});
					}else {
						layer.alert('操作失败！', function(alIndex){
								layer.close(alIndex);
								parent.layer.close(editIndex);
						});
					}
				}
	  		});
	});
	
  });
  
   		
		
	</script>
</html>
