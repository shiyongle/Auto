<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="index_banner.css" media="all" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript"  language="javascript" src="index_banner.js"></script>
<title>同城供应链物流</title>
</head>
<body>
 <div id="head">
	  <%@ include file="../head_white.jsp" %>   
    </div> 
	<div class="banner"><img src="${ctx}/css/images/banner_index_03.png"></img></div>
	<div class="content">
	<div class="tit1">
		<hr style="float:left" />
       <img src="${ctx}/css/images/spot.png"  style="float:left"/>
		<span>价值主张</span>	
	 <img src="${ctx}/css/images/spot.png"  style="float:left"/>
       	<hr style="float:right" />
	</div>
	<div class="content1">
	<p>在同城物流中，大部分车辆送货后空车回程，全程空载率超过50%，一路好运，大范围整合社会车辆和企业自有车辆，
通过高效的车货信息匹配和智能调度，充分利用大量返程空载车，提高车辆使用效率，降低物流成本。</p>
<img src="${ctx}/css/images/content3.png"></img>
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
		<li>物流费用高 </li>
		<li>车辆闲置 </li>
		<li>送货时间不准 </li>
		<li>找车难</li>
		<li>票据不规范 </li>
		<li>货损追索难</li>
	</ul>
	</div>
	
	</div>
	<div id="foot">
       	<iframe src="../all_foot.jsp" scrolling="no" frameborder="0" vspace="0" width=100% height="150"></iframe>
    </div>
</body>
</html>