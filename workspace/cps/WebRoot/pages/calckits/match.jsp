<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>

<meta charset="utf-8" />

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!-- 新 Bootstrap 核心 CSS 文件 -->
<!-- <link rel="stylesheet"href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" /> -->



<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<!-- <script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script> -->
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<!-- <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
<!-- 以下两个插件用于在IE8以及以下版本浏览器支持HTML5元素和媒体查询，如果不需要用可以移除 -->
<!-- <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script> -->
<script
	src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	
<script type="text/javascript" src="${ctx}/js/calckits/match.js"></script>
<title>瓦楞纸箱性能智能匹配器</title>
<style>

/* 去掉数字加减箭头 */
input::-webkit-outer-spin-button,  
input::-webkit-inner-spin-button {
    -webkit-appearance: none !important;
    margin: 0;
}

.content {
	width: 1000px;
	font: 14px 宋体;
	
}

.title {
	background-color: #d80c18;
	padding: 15px;
	color: #fff;
	border-top-left-radius: 10px;
	border-top-right-radius: 10px;
}
.label_title{line-height:32px;font-size:17px;float:left; margin-left:20px;}

/*new*/
*{margin:0;padding:0}
.match{ padding:10px; width:1000px; overflow:hidden; margin:0 auto;}
.match input,.match select{ width:150px; height:25px;border-radius:2px; background:none;border:1px solid #999; line-height:25px;}
.match input{ padding-left:10px;}
.match table tr{ height:50px;}
.text-center{ text-align:center; font-size:14px; line-height:28px;}
.col-md-2 input{ width:70px; text-align:center; padding-left:0}
.table01,.table02,.table03{ border:1px solid #ebebeb;margin-bottom:15px; width:600px;}
.table02,.table03{ padding-bottom:15px;}
#right input{background:#E1E1E1;}


</style>


<script>

</script>
</head>

<body>
	<div class="content">
		<div>
			<p class="title">瓦楞纸箱性能智能匹配器</p>
		</div>
	</div>

	<div class="match">
	<div id="t1">
		<table>
			<tr>
				<td>请输入内装物重量Kg：</td>
				<td><input  type="number"  placeholder="重量"  id="weight"
					/></td>
			</tr>
	
			<tr>
				<td>请输入纸箱内尺寸（长x宽x高）mm：</td>
				<td><input type="number"  placeholder="长"
					id="a" /></td>
				<td><h4>&nbsp;×&nbsp;</h4></td>
				<td><input type="number"  placeholder="宽"
					id="b"  /></td>
				<td><h4>&nbsp;×&nbsp;</h4></td>
				<td><input type="number"  placeholder="高"
					id="c" /></td>
			</tr>
			<tr>
				<td>请选择使用单瓦楞/双瓦楞：</td>
				<td>
					<div >
						<select  name="choose" id= "changechoose"  >
							<option id="">单瓦楞</option>
							<option id="">双瓦楞</option>
						
						</select>
					</div>
				</td>
			</tr>

		</table>
	</div>
	<div id="left" style="width:260px;float:left;margin-top:80px; height:145px; line-height:145px;">推荐结果：</div>
	
	<div id="right" style="width:600px;float:left">
		<table class="table01">
			<tr>
			<td class="text-center" width="200">瓦楞纸板最小综合定量(g/m2):</td>								
			<td><input  placeholder="综合定量"  id="ration" readonly /></td>								
			</tr>
		</table>

	<table class="table02">
	<tr> <td colspan="4"><p class="text-center" ><strong>恶劣储运环境下</strong></p></td></tr>
	
	<tr>
	<td class="col-md-2 text-center"  >纸箱代号<input       id="el1" readonly /></td>
	<td class="col-md-2 text-center"> 纸板代号<input     id="el2" readonly /></td>
	<td class="col-md-4 text-center">耐破强度不低于(kPa)<input     id="el3" readonly /></td>
	<td class="col-md-4 text-center"  >边压强度不低于(kN/m)<input     id="el4" readonly /></td>
	</tr>
	</table>
	<table  class="table03">
	<tr ><td colspan="4 text-center"><p class="text-center" ><strong>一般储运环境下</strong></p></td></tr>
	<tr>
	<td class="col-md-2 text-center">纸箱代号<input     id="yb1" readonly /></td>
	<td class="col-md-2 text-center"> 纸板代号<input     id="yb2" readonly /></td>
	<td class="col-md-4 text-center">耐破强度不低于(kPa)<input     id="yb3" readonly /></td>
	<td class="col-md-4 text-center" >边压强度不低于(kN/m)<input     id="yb4" readonly /></td>
	</tr>
	</table>
	</div>
  </div>
</body>
</html>