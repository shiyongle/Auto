<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>常用订单--一路好运</title>
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" />
<link href="${ctx}/pages/pcWeb/css/common_order.css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
<script type="text/javascript" src="${ctx}/pages/pcWeb/js/common_order.js"></script>
</head>
<body>
	
	<div class="jumbotron main">
		
		<!--时间-->
		<div class="row">
			<div class="col-sm-12">
				<form class="form-inline">
  					<div class="form-group">
   					 	<span>时间：</span>
    					<input type="text" class="form-control" id="time1" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'time2\')}'})" placeholder="开始时间">--
    					<input type="text" class="form-control" id="time2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'time1\')}'})" placeholder="结束时间">
  					</div>
				</form>
			</div>
		</div>

		<!--关键字搜索-->
		<div class="row">
			<div class="col-sm-12">
				<form class="form-inline">
				<div class="form-group ">	
					<span>搜索：</span>
      				<input type="text" class="form-control" id="search" name="search" placeholder="搜索全部订单">					
					</div>
				</form>
			</div>
		</div>
		
		<!--常用订单数据列表-->
		<div class="table-responsive">
		<table class="table table-responsive table-hover" id="common_order_table"></table>
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
var url="${ctx}/pcWeb/order/pcWebcycleorder.do?status=5&pagesize="+size+"&pagenum=1"+"&isCommon=1";
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
				$("#common_order_table").empty();
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
				$("#common_order_table").append(html);
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
    			url:'${ctx}/pcWeb/order/pcWebcycleorder.do?status=5&pagesize='+size+'&pagenum='+p+'&isCommon=1',
    			dataType:'json',
    			success:function(response){
    				$("#common_order_table").empty();
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
    				$("#common_order_table").append(html);
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