<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%> 
<%@ include file="/pages/pc/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>首页</title>
<link href="${ctx}/pages/pc/js/easyui/themes/default/easyui.css" type="text/css"  rel="stylesheet" />
<link href="${ctx}/pages/pc/js/easyui/themes/icon.css" type="text/css"  rel="stylesheet" />
<script src="${ctx}/pages/pc/js/jquery-2.1.4.min.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/common.js" type="text/javascript"></script>
<script src="${ctx}/pages/pc/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<style type="text/css">
.Mtree-info{margin-top:5px}.Mtree-info li{height:20px;list-style-type:none}.Mtree-info a{text-decoration:none;font-size:12px}.Mtree-info a:hover{color:red}
.Mbottom{height:40px;min-width:1004px;width:100%;line-height:40px;color:blue;font-size:13px;margin:0 auto;text-align:center;font-weight:700;float:left}
#hello{text-align: right;font-family: "Microsoft YaHei" ! important;color:#0000ff; font-size: 15px;margin-right:3px; margin-top: 62px;  }
#hello span{color:#0000ff ;}
#hello a {color: #0000ff;}
#out{text-align: right;font-family: "Microsoft YaHei" ! important;font-size: 15px;color:#888888;margin-right:10px;   }
#out a{color:#0000ff;}
#north{ height: 100px;  background:#E6F0FF;}
</style>
<script type="text/javascript">
$(document).ready(function(){
    /*        by les 首页动态加载                 */
	$.ajax({
		url : '${ctx}/menu/getMenuList.do?',
		async: false,
		success: function(res){
			var preurl = "${ctx}";
			var menus = JSON.parse(res);
			//一级菜单
			for (index in menus){
				var childmenus = menus[index].children;
				var parentnode = menus[index].text;
				
				var html = '<div title="'+parentnode+'" style="overflow:auto;padding:10px;">';
				//二级菜单
				if(childmenus){
					html += '<ul  class="Mtree-info">';
					for(var i=0;i<childmenus.length;i++){
						var node = childmenus[i].text;
						var url = preurl+childmenus[i].attributes.url;
						/*********************当引号 要转义，当引号 要转义，当引号 要转义，当引号 要转义，当引号 要转义，*********************/
						html += '<li><a href="javascript:void(0)" onclick="db_click(\''+node+'\',\''+url+'\')">'+node+'</a></li>';

					}					
				}
				html +='</ul></div>';
				 $('#leftMenu1').append(html);
			}
			
		},
		failure: function(){
			$.messager.alert("提示", "您还未开通权限,请联系管理员...");
		}
	});
    /*        by les 首页动态加载                 */
    
	$('#leftMenu1').accordion({    
	    animate:true,
	    fit:true,
	    selected:1
	});  
	
});
function db_click(text,url){
        if ($("#tabs").tabs('exists', text)) {
            $('#tabs').tabs('select', text);
        } else {
        	if(url !=''){
           		 var content = '<iframe scrolling="no" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';  
        		 $('#tabs').tabs('add', {
	                title : text,
	                closable : true,
	                content : content
           		 });
        	}else{
        		$.messager.alert("提示", "开发中...");
        	}
        }
}
function out(){
		$.messager.confirm('消息', '您想要退出该系统吗？', function(r){
			if (r){
			   window.location.href="${ctx}/user/logout.do" ;
			}
		});
	}







		
	
</script>

</head>
<body class="easyui-layout">

    <div id="north" data-options="region:'north',split:true" >
    <img src="${ctx}/pages/pc/images/cps-red.png"  style="left;margin-left: -35px;margin-top: 12px;"draggable="false" />
	  <div  style="float: right;"> 
		 <c:set var="username" scope="session" value="${username}"/>
		 <c:choose >
				    <c:when test="${username!=null}">
				   			<div id="hello">  Hi,<span>${username}</span>
				   				 <a href="#" onclick="out()" id="out">退出</a> 
				   			</div>
				   			
				    </c:when>
				    <c:otherwise>
				           	<div id="hello">  你好,请先&nbsp<a href="${ctx}/login.jsp">登录</a></div>
				    </c:otherwise>
		 </c:choose>  
      </div>
    </div>   
	<div data-options="region:'west',split:true" title="导航菜单" style="width: 207px;">
	<div id="leftMenu1" style="border: 0;">
<!-- 			<div title="运维中心" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul  class="Mtree-info"> -->
<%--  				     <li><a href="javascript:void(0)" onclick="db_click('订单管理','${ctx}/order/list.do')">订单管理</a></li>  --%>
<!--      		  </ul> -->
<!-- 			</div> -->
<!-- 			<div title="基础资料" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul  class="Mtree-info"> -->
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('用户管理','${ctx}/user/list.do')">用户管理</a></li> --%>
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('车辆管理','${ctx}/car/list.do')">车辆管理</a></li> --%>
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('消息管理','${ctx}/message/list.do')">消息管理</a></li> --%>
<%--     				 <li><a href="javascript:void(0)" onclick="db_click('客户资料','${ctx}/usercustomer/list.do')">客户资料</a></li>				 --%>
<!--      		  </ul> -->
<!-- 			</div> -->
<!-- 			<div title="认证管理" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul class="Mtree-info"> -->
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('认证管理','${ctx}/iden/list.do')">认证管理</a></li> --%>
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('APP首页图片管理','${ctx}/upload/load.do')">APP首页图片管理</a></li> --%>
<!--      		   </ul> -->
<!-- 			</div> -->
<!-- 			<div title="规则管理" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul class="Mtree-info"> -->
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('临时用车计费规则','${ctx}/rule/list.do')">临时用车计费规则</a></li> --%>
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('协议用车计费规则','${ctx}/protocol/list.do')">协议用车计费规则</a></li> --%>
<%-- 				     <li><a href="javascript:void(0)" onclick="db_click('好运券规则管理','${ctx}/couponRule/list.do')">好运券规则管理</a></li> --%>
<!--      		   </ul> -->
<!-- 			</div> -->
<!-- 			<div title="好运券管理" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul class="Mtree-info"> -->
<%-- 				      <li><a href="javascript:void(0)" onclick="db_click('首页好运券管理','${ctx}/coupons/list.do')">首页好运券管理</a></li> --%>
<%-- 				      <li><a href="javascript:void(0)" onclick="db_click('指派好运券管理','${ctx}/couponsAssign/list.do')">指派好运券管理</a></li> --%>
<%-- 				      <li><a href="javascript:void(0)" onclick="db_click('业务员好运券管理','${ctx}/couponsSales/list.do')">业务员好运券管理</a></li> --%>
<!--      		   </ul> -->
<!-- 			</div> -->
<!-- 			<div title="系统管理" style="overflow:auto;padding:10px;"> -->
<!-- 			   <ul class="Mtree-info"> -->
<%-- 				      <li><a href="javascript:void(0)" onclick="db_click('菜单管理','${ctx}/menu/list.do')">菜单管理</a></li> --%>
<%-- 				      <li><a href="javascript:void(0)" onclick="db_click('权限管理','${ctx}/user/list.do')">权限管理</a></li> --%>
<!--      		   </ul> -->
<!-- 			</div> -->
<%-- <div title="报表中心" style="overflow:auto;padding:10px;">
			   <ul class="Mtree-info">
				      <li><a href="javascript:void(0)" onclick="db_click('业务员司机运费报表','${ctx}/pages/pc/reportCenter/freightReport.jsp')">业务员司机运费报表</a></li>
     		   </ul>
	</div> --%>
			
	</div>
	</div>
	
	<div data-options="region:'center',title:'我的业务'" id="centerR">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
      		<div title="首页"></div>
    	</div>
	</div>
	<div data-options="region:'south',border:false" style="height:50px;background:#E6F0FF;padding:10px;">
		<div class="Mbottom">Copyright © 2015 CPS All Rights Reserved</div>
	</div>
	
	
	
<div id="rcmenu" class="easyui-menu" style="">
    <div data-options="iconCls:'icon-cancel'" id="closecur">
        关闭
    </div>
    <div id="closeall">
        关闭全部
    </div>
    <div id="closeother">
        关闭其他
    </div>
    <div class="menu-sep"></div>
    <div id="closeright">
        关闭右侧标签页
    </div>
    <div id="closeleft">
        关闭左侧标签页
    </div>
</div>

</body>
</html>

<script>
/*右键菜单*/
$(function(){
    
    $(".tabs-header").on('contextmenu',function(e){
        e.preventDefault();
        $('#rcmenu').menu('show', {
            left: e.pageX,
            top: e.pageY
        });
    });
    //关闭当前标签页
    $("#closecur").on("click",function(){
        var tab = $('#tabs').tabs('getSelected');
        var index = $('#tabs').tabs('getTabIndex',tab);
        $('#tabs').tabs('close',index);
    });
    //关闭所有标签页
    $("#closeall").on("click",function(){
        var tablist = $('#tabs').tabs('tabs');
        for(var i=tablist.length-1;i>=0;i--){
            $('#tabs').tabs('close',i);
        }
    });
    //关闭非当前标签页（先关闭右侧，再关闭左侧）
    $("#closeother").on("click",function(){
        var tablist = $('#tabs').tabs('tabs');
        var tab = $('#tabs').tabs('getSelected');
        var index = $('#tabs').tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $('#tabs').tabs('close',i);
        }
        var num = index-1;
        for(var i=num;i>=0;i--){
            $('#tabs').tabs('close',0);
        }
    });
    //关闭当前标签页右侧标签页
    $("#closeright").on("click",function(){
        var tablist = $('#tabs').tabs('tabs');
        var tab = $('#tabs').tabs('getSelected');
        var index = $('#tabs').tabs('getTabIndex',tab);
        for(var i=tablist.length-1;i>index;i--){
            $('#tabs').tabs('close',i);
        }
    });
    //关闭当前标签页左侧标签页
    $("#closeleft").on("click",function(){
        var tab = $('#tabs').tabs('getSelected');
        var index = $('#tabs').tabs('getTabIndex',tab);
        var num = index-1;
        for(var i=0;i<=num;i++){
            $('#tabs').tabs('close',0);
        }
    });
         
});
</script>
 
