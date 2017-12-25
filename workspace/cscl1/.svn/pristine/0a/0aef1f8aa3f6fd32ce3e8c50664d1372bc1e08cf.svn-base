<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>好运券--钱包</title>
    <meta name="viewport" content="maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width,initial-scale=1.0"/>
    <link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet" type="text/css" >
    <link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/pages/pcWeb/css/wallet_luck_coupons.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/page.cscl.js" ></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/public.js"></script>
    <script type="text/javascript" src="${ctx}/pages/pcWeb/js/wallet_luck_coupons.js"></script>
</head>
<body>
	<div class="jumbotron">
		<div class="title">优惠券</div> 				 	
		<div class="main">
    	
    	<div class="left zt">状态：</div>
    	<div class="left" id="zt">
    	<a href="javascript:void(0)" class="btn btn-danger btn-a">全部</a>
    	<a href="javascript:void(0)" class="btn" >未使用</a>
    	<a href="javascript:void(0)" class="btn" >已使用</a>
    	<a href="javascript:void(0)" class="btn" >已过期</a>	
    	</div>
    	
    	</div>
    	
    	<div class="coupons_list">
    		<ul id="coupons">
<!--     			<li> -->
<!--     				<div class="line1"> -->
<!--     					<div class="left money text-center redClolr"> -->
<!--     						<div class="money_1">30</div> -->
<!--     						<div class="money_2">满200元使用</div> -->
<!--     					</div> -->
    					
<!--     					<div class="left tip"> -->
<!--     						<div class="tip_1">一路好运专用</div> -->
<!--     						<div class="tip_2">满200元可用，在线支付专享</div> -->
<!--     					</div> -->
    					
<!--     				</div> -->
    				
<!--     				<div class="line2"> -->
<!--     					<div class="left money text-center line2_left"> -->
<!--     						未使用 -->
<!--     					</div> -->
<!--     					<div class="left tip text-center line2_left"> -->
<!--     						有效期：2016-05-31至2016-06-31 -->
<!--     					</div> -->
<!--     				</div> -->
<!--     			</li> -->
    			
<!--     			<li> -->
<!--     				<div class="line1"> -->
<!--     					<div class="left money text-center redClolr"> -->
<!--     						<div class="money_1">30</div> -->
<!--     						<div class="money_2">满200元使用</div> -->
<!--     					</div> -->
    					
<!--     					<div class="left tip"> -->
<!--     						<div class="tip_1">一路好运专用</div> -->
<!--     						<div class="tip_2">满200元可用，在线支付专享</div> -->
<!--     					</div> -->
    					
<!--     				</div> -->
    				
<!--     				<div class="line2"> -->
<!--     					<div class="left money text-center line2_left"> -->
<!--     						未使用 -->
<!--     					</div> -->
<!--     					<div class="left tip text-center line2_left"> -->
<!--     						有效期：2016-05-31至2016-06-31 -->
<!--     					</div> -->
<!--     				</div> -->
<!--     			</li> -->
    			
    			
<!--     			<li class="over"> -->
<!--     				<div class="line1"> -->
<!--     					<div class="left money text-center redClolr"> -->
<!--     						<div class="money_1">30</div> -->
<!--     						<div class="money_2">满200元使用</div> -->
<!--     					</div> -->
    					
<!--     					<div class="left tip"> -->
<!--     						<div class="tip_1">一路好运专用</div> -->
<!--     						<div class="tip_2">满200元可用，在线支付专享</div> -->
<!--     					</div> -->
    					
<!--     				</div> -->
    				
<!--     				<div class="line2"> -->
<!--     					<div class="left money text-center line2_left"> -->
<!--     						已使用 -->
<!--     					</div> -->
<!--     					<div class="left tip text-center line2_left"> -->
<!--     						有效期：2016-05-31至2016-06-31 -->
<!--     					</div> -->
<!--     				</div> -->
<!--     			</li> -->
    			
<!--     			<li class="over"> -->
<!--     				<div class="line1"> -->
<!--     					<div class="left money text-center redClolr"> -->
<!--     						<div class="money_1">30</div> -->
<!--     						<div class="money_2">满200元使用</div> -->
<!--     					</div> -->
    					
<!--     					<div class="left tip"> -->
<!--     						<div class="tip_1">一路好运专用</div> -->
<!--     						<div class="tip_2">满200元可用，在线支付专享</div> -->
<!--     					</div> -->
    					
<!--     				</div> -->
    				
<!--     				<div class="line2"> -->
<!--     					<div class="left money text-center line2_left"> -->
<!--     						已过期 -->
<!--     					</div> -->
<!--     					<div class="left tip text-center line2_left"> -->
<!--     						有效期：2016-05-31至2016-06-31 -->
<!--     					</div> -->
<!--     				</div> -->
<!--     			</li> -->
    		</ul>
    	</div>
	</div>
<script>
var size=10;//一页显示多少条
var total;//总共多少条记录
var pageNum=1;//第几页
var key;//关键字
var url="${ctx}/app/coupons/load.do?pagesize="+size+"&pagenum="+pageNum;//好运券接口
var newurl="${ctx}/app/coupons/load.do?pagesize="+size+"&pagenum="+pageNum;
pageOrderSearch(total,size,url);//支付分页，传入总数量(total)，每页数量(size)
$(document).ready(function(){
	//点击切换状态
	$("#zt a").click(function(){
		$(this).addClass("btn-danger btn-a").siblings().removeClass("btn-danger btn-a");
		key=$(this).text();
// 		alert(key);
		switch(key){//
		case "全部":newurl=url;break;
		case "未使用":newurl=url+"&isUse="+0;break;
		case "已使用":newurl=url+"&isUse="+1;break;
		case "已过期":newurl=url+"&isOverDue="+1;break;
		default:break;		
		}	
// 		alert(newurl);
		pageOrderSearch(total,size,newurl);
	})
})

//页码配置
function pageOrderSearch(total,size,newurl){
	/*页面加载后*/
	$.ajax({
			type:"post",
			url:newurl,
			dataType:'json',
			success:function(response){
				total=response.data.length;//获取总数
				$("#coupons").empty();
				var html="";   				
				$.each(response.data, function(i,e){
					var type;//好运券状态
					var over;//好运券若已经使用或者过期
					switch(e.isUse){//
						case 0:type="未使用";break;
						case 1:type="已使用";break;
						default:break;		
					}	
					switch(e.isOverdue){
					   		case 0:type="未过期";break;
					  	 	case 1:type="已过期";over="over";break;
				       		default:break;		
					}
					html+='<li class="'+over+'">'+
    				'<div class="line1">'+
					'<div class="left money text-center redClolr">'+
						'<div class="money_1">'+e.dollars+'</div>'+
						'<div class="money_2">满'+e.compareDollars+'元使用</div>'+
					'</div>'+
					'<div class="left tip">'+
						'<div class="tip_1">一路好运专用</div>'+
						'<div class="tip_2">满'+e.compareDollars+'元可用，在线支付专享</div>'+
					'</div>'+					
					'</div>'+
					'<div class="line2">'+
					'<div class="left money text-center line2_left">'+type+'</div>'+
					'<div class="left tip text-center line2_left">有效期：'+e.startTimeString+'至'+e.endTimeString+'</div>'+
					'</div></li>';
				});
				$("#coupons").append(html);
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
  			url:newurl,
  			dataType:'json',
  			success:function(response){
  				total=response.data.length;//获取总数
				$("#coupons").empty();
				var html="";   				
				$.each(response.data, function(i,e){
					var type;//好运券状态
					var over;//好运券若已经使用或者过期
					switch(e.isUse){//
					case 0:type="未使用";break;
					case 1:type="已使用";break;
					default:break;		
					}	
					switch(e.isOverdue){
					   case 0:type="未过期";break;
					   case 1:type="已过期";over="over";break;
				       	default:break;		
					}
					html+='<li class="'+over+'">'+
    				'<div class="line1">'+
					'<div class="left money text-center redClolr">'+
						'<div class="money_1">'+e.dollars+'</div>'+
						'<div class="money_2">满'+e.compareDollars+'元使用</div>'+
					'</div>'+
					'<div class="left tip">'+
						'<div class="tip_1">一路好运专用</div>'+
						'<div class="tip_2">满'+e.compareDollars+'元可用，在线支付专享</div>'+
					'</div>'+					
					'</div>'+
					'<div class="line2">'+
					'<div class="left money text-center line2_left">'+type+'</div>'+
					'<div class="left tip text-center line2_left">有效期：'+e.startTimeString+'至'+e.endTimeString+'</div>'+
					'</div></li>';
				});
				$("#coupons").append(html);
				getHtmlLoadingAfterHeight();
  			}
  		});
      }
  });	
}
</script>
</body>
</html>