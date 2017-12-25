<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="zh-CN">
<head>

<meta charset="utf-8" />

<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta http-equiv="X-UA-Compatible" content="IE=9" />
<meta name="renderer" content="webkit" />
<meta name="viewport" content="width=device-width, initial-scale=1" />


<script type="text/javascript" src="${ctx}/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx}/js/calckits/Unitconversion.js"></script>


<title>单位换算</title>
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

*{margin:0;padding:0}
body{ font-size:14px;}
#unit{ width:980px; height:150px;margin:0 auto;}
#unit ul{ width:980px; height:50px;}
#unit ul li{ list-style:none; float:left; margin-right:5px; height:50px;}
#unit ul li a{ text-decoration:none; color:#337ab7; display:block;  height:30px;padding:10px 15px;border-radius:4px; line-height:30px;}
#unit ul li a.on,#unit ul li a.on:hover{color:#fff;background:#337ab7;}
#unit ul li a:hover{ color:#23527c; background:#eee;}
#myTabContent{ width:820px; margin:0 auto; height:100px;}
#myTabContent input,#myTabContent select{  height:30px;border-radius:2px; background:none;border:1px solid #999; line-height:30px; margin-right:10px;}
#myTabContent input{width:300px; padding-left:10px;text-outline: 2px 2px #ff0000;} 
#myTabContent select{ width:180px; font-size:18px;font-family:Microsoft Yahei;color:#393939}
#myTabContent button{height:26px;border:1px solid #999;border-radius:2px; background:none; margin-right:10px; cursor:pointer;}
.glyphicon{ width:27px; height:25px; background:url(${ctx}/css/images/jh.png) center no-repeat; display:inline-block;}
.text-center{ text-align:center;}
#myTabContent div{ color:#777; font-size:20px; font-family:Microsoft Yahei; margin-top:10px;}
.text-muted { margin-top:10px;}
#length{ overflow:hidden; margin-top:10px;}

</style>
</head>
<body>

<div class="content">
		<div>
			<p class="title">瓦楞纸箱性能智能匹配器</p>
		</div>
</div>

<div id="unit">
<div>
		<ul id="myTab" class="">
			<li ><a href="#length" data-toggle="pill" class="on tab_a">长度</a></li>
			<li ><a href="#quality"  data-toggle="pill"  class="tab_a" >质量、力</a></li>
			<li><a href="#crush" data-toggle="pill" class="tab_a">环压、边压强度单位</a></li>
			<li><a href="#bursting" data-toggle="pill" class="tab_a">耐破强度</a></li>
			<li><a href="#stab" data-toggle="pill" class="tab_a">戳创强度</a></li>

		</ul>
</div>
		<div id="myTabContent" class="tab-content">
			<div class="tab-pane fade in active" id="length"  >
				<table   >
					<tr>
						<td><input class="form-control left-control" type="number" placeholder="请输入数值" id="lnumber"/></td>
						<td>
						<select class="form-control" name="lengthunit1" id="l1">
								<option >米(m)</option>
								<option >英寸(in)</option>
								<option >英尺(ft)</option>
						</select>
						</td>
						<td><button class="btn btn-default " ><span class="glyphicon glyphicon-transfer"></span></button></td>
						<td>
						<select class="form-control" name="lengthunit2" id="l2">
								<option >米(m)</option>
								<option >英寸(in)</option>
								<option >英尺(ft)</option>
						</select>
						</td>
					</tr>				
				</table>

	            <h3 class="text-muted text-center" id="lengthresult"><strong>
	            <label id="numbershow1" ></label>
	            <label id="unitshow1" ></label>
	            <label id="denghao" >=</label>
	            <label id="numbershow2" ></label>
	            <label id="unitshow2" ></label>	            
	            </strong></h3>
			</div>
			<div class="tab-pane fade" id="quality" style="display:none;">
				<table  >
					<tr>
						<td><input class="form-control left-control" type="number" placeholder="请输入数值" id="qnumber"/></td>
						<td>
						<select class="form-control" name="qualityunit1" id="q1">
								<option >千克(KG)</option>
								<option >牛顿(N)</option>
								<option >磅(lb)</option>
						</select>
						</td>
						<td><button class="btn btn-default "><span class="glyphicon glyphicon-transfer"></span></button></td>
						<td>
						<select class="form-control" name="qualityunit2" id="q2">
								<option >千克(KG)</option>
								<option >牛顿(N)</option>
								<option >磅(lb)</option>
						</select>
						</td>
					</tr>				
				</table>

	            <h3 class="text-muted text-center" id="lengthresult"><strong>
	            <label id="numbershowq1" ></label>
	            <label id="unitshowq1" ></label>
	            <label id="denghaoq" >=</label>
	            <label id="numbershowq2" ></label>
	            <label id="unitshowq2" ></label>
	            
	            </strong></h3>

			</div>
			<div class="tab-pane fade" id="crush" style="display:none">
				<table    >
					<tr>
						<td><input class="form-control left-control" type="number" placeholder="请输入数值" id="cnumber" /></td>
						<td>
						<select class="form-control" name="crushunit1" id="c1">
								<option >千牛/米(KN/m)</option>
								<option >磅/英寸(lbs/in)</option>
								
						</select>
						</td>
						<td><button class="btn btn-default " ><span class="glyphicon glyphicon-transfer"></span></button></td>
						<td>
						<select class="form-control" name="crushunit2" id="c2">
								<option >千牛/米(KN/m)</option>
								<option >磅/英寸(lbs/in)</option>
						</select>
						</td>
					</tr>				
				</table>

	            <h3 class="text-muted text-center" id="crushresult"><strong>
	            <label id="numbershowc1" ></label>
	            <label id="unitshowc1" ></label>
	            <label id="denghaoc" >=</label>
	            <label id="numbershowc2" ></label>
	            <label id="unitshowc2" ></label>	            
	            </strong></h3>
			</div>
			<div class="tab-pane fade" id="bursting" style="display:none">
				<table    >
					<tr>
						<td><input class="form-control left-control" type="number" placeholder="请输入数值" id="bnumber" /></td>
						<td>
						<select class="form-control" name="burstingunit1" id="b1">
								<option >千帕(Kpa)</option>
								<option >千克力/平方厘米(kgf/cm²)</option>
								<option >磅/平方英寸(Lbf/in²)</option>
						</select>
						</td>
						<td><button class="btn btn-default " ><span class="glyphicon glyphicon-transfer"></span></button></td>
						<td>
						<select class="form-control" name="burstingunit2" id="b2">
								<option >千帕(Kpa)</option>
								<option >千克力/平方厘米(kgf/cm²)</option>
								<option >磅/平方英寸(Lbf/in²)</option>
						</select>
						</td>
					</tr>				
				</table>

	            <h3 class="text-muted text-center" id="burstingresult"><strong>
	            <label id="numbershowb1" ></label>
	            <label id="unitshowb1" ></label>
	            <label id="denghaob" >=</label>
	            <label id="numbershowb2" ></label>
	            <label id="unitshowb2" ></label>	            
	            </strong></h3>
			</div>
			<div class="tab-pane fade" id="stab" style="display:none">
				<table    >
					<tr>
						<td><input class="form-control left-control" type="number" placeholder="请输入数值" id="snumber" /></td>
						<td>
						<select class="form-control" name="stabunit1" id="s1">
								<option >焦耳(J)</option>
								<option >千克*米(kg*cm)</option>
								<option >磅*英寸(LBS*INCH)</option>
								
						</select>
						</td>
						<td><button class="btn btn-default " ><span class="glyphicon glyphicon-transfer"></span></button></td>
						<td>
						<select class="form-control" name="stabunit2" id="s2">
								<option >焦耳(J)</option>
								<option >千克*米(kg*cm)</option>
								<option >磅*英寸(LBS*INCH)</option>
						</select>
						</td>
					</tr>				
				</table>

	            <h3 class="text-muted text-center" id="stabresult"><strong>
	            <label id="numbershows1" ></label>
	            <label id="unitshows1" ></label>
	            <label id="denghaos" >=</label>
	            <label id="numbershows2" ></label>
	            <label id="unitshows2" ></label>	            
	            </strong></h3>
			</div>

		</div>
	</div>
</body>
</html>