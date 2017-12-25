<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的业务-对账单</title>
<link rel="stylesheet" type="text/css" href="${ctx}/css/jquery.selectlist.css" />
<script src="${ctx }/js/_common.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery.selectlist.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/pdf/pdfobject.js"></script>
<style>
* {
	margin: 0px auto;
	padding: 0px;
}

input[type=text] {
	padding-left: 5px;
	outline: none;
	width: 300px;
	height: 30px;
	border: 1px solid lightgray;
}

#nav {
	width: 100%;
}

#container {
	height: 780px;
	background-color: white;
}

#container .p1 {
	font-size: 12px;
	line-height: 40px;
	height: 40px;
	width: 1080px;
	background-color:#f1f1f1;
}

/*tab标签*/
/*2016年3月18日09:30:38 HT*/
#require {
	width: 1080px;
	margin: 0 auto;
	margin-top: 10px;
}

#tbl1 {
	font-size: 12px;
	text-align: center;
	border-collapse: collapse;
	clear: both;
}

#tbl1 td {
	border-bottom: 2px solid #f76350;
}

a {
	text-decoration: none;
}

#pdflist {
	float: left;
}

#pdflist a {
	float: left;
	padding: 4px 10px;
	color: #3a3a3a;
	border-radius: 2px 2px 0 0;
	font-size: 17px;
	font-family: 'Microsoft Yahei';
}

#pdflist .active {
	color: #fff;
	background: #f76350;
}

.warning{
	color: red;
	text-align: center;
	padding-top: 20px;
	font-size: 20px;
	display: none;
}

.downLoad  input{
	width:20px;
	height:20px;
	background:url(../css/images/xiazai.png) 2px 2px no-repeat;
	border:none;
	cursor:pointer;
	float: left;
}

.downLoad a{
	display: block;
	float: left;
	font-size: 15px;
}

	
</style>
</head>

<body style="background-color:#f1f1f1;" onload="IFrameResize();">
	<div id="nav">
        <div id="container">
        	<p class="p1">平台首页&nbsp;&nbsp;&gt;&nbsp;&nbsp;我的业务&nbsp;&nbsp;&gt;&nbsp;&nbsp;对账单</p>
	        <div id="require">
			<table cellpadding="0" cellspacing="0" border="0" width="1080" id="tbl1" style="table-layout: fixed; ">   
			 <tr align="left">
			    <td colspan="5" style="border-bottom: 2px solid #f76350;">
				     <p id="pdflist">
				     	<c:forEach var="list" items="${list}" varStatus="status">
				     		<c:choose>
				     			<c:when test="${status.index ==0 }">
				     				<a href="javascript: void(0)"  fid="${list.ffileid}" class = "active" >${list.fmonth}</a>
				     			</c:when>
				     			<c:otherwise>
				     				<a href="javascript: void(0)"  fid="${list.ffileid}">${list.fmonth}</a>
				     			</c:otherwise>
				     		</c:choose>
						</c:forEach>
				     </p>  
			    </td>
			    <td>
			    </td>
			 	<td style="width: 130px;">
			 		<c:if test="${not empty select_list}">
			 		<select id="monthSelector">
                         <c:forEach var="select_list" items="${select_list}" >
	                          <option value="${select_list.ffileid}"  id="${select_list.fid}" >${select_list.fmonth}</option>
						</c:forEach>
                  	</select>
			 		</c:if>
			 	</td>
			    <td class="downLoad" style="width: 60px;">
			    	<a href="javascript:void(0)" onclick="download();">下载</a>
			    	<input type="button" value=""  onclick="download();"/>
			    </td>
			   </tr> 
			</table>
			</div>
			
		<div id="pdfviewer" style="width:100%; height: 700px;">
			<p class="warning">
				无法阅览,您还没有安装PDF阅读器！</br>
				<a href="https://get.adobe.com/cn/reader/" target="_blank">点击下载安装</a>
			</p>
			
		</div>
		</div>
	</div>
</body>
<script type="text/javascript">
var currentFid="";
$(document).ready(function(e){
	var $num1=$("#monthSelector option").size();
	$("#monthSelector").selectlist({
		zIndex: $num1,
		width: 120,
		height: 25
	});
	IFrameResize();
	
	showpdf($("#pdflist").find("a.active").attr("fid"));
	$("#pdflist").find("a").click(function(){
		$me= $(this);
		$("#pdflist").find("a").removeClass("active");
		$me.addClass("active");
		showpdf($me.attr("fid"));
		$("#monthSelector").find("input").val("");
	});
	$("#monthSelector").find("input").val("").attr("placeholder","其他");
	$("#monthSelector li").click(function(){
		$("#monthSelector").find("input").val($(this).text());
		$("#pdflist").find("a").removeClass("active");
		$("#monthSelector").find(".select-list").hide();
		showpdf($(this).attr("data-value"));
	});
});

function showpdf(fid){
	if(fid){
		currentFid = fid;
		if(PDFObject.supportsPDFs){
			PDFObject.embed("${ctx}/statement/viewpdf.net?fid=" + fid, "#pdfviewer");
		} else {
			$("#pdfviewer .warning").show();
			layer.alert("无法阅览,您还没有安装PDF阅读器！");
		}
	}
}


function download(){
	if(currentFid){
		window.open("${ctx}/productfile/downProductdemandFile.net?fid="+currentFid,"_blank");
	}
}

function IFrameResize() {
	getHtmlLoadingBeforeHeight();
	getHtmlLoadingAfterHeight();
}
</script>

</html>
