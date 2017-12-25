<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>纸箱接单详情</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/cardDetail.css" />
<script type="text/javascript" src="${ctx}/js/_common.js"></script>
<script type="text/javascript" src="${ctx}/pages/productplan/js/cardDetail.js"></script>
<script type="text/javascript">
$(function(){
	function stateText(name){
		name = parseInt(name);
		switch(name){
		case 0: return '未接收';
		case 1: return '已接收';
		case 2:;
		case 3: return '已入库';
		default: return '';
		}
	}
	var dom = $('#orderState');
	dom.text(stateText(dom.text()));
	getHtmlLoadingBeforeHeight();
	getHtmlLoadingAfterHeight();
});
</script>
</head>
<body>
	<div id="header">
    	<p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">纸箱接单</a> &gt; <a href="#">详情</a>
        </p>
    </div>
    <div id="main">
    	<div class="content1 clear">
    		<h3>订单信息</h3>
    		<div class="detail">
    			<div class="detail_fstate">
    				订单状态：<span id="orderState">${productplan.fstate}</span>
    			</div>
    			<div class="detail_content">
    				<p>制造商订单号：<span>${productplan.fnumber}</span></p>
    				<div>
    					<p>采购订单号：<span>${productplan.fpcmordernumber}</span></p>
    					<p>客　户：<span>${productplan.cname}</span></p>
    					<p>数　量：<span>${productplan.famount}</span></p>
    				</div>
    				<div>
    					<p>产品：<span>${productplan.fproductname}</span></p>
    					<p>规格：<span>${productplan.fspec}</span></p>
    					<p>交期：<span>${productplan.farrivetime}</span></p>
    				</div>
    				<p class="address">地　址：<span>${productplan.faddress}</span></p>
    				<%-- <p>制 造 商：<span>${productplan.}</span></p> --%>
    			</div>
    			<dl class="detail_stock">
    				<dt>出入库:</dt>
    				<dd>入库：<span>${productplan.fstockinqty}</span></dd>
    				<dd>出库：<span>${productplan.fstockoutqty}</span></dd>
    			</dl>
    		</div>
    		<div class="files">
            	<h5>附件信息</h5>
            	<c:forEach  var="fileinfo" items="${filelist}"  >
	                <dl>
	                	<dt>
	                    	<img src="${ctx}/css/images/file.jpg" id="${fileinfo.fid}"/>
	                         <a href="${ctx}/productfile/downProductdemandFile.net?fid=${fileinfo.fid}"  target="_blank">下载</a>
	   					</dt>
	                    <dd>${fileinfo.fname}</dd>
	                </dl>
                </c:forEach>
               <%--  <dl>
                	<dt>
                    	<img src="${ctx}/css/images/file.jpg"/>
                        <a href="#">下载</a>
                    </dt>
                    <dd>外箱效果图.zip</dd>
                </dl> --%>
                <c:if test="${filelist!= null && fn:length(filelist) > 0}">
	                <a href="${ctx}/productfile/downProductdemandFiles.net?pfid=${productplan.fcustproduct}&ftype=ddinfo"  target="_blank" class="download">全部下载</a>
                </c:if>
            </div>
        </div>
    	<div class="content2">
    		<h3>产品信息</h3>
    		<div class="clear">
    			<ul>
    				<li>产品编码：<span>${productdef.fnumber}</span></li>
    				<li>产品名称：<span>${productdef.fname}</span></li>
    				<li>瓦楞楞型：<span>${productdef.ftilemodelid}</span></li>
    				<li>纸箱规格：
    					<c:if test="${productdef.fboxlength != null}">
	    					<span>${productdef.fboxlength} X ${productdef.fboxwidth} X ${productdef.fboxheight}</span>
						</c:if>
    				</li>
    				<li>出库规格：
    					<c:if test="${productdef.fmateriallength != null}">
	    					<span>${productdef.fmateriallength} X ${productdef.fmaterialwidth}</span>
    					</c:if>
    				</li>
    				<li>横向跑线公式：<span>${productdef.fhstaveexp}</span></li>
    				<li>套色精度：<span>${productdef.fchromaticprecision}</span></li>
    				<li>品质要求：<span>${productdef.fquality}</span></li>
    			</ul>
    			<ul>
    				<li>计量单位：<span>${productdef.forderunitid}</span></li>
    				<li>用料编码：<span>${productdef.fmaterialcode}</span></li>
    				<li>瓦楞结构：<span></span></li>
    				<li>纸板规格：
    					<c:if test="${productdef.fboardlength != null}">
	    					<span>${productdef.fboardlength} X ${productdef.fboardwidth}</span>
    					</c:if>
    				</li>
    				<li>跑线公式：<span>${productdef.fstavetype}</span></li>
    				<li>纵向跑线公式：<span>${productdef.fvstaveexp}</span></li>
    				<li>生产工艺：<span>${productdef.fworkproc}</span></li>
    				<li>成型方式：<span>${productdef.fmodelmethod}</span></li>
    			</ul>
    		</div>
    	</div>
	    <a href="javascript:history.back(-1);" class="retBtn">返回记录列表</a>
    </div>
</body>
</html>