<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>历史订单--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/history_order.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/history_order.js"></script>
</head>
<body>
	
	<div class="jumbotron main">
		
		<!--时间-->
		<div class="row">
			<div class="col-sm-6">
				<form class="form-inline">
  					<div class="form-group">
   					<span>时间：</span>
    				<input type="text" class="form-control" id="time1" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'time2\')}'})" placeholder="开始时间">--
    				<input type="text" class="form-control" id="time2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'time1\')}'})" placeholder="结束时间">
  					</div>
				</form>
			</div>
			
			<div class="col-sm-6 time_recent">
				<div style="line-height: 30px">最近：</div>
				<div class="time_recent_check" id="recent_time">
				<a href="javascript:void(0)" data-val='1' id="day_3">三天</a>
				<a href="javascript:void(0)" data-val='2' id="day_7">一星期</a>
				<a href="javascript:void(0)" data-val='3' id="day_30">一个月</a>
				</div>
			</div>
		</div>
		
		
		<!--状态-->
		<div class="row">
			<div class="col-sm-12 time_recent" id="state_type">
				<div style="line-height: 30px">状态：</div>
				<div class="time_recent_check">
				<a href="javascript:void(0)" class="" data-type='4'>全部</a>
				<a href="javascript:void(0)" data-type='6'>已取消</a>
				<a href="javascript:void(0)" data-type='5'>已完成</a>
				</div>
			</div>
		</div>
 		<!--关键字搜索-->
		<!-- <div class="row">
			<div class="col-sm-12 time_recent">
				<form class="form-horizontal">
				<div class="form-group">	
					<div style="line-height: 30px">关键字：</div>
					<div>
      				<input type="text" class="form-control" id="search" name="search" placeholder="搜索全部订单">
    				</div>
					
					</div>
				</form>
			</div>
		</div>  -->
		
		<!--我的订单数据列表-->
		<div class="table-responsive">
		<table class="table table-hover" id="history_order_table"></table>
		<!--页码-->
		<div class="row">					
			<div class="page col-xs-12">
			<div id="PageCode"></div>
			</div>
		</div>
		</div>
	</div>
	
	
	

</body>
</html>

<script>
var size=15;//一页显示多少条
var total;//总共多少条记录
var url="${ctx}/pcWeb/order/pcWebcycleorder.do?status=4&pagesize="+size+"&pagenum=1";
var html_table_head='<tr class="table_head">'+
					'<td>用车时间</td>'+
					'<td>订单号</td>'+
					'<td>提货点</td>'+
					'<td>卸货点</td>'+
					'<td>金额</td>'+
					'<td>状态</td>'+
					'<td>操作</td>'+
					'</tr>';
		pageOrderSearch(total,size,url);//订单分页，传入总单数量(total)，每页数量(size)
		
$(function(){	
	
//时间日期搜索
	$("#time2").blur(function(){
		var time1=$("#time1").val();
		var time2=$("#time2").val();
		var newurl="${ctx}/pcWeb/order/pcWebcycleorder.do?status=0&pagesize="+size+"&pagenum=1"+"&loadedTimeBegin="+time1+"&loadedTimeEnd="+time2;
		pageOrderSearch(total,size,newurl);		
	})
	
//	最近天数选择,搜索
	$("#recent_time a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");//样式切换
		var search_key=$(this).data("val");
		var newurl="${ctx}/pcWeb/order/pcWebcycleorder.do?status=4&pagesize="+size+"&pagenum=1"+"&key="+search_key;
		pageOrderSearch(total,size,newurl);
		$("#state_type a").each(function(i,e){
			$(e).removeClass("active");
		});
	})
	
	
//	状态选择,搜索
	$("#state_type a").click(function(){
		$(this).addClass("active").siblings().removeClass("active");//样式切换
		var search_key=$(this).data("type");
		var newurl="${ctx}/pcWeb/order/pcWebcycleorder.do?pagesize="+size+"&pagenum=1"+"&status="+search_key;
		pageOrderSearch(total,size,newurl);
		$("#recent_time a").each(function(i,e){
			$(e).removeClass("active");
		});
	})
	
//	搜索
	/* $("#search").blur(function(){
		var search_key=$(this).val();
		var key={searchKey:search_key};
		var newurl="${ctx}/pcWeb/order/cycleorder.do?status=0&pagesize="+size+"&pagenum=1"+"&key="+key;
		pageOrderSearch(total,size,newurl);
	})
	 */
})


//页码配置
function pageOrderSearch(total,size,url){
	/*页面加载后*/
	$.ajax({
			type:"post",
			url:url,
			dataType:'json',
			async:false,
			success:function(response){				
				total=response.total;//获取到订单的总数
				$("#history_order_table").empty();
				var html=html_table_head;   						
				$.each(response.data, function(i,e){
					var status,option;
					switch(e.status){//1:待付款 2:派车中 3:运输中 车主 :进行中 4:待评价 车主 已完成 5:已完成 车主 已完成 6:已取消 车主 已关闭
					case 1:status="待付款";
						   option='<a href="javascript:void(0)" class="pay_order" onclick="orderPay('+e.id+',event)">付　　款</a><br/>'+
						          '<a href="javascript:void(0)" class="cancel_order" onclick="orderCancel('+e.id+',event)">取消订单</a>';
						   break;
					case 2:status="派车中";option="";break;
					case 3:status="运输中";
						   option='<a href="javascript:void(0)" class="pay_add" onclick="orderPayAdd('+e.id+',event)">追加费用</a>';
						   break;
					case 4:status="待评价";
						   option='<a href="javascript:void(0)" class="order_evaluate" onclick="orderPj('+e.id+',event)">立即评价</a>';
						   break;
					case 5:status="已完成";
					option='<a href="javascript:void(0)" class="order_see_evaluate" onclick="orderSeePj(\''+e.number+'\',event)">查看评价</a>';
					   		break;
					case 6:status="已取消";option="";break;
					default:break;							
					}
					if(e.mileage!=null){//包天
						var jine='￥'+e.freight+'/'+e.mileage+'公里';
					}
					else{
						var jine='￥'+e.freight;
					}
					html+='<tr onclick=viewDetial('+e.id+')>'+
					'<td>'+e.createTimeString+'</td>'+
					'<td>'+e.number+'</td>'+
					'<td title="'+e.addressName+'">'+e.addressName.substr(0,6)+'......</td>'+
					'<td title="'+e.takeAddress+'">'+e.takeAddress.substr(0,6)+'......</td>'+
					'<td>'+jine+'</td>'+
					'<td class="redClolr">'+status+'</td>'+
					'<td>'+option+'</td>'+
					'</tr>';	
				});
				$("#history_order_table").append(html);
				getHtmlLoadingAfterHeight();
			}
		});
	
	$("#PageCode").empty().unbind();//移除分页绑定的相关事件
	$("#PageCode").createPage({//分页
        pageCount:Math.ceil(total/size),//向下取整
        current:1,
        backFn:function(p){//回调函数，p为点击的那页
            $.ajax({
    			type:"post",
    			url:"${ctx}/pcWeb/order/pcWebcycleorder.do?status=4&pagesize="+size+"&pagenum="+p,
    			dataType:'json',
    			success:function(response){
    				$("#history_order_table").empty();
    				var html=html_table_head;   				
    				$.each(response.data, function(i,e){
    					var status,option;
    					switch(e.status){//1:待付款 2:派车中 3:运输中 车主 :进行中 4:待评价 车主 已完成 5:已完成 车主 已完成 6:已取消 车主 已关闭
    					case 1:status="待付款";
    						   option='<a href="javascript:void(0)" class="pay_order" onclick="orderPay('+e.id+',event)">付　　款</a><br/>'+
    						          '<a href="javascript:void(0)" class="cancel_order" onclick="orderCancel('+e.id+',event)">取消订单</a>';
    						   break;
    					case 2:status="派车中";option="";break;
    					case 3:status="运输中";
    						   option='<a href="javascript:void(0)" class="pay_add" onclick="orderPayAdd('+e.id+',event)">追加费用</a>';
    						   break;
    					case 4:status="待评价";
    						   option='<a href="javascript:void(0)" class="order_evaluate" onclick="orderPj('+e.id+',event)">立即评价</a>';
    						   break;
    					case 5:status="已完成";
    					option='<a href="javascript:void(0)" class="order_see_evaluate" onclick="orderSeePj(\''+e.number+'\',event)">查看评价</a>';
				   				break;
    					case 6:status="已取消";option="";break;
    					default:break;							
    					}
    					if(e.mileage!=null){//包天
    						var jine='￥'+e.freight+'/'+e.mileage+'公里';
    					}
    					else{
    						var jine='￥'+e.freight;
    					}
    					html+='<tr onclick=viewDetial('+e.id+')>'+
    					'<td>'+e.createTimeString+'</td>'+
    					'<td>'+e.number+'</td>'+
    					'<td title="'+e.addressName+'">'+e.addressName.substr(0,6)+'......</td>'+
    					'<td title="'+e.takeAddress+'">'+e.takeAddress.substr(0,6)+'......</td>'+
    					'<td>'+jine+'</td>'+
    					'<td class="redClolr">'+status+'</td>'+
    					'<td>'+option+'</td>'+
    					'</tr>';	
    				});
    				$("#history_order_table").append(html);
    				getHtmlLoadingAfterHeight();
    			}
    		});
        }
    });	
}
		
function viewDetial(id){
	location.href="${ctx}/pcWeb/orderDetail/pcWebloadHuoOrderDetail.do?orderId="+id;//根据接口跳转到订单详情页面

}	

//付款
function orderPay(id,event){
	location.href="${ctx}/pcWeb/pcWebPay/pcWebPayMent.do?id="+id;
 	event.stopPropagation();//阻止冒泡,避免tr的点击事件

}

//取消付款
function orderCancel(id,event){
	location.href="${ctx}/pcWeb/order/cancelorder.do?orderId="+id;
 	event.stopPropagation();//阻止冒泡,避免tr的点击事件

}

//追加费用
function orderPayAdd(id,event){
 	event.stopPropagation();//阻止冒泡,避免tr的点击事件

}

//评价
function orderPj(id,event){
	location.href="${ctx}/pcWeb/rating/pcWebEvaluate.do?orderId="+id;//根据接口跳转到评价详情页面
 	event.stopPropagation();//阻止冒泡,避免tr的点击事件

}

//查看评价
function orderSeePj(number,event){
	location.href="${ctx}/pcWeb/rating/pcWebloadRating.do?orderNum="+number+"&ratingType="+0;//订单查看界面
 	event.stopPropagation();//阻止冒泡,避免tr的点击事件

}
</script>

