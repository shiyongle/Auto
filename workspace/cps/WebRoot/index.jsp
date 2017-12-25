<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/pages/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>CPS</title>
<link rel="stylesheet" type="text/css" href="css/new_index.css?vision=<%=Math.random()%>" />
<script type="text/javascript" src="<c:url value='/js/_common.js'/>"></script>
<script type="text/javascript" language="javascript" src="${ctx}/js/new_index.js?vision=<%=Math.random()%>" ></script>
 </head>
<body >
<!-- hss 2016年4月2日 去掉原头部，改为头部引入 -->
	<div id="head">
	  <%@ include file="pages/head_white.jsp" %>   
    </div> 
    <div id="banner">
        <ul>
             <!--hss 2016年3月31日 修改图片 -->
	    	<li style="left:0;"><img src="css/images/banner_index_01.png" alt="banner1"/></li>
	    	<li ><img src="css/images/banner_index_02.png" alt="banner2"/></li>
	    	<li ><img src="css/images/banner_index_03.png" alt="banner3"/></li>
	    	<li ><img src="css/images/banner_index_04.png" alt="banner4"/></li>
        </ul>      
       <a class="prev"  onselectstart="return false;"></a>
       <a  class="next" onselectstart="return false;"></a>
    </div>
    <div id="main">
    	<div id="content">
        	<div class="mk"> 
        	 <!--hss 2016年3月31日 修改图片 -->           	
                <img class="mk1" src="css/images/mk_index_design.png" alt="mk1" data-index="4" data-url="${ctx}/lineDesign/_create.net"/>
                <img class="mk2" src="css/images/mk_index_order.png" alt="mk2" data-index="3" data-url="${ctx}/menuTree/center.net?menu=c3d28df961a3c9ebfc7994361031186c" />
                <img class="mk3" src="css/images/mk_index_record.png" alt="mk3" data-index="2"  data-url="${ctx}/menuTree/center.net?menu=7a403c6ed40df9351325af3b5cfdce5b" />
                <img class="mk4" src="css/images/mk_index_logistics.png" alt="mk4" data-index="1" onclick="onclickImg();" />
            </div>
        </div>
        
    </div>
<!--  ----------------------------明星产品方案库 hss 2016年4月12日 ---------------------------    -->
		<div class="Starbackground">
		<div class="StarProduct">
			<div class="startit">
				<a href="">方案库</a>
			</div>
			<ul>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>低压电气</p>
					</div> <!-- 鼠标点击时出现覆盖 -->
					<div class="li1">
						<div class="tit" >低压电气</div>
						<div class="left">
							<img src="pages/StarProduct/images/appliances1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/appliances2.png"></img> <img
								src="pages/StarProduct/images/appliances3.png"></img> <img
								src="pages/StarProduct/images/appliances4.png"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>汽摩配</p>
					</div>
					<div class="li2">
						<div class="tit">汽摩配</div>
						<div class="left">
							<img src="pages/StarProduct/images/li2_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li2_2.jpg"></img> <img
								src="pages/StarProduct/images/li2_3.jpg"></img> <img
								src="pages/StarProduct/images/li2_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>鞋/服/箱包</p>
					</div>
					<div class="li3">
						<div class="tit">鞋/服/箱包</div>
						<div class="left">
							<img src="pages/StarProduct/images/clothing1.png"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/clothing2.png"></img> <img
								src="pages/StarProduct/images/clothing3.png"></img> <img
								src="pages/StarProduct/images/clothing4.png"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>家用电器</p>
					</div>
					<div class="li4">
						<div class="tit">家用电器</div>
						<div class="left">
							<img src="pages/StarProduct/images/li4_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li4_2.jpg"></img> <img
								src="pages/StarProduct/images/li4_3.jpg"></img> <img
								src="pages/StarProduct/images/li4_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>电子电器</p>
					</div>
					<div class="li5">
						<div class="tit">电子电器</div>
						<div class="left">
							<img src="pages/StarProduct/images/li5_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li5_2.jpg"></img> <img
								src="pages/StarProduct/images/li5_3.jpg"></img> <img
								src="pages/StarProduct/images/li5_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>五金制品业</p>
					</div>
					<div class="li6">
						<div class="tit">五金制品业</div>
						<div class="left">
							<img src="pages/StarProduct/images/li6_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li6_2.JPG"></img> <img
								src="pages/StarProduct/images/li6_3.JPG"></img> <img
								src="pages/StarProduct/images/li6_4.JPG"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>设备制造</p>
					</div>
					<div class="li7">
						<div class="tit">设备制造</div>
						<div class="left">
							<img src="pages/StarProduct/images/li7_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li7_2.jpg"></img> <img
								src="pages/StarProduct/images/li7_3.jpg"></img> <img
								src="pages/StarProduct/images/li7_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>家具</p>
					</div>
					<div class="li8">
						<div class="tit">家具</div>
						<div class="left">
							<img src="pages/StarProduct/images/li8_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li8_2.jpg"></img> <img
								src="pages/StarProduct/images/li8_3.jpg"></img> <img
								src="pages/StarProduct/images/li8_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>文教用品及玩具</p>
					</div>
					<div class="li9">
						<div class="tit">文教用品及玩具</div>
						<div class="left">
							<img src="pages/StarProduct/images/li9_1.png"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li9_2.jpg"></img> <img
								src="pages/StarProduct/images/li9_3.jpg"></img> <img
								src="pages/StarProduct/images/li9_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>食品与饮料</p>
					</div>
					<div class="li10">
						<div class="tit">食品与饮料</div>
						<div class="left">
							<img src="pages/StarProduct/images/li10_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li10_2.jpg"></img> <img
								src="pages/StarProduct/images/li10_3.jpg"></img> <img
								src="pages/StarProduct/images/li10_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>健康、运动、娱乐器材</p>
					</div>
					<div class="li11">
						<div class="tit">健康、运动、娱乐器材</div>
						<div class="left">
							<img src="pages/StarProduct/images/li11_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li11_2.jpg"></img> <img
								src="pages/StarProduct/images/li11_3.jpg"></img> <img
								src="pages/StarProduct/images/li11_4.jpg"></img>
						</div>
					</div>
				</li>
				<li>
					<div class="fugai" onclick="StarProductlist(this)">
						<p>其它</p>
					</div>
					<div class="li12">
						<div class="tit">其它</div>
						<div class="left">
							<img src="pages/StarProduct/images/li12_1.jpg"></img>
						</div>
						<div class="right">
							<img src="pages/StarProduct/images/li12_2.jpg"></img> <img
								src="pages/StarProduct/images/li12_3.jpg"></img> <img
								src="pages/StarProduct/images/li12_4.jpg"></img>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>

	<!-- --------------------服务功能图标 hss 2016年4月2日----------------------------- -->
	<div class="Services">
		<ul>
			<a href="${ctx}/fuwu_index.jsp" target="_blank" style="text-decoration:none;">
			<li>
			<img src="css/images/fuwuico.png"></img>
			<span>服务</span>
			<p>服务宝全程保障交易<br/>四大安全保障解决您的后顾之忧</p>
			</li>
			</a>
			<li  onclick="saledeliver_list();">
			<img src="css/images/saledeliver.png"></img>
			<span>送货凭证</span>
			<p>查看出库情况，规范出库单形式<br/>解决漏单，梳理财务对账，减少出错机率</p>
			</li>
			<li onclick="boardorder_list();" style="border:none">
			<img src="css/images/order.png"></img>
			<span>纸板订单</span>
			<p>每一家入驻企业都经过严格的线下整合<br/>杜绝不入流的黑作坊</p>
			</li>
		</ul>
	</div>
    
    <div id="foot">
       	<iframe src="${ctx}/pages/all_foot.jsp" scrolling="no" frameborder="0" vspace="0" width=100% height="150"></iframe>
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
function validateSkip(url)
{
	
	if("${session.user.fname}" !="" ){
		/* if(url.indexOf("redispathcher.net")>0&&"${session.user.ftype}"=="0"){
			layer.msg('没有权限查看', {	
			  	icon: 5 ,
			    offset: 'auto',
			    shift: 6,
			    time: 1000//1秒自动关闭
			});
		}else{ */
		window.location.href=url ;
		/* }	 */
	}else
	{
		layer.open(config);
	}
}
function saledeliver_list(){
	window.location.href="${ctx}/menuTree/center.net?menu=38394e4fdb782e0e2b45b8afdf2cfa64";//送货凭证
}
function boardorder_list(){
	window.location.href="${ctx}/menuTree/center.net?menu=7a403c6ed40df9351325af3b5cfdce5b";//纸板订单
}

function StarProductlist(e){
	var type=$(e).find("p").text();  //设置url 的id为明星产品类型，便于后面取值
	window.location.href="${ctx}/designschemes/StarProductList.net?type="+type;
}


</script>
   
</body>
</html>
