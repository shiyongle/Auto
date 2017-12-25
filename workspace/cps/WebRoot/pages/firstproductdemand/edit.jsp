<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的需求-新增需求</title>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript" src="${ctx}/js/ajaxfileupload.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.form.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/layer/layer.js"></script>
<style>
*{
	margin:0px auto;
	padding:0px;
	font-family:"微软雅黑";
	}
.red{color:red;}
#nav{
	width:1055px;
	height:420px;
	}
#nav .div1,#nav .div3{
	float:left;
	}
#nav table{
	border-collapse:collapse;
	}
.div1{
	width:550px;
	padding-right:10px;
	height:330px;
	color:#666;
	margin-bottom:5px;
	border-right:3px dashed lightgray;
	}
.div1 ._require,.div1 .selected{
	width:180px;
	height:30px;
	font-size:16px;
	}
.div1 ._date{
	float:left;
	width:155px;
	height:30px;
	border:1px solid #666;
	border-right:none;
	}
.div1 .for_date{
	display:block;
	float:left;
	width:25px;
	height:27px;
	border:1px solid #666;
	border-left:none;
	cursor:default;
	padding-top:3px;
	}
.div1 .for_date .img{
	border:none;
	}
.div1 .reduce,.div1 .amount,.div1 .plus{
	float:left;
	}
.div1 .amount{
	text-align:center;
	display:block;
	float:left;
	height:27px;
	width:120px;
	}
.div1 .plus,.div1 .reduce{
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
	
.div1 .plus,.div1 .reduce:hover{
	cursor:pointer;
	color:#000;
	font-size:20px;
	}
.div1 ._yes,.div1 ._no{
	display:inline-block;
	width:63px;
	height:30px;
	border:1px solid #666;
	text-align:center;
	line-height:30px;
	text-decoration:none;
	color:#666;
	}
.div1 ._color{
	border:2px solid #C00;;
	height:32px;
	}
.div1 .description{
	height:80px;
	width:454px;
	}
#nav .div3{
	height:345px;
	width:280px;
	margin-bottom:10px;
	color:#666;
	margin-left:8px;
	}
.div3 ._setting{
	border:none;
	display:block;
	float:left;
	font-size:14px;
	text-align:center;
	color:#666;
	width:175px;
	height:30px;
	line-height:30px;
	cursor:default;
	}
.div3 ._fileName{
	display:block;
	float:left;
	border:none;
	width:270px;
	height:30px;
	line-height:30px;
	font-size:14px;
	color:#666;
	cursor:default;
	text-align:center;
	}

.div3 ._list{
	font-size:14px;
	text-align:center;
	border-collapse:collapse;
	}
.div3 ._list tr{
	border:1px solid lightgray;
	}
.div3 ._list tr td{
	border:none;
	}
.div3 .front{
	position:relative;
	display: inline-block;
	background: #C00;
	overflow: hidden;
	color: white;
	text-decoration:none;
	text-indent: 0;
	line-height:30px;
	width:140px;
	text-align:center;
	height:30px;
	cursor:pointer;
	margin-top:5px;
	*+margin-top:2.5px;
	}
.front input {
	position:absolute;
	font-size: 100px;
	right: 0;
	top: 0;
	opacity: 0;
	width:140px;
	height:30px;
	}
/*div4*/
#nav .div4{
	height:60px;
	width:550px;
	text-align:center;
	margin-top:10px;
	clear:both;
	}
.div4 ._submit,.div4 ._btn{
	width:135px;
	height:40px;
	line-height:40px;
	font-family:"微软雅黑";
	font-size:18px;
	border:none;
	background-color:#C00;
	color:white;
	cursor:pointer;
	margin-top:10px;
	*+margin-top:5px;
	}
#nav form .p1{
	height:45px;
	line-height:45px;
	width:550px;
	font-size:20px;
	color:black;
	}
</style>
<script type="text/javascript">
function loadPic(){
		$.ajax({
				type : "POST",
				url : "${ctx}/productfile/loadByParentId.net?id="+"${demand.fid}",
				dataType:"json",
				async:false,
				success : function(response) {
					 $.each(response.data, function(i, ev) {
					 		var html =[
							          '<tr height="30" id="',ev.fid,'">',
										'<td width="270" style="word-wrap:break-word;">',ev.fname,'</td>',
										'<td width=""><a href="javascript:void(0);" onclick="p_del(\''+ev.fid+'\');">删除</a></td>',
									  '</tr>'
									  ].join("");
							$(html).appendTo("#sc_photo");
					 });
				}
		});
}


$(document).ready(function(){
	if("${demand.fsupplierid}"!=""){
		document.getElementById("fsupplierid").options["${demand.fsupplierid}"].selected="selected";
	}
	if("${demand.fiszhiyang }"=="true"){
		$("#yes_").addClass("_color");
	}else{
		$("#no_").addClass("_color");
	}
	//数量
	$("#min_quantity").mouseover(function(){
	 	if($("#amount").val()==1){
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
	//是否制样
	$("#yes_").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
	});
	$("#no_").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
	});
	window.loadPic();
	
	var index = parent.layer.getFrameIndex(window.name);
	$("#demand_fb").click(function() { 
		if(isVal()==true){
			var options = {  
                    url : "${ctx}/firstproductdemand/update.net?isfb=1",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	layer.alert('操作成功！', function(alIndex){
                        			parent.gridFirstproducteMandTable(1);
                        			parent.table_check();
									parent.hang_xg();
									layer.close(alIndex);
									parent.layer.close(index);
							});
                        }else{
                        	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
							});
                        }
                    },
                    error:function(){
                    	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
						});
                    }
            };  
            $("#c_cg").ajaxSubmit(options);//绑定页面中form表单的id 
		}
	});

	$("#demand_ccg").click(function() { 
		if(isVal()==true){
			var options = {  
                    url : "${ctx}/firstproductdemand/update.net?isfb=2",
                    dataType:"json",
                    type : "POST",
                    success : function(msg) {
                        if(msg.success == "success"){
                        	layer.alert('操作成功！', function(alIndex){
                        			parent.gridFirstproducteMandTable(1);
                        			parent.table_check();
									parent.hang_xg();
									layer.close(alIndex);
									parent.layer.close(index);
							});
                        }else{
                        	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
							});
                        }
                    },
                    error:function(){
                    	layer.alert('操作失败！', function(alIndex){
									layer.close(alIndex);
									parent.layer.close(index);
						});
                    }
            };  
            $("#c_cg").ajaxSubmit(options);//绑定页面中form表单的id */
		}
	});
});

//上传
 function uploadImage(){
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
   			var fid ="${demand.fid}";
			$.ajaxFileUpload({
				url : "${ctx}/productfile/uploadImg_P.net?fid="+fid,
				secureuri:false,
				fileElementId:'fl',
				type:'POST',
				dataType: 'json',
				success: function (response){
					if(response.success){
						layer.alert('操作成功！', function(index){
							layer.close(index);
							var html =[
							          '<tr height="30" id='+response.fid+'>',
										'<td width="270" style="word-wrap:break-word;">'+response.pname+'</td>',
										'<td width=""><a href="javascript:void(0);" onclick="p_del(\''+response.fid+'\');">删除</a></td>',
									  '</tr>'
									  ].join("");
							$(html).appendTo("#sc_photo");
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
 
//图片删除
function p_del(obj){
 	$.ajax({
			type : "POST",
			url : "${ctx}/productfile/deleteImg.net",
			dataType:"text",
			async:false,
			data: {"fid":obj},  
			success : function(response) {
				if(response =="success"){
					layer.alert('操作成功！', function(index){
						layer.close(index);
						$('#'+obj).remove();
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
 function isYes(obj){
	if(obj==0){
		$("#fiszhiyang").val(false);
	}else{
		$("#fiszhiyang").val(true);
	}
}
 
function isVal(){
	if($("#fname").val()==""){
		layer.tips('需求名称不能为空！', '#fname', {
		    tips: [1, '#F7874E'],
		    time: 4000
		});
		return false;
	}else if($("#foverdate").val()==""){
		layer.tips('入库日期不能为空！', '#foverdate', {
		    tips: [1, '#F7874E'],
		    time: 4000
		});
		return false;
	}else if($("#farrivetime").val()==""){
		layer.tips('发货日期不能为空！', '#farrivetime', {
		    tips: [1, '#F7874E'],
		    time: 4000
		});
		return false;
	}else if($("#fdescription").val()==""){
		layer.tips('描述不能为空！', '#fdescription', {
		    tips: [1, '#F7874E'],
		    time: 4000
		});
		return false;
	}else if($("#sc_photo").find("tr").length==0){
		layer.tips('请上传附件！', '#scfj', {
		    tips: [1, '#F7874E'],
		    time: 4000
		});
		return false;
	}else{
	  return true;
	}
}


</script>
</head>

<body>
	<div id="nav">
    	<form id="c_cg">
        	<div class="div1">
            	<p class="p1">需求信息</p>
			    <input type="hidden" id="fiszhiyang" name="fiszhiyang" value="${demand.fiszhiyang }"/>
			    <input type="hidden" id="fid" name="fid" value="${demand.fid }"/>
                <table cellpadding="0" cellspacing="0" border="0" width="550">
                	<tr height="50">
                    	<td align="right" width="90">需求名称<span class="red">*</span>:</td>
                        <td width="185"><input type="text" id="fname" name="fname" class="_require" value="${demand.fname }"/></td>
                        <td align="right" width="90">订单数量:</td>
                        <td>
                           	<input id="min_quantity" type="button" value="－" class="reduce" />
                           	<input id="amount" type="text" class="amount" name="famount" value="${demand.famount}"/>
                           	<input id="add_quantity" type="button" value="＋" class="plus" />
	                    </td>
                    </tr>
                    <tr height="50">
	                    <td align="right">制造商:</td>
                        <td width="185">
                        	<select id="fsupplierid" name="fsupplierid" class="selected">
									<option value="0">--请选择--</option>
									<c:forEach var="entry" items="${supplier}">
										<option value="${entry.fid}" id="${entry.fid}" >${entry.fname}</option>
									</c:forEach>
						    </select>
                        </td>
                        <td align="right">是否制样:</td>
                        <td>
                        	<a href="javascript:void(0);" class="_yes " id="yes_" onclick="isYes(1);">是</a>&nbsp;&nbsp;&nbsp;
                        	<a href="javascript:void(0);" class="_no" id="no_" onclick="isYes(0);">否</a>
                        </td>
                    </tr>
                    <tr height="50">
                    	<td align="right" width="90">入库日期<span class="red">*</span>:</td>
                        <td width="185">
                        		<input type="text" id="foverdate" name="foverdate" class="_date" readonly="readonly"     onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${foverdate_first}',maxDate:'${foverdate_last}'})" value="${demand.foverdateString}"/>
                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('foverdate'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${foverdate_first}',maxDate:'${foverdate_last}'})"   src="${ctx}/css/images/sj.png"/></a>
                        </td>
                        <td align="right" width="90">发货日期<span class="red">*</span>:</td>
                        <td width="185">
                        		<input type="text" id="farrivetime" name="farrivetime" class="_date" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${farrivetime_first}',maxDate:'${farrivetime_last}'})" value="${demand.farrivetimeString}"/>
                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('farrivetime'),dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'${farrivetime_first}',maxDate:'${farrivetime_last}'})" src="${ctx}/css/images/sj.png"/></a>
                        </td>
                    </tr>
                    <tr height="50">
                    	<td width="82" align="right">联系人:</td>
                        <td width="180"><input type="text" class="_require" id="flinkman" name="flinkman" value="${demand.flinkman}"/></td>
                        <td width="82" align="right">联系电话:</td>
                        <td width="180"><input type="text"  class="_require" id="flinkphone" name="flinkphone" value="${demand.flinkphone}"/></td>
                    </tr>
                    <tr height="80">
                    	<td align="right">需求描述<span class="red">*</span>:</td>
                        <td colspan="3"><textarea id="fdescription" name="fdescription"  class="description" placeholder="填写包装要求、特殊要求、现状问题、填写内容具体、准确、设计效率越高" style="font-size:13px">${demand.fdescription}</textarea></td>
                    </tr>
                </table>
            </div>
            <div class="div3">
            	<p class="p1" style="">附件分录</p>
                <div style="height:40px;width:250px;float:left;">
                	<span style="display:block;float:left;line-height:40px;width:82px;text-align:right;">附件上传：</span>
                    <a id="scfj" href="javascript:void(0);" class="front" onclick="document.getElementById('fl').click();">点击上传
                    	<input type="file" id="fl" name ="fileupload"  onchange="uploadImage();"/>
                    </a>
                </div>
                <div style="height:30px;background-color:#f2f2f2;border:1px solid lightgray;border-bottom:none;width:466px;float:left; margin-top:20px;">
                	<span class="_fileName">附件名称</span><span class="_setting">操作</span>
                </div>
                <div style="height:190px;overflow-y:scroll;width:466px;position:relative;margin-right:85px;border:1px solid lightgray;border-top:none;">
                    <table id="sc_photo" cellpadding="0" cellspacing="0" border="1" width="448" class="_list" style="table-layout:fixed;"></table>
                </div>
            </div>
             <div class="div4">
            	<input type="button" id="demand_fb" value="发布" class="_submit" style="margin-right:30px;margin-left:60px;"/>
            	<input type="button" id="demand_ccg" value="存草稿" class="_btn" style="background-color:#F9918E;"/>
            </div>
        </form>
    </div>
</body>
</html>
