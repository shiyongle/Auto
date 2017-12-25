<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";
%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单详情</title>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/css/orderDetail.css" />
 <script type="text/javascript" >
    $(function(){
    	window.getHtmlBodyHeight();
    	chooseDesign();
    });
       function chooseDesign(){
    	   var fstate = "${deliverapplyinfo.fstate}";
    	   if(fstate==6)$(".shipping_list").hide();
    	   $("ul.status li").last().addClass("last");
			$("ul.status li").each(function(i,index){
			if($(this).data("fstate")<=fstate) $(this).addClass("done");
			});
       }
      	function gobackOrderlist(){
      		parent.$('#c3d28df961a3c9ebfc7994361031186c').click();
			localStorage.gotoPage = "#tab_list :nth-child(3)";//订单记录
      	//parent.location.href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c";
        }
 </script>
</head>

<body >
	<div id="header">
    	<p>
        	<a href="#">平台首页</a> &gt; <a href="#">我的业务</a> &gt; <a href="#">在线下单</a> &gt; <a href="#">订单记录</a> &gt; <a href="#">详情</a>
        </p>
    </div>
    <div id="main">
    	<ul class="status">
    	<c:forEach  var="stateinfo" items="${deliverapplystate}"  varStatus="status">
 
    	    <li class=""  data-fstate="${stateinfo.fstate}"><em>${stateinfo.fstateValue}</em><span><fmt:formatDate value="${stateinfo.fcreatetime}" pattern="yyyy-MM-dd HH:mm"/></span>
   			<c:if test="${status.index==3}" >
   			 <ol class="shipping_list">
                	<li>总数：${deliverapplyinfo.famount}</li>
                    <li>已发：${deliverapplyinfo.foutQty}</li>
                    <li>剩余：${deliverapplyinfo.famount-deliverapplyinfo.foutQty}</li>
                </ol>
              </c:if> 	
             </li>
              </c:forEach>
            <div class="clear"></div>
        </ul>
        <div class="content1">
        	<h3>订单信息</h3>
            <ul class="detail">            
            	<li class="first">订单编号：<span>${deliverapplyinfo.fnumber}</span></li>
                <li class="detail_top">名称：<span>${deliverapplyinfo.fpdtname}</span></li>
                <li class="detail_top">采购订单号：<span>${deliverapplyinfo.fordernumber}</span></li>
                <li class="detail_top">规格：<span>${deliverapplyinfo.fpdtspec} cm</span></li>
                <li class="detail_top">数量：<span>${deliverapplyinfo.famount}</span></li>
                <li class="detail_middle">交货时间：<span>${deliverapplyinfo.farrivetimeString}</span></li>
                <li class="detail_bottom">交货地址：<span>${deliverapplyinfo.faddress}</span></li>
                <li class="detail_bottom">制 造 商：<span>${deliverapplyinfo.suppliername}</span></li>
            </ul>
            <div class="files">
            	<h5>附件信息</h5>
            	<c:forEach  var="fileinfo" items="${custproductfile}"  >
                <dl>
                	<dt>
                    	<img src="${ctx}/css/images/file.jpg" id="${fileinfo.fid}"/>
                         <a href="${ctx}/productfile/downProductdemandFile.net?fid=${fileinfo.fid}"  target="_blank">下载</a>
   					</dt>
                    <dd>${fileinfo.fname}</dd>
                </dl>
                </c:forEach>
                <div class="clear"></div>
                <a href="${ctx}/productfile/downProductdemandFiles.net?pfid=${deliverapplyinfo.fcusproductid}&ftype=ddinfo"  target="_blank">全部下载</a>
                   </div>
            <div class="clear"></div>
        </div>
        <div class="content2">
        	<h3>收货人信息</h3>
            <p>联 系 人：<span>${deliverapplyinfo.flinkman}</span></p>
            <p>联系电话：<span>${deliverapplyinfo.flinkphone}</span></p>
        </div>
        <a href="javascript:void(0)" class="retBtn" onclick="gobackOrderlist()" >返回订单列表</a>
    </div>
</body>
</html>
