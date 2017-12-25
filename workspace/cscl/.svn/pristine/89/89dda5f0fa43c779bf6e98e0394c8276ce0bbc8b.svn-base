<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/pcWeb/common/taglibs.jsp"%>
<link href="${ctx}/pages/pcWeb/css/top.css" rel="stylesheet">
<script src="${ctx}/pages/pcWeb/js/layer/layer.js"></script>
<!-- 头部title -->
<nav class="navbar navbar-default" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle toggle"  data-target="#example-navbar-collapse">
				<span class="sr-only">切换导航 </span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>  
			<a class="navbar-brand" href="${ctx}/pages/pcWeb/index/index.jsp"><img src="${ctx}/pages/pcWeb/css/images/index/logo.png"/></a>
		</div>
		<div class="collapse navbar-collapse toggle2" id="example-navbar-collapse">
			<ul class="nav navbar-nav navbar-right" id="nav_ul">
				<li id="nav_index"><a href="${ctx}/pages/pcWeb/index/index.jsp">首页 </a></li>
				<li id="nav_call_car"><a  href="javascript:" onclick="judgeIdentity2()">我要叫车</a></li>
				<li id="nav_user_type"><a id="user_type_name" href="javascript:" onclick="judgeIdentity()"></a></li>
				<li id="nav_driver"><a href="${ctx}/pages/pcWeb/join_us/join_driver.jsp">司机加盟</a></li>
				<li id="nav_about_us"><a href="${ctx}/pages/pcWeb/about_us/about_us.jsp">关于我们</a></li>
				<li id="nav_help_center"><a href="${ctx}/pages/pcWeb/help/helpCenter.jsp">帮助中心</a></li>				
			</ul>
		</div>

	</div>
</nav>
<!-- ${ctx}/pages/pcWeb/login/login_in.jsp -->
<!-- ${ctx}/pages/pcWeb/register_iden/register.jsp -->

<script>
var usertype="1";
$(function(){
	//提示...
// 	$("#nav_call_car a").click(function(){//我要叫车
// 		$("#top_car_err").tooltip("show");
// 		setTimeout(function(){
// 			$("#top_car_err").tooltip("hide");
// 		},1000);
// 	})
// 	$("#nav_driver a").click(function(){//司机加盟
// 		$("#top_driver_err").tooltip("show");
// 		setTimeout(function(){
// 			$("#top_driver_err").tooltip("hide");
// 		},1000);
// 	})
// 	$(document).on("click","#nav_login a",function(){
// 		$("#top_login_err").tooltip("show");
// 		setTimeout(function(){
// 			$("#top_login_err").tooltip("hide");
// 		},1000);
// 	})
// 	$(document).on("click","#nav_register a",function(){
// 		$("#top_register_err").tooltip("show");
// 		setTimeout(function(){
// 			$("#top_register_err").tooltip("hide");
// 		},1000);
// 	})
	
	var html1='<li id="nav_login"><a href="${ctx}/pages/pcWeb/login/login_in.jsp" >登录</a></li>'+
	'<li class="li_border">|</li>'+
	'<li id="nav_register"><a href="${ctx}/pages/pcWeb/register_iden/register.jsp" class="redClolr">注册</a></li>';

	var html2='<li id="nav_user_name"><a class="redClolr">${username}</a></li>'+
			  '<li id="nav_exit"><a href="javascript:void(0)" onclick="login_exit()">退出</a></li>';
	if("${username}"!=""){
		$("#nav_ul").append(html2);
	}
	else{
		$("#nav_ul").append(html1);
	}
	
	//个人或企业用户判断
// 	var usertype="${usertype}";
	if(usertype&&"${username}"!=""){
		$("#nav_user_type").show();
		if(usertype=="1"){
			$("#user_type_name").text("个人用户");
			$("#menu_person").show();//控制menu_center.jsp页面显示个人用户菜单
			return false;
		}
		if(usertype=="2"){
			$("#user_type_name").text("企业用户");
			$("#menu_company").show();//控制menu_center.jsp页面的显示企业用户菜单
			return false;
		}
	}
		
})

/*退出系统*/
 function login_exit(){	
	window.location.href="${ctx}/pcWeb/logout.do" ;//退出后返回到首页
}

//导航栏展开，折叠
$(document).ready(function(){
	$(".toggle").click(function(){
		$(".toggle2").slideToggle("slow");
	});
});

//认证判断,认证后才能点击个人中心
function judgeIdentity(){
	var iden="${isPassIdentify}";
	if(iden!=null&&iden=="1"){//未认证
		layer.msg("认证后才可使用！");
		setTimeout(function(){
			window.location.href="${ctx}/pages/pcWeb/register_iden/identity_person.jsp";	
		},1000);
		return false;
	}	
	if(iden!=null&&iden=="2"){//认证驳回
		layer.msg("个人认证已驳回，请重新认证！");
		setTimeout(function(){
			window.location.href="${ctx}/pages/pcWeb/register_iden/identity_person.jsp";	
		},1000);
		return false;
	}
	if(iden!=null&&iden=="3"){//认证审核中
		layer.msg("认证审核中，请耐心等待！");
		return false;
		}
	else{//个人认证了或者企业认证了
		window.location.href="${ctx}/pages/pcWeb/menu/menu_center.jsp";
	}
}

//认证判断,认证后才能点击我要叫车
function judgeIdentity2(){
	var iden="${isPassIdentify}";
	if(iden!=null&&iden=="1"){//未认证
		layer.msg("认证后才可使用！");
		setTimeout(function(){
			window.location.href="${ctx}/pages/pcWeb/register_iden/identity_person.jsp";	
		},1000);
		return false;
	}	
	if(iden!=null&&iden=="2"){//认证驳回
		layer.msg("个人认证已驳回，请重新认证！");
		setTimeout(function(){
			window.location.href="${ctx}/pages/pcWeb/register_iden/identity_person.jsp";	
		},1000);
		return false;
	}
	if(iden!=null&&iden=="3"){//认证审核中
		layer.msg("认证审核中，请耐心等待！");
		return false;
		}
	if("${username}"!=""){
		window.location.href="${ctx}/pages/pcWeb/call_car/menu_call_car.jsp";
	}
	else{
		window.location.href="${ctx}/pages/pcWeb/login/login_in.jsp";
	}
}
</script>