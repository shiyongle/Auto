<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>总首页</title>
<link href="css/all_index.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script type="text/javascript" src="${ctx}/js/imageScroll.js"></script>
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
</head>

<body>
	<div id="nav">
        	<div class="head_content">
        		<img src="css/images/cps-red.png"/>
                <a href="javascript:void(0);" onclick="onclickImg();">工业物流</a>
                <a href="${ctx}/index.jsp">我是终端客户</a>
                <a href="javascript:void(0);" onclick="onclickImg();">我是纸箱厂</a>     
            </div>
        <div id="container">
        	<!--特效-->
        	<div class="main">
            <div class="slider">
                <div id="scrollBox" class="scrollBox">
                    <div id="scrollPic" class="scrollPic">
                    </div>
                    <div id="scrollDot" class="scrollDot">
                    </div>
                    <div id="prevCur" class="prevCur">
                    </div>
                    <div id="nextCur" class="nextCur">
                    </div>
                </div>
            </div>
        	</div>
        </div>
          
        <div id="foot">
        	<div class="foot_content">
            	<img src="${ctx}/css/images/2wm.png"/>
            </div>
        </div>
    </div>
<script type="text/javascript" language="javascript">
	$(window).resize(function(){
		if($(window).width()>$(".main").width()){
			$("#container").width("100%");
		}else{
			$("#container").width(screen.width);
			$("#nav").width(screen.width);
		}
	});
	var scrollBox0=new ScrollBox();
	scrollBox0.Context=[
	{"img":"${ctx}/css/images/L4.png"},
	{"img":"${ctx}/css/images/L3.png"},
	{"img":"${ctx}/css/images/L6.png"},
	{"img":"${ctx}/css/images/L5.png"},
	{"img":"${ctx}/css/images/L2.png"}
	];
	scrollBox0.sDot="scrollDot";
	scrollBox0.pCur="prevCur";
	scrollBox0.nCur="nextCur";
	scrollBox0.cScrollPic="scrollPic";
	scrollBox0.cWidth=1280;
	scrollBox0.cHeight=490;
	scrollBox0.itemWidth=960; //当前图片宽度
	scrollBox0.itemHeight=490; //当前图片高度
	scrollBox0.speedTime = 4000;
	scrollBox0.auto = true;
	scrollBox0.init();
</script>
</body>
</html>
    