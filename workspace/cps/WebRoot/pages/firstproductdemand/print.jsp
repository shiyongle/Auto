<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/";%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>需求明细</title>
<script type="text/javascript" language="javascript" src="${ctx}/js/jquery-1.8.3.min.js" ></script>
<script type="text/javascript" src="${ctx}/js/jquery.jqprint.js"></script>
<script>
function printQuot(){
	$("#out").jqprint();
}
</script>
</head>
<body style="width: 100%;">
	<div style="margin: 0 auto;"><input type="button" value="打印" id="print" onclick="printQuot();"></div>
	<div id="out" style="margin: 0 auto;">
			<table style="border:0px solid #000; text-align:center;" cellspacing="0" align="center">
				<tr>  
					<td height="50" colspan="3">
					    <div id="companyTitle" style="font-size: 28px;text-align: center; font-weight: bold; float: left;">服务平台</div>
					</td>
				</tr>
				<tr>
					<td height="36"  style="border:1px solid #000;border-right:0;border-bottom:0">需求编号</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">11111111111111111111</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">需求名称</td>
					<td style="border:1px solid #000;border-bottom:0">2222222222222222222</td>
				</tr>
				<tr>
					<td height="36" style="border:1px solid #000;border-right:0;border-bottom:0">&nbsp;需求状态</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">3333333333333333</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">是否制样</td>
					<td style="border:1px solid #000;border-bottom:0">4444444444444444</td>
				</tr>
				<tr>
					<td height="36" style="border:1px solid #000;border-right:0;border-bottom:0">&nbsp;制造商</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">5555555555555555</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">订单数量</td>
					<td style="border:1px solid #000;border-bottom:0">66666666666666666666666</td>
				</tr>
				<tr>
					<td height="36" style="border:1px solid #000;border-right:0;border-bottom:0">入库日期</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">777777777777777777</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">发货日期</td>
					<td style="border:1px solid #000;border-bottom:0">8888888888888888</td>
				</tr>
				<tr>
					<td height="36" style="border:1px solid #000;border-right:0;border-bottom:0">联系人</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">99999999999999</td>
					<td style="border:1px solid #000;border-right:0;border-bottom:0">联系电话</td>
					<td style="border:1px solid #000;border-bottom:0">2322222222222222222</td>
				</tr>
				<tr>
					<td height="70" width="300px" style="border:1px solid #000;border-right:0">需求描述</td>
					<td style="border:1px solid #000" colspan="3">哈哈哈哈哈哈哈</td>
				</tr>
				<tr>
					<td height="300" style="border:1px solid #000;border-right:0">附件信息</td>
					<td style="border:1px solid #000" colspan="3">哈哈哈哈哈哈哈<br>
																     哈哈哈哈哈哈哈<br>
															      哈哈哈哈哈哈哈<br>
															      哈哈哈哈哈哈哈<br>
															      哈哈哈哈哈哈哈<br>
															    哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															       哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
															      哈哈哈哈哈哈哈<br>  
					</td>
				</tr>
			</table>
		
	</div>
</body>
</html>