<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="index_banner.css" media="all" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript"  language="javascript" src="index_banner.js"></script>
<title>一站式交易平台</title>
</head>
<body>
<div id="head">
	  <%@ include file="../head_white.jsp" %>   
    </div> 
	<div class="banner"><img src="${ctx}/css/images/banner_index_04.png"></img></div>
	<div class="content">
	<div class="tit1">
		<hr style="float:left" />
       <img src="${ctx}/css/images/spot.png"  style="float:left"/>
		<span>价值主张</span>	
	 <img src="${ctx}/css/images/spot.png"  style="float:left"/>
       	<hr style="float:right" />
	</div>
	<div class="content1">
	<p>一站式在线采购平台连接了包装客户和包装供应商，客户可以在线下单，供应商在线接单，相当于包装行业的阿里巴
巴。同时，平台具有产品档案管理、客户管理、库存管理、标准化出库单据、业务管理、自动对账等多重功能。</p>
<img src="${ctx}/css/images/content4.png"></img>
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
		<li>采购下单繁琐</li>
		<li>包装图纸混乱</li>
		<li>交期不准</li>
		<li>需求响应慢</li>
		<li>多头采购混乱</li>
		<li>异常响应慢 </li>
		<li>产品档案缺失</li>
		<li>电话催单烦 </li>
		<li>订单状态失控 </li>
		<li>库存管理粗放 </li>
		<li>供应商管控难</li>
	</ul>
	</div>
	
	</div>
	<div id="foot">
       	<iframe src="../all_foot.jsp" scrolling="no" frameborder="0" vspace="0" width=100% height="150"></iframe>
    </div>
</body>
</html>