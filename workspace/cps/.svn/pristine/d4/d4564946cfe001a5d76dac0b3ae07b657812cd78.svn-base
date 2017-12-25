<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${ctx}/css/kkpager.css"  />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/pages/StarProduct/css/StarProductList.css" media="all" />
<script type="text/javascript" language="javascript" src="${ctx}/js/kkpager.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/pages/StarProduct/js/StarProductList.js"></script>
<title>明星产品内页</title>
<script>
$(document).ready(function(){
    var ftype="${requestScope.ftype}";
    $(".type").text(ftype);
    $("#ftype").val(ftype);  
 	 loadlist(1);
    aclick();
 	//搜索
	$("#searchButton").click(function() {
		loadlist(1);
		
	});
	
});
</script>
</head>

<body>
      <div id="head">
      <%@ include file="../all_head.jsp" %>           	
      </div>	
	<div class="box">
	<div class="top">
	<div class="top_t">
		<div class="tit">
		<span class="type">低压电气</span>
		<span class="number" id="procount"></span></div>
		<div class="topright">
			<form action="#" method="post" id="searchForm" onsubmit="return false">
				<input type="button" value="" class="searchButton" id="searchButton" /> 
				<input type="hidden" id ="ftype"  name="ftype"/>
				<input type="text" id="searchKey" class="searchKey" name="searchKey" placeholder="搜索" />
			</form>
		</div>
	</div>	
	<div class="top_b">
		<ul >			
			<li><a href="javascript:void(0)" >低压电气</a></li>
			<li><a href="javascript:void(0)" >汽摩配</a></li>
			<li><a href="javascript:void(0)" >鞋/服/箱包</a></li>
			<li><a href="javascript:void(0)" >家用电器</a></li>
			<li><a href="javascript:void(0)" >电子电器</a></li>
			<li><a href="javascript:void(0)" >五金制品业</a></li>
			<li><a href="javascript:void(0)" >设备制造</a></li>
			<li><a href="javascript:void(0)" >家具</a></li>
			<li><a href="javascript:void(0)" >文教用品及玩具</a></li>
			<li><a href="javascript:void(0)" >食品与饮料</a></li>
			<li><a href="javascript:void(0)" >健康、运动、娱乐器材</a></li>
			<li><a href="javascript:void(0)" >其它</a></li>			
	    </ul>
	</div>
	
	</div>
	<div class="list">	
	<ul id="Productlist">
<!-- 	<li> -->
<!-- 		<div class="litop"> -->
<!-- 			<img src="images/listlogo.png"></img> -->
<!-- 			<p>产品名称产品名称产品名称名称产品名称名称产品名称名称产品名称</p> -->
<!-- 		</div> -->
<!-- 		<div class="libottom"> -->
<%-- 			<div class="lileft"><img src="${ctx}/pages/StarProduct/images/listlogo.png"></img></div> --%>
<!-- 			<div class="liright"> -->
<!-- 				<p><span>东经包装:</span><span>昨天</span></p> -->
<!-- 				<p>发布在鞋类包装设计类</p> -->
<!-- 			</div> -->
<!-- 		</div>	 -->
<!-- 	</li> -->

	</ul>
	</div>
	 
	</div>
	<div id="kkpager"></div> 
    <div id="foot">
         <iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width=100% height="150" frameborder="0" id="allfoot"></iframe>
    </div>	
    
</body>
	
</html>