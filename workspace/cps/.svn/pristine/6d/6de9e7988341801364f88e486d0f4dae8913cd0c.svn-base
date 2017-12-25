<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>商品详情</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/order_products.css" />
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/My97DatePicker/WdatePicker.js" ></script>
<script type="text/javascript">
$(document).ready(function(){
	//根据当前事件,设置【上午】/【下午】
	var currentDate = new Date();
	var currentHours = currentDate.getHours();
	if(currentHours <= 13){
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
	
	
	
	//加载图片
	$.ajax({
		type : "POST",
		url : "${ctx}/productfile/getPictureUrl.net",
		dataType:"json",
		async:false,
		data: {"fid":"${fid}"},  
		success : function(response) {
			//var html ="<img src="+response.pathUrl+" width="+400+" height="+400+" />";
			//此处应该拼接html字符串然后记载
			var html ="<div class="+'box'+">"+
                    "<div class=\"tb-booth tb-pic tb-s310\">"+
                        "<a href="+'css/images/01.jpg'+"><img src="+response.pathUrl+" rel="+response.pathUrl.replace("smallvmifile","vmifile")+" class="+'jqzoom'+" /></a>"+
                    "</div>"+
                    "<div class="+'spec-scroll'+"><a class="+'prev'+">&lt;</a><a class="+'next'+">&gt;</a>"+
                    "<div class="+'items'+">"+
                    	"<ul class="+'tb-thumb'+" id="+'thumblist'+">"+
                            //<li class="tb-selected"><div class="tb-pic tb-s40">
                            //<a href="#"><img  src="css/images/8.jpg" mid="css/images/02_mid.jpg" big="css/images/02.jpg" width="55" height="50"/></a></div>
                            //</li>
                    	"</ul>"+
                    "</div>"+
                    "</div>"+
                   "</div>"+
                   "<a href="+'javascript:void(0);'+" onclick="+'fileUpload();'+" class="+'smart'+">上传图片</a>"+
		           "<a href="+'javascript:void(0);'+" onclick="+'deleUpload();'+" class="+'delete'+">删除图片</a>";
		         
		     $(html).appendTo("#container_left");
		      if(response.list!=null && response.list.length>0){
		      	var html2="";
		      	for(var i=0;i<response.list.length;i++){
		      		if(i==0){
		      			html2="<li class="+'tb-selected'+"><div class=\"tb-pic tb-s40\">"+
                            "<a href='javascript:void(0);'><img  src="+response.list[i]+" mid="+response.list[i].replace("smallvmifile","vmifile")+" big="+response.list[i].replace("smallvmifile","vmifile")+" width='55' height='50'/></a></div></li>";
		      		}else{
		      			html2="<li><div class=\"tb-pic tb-s40\">"+
                            "<a href='javascript:void(0);'><img  src="+response.list[i]+" mid="+response.list[i].replace("smallvmifile","vmifile")+" big="+response.list[i].replace("smallvmifile","vmifile")+" width='55' height='50'/></a></div></li>";
		      		}
		      	$(html2).appendTo(".items ul");
		      	}		
		     }
		}
	});
	//放大镜
	window.doFANGDA();
	//选项卡
	var $li = $(".tab li");
	var $tbl = $(".formlist table");			
	$(".tab li").click(function(){
		var $this = $(this);
		var $t = $this.index();
		$li.removeClass();
		$this.addClass("current");
		$tbl.css('display','none');
		$tbl.eq($t).css('display','block');
	});
	//数量
	$("#min_quantity").mouseover(function(){
  		if($("#amount").val()<1){
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
 		if($("#amount").val()>=1){
      	   $("#amount").val(parseInt($("#amount").val())-1);
		}else{
		   $("#min_quantity").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}
	});
	
	//计划数量
	$("#plan_min").mouseover(function(){
  		if($("#fplanAmount").val()<1){
		   $("#plan_min").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}else{
		   $("#plan_min").css({cursor:"pointer",color:"#000",'font-size':"20px"});
		}
	});
	//计划数量+
    $("#plan_add").click(function(){       
        $("#fplanAmount").val(parseInt($("#fplanAmount").val())+1);
    });
    //计划数量-
	$("#plan_min").click(function(){  
 		if($("#fplanAmount").val()>=1){
      	  $("#fplanAmount").val(parseInt($("#fplanAmount").val())-1);
		}else{
		  $("#plan_min").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}
	});
	//平均数量
	$("#avg_min").mouseover(function(){
  		if($("#faveragefAmount").val()<1){
			$("#avg_min").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}else{
			$("#avg_min").css({cursor:"pointer",color:"#000",'font-size':"20px"});
		}
	});
	//平均数量+
    $("#avg_add").click(function(){       
        $("#faveragefAmount").val(parseInt($("#faveragefAmount").val())+1);
    });
   //平均数量-
	$("#avg_min").click(function(){  
 		if($("#faveragefAmount").val()>=1){
      		$("#faveragefAmount").val(parseInt($("#faveragefAmount").val())-1);
		}else{
			$("#avg_min").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
		}
	});
	$(".reduce").mouseout(function(){
		$(".reduce").css({cursor:"not-allowed",color:"#585858",'font-size':"15px"});
	});
});
</script>
</head>

<body >
	<div id="nav">
       	<div id="container">
        	<div id="container_left">
        		 <!--...
                 	<div class="box">
                    <div class="tb-booth tb-pic tb-s310">
                        <a href="css/images/01.jpg"><img src="css/images/8.jpg" rel="css/images/8-1.jpg" class="jqzoom" /></a>
                    </div>
                    <div class="spec-scroll"><a class="prev">&lt;</a><a class="next">&gt;</a>
                    <div class="items">
                    	<ul class="tb-thumb" id="thumblist">
                            <li class="tb-selected"><div class="tb-pic tb-s40"><a href="#"><img src="css/images/01_small.jpg" mid="css/images/01_mid.jpg" big="css/images/8-1.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40">
                            <a href="#"><img  src="css/images/8.jpg" mid="css/images/02_mid.jpg" big="css/images/02.jpg" width="55" height="50"/></a></div>
                            </li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/03_small.jpg" mid="css/images/03_mid.jpg" big="css/images/03.jpg"/></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/04_small.jpg" mid="css/images/04_mid.jpg" big="css/images/04.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                            <li><div class="tb-pic tb-s40"><a href="#"><img src="css/images/05_small.jpg" mid="css/images/05_mid.jpg" big="css/images/05.jpg"></a></div></li>
                    	</ul>
                    </div>
                    </div>
                </div>
               	//以上div是控制放大镜和多图片滑动效果
                 	<a href="#" class="smart">上传图片</a><a href="#" class="delete">删除图片</a>
           		--->
           	</div>
            <div id="container_right">
            	<ul class="tab">
                	<li class="current">要货</li>
                    <li>备货</li>
                </ul>
                <div class="formlist">
	            	<form id="productForm">
	        			<input type="hidden" id="fcusproductid" name="deliverapply.fcusproductid" value="${fid}"/>
	        			<input type="hidden" id="hours" name="deliverapply.hours" />
	        			<input type="hidden" id="hours" name="deliverapply.fsupplierid" value="${supplierId}"/>
	                	<table cellspacing="0" cellpadding="0" width="800" style="display:block;">
	                    	<tr>
	                        	<td colspan="2" class="tlt">&nbsp;&nbsp;<span>${product.fname}</span></td>
	                        </tr>
	                        <tr>
	                        	<td width="110" class="td1">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</td>
	                            <td width="725">${product.fspec}</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">最近下单:</td>
	                            <td>${product.flastorderfamount}只<span style="font-size:14px;">( ${product.flastordertime} )</span></td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存:</td>
	                            <td>${product.balanceqty}只(需发货200只)</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">采购订单:</td>
	                            <td><input type="text" class="lst" id="fordernumber" name="deliverapply.fordernumber"/></td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">数&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量:</td>
	                            <td>
	                            	<input id="min_quantity" type="button" value="－" class="reduce" />
	                            	<input id="amount" type="text" value="0" class="amount" name="deliverapply.famount"/>
	                            	<input id="add_quantity" type="button" value="＋" class="plus" />
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">交&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;期:</td>
	                            <td>
	                            	<input type="text" id="jdate" class="date_" name="deliverapply.farrivetime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${currentTime}'})" value="${farriveTime}"/>
	                                <a class="for_date"><img  onclick="WdatePicker({el:$dp.$('jdate'),minDate:'${currentTime}'})"   src="${ctx}/css/images/sj.png"/></a>
	                                <a href="javascript:void(0);" id="am" class="am">上午</a>
	                                <a href="javascript:void(0);" id="pm" class="pm">下午</a>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1"><span class="red">*</span>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址:</td>
	                        	<td class="m-info-content">
									<select id="addressId" name="deliverapply.faddress" class="lst">
										<option value="0">--请选择--</option>
										<c:forEach var="entry" items="${address}">
											<option value="${entry.fdetailaddress}" id="${entry.fid}" >${entry.fdetailaddress}</option>
										</c:forEach>
									</select>
	                                <input type="button" value="新增" class="_add" onClick="add_address();"/>
								</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
	                            <td height="70"><input type="text" placeholder="其他要求" class="lst" id="fdescription" name="deliverapply.fdescription"/></td>
	                        </tr>
                    </table>
                    <table cellspacing="0" cellpadding="0" width="800" style="display:none;">
	                    	<tr>
	                        	<td colspan="2" class="tlt">&nbsp;&nbsp;<span>${product.fname}</span></td>
	                        </tr>
	                        <tr>
	                        	<td width="110" class="td1">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</td>
	                            <td width="725">${product.fspec}</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">最近下单:</td>
	                            <td>${product.flastorderfamount}只<span style="font-size:14px;">( ${product.flastordertime} )</span></td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">库&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;存:</td>
	                            <td>${product.balanceqty}只(需发货200只)</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">采购订单:</td>
	                            <td><input type="text" class="lst" id="fordernumber" name="deliverapply.fordernumber"/></td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">计划数量:</td>
	                            <td>
	                                <input id="plan_min" type="button" value="－" class="reduce" />
	                    			<input id="fplanAmount" type="text" value="0" class="amount" name="deliverapply.fplanamount"/>
	                    			<input id="plan_add" type="button" value="＋" class="plus"   />
	                          	</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">平均发货:</td>
	                            <td>
	                               	<input id="avg_min"   type="button" value="－" class="reduce" />
	                   				<input id="faveragefAmount" type="text" value="0" class="amount" name="deliverapply.faveragefamount"/>
	                   				<input id="avg_add"   type="button" value="＋" class="plus"	/>
	                          	</td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">首次发货:</td>
	                            <td>
	                            	<input type="text" id="ffinishtime" class="date_" name="deliverapply.ffinishtime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${currentTime}'})" value="${ffinishtime}"/>
	                                <a class="for_date"><img onclick="WdatePicker({el:$dp.$('ffinishtime'),minDate:'${currentTime}'})" src="${ctx}/css/images/sj.png"/></a>
	                        		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',2);" class="pm">2日内</a>
	                        		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',5);" class="pm">5日内</a>
	                        		<a href="javascript:void(0);" onclick="setFirstTime('ffinishtime',7);" class="am _color">7日内</a>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">备货周期:</td>
	                            <td>
	                           		<input type="text" id="fconsumetime" class="date_" name="deliverapply.fconsumetime" readonly="readonly" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${currentTime}',maxDate:'${fconsumetime}'})" value="${fconsumetime}"/>
	                                <a class="for_date"><img onclick="WdatePicker({el:$dp.$('fconsumetime'),minDate:'${currentTime}',maxDate:'${fconsumetime}'})" src="${ctx}/css/images/sj.png"/></a>
	                            	<a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',7);" class="pm">一周内</a>
	                            	<a href="javascript:void(0);" onclick="setFirstTime('fconsumetime',15);" class="pm">半月内</a>
	                            	<a href="javascript:void(0);" onclick="setConsumeTime();" class="am _color">一月内</a>
	                            </td>
	                        </tr>
	                        <tr>
	                        	<td class="td1">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</td>
	                            <td height="70"><input type="text" placeholder="其他要求" class="lst" style="width:465px;" name="deliverapply.fremark"/></td>
	                        </tr>
                    </table>
	                    <p class="public_st">
	                    	<input type="button" value="下单" class="_submit" id="saveButton"/>
	                    	<input type="button" value="返回" class="_btn" onclick="lastStep();"/>
	                    </p>
	                </form>
                </div>
            </div>
          </div>
    </div>
<script language="javascript">
//新增收货地址
function add_address(){
	layer.open({
			    title: ['新增收货地址','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			    area: ['530px', '200px'],
			    content: "${ctx}/custproduct/add_address.net?fcustomerid="+"${product.fcustomerid}"
	});
}
//图片上传
function fileUpload(){
	layer.open({
			    title: ['文件上传','background-color:#CC0000; color:#fff;'],
			    type: 2,
			    anim:true,
			    area: ['600px', '630px'],
			    content: "${ctx}/custproduct/fileUpload.net?fid="+"${fid}"
	});
}
//图片删除
function deleUpload(){
	//根据src来截取fid,传递对应的图片id，进行删除操作
	var fid =$(".tb-selected div a img").attr("src").substring($(".tb-selected div a img").attr("src").lastIndexOf("/")+1,$(".tb-selected div a img").attr("src").lastIndexOf("."));
	$.ajax({
		type : "POST",
		url : "${ctx}/productfile/deleteImg.net",
		dataType:"text",
		async:false,
		data: {"fid":fid},  
		success : function(response) {
			if(response =="success"){
				layer.alert('操作成功！', function(index){
				layer.close(index);
				refreshImg();
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

/*** 刷新div*/
function refreshImg(){
	$("#container_left").empty();
	$.ajax({
		type : "POST",
		url : "${ctx}/productfile/getPictureUrl.net",
		dataType:"json",
		async:false,
		data: {"fid":"${fid}"},  
		success : function(response) {
			//var html ="<img src="+response.pathUrl+" width="+400+" height="+400+" />";
			//此处应该拼接html字符串然后记载
			var html ="<div class="+'box'+">"+
                    "<div class=\"tb-booth tb-pic tb-s310\">"+
                        "<a href="+'css/images/01.jpg'+"><img src="+response.pathUrl+" rel="+response.pathUrl.replace("smallvmifile","vmifile")+" class="+'jqzoom'+" /></a>"+
                    "</div>"+
                    "<div class="+'spec-scroll'+"><a class="+'prev'+">&lt;</a><a class="+'next'+">&gt;</a>"+
                    "<div class="+'items'+">"+
                    	"<ul class="+'tb-thumb'+" id="+'thumblist'+">"+
                            //<li class="tb-selected"><div class="tb-pic tb-s40">
                            //<a href="#"><img  src="css/images/8.jpg" mid="css/images/02_mid.jpg" big="css/images/02.jpg" width="55" height="50"/></a></div>
                            //</li>
                    	"</ul>"+
                    "</div>"+
                    "</div>"+
                   "</div>"+
                   "<a href="+'javascript:void(0);'+" onclick="+'fileUpload();'+" class="+'smart'+">上传图片</a>"+
		           "<a href="+'javascript:void(0);'+" onclick="+'deleUpload();'+" class="+'delete'+">删除图片</a>";
		         
		     $(html).appendTo("#container_left");
		      if(response.list!=null && response.list.length>0){
		      	var html2="";
		      	for(var i=0;i<response.list.length;i++){
		      		if(i==0){
		      			html2="<li class="+'tb-selected'+"><div class=\"tb-pic tb-s40\">"+
                            "<a href='javascript:void(0);'><img  src="+response.list[i]+" mid="+response.list[i].replace("smallvmifile","vmifile")+" big="+response.list[i].replace("smallvmifile","vmifile")+" width='55' height='50'/></a></div></li>";
		      		}else{
		      			html2="<li><div class=\"tb-pic tb-s40\">"+
                            "<a href='javascript:void(0);'><img  src="+response.list[i]+" mid="+response.list[i].replace("smallvmifile","vmifile")+" big="+response.list[i].replace("smallvmifile","vmifile")+" width='55' height='50'/></a></div></li>";
		      		}
		      	$(html2).appendTo(".items ul");
		      	}		
		     }
		}
	});
	window.doFANGDA();
}
function sendAjax(){
	 $.ajax({
			url:"${ctx}/custproduct/getAddress.net",
			type:"post",
			dataType:"json",
			data:{fid:"${fid}"},
			success:function(data){
				$("#addressId").empty();
				$("<option value='0'>--请选择--</option>").appendTo("#addressId");
				$.each(data.address,function(i,item){
					$("<option></option>").val(item.fid).text(item.fdetailaddress).appendTo("#addressId");	
				});
			}
		});
}
//上一步
function lastStep(){
	window.location.href="${ctx}/custproduct/list.net";	
}
//设置时间
function setFirstTime(id,obj){
	$.ajax({
			url:"${ctx}/custproduct/setFirstTime.net",
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
			url:"${ctx}/custproduct/setTime.net",
			type:"post",
			dataType:"json",
			success:function(data){
				$("#fconsumetime").val(data.date);
			}
	});
}
//下单
$("#saveButton").click(function() {
		if($("#amount").val()==0 && $("#fplanAmount").val()==0){
			layer.msg('请选择下单类型,并完善相关信息', {icon: 5, offset: 'auto', shift: 6,time: 3000});
			return;
		}else if($("#amount").val()!=0 && $("#addressId").val()==0){
			layer.tips('请选择收货地址', '#addressId',{ tips:[1,'#78BA32']});
			return;
		}
		$.ajax({
			url:"${ctx}/deliverapply/save.net",
			type:"post",
			dataType:"text",
			data:$("#productForm").serialize(),
			success:function(data){
				if(data ="success"){
					layer.alert('操作成功！', function(index){
					window.location.href="${ctx}/custproduct/list.net";	
					layer.close(index);
					});
				}else{
					layer.alert('操作失败！', function(index){layer.close(index);});
				}
			},
			error:function (){
					layer.alert('操作失败！', function(index){layer.close(index);});
			}
		});
});
</script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jQuery.jqzoom.fdj.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.imagezoom.min.js"></script>
</body>
</html>