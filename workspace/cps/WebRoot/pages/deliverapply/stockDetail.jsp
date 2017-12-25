<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>备货详情</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx }/css/stockDetail.css" />
 <script type="text/javascript">
$(function(){
window.getHtmlBodyHeight();
    		$('.retBtn').click(function(){
    			history.back();
    		});
});

function lastStep(){
		parent.location.href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c";	
}
</script>
</head>

<body>
	<div id="header">
    	<p>
        	<span href="">平台首页</span> &gt; <span href="">我的业务</span> &gt; <span href="">在线下单</span> &gt; <span href="">订单记录</span> &gt; <span href="">备货详情</span>
        </p>
    </div>
    <div id="main">
    	<ul class="status">
            <li class="done"><em>提交订单</em><span>${mystock.fcreatetime.toString().substring(0,16)}</span></li>
	            <c:if test="${mystock.fstate==0 }">
	            <li class="last"><em>未接收</em><span></span></li>
	            </c:if>
	            <c:if test="${mystock.fstate!=0 }">
	            <li class="done last"><em>已接收</em><span></span></li>
	            </c:if>
            <div class="clear"></div>
        </ul>
        <div class="content1">
        	<h3>备货信息</h3>
            <ul class="detail">            
            	<li class="first">订单编号：<span>${mystock.fnumber }</span></li>
                <li class="detail_top">名称：<span>${custproduct.fname }</span></li>
                <li class="detail_top">采购编号：<span>${mystock.fpcmordernumber }</span></li>
                <li class="detail_top">规格：<span>${custproduct.fspec }</span></li>
                <li class="detail_top">计划数量：<span>${mystock.fplanamount }</span></li>
                <li class="detail_middle">首次发货：<span>${mystock.ffinishtime }</span></li>
                <li class="detail_middle">末次发货：<span>${mystock.fconsumetime }</span></li>
                <li class="detail_bottom">制 造 商：<span>${supplier.fname }</span></li>
            </ul>
            <div class="files">
            	<h5>附件信息</h5>
            	<c:forEach var="entry" items="${productdemandfile}">
	                <dl>
	                	<dt>
	                    	<img src="${ctx }/css/images/file.jpg"/>
	                        <a href="${ctx}/productfile/downProductdemandFile.net?fid=${entry.fid}"  target="_blank">下载</a>
	                    </dt>
	                    <dd>${entry.fname }</dd>
	                </dl>
               </c:forEach>
                <div class="clear"></div>
                   <a href="${ctx}/productfile/downProductdemandFiles.net?pfid=${mystock.fcustproductid}&ftype=ddinfo"  class="download"  target="_blank">全部下载</a>
            </div>
            <div class="clear"></div>
        </div>
        <a href="javascript:void(0);" class="retBtn"  onclick="lastStep()">返回订单记录</a>
    </div>
</body>
</html>