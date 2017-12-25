<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<%@ include file="/pages/all_head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>快速下单</title>
<link type="text/css" href="${ctx}/css/takeOrderQuick.css" rel="stylesheet"/>
<script src="${ctx}/js/jquery-1.8.3.min.js" type="text/javascript" language="javascript"></script>
<script language="javascript">
	$(document).ready(function(){
		var menu = "${menu}";
		$("#"+menu).parent().parent().attr("style","displsy:block");
		$("#"+menu).parent().attr("class","current_t");
		$("#"+menu).css("color","red");
		$("#"+menu).css("fontSize","17px");
		if(menu=="64d6d70f6f80f70bbe73b62d9cf9b004"){//在线设计列表
// 			 $("#iframepage").attr("src","${ctx}/firstproductdemand/new_list.net");
		}else if(menu =="c3d28df961a3c9ebfc7994361031186c"){//快速下单列表
			$("#iframepage").attr("src","${ctx}/custproduct/new_list.net");
		}else if(menu =="d7cc44bc024adf67cbe459cf57bb2ae4"){//用户资料列表
			$("#iframepage").attr("src","${ctx}/usercenter/new_ucenter.net");
		}else{
			$('#'+menu).click();
		}
	});
	function goTop(){
		parent.scrollTo(0,0);
	}
	function quickShop(){
		
	}
</script>	
</head>
<body style="background-color:#f1f1f1;" class="noselect">
	<div id="nav">
        <!--中间内容开始-->
        <div id="container">
            <div class="menu">
            	<div class="space"></div>
            	<ul class="ztree">
<!--                 	<li> -->
<!--                     	<p onclick="tree(this);">我的业务</p> -->
<!--                     	<ul id="mywork" class="ztree_content"> -->
<%--                     	 <c:forEach var="entry" items="${menulist }"> --%>
<%-- 							<li auto="ftype0" onclick="current_tab(this);"><a id="${entry.fid }"  href="javascript:void(0);" onclick="${entry.furl}();" >${entry.fname }&nbsp;&nbsp;&gt;</a></li> --%>
<%-- 						 </c:forEach> --%>
<!--                         </ul> -->
<!--                     </li> -->
                	<li>
                    	<p onclick="tree(this);">我的业务</p>
                    	<ul style="display:none;" class="ztree_content">
							<li onclick="current_tab(this);"><a id="64d6d70f6f80f70bbe73b62d9cf9b004"  href="javascript:void(0);" onclick="online_list();" >在线设计&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="c3d28df961a3c9ebfc7994361031186c"  href="javascript:void(0);" onclick="lineOrder_list();"  target="forTree">快速下单&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="38394e4fdb782e0e2b45b8afdf2cfa64"  href="javascript:void(0);" onclick="saledeliver_list();"  target="forTree">送货凭证&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="7ca47c2e523c6f8e75b7ba2fc3b408eb"  href="javascript:void(0);" onclick="myReceipt_list();"  target="forTree">我的收货&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="7a403c6ed40df9351325af3b5cfdce5b"  href="javascript:void(0);" onclick="board_list();"  target="forTree">纸板订单&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a href="javascript:void(0);" onclick="card_list();"  target="forTree">纸箱接单&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="8f3b223239bbc3454aaf308e406a351b"  href="javascript:void(0);" onclick="schemeDesign_list();"  target="forTree">我的设计&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id=""  href="javascript:void(0);" onclick="customer_list();"  target="forTree">客户管理&nbsp;&nbsp;&gt;</a></li>
                       		<li onclick="current_tab(this);"><a id="d7e5558ca675c2a1460d5c5f2b595d75"  href="javascript:void(0);" onclick="myProduct_list();"  target="forTree">产品档案&nbsp;&nbsp;&gt;</a></li>
                       		 <li onclick="current_tab(this);"><a id="a3390eb8e7a1c9f68bffb717143d2f73" href="javascript:void(0);" onclick="my_designschemes();"  target="forTree">作品管理&nbsp;&nbsp;&gt;</a></li>
                       		<li onclick="current_tab(this);"><a href="javascript:void(0);" onclick="statement_list();"  target="forTree">对账单&nbsp;&nbsp;&nbsp;&gt;</a></li>
                        </ul>
                    </li>
                    <li>
                    	<p onclick="tree(this);">用户中心</p>
                    	<ul style="display:none;" class="ztree_content">
                    		<li onclick="current_tab(this);"><a id="d7cc44bc024adf67cbe459cf57bb2ae4" href="javascript:void(0);" onclick="my_materialCom();"  target="forTree">常用材料&nbsp;&nbsp;&gt;</a></li>
							<li onclick="current_tab(this);"><a id="d7cc44bc024adf67cbe459cf57bb2ae4" href="javascript:void(0);" onclick="my_centerinfo();"  target="forTree">用户资料&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="7fc88aeedae8d92a749abc4e9815923d" href="javascript:void(0);" onclick="my_pwd();"  target="forTree">修改密码&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a href="javascript:void(0);" onclick="my_param();"  target="forTree">用户参数&nbsp;&nbsp;&gt;</a></li>
                            <li onclick="current_tab(this);"><a id="bca1ea039f43ad2dbac8e894699a7444" href="javascript:void(0);" onclick="my_address();"  target="forTree">地址管理&nbsp;&nbsp;&gt;</a></li>
							<li onclick="current_tab(this);"><a id="a3390eb8e7a1c9f68bffb717143d2f73" href="javascript:void(0);" onclick="my_designschemes();"  target="forTree">作品管理&nbsp;&nbsp;&gt;</a></li>
                        </ul>
                    </li>
                     <li>
                    	<p onclick="tree(this);">智能工具</p>
                    	<ul style="display:none;" class="ztree_content">
                    		<li onclick="current_tab(this);"><a  href="javascript:void(0);" onclick="calculator();"  target="forTree">智能换算&nbsp;&nbsp;&gt;</a></li>
							<li onclick="current_tab(this);"><a  href="javascript:void(0);" onclick="match();"  target="forTree">智能匹配&nbsp;&nbsp;&gt;</a></li>
							 <li onclick="current_tab(this);"><a id="" href="javascript:void(0);" onclick="unitchage();"  target="forTree">单位换算&nbsp;&nbsp;&gt;</a></li>
							
                        </ul>
                    </li>
                </ul>
            </div>
            <div class="content" id="content">
            	<iframe id="iframepage" src="${ctx}/firstproductdemand/new_list.net"    scrolling="no"  frameborder="0"   name="forTree" ></iframe> <!--HT 2016年4月14日14:31:16-->
		        <div id="quickTip" style="position: fixed;bottom:160px;margin-left:1070px;display:none;cursor:pointer;">
		        	<div style="background:url(${ctx}/css/images/quickShop.png) 0px 0px no-repeat;width:35px;height:40px;" onclick="goTop();"></div>
		        	<div style="background:url(${ctx}/css/images/quickShop.png) 0px -40px no-repeat;width:35px;height:40px;" onclick="quickShop()"></div>
		        </div>
            </div>
        </div>
        <iframe id="panelPrint"  style="display:none;"></iframe>
        <div id="foot">
        	<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" width=100% height="150" frameborder="0" id="allfoot"></iframe>
        </div>
    </div>
<script language="javascript">
//$(function(){
//		$("#mywork").children("li").css("display","none");
//		var ftypeno=${session.user.ftype};
//		$("li[auto=ftype"+ftypeno+"]").css("display","block");
//});

	$(window).resize(function(){
		if($(window).width()>1280){
			$("#foot").width("100%");
		}else{
			$("#foot").width(1280);
		}
	});
	//点击一级菜单
	function tree(t){
		if($(t).parent().children("ul").is(":visible")){//判断父级菜单是否展开
			$(t).parent().children("ul").hide();
		}else{
			$(t).parent().children("ul").show();
			$(t).parent().siblings().children("ul").hide();
		}
	}
	
	function current_tab(t){
		$(t).addClass("current_t");
		//同级兄弟移除点击样式
		$(t).siblings().removeClass("current_t");
		//如果针对显示的ul任何一条进行点击，那么其他所有ul下面的li全部移除点击样式
		$(t).parent().parent().siblings().children("ul").children().removeClass("current_t");
		$(t).parent().parent().siblings().children("ul").children().children().css({color:"black",fontSize:"15px"});
		$(t).children().css({color:"red",fontSize:"17px"});
		$(t).siblings().children().css({color:"black",fontSize:"15px"});
	}
	
	
	/*** 在线设计*/
	function online_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/firstproductdemand/new_list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/*** 快速下单*/
	function lineOrder_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/custproduct/new_list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*地址管理 */
	function my_address(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/address/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*常用材料 */
	function my_materialCom(){
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false, //禁止拖拽
			    type: 2, //层类型传入的值有：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）。
			    anim:true,
			    area: ['820px', '470px'], //宽高
			    //content:  "${ctx}/pages/board/commonFmaterial.jsp"
			    content: "${ctx}/productdef/commonFmaterial.net"
			});
			parent.layer.style(i,{
				'border-radius':'10px'  //边框半径10px,即内容
			});
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*菜单配置 */
	function my_menu(){
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['820px', '470px'],
			    content:  "${ctx}/usermenu/list.net"
			});
			parent.layer.style(i,{
				'border-radius':'10px'
			});
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*用户资料 */
	function my_centerinfo(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/usercenter/new_ucenter.net");
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*修改密码 */
	function my_pwd(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/usercenter/update_pwd.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/* 用户参数 */
	function my_param(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/usercenter/userparam.net");
		}else{
			window.goToSmallLogin();
		}
	}
	//我的收货
	function myReceipt_list(){
		if("${session.user.fname}" !=""){
			 //$("#iframepage").attr("src","${ctx}/usercenter/update_pwd.net");
			 $("#iframepage").attr("src","${ctx}/pages/deliverorder/myReceipt_list.jsp");
		}else{
			window.goToSmallLogin();
		}
	}
	//纸板订单
	function board_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/board/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	//纸箱接单
	function card_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/productplan/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/*** 送货凭证*/
	function send_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/saledeliver/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/*** 送货凭证*/
	function saledeliver_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/saledeliver/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/**客户管理*/
	function customer_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/customer/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/*** 产品档案*/
	function myProduct_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/productdef/productlist.net");
		}else{
			window.goToSmallLogin();
		}
	}
	/**我的设计*/
	function schemeDesign_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/schemedesign/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
	
	/*瓦楞纸箱内外径智能换算器*/
	function calculator(){
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['1000px', '350px'],
			    content:  "${ctx}/pages/calckits/calculator.jsp"
			});
			parent.layer.style(i,{
				'border-radius':'10px'
			});
		}else{
			window.goToSmallLogin();
		}
	}
	/*智能匹配器*/
	function match(){
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['1000px', '580px'],
			    content:  ["${ctx}/pages/calckits/match.jsp",'no']
			});
			parent.layer.style(i,{
				'border-radius':'10px'
			});
		}else{
			window.goToSmallLogin();
		}
	}	
	
	/**	*单位换算 */
	function unitchage(){
		//$("#iframepage").attr("src","${ctx}/pages/calckits/Unitconversion.jsp");
		if("${session.user.fname}" !=""){
			var i = parent.layer.open({
			    title:'',move:false,
			    type: 2,
			    anim:true,
			    area: ['1000px', '230px'],
			    content:  ["${ctx}/pages/calckits/Unitconversion.jsp",'no']
			});
			parent.layer.style(i,{
				'border-radius':'10px'
			});
		}else{
			window.goToSmallLogin();
		}
	}
	
	/**	*作品管理 */
	function my_designschemes(){
		if("${session.user.fname}" !=""){

			 $("#iframepage").attr("src","${ctx}/pages/designschemes/product_list.jsp");
		}else{
			window.goToSmallLogin();
		}
	}
	
	/*** 对账单*/
	function statement_list(){
		if("${session.user.fname}" !=""){
			 $("#iframepage").attr("src","${ctx}/statement/list.net");
		}else{
			window.goToSmallLogin();
		}
	}
</script>
</body>
</html>
