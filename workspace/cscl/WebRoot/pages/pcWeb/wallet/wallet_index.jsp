<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_index.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_index.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bankcard.js"></script>
</head>
<body>
	<div class="jumbotron">
		<div class="row">
			<div class="col-sm-3 demo">
				<div class="text-center">账户余额（元）</div>
				<div class="text-center redNum" id="money"></div>
				<div class="text-center">
<%-- 					<a href="${ctx}/pages/pcWeb/wallet/wallet_recharge.jsp" class="btn btn-danger btn-a">充值</a> --%>
				<a  class="btn btn-danger btn-a" onclick="open_recharge()">充值</a>
				</div>
			</div>
		 	
			<div class="col-sm-3 demo">
				<div class="text-center" id="coupon">好运券（张）</div>
				<div class="text-center redNum" id="valCoupon">0</div>
				<div class="text-center">
					<a href="${ctx}/pages/pcWeb/wallet/wallet_luck_coupons.jsp" class="redClolr">查看</a>
				</div>
			</div>
			
			<div class="col-sm-4 demo col-sm-offset-1" id="bank_card">
				<div class="text-center">银行卡</div>
				<div id="bank_card_list">
<!-- 				<div class="bank_card"> -->
<!-- 					<img src="${ctx}/pages/pcWeb/css/images/wallet/zs_bank.png" class="img_bank"/> -->
<!-- 					<div class="mess_bank"> -->
<!-- 						<p>中国招商银行</p> -->
<!-- 						<p>**2256　储蓄卡|快捷</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- 				<div class="bank_card"> -->
<!-- 					<img src="${ctx}/pages/pcWeb/css/images/wallet/zs_bank.png" class="img_bank"/> -->
<!-- 					<div class="mess_bank"> -->
<!-- 						<p>中国招商银行</p> -->
<!-- 						<p>**2256　储蓄卡|快捷</p> -->
<!-- 					</div> -->
<!-- 				</div> -->
				</div>
				<div class="text-center redClolr">
					<a href="${ctx}/pages/pcWeb/wallet/wallet_card.jsp" class="redClolr">全部银行卡</a>
				</div>
			</div>
		</div>
		
		<!--交易记录-->
		<div class="row gray_bg">
			<div class="col-xs-12">
				交易记录
			</div>
		</div>
		
		<div class="row">
			<div class="col-xs-12">
				<form class="form-inline">
				<div class="row">
					<div class="col-sm-5 col-xs-12">
					<span>时间：</span>
					<input type="text" class="form-control" id="time1" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'time2\')}'})" placeholder="开始时间">--
    				<input type="text" class="form-control" id="time2" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'time1\')}'})" placeholder="结束时间">
					</div>
					
					<div class="col-sm-4 col-xs-12" id="pay_time">
					<span>周期：</span>
					<a href="javascript:void(0)" class="btn" data-time="1">一周</a>
					<a href="javascript:void(0)" class="btn" data-time="2">一月</a>
					<a href="javascript:void(0)" class="btn" data-time="3">一年</a>
					</div>
				
					<div class="col-sm-3 col-xs-12 redClolr" style="text-align:right">
<%-- 						　|　<a href="#"><img src="${ctx}/pages/pcWeb/css/images/wallet/search.png"/></a>　|　 --%>
						<a href="#" class="redClolr" >导出EXCLE</a>
					</div>
					
				</div>
				</form>
			</div>
		</div>
		
		<!--表格-->
		<div class=" row">
			<div class="col-xs-12">				
  			<table class="table table-hover" id="wallet_index_table">
<!--   				<tr> -->
<!--   					<td>时间</td> -->
<!--   					<td>金额（元）</td> -->
<!--   					<td>类型</td> -->
<!--   					<td>　</td> -->
<!--   				</tr> -->
  				
<!--   				<tr> -->
<!--   					<td>2016-05-30  20:10</td> -->
<!--   					<td class="redClolr">1000.00</td> -->
<!--   					<td>支出</td> -->
<!--   					<td><a href="javascript:void(0)" class="blue">查看</a></td> -->
<!--   				</tr> -->
  	
  				
  			</table>
  			<!--页码-->
			<div class="row">					
				<div class="page col-xs-12">
				<div id="PageCode"></div>
				</div>
			</div>
			</div>
		</div>
	</div>
</body>
</html>
<script>
var size=10;//一页显示多少条
var total;//总共多少条记录
var html_table_head='<tr><td>时间</td><td>金额（元）</td><td>类型</td><td>收支</td><td>　</td></tr>';
var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1";
pageOrderSearch(total,size,url);//支付分页，传入总数量(total)，每页数量(size)
$(function(){
	//余额
	$.ajax({
	type:"post",
	url:"${ctx}/pcWeb/pcWebPay/UsePayBalance.do",
	dataType:"json",
	success:function(response){
	if(response.success=="true"){
		var money=response.data;
		$("#money").text(money);
	} 
	}
});
	
	
	//查询好运卷数量
 	$.ajax({
		type:"post",
		url:"${ctx}/app/coupons/load.do?pagesize="+size+"&pagenum=1",
		dataType:"json",
		success:function(response){
		if(response.success=="true"){
		    $("#valCoupon").text(response.total);
		} 
	}
});
	//得到银行卡信息	
	$.ajax({
        type:"post",
        url:"${ctx}/pcWeb/bank/findByBankList.do?type=1",
		dataType:"json",
  		success:function(response){
  			$("#bank_card_list").empty();
  			console.log(response); 
    		//$("#money").text(data.money);	//账户余额
    		//$("#coupon").text(data.coupon);	//好运券数量
    		var html=""; 
//     		var cardbank;
//     		var cardtype;  
    		if(response.success=="true"){
    			
        		$.each(response.data, function(i,e) {
        			html+='<div class="bank_card">'+
        				  '<img style="width:20px; vertical-align:top;" src="${ctx}/bankIco/'+e.ficon+'" />'+
        					'<div class="mess_bank">'+
        					'<p>'+e.fbankName+'</p>'+
        					'<p>****'+e.fcardNumber+'</p>'+
        					'</div>'+
        					'</div>';
       			});
    		}

    		$("#bank_card_list").append(html);
  		}
    });
	
	//周期点击切换
	$("#pay_time a").click(function(){
		$("#time1").val("");
		$("#time2").val("");
		$(this).addClass("btn-danger btn-a").siblings("a").removeClass("btn-a btn-danger");
		var key=$(this).data("time");
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&key="+key;
		pageOrderSearch(total,size,url);
	})
	
	//按时间筛选交易记录
	$("#time1").blur(function(){
		$("#pay_time a").each(function(){
			$(this).removeClass("btn-a btn-danger");
		})
		var time1=$("#time1").val();
		var time2=$("#time2").val();
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&time1="+time1+"&time2="+time2;
		if(time1!=""&&time2!=""){	
		pageOrderSearch(total,size,url);
		}		
	});
	$("#time2").blur(function(){
		$("#pay_time a").each(function(){
			$(this).removeClass("btn-a btn-danger");
		})
		var time1=$("#time1").val();
		var time2=$("#time2").val();
		var url="${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number=1"+"&time1="+time1+"&time2="+time2;
		if(time1!=""&&time2!=""){			
			pageOrderSearch(total,size,url);
		}
	});
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
//				console.log(response);
				total=response.total;//获取到交易记录的总数
				$("#wallet_index_table").empty();
				var html=html_table_head;   				
				$.each(response.data, function(i,e){
					var type = '';
					switch(e.type){//
					case 1:type="支出";break;
					case 2:type="收入";break;
				    case 3:type="";break;
					default:break;							
					}
					//业务类型1下单支付2补交货款3运营异常4订单完成5提现6充值7转介绍奖励8货主退款
  					var fbusinessType ='';
  					switch(e.fbusinessType){
  						case 0:fbusinessType="余额调整";break;
  						case 1:fbusinessType="下单支付";break;
	  					case 2:fbusinessType="补交货款";break;
	  					case 3:fbusinessType="运营异常"; break;
	  					case 4:fbusinessType="订单完成";break;
	  					case 5:fbusinessType="提现";break;
	  					case 6:fbusinessType="充值"; break;
	  					case 7:fbusinessType="转介绍奖励";break;
	  					case 8:fbusinessType="货主退款"; break;
	  					case 9:fbusinessType="绑卡"; break;
	  					case 10:fbusinessType="订单运费调整";break;
	  					default:break;
  					}
  					//收支
  					var ftype ='';
  					switch(e.ftype){
  						case -1:ftype="支出";break;
  						case 1 :ftype="收入";break;
  						default:break;
  					}
					html+='<tr>'+
					'<td>'+e.createTimeString+'</td>'+
					'<td class="redClolr">'+e.famount+'</td>'+
					'<td>'+fbusinessType+'</td>'+
					'<td>'+ftype+'</td>'+
					//隐藏掉查看 			'<td><a href="javascript:void(0)" class="blue" class="view_detail">查看</a></td>'+
					'<td></td>'+
					'</tr>';
				});
				$("#wallet_index_table").append(html);
// 				getHtmlLoadingAfterHeight();
			}
		});
	
	$("#PageCode").empty().unbind();//移除分页绑定的相关事件
	$("#PageCode").createPage({//分页
      pageCount:Math.ceil(total/size),//向下取整
      current:1,
      backFn:function(p){//回调函数，p为点击的那页
          $.ajax({
  			type:"post",
  			url:"${ctx}/pcWeb/pcWebPay/UsePayAccountStatement.do?pagesize="+size+"&number="+p/* +"&key="+key */,
  			dataType:'json',
  			success:function(response){
  				$("#wallet_index_table").empty();
  				var html=html_table_head;   				
  				$.each(response.data, function(i,e){
  					var type ='';
  					switch(e.type){//
  					case 1:type="支出";						   
  						   break;
  					case 2:type="收入";break;
  					case 3:type="";
  						   break;
  					default:break;							
  					}
  					//业务类型1下单支付2补交货款3运营异常4订单完成5提现6充值7转介绍奖励8货主退款
  					var fbusinessType ='';
  					switch(e.fbusinessType){
  						case 0:fbusinessType="余额调整";break;
						case 1:fbusinessType="下单支付";break;
	  					case 2:fbusinessType="补交货款";break;
	  					case 3:fbusinessType="运营异常"; break;
	  					case 4:fbusinessType="订单完成";break;
	  					case 5:fbusinessType="提现";break;
	  					case 6:fbusinessType="充值"; break;
	  					case 7:fbusinessType="转介绍奖励";break;
	  					case 8:fbusinessType="货主退款"; break;
	  					case 9:fbusinessType="绑卡"; break;
	  					case 10:fbusinessType="订单运费调整";break;
	  					default:break;
  					}
  					//收支
  					var ftype ='';
  					switch(e.ftype){
  						case -1:ftype="支出";
  						break;
  						case 1 :ftype="收入";
  						break;
  						default:break;
  					}
  					html+='<tr>'+
						'<td>'+e.createTimeString+'</td>'+
						'<td class="redClolr">'+e.famount+'</td>'+
						'<td>'+fbusinessType+'</td>'+
						'<td>'+ftype+'</td>'+
    					//隐藏掉查看 			'<td><a href="javascript:void(0)" class="blue" class="view_detail">查看</a></td>'+
    					'<td></td>'+ 
    					'</tr>';     
  				});
  				$("#wallet_index_table").append(html);
//   				getHtmlLoadingAfterHeight();
  			}
  		});
      }
  });	
}

/*充值*/
function open_recharge(){
	location.href="${ctx}/pcWeb/pcWebPay/pcWebVoucher.do";	
}

</script>