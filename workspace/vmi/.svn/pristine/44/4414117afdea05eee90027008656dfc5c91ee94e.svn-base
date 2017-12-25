<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="extlib/resources/css/ext-all.css">
	<link rel="stylesheet" type="text/css"
	href="extlib/resources/css/ext-patch.css">
<!-- ext-all-gray.css \ext-all.css   \ext-all-neptune.css     extlib/resources/css/ext-all.css -->
<link rel="stylesheet" type="text/css" href="style/icons.css" />
<link rel="stylesheet" type="text/css"
	href="extlib/ux/grid/css/GridFilters.css" />
<link rel="stylesheet" type="text/css"
	href="extlib/ux/grid/css/RangeMenu.css" />
<script type="text/javascript" src="extlib/ext-all.js"></script>
<!-- ext-all-debug.js -->
<script type="text/javascript" src="extlib/locale/ext-lang-zh_CN.js"></script>
<script type="text/javascript" src="js/tools/file/js/jquery-1.6.2.min.js"></script>
<title>智能服务平台</title>
<style type="text/css">
.align-center {
	margin: 0 auto; /* 居中 这个是必须的，，其它的属性非必须  */
	width: 100%; /* 给个宽度 顶到浏览器的两边就看不出居中效果了 */
	height: 100%;
	background: white; /* 背景色 */
	/*text-align:center; /* 文字等内容居中 */
}
.x-grid-with-row-lines .x-grid-cell {
	border-width: 1px 0;
 	border-top-color: #fafafa;
 	border-bottom-color: #BDBDBD
}
.x-grid-with-col-lines .x-grid-cell {
       padding-right : 0 ;
       border-right : 1px solid #BDBDBD
}
.table1
{
position:relative;
width:2;
z-index:1;
float:right;
cursor:e-resize;
}

#loading-mask {
	Z-INDEX: 20000;
	LEFT: 0px;
	WIDTH: 100%;
	POSITION: absolute;
	TOP: 0px;
	HEIGHT: 100%;
	BACKGROUND-COLOR: white
}

#loading {
	PADDING-RIGHT: 2px;
	PADDING-LEFT: 2px;
	Z-INDEX: 20001;
	LEFT: 45%;
	PADDING-BOTTOM: 2px;
	PADDING-TOP: 2px;
	POSITION: absolute;
	TOP: 40%;
	HEIGHT: auto
}

#loading IMG {
	MARGIN-BOTTOM: 5px
}

#loading .loading-indicator {
	PADDING-RIGHT: 10px;
	PADDING-LEFT: 10px;
	BACKGROUND: white;
	PADDING-BOTTOM: 10px;
	MARGIN: 0px;
	FONT: 12px 宋体, arial, helvetica;
	COLOR: #555;
	PADDING-TOP: 10px;
	HEIGHT: auto;
	TEXT-ALIGN: center
}

.banner {
	font-family: "宋体";
	font-size: 12px;
	color: 4798D7;
}

#rTel{
	display: block;
	background: url(images/arrow.gif) no-repeat right center;
	padding-right: 15px;
	cursor: pointer;
	margin-right: 30px;
}
body>ul{
	list-style-type: none;
	position: absolute;
	z-index: 9999;
	/* left: 1183px; */
	top: 36px;
	padding: 0;
	background: #fff;
	padding: 5px;
	border: 1px solid #bbb;
	border-top: none;
	display: none;
}
body>ul li{
	height: 25px;
	line-height: 25px;
	font-weight: bold;
}
body>ul span{
	font-weight: normal;
}
.resizeRightDivClass
{
position:relative;
width:2;
z-index:1;
float:right;
cursor:e-resize;
 -moz-user-select:none;/*火狐*/
  -webkit-user-select:none;/*webkit浏览器*/
  -ms-user-select:none;/*IE10*/
  -khtml-user-select:none;/*早期浏览器*/
  user-select:none;
}
.resizeLeftDivClass
{
position:relative;
width:2;
z-index:1;
float:left;
cursor:e-resize;
 -moz-user-select:none;/*火狐*/
  -webkit-user-select:none;/*webkit浏览器*/
  -ms-user-select:none;/*IE10*/
  -khtml-user-select:none;/*早期浏览器*/
  user-select:none;
}
.overflowHiddenClass{
	white-space:nowrap;
	overflow:hidden;
}
</style>

</head>
<script type="text/javascript" src="js/MD5.js"></script>
<script type="text/javascript" src="js/index.js"></script>
<script type="text/javascript" src="js/ZeroClipboard.js"></script>
<script type="text/javascript">
	//解决Iframe IE加载不完全的问题
	function endIeStatus() {
	}
	Ext.EventManager.on(window, 'load', function() {
		setTimeout(function() {
			Ext.get('loading').remove();
			Ext.get('loading-mask').fadeOut({
				remove : true
			});
		}, 200);
	});
	
	if(Ext.isIE){
		
		(function(){
			if(!/*@cc_on!@*/0){
						
				return;
			}
			var e = "abbr,article,aside,audio,canvas,datalist,details,dialog,eventsource,figure,footer,header,hgroup,mark,menu,meter,nav,output,progress,section,time,video".split(',')
			,i=e.length;
			while(i--){
				document.createElement(e[i])
			}
			})()
	}
</script>

<!---<script type="text/javascript" src="js/test.js"></script>-->
<!---<script type="text/javascript" src="js/LoginForm.js"></script>  -->

<body>
	<div id="hello-win" class="x-hidden">
		<div id="hello-tabs">
			<img border="0" width="460" height="100"
				src="images/login_banner.png" />
		</div>
	</div>
	
	<!--显示loding区域-->
	<DIV id=loading-mask></DIV>
	<DIV id=loading>
		<DIV class=loading-indicator>
			<IMG style="MARGIN-RIGHT: 8px" height=32 src="images/ajax.gif"
				width=36 align="absMiddle" />正在初始化,请稍等...
		</DIV>
	</DIV>
	<div id="mydiv" class="align-center"></div>
	<div id="north" style="background-image:url('images/headbackpage.png');">
	
		<table border="0" cellpadding="0" cellspacing="0" width="100%"
			height="60">
			<!-- background="" -->
			<tr>
				<!-- 
		<td style="padding-left:15px"><img class="IEPNG"
			src="" /> 
				</td>-->
				<td style="padding-right: 5px">
					<table width="100%" border="0" cellpadding="0" cellspacing="3"
						class="banner">
						<tr align="right">
							<td><span id="rTime"></span></td>
						</tr>
						<tr align="right">
							<td>
								<table border="0" cellpadding="0" cellspacing="1">
									<tr>
									  <td><a id="rTel">联系方式</a></td>
										<td><div id="themeDiv"></div></td>
										<td>&nbsp;</td>
										<td><div id="configDiv"></div></td>
										<td>&nbsp;</td>

										<td><div id="closeDiv"></div></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<iframe id = "panelPrint" width="100%" height="100%" ></iframe>
	<ul id="vmi_concat">
		<li><span>订单服务1：</span>0577-55575296</li>
		<li><span>订单服务2：</span>0577-55575256</li>
		<li><span>设计服务：</span>0577-55575290</li>
		<li><span>技术支持：</span>0577-85393777</li>
		<li><span>客户投诉：</span>0577-85395555</li>
	</ul>
</body>

</html>