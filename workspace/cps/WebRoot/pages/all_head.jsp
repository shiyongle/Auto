<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>红色头部</title>

<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>


<style>
*{margin:0px auto;padding:0px;}
li{list-style:none;}
img{border:none;}
a{text-decoration: none;}
#header{width:100%;height:100px;background:#C00;position:relative;font-size:14px;font-family:微软雅黑;font-weight:bold;color:#fff;}

#top{width:1280px;height:80px;padding-top:10px;}
.top_left{float:left;}
#top a{color:#fff;font-family:微软雅黑;}
#top a:hover{color:#000;}

#logo{width:360px; height:80px;float:left;border-right:2px solid #fff;}
#logo img{display:block;width:140px;height:40px;padding:10px 100px;}
#logo span{width:340x;font-family:宋体;color:#fff;text-align:center;letter-spacing:3px;padding:0 5px; }

.ico{height:18px;width:18px; margin-left:20px; margin-top:30px;}

#top .link{float:right;height:80px;position:relative;z-index:999;margin-right:-25px;}
#top .link a{display:block;float:left;padding:0 32px;line-height:85px;color:#fff;}
#top .link a:hover{color:#000;}

#nav_onlinedesign{position:relative;}
.ope_design{display:none;position:absolute;right:-5px;top:70px;cursor:default;z-index:200;}
#nav_onlinedesign:hover .ope_design{display:block;}
#nav_onlinedesign .addDemand{display:block;width:140px;height:100px;background:#c00;margin:auto ;height:32px;line-height:32px;text-align:center;cursor:pointer;color:#fff;}
#nav_onlinedesign .addDemand:hover {color:#000;}

#top .anewwelcom{margin-left: 45px;cursor:pointer;padding:6px 8px;}
#top .anewreg{margin-left: 10px;cursor:pointer;padding:6px 8px;}
#top .spnewwelcom{margin-left: 45px;width:30px;}
#top .anewwelcom:hover,#top .anewreg:hover{color:#000;}
</style>
</head>
<body>
<div id="header">
<div id="top">
<div class="top_left">
        <div id="logo">
    	<img src="${ctx}/css/images/LOGO2.png" alt="logo" />
    	<span >打造中小企业降本提效，价值倍增第一平台</span>
    	</div>
        <s:if test="#session.user == null">
		         <img src="${ctx}/css/images/index_04.png" class="ico"></img>
		         <a class="anewwelcom" style="margin-left:0; color:#fff;" >登录</a>
		         <a class="anewreg">立即注册</a>          		 	
         </s:if>
       	 <s:else>
	       		<img src="${ctx}/css/images/index_04.png" class="ico" style=";margin-right:-35px;"></img>
	       		<span class="spnewwelcom" >Hi,<s:property value="#session.user.fname"></s:property>
	       		<a href="${ctx}/user_logout.net" target="_self" style="margin-left:15px;">退 出</a></span>
       	 </s:else> 
 </div>      
       <div class="link">
       
        	<a href="${ctx}/index.jsp"  >首 页</a>
        	<a  onclick="lineDesgin();"style="color:#fff; cursor:pointer;" >用户中心</a>
        	
            <a href="javascript:void(0)" id="nav_onlinedesign">
            	在线设计<span style="color:#fff;">﹀</span>
            	<div class="ope_design">
            		<span class="addDemand" onclick="location.href='${ctx}/lineDesign/_create.net';">发布需求&nbsp;</span>
                    <span class="addDemand" onclick="location.href='${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004';">查看需求&nbsp;</span>                   
            	</div>
            </a>
            <a href="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c" >在线下单</a>
             <a href="http://www.djcps.com/Works.aspx?tid=76" target="_blank">经典案例</a> 
        </div>
        </div>
        </div>
<script type="text/javascript" language="javascript">
var config={
	    title:'',
	    move:false,
	    type: 2,
	    anim:true,
	    skin:'layskin',
	    area: ['350px', '310px'],
	    content:  window.getRootPath()+"/pages/smallLogin/smallLogin.jsp"
	};

$("a[class='anewwelcom']").click(function(){
	layer.open(config);
});

$("a[class='anewreg']").click(function(){
	window.location.href="${ctx}/user_smallReg.net" ;
});

function lineDesgin(){
	//window.location.href="${ctx}/menuTree/center.net?menu=d7cc44bc024adf67cbe459cf57bb2ae4"; //用户中心
	window.location.href="${ctx}/menuTree/center.net?menu=64d6d70f6f80f70bbe73b62d9cf9b004";//在线设计
}
//百分比宽度缩放
 $(document).ready(function(){	
	if(screen.width>1280){/*2016年4月14日14:28:27 HT*/
		$("#header").width("100%");
				
			}else{
				$("#header").width(1280);
			}
	});
$(window).resize(function(){	
	if($(window).width()>1280){
		$("#header").width("100%");
		
	}else{
		$("#header").width(1280);
	}
}); 

</script> 

</body>


</html>
