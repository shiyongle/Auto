<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>作品列表</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="${ctx}/pages/designschemes/css/product_list.css"
	rel="stylesheet" type="text/css" />
<link href="${ctx}/css/kkpager.css" rel="stylesheet" type="text/css" />
<script src="<c:url value='/js/_common.js'/>" type="text/javascript"></script>
<script src="${ctx}/pages/designschemes/js/product_list.js"
	type="text/javascript"></script>
<script src="${ctx}/js/kkpager.js" type="text/javascript"
	language="javascript"></script>
</head>

<body>

	<div id="container">
		<p class="p1">平台首页 > 用户中心 >作品管理</p>
		<div class="show_list_main">
			<div class="publish">
				<a class="publish_a" href="${ctx}/designschemes/publish.net">继续发布</a>
				<form action="" method="post" id="searchForm"
					onsubmit="return false">
					<input type="button" value="" class="searchButton"
						id="searchButton" onclick="productList(1)" /> <input type="text"
						id="searchKey" class="searchKey"
						name="searchKey" placeholder="搜索" />
				</form>
			</div>

			<div class="product_list">
				<ul>
					<!-- 设计作品列表 -->
				</ul>
			</div>

			<div id="kkpager"></div>

		</div>
	</div>

</body>
</html>