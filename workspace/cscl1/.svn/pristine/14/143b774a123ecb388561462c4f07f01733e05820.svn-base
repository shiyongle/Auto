<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>订单详情</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/OrderDetails.css"  rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/layer/layer.js" ></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>    
</head>
<body>


<div class="body">
    <div class="body_info jumbotron">
        <div>
            <div class="row" id="orderMsg01">
                <div class="col-xs-10  col-md-3 message">
                    <span>运单号：</span>
                    <span class="text-describe">${data.number}</span>
                </div>
                <div class="col-xs-12 col-md-3 message">
                    <span>用车时间：</span>
                    <span class="text-describe">${data.loadedTimeString}</span>
                </div>
                <div class="col-xs-12 col-md-3 message">
                    <span>下单时间：</span>
                    <span class="text-describe">${data.createTimeString}</span>
                </div>
                <div id="orderStatus">
                <span>
                    <span class="text-warning" id="orderTypeTxt"></span>
                </span>
                </div>
            </div>
        </div>
        <hr>
        
        <!--提货点卸货点图(多个卸货点)-->
        <div id="xieTooMuch" style="display:none">
        	<div>
            <ul id="addressUl">
<!--                 <span class="Address-list-Line"></span> -->
<!--                 <li> -->
<!--                     <span class="Address-list-box"> -->
<%--                         <img style="position: absolute;left: -38px;top: -5px;" src="${ctx}/pages/pcWeb/css/images/order_details/ti.png"> --%>
<!--                     </span> -->
<!--                     <span> -->
<!--                         <span> -->
<!--                             提货点： -->
<!--                         </span> -->
<!--                         <span class="text-describe"> -->
<!--                             XXXXX -->
<!--                         </span> -->
<!--                     </span> -->
<!--                 </li> -->

<!--                 <li> -->
<!--                     <span class="Address-list-box"> -->
<!--                         <span class="Address-list-dont"></span> -->
<!--                     </span> -->
<!--                     <span> -->
<!--                         <span> -->
<!--                             卸货点： -->
<!--                         </span> -->
<!--                         <span class="text-describe"> -->
<!--                             XXXXX -->
<!--                         </span> -->
<!--                     </span> -->
<!--                 </li> -->
            </ul>
            <ul>
                <li class="margin-bottom-10">
                    <span>
                        <span>
                            卸货点数：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>
                <li>
                    <span>
                        <span>
                            里程：
                        </span>
                        <span class="text-describe">
                            XXXXX
                        </span>
                    </span>
                </li>
            </ul>
        </div>
        <hr style="border-top: 1px dashed #eee">
        </div>
        
        <!--提货点卸货点信息（一个卸货点）-->
        <div id="xieOnlyOne">
        	<div class="row margin-bottom-22">
        		 <div class="col-xs-12  message">
                    <span>
                        <span>提货点：</span>
                        <c:forEach items="${data.takeList}" var="take">
                        <span class="text-describe">${take.linkman},${take.phone},${take.addressName}</span>
                        </c:forEach>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                    <c:forEach items="${data.recList}" var="rec">
                        <span>卸货点：</span>
                        <span class="text-describe">${rec.linkman},${rec.phone},${rec.addressName}</span>
                         </br>
                        </c:forEach>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                        <span>卸货点数：</span>
                        <span class="text-describe">${data.fopint}</span>
                    </span>
                </div>
                
                 <div class="col-xs-12  message">
                    <span>
                        <span>里程：</span>
                        <span class="text-describe">${data.mileage}</span>
                    </span>
                </div>
        	</div>
        </div>
        
        <hr style="border-top: 1px dashed #eee">
        <div>
            <div class="row">
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>货物类型：</span>
                        <span class="text-describe">${data.goodsTypeName}</span>
                    </span>
                </div>
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>所需车型：</span>
                        <span class="text-describe">${data.carSpecName }</span>
                    </span>
                </div>
            </div>
            <div class="row ">
                <div class="col-xs-12 col-md-6 message" id="goodW">
                    <span>
                        <span>货物重量：</span>
                        <span class="text-describe">${data.weight}</span>
                    </span>
                </div>
                <div class="col-xs-12 col-md-6 message" id="goodV">
                    <span>
                        <span>货物体积：</span>
                        <span class="text-describe">${data.volume}</span>
                    </span>
                </div>
            </div>
            <div class="row ">
                <div class="col-xs-12 col-md-6 message" id="goodL">
                    <span>
                        <span>货物长度：</span>
                        <span class="text-describe">${data.length}</span>
                    </span>
                </div>
                <div class="col-xs-12 col-md-6 message" id="other">
                    <span>
                        <span>其他：</span>
                        <span class="text-describe">${data.carTypeName}</span>
                    </span>
                </div>
            </div>
            <div class="row ">               
                <div class="col-xs-12 col-md-6 message">
                    <span>
                        <span>注意事项：</span>
                        <span class="text-describe">${data.fremark}</span>
                    </span>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px dashed #eee">
        <div>
            <div class="row margin-bottom-22">
                <div class="col-xs-6 col-md-4">
                    <span>
                        <span>增值服务：</span>
                        <span class="text-describe">${data.fincrementServe}</span>
                    </span>
                </div>
            </div>
        </div>
        <hr style="border-top: 1px dashed #eee">
        <div>
            <ul class="margin-bottom-18">
                <li>
                    <span class="text-warning">
                        <span>
                            应付款：
                        </span>
                        <span>
                             ${data.freight}
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            好运劵：
                        </span>
                        <span>
                              ${data.dollars}
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            追加款：
                        </span>
                        <span>
                            ${data.allCost}
                        </span>
                    </span>
                </li>
                <li>
                    <span class="text-warning">
                        <span>
                            实付款：
                        </span>
                        <span>
                         <c:out value="${data.freight-data.dollars}"></c:out>
                        </span>
                    </span>
                </li>
            </ul>
        </div>
        <hr>
        <div class="row">
            <div class="col-xs-12" style="margin-bottom: 20px; text-align: right;" id="orderOption">
<!--                 <button type="button" class="btn btn-danger">付款</button> -->
<!--                 <button type="button" class="btn ">取消订单</button> -->
            </div>
        </div>

    </div>
</div>


</body>
</html>
<script>
var orderTypeTxt=${data.type};
var orderStatus=${data.status};
var id=${data.id};
// var orderTypeTxt=1;
// var orderStatus=3;
var status="",orderButton=0;

$(function(){	
	//订单类型（右上角红色文字）		
	switch(orderTypeTxt){
	case 1: if(orderStatus==1){
				status="整车　待付款";orderButton=1;switchCase();break;
             }
	        if(orderStatus==2){
                 status="整车　派车中";orderButton=2;switchCase();break;
            }
	         if(orderStatus==3){
	        	 status="整车　运输中";orderButton=3;switchCase();break;
	         }
	         if(orderStatus==4){
	        	 status="整车　待评价";orderButton=4;switchCase();break;
	         }
	         if(orderStatus==5){
	        	 status="整车　已完成";orderButton=5;switchCase();break;
	         }
	case 2:
		     if(orderStatus==1){
		    	 status="零担　待付款";orderButton=6;switchCase();break;
             }
		     if(orderStatus==2){
	        	 status="零担　派车中";orderButton=7;switchCase();break;
	         }
	         if(orderStatus==3){
	        	 status="零担　运输中";orderButton=8;switchCase();break;
	         }
	         if(orderStatus==4){
	        	 status="零担　待评价";orderButton=9;switchCase();break;
	         }
	         if(orderStatus==5){
	        	 status="零担　已完成";orderButton=10;switchCase();break;
	         }
	
	case 3:
		    if(orderStatus==1){
		    	status="包天　待付款";orderButton=11;switchCase();break;
            }
		    if(orderStatus==2){
	        	 status="包天　派车中";orderButton=12;switchCase();break;
	         }
	         if(orderStatus==3){
	        	 status="包天　运输中";orderButton=13;switchCase();break;
	         }
	         if(orderStatus==4){
	        	 status="包天　待评价";orderButton=14;switchCase();break;
	         }
	         if(orderStatus==5){
	        	 status="包天　已完成";orderButton=15;switchCase();break;
	         }
	
/* 	case 1:status="整车　待付款";
	case 2:status="整车　运输中";switchCase();break;
	case 3:status="整车　已完成";switchCase();break;
	case 4:status="零担　待付款";switchCase();break;
	case 5:status="零担　运输中";switchCase();break;
	case 6:status="零担　已完成";switchCase();break;
	case 7:status="包天　待付款";switchCase();break;
	case 8:status="包天　运输中";switchCase();break;
	case 9:status="包天　已完成";switchCase();break; */	
	}
	$("#orderTypeTxt").text(status);
})

//switch-case里的操作
function switchCase(){
    if("${data.fopint}">1){				
		xieMore();
 	}	
	optionButton();
}

//有多个卸货点,显示提货点卸货点图
function xieMore(){
	$.ajax({
		type:"post",
		url:"${ctx}/pcWeb/order/pcWebDeatilByOrderId.do?type="+2+"&orderId"+id,//提货点和卸货点接口				
		dataType:"json",
		success:function(response){
		if(response.success=="true"){
// 			alert("开始查询地址啦");
				$("#xieOnlyOne").hide();
				$("#xieTooMuch").show();
				$("#addressUl").empty();
				
				var html='<span class="Address-list-Line"></span>'+
				'<li>'+
				'<span class="Address-list-box">'+
				'<img style="position: absolute;left: -38px;top: -5px;" src="${ctx}/pages/pcWeb/css/images/order_details/ti.png">'+
				'</span>'+
				'<span>'+
	                '<span>提货点：</span>'+
	               ' <c:forEach items="${data.takeList}" var="take">'+
                   ' <span class="text-describe">'+'${take.linkman}'+','+'${take.phone}'+','+'${take.addressName}'+'</span>'+
                    '</c:forEach>' +
	            '</span>'+
	            '</li>';
            
                $.each(response.data,function(i,e){
        			html+='<li>'+
        			'<span class="Address-list-box">'+
        			'<span class="Address-list-dont"></span>'+
        			'</span>'+
        			'<span>'+
        			'<span>卸货点：</span>'+
        			'<span class="text-describe">'+e.linkMan+','+e.linkPhone+','+e.addressName+'</span>'+
        			'</span>'+
        			'</li>';
        		})
        		$("#addrssUl").append(html);
		}		
		}
	});
}

//根据订单状态判断操作按钮，以及整车，零担类型显示（重量、体积、长度）
function optionButton(){
	//数组扩展，判断是否包含某个值
	Array.prototype.contains = function (obj) {
	    var i = this.length;
	    while (i--) {
	        if (this[i] === obj) {
	            return true;
	        }
	    }
	    return false;
	}
	
	if(orderTypeTxt==1){//整车显示
		$("#other").show();
	}
	
	if(orderTypeTxt==2){//零担显示
		$("#goodW,#goodV,#goodL").show();
	}	
	
	if([1,6,11].contains(orderButton)){//所有待付款状态的订单
		var optionHtml='<button type="button" class="btn btn-danger" onclick=pay('+id+')>付款</button>'+
						'<button type="button" class="btn " onclick=payCancel('+id+')>取消订单</button>';
		$("#orderOption").append(optionHtml); 
	}
	
	if([3,8,13].contains(orderButton)){//所有运输中状态的订单
		var optionHtml='<button type="button" class="btn btn-danger" onclick=payAdd('+id+')>追加款</button>';
		$("#orderOption").append(optionHtml); 
	}
	
	if([5,10,15].contains(orderButton)){//所有已完成的订单
		var optionHtml='<button type="button" class="btn btn-danger" onclick=orderPj('+id+')>立即评价</button>';
		$("#orderOption").append(optionHtml); 
	}
}

//付款
function pay(id){
	location.href="${ctx}/pcWeb/pcWebPay/pcWebPayMent.do?id="+id;
}

//取消订单
function payCancel(id){
//	location.href="${ctx}/pcWeb/order/pcWebcancelorder.do?orderId="id;
	layer.confirm('确定取消付款?', {icon: 3, title:'提示'}, function(index){
		$.ajax({
			type:"post",
			url:"${ctx}/pcWeb/order/cancelorder.do?orderId="+id,		
			dataType:"json",
			success:function(response){
			if(response.success=="true"){	
				layer.close(index);
				layer.msg("取消成功！");
				location.href="${ctx}/pages/pcWeb/individual_user/my_order.jsp";
			}
			}
		});
		  
	});
}

//追加款
function payAdd(id){
	 /* $.ajax({
	  	type:"post",
	  	url:"${ctx}/app/protocol/payInfoAndServer.do?orderId="+id+"&type="+1, 
	  	dataType:"json",
	  	success:function(response){
	  	if(response.success=="true"){ */
	  		location.href="${ctx}/pcWeb/addPay/pcWebaddPay.do?orderId="+id;
	 /*  	}
	  	}
	  });  */
}

//立即评价
function orderPj(id){
	location.href="${ctx}/pcWeb/rating/pcWebEvaluate.do?orderId="+id;//根据接口跳转到评价详情页面
}
</script>