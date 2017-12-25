<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="index_banner.css" media="all" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript"  language="javascript" src="index_banner.js"></script>
<title>产品包装解决方案</title>
</head>
<body>
    <div id="head">
	  <%@ include file="../head_white.jsp" %>   
    </div> 
	<div class="banner"><img src="${ctx}/css/images/banner_index_02.png"></img></div>
	<div class="content">
	<div class="tit1">
		<hr style="float:left" />
       <img src="${ctx}/css/images/spot.png"  style="float:left"/>
		<span>价值主张</span>	
	 <img src="${ctx}/css/images/spot.png"  style="float:left"/>
       	<hr style="float:right" />
	</div>
	<div class="content1">
	<p>通过平台整合大量设计、研发资源和人才，提供标准化的工具，通过标准流程，为全国各地的客户提供在线包装解决方案，打破传统包装企业的地域限制，使客户可在较短的时间内，以相对较低的成本获得满意的方案。</p>
	<img src="${ctx}/css/images/content2.png"></img>
</div>

<div class="tit2">
		<hr style="float:left" />
       <img src="${ctx}/css/images/spot.png"  style="float:left"/>
		<span>为客户解决的问题</span>	
	 <img src="${ctx}/css/images/spot.png"  style="float:left"/>
       	<hr style="float:right" />
	</div>
	<div class="content2">
	<ul>
		<li>包装物种类多 </li>
		<li>包装规格杂 </li>
		<li>管理难度大 </li>
		<li>保护性能差 </li>
		<li>过度包装 </li>
		<li>退赔损失</li>
	</ul>
	</div>
	
	</div>
	
	<div id="foot">
       	<iframe src="../all_foot.jsp" scrolling="no" frameborder="0" vspace="0" width=100% height="150"></iframe>
    </div>
</body>
</html>