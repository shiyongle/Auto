<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>地址管理--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/address_manage.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/address_manage.js"></script>
</head>
<body>

	<div class="jumbotron main">
		<div class="row">
			<div class="col-xs-12">
				<h4>新增收货地址</h4>
			</div>
		</div>
		
		<form class="form-inline" id="apply_form" method="post">
		<!--收货人-->
		<div class="inline-margin">			
		<div class="form-group">
    		<span><font class="red">*</font>收货人姓名:</span>
    		<input type="text" class="form-control " name="linkman" id="reveiverName" placeholder="">
    		<input type="hidden" name="type" value="2" id="type">
    		<a id="reveiverName_err" data-placement="right" title="收货人姓名不能为空"></a>
  		</div>	
		</div>
  		
  		<!--所在地-->
  		<div class="inline-margin">			
<!-- 		<div class="form-group"> -->
<!--     		<span><font class="red">*</font>所在地区:</span> -->
<!--     		<input type="text" class="form-control" value="浙江温州" name="address" id="address" placeholder=""> -->
<!--     		<a id="address_err" data-placement="right" title="所在地区不能为空"></a> -->
<!--   		</div>	 -->
		</div>
  		<!--<div class="inline-margin">		
  		<div class="form-group">
    		<span><font class="red">*</font>所在地区:</span>
    		<select class="form-control" id="address1">
    			<option value="1">中国大陆</option>
    		</select>
    		<select class="form-control" id="address2">
    			<option value="1">请选择省份市区</option>
    			<option value="2">温州</option>
    		</select>
    		<a id="address2_err"  data-placement="right" title="请选择省份市区"></a>
  		</div>
  		</div>-->
  		
  		<!--详细地址-->
  		<div class="inline-margin">		
  		<div class="form-group" id="zheng">
    		<span for="exampleInputName2"><font class="red">*</font>详细地址:</span>	
<!--     		<textarea name="street" id="street" class="form-control" rows="3" placeholder="建议你如实填写详细收货地址，例如街道名称"></textarea> -->
    		<input type="text" class="form-control map"  name="linkaddress" id="address_th" placeholder="请在地图上选择详细地址" readonly style="width:400px">
    		<input type="hidden"  name="longitude" id="zheng_address_th_lng">
			<input type="hidden"  name="latitude" id="zheng_address_th_lat">
    		<div class="input-group-addon address_div" style="width:70px; float:right">
				<img src="${ctx}/pages/pcWeb/css/images/car/add.png" class="map_icon" width="14"/>
			</div>
    		<a id="address_th_err"  data-placement="right" title="地址不能为空"></a>
  		</div>
  		</div>
  		
  		<!--联系电话-->
		<div class="inline-margin">			
		<div class="form-group">
    		<span><font class="red">*</font>联系电话:</span>
    		<input type="text" class="form-control " name="linkphone" id="telephone" placeholder="">
    		<a id="telephone_err"  data-placement="right" title="电话不能为空"></a>
  		</div>	
		</div>
		
		<!--设为默认 隐藏掉-->
<!-- 		<div class="inline-margin">			 -->
<!-- 		<div class="form-group"> -->
<!--     		<span class="mr_s"></span> -->
<!--     		<input type="checkbox" style="width: 20px;height: 20px;" name="acquiesce" class="checkbox-inline"> -->
<!--     		<span class="mr">设置为默认</span> -->
<!--   		</div>	 -->
<!-- 		</div> -->
		
		<!--设为默认-->
		<div class="inline-margin">			
		<div class="form-group">
    		<span class="mr_s"></span>
    		<a href="#" class="btn btn-danger btn-a" id="save" onclick="formValidate()">保存收货地址</a>
    		<a href="#" class="btn btn-danger btn-a" id="update" style="display:none" onclick="update()">修改收货地址</a>
  		</div>	
		</div>
  		
		</form>
		
		<div class="row">
			<div class="col-xs-12">
				<h4>已保存了<span id="total">0</span>条地址</h4>
			</div>
		</div>
		
		<!--收货地址数据列表-->
		<div class="table-responsive">  <!-- class="table-responsive"-->
		<table class="table table-hover" id="address_table"></table>
		<!--页码-->
		<div class="row">
			<div class="page col-xs-12">
			<div id="PageCode"></div>
			</div>
		</div>
		</div>
		
	</div>
	<!--显示地图 -->
	<div id="map_container">
		<div class="map_line1">
		<div class="map_tip">点击地图选择地址</div>
		<div class="map_close">关闭</div>
		</div>
		<%@ include file="/pages/pcWeb/map/map_gd.jsp"%>	
	</div>
	<div class="masker"></div>
</body>
</html>

<script> 
$(window).load(function(){
	var screenWidth = $(window).width();//当前窗口宽度  
	var screenHeight = $(window).height();//当前窗口高度  
	$(".masker").css({"height":screenHeight,"width":screenWidth});	
})

var loadAddress;
var size=5;//一页显示多少条
var total;//总共多少条记录
var html_table_head='<tr class="table_head">'+
'<td>收货人</td>'+
'<td>收货地址</td>'+
'<td>手机</td>'+
'<td>操作</td>'+
// '<td>默认</td>'+
'<td></td>'+
'</tr>'; 

loadAddress=$.ajax({
	type:"post",
	url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum=1",
	success:function(response){
		response=JSON.parse(response);
		if(response.success=="true"){				
		$("#total").text(response.total);//总共多少条地址
		total=$("#total").text();//总共
		$("#address_table").empty();
		var html=html_table_head; 
		$.each(response.data, function(i,e) {
			html+='<tr>'+
			'<td>'+e.linkMan+'</td>'+
			'<td>'+e.addressName+'</td>'+
			'<td>'+e.linkPhone+'</td>'+
			'<td>'+
			'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
			'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
			'</td>'+
//				'<td>默认地址</td>'+
			'<td></td>'+
			'</tr>';
		});
		$("#address_table").append(html);
		getHtmlLoadingAfterHeight();
		}
		
		
		//页码配置
	    $("#PageCode").createPage({
	        pageCount:Math.ceil(total/size),//向下取整
	        current:1,
	        backFn:function(p){//回调函数，p为点击的那页
	        	$.ajax({
	            	type:"post",
	        		url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum="+p,
	        		success:function(response){
	        			response=JSON.parse(response);
	        			if(response.success=="true"){				
	        			$("#total").text(response.total);//总共多少条地址
	        			$("#address_table").empty();
	        			var html=html_table_head; 
	        			$.each(response.data, function(i,e) {
	        				html+='<tr>'+
	        				'<td>'+e.linkMan+'</td>'+
	        				'<td>'+e.addressName+'</td>'+
	        				'<td>'+e.linkPhone+'</td>'+
	        				'<td>'+
	        				'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
	        				'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
	        				'</td>'+
// 	        				'<td>默认地址</td>'+
							'<td></td>'+
	        				'</tr>';
	        			});
	        			$("#address_table").append(html);
	        			getHtmlLoadingAfterHeight();
	        			}
	        		}
	        	});
	        }
	    });
	}
});


$(document).ready(function(){
// 	弹出地图
	$(document).on("click",".map_icon,.map",function(e){
		var h=e.pageY+100;
		$("#map_container").css({"top":h+"px"}).show();
		$(".masker").show();
	})
// 关闭地图
	$(".map_close").on("click",function(){
		$("#map_container").hide();
		$(".masker").hide();
	})
	
	
	
	
});

function formValidate(){
	/*表单验证提示*/
	var val = ["reveiverName","address_th","telephone"];
	for(var i=0;i<val.length;i++){
		var name=$("#"+val[i]).val();
		if(name==""||name==null){
			$("#"+val[i]).focus();
			$("#"+val[i]+"_err").tooltip("show");
			setTimeout(function(){
				$("#"+val[i]+"_err").tooltip("hide");
			},1000);
			return false;
		}
	}
	
	var tel=$("#telephone").val();
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
	if(!(re.test(tel))){
		layer.msg("手机格式错误！");
		return false;
	}
	
	$("#type").attr("disabled",false);
	/*ajax传送表单数据*/
	var goods_address=$("#apply_form").serialize();
	$.ajax({
		type:"POST",
		url:"${ctx}/app/insert/address.do",
		data:goods_address,
		success:function(response){
			$.ajax({
				type:"post",
				url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum=1",
				success:function(response){					
					response=JSON.parse(response);
					if(response.success=="true"){				
					$("#total").text(response.total);//总共多少条地址
					total=$("#total").text();//总共
					$("#address_table").empty();
					var html=html_table_head; 
					$.each(response.data, function(i,e) {
						html+='<tr>'+
						'<td>'+e.linkMan+'</td>'+
						'<td>'+e.addressName+'</td>'+
						'<td>'+e.linkPhone+'</td>'+
						'<td>'+
						'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
						'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
						'</td>'+
// 						'<td>默认地址</td>'+
						'</tr>';
					});
					$("#address_table").append(html);
					getHtmlLoadingAfterHeight();
					}
					
					
					//页码配置
				    $("#PageCode").createPage({
				        pageCount:Math.ceil(total/size),//向下取整
				        current:1,
				        backFn:function(p){//回调函数，p为点击的那页
				        	$.ajax({
				            	type:"post",
				        		url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum="+p,
				        		success:function(response){
				        			response=JSON.parse(response);
				        			if(response.success=="true"){				
				        			$("#total").text(response.total);//总共多少条地址
				        			$("#address_table").empty();
				        			var html=html_table_head; 
				        			$.each(response.data, function(i,e) {
				        				html+='<tr>'+
				        				'<td>'+e.linkMan+'</td>'+
				        				'<td>'+e.addressName+'</td>'+
				        				'<td>'+e.linkPhone+'</td>'+
				        				'<td>'+
				        				'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
				        				'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
				        				'</td>'+
// 				        				'<td>默认地址</td>'+
				        				'<td></td>'+
				        				'</tr>';
				        			});
				        			$("#address_table").append(html);
				        			getHtmlLoadingAfterHeight();
				        			}
				        		}
				        	});
				        }
				    });
				    $("#reveiverName").val("");
		 			$("#address_th").val("");
		 			$("#telephone").val("");
				}
			});			
		}
	});
}

/*删除地址*/
function deleteAddress(_id){
	$.ajax({
		type:"POST",
		url:"${ctx}/app/appaddress/delete.do?id="+_id,
		async:false,
		success:function(response){				
				//提交成功
			$.ajax({
				type:"post",
				url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum=1",
				success:function(response){
					response=JSON.parse(response);
					if(response.success=="true"){				
					$("#total").text(response.total);//总共多少条地址
					total=$("#total").text();//总共
					$("#address_table").empty();
					var html=html_table_head; 
					$.each(response.data, function(i,e) {
						html+='<tr>'+
						'<td>'+e.linkMan+'</td>'+
						'<td>'+e.addressName+'</td>'+
						'<td>'+e.linkPhone+'</td>'+
						'<td>'+
						'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
						'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
						'</td>'+
// 						'<td>默认地址</td>'+
						'</tr>';
					});
					$("#address_table").append(html);
					getHtmlLoadingAfterHeight();
					}
					
					
					//页码配置
				    $("#PageCode").createPage({
				        pageCount:Math.ceil(total/size),//向下取整
				        current:1,
				        backFn:function(p){//回调函数，p为点击的那页
				        	$.ajax({
				            	type:"post",
				        		url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum="+p,
				        		success:function(response){
				        			response=JSON.parse(response);
				        			if(response.success=="true"){				
				        			$("#total").text(response.total);//总共多少条地址
				        			$("#address_table").empty();
				        			var html=html_table_head; 
				        			$.each(response.data, function(i,e) {
				        				html+='<tr>'+
				        				'<td>'+e.linkMan+'</td>'+
				        				'<td>'+e.addressName+'</td>'+
				        				'<td>'+e.linkPhone+'</td>'+
				        				'<td>'+
				        				'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
				        				'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
				        				'</td>'+
// 				        				'<td>默认地址</td>'+
				        				'</tr>';
				        			});
				        			$("#address_table").append(html);
				        			getHtmlLoadingAfterHeight();
				        			}
				        		}
				        	});
				        }
				    });
				}
			});
		}
	});
}

/*修改*/
function editAddress(_id){
	$("#save").hide();
	$("#update").show();
	$("#type").attr("disabled",true);
 	$.ajax({
 		type:"POST",
 		url:"${ctx}/app/appaddress/edit.do?id="+_id,
 		success:function(response){
 			response=JSON.parse(response);
 			$("#reveiverName").val(response.data.linkMan);
 			$("#address_th").val(response.data.addressName);
 			$("#telephone").val(response.data.linkPhone);
 			$("#zheng_address_th_lng").val(response.data.longitude);
 			$("#zheng_address_th_lat").val(response.data.latitude);
 			$("#update").attr("data-id",response.data.id);
 		}
 	})
 

}

/*保存修改*/
function update(){
	/*表单验证提示*/
	var val = ["reveiverName","address_th","telephone"];
	for(var i=0;i<val.length;i++){
		var name=$("#"+val[i]).val();
		if(name==""||name==null){
			$("#"+val[i]).focus();
			$("#"+val[i]+"_err").tooltip("show");
			setTimeout(function(){
				$("#"+val[i]+"_err").tooltip("hide");
			},1000);
			return false;
		}
	}
	
	var tel=$("#telephone").val();
	var re = /^1[3|4|5|7|8][0-9]\d{4,8}$/;//手机号码正则表达式
	if(!(re.test(tel))){
		layer.msg("手机格式错误！");
		return false;
	}
	
	var goods_address=$("#apply_form").serialize();
	var _id=$("#update").data("id");
	$.ajax({
 		type:"POST",
 		url:"${ctx}/app/appaddress/update.do?id="+_id,
 		data:goods_address,
 		success:function(response){
 			console.log(response);
 			$("#save").show();
 			$("#update").hide();
 			$.ajax({
				type:"post",
				url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum=1",
				success:function(response){
					$("#reveiverName").val("");
		 			$("#address_th").val("");
		 			$("#telephone").val("");
					response=JSON.parse(response);
					if(response.success=="true"){				
					$("#total").text(response.total);//总共多少条地址
					total=$("#total").text();//总共
					$("#address_table").empty();
					var html=html_table_head; 
					$.each(response.data, function(i,e) {
						html+='<tr>'+
						'<td>'+e.linkMan+'</td>'+
						'<td>'+e.addressName+'</td>'+
						'<td>'+e.linkPhone+'</td>'+
						'<td>'+
						'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
						'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
						'</td>'+
// 						'<td>默认地址</td>'+
						'</tr>';
					});
					$("#address_table").append(html);
					getHtmlLoadingAfterHeight();
					}
					
					
					//页码配置
				    $("#PageCode").createPage({
				        pageCount:Math.ceil(total/size),//向下取整
				        current:1,
				        backFn:function(p){//回调函数，p为点击的那页
				        	$.ajax({
				            	type:"post",
				        		url:"${ctx}/app/query/loadAddress.do?"+"type=2&pagesize="+size+"&pagenum="+p,
				        		success:function(response){
				        			response=JSON.parse(response);
				        			if(response.success=="true"){				
				        			$("#total").text(response.total);//总共多少条地址
				        			$("#address_table").empty();
				        			var html=html_table_head; 
				        			$.each(response.data, function(i,e) {
				        				html+='<tr>'+
				        				'<td>'+e.linkMan+'</td>'+
				        				'<td>'+e.addressName+'</td>'+
				        				'<td>'+e.linkPhone+'</td>'+
				        				'<td>'+
				        				'<a href="javascript:void(0)" onclick="editAddress('+e.id+')">修改</a>　'+
				        				'<a href="javascript:void(0)" class="delete" onclick="deleteAddress('+e.id+')">删除</a>'+
				        				'</td>'+
// 				        				'<td>默认地址</td>'+
				        				'</tr>';
				        			});
				        			$("#address_table").append(html);
				        			getHtmlLoadingAfterHeight();
				        			}
				        		}
				        	});
				        }
				    });
				}
			});
 		}
 	})
}
</script>
