<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=0,width=device-width">
<meta name="format-detection" content="telephone=no,email=no,adress=no"/>
<title>查看评价(司机评价货主)</title>
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
			<p>运单号:<span>201512210001</span></p>
			<p><span>丁大力</span></p>			
		</div>
	</div>
	
	<div class="main_bottom">
		<div class="row">
		<div class="col-xs-12">
			<div class="grade"><font>0</font>分</div>
			<div class="man">满分好评</div>
			<div class="star">										
															
			</div>			
		</div>
		</div>	
		
		<div class="row">
		<div class="col-xs-12">
			<div class="valuate-list">
			<img src="${ctx}/pages/pcWeb/css/images/evaluate/evaluate_good.png"/>
			货主态度很好，沟通顺畅！
			<font>[2016-7-27]</font>
			</div>	
		</div>
		</div>		
	</div>
	</div>
</body>
</html>

<script>
$(document).ready(function(){	
		/*评分*/
		$(".grade font").text("${评分}");		
		for(var i=0;i<"${评分}";i++){//星星的数量
			$(".star").append('<img src="${ctx}/pages/pcWeb/css/images/evaluate/star_gold_lg.png"/>');
		}
		if("${评分}"==5){
			$(".man").show();
		}
		
		$.ajax({
			type:"post",
			url:"",			
			dataType:"json",
			success:function(response){
			if(response.success=="true"){
				$(".valuate-list").empty();
				var type;
				var html='<img src="${ctx}/pages/pcWeb/css/images/evaluate/evaluate_'+type+'.png"/>';
				if("好评"){
					type="good";					
				}
				else{
					type="bad";
				}
				html+='评价内容'+
					  '<font>['+response.data.时间+']</font>';
				$(".valuate-list").append(html);
			}
			}
		});
})
</script>