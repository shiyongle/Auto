<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>修改</title>
    <style>
    	.m-info-title{
			text-align:right;
			height:40px;
			line-height:40px;
			font-weight:400;
			padding-right:10px;
			font-family:"微软雅黑";
		}
		.red{
		    color:red;
		}
		.Mbutton25 {
			width: 100px;
			height: 26px;
			line-height: 26px;
			text-align:center;
			color: #2977A8;
			font-weight:700;
			cursor: pointer;	
			font-size: 16px;
		}
		.saveButton{
			margin-top: 8px;
			background-color:#CC0000;
			color:white;
			float:left;
			border:none;
			margin-left:150px;
		}
		.cancleButton{
			float:left;
			margin-top: 8px;
			background-color:#f57771;
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
			cursor:default;
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
		._color{
			border:2px solid red;
		}	
	</style>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
  </head>
  <body>
    <div id="raise">
       	<form id="updateForm" method="post" action="${ctx}/mystock/update.net" >
    	    <input type="hidden" name="mystock.fid" value="${mystock.fid}"/>
           	<table cellpadding="0" cellspacing="0">
                   <tr>
	                   	<td class="m-info-title">计划数量:</td>
	                    <td>
                   			<input id="min_quantity" type="button" value="－" class="reduce" />
                           	<input id="amount" type="text" class="amount" name="mystock.fplanamount" value="${mystock.fplanamount==null?0:mystock.fplanamount}"/>
                           	<input id="add_quantity" type="button" value="＋" class="plus" />
	                    </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">平均数量:</td>
	                    <td >
                   			<input id="min_q" type="button" value="－" class="reduce" />
                           	<input id="amount_q" type="text" class="amount" name="mystock.faveragefamount" value="${mystock.faveragefamount==null?0:mystock.faveragefamount}"/>
                           	<input id="add_q" type="button" value="＋" class="plus" />
	                    </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">首次发货:</td>
	                   	<td>
                            	<input type="text" id="ffinishtime" class="date_" name="mystock.ffinishtime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${currentTime}'})" value="${mystock.ffinishtime}"/>
                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('ffinishtime'),minDate:'${currentTime}'})"  src="${ctx}/css/images/sj.png"/></a>
                                <c:choose>
                                	<c:when test="${day==2}">
                                		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',2);" class="am _color">2日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',5);" class="pm">5日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',7);" class="pm">7日内</a>
                                	</c:when>
                                	<c:when test="${day==5}">
                                		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',2);" class="am">2日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',5);" class="pm _color">5日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',7);" class="pm">7日内</a>
                                	</c:when>
                                	<c:otherwise>
                                		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',2);" class="am">2日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',5);" class="pm">5日内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',7);" class="pm _color">7日内</a>
                                	</c:otherwise>
                                </c:choose>
                        </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">备货周期:</td>
	                   	<td>
                            	<input type="text" id="fconsumetime" class="date_" name="mystock.fconsumetime" readonly="readonly" onfocus="WdatePicker({el:$dp.$('fconsumetime'),minDate:'${currentTime.toLocaleString().substring(0,10)}',maxDate:'${mystock.fconsumetime}'})" value="${mystock.fconsumetime}"/>
                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('fconsumetime'),minDate:'${currentTime.toLocaleString().substring(0,10)}',maxDate:'${mystock.fconsumetime}'})"   src="${ctx}/css/images/sj.png"/></a>
                                <c:choose>
                                	<c:when test="${mouth>0&&mouth<=7}">
                                		<a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',7);" class="am _color">一周内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',15);" class="pm">半月内</a>
		                                <a href="javascript:void(0);" onclick="setConsumeTime();" class="pm">一月内</a>
                                	</c:when>
                                	<c:when test="${mouth>7&&mouth<=15}">
                                		<a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',7);" class="am">一周内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',15);" class="pm _color">半月内</a>
		                                <a href="javascript:void(0);" onclick="setConsumeTime();" class="pm">一月内</a>
                                	</c:when>
                                	<c:when test="${mouth>15}">
                                		<a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',7);" class="am">一周内</a>
		                                <a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',15);" class="pm">半月内</a>
		                                <a href="javascript:void(0);" onclick="setConsumeTime();" class="pm _color">一月内</a>
                                	</c:when>
                                </c:choose>
                        </td>
                   </tr>
                   <tr>
	                   	<td class="m-info-title">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
	                    <td class="m-info-content"><textarea id="farrivetime" name="mystock.fremark" rows="4" cols="59">${mystock.fremark}</textarea></td>
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
									parent.two($("#queryForm").serialize(),1);
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
	/**第二个平均数量**/
	$("#min_q").mouseover(function(){
  		if($("#amount_q").val()<=1){
			$("#min_q").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}else{
			$("#min_q").css({cursor:"pointer",color:"#000",'font-size':"20px"});
		}
	});
	//数量+
    $("#add_q").click(function(){      
        $("#amount_q").val(parseInt($("#amount_q").val())+1);
    });
    //数量-
	$("#min_q").click(function(){  
    		if($("#amount_q").val()>1){
	        	$("#amount_q").val(parseInt($("#amount_q").val())-1);
   			}else{
   				$("#min_q").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
   			}
	});
//设置时间
function setFirstTime(id,obj){
	$.ajax({
			url:"${ctx}/mystock/setFirstTime.net",
			type:"post",
			dataType:"json",
			data:{day:obj},
			success:function(data){
				if(id=="ffinishtime"){
					$("#ffinishtime").val(data.date);
				}else{
					$("#fconsumetime").val(data.date);
				}
			}
		});
}
//设置时间
function setConsumeTime(obj){
	$.ajax({
			url:"${ctx}/mystock/setTime.net",
			type:"post",
			dataType:"json",
			success:function(data){
				$("#fconsumetime").val(data.date);
			}
	});
}
$(".pm").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		});
	$(".am").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		});
	</script>
</html>
