<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!-- <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" /> -->

<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!-- <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script> -->
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<!-- <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script> -->
<script
	src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	
<script type="text/javascript" src="${ctx}/js/calckits/calculator.js"></script>
<title>瓦楞纸箱内外径智能换算器</title>
<style>
/* 去掉数字加减箭头 */
input::-webkit-outer-spin-button,  
input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
}

/* .layui-layer-iframe .layui-layer-content{border-radius: 10px} */


.title {
	background-color: #d80c18;
	padding: 15px;
	color: #fff;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}


/*new*/
*{margin:0}
span{ display:inline-block; padding:0 22px; font-size:15px; font-weight:bold;}
.calculator{ width:967px; overflow:hidden; margin:0 auto;padding-top:15px;}
.calculator input,.calculator select{ width:150px; height:25px;border-radius:2px; background:none;border:1px solid #999; line-height:25px;}
.calculator table{ width:100%;}
#t1{ width:1000px;}
#t2 input{ background:#E1E1E1;}
.cc_check{ overflow:hidden; margin-left:50px; }
.cc_check span{margin-right:70px}

</style>


<script>

</script>
</head>

<body>
	<div class="content">
		<div>
			<p class="title">瓦楞纸箱内外径智能换算器</p>
		</div>
	</div>
	
	<div class="calculator">
<div id="t1">
		<div style=" float:left; width:220px;">请选择瓦楞类型：</div>
		<div style=" float:left; width:215px;">
				
						<select   name="Corrugatedtype"  id="changetype"   >
							<option id="BCC">BCC</option>
							<option id="EBC">EBC</option>
							<option id="BC">BC</option>
							<option id="BE">BE</option>
							<option id="C">C</option>
							<option id="B">B</option>
							<option id="E">E</option>
						</select>
        </div>

<div style=" float:left; width:215px;">请选择尺寸类型：</div>
		<div style=" float:left; width:215px;">			
						<select  name="size" id= "changesize"  >
							<option id="neijing">内径</option>
							<option id="zhijing">制径</option>
							<option id="waijing">外径</option>

						</select>
				</div>

<table>
            
			<tr height="50">
				<td width="215">请输入尺寸（长x宽x高）mm：</td>
				<td colspan="3"><input type="num"  placeholder="长"id="a" /><span>×</span><input type="num"  placeholder="宽"id="b"  /><span>×</span><input type="num"   placeholder="高"id="c" /></td>
			</tr>

		</table>
</div>
<div id="t2">
		<table>
			<tr>
				<td width="215">结果值(长x宽x高)mm：</td>
				<td >
					<table>
						<tr text-align="center">
                       
							<td><input type="num"  placeholder="长" id="a1" readonly/><span>×</span><input type="num" 
								placeholder="宽" id="b1" readonly /><span>×</span><input type="num" 
								placeholder="高" id="c1" readonly />（内径尺寸）
                                </td>
						</tr>
					</table>
                    <br/>
                    <table>

						<tr>
                       
							<td width="154"><input type="num"  placeholder="长" id="a2" readonly /><span>×</span><input type="num" 
								placeholder="宽" id="b2" readonly /><span>×</span><input type="num" 
								placeholder="高" id="c2" readonly />（制径尺寸）</td>
						</tr>
					</table>
                    
                    <br/>
                    <table>

						<tr>
                        
							<td width="154"><input type="num" 
								placeholder="长" id="a3" 
								readonly /><span>×</span><input type="num" 
								placeholder="宽" id="b3" readonly /><span>×</span><input type="num" 
								placeholder="高" id="c3" readonly />（外径尺寸）</td>
						</tr>
					</table>
                    
				</td>
				
			</tr>
			<tr height="50">
				<td width="215">展开面积：</td>
				<td><input type="num"   placeholder="面积"  id="area" readonly />&nbsp;m<sup>2</sup>&nbsp;</td>
			</tr>
		</table>

</div>
</div>
</body>
</html>