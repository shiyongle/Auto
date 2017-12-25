<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/header.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的需求-需求详情</title>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${ctx}/js/layer/layer.js"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/LodopFuncs.js"></script>
<object id="LODOP" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width="0" height="0">
	<embed id="LODOP_EM" type="application/x-print-lodop" width="0" height="0"></embed>
</object>
<style>
	*{
		margin:0px auto;
		padding:0px;
		font-family:"微软雅黑";
		}
	#nav{width:1280px;}
	#container{
		width:1280px;
		height:870px;
		}
	#container2{
		width:1280px;
		height:870px;
		}
	.div1{
		width:1260px;
		font-size:18px;
		font-weight:bolder;
		padding-left:20px;
		height:40px;
		line-height:40px;
		}
	.div2{
		width:1280px;
		height:475px;
		}
	.div2 p{
		width:1230px;
		height:35px;
		line-height:35px;
		font-size:18px;
		font-weight:bolder;
		padding-left:50px;
		}
	.div3{
		height:230px;
		width:735px;
		margin-left:50px;
		+margin-left:25px;
		}
	.div3 p{
		height:40px;
		line-height:40px;
		font-size:18px;
		font-weight:bolder;
		}
	.div3 .div3_title{
		width:716px;
		height:30px;
		background-color:#EEEEEE;
		border:1px solid #999;
		margin-left:19px;
		+margin-left:9px;
		}
	.div3 .btn1{
		height:30px;
		border:none;
		background:none;
		cursor:default;
		width:695px;
		text-align:center;
		line-height:30px;
		}
	.div3 .div3_content{
		width:716px;
		height:150px;
		margin-left:19px;
		+margin-left:9px;
		text-align:center;
		position:relative;
		overflow-y:scroll;
		border:1px solid #999;
		border-top:none;
		border-collapse:collapse;
		}
	.div3 .div3_content table{
		border-collapse:collapse;
		}
	.div3 .div3_content table tr{
		border:1px solid #999;
		border-top:none;
		border-left:none;
		border-right:none;
		}
	.div3 .div3_content table tr td{
		border:none;
		}
	.div4{
		height:80px;
		width:1010px;
		padding-top:40px;
		padding-left:270px;
		}
	.div4 .print,.div4 .back{
		width:135px;
		height:40px;
		background-color:#C00;
		border:none;
		color:white;
		font-family:"微软雅黑";
		font-size:18px;
		margin-right:20px;
		+margin-right:10px;
		cursor:pointer;
		}

</style>
<script>
$(function(){
	var htm="";
	$.ajax({
				type : "POST",
				url : "${ctx}/productfile/loadByParentId.net?id="+"${productdemand.fid}",
				dataType:"json",
				async:false,
				success : function(response) {
					 $.each(response.data, function(i, ev) {
					 		var html =[
					 		          '<tr height="30">',
										'<td style="word-wrap:break-word;padding-left:10px;padding-right:10px;">',ev.fname,'</td>',
									   '</tr>'].join("");
							/* var tdhtml =[ev.fname,'</br>'].join("");
							alert(tdhtml); */
							$(html).appendTo("#sc_photo");
							htm=htm+ev.fname+"</br>";
							$("#td_photo").html(htm);
					 });
				}
		});
});

function print(){
		window.location.href="${ctx}/firstproductdemand/print.net?id="+"${productdemand.fid}";
}

var LODOP; //声明为全局变量 
     var LODOP; //声明为全局变量 
	function Preview() {		
		CreatePrintPage();
	  	LODOP.PREVIEW();		
	};
	function CreatePrintPage() {
		LODOP=getLodop(document.getElementById('LODOP'), document.getElementById('LODOP_EM'));  
        LODOP.ADD_PRINT_TEXT(30, 280, 500, 50, "需求详情信息");
        LODOP.SET_PRINT_STYLEA(1, "ItemType", 1);
        LODOP.SET_PRINT_STYLEA(1, "FontSize", 20);
        LODOP.SET_PRINT_STYLEA(1, "Bold", 1);
        LODOP.ADD_PRINT_TEXT(1050, 370, 200, 22, "第#页/共&页");//在距上边界1050象素，左 370，宽为200，高为22这样一个区域内打印页码及总页数
        LODOP.SET_PRINT_STYLEA(2, "ItemType", 2);
        LODOP.SET_PRINT_STYLEA(2, "HOrient", 1);
		LODOP.ADD_PRINT_TABLE(110, 33, 750, 900, document.getElementById("container2").innerHTML);
	};	

</script>
</head>

<body>
	<div id="nav">
	<!-- 
    	<div id="head" style="height:221px;width:1280px;">
        	<iframe src="list_head.html" frameborder="0" scrolling="no" width="1280px" height="315px" style="position:absolute;z-index:9999;"></iframe>
        </div>
    -->
    	<div id="container">
        	<div class="div1">我的需求&nbsp;&nbsp;&gt;&nbsp;&nbsp;需求详情</div>
            <div class="div2">
            	<p>需求信息</p>
                <table cellpadding="0" cellspacing="0" border="0" height="440" width="1270" style="font-size:16px;color:#666;">
                	<tr>
                    	<td align="right" width="165">需求编号：</td>
                        <td>${productdemand.fnumber}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">需求名称：</td>
                        <td >${productdemand.fname}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">需求状态：</td>
                        <td>${productdemand.fstate}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">是否制样：</td>
                        <td><c:if test="${productdemand.fiszhiyang==true}">是</c:if><c:if test="${productdemand.fiszhiyang==false}">否</c:if></td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">订单数量：</td>
                        <td>${productdemand.famount}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">入库日期：</td>
                        <td>${productdemand.foverdateString}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">发货日期：</td>
                        <td>${productdemand.farrivetimeString}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">联系人：</td>
                        <td>${productdemand.flinkman}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">联系电话：</td>
                        <td>${productdemand.flinkphone}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">制造商：</td>
                        <td>${productdemand.supplierName}</td>
                    </tr>
                    <tr>
                    	<td align="right" width="165">需求描述：</td>
                        <td style="padding-right:480px;">${productdemand.fdescription}</td>
                    </tr>
                </table>
            </div>
            <div class="div3">
            	<p>附件列表</p>
                <div class="div3_title">
                	<input type="button" value="附件名称" class="btn1"/>
                </div>
                <div class="div3_content">
                	<table id="sc_photo" cellpadding="0" cellspacing="0" border="0" width="695" style="table-layout:fixed;"></table>
                </div>
            </div>
            <div class="div4">
            	<input type="button" value="打印" class="print" onclick="Preview();"/>
            	<input type="button" value="返回" class="back" onclick="javascript :history.back(-1);" style="background-color:#F9918E;"/>
            </div>
        </div>
        <div id="container2" style="display:none;" >
	            <div class="div2">
	                <table cellpadding="0" cellspacing="0" border="0" height="440" width="1270" style="font-size:16px;color:#666;">
	                	<tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">需求编号：</td>
	                        <td>${productdemand.fnumber}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">需求名称：</td>
	                        <td >${productdemand.fname}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">需求状态：</td>
	                        <td>${productdemand.fstate}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">是否制样：</td>
	                        <td><c:if test="${productdemand.fiszhiyang==true}">是</c:if><c:if test="${productdemand.fiszhiyang==false}">否</c:if></td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">订单数量：</td>
	                        <td>${productdemand.famount}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">入库日期：</td>
	                        <td>${productdemand.foverdateString}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">发货日期：</td>
	                        <td>${productdemand.farrivetimeString}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">联系人：</td>
	                        <td>${productdemand.flinkman}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">联系电话：</td>
	                        <td>${productdemand.flinkphone}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">制造商：</td>
	                        <td>${productdemand.supplierName}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">需求描述：</td>
	                        <td style="padding-right:600px;">${productdemand.fdescription}</td>
	                    </tr>
	                    <tr height="35">
	                    	<td align="right" width="165" style="font-size:19px;color:black;">附件列表：</td>
	                        <td id="td_photo"></td>
	                    </tr>
	                </table>
	            </div>
        </div>
         
        <!-- 
    	<div id="foot" style="height:40px;width:1280px;">
        	<iframe src="all_foot.html" frameborder="0" scrolling="no" width="1280px" height="40px"></iframe>
        </div>
         -->
    </div>
</body>
</html>

