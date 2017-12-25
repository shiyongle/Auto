<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>作品详情</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link href="${ctx}/pages/StarProduct/css/product_detial.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx}/pages/StarProduct/js/product_detial.js"
	type="text/javascript"></script>
</head>

<body>
	<div id="head">
		<%@ include file="../all_head.jsp"%>
	</div>
	<div class="pro_top">
		<div class="pro_autor_img">
			<img src="/cps/pages/StarProduct/images/head.png" />
		</div>
		<div class="pro_tit">
			<div class="pro_tit01">${designscheme.fname}</div>
			<div class="pro_tit02">
				<i>by</i><font class="red" id="company">${designscheme.fsuppliername}：</font><span
					class="dateNum">${publishday}</span> 发布在<font
					class="red" id="proType">${designscheme.ftype}</font>
			</div>
		</div>
	</div>


	<div class="pro_main">
		<div class="pro_left">
			<div class="pro_ps">
				<div class="pro_psTxt">${designscheme.fdescription}</div>
				<a href="${ctx}/lineDesign/_create.net?fsourceid=${designscheme.fid}" class="require">提需求</a>
			</div>
			<div class="pro_content">
				<c:forEach var="proImg" items="${imglist}">
					<div class="pro_contentImg">
						<img
							src="${ctx}/productfilenol/getoriginFileByImageId.net?fid=${proImg.fid}" />
						<%-- <img src="${proImg.fpath}.substring(file.fpath.indexOf('/')"/> --%>
						<br/><p style="text-align:left; width: 710px;">${proImg.fdescription}</p>
					</div>
				</c:forEach>

			</div>
		</div>

		<div class="pro_right">
			<div class="ewm">
				<img src="/cps/pages/StarProduct/images/code.jpg" />
				<p>扫一扫二维码</p>
				<p>关注东经包装公众号</p>
			</div>
		</div>

		<div class="clear"></div>

		<div class="main_require">
			<a href="${ctx}/lineDesign/_create.net?fsourceid=${designscheme.fid}" class="main_requireA">提需求</a>
			<a href="javascript:window.close();" class="main_requireA">关闭页面</a>
		</div>

	</div>
	<div id="foot">
		<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width=100%
			height="150" frameborder="0" id="allfoot"></iframe>
	</div>

</body>
</html>
