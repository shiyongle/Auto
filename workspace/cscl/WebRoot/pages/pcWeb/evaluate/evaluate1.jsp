<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>评价(司机评价货主)</title>
<!-- Bootstrap -->
<link href="${ctx}/pages/pcWeb/css/bootstrap.min.css" rel="stylesheet">
<link href="${ctx}/pages/pcWeb/css/common.css" rel="stylesheet">
<link href="${ctx}/pages/pcWeb/css/evaluate.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<script src="${ctx}/pages/pcWeb/js/jquery-1.12.3.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/bootstrap.min.js"></script>
<script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<script src="${ctx}/pages/pcWeb/js/public.js" type="text/javascript"></script>
<script src="${ctx}/pages/pcWeb/js/evaluate.js"></script>
<!--[if lt IE 9]>
<script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
<script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
</head>
<body>
	<div class="jumbotron main">
	<!--用户信息-->
	<div class="main_top row">
		<div class="col-xs-3 col-sm-2 col-md-1">
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/car-ev.png"/>
		</div>
		<div class="col-xs-9 col-sm-5 col-md-4">
			<p>运单号:<span id="orderNumber">${orderNumber}</span></p>
			<p><span>${creatorName}</span></p>			
		</div>
	</div>
	
	<div class="main_bottom">
	<form id="evaluate_form">
		<input type="hidden"  value="${creatorName}"  name="name"><!-- 被评价人姓名 -->
		<input type="hidden"  value="${orderNumber}"  name="orderNum"><!-- 订单号 -->
    	<input type="hidden"  value="${ratingType}"  name="ratingType">
    	<input type="hidden"  value="${orderId}"  name="orderId">
		<div class="row">
		<div class="col-xs-12">
			<div class="grade"><font>0</font>分</div>
			<div class="man">满分好评</div>
			<div class="star">			
			<div>
				<span></span>
				<a id="evaluate_0" data-placement="top" title="请评价星级"></a>
			</div>
			
			<input type="hidden" name="ratingScore" id="hid_service"/><!--司机对货主评价总分 -->
			</div>			
		</div>
		</div>		
			
	
		<div class="evaluate_good">
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/evaluate_good.png"/>
			<span>好评</span>
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/checked.png"/>
			<input type="radio" name="estimate" id="evaluateType" >
		</div>
		<div class="evaluate_bad">
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/evaluate_bad.png"/>
			<span>差评</span>
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/checked.png"/>
		</div>
  		<div class="form-group">
    		<textarea id="mark" class="form-control" rows="3" name="remark" placeholder="快点来说点什么吧"></textarea>
  		</div>
  		<div class="form-group">
    		<span class="mr_s"></span>
    		<a href="#" class="btn btn-danger btn-a btn-submit">发表评价</a>
  		</div>	
  		</form>
	</div>
	</div>
</body>
</html>

<script>
$(document).ready(function(){
	$("#evaluateType").prop("checked",true).val("1");//默认好评
	/*好/差评切换*/
	$(".evaluate_good").click(function(){
		$(".evaluate_good img:nth-child(1)").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/evaluate_good.png");
		$(".evaluate_good img:nth-child(3)").css("display","block");
		$(".evaluate_good").css("border","2px solid #E44349");
		$(".evaluate_bad img:nth-child(1)").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/evaluate_bad.png");
		$(".evaluate_bad img:nth-child(3)").css("display","none");
		$(".evaluate_bad").css("border","2px solid #C5C5C5");
		$("#evaluateType").prop("checked",true).val("1");
	});
	$(".evaluate_bad").click(function(){
		$(".evaluate_bad img:nth-child(1)").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/evaluate_bad_red.png")
		$(".evaluate_bad img:nth-child(3)").css("display","block");
		$(".evaluate_bad").css("border","2px solid #E44349");
		$(".evaluate_good img:nth-child(1)").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/evaluate_good_gray.png")
		$(".evaluate_good img:nth-child(3)").css("display","none");
		$(".evaluate_good").css("border","2px solid #C5C5C5");
		$("#evaluateType").prop("checked",true).val("2");
	});
	
	/*星星评分*/
	var k=[-1,-1,-1];
	var list=0;
	//var star_n=0;
	var star_html='<img src="${ctx}/pages/pcWeb/css/images/evaluate/star_white_lg.png"/>';
	for(var i=0;i<5;i++){
		$(".star div span").after(star_html);
	}
	$(".star div img").mouseenter(function(){
		$(this).attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/star_gold_lg.png"); 
		$(this).prevAll().attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/star_gold_lg.png"); 
		$(this).nextAll().attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/star_white_lg.png"); 
	});
	$(".star div img").click(function(){
		//star_n=$(this).index();
		$(".man").hide();
		list=$(this).parent().index();	//第几个div，从0开始
		k[list]=$(this).index()-1;		//第几个星星,从0开始		
		$(".grade font").text(k[list]+1);
		if((k[list]+1)==5){
			$(".man").show();
		}
	});
	$(".star div").mouseleave(function(){
		$(this).find("img").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/star_white_lg.png"); 
		for(var i=0;i<3;i++){
			for(var j=0;j<=k[i];j++){
				$(".star div:eq("+i+")").find("img:eq("+j+")").attr('src',"${ctx}/pages/pcWeb/css/images/evaluate/star_gold_lg.png");		
			}
		}
	});
	
	
	/*表单验证提示*/	
	$(".btn-submit").click(function(){
		var fwtd=k[0]+1;//总分星级				
		$("#hid_service").val(fwtd);
		
			if($("#hid_service").val()==0){
				$("#evaluate_0").tooltip("show");
				setTimeout(function(){
					$("#evaluate_0").tooltip("hide");
				},1000);
				return false;
			}
		
// 		var orderNumber=$("#orderNumber").text();
		var data=$("#evaluate_form").serialize();
		alert(data)
		$.ajax({
			type:"post",
			url:"${ctx}/app/rating/saveRating.do",//评价保存接口
			data:data,
			dataType:"json",
			success:function(response){
			if(response.success=="true"){
				layer.msg("评价成功");
				location.href="evaluate_look.jsp";//订单查看界面
			}
			}
		});
		
	});
			
})
</script>