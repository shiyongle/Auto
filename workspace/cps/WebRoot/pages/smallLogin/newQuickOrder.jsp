<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%-- <%@ include file="/pages/smallLogin/allHead.html"%>
<%@ include file="/pages/smallLogin/allfoot.html"%> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>快速下单</title>

<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript"
	src="${ctx}/js/My97DatePicker/WdatePicker.js"></script>
<style>
*{
	margin:0px auto;
	padding:0px;
	}
	#container_right .lst{
	width:495px;
	height:30px;
	font-size:14px;
	border:1px solid #666;
	}
.tab li {
	height: 50px;
	width: 200px;
	line-height: 50px;
	float: left;
	text-align: center;
	font-family: "微软雅黑";
	font-size: 20px;
	cursor: pointer;
	color: #999;
}
#nav{
	width:100%;
	height:auto;
	}
#container {
	width: 900px;
	margin-top: 20px;
	height: 800px;
}

#container_right {
	width: 900px;
	margin-left: 20px;
	height: auto;
}

.formlist {
	width: auto;
	padding-top: 15px;
}

#container_right .td1 {
	text-align: justify;
	text-align-last: justify;
}

#productForm {
	padding-left: 70px;
}
#container_right .link{
	/* display:block; */
	width:280px;
	/* float:left; */
	height:30px;
	border:1px solid #666;
	margin:10px 10px 10px 0px; 
	}
	#container_right .am{
	display:block;
	float:left;
	width:65px;
	height:28px;
	border:1px solid #666;
	margin-top:6px;
	line-height:28px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:black;
	margin-left:20px;
	}
#container_right .pm{
	display:block;
	float:left;
	width:65px;
	height:28px;
	border:1px solid #666;
	margin-top:6px;
	line-height:28px;
	text-align:center;
	text-decoration:none;
	font-size:16px;
	color:black;
	margin-left:10px;
	}
	#container_right form table{
	line-height:50px;
	font-size:18px;
	font-family:"微软雅黑";
	color:#555;
	}
	#container_right .for_date{
	display:block;
	float:left;
	height:28px;
	padding-top:2px;
	width:28px;
	border:1px solid #666;
	border-left:none;
	margin-top:6px;
	cursor:progress;
	}
	.for_date img{
	border:none;
	}
	#container_right .date_{
	display:block;
	float:left;
	width:180px;
	height:30px;
	border:1px solid #666;
	border-right:none;
	margin-top:6px;
	}
	#container_right ._btn{
	cursor:pointer;
	width:180px;
	height:47px;
	font-size:20px;
	color:white;
	background-color:#CC0000;
	border:1px solid #CC0000;
	font-family:"微软雅黑";
	line-height:45px;
	margin-left:20px;
	}
	#container_right ._submit{
	cursor:pointer;
	width:180px;
	height:47px;
	font-size:20px;
	color:white;
	background-color:#CC0000;
	border:none;
	font-family:"微软雅黑";
	line-height:47px;
	}
	.formlist .public_st{
	width:705px;
	height:75px;
	padding-left:115px;
	padding-top:25px;
	}
	.formlist  input,.formlist  textarea{
		padding-left:5px;
		font-family:"宋体";
		font-size:14px;
	}
	.formlist span{
		color:#333333;
		font-size:16px;
	}
	#container_right ._color{
	border:2px solid #C00;
	}
	a{text-decoration:none;color:black;font-size:16px;} 
	a:hover {color: red}
	/* background:url(${ctx}/css/images/designOnline.png) 1px 1px no-repeat;background-size:100% 100%; */
</style>
</head>
<body style="background-color:#F0F0F0;">
	<div style="width:100%;height:260px;"><img alt="" width="100%" height="100%" src="${ctx}/css/images/designOnline.png"></div>
	<div id="nav">
		<div id="container">
			<div id="container_right">
				<div align="left"><a href="#">平台首页</a> ><a href="#"> 我的业务 ></a><a href="#"> 在线设计</a></div>
				<div class="formlist" style="background-color:#FFFFFF;margin-top:10px;">
					<div align="center">
						<img alt="" src="${ctx}/css/images/hx-l.png"
							style="float: left; border: none;"> <img alt=""
							src="${ctx}/css/images/hx-r.png"
							style="margin-top: 6px; float: right; border: none;"> <span
							style="font-family: 微软雅黑;color:#676464; font-size: 25px;"><b>需求信息</b></span>
						<div style="font-family: Arial;color:#8D8C8C; font-size: 16px;">Demand	information</div>
						<!--     <hr style="position:absolute;float:left;height:2px;border:none;border-top:2px double lightgray;width:300px" />
				<hr style="float:right;height:2px;border:none;border-top:2px double lightgray;width:300px" /> -->
					</div>
					<form id="productForm" >
						<table cellspacing="0" cellpadding="0" width="600px" style="display: block;">
							<tr>
								<td width="110"><span><b>需求名称</b></span><br/>
								<input type="text" class="lst" id=""	name="" /></td>
							</tr>
							<tr>
								<td>
									<span><b>请确认你的联系方式，我们才知道怎么联系你哦</b></span>
									<div><input placeholder="&nbsp;联系人"  type="text" class="link"  id=""	name="" /></div>
									<div><input placeholder="&nbsp;请输入手机号码"  type="text" class="link" id=""	name="" /></div>
									<input placeholder="&nbsp;输入手机验证码"  type="text" class="link"  style="width:150px;" id=""	name="" /><input  style="margin-top:5px;width:120px;height:30px;" type="button"  value="获取手机验证码" />
								</td>
							</tr>
							<tr>
								<td align="middle">
										<span style="float:left;"><b>是否制样：</b></span>
										<a 	href="javascript:void(0);" id="am" class="am" style="margin-top:10px;">是</a> 
										<a	href="javascript:void(0);" id="pm" class="pm" style="margin-top:10px;">否</a>
								</td>
							</tr>
							<tr>
								<td>
									<span style="float:left;"><b>交货日期</b>&nbsp;</span>
									<input type="text" id="jdate" class="date_"	name="deliverapply.farrivetime" readonly="readonly"	onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'${currentTime}'})" 	value="${farriveTime}" /> 
									<a class="for_date"><img onclick="WdatePicker({el:$dp.$('jdate'),minDate:'${currentTime}'})"	src="${ctx}/css/images/sj.png" /></a> 
									<a 	href="javascript:void(0);"  class="am">上午</a> 
									<a	href="javascript:void(0);"  class="pm">下午</a>
								</td>
							</tr>
							<tr>
								<td>
									<span><b>说说你的具体要求：</b></span>
									<textarea style="width:495px;height:300px;"></textarea>
								</td>
							</tr>
							<tr>
								<td><input type="file" id="file" multiple="true" style="display: none;" /> 
								<input type="button"	onclick="$('#file').click()"	style="outline: none; margin-top: 20px; background-color: #E4E3E3; text-align: middle; width: 70px; height: 25px; border-radius: 10px;" value="添加附件" /> 
								<span	style="font-family: 宋体; font-size: 12px;">&nbsp;最多可添加5个附件每个大小不超过10MB&nbsp;</span>
									<a href=""	style="text-decoration: none; color: red; margin-left: 30px;">上传遇到问题？</a>
								</td>
							</tr>
						</table>
					
						<p class="public_st">
							<input type="button" value="存草稿" class="_submit" id="saveButton" />
							<input type="button" value="提交" class="_btn"
								onclick="lastStep();" />
						</p>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
  //根据当前事件,设置【上午】/【下午】
	var currentDate = new Date();
	var currentHours = currentDate.getHours();
	if(currentHours <= 13){
		$(".am").addClass("_color");
		$(".am").siblings().removeClass("_color");
		$("#hours").val("am");
	}else{
		$(".pm").addClass("_color");
		$(".pm").siblings().removeClass("_color");
		$("#hours").val("pm");
	}
	
  //点击设置【上午】/【下午】
	$(".am").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		$("#hours").val("am");
	});
	$(".pm").click(function(){
		$(this).addClass("_color");
		$(this).siblings().removeClass("_color");
		$("#hours").val("pm");
	});
    </script>
</body>
</html>