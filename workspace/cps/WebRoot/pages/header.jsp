<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link  rel="stylesheet" type="text/css" href="${ctx}/css/header.css"/>
	<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
	<title>快速下单头部页面</title>
	<script type="text/javascript">
		function goToSmallLogin(){
			var i = layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['350px', '310px'],
			    content:  window.getRootPath()+"/pages/smallLogin/smallLogin.jsp"
			});
			layer.style(i,{
				'border-radius':'25px',
				'border':'2px solid #a1a1a1'
			});
		}
		function search(){
			if($("#keyword").val() !=''){
				window.location.href="${ctx}/custproduct/list.net?keyword="+encodeURI(encodeURIComponent($("#keyword").val())) ;
			}else{
				window.location.href="${ctx}/custproduct/list.net" ;
			}
		}
		
		/*** 快速下单*/
		function quickOrder_(){
			if("${session.user.fname}" !=""){
				 location.href=window.getRootPath()+"/custproduct/list.net";
			}else{
				goToSmallLogin();
			}
		}
		/*** 在线设计*/
		function myNeeds(){
			location.href=window.getRootPath()+"/lineDesign/_create.net";
		}
		/*** 要货订单*/
		function deliverapplyOrder_(){
			if("${session.user.fname}" !=""){
				 location.href=window.getRootPath()+"/deliverapply/list.net";
			}else{
				goToSmallLogin();
			}
		}
		/*** 备货订单*/
		function mystockOrder_(){
			if("${session.user.fname}"!=""){
				 location.href=window.getRootPath()+"/mystock/list.net";
			}else{
				goToSmallLogin();
			}
		}
		/*** 收货情况*/
		function _mydelivery(){
			if("${session.user.fname}" !=""){
				 location.href=window.getRootPath()+"/mydelivery/list.net";
			}else{
				goToSmallLogin();
			}
		}
		
		/*** 用户中心*/
		function _ucenter(){
				 location.href=window.getRootPath()+"/usercenter/ucenter.net";
		}
		$(window).resize(function(){
		if($(window).width()>$("#head_content").width()){
			$("#head_content").width("100%");
		}else{
			$("#head_content").width(1280);
		}
		});
</script>
	</head>
<body>
        <div id="head">
        	<div id="message" style="display:none">
            	<div class="head_message">
                </div>
            </div>
        	<div id="head_content">
	            <div class="head_top">
	            	<img src="${ctx}/css/images/home.png" height="12px"/>
	            	<a href="${ctx}/all_index.jsp" target="_self">平台首页</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            	<s:if test="#session.user == null">
	            		<a href="${ctx}/user_login.net" target="_self">请登录</a>&nbsp;&nbsp;&nbsp;
	            		<a href="user_reg.net" target="_self">免费注册</a>
	            	</s:if>
					<s:if test="#session.user!= null">Hi,
						<s:property value="#session.user.fname"></s:property>
						<a href="${ctx}/user_logout.net" target="_self">退出</a>
					</s:if>
					<!--  <a href="${ctx}//vdispatcher/redispathcher.net" target="_self">[&nbsp;切换&nbsp;]</a>-->
	          	</div>
          	</div>
            <div class="head_buttom">
            	<form id ="searchForm" action="javascript:void(0)" method="post">
                	<table cellpadding="0" cellspacing="0" width="1280">
                    	<tr>
                        	<td>
                        		<a href="${ctx}/index.jsp" title="返回首页"><img src="${ctx}/css/images/slogo.png"/></a>
                                <input type="text" class="n1" placeholder="请输入产品名称,规格" id="keyword" name="keyword" value="${keyword}"/>
                                <span class="s3"><input type="submit" value="搜索" class="n2" onclick="search()" onkeypress="if (event.keyCode == 13) _search();"/></span>
                                <span class="s5"><input type="button" value="购物车(0)" class="ns" onclick="onclickImg();"/></span>
                                <span class="s4"><input type="button" value="用户中心" class="n3" onclick="_ucenter();"/></span>
							</td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="head_menu">
            	<a href="${ctx}/index.jsp" target="_self">首&nbsp;&nbsp;&nbsp;页</a>
                <a href="javascript:void(0);" onclick="quickOrder_();"  target="_self">快速下单</a>
                <a href="javascript:void(0);" onclick="myNeeds();" target="_self">在线设计</a>
                <a href="javascript:void(0);" onclick="ddjk();" target="_self" class="p2">订单监控&nbsp;<img id="arrow" src="${ctx}/css/images/xl.png"/></a>
               	<a href="javascript:void(0);" onclick="_mydelivery();" target="_self">收货情况</a>
               	<ul class="down_list">
                	<li><a href="javascript:void(0);" onclick="deliverapplyOrder_();">要货订单</a></li>
                    <li><a href="javascript:void(0);" onclick="mystockOrder_();">备货订单</a></li>
                </ul>
            </div>
        </div>
<script language="javascript">

	$(".head_menu a").mouseover(function(){
			$(this).addClass("p1").siblings().removeClass("p1");
			if($(this).hasClass("p2")){
				$(".down_list").show().mouseover(function(){$(this).show();});
				$("#arrow").attr("src","${ctx}/css/images/sg.png");
			}else{
				$(".down_list").hide();
				$("#arrow").attr("src","${ctx}/css/images/xl.png");
			}
	});
	$(".head_menu a").mouseout(function(){
			$(this).removeClass("p1");
			 if($(this).hasClass("p2")){
				$(".down_list").hide();
				$("#arrow").attr("src","${ctx}/css/images/xl.png");
			} 
	});
	$(".down_list").mouseout(function(){$(this).hide();});
	function ddjk(){
		if($(".down_list").is(":hidden")){
			$(".down_list").show().mouseover(function(){$(this).show();});
			$("#arrow").attr("src","${ctx}/css/images/sg.png");
		}else{
			$(".down_list").hide();
			$("#arrow").attr("src","${ctx}/css/images/xl.png");
		}
	}
</script>
</body>
</html>
